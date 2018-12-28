package com.base.hyl.houbasemodule.sdutils;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class AchievementUtil {

    /**
     * Date类型日期转String，以日为单位（界面显示使用）
     * @param date
     * @return
     */
    public static String getTextDayTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        return format.format(date);
    }

    /**
     * 获取经营指标类型（UI显示使用）
     * 人均标保rjbb/人均件数rjjs/件均标保jjbb/3000P人力3000
     * @param type
     * @return
     */
    public static String getTextOperatingsType(boolean isPersonal,String type){
        if(isPersonal){
            switch (type){
                case "jjbb":
                    return "件均标准保费";
                case "rjbb":
                    return "标准保费";
                case "rjjs":
                    return "承保件数";
                default:
                    return "";

            }
        }else{
            switch (type){
                case "jjbb":
                    return "件均标准保费";
                case "rjbb":
                    return "人均标准保费";
                case "rjjs":
                    return "人均件数";
                case "3000":
                    return "3000P";
                default:
                    return "";

            }
        }
    }

    /**
     * 获取经营指标类型（服务器传参使用）
     * 人均标保rjbb/人均件数rjjs/件均标保jjbb/3000P人力3000
     * @param type
     * @return
     */
    public static String getHttpOperatingsType(String type){
        if (type.equals("人均标准保费") || type.equals("标准保费")){
            return "rjbb";
        }else if(type.equals("人均件数") || type.equals("承保件数")){
            return "rjjs";
        }else if(type.equals("件均标准保费")){
            return "jjbb";
        }else if(type.equals("3000P")){
            return "3000";
        }
        return "";
    }

    /**
     * 获取经营指标单位
     * 人均标保rjbb/人均件数rjjs/件均标保jjbb/3000P人力3000
     * @param type
     * @return
     */
    public static String getHttpOperatingsUnit(String type){
        if (type.equals("人均标准保费")){
            return "元";
        }else if(type.equals("人均件数")){
            return "件";
        }else if(type.equals("件均标准保费")){
            return "元";
        }else if(type.equals("3000P")){
            return "人";
        }
        return "";
    }

    /**
     * 格式化字符串日期
     * @param str
     * @return
     */
    public static String getHttpDate(String str){
        return str.replaceAll("月", "").replaceAll("年", "");
    }

    /**
     * Date类型日期转String，以月为单位（界面显示使用，不去0）
     * @param date
     * @return
     */
    public static String getTextMonthTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月");
        return format.format(date);
    }

    /**
     * 日期去0，以月为单位（界面显示使用）
     * @param str
     * @return
     */
    public static String getTextMonthTimeDelZero(String str) {
        int y = Integer.valueOf(str.substring(0,4));
        int m = Integer.valueOf(str.substring(5,7));
        return y + "年" + m + "月";
    }

    /**
     * Date类型日期转String，以月为单位（服务器传参使用）
     * @param date
     * @return
     */
    public static String getHttpMonthTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
        return format.format(date);
    }

    /**
     * Date类型日期转String，以年为单位（界面显示使用）
     * @param date
     * @return
     */
    public static String getTextYearTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年");
        return format.format(date);
    }

    /**
     * Date类型日期转String，以年为单位（服务器传参使用）
     * @param date
     * @return
     */
    public static String getHttpYearTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy");
        return format.format(date);
    }

    /**
     * 获取继续率类型（服务器传参使用）
     * @param type
     * @return
     */
    public static String getHttpPersistencyType(String type){
        if (type.equals("继续率")){
            return "JXL";
        }else if(type.equals("应收")){
            return "YS";
        }else if(type.equals("实收")){
            return "SS";
        }
        return null;
    }
    /**
     * 获取承保类型（服务器传参使用）
     * @param type
     * @return
     */
    public static String getHttpUnderwriterType(String type){
        if (type.equals("件数")){
            return "js";
        }else if(type.equals("标保")){
            return "bb";
        }
        return null;
    }
    /**
     * 获取受理类型（服务器传参使用）
     * @param type
     * @return
     */
    public static String getHttpAcceptType(String type){
        if (type.equals("件数")){
            return "js";
        }else if(type.equals("规模保费")){
            return "gb";
        }
        return null;
    }

    /**
     * 获取可查看的月份范围
     * @param num
     * @return
     */
    public static ArrayList<String> getMonthDateRange(int num){
        ArrayList<String> dateList = new ArrayList<>();
        for (int i = 0; i<num; i++){
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, -i);
            dateList.add(AchievementUtil.getTextMonthTime(calendar.getTime()));
        }
        return dateList;
    }

    /**
     * 获取可查看的年份范围
     * @param num
     * @return
     */
    public static ArrayList<String> getYearDateRange(int num){
        ArrayList<String> dateList = new ArrayList<>();
        for (int i = 0; i<num; i++){
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.YEAR, -i);
            dateList.add(AchievementUtil.getTextYearTime(calendar.getTime()));
        }
        return dateList;
    }

    /**
     * 设置承保的类型数组
     * @return
     */
    public static ArrayList<String> setUnderWriterType(){
        ArrayList<String> typeList = new ArrayList<>();
        typeList.add("件数");
        typeList.add("标保");
        return typeList;
    }
    /**
     * 设置受理的类型数组
     * @return
     */
    public static ArrayList<String> setAcceptType(){
        ArrayList<String> typeList = new ArrayList<>();
        typeList.add("件数");
        typeList.add("规模保费");
        return typeList;
    }
    /**
     * 设置继续率的类型数组
     * @return
     */
    public static ArrayList<String> setPersistencyType(){
        ArrayList<String> typeList = new ArrayList<>();
        typeList.add("继续率");
        typeList.add("应收");
        typeList.add("实收");
        return typeList;
    }
}
