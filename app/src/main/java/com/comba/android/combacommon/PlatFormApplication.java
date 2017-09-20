package com.comba.android.combacommon;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.hss01248.dialog.MyActyManager;
import com.hss01248.dialog.StyledDialog;
import com.yanzhenjie.nohttp.Logger;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.OkHttpNetworkExecutor;
import com.yanzhenjie.nohttp.cache.DBCacheStore;
import com.yanzhenjie.nohttp.cookie.DBCookieStore;
import com.yanzhenjie.nohttp.tools.HeaderUtil;

import java.net.HttpCookie;
import java.net.URI;

/**
 * Created by chenhailin on 2017/5/16.
 */

public class PlatFormApplication extends Application {
    private static Application _instance;

    @Override
    public void onCreate() {
        super.onCreate();
        _instance = this;

        Logger.setDebug(BuildConfig.DEBUG);// 开启NoHttp的调试模式, 配置后可看到请求过程、日志和错误信息。
        Logger.setTag("PlatFormApplication");// 设置NoHttp打印Log的tag。

        // 一般情况下你只需要这样初始化：
//        NoHttp.initialize(this);

        // 如果你需要自定义配置：
        NoHttp.initialize(this, new NoHttp.Config()
                // 设置全局连接超时时间，单位毫秒，默认10s。
                .setConnectTimeout(30 * 1000)
                // 设置全局服务器响应超时时间，单位毫秒，默认10s。
                .setReadTimeout(30 * 1000)
                // 配置缓存，默认保存数据库DBCacheStore，保存到SD卡使用DiskCacheStore。
                .setCacheStore(
                        new DBCacheStore(this).setEnable(true) // 如果不使用缓存，设置setEnable(false)禁用。
                )
                // 配置Cookie，默认保存数据库DBCookieStore，开发者可以自己实现。
                // 如果不维护cookie，设置false禁用。
                .setCookieStore(new DBCookieStore(this).setCookieStoreListener(mListener))
                // 配置网络层，默认使用URLConnection，如果想用OkHttp：OkHttpNetworkExecutor。
                .setNetworkExecutor(new OkHttpNetworkExecutor())
        );
        // 如果你需要用OkHttp，请依赖下面的项目，version表示版本号：
        // compile 'com.yanzhenjie.nohttp:okhttp:1.1.1'
        // NoHttp详细使用文档：http://doc.nohttp.net

        StyledDialog.init(this);//ios 风格dialog
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                MyActyManager.getInstance().setCurrentActivity(activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }

    /**
     * Cookie管理监听。
     */
    private DBCookieStore.CookieStoreListener mListener = new DBCookieStore.CookieStoreListener() {
        @Override
        public void onSaveCookie(URI uri, HttpCookie cookie) { // Cookie被保存时被调用。
            // 1. 判断这个被保存的Cookie是我们服务器下发的Session。
            // 2. 这里的JSessionId是Session的name，
            //    比如java的是JSessionId，PHP的是PSessionId，
            //    当然这里只是举例，实际java中和php不一定是这个，具体要咨询你们服务器开发人员。
            if("JSessionId".equals(cookie.getName())) {
                // 设置有效期为最大。
                cookie.setMaxAge(HeaderUtil.getMaxExpiryMillis());
            }
        }

        @Override
        public void onRemoveCookie(URI uri, HttpCookie cookie) {// Cookie被移除时被调用。
        }
    };

    public static Application getInstance() {
        return _instance;
    }
}
