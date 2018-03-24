package com.weicoder.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.weicoder.common.constants.ArrayConstants;
import com.weicoder.common.constants.StringConstants;

import com.weicoder.common.lang.Conversion;
import com.weicoder.common.lang.Validate;
import com.weicoder.common.log.Logs;
import com.weicoder.common.params.CommonParams;

/**
 * 对字符串进行一些处理。
 * @author WD
 */
public final class StringUtil {
	/**
	 * 字符串累加
	 * @param os 累加字符串
	 * @return 返回累加后字符串
	 */
	public static String add(Object... os) {
		StringBuilder sb = new StringBuilder();
		for (Object o : os) {
			sb.append(Conversion.toString(o));
		}
		return sb.toString();
	}

	/**
	 * 判断两个字符串是否相等 两个字符串都要不为空情况下才判断 都为空也为false
	 * @param str1 第一个字符串
	 * @param str2 第二个字符串
	 * @return true false
	 */
	public static boolean equals(String str1, String str2) {
		return trim(str1).equals(trim(str2));
	}

	/**
	 * 返回字符串长度，汉字占两字节 主要是用于计算有汉字时的长度 一般情况下不使用,如果str为空返回0
	 * @param str 要校验长度的字符串
	 * @return 字符串长度
	 */
	public static int getLength(String str) {
		// 如果为空返回0
		if (EmptyUtil.isEmpty(str)) {
			return 0;
		}
		// 初始化长度
		int length = 0;
		// 获得字符串的字符数组
		char[] temp = str.toCharArray();
		// 循环字符数组
		for (int i = 0; i < temp.length; i++) {
			// 判断是否中文
			if (Validate.isChinese(String.valueOf(temp[i]))) {
				// 中文长度加2
				length += 2;
			} else {
				// 不是中文长度加1
				length++;
			}
		}
		// 返回长度
		return length;
	}

	/**
	 * 使用正则表达式截取字符 截取第一个 如果没找到返回 ""
	 * @param str 要截取的字符串
	 * @param regex 正则表达式
	 * @return 截取后的字符串
	 */
	public static String sub(String str, String regex) {
		return sub(str, regex, 1);
	}

	/**
	 * 使用正则表达式截取字符 如果没找到返回 ""
	 * @param str 要截取的字符串
	 * @param regex 正则表达式
	 * @param index 截取组位置 重1开始
	 * @return 截取后的字符串
	 */
	public static String sub(String str, String regex, int index) {
		// 获得Matcher
		Matcher matcher = Pattern.compile(regex).matcher(str);
		// 返回字符串
		return matcher.find(index - 1) ? matcher.group(index) : StringConstants.EMPTY;
	}

	/**
	 * 使用正则表达式截取字符 获得全部匹配的
	 * @param str 要截取的字符串
	 * @param regex 正则表达式
	 * @return 字符串数组
	 */
	public static String[] subAll(String str, String regex) {
		// 获得Matcher
		Matcher matcher = Pattern.compile(regex).matcher(str);
		// 声明字符串
		String[] sub = new String[matcher.end()];
		// 循环获得字符串
		for (int i = 0; i < sub.length; i++) {
			// 添加匹配的字符串
			sub[i] = matcher.group(i + 1);
		}
		// 返回字符串数组
		return sub;
	}

	/**
	 * 从前截取字符串,如果str为空返回str<br>
	 * <h2>注: 如果没有开始字符串 开始长度为0 如果没有结束字符串 结束长度为str长度</h2>
	 * <h2>注: 不包含开始与结束字符串</h2>
	 * @param str 要截取的字符串
	 * @param start 开始截取的字符串
	 * @param end 结束字符串
	 * @return 返回截取后的字符串
	 */
	public static String subString(String str, String start, String end) {
		// 字符串为空返回原串
		if (EmptyUtil.isEmpty(str)) {
			return str;
		}
		// 开始位置
		int i = str.indexOf(start) == -1 ? 0 : str.indexOf(start) + start.length();
		// 结束位置
		int j = str.indexOf(end) == -1 ? str.length() : str.indexOf(end);
		// 如果结束位置小于开始位置
		if (j < i) {
			j = str.substring(i).indexOf(end);
			j = j == -1 ? str.length() : j + i;
		}
		// 返回截取的字符串
		return str.substring(i, j);
	}

	/**
	 * 截取字符串
	 * @param str 字符串
	 * @param start 开始字符
	 * @return 截取后的字符串
	 */
	public static String subString(String str, int start) {
		return EmptyUtil.isEmpty(str) ? StringConstants.EMPTY : subString(str, start, str.length());
	}

