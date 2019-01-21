package com.base.common.sdutils;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by houyongliang on 2017/5/31.
 */

public class TimeUtils {
    /**
     * 通过 long 获取时间
     *
     * @return
     */
    public static String transferLongToDate(String dateFormat, Long millSec) {
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        Date currentTime = new Date(millSec);
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 通过 long 获取 "HH:mm:ss" 时间
     *
     * @return
     */
    public static String getDateStr(Long millSec) {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        Date currentTime = new Date(millSec);
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 将 string 转为 指定样式的时间
     *
     * @param time
     * @return
     */
    public static String getDateStr(String time) {
        if (TextUtils.isEmpty(time)) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        SimpleDateFormat sdf1 = new SimpleDateFormat("MM月dd日  HH:mm");
        try {
            Date date = sdf.parse(time);

            String str = sdf1.format(date);
            return str;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /* 设置时间*/
//    public static  String getVodieTime(int time){
//       int ss=time/60;
//        int mm=time/60/60;
//        int hh=time/60/60/24;
//        return  hh+""+mm+""+ss;
//    }
    public static String secToTime(int time) {
        String timeStr = null;
        int hour = 0;
        int minute = 0;
        int second = 0;
        if (time <= 0)
            return "00:00:00";
        else {
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
                timeStr = "00" + ":" + unitFormat(minute) + ":" + unitFormat(second);
            } else {
                hour = minute / 60;
                if (hour > 99)
                    return "99:59:59";
                minute = minute % 60;
                second = time - hour * 3600 - minute * 60;
                timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
            }
        }
        return timeStr;
    }
    public static String secToTime(long time) {
        String timeStr = null;
        int hour = 0;
        int minute = 0;
        int second = 0;
        if (time <= 0)
            return "00:00:00";
        else {
            minute =(int) time / 60;
            if (minute < 60) {
                second =(int) time % 60;
                timeStr = "00" + ":" + unitFormat(minute) + ":" + unitFormat(second);
            } else {
                hour = minute / 60;
                if (hour > 99)
                    return "99:59:59";
                minute = minute % 60;
                second =(int)( time - hour * 3600 - minute * 60);
                timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
            }
        }
        return timeStr;
    }
    public static String secToTime(String times) {
        if(TextUtils.isEmpty(times)){
            return "00:00:00";
        }
        String timeStr = null;
        int hour = 0;
        int minute = 0;
        int second = 0;
        int time=0;
        try {
            time = Integer.parseInt(times);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        if (time <= 0){
            return "00:00:00";}
        else {
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
                timeStr = "00" + ":" + unitFormat(minute) + ":" + unitFormat(second);
            } else {
                hour = minute / 60;
                if (hour > 99){
                    return "99:59:59";}
                minute = minute % 60;
                second = time - hour * 3600 - minute * 60;
                timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
            }
        }
        return timeStr;
    }
    public static String unitFormat(int i) {
        String retStr = null;
        if (i >= 0 && i < 10)
            retStr = "0" + Integer.toString(i);
        else
            retStr = "" + i;
        return retStr;
    }

    /* 獲取當前系統時間*/
    public static String getCurrentTime() {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");//设置日期格式
        return df.format(new Date());// new Date()为获取当前系统时间
    }

    /* 獲取當前系統時間*/
    public static String getCurrentTimeHM() {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");//设置日期格式
        return df.format(new Date());// new Date()为获取当前系统时间
    }

    public static String secToTimeMS(int time) {
        String timeStr = null;
        int minute = 0;
        int second = 0;
        if (time <= 0)
            return "00:00";
        else {
            minute = time / 60;
            second = time % 60;
            timeStr = unitFormat(minute) + ":" + unitFormat(second);
        }
        return timeStr;
    }

    public static String secToHMS(long time) {
        SimpleDateFormat formatter;
        if(time<(3600*1000)){
            formatter = new SimpleDateFormat("mm:ss");
        }else{
            formatter = new SimpleDateFormat("HH:mm:ss");
        }
        time = time - TimeZone.getDefault().getRawOffset();//去掉时区差
        String dateString = formatter.format(time);
        return dateString;
    }


}
