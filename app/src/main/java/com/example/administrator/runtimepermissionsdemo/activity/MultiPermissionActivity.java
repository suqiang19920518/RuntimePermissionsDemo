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
 * @description: 多个权限申请界面
 */
public class MultiPermissionActivity extends BaseActivity {

    private Button mBtnRequestPermissions;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_multi_permission;
    }

    @Override
    protected void findViews() {
        mBtnRequestPermissions = ((Button) findViewById(R.id.btn_request_permissions));
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
        mBtnRequestPermissions.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_request_permissions:
                PermissionUtils.requestMultiPermissions(this, mPermissionGrant, false);
                break;
        }
    }

    private PermissionUtils.PermissionGrant mPermissionGrant = new PermissionUtils.PermissionGrant() {
        @Override
        public void onPermissionGranted(int requestCode) {
            switch (requestCode) {
                case PermissionUtils.CODE_MULTI_PERMISSION:
                    ToastUtils.Toast(MultiPermissionActivity.this,"以上所有权限都已授予");
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
