package com.comba.android.combacommon.utils;

import android.util.Log;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by liangchunfeng on 2017/4/24.
 */

public class StringUtilsTest {

    @Test
    public void isEmptyTest(){
        String s = "123";
        boolean test = StringUtils.isNotEmpty(s);
        assertEquals(test,true);
    }

    @Test
    public void string2HtmlTest(){
        String s = "<html></html>";
        String res = StringUtils.string2Html(s);
        assertEquals(res,"&lt;html&gt;&lt;/html&gt;");
    }

    @Test
    public void stringToIntTest(){
        String s = "fdsfds";
        int res = StringUtils.stringToInt(s,1);
        assertEquals(res,1);
    }

    @Test
    public void filterSpecialCharTest(){
        String s = "dsfds%fdh__++23*";
        String res = StringUtils.filterSpecialChar(s);
        assertEquals(res,"dsfdsfdh23");
    }

    @Test
    public void containsChineseTest(){
        String s = "dsfds";
        boolean res = StringUtils.containsChinese(s);
        assertEquals(res,false);
    }

    @Test
    public void containsWithIgnoreCaseTest(){
        String source = "你好，中国China";
        String data = "国china";
        boolean res = StringUtils.containsWithIgnoreCase(source,data);
        assertEquals(res,true);
    }

    @Test
    public void isIntTest(){
        Object i = "663.21";
        boolean res = StringUtils.isInt(i);
        assertEquals(res,true);
    }

    @Test
    public void toIntegerTest(){
        Object i = "123.0";
        Integer res = StringUtils.toInteger(i,1);
        assertEquals(res,new Integer(123));
    }

    @Test
    public void trimHeadAndTailTest(){
        String s = "";
        String res = StringUtils.trimHeadAndTail(s);
        assertEquals("",res);
    }

}
