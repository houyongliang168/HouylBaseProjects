package com.base.hyl.houylbaseprojects;

import android.os.Environment;

/**
 * Created by houyl on 2018/6/20.
 *
 * 常量池
 */

public class Constant {
    /** 内置SD卡路径**/
    public static String PATH_SD_CARD = Environment.getExternalStorageDirectory().toString() + "/";
    public static final String HTTP_SUCCESS = "0";//数据获取成功
    public static final String HTTP_FAIL = "-100";//数据获取失败
    public static final String DB_QUTUO_NAME = "iscals-qutuo-db";//区拓的数据库
    public static final String APP_ID = "";//微信APP_ID
    public static final String WEB_URL = "WEB_URL";//webview 跳转处理
    /*测试一下*/
}
