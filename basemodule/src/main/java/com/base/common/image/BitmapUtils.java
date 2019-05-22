package com.base.common.image;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;


import com.base.common.log.MyLog;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by REN SHI QIAN on 2018/4/19.
 */

public class BitmapUtils {
    //将压缩后的图片保存的本地上指定路径中
    private static String TMP_DIR = Environment.getExternalStorageDirectory().getPath() + "/com/picture/h5_picture";
    private static String TAG_LOG = "BitmapUtil";

    /**
     *  压缩Bitmap（质量压缩法）并保存图片，返回图片在本地保存地址
     * @param bitmap
     * @param filename
     * @return
     */
    public static String compressAndSaveBitmap(Bitmap bitmap, String filename) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到output中
        MyLog.wtf(TAG_LOG, "图片压缩前：大小=" + (output.toByteArray().length / 1024) + "KB");
        MyLog.wtf(TAG_LOG, "图片压缩前：大小=" + (output.toByteArray().length / 1024 / 1024) + "M");
        int options = 100;
        while (output.toByteArray().length > 3 * 1024 * 1024 && options > 10) {// 循环判断如果压缩后图片是否大于2M,大于继续压缩
            output.reset();// 重置output即清空output
            options -= 10;// 每次都减少10
            MyLog.wtf(TAG_LOG, "图片压缩后：options=" + options);
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, output);// 这里压缩options%，把压缩后的数据存放到output中
            MyLog.wtf(TAG_LOG, "图片压缩后：大小=" + (output.toByteArray().length / 1024) + "KB");
            MyLog.wtf(TAG_LOG, "图片压缩后：大小=" + (output.toByteArray().length / 1024 / 1024) + "M");
        }
        MyLog.wtf(TAG_LOG, "图片处理完成!大小==" + output.toByteArray().length / 1024 / 1024+ "M");
        byte[] result = output.toByteArray();

        File dir = new File(TMP_DIR);
        if(!dir.exists()){
            dir.mkdirs();
        }
        File tempFile = new File(dir, filename);
        if(tempFile.exists()){
            tempFile.delete();
        }
        String filePath = tempFile.getAbsolutePath();
        try {
            FileOutputStream fileOut = new FileOutputStream(tempFile);
            fileOut.write(result);
            fileOut.flush();
            fileOut.close();
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 很重要
        if (bitmap != null) {
            bitmap.recycle();
        }
        return filePath;
    }

    /**
     * 压缩Bitmap（质量压缩法），返回bitmap
     * @param image
     * @return
     */
    public static Bitmap compressImageToBitmap(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        int options = 90;
        while (baos.toByteArray().length / 1024 > 100) {
            baos.reset();
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);
            options -= 10;
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
        return bitmap;
    }

    //public static List<String> drr = new ArrayList<String>();
    // TelephonyManager tm = (TelephonyManager) this
    // .getSystemService(Context.TELEPHONY_SERVICE);
    // 图片sd地址 上传服务器时把图片调用下面方法压缩后 保存到临时文件夹 图片压缩后小于100KB，失真度不明显
    public static Bitmap revitionImageSize(String path) throws IOException {
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(
                new File(path)));

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        // Bitmap btBitmap=BitmapFactory.decodeFile(path);
        // System.out.println("原尺寸高度："+btBitmap.getHeight());
        // System.out.println("原尺寸宽度："+btBitmap.getWidth());
        BitmapFactory.decodeStream(in, null, options);
        in.close();
        int i = 0;
        Bitmap bitmap = null;
        while (true) {
            if ((options.outWidth >> i <= 800)
                    && (options.outHeight >> i <= 800)) {
                in = new BufferedInputStream(
                        new FileInputStream(new File(path)));
                options.inSampleSize = (int) Math.pow(2.0D, i);
                options.inJustDecodeBounds = false;
                bitmap = BitmapFactory.decodeStream(in, null, options);
                break;
            }
            i += 1;
        }
        // 当机型为三星时图片翻转
