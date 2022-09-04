package com.weicoder.common.statics;

import java.security.SecureRandom;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import com.weicoder.common.lang.W;

/**
 * 随机数使用类
 * 
 * @author wdcode
 *
 */
public class Randoms {
	// 随机数对象
	private final static Random	RANDOM	= new Random();
	// 安全随机对象
	private final static Random	SECURE	= new SecureRandom();

	/**
	 * 返回当前线程的ThreadLocalRandom
	 * 
	 * @return ThreadLocalRandom 独立线程的随机数
	 */
	public static Random current() {
		return ThreadLocalRandom.current();
	}

	/**
	 * 声明个新的Random
	 * 
	 * @return Random
	 */
	public static Random random() {
		return RANDOM;
	}

	/**
	 * 声明个新的SecureRandom
	 * 
	 * @return SecureRandom
	 */
	public static Random secure() {
		return SECURE;
	}

	/**
	 * 随机获得一个字节数组 确保输入的m和n在byte之内 不做额外验证
	 * 
	 * @param len 生成长度
	 * @param m   开始数
	 * @param n   结束数
	 * @return 字节数组
	 */
	public static byte[] nextBytes(int len, int m, int n) {
		return nextBytes(random(), len, m, n);
	}

	/**
	 * 随机获得一个字节数组 确保输入的m和n在byte之内 不做额外验证
	 * 
	 * @param r   随机数生成类
	 * @param len 生成长度
	 * @param m   开始数
	 * @param n   结束数
	 * @return 字节数组
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

//	/**
//	 * 为大数据优化的方法
//	 * 
//	 * @param len 生成长度
//	 * @param s   开始数
//	 * @param e   结束数
//	 * @param n   生成列表长度
//	 * @return 字节数组
//	 */
//	public static List<byte[]> bytes(int len, int s, int e, int n) {
//		return bytes(random(), len, s, e, n);
//	}
//
//	/**
//	 * 为大数据优化的方法
//	 * 
//	 * @param r   随机数生成类
//	 * @param len 生成长度
//	 * @param s   开始数
//	 * @param e   结束数
//	 * @param n   生成列表长度
//	 * @return 字节数组
//	 */
//	public static List<byte[]> bytes(Random r, int len, int s, int e, int n) {
//		// 声明返回列表
//		List<byte[]> list = W.L.newList(n);
//		// 验证不可重复数列表
////		Set<Byte> set = W.S.newSet(len);
//		// 循环获得随机数组
//		for (int i = 0; i < n; i++)
//			list.add(bytes(r, len, s, e));
////		list.add(bytes(r, len, s, e,set));
//		// 返回列表
//		return list;
//	}

//	/**
//	 * 为大数据优化的方法
//	 * 
//	 * @param r   随机数生成类
//	 * @param len 生成长度
//	 * @param m   开始数
//	 * @param n   结束数
//	 * @return 字节数组
//	 */
//	public static byte[] bytes(Random r, int len, int s, int e) {
//		// 声明保存结果
//		byte[] b = new byte[len];
//		// 循环次数
//		for (int i = 0; i < len; i++) {
//			// 随机获得int强转成byte
//			byte t = (byte) r.nextInt(s, e);
//			// 是否可重复
//			boolean is = true;
//			for (int j = 0; j < i; j++)
//				if (b[j] == t) {
//					i--;
//					is = false;
//					break;
//				}
//			if (is)
//				b[i] = t;
//
////			if (set.contains(t))
////				i--;
////			else
////				set.add(b[i] = t);
//		}
//		// 每次清除列表
////		set.clear();
//		// 返回保存结果
//		return b;
//	}

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
//		return m > n ? 0 : nextInt(n - m) + m;
		return random().nextInt(m, n);
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
}