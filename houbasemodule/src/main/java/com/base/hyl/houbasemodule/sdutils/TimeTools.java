package com.base.hyl.houbasemodule.sdutils;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by houyongliang on 2017/8/5.
 */

public class TimeTools {
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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy.MM.dd");
        try {
            Date date = sdf.parse(time);

            String str = sdf1.format(date);
            return str;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(time.contains("长期")){
            return time;
        }
        return null;
    }

    /**
     * 判断当前时间 与 获取的时间
     *
     * @param time 获取的时间
     * @return 0 未比对  1 未过期  -1  过期
     */
    public static int isIDCardExpired(String time) {
        if (TextUtils.isEmpty(time)) {
            return 0;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        try {
            Date dt1 = sdf.parse(time);
            Date dt2 = sdf.parse(sdf.format(new Date()));
            ;
            if (dt1.getTime() > dt2.getTime()) {
         /*未过期*/
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
             /*过期*/
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        /*如果长期 则不过期*/
        if(time.contains("长期")){
            return 1;
        }
        return 0;
    }

    /**
     * 将 获取当前时间 样式
     *
     * @param
     * @return
     */
    public static String getNowDateStr() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(new Date());

    }
}
