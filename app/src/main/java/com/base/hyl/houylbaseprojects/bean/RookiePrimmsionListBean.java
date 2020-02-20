package com.base.hyl.houylbaseprojects.bean;

public class RookiePrimmsionListBean {
    @Override
    public String toString() {
        return "RookiePrimmsionListBean{" +
                "resourceName='" + resourceName + '\'' +
                ", resourcePath='" + resourcePath + '\'' +
                ", logoImgUrl='" + logoImgUrl + '\'' +
                ", targetType='" + targetType + '\'' +
                ", type='" + type + '\'' +
                ", url='" + url + '\'' +
                ", android='" + android + '\'' +
                ", ios='" + ios + '\'' +
                ", trackLabel='" + trackLabel + '\'' +
                ", trackEvent='" + trackEvent + '\'' +
                ", blocked=" + blocked +
                ", tip='" + tip + '\'' +
                '}';
    }

    /**
     * resourceName : 培训辅导
     * resourcePath : /1005

     * targetType : h5
     * type :

     * android :
     * ios :
     * trackLabel : 点击培训辅导(新人)_33_12
     * trackEvent : 新人登录_+debug/release+_34
     */

    private String resourceName;
    private String resourcePath;
    private String logoImgUrl;
    private String targetType;
    private String type;
    private String url;
    private String android;
    private String ios;
    private String trackLabel;
    private String trackEvent;
    private boolean blocked;
    private String tip;

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getResourcePath() {
        return resourcePath;
    }

    public void setResourcePath(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    public String getLogoImgUrl() {
        return logoImgUrl;
    }

    public void setLogoImgUrl(String logoImgUrl) {
        this.logoImgUrl = logoImgUrl;
    }

    public String getTargetType() {
        return targetType;
    }

    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAndroid() {
        return android;
    }

    public void setAndroid(String android) {
        this.android = android;
    }

    public String getIos() {
        return ios;
    }

    public void setIos(String ios) {
        this.ios = ios;
    }

    public String getTrackLabel() {
        return trackLabel;
    }

    public void setTrackLabel(String trackLabel) {
        this.trackLabel = trackLabel;
    }

    public String getTrackEvent() {
        return trackEvent;
    }

    public void setTrackEvent(String trackEvent) {
        this.trackEvent = trackEvent;
    }
}
