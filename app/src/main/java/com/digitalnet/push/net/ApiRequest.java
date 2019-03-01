package com.digitalnet.push.net;

import com.digitalnet.push.base.BaseApplication;
import com.digitalnet.push.bean.LoginBean;
import com.digitalnet.push.bean.LogoutBean;
import com.digitalnet.push.share.SharedPrefHelper;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class ApiRequest {
      private static ApiService mApiService;
      public static void init(BaseApplication mContext) {
            File cacheDir = new File(mContext.getExternalCacheDir(), "response");
            mApiService = new RetrofitFactory<ApiService>().getRetrofitAPI(ApiService.class, BaseApplication.baseurl, cacheDir, BaseApplication.isDebug());
      }

      /**
       * 登入
       * @param username
       * @param pwd
       * @param netCallback
       */
      public  static  void login(String username,String pwd,NetCallback<LoginBean> netCallback){
            Map<String,String>params=new HashMap<>();
            params.put("username",username);
            params.put("pwd",pwd);
            mApiService.login(params).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(netCallback);

      }

      /***
       * 登出
       * @param netCallback
       */
      public  static  void logout(NetCallback<LogoutBean> netCallback){
            mApiService.logout(SharedPrefHelper.getInstance().getUserToken(),null).subscribeOn(Schedulers.io())
                      .observeOn(AndroidSchedulers.mainThread()).subscribe(netCallback);

      }
}
