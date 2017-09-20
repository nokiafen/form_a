package com.comba.android.combacommon.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by zhangjiawei on 2017/5/2.
 */

public class SystemInfo {

    /** 手机型号 */
    public static String getPhoneModel (){
        return android.os.Build.MODEL;
    }

    /** SDK版本 */
    public static String getVersionSdk (){
        return android.os.Build.VERSION.SDK;
    }

    /** 系统版本 */
    public static String getVersionRelease (){
        return android.os.Build.VERSION.RELEASE;
    }

    /** 软件版本 */
    public static String getVersionRelease (Context context){
        return android.os.Build.VERSION.RELEASE;
    }

    /** 软件版本 */
    private String getAppVersionName(Context context,String Package) {
        String versionName = "";
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(Package, 0);
            versionName = packageInfo.versionName;
            if (TextUtils.isEmpty(versionName)) {
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionName;
    }

    public static String getTelephonyInfo(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        /**
         * 所有的设备都可以返回一个TelephonyManager.getDeviceId()
         * 如果是GSM网络，返回IMEI；如果是CDMA网络，返回MEID
         * 手机的唯一标识，像GSM手机的 IMEI 和 CDMA手机的 MEID. 但在中国山寨手机导致这个号码不是唯一标识了
         * 取出的值是international mobile Equipment identity手机唯一标识码，即imei
         */
        String deviceid = tm.getDeviceId();
        /**
         * 对于移动的用户，手机号码(MDN)保存在运营商的服务器中，而不是保存在SIM卡里。SIM卡只保留了IMSI和一些验证信息
         * 取出的 值是手机号，即MSISDN ： mobile subscriber ISDN用户号码，这个是我们说的139，136那个号码；
         * 是通过SIM卡相关文件记录得到的数据
         * 归结到是否可以从SIM卡的EFmsisdn文件取出手机号码了，不幸的是一般运营商不会把用户号码写在这个文件的，为什么呢？
         * 因为这个手机号码是在用户买到卡并开通时才将IMSI和MSISDN对应上的，卡内生产出来时只有IMSI，你不知道用户喜欢那个手机号码，因此一般不先对应IMSI和MSISDN，即时有对应也不写这个文件的。
         */
        String tel = tm.getLine1Number();

        /**
         * 所有的GSM设备(测试设备都装载有SIM卡) 可以返回一个TelephonyManager.getSimSerialNumber()

         // 所有的CDMA 设备对于 getSimSerialNumber() 却返回一个空值！

         // 360手机卫士可能会影响到imei和imsi的获取，禁止了“获取该应用获取设备信息”，改为“允许”即可正常获取IMEI、IMSI

         // 返回SIM卡的序列号(ICCID) ICCID:ICC

         // identity集成电路卡标识，这个是唯一标识一张卡片物理号码的
         */
        String iccid = tm.getSimSerialNumber();

        /**
         * sim卡的序列号(IMSI)，international

         // mobiles subscriber

         // identity国际移动用户号码标识，
         获取imei和imsi的第二种方法
         String imsi2 =Android.os.SystemProperties.get(

         // android.telephony.TelephonyProperties.PROPERTY_IMSI);

         // String imei2

         // =android.os.SystemProperties.get(android.telephony.TelephonyProperties.PROPERTY_IMEI);
         */

        String imsi = tm.getSubscriberId();

        /**
         * 此处获取设备ANDROID_ID

         // 所有添加有谷歌账户的设备可以返回一个 ANDROID_ID

         // 所有的CDMA设备对于 ANDROID_ID 和

         // TelephonyManager.getDeviceId()返回相同的值（只要在设置时添加了谷歌账户）

         // 有些山寨手机这个号码是一个…

         // 是一个64位的数字，在设备第一次启动的时候随机生成并在设备的整个生命周期中不变。（如果重新进行出厂设置可能会改变）
         */
        String android_id = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);

        /**
         * 获取Android手机型号和OS的版本号
         */
        String mobileName = Build.DEVICE;
        String osVersion = Build.VERSION.RELEASE;
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        String macAddress = info.getMacAddress();// 更换为MacAddressWifi地址
        String softwareVersion = tm.getDeviceSoftwareVersion();// String

        /**
         * 获取当前手机支持的移动网络类型
         */
        String phoneType = null;
        if (tm.getPhoneType() == TelephonyManager.PHONE_TYPE_NONE) {
            phoneType = "NONE: ";
        } else if (tm.getPhoneType() == TelephonyManager.PHONE_TYPE_GSM) {
            phoneType = "GSM: IMEI";
        } else if (tm.getPhoneType() == TelephonyManager.PHONE_TYPE_CDMA) {
            phoneType = "CDMA: MEID/ESN";
        } else {
            phoneType = "UNKNOWN: ID";
        }
        String sytemInfo = "设备名称 : " + mobileName +"\n系统版本：" + osVersion +"\n软件版本： "
                + softwareVersion +"\n设备ID（imei） " + deviceid +"\n"
                +"手机号  :  " + tel +"\n" +"SIM卡集成电路卡标识 : " + iccid +"\n"
                +"SIM国际移动号码标示 ： " + imsi +"\n" +"ANDROID_ID : " + android_id
                + "\n手机网络类型： " + phoneType + "\nMAC地址： " + macAddress;
        return sytemInfo;
    }




