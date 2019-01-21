package com.base.common.utils;

import java.util.HashSet;
import java.util.List;

/**
 * Created by 41113 on 2018/6/27.
 */

public class ListUtils {
    /**
     * 删除重复的数据
     *
     * @param list
     * @return
     */
    public static List removeDuplicate(List list) {
        HashSet h = new HashSet(list);
        list.clear();
        list.addAll(h);
        return list;
    }
}
