package com.digitalnet.push.bean;

/**
 * 北京润农金服科技有限公司  版权所有
 * Copyright (c) 2018 www.runnongjinfu.com All Rights Reserved
 * <p>
 * 作者：曾红永  Email：751086743@qq.com
 * 创建时间：2018/11/28 19:39  星期三
 * 类描述 ： This is  LogoutBean
 * </p>
 **/
public class LogoutBean {

      /**
       * flag : true
       * msg : 注销成功
       * data : null
       */

      private boolean flag;
      private String msg;
      private Object data;

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

      public Object getData() {
            return data;
      }

      public void setData(Object data) {
            this.data = data;
      }
}
