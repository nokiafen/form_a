package com.comba.android.combacommon.net;

import android.app.Activity;
import android.graphics.Bitmap;

import com.comba.android.combacommon.entity.ParamsBeans;
import com.comba.android.combacommon.net.httpAbout.SSLContextUtil;
import com.comba.android.combacommon.pay.UPPayActivity;
import com.comba.android.combacommon.pay.UPpayBaseActivity;
import com.yanzhenjie.nohttp.BasicBinary;
import com.yanzhenjie.nohttp.Binary;
import com.yanzhenjie.nohttp.BitmapBinary;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.tools.ImageLocalLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLContext;

/**
 * Created by chenhailin on 2017/5/19.
 */

public class HttpRequestUtil {


    /**
     *
     * @param isHttpsRequest 是否是https请求
     * @param requestWay  请求方式 Get Post ...
     * @param activity   继承BaseOkHttpActivity;
     * @param listener  回调
     * @param url      请求链接
     * @param what     该请求标记
     * @param cancleble    是否可以取消
     * @param isLoading  是否显示加载中
     */

    public static  void  getPhotoRequest(Boolean isHttpsRequest ,Boolean needHttpsCer, RequestMethod requestWay, Activity activity,HttpListener listener,String url,int what,Boolean cancleble,Boolean isLoading ){
      Request<Bitmap> request= NoHttp.createImageRequest(url, requestWay);
        needHttpsRequest(isHttpsRequest, needHttpsCer, request);
        ((BaseOkHttpActivity)activity).request(what,request
       , listener,cancleble,isLoading);
    }


    /**
     *
     * @param isHttpsRequest 请求方式
     * @param needHttpsCer 请求证书需要？
     * @param activity
     * @param listener  回调接口
     * @param url  链接
     * @param what  请求标记
     * @param cancleble 是否可取消
     * @param isLoading 是否含弹窗
     * @param paramsBeansArrayList  参数集合
     * @param binariesName  文件上传集合名
     */
    public static  void  upLoadPhotoRequest(Boolean isHttpsRequest , Boolean needHttpsCer, Activity activity, HttpListener listener, String url, int what, Boolean cancleble, Boolean isLoading , ArrayList<ParamsBeans> paramsBeansArrayList,String binariesName){
        Request<String> request = NoHttp.createStringRequest(url, RequestMethod.POST );
        needHttpsRequest(isHttpsRequest, needHttpsCer, request);
        // 添加普通参数。
        for (ParamsBeans beans:paramsBeansArrayList )
             {
                 if (!beans.isFile()) {
                     request.add(beans.getKey(), beans.getParams());
                 }
             }
        List<Binary> binaries = new ArrayList<>();
        for (ParamsBeans beans:paramsBeansArrayList) {
            if (beans.isFile()) {
                Bitmap file2 = ImageLocalLoader.getInstance().readImage(beans.getParams(), 720, 1280); //全路径
                File files=new File(beans.getParams());

                BasicBinary binary2 = new BitmapBinary(file2, files.isFile()?files.getName():beans.getKey());// 后面参数为文件名
                binaries.add(binary2);
            }
        }
        request.add(binariesName, binaries);
        ((BaseOkHttpActivity)activity).request(what, request, listener , cancleble, isLoading);
    }


    private static void needHttpsRequest(Boolean isHttpsRequest, Boolean needHttpsCer, Request request) {
        if(isHttpsRequest){
            if (needHttpsCer) {
                SSLContext sslContext = SSLContextUtil.getSSLContext();
                // 主要是需要一个SocketFactory对象，这个对象是java通用的，具体用法还请Google、Baidu。
                if (sslContext != null)
                    request.setSSLSocketFactory(sslContext.getSocketFactory());
            }else{
                SSLContext sslContext = SSLContextUtil.getDefaultSLLContext();
                if (sslContext != null)
                    request.setSSLSocketFactory(sslContext.getSocketFactory());
                request.setHostnameVerifier(SSLContextUtil.HOSTNAME_VERIFIER);
            }
        }
    }

    public static  void  commonHttpRequst(Activity activity,Boolean isHttpsRequest,Boolean needHttpsCer,String url,ArrayList<ParamsBeans> paramsBeanses,HttpListener listener ,int what){
      Request<String>   request = NoHttp.createStringRequest(url, RequestMethod.GET);
        needHttpsRequest(isHttpsRequest, needHttpsCer, request);
        if (paramsBeanses!=null) {
            for (ParamsBeans beans:paramsBeanses )
            {
                if (!beans.isFile()) {
                    request.add(beans.getKey(), beans.getParams());
                }
            }
        }

        if(activity instanceof UPpayBaseActivity){
            ((UPpayBaseActivity)activity).request(what, request, listener , false, true);
        }else{
            ((BaseOkHttpActivity)activity).request(what, request, listener , false, true);
        }

    }


}
