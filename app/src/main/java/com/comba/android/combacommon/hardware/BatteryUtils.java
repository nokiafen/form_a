package com.comba.android.combacommon.hardware;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

/**
 * Created by liangchunfeng on 2017/5/19.
 * Android设备电池工具类
 */

public class BatteryUtils {

    private static BatteryUtils baetteryUtils= null;
    private BatteryBroadcastReceiver batteryBroadcastReceiver;
    private static OnBatteryChangedListener mListener;

    private BatteryUtils(){
        batteryBroadcastReceiver = new BatteryBroadcastReceiver();
    }

    public static BatteryUtils getInstance(){
        if(baetteryUtils == null){
            baetteryUtils = new BatteryUtils();
        }
        return baetteryUtils;
    }

    /**
     * 获取电池的状态信息
     * @param context   上下文对象
     * @param listener  电池状态监听器
     */
    public void getBatteryStatusInfo(Context context,OnBatteryChangedListener listener){
        mListener = listener;
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_BATTERY_CHANGED);
        context.registerReceiver(batteryBroadcastReceiver, filter);
    }

    /**
     * 取消电池电量监听器
     * @param context
     */
    public void unRegisterBatteryListener(Context context){
        context.unregisterReceiver(batteryBroadcastReceiver);
    }

    private class BatteryBroadcastReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals(Intent.ACTION_BATTERY_CHANGED)){

                int level = intent.getIntExtra("level", 0);

                int temperature = intent.getIntExtra("temperature", 0);

                int status = intent.getIntExtra("status", 0);
                int scale = intent.getIntExtra("scale", 0);
                int voltage = intent.getIntExtra("voltage", 0);
                if(mListener!=null){
                    mListener.onChanged(level);
                    mListener.onTemperatureChanged(temperature);
                    mListener.onStatusChanged(status);
                    mListener.onScaleChanged(scale);
                    mListener.onVoltageChanged(voltage);
                }
            }
        }
    }

    public interface  OnBatteryChangedListener{

        /**
         * 获得手机剩余电量
         * @param level
         */
        void onChanged(int level);

        /**
         * 获取手机电池温度
         * @param temperature
         */
        void onTemperatureChanged(int temperature);

        /**
         * 得到电池的状态
         * @param status
         */
        void onStatusChanged(int status);

        /**
         * 获取电池的最大值
         * @param scale
         */
        void onScaleChanged(int scale);

        /**
         * 获取电池的电压
         * @param voltage
         */
        void onVoltageChanged(int voltage);
    }

}
