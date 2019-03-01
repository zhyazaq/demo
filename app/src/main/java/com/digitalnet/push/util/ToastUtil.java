package com.digitalnet.push.util;

import android.support.annotation.StringRes;
import android.view.Gravity;
import android.widget.Toast;

import com.digitalnet.push.base.BaseApplication;



public class ToastUtil {
    private static Toast mToast;

    /**
     * 短时间显示Toast
     */
    public static void showToast(@StringRes int resId) {
        if (BaseApplication.mBaseApplication != null) {
            showToast(BaseApplication.mBaseApplication.getResources().getText(resId).toString());
        }
    }

    /**
     * @param message
     */
    public static void showToast(final String message) {
        if (ThreadUtils.isMainThread()) {
            showMessage(message);
        } else {
            ThreadUtils.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    showMessage(message);
                }
            });
        }

    }

    private static void showMessage(String message) {
        if (BaseApplication.mBaseApplication != null) {
            if (mToast == null) {
                mToast = Toast.makeText(BaseApplication.mBaseApplication, message, Toast.LENGTH_SHORT);
            } else {
                mToast.setText(message);
            }
            mToast.setGravity(Gravity.CENTER, 0, 0);
            mToast.show();
        }
    }


}
