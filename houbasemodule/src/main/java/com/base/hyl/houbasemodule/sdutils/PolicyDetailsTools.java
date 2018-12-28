package com.base.hyl.houbasemodule.sdutils;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by houyongliang on 2017/11/3.
 * 客户属性处理 工具类
 */

public class PolicyDetailsTools {

    private PolicyDetailsTools() {
    }

    private static class Bulid {
        private static final PolicyDetailsTools single = new PolicyDetailsTools();
    }

    public static PolicyDetailsTools getInstance() {
        return Bulid.single;
    }


    /**
     * 将 string 转为 指定样式的时间
     *
     * @param time
     * @return
     */
    public String getDateStr(String time) {
        if (TextUtils.isEmpty(time)) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy年-MM月-dd日");
        try {
            Date date = sdf.parse(time);

            String str = sdf1.format(date);
            return str;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取当前的时间 yyyy-MM-dd
     *
     * @return
     */
    public String getNowDate() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String str = sdf.format(date);
        return str;

    }


    /**
     * @param date "2012-5-27"
     * @return
     */
    public Calendar getCalendar(String date) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        try {
            Date d = sdf.parse(date);
            calendar.setTime(d);
            return calendar;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return calendar;

    }

    /**
     * date 时间转为 long
     *
     * @param date "yyyy-MM-dd"
     * @return
     */
    public long getDateTime(String date) {
        if (TextUtils.isEmpty(date)) {
            return 0;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dt2 = null;
        try {
            dt2 = sdf.parse(date);
            return dt2.getTime() / 1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //继续转换得到秒数的long型
        return 0;

    }

    /**
     * 日期格式转化
     * @param time
     * @return
     */
    public String getTransformDateTime(String time) {
        if (TextUtils.isEmpty(time)) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
        try {
            Date date = sdf.parse(time);
            String str = sdf1.format(date);
            return str;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";

    }

}
