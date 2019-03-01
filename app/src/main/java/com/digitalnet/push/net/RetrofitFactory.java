package com.digitalnet.push.net;

import java.io.File;


public class RetrofitFactory<T> {

      private T retrofitAPI;

      public RetrofitFactory() {
      }

      private static final Object monitor = new Object();

      /**
       * @param clazz    apiInterface
       * @param baseUrl  主机地址
       * @param cacheDir retrofit缓存地址
       */
      public T getRetrofitAPI(Class<T> clazz, String baseUrl, File cacheDir, boolean isDebug) {
            synchronized (monitor) {
                  if (retrofitAPI == null) {
                        retrofitAPI = new RetrofitClient(baseUrl, cacheDir, isDebug).getRetrofit().create(clazz);
                  }
                  return retrofitAPI;
            }
      }

}
