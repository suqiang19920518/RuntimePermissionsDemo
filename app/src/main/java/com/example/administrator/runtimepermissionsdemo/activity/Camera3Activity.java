package com.example.administrator.runtimepermissionsdemo.activity;

import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;

import com.example.administrator.runtimepermissionsdemo.R;
import com.example.administrator.runtimepermissionsdemo.camera.CameraUtil2;
import com.example.administrator.runtimepermissionsdemo.utils.PermissionUtils;


/**
 * @author: sq
 * @date: 2017/7/21
 * @corporation: 深圳市思迪信息科技有限公司
 * @description: 摄像头开启界面（TextureView）
 */
public class Camera3Activity extends AppCompatActivity implements TextureView.SurfaceTextureListener, Camera.PreviewCallback, View.OnClickListener {

    private TextureView mTextureView;
    private Button mBtnSwitch;

    private CameraUtil2 cameraUti2;

    private PermissionUtils.PermissionGrant mPermissionGrant = new PermissionUtils.PermissionGrant() {
        @Override
        public void onPermissionGranted(int requestCode) {
            switch (requestCode) {
                case PermissionUtils.CODE_CAMERA:
                    cameraUti2.open();//打开摄像头
                    break;
                default:
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera3);
        initView();
        initEvent();
        cameraUti2 = new CameraUtil2(this, mTextureView);
        cameraUti2.setCamera_orientation(CameraUtil2.FRONT_CAMERA);//设置摄像头方向

    }

    @Override
    protected void onPause() {
        super.onPause();
        cameraUti2.close();//释放资源，关闭摄像头
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cameraUti2.close();//释放资源，关闭摄像头
    }

    private void initView() {
        mTextureView = ((TextureView) findViewById(R.id.texture_preview));
        mBtnSwitch = ((Button) findViewById(R.id.btn_switch));
    }

    private void initEvent() {
        mTextureView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                cameraUti2.autoFocus();//自动对焦
                return false;
            }
        });
        mBtnSwitch.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_switch:
                cameraUti2.close();//释放资源，关闭摄像头
                cameraUti2.setFirstBlood(true);
                if (cameraUti2.getCamera_orientation() == CameraUtil2.BEHIND_CAMERA) {
                    cameraUti2.setCamera_orientation(CameraUtil2.FRONT_CAMERA);
                } else {
                    cameraUti2.setCamera_orientation(CameraUtil2.BEHIND_CAMERA);
                }
                cameraUti2.open();//打开摄像头
                break;
        }
    }

    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {
        Log.e("Camera3Activity", "=============onPreviewFrame=============");
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        //申请权限——相机
        PermissionUtils.requestPermission(this, PermissionUtils.CODE_CAMERA, mPermissionGrant, true);
        Log.e("Camera3Activity", "=============onSurfaceTextureAvailable=============");
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
        Log.e("Camera3Activity", "=============onSurfaceTextureSizeChanged=============");
        cameraUti2.autoFocus();//自动对焦
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        Log.e("Camera3Activity", "=============onSurfaceTextureDestroyed=============");
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
        Log.e("Camera3Activity", "=============onSurfaceTextureUpdated=============");
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
