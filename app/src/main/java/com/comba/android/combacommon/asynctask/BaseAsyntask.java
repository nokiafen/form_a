package com.comba.android.combacommon.asynctask;

import android.os.AsyncTask;

import com.comba.android.combacommon.contacts.Contact;

/**
 * Created by chenhailin on 2017/6/2.
 */

public abstract class BaseAsyntask extends AsyncTask<Void, Void,AsynBean> {


    /**
     * 后台任务（耗时相关不用开线程）
     * @param params   长度可变参数
     * @return
     * AsynBean 根据业务逻辑 继承AsynBean 实现自己的逻辑
     */
    @Override
    public AsynBean doInBackground(Void... params) {
      return   backGroundTask();
    }

    @Override
    public void onPostExecute(AsynBean ret) {
       //后台任务结果 数据以contact 返回到这，已经到线程外，可以直接刷新UI
        backToUiThread(ret);
    }

    /**
     * 后台耗时任务
     * @return
     */
    public  abstract  AsynBean backGroundTask();

    /**
     * 获取结果回到主线程
     * @param bean
     */
    public  abstract  void backToUiThread(AsynBean bean);



    public class  TimeGet extends BaseAsyntask{



        @Override
        public AsynBean backGroundTask() {
            return null;
        }

        @Override
        public void backToUiThread(AsynBean bean) {

        }
    }


}
