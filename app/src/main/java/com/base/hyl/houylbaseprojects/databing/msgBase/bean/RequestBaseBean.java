package com.base.hyl.houylbaseprojects.databing.msgBase.bean;


public class RequestBaseBean<T> {
    private String rspCode;
    private String rspDesc;
    private String url;
    private String pageNums;

    private T  info;
   public void setInfo(T info) {
       this.info = info;
   }
    public T getInfo() {
        return info;
    }

    public String getRspCode() {
        return rspCode;
    }

    public void setRspCode(String rspCode) {
        this.rspCode = rspCode;
    }

    public String getRspDesc() {
        return rspDesc;
    }

    public void setRspDesc(String rspDesc) {
        this.rspDesc = rspDesc;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPageNums() {
        return pageNums;
    }

    public void setPageNums(String pageNums) {
        this.pageNums = pageNums;
    }


    @Override
    public String toString() {
        return "MsgListBaseBean{" +
                "rspCode='" + rspCode + '\'' +
                ", rspDesc='" + rspDesc + '\'' +
                ", url='" + url + '\'' +
                ", pageNums='" + pageNums + '\'' +
//                ", sign='" + sign + '\'' +
//                ", signStr='" + signStr + '\'' +
                ", info=" + info +
                '}';
    }
}
