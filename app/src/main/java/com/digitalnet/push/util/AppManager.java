package com.digitalnet.push.util;

import android.app.Activity;

import java.util.Stack;


public class AppManager {

	private static Stack<Activity> mActivityStack;
	private static AppManager mManagerInstance;

	private AppManager() {
	}

	/**
	 * 获取AppManager单一实例
	 */
	public static AppManager getAppManager() {
		if (mManagerInstance == null) {
			mManagerInstance = new AppManager();
		}
		return mManagerInstance;
	}

	/**
	 * 添加Activity到堆栈
	 */
	public void addActivity(Activity activity) {
		if (mActivityStack == null) {
			mActivityStack = new Stack<Activity>();
		}
		mActivityStack.add(activity);
	}
	
	/**
	 * 从堆栈里删除对应的Activity
	 */
	public void removeActivity(Activity activity){
		if (mActivityStack != null) {
			mActivityStack.remove(activity);
		}
	}

	/**
	 * 获取当前Activity（堆栈中最后一个压入的）
	 */
	public Activity getCurrentActivity() {
		if (mActivityStack != null) {
			Activity activity = mActivityStack.lastElement();
			return activity;
		}
		return null;
	}

	/**
	 * 结束当前Activity（堆栈中最后一个压入的）
	 */
	public void finishCurrentActivity() {
		Activity activity = mActivityStack.lastElement();
		finishActivity(activity);
	}

	/**
	 * 结束指定的Activity
	 */
	public void finishActivity(Activity activity) {
		if (activity != null) {
			mActivityStack.remove(activity);
			activity.finish();
			activity = null;
		}
	}

	/**
	 * 结束指定类名的Activity
	 */
	public void finishActivity(Class<?> cls) {
		for (Activity activity : mActivityStack) {
			if (activity.getClass().equals(cls)) {
				finishActivity(activity);
			}
		}
	}

	/**
	 * 结束所有Activity
	 */
	public void finishAllActivity() {
		for (int i = 0, size = mActivityStack.size(); i < size; i++) {
			if (null != mActivityStack.get(i)) {
				mActivityStack.get(i).finish();
			}
		}
		mActivityStack.clear();
	}

	/**
	 * 退出应用程序
	 */
	public void exitApplication() {
		try {
			finishAllActivity();
//			ActivityManager activityMgr = (ActivityManager) context
//					.getSystemService(Context.ACTIVITY_SERVICE);
//			activityMgr.killBackgroundProcesses(context.getPackageName());
//			context.unregisterReceiver(LockScreenReceiver.getScreenReceiver());
//			System.exit(0);
//			android.os.Process.killProcess(android.os.Process.myPid());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
