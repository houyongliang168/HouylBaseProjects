package com.base.hyl.houbasemodule.framework.log;

import android.util.Log;

import com.base.hyl.houbasemodule.BuildConfig;


/**
 * Created by zhaoyu on 2017/4/18.类说明：自定义log
 */
public class MyLog {
    public static void wtf(String tag, String msg) {
        if (BuildConfig.IS_DEBUG) {//不是调试模式时则不显示所有log
            Log.wtf(tag, msg);
        }
    }

//    public static void e(Throwable e) {
//        TCAgent.onError(App.getContext(), e);
//    }
}
