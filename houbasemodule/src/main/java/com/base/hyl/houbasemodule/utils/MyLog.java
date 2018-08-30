package com.base.hyl.houbasemodule.utils;

import android.util.Log;




/**
 * Created by zhaoyu on 2017/4/18.类说明：自定义log
 */
public class MyLog {
    public static void wtf(String tag, String msg) {
        if (App.ISDEBUG) {//不是调试模式时则不显示所有log
            Log.wtf(tag, msg);
        }
    }


}