	/**
	 * 截取字符串
	 * @param str 字符串
	 * @param start 开始字符
	 * @param end 结束字符
	 * @return 截取后的字符串
	 */
	public static String subString(String str, int start, int end) {
		// 字符串为空返回原串
		if (EmptyUtil.isEmpty(str)) {
			return str;
		}
		// 字符串长度
		int len = str.length();
		// 开始位置
		start = start > len ? 0 : start;
		// 结束位置
		end = end > len ? len : end;
		// 如果结束位置小于开始位置
		if (end < start && end < len) {
			str = str.substring(start);
			end = end == -1 ? len : end + start;
		}
		// 返回截取的字符串
		return str.substring(start, end);
	}

	/**
	 * 从后截取字符串,如果str为空返回str<br>
	 * <h2>注: 如果没有开始字符串 开始长度为0 如果没有结束字符串 结束长度为str长度</h2>
	 * @param str 要截取的字符串
	 * @param start 开始截取的字符串
	 * @param end 结束字符串
	 * @return 返回截取后的字符串
	 */
	public static String subStringLast(String str, String start, String end) {
		// 字符串为空返回""
		if (EmptyUtil.isEmpty(str)) {
			return str;
		}
		// 开始位置
		int i = str.lastIndexOf(start) == -1 ? 0 : str.lastIndexOf(start) + start.length();
		// 结束位置
		int j = str.lastIndexOf(end) == -1 ? 0 : str.lastIndexOf(end);
		// 返回截取的字符串
		return str.substring(i, j);
	}

	/**
	 * 从前截取字符串,如果str为空返回str<br>
	 * <h2>注: 如果没有开始字符串 开始长度为0</h2>
	 * @param str 要截取的字符串
	 * @param start 开始截取的字符串
	 * @return 返回截取后的字符串
	 */
	public static String subString(String str, String start) {
		// 字符串为空返回""
		if (EmptyUtil.isEmpty(str)) {
			return str;
		}
		// 开始位置
		int i = str.indexOf(start) == -1 ? 0 : str.indexOf(start) + start.length();
		// 如果开始长度为0 返回原串
		if (i == 0) {
			return str;
		}
		// 返回截取的字符串
		return str.substring(i, str.length());
	}

	/**
	 * 从后截取字符串,如果str为空返回str<br>
	 * <h2>注: 如果没有开始字符串 开始长度为0</h2>
	 * @param str 要截取的字符串
	 * @param start 开始截取的字符串
	 * @return 返回截取后的字符串
	 */
	public static String subStringLast(String str, String start) {
		// 字符串为空返回原串
		if (EmptyUtil.isEmpty(str)) {
			return str;
		}
		// 开始位置
		int i = str.lastIndexOf(start) == -1 ? 0 : str.lastIndexOf(start) + start.length();
		// 如果开始长度为0 返回原串
		if (i == 0) {
			return str;
		}
		// 返回截取的字符串
		return str.substring(i, str.length());
	}

	/**
	 * 从前截取字符串,如果str为空返回str<br>
	 * <h2>注: 如果没有开始字符串 开始长度为0</h2>
	 * @param str 要截取的字符串
	 * @param end 截取到的字符串
	 * @return 返回截取后的字符串
	 */
	public static String subStringEnd(String str, String end) {
		// 字符串为空返回""
		if (EmptyUtil.isEmpty(str)) {
			return str;
		}
		// 开始位置
		int i = str.indexOf(end) == -1 ? 0 : str.indexOf(end);
		// 如果开始长度为0 返回原串
		if (i == 0) {
			return str;
		}
		// 返回截取的字符串
		return str.substring(0, i);
	}

	/**
	 * 从后截取字符串,如果str为空返回str<br>
	 * <h2>注: 如果没有开始字符串 开始长度为0</h2>
	 * @param str 要截取的字符串
	 * @param end 截取到的字符串
	 * @return 返回截取后的字符串
	 */
	public static String subStringLastEnd(String str, String end) {
		// 字符串为空返回""
		if (EmptyUtil.isEmpty(str)) {
			return str;
		}
		// 开始位置
		int i = str.lastIndexOf(end) == -1 ? 0 : str.lastIndexOf(end);
		// 如果开始长度为0 返回原串
		if (i == 0) {
			return str;
		}
		// 返回截取的字符串
		return str.substring(0, i);
	}

