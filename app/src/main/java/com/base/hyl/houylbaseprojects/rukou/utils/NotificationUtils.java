package com.base.hyl.houylbaseprojects.rukou.utils;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class NotificationUtils {
    /**
     * 通过反射获取通知的开关状态
     *
     * @param context
     * @return
     */
//    public static boolean isNotificationEnabled(Context context) {
//
//        AppOpsManager mAppOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
//        ApplicationInfo appInfo = context.getApplicationInfo();
//        String pkg = context.getApplicationContext().getPackageName();
//        int uid = appInfo.uid;
//        Class appOpsClass = null; /* Context.APP_OPS_MANAGER */
//        try {
//            appOpsClass = Class.forName(AppOpsManager.class.getName());
//            Method checkOpNoThrowMethod = appOpsClass.getMethod(CHECK_OP_NO_THROW, Integer.TYPE, Integer.TYPE, String.class);
//            Field opPostNotificationValue = appOpsClass.getDeclaredField(OP_POST_NOTIFICATION);
//            int value = (int) opPostNotificationValue.get(Integer.class);
//            return ((int) checkOpNoThrowMethod.invoke(mAppOps, value, uid, pkg) == AppOpsManager.MODE_ALLOWED);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return true;
//    }
}
