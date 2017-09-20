package com.comba.android.combacommon.hardware;

import android.content.Context;
import android.net.wifi.WifiManager;

/**
 * Created by liangchunfeng on 2017/5/18.
 */

public class WifiUtils {


    /**
     * 检测 WiFi 开关是否打开
     * @param context
     * @return
     */
    public static boolean isWifiEnabled(Context context) {
        WifiManager wifimanager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        return wifimanager.isWifiEnabled();
    }

    /**
     * 强制打开 Wifi 开关
     * @param context
     * @return
     */
    public static boolean setWifiEnabled(Context context){
        WifiManager wifimanager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        return wifimanager.setWifiEnabled(true);
    }

    /**
     * 强制关闭 Wifi 开关
     * @param context
     * @return
     */
    public static boolean setWifiDisabled(Context context){
        WifiManager wifimanager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        return wifimanager.setWifiEnabled(false);
    }

}
