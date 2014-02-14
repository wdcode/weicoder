package com.weicoder.common.codec;

import com.weicoder.common.constants.ArrayConstants;
import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.lang.Bytes;
import com.weicoder.common.util.EmptyUtil;

/**
 * Hex 编码解码
 * @author WD
 * @since JDK6
 * @version 1.0 2013-08-14
 */
public final class Hex {
	// 编码用
	private static final char[]	DIGITS	= "0123456789abcdef".toCharArray();

	/**
	 * Hex 编码
	 * @param data 要编码的对象 此对象要能转换成字节数组
	 * @return 编码后的字符串
	 */
	public static String encode(Object data) {
		return encode(Bytes.toBytes(data));
	}

	/**
	 * Hex 编码
	 * @param data 要编码的字节数组
	 * @return 编码后的字符串
	 */
	public static String encode(byte[] data) {
		// 如果为空返回字符串
		if (EmptyUtil.isEmpty(data)) {
			return StringConstants.EMPTY;
		}
		// 声明字符数组
		int l = data.length;
		char[] out = new char[l << 1];
		// 2个字节保存一个hex.
		for (int i = 0, j = 0; i < l; i++) {
			out[j++] = DIGITS[(0xF0 & data[i]) >>> 4];
			out[j++] = DIGITS[0x0F & data[i]];
		}
		// 返回字符串
		return new String(out);
	}

	/**
	 * 解码Hex
	 * @param str 要解码的字符串
	 * @return 解码后的字节数组
	 */
	public static byte[] decode(String str) {
		// 如果要解码的字符串为空 返回字节数组
		if (EmptyUtil.isEmpty(str)) {
			return ArrayConstants.BYTES_EMPTY;
		}
		// 声明字节数组
		char[] data = str.toCharArray();
		int len = data.length;
		byte[] out = new byte[len >> 1];
		// 2个字符转换成一个字节.
		for (int i = 0, j = 0; j < len; i++) {
			int f = Character.digit(data[j++], 16) << 4;
			f = f | Character.digit(data[j++], 16);
			out[i] = (byte) (f & 0xFF);
		}
		// 返回字节数组
		return out;
	}

	private Hex() {}
}