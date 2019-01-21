package com.base.common.image;

import android.graphics.Bitmap;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import android.content.Context;
import android.graphics.BitmapFactory;

/**
 * Created by zhaoyu on 2018/5/29.
 */

public class BitmapQuality {
    /**
     * 质量压缩方法
     */
    public static Bitmap compressImage(Bitmap mBitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 90;
        while (baos.toByteArray().length / 1024 > 128) { // 循环判断如果压缩后图片是否大于128kb,大于继续压缩
            baos.reset(); // 重置baos即清空baos
            mBitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;// 每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
        return bitmap;
    }
}
