package com.digitalnet.push.receiver;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.digitalnet.push.base.BaseApplication;
import com.digitalnet.push.bean.MessageEvent;
import com.digitalnet.push.ui.MainActivity;
import com.digitalnet.push.util.BrowserUtil;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cn.jpush.android.api.JPushInterface;

/**
 * 自定义接收器
 * 
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class MyReceiver extends BroadcastReceiver {
	private static final String TAG = "JPush=====>";
	private int mTaskId;

	@Override
	public void onReceive(Context context, Intent intent) {

		Bundle bundle = intent.getExtras();
		Log.e(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));


		if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
			Integer regId = bundle.getInt(JPushInterface.EXTRA_REGISTRATION_ID);
			Log.e(TAG, "[MyReceiver] 接收Registration Id : " + regId);


//			SharedPrefHelper.getInstance().setJpushStrMes(true);
		} else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
			String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
			Log.e(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " +message );
			//接收到消息
//			SharedPrefHelper.getInstance().setJpushStrMes(true);
			Intent intent1=new Intent("SHUAXIN");
			context.sendBroadcast(intent1);
		} else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
			Log.e(TAG, "[MyReceiver] 接收到推送下来的通知");
			int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
			BaseApplication.list.add(notifactionId);
//			SharedPrefHelper.getInstance().setJpushStrMes(true);
			Intent intent1=new Intent("SHUAXIN");
			context.sendBroadcast(intent1);

		} else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
			Log.e(TAG, "[MyReceiver] 用户点击打开了通知");
			String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
			JSONObject extrasJson = null;
			try {
				extrasJson = new JSONObject(extras);
				String path = extrasJson.optString("path");


				if(IsForeground(context) == false) {
					ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
					am.moveTaskToFront(mTaskId, ActivityManager.MOVE_TASK_WITH_HOME);
				}

				EventBus.getDefault().post(new MessageEvent(path));
//				Intent mIntent = new Intent(context, MainActivity.class);
//				bundle.putString("path", path);
//				mIntent.putExtras(bundle);
//				mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//				context.startActivity(mIntent);
			} catch (JSONException e) {
				e.printStackTrace();
			}


		} else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
			Log.e(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
			//在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

		}else if(JPushInterface.EXTRA_NOTIFICATION_TITLE.equals(intent.getAction())){
			//对应 Portal 推送通知界面上的“通知标题”字段
//			String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
//			String content = bundle.getString(JPushInterface.EXTRA_ALERT);
//			SystemUtils.left_out("这是我要的通知内容======-----------"+content);
			String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
		} else if(JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
			boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
			Log.e(TAG, "[MyReceiver]" + intent.getAction() +" connected state change to "+connected);
		} else {
			Log.e(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
		}
	}

	// 打印所有的 intent extra 数据
	private static String printBundle(Bundle bundle) {
		StringBuilder sb = new StringBuilder();
		for (String key : bundle.keySet()) {
			if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
				sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
			}else if(key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)){
				sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
			}
			else {
				sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
			}
		}
		return sb.toString();
	}



	public boolean IsForeground(Context context) {
		ActivityManager                       am    = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
		if (tasks != null && !tasks.isEmpty()) {
			ComponentName topActivity = tasks.get(0).topActivity;
			if (topActivity.getPackageName().equals(context.getPackageName())) {
				mTaskId = tasks.get(0).id;
				return true;
			}
		}
		return false;
	}

}
