package com.base.widget.smartRefresh;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.hyl.houbasemodule.R;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;


/**
 * Created by zcc on 2018/4/8.
 * SmartRefreshLayoutHeader
 */

public class MyRefreshHeader extends LinearLayout implements RefreshHeader {

    private Context mContext;
    private AnimationDrawable mAnimationDrawable;
    private ImageView iv_header;
    private ImageView iv_header_frame;
    private TextView tv_header_text;

    public MyRefreshHeader(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    private void initView() {
        View root = View.inflate(mContext, R.layout.refresh_layout_header, MyRefreshHeader.this);
        iv_header_frame = (ImageView) root.findViewById(R.id.iv_header_frame);
        mAnimationDrawable = (AnimationDrawable) iv_header_frame.getDrawable();
        iv_header = (ImageView) root.findViewById(R.id.iv_header);
        tv_header_text = (TextView) root.findViewById(R.id.tv_header_text);
    }

    /**
     * 启动动画
     */
    private void startAnimation() {
        iv_header.setVisibility(View.GONE);
        iv_header_frame.setVisibility(View.VISIBLE);
        mAnimationDrawable.start();
    }

    /**
     * 停止动画
     */
    private void stopAnimation() {
        mAnimationDrawable.stop();
        iv_header_frame.setVisibility(View.GONE);
        iv_header.setVisibility(View.VISIBLE);
    }

    /**
     * 设置刷新状态文字
     *
     * @param text
     */
    public void setHeaderText(String text) {
        tv_header_text.setText(text);
    }


    @NonNull
    @Override
    public View getView() {
        return this;
    }

    /**
     * 获取变换方式
     *
     * @return
     */
    @NonNull
    @Override
    public SpinnerStyle getSpinnerStyle() {
        return SpinnerStyle.Translate;
    }

    @Override
    public void setPrimaryColors(@ColorInt int... ints) {
        setBackgroundColor(Color.parseColor("#f4f4f4"));
    }

    /**
     * 尺寸定义完成
     * @param refreshKernel
     * @param i
     * @param i1
     */
    @Override
    public void onInitialized(@NonNull RefreshKernel refreshKernel, int i, int i1) {
    }

    /**
     * 手指拖动下拉
     * @param b
     * @param v
     * @param i
     * @param i1
     * @param i2
     */
    @Override
    public void onMoving(boolean b, float v, int i, int i1, int i2) {
    }

    /**
     * 手指释放之后
     * @param refreshLayout
     * @param i
     * @param i1
     */
    @Override
    public void onReleased(@NonNull RefreshLayout refreshLayout, int i, int i1) {
    }

    /**
     * 开始动画
     *
     * @param refreshLayout
     * @param i
     * @param i1
     */
    @Override
    public void onStartAnimator(@NonNull RefreshLayout refreshLayout, int i, int i1) {
    }

    /**
     * 动画结束
     *
     * @param refreshLayout
     * @param b
     * @return
     */
    @Override
    public int onFinish(@NonNull RefreshLayout refreshLayout, boolean b) {
        return 0;
    }

    @Override
    public void onHorizontalDrag(float v, int i, int i1) {

    }

    @Override
    public boolean isSupportHorizontalDrag() {
        return false;
    }

    @Override
    public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState oldState, @NonNull RefreshState newState) {
        switch (newState) {
            case None:
                stopAnimation();
                break;
            case PullDownToRefresh:
                setHeaderText("下拉刷新");
                break;
            case Refreshing:
                setHeaderText("正在刷新...");
                break;
            case ReleaseToRefresh:
                setHeaderText("松开进行刷新");
                startAnimation();
                break;
        }
    }
}
