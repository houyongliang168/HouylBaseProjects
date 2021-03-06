package com.base.common.windows;

import android.content.Context;
import android.content.res.Configuration;

/**
 * Created by zhaoyu on 2018/1/10.类说明：判断是手机还是平板
 */

public class DeviceType {
    /**
     * 判断当前设备是手机还是平板，代码来自 Google I/O App for Android
     * @param context
     * @return 平板返回 True，手机返回 False
     */
    public static boolean isPad(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }
}
