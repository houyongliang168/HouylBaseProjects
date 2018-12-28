package com.base.hyl.houbasemodule.utils;

import android.text.TextUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtil {
    static SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");

    public static String getStringTime(long time) {
        String r = "";
        long nowtimelong = System.currentTimeMillis();
        long result = Math.abs(nowtimelong - time);
        if (result < 60000) {// 一分钟内
            long seconds = result / 1000;
            if (seconds == 0) {
                r = "刚刚";
            } else {
                r = seconds + "秒前";
            }
        } else if (result >= 60000 && result < 3600000) {// 一小时内
            long seconds = result / 60000;
            r = seconds + "分钟前";
        } else if (result >= 3600000 && result < 86400000) {// 一天内
            long seconds = result / 3600000;
            r = seconds + "小时前";
        } else if (result >= 86400000 && result < 1702967296) {// 三十天内
            long seconds = result / 86400000;
            r = seconds + "天前";
        } else {// 日期格式
            r = format.format(new Date(time));
        }
        return r;
    }

    public static Date getDate(String date) {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm ");

        try {
            return formatter.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static long getLongTime(String time) {
        if(TextUtils.isEmpty(time)){
            return 0;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = null;
        try {
            date = format.parse(time);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;

    }

    /**
     * 得到现在小时
     */
    public static String getHour(long time) {
        Date currentTime = new Date(time);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        String hour;
        hour = dateString.substring(11, 13);
        return hour;
    }

    /**
     * 得到年份
     */
    public static String getYear(long time) {
        Date currentTime = new Date(time);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        String year;
        year = dateString.substring(0, 4);
        return year;
    }

    /**
     * 得到年月日
     */
    public static String getYMD(long time) {
        Date currentTime = new Date(time);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        String year;
        year = dateString.substring(0, 10);
        return year;
    }

    /**
     * 得到现在时间
     */
    public static String getTimeOther(long time) {
        Date currentTime = new Date(time);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);

        return dateString;
    }

    /**
     * 得到时间
     */
    public static String getTime(long time) {
        Date currentTime = new Date(time);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        String dateString = formatter.format(currentTime);

        return dateString;
    }


    public static String getDayTime(long time) {
        Date currentTime = new Date(time);
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        String dateString = formatter.format(currentTime);

        return dateString;
    }

    public static String getDayTimeOther(long time) {
        Date currentTime = new Date(time);
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        String dateString = formatter.format(currentTime);

        return dateString;
    }

    public static int getDay(long time) {
        Date currentTime = new Date(time);
        return currentTime.getDate();
    }

    public static int getMouth(long time) {
        Date currentTime = new Date(time);
        return currentTime.getMonth() + 1;
    }

    public static int getIntMouth(String time) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(time);
            return date.getMonth() + 1;
        } catch (ParseException e) {

        }

        return 0;
    }

    public static int getIntYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    public static String getYearTime(long time) {
        Date currentTime = new Date(time);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
        String dateString = formatter.format(currentTime);

        return dateString;
    }

    public static long getLongYearTime(String time) {
        Date date = null;
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            try {
                date = new SimpleDateFormat("yyyy年MM月dd日").parse(time);
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        return date.getTime();
    }

    public static long getTime(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = sdf.parse(time);
            return date.getTime();
        } catch (ParseException e) {
            return System.currentTimeMillis();
        }
    }

    public static String timeFormatString(Long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date(time));
    }

    public static Long timeBefore(int num) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.MONTH, c.get(Calendar.MONTH) - 1);
        Date day = c.getTime();
        return day.getTime();
    }

    public static long timeDifference(String startTime, String endTime) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date d1 = df.parse(startTime);
            Date d2 = df.parse(endTime);
            long day=d1.getTime() - d2.getTime();
            long days=day / (60 * 60 * 1000 * 24);
            long years=days / 365;
            return years;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }


    /**
     * 将 string 转为 指定样式的时间
     * <p>
     * 团队生日提醒
     * @param time
     * @return
     */
    public static String getDateStr(String time) {
        if (TextUtils.isEmpty(time)) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
        SimpleDateFormat sdf1 = new SimpleDateFormat("M月d日");
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
     * 将 string 转为 指定样式的时间
     * <p>
     * 团队生日提醒
     * @param time
     * @return
     */
    public static Date getDateSty(String time) {
        if (TextUtils.isEmpty(time)) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
              try {
            Date date = sdf.parse(time);


            return date;
        } catch (ParseException e) {
            e.printStackTrace();

        }
        return null;
    }

    public static String getNomalDate(String date){
        if(TextUtils.isEmpty(date)){
            return "";
        }
        String[] split = date.split("-");
        StringBuilder sb=new StringBuilder();
        for (int i = 0; i < split.length; i++) {

            if(i==split.length-1){
                if(split[i].length()==1){
                    split[i]="0"+split[i];
                    sb.append(split[i]);
                }else{
                    sb.append(split[i]);
                }
            }else {
                if(split[i].length()==1){
                    split[i]="0"+split[i];
                    sb.append(split[i]+"-");
                }else{
                    sb.append(split[i]+"-");
                }
            }

        }
        return sb.toString();
    }
}
