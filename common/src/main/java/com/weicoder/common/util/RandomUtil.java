package com.weicoder.common.util;

import java.util.Random;

/**
 * 随机数工具类
 * @author WD
 * @since JDK7
 * @version 1.0 2010-07-11
 */
public final class RandomUtil {
	// 声明随机数对象
	private final static Random	RANDOM;

	/**
	 * 静态实例化
	 */
	static {
		RANDOM = new Random();
	}

	/**
	 * 返回Random
	 * @return Random
	 */
	public static Random getRandom() {
		return RANDOM;
	}

	/**
	 * 同方法Random.nextInt()
	 * @return int 返回的随机数
	 */
	public static int nextInt() {
		return RANDOM.nextInt();
	}

	/**
	 * 同方法Random.nextInt(n)
	 * @param n 在0-n的范围中
	 * @return int 返回的随机数
	 */
	public static int nextInt(int n) {
		return RANDOM.nextInt(n);
	}

	/**
	 * 在m-n的范围中随机获得
	 * @param m 起始数
	 * @param n 结束数
	 * @return int 返回的随机数
	 */
	public static int nextInt(int m, int n) {
		return m > n ? 0 : nextInt(n - m) + m;
	}

	/**
	 * 同方法Random.nextDouble()
	 * @return double 返回的随机数
	 */
	public static double nextDouble() {
		return RANDOM.nextDouble();
	}

	/**
	 * 同方法Random.nextFloat()
	 * @return float 返回的随机数
	 */
	public static float nextFloat() {
		return RANDOM.nextFloat();
	}

	/**
	 * 同方法Random.nextLong()
	 * @return long 返回的随机数
	 */
	public static long nextLong() {
		return RANDOM.nextLong();
	}

	/**
	 * 获取指定位数的随机数
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
			if ((n > 91 && n < 97) || (n > 57 && n < 65)) {
				n += nextInt(7, 16);
			}
			// 添加到字符缓存中
			veryfy.append((char) n);
		}
		// 返回
		return veryfy.toString();
	}

	/**
	 * 私有构造
	 */
	private RandomUtil() {}
}
