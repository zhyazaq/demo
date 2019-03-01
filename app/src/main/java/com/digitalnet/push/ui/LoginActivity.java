package com.digitalnet.push.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.digitalnet.push.R;
import com.digitalnet.push.base.BaseActivity;
import com.digitalnet.push.bean.LoginBean;
import com.digitalnet.push.net.ApiRequest;
import com.digitalnet.push.net.NetCallback;
import com.digitalnet.push.net.NetWorkUtil;
import com.digitalnet.push.share.SharedPrefHelper;
import com.digitalnet.push.util.StringUtil;
import com.digitalnet.push.util.ToastUtil;
import com.digitalnet.push.util.VerifyCheck;

import cn.jpush.android.api.JPushInterface;

import static com.digitalnet.push.util.ToastUtil.showToast;


public class LoginActivity extends BaseActivity implements View.OnClickListener {


      private android.widget.EditText mTvUserPhone;
      private android.widget.EditText mTvUserPwd;
      private android.widget.TextView mTvLoginBt;

      @Override
      public int getLayoutId() {
            return R.layout.activity_login;
      }

      @Override
      public void initData() {
            mTvUserPhone = (EditText) findViewById(R.id.tv_user_phone);
          if(mTvUserPhone.getText()!=null &&  mTvUserPhone.getText().length() !=0){
                mTvUserPhone.setSelection(mTvUserPhone.getText().length());
          }
            mTvUserPwd = (EditText) findViewById(R.id.tv_user_pwd);
            mTvLoginBt = (TextView) findViewById(R.id.tv_login_bt);
            mTvLoginBt.setOnClickListener(this);
      }



      @Override
      public void onClick(View view) {
            switch (view.getId()){
                  case R.id.tv_login_bt:
                        if (!NetWorkUtil.isNetDeviceAvailable()) {
                              showToast(R.string.network_is_not_available);
                              return;
                        }
                        String username = mTvUserPhone.getText().toString().trim();
                        String pwd = mTvUserPwd.getText().toString().trim();
                        if (StringUtil.isNullOrEmpty(username)) {
                              showToast("用户名不能为空！");
                              dyd(mTvUserPhone);
                              mTvUserPhone.requestFocus();
                              return;
                        }

                        if (StringUtil.isNullOrEmpty(pwd)) {
                              showToast("密码不能为空！");
                              dyd(mTvUserPwd);
                              mTvUserPwd.requestFocus();
                              return;
                        }
                        userLogin(username,pwd);
                        break;
            }
      }

      /***
       * 账号登录
       * @param username
       * @param pwd
       */
      private void userLogin(String username, String pwd) {
            ApiRequest.login(username, pwd, new NetCallback<LoginBean>() {
                  @Override
                  public void onSuccess(LoginBean response) {
                        if (response != null && response.isFlag()&& response.getData()!=null) {
                              SharedPrefHelper.getInstance().setUserAlias(response.getData().getId());
                              SharedPrefHelper.getInstance().setUserName(response.getData().getUserName());
                              SharedPrefHelper.getInstance().setLoginName(response.getData().getLoginName());
                              SharedPrefHelper.getInstance().setUserIsLogin(true);
                              SharedPrefHelper.getInstance().setUserToken(response.getData().getToken());
                              if(JPushInterface.isPushStopped(LoginActivity.this)){
                                    JPushInterface.resumePush(LoginActivity.this);
                              }
                              startActivity(new Intent(LoginActivity.this,MainActivity.class));
                        }else{
                              ToastUtil.showToast(response==null?"数据错误，请联系技术人员":response.getMsg());
                        }
                  }
                  @Override
                  public void onFail(String msg) {

                  }
            });

      }
}
