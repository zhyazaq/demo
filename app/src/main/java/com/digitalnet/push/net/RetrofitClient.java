package com.digitalnet.push.net;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitClient {
      private long TIMEOUT_READ = 60;
      private long TIMEOUT_CONNECTION = 60;
      private long TIMEOUT_WRITE = 60;
      private String baseUrl;
      private File cacheDir;
      private boolean isDebug;

      public RetrofitClient(String baseUrl, File cacheDir, boolean isDebug) {
            this.baseUrl=baseUrl;
            this.cacheDir=cacheDir;
            this.isDebug=isDebug;
      }

      public Retrofit getRetrofit() {
            return new Retrofit.Builder()
                      //设置OKHttpClient
                      .client(getOkHttpClient())
                      //baseUrl
                      .baseUrl("http://123.126.109.195:8051")
                      //gson转化器
                      .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                      .addConverterFactory(GsonConverterFactory.create())
                      .build();
      }

      /**
       * 设置okhttpClient 缓存相关等
       */
      public OkHttpClient getOkHttpClient() {
            Cache cache = new Cache(cacheDir, 10 * 1024 * 1024);
            HttpsUtils.SSLParams sslParams = null;
            try {
                  sslParams = HttpsUtils.getSslSocketFactory(null, null, null);
//                  sslParams = HttpsUtils.getSslSocketFactory(new InputStream[]{CommonApplication.getApplication().getAssets().open("dkcs.cer")}, null, null);
            } catch (Exception e) {
                  sslParams = HttpsUtils.getSslSocketFactory(null, null, null);
            }
            return new OkHttpClient.Builder()
                      //添加head
                      //打印请求log
                      .addInterceptor(new LoggerInterceptor("HttpTag", isDebug))
                      //必须是设置Cache目录
                      .cache(cache)
                      //time out
                      .readTimeout(TIMEOUT_READ, TimeUnit.SECONDS)
                      .connectTimeout(TIMEOUT_CONNECTION, TimeUnit.SECONDS)
                      .writeTimeout(TIMEOUT_WRITE, TimeUnit.SECONDS)
                      .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                      .build();
      }
}
