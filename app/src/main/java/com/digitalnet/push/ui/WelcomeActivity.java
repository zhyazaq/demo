package com.digitalnet.push.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.digitalnet.push.R;
import com.digitalnet.push.base.BaseActivity;
import com.digitalnet.push.share.SharedPrefHelper;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class WelcomeActivity extends BaseActivity {

      private Disposable mDisposable;



      @Override
      public int getLayoutId() {
            return R.layout.activity_welcom;
      }

      @Override
      public void initData() {

      }

      @Override
      protected void onResume() {
            super.onResume();
            mDisposable = Observable.interval(3, TimeUnit.SECONDS).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Long>() {
                  @Override
                  public void accept(Long aLong) throws Exception {
                        if (SharedPrefHelper.getInstance().getUserIsLogin()) {
                              startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
                        }else{
                              startActivity(new Intent(WelcomeActivity.this,LoginActivity.class));
                        }
                        finish();
                  }
            });

      }

      @Override
      protected void onDestroy() {
            super.onDestroy();
            if (!mDisposable.isDisposed()) {
                  mDisposable.dispose();
            }
      }
}
