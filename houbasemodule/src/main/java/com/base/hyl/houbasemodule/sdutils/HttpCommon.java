package com.base.hyl.houbasemodule.sdutils;

/**
 * Created by houyongliang on 2018/1/24.
 */


public abstract class HttpCommon implements Runnable {
    public static final int TIMEOUT = 30000;
    public static final int TIMEOUT_SHORT = 10000;

    /**
     * 更新素材库时间
     */
    public static final int TIMEOUT_WIFI_4G = 8000;
    public static final int TIMEOUT_3G = 10000;
    public static final int TIMEOUT_2G = 30000;


    protected static final int BUFFERED_READER_SIZE = 8192;
    protected static final int SOCKET_BUFFER_SIZE = 2048;

    protected String mEncoding = "UTF-8";
}
