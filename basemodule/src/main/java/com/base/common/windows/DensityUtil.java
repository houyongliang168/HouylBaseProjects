package com.base.common.windows;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;


import com.base.common.string.StringUtil;

import java.lang.reflect.Method;

public class DensityUtil {


    public static void setTvWidth(TextView tv, String maxStr, String defaultStr) {
        ViewGroup.LayoutParams layoutParams = tv.getLayoutParams();
        int oldWidth = layoutParams.width;
        //在原来的宽度和计算出的最大宽度之间取大值
        layoutParams.width = Math.max((int) StringUtil.getTextViewLength(tv, defaultStr) + 1, Math.max((int) StringUtil.getTextViewLength(tv, maxStr) + 1, oldWidth));
//		layoutParams.width *=2;
        tv.setLayoutParams(layoutParams);
    }

    @SuppressLint("NewApi")
    public static ScreenInfo getScreenInfo(Context mContext) {
        WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        int realWidth;
        int realHeight;
        DisplayMetrics realMetrics = new DisplayMetrics();
        if (Build.VERSION.SDK_INT >= 17) {
            //new pleasant way to get real metrics
            display.getRealMetrics(realMetrics);
            realWidth = realMetrics.widthPixels;
            realHeight = realMetrics.heightPixels;

        } else if (Build.VERSION.SDK_INT >= 14) {
            //reflection for this weird in-between time
            try {
                Method mGetRawH = Display.class.getMethod("getRawHeight");
                Method mGetRawW = Display.class.getMethod("getRawWidth");
                realWidth = (Integer) mGetRawW.invoke(display);
                realHeight = (Integer) mGetRawH.invoke(display);
            } catch (Exception e) {
                //this may not be 100% accurate, but it's all we've got
                display.getMetrics(realMetrics);
                realWidth = realMetrics.widthPixels;
                realHeight = realMetrics.heightPixels;
            }

        } else {
            //This should be close, as lower API devices should not have window navigation bars
            display.getMetrics(realMetrics);
            realWidth = realMetrics.widthPixels;
            realHeight = realMetrics.heightPixels;
        }
        int screenWidth = realWidth;
        int screenHeight = realHeight;
        return new ScreenInfo(screenWidth, screenHeight);
    }
//	/**
//	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
//	 */
//	public static int dip2px(Context context, float dpValue) {
//		final float scale = context.getResources().getDisplayMetrics().density;
//		return (int) (dpValue * scale + 0.5f);
//	}
//
//	/**
//	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
//	 */
//	public static int px2dip(Context context, float pxValue) {
//		final float scale = context.getResources().getDisplayMetrics().density;
//		return (int) (pxValue / scale + 0.5f);
//	}

    public static class ScreenInfo {

        private final int screenWidth;
        private final int screenHeight;

        private ScreenInfo(int screenWidth, int screenHeight) {
            this.screenWidth = screenWidth;
            this.screenHeight = screenHeight;
        }

        public int getScreenWidth() {
            return screenWidth;
        }

        public int getScreenHeight() {
            return screenHeight;
        }

    }

    public static class DimensionInfo {

        private final int width;
        private final int height;

        private DimensionInfo(int width, int height) {
            this.width = width;
            this.height = height;
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }

    }

    public static DimensionInfo getImageDimensionInfo(Context mContext, int imgResId) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        /**
         * 最关键在此，把options.inJustDecodeBounds = true;
         * 这里再decodeFile()，返回的bitmap为空，但此时调用options.outHeight时，已经包含了图片的高了
         */
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(mContext.getResources(), imgResId, options); // 此时返回的bitmap为null
        return new DimensionInfo(options.outWidth, options.outHeight);
    }




    // 屏幕宽度（像素）
    public static int getWindowWidth(Activity context) {
        DisplayMetrics metric = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(metric);
        return metric.widthPixels;
    }

    // 屏幕高度（像素）
    public static int getWindowHeight(Activity context) {
        DisplayMetrics metric = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(metric);
        return metric.heightPixels;
    }



    public static float getDensity(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.density;
    }

    public static float getDensityDpi(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.densityDpi;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /*----------------------------------*/
    /**
     * dp转px
     * @param context
     * @param dpVal
     * @return
     */
    public static int dp2px(Context context, float dpVal) {
        return (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, getDisplayMetrics(context));
    }

    /**
     * sp转px
     * @param context
     * @param spVal
     * @return
     */
    public static int sp2px(Context context, float spVal) {
        return (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_SP, spVal, getDisplayMetrics(context));
    }

    /**
     * px转dp
     * @param context
     * @param pxVal
     * @return
     */
    public static int px2dp(Context context, float pxVal) {
        return (int) (pxVal / getDisplayMetrics(context).density + 0.5f);
    }

    /**
     * px转sp
     * @param context
     * @param pxVal
     * @return
     */
    public static int px2sp(Context context, float pxVal) {
        return (int) (pxVal / getDisplayMetrics(context).scaledDensity + 0.5f);
    }


    /**
     * 获取DisplayMetrics
     * @param context
     * @return
     */
    public static DisplayMetrics getDisplayMetrics(Context context) {
        return context.getResources().getDisplayMetrics();
    }

    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }
}
