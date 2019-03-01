package com.digitalnet.push.share;

import android.content.Context;
import android.content.SharedPreferences;

import com.digitalnet.push.base.BaseApplication;


public class SharedPrefHelper {
      /**
       * SharedPreferences的名字
       */
      private static final String SP_FILE_NAME = "APPLICATION_SP";
      private static SharedPrefHelper sharedPrefHelper = null;
      private static SharedPreferences sharedPreferences;

      public static synchronized SharedPrefHelper getInstance() {
            if (null == sharedPrefHelper) {
                  sharedPrefHelper = new SharedPrefHelper();
            }
            return sharedPrefHelper;
      }

      private SharedPrefHelper() {
            sharedPreferences = BaseApplication.mBaseApplication.getSharedPreferences(SP_FILE_NAME, Context.MODE_PRIVATE);
      }

      public void setUserIsLogin(boolean isLogin) {
            sharedPreferences.edit().putBoolean("userlogin", isLogin).commit();
      }

      public boolean getUserIsLogin() {
            return sharedPreferences.getBoolean("userlogin", false);
      }
      public void setUserSuccAlias(boolean isSuccess) {
            sharedPreferences.edit().putBoolean("isSuccess", isSuccess).commit();
      }

      public boolean getUserSuccAlias() {
            return sharedPreferences.getBoolean("isSuccess", false);
      }

      public void setUserToken(String token) {
            sharedPreferences.edit().putString("token", token).commit();
      }

      public String getUserToken() {
            return sharedPreferences.getString("token", "");
      }

      public void setUserAlias(String alias) {
            sharedPreferences.edit().putString("alias", alias).commit();
      }
      public String getUserAlias() {
            return sharedPreferences.getString("alias", "");
      }
      public String getUserName() {
            return sharedPreferences.getString("UserName", "");

      }  public void setUserName(String UserName) {
            sharedPreferences.edit().putString("UserName", UserName).commit();
      }
      public String getLoginName() {
            return sharedPreferences.getString("LoginName", "");

      }  public void setLoginName(String LoginName) {
            sharedPreferences.edit().putString("LoginName", LoginName).commit();
      }


      public void clearData() {
            sharedPreferences.edit().clear();
      }


}
