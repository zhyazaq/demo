package com.digitalnet.push.net;

import com.digitalnet.push.bean.LoginBean;
import com.digitalnet.push.bean.LogoutBean;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiService {
      @FormUrlEncoded
      @POST("Api/Auth/Token")
      Observable<LoginBean> login(@FieldMap Map<String,String> map);

      @FormUrlEncoded
      @POST("Api/Auth/Logout")
      Observable<LogoutBean> logout(@Header("token")String token, @Field("null")String s);


}
