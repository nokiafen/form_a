package com.comba.android.combacommon;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.comba.android.combacommon.utils.Cache;
import com.comba.android.combacommon.utils.ScreenUtil;
import com.comba.android.combacommon.utils.StringUtils;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    public  static  final  String   test_log="Platform..InstrumentedTest..";
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.comba.android.combacommon", appContext.getPackageName());
    }



    @Test
    public void getDemension() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        Cache.setContext(appContext);
        ScreenUtil.getStatusBarHeight(appContext);
        Log.d(test_log, ScreenUtil.getDisplayHeight()+""+ScreenUtil.getNavBarHeight(appContext)+""+ ScreenUtil.getStatusBarHeight(appContext));
        assertEquals("com.comba.android.combacommon", appContext.getPackageName());
    }

}
