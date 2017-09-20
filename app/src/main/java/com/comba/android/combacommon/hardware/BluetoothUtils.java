package com.comba.android.combacommon.hardware;

import android.bluetooth.BluetoothAdapter;

/**
 * Created by liangchunfeng on 2017/5/18.
 * 蓝牙工具类
 */

public class BluetoothUtils {

    /**
     * 判断当前设备是否支持蓝牙
     * @return  true:支持，false:不支持
     */
    public static boolean isBluetoothSupported(){
        return BluetoothAdapter.getDefaultAdapter() != null ? true : false;
    }

    /**
     * 判断蓝牙是否打开
     * @return  true:打开，false:关闭
     */
    public static boolean isBluetoothEnabled(){
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(bluetoothAdapter != null){
            return bluetoothAdapter.isEnabled();
        }else {
            return false;
        }
    }

    /**
     * 强制开启当前设备的蓝牙
     *
     * @return true：强制打开 Bluetooth　成功　false：强制打开 Bluetooth 失败
     */
    public static boolean turnOnBluetooth(){
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter
                .getDefaultAdapter();

        if (bluetoothAdapter != null)
        {
            return bluetoothAdapter.enable();
        }

        return false;
    }

    /**
     * 强制关闭当前设备的蓝牙
     *
     * @return  true：强制关闭 Bluetooth　成功　false：强制关闭 Bluetooth 失败
     */
    public static boolean turnOffBluetooth(){
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter
                .getDefaultAdapter();

        if (bluetoothAdapter != null)
        {
            return bluetoothAdapter.disable();
        }

        return false;
    }

}
