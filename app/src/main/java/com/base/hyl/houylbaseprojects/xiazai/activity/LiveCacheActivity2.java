package com.base.hyl.houylbaseprojects.xiazai.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.base.common.base.BaseActivity;
import com.base.hyl.houylbaseprojects.R;
import com.base.hyl.houylbaseprojects.xiazai.adpter.CustomDetailsFragmentAdapter;
import com.base.hyl.houylbaseprojects.xiazai.fragment.LiveCacheFragment;
import com.base.hyl.houylbaseprojects.xiazai.fragment.LiveCacheFragment2;
import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.downlaod.DownInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zcc on 2018/1/12.
 * 视频缓存
 */
public class LiveCacheActivity2 extends BaseActivity {
    private static final String INTENT_DATA = "downInfo";
    private View iv_cache_back;
    private TextView tv_cache_edit;
    private SlidingTabLayout tab_cache;
    private ViewPager pager_cache;
    private List<String> listTitles = new ArrayList<>();
    private boolean mIsEdit = false;
    private static int fragmentPosition = 0;
    private DownInfo mDownInfo = null;
    private List<Fragment> listFragment=new ArrayList<>();
    private TextView tv_add;
    private CustomDetailsFragmentAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.live_activity_cache);

        initView();
        initData();
    }

    private void initData() {
        listFragment.add(LiveCacheFragment.getInstance(0+"", mIsEdit));
        listFragment.add(LiveCacheFragment.getInstance(1+"", mIsEdit));
        listTitles.add("已缓存");
        listTitles.add("缓存中");
        adapter = new CustomDetailsFragmentAdapter(getSupportFragmentManager(), listFragment, listTitles);
        pager_cache.setAdapter(adapter);
        pager_cache.setOffscreenPageLimit(2);
        tab_cache.setViewPager(pager_cache);
        pager_cache.setCurrentItem(0);
        /*监听 tab 选择*/
        tab_cache.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {    }

            @Override
            public void onTabReselect(int position) { }
        });

    }

    private void initView() {
        iv_cache_back = findViewById(R.id.iv_cache_back);
        iv_cache_back.setOnClickListener(mOnClickListener);
        tv_cache_edit = (TextView) findViewById(R.id.tv_cache_edit);
        tv_cache_edit.setOnClickListener(mOnClickListener);
        tab_cache = (SlidingTabLayout) findViewById(R.id.tab_cache);
        pager_cache = (ViewPager) findViewById(R.id.pager_cache);
        tv_add = findViewById(R.id.tv_add);
        tv_add.setOnClickListener(mOnClickListener);

    }


    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == iv_cache_back) {
                if (mIsEdit) {
                    setEditFalse();
                } else {
                    finish();
                }
            } else if (v == tv_cache_edit) {
                String cacheEditStr = tv_cache_edit.getText().toString();
                if (cacheEditStr.equals("编辑")) {
                    mIsEdit = true;
                    tv_cache_edit.setText("完成");
                } else {
                    mIsEdit = false;
                    tv_cache_edit.setText("编辑");
                }
                if (adapter != null) {
                    adapter.notifyDataSetChanged();
                }
            }else if(v ==tv_add){//添加数据



            }
        }
    };

    @Override
    public void onBackPressed() {
        if (mIsEdit) {
            setEditFalse();
        } else {
            finish();
        }
    }

    /**
     * 设置编辑状态
     */
    public void setEditFalse() {
        mIsEdit = false;
        tv_cache_edit.setText("编辑");
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }


}