    /**===================================================================================================================================================================================================*/
    public List<AppInfo> queryAppInfo(Context context) {
        List<AppInfo> mlistAppInfo = new ArrayList<>();
        PackageManager pm = context.getPackageManager(); // 获得PackageManager对象
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        // 通过查询，获得所有ResolveInfo对象.
        List<ResolveInfo> resolveInfos = pm
                .queryIntentActivities(mainIntent, PackageManager.MATCH_DEFAULT_ONLY);
        // 调用系统排序 ， 根据name排序
        // 该排序很重要，否则只能显示系统应用，而不能列出第三方应用程序
        Collections.sort(resolveInfos, new ResolveInfo.DisplayNameComparator(pm));
        for (ResolveInfo reInfo : resolveInfos) {
            String activityName = reInfo.activityInfo.name; // 获得该应用程序的启动Activity的name
            String pkgName = reInfo.activityInfo.packageName; // 获得应用程序的包名
            String appLabel = (String) reInfo.loadLabel(pm); // 获得应用程序的Label
            Drawable icon = reInfo.loadIcon(pm); // 获得应用程序图标
            // 为应用程序的启动Activity 准备Intent
            Intent launchIntent = new Intent();
            launchIntent.setComponent(new ComponentName(pkgName,
                    activityName));
            // 创建一个AppInfo对象，并赋值
            AppInfo appInfo = new AppInfo();
            appInfo.setAppLabel(appLabel);
            appInfo.setPkgName(pkgName);
            appInfo.setAppIcon(icon);
            appInfo.setIntent(launchIntent);
            mlistAppInfo.add(appInfo); // 添加至列表中
            System.out.println(appLabel + " activityName---" + activityName + " pkgName---" + pkgName);
        }
        return mlistAppInfo;
    }

    private class AppInfo{
        private String appLabel;
        private String pkgName;
        private String activityName;
        private Drawable appIcon;
        private Intent intent;

        public String getAppLabel() {
            return appLabel;
        }

        public void setAppLabel(String appLabel) {
            this.appLabel = appLabel;
        }

        public String getPkgName() {
            return pkgName;
        }

        public void setPkgName(String pkgName) {
            this.pkgName = pkgName;
        }

        public String getActivityName() {
            return activityName;
        }

        public void setActivityName(String activityName) {
            this.activityName = activityName;
        }

        public Drawable getAppIcon() {
            return appIcon;
        }

        public void setAppIcon(Drawable appIcon) {
            this.appIcon = appIcon;
        }

        public Intent getIntent() {
            return intent;
        }

        public void setIntent(Intent intent) {
            this.intent = intent;
        }
    }

}
