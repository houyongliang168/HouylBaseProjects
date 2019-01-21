package com.base.common.util;

/**
 * Created by yanggang12 on 2017/5/4.
 */

public class FilterUtils {
    /**
     * 去除bom报头
     */
    public static String formatString(String s) {
        if (s != null) {
            s = s.replaceAll("\ufeff", "");
        }
        return s;
    }
}
