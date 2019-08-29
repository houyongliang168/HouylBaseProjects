package com.base.hyl.houylbaseprojects.download.core;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.IBinder;


import com.base.common.log.MyToast;
import com.base.common.net.NetWorkStateChange;
import com.base.common.store.SPUtil;
import com.base.hyl.houylbaseprojects.download.core.callback.DownloadManager;
import com.base.hyl.houylbaseprojects.download.core.config.Config;
import com.base.hyl.houylbaseprojects.download.core.db.DownloadDBController;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 */

public class DownloadService extends Service {

    private static final String TAG = "DownloadService";
    public static DownloadManager downloadManager;

    public static DownloadManager getDownloadManager(Context context) {
        return getDownloadManager(context, null);
    }

    public static DownloadDBController getDownloadDBController(Context context){
        if(downloadManager==null){
            getDownloadManager(context);
        }
        if(downloadManager==null){
            return null;
        }
       return downloadManager.getDownloadDBController();
    }

    public static DownloadManager getDownloadManager(Context context, Config config) {
        if (!isServiceRunning(context)) {
            Intent downloadSvr = new Intent(context, DownloadService.class);
            context.startService(downloadSvr);
        }
        if (DownloadService.downloadManager == null) {
            DownloadService.downloadManager = DownloadManagerImpl.getInstance(context, config);
        }
        return downloadManager;
    }

    private static boolean isServiceRunning(Context context) {
        boolean isRunning = false;
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningServiceInfo> serviceList = activityManager
                .getRunningServices(Integer.MAX_VALUE);

        if (serviceList == null || serviceList.size() == 0) {
            return false;
        }

        for (int i = 0; i < serviceList.size(); i++) {
            if (serviceList.get(i).service.getClassName().equals(
                    DownloadService.class.getName())) {
                isRunning = true;
                break;
            }
        }
        return isRunning;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    /* 关闭服务*/
    private static void stopService(Context context){
        Intent stopIntent = new Intent(context, DownloadService.class);
        context.stopService(stopIntent);
    }


    @Override
    public void onDestroy() {
        if (downloadManager != null) {
            downloadManager.onDestroy();
            downloadManager = null;
        }
        super.onDestroy();
    }

    // 监听网络状态 暂停的方法
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showNetWorkChangeDialog(NetWorkStateChange netWorkStateChange) {
        if (netWorkStateChange.getConnectType() == ConnectivityManager.TYPE_MOBILE) {
            SPUtil spUtils = new SPUtil("autoLogin");
            boolean isGLoad = spUtils.getBoolean("isGLoad");
            if (isGLoad) {
                MyToast.showLong("您已允许4G环境下载视频");
            } else {
                if(downloadManager!=null){
                    downloadManager.pauseAll();
                }

            }
        }
    }
}
