package com.digitalnet.push.base;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;

import com.digitalnet.push.Constants;
import com.digitalnet.push.R;
import com.digitalnet.push.share.SharedPrefHelper;
import com.digitalnet.push.util.AppManager;
import com.digitalnet.push.util.StatusBarUtil;
import com.gyf.barlibrary.ImmersionBar;
import com.zhy.autolayout.AutoLayoutActivity;


public abstract class BaseActivity extends AutoLayoutActivity {
      private ImmersionBar mImmersionBar;
      @Override
      protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            mImmersionBar = ImmersionBar.with(this);
            mImmersionBar.init();   //所有子类都将继承这些相同的属性
            AppManager.getAppManager().addActivity(this);
            setContentView(getLayoutId());
            initData();
      }
      public abstract  int getLayoutId();
      public abstract  void  initData();

      //让view抖一抖的方法
      public void dyd(EditText editText) {
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            editText.startAnimation(shake);
      }

      @Override
      protected void onDestroy() {
            super.onDestroy();
            if (mImmersionBar != null)
                  mImmersionBar.destroy();  //必须调用该方法，防止内存泄漏，不调用该方法，如果界面bar发生改变，在不关闭app的情况下，退出此界面再进入将记忆最后一次bar改变的状态
      }

}
