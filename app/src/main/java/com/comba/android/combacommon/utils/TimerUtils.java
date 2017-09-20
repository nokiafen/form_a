package com.comba.android.combacommon.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by zhangjiawei on 2017/5/9.
 * 定时器操作
 */

public class TimerUtils {

    private static final TimerUtils instance = new TimerUtils();
    private Timer mTimer;
    private Handler mHandler;

    private TimerUtils() {
        mTimer = new Timer();
        mHandler = new Handler();
    }

    public static TimerUtils getInstance() {
        return instance;
    }

    /** delay为long,period为long：从现在起过delay毫秒以后，每隔period毫秒执行一次 */
    private void m_TimerSchedule(TimerTask task, long delay, long period) {
        mTimer.schedule(task, delay, period);
    }
    /** firstTime为Date类型,period为long，表示从firstTime时刻开始，每隔period毫秒执行一次 */
    private void m_TimerSchedule(TimerTask task, Date firstTime, long period) {
        mTimer.schedule(task, firstTime, period);
    }

    /** delay 为long类型：从现在起过delay毫秒执行一次 */
    private void m_TimerSchedule(TimerTask task, long delay) {
        mTimer.schedule(task, delay);
    }

    /** time为Date类型：在指定时间执行一次 */
    private void m_TimerSchedule(TimerTask task, Date time) {
        mTimer.schedule(task, time);
    }


    /**
     * 每隔millisInFuture毫秒执行onTick中的方法一次
     * 直到执行完millisInFuture/countDownInterval次为止，最后会执行onFinish()中的方法
     * @param millisInFuture
     * @param countDownInterval
     * @param callback
     */
    private void m_CountDownTimer(long millisInFuture, long countDownInterval, final CountDownTimerCallBack callback) {
        CountDownTimer cdt = new CountDownTimer(millisInFuture, countDownInterval) {

            @Override
            public void onTick(long l) {
                callback.onTick(l);
            }

            @Override
            public void onFinish() {
                callback.onFinish();
            }
        };
        cdt.start();
    }

    private abstract class CountDownTimerCallBack{
        public abstract void onTick(long var1);

        public abstract void onFinish();

    }

    private void m_AlarmManager(Context context, Class<?> cls,long triggerAtMillis){
        Intent intent2 = new Intent(context,cls);
        PendingIntent pd =PendingIntent.getBroadcast(context, 0, intent2,PendingIntent.FLAG_ONE_SHOT);
        AlarmManager am = (AlarmManager)context.getSystemService(context.ALARM_SERVICE);
        long triggerTime = SystemClock.elapsedRealtime() + triggerAtMillis;
        am.set(AlarmManager.ELAPSED_REALTIME,triggerTime, pd);
    }

    private void m_HandlerPostDelayed(Runnable r, long delayMillis){
        mHandler.postDelayed(r,delayMillis);
    }
}
