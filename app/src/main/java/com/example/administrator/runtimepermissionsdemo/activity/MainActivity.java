package com.example.administrator.runtimepermissionsdemo.activity;

import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.administrator.runtimepermissionsdemo.R;

public class MainActivity extends BaseActivity {
    public static final String TAG = "MainActivity";

    private Button mBtnRequestSingle;
    private Button mBtnRequestMulti;
    private Button mBtnOpenSetting;
    private Button mBtnOpenCamera1;
    private Button mBtnOpenCamera2;
    private Button mBtnOpenCamera3;
    private Button mBtnAddContacts;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void findViews() {
        mBtnRequestSingle = ((Button) findViewById(R.id.btn_request_single));
        mBtnRequestMulti = ((Button) findViewById(R.id.btn_request_multi));
        mBtnOpenSetting = ((Button) findViewById(R.id.btn_open_setting));
        mBtnOpenCamera1 = ((Button) findViewById(R.id.btn_open_camera1));
        mBtnOpenCamera2 = ((Button) findViewById(R.id.btn_open_camera2));
        mBtnOpenCamera3 = ((Button) findViewById(R.id.btn_open_camera3));
        mBtnAddContacts = ((Button) findViewById(R.id.btn_add_contacts));
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
        mBtnRequestSingle.setOnClickListener(this);
        mBtnRequestMulti.setOnClickListener(this);
        mBtnOpenSetting.setOnClickListener(this);
        mBtnOpenCamera1.setOnClickListener(this);
        mBtnOpenCamera2.setOnClickListener(this);
        mBtnOpenCamera3.setOnClickListener(this);
        mBtnAddContacts.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_request_single:
                startActivity(SinglePermissionActivity.class);
                break;
            case R.id.btn_request_multi:
                startActivity(MultiPermissionActivity.class);
                break;
            case R.id.btn_open_setting:
                openSetting();
                break;
            case R.id.btn_open_camera1:
                startActivity(Camera1Activity.class);
                break;
            case R.id.btn_open_camera2:
                startActivity(Camera2Activity.class);
                break;
            case R.id.btn_open_camera3:
                startActivity(Camera3Activity.class);
                break;
            case R.id.btn_add_contacts:
                startActivity(ContactsActivity.class);
                break;
            default:
                break;

        }
    }

    /**
     * 打开设置界面
     */
    public void openSetting() {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Log.e(TAG, "getPackageName(): " + getPackageName());
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivity(intent);
    }
}
