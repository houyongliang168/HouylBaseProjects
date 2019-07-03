package com.base.hyl.houylbaseprojects.databing.msgBase.bean;

/**
 * Created by zhaoyu on 2017/5/9.
 */

public class DeleteMsg {


    /**
     * rspCode : 0
     * rspDesc : 成功
     * info : {"delSuccess":"40","delFail":""}
     * url :
     * pageNums : 1
     * sign : F769612C95CD8A8E1D95CB3C0D2DC590
     * signStr : {"delSuccess":"40","delFail":""}
     */

    private String rspCode;
    private String rspDesc;
    private InfoBean info;
    private String url;
    private String pageNums;
    private String sign;
    private String signStr;

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

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
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

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getSignStr() {
        return signStr;
    }

    public void setSignStr(String signStr) {
        this.signStr = signStr;
    }

    public static class InfoBean {
        /**
         * delSuccess : 40
         * delFail :
         */

        private String delSuccess;
        private String delFail;

        public String getDelSuccess() {
            return delSuccess;
        }

        public void setDelSuccess(String delSuccess) {
            this.delSuccess = delSuccess;
        }

        public String getDelFail() {
            return delFail;
        }

        public void setDelFail(String delFail) {
            this.delFail = delFail;
        }
    }
}
