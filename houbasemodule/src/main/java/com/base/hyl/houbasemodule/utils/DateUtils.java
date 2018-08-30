package com.base.hyl.houbasemodule.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by zcc on 2018/7/25.
 * 日期工具类
 */

public class DateUtils {
    /**
     * 判断今天是否在时间段内
     *
     * @param beginTime yyyy-MM-dd
     * @param endTime   yyyy-MM-dd
     * @return true在，false不在
     */
    public static boolean todayBetween(String beginTime, String endTime) {
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Calendar todayCalendar = Calendar.getInstance();
            String todayTime = df.format(todayCalendar.getTime());
            Date today = df.parse(todayTime);
            Date begin = df.parse(beginTime);
            Date end = df.parse(endTime);
            if (today.getTime() >= begin.getTime() && today.getTime() <= end.getTime()) {
//            if (today.after(begin) && today.before(end)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 判断某天是否在时间段内
     *
     * @param someTime  yyyy-MM-dd
     * @param beginTime yyyy-MM-dd
     * @param endTime   yyyy-MM-dd
     * @return true在，false不在
     */
    public static boolean someDayBetween(String someTime, String beginTime, String endTime) {
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date day = df.parse(someTime);
            Date begin = df.parse(beginTime);
            Date end = df.parse(endTime);
            if (day.getTime() >= begin.getTime() && day.getTime() <= end.getTime()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }
}
