package com.digitalnet.push.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import java.util.List;


public class BrowserUtil {
      public static void hasBrowser(Context context){
            PackageManager pm=context.getPackageManager();
            Intent intent=new Intent(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_BROWSABLE);
            intent.setData(Uri.parse("http://"));
            @SuppressLint("WrongConstant") List<ResolveInfo> list=pm.queryIntentActivities(intent, PackageManager.GET_INTENT_FILTERS);
            if(list.size()>0){
                  for (int i = 0; i < list.size(); i++) {
                        ActivityInfo activityInfo=list.get(i).activityInfo;
                        Log.i("GetBrowserInfo", "The packageName is "+activityInfo.packageName+" "+activityInfo.name+"\n");
                  }
            }
            if(isAppInstalled(context, "com.android.chrome")){
                  Intent intent2=new Intent(Intent.ACTION_VIEW);
                  intent2.addCategory(Intent.CATEGORY_BROWSABLE);
                  intent2.setData(Uri.parse("http://www.baidu.com"));//复制的百度下载链接
                  intent2.setClassName("com.android.chrome", "com.google.android.apps.chrome.Main");
                  context.startActivity(intent2);
            }else{
                  Toast.makeText(context, "亲，您尚未安装谷歌浏览器，请先安装", Toast.LENGTH_SHORT).show();
                  //http://u.androidgame-store.com/new/game1/4/110904/com.android.chrome-49.0.2623.91-262309101.apk?f=baidu_1
                  Intent intent3=new Intent(Intent.ACTION_VIEW);
                  intent3.addCategory(Intent.CATEGORY_BROWSABLE);
                  intent3.setData(Uri.parse("http://u.androidgame-store.com/new/game1/4/110904/com.android.chrome-49.0.2623.91-262309101.apk?f=baidu_1"));
                  intent3.setClassName("com.android.browser", "com.android.browser.BrowserActivity");//调用系统浏览器下载，下载到系统的下载地址
                  context.startActivity(intent3);
            }
      }




      public static boolean isAppInstalled(Context context,String packageName){
            PackageInfo packageInfo;
            try {
                  packageInfo=context.getPackageManager().getPackageInfo(packageName, 0);
            } catch (PackageManager.NameNotFoundException e) {
                  // TODO Auto-generated catch block
                  packageInfo=null;
                  e.printStackTrace();
            }
            if(packageInfo==null){
                  return false;
            }else{
                  return true;
            }
      }

}
