package com.example.administrator.runtimepermissionsdemo.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.administrator.runtimepermissionsdemo.R;
import com.example.administrator.runtimepermissionsdemo.camera.CameraPreviewFragment;
import com.example.administrator.runtimepermissionsdemo.utils.PermissionUtils;
import com.example.administrator.runtimepermissionsdemo.utils.WakeLockUtil;

/**
 * @author: sq
 * @date: 2017/7/20
 * @corporation: 深圳市思迪信息科技有限公司
 * @description: 摄像头开启界面
 */
public class Camera1Activity extends AppCompatActivity {

    public static final String TAG = "Camera1Activity";

    /**
     * Id to identify a camera permission request.
     */
    private static final int REQUEST_CAMERA = 4;

    /**
     * Root of the layout of this Activity.
     */
    private View mLayout;

    private boolean isOpenCamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera1);
        initView();
        showCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();
        WakeLockUtil.getInstance().keepCreenWake(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            if (!isOpenCamera) {
                showCameraPreview();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        WakeLockUtil.getInstance().cancleCreenWake();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        WakeLockUtil.getInstance().cancleCreenWake();
    }

    private void initView() {
        mLayout = findViewById(R.id.camera_content_fragment);
    }

    /**
     * Called when the 'show camera' button is clicked.
     * Callback is defined in resource layout definition.
     */
    public void showCamera() {
        Log.i(TAG, "Show camera button pressed. Checking permission.");
        // BEGIN_INCLUDE(camera_permission)
        // Check if the Camera permission is already available.

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // Camera permission has not been granted.

            requestCameraPermission();
            Log.d(TAG, "requestCamera showCamera if");

        } else {
            Log.d(TAG, "requestCamera showCamera else");
            // Camera permissions is already available, show the camera preview.
            Log.i(TAG,
                    "CAMERA permission has already been granted. Displaying camera preview.");
            showCameraPreview();
        }

        // END_INCLUDE(camera_permission)

    }

    /**
     * Requests the Camera permission.
     * If the permission has been denied previously, a SnackBar will prompt the user to grant the
     * permission, otherwise it is requested directly.
     */
    private void requestCameraPermission() {
        Log.i(TAG, "requestCameraPermission CAMERA permission has NOT been granted. Requesting permission.");

        // BEGIN_INCLUDE(camera_permission_request)
        Log.d(TAG, "requestCameraPermission shouldShowRequestPermissionRationale: " + ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.CAMERA));
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.CAMERA)) {
            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // For example if the user has previously denied the permission.
            Log.i(TAG,
                    "requestCameraPermission Displaying camera permission rationale to provide additional context.");
//            Snackbar.make(mLayout, R.string.permission_camera_user_hint1,
//                    Snackbar.LENGTH_INDEFINITE)
//                    .setAction(R.string.ok, new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            Log.d(TAG, "requestCameraPermission CAMERA permission Snack bar");
//                            ActivityCompat.requestPermissions(Camera1Activity.this,
//                                    new String[]{Manifest.permission.CAMERA},
//                                    REQUEST_CAMERA);
//                        }
//                    })
//                    .show();

            ActivityCompat.requestPermissions(Camera1Activity.this,
                    new String[]{Manifest.permission.CAMERA},
                    REQUEST_CAMERA);

        } else {
            Log.d(TAG, "requestCameraPermission else");
            // Camera permission has not been granted yet. Request it directly.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                    REQUEST_CAMERA);
        }
        // END_INCLUDE(camera_permission_request)
    }

    /**
     * Display the {@link CameraPreviewFragment} in the content area if the required Camera
     * permission has been granted.
     */
    private void showCameraPreview() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.camera_content_fragment, CameraPreviewFragment.newInstance())
//                .addToBackStack("contacts")
                .commit();
        isOpenCamera =true;
    }

    private PermissionUtils.PermissionGrant mPermissionGrant = new PermissionUtils.PermissionGrant() {
        @Override
        public void onPermissionGranted(int requestCode) {
            switch (requestCode) {
                case PermissionUtils.CODE_CAMERA:
                    showCameraPreview();
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

        PermissionUtils.requestPermissionsResult(this, requestCode, permissions, grantResults, mPermissionGrant, true);

    }
}
