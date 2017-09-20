package com.comba.android.combacommon.pay;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import com.comba.android.combacommon.R;
import com.comba.android.combacommon.net.HttpListener;
import com.comba.android.combacommon.net.HttpRequestUtil;
import com.unionpay.UPPayAssistEx;
import com.yanzhenjie.nohttp.rest.Response;

/**
 * Created by chenhailin on 2017/6/5.
 */

public class UPPayActivity extends UPpayBaseActivity implements HttpListener<String>{
    public static final int PLUGIN_VALID = 0;
    public static final int PLUGIN_NOT_INSTALLED = -1;
    public static final int PLUGIN_NEED_UPGRADE = 2;
    public static final String LOG_TAG="UPPayActivity";
    private final String mMode = "01";
    //模拟获取订单url
    private static final String TN_URL_01 = "http://101.231.204.84:8091/sim/getacptn";

    public  static final  int RESUTL_CODEPAY_SUCCESS=0x01;
    public  static final  int RESUTL_CODEPAY_FAIL=0x02;
    public  static final  int RESUTL_CODEPAY_CANCLE=0x03;
    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        setContentView(R.layout.uptest_layout);
        toPay();

    }

    //点击事件发起支付
    public void toPay(){
        // “00” – 银联正式环境
        // “01” – 银联测试环境，该环境中不发生真实交易
        String serverMode = mMode;
          getTradeNum();
    }

    //唤起银联插件
    private void evokeUpPlugIn(UPPayActivity upPayActivity, String tn, String serverMode) {
        int ret = UPPayAssistEx.startPay(upPayActivity, null, null, tn, serverMode);
        if (ret == PLUGIN_NEED_UPGRADE || ret == PLUGIN_NOT_INSTALLED) {
            // 需要重新安装控件
            Log.e(LOG_TAG, " plugin not found or need upgrade!!!");

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("提示");
            builder.setMessage("完成购买需要安装银联支付控件，是否安装？");

            builder.setNegativeButton("确定",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            UPPayAssistEx.installUPPayPlugin(UPPayActivity.this);
                            dialog.dismiss();
                        }
                    });

            builder.setPositiveButton("取消",
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            builder.create().show();

        }
        Log.e(LOG_TAG, "" + ret);
    }

    //请求生成订单号
    private void getTradeNum() {
        HttpRequestUtil.commonHttpRequst(this,true,false,TN_URL_01,null,this,0);
//        return  "";
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }

        String msg = "";
        /*
         * 支付控件返回字符串:success、fail、cancel 分别代表支付成功，支付失败，支付取消
         */
        String str = data.getExtras().getString("pay_result");
        if (str.equalsIgnoreCase("success")) {

            // 如果想对结果数据验签，可使用下面这段代码，但建议不验签，直接去商户后台查询交易结果
            // result_data结构见c）result_data参数说明
            if (data.hasExtra("result_data")) {
                String result = data.getExtras().getString("result_data");
                try {
                    JSONObject resultJson = new JSONObject(result);
                    String sign = resultJson.getString("sign");
                    String dataOrg = resultJson.getString("data");
                    // 此处的verify建议送去商户后台做验签
                    // 如要放在手机端验，则代码必须支持更新证书
                    boolean ret = verify(dataOrg, sign, mMode);
                    if (ret) {
                        // 验签成功，显示支付结果
                        msg = "支付成功！";
                    } else {
                        // 验签失败
                        msg = "支付失败！";
                    }
                } catch (JSONException e) {
                }
            }
            // 结果result_data为成功时，去商户后台查询一下再展示成功
            msg = "支付成功！";
            this.setResultUnion(RESUTL_CODEPAY_SUCCESS);
        } else if (str.equalsIgnoreCase("fail")) {
            msg = "支付失败！";
            this.setResultUnion(RESUTL_CODEPAY_FAIL);
        } else if (str.equalsIgnoreCase("cancel")) {
            msg = "用户取消了支付";
            this.setResultUnion(RESUTL_CODEPAY_CANCLE);
        }
        this.setResultUnion(1);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("支付结果通知");
        builder.setMessage(msg);
        builder.setInverseBackgroundForced(true);
        // builder.setCustomTitle();
        builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();


    }

    private boolean verify(String msg, String sign64, String mode) {
        // 此处的verify，商户需送去商户后台做验签
        return true;
    }


    @Override
    public void onSucceed(int what, Response<String> response) {
        if (what==0) {
            String tradeNum=response.get();
            evokeUpPlugIn(this,tradeNum,mMode);
        }
    }

    @Override
    public void onFailed(int what, Response<String> response) {
        showMessageDialog(R.string.request_failed, response.getException().getMessage());
    }

    public  void setResultUnion(int code){
            setResult(code);
            this.finish();
    }

}
