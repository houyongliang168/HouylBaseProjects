package com.base.hyl.houylbaseprojects.video;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.ImageFormat;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.media.Image;
import android.media.ImageReader;
import android.os.Build;
import android.os.Environment;
import android.os.HandlerThread;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.FrameLayout;

import com.base.common.log.MyToast;

import com.base.hyl.houylbaseprojects.R;
import com.base.hyl.houylbaseprojects.camera.CameraView;
import com.yanzhenjie.permission.AndPermission;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */

public class FullscreenActivity extends AppCompatActivity  {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;
    private String TAG = FullscreenActivity.class.getSimpleName();
    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private View mContentView;
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };
    private View mControlsView;
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
            mControlsView.setVisibility(View.VISIBLE);
        }
    };
    private boolean mVisible;
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };
    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };
    private TextureView mTextureView;
    private CameraManager mCameraManager;
    private int numberOfCameras;
    private Surface surface;
    private CaptureRequest.Builder mPreviewRequestBuilder;
    private FrameLayout frameLayout;
    private CameraView camera;

    private Handler mBackgroundHandler;
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBackgroundHandler != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                mBackgroundHandler.getLooper().quitSafely();
            } else {
                mBackgroundHandler.getLooper().quit();
            }
            mBackgroundHandler = null;
        }
    }
    private Handler getBackgroundHandler() {
        if (mBackgroundHandler == null) {
            HandlerThread thread = new HandlerThread("background");
            thread.start();
            mBackgroundHandler = new Handler(thread.getLooper());
        }
        return mBackgroundHandler;
    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fullscreen);

        mVisible = true;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.fullscreen_content);
        frameLayout = findViewById(R.id.fl_container);
        /* 初始化监听*/
        mTextureView = findViewById(R.id.textureView_fullscreen_content);
        // Set up the user interaction to manually show or hide the system UI.
        mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                camera.start();
                camera.takePicture();


            }
        });

        findViewById(R.id.dummy_button).setOnTouchListener(mDelayHideTouchListener);
        View mHeadView = View.inflate(this, R.layout.item_camera, null);
        camera = mHeadView.findViewById(R.id.camera);
        frameLayout.addView(mHeadView);
        camera.addCallback(new CameraView.Callback() {
            @Override
            public void onCameraOpened(CameraView cameraView) {
                super.onCameraOpened(cameraView);
            }

            @Override
            public void onCameraClosed(CameraView cameraView) {
                super.onCameraClosed(cameraView);
            }

            @Override
            public void onPictureTaken(CameraView cameraView, byte[] data) {
                super.onPictureTaken(cameraView, data);
                getBackgroundHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        String path = Environment.getExternalStorageDirectory() + File.separator +"AAA";
                        File files = new File(path);
                        if (!files.exists()) {
                            files.mkdirs();
                        }


                        FileOutputStream os = null;
                        try {
                            os = new FileOutputStream(path + File.separator + "1pic.jpg");
                            os.write(data);
                            os.close();

                        } catch (IOException e) {
                            Log.w(TAG, "Cannot write to " + os, e);
                        } finally {
                            if (os != null) {
                                try {
                                    os.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    Log.w(TAG, "IOException" +e.getMessage());
                                }
                            }
                        }


                    }

                });


            }

        });

        initListener();
    };

    private void initListener() {
        //TextureView's SurfaceTexture Listener
        TextureView.SurfaceTextureListener mSurfaceTextureListener = new TextureView.SurfaceTextureListener() {
            @Override
            public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
                Log.d(TAG, "onSurfaceTextureAvailable:");
                try {
                    //注意这里除了预览的Surface，我们还添加了imageReader.getSurface()它就是负责拍照完成后用来获取数据的

                    initCamera();
                } catch (CameraAccessException e) {
                    e.printStackTrace();
                }
              //  openCamera();
            }

            @Override
            public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
                Log.d(TAG, "onSurfaceTextureSizeChanged:");
            }

            @Override
            public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
                Log.d(TAG, "onSurfaceTextureDestroyed:");
              //  closeCamera();
              //  stopCameraThread();
                return true;
            }

            @Override
            public void onSurfaceTextureUpdated(SurfaceTexture surface) {
            }
        };


        mTextureView.setSurfaceTextureListener(mSurfaceTextureListener);




    }

    @Override
    protected void onStop() {
        super.onStop();
        camera.stop();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }

    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mControlsView.setVisibility(View.GONE);
        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    @SuppressLint("InlinedApi")
    private void show() {
        // Show the system bar
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    private void show(String name) {
        MyToast.showShort(name);
    }

    /**
     * Schedules a call to hide() in delay milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }

    public void getPermission() {
        AndPermission.with(this)
                .permission(Manifest.permission.CAMERA)
                // 准备方法，和 okhttp 的拦截器一样，在请求权限之前先运行改方法，已经拥有权限不会触发该方法
                .rationale((context, permissions, executor) -> {
                    // 此处可以选择显示提示弹窗
                    executor.execute();
                })
                // 用户给权限了
                .onGranted(permissions -> show("用户给权限啦"))
                // 用户拒绝权限，包括不再显示权限弹窗也在此列
                .onDenied(permissions -> {
                    // 判断用户是不是不再显示权限弹窗了，若不再显示的话进入权限设置页
                    if (AndPermission.hasAlwaysDeniedPermission(FullscreenActivity.this, permissions)) {
                        // 打开权限设置页
                        AndPermission.permissionSetting(FullscreenActivity.this).execute();
                        return;
                    }
                    show("用户拒绝权限");
                })
                .start();
    }

    public void camera() {


    }

    private String  faceFrontCameraId;
    private CameraCharacteristics frontCameraCharacteristics;
    private   CameraDevice  mcameraDevice;
    @TargetApi(21)
    public void initCamera() throws CameraAccessException {
        mCameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            final String[] ids = mCameraManager.getCameraIdList();
            numberOfCameras = ids.length;
            for (String id : ids) {
                final CameraCharacteristics characteristics = mCameraManager.getCameraCharacteristics(id);
                final int orientation = characteristics.get(CameraCharacteristics.LENS_FACING);
                if (orientation == CameraCharacteristics.LENS_FACING_FRONT) {
                    faceFrontCameraId = id;
                    Integer   faceFrontCameraOrientation = characteristics.get(CameraCharacteristics.SENSOR_ORIENTATION);
                    frontCameraCharacteristics = characteristics;
                } else {
                    String faceBackCameraId = id;
                    Integer   faceBackCameraOrientation = characteristics.get(CameraCharacteristics.SENSOR_ORIENTATION);
                    CameraCharacteristics  backCameraCharacteristics = characteristics;
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error during camera initialize");
        }


        CameraDevice.StateCallback stateCallback = new CameraDevice.StateCallback() {
            @Override
            public void onOpened(@NonNull CameraDevice cameraDevice) {
                //获取CameraDevice
                  mcameraDevice = cameraDevice;

                try {
                    mPreviewRequestBuilder = mcameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
                    SurfaceTexture surfaceTexture=    mTextureView.getSurfaceTexture();
                    surface = new Surface(surfaceTexture);
                    mPreviewRequestBuilder.addTarget(surface);
                    mcameraDevice.createCaptureSession(Arrays.asList(surface, mImageReader.getSurface()),
                            mSessionCallback
                            , null);
                } catch (CameraAccessException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onDisconnected(@NonNull CameraDevice cameraDevice) {
                //关闭CameraDevice
                cameraDevice.close();

            }

            @Override
            public void onError(@NonNull CameraDevice cameraDevice, int error) {
                //关闭CameraDevice
                cameraDevice.close();
            }
        };
        mCameraManager.openCamera(faceFrontCameraId, stateCallback, null);


        prepareImageReader();
    }



    CameraCaptureSession mCaptureSession;
    private final CameraCaptureSession.StateCallback mSessionCallback
            = new CameraCaptureSession.StateCallback() {

        @Override
        public void onConfigured(@NonNull CameraCaptureSession session) {
            if (mcameraDevice == null) {
                return;
            }
            mCaptureSession = session;
            try {
                mCaptureSession.setRepeatingRequest(mPreviewRequestBuilder.build(),captureCallback
                       , null);
            } catch (CameraAccessException e) {
                Log.e(TAG, "Failed to start camera preview because it couldn't access camera", e);
            } catch (IllegalStateException e) {
                Log.e(TAG, "Failed to start camera preview.", e);
            }
        }

        @Override
        public void onConfigureFailed(@NonNull CameraCaptureSession session) {
            Log.e(TAG, "Failed to configure capture session.");
        }

        @Override
        public void onClosed(@NonNull CameraCaptureSession session) {
            if (mCaptureSession != null && mCaptureSession.equals(session)) {
                mCaptureSession = null;
            }
        }

    };
  //  CameraCaptureSession.CaptureCallback来处理对焦请求返回的结果。
    private CameraCaptureSession.CaptureCallback captureCallback = new CameraCaptureSession.CaptureCallback() {

        @Override
        public void onCaptureProgressed(@NonNull CameraCaptureSession session,
                                        @NonNull CaptureRequest request,
                                        @NonNull CaptureResult partialResult) {
        }

        @Override
        public void onCaptureCompleted(@NonNull CameraCaptureSession session,
                                       @NonNull CaptureRequest request,
                                       @NonNull TotalCaptureResult result) {
            //等待对焦
            final Integer afState = result.get(CaptureResult.CONTROL_AF_STATE);
            if (afState == null) {
                //对焦失败，直接拍照
               captureStillPicture();
            } else if (CaptureResult.CONTROL_AF_STATE_FOCUSED_LOCKED == afState
                    || CaptureResult.CONTROL_AF_STATE_NOT_FOCUSED_LOCKED == afState
                    || CaptureResult.CONTROL_AF_STATE_INACTIVE == afState
                    || CaptureResult.CONTROL_AF_STATE_PASSIVE_SCAN == afState) {
                Integer aeState = result.get(CaptureResult.CONTROL_AE_STATE);
                if (aeState == null ||
                        aeState == CaptureResult.CONTROL_AE_STATE_CONVERGED) {
                  //  previewState = STATE_PICTURE_TAKEN;
                    //对焦完成，进行拍照
                   captureStillPicture();
                } else {
                 //   runPreCaptureSequence();
                }
            }
        }
    };
// 拍照
    private void captureStillPicture() {
        try {
            if (null == mcameraDevice) {
                return;
            }

            //构建用来拍照的CaptureRequest
            final CaptureRequest.Builder captureBuilder =
                    mcameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE);
            captureBuilder.addTarget(mImageReader.getSurface());

            //使用相同的AR和AF模式作为预览
            captureBuilder.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);
            //设置方向
            //captureBuilder.set(CaptureRequest.JPEG_ORIENTATION, getPhotoOrientation(mCameraConfigProvider.getSensorPosition()));

            //创建会话
            CameraCaptureSession.CaptureCallback CaptureCallback = new CameraCaptureSession.CaptureCallback() {
                @Override
                public void onCaptureCompleted(@NonNull CameraCaptureSession session,
                                               @NonNull CaptureRequest request,
                                               @NonNull TotalCaptureResult result) {
                    Log.d(TAG, "onCaptureCompleted: ");
                }
            };
            //对焦
//            try {
//                //相机对焦
//                captureBuilder.set(CaptureRequest.CONTROL_AF_TRIGGER, CameraMetadata.CONTROL_AF_TRIGGER_START);
//                //修改状态
//              //  previewState = STATE_WAITING_LOCK;
//                //发送对焦请求
//                mCaptureSession.capture(captureBuilder.build(), captureCallback, null);
//            } catch (Exception ignore) {
//            }



            //停止连续取景
            mCaptureSession.stopRepeating();
            //捕获照片
            mCaptureSession.capture(captureBuilder.build(), CaptureCallback, null);

        } catch (CameraAccessException e) {
            Log.e(TAG, "Error during capturing picture");
        }
    }
    private ImageReader mImageReader;

    private void prepareImageReader() {
        if (mImageReader != null) {
            mImageReader.close();
        }

       mImageReader = ImageReader.newInstance(640,420,
                ImageFormat.JPEG, /* maxImages */ 2);
        mImageReader.setOnImageAvailableListener(mOnImageAvailableListener, null);
    }
    private final ImageReader.OnImageAvailableListener mOnImageAvailableListener
            = new ImageReader.OnImageAvailableListener() {

        @Override
        public void onImageAvailable(ImageReader reader) {
            //获取图片并保存
            try (Image image = reader.acquireNextImage()) {
                Image.Plane[] planes = image.getPlanes();
                if (planes.length > 0) {
                    ByteBuffer buffer = planes[0].getBuffer();
                    byte[] data = new byte[buffer.remaining()];
                    buffer.get(data);

                   // mCallback.onPictureTaken(data);
                }
            }
        }

    };


}