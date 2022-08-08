package com.weicoder.common.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.W;

/**
 * 各种数学相关操作类
 * 
 * @author WD
 */
public class MathUtil {
	// 取舍方式
	private final static RoundingMode ROUND = RoundingMode.HALF_UP;
//	// 随机数对象 线程安全ThreadLocalRandom
//	private final static Random			RANDOM	= new Random();

//	/**
//	 * 返回当前线程的ThreadLocalRandom
//	 * 
//	 * @return Random
//	 */
//	public static ThreadLocalRandom current() {
//		return ThreadLocalRandom.current();
//	}

	/**
	 * 返回当前线程的ThreadLocalRandom
	 * 
	 * @return ThreadLocalRandom 独立线程的随机数
	 */
	public static ThreadLocalRandom random() {
		return ThreadLocalRandom.current();
	}

	public static byte[] nextBytes(int len, int m, int n) {
		return nextBytes(random(), len, m, n);
	}

	/**
	 * 获得
	 * 
	 * @param r
	 * @param len
	 * @param m
	 * @param n
	 * @return
	 */
	public static byte[] nextBytes(Random r, int len, int m, int n) {
		return nextBytes(r, len, m, n, false);
	}

	/**
	 * 随机获得一个字节数组 确保输入的m和n在byte之内 不做额外验证
	 * 
	 * @param r   随机数生成类
	 * @param len 生成长度
	 * @param m   开始数
	 * @param n   结束数
	 * @param is  是否可重复 true 可重复 false 不可重复
	 * @return 字节数组
	 */
	public static byte[] nextBytes(Random r, int len, int s, int e, boolean is) {
		// 声明保存结果
		byte[] b = new byte[len];
		// 声明个set以便排除重复数据
		Set<Byte> set = W.S.newSet(len);
		// 循环次数
		for (int i = 0; i < len; i++) {
			// 随机获得int强转成byte
			byte t = (byte) r.nextInt(s, e);
			// 是否可重复
			if (!is && set.contains(t))
				i--;
			else
				set.add(b[i] = t);
		}
		// 每次清除列表
		set.clear();
		// 返回保存结果
		return b;
	}

	/**
	 * 随机获得一个字节数组列表 验证s与e在Byte.min~Byte.max之间
	 * 
	 * @param r   随机数生成类
	 * @param len 生成长度
	 * @param s   开始数
	 * @param e   结束数
	 * @param n   生成列表长度
	 * @param is  是否可重复 true 可重复 false 不可重复
	 * @return 字节数组
	 */
	public static List<byte[]> nextBytes(Random r, int len, int s, int e, int n, boolean is) {
		// 验证开始结束值
		if (s < Byte.MIN_VALUE)
			s = Byte.MIN_VALUE;
		if (e > Byte.MAX_VALUE)
			e = Byte.MAX_VALUE + 1;
		// 声明返回列表
		List<byte[]> list = W.L.newList(n);
		// 循环获得随机数组
		for (int i = 0; i < n; i++)
			list.add(nextBytes(r, len, s, e, is));
		// 返回列表
		return list;
	}

	/**
	 * 为大数据优化的方法
	 * 
	 * @param r   随机数生成类
	 * @param len 生成长度
	 * @param s   开始数
	 * @param e   结束数
	 * @param n   生成列表长度
	 * @return 字节数组
	 */
	public static List<byte[]> bytes(Random r, int len, int s, int e, int n) {
		// 声明返回列表
		List<byte[]> list = W.L.newList(n);
		// 验证不可重复数列表
//		Set<Byte> set = W.S.newSet(len);
		// 循环获得随机数组
		for (int i = 0; i < n; i++)
			list.add(bytes(r, len, s, e));
//		list.add(bytes(r, len, s, e,set));
		// 返回列表
		return list;
	}

	/**
	 * 为大数据优化的方法
	 * 
	 * @param r   随机数生成类
	 * @param len 生成长度
	 * @param m   开始数
	 * @param n   结束数
	 * @return 字节数组
	 */
	public static byte[] bytes(Random r, int len, int s, int e) {
		// 声明保存结果
		byte[] b = new byte[len];
		// 循环次数
		for (int i = 0; i < len; i++) {
			// 随机获得int强转成byte
			byte t = (byte) r.nextInt(s, e);
			// 是否可重复
			boolean is = true;
			for (int j = 0; j < i; j++)
				if (b[j] == t) {
					i--;
					is = false;
					break;
				}
			if (is)
				b[i] = t;

//			if (set.contains(t))
//				i--;
//			else
//				set.add(b[i] = t);
		}
		// 每次清除列表
//		set.clear();
		// 返回保存结果
		return b;
	}

	/**
	 * 同方法Random.nextInt()
	 * 
	 * @return int 返回的随机数
	 */
	public static int nextInt() {
		return random().nextInt();
	}

	/**
	 * 同方法Random.nextInt(n)
	 * 
	 * @param n 在0-n的范围中
	 * @return int 返回的随机数
	 */
	public static int nextInt(int n) {
		return random().nextInt(n);
	}

	/**
	 * 在m-n的范围中随机获得
	 * 
	 * @param m 起始数
	 * @param n 结束数
	 * @return int 返回的随机数
	 */
	public static int nextInt(int m, int n) {
		return m > n ? 0 : nextInt(n - m) + m;
	}

	/**
	 * 同方法Random.nextDouble()
	 * 
	 * @return double 返回的随机数
	 */
	public static double nextDouble() {
		return random().nextDouble();
	}

	/**
	 * 同方法Random.nextFloat()
	 * 
	 * @return float 返回的随机数
	 */
	public static float nextFloat() {
		return random().nextFloat();
	}

	/**
	 * 同方法Random.nextLong()
	 * 
	 * @return long 返回的随机数
	 */
	public static long nextLong() {
		return random().nextLong();
	}

	/**
	 * 获取指定位数的随机数
	 * 
	 * @param len 随机长度
	 * @return 字符串格式的随机数
	 */
	public static String random(int len) {
		// 声明字符缓存
		StringBuilder veryfy = new StringBuilder();
		// 循环位数
		for (int i = 0; i < len; i++) {
			// 随机获得整数
			int n = nextInt(48, 123);
			// 判断不在a-z A-Z中
			if ((n > 91 && n < 97) || (n > 57 && n < 65))
				n += nextInt(7, 16);
			// 添加到字符缓存中
			veryfy.append((char) n);
		}
		// 返回
		return veryfy.toString();
	}

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
		return v.indexOf(StringConstants.COMMA) == -1 ? v : v.replaceAll(StringConstants.COMMA, StringConstants.EMPTY);
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
