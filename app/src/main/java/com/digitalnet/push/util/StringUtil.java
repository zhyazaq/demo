package com.digitalnet.push.util;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.TextAppearanceSpan;

import java.math.BigDecimal;
import java.text.DecimalFormat;


public class StringUtil {

	/**
	 * 判断字符串是否为null或者空字符串
	 * @param str
	 * @return
	 */
	public static boolean isNullOrEmpty(String str) {
		boolean result = false;
		if (null == str || "".equals(str.trim())||"o".equals(str)||"null".equals(str.trim())) {
			result = true;
		}
		return result;
	}

	/**
	 * 如果i小于10，添加0后生成string
	 * @param i
	 * @return
	 */
	public static String addZreoIfLessThanTen(int i) {

		String string = "";
		int ballNum = i + 1;
		if (ballNum < 10) {
			string = "0" + ballNum;
		} else {
			string = ballNum + "";
		}
		return string;
	}
	public static String getNotNullString(String str){
		if (str==null) {
			str="";
		}
		return str;
	}
	/**
	 * 
	 * @param string
	 * @return
	 */
	public static boolean isNotNull(String string) {
		if (null != string && !"".equals(string.trim())&&!"o".equals(string)&&!"null".equals(string.trim())) {
			return true;
		}
		return false;
	}

	/**
	 * 去掉一个字符串中的所有的单个空格" "
	 * @param string
	 */
	public static String replaceSpaceCharacter(String string) {
		if (null == string || "".equals(string)) {
			return "";
		}
		return string.replace(" ", "");
	}

	/**
	 * 获取小数位为6位的经纬度
	 * @param string
	 * @return
	 */
	public static String getStringLongitudeOrLatitude(String string) {
		if (StringUtil.isNullOrEmpty(string)) {
			return "";
		}
		if (string.contains(".")) {
			String[] splitArray = string.split("\\.");
			if (splitArray[1].length() > 6) {
				String substring = splitArray[1].substring(0, 6);
				return splitArray[0] + "." + substring;
			} else {
				return string;
			}
		} else {
			return string;
		}
	}
	private static BigDecimal bigDecimal;
	private static DecimalFormat fnum;

	/**
	 * 保留小数点后两位
	 * 
	 * @param value
	 * @return
	 */
	public static String getStringFormat(double value) {
		if (value == 0) {
			return "0.00";
		}
		try {
			bigDecimal = new BigDecimal(value);
			fnum = new DecimalFormat("#0.00");
			return fnum.format(bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP)
					.doubleValue());
		} catch (Exception e) {
			e.printStackTrace();
			return "0.00";
		}
	}

	/**
	 * 保留小数点后一位
	 * 
	 * @param value
	 * @return
	 */
	public static String getOneStringFormat(double value) {
		if (value == 0) {
			return "0";
		}
		try {
			bigDecimal = new BigDecimal(value);
			fnum = new DecimalFormat("#0.0");
			return fnum.format(bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP)
					.doubleValue());
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}
	//把一个字符串中的大写转为小写，小写转换为大写：思路1
	public static String lowToup(String str){
		StringBuffer sb = new StringBuffer();
		if(str!=null){
			for(int i=0;i<str.length();i++){

				char c = str.charAt(i);
				if(Character.isUpperCase(c)){
					sb.append(Character.toLowerCase(c));
				}else if(Character.isLowerCase(c)){
					sb.append(Character.toUpperCase(c));
				}
			}
		}

		return sb.toString();
	}

	//把一个字符串中的大写转为小写，小写转换为大写：思路2
	public static String upTolow(String str){
		for(int i=0;i<str.length();i++){
			//如果是小写
			if(str.substring(i, i+1).equals(str.substring(i, i+1).toLowerCase())){
				str.substring(i, i+1).toUpperCase();
			}else{
				str.substring(i, i+1).toLowerCase();
			}
		}
		return str;
	}

//	/***
//	 *  判读字符串是否有乱码
//	 * @param message
//	 * @return
//	 */
//	public  static  boolean ChcekMessCode(String message){
//		char[] bytes = message.toCharArray();
//		for(int i=0;i< bytes.length;i++){
//			boolean isMessCOde= RegexpUtils.isMessyCode(String.valueOf(bytes[i]));
//			if(isMessCOde){
//				return  true;
//			}
//
//		}
//		return false;
//	}
	/**
	 * 设置“xxx元” 元之前的数字缩放，标色
	 * @param context
	 * @param spanTextString   要处理的文字
	 * @param spanTextColorRes  想要缩放的文字的颜色
	 * @param spanTextSize   单位px
	 * @return
	 */
	public static SpannableString generateStr(Context context, @NonNull String spanTextString, @ColorRes int spanTextColorRes, int spanTextSize){
		if (spanTextString==null) {
			spanTextString="";
		}
		int yuan=spanTextString.indexOf("元");
		if (yuan<0) {
			yuan=0;
		}
		SpannableString spannableString=new SpannableString(spanTextString);
		spannableString.setSpan(new TextAppearanceSpan(null, 0, spanTextSize, null, null),0,yuan, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		spannableString.setSpan(new ForegroundColorSpan(context.getResources().getColor(spanTextColorRes)),0,yuan, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		return spannableString;
	}
	public static String settingphone(String phone) {
		if(StringUtil.isNullOrEmpty(phone)){
			return "";
		}
		String phone_s = phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
		return phone_s;
	}

}
