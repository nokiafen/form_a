package com.comba.android.combacommon.hardware;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * CPU工具类
 */

public class CpuUtils {

    public static int getAppCpuUsedPercent(String packageName) {
        String[] cpuInfos = null;
        int AppCpuUsedPercent = -1;
        try {
            int pid = android.os.Process.myPid();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    Runtime.getRuntime().exec("top -n 1").getInputStream()), 500);
            String load = reader.readLine();
            while (load != null) {
                if (load.contains(packageName) && load.contains(String.valueOf(pid))) {
                    break;
                }
                load = reader.readLine();
            }
            reader.close();
            cpuInfos = load.split("%");
            AppCpuUsedPercent = Integer.parseInt(cpuInfos[0].substring(cpuInfos[0].length() - 3).trim());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return AppCpuUsedPercent;
    }

}
