package com.digitalnet.push.bean;

/**
 * 北京润农金服科技有限公司  版权所有
 * Copyright (c) 2018 www.runnongjinfu.com All Rights Reserved
 * <p>
 * 作者：曾红永  Email：751086743@qq.com
 * 创建时间：2018/11/28 18:52  星期三
 * 类描述 ： This is  LoginBean
 * </p>
 **/
public class LoginBean {

      /**
       * flag : true
       * msg : 登录成功
       * data : {"Id":1,"UserName":"裴可","UserNameEn":"SHB_marketing","LoginName":"SHB_marketing","PhoneNum":"15952168282","Token":"4a1883a7-7124-4203-9fee-72dc4add7a43"}
       */

      private boolean flag;
      private String   msg;
      private DataBean data;

      public boolean isFlag() {
            return flag;
      }

      public void setFlag(boolean flag) {
            this.flag = flag;
      }

      public String getMsg() {
            return msg;
      }

      public void setMsg(String msg) {
            this.msg = msg;
      }

      public DataBean getData() {
            return data;
      }

      public void setData(DataBean data) {
            this.data = data;
      }

      public static class DataBean {
            /**
             * Id : 1
             * UserName : 裴可
             * UserNameEn : SHB_marketing
             * LoginName : SHB_marketing
             * PhoneNum : 15952168282
             * Token : 4a1883a7-7124-4203-9fee-72dc4add7a43
             */

            private String Id;
            private String UserName;
            private String UserNameEn;
            private String LoginName;
            private String PhoneNum;
            private String Token;

            public String getId() {
                  return Id;
            }

            public void setId(String Id) {
                  this.Id = Id;
            }

            public String getUserName() {
                  return UserName;
            }

            public void setUserName(String UserName) {
                  this.UserName = UserName;
            }

            public String getUserNameEn() {
                  return UserNameEn;
            }

            public void setUserNameEn(String UserNameEn) {
                  this.UserNameEn = UserNameEn;
            }

            public String getLoginName() {
                  return LoginName;
            }

            public void setLoginName(String LoginName) {
                  this.LoginName = LoginName;
            }

            public String getPhoneNum() {
                  return PhoneNum;
            }

            public void setPhoneNum(String PhoneNum) {
                  this.PhoneNum = PhoneNum;
            }

            public String getToken() {
                  return Token;
            }

            public void setToken(String Token) {
                  this.Token = Token;
            }
      }
}
