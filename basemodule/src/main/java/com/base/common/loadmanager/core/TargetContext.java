package com.base.common.loadmanager.core;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

/**
 * Description:TODO
 * Create Time:2017/9/4 16:28
 * Author:KingJA
 * Email:kingjavip@gmail.com
 * 获取代替对象的一般信息
 */
public class TargetContext {
    private Context context; //上下文
    private ViewGroup parentView;//父view
    private View oldContent;//之前的内容
    private int childIndex;//位置信息

    public TargetContext(Context context, ViewGroup parentView, View oldContent, int childIndex) {
        this.context = context;
        this.parentView = parentView;
        this.oldContent = oldContent;
        this.childIndex = childIndex;
    }

    public Context getContext() {
        return context;
    }

    View getOldContent() {
        return oldContent;
    }

    int getChildIndex() {
        return childIndex;
    }

    ViewGroup getParentView() {
        return parentView;
    }
}
