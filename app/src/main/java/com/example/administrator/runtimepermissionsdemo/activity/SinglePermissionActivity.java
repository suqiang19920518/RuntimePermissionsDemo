package com.example.administrator.runtimepermissionsdemo.activity;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;

import com.example.administrator.runtimepermissionsdemo.R;
import com.example.administrator.runtimepermissionsdemo.utils.PermissionUtils;
import com.example.administrator.runtimepermissionsdemo.utils.ToastUtils;

/**
 * @author: sq
 * @date: 2017/7/20
 * @corporation: 深圳市思迪信息科技有限公司
 * @description: 单个权限申请界面
 */
public class SinglePermissionActivity extends BaseActivity {

    private Button mBtnCamera;
    private Button mBtnAudio;
    private Button mBtnAccounts;
    private Button mBtnCallPhone;
    private Button mBtnReadPhone;
    private Button mBtnFileLocation;
    private Button mBtnCoarseLocation;
    private Button mBtnReadStorage;
    private Button mBtnWriteStorage;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_single_permission;
    }

    @Override
    protected void findViews() {
        mBtnCamera = ((Button) findViewById(R.id.btn_camera));
        mBtnAudio = ((Button) findViewById(R.id.btn_audio));
        mBtnAccounts = ((Button) findViewById(R.id.btn_accounts));
        mBtnCallPhone = ((Button) findViewById(R.id.btn_call_phone));
        mBtnReadPhone = ((Button) findViewById(R.id.btn_read_phone));
        mBtnFileLocation = ((Button) findViewById(R.id.btn_file_location));
        mBtnCoarseLocation = ((Button) findViewById(R.id.btn_coarse_location));
        mBtnReadStorage = ((Button) findViewById(R.id.btn_read_storage));
        mBtnWriteStorage = ((Button) findViewById(R.id.btn_write_storage));
    }

    @Override
    protected void initObjects() {

    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListeners() {
        mBtnCamera.setOnClickListener(this);
        mBtnAudio.setOnClickListener(this);
        mBtnAccounts.setOnClickListener(this);
        mBtnCallPhone.setOnClickListener(this);
        mBtnReadPhone.setOnClickListener(this);
        mBtnFileLocation.setOnClickListener(this);
        mBtnCoarseLocation.setOnClickListener(this);
        mBtnReadStorage.setOnClickListener(this);
        mBtnWriteStorage.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_camera:
                //申请权限——相机
                PermissionUtils.requestPermission(this, PermissionUtils.CODE_CAMERA, mPermissionGrant, false);
                break;
            case R.id.btn_audio:
                //申请权限——录音（麦克风）
                PermissionUtils.requestPermission(this, PermissionUtils.CODE_RECORD_AUDIO, mPermissionGrant, false);
                break;
            case R.id.btn_accounts:
                //申请权限——访问通讯录
                PermissionUtils.requestPermission(this, PermissionUtils.CODE_GET_ACCOUNTS, mPermissionGrant, false);
                break;
            case R.id.btn_call_phone:
                //申请权限——拨打电话
                PermissionUtils.requestPermission(this, PermissionUtils.CODE_CALL_PHONE, mPermissionGrant, false);
                break;
            case R.id.btn_read_phone:
                //申请权限——读取电话状态
                PermissionUtils.requestPermission(this, PermissionUtils.CODE_READ_PHONE_STATE, mPermissionGrant, false);
                break;
            case R.id.btn_file_location:
                //申请权限——获取精确位置
                PermissionUtils.requestPermission(this, PermissionUtils.CODE_ACCESS_FINE_LOCATION, mPermissionGrant, false);
                break;
            case R.id.btn_coarse_location:
                //申请权限——获取错略位置
                PermissionUtils.requestPermission(this, PermissionUtils.CODE_ACCESS_COARSE_LOCATION, mPermissionGrant, false);
                break;
            case R.id.btn_read_storage:
                //申请权限——读取存储空间
                PermissionUtils.requestPermission(this, PermissionUtils.CODE_READ_EXTERNAL_STORAGE, mPermissionGrant, false);
                break;
            case R.id.btn_write_storage:
                //申请权限——写入外部存储
                PermissionUtils.requestPermission(this, PermissionUtils.CODE_WRITE_EXTERNAL_STORAGE, mPermissionGrant, false);
                break;

        }
    }

    /**
     * 权限授予通过，回调
     */
    private PermissionUtils.PermissionGrant mPermissionGrant = new PermissionUtils.PermissionGrant() {
        @Override
        public void onPermissionGranted(int requestCode) {
            switch (requestCode) {
                case PermissionUtils.CODE_RECORD_AUDIO:
                    ToastUtils.Toast(SinglePermissionActivity.this, "录音（麦克风）权限已授予");
                    // TODO: 后续相关操作
                    break;
                case PermissionUtils.CODE_GET_ACCOUNTS:
                    ToastUtils.Toast(SinglePermissionActivity.this, "访问通讯录权限已授予");
                    // TODO: 后续相关操作
                    break;
                case PermissionUtils.CODE_READ_PHONE_STATE:
                    ToastUtils.Toast(SinglePermissionActivity.this, "读取电话状态权限已授予");
                    // TODO: 后续相关操作
                    break;
                case PermissionUtils.CODE_CALL_PHONE:
                    ToastUtils.Toast(SinglePermissionActivity.this, "拨打电话权限已授予");
                    // TODO: 后续相关操作
                    break;
                case PermissionUtils.CODE_CAMERA:
                    ToastUtils.Toast(SinglePermissionActivity.this, "相机权限已授予");
                    // TODO: 后续相关操作
                    break;
                case PermissionUtils.CODE_ACCESS_FINE_LOCATION:
                    ToastUtils.Toast(SinglePermissionActivity.this, "获取精确位置权限已授予");
                    // TODO: 后续相关操作
                    break;
                case PermissionUtils.CODE_ACCESS_COARSE_LOCATION:
                    ToastUtils.Toast(SinglePermissionActivity.this, "获取错略位置权限已授予");
                    // TODO: 后续相关操作
                    break;
                case PermissionUtils.CODE_READ_EXTERNAL_STORAGE:
                    ToastUtils.Toast(SinglePermissionActivity.this, "读取存储空间权限已授予");
                    // TODO: 后续相关操作
                    break;
                case PermissionUtils.CODE_WRITE_EXTERNAL_STORAGE:
                    ToastUtils.Toast(SinglePermissionActivity.this,"写入外部存储权限已授予");
                    // TODO: 后续相关操作
                    break;
                default:
                    break;
            }
        }
    };


    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        PermissionUtils.requestPermissionsResult(this, requestCode, permissions, grantResults, mPermissionGrant, false);
    }

}
