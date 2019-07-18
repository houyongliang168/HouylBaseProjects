package com.wzgiceman.rxretrofitlibrary.retrofit_rx.downlaod;


import com.wzgiceman.rxretrofitlibrary.retrofit_rx.listener.HttpDownOnNextListener;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Generated;

import java.io.Serializable;

/**
 * apk下载请求数据基础类
 * Created by WZG on 2016/10/20.
 */

@Entity
public class DownInfo implements Serializable {
    private static final long serialVersionUID = -190734710746841476L;
    @Id
    private long id;
    /**
     * 存储位置
     */
    private String savePath;
    /**
     * url
     */
    private String url;
    /**
     * 文件总长度
     */
    private long countLength;
    /**
     * 下载长度
     */
    private long readLength;
    /**
     * state状态
     */
    private int stateInte;
    /**
     * 缩略图
     */
    private String imageUrl;
    /**
     * title
     */
    private String title;
    /**
     * description
     */
    private String description;
    /**
     * isBanner
     */
    private boolean isBanner;//视频播放用
    /**
     * liveId
     */
    private String liveId;//视频播放用

    /**
     * 下载唯一的HttpService
     */
    @Transient
    private HttpDownService service;
    /**
     * 回调监听
     */
    @Transient
    private HttpDownOnNextListener listener;
    /**
     * 超时设置
     */
    @Transient
    private int connectonTime = 6;
    /**
     * 删除标记（true删除,false不删除）
     */
    @Transient
    private boolean delFlag = false;

    public DownInfo(String url) {
        setUrl(url);
    }
//    private DownloadException exception;//获取异常内容
//
//    public DownloadException getException() {
//        return exception;
//    }
//
//    public void setException(DownloadException exception) {
//        this.exception = exception;
//    }

    @Keep
    public DownInfo(long id, String savePath, String url, long countLength,
            long readLength, int stateInte, String imageUrl, String title,
            String description, boolean isBanner, String liveId) {
        this.id = id;
        this.savePath = savePath;
        this.url = url;
        this.countLength = countLength;
        this.readLength = readLength;
        this.stateInte = stateInte;
        this.imageUrl = imageUrl;
        this.title = title;
        this.description = description;
        this.isBanner = isBanner;
        this.liveId = liveId;
    }


    @Keep
    public DownInfo() {
    }


    public DownState getState() {
        switch (getStateInte()) {
            case 0:
                return DownState.START;
            case 1:
                return DownState.DOWN;
            case 2:
                return DownState.PAUSE;
            case 3:
                return DownState.STOP;
            case 4:
                return DownState.ERROR;
            case 5:
            default:
                return DownState.FINISH;
        }
    }

    public void setState(DownState state) {
        setStateInte(state.getState());
    }


    public int getStateInte() {
        return stateInte;
    }

    public void setStateInte(int stateInte) {
        this.stateInte = stateInte;
    }

    public HttpDownOnNextListener getListener() {
        return listener;
    }

    public void setListener(HttpDownOnNextListener listener) {
        this.listener = listener;
    }

    public HttpDownService getService() {
        return service;
    }

    public void setService(HttpDownService service) {
        this.service = service;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }


    public long getCountLength() {
        return countLength;
    }

    public void setCountLength(long countLength) {
        this.countLength = countLength;
    }


    public long getReadLength() {
        return readLength;
    }

    public void setReadLength(long readLength) {
        this.readLength = readLength;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getConnectonTime() {
        return this.connectonTime;
    }

    public void setConnectonTime(int connectonTime) {
        this.connectonTime = connectonTime;
    }

    public boolean isDelFlag() {
        return delFlag;
    }

    public void setDelFlag(boolean delFlag) {
        this.delFlag = delFlag;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isBanner() {
        return isBanner;
    }

    public void setBanner(boolean banner) {
        isBanner = banner;
    }

    public String getLiveId() {
        return liveId;
    }

    public void setLiveId(String liveId) {
        this.liveId = liveId;
    }


    public boolean getIsBanner() {
        return this.isBanner;
    }


    public void setIsBanner(boolean isBanner) {
        this.isBanner = isBanner;
    }

    @Override
    public String toString() {
        return "DownInfo{" +
                "id=" + id +
                ", savePath='" + savePath + '\'' +
                ", url='" + url + '\'' +
                ", countLength=" + countLength +
                ", readLength=" + readLength +
                ", stateInte=" + stateInte +
                ", imageUrl='" + imageUrl + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", isBanner=" + isBanner +
                ", liveId='" + liveId + '\'' +
                ", service=" + service +
                ", listener=" + listener +
                ", connectonTime=" + connectonTime +
                ", delFlag=" + delFlag +
                '}';
    }
}
