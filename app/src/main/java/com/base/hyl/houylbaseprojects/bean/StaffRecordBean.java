package com.base.hyl.houylbaseprojects.bean;

public class StaffRecordBean {
    public static final int ONLE_TITLE = 0;//只有标题
    public static final int MARK_INFO = 1;//标注信息
    public static final int SUC_INFO = 2;//成功信息
    public static final int FAIL_INFO = 3;//失败信息
    public static final int ONLY_ENDING = 4;//结尾

    public StaffRecordBean(int type, String title, String time) {
        this.type=type;
        this.title=title;
        this.time=time;
    }
    public StaffRecordBean(int type, String title) {
        this.type=type;
        this.title=title;
    }
    public StaffRecordBean(int type) {
        this.type=type;
    }
    private int type;//类型5种
    private String title;//类型四种
    private String time;//时间

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "RecordBean{" +
                "type='" + type + '\'' +
                ", title='" + title + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
