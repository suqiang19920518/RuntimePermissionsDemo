package com.example.administrator.runtimepermissionsdemo.camera;


import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Surface;
import android.view.TextureView;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.administrator.runtimepermissionsdemo.utils.WakeLockUtil;

import java.io.IOException;
import java.util.List;

/**
 * @author: sq
 * @date: 2017/7/21
 * @corporation: 深圳市思迪信息科技有限公司
 * @description: 摄像头工具类——TextureView
 */
public class CameraUtil2 {

    // 内（前）置摄像头
    public static int FRONT_CAMERA = 1;

    // 外（后）置摄像头
    public static int BEHIND_CAMERA = 0;

    private int previewWidth = 480, previewHeight = 640;//预览宽高

    private Camera camera;
    private CameraSizeMatcher cameraSizeMatcher;
    private int camera_orientation;//摄像头方向

    public static boolean ifFocus;
    private boolean firstBlood = true;
    private boolean isPreview = false;

    private TextureView mTextureView;

    private Context mContext;

    public CameraUtil2(Context mContext, TextureView mTextureView) {
        this.mContext = mContext;
        this.mTextureView = mTextureView;
        config(mContext, mTextureView);
    }

    /**
     * 配置mTextureView
     *
     * @param mContext
     * @param mTextureView
     */
    private void config(Context mContext, TextureView mTextureView) {
        mTextureView.setFocusable(true);
        mTextureView.setSurfaceTextureListener((TextureView.SurfaceTextureListener) mContext);

    }

    /**
     * 打开相机
     */
    public void open() {
        if (firstBlood) {
            mTextureView.post(new Runnable() {
                @Override
                public void run() {
                    initCamera(camera_orientation);
                    autoFocus();
                }
            });
            firstBlood = false;
        }
    }

    /* 初始相机函数 */
    private void initCamera(int cameraType) {
        if (!isPreview) {

            try {
                Camera.CameraInfo cameraInfo = null;
                WakeLockUtil.getInstance().keepCreenWake(mContext);//保持屏幕一直唤醒
                if (cameraType == BEHIND_CAMERA) {
                    if (!CameraHelper.hasFrontFacingCamera()) {
                        Toast.makeText(mContext, "你的手机没有后置摄像头，无法启动相机服务！", Toast.LENGTH_LONG).show();
                        if (mContext instanceof Activity)
                            ((Activity) mContext).finish();
                        return;
                    }
                } else if (cameraType == FRONT_CAMERA) {
                    if (!CameraHelper.hasFrontFacingCamera()) {
                        Toast.makeText(mContext, "你的手机没有前置摄像头，无法启动相机服务！", Toast.LENGTH_LONG).show();
                        if (mContext instanceof Activity)
                            ((Activity) mContext).finish();
                        return;
                    }
                }
                camera = Camera.open(cameraType);
                cameraInfo = new Camera.CameraInfo();
                Camera.getCameraInfo(cameraType, cameraInfo);
                initCameraParameters(cameraType, cameraInfo, mContext);
                camera.setPreviewCallback((Camera.PreviewCallback) mContext);
                camera.setPreviewTexture(mTextureView.getSurfaceTexture());
                camera.startPreview();
                camera.cancelAutoFocus();
                isPreview = true;
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(mContext, "无法连接相机服务，请检查相机权限", Toast.LENGTH_LONG).show();
                close();
                firstBlood = true;
                if (mContext instanceof Activity)
                    ((Activity) mContext).finish();
//                finish();
            }
        }
    }

