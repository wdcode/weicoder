package com.weicoder.common.util;

import java.util.regex.Pattern;

import com.weicoder.common.constants.C;

/**
 * 用于校验字符串是否符合正则表达式
 * 
 * @author WD
 */
public sealed class RegexUtil permits U.RE {
	/**
	 * 字母 正则表达式为 ^[a-zA-Z]+$
	 * 
	 * @param str 要验证的字符串
	 * @return true false
	 */
	public static boolean isLetters(String str) {
		return is(C.R.LETTERS, str);
	}

	/**
	 * 联通、电信号段 正则表达式为 ^(13[0-3]|15[36]|189)(\\d){8}$
	 * 
	 * @param str 要验证的字符串
	 * @return true false
	 */
	public static boolean isCUQMobile(String str) {
		return is(C.R.CUQMOBILE, str);
	}

	/**
	 * 电信号段 正则表达式为 ^(133)(\\d){8}$
	 * 
	 * @param str 要验证的字符串
	 * @return true false
	 */
	public static boolean isTELEMobile(String str) {
		return is(C.R.TELEMOBILE, str);
	}

	/**
	 * 只能含有中文和英文 正则表达式为 ^[a-zA-Z\u4e00-\u9fa5 .]+$
	 * 
	 * @param str 要验证的字符串
	 * @return true false
	 */
	public static boolean isRealName(String str) {
		return is(C.R.REALNAME, str);
	}

	/**
	 * 只能输入6-20个字母、数字、下划线 正则表达式为 ^(\\w){6,20}$
	 * 
	 * @param str 要验证的字符串
	 * @return true false
	 */
	public static boolean isPwd(String str) {
		return is(C.R.PWD, str);
	}

	/**
	 * 校验密码
	 * 
	 * @param str 要验证的字符串
	 * @return true false
	 */
	public static boolean isPassword(String str) {
		return is(C.R.PASSWORD, str);
	}

	/**
	 * 日期 正则表达式为 (\\d{4})-(0[1-9]|1[0-2])-(0[1-9]|1[0-9]|2[0-9]|3[0-1])$
	 * 
	 * @param str 要验证的字符串
	 * @return true false
	 */
	public static boolean isDate(String str) {
		return is(new String[] { C.R.DATE_YYYYMMDD, C.R.DATE_YYYY_MM_DD, C.R.DATE_Y_M_D_H_M_S, C.R.DATE_Y_M_D_H_M,
				C.R.DATE_YMD_H_M_S, C.R.DATE_HH_MM_SS }, str);
	}

	/**
	 * Email 正则表达式为 ^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)+$
	 * 
	 * @param str 要验证的字符串
	 * @return true false
	 */
	public static boolean isEmail(String str) {
		return is(C.R.EMAIL, str);
	}

	/**
	 * 只能输入4-18个以字母开头、可带数字、"_"的字串 正则表达式为 ^[a-zA-Z]{1}[\\w]{3,17}$
	 * 
	 * @param str 要验证的字符串
	 * @return true false
	 */
	public static boolean isUserName(String str) {
		return is(C.R.USERNAME, str);
	}

	/**
	 * 电话 正则表达式为 ^[+]{0,1}(\\d){1,3}[ ]?([-]?((\\d)|[ ]){1,12})+$
	 * 
	 * @param str 要验证的字符串
	 * @return true false
	 */
	public static boolean isPhone(String str) {
		return is(C.R.PHONE, str);
	}

	/**
	 * 手机 正则表达式为 ^(13[0-9]|15[0|3|6-9]|18[8|9])\\d{8}$
	 * 
	 * @param str 要验证的字符串
	 * @return true false
	 */
	public static boolean isMobile(String str) {
		return is(C.R.MOBILE, str);
	}

	/**
	 * 邮政编码 正则表达式为 ^[a-zA-Z0-9 ]{3,6}$
	 * 
	 * @param str 要验证的字符串
	 * @return true false
	 */
	public static boolean isPost(String str) {
		return is(C.R.POST, str);
	}

	/**
	 * IP 正则表达式为 ^\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}$
	 * 
	 * @param str 要验证的字符串
	 * @return true false
	 */
	public static boolean isIp(String str) {
		return is(C.R.IP, str);
	}

	/**
	 * 只有中文 正则表达式为 ^([\u4e00-\u9fa5]*)$
	 * 
	 * @param str 要验证的字符串
	 * @return true false
	 */
	public static boolean isChinese(String str) {
		return is(C.R.CHINESE, str);
	}

	/**
	 * URL 正则表达式为 ^[a-zA-z]+://(\\w+(-\\w+)*)(\\.(\\w+(-\\w+)*))*(\\?\\S*)?$
	 * 
	 * @param str 要验证的字符串
	 * @return true false
	 */
	public static boolean isURL(String str) {
		return is(C.R.URL, str);
	}

	/**
	 * 全由数字组成 正则表达式为 ^\\d*$
	 * 
	 * @param str 要验证的字符串
	 * @return true false
	 */
	public static boolean isDigit(String str) {
		return is(C.R.DIGIT, str);
	}

	/**
	 * 正整数 正则表达式为 ^[0-9]*[1-9][0-9]*$
	 * 
	 * @param str 要验证的字符串
	 * @return true false
	 */
	public static boolean isNumber(String str) {
		return is(C.R.NUMBER, str);
	}

	/**
	 * 身份证 正则表达式为 ^[\\d]{15}|[\\d]{17}[\\dxX]{1}$
	 * 
	 * @param str 要验证的字符串
	 * @return true false
	 */
	public static boolean isIdentityCardNum(String str) {
		return is(C.R.IDENTITYCARDNUM, str);
	}

	/**
	 * 字母和数值 正则表达式为 ^[a-zA-Z0-9]$
	 * 
	 * @param str 要验证的字符串
	 * @return true false
	 */
	public static boolean isChar(String str) {
		return is(C.R.CHARS, str);
	}

	/**
	 * 字母 数值 汉字 空格 正则表达式为 ^([a-zA-Z0-9\u4e00-\u9fa5]|[_]|[ ]|[-]){1,100}$
	 * 
	 * @param str 要验证的字符串
	 * @return true false
	 */
	public static boolean isCharNumber(String str) {
		return is(C.R.CHARNUMBER, str);
	}

	/**
	 * 校验字符串
	 * 
	 * @param regex 正则表达式
	 * @param str   要验证的字符串 校验的字符串
	 * @return true false
	 */
	public static boolean is(String regex, String str) {
		return U.E.isEmpty(regex) || U.E.isEmpty(str) ? false : Pattern.compile(regex).matcher(str).matches();
	}

	/**
	 * 校验字符串
	 * 
	 * @param regexs 正则表达式
	 * @param str    要验证的字符串 校验的字符串
	 * @return true false
	 */
	public static boolean is(String[] regexs, String str) {
		// 如果为空返回false
		if (U.E.isEmpty(regexs) || U.E.isEmpty(str))
			return false;
		else
			// 循环判断正则 只要有一个符合就返回true
			for (String regex : regexs)
				if (is(regex, str))
					return true;
		return false;
	}
}
