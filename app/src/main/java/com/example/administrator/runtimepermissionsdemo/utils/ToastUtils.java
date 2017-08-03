package com.example.administrator.runtimepermissionsdemo.utils;


import android.content.Context;
import android.widget.Toast;

/**
 * @author: sq
 * @date: 2017/8/1
 * @corporation: 深圳市思迪信息科技有限公司
 * @description: Toast封装类
 */
public class ToastUtils {
    private static Toast mToast;

    private ToastUtils() {
    }

    public static void Toast(Context context, String msg) {
        if(mToast == null) {
            mToast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
        } else {
            mToast.setText(msg);
        }

        mToast.show();
    }

    public static void Toast(int gravity, Context context, String msg) {
        if(mToast == null) {
            mToast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
        } else {
            mToast.setText(msg);
        }

        mToast.setGravity(gravity, 0, 0);
        mToast.show();
    }
}
