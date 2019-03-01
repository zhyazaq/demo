package com.digitalnet.push.util;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.digitalnet.push.R;


public class LogoutUtil {
	
	private static LogoutUtil logoutUtil;
	
	private LogoutUtil() {
		
	}
	
	public static LogoutUtil getInstence(){
		if (logoutUtil == null) {
			logoutUtil = new LogoutUtil();
		}
		
		return logoutUtil;
	}

	/**
	 * 退出程序
	 */
	private boolean isExit;
	public void exit(Context context) {
		if (!isExit) {
			isExit = true;
//			Toast.makeText(context, "再按一次退出"+context.getResources().getString(R.string.app_name), Toast.LENGTH_SHORT).show();
			ToastUtil.showToast("再按一次退出"+context.getResources().getString(R.string.app_name));
			mHandler.sendEmptyMessageDelayed(0, 1000);
		} else {
			AppManager.getAppManager().exitApplication();
//			System.exit(0);

		}
	}
	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			isExit = false;
		}
	};
}
