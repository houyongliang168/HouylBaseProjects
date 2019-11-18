/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.base.hyl.houylbaseprojects.camera.camera;

import android.annotation.SuppressLint;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.support.v4.util.SparseArrayCompat;
import android.view.SurfaceHolder;

import com.base.hyl.houylbaseprojects.camera.material.AspectRatio;

import com.base.hyl.houylbaseprojects.camera.material.Constants;
import com.base.hyl.houylbaseprojects.camera.material.Size;

import com.base.hyl.houylbaseprojects.camera.material.SizeMap;
import com.base.hyl.houylbaseprojects.camera.preview.PreviewImpl;


import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.concurrent.atomic.AtomicBoolean;


@SuppressWarnings("deprecation")
public class Camera1 extends CameraViewImpl {
    /**
     * Camera; //操作和管理相机资源，支持相机资源切换，设置预览和拍摄尺寸，设置光圈、曝光等相关参数。
     *
     * SurfaceView：用于绘制相机预览图像，提供实时预览的图像。
     *
     * SurfaceHolder：用于控制Surface的一个抽象接口，它可以控制Surface的尺寸、格式与像素等，并可以监视Surface的变化。
     *
     * SurfaceHolder.Callback：用于监听Surface状态变化的接口。
     */
    private static final int INVALID_CAMERA_ID = -1;
    /* 基本参数 获取*/
    private static final SparseArrayCompat<String> FLASH_MODES = new SparseArrayCompat<>();

    static {
        FLASH_MODES.put(Constants.FLASH_OFF, Camera.Parameters.FLASH_MODE_OFF);
        FLASH_MODES.put(Constants.FLASH_ON, Camera.Parameters.FLASH_MODE_ON);
        FLASH_MODES.put(Constants.FLASH_TORCH, Camera.Parameters.FLASH_MODE_TORCH);
        FLASH_MODES.put(Constants.FLASH_AUTO, Camera.Parameters.FLASH_MODE_AUTO);
        FLASH_MODES.put(Constants.FLASH_RED_EYE, Camera.Parameters.FLASH_MODE_RED_EYE);
    }


    //图片捕捉 线程处理逻辑
    private final AtomicBoolean isPictureCaptureInProgress = new AtomicBoolean(false);

    public  Camera mCamera; //操作和管理相机资源，支持相机资源切换，设置预览和拍摄尺寸，设置光圈、曝光等相关参数。

    private Camera.Parameters mCameraParameters;

    private final Camera.CameraInfo mCameraInfo = new Camera.CameraInfo();//相机相关信息

    private final SizeMap mPreviewSizes = new SizeMap();// 摄像头预览可设置的大小

    private final SizeMap mPictureSizes = new SizeMap();//摄像头 拍照可设置的大小

    private AspectRatio mAspectRatio;//配置的属性信息 纵横比

    private boolean mShowingPreview;//是否展示预览

    private boolean mAutoFocus;

    private int mFacing;//设置 摄像头  前置摄像头还是 后置摄像头

    private int mCameraId; // 与 Facing 对应的摄像头ID
    private int mFlash;

    private int mDisplayOrientation;

    public Camera1(Callback callback, PreviewImpl preview) {
        super(callback, preview);
        preview.setCallback(new PreviewImpl.Callback() {
            @Override
            public void onSurfaceChanged() {//表面改变内容的时候
                if (mCamera != null) {
                    setUpPreview();
                    adjustCameraParameters();
                }
            }
        });
    }

    @Override
    public boolean start() {
        chooseCamera();
        openCamera();
        if (mPreview.isReady()) {
            setUpPreview();//设置展示的控件
        }
        mShowingPreview = true;
        mCamera.startPreview();//开启预览
        return true;
    }

    @Override
    public void stop() {
        if (mCamera != null) {
            mCamera.stopPreview();//关闭预览
        }
        mShowingPreview = false;
        releaseCamera();
    }

