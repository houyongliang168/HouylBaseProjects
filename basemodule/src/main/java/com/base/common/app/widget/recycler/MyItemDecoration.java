package com.base.common.app.widget.recycler;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by houyongliang on 2018/5/10 11:23.
 * Function(功能):
 */

public class MyItemDecoration extends RecyclerView.ItemDecoration {
    private int space;

    public MyItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left = space;
        outRect.right = space;
//      outRect.bottom = space;//条目下间距
        outRect.top = space;//条目 上间距

    }


}
