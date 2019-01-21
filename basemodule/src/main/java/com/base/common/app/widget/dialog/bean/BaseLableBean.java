package com.base.common.app.widget.dialog.bean;

/**
 * Created by liuyang249 on 2018/7/4.
 */

public class BaseLableBean implements Cloneable{
    protected String lableName;
    protected boolean isCheck;
    protected String lableId;
    public BaseLableBean(String lableName,String lableId) {
        this.lableName = lableName;
        this.lableId=lableId;
    }
    public BaseLableBean(String lableName) {
        this.lableName = lableName;
    }

    public BaseLableBean(String lableName, boolean isCheck, String lableId) {
        this.lableName = lableName;
        this.isCheck = isCheck;
        this.lableId = lableId;
    }

    public String getLableId() {
        return lableId;
    }

    public void setLableId(String lableId) {
        this.lableId = lableId;
    }

    public String getLableName() {
        return lableName;
    }

    public void setLableName(String lableName) {
        this.lableName = lableName;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public BaseLableBean(String lableName, boolean isCheck) {
        this.lableName = lableName;
        this.isCheck = isCheck;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
