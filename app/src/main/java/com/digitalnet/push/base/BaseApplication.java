package com.digitalnet.push.base;

import android.app.Application;
import android.os.Handler;

import com.digitalnet.push.net.ApiRequest;
import com.digitalnet.push.net.NetWorkUtil;
import com.gyf.barlibrary.ImmersionBar;
import com.lzy.okgo.OkGo;
import com.zhy.autolayout.config.AutoLayoutConifg;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;


public class BaseApplication extends Application {
     public static String baseurl;
     public static boolean mIsDebug;
     public static   BaseApplication mBaseApplication;
      private static Handler       mHandler = new Handler();
      public static  List<Integer> list     = new ArrayList<>();

      public static Handler getHandle() {
            return mHandler;
      }
      @Override
      public void onCreate() {
            super.onCreate();

            mBaseApplication=this;
            baseurl="http://101.200.128.107:10203/";
            NetWorkUtil.init(this);
            ApiRequest.init(this);
            JPushInterface.setDebugMode(true);
            JPushInterface.init(this);
            AutoLayoutConifg.getInstance().useDeviceSize();
            OkGo.getInstance().init(this);
            mIsDebug=false;
      }

      public static boolean isDebug() {
            return mIsDebug;
      }
}
