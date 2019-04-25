package com.base.hyl.houylbaseprojects.rukou.bean;

/**
 * Created by houyongliang on 2018/3/7 16:45.
 * Function(功能):
 */

public class AgentBean {
    private String details;/*描述*/
    private String key;
    private int img;/*图片地址值*/
    private String value;
    private boolean hasMsg;

    @Override
    public String toString() {
        return "AgentBean{" +
                "details='" + details + '\'' +
                ", key='" + key + '\'' +
                ", img=" + img +
                ", value='" + value + '\'' +
                '}';
    }

    public boolean isHasMsg() {
        return hasMsg;
    }

    public void setHasMsg(boolean hasMsg) {
        this.hasMsg = hasMsg;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getImg() {
        return img;
    }

    public void setImg(Integer img) {
        this.img = img;
    }


    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

}
