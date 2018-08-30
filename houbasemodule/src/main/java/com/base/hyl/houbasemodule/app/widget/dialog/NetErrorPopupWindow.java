package com.base.hyl.houbasemodule.app.widget.dialog;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.base.hyl.houbasemodule.R;


/**
 * Created by zhaoyu on 2017/5/12.
 */

public class NetErrorPopupWindow extends PopupWindow  {
    private View view;
    private FragmentActivity activity;
    // 用于保存PopupWindow的宽度
    private int width;
    // 用于保存PopupWindow的高度
    private int height;
    public NetErrorPopupWindow(FragmentActivity activity) {
        this.activity=activity;
        initPopupWindow();
    }

    private void initPopupWindow() {
        LayoutInflater inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.view = inflater.inflate(R.layout.basemodule_dialog_neterror, null);
        this.setContentView(view);
        view.findViewById(R.id.rl_net).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));//进入无线网络配置界面
                NetErrorPopupWindow.this.dismiss();
            }
        });
        // 设置弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置弹出窗体可点击
        this.setTouchable(true);
        this.setFocusable(true);
        // 设置点击是否消失
        this.setOutsideTouchable(true);
        //设置弹出窗体动画效果
        //this.setAnimationStyle(R.style.netError_popup_anim);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable background = new ColorDrawable(0x000000);
        //background.setAlpha(178);
        //设置弹出窗体的背景
        this.setBackgroundDrawable(background);

        // 绘制
        this.mandatoryDraw();
    }
    /**
     * 强制绘制popupWindowView，并且初始化popupWindowView的尺寸
     */
    private void mandatoryDraw() {
        this.view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        /**
         * 强制刷新后拿到PopupWindow的宽高
         */
        this.width = this.view.getMeasuredWidth();
        this.height = this.view.getMeasuredHeight();
    }

    /**
     * 显示在控件的下右方
     *
     * @param parent parent
     */
    public void showAtDropDownRight(View parent) {
        if (parent.getVisibility() == View.GONE) {
            this.showAtLocation(parent, 0, 0, 0);
        } else {
            // x y
            int[] location = new int[2];
            //获取在整个屏幕内的绝对坐标
            parent.getLocationOnScreen(location);
            this.showAtLocation(parent, 0, location[0] + parent.getWidth() - this.width, location[1] + parent.getHeight());
        }
    }

    /**
     * 显示在控件的下左方
     *
     * @param parent parent
     */
    public void showAtDropDownLeft(View parent) {
        if (parent.getVisibility() == View.GONE) {
            this.showAtLocation(parent, 0, 0, 0);
        } else {
            // x y
            int[] location = new int[2];
            //获取在整个屏幕内的绝对坐标
            parent.getLocationOnScreen(location);
            this.showAtLocation(parent, 0, location[0], location[1] + parent.getHeight());
        }
    }

    /**
     * 显示在控件的下中方
     *
     * @param parent parent
     */
    public void showAtDropDownCenter(View parent) {
        if (parent.getVisibility() == View.GONE) {
            this.showAtLocation(parent, 0, 0, 0);
        } else {
            // x y
            int[] location = new int[2];
            //获取在整个屏幕内的绝对坐标
            parent.getLocationOnScreen(location);
            this.showAtLocation(parent, 0, location[0] / 2 + parent.getWidth() / 2 - this.width / 6, location[1] + parent.getHeight());
        }
    }

}
