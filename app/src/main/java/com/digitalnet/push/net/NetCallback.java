package com.digitalnet.push.net;

import com.digitalnet.push.bean.BaseBean;
import com.google.gson.JsonParseException;


import java.net.ConnectException;
import java.net.SocketTimeoutException;

import io.reactivex.observers.DisposableObserver;

public abstract class NetCallback<T> extends DisposableObserver<T> {
    private static final String NO_RESULT = "请求失败";
    private static final String LOGIN_INVALID = "登录失效，请重新登录！";
    private static final String NO_NETWORK = "网络不可用，请检查网络连接！";


    private static final String ERROR_TIME_OUT = "连接超时，请检查网络";
    private static final String ERROR_CONNECT = "连接异常";
    private static final String ERROR_PARSE = "数据解析错误！";

    @Override
    protected void onStart() {
        if (!NetWorkUtil.isNetDeviceAvailable()) {
            this.dispose();
            onFail(NO_NETWORK);
        }
    }

    @Override
    public void onNext(T t) {
        if (t != null) {
            if (t instanceof BaseBean) {
                if (((BaseBean) t).getCode() == 401) {
//                    BaseApplication.getApplication().goLogin();
                    onFail(LOGIN_INVALID);  //登录失效
                } else {
                    onSuccess(t);
                }
            } else {
                onSuccess(t);
            }
        } else {
            onFail(NO_RESULT);
        }
    }


    @Override
    public void onError(Throwable e) {
        if (e instanceof SocketTimeoutException) {
            onFail(ERROR_TIME_OUT);
        } else if (e instanceof ConnectException) {
            onFail(ERROR_CONNECT);
        } else if (e instanceof JsonParseException) {
            onFail(ERROR_PARSE);
        } else {
            onFail(NO_RESULT);
        }
    }

    @Override
    public void onComplete() {
    }

    /**
     * 请求成功的回调
     */
    public abstract void onSuccess(T response);

    /**
     * 请求失败的回调
     */
    public abstract void onFail(String msg);
}
