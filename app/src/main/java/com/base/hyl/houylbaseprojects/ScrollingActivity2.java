package com.base.hyl.houylbaseprojects;

import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.base.common.log.MyLog;
import com.base.common.utils.ScreenUtils;

import java.net.URLEncoder;

public class ScrollingActivity2 extends AppCompatActivity implements View.OnClickListener {
    private String TAG="=====";
    private ConstraintLayout cl_main_2,cl_main_1;
    private NestedScrollView nestedScrollView_fresh_man;
    private FrameLayout fl_container;
    private ImageView iv_exit,iv_setting;
    private int  heightMost=0;
    private MyLinearLayout myLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling3);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initView();
        initListener();
     //   URLEncoder.encode("dsaf","utf-8");

    }


    private void initListener() {
        nestedScrollView_fresh_man.post(new Runnable() {
            @Override
            public void run() {
                nestedScrollView_fresh_man.scrollTo(0,0);
            }
        });

        nestedScrollView_fresh_man.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                Log.e(TAG, "scrollY :"+scrollY);
                Log.e(TAG, "oldScrollY :"+oldScrollY);
                if (scrollY > oldScrollY) {
                    Log.e(TAG, "下滑");
                }
                if (scrollY < oldScrollY) {
                    Log.e(TAG, "上滑");
                }
                if (scrollY == 0) {
                    Log.e(TAG, "滑倒顶部");
                }
                if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                    Log.e(TAG, "滑倒底部");
                }
//
//                int offset = Math.abs(scrollY);
//                //最大偏移距离
//                int scrollRange = heightMost;
//                int ht= cl_main_2.getHeight();
//
//                MyLog.wtf("verticalOffset :",offset+"");
//                MyLog.wtf("offset :",offset+"");
//                MyLog.wtf("scrollRange :",scrollRange+"");
//
//
//                if(offset<=ht){//小于伸缩控件的高度
//                    //  double  scale=   offset/ht*100;
//                    double  scale=  1-(offset) /(double)ht;
////                    cl_main_2.setPivotY(y) ;
////                    cl_main_2.setPivotX(x) ;
//                    ViewCompat.setScaleY(cl_main_2, (float) scale);
//                    MyLog.wtf("scale offset<=ht:",scale+"");
//                }else if(offset<=scrollRange){
//                    ViewCompat.setScaleY(cl_main_2, 0);
//                }else {
//                    ViewCompat.setScaleY(cl_main_2, 0);
//                }





            }
        });

    }

    private void initView() {
        cl_main_2 = findViewById(R.id.cl_main_2);
        cl_main_1 = findViewById(R.id.cl_main_1);
        myLinearLayout = findViewById(R.id.MyLinearLayout);
        fl_container = findViewById(R.id.fl_container);
        iv_exit = findViewById(R.id.iv_exit);
        iv_setting = findViewById(R.id.iv_setting);
        iv_exit.setOnClickListener(this);
        nestedScrollView_fresh_man = findViewById(R.id.nestedScrollView_fresh_man);
        int sw = ScreenUtils.getScreenWidth(this);
        int sh = ScreenUtils.getScreenHeight(this);
        ViewGroup.LayoutParams cl_mainLP = cl_main_1.getLayoutParams();
        cl_mainLP.width = sw;
        cl_mainLP.height = sw * 328 / 720;
        heightMost=sw * 328 / 720;
        ViewGroup.LayoutParams fl_containerLP = fl_container.getLayoutParams();
        fl_containerLP.width = sw;
        fl_containerLP.height = sh;


    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_exit:

                if(nestedScrollView_fresh_man!=null){
                    nestedScrollView_fresh_man.scrollTo(0,0);
                }

                break;
            case R.id.iv_setting:


                break;
        }

    }
}
