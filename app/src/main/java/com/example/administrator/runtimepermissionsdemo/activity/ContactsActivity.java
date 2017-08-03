package com.example.administrator.runtimepermissionsdemo.activity;

import android.Manifest;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.OperationApplicationException;
import android.content.pm.PackageManager;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.administrator.runtimepermissionsdemo.R;
import com.example.administrator.runtimepermissionsdemo.utils.PermissionUtils;
import com.example.administrator.runtimepermissionsdemo.utils.ToastUtils;

import java.util.ArrayList;

/**
 * @author: sq
 * @date: 2017/7/24
 * @corporation: 深圳市思迪信息科技有限公司
 * @description: 添加联系人界面
 */
public class ContactsActivity extends BaseActivity {

    public static final String TAG = "MainActivity";

    /**
     * Id to identify a camera permission request.
     */
    private static final int REQUEST_CONTACTS = 1;

    private int number = 1;
    private String DUMMY_CONTACT_NAME = "";

    private Button mBtnAddContacts;

    private PermissionUtils.PermissionGrant mPermissionGrant = new PermissionUtils.PermissionGrant() {
        @Override
        public void onPermissionGranted(int requestCode) {
            switch (requestCode) {
                case PermissionUtils.CODE_GET_ACCOUNTS:
                    insertDummyContact(); //添加联系人
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_contacts;
    }

    @Override
    protected void findViews() {
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
        mBtnAddContacts.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_add_contacts) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                    != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_CONTACTS)
                    != PackageManager.PERMISSION_GRANTED) {
                // Contact permission has not been granted.

                requestContactsPermissions(); //申请权限
                Log.d(TAG, "requestContact addContact if");

            } else {
                Log.d(TAG, "requestContact addContact else");
                // Contact permissions is already available, add the contact.
                Log.i(TAG,
                        "CONTACTS permission has already been granted. Add contact.");
                insertDummyContact(); //添加联系人
            }

        }
    }

    /**
     * Accesses the Contacts content provider directly to insert a new contact.
     * <p>
     * The contact is called "__DUMMY ENTRY" and only contains a name.
     */
    private void insertDummyContact() {
        // Two operations are needed to insert a new contact.
        ArrayList<ContentProviderOperation> operations = new ArrayList<ContentProviderOperation>(2);

        // First, set up a new raw contact.
        ContentProviderOperation.Builder op =
                ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                        .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                        .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null);
        operations.add(op.build());

        // Next, set the name for the contact.
        DUMMY_CONTACT_NAME = "张三" + number;
        op = ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE,
                        ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
                        DUMMY_CONTACT_NAME);
        operations.add(op.build());

        // Apply the operations.
        ContentResolver resolver = getContentResolver();
        try {
            resolver.applyBatch(ContactsContract.AUTHORITY, operations);
            number++;
            ToastUtils.Toast(this,"添加联系人==" + DUMMY_CONTACT_NAME + "==成功！");
        } catch (RemoteException e) {
            ToastUtils.Toast(this,"添加联系人==" + DUMMY_CONTACT_NAME + "==失败！");
            Log.e(TAG, "Could not add a new contact: " + e.getMessage());
        } catch (OperationApplicationException e) {
            ToastUtils.Toast(this,"添加联系人==" + DUMMY_CONTACT_NAME + "==失败！");
            Log.e(TAG, "Could not add a new contact: " + e.getMessage());
        }
    }


    /**
     * Requests the Contacts permissions.
     * If the permission has been denied previously, a SnackBar will prompt the user to grant the
     * permission, otherwise it is requested directly.
     */
    private void requestContactsPermissions() {
        // BEGIN_INCLUDE(contacts_permission_request)
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_CONTACTS)
                || ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.WRITE_CONTACTS)) {

            ActivityCompat.requestPermissions(ContactsActivity.this,
                    new String[]{Manifest.permission.CAMERA},
                    REQUEST_CONTACTS);

        } else {
            // Contact permissions have not been granted yet. Request them directly.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS,
                    Manifest.permission.WRITE_CONTACTS}, REQUEST_CONTACTS);
        }
        // END_INCLUDE(contacts_permission_request)
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        PermissionUtils.requestPermissionsResult(this, requestCode, permissions, grantResults, mPermissionGrant, false);

    }
}
