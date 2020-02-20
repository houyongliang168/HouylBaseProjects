package com.base.hyl.houylbaseprojects.download.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.base.common.base.CoreBaseActivity;
import com.base.hyl.houylbaseprojects.App;
import com.base.hyl.houylbaseprojects.R;
import com.base.hyl.houylbaseprojects.download.adpter.DownCacheFragmentAdapter;
import com.base.hyl.houylbaseprojects.download.bean.DownloadStatusChange;
import com.base.hyl.houylbaseprojects.download.contract.DownLoadPresenter;
import com.base.hyl.houylbaseprojects.download.contract.IDownLoadContract;
import com.base.hyl.houylbaseprojects.download.core.DownloadService;
import com.base.hyl.houylbaseprojects.download.core.callback.DownloadManager;
import com.base.hyl.houylbaseprojects.download.core.domain.DownloadInfo;
import com.base.hyl.houylbaseprojects.download.fragment.DownCachedFragment;
import com.base.hyl.houylbaseprojects.download.fragment.DownCacheingFragment;
import com.flyco.tablayout.SlidingTabLayout;


import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by zcc on 2018/1/12.
 * 视频缓存
 */
public class DownCacheActivity extends CoreBaseActivity<DownLoadPresenter> implements IDownLoadContract.IDownLoadView{
    private static final String INTENT_DATA = "downInfo";
    private static final String INTENT_POSSION = "POSSION";
    /* 控件*/
    private View iv_cache_back;
    private TextView tv_cache_edit;
    private SlidingTabLayout tab_cache;
    private ViewPager pager_cache;
    /* 其他属性*/
    private DownloadInfo mDownInfo = null;
    private String[] mTitles = new String[]{"已缓存", "缓存中"};
    private List<String> mTitlesList = new ArrayList<>();
    private List<Fragment> listFragment = new ArrayList<>();
    private boolean mIsEdit = false;
    private  int fragmentPosition = 0;
    private DownCacheFragmentAdapter adapter;
    private DownCachedFragment downCachedFragment;
    private DownCacheingFragment downCacheingFragment;
    private DownloadManager downloadManager;

    /**
     * 供视频播放页面跳转至下载
     *
     * @param context 上下文
     * @param info  传递的信息
     * @param possion 位置
     */
    public static void startDownCacheActivity(Context context, DownloadInfo info, int possion) {
        Intent intent = new Intent(context, DownCacheActivity.class);
        if(info!=null){
            intent.putExtra(INTENT_DATA, info);
        }
        intent.putExtra(INTENT_POSSION, possion);
        context.startActivity(intent);
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        getIntentData();
        iv_cache_back = findViewById(R.id.iv_cache_back);
        iv_cache_back.setOnClickListener(mOnClickListener);
        tv_cache_edit =  findViewById(R.id.tv_cache_edit);
        tv_cache_edit.setOnClickListener(mOnClickListener);
        tab_cache =  findViewById(R.id.tab_cache);
        pager_cache = findViewById(R.id.pager_cache);
        /* 获取两个Fragment 对象*/
        downCachedFragment = DownCachedFragment.getInstance();
        listFragment.add(downCachedFragment);
        downCacheingFragment = DownCacheingFragment.getInstance(false,mDownInfo);
        listFragment.add(downCacheingFragment);
        adapter = new DownCacheFragmentAdapter(getSupportFragmentManager(), listFragment, mTitlesList);
        pager_cache.setAdapter(adapter);
        tab_cache.setViewPager(pager_cache);
        pager_cache.setCurrentItem(fragmentPosition);
        /* 设置滑动监听*/
        pager_cache.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                fragmentPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void getIntentData() {//获取传递的数据
        Intent intent = getIntent();
        mDownInfo = (DownloadInfo) intent.getSerializableExtra(INTENT_DATA);
        fragmentPosition =  intent.getIntExtra(INTENT_POSSION,0);
        /* 获取基本的数据*/
        List<String> list = Arrays.asList(mTitles);
        mTitlesList.addAll(list);
        /* 初始化相关数据*/
        downloadManager = DownloadService.getDownloadManager(App.getContext());
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_live_cache;
    }




    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == iv_cache_back) {
                if (mIsEdit) {
                    setEditStatus(false);
                } else {
                    finish();
                }
            } else if (v == tv_cache_edit) {
                setEditStatus(!mIsEdit);
            }
        }
    };

    @Override
    public void onBackPressed() {
        if (mIsEdit) {
            setEditStatus(false);
        } else {
            finish();
        }
    }



    /**
     * 设置编辑状态
     */
    public void setEditStatus(boolean isEdit) {
        mIsEdit = isEdit;
        if(!isEdit){//如果为编辑
            tv_cache_edit.setText("编辑");
        }else{
            tv_cache_edit.setText("完成");
        }
        /* 设置监听*/
        if(listenerList!=null){
            for (int i = 0; i < listenerList.size(); i++) {
                listenerList.get(i).editStatusChangeListener(mIsEdit);
            }
        }
        if(adapter!=null){
            adapter.notifyDataSetChanged();
        }
    }
    /* 设置状态改变监听*/
    public interface  EditStatusChangeListener{
        void  editStatusChangeListener(boolean isEdit);
        void  downInfoStatusChangeListener();
    }
    private List<EditStatusChangeListener> listenerList=new ArrayList<>();
    /* 设置监听*/
    public void setEditStatusChangeListener( EditStatusChangeListener  listener){
        if(listenerList==null){
            return;
        }
        if(listenerList.contains(listener)){
            return;
        }
        listenerList.add(listener);
    }
    /* 移除监听*/
    public void removeEditStatusChangeListener( EditStatusChangeListener  listener){
        if(listenerList==null){
            return;
        }
        if(listenerList.contains(listener)){
            listenerList.remove(listener);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(listenerList!=null&&listenerList.size()>0){
            listenerList.clear();
            listenerList=null;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getStatus(DownloadStatusChange statusChange){
        /* 设置监听*/
        if(listenerList!=null){
            for (int i = 0; i < listenerList.size(); i++) {
                listenerList.get(i).downInfoStatusChangeListener();
            }
        }
    }

}
