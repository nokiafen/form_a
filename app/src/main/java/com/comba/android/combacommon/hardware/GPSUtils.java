package com.comba.android.combacommon.hardware;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

/**
 * Created by liangchunfeng on 2017/5/18.
 */

public class GPSUtils {

    /**
     * 判断Gps是否打开
     *
     * @param context   上下文对象
     * @return  true:是  false:否
     */
    public static boolean isGpsEnabled(Context context) {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP){
            String str = Settings.Secure.getString(context.getContentResolver(),
                    Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            if (str != null) {
                return str.contains("gps");
            } else {
                return false;
            }
        }else{
            LocationManager locationManager = ((LocationManager) context
                    .getSystemService(Context.LOCATION_SERVICE));
            return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        }

    }

    /**
     * 强制帮用户打开GPS(该接口目前测试发现问题，不能使用)
     * @param context
     */
    public static final void openGPS(Context context) {
        Intent GPSIntent = new Intent();
        GPSIntent.setClassName("com.android.settings",
                "com.android.settings.widget.SettingsAppWidgetProvider");
        GPSIntent.addCategory("android.intent.category.ALTERNATIVE");
        GPSIntent.setData(Uri.parse("custom:3"));
        try {
            PendingIntent.getBroadcast(context, 0, GPSIntent, 0).send();
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }
    }

}