    /**
     * 配置相机参数
     */
    private void initCameraParameters(int cameraType, Camera.CameraInfo cameraInfo, Context context) {
        if (camera != null) {
            Camera.Parameters parameters = camera.getParameters();// 获得相机参数
            if (cameraType == BEHIND_CAMERA) {
                // Get the rotation of the screen to adjust the preview image accordingly.
                final int displayRotation = ((Activity) context).getWindowManager().getDefaultDisplay().getRotation();
                int orientation = calculatePreviewOrientation(cameraInfo, displayRotation);
                // 设置摄像头方向
                camera.setDisplayOrientation(orientation);
            } else {
                // 设置摄像头方向
                if (Build.MODEL.equals("N9180") || Build.MODEL.equals("U9180")) {
                    // 针对中兴N9180
                    camera.setDisplayOrientation(270);
                } else {
                    camera.setDisplayOrientation(90);
                }
                parameters.setRotation(270);
            }
            List<Camera.Size> sizes = camera.getParameters().getSupportedPreviewSizes();

//            Camera.Size s = getOptimalPreviewSize(sizes);
//            parameters.setPictureSize(s.width, s.height);
//            parameters.setPreviewSize(s.width, s.height);

//            parameters.setPictureSize(previewWidth, previewHeight);

            cameraSizeMatcher = new CameraSizeMatcher(context);
            Camera.Size pictureSize = cameraSizeMatcher.findBestPictureResolution(camera);
            Camera.Size previewSize = cameraSizeMatcher.findBestPreviewResolution(camera);
            parameters.setPictureSize(pictureSize.width, pictureSize.height);
            parameters.setPreviewSize(previewSize.width, previewSize.height);

            List<String> focusModes = parameters.getSupportedFocusModes();
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
//            if (Build.BRAND.equals("Xiaomi") || Build.BRAND.equals("ZTE")) {
//                if (focusModes.contains(Camera.Parameters.FOCUS_MODE_AUTO)) {
//                    parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
//                }
//            } else {
//                if (focusModes.contains(Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
//                    parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
//                }
//
//            }
                    /* 设定相片格式为JPEG */
            parameters.setPictureFormat(PixelFormat.JPEG);
            try {
                camera.setParameters(parameters);
            } catch (Exception localException) {
                localException.printStackTrace();
            }
        }
    }

    /**
     * Calculate the correct orientation for a {@link Camera} preview that is displayed on screen.
     * <p>
     * Implementation is based on the sample code provided in
     * {@link Camera#setDisplayOrientation(int)}.
     */
    public static int calculatePreviewOrientation(Camera.CameraInfo info, int rotation) {
        int degrees = 0;

        switch (rotation) {
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                break;
        }

        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360;  // compensate the mirror
        } else {  // back-facing
            result = (info.orientation - degrees + 360) % 360;
        }

        return result;
    }

    /**
     * 自动对焦
     */
    public void autoFocus() {
        if (camera != null) {
            try {
                camera.autoFocus(new Camera.AutoFocusCallback() {

                    @Override
                    public void onAutoFocus(boolean success, Camera camera) {
                        if (success) {
                            ifFocus = true;
                            camera.cancelAutoFocus();
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取适配的Camera 的preview size 和 picture size
     *
     * @param sizes
     * @return Size
     */
    private Camera.Size getOptimalPreviewSize(List<Camera.Size> sizes) {
        int w;
        int h;
        // 获取窗口管理器
        WindowManager wm = ((Activity) mContext).getWindowManager();
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        // 获取屏幕的宽和高
        display.getMetrics(metrics);
        w = metrics.widthPixels;
        h = metrics.heightPixels;
        final double ASPECT_TOLERANCE = 0.2D;
        double targetRatio = (double) w / h;
        if (sizes == null)
            return null;
        Camera.Size optimalSize = null;
        double minDiff = Double.MAX_VALUE;
        int targetHeight = h;
        // Try to find an size match aspect ratio and size
        for (Camera.Size size : sizes) {
            double ratio = (double) size.width / size.height;
            if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE)
                continue;
            if (Math.abs(size.height - targetHeight) < minDiff) {
                optimalSize = size;
                minDiff = Math.abs(size.height - targetHeight);
            }
        }
        // Cannot find the one match the aspect ratio, ignore the requirement
        if (optimalSize == null) {
            minDiff = Double.MAX_VALUE;
            for (Camera.Size size : sizes) {
                if (Math.abs(size.height - targetHeight) < minDiff) {
                    optimalSize = size;
                    minDiff = Math.abs(size.height - targetHeight);
                }
            }
        }
        return optimalSize;
    }

    /**
     * 关闭相机
     */
    public void close() {
        if (camera != null) {
            WakeLockUtil.getInstance().cancleCreenWake();//取消屏幕唤醒
            camera.setPreviewCallback(null); // 解决Method called after
            camera.cancelAutoFocus();
            if (isPreview) {
                camera.stopPreview();
                isPreview = false;
            }
            try {
                camera.setPreviewDisplay(null);
            } catch (IOException e) {
                e.printStackTrace();
            }
            camera.release();
            camera = null;
        }
    }

    public void setCamera_orientation(int camera_orientation) {
        this.camera_orientation = camera_orientation;
    }

    public int getCamera_orientation() {
        return camera_orientation;
    }

    public void setFirstBlood(boolean firstBlood) {
        this.firstBlood = firstBlood;
    }
}
