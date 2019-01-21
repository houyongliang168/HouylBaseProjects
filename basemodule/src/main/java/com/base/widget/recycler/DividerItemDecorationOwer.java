package com.base.widget.recycler;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by houyongliang on 2018/5/18 10:36.
 * Function(功能):
 */

public class DividerItemDecorationOwer extends RecyclerView.ItemDecoration {
    public static final int HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL;

    public static final int VERTICAL_LIST = LinearLayoutManager.VERTICAL;

    private Drawable mDivider;

    private int mOrientation;

    private Context mContext;



    private boolean isDrawBorderLine = false;//默认不绘制下边界线

    public DividerItemDecorationOwer(Context context, @NonNull Drawable drawable, int orientation) {
        mContext = context;
        mDivider = drawable;
        setOrientation(orientation);
    }

    /*通过 R.id.drawable 获取 drawable */
    public DividerItemDecorationOwer(Context context, @DrawableRes int drawableId, int orientation) {
//        mDivider = context.getResources().getDrawable(drawableId); //两种方式 ，第一种方式
        mDivider = ContextCompat.getDrawable(context, drawableId);//第二种方式
        setOrientation(orientation);
    }

    /*设置 方向*/
    public void setOrientation(int orientation) {
        if (orientation != HORIZONTAL_LIST && orientation != VERTICAL_LIST) {
            throw new IllegalArgumentException("invalid orientation");
        }
        mOrientation = orientation;
    }

/*  说明：
    onDraw方法先于drawChildren
    onDrawOver在drawChildren之后，一般我们选择复写其中一个即可。
    getItemOffsets 可以通过outRect.set()为每个Item设置一定的偏移量，主要用于绘制Decorator。*/

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (mOrientation == VERTICAL_LIST) {
            drawVertical(c, parent);
        } else {
            drawHorizontal(c, parent);
        }
    }

    public void drawVertical(Canvas c, RecyclerView parent) {
        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            RecyclerView v = new RecyclerView(parent.getContext());
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int top, bottom;
            if (i == childCount - 1 &&! isDrawBorderLine) {
//                top = child.getBottom() + params.bottomMargin; //top 是到顶点的距离
//                bottom = 0;
               continue;
            } else {
                top = child.getBottom() + params.bottomMargin;
                bottom = top + mDivider.getIntrinsicHeight();
            }
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    public void drawHorizontal(Canvas c, RecyclerView parent) {
        final int top = parent.getPaddingTop();
        final int bottom = parent.getHeight() - parent.getPaddingBottom();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int left;
            final int right;
            if (i == childCount - 1 &&! isDrawBorderLine) {
//                top = child.getBottom() + params.bottomMargin; //top 是到顶点的距离
//                bottom = 0;
                continue;
            } else {
                left =  child.getRight() + params.rightMargin;
                right = left + mDivider.getIntrinsicHeight();
            }
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
//        if (mOrientation == VERTICAL_LIST) {
//            outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
//        } else {
//            outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);
//        }
        int left = 0, right = 0, top = 0, bottom = 0;
        int childCount = parent.getAdapter().getItemCount();
        int itemPosition = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
        if (((LinearLayoutManager) parent.getLayoutManager()).getOrientation() == LinearLayoutManager.VERTICAL) {
            bottom = mDivider.getIntrinsicHeight();
//            if(itemPosition==0&&isDrawBorderLine){
//                //第一个item，需要绘制就在顶部留出偏移
//                top=mDivider.getIntrinsicHeight();
//            }
            if (itemPosition == childCount - 1 && !isDrawBorderLine) {
                //最后一个item，需要绘制则留出偏移，默认留出，不需要则设置bottom为0
                bottom = 0;
            }
        } else {
            right = mDivider.getIntrinsicHeight();
//            if(itemPosition==0&&isDrawBorderLine){
//                //第一个item，需要绘制就在左边留出偏移
//                left=mDivider.getIntrinsicHeight();
//            }
            if (itemPosition == childCount - 1 && !isDrawBorderLine) {
                //最后一个item，需要绘制则留出偏移，默认留出，不需要则设置right为0
                right = 0;
            }
        }
        outRect.set(left, top, right, bottom);
    }
    public boolean isDrawBorderLine() {
        return isDrawBorderLine;
    }

    public void setDrawBorderLine(boolean drawBorderLine) {
        isDrawBorderLine = drawBorderLine;
    }
}
