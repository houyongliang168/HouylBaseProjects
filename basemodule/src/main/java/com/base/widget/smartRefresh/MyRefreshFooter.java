package com.base.widget.smartRefresh;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.hyl.houbasemodule.R;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;


/**
 * Created by zcc on 2018/4/8.
 * SmartRefreshLayoutFooter
 */

public class MyRefreshFooter extends LinearLayout implements RefreshFooter {

    public static String REFRESH_FOOTER_PULLING = "查看更多";//"上拉加载更多";
    public static String REFRESH_FOOTER_RELEASE = null;//"释放立即加载";
    public static String REFRESH_FOOTER_LOADING = "正在加载";//"正在加载...";
    public static String REFRESH_FOOTER_REFRESHING = null;//"正在刷新...";
    public static String REFRESH_FOOTER_FINISH = null;//"加载完成";
    public static String REFRESH_FOOTER_FAILED = "加载失败";//"加载失败";
    public static String REFRESH_FOOTER_NOTHING = "没有更多了";//"没有更多数据了";

    protected boolean mNoMoreData = false;

    private Context mContext;
    private TextView tv_footer_text;

    public MyRefreshFooter(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    private void initView() {
        View root = View.inflate(mContext, R.layout.refresh_layout_footer, MyRefreshFooter.this);
        tv_footer_text = (TextView) root.findViewById(R.id.tv_footer_text);
    }

    /**
     * 设置刷新状态文字
     */
    private void setFooterText(String text) {
        tv_footer_text.setText(text);
    }

    /**
     * 设置数据全部加载完成，将不能再次触发加载功能
     */
    @Override
    public boolean setNoMoreData(boolean noMoreData) {
        if (mNoMoreData != noMoreData) {
            mNoMoreData = noMoreData;
            if (noMoreData) {
                setFooterText(REFRESH_FOOTER_NOTHING);
            } else {
                setFooterText(REFRESH_FOOTER_PULLING);

            }
        }
        return true;

    }

//    @Override
//    public boolean setNoMoreData(boolean b) {
//                setFooterText("没有更多了");
//        return b;
//    }
    @NonNull
    @Override
    public View getView() {
        return this;
    }

    @NonNull
    @Override
    public SpinnerStyle getSpinnerStyle() {
        return SpinnerStyle.Translate;
    }

    @Override
    public void setPrimaryColors(@ColorInt int... ints) {
        setBackgroundColor(Color.parseColor("#f4f4f4"));
    }

    @Override
    public void onInitialized(@NonNull RefreshKernel refreshKernel, int i, int i1) {
    }

    @Override
    public void onMoving(boolean b, float v, int i, int i1, int i2) {
    }

    @Override
    public void onReleased(@NonNull RefreshLayout refreshLayout, int i, int i1) {
    }

    @Override
    public void onStartAnimator(@NonNull RefreshLayout refreshLayout, int i, int i1) {

    }

    @Override
    public int onFinish(@NonNull RefreshLayout refreshLayout, boolean success) {
        if (!mNoMoreData) {
            setFooterText(success ? REFRESH_FOOTER_FINISH : REFRESH_FOOTER_FAILED);
        }
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
        if (!mNoMoreData) {
            switch (newState) {
                case PullUpToLoad:
                    setFooterText(REFRESH_FOOTER_PULLING);
                case Loading:
                case LoadReleased:
                case ReleaseToLoad:
                    setFooterText("正在加载...");
                    break;
                case LoadFinish:
                    setFooterText("加载更多");
                    break;
                default:
                    break;
            }

        }

    }
}
