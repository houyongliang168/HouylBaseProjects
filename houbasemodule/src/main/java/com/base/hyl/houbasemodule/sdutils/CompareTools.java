package com.base.hyl.houbasemodule.sdutils;

import android.text.TextUtils;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created by houyongliang on 2017/7/12.
 * 比较电话是否相同的工具
 */

public class CompareTools {
    /**
     * 比较两个 电话是否相同(比较两个加密后的字符串)
     * 按照，截取
     *
     * @param mobile1
     * @param mobile2
     * @return
     */
    public static boolean isEquals(String mobile1, String mobile2) {
        Boolean isEquals = false;
        mobile1=getMobils(mobile1);
        mobile2=getMobils(mobile2);
        Logger.e("mobile1"+mobile1);
        Logger.e("mobile2"+mobile2);
        String[] strs1 = mobile1.split(",");
        String[] strs2 = mobile2.split(",");
        if (strs1.length != strs2.length) {
            isEquals = false;
            return isEquals;
        } else {
            /*比较两个str 数组 每个字段*/
            Arrays.sort(strs1);
            Arrays.sort(strs2);
            if (Arrays.equals(strs1, strs2)) {
                isEquals = true;
                return isEquals;
            } else {
                isEquals = false;
                return isEquals;
            }
        }
    }

    /**
     * 比较两个 电话是否有包含关系(比较两个加密后的字符串)
     *
     * @param mobile1
     * @param mobile2 判断  mobile1 包含 mobile2
     * @return
     */
    public static boolean isEqualContain(String mobile1, String mobile2) {
        /*包含关系 处理*/
        if (TextUtils.isEmpty(mobile1)) {
            return false;
        }
        if (TextUtils.isEmpty(mobile2)) {
            return false;
        }
        Boolean isEquals = false;
        mobile1 = getMobils(mobile1);
        mobile2 = getMobils(mobile2);
        Logger.e("mobile1" + mobile1);
        Logger.e("mobile2" + mobile2);
        String[] strs1 = mobile1.split(",");
        String[] strs2 = mobile2.split(",");

        /*比较两个str 数组 每个字段*/
        Arrays.sort(strs1);
        Arrays.sort(strs2);
        List _a = Arrays.asList(strs1);
        List _b = Arrays.asList(strs2);
        Collection realA = new ArrayList<String>(_a);
        Collection realB = new ArrayList<String>(_b);
        if (realA.containsAll(realB)) {
            isEquals = true;
            return isEquals;
        } else {
            return false;
        }
    }


    /**
     * 比较两个 电话是否有交集
     *
     * @param mobile1
     * @param mobile2 判断  mobile1 包含 mobile2
     *                intersection  翻译为 交集
     * @return
     */
    public static boolean isEqualIntersection(String mobile1, String mobile2) {
        /*交集关系 处理*/
        if (TextUtils.isEmpty(mobile1)) {
            return false;
        }
        if (TextUtils.isEmpty(mobile2)) {
            return false;
        }
        Boolean isEquals = false;
        mobile1 = getMobils(mobile1);
        mobile2 = getMobils(mobile2);
        Logger.e("mobile1" + mobile1);
        Logger.e("mobile2" + mobile2);
        String[] strs1 = mobile1.split(",");
        String[] strs2 = mobile2.split(",");

        /*比较两个str 数组 每个字段*/
        Arrays.sort(strs1);
        Arrays.sort(strs2);
        List<String> _a = Arrays.asList(strs1);
        List<String> _b = Arrays.asList(strs2);
        ArrayList<String> realA = new ArrayList<String>(_a);
        ArrayList<String> realB = new ArrayList<String>(_b);
        realA.retainAll(realB);
        if (realA.size() > 0) {
            isEquals = true;
            return isEquals;
        } else {
            return false;
        }
    }

    /**
     * 对电话号码去空格和 去 “—”，最后没有 “，”
     *
     * @param mobils
     * @return
     */
    public static String getMobils(String mobils) {
        String dest = "";
        if (mobils != null) {
     /* 去空格*/
            dest = mobils.replaceAll(" ", "");
        /*去"-"*/
            dest = dest.replaceAll("-", "");
        /*去掉最后 已 ，结尾*/
            if (dest.endsWith(",")) {
                dest = dest.substring(0, dest.length() - 1);
            }
        }
        return dest;
    }

    /*加密算法 电话加密后比较内容*/

//    public static void main(String[] args) {
//        System.out.println(getMobils("jk kjl  -------,"));
//
//    }

}
