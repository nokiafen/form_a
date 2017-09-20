package com.comba.android.combacommon.utils;

import android.content.Context;

/**
 *  全局缓存
 */
public class Cache {

    private static Context context;

    private static String account;

    public static void clear() {
        account = null;
    }

    public static String getAccount() {
        return account;
    }

    public static void setAccount(String account) {
        Cache.account = account;
    }

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        Cache.context = context.getApplicationContext();
    }
}
