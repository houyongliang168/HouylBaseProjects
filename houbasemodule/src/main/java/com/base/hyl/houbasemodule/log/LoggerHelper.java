package com.base.hyl.houbasemodule.log;

import android.text.TextUtils;


import com.base.hyl.houbasemodule.BuildConfig;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;



public class LoggerHelper {
    public static boolean isDebug = BuildConfig.IS_DEBUG;

    public static void init(String tag) {
        if(isDebug) {
            if(TextUtils.isEmpty(tag)){
                Logger.init();
            }else {
                Logger.init(tag);
            }

        }else{
            Logger.init().logLevel(LogLevel.NONE) ;
        }
    }

    public static void json(String jsonData) {
        if(isDebug) {
        Logger.json(jsonData);
        }
    }

    public static void debug(String tag, String message) {
        if(isDebug) {
        Logger.t(tag).d(message);
        }
    }

    public static void info(String tag, String message) {
        if(isDebug) {
        Logger.t(tag).i(message);
        }
    }

    public static void error(String tag, String message) {
        if(isDebug) {
        Logger.t(tag).e(message);
        }
    }

    public static void debug(String message) {
        if(isDebug) {
        Logger.d(message);
        }
    }

    public static void info(String message) {
        if(isDebug) {
        Logger.i(message);
        }
    }

    public static void error(String message) {
        if(isDebug) {
        Logger.e(message);
        }
    }
}
