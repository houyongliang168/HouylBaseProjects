package com.base.common.loadmanager.core;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.FrameLayout;


import com.base.common.loadmanager.LoadSirUtil;
import com.base.common.loadmanager.callback.Callback;
import com.base.common.loadmanager.callback.SuccessCallback;

import java.util.HashMap;
import java.util.Map;

/**
 * Description:TODO
 * Create Time:2017/9/2 17:02
 * Author:KingJA
 */

/**
 *  多个布局的容器
 */
public class LoadLayout extends FrameLayout {
    private Map<Class<? extends Callback>, Callback> callbacks = new HashMap<>();/* 容器 ,获取所有对象*/
    private Context context;//上下文
    private Callback.OnReloadListener onReloadListener;/* copy 后重新加载监听*/
    private Class<? extends Callback> preCallback;//之前的 回调 标识
    private Class<? extends Callback> curCallback;//当前的回调 标识
    private static final int CALLBACK_CUSTOM_INDEX = 1;
   // private View  rootView;//获取成功后的界面
    /* 构造方法1*/
    public LoadLayout(@NonNull Context context) {
        super(context);
    }
    /* 构造方法2 设置监听*/
    public LoadLayout(@NonNull Context context, Callback.OnReloadListener onReloadListener) {
        this(context);
        this.context = context;
        this.onReloadListener = onReloadListener;
    }
    /* 设置成功的回调*/
    public void setupSuccessLayout(Callback callback) {
        addCallback(callback);//设置监听
        View successView = callback.getRootView();
        successView.setVisibility(View.GONE);//之前的布局隐藏
        addView(successView);
        curCallback = SuccessCallback.class;//设置为成功的回调
    }
    /* 设置回调 开始的时候设置*/
    public void setupCallback(Callback callback) {
        Callback cloneCallback = callback.copy();// 复制回调的信息 浅拷贝
        // 重新设置回调的
        cloneCallback.setCallback(null, context, onReloadListener);
        addCallback(cloneCallback);//设置监听
    }
    // 添加监听
    public void addCallback(Callback callback) {
        if (!callbacks.containsKey(callback.getClass())) {
            callbacks.put(callback.getClass(), callback);
        }
    }
    /* 展示 回调的信息*/
    public void showCallback(final Class<? extends Callback> callback) {
        checkCallbackExist(callback);//判断是都有回调信息
        if (LoadSirUtil.isMainThread()) {//如果是主线程
            showCallbackView(callback);
        } else {
            postToMainThread(callback);//子线程处理
        }
    }
    //获取当前的 回调 (展示页面)
    public Class<? extends Callback> getCurrentCallback() {
        return curCallback;
    }

    private void postToMainThread(final Class<? extends Callback> status) {
        post(new Runnable() {
            @Override
            public void run() {
                showCallbackView(status);
            }
        });
    }
    /* 展示回调的view */
    private void showCallbackView(Class<? extends Callback> status) {
        if (preCallback != null) {//获取之前的回调信息
            if (preCallback == status) {//如果当前状态与之前状态一致 不处理
                return;
            }
            callbacks.get(preCallback).onDetach();//解绑
        }
        if (getChildCount() > 1) {
            removeViewAt(CALLBACK_CUSTOM_INDEX);//移除其他view
        }

        for (Class key : callbacks.keySet()) {//遍历map
            if (key == status) {//找到当前的状态
                //如果当前的状态是 成功
                SuccessCallback successCallback = (SuccessCallback) callbacks.get(SuccessCallback.class);
                if (key == SuccessCallback.class) {
                    successCallback.show();
                } else {//如果当前的状态不是 成功
                    //判断要不要展示成功界面
                    successCallback.showWithCallback(callbacks.get(key).getSuccessVisible());
                    View rootView = callbacks.get(key).getRootView();
                    addView(rootView);//添加view
                    callbacks.get(key).onAttach(context, rootView);//绑定
                }
                preCallback = status;//更新之前状态
            }
        }
        curCallback = status;//更新当前的状态
    }
    /* 设置回调 可以获取相关view 并重新设置信息*/
    public void setCallBack(Class<? extends Callback> callback, Transport transport) {
        if (transport == null) {
            return;
        }
        checkCallbackExist(callback);//检查是否存在
        transport.order(context, callbacks.get(callback).obtainRootView());
    }

    private void checkCallbackExist(Class<? extends Callback> callback) {
        if (!callbacks.containsKey(callback)) {
            throw new IllegalArgumentException(String.format("The Callback (%s) is nonexistent.", callback
                    .getSimpleName()));
        }
    }
}
