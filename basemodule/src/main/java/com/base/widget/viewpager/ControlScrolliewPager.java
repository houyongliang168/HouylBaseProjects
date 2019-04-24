package com.base.widget.viewpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 *是否可以滑动的 viewpager
 */

public class ControlScrolliewPager extends ViewPager {
    private boolean isScroll=true;
    public ControlScrolliewPager(Context context) {
        super(context);
    }

    public ControlScrolliewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    /**
     * @param isScroll 是否滑动（true 滑动，false 禁止）
     */
    public void setScroll(boolean isScroll){
        this.isScroll=isScroll;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (isScroll) {
            return super.onTouchEvent(ev);
        } else {
            return false;
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (isScroll) {
            return super.onInterceptTouchEvent(ev);
        } else {
            return false;
        }
    }
}
