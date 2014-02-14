package com.weicoder.common.codec;

import com.weicoder.common.constants.ArrayConstants;
import com.weicoder.common.lang.Bytes;
import com.weicoder.common.util.EmptyUtil;

/**
 * Base64 编码解码
 * @author WD
 * @since JDK7
 * @version 1.0 2013-11-26
 */
public final class Base64 {
	// 编码用
	private final static char[]	DIGITS	= "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=".toCharArray();
	private final static byte[]	CODES	= new byte[256];
	static {
		for (int i = 0; i < 256; i++) {
			CODES[i] = -1;
		}
		for (int i = 'A'; i <= 'Z'; i++) {
			CODES[i] = (byte) (i - 'A');
		}
		for (int i = 'a'; i <= 'z'; i++) {
			CODES[i] = (byte) (26 + i - 'a');
		}
		for (int i = '0'; i <= '9'; i++) {
			CODES[i] = (byte) (52 + i - '0');
		}
		CODES['+'] = 62;
		CODES['/'] = 63;
	}

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
		// 声明保存char数组
		final char[] out = new char[((data.length + 2) / 3) * 4];
		// 编码
		for (int i = 0, index = 0; i < data.length; i += 3, index += 4) {
			// 标记
			boolean quad = false;
			boolean trip = false;
			// 编码
			int val = (0xFF & data[i]);
			val <<= 8;
			if ((i + 1) < data.length) {
				val |= (0xFF & data[i + 1]);
				trip = true;
			}
			val <<= 8;
			if ((i + 2) < data.length) {
				val |= (0xFF & data[i + 2]);
				quad = true;
			}
			out[index + 3] = DIGITS[(quad ? (val & 0x3F) : 64)];
			val >>= 6;
			out[index + 2] = DIGITS[(trip ? (val & 0x3F) : 64)];
			val >>= 6;
			out[index + 1] = DIGITS[val & 0x3F];
			val >>= 6;
			out[index + 0] = DIGITS[val & 0x3F];
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
		// 要解码的字符串为空
		if (EmptyUtil.isEmpty(str)) {
			return ArrayConstants.BYTES_EMPTY;
		}
		// 变为char数组
		char[] data = str.toCharArray();
		// 获得长度
		int tempLen = data.length;
		// 获得长度
		for (int ix = 0; ix < data.length; ix++) {
			if ((data[ix] > 255) || CODES[data[ix]] < 0) {
				--tempLen; // ignore non-valid chars and padding
			}
		}
		int len = (tempLen / 4) * 3;
		if ((tempLen % 4) == 3) {
			len += 2;
		}
		if ((tempLen % 4) == 2) {
			len += 1;
		}
		// 输出数组
		final byte[] out = new byte[len];

		// 标记
		int shift = 0; // # of excess bits stored in accum
		int accum = 0; // excess bits
		int index = 0;

		// 解码
		for (int ix = 0; ix < data.length; ix++) {
			final int value = (data[ix] > 255) ? -1 : CODES[data[ix]];

			if (value >= 0) { // skip over non-code
				accum <<= 6; // bits shift up by 6 each time thru
				shift += 6; // loop, with new bits being put in
				accum |= value; // at the bottom.
				if (shift >= 8) { // whenever there are 8 or more shifted in,
					shift -= 8; // write them out (from the top, leaving any
					out[index++] = // excess at the bottom for next iteration.
					(byte) ((accum >> shift) & 0xff);
				}
			}
		}
		// 返回字节数组
		return out;
	}

	private Base64() {}
}