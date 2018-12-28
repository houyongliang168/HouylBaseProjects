package com.base.hyl.houbasemodule.util;

import java.util.List;

/**
 * Created by zcc on 2018/4/20.
 */

public class ListUtils {
    /**
     * list集合转成String字符串
     *
     * @param list
     * @return
     */
    public static String listToString(List<String> list) {
        if (list == null) {
            return null;
        }
        StringBuilder result = new StringBuilder();
        boolean flag = false;
        for (String item : list) {
            if (flag) {
                result.append(",");
            } else {
                flag = true;
            }
            result.append(item);
        }
        return result.toString();
    }

}
