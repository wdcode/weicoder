package com.weicoder.common.crypto;

import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import com.weicoder.common.codec.Hex;
import com.weicoder.common.constants.ArrayConstants;
import com.weicoder.common.constants.EncryptConstants;
import com.weicoder.common.lang.Maps;
import com.weicoder.common.params.CommonParams;
import com.weicoder.common.util.StringUtil;

/**
 * hmac算法
 * @author WD
 * @since JDK7
 * @version 1.0 2014-1-25
 */
public final class HMac {
	// hmac算法使用
	private final static Map<String, Mac>	MACS	= Maps.getConcurrentMap();

	/**
	 * 使用HMAC-SHA1进行消息签名, 返回字节数组,长度为20字节.
	 * @param text 原始输入字符串
	 */
	public static String sha1(String text) {
		return Hex.encode(sha1(StringUtil.toBytes(text)));
	}

	/**
	 * 使用HMAC-SHA1进行消息签名, 返回字节数组,长度为20字节.
	 * @param text 原始输入字符串
	 */
	public static byte[] sha1(byte[] b) {
		return doFinal(b, EncryptConstants.ALGO_HMAC_SHA_1, CommonParams.ENCRYPT_KEY);
	}

	/**
	 * 使用HMAC-SHA1进行消息签名, 返回字节数组,长度为20字节.
	 * @param text 原始输入字符串
	 */
	public static String sha256(String text) {
		return Hex.encode(sha256(StringUtil.toBytes(text)));
	}

	/**
	 * 使用HMAC-SHA1进行消息签名, 返回字节数组,长度为20字节.
	 * @param text 原始输入字符串
	 */
	public static byte[] sha256(byte[] b) {
		return doFinal(b, EncryptConstants.ALGO_HMAC_SHA_256, CommonParams.ENCRYPT_KEY);
	}

	/**
	 * 使用HMAC-SHA1进行消息签名, 返回字节数组,长度为20字节.
	 * @param text 原始输入字符串
	 */
	public static String sha384(String text) {
		return Hex.encode(sha384(StringUtil.toBytes(text)));
	}

	/**
	 * 使用HMAC-SHA1进行消息签名, 返回字节数组,长度为20字节.
	 * @param text 原始输入字符串
	 */
	public static byte[] sha384(byte[] b) {
		return doFinal(b, EncryptConstants.ALGO_HMAC_SHA_384, CommonParams.ENCRYPT_KEY);
	}

	/**
	 * 使用HMAC-SHA1进行消息签名, 返回字节数组,长度为20字节.
	 * @param text 原始输入字符串
	 */
	public static String sha512(String text) {
		return Hex.encode(sha512(StringUtil.toBytes(text)));
	}

	/**
	 * 使用HMAC-SHA1进行消息签名, 返回字节数组,长度为20字节.
	 * @param text 原始输入字符串
	 */
	public static byte[] sha512(byte[] b) {
		return doFinal(b, EncryptConstants.ALGO_HMAC_SHA_512, CommonParams.ENCRYPT_KEY);
	}

	/**
	 * 加密
	 * @param b 要加密的字节数组
	 * @param algorithm 算法
	 * @param keys 键
	 * @return
	 */
	private static byte[] doFinal(byte[] b, String algorithm, String keys) {
		try {
			return getMac(algorithm, keys).doFinal(b);
		} catch (Exception e) {
			return ArrayConstants.BYTES_EMPTY;
		}
	}

	/**
	 * 根据算法与键获得Mac
	 * @param algorithm 算法
	 * @param keys 键
	 * @return Mac
	 */
	private static Mac getMac(String algorithm, String keys) {
		// 获得Mac
		Mac mac = MACS.get(algorithm + keys);
		// mac为空
		if (mac == null) {
			try {
				// 获得Mac
				MACS.put(algorithm + keys, mac = Mac.getInstance(algorithm));
				// 初始化算法
				mac.init(new SecretKeySpec(StringUtil.toBytes(keys), algorithm));
			} catch (Exception e) {}
		}
		// 返回Mac
		return mac;
	}

	private HMac() {}
}
