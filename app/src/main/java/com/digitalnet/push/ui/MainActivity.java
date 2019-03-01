package com.digitalnet.push.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.digitalnet.push.R;
import com.digitalnet.push.base.BaseActivity;
import com.digitalnet.push.base.BaseApplication;
import com.digitalnet.push.bean.LogoutBean;
import com.digitalnet.push.bean.MessageEvent;
import com.digitalnet.push.net.ApiRequest;
import com.digitalnet.push.net.NetCallback;
import com.digitalnet.push.share.SharedPrefHelper;
import com.digitalnet.push.util.LogoutUtil;
import com.digitalnet.push.util.NotificationsUtils;
import com.digitalnet.push.util.StringUtil;
import com.digitalnet.push.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

public class MainActivity extends BaseActivity implements View.OnClickListener {


      private TextView mTvUser;
      private TextView mTvGotoNet;
      private TextView mTvGotoLogout;

      @Override
      public int getLayoutId() {
            return R.layout.activity_main;
      }

      @Override
      protected void onResume() {
            super.onResume();
            //绑定别名在onresume 不用担心重复绑定以及效率问题
            if (!SharedPrefHelper.getInstance().getUserSuccAlias()) {
                  setAlias(SharedPrefHelper.getInstance().getUserAlias());
            }
//           String path= getIntent().getStringExtra("path");
//            if(StringUtil.isNotNull(path)){
//                  openChrome(path);
//            }
      }

      @Override
      protected void onStart() {
            super.onStart();
            EventBus.getDefault().register(this);
      }

      @Override
      protected void onStop() {
            super.onStop();
            EventBus.getDefault().unregister(this);
      }

      @Subscribe(threadMode = ThreadMode.MAIN)
      public void onMessageEvent(MessageEvent messageEvent) {
            openChrome(messageEvent.msg);
      }

      /***
       * 打开浏览器跳转对应的url
       * @param url
       */
      private void openChrome(String url) {


//            Bundle bundle =new Bundle();
//            bundle.putString("url", url);
//            bundle.putString("title", "DME");
//            startActivity(new Intent(MainActivity.this,WebViewActivity.class), bundle);
//
            if(Build.BRAND.equalsIgnoreCase("oppo" )|| Build.MODEL.equalsIgnoreCase("oppo")){
                  Bundle bundle =new Bundle();
                  bundle.putString("url", url);
                  bundle.putString("title", "DME");
                  startActivity(new Intent(MainActivity.this,WebViewActivity.class), bundle);
            }else{
                  Uri uri = Uri.parse(url);
                  Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                  startActivity(intent);
            }

//


      }

