package com.weicoder.common.codec;

import com.weicoder.common.util.U;

/**
 * Base64 编码解码
 * @author WD
 */
public final class Base64 {
	/**
	 * Hex 编码
	 * @param data 要编码的对象 此对象要能转换成字节数组
	 * @return 编码后的字符串
	 */
	public static String encode(String data) {
		return encode(U.S.toBytes(data));
	}

	/**
	 * Hex 编码
	 * @param data 要编码的字节数组
	 * @return 编码后的字符串
	 */
	public static String encode(byte[] data) {
		return java.util.Base64.getEncoder().encodeToString(data);
	}

	/**
	 * 解码Hex
	 * @param str 要解码的字符串
	 * @return 解码后的字节数组
	 */
	public static String decodeString(String str) {
		return U.S.toString(decode(str));
	}

	/**
	 * 解码Hex
	 * @param str 要解码的字符串
	 * @return 解码后的字节数组
	 */
	public static byte[] decode(String str) {
		try {
			return java.util.Base64.getDecoder().decode(str);
		} catch (Exception e) {
			return U.S.toBytes(str);
		}
	}

	private Base64() {}
}