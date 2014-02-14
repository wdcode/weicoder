package com.weicoder.common.constants;

/**
 * 保存正则表达式常量类
 * @author WD
 * @since JDK7
 * @version 1.0 2009-09-15
 */
public final class RegexConstants {
	/**
	 * 全由数字组成
	 */
	public final static String	DIGIT				= "^\\d+$";
	/**
	 * 4-18个以字母开头、可带数字、"_"的字串
	 */
	public final static String	USERNAME			= "^[a-zA-Z]{1}[\\w]{3,17}$";
	/**
	 * 普通密码 6-20个字母、数字、下划线
	 */
	public final static String	PWD					= "^(\\w){6,20}$";
	/**
	 * 普通电话、传真号码：可以“+”开头，除数字外，可含有“-”
	 */
	public final static String	PHONE				= "^[+]{0,1}(\\d){1,3}[   ]?([-]?((\\d)|[   ]){1,12})+$";
	/**
	 * 手机号码 13x 15x 188 189 开头
	 */
	public final static String	MOBILE				= "^(13[0-9]|15[0|3|6-9]|18[8|9])\\d{8}$";
	/**
	 * 邮政编码
	 */
	public final static String	POST				= "^[a-zA-Z0-9   ]{3,6}$";
	/**
	 * IP
	 */
	public final static String	IP					= "^\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}$";
	/**
	 * 正整数
	 */
	public final static String	NUMBER				= "^[0-9]*[1-9][0-9]*$";
	/**
	 * Email
	 */
	public final static String	EMAIL				= "^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)+$";
	/**
	 * URL
	 */
	public final static String	URL					= "^[a-zA-z]+://(\\w+(-\\w+)*)(\\.(\\w+(-\\w+)*))*(\\?\\S*)?$";
	/**
	 * 汉字
	 */
	public final static String	CHINESE				= "^[\\u4e00-\\u9fa5]*$";
	/**
	 * 字母和数字
	 */
	public final static String	CHARS				= "^[a-zA-Z0-9]$";
	/**
	 * 字母 数值 汉字 空格
	 */
	public final static String	CHARNUMBER			= "^([a-zA-Z0-9\u4e00-\u9fa5]|[_]|[ ]|[-]){1,100}$";
	/**
	 * 中文和英文
	 */
	public final static String	REALNAME			= "^[a-zA-Z\u4e00-\u9fa5 .]+$";
	/**
	 * 密码 字母数字特殊符号
	 */
	public final static String	PASSWORD			= "^[a-zA-Z0-9.!#$%&^*-_=+~?><]+$";
	/**
	 * 联通、电信号段
	 */
	public final static String	CUQMOBILE			= "^(13[0-3]|15[36]|189)(\\d){8}$";
	/**
	 * 电信号段
	 */
	public final static String	TELEMOBILE			= "^(133)(\\d){8}$";
	/**
	 * 字母
	 */
	public final static String	LETTERS				= "^[a-zA-Z]+$";
	/**
	 * 身份证
	 */
	public final static String	IDENTITYCARDNUM		= "^[\\d]{15}|[\\d]{17}[\\dxX]{1}$";

	/**
	 * Date 如20040606
	 */
	public final static String	DATE_YYYYMMDD		= "^(\\d{4})(0[1-9]|1[0-2])(0[1-9]|1[0-9]|2[0-9]|3[0-1])$";
	/**
	 * Date 如20040606
	 */
	public final static String	DATE_YYYYMM			= "^(\\d{4})(0[1-9]|1[0-2])$";
	/**
	 * Date 如2004-06-06
	 */
	public final static String	DATE_YYYY_MM_DD		= "^(\\d{4})\\-(0[1-9]|1[0-2])\\-(0[1-9]|1[0-9]|2[0-9]|3[0-1])$";
	/**
	 * DateTime 如2004-11-12 12:10:16
	 */
	public final static String	DATE_Y_M_D_H_M_S	= "^(\\d{4})\\-(0[1-9]|1[0-2])\\-(0[1-9]|1[0-9]|2[0-9]|3[0-1]) (0[0-9]|1[0-9]|2[0-4]):([0-5][0-9]):([0-5][0-9])$";
	/**
	 * DateTime 如2004-11-12 12:10
	 */
	public final static String	DATE_Y_M_D_H_M		= "^(\\d{4})\\-(0[1-9]|1[0-2])\\-(0[1-9]|1[0-9]|2[0-9]|3[0-1]) (0[0-9]|1[0-9]|2[0-4]):([0-5][0-9])$";
	/**
	 * Date 如20040606
	 */
	public final static String	DATE_YMD_H_M_S		= "^(\\d{4})(0[1-9]|1[0-2])(0[1-9]|1[0-9]|2[0-9]|3[0-1]) (0[0-9]|1[0-9]|2[0-4]):([0-5][0-9]):([0-5][0-9])$";
	/**
	 * Date 如12:10:16
	 */
	public final static String	DATE_HH_MM_SS		= "^(0[0-9]|1[0-9]|2[0-4]):([0-5][0-9]):([0-5][0-9])$";

	/**
	 * 私有构造禁止外部实例化
	 */
	private RegexConstants() {}
}
