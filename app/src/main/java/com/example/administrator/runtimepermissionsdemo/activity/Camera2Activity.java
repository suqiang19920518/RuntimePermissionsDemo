package com.example.administrator.runtimepermissionsdemo.activity;

import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import com.example.administrator.runtimepermissionsdemo.R;
import com.example.administrator.runtimepermissionsdemo.camera.CameraUtil;
import com.example.administrator.runtimepermissionsdemo.utils.PermissionUtils;

import static com.example.administrator.runtimepermissionsdemo.camera.CameraUtil.FRONT_CAMERA;

/**
 * @author: sq
 * @date: 2017/7/21
 * @corporation: 深圳市思迪信息科技有限公司
 * @description: 摄像头开启界面（SurfaceView）
 */
public class Camera2Activity extends AppCompatActivity implements SurfaceHolder.Callback, Camera.PreviewCallback, View.OnClickListener {

    private SurfaceView mSurfaceView;
    private Button mBtnSwitch;

    private CameraUtil cameraUtil;

    private PermissionUtils.PermissionGrant mPermissionGrant = new PermissionUtils.PermissionGrant() {
        @Override
        public void onPermissionGranted(int requestCode) {
            switch (requestCode) {
                case PermissionUtils.CODE_CAMERA:
                    cameraUtil.open();//打开摄像头
                    break;
                default:
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera2);
        initView();
        initEvent();
        cameraUtil = new CameraUtil(this, mSurfaceView);
        cameraUtil.setCamera_orientation(FRONT_CAMERA);//设置摄像头方向

        //申请权限——相机
//        PermissionUtils.requestPermission(this, PermissionUtils.CODE_CAMERA, mPermissionGrant, true);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
//            cameraUtil.open();//打开摄像头
//        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        cameraUtil.close();//释放资源，关闭摄像头
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cameraUtil.close();//释放资源，关闭摄像头
    }

    private void initView() {
        mSurfaceView = ((SurfaceView) findViewById(R.id.surface_preview));
        mBtnSwitch = ((Button) findViewById(R.id.btn_switch));
    }

    private void initEvent() {
        mSurfaceView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                cameraUtil.autoFocus();//自动对焦
                return false;
            }
        });
        mBtnSwitch.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_switch:
                cameraUtil.close();//释放资源，关闭摄像头
                cameraUtil.setFirstBlood(true);
                if (cameraUtil.getCamera_orientation() == CameraUtil.BEHIND_CAMERA) {
                    cameraUtil.setCamera_orientation(CameraUtil.FRONT_CAMERA);
                } else {
                    cameraUtil.setCamera_orientation(CameraUtil.BEHIND_CAMERA);
                }
                cameraUtil.open();//打开摄像头
                break;
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.e("Camera2Activity", "=============surfaceCreated=============");
        //申请权限——相机
        PermissionUtils.requestPermission(this, PermissionUtils.CODE_CAMERA, mPermissionGrant, true);
//        cameraUtil.open();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.e("Camera2Activity", "=============surfaceChanged=============");
        cameraUtil.autoFocus();//自动对焦
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.e("Camera2Activity", "=============surfaceDestroyed=============");
    }

    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {
        Log.e("Camera2Activity", "=============onPreviewFrame=============");
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        PermissionUtils.requestPermissionsResult(this, requestCode, permissions, grantResults, mPermissionGrant, true);

    }

}
