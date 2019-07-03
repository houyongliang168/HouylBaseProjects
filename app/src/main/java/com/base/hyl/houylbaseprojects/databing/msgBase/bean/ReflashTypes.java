package com.base.hyl.houylbaseprojects.databing.msgBase.bean;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by houyongliang on 2018/5/25 10:48.
 * Function(功能): 定义不同的刷新状态
 */

public class ReflashTypes {
    @ReflashStatus
    int currentStatus = NONE;/*默认为 没有*/

    //先定义 常量
    public static final int NONE = 0;
    public static final int REFLASH = 1;
    public static final int LOAD_MORE = 2;

    //用 @IntDef "包住" 常量；
    // @Retention 定义策略
    // 声明构造器
    @IntDef({NONE, REFLASH, LOAD_MORE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ReflashStatus {

    }

    public void setCurrentStatus(@ReflashStatus int currentStatus) {
        this.currentStatus = currentStatus;
    }

    @ReflashStatus
    public int getCurrentStatus() {
        return currentStatus;
    }

}
