package com.base.hyl.houylbaseprojects;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.base.common.log.MyLog;
import com.base.hyl.houylbaseprojects.adapter.MyTestRecordRecyViewAdapter;

import java.util.ArrayList;
import java.util.List;



public class ScrollingActivity extends AppCompatActivity {
    List<String> list=new ArrayList<>();
    private Toolbar toolbar;
    private TextView tv;
    private AppBarLayout app_bar;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private ImageView imageView4;
    private ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling2);
        //处理数据
        list.add("12313456");
        list.add("12313456");
        list.add("12313456");
        list.add("12313456");
        list.add("12313456");
        list.add("12313456");
        list.add("12313456");
        list.add("12313456");
        toolbar = findViewById(R.id.toolbar);
        tv = findViewById(R.id.tv);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        app_bar = findViewById(R.id.app_bar);
       imageView4 = findViewById(R.id.imageView4);
        constraintLayout = findViewById(R.id.constraintLayout);

      findViewById(R.id.image).setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Toast.makeText(ScrollingActivity.this,"dfadfadf",Toast.LENGTH_SHORT).show();
          }
      });
        collapsingToolbarLayout = findViewById(R.id.collapsing_layout);

        RecyclerView recy =  findViewById(R.id.recy);
      
        recy.setLayoutManager(new LinearLayoutManager(this));
        MyTestRecordRecyViewAdapter adapter=new MyTestRecordRecyViewAdapter(this,list);
        recy.setAdapter(adapter);

        initListener();

    }
    private CollapsingToolbarLayoutState state;
    private void initListener() {

        app_bar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                //垂直方向偏移量
                int offset = Math.abs(verticalOffset);
                //最大偏移距离

                int scrollRange = appBarLayout.getTotalScrollRange();
                int ht=imageView4.getHeight();
                int ht_cl=constraintLayout.getHeight();
//                if(offset==0||offset<=1){//初始的时候
//                    ViewCompat.setScaleY(imageView4, 1);
//                }else if(offset==scrollRange){//顶部的时候
//                    ViewCompat.setScaleY(imageView4, 0);
//                }else if(offset<scrollRange){
                    MyLog.wtf("verticalOffset :",verticalOffset+"");
                    MyLog.wtf("offset :",offset+"");
                    MyLog.wtf("scrollRange :",scrollRange+"");
//                    //  MyLog.wtf("f :",f+"");
////                    ObjectAnimator animator = ObjectAnimator.ofFloat(imageView4, "scaleY", 1f, f);
////                    // 表示的是:
////                    // 动画作用对象是mButton
////                    // 动画作用的对象的属性是X轴缩放
////                    // 动画效果是:放大到3倍,再缩小到初始大小
////                    animator.setDuration(5000);
////                    animator.start();
////                    //计算偏移量
////                    float distance = y - lastY;
////                    //计算出要缩放的scale
////                    mScale = 1 + distance * scaleRatio / getHeight();
////                    //设置缩放的锚点
////                    setPivotY(0f);
////                    setPivotX(getWidth() / 2);
//                    //进行缩放
////                  ViewCompat.setScaleY(imageView4, offset);
//                    int ht=imageView4.getHeight();
//                    MyLog.wtf("ht :",ht+"");
//
//                    if(ht-offset>0){
//                        ViewCompat.animate(imageView4) //实现动画的操作
//                                .setDuration(200)
//                                .scaleY(0.2f)
//                                .start();
//                    }else{
//                        ViewCompat.setScaleY(imageView4, 0);
//                    }
//
//                }else{
//                    ViewCompat.setScaleY(imageView4, 1);
//                }


                //当滑动没超过一半，展开状态下 toolbar 显示内容，更具收缩位置，改变透明值
//                if (offset <= ht/5 ) {
//                    //根据偏移百分比 计算透明值
//                    ViewCompat.setScaleY(constraintLayout, 1);
//
//                }else
                    if(offset<=ht){

                  //  double  scale=   offset/ht*100;
                    double  scale=  1-(offset) /(double)ht;

                    ViewCompat.setScaleY(constraintLayout, (float) scale);
                    MyLog.wtf("scale offset<=ht/2:",scale+"");
                }else if(offset<=scrollRange){
                    ViewCompat.setScaleY(constraintLayout, 0);
                }
                //当滑动超过一半，收缩状态下 toolbar 显示内容，根据收缩位置，改变透明值
                else {
                    ViewCompat.setScaleY(constraintLayout, 0);

                }
//                double  scale_cl=  1-(offset) /(double)ht_cl;
//
//                ViewCompat.setScaleY(constraintLayout, (float) scale_cl);


                if (verticalOffset == 0) {
                    if (state != CollapsingToolbarLayoutState.EXPANDED) {
                        state = CollapsingToolbarLayoutState.EXPANDED;//修改状态标记为展开
                      //  collapsingToolbarLayout.setTitle("EXPANDED");//设置title为EXPANDED
                    }
                } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
                    if (state != CollapsingToolbarLayoutState.COLLAPSED) {
                        collapsingToolbarLayout.setTitle("");//设置title不显示
                        tv.setVisibility(View.VISIBLE);//隐藏播放按钮
                        state = CollapsingToolbarLayoutState.COLLAPSED;//修改状态标记为折叠
                    }
                } else {
                    if (state != CollapsingToolbarLayoutState.INTERNEDIATE) {
                        if(state == CollapsingToolbarLayoutState.COLLAPSED){
                            tv.setVisibility(View.GONE);//由折叠变为中间状态时隐藏播放按钮
                        }
                      //  collapsingToolbarLayout.setTitle("INTERNEDIATE");//设置title为INTERNEDIATE
                        state = CollapsingToolbarLayoutState.INTERNEDIATE;//修改状态标记为中间
                    }
                }
            }
        });
    }

    public void scale(View view){
        ObjectAnimator animator = ObjectAnimator.ofFloat(view,"scaleY",0f,1f);
        animator.setDuration(800);
        animator.start();
    }

    private enum CollapsingToolbarLayoutState {
        EXPANDED,
        COLLAPSED,
        INTERNEDIATE
    }

    int mOffsetTop=0;
    int mLayoutTop=0;
    private void updateOffsets(int verticalOffset,View view) {

       // view.setScaleY(-verticalOffset);
//     int d= (int) Math.round(-verticalOffset * 0.7);
//        if(mOffsetTop!=d){
//            mOffsetTop=d;
//            ViewCompat.offsetTopAndBottom(view, mOffsetTop - (view.getTop() - mLayoutTop));
//        }
//
//
    }
}
