package com.base.hyl.houbasemodule.sdutils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Picture;
import android.os.Environment;
import android.view.View;
import android.webkit.WebView;
import android.widget.ScrollView;

import com.base.hyl.houbasemodule.tools.FileUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by houyongliang on 2017/8/30.
 * 截屏处理 工具
 * 截取 屏幕 并 压缩为 110 kb 左右
 * <p>
 * 提供截取 屏幕 ，scrollview  ,以及 webview 里面的内容 ，删除所有 截取图片的方法
 * <p>
 * 考虑 将 文件路径 保存至 SP 中 进行存取
 * <p>
 * getSet 获取所有 图片集合
 * deleteAllPng  删除所有的图片集合
 */

public class CaptureScreenTools {


    private Set<String> paths;
    private SharedPreferences sp;
    /*单例模式  */
    private CaptureScreenTools() {
    }
    private SharedPreferences getSp(Context context) {
        if (sp == null) {
            sp = context.getSharedPreferences("CaptureScreenTools", Context.MODE_PRIVATE);
        }
        return sp;
    }

    private static class Bulid {
        private static final CaptureScreenTools single = new CaptureScreenTools();
    }

    public static CaptureScreenTools getInstance() {
        return Bulid.single;
    }


    /**
     * 截取 导航栏 之外的屏幕
     *
     * @param activity    上下文
     * @param fileName    文件名字
     * @param needRecycle 是否需要回收 img_default 对象
     */
    public void getBitmap(Activity activity, String fileName, Boolean needRecycle) {
        View dView = activity.getWindow().getDecorView();
        dView.setDrawingCacheEnabled(true);
        dView.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(dView.getDrawingCache());
        if (bitmap != null) {
            bitmap = compressBitmap(bitmap);/*获取图片*/
            String path = save2Bitmap(bitmap, fileName);
            putSetString(activity, path);

            if (needRecycle) {
                bitmap.recycle();
            }


        }
    }
    public void getDialogBitmap(Context context, Dialog dialog, String fileName, Boolean needRecycle) {
        View dView = dialog.getWindow().getDecorView();
        dView.setDrawingCacheEnabled(true);
        dView.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(dView.getDrawingCache());
        if (bitmap != null) {
            bitmap = compressBitmap(bitmap);/*获取图片*/
            String path = save2Bitmap(bitmap, fileName);
            putSetString(context, path);

            if (needRecycle) {
                bitmap.recycle();
            }


        }
    }

