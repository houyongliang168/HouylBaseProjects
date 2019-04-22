package com.base.hyl.houylbaseprojects;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

public class TagDecoration extends RecyclerView.ItemDecoration {
    private Context mContext;
    private    int otherHeight;//定义其他总高度
    private View type3;
    private int surplusHeight;
    private Paint dividerPaint;
    private Paint linePaint;
    private View view_up;
    private int width;
    private float locationX,locationY;
    int endGuji=0;
    public TagDecoration(Context context) {
       initView(  context);
    }

    private void initView(Context context) {

        mContext = context;
        dividerPaint = new Paint();
        linePaint = new Paint();

        type3 =  LayoutInflater.from(mContext).inflate(R.layout.item_add_staff_time_three, null);

    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int childPosition = parent.getChildAdapterPosition(view);
        int itemCount = parent.getAdapter().getItemCount();
        if(childPosition==itemCount-1){//最后一个条目

            int childCount = parent.getChildCount();
            int allHeight=parent.getHeight();//获取总高度
            int otherHeight=0;//剩余高度
            surplusHeight = 0;
            for (int i = 0; i < childCount; i++) {
                View child = parent.getChildAt(i);
                otherHeight+=child.getHeight();//获取其所有高度
                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams)child.getLayoutParams();
            
                
                if(i==childCount-1){//最后一个进行比较
                    if(otherHeight>=allHeight){//大于总高度则不处理
                        surplusHeight=0;
                    }else{
                        surplusHeight = allHeight-otherHeight;//拿到剩余的高度
                        outRect.bottom = surplusHeight;
                    }
                }
            }
   
        }
        
        
        /* 给定一个 固定的空间大小*/
      
    }


    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);

        if(surplusHeight>0){//有间距的时候处理
            int childCount = parent.getChildCount();//获取总的条目数量
            for (int i = 0; i < childCount; i++) {
                if (i == childCount - 1) {
                    View child = parent.getChildAt(i);
                    dividerPaint.setColor(mContext.getResources().getColor(R.color.colorff5722));
                    c.drawLine(60, 0, 80, child.getBottom() + surplusHeight, dividerPaint);
                }
            }
        }
//        int itemCount = parent.getAdapter().getItemCount();
//        /* 给定一个 固定的空间大小*/
//       
//        int allHeight=parent.getHeight();//获取总高度
//        int otherHeight=0;//剩余高度
//        surplusHeight = 0;
//        for (int i = 0; i < childCount; i++) {
//            View child = parent.getChildAt(i);
//            int pos = parent.getChildAdapterPosition(child);
//            otherHeight+=child.getHeight();//获取其所有高度
//            if(i==childCount-1){//最后一个进行比较
//
//
//
//                if(otherHeight>=allHeight){//大于总高度则不处理
//                    surplusHeight=0;
//                }else{
//                    float top = child.getBottom();
//                int left = parent.getPaddingLeft();
//                int right = parent.getPaddingRight();
//                float bottom = child.getBottom() + surplusHeight;
//                    
//                    surplusHeight = allHeight-otherHeight;//拿到剩余的高度
//                
//                  
//
//                }
//            }
//        }
        
        
    }


    
}