	/**
	 * 转换字符串的编码格式 如果source为空 返回原串 如果转换异常返回原串
	 * @param source 要转换的字符串
	 * @param tChar 转换编码
	 * @return 转换后的字符串
	 */
	public static String toCharset(String source, String tChar) {
		try {
			return EmptyUtil.isEmpty(source) ? source : new String(source.getBytes(), tChar);
		} catch (Exception e) {
			return StringConstants.EMPTY;
		}
	}

	/**
	 * 转换字符串的编码格式 如果source为空 返回原串 如果转换异常返回原串
	 * @param source 要转换的字符串
	 * @param sChar 原编码
	 * @param tChar 转换编码
	 * @return 转换后的字符串
	 */
	public static String toCharset(String source, String sChar, String tChar) {
		try {
			return EmptyUtil.isEmpty(source) ? source : new String(source.getBytes(sChar), tChar);
		} catch (Exception e) {
			Logs.warn(e);
			return StringConstants.EMPTY;
		}
	}

	/**
	 * 把输入的其它命名法变成驼峰命名法,如 User_Id = userId User = user
	 * @param name 属性名
	 * @return 转换后的字符串
	 */
	public static String convert(String name) {
		// 如果为空返回原串
		if (EmptyUtil.isEmpty(name)) {
			return name;
		}
		// 分解_个字段
		String[] strs = name.split(StringConstants.UNDERLINE);
		// 实例一个字符串缓存
		StringBuilder buf = new StringBuilder();
		// 循环数组
		for (int i = 0; i < strs.length; i++) {
			// 获得新字符串
			String s = strs[i];
			// 添加字符串
			if (!EmptyUtil.isEmpty(s)) {
				if (i == 0) {
					buf.append(s.substring(0, 1).toLowerCase());
				} else {
					buf.append(s.substring(0, 1).toUpperCase());
				}
				buf.append(s.substring(1, s.length()));
			}
		}
		// 返回新的字符串
		return buf.toString();
	}

	/**
	 * 把驼峰命名转换为数据库名,如 userInfo = user_info
	 * @param name 属性名
	 * @return 转换后的字符串
	 */
	public static String toDbName(String name) {
		// 如果为空返回原串
		if (EmptyUtil.isEmpty(name)) {
			return name;
		}
		// 分解成字符
		char[] cs = name.toCharArray();
		// 实例一个字符串缓存
		StringBuilder buf = new StringBuilder();
		// 计算拼凑
		for (int i = 0; i < cs.length; i++) {
			// 获取字符
			char c = cs[i];
			// 第一个字符
			if (i == 0) {
				// 转换成小写
				buf.append(Character.toLowerCase(c));
			} else {
				// 判断是否大写
				if (Character.isUpperCase(c)) {
					// 是大写加 _ 然后变成小写
					buf.append(StringConstants.UNDERLINE).append(Character.toLowerCase(c));
				} else {
					buf.append(c);
				}
			}
		}
		// 返回新的字符串
		return buf.toString();
	}

	/**
	 * 转换字节数组为字符串
	 * @param b 字节数组
	 * @return 字符串
	 */
	public static String toString(byte[] b) {
		return toString(b, CommonParams.ENCODING);
	}

	/**
	 * 转换字节数组为字符串
	 * @param b 字节数组
	 * @param charsetName 编码格式
	 * @return 字符串
	 */
	public static String toString(byte[] b, String charsetName) {
		try {
			return EmptyUtil.isEmpty(b) ? StringConstants.EMPTY : new String(b, charsetName);
		} catch (Exception e) {
			Logs.warn(e);
			return StringConstants.EMPTY;
		}
	}

	/**
	 * 转换字符串为字节数组
	 * @param s 字符串
	 * @return 字节数组
	 */
	public static byte[] toBytes(String s) {
		return toBytes(s, CommonParams.ENCODING);
	}

	/**
	 * 转换字符串为字节数组
	 * @param s 字符串
	 * @param charsetName 编码格式
	 * @return 字节数组
	 */
	public static byte[] toBytes(String s, String charsetName) {
		try {
			return EmptyUtil.isEmpty(s) ? ArrayConstants.BYTES_EMPTY : s.getBytes(charsetName);
		} catch (Exception e) {
			Logs.warn(e);
			return ArrayConstants.BYTES_EMPTY;
		}
	}

	/**
	 * 判断str是否包含searchStr
	 * @param str 字符串
	 * @param searchStr 被包含串
	 * @return true 包含 false 不包含
	 */
	public static boolean contains(String str, String searchStr) {
		// 如果一个串为空 返回false
		if (EmptyUtil.isEmpty(str) || EmptyUtil.isEmpty(searchStr)) {
			return false;
		}
		// 判断是否包含
		return str.indexOf(searchStr) >= 0;
	}

