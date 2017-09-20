package com.comba.android.combacommon.survival;

import android.app.Activity;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

/**
 * Created by comba on 2017/5/8.
 */

public class SurvivalUtil {

    private void SetServiceSuivival(Service context, Class<Activity> activtiyClass, String strTicker, int icon) {
        //构建一个Intent
        Intent resultIntent = new Intent(context, activtiyClass);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(context, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setTicker(strTicker)
                .setAutoCancel(true)
                .setSmallIcon(icon)
                .setContentIntent(resultPendingIntent);
        Notification notification = builder.build();
        context.startForeground(1, builder.build());
    }

}
