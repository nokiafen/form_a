package com.comba.android.combacommon.hardware;

import android.app.ActivityManager;
import android.content.Context;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by liangchunfeng on 2017/5/18.
 * 内存工具类
 */

public class MemoryUtils {

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

}
