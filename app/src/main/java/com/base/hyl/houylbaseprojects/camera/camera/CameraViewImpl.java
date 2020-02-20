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

import android.view.View;

import com.base.hyl.houylbaseprojects.camera.material.AspectRatio;
import com.base.hyl.houylbaseprojects.camera.preview.PreviewImpl;

import java.util.Set;


public abstract class CameraViewImpl {

    protected final Callback mCallback;//对外提供回调的内容

    protected final PreviewImpl mPreview; // 拍照的抽象类

    public  CameraViewImpl(Callback callback, PreviewImpl preview) {
        mCallback = callback;
        mPreview = preview;
    }

    public  View getView() {
        return mPreview.getView();
    }

    /**
     * @return {@code true} if the implementation was able to start the camera session.
     */
    public  abstract boolean start();// 开始

    public  abstract void stop();//结束

    public   abstract boolean isCameraOpened();//相机是否开启

    public   abstract void setFacing(int facing);//设置前置摄像头

    public  abstract int getFacing();//获取前置摄像头id

    public   abstract Set<AspectRatio> getSupportedAspectRatios();//获取设置的多个宽高比

    /**
     * @return {@code true} if the aspect ratio was changed.
     */
    public  abstract boolean setAspectRatio(AspectRatio ratio);//设置宽高比例

    public   abstract AspectRatio getAspectRatio();//获取宽高比例

    public  abstract void setAutoFocus(boolean autoFocus); //设置自动对焦

    public  abstract boolean getAutoFocus();//获取是否 自动对焦

    public abstract void setFlash(int flash);//设置闪光灯

    public abstract int getFlash();//获取闪光灯 id

    public abstract void takePicture();//拍照

    public abstract void setDisplayOrientation(int displayOrientation);//设置角度

   public interface Callback {

        void onCameraOpened(); // 相机打开

        void onCameraClosed();//相机关闭

        void onPictureTaken(byte[] data);//相机拍摄图片回调

    }

}
