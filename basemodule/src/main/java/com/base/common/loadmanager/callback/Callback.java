package com.base.common.loadmanager.callback;

import android.content.Context;
import android.view.View;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;


/**
 * Description:TODO
 * Create Time:2017/9/2 17:04
 * Author:KingJA
 *
 */
public abstract class Callback implements Serializable {
    private View rootView;//需要展示的view
    private Context context;//上下文
    private OnReloadListener onReloadListener;// 点击监听 重试效果
    private boolean successViewVisible;//获取是否展示的状态
    //构造方法1
    public Callback() {
    }
    //构造方法2
    public  Callback(View view, Context context, OnReloadListener onReloadListener) {
        this.rootView = view;
        this.context = context;
        this.onReloadListener = onReloadListener;
    }
    //重新设置该对象状态
    public Callback setCallback(View view, Context context, OnReloadListener onReloadListener) {
        this.rootView = view;
        this.context = context;
        this.onReloadListener = onReloadListener;
        return this;
    }
    //获取展示的view
    public View getRootView() {
        int resId = onCreateView();
        if (resId == 0 && rootView != null) {//获取直接设置的rootView
            return rootView;
        }

        if (onBuildView(context) != null) {//获取 buildview 设置的rootview
            rootView = onBuildView(context);
        }

        if (rootView == null) {//如果 buildview 没有设置,重新加载获取 rootview
            rootView = View.inflate(context, onCreateView(), null);
        }
        // 设置rootview 的点击事件
        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onReloadEvent(context, rootView)) {
                    return;
                }
                if (onReloadListener != null) {
                    onReloadListener.onReload(v);
                }
            }
        });
        onViewCreate(context, rootView);//传递rootview context
        return rootView;
    }

    protected View onBuildView(Context context) {
        return null;
    }

    /**
     * if return true, the successView will be visible when the view of callback is attached.
     */
    //是否展示 成功的页面
    public boolean getSuccessVisible() {
        return successViewVisible;
    }
    //设置是否成功展示
    void setSuccessVisible(boolean visible) {
        this.successViewVisible = visible;
    }

    //是否可以点击回调 事件 设置
    protected boolean onReloadEvent(Context context, View view) {
        return false;
    }
    /* 复制一份 callback 可以考虑原型模式*/
    public Callback copy() {
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        ObjectOutputStream oos;
        Object obj = null;
        try {
            oos = new ObjectOutputStream(bao);
            oos.writeObject(this);
            oos.close();
            ByteArrayInputStream bis = new ByteArrayInputStream(bao.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bis);
            obj = ois.readObject();
            ois.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (Callback) obj;
    }

    /**
     * @since 1.2.2
     */
    // 获取根目录
    public View obtainRootView() {
        if (rootView == null) {
            rootView = View.inflate(context, onCreateView(), null);
        }
        return rootView;
    }
    /* 定义点击回调接口*/
    public interface OnReloadListener extends Serializable {
        void onReload(View v);
    }
    //获取自定义需要展示的view id
    protected abstract int onCreateView();
    // 创建rootview
    /**
     * Called immediately after {@link #onCreateView()}
     *
     * @since 1.2.2
     */
    protected void onViewCreate(Context context, View view) {
    }
    //绑定到 loadlayout
    /**
     * Called when the rootView of Callback is attached to its LoadLayout.
     *
     * @since 1.2.2
     */
    public void onAttach(Context context, View view) {
    }
    /*  从loadlayout解绑  */
    /**
     * Called when the rootView of Callback is removed from its LoadLayout.
     *
     * @since 1.2.2
     */
    public void onDetach() {
    }

}
