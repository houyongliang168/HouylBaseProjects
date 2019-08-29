package com.base.common.loadmanager.core;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;


import com.base.common.loadmanager.callback.Callback;
import com.base.common.loadmanager.callback.SuccessCallback;

import java.util.List;

/**
 * Description:TODO
 * Create Time:2017/9/6 10:05
 * Author:KingJA
 * Email:kingjavip@gmail.com
 * 管理的类  处理 展示信息
 */
public class LoadService<T> {
    private LoadLayout loadLayout;//获取容器
    private Convertor<T> convertor;/* 转化的接口 将一些状态传递过来*/
    //构造方法 基本信息传递

    /**
     *
     * @param convertor   类型转换 为想要的TAG
     * @param targetContext  目标view 相关状态的保存
     * @param onReloadListener 点击回调
     * @param builder  相关的初始化设置信息
     */
    LoadService(Convertor<T> convertor, TargetContext targetContext, Callback.OnReloadListener onReloadListener,
                LoadSir.Builder builder) {
        this.convertor = convertor;
        Context context = targetContext.getContext();
        View oldContent = targetContext.getOldContent();
        ViewGroup.LayoutParams oldLayoutParams = oldContent.getLayoutParams();
        loadLayout = new LoadLayout(context, onReloadListener);
        loadLayout.setupSuccessLayout(new SuccessCallback(oldContent, context,
                onReloadListener));
        if (targetContext.getParentView() != null) {//往里面插入了一个view
            targetContext.getParentView().addView(loadLayout, targetContext.getChildIndex(), oldLayoutParams);
        }
        initCallback(builder);
    }
    /* 初始化信息 获取默认的回调信息*/
    private void initCallback(LoadSir.Builder builder) {
        List<Callback> callbacks = builder.getCallbacks();//获取设置的所有参数信息
        Class<? extends Callback> defalutCallback = builder.getDefaultCallback();
        if (callbacks != null && callbacks.size() > 0) {
            for (Callback callback : callbacks) {// 设置回调相关信息
                loadLayout.setupCallback(callback);
            }
        }
        if (defalutCallback != null) {//展示默认 回调
            loadLayout.showCallback(defalutCallback);
        }
    }
    // 展示成功的信息
    public void showSuccess() {
        loadLayout.showCallback(SuccessCallback.class);
    }
    //  展示回调的信息
    public void showCallback(Class<? extends Callback> callback) {
        loadLayout.showCallback(callback);
    }
    /* 获取转换的Tag ,并处理*/
    public void showWithConvertor(T t) {
        if (convertor == null) {
            throw new IllegalArgumentException("You haven't set the Convertor.");
        }
        loadLayout.showCallback(convertor.map(t));
    }
    /* 获取容器对象*/
    public LoadLayout getLoadLayout() {
        return loadLayout;
    }
    /* 获取当前的展示 类型信息*/
    public Class<? extends Callback> getCurrentCallback() {
        return loadLayout.getCurrentCallback();
    }

//    /**
//     * obtain rootView if you want keep the toolbar in Fragment
//     * @since 1.2.2
//     * @deprecated
//     */
//    public LinearLayout getTitleLoadLayout(Context context, ViewGroup rootView, View titleView) {
//        LinearLayout newRootView = new LinearLayout(context);
//        newRootView.setOrientation(LinearLayout.VERTICAL);
//        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.MATCH_PARENT);
//        newRootView.setLayoutParams(layoutParams);
//        rootView.removeView(titleView);
//        newRootView.addView(titleView);
//        newRootView.addView(loadLayout, layoutParams);
//        return newRootView;
//    }

    /**
     * modify the callback dynamically 修改View回调的状态
     *
     * @param callback  which callback you want modify(layout, event)
     * @param transport a interface include modify logic
     * @since 1.2.2
     */
    public LoadService<T> setCallBack(Class<? extends Callback> callback, Transport transport) {
        loadLayout.setCallBack(callback, transport);
        return this;
    }
}
