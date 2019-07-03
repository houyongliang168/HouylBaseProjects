package com.wzgiceman.rxretrofitlibrary.retrofit_rx;


import android.content.Context;

/**
 * 全局app
 * Created by WZG on 2016/12/12.
 */

public class RxRetrofitApp  {
    private static Context application;
    private static boolean debug;


    public static void init(Context app){
        setApplication(app);
        setDebug(true);
    }

    public static void init(Context app,boolean debug){
        setApplication(app);
        setDebug(debug);
    }

    public static Context getApplication() {
        return application;
    }

    private static void setApplication(Context application) {
        RxRetrofitApp.application = application;
    }

    public static boolean isDebug() {
        return debug;
    }

    public static void setDebug(boolean debug) {
        RxRetrofitApp.debug = debug;
    }
}
