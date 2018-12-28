package com.base.hyl.houbasemodule.sdutils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.raizlabs.android.dbflow.sql.language.Condition;
import com.taikanglife.isalessystem.R;
import com.taikanglife.isalessystem.common.utils.TCAgentUtils;
import com.taikanglife.isalessystem.module.main.LiveCacheActivity;
import com.taikanglife.isalessystem.module.main.live.VoidActivity2;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.downlaod.DownInfo;

import java.io.File;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * 直播点播的 工具类
 * Created by houyongliang on 2018/1/25.
 */

public class LiveTools {
    /*单例模式*/
    private LiveTools() {
    }

    private static class Bulid {
        private static final LiveTools single = new LiveTools();
    }

    public static LiveTools getInstance() {
        return Bulid.single;
    }

    /**
     * @param activity
     */
    public void showDownDialog(final Activity activity, final DownInfo downInfo) {

        View view = activity.getLayoutInflater().inflate(R.layout.item_live_void_download,
                null);
        /*获取视图*/
        TextView tv_size = (TextView) view.findViewById(R.id.tv_size);
        TextView tv_see_download = (TextView) view.findViewById(R.id.tv_see_download);
        TextView tv_begin_download = (TextView) view.findViewById(R.id.tv_begin_download);
        String num = getSDCardNum();
        /**
         * 对剩余的空间进行处理
         */
        tv_size.setText(TextUtils.isEmpty(num) ? "剩余空间不足" : ("(剩余" + num + ")"));
        final Dialog dialog = new Dialog(activity, R.style.transparentFrameWindowStyle);
        dialog.setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        Window window = dialog.getWindow();
        // 设置显示动画
        window.setWindowAnimations(R.style.main_menu_animstyle);
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y = activity.getWindowManager().getDefaultDisplay().getHeight();
        // 以下这两句是为了保证按钮可以水平满屏
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;

        // 设置显示位置
        dialog.onWindowAttributesChanged(wl);
        // 设置点击外围解散
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();

        tv_see_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 2018/1/25 跳转到已缓冲页面
                LiveCacheActivity.startLiveCacheActivity(activity);
//                if(activity instanceof VoidActivity2){
//                    ((VoidActivity2)activity).finishActivity();
//                }

                Logger.e("查看下载点击 ");
                if (dialog != null) {
                    dialog.dismiss();
                }
                /**
                 *查看缓存（点击点播列表右上方的缓存按钮或者点击下载之后的查看缓存按钮）
                 */
                Map<String,Object> map=new HashMap<String,Object>();
                if(downInfo!=null){
                    map.put("liveId",TextUtils.isEmpty(downInfo.getLiveId())?"":downInfo.getLiveId());
                }
                TCAgentUtils.setTcAgentDefaut(activity,"_8","_8_15",map);

            }
        });
        tv_begin_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Logger.e("downInfo"+downInfo.toString());
                // TODO: 2018/1/25 跳转到下载页面
                LiveCacheActivity.startLiveCacheActivity(activity,downInfo);
//                if(activity instanceof VoidActivity2){
//                    ((VoidActivity2)activity).finishActivity();
//                }
                Logger.e("开始下载点击 ");
                /*返回页面 dialog 消失*/
                if (dialog != null) {
                    dialog.dismiss();
                }
                   /*插码*/
//                Map<String, String> map = new HashMap<String, String>();
//                map.put("liveId", liveId);
//                if ("1".equals(isLive)) {
//                    TCAgent.onEvent(activity, TCAgent_EventId.EVENT_LIVE, "直播转发1（微信）", map);
//
//                }
//                if ("0".equals(isLive)) {
//                    TCAgent.onEvent(activity, TCAgent_EventId.EVENT_LIVE, "点播转发1（微信）", map);
//                }

            }
        });
    }


