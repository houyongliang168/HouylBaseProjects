package com.base.common.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


import com.base.common.tools.Base64;
import com.base.hyl.houbasemodule.tools.Base64;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;

/**
 * Created by zhaoyu on 2017/8/1.
 */

public class Image2String {
    /**
     * 图片转换成字符串[BASE64]
     *
     * @return
     */
    public static String getImageBinary(String path) {
        FileInputStream fis;

        try {
             fis = new FileInputStream(path);
//            int available = fis.available();
//            Logger.e("houyl"+available);
//            if(available/1024>=200){//如果大小大于200KB 压缩
//
//                fis.close();
//                return compressBitmap(path);
//            }
            /*小于 500 KB*/

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[8192];
            int count = 0;
            while ((count = fis.read(buffer)) >= 0) {
                baos.write(buffer, 0, count);
            }
            String uploadBuffer = new String(Base64.encodeBase64(baos.toByteArray()));
            fis.close();
            return uploadBuffer;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    public static String compressBitmap(String srcPath) {

        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath,newOpts);//此时返回bm为空
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, output);
        int options = 100;
        while (output.toByteArray().length > 150 * 1024 && options != 10) {
            output.reset();
            options -= 1;
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, output);
        }
        byte[] result = output.toByteArray();
        String uploadBuffer = new String(Base64.encodeBase64(result));

        if(bitmap!=null){
            bitmap.recycle();
        }
        return uploadBuffer;
    }

}
