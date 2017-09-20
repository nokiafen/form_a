package com.comba.android.combacommon.hardware;

import android.app.ActivityManager;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.Buffer;

/**
 * Created by liangchunfeng on 2017/5/15.
 * 硬件操作类
 */

public class HardwareUtils {


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


    /**
     * 获取Android系统当前可用内存大小(单位K)
     * @param context   上下文对象
     * @return  可用内存大小，单位 K
     */
    public static long getAvailMemoryWithK(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        //mi.availMem; 当前系统的可用内存
        return mi.availMem>>10;
    }

    /**
     * 获取Android系统当前可用内存大小(单位M)
     * @param context   上下文对象
     * @return  可用内存大小，单位 M
     */
    public static long getAvailMemoryWithM(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        //mi.availMem; 当前系统的可用内存
        return mi.availMem>>20;
    }

    /**
     * 获取 Android 系统的总内存大小，单位为 K
     * @return
     */
    public static long getTotalMemoryWithK() {
        String str1 = "/proc/meminfo";// 系统内存信息文件
        String str2;
        String[] arrayOfString;
        long initial_memory = 0;
        try {
            FileReader localFileReader = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(
                    localFileReader, 8192);
            str2 = localBufferedReader.readLine();// 读取meminfo第一行，系统总内存大小
            arrayOfString = str2.split("\\s+");
            initial_memory = Integer.valueOf(arrayOfString[1]).intValue();
            localBufferedReader.close();

        } catch (IOException e) {
        }
        return initial_memory;
    }

    /**
     * 获取 Android 系统的总内存大小，单位为M
     * @return
     */
    public static long getTotalMemoryWithM() {
        String str1 = "/proc/meminfo";// 系统内存信息文件
        String str2;
        String[] arrayOfString;
        long initial_memory = 0;
        try {
            FileReader localFileReader = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(
                    localFileReader, 8192);
            str2 = localBufferedReader.readLine();// 读取meminfo第一行，系统总内存大小
            arrayOfString = str2.split("\\s+");
            initial_memory = Integer.valueOf(arrayOfString[1]).intValue();// 获得系统总内存，单位是KB，乘以1024转换为Byte
            localBufferedReader.close();
        } catch (IOException e) {
        }
        return initial_memory>>10;
    }


    /**
     * 获得CPU的使用率
     * @return
     */
//    public static int getCpuUsageRate(){
//        StringBuilder sb = new StringBuilder();
//        int rate = 0;
//        try {
//            String result = "";
//            Process p;
//            p = Runtime.getRuntime().exec("top -n l");
//            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
//            while ((result = reader.readLine()) != null){
//                if(result.trim().length() < 1){
//                    continue;
//                }else{
//                    String[] CPUuse = result.split("%");
//                    sb.append("USER:"+CPUuse[0]+"/n");
//                    String[] CPUusage = CPUuse[0].split("User");
//                    String[] sysUsage = CPUuse[1].split("System");
//                    sb.append("CPU:" + CPUusage[1].trim() + " length:" + CPUusage[1].trim().length() + "\n");
//                    sb.append("SYS:" + sysUsage[1].trim() + " length:" + sysUsage[1].trim().length() + "\n");
//                    rate = Integer.parseInt(CPUusage[1].trim()) + Integer.parseInt(sysUsage[1].trim());
//                    break;
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return rate;
//    }

    /**
     * 获取当前应用CPU的使用率
     * @return
     */
    public static float getProcessCpuRate()
    {

        float totalCpuTime1 = getTotalCpuTime();
        float processCpuTime1 = getAppCpuTime();
        try
        {
            Thread.sleep(360);

        }
        catch (Exception e)
        {
        }

        float totalCpuTime2 = getTotalCpuTime();
        float processCpuTime2 = getAppCpuTime();

        float cpuRate = 100 * (processCpuTime2 - processCpuTime1)
                / (totalCpuTime2 - totalCpuTime1);

        return cpuRate;
    }

    public static long getTotalCpuTime()
    { // 获取系统总CPU使用时间
        String[] cpuInfos = null;
        try
        {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    new FileInputStream("/proc/stat")), 1000);
            String load = reader.readLine();
            reader.close();
            cpuInfos = load.split(" ");
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
        long totalCpu = Long.parseLong(cpuInfos[2])
                + Long.parseLong(cpuInfos[3]) + Long.parseLong(cpuInfos[4])
                + Long.parseLong(cpuInfos[6]) + Long.parseLong(cpuInfos[5])
                + Long.parseLong(cpuInfos[7]) + Long.parseLong(cpuInfos[8]);
        return totalCpu;
    }

    public static long getAppCpuTime()
    { // 获取应用占用的CPU时间
        String[] cpuInfos = null;
        try
        {
            int pid = android.os.Process.myPid();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    new FileInputStream("/proc/" + pid + "/stat")), 1000);
            String load = reader.readLine();
            reader.close();
            cpuInfos = load.split(" ");
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
        long appCpuTime = Long.parseLong(cpuInfos[13])
                + Long.parseLong(cpuInfos[14]) + Long.parseLong(cpuInfos[15])
                + Long.parseLong(cpuInfos[16]);
        return appCpuTime;
    }

//    public static String getCpuRate(){
//        CPURate rate = new CPURate();
//        return rate.getCPURate();
//    }

}
