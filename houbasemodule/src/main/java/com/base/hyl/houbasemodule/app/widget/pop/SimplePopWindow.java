//package com.base.hyl.houbasemodule.app.widget.pop;
//
//import android.content.Context;
//import android.graphics.drawable.ColorDrawable;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.view.Gravity;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.PopupWindow;
//import android.widget.TextView;
//
//
//
//import com.base.hyl.houbasemodule.utils.DensityUtil;
//
//import java.util.List;
//import com.base.hyl.houbasemodule.R;
//import android.view.ViewGroup.LayoutParams;
//
///**
// * Created by 41113 on 2018/7/5.
// */
//
//public class SimplePopWindow<T> extends PopupWindow {
//
//
//    private Context context;
//    private LayoutInflater inflater;
//    private List<T> list;
//    private RecyclerView recy;
//    private OnItemClickListener myOnItemClickListener;
//
//    public SimplePopWindow(Context context, List<T> list) {
//        this.context = context;
//        this.list = list;
//        inflater = LayoutInflater.from(context);
//        initView();
//    }
//
//    private void initView() {
//
//        View conentView = inflater.inflate(R.layout.basemodule_simple_popup_window, null);
//        recy = ((RecyclerView) conentView.findViewById(R.id.recy_base_simple_pop_window));
//        recy.setLayoutManager(new LinearLayoutManager(context));
//        recy.setAdapter(new SimpleAdapter(context, list));
//
////        DisplayMetrics dm = new DisplayMetrics();
////        dm = context.getApplicationContext().getResources().getDisplayMetrics();
////        int screenWidth = dm.widthPixels;
////        int screenHeight = dm.heightPixels;
//
//        // 设置PopupWindow的View
//        this.setContentView(conentView);
//        // 设置PopupWindow弹出窗体的宽
//        this.setWidth(LayoutParams.WRAP_CONTENT);
//        // 设置PopupWindow弹出窗体的高
//        this.setHeight(LayoutParams.WRAP_CONTENT);
//        // 设置PopupWindow弹出窗体可点击
//        this.setFocusable(true);
//        this.setOutsideTouchable(true);
//        // 刷新状态
//        this.update();
//        // 实例化一个ColorDrawable颜色为半透明
//        ColorDrawable dw = new ColorDrawable(00000000);
//        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
//        this.setBackgroundDrawable(dw);
//        // mPopupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
//        // 设置SelectPicPopupWindow弹出窗体动画效果
//        this.setAnimationStyle(R.style.TRM_ANIM_STYLE);
//
//    }
//
//
//    /**
//     * 显示popupWindow
//     *
//     * @param parent
//     */
//    public void showPopupWindow(View parent) {
//        if (!this.isShowing()) {
//            // 以下拉方式显示popupwindow
//            this.showAtLocation(parent, Gravity.CENTER, 0, 0);
//        } else {
//            this.dismiss();
//        }
//    }
//
//
//    /**
//     * 显示popupWindow
//     *
//     * @param parent
//     */
//    public void showPopAsDropDown(View parent) {
//        if (!this.isShowing()) {
//            // 以下拉方式显示popupwindow
//            this.setWidth(parent.getWidth());
//            /*设置最大高度 4 个条目 高度*/
//            if (list.size() > 4) {
//                int height = 4 * (DensityUtil.dip2px(context, 40f));  //原
//                this.setHeight(height);
//            }
//
//            this.showAsDropDown(parent,DensityUtil.dp2px(context,10),0);
//        } else {
//            this.dismiss();
//        }
//    }
//
//
//    public  class SimpleAdapter<T> extends RecyclerView.Adapter<SimpleAdapter.MyViewHolder> {
//        private List<T> list;
//        private Context mContext;
//
//
//        public SimpleAdapter(Context context, List<T> list) {
//            this.mContext = context;
//            this.list = list;
//
//        }
//
//        @Override
//        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//
//            return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.basemodule_simple_popup_window_item, parent, false));
//        }
//
//
//
//        @Override
//        public void onBindViewHolder(MyViewHolder holder, final int position) {
//
//
//            if (myOnItemClickListener != null) {
//                myOnItemClickListener.setData(list.get(position), holder.tv, holder.iv);
//            }
//
//            /*设置条目点击事件*/
//            holder.itemView.setClickable(true);
//            holder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    dismiss();
//                    if (myOnItemClickListener != null) {
//                        myOnItemClickListener.onItemClick(v, position);
//                    }
//                }
//
//
//            });
//
//        }
//
//
//        @Override
//        public int getItemCount() {
//            return list == null ? 0 : list.size();
//        }
//
//        public class MyViewHolder extends RecyclerView.ViewHolder {
//
//            TextView tv;
//            ImageView iv;
//
//            public MyViewHolder(View itemView) {
//                super(itemView);
//                iv = (ImageView) itemView.findViewById(R.id.iv_base_simple_item);
//                tv = (TextView) itemView.findViewById(R.id.tv_base_simple_item);
//
//
//            }
//        }
//    }
//
//    public void setOnItemClick(OnItemClickListener<T> myOnItemClickListener) {
//        this.myOnItemClickListener = myOnItemClickListener;
//
//    }
//
//
//    public interface OnItemClickListener<T> {
//
//        void onItemClick(View view, int position);
//
//        void setData(T t, TextView tv, ImageView iv);
//    }
//
//
//}
