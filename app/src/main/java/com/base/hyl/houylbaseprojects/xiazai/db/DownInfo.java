package com.base.hyl.houylbaseprojects.xiazai.db;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.Transient;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Unique;

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
    @Unique
    private String liveId;//视频播放用


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





    public int getStateInte() {
        return stateInte;
    }

    public void setStateInte(int stateInte) {
        this.stateInte = stateInte;
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
                ", connectonTime=" + connectonTime +
                ", delFlag=" + delFlag +
                '}';
    }
}
