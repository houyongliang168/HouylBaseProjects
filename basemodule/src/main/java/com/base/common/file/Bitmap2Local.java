package com.base.common.file;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by zhaoyu on 2017/8/8.类说明：bitmap图片保存本地
 */

public class Bitmap2Local {
    /**
     * 保存方法
     */
    public static void saveBitmap(Bitmap bm, String picName) {
        File f = new File(Environment.getExternalStorageDirectory(), picName);
        if (f.exists()) {
            f.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void savePic(Bitmap bm, String picName) {
        File f = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/com.taikanglife.isalessystem/" + picName);
        if (f.exists()) {
            f.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 质量压缩
     *
     * @param bitmap
     * @param outPath
     * @param maxSize
     * @return
     */
    public static String qualityCompressImg(Bitmap bitmap, String outPath, int maxSize) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        float option = 100f;
        bitmap.compress(Bitmap.CompressFormat.JPEG, (int) option, byteArrayOutputStream);
        while (byteArrayOutputStream.toByteArray().length / 1024 > maxSize) {
            byteArrayOutputStream.reset();
            option *= 0.9f;
            if (option < 1) {
                break;
            }
            bitmap.compress(Bitmap.CompressFormat.JPEG, (int) option, byteArrayOutputStream);
        }
        try {
            FileOutputStream outputStream = new FileOutputStream(outPath);
            outputStream.write(byteArrayOutputStream.toByteArray());
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outPath;
    }

    /**
     * 尺寸压缩
     * @param path
     * @param viewWidth
     * @param viewHeight
     * @return
     */
    public static Bitmap ratioBitmap(String path, int viewWidth, int viewHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 1;
        options.inJustDecodeBounds = true;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        BitmapFactory.decodeFile(path, options);
        int h = options.outHeight;
        int w = options.outWidth;
        int inSampleSize = (int) Math.max(h * 1.0 / viewWidth, w * 1.0 / viewHeight);
        if (inSampleSize <= 0) {
            inSampleSize = 1;
        }
        options.inJustDecodeBounds = false;
        options.inSampleSize = inSampleSize;
        return BitmapFactory.decodeFile(path, options);
    }

}