	/**
	 * 去除两边空格
	 * @param s 要处理的字符串
	 * @return 处理后的字符串
	 */
	public static String trim(String s) {
		return Conversion.toString(s).trim();
	}

	/**
	 * 替换字符串
	 * @param s 要替换的字符串
	 * @param regex 正则表达式
	 * @param replacement 要替换掉的字符
	 * @return 替换后的字符
	 */
	public static String replace(String s, String regex, String replacement) {
		return EmptyUtil.isEmpty(s) ? StringConstants.EMPTY : s.replace(regex, replacement);
	}

	/**
	 * 替换字符串
	 * @param s 要替换的字符串
	 * @param regex 正则表达式
	 * @param replacement 要替换掉的字符
	 * @return 替换后的字符
	 */
	public static String replaceAll(String s, String regex, String replacement) {
		return EmptyUtil.isEmpty(s) ? StringConstants.EMPTY : s.replaceAll(regex, replacement);
	}

	/**
	 * 拆分字符串
	 * @param s 要拆分的字符串
	 * @param regex 正则表达式
	 * @return 替换后的字符
	 */
	public static String[] split(String s, String regex) {
		return EmptyUtil.isEmpty(s) ? ArrayConstants.STRING_EMPTY : s.split(regex);
	}

	/**
	 * 获得方法名
	 * @param prefix 方法前缀 比如set get
	 * @param name 方法的后缀名 比如字段名
	 * @return 方法名
	 */
	public static String getMethodName(String prefix, String name) {
		return convert(prefix + StringConstants.UNDERLINE + name);
	}

	/**
	 * 分解字符串
	 * @param text 整串
	 * @param len 新串的长度
	 * @return 新串
	 */
	public static String resolve(String text, int len) {
		// 字符串为空
		if (EmptyUtil.isEmpty(text)) {
			return text;
		}
		// 如果字符串长度大于要返回的长度
		if (text.length() > len) {
			// 声明字符串缓存
			StringBuilder sb = new StringBuilder(len);
			// 获得分解份数
			int size = text.length() / len;
			// 循环累加字符串
			for (int i = 0; i < len; i++) {
				sb.append(text.charAt(i * size));
			}
			// 赋值
			text = sb.toString();
		}
		// 返回加密字符串
		return text;
	}

	/**
	 * 字符串结合
	 * @param s1 字符串
	 * @param s2 字符串
	 * @return 返回聚合后字符串
	 */
	public static String combine(String s1, String s2) {
		return EmptyUtil.isEmpty(s1) || EmptyUtil.isEmpty(s2) ? StringConstants.EMPTY
				: combine(s1, s2, (s1.length() + s2.length()) / s1.length());
	}

	/**
	 * 字符串结合
	 * @param s1 字符串
	 * @param s2 字符串
	 * @param len 合并长度
	 * @return 返回聚合后字符串
	 */
	public static String combine(String s1, String s2, int len) {
		// 获得字符数组
		char[] c1 = s1.toCharArray();
		char[] c2 = s2.toCharArray();
		char[] c = new char[c1.length + c2.length];
		// 字符下标
		int i1 = 0;
		int i2 = 0;
		// 合并到字符里
		for (int i = 0; i < c.length; i++) {
			if (i % len == 0) {
				c[i] = c1[i1++];
			} else {
				c[i] = c2[i2++];
			}
		}
		// 返回字符数组
		return new String(c);
	}

	/**
	 * 字符串拆分 分解combine方法结合的字符串
	 * @param s 要拆分的字符串
	 * @param len 拆分长度
	 * @return 返回分开后字符串数组
	 */
	public static String[] separate(String s, int len) {
		// 如果字符为空返回空
		if (EmptyUtil.isEmpty(s)) {
			return ArrayConstants.STRING_EMPTY;
		}
		// 声明字符数组
		char[] c = s.toCharArray();
		char[] c1 = new char[c.length / len];
		char[] c2 = new char[c.length - c1.length];
		// 字符下标
		int i1 = 0;
		int i2 = 0;
		// 拆分数组
		for (int i = 0; i < c.length; i++) {
			if (i % len == 0) {
				c1[i1++] = c[i];
			} else {
				c2[i2++] = c[i];
			}
		}
		// 返回字符串数组
		return new String[] { new String(c1), new String(c2) };
	}

	private StringUtil() {}
}