    // Suppresses Camera#setPreviewTexture
    //设置预览界面 必须surface change 状态改变
    @SuppressLint("NewApi")
    public void setUpPreview() {
        try {
            if (mPreview.getOutputClass() == SurfaceHolder.class) {
                mCamera.setPreviewDisplay(mPreview.getSurfaceHolder());
            } else {
                mCamera.setPreviewTexture((SurfaceTexture) mPreview.getSurfaceTexture());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    /* camera 是否开启*/
    @Override
    public  boolean isCameraOpened() {
        return mCamera != null;
    }
    /* 设置 摄像头 */
    @Override
    public void setFacing(int facing) {
        if (mFacing == facing) {
            return;
        }
        mFacing = facing;
        if (isCameraOpened()) {
            stop();
            start();
        }
    }

    @Override
    public int getFacing() {
        return mFacing;
    }

    @Override
    public  Set<AspectRatio> getSupportedAspectRatios() {
        SizeMap idealAspectRatios = mPreviewSizes;
        for (AspectRatio aspectRatio : idealAspectRatios.ratios()) {
            if (mPictureSizes.sizes(aspectRatio) == null) {
                idealAspectRatios.remove(aspectRatio);
            }
        }
        return idealAspectRatios.ratios();
    }
    /* 设置纵横比*/
    @Override
    public  boolean setAspectRatio(AspectRatio ratio) {
        if (mAspectRatio == null || !isCameraOpened()) {
            // Handle this later when camera is opened
            mAspectRatio = ratio;
            return true;
        } else if (!mAspectRatio.equals(ratio)) {
            final Set<Size> sizes = mPreviewSizes.sizes(ratio);
            if (sizes == null) {
                throw new UnsupportedOperationException(ratio + " is not supported");
            } else {
                mAspectRatio = ratio;
                adjustCameraParameters();
                return true;
            }
        }
        return false;
    }
    /* 获取纵横比*/
    @Override
    public  AspectRatio getAspectRatio() {
        return mAspectRatio;
    }
    /* 设置对焦*/
    @Override
    public void setAutoFocus(boolean autoFocus) {
        if (mAutoFocus == autoFocus) {
            return;
        }
        if (setAutoFocusInternal(autoFocus)) {
            mCamera.setParameters(mCameraParameters);
        }
    }

    @Override
    public  boolean getAutoFocus() {
        if (!isCameraOpened()) {
            return mAutoFocus;
        }
        String focusMode = mCameraParameters.getFocusMode();
        return focusMode != null && focusMode.contains("continuous");
    }

    @Override
    public  void setFlash(int flash) {
        if (flash == mFlash) {
            return;
        }
        if (setFlashInternal(flash)) {
            mCamera.setParameters(mCameraParameters);
        }
    }

    @Override
    public int getFlash() {
        return mFlash;
    }

    @Override
    public void takePicture() {
        if (!isCameraOpened()) {
            throw new IllegalStateException(
                    "Camera is not ready. Call start() before takePicture().");
        }
        if (getAutoFocus()) {
            mCamera.cancelAutoFocus();
            mCamera.autoFocus(new Camera.AutoFocusCallback() {
                @Override
                public void onAutoFocus(boolean success, Camera camera) {
                    takePictureInternal();
                }
            });
        } else {
            takePictureInternal();
        }
    }

    public void takePictureInternal() {
        if (!isPictureCaptureInProgress.getAndSet(true)) {
            mCamera.takePicture(null, null, null, new Camera.PictureCallback() {
                @Override
                public void onPictureTaken(byte[] data, Camera camera) {
                    isPictureCaptureInProgress.set(false);
                    mCallback.onPictureTaken(data);
                    camera.cancelAutoFocus();
                    camera.startPreview();
                }
            });
        }
    }
    /* 设置角度*/
    @Override
    public  void setDisplayOrientation(int displayOrientation) {
        if (mDisplayOrientation == displayOrientation) {
            return;
        }
        mDisplayOrientation = displayOrientation;
        if (isCameraOpened()) {
            /* 设置旋转 这两个计算的方法不同 ??? */
            mCameraParameters.setRotation(calcCameraRotation(displayOrientation));
            mCamera.setParameters(mCameraParameters);
            mCamera.setDisplayOrientation(calcDisplayOrientation(displayOrientation));
        }
    }

    /**
     * This rewrites {@link #mCameraId} and {@link #mCameraInfo}.
     * 选择 摄像头
     */
    private void chooseCamera() {
        for (int i = 0, count = Camera.getNumberOfCameras(); i < count; i++) {
            Camera.getCameraInfo(i, mCameraInfo);//获取摄像头的信心
            if (mCameraInfo.facing == mFacing) {
                mCameraId = i;
                return;
            }
        }
        mCameraId = INVALID_CAMERA_ID;
    }

    /**
     *  开启摄像头
     */
    private void openCamera() {
        //释放摄像头
        if (mCamera != null) {
            releaseCamera();
        }
        //打开需要的摄像头
        mCamera = Camera.open(mCameraId);
        mCameraParameters = mCamera.getParameters();
        //获取相机 可设置的 预览 大小 和 拍摄的照片大小
        // Supported preview sizes
        mPreviewSizes.clear(); //预览内容清空
        for (Camera.Size size : mCameraParameters.getSupportedPreviewSizes()) {
            mPreviewSizes.add(new Size(size.width, size.height));
        }
        // Supported picture sizes;
        mPictureSizes.clear();
        for (Camera.Size size : mCameraParameters.getSupportedPictureSizes()) {
            mPictureSizes.add(new Size(size.width, size.height));
        }
        // AspectRatio
        if (mAspectRatio == null) {
            mAspectRatio = Constants.DEFAULT_ASPECT_RATIO;
        }
        adjustCameraParameters();
        /* 设置 获取图片的方向*/
        mCamera.setDisplayOrientation(calcDisplayOrientation(mDisplayOrientation));
        mCallback.onCameraOpened();//回调相机打开
    }
    //关闭 比例
    private AspectRatio chooseAspectRatio() {
        AspectRatio r = null;
        for (AspectRatio ratio : mPreviewSizes.ratios()) {
            r = ratio;
            if (ratio.equals(Constants.DEFAULT_ASPECT_RATIO)) {
                return ratio;
            }
        }
        return r;
    }

    //调整相机参数
    void adjustCameraParameters() {
        SortedSet<Size> sizes = mPreviewSizes.sizes(mAspectRatio);
        if (sizes == null) { // Not supported
            mAspectRatio = chooseAspectRatio();
            sizes = mPreviewSizes.sizes(mAspectRatio);
        }
        Size size = chooseOptimalSize(sizes);

        // Always re-apply camera parameters
        // Largest picture size in this ratio
        final Size pictureSize = mPictureSizes.sizes(mAspectRatio).last();
        if (mShowingPreview) {//如果正在预览 停止预览
            mCamera.stopPreview();
        }
        /* 设置预览的大小 和 生成图片的大小*/
        mCameraParameters.setPreviewSize(size.getWidth(), size.getHeight());
        mCameraParameters.setPictureSize(pictureSize.getWidth(), pictureSize.getHeight());
        mCameraParameters.setRotation(calcCameraRotation(mDisplayOrientation));
        /* 设置内部对焦  */
        setAutoFocusInternal(mAutoFocus);
        /* 设置内部闪光*/
        setFlashInternal(mFlash);
        mCamera.setParameters(mCameraParameters);
        /* 开启预览*/
        if (mShowingPreview) {
            mCamera.startPreview();
        }
    }

    @SuppressWarnings("SuspiciousNameCombination")
    private Size chooseOptimalSize(SortedSet<Size> sizes) {// 选择最优的 size 大小
        if (!mPreview.isReady()) { // Not yet laid out
            return sizes.first(); // Return the smallest size
        }
        int desiredWidth;
        int desiredHeight;
        final int surfaceWidth = mPreview.getWidth();
        final int surfaceHeight = mPreview.getHeight();
        if (isLandscape(mDisplayOrientation)) {//横着
            desiredWidth = surfaceHeight;
            desiredHeight = surfaceWidth;
        } else {//竖着
            desiredWidth = surfaceWidth;
            desiredHeight = surfaceHeight;
        }
        Size result = null;
        for (Size size : sizes) { // Iterate from small to large
            if (desiredWidth <= size.getWidth() && desiredHeight <= size.getHeight()) {
                return size;

            }
            result = size;
        }
        //没有合适的就是最后的那个size
        return result;
    }
    /* 释放相机资源*/
    private void releaseCamera() {
        if (mCamera != null) {
            mCamera.release();
            mCamera = null;
            mCallback.onCameraClosed();
        }
    }

    /**
     * Calculate display orientation
     * 计算展示的 方向  预览的view
     * https://developer.android.com/reference/android/hardware/Camera.html#setDisplayOrientation(int)
     *
     * This calculation is used for orienting the preview
     *
     * Note: This is not the same calculation as the camera rotation
     *
     * @param screenOrientationDegrees Screen orientation in degrees
     * @return Number of degrees required to rotate preview
     */
    private int calcDisplayOrientation(int screenOrientationDegrees) {
        if (mCameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {//前置摄像头
            return (360 - (mCameraInfo.orientation + screenOrientationDegrees) % 360) % 360;
        } else {  // back-facing
            return (mCameraInfo.orientation - screenOrientationDegrees + 360) % 360;
        }
    }

    /**
     * 计算旋转角度
     *
     * 最终的照片的角度
     * Calculate camera rotation
     *
     * This calculation is applied to the output JPEG either via Exif Orientation tag
     * or by actually transforming the bitmap. (Determined by vendor camera API implementation)
     *
     * Note: This is not the same calculation as the display orientation
     *
     * @param screenOrientationDegrees Screen orientation in degrees
     * @return Number of degrees to rotate image in order for it to view correctly.
     */
    private int calcCameraRotation(int screenOrientationDegrees) {
        if (mCameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {//前置摄像头
            return (mCameraInfo.orientation + screenOrientationDegrees) % 360;
        } else {  // back-facing 后置摄像头 角度是  90 和270 设置为180
            final int landscapeFlip = isLandscape(screenOrientationDegrees) ? 180 : 0;
            return (mCameraInfo.orientation + screenOrientationDegrees + landscapeFlip) % 360;
        }
    }

    /**
     * Test if the supplied orientation is in landscape.
     *
     * @param orientationDegrees Orientation in degrees (0,90,180,270)
     * @return True if in landscape, false if portrait
     */
    private boolean isLandscape(int orientationDegrees) {
        return (orientationDegrees == Constants.LANDSCAPE_90 ||
                orientationDegrees == Constants.LANDSCAPE_270);
    }

    /**
     * @return {@code true} if {@link #mCameraParameters} was modified.
     * 设置内部 自动对焦  ?? 自动拍照 怎么是连续拍照
     */
    private boolean setAutoFocusInternal(boolean autoFocus) {
        mAutoFocus = autoFocus;
        if (isCameraOpened()) {//如果相机开着
            final List<String> modes = mCameraParameters.getSupportedFocusModes();
//            if (autoFocus && modes.contains(Camera.Parameters.FOCUS_MODE_AUTO )) {//自动对焦模式
//                mCameraParameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO );
//            }else
            if (autoFocus && modes.contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {//连续拍照模式
                mCameraParameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
            } else if (modes.contains(Camera.Parameters.FOCUS_MODE_FIXED)) {//固定焦距模式，拍摄老司机模式；
                mCameraParameters.setFocusMode(Camera.Parameters.FOCUS_MODE_FIXED);
            } else if (modes.contains(Camera.Parameters.FOCUS_MODE_INFINITY)) {//远景模式，拍风景大场面的模式；
                mCameraParameters.setFocusMode(Camera.Parameters.FOCUS_MODE_INFINITY);
            } else {
                mCameraParameters.setFocusMode(modes.get(0));
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * @return {@code true} if {@link #mCameraParameters} was modified.
     */
    private boolean setFlashInternal(int flash) {
        if (isCameraOpened()) {
            List<String> modes = mCameraParameters.getSupportedFlashModes();
            String mode = FLASH_MODES.get(flash);
            if (modes != null && modes.contains(mode)) {
                mCameraParameters.setFlashMode(mode);
                mFlash = flash;
                return true;
            }
            String currentMode = FLASH_MODES.get(mFlash);
            if (modes == null || !modes.contains(currentMode)) {
                mCameraParameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                mFlash = Constants.FLASH_OFF;
                return true;
            }
            return false;
        } else {
            mFlash = flash;
            return false;
        }
    }

}