//      bitmap = Photo.photoAdapter(path, bitmap);
//      System.out.println("-----压缩后尺寸高度：" + bitmap.getHeight());
//      System.out.println("-----压缩后尺寸宽度度：" + bitmap.getWidth());
        return bitmap;
    }



    public static Bitmap getLoacalBitmap(String url) {
        try {
            FileInputStream fis = new FileInputStream(url);
            return BitmapFactory.decodeStream(fis); // /把流转化为Bitmap图片

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 从Resources中加载图片
    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options); // 读取图片长款
        options.inSampleSize = getFitInSampleSize(options, reqWidth,
                reqHeight); // 计算inSampleSize
        options.inJustDecodeBounds = false;
        Bitmap src = BitmapFactory.decodeResource(res, resId, options); // 载入一个稍大的缩略图
        //return createScaleBitmap(src, reqWidth, reqHeight); // 进一步得到目标大小的缩略图
        return src;
    }

    // 从sd卡上加载图片
    public static Bitmap getFitSampleBitmapFromSdcard(String pathName, int reqWidth, int reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(pathName, options);
        options.inSampleSize = getFitInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inDither = true;
        Bitmap src = BitmapFactory.decodeFile(pathName, options);
        //return createScaleBitmap(src, reqWidth, reqHeight);
        return src;
    }

    /**
     * 通过Uri获取bitmap
     * @param context
     * @param fileName
     * @return
     * @throws IOException
     */
    public static Bitmap getBitmapFromMedia(Context context, String fileName) throws IOException {
        File file = new File(fileName);
        Uri photoUri = Uri.fromFile(file);
        ContentResolver resolver = context.getContentResolver();
        Bitmap bitmap = MediaStore.Images.Media.getBitmap(resolver, photoUri);
        return bitmap;
    }

    public static Bitmap getBitmapFromUri(Context context, Uri uri) {
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }

    /**
     * 根据文件路径获取bitmap资源
     * @return
     */
    public static Bitmap getBitmapByFileDescriptor(String path, int reqWidth, int reqHeight){
        FileInputStream fis = null;
        // 用decodeFileDescriptor()来生成bimap比decodeFile()省内存
        // decodeFile()最终是以流的方式生成bitmap ;decodeFileDescriptor通过底层生成bitmap
        FileDescriptor fdp = null;
        try {
            fis = new FileInputStream(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            fdp = fis.getFD();
        } catch (IOException e) {
            e.printStackTrace();
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap bitmap1 = BitmapFactory.decodeFileDescriptor(fdp, null, options);//此时返回 bm 为空
        options.inJustDecodeBounds = false;
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        options.inSampleSize = getFitInSampleSize(options, reqWidth, reqHeight);
        //重新读入图片，注意此时已经把 options.inJustDecodeBounds 设回 false 了
        Bitmap bitmap2 = BitmapFactory.decodeFileDescriptor(fdp, null, options);//此时返回 bm 为空
        return bitmap2;
    }

    /**
     * 计算压缩比
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    private static int getFitInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        /*if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }*/
        if(height > reqHeight || width > reqWidth){
            int widthRatio = Math.round((float)width / (float)reqWidth);
            int heightRatio = Math.round((float)height / (float)reqHeight);
            inSampleSize = Math.min(widthRatio, heightRatio);
        }
        MyLog.wtf(TAG_LOG, "计算压缩比inSampleSize=" + inSampleSize);
        return inSampleSize;
    }

    /**
     * 将InputStream转换为Byte数组
     * @param in
     * @return
     */
    public static byte[] inputStreamToByteArray(InputStream in)
    {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        try {
            while((len = in.read(buffer)) != -1)
            {
                outputStream.write(buffer, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                in.close();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return outputStream.toByteArray();
    }




    /**
     * 以src为原图，创建新的图像，指定新图像的高宽以及是否可变
     * @param src
     * @param dstWidth
     * @param dstHeight
     * @return
     */
    public static Bitmap createScaleBitmap(Bitmap src, int dstWidth, int dstHeight) {
        Bitmap dst = Bitmap.createScaledBitmap(src, dstWidth, dstHeight, true);
        if (src != dst) { // 如果没有缩放，那么不回收
            src.recycle(); // 释放Bitmap的native像素数组
        }
        return dst;
    }



    // 从sd卡上加载图片 并旋转为
    public static Bitmap getFitSampleBitmapFromSdcardRotaing(String pathName, int reqWidth, int reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(pathName, options);
        options.inSampleSize = getFitInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inDither = true;
       int  angle= readPictureDegree(pathName);
        Bitmap src = BitmapFactory.decodeFile(pathName, options);
        // 旋转图片 动作
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(src, 0, 0,
                src.getWidth(), src.getHeight(), matrix, true);

        return resizedBitmap;
    }


    /**
     * 读取图片属性：旋转的角度
     *
     * @param path 图片绝对路径
     * @return degree旋转的角度
     */
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /*
    * Uri originalUri = null;
      File file = null;
      if (null != data && data.getData() != null) {
          originalUri = data.getData();
          file = getFileFromMediaUri(ac, originalUri);
      }
 Bitmap photoBmp = getBitmapFormUri(ac, Uri.fromFile(file));
  int degree = getBitmapDegree(file.getAbsolutePath());

   //把图片旋转为正的方向

    Bitmap newbitmap = rotateBitmapByDegree(photoBmp, degree);
    * */

    /**
     * 通过Uri获取文件
     * @param ac
     * @param uri
     * @return
     */
    public static File getFileFromMediaUri(Context ac, Uri uri) {
        if(uri.getScheme().toString().compareTo("content") == 0){
            ContentResolver cr = ac.getContentResolver();
            Cursor cursor = cr.query(uri, null, null, null, null);// 根据Uri从数据库中找
            if (cursor != null) {
                cursor.moveToFirst();
                String filePath = cursor.getString(cursor.getColumnIndex("_data"));// 获取图片路径
                cursor.close();
                if (filePath != null) {
                    return new File(filePath);
                }
            }
        }else if(uri.getScheme().toString().compareTo("file") == 0){
            return new File(uri.toString().replace("file://",""));
        }
        return null;
    }

    /**
     * 将图片按照某个角度进行旋转
     *
     * @param bm     需要旋转的图片
     * @param degree 旋转角度
     * @return 旋转后的图片
     */
    public static Bitmap rotateBitmapByDegree(Bitmap bm, int degree) {
        Bitmap returnBm = null;

        // 根据旋转角度，生成旋转矩阵
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        try {
            // 将原始图片按照旋转矩阵进行旋转，并得到新的图片
            returnBm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
        } catch (OutOfMemoryError e) {
        }
        if (returnBm == null) {
            returnBm = bm;
        }
        if (bm != returnBm) {
            bm.recycle();
        }
        return returnBm;
    }


    /**
     * 读取图片的旋转的角度
     *
     * @param path 图片绝对路径
     * @return 图片的旋转角度
     */
    public static int getBitmapDegree(String path) {
        int degree = 0;
        try {
            // 从指定路径下读取图片，并获取其EXIF信息
            ExifInterface exifInterface = new ExifInterface(path);
            // 获取图片的旋转信息
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 通过Uri获取filepath
     * @param
     * @param uri
     * @return
     */
    public static String getRealFilePath(final Context context, final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }
    /**
     * 获取视频首帧图并转化为bitmap
     * @param videoUrl
     * @return
     */
    private Bitmap voidToFirstBitmap(String videoUrl){
        MediaMetadataRetriever metadataRetriever = new MediaMetadataRetriever();
        metadataRetriever.setDataSource(videoUrl);
        Bitmap bitmap = metadataRetriever.getFrameAtTime();
        return bitmap;
    }
    // 获取视频缩略图
    public static Bitmap getVideoThumbnail(String filePath) {
        Bitmap bitmap1 = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            retriever.setDataSource(filePath);
            bitmap1 = retriever.getFrameAtTime();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
            e.printStackTrace();

        } finally {
            try {
                retriever.release();
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
        return bitmap1;
    }


    public static String compressAndSaveBitmap2down(Bitmap bitmap, String filename,int down) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到output中
        MyLog.wtf(TAG_LOG, "图片压缩前：大小=" + (output.toByteArray().length / 1024) + "KB");
        MyLog.wtf(TAG_LOG, "图片压缩前：大小=" + (output.toByteArray().length / 1024 / 1024) + "M");
        int options = 100;
        while (output.toByteArray().length > 200 * 1024 && options > 10) {// 循环判断如果压缩后图片是否大于200KB,大于继续压缩
            output.reset();// 重置output即清空output
            options -= down;// 每次都减少10
            MyLog.wtf(TAG_LOG, "图片压缩后：options=" + options);
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, output);// 这里压缩options%，把压缩后的数据存放到output中
            MyLog.wtf(TAG_LOG, "图片压缩后：大小=" + (output.toByteArray().length / 1024) + "KB");
            MyLog.wtf(TAG_LOG, "图片压缩后：大小=" + (output.toByteArray().length / 1024 / 1024) + "M");
        }
        MyLog.wtf(TAG_LOG, "图片处理完成!大小==" + output.toByteArray().length / 1024 / 1024+ "M");
        byte[] result = output.toByteArray();

        File dir = new File(TMP_DIR);
        if(!dir.exists()){
            dir.mkdirs();
        }
        File tempFile = new File(dir, filename);
        if(tempFile.exists()){
            tempFile.delete();
        }
        String filePath = tempFile.getAbsolutePath();
        try {
            FileOutputStream fileOut = new FileOutputStream(tempFile);
            fileOut.write(result);
            fileOut.flush();
            fileOut.close();
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 很重要
        if (bitmap != null) {
            bitmap.recycle();
        }
        return filePath;
    }
}
