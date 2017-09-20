package com.comba.android.combacommon.hardware;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.TextView;

import com.comba.android.combacommon.toast.ToastUtil;
import com.comba.android.combacommon.utils.Cache;

/**
 * Created by liangchunfeng on 2017/5/18.
 * 传感器工具类
 */

public class SensorUtils {

    private static SensorUtils sensorUtils= null;
    private static SensorManager sensorManager;
    private OnSensorChangedListener orientationListener;
    private OnSensorChangedListener accelemetorListener;
    private OnSensorChangedListener magneticListener;
    private OnSensorChangedListener pressureListener;
    private OnSensorChangedListener temperatureListener;
    private OnSensorChangedListener proximityListener;
    private OnSensorChangedListener lightListener;
    private OnSensorChangedListener gyroscopeListener;
    private OnSensorChangedListener gravityListener;

    private SensorListener sensorListener;
    private static Context mContext;

    private SensorUtils(){
        mContext = Cache.getContext();
        sensorManager = (SensorManager)mContext.getSystemService(Context.SENSOR_SERVICE);
        sensorListener = new SensorListener();
    }

    public static SensorUtils getInstance(){
        if(sensorUtils == null){
            sensorUtils = new SensorUtils();
        }
        return sensorUtils;
    }


    /**
     * 注册自己的传感器监听类
     * @param listener  传感器监听类
     * @param sensorType    传感器类型
     */
    public static void registerSelfSensorListener(SensorEventListener listener,int sensorType){
        sensorManager = (SensorManager)mContext.getSystemService(Context.SENSOR_SERVICE);
        Sensor sensor = sensorManager.getDefaultSensor(sensorType);
        sensorManager.registerListener(listener,sensor,SensorManager.SENSOR_DELAY_NORMAL);
    }

    /**
     * 注销自己的传感器监听
     * @param listener  传感器监听类
     */
    public static void unregisterSelfSensorListener(SensorEventListener listener){
        if(listener == null){
            return ;
        }
        sensorManager.unregisterListener(listener);
    }


    /**
     *  获取方向传感器信息
     * @return  x,y,z
     */
    public void getOrientationSensorInfo(OnSensorChangedListener listener){
        this.orientationListener = listener;
        Sensor orientationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        sensorManager.registerListener(sensorListener,orientationSensor,SensorManager.SENSOR_DELAY_NORMAL);
    }


    /**
     * 判断是否支持加速度传感器
     * @return  true:支持     false:不支持
     */
    public boolean isSupportAccelerometerSensor(){
        return mContext.getPackageManager().hasSystemFeature(PackageManager.FEATURE_SENSOR_ACCELEROMETER);
    }

    /**
     * 获取加速度传感器信息
     * @return
     */
    public void getAccelerometerSensorInfo(OnSensorChangedListener listener){
        this.accelemetorListener = listener;
        Sensor accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(sensorListener,accelerometerSensor,SensorManager.SENSOR_DELAY_NORMAL);
    }


    /**
     * 获取磁场传感器信息
     * @param listener
     * @return
     */
    public void getMagneticSensorInfo(OnSensorChangedListener listener){
        this.magneticListener = listener;
        Sensor magneticSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        if(magneticSensor == null){
            ToastUtil.showToastShort(mContext,"该设备不支持磁场传感器");
            return ;
        }
        sensorManager.registerListener(sensorListener,magneticSensor,SensorManager.SENSOR_DELAY_NORMAL);
    }


    /**
     * 获取压力传感器信息
     * @return
     */
    public void getPressureSensorInfo(OnSensorChangedListener listener){
        this.pressureListener = listener;
        Sensor pressureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        if(pressureSensor == null){
            ToastUtil.showToastShort(mContext,"该设备不支持压力传感器");
            return ;
        }
        sensorManager.registerListener(sensorListener,pressureSensor,SensorManager.SENSOR_DELAY_NORMAL);
    }


    /**
     * 判断设备是否支持温度传感器
     * @return  true:支持     false:不支持
     */
    public boolean isSupportTemperatureSensor(){
        return mContext.getPackageManager().hasSystemFeature(PackageManager.FEATURE_SENSOR_AMBIENT_TEMPERATURE);
    }


