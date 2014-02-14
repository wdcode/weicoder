package com.weicoder.common.util;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.weicoder.common.constants.ArrayConstants;
import com.weicoder.common.constants.HtmlConstants;

import com.weicoder.common.constants.StringConstants;

import com.weicoder.common.lang.Conversion;
import com.weicoder.common.lang.Lists;
import com.weicoder.common.lang.Validate;
import com.weicoder.common.params.CommonParams;

/**
 * 对字符串进行一些处理。
 * @author WD
 * @since JDK7
 * @version 1.0 2009-03-01
 */
public final class StringUtil {
	/**
	 * 把HTML中的保留字符专成对应的取代字符,如果为空返回原串 <br/>
	 * <h2>注: 替换"&"-->"&amp;" "<"-->"&lt;" ">"-->"&gt;" "\""-->"&quot;</h2>
	 * @param context 需要转换的字符串
	 * @return 转换后的字符串
	 */
	public static String htmlConvertHigh(String context) {
		// 判断字符串为空
		if (EmptyUtil.isEmpty(context)) {
			return context;
		}
		// 替换&
		if (context.indexOf(StringConstants.AMP) > -1) {
			context = context.replaceAll(StringConstants.AMP, HtmlConstants.ESC_AMP);
		}
		// 替换<
		if (context.indexOf(StringConstants.LT) > -1) {
			context = context.replaceAll(StringConstants.LT, HtmlConstants.ESC_LT);
		}
		// 替换>
		if (context.indexOf(StringConstants.GT) > -1) {
			context = context.replaceAll(StringConstants.GT, HtmlConstants.ESC_GT);
		}
		// 替换"
		if (context.indexOf(StringConstants.QUOT) > -1) {
			context = context.replaceAll(StringConstants.QUOT, HtmlConstants.ESC_QUOT);
		}
		// 返回转换好的字符串
		return context;
	}

	/**
	 * 把HTML中的保留字符专成对应的取代字符,如果为空返回原串 <br/>
	 * <h2>注: 调用htmlConvertHigh(String context)方法</h2>
	 * @param map 要转化的Map
	 * @return 转换后的Map
	 */
	public static Map<String, String> htmlConvertHigh(Map<String, String> map) {
		// 判断map是否为空
		if (EmptyUtil.isEmpty(map)) {
			// 为空直接返回
			return map;
		}
		// 循环map
		for (Map.Entry<String, String> entry : map.entrySet()) {
			// 转换字符串
			entry.setValue(htmlConvertHigh(entry.getValue()));
		}
		// 返回Map
		return map;
	}

	/**
	 * 把HTML中的保留字符专成对应的取代字符,如果为空返回原串 <br/>
	 * <h2>注: 调用htmlConvertHigh(Map<String, String> map)方法</h2>
	 * @param list 要转化的list
	 * @return 转换后的list
	 */
	public static List<Map<String, String>> htmlConvertHigh(List<Map<String, String>> list) {
		// 判断list是否为空
		if (EmptyUtil.isEmpty(list)) {
			return list;
		}
		// 获得列表大小
		int size = list.size();
		// 声明列表保存新变量
		List<Map<String, String>> ls = Lists.getList(size);
		// 循环list
		for (int i = 0; i < size; i++) {
			// 转换数据
			ls.add(htmlConvertHigh(list.get(i)));
		}
		// 返回list
		return ls;
	}

	/**
	 * 把HTML中的保留字取代字符转成对应的保留字字符,如果为空返回原串<br/>
	 * <h2>注: 替换"&lt;"-->"<" "&gt;"-->">" "&quot;"-->"\"" "&amp;"-->"&"</h2>
	 * @param context 需要转换的字符串
	 * @return 转换后的字符串
	 */
	public static String highConvertHtml(String context) {
		// 判断字符串为空
		if (EmptyUtil.isEmpty(context)) {
			return context;
		}
		// 替换&amp;
		if (context.indexOf(HtmlConstants.ESC_AMP) > -1) {
			context = context.replaceAll(HtmlConstants.ESC_AMP, StringConstants.AMP);
		}
		// 替换&lt;
		if (context.indexOf(HtmlConstants.ESC_LT) > -1) {
			context = context.replaceAll(HtmlConstants.ESC_LT, StringConstants.LT);
		}
		// 替换&gt;
		if (context.indexOf(HtmlConstants.ESC_GT) > -1) {
			context = context.replaceAll(HtmlConstants.ESC_GT, StringConstants.GT);
		}
		// 替换&quot;
		if (context.indexOf(HtmlConstants.ESC_QUOT) > -1) {
			context = context.replaceAll(HtmlConstants.ESC_QUOT, StringConstants.QUOT);
		}
		// 返回转换好的字符串
		return context;
	}

	/**
	 * 把HTML中的保留字符专成对应的取代字符,如果为空返回原串 <br/>
	 * <h2>注: 调用htmlConvertHigh(String context)方法</h2>
	 * @param map 要转化的Map
	 * @return 转换后的Map
	 */
	public static Map<String, String> highConvertHtml(Map<String, String> map) {
		// 判断map是否为空
		if (EmptyUtil.isEmpty(map)) {
			// 为空直接返回
			return map;
		}
		// 循环map
		for (Map.Entry<String, String> entry : map.entrySet()) {
			// 转换字符串
			entry.setValue(highConvertHtml(entry.getValue()));
		}
		// 返回Map
		return map;
	}

	/**
	 * 把HTML中的保留字符专成对应的取代字符,如果为空返回原串 <br/>
	 * <h2>注: 调用htmlConvertHigh(Map<String, String> map)方法</h2>
	 * @param list 要转化的list
	 * @return 转换后的list
	 */
	public static List<Map<String, String>> highConvertHtml(List<Map<String, String>> list) {
		// 判断list是否为空
		if (EmptyUtil.isEmpty(list)) {
			return list;
		}
		// 获得列表大小
		int size = list.size();
		// 声明列表保存新变量
		List<Map<String, String>> ls = Lists.getList(size);
		// 循环list
		for (int i = 0; i < size; i++) {
			// 转换数据
			ls.add(highConvertHtml(list.get(i)));
		}
		// 返回list
		return ls;
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
	 * <h2>注: 如果没有开始字符串 开始长度为0 如果没有结束字符串 结束长度为str长度</h2> <h2>注: 不包含开始与结束字符串</h2>
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
	 * @param end 结束字符
	 * @return 截取后的字符串
	 */
	public static String subString(String str, int start) {
		return subString(str, start, str.length());
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
			return StringConstants.EMPTY;
		}
	}

	/**
	 * 把输入的其它命名法变成驼峰命名法,如 User_Id --> userId User --> user
	 * @param name 属性名
	 * @return 转换后的字符串
	 */
	public static String convert(String name) {
		// 如果为空返回原串
		if (EmptyUtil.isEmpty(name)) {
			return name;
		}
		// 分解_个字段
		String[] strs = name.toLowerCase().split(StringConstants.UNDERLINE);
		// 实例一个字符串缓存
		StringBuilder buf = new StringBuilder();
		// 保存字符
		String s = null;
		// 获得新字符串
		s = strs[0];
		// 添加字符串
		buf.append(s.substring(0, 1).toLowerCase()).append(s.substring(1, s.length()));
		// 循环数组
		for (int i = 1; i < strs.length; i++) {
			// 获得新字符串
			s = strs[i];
			// 添加字符串
			buf.append(s.substring(0, 1).toUpperCase()).append(s.substring(1, s.length()));
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
	 * 私有构造
	 */
	private StringUtil() {}
}
