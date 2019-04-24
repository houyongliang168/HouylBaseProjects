package com.base.hyl.houylbaseprojects.入口;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.base.hyl.houylbaseprojects.R;
import com.base.widget.viewpager.ControlScrolliewPager;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainEntranceActivity extends AppCompatActivity {
    private List<Fragment> mFragments;
    private MeEntryFragment meEntryFrag;
    private ControlScrolliewPager  vp_control_srcoll;
    private SlidingTabLayout tab;
    private String[] mTitles;
    private FloatingActionButton fab;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_entrance);
       
        initView();
        initEvent();
        initData();
        vp_control_srcoll.setAdapter(new PagerAdapter(getSupportFragmentManager()));
        tab.setViewPager(vp_control_srcoll);
        
    }

    private void initEvent() {
        vp_control_srcoll.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {



            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        vp_control_srcoll=  findViewById(R.id.vp_control_srcoll);
        tab=findViewById(R.id.tab);
        fab = findViewById(R.id.fab);
        vp_control_srcoll.setOffscreenPageLimit(3);
       
       

    }

    private void initData() {
        mTitles= new String[]{"我的入口1"};
        mFragments = new ArrayList<>();
        meEntryFrag = new MeEntryFragment();
        mFragments.add(meEntryFrag);


    }
    class PagerAdapter extends FragmentPagerAdapter {

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mTitles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }
    }

}