    /**
     * 获取温度传感器信息
     * @return
     */
    public void getTemperatureSensorInfo(OnSensorChangedListener listener){
        this.temperatureListener = listener;
        Sensor temperatureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        if(temperatureSensor == null){
            ToastUtil.showToastShort(mContext,"该设备不支持温度传感器");
            return ;
        }
        sensorManager.registerListener(sensorListener,temperatureSensor,SensorManager.SENSOR_DELAY_NORMAL);
    }


    /**
     * 判断设备是否支持距离传感器
     * @return  true:支持     false:不支持
     */
    public boolean isSupportProximitySensor(){
        return mContext.getPackageManager().hasSystemFeature(PackageManager.FEATURE_SENSOR_PROXIMITY);
    }

    /**
     * 获取距离传感器信息
     * @return
     */
    public void getProximitySensorInfo(OnSensorChangedListener listener){
        this.proximityListener = listener;
        Sensor proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        if(proximitySensor == null){
            ToastUtil.showToastShort(mContext,"该设备不支持距离传感器");
            return ;
        }
        sensorManager.registerListener(sensorListener,proximitySensor,SensorManager.SENSOR_DELAY_NORMAL);
    }


    /**
     * 判断设备是否支持光线传感器
     * @return  true:支持   false:不支持
     */
    public boolean isSupportLightSensor(){
        return mContext.getPackageManager().hasSystemFeature(PackageManager.FEATURE_SENSOR_LIGHT);
    }


    /**
     * 获取光线传感器信息
     * @param listener
     */
    public void getLightSensorInfo(OnSensorChangedListener listener){
        this.lightListener = listener;
        Sensor proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        if(proximitySensor == null){
            ToastUtil.showToastShort(mContext,"该设备不支持光线传感器");
            return ;
        }
        sensorManager.registerListener(sensorListener,proximitySensor,SensorManager.SENSOR_DELAY_NORMAL);
    }


    /**
     * 判断设备是否支持陀螺仪
     * @return  true:支持  false:不支持
     */
    public boolean isSupportGyroscopeSensor(){
        return mContext.getPackageManager().hasSystemFeature(PackageManager.FEATURE_SENSOR_GYROSCOPE);
    }

    /**
     * 获取陀螺仪参数信息
     * @return
     */
    public void getGyroscopeSensorInfo(OnSensorChangedListener listener){
        this.gyroscopeListener = listener;
        Sensor gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        if(gyroscopeSensor == null){
            ToastUtil.showToastShort(mContext,"该设备不支持陀螺仪");
            return ;
        }
        sensorManager.registerListener(sensorListener,gyroscopeSensor,SensorManager.SENSOR_DELAY_NORMAL);
    }



    /**
     * 获取重力传感器参数信息
     * @return
     */
    public void getGravitySensorInfo(OnSensorChangedListener listener){
        this.gravityListener = listener;
        Sensor gravitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        if(gravitySensor == null){
            ToastUtil.showToastShort(mContext,"该设备不支持重力传感器");
            return;
        }
        sensorManager.registerListener(sensorListener,gravitySensor,SensorManager.SENSOR_DELAY_NORMAL);
    }

    /**
     * 注销传感器监听
      */
    public void unRegisterSensor(){
        sensorManager.unregisterListener(sensorListener);
    }

    private final class SensorListener implements SensorEventListener{

        @Override
        public void onSensorChanged(SensorEvent event) {
            int type = event.sensor.getType();
            float[] values = event.values;
            if(type == Sensor.TYPE_ORIENTATION){
                orientationListener.onChanged(values);
            }
            if(type == Sensor.TYPE_ACCELEROMETER){
                accelemetorListener.onChanged(values);
            }
            if(type == Sensor.TYPE_MAGNETIC_FIELD){
                magneticListener.onChanged(values);
            }
            if(type == Sensor.TYPE_PRESSURE){
                pressureListener.onChanged(values);
            }
            if(type ==  Sensor.TYPE_AMBIENT_TEMPERATURE){
                temperatureListener.onChanged(values);
            }
            if(type == Sensor.TYPE_PROXIMITY){
                proximityListener.onChanged(values);
            }
            if(type == Sensor.TYPE_LIGHT){
                lightListener.onChanged(values);
            }
            if(type == Sensor.TYPE_GYROSCOPE){
                gyroscopeListener.onChanged(values);
            }
            if(type == Sensor.TYPE_GRAVITY){
                gravityListener.onChanged(values);
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    }


    /**
     * 传感器状态值发生变化回调接口
     */
    public interface OnSensorChangedListener{

        void onChanged(float[] values);
    }

}
