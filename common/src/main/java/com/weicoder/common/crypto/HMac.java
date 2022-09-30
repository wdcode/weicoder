package com.weicoder.common.crypto;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import com.weicoder.common.codec.Hex;
import com.weicoder.common.constants.C; 
import com.weicoder.common.params.P;
import com.weicoder.common.util.U; 

/**
 * hmac算法
 * @author WD
 */
public final class HMac {
	/**
	 * 使用HMAC-SHA1进行消息签名, 返回字节数组,长度为20字节.
	 * @param text 原始输入字符串
	 * @return 结果
	 */
	public static String sha1(String text) {
		return Hex.encode(sha1(U.S.toBytes(text)));
	}

	/**
	 * 使用HMAC-SHA1进行消息签名, 返回字节数组,长度为20字节.
	 * @param b 原始输入字符串
	 * @return 结果
	 */
	public static byte[] sha1(byte[] b) {
		return sha1(b, P.C.ENCRYPT_KEY);
	}

	/**
	 * 使用HMAC-SHA1进行消息签名, 返回字节数组,长度为20字节.
	 * @param b 原始输入字符串
	 * @param key 加密key
	 * @return 结果
	 */
	public static byte[] sha1(byte[] b, String key) {
		return doFinal(b, C.E.ALGO_HMAC_SHA_1, key);
	}

	/**
	 * 使用HMAC-SHA1进行消息签名, 返回字节数组,长度为20字节.
	 * @param text 原始输入字符串
	 * @return 结果
	 */
	public static String sha256(String text) {
		return Hex.encode(sha256(U.S.toBytes(text)));
	}

	/**
	 * 使用HMAC-SHA1进行消息签名, 返回字节数组,长度为20字节.
	 * @param b 原始输入字符串
	 * @return 结果
	 */
	public static byte[] sha256(byte[] b) {
		return doFinal(b, C.E.ALGO_HMAC_SHA_256, P.C.ENCRYPT_KEY);
	}

	/**
	 * 使用HMAC-SHA1进行消息签名, 返回字节数组,长度为20字节.
	 * @param text 原始输入字符串
	 * @return 结果
	 */
	public static String sha384(String text) {
		return Hex.encode(sha384(U.S.toBytes(text)));
	}

	/**
	 * 使用HMAC-SHA1进行消息签名, 返回字节数组,长度为20字节.
	 * @param b 原始输入字符串
	 * @return 结果
	 */
	public static byte[] sha384(byte[] b) {
		return doFinal(b, C.E.ALGO_HMAC_SHA_384, P.C.ENCRYPT_KEY);
	}

	/**
	 * 使用HMAC-SHA1进行消息签名, 返回字节数组,长度为20字节.
	 * @param text 原始输入字符串
	 * @return 结果
	 */
	public static String sha512(String text) {
		return Hex.encode(sha512(U.S.toBytes(text)));
	}

	/**
	 * 使用HMAC-SHA1进行消息签名, 返回字节数组,长度为20字节.
	 * @param b 字节数组
	 * @return 结果
	 */
	public static byte[] sha512(byte[] b) {
		return doFinal(b, C.E.ALGO_HMAC_SHA_512, P.C.ENCRYPT_KEY);
	}

	/**
	 * 加密
	 * @param b 要加密的字节数组
	 * @param algorithm 算法
	 * @param keys 键
	 * @return 结果
	 */
	private static byte[] doFinal(byte[] b, String algorithm, String keys) {
		try {
			// 参数为空时
			if (U.E.isEmptys(b, algorithm, keys))
				return C.A.BYTES_EMPTY;
			return getMac(algorithm, keys).doFinal(b);
		} catch (Exception e) {
			return C.A.BYTES_EMPTY;
		}
	}

	/**
	 * 根据算法与键获得Mac
	 * @param algorithm 算法
	 * @param keys 键
	 * @return Mac
	 */
	private static Mac getMac(String algorithm, String keys) {
		// 声明Mac
		Mac mac = null;
		try {
			// 初始化算法
			mac = Mac.getInstance(algorithm);
			mac.init(new SecretKeySpec(U.S.toBytes(keys), algorithm));
		} catch (Exception e) {}
		return mac;
	}

	private HMac() {}
}
