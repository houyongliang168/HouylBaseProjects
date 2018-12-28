package com.base.hyl.houbasemodule.sdutils;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;

/**
 * Created by houyongliang on 2017/6/2.
 */

public class Tools {
    public static int String2Int(String str) {
        try {
            int a = Integer.parseInt(str);
            return a;
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        return 0;
    }
    ///*键盘显示*/
    public void addLayoutListener(final View main, final View scroll) {
        main.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
            /*1.获取main在窗体的可视区域*/
                main.getWindowVisibleDisplayFrame(rect);
            /*2. 获取 main在窗体的不可视区域高度，在键盘没有弹起时*/
            /*main.getRootView.getHeight 调节高度和recrt.bottom 高度一样*/
                int mainInvisibleHeight = main.getRootView().getHeight() - rect.bottom;
            /*3 不可见区域大于100 说明键盘弹起*/
                if (mainInvisibleHeight > 100) {
                    int[] location = new int[2];
                    scroll.getLocationInWindow(location);
                /*4 获取scroll 的窗体坐标，算出main 需要滚动的高度*/
                    int srollHeight = (location[1] + scroll.getHeight()) - rect.bottom;
                /*5. 让界面整体上移键盘的高度*/
                    main.scrollTo(0, srollHeight);
                } else {
                /*3. 不可见区域小于100，说明键盘隐藏了，把界面下移动，移回原有高度*/
                    main.scrollTo(0, 0);
                }
            }
        });
    }
}