//    //一个包装类，用来检索文件系统的信息
//    StatFs stat = new StatFs(path);
//    //文件系统的块的大小（byte）
//    long blockSize = stat.getBlockSize();
//    //文件系统的总的块数
//    long totalBlocks = stat.getBlockCount();
//    //文件系统上空闲的可用于程序的存储块数
//    long availableBlocks = stat.getAvailableBlocks();
//
//    //总的容量
//    long totalSize = blockSize*totalBlocks;
//    //空闲的大小
//    long availableSize = blockSize*availableBlocks;

    /**
     * 获取sd 的大小
     *
     * @return
     */
    public String getSDCardNum() {
        long num = getSDAvailableSize();
        return getSDDataSize(num);
    }

    /**
     * 返回byte的数据大小对应的文本
     *
     * @param size
     * @return
     */
    public String getDataSize(long size) {
        if (size < 0) {
            size = 0;
        }
        DecimalFormat formater = new DecimalFormat("####.00");
        if (size < 1024) {
            return size + "bytes";
        } else if (size < 1024 * 1024) {
            float kbsize = size / 1024f;
            return formater.format(kbsize) + "KB";
        } else if (size < 1024 * 1024 * 1024) {
            float mbsize = size / 1024f / 1024f;
            return formater.format(mbsize) + "MB";
        } else if (size < 1024 * 1024 * 1024 * 1024) {
            float gbsize = size / 1024f / 1024f / 1024f;
            return formater.format(gbsize) + "GB";
        } else {
            return "size: error";
        }

    }

    /**
     * 对 SD 卡的大小再次进行处理
     *
     * @param size
     * @return
     */

    public String getSDDataSize(long size) {
        Logger.e("size:" + size);
        if (size < 1024 * 1024 * 100) {/*小于100M 显示空间不足*/
            return "SD卡空间不足";
        }
        DecimalFormat formater = new DecimalFormat("###0.0");
//        if (size < 1024 * 1024 * 1024) {
//            float mbsize = size / 1024f / 1024f;
//            return formater.format(mbsize) + "MB";
//        } else

        if (size < (1024L * 1024L * 1024L * 1024L)) {
            float gbsize = size / 1024f / 1024f / 1024f;
            Logger.e(formater.format(gbsize) + "G");
            return formater.format(gbsize) + "G";
        } else {
            float gbsize = size / 1024f / 1024f / 1024f / 1024f;
            return formater.format(gbsize) + "T";
        }

    }


    /**
     * 计算总空间
     *
     * @param path
     * @return
     */
    private long getTotalSize(String path) {
        StatFs fileStats = new StatFs(path);
        fileStats.restat(path);
        return (long) fileStats.getBlockCount() * fileStats.getBlockSize();
    }

    /**
     * 计算剩余空间
     *
     * @param path
     * @return
     */
    private long getAvailableSize(String path) {
        StatFs fileStats = new StatFs(path);
        fileStats.restat(path);
        return (long) fileStats.getAvailableBlocks() * fileStats.getBlockSize(); // 注意与fileStats.getFreeBlocks()的区别
    }

    /**
     * 计算SD卡的剩余空间
     *
     * @return 剩余空间
     */
    public long getSDAvailableSize() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return getAvailableSize(Environment.getExternalStorageDirectory().getPath().toString());
        }
        return 0;
    }

    /**
     * 计算系统的剩余空间
     *
     * @return 剩余空间
     */
    public long getSystemAvailableSize() {
        // context.getFilesDir().getAbsolutePath();
        return getAvailableSize("/data");
    }

    /**
     * 是否有足够的空间
     *
     * @param filePath 文件路径，不是目录的路径
     * @return
     */
    public boolean hasEnoughMemory(String filePath) {
        File file = new File(filePath);
        long length = file.length();
        if (filePath.startsWith("/sdcard") || filePath.startsWith("/mnt/sdcard")) {
            return getSDAvailableSize() > length;
        } else {
            return getSystemAvailableSize() > length;
        }
    }

    /**
     * 获取SD卡的总空间
     *
     * @return
     */
    public long getSDTotalSize() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return getTotalSize(Environment.getExternalStorageDirectory().toString());
        }
        return 0;
    }

    /**
     * 获取系统可读写的总空间
     *
     * @return
     */
    public long getSysTotalSize() {
        return getTotalSize("/data");
    }

    /**
     *
     * @param vodUrl 初始的 URL 地址
     * @return  返回的mp4 的名字
     */
    public String getMp4Name(String vodUrl) {
        //int i=vodUrl.lastIndexOf("/"); //获取最后的 /的位置
        return vodUrl.substring(vodUrl.lastIndexOf("/") + 1, vodUrl.length());
    }
}
