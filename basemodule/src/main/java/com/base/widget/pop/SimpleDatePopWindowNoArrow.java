package com.base.widget.pop;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.IntDef;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;


import com.base.common.utils.ScreenUtils;
import com.base.common.windows.DensityUtil;
import com.base.hyl.houbasemodule.R;
import com.base.widget.time_picker.TimePickerView;
import com.base.widget.time_picker.listener.CustomListener;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by 41113 on 2018/7/5.
 */

public class SimpleDatePopWindowNoArrow extends PopupWindow implements View.OnClickListener {


    private Context context;
    private LayoutInflater inflater;
    private OnItemClickListener myOnItemClickListener;
    private TimePickerView timePicker;
    private TextView tv_startDate;
    private TextView tv_endDate;
    private LinearLayout ll_basemodule_dissmiss, ll_basemodule_bg;
    private TimePickerView.Builder builder;
    private boolean is_initialize_start = false;//是否初始化  默认为false
    private boolean is_initialize_end = false;//是否初始化  默认为false


    public SimpleDatePopWindowNoArrow(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        initView();
    }

    private void initView() {

        View conentView = inflater.inflate(R.layout.basemodule_simple_date_popup_window_no_arrow, null);
        FrameLayout fl_timepicker_container = (FrameLayout) conentView.findViewById(R.id.fl_basemodule_timepicker_container);
        initTimePicker(fl_timepicker_container);
        tv_startDate = (TextView) conentView.findViewById(R.id.tv_basemodule_startDate);
        tv_endDate = (TextView) conentView.findViewById(R.id.tv_basemodule_endDate);
        ll_basemodule_bg = (LinearLayout) conentView.findViewById(R.id.ll_basemodule_bg);//下面的背景


        TextView tv_reset = (TextView) conentView.findViewById(R.id.tv_basemodule_date_reset);
        TextView tv_confirm = (TextView) conentView.findViewById(R.id.tv_basemodule_date_confirm);

        tv_startDate.setOnClickListener(this);
        tv_endDate.setOnClickListener(this);
        tv_reset.setOnClickListener(this);
        tv_confirm.setOnClickListener(this);

        ll_basemodule_bg.setOnClickListener(this);//下面的背景
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
        this.update();
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


    private void initTimePicker(FrameLayout fl_timepicker_container) {
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.YEAR, -5);
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.YEAR, 5);
        //是否只显示中间选中项的label文字，false则每项item全部都带有label。
        builder = new TimePickerView.Builder(context, timeSelectListener);
        timePicker = builder
                .setLayoutRes(R.layout.basemodule_pickerview_owner_time, new CustomListener() {
                    @Override
                    public void customLayout(View v) {

//                        ll_basemodule_dissmiss = (LinearLayout) v.findViewById(R.id.ll_basemodule_dissmiss);
//                        ll_basemodule_dissmiss.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                dismiss();
//                            }
//                        });

                    }
                })
                .setType(new boolean[]{true, true, true, false, false, false})
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(Color.parseColor("#eeeeee"))
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setDecorView(fl_timepicker_container)
                .setContentSize(15)
                .gravity(Gravity.CENTER)
                .setLineSpacingMultiplier(2.25f)
                .setTextColorCenter(Color.parseColor("#ff5722"))
                .setOutSideCancelable(false)
                .setBackgroundId(0x00000000)
                .build();
        timePicker.setKeyBackCancelable(false);
        timePicker.show(false);

    }

    /*对外提供 修改 起始时间和 终止时间的方法*/
    public void setOwnRangDate(Calendar startDate, Calendar endDate) {
        if (builder != null) {
            builder.setRangDate(startDate, endDate);
        }

    }

    public void setOnItemClick(OnItemClickListener myOnItemClickListener) {
        this.myOnItemClickListener = myOnItemClickListener;

    }


    public static final int MIN_CLICK_DELAY_TIME = 1000;
    private long lastClickTime = 0;
    private Calendar today = Calendar.getInstance();

    @Override
    public void onClick(View v) {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - lastClickTime < MIN_CLICK_DELAY_TIME) {
            return;
        }

        int i = v.getId();
        if (i == R.id.tv_basemodule_startDate) {//开始时间
            setDataType(DataType.START_DATA);
            tv_startDate.setBackgroundResource(R.drawable.basemodule_bg_filter_date_checked);
            tv_endDate.setBackgroundResource(R.drawable.basemodule_bg_filter_date_unchecked);
            if (!is_initialize_start) {
                String dateStr = getDateStrFromCalendar(today);
                tv_startDate.setText(dateStr);
                is_initialize_start = true;
            }


        } else if (i == R.id.tv_basemodule_endDate) {//结束时间
            setDataType(DataType.END_DATA);
            tv_startDate.setBackgroundResource(R.drawable.basemodule_bg_filter_date_unchecked);
            tv_endDate.setBackgroundResource(R.drawable.basemodule_bg_filter_date_checked);
            if (!is_initialize_end) {
                String dateStr = getDateStrFromCalendar(today);
                tv_endDate.setText(dateStr);
                is_initialize_end = true;
            }

        } else if (i == R.id.tv_basemodule_date_reset) {//重置
            setDataType(DataType.START_DATA);//时间设置为 开始时间
            Calendar cc = Calendar.getInstance();
            timePicker.setDate(cc);
            tv_startDate.setText(getDateStrFromCalendar(cc));
            tv_endDate.setText("");
            tv_startDate.setBackgroundResource(R.drawable.basemodule_bg_filter_date_checked);
            tv_endDate.setBackgroundResource(R.drawable.basemodule_bg_filter_date_unchecked);
            is_initialize_end = false;
            is_initialize_start = false;

        } else if (i == R.id.tv_basemodule_date_confirm) {//确定
            if (myOnItemClickListener != null) {
                myOnItemClickListener.setData(tv_startDate.getText().toString(), tv_endDate.getText().toString());
            }

            if (null != this && isShowing()) {
                dismiss();

            }

        } else if (i == R.id.ll_basemodule_dissmiss) {// dissmiss 处理

            if (null != this && isShowing()) {
                dismiss();

            }

        } else if (i == R.id.ll_basemodule_bg) {// dissmiss 处理


            if (null != this && isShowing()) {
                dismiss();

            }

        }


    }


    public interface OnItemClickListener {

        void setData(String startData, String endData);

//        void  setDissmiss();
    }


    private TimePickerView.OnTimeSelectListener timeSelectListener = new TimePickerView.OnTimeSelectListener() {
        @Override
        public void onTimeSelect(Date date, View v) {
            Calendar cc = Calendar.getInstance();
            cc.setTime(date);
            String dateStr = getDateStrFromCalendar(cc);
            int dataType = getDataType();

            if (dataType == DataType.START_DATA) {
                tv_startDate.setText(dateStr);

            } else if (dataType == DataType.END_DATA) {
                tv_endDate.setText(dateStr);
            }

        }
    };


    private String getDateStrFromCalendar(Calendar cc) {
        if (cc == null) {
            return null;
        }
        int year = cc.get(Calendar.YEAR);
        int month = cc.get(Calendar.MONTH) + 1;
        int day = cc.get(Calendar.DAY_OF_MONTH);
        String dateStr = String.format("%4d-%02d-%02d", year, month, day);
        return dateStr;
    }




    /*定义常量*/

    @SimpleDatePopWindowNoArrow.DataType
    private int data = DataType.START_DATA;//默认为开始

    /**
     * IntDef 处理 逻辑
     */
    @Documented // 表示开启Doc文档
    @IntDef({DataType.START_DATA,
            DataType.END_DATA}) //限定为 START_DATA,END_DATA
    @Target({
            ElementType.PARAMETER,
            ElementType.FIELD,
            ElementType.METHOD,
    }) //表示注解作用范围，参数注解，成员注解，方法注解
    @Retention(RetentionPolicy.SOURCE) //表示注解所存活的时间,在运行时,而不会存在 .class 文件中
    public @interface DataType { //接口，定义新的注解类型
        int START_DATA = 1;
        int END_DATA = 2;
    }

    public void setDataType(@SimpleDatePopWindowNoArrow.DataType int data) {
        this.data = data;
    }


    @SimpleDatePopWindowNoArrow.DataType
    public int getDataType() {
        return data;
    }

}



