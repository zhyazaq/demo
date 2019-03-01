package com.digitalnet.push.bean;

import com.google.gson.annotations.SerializedName;



public class BaseBean<T> {

    public BaseBean(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @SerializedName(value = "code", alternate = {"errorCode"})
    private int code;

    @SerializedName(value = "message",alternate = {"msg"})
    private String message = "数据有误，请联系客服人员～";

    @SerializedName(value = "resultObj", alternate = {"result"})
    private T mResult;

    @SerializedName("timestamp")
    private String timestamp;

    private String token;

    private String vtoken ;

    private String ticket ;

    private String WithdrawalsId;//返回订单

    public String getWithdrawalsId() {
        return WithdrawalsId;
    }

    public void setWithdrawalsId(String withdrawalsId) {
        WithdrawalsId = withdrawalsId;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }
    public String getVtoken() {
        return vtoken;
    }

    public void setVtoken(String vtoken) {
        this.vtoken = vtoken;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResult() {
        return mResult;
    }

    public void setResult(T result) {
        mResult = result;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