    /**
     * 截取scrollview的屏幕
     **/
    public void getScrollViewBitmap(Context context, ScrollView scrollView, String fileName, Boolean needRecycle) {
        int h = 0;
        Bitmap bitmap;
        for (int i = 0; i < scrollView.getChildCount(); i++) {
            h += scrollView.getChildAt(i).getHeight();
        }
        // 创建对应大小的bitmap
        bitmap = Bitmap.createBitmap(scrollView.getWidth(), h,
                Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(bitmap);
        scrollView.draw(canvas);
        if (bitmap != null) {
            bitmap = compressBitmap(bitmap);/*获取图片*/
            String path = save2Bitmap(bitmap, fileName);
            putSetString(context, path);
            if (needRecycle) {
                bitmap.recycle();
            }


        }
    }

    //这是webview的，利用了webview的api
    public void captureWebView(Context context, WebView webView, String fileName, Boolean needRecycle) {
        Picture snapShot = webView.capturePicture();
        Bitmap bmp = Bitmap.createBitmap(snapShot.getWidth(),
                snapShot.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);
        snapShot.draw(canvas);
        if (bmp != null) {
            bmp = compressBitmap(bmp);/*获取图片*/
//            save2Bitmap(bmp, fileName);
            String path = save2Bitmap(bmp, fileName);
            putSetString(context, path);
            if (needRecycle) {
                bmp.recycle();
            }

        }
    }

    //本地图片
    public void getLocalPng(Context context, Bitmap bmp, String fileName, Boolean needRecycle) {
        String path = save2Bitmap(bmp, fileName);
        putSetString(context, path);
        if (needRecycle) {
            bmp.recycle();
        }
    }

    /**
     * 删除 所有的 截取的图片
     *
     * @return
     */
    public boolean deleteAllPng(Context context) {
        if (paths == null) {
            return false;
        }
        if (paths.size() == 0) {
            return false;
        }
        for (String path : paths) {
            FileUtil.deleteFolder(path);
        }
        resetSetString(context);/*将set 集合置空*/
        return true;
    }


    public int getScale(int oldWidth, int oldHeight, int newWidth, int newHeight) {
        if ((oldHeight > newHeight && oldWidth > newWidth)
                || (oldHeight <= newHeight && oldWidth > newWidth)) {
            int be = (int) (oldWidth / (float) newWidth);
            if (be <= 1)
                be = 1;
            return be;
        } else if (oldHeight > newHeight && oldWidth <= newWidth) {
            int be = (int) (oldHeight / (float) newHeight);
            if (be <= 1)
                be = 1;
            return be;
        }
        return 1;
    }


    public String save2Bitmap(Bitmap btp, String filename) {
        // 图片路径---获取内置SD卡路径
        String sdCardPath = Environment.getExternalStorageDirectory().getPath();
        // 图片名字 -- 文件路径  名字+时间戳
        filename = filename + TimeTools.getNowDateStr() + ".png";
        File tempFile = new File(sdCardPath, filename);/*获取文件*/
        String filePath = tempFile.getAbsolutePath();/*获取文件路径*/
        try {
            FileOutputStream fileOut = new FileOutputStream(tempFile);
            btp.compress(Bitmap.CompressFormat.JPEG, 100, fileOut);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return filePath;
    }
    //本地图片
    public void getLocalPng2(Context context, Bitmap bmp, String fileName, Boolean needRecycle) {
        String path = saveBitmap2(bmp, fileName);
        putSetString(context, path);
        if (needRecycle) {
            bmp.recycle();
        }
    }
    public String saveBitmap2(Bitmap btp, String filename) {
        // 图片路径---获取内置SD卡路径
        String sdCardPath = Environment.getExternalStorageDirectory().getPath();
        // 图片名字 -- 文件路径  名字+时间戳
        filename = filename + "webview"+ ".png";
        File tempFile = new File(sdCardPath, filename);/*获取文件*/
        String filePath = tempFile.getAbsolutePath();/*获取文件路径*/
        try {
            FileOutputStream fileOut = new FileOutputStream(tempFile);
            btp.compress(Bitmap.CompressFormat.JPEG, 100, fileOut);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return filePath;
    }
    public String saveBitmap(Bitmap btp, String path, String filename) {
        File tempFile = new File(path, filename);
        String filePath = tempFile.getAbsolutePath();
        try {
            FileOutputStream fileOut = new FileOutputStream(tempFile);
            btp.compress(Bitmap.CompressFormat.JPEG, 100, fileOut);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return filePath;
    }

    public Bitmap compressBitmap(Bitmap bitmap) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, output);
        int options = 100;
        while (output.toByteArray().length > 100 * 1024 && options != 10) {
            output.reset();
            options -= 10;
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, output);
        }
        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Bitmap cBitmap = BitmapFactory.decodeByteArray(result, 0, result.length);
        return cBitmap;
    }

    public byte[] readStream(InputStream inStream) throws Exception {
        byte[] buffer = new byte[1024];
        int len = -1;
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        byte[] data = outStream.toByteArray();
        outStream.close();
        inStream.close();
        return data;
    }

    public Bitmap scaleBitmap(Bitmap bmp, int width, int height) {
        int bmpWidth = bmp.getWidth();
        int bmpHeght = bmp.getHeight();
        Matrix matrix = new Matrix();
        matrix.postScale((float) width / bmpWidth, (float) height / bmpHeght);
        return Bitmap.createBitmap(bmp, 0, 0, bmpWidth, bmpHeght, matrix, true);
    }

    /*-------------------------------路径地址 ---数据存储在 sp 中--------------------------------------*/
    /*sp 的 key*/
    private final String key = "bitmaps";

    /**
     * 存储path集合
     * 每次进入 取set集合 ，将数据放入set集合
     *
     * @param context 上下文
     *                存储的键
     *                存储的集合
     */
    public void putSetString(Context context, String path) {
        paths = getSet(context);
        paths.add(path);
        SharedPreferences preferences = getSp(context);
        //存入数据
        SharedPreferences.Editor editor = preferences.edit();
        editor.putStringSet(key, paths);
        editor.commit();
    }

    /**
     * 存储path集合
     * 将 set 集合 置空
     *
     * @param context 上下文
     */
    public void resetSetString(Context context) {
        paths = getSet(context);
        paths.clear();
        SharedPreferences preferences = getSp(context);
        //存入数据
        SharedPreferences.Editor editor = preferences.edit();
        editor.putStringSet(key, paths);
        editor.commit();
    }

    /**
     * 获取List集合
     *
     * @param context 上下文
     *                key     键
     * @return List集合
     */
    public Set<String> getSet(Context context) {
        SharedPreferences preferences = getSp(context);
        return preferences.getStringSet(key, new HashSet<String>());
    }


}