      // 这是来自 JPush Example 的设置别名的 Activity 里的代码。一般 App 的设置的调用入口，在任何方便的地方调用都可以。
      private void setAlias(String alias) {
            // 调用 Handler 来异步设置别名
            mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, alias));
      }

      private final        TagAliasCallback mAliasCallback = new TagAliasCallback() {
            @Override
            public void gotResult(int code, String alias, Set<String> tags) {
                  String logs;
                  switch (code) {
                        case 0:
                              logs = "Set tag and alias success";
                              // 建议这里往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
                              SharedPrefHelper.getInstance().setUserSuccAlias(true);
                              if (mHandler != null) {
                                    mHandler.removeCallbacksAndMessages(MSG_SET_ALIAS);
                              }
                              break;
                        case 6002:
                              // 延迟 60 秒来调用 Handler 设置别名
                              if (!SharedPrefHelper.getInstance().getUserSuccAlias()) {
                                    mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
                              }
                              break;
                        default:
                              logs = "Failed with errorCode = " + code;
                              break;
                  }
            }
      };
      private static final int              MSG_SET_ALIAS  = 1001;
      private final        Handler          mHandler       = new Handler() {
            @Override
            public void handleMessage(android.os.Message msg) {
                  super.handleMessage(msg);
                  switch (msg.what) {
                        case MSG_SET_ALIAS:
                              // 调用 JPush 接口来设置别名。
                              JPushInterface.setAliasAndTags(getApplicationContext(),
                                        (String) msg.obj,
                                        null,
                                        mAliasCallback);
                              SharedPrefHelper.getInstance().setUserSuccAlias(true);
                              break;
                        default:
                              break;
                  }
            }
      };

      @Override
      public void initData() {
            initView();
            startOpenNotifiction();
      }

      private void initView() {
            mTvUser = (TextView) findViewById(R.id.tv_user);
            mTvGotoNet = (TextView) findViewById(R.id.tv_goto_net);
            mTvGotoNet.setOnClickListener(this);
            mTvGotoLogout = (TextView) findViewById(R.id.tv_goto_logout);
            mTvGotoLogout.setOnClickListener(this);
            mTvUser.setText("您好，" + SharedPrefHelper.getInstance().getUserName() + " \n 登录成功");
      }

      @Override
      public void onClick(View view) {
            switch (view.getId()) {
                  case R.id.tv_goto_net://前往网页版
                        openChrome("http://123.126.109.195:8048/dme_mobile/Home/Main.aspx?loginname=" + SharedPrefHelper.getInstance().getLoginName());
                        break;
                  case R.id.tv_goto_logout://退出登录
                        showSureAndBackDialog(new View.OnClickListener() {
                              @Override
                              public void onClick(View view) {
                                    dissmissDialog();
                              }
                        }, new View.OnClickListener() {
                              @Override
                              public void onClick(View view) {
                                    logout();
                              }
                        });
                        break;
            }

      }

      /***
       * 登出
       */
      private void logout() {
            ApiRequest.logout(new NetCallback<LogoutBean>() {
                  @Override
                  public void onSuccess(LogoutBean response) {
                        if (response != null && response.isFlag()) {
                              SharedPrefHelper.getInstance().setUserAlias("");
                              SharedPrefHelper.getInstance().setUserName("");
                              SharedPrefHelper.getInstance().setLoginName("");
                              SharedPrefHelper.getInstance().setUserToken("");
                              SharedPrefHelper.getInstance().setUserSuccAlias(false);
                              SharedPrefHelper.getInstance().setUserIsLogin(false);
                              SharedPrefHelper.getInstance().clearData();
                              for (int i = 0; i < BaseApplication.list.size(); i++) {
                                    JPushInterface.clearNotificationById(MainActivity.this, BaseApplication.list.get(i));
                              }
                              JPushInterface.clearAllNotifications(MainActivity.this);
                              if (!JPushInterface.isPushStopped(MainActivity.this)) {
                                    JPushInterface.stopPush(MainActivity.this);
                              }
                              startActivity(new Intent(MainActivity.this, LoginActivity.class));
                              finish();
                        } else {
                              ToastUtil.showToast(response.getMsg());
                        }

                  }

                  @Override
                  public void onFail(String msg) {

                  }
            });

      }


      /***
       * 前往设置开启通知栏权限，7.0默认通知栏权限关闭
       */
      private void startOpenNotifiction() {
            if (!NotificationsUtils.isNotificationEnabled(this)) {
                  showSureOrCancel(false, true, "检测到您没有打开通知权限，是否去打开",
                            false, "取消", new View.OnClickListener() {
                                  @Override
                                  public void onClick(View v) {
                                        dismissSureAndBackDialog();
                                  }
                            }, "确定", new View.OnClickListener() {
                                  @Override
                                  public void onClick(View v) {
                                        dismissSureAndBackDialog();
                                        Intent localIntent = new Intent();
                                        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        if (Build.VERSION.SDK_INT >= 9) {
                                              localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                                              localIntent.setData(Uri.fromParts("package", MainActivity.this.getPackageName(), null));
                                        } else if (Build.VERSION.SDK_INT <= 8) {
                                              localIntent.setAction(Intent.ACTION_VIEW);
                                              localIntent.setClassName("com.android.settings",
                                                        "com.android.settings.InstalledAppDetails");
                                              localIntent.putExtra("com.android.settings.ApplicationPkgName",
                                                        MainActivity.this.getPackageName());
                                        }
                                        startActivity(localIntent);
                                  }
                            });
            }
      }


      //带有两个按钮的弹框提示
      private Dialog showVipNightDialog = null;
      private View         layoutNight;
      private TextView     mTextViewContent;
      private TextView     mTextViewCancel;
      private TextView     mTextViewSure;
      private TextView     mTextViewOnlySure;
      private ImageView    mImageViewStatus;
      private LinearLayout mLinearLayoutButtom;
      private LinearLayout mLinearLayoutOnlySure;

      /***
       *
       * @param content
       * @param cancleable
       * @param cancelContent  想用默认“确认” 直接传null
       * @param onCancleListener
       * @param sureContent 想用默认“取消” 直接传null
       * @param OnSureListener  showSureOrCancel
       */
      public void showSureOrCancel(boolean isOnlySure, boolean successOrFail, String content, boolean cancleable, String cancelContent, View.OnClickListener onCancleListener, String sureContent, View.OnClickListener OnSureListener) {
            if (!this.isFinishing()) {
                  if (showVipNightDialog == null) {
                        showVipNightDialog = new Dialog(this, R.style.update_dialog);
                        layoutNight = View.inflate(this, R.layout.dialog_sure_or_cancel, null);
                        mTextViewSure = ((TextView) layoutNight.findViewById(R.id.bt_sure));
                        mTextViewCancel = ((TextView) layoutNight.findViewById(R.id.bt_cancel));
                        mTextViewContent = ((TextView) layoutNight.findViewById(R.id.tv_content));
                        mTextViewOnlySure = ((TextView) layoutNight.findViewById(R.id.tv_only_sure));
                        mImageViewStatus = ((ImageView) layoutNight.findViewById(R.id.img_dialog_status));
                        mLinearLayoutButtom = ((LinearLayout) layoutNight.findViewById(R.id.ll_dialog_buttom));
                        mLinearLayoutOnlySure = ((LinearLayout) layoutNight.findViewById(R.id.ll_sure_only));
                  }
                  mTextViewContent.setText(content);
                  if (cancelContent != null) {
                        mTextViewCancel.setText(cancelContent);
                  }
                  if (successOrFail) {
                        mImageViewStatus.setImageResource(R.drawable.img_dialog_success);
                  } else {
                        mImageViewStatus.setImageResource(R.drawable.img_dialog_wraming);
                  }
                  if (isOnlySure) {
                        mLinearLayoutOnlySure.setVisibility(View.VISIBLE);
                        mLinearLayoutButtom.setVisibility(View.GONE);
                  } else {
                        mLinearLayoutOnlySure.setVisibility(View.GONE);
                        mLinearLayoutButtom.setVisibility(View.VISIBLE);
                  }
                  if (sureContent != null) {
                        mTextViewSure.setText(sureContent);
                  }
                  if (onCancleListener != null) {
                        mTextViewCancel.setOnClickListener(onCancleListener);
                        mTextViewCancel.setVisibility(View.VISIBLE);
                  } else {
                        mTextViewCancel.setVisibility(View.GONE);
                  }
                  if (OnSureListener != null) {
                        mTextViewSure.setOnClickListener(OnSureListener);
                        mTextViewOnlySure.setOnClickListener(OnSureListener);
                  }
                  showVipNightDialog.setContentView(layoutNight);
                  showVipNightDialog.show();
                  showVipNightDialog.setCancelable(cancleable);
                  Window                     dialogWindow = showVipNightDialog.getWindow();
                  WindowManager.LayoutParams lp           = dialogWindow.getAttributes();
                  dialogWindow.setGravity(Gravity.LEFT | Gravity.TOP);
                  lp.x = (int) (getScreenWidth() * 0.15); // 新位置X坐标
                  lp.y = (int) (getScreenHeight() * 0.15); // 新位置Y坐标
                  lp.width = (int) (getScreenWidth() * 0.70); // 宽度
                  lp.height = (int) (getScreenHeight() * 0.42); // 高度
                  dialogWindow.setAttributes(lp);


            }
      }

      /**
       * 得到屏幕宽度
       *
       * @return 宽度
       */
      public int getScreenWidth() {
            DisplayMetrics dm = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(dm);
            int screenWidth = dm.widthPixels;
            return screenWidth;
      }

      /**
       * 得到屏幕高度
       *
       * @return 高度
       */
      public int getScreenHeight() {
            DisplayMetrics dm = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(dm);
            int screenHeight = dm.heightPixels;
            return screenHeight;
      }

      public void dismissSureAndBackDialog() {
            if (showVipNightDialog != null && showVipNightDialog.isShowing()) {
                  showVipNightDialog.dismiss();
            }

      }

      public void dissmissDialog() {
            if (showDialog != null && showDialog.isShowing()) {
                  showDialog.dismiss();
            }

      }


      @Override
      protected void onDestroy() {
            super.onDestroy();
            dismissSureAndBackDialog();
            dissmissDialog();
      }

      @Override
      public void onBackPressed() {
            LogoutUtil.getInstence().exit(this);
      }


      //带有两个按钮的弹框提示
      private AlertDialog showDialog = null;
      private View         layoutDialog;
      private Button       vip_give_up;
      private Button       vip_sure;
      private TextView     tv_vip_back;
      private TextView     tv_title;
      private LinearLayout ll_title;

      /***
       *
       /***
       *
       * @param needTitle 是否需要标题
       * @param titile   标题内容
       * @param content  内容
       * @param cancleable  是否点击外部能取消
       *@param cancelContent      左边按钮的文字
       * @param onCancleListener  取消的监听--左边
       *  @param sureContent       右边按钮的文字
       * @param OnSureListener     确认的监听---右边
       */
      public void showSureAndBackDialog(View.OnClickListener onCancleListener, View.OnClickListener OnSureListener) {
            if (!this.isFinishing()) {
                  if (showDialog == null) {
                        showDialog = new AlertDialog.Builder(new ContextThemeWrapper(this, R
                                  .style.update_dialog)).create();
                        layoutDialog = View.inflate(this, R.layout.hint_vip_back_dialog_layout, null);
                        vip_give_up = ((Button) layoutDialog.findViewById(R.id.tv_vip_giveup));
                        vip_sure = ((Button) layoutDialog.findViewById(R.id.tv_vip_sure));
                        tv_vip_back = ((TextView) layoutDialog.findViewById(R.id.tv_vip_back));
                        tv_title = ((TextView) layoutDialog.findViewById(R.id.tv_title));
                        ll_title = (LinearLayout) layoutDialog.findViewById(R.id.ll_title);
                  }
                  ll_title.setVisibility(View.GONE);


                  if (onCancleListener != null) {
                        vip_give_up.setOnClickListener(onCancleListener);
                  } else {
                        vip_give_up.setVisibility(View.GONE);
                  }
                  if (OnSureListener != null) {
                        vip_sure.setOnClickListener(OnSureListener);
                  } else {
                        vip_sure.setVisibility(View.GONE);
                  }
                  showDialog.show();
                  showDialog.setCancelable(false);
                  WindowManager.LayoutParams params = showDialog.getWindow().getAttributes();
                  params.width = (int) (getScreenWidth() * 0.75);
                  params.format = PixelFormat.TRANSPARENT;
                  showDialog.setContentView(layoutDialog);
                  showDialog.getWindow().setAttributes(params);
            }
      }
}
