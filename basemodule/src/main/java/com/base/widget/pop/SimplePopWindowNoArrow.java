package com.base.widget.pop;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;


import com.base.common.utils.ScreenUtils;
import com.base.common.windows.DensityUtil;
import com.base.hyl.houbasemodule.R;

import java.util.List;

/**
 * Created by 41113 on 2018/7/5.
 */

public class SimplePopWindowNoArrow<T> extends PopupWindow {


    private Context context;
    private LayoutInflater inflater;
    private List<T> list;
    private RecyclerView recy;
    private FrameLayout fl, fl2;
    private OnItemClickListener myOnItemClickListener;

    public SimplePopWindowNoArrow(Context context, List<T> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
        initView();
    }

    private void initView() {

        View conentView = inflater.inflate(R.layout.basemodule_simple_popup_window_no_arrow, null);
        recy = ((RecyclerView) conentView.findViewById(R.id.recy_base_simple_pop_window));
        fl = ((FrameLayout) conentView.findViewById(R.id.fl_base_simple_pop_window));
        fl2 = ((FrameLayout) conentView.findViewById(R.id.fl_base_simple_pop_window_2));
        recy.setLayoutManager(new LinearLayoutManager(context));
        recy.setAdapter(new SimpleAdapter(context, list));
        fl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        fl2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        // 设置此参数获得焦点，否则无法点击
        // 设置PopupWindow的View
        this.setContentView(conentView);
        // 设置PopupWindow弹出窗体的宽
        this.setWidth(LayoutParams.MATCH_PARENT);

        // 设置PopupWindow弹出窗体的高
        this.setHeight(LayoutParams.MATCH_PARENT);
        // 设置PopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        // 刷新状态
//        this.update();
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(00000000);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        this.setBackgroundDrawable(dw);
        // mPopupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.TRM_ANIM_STYLE);

    }


    /**
     * 显示popupWindow
     *
     * @param parent
     */
    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            // 以下拉方式显示popupwindow
            this.showAtLocation(parent, Gravity.CENTER, 0, 0);
        } else {
            this.dismiss();
        }
    }


    /**
     * 显示popupWindow
     *
     * @param parent
     */
    public void showPopAsDropDown(View parent) {
        if (!this.isShowing()) {
            // 以下拉方式显示popupwindow
            if (Build.VERSION.SDK_INT < 24) {
                this.showAsDropDown(parent, DensityUtil.dp2px(context, 10), 0);
            } else {
                // 获取控件的位置，安卓系统>7.0
                int[] location = new int[2];
                parent.getLocationOnScreen(location);
                int offsetY = location[1] + parent.getHeight();
                if (Build.VERSION.SDK_INT >= 25) { // Android 7.1中，PopupWindow高度为 match_parent 时，会占据整个屏幕
                    // 故而需要在 Android 7.1上再做特殊处理
                    int screenHeight = ScreenUtils.getScreenHeight(context); // 获取屏幕高度
                    setHeight(screenHeight - offsetY); // 重新设置 PopupWindow 的高度
                }
                showAtLocation(parent, Gravity.NO_GRAVITY, 0, offsetY);
            }

        } else {
            this.dismiss();
        }
    }


    public class SimpleAdapter<T> extends RecyclerView.Adapter<SimpleAdapter.ViewHolder> {
        private List<T> list;
        private Context mContext;


        public SimpleAdapter(Context context, List<T> list) {
            this.mContext = context;
            this.list = list;

        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.basemodule_simple_popup_window_item, parent, false));
        }

        @Override
        public void onBindViewHolder(SimpleAdapter.ViewHolder holder, final int position) {


            if (myOnItemClickListener != null) {
                myOnItemClickListener.setData(list.get(position), holder.tv, holder.iv);
            }

            /*设置条目点击事件*/
            holder.itemView.setClickable(true);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                    if (myOnItemClickListener != null) {
                        myOnItemClickListener.onItemClick(v, position);
                    }
                }


            });

        }


        @Override
        public int getItemCount() {
            return list == null ? 0 : list.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView tv;
            ImageView iv;

            public ViewHolder(View itemView) {
                super(itemView);
                iv = (ImageView) itemView.findViewById(R.id.iv_base_simple_item);
                tv = (TextView) itemView.findViewById(R.id.tv_base_simple_item);


            }
        }
    }

    public void setOnItemClick(OnItemClickListener<T> myOnItemClickListener) {
        this.myOnItemClickListener = myOnItemClickListener;

    }


    public interface OnItemClickListener<T> {

        void onItemClick(View view, int position);

        void setData(T t, TextView tv, ImageView iv);
    }


}
