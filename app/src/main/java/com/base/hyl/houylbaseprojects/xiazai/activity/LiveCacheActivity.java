package com.base.hyl.houylbaseprojects.xiazai.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.base.common.base.BaseActivity;
import com.base.hyl.houylbaseprojects.R;
import com.base.hyl.houylbaseprojects.xiazai.fragment.LiveCacheFragment;
import com.flyco.tablayout.SlidingTabLayout;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.downlaod.DownInfo;

/**
 * Created by zcc on 2018/1/12.
 * 视频缓存
 */
public class LiveCacheActivity extends BaseActivity {
    private static final String INTENT_DATA = "downInfo";
    private View iv_cache_back;
    private TextView tv_cache_edit;
    private SlidingTabLayout tab_cache;
    private ViewPager pager_cache;
    private String[] mTitles = new String[]{"已缓存", "缓存中"};
    private String[] mType = new String[]{"1", "2"};
    private boolean mIsEdit = false;
    private static int fragmentPosition = 0;
    private DownInfo mDownInfo = null;

    /**
     * 供视频播放页面跳转至下载
     *
     * @param context
     * @param info
     */
    public static void startLiveCacheActivity(Context context, DownInfo info) {
        Intent intent = new Intent(context, LiveCacheActivity.class);
        intent.putExtra(INTENT_DATA, info);
        fragmentPosition = 1;
        context.startActivity(intent);
    }

    /**
     * 仅跳转到下载页
     *
     * @param context
     */
    public static void startLiveCacheActivity(Context context) {
        Intent intent = new Intent(context, LiveCacheActivity.class);
        fragmentPosition = 0;
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.live_activity_cache);

        if (fragmentPosition == 1) {
            Intent intent = getIntent();
            mDownInfo = (DownInfo) intent.getSerializableExtra(INTENT_DATA);
        }
        initView();
    }

    private void initView() {
        iv_cache_back = findViewById(R.id.iv_cache_back);
        iv_cache_back.setOnClickListener(mOnClickListener);
        tv_cache_edit = (TextView) findViewById(R.id.tv_cache_edit);
        tv_cache_edit.setOnClickListener(mOnClickListener);
        tab_cache = (SlidingTabLayout) findViewById(R.id.tab_cache);
        pager_cache = (ViewPager) findViewById(R.id.pager_cache);
        pager_cache.setAdapter(new PagerAdapter(getSupportFragmentManager()));
        tab_cache.setViewPager(pager_cache);
        pager_cache.setCurrentItem(fragmentPosition);
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
                PagerAdapter adapter = (PagerAdapter) pager_cache.getAdapter();
                if (adapter != null) {
                    adapter.notifyDataSetChanged();
                }
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
        PagerAdapter adapter = (PagerAdapter) pager_cache.getAdapter();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    class PagerAdapter extends FragmentPagerAdapter {
        private LiveCacheFragment mFragment = null;

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            mFragment = (LiveCacheFragment) super.instantiateItem(container, position);
            mFragment.updateData(mType[position], mIsEdit);
            return mFragment;
        }

        @Override
        public int getItemPosition(Object object) {
            return PagerAdapter.POSITION_NONE;
        }

        @Override
        public Fragment getItem(int position) {
            if (mDownInfo == null) {
                mFragment = LiveCacheFragment.getInstance(mType[position], mIsEdit);
            } else {
                mFragment = LiveCacheFragment.getInstance(mType[position], mIsEdit, mDownInfo);
            }
            return mFragment;
        }

        @Override
        public int getCount() {
            return mTitles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        public LiveCacheFragment getFragment() {
            return mFragment;
        }
    }
}
