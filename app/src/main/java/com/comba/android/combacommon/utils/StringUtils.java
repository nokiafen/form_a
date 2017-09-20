package com.comba.android.combacommon.utils;

import android.support.annotation.Nullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by liangchunfeng on 2017/4/24.
 * 字符串常用工具类
 */

public class StringUtils {

    /**
     * 判断字符串是否为空，包括 null 以及 ""
     * @return true:字符串为空
     *         false:字符串不为空
     */
    public static boolean isEmpty(@Nullable String str){
        if(str == null || str.equals("")){
            return true;
        }
        return false;
    }

    /**
     * 判断字符串是否非空
     * @param str   待校验的字符串
     * @return  true:非空
     *          false:空
     */
    public static boolean isNotEmpty(String str){
        if(str == null || str.equals("")){
            return false;
        }
        return true;
    }

    /**
     * 检测字符串是否为 null 或者是否是"" 或者是否全部是由空格组成
     * @param str
     * @return
     */
    public static boolean isBlank(String str) {
        return (str == null || str.trim().length() == 0);
    }


    /**
     * 替换字符串
     * @param source
     * @param regex
     * @param replacement
     * @return
     */
    public static String replace(String source,String regex,String replacement){
        return source == null ? "":source.replaceAll(regex,replacement);
    }


    /**
     * 字符串转换为HTML显示字符
     * @param source
     * @return
     */
    public static String string2Html(String source){
        if(isEmpty(source)){
            return "";
        }
        source = replace(source,"&","&amp;");
        source = replace(source,"<","&lt;");
        source = replace(source,">","&gt;");
        source = replace(source,"\"","&quot;");
        return source;
    }


    /**
     * 字符串转换为 int 型数据
     * @param str   需要转换的字符串
     * @param defaultValue  默认值
     * @return  转换后的结果
     */
    public static int stringToInt(String str, int defaultValue){
        if (isEmpty(str)){
            return defaultValue;
        }
        try{
            return Integer.parseInt(str);
        }catch (NumberFormatException e){
            return defaultValue;
        }
    }


    /**
     * 过滤字符串中除数字和字母意外的字符
     * @param number    源字符串
     * @return  过滤特殊字符后的字符串
     */
    public static String filterSpecialChar(String number)
    {
        if (isEmpty(number))
        {
            return number;
        }
        Pattern pattern = Pattern.compile("[^(0-9A-Za-z)]");
        Matcher matcher = pattern.matcher(number);
        return matcher.replaceAll("");
    }


    /**
     * 判断输入的字符是否是汉字
     */
    public static boolean isChinese(char a) {
        int v = (int)a;
        return (v >=19968 && v <= 171941);
    }

    /**
     * 判断字符串中是否包含中文
     * @param s
     * @return  true:包含中文
     *          false:不包含中文
     */
    public static boolean containsChinese(String s){
        if (null == s || "".equals(s.trim()))
            return false;
        for (int i = 0; i < s.length(); i++) {
            if (isChinese(s.charAt(i))) return true;
        }
        return false;
    }

    /**
     * 测试字符串中是否包含另外一个字符串，不区分大小写
     * @param value 源字符串
     * @param sub   需要查找的字符串
     * @return  true:包含
     *          false:不包含
     */
    public static boolean containsWithIgnoreCase(String value, String sub) {
        if(value==null || sub==null) {
            return false;
        }
        if(value.length() < sub.length()) {
            return false;
        }
        return value.toUpperCase().contains(sub.toUpperCase());
    }


    /**
     * 判断是否是 int 类型数据
     * @param srcStr
     * @return
     */
    public static boolean isInt(Object srcStr) {
        if (srcStr == null) {
            return false;
        }
        String s = srcStr.toString().replaceAll("(\\s)", "");
        Pattern p = Pattern.compile("([-]?[\\d]+)");
        Matcher m = p.matcher(s);
        return m.matches();
    }


    /**
     * 转换字符串为 int 类型数据
     * @param srcStr    需要转换的数据
     * @param defaultValue  默认值
     * @return
     */
    public static Integer toInteger(Object srcStr, Integer defaultValue) {
        try {
            if (srcStr != null && isInt(srcStr)) {
                String s = srcStr.toString().replaceAll("(\\s)", "");
                return s.length() > 0 ? Integer.valueOf(s) : defaultValue;
            }
        } catch (Exception e) {
            //logger.info(e.getMessage(), e);
            ;
        }
        return defaultValue;
    }


    /**
     * 去掉字符串头尾的空格，中间的空格保留
     * @param source
     * @return
     */
    public static String trimHeadAndTail(String source){
        int i = source.length();
        int j = 0;
        int k = 0;
        char[] arrayOfChar = source.toCharArray();// 将字符串转换成字符数组
        while ((j < i) && (arrayOfChar[(k + j)] <= ' '))
            ++j;// 确定字符串前面的空格数
        while ((j < i) && (arrayOfChar[(k + i - 1)] <= ' '))
            --i;// 确定字符串后面的空格数
        return (((j > 0) || (i < source.length())) ? source.substring(j, i) : source);// 返回去除空格后的字符串
    }

}
