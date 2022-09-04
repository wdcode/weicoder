package com.weicoder.common.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.weicoder.common.constants.C;
import com.weicoder.common.lang.W;

/**
 * 各种数学相关操作类
 * 
 * @author WD
 */
public class MathUtil {
	// 取舍方式
	private final static RoundingMode ROUND = RoundingMode.HALF_UP;

	/**
	 * 提供精确的加法运算。
	 * 
	 * @param values 相加的数字
	 * @return 相加结果
	 */
	public static BigDecimal add(Object... values) {
		// 声明结果
		BigDecimal result = BigDecimal.ZERO;
		// 循环相加
		for (int i = 0; i < values.length; i++)
			result = result.add(W.C.toBigDecimal(values[i]));
		// 返回结果
		return result;

	}

	/**
	 * 提供精确的减法运算。
	 * 
	 * @param v1 被减数
	 * @param v2 减数
	 * @return 结果
	 */
	public static BigDecimal subtract(Object v1, Object v2) {
		return scale(v1).subtract(W.C.toBigDecimal(v2));

	}

	/**
	 * 提供精确的减法运算
	 * 
	 * @param v1    被减数
	 * @param v2    减数
	 * @param scale 保留精度
	 * @return 结果
	 */
	public static BigDecimal subtract(Object v1, Object v2, int scale) {
		return subtract(v1, v2).setScale(scale, ROUND);

	}

	/**
	 * 提供精确的乘法运算。
	 * 
	 * @param values 相乘的数字
	 * @return 相乘结果
	 */
	public static BigDecimal multiply(Object... values) {
		// 声明结果
		BigDecimal result = BigDecimal.ONE;
		// 循环相乘
		for (int i = 0; i < values.length; i++)
			result = result.multiply(W.C.toBigDecimal(values[i]));
		// 返回结果
		return result;
	}

	/**
	 * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到 小数点以后2位，以后的数字四舍五入。
	 * 
	 * @param v1 被除数
	 * @param v2 除数
	 * @return 两个参数的商
	 */
	public static BigDecimal divide(Object v1, Object v2) {
		return divide(v1, v2, 2);
	}

	/**
	 * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到 小数点以后2位，以后的数字四舍五入。
	 * 
	 * @param v1    被除数
	 * @param v2    除数
	 * @param scale 小数点后保留几位
	 * @return 两个参数的商
	 */
	public static BigDecimal divide(Object v1, Object v2, int scale) {
		return scale(v1, scale).divide(scale(v2, scale), scale, ROUND);
	}

	/**
	 * 提供（相对）精确的取模运算，当发生除不尽的情况时，精确到 小数点以后2位，以后的数字四舍五入。
	 * 
	 * @param v1 被除数
	 * @param v2 除数
	 * @return 两个参数的商
	 */
	public static BigDecimal remainder(Object v1, Object v2) {
		return remainder(v1, v2, 2);
	}

	/**
	 * 提供（相对）精确的取模运算，当发生除不尽的情况时，精确到 小数点以后2位，以后的数字四舍五入。
	 * 
	 * @param v1    被除数
	 * @param v2    除数
	 * @param scale 小数点后保留几位
	 * @return 两个参数的商
	 */
	public static BigDecimal remainder(Object v1, Object v2, int scale) {
		return scale(v1, scale).remainder(scale(v2, scale));
	}

	/**
	 * 提供精确的小数位四舍五入处理。
	 * 
	 * @param v 需要四舍五入的数字
	 * @return 四舍五入后的结果
	 */
	public static BigDecimal scale(Object v) {
		return scale(v, 2);
	}

	/**
	 * 提供精确的小数位四舍五入处理。
	 * 
	 * @param v     需要四舍五入的数字
	 * @param scale 小数点后保留几位
	 * @return 四舍五入后的结果
	 */
	public static BigDecimal scale(Object v, int scale) {
		return W.C.toBigDecimal(v).setScale(scale, ROUND);
	}

	/**
	 * 格式化数字 把数字里的,去掉
	 * 
	 * @param v 数字
	 * @return 去掉,后的数字
	 */
	public static String take(String v) {
		return v.indexOf(C.S.COMMA) == -1 ? v : v.replaceAll(C.S.COMMA, C.S.EMPTY);
	}

	/**
	 * 根据传进来的值返回长数字字符串 去处科学计数法用
	 * 
	 * @param val 要获得的值
	 * @return 字符串
	 */
	public static String toString(Object val) {
		return W.C.toBigDecimal(val).toPlainString();
	}
}
