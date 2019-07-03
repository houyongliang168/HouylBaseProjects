package com.base.hyl.houylbaseprojects.databing.msgBase.bean;

public class MsgBaseInfoBean {
    /**
     * shareDate :
     * updateDate : 2019-06-27 10:23:05
     * shareInfo : {"share":"false"}
     * shareInfoId :
     * showRound : false
     * receiptDate :
     * title : 请假申请流转中2
     * content : 您于2019-05-20提交的事假申请（20天）尚在流程中，
     请关注！
     * url :
     * isHtml : 0
     * internalGoto :
     * imageUrl :
     * id : 50146128
     * msgtype : 15
     * selected : false
     * createDate : 2019-06-27 10:23:05
     */

    private String shareDate;
    private String updateDate;
    private ShareInfoBean shareInfo;
    private String shareInfoId;
    private String showRound;
    private String receiptDate;
    private String title;
    private String content;
    private String url;
    private String isHtml;
    private String internalGoto;
    private String imageUrl;
    private int id;
    private String msgtype;
    private String selected;
    private String createDate;

    public String getShareDate() {
        return shareDate;
    }

    public void setShareDate(String shareDate) {
        this.shareDate = shareDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public ShareInfoBean getShareInfo() {
        return shareInfo;
    }

    public void setShareInfo(ShareInfoBean shareInfo) {
        this.shareInfo = shareInfo;
    }

    public String getShareInfoId() {
        return shareInfoId;
    }

    public void setShareInfoId(String shareInfoId) {
        this.shareInfoId = shareInfoId;
    }

    public String getShowRound() {
        return showRound;
    }

    public void setShowRound(String showRound) {
        this.showRound = showRound;
    }

    public String getReceiptDate() {
        return receiptDate;
    }

    public void setReceiptDate(String receiptDate) {
        this.receiptDate = receiptDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIsHtml() {
        return isHtml;
    }

    public void setIsHtml(String isHtml) {
        this.isHtml = isHtml;
    }

    public String getInternalGoto() {
        return internalGoto;
    }

    public void setInternalGoto(String internalGoto) {
        this.internalGoto = internalGoto;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMsgtype() {
        return msgtype;
    }

    public void setMsgtype(String msgtype) {
        this.msgtype = msgtype;
    }

    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public static class ShareInfoBean {
        /**
         * share : false
         */

        private String share;

        public String getShare() {
            return share;
        }

        public void setShare(String share) {
            this.share = share;
        }

        @Override
        public String toString() {
            return "ShareInfoBean{" +
                    "share='" + share + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "MsgBaseInfoBean{" +
                "shareDate='" + shareDate + '\'' +
                ", updateDate='" + updateDate + '\'' +
                ", shareInfo=" + shareInfo +
                ", shareInfoId='" + shareInfoId + '\'' +
                ", showRound='" + showRound + '\'' +
                ", receiptDate='" + receiptDate + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", url='" + url + '\'' +
                ", isHtml='" + isHtml + '\'' +
                ", internalGoto='" + internalGoto + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", id=" + id +
                ", msgtype='" + msgtype + '\'' +
                ", selected='" + selected + '\'' +
                ", createDate='" + createDate + '\'' +
                '}';
    }

    //    private String content;
//    private int id;
//    private String title;
//    private int msgType;
//    private String createDate;
//    private String updateDate;
//    //private int isReaded;
//    //private String msgTypeName;
//    private String url;
//    private String identification;
//    private int isHtml;
//    private String internalGoto;
//
//    private boolean showRound;
//    private String channel;
//
//    private String staffNumber;
//    private boolean selected;
//
//    public String getIdentification() {
//        return identification;
//    }
//
//    public void setIdentification(String identification) {
//        this.identification = identification;
//    }
//
//    public int getIsHtml() {
//        return isHtml;
//    }
//
//    public void setIsHtml(int isHtml) {
//        this.isHtml = isHtml;
//    }
//
//    public String getInternalGoto() {
//        return internalGoto;
//    }
//
//    public void setInternalGoto(String internalGoto) {
//        this.internalGoto = internalGoto;
//    }
//
//    public String getUpdateDate() {
//        return updateDate;
//    }
//
//    public void setUpdateDate(String updateDate) {
//        this.updateDate = updateDate;
//    }
//
//    public boolean isShowRound() {
//        return showRound;
//    }
//
//    public void setShowRound(boolean showRound) {
//        this.showRound = showRound;
//    }
//
//    public String getChannel() {
//        return channel;
//    }
//
//    public void setChannel(String channel) {
//        this.channel = channel;
//    }
//
//
//    public String getStaffNumber() {
//        return staffNumber;
//    }
//
//    public void setStaffNumber(String staffNumber) {
//        this.staffNumber = staffNumber;
//    }
//
//    public boolean isSelected() {
//        return selected;
//    }
//
//    public void setSelected(boolean selected) {
//        this.selected = selected;
//    }
//
//    public String getContent() {
//        return content;
//    }
//
//    public void setContent(String content) {
//        this.content = content;
//    }
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public int getMsgType() {
//        return msgType;
//    }
//
//    public void setMsgType(int msgType) {
//        this.msgType = msgType;
//    }
//
//    public String getCreateDate() {
//        return createDate;
//    }
//
//    public void setCreateDate(String createDate) {
//        this.createDate = createDate;
//    }
//
//
//
//    public String getUrl() {
//        return url;
//    }
//
//    public void setUrl(String url) {
//        this.url = url;
//    }
//
//    @Override
//    public String toString() {
//        return "MsgBaseInfoBean{" +
//                "content='" + content + '\'' +
//                ", id=" + id +
//                ", title='" + title + '\'' +
//                ", msgType=" + msgType +
//                ", createDate='" + createDate + '\'' +
//                ", url='" + url + '\'' +
//                ", identification='" + identification + '\'' +
//                ", isHtml=" + isHtml +
//                ", internalGoto='" + internalGoto + '\'' +
//                ", updateDate='" + updateDate + '\'' +
//                ", showRound=" + showRound +
//                ", channel='" + channel + '\'' +
//                ", staffNumber='" + staffNumber + '\'' +
//                ", selected=" + selected +
//                '}';
//    }


}
