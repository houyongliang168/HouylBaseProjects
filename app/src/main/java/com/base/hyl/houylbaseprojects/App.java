package com.base.hyl.houylbaseprojects;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.base.common.log.MyLog;
import com.base.common.utils.ApplicationTool;

import com.base.widget.smartRefresh.MyRefreshFooter;
import com.base.widget.smartRefresh.MyRefreshHeader;

import com.orhanobut.logger.Logger;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by zhaoyu on 2017/4/18.
 */

public class App extends MultiDexApplication {
    /**
     * 全局的context
     * 周六随心投准生产代码
     */
    private static Context mContext;



    @Override
    public void onCreate() {
        super.onCreate();


        closeAndroidPDialog();// 解决小米MIUI在Android9.0系统上app调用私有API的弹窗警告

        mContext = this;

        Logger.init();


    }




    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        try {
            Class<?> multidex = Class.forName("android.support.multidex.MultiDex");
            Method install = multidex.getMethod("install", Context.class);
            install.invoke(null, base);
            MultiDex.install(base);
        } catch (Exception e) {
            MyLog.wtf("zcc", e.getMessage());
        }
    }

    public static Context getContext() {
        return mContext;
    }

    /**
     * 退出程序
     */
    public void exitApp() {
        try {
            MyLog.wtf("zcc","exitAPP!");
//            setActiveApp(false);
//            unregisterReceiver(receiver);
//            finishAllActivity();

            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        } catch (Exception e) {

//            HttpUtil.clearType();
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        }
    }
//
//    public void logout() {
//        finishAllActivity();
//        setTag("-1");
//        App.perInfo = null;
//        spUtils.put("token", "");
//        spUtils.remove("yinbao_change_staffnumber");
//        Intent intent = new Intent(getContext(), LoginActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        getContext().startActivity(intent);
//
//    }

//    private void setTag(String tag) {
//        Set<String> tagSet = new LinkedHashSet<>();
//        //调用JPush API设置Tag
//        JPushInterface.setAliasAndTags(getContext(), null, tagSet, mTagsCallback);
//    }

//    private TagAliasCallback mTagsCallback = new TagAliasCallback() {
//
//        @Override
//        public void gotResult(int code, String alias, Set<String> tags) {
//
//            switch (code) {
//                case 0:
//                    MyLog.wtf("logout","logout");
//                    break;
//
//                case 6002:
//                    break;
//
//                default:
//                    MyLog.wtf("logout",code+"");
//            }
//
//        }
//
//    };



    /**删除文件及文件夹
     * @param file
     */
    private void deleteFile(File file) {
        if (file.exists()) {
            if (file.isFile()) {
                file.delete();
            } else if (file.isDirectory()) {
                File files[] = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    this.deleteFile(files[i]);
                }
            }
            file.delete();
        }
    }


    private void closeAndroidPDialog(){
        try {
            Class aClass = Class.forName("android.content.pm.PackageParser$Package");
            Constructor declaredConstructor = aClass.getDeclaredConstructor(String.class);
            declaredConstructor.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Class cls = Class.forName("android.app.ActivityThread");
            Method declaredMethod = cls.getDeclaredMethod("currentActivityThread");
            declaredMethod.setAccessible(true);
            Object activityThread = declaredMethod.invoke(null);
            Field mHiddenApiWarningShown = cls.getDeclaredField("mHiddenApiWarningShown");
            mHiddenApiWarningShown.setAccessible(true);
            mHiddenApiWarningShown.setBoolean(activityThread, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                return new MyRefreshHeader(context);
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                return new MyRefreshFooter(context);
            }
        });
    }





}