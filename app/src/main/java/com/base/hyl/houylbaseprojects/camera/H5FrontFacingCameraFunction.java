package com.base.hyl.houylbaseprojects.camera;


import android.annotation.TargetApi;
import android.content.Context;
import android.hardware.Camera;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.util.Log;

/**
 * 调用前置摄像头的方法
 */
public class H5FrontFacingCameraFunction {

    private static String TAG=H5FrontFacingCameraFunction.class.getSimpleName();
   // 属性 hasfrontFacingCamera  是否有前置摄像头
    public static  boolean hasfrontFacingCamera=false;

    private H5FrontFacingCameraFunction() {

    }

    private static class H5FrontFacingCameraFunctionBuilder {
        private static H5FrontFacingCameraFunction instance = new H5FrontFacingCameraFunction();
    }

    public static H5FrontFacingCameraFunction getInstance() {
        return H5FrontFacingCameraFunction.H5FrontFacingCameraFunctionBuilder.instance;
    }

    //判断是否有前置摄像头
    public static boolean hasfrontFacingCamera(Context context) {
        boolean hasfrontCamera = false;
        if(hasCamera2(context)){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                CameraManager  mCameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
                try {
                    final String[] ids = mCameraManager.getCameraIdList();
                    int  numberOfCameras = ids.length;
                    for (String id : ids) {
                        final CameraCharacteristics characteristics = mCameraManager.getCameraCharacteristics(id);

                        final int orientation = characteristics.get(CameraCharacteristics.LENS_FACING);
                        if (orientation == CameraCharacteristics.LENS_FACING_FRONT) {
                            hasfrontCamera=true;
                            hasfrontFacingCamera=true;
                            return hasfrontCamera;
                        } else {
                        }
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Error during camera initialize");
                }
            }
        }else{
            //有多少个摄像头
          int  numberOfCameras = Camera.getNumberOfCameras();
            for (int i = 0; i < numberOfCameras; ++i) {
                final Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
                Camera.getCameraInfo(i, cameraInfo);
                //前置摄像头
                 if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                    hasfrontCamera= true;
                     hasfrontFacingCamera=true;
                    return hasfrontCamera;
                }else if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) { } //后置摄像头
            }
        }
        return hasfrontCamera;
    }

    //判断是否有Camera2
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static boolean hasCamera2(Context mContext) {
        if (mContext == null) return false;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) return false;
        try {
            CameraManager manager = (CameraManager) mContext.getSystemService(Context.CAMERA_SERVICE);
            String[] idList = manager.getCameraIdList();
            boolean notFull = true;
            if (idList.length == 0) {
                notFull = false;
            } else {
                for (final String str : idList) {
                    if (str == null || str.trim().isEmpty()) {
                        notFull = false;
                        break;
                    }
                    final CameraCharacteristics characteristics = manager.getCameraCharacteristics(str);

                    final int supportLevel = characteristics.get(CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL);
                    if (supportLevel == CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL_LEGACY) {
                        notFull = false;
                        break;
                    }else if (supportLevel == CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL_LEGACY) {
                        notFull = false;
                        break;
                    }
                }
            }
            return notFull;
        } catch (Throwable ignore) {
            return false;
        }
    }







}
