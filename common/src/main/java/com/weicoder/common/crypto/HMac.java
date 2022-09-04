package com.weicoder.common.crypto;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import com.weicoder.common.codec.Hex;
import com.weicoder.common.constants.ArrayConstants;
import com.weicoder.common.constants.EncryptConstants;
import com.weicoder.common.params.CommonParams;
import com.weicoder.common.util.StringUtil;
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
		return Hex.encode(sha1(StringUtil.toBytes(text)));
	}

	/**
	 * 使用HMAC-SHA1进行消息签名, 返回字节数组,长度为20字节.
	 * @param b 原始输入字符串
	 * @return 结果
	 */
	public static byte[] sha1(byte[] b) {
		return sha1(b, CommonParams.ENCRYPT_KEY);
	}

	/**
	 * 使用HMAC-SHA1进行消息签名, 返回字节数组,长度为20字节.
	 * @param b 原始输入字符串
	 * @param key 加密key
	 * @return 结果
	 */
	public static byte[] sha1(byte[] b, String key) {
		return doFinal(b, EncryptConstants.ALGO_HMAC_SHA_1, key);
	}

	/**
	 * 使用HMAC-SHA1进行消息签名, 返回字节数组,长度为20字节.
	 * @param text 原始输入字符串
	 * @return 结果
	 */
	public static String sha256(String text) {
		return Hex.encode(sha256(StringUtil.toBytes(text)));
	}

	/**
	 * 使用HMAC-SHA1进行消息签名, 返回字节数组,长度为20字节.
	 * @param b 原始输入字符串
	 * @return 结果
	 */
	public static byte[] sha256(byte[] b) {
		return doFinal(b, EncryptConstants.ALGO_HMAC_SHA_256, CommonParams.ENCRYPT_KEY);
	}

	/**
	 * 使用HMAC-SHA1进行消息签名, 返回字节数组,长度为20字节.
	 * @param text 原始输入字符串
	 * @return 结果
	 */
	public static String sha384(String text) {
		return Hex.encode(sha384(StringUtil.toBytes(text)));
	}

	/**
	 * 使用HMAC-SHA1进行消息签名, 返回字节数组,长度为20字节.
	 * @param b 原始输入字符串
	 * @return 结果
	 */
	public static byte[] sha384(byte[] b) {
		return doFinal(b, EncryptConstants.ALGO_HMAC_SHA_384, CommonParams.ENCRYPT_KEY);
	}

	/**
	 * 使用HMAC-SHA1进行消息签名, 返回字节数组,长度为20字节.
	 * @param text 原始输入字符串
	 * @return 结果
	 */
	public static String sha512(String text) {
		return Hex.encode(sha512(StringUtil.toBytes(text)));
	}

	/**
	 * 使用HMAC-SHA1进行消息签名, 返回字节数组,长度为20字节.
	 * @param b 字节数组
	 * @return 结果
	 */
	public static byte[] sha512(byte[] b) {
		return doFinal(b, EncryptConstants.ALGO_HMAC_SHA_512, CommonParams.ENCRYPT_KEY);
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
				return ArrayConstants.BYTES_EMPTY;
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
		// 声明Mac
		Mac mac = null;
		try {
			// 初始化算法
			mac = Mac.getInstance(algorithm);
			mac.init(new SecretKeySpec(StringUtil.toBytes(keys), algorithm));
		} catch (Exception e) {}
		return mac;
	}

	private HMac() {}
}
