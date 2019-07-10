package com.base.hyl.houylbaseprojects.xiazai.bean;

public class Config {
    private int connectTimeout = 10000;
    private int readTimeout = 10000;
    private int downloadThread = 3;
    private int eachDownloadThread = 2;


    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public int getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }

    public int getDownloadThread() {
        return downloadThread;
    }

    public void setDownloadThread(int downloadThread) {
        this.downloadThread = downloadThread;
    }

    public int getEachDownloadThread() {
        return eachDownloadThread;
    }

    public void setEachDownloadThread(int eachDownloadThread) {
        this.eachDownloadThread = eachDownloadThread;
    }
}