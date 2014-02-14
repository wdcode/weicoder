package com.weicoder.common.crypto;

import java.security.MessageDigest;
import java.util.Map;

import com.weicoder.common.codec.Hex;
import com.weicoder.common.constants.EncryptConstants;
import com.weicoder.common.constants.StringConstants;

import com.weicoder.common.lang.Maps;
import com.weicoder.common.params.CommonParams;
import com.weicoder.common.util.EmptyUtil;
import com.weicoder.common.util.StringUtil;

/**
 * 信息摘要类
 * @author WD
 * @since JDK7
 * @version 1.0 2010-06-22
 */
public final class Digest {
	// 保存摘要算法
	private final static Map<String, MessageDigest>	DIGEST	= Maps.getConcurrentMap();

	/**
	 * 加密密码 空密码不加密
	 * @param text 要加密的文本
	 * @return 加密后的文本
	 */
	public static String password(String text) {
		return EmptyUtil.isEmpty(text) ? StringConstants.EMPTY : absolute(text, 20);
	}

	/**
	 * 先普通加密 在获得摘要 无法解密
	 * @param b 要加密的字节数组
	 * @return 加密后的文本
	 */
	public static byte[] absolute(byte[] b) {
		return digest(Encrypts.encrypt(b));
	}

	/**
	 * 先普通加密 在获得摘要 无法解密
	 * @param text 要加密的文本
	 * @return 加密后的文本
	 */
	public static String absolute(String text) {
		return digest(Encrypts.encrypt(text));
	}

	/**
	 * 先普通加密 在获得摘要 无法解密
	 * @param text 要加密的文本
	 * @param len 要返回字符串的长度
	 * @return 加密后的文本
	 */
	public static String absolute(String text, int len) {
		return StringUtil.resolve(absolute(text), len);
	}

	/**
	 * 获得字符串摘要
	 * @param text 要获得摘要的字符串
	 * @return 获得摘要后的字节数组的hex后字符串
	 */
	public static String digest(String text) {
		return Hex.encode(digest(StringUtil.toBytes(text)));
	}

	/**
	 * 获得字符串摘要
	 * @param b 要获得摘要的字节数组
	 * @return 获得摘要后的字节数组
	 */
	public static byte[] digest(byte[] b) {
		return getMessageDigest(b, CommonParams.ENCRYPT_DIGEST);
	}

	/**
	 * 返回字符串的MD2(信息-摘要算法)码
	 * @param text 要MD2的字符串
	 * @return MD2后的字节数组的hex后字符串
	 */
	public static String md2(String text) {
		return Hex.encode(md2(StringUtil.toBytes(text)));
	}

	/**
	 * 返回字符串的MD2(信息-摘要算法)码
	 * @param b 要MD2的z字节数组
	 * @return MD2后的字节数组
	 */
	public static byte[] md2(byte[] b) {
		return getMessageDigest(b, EncryptConstants.ALGO_MD2);
	}

	/**
	 * 返回字符串的MD5(信息-摘要算法)码
	 * @param text 要MD5的字符串
	 * @return MD5后的字节数组的hex后字符串
	 */
	public static String md5(String text) {
		return Hex.encode(md5(StringUtil.toBytes(text)));
	}

	/**
	 * 返回字符串的MD5(信息-摘要算法)码
	 * @param b 要MD5的字节数组
	 * @return MD5后的字节数组
	 */
	public static byte[] md5(byte[] b) {
		return getMessageDigest(b, EncryptConstants.ALGO_MD5);
	}

	/**
	 * 返回字符串的SHA-256(信息-摘要算法)码
	 * @param text 要SHA-256的字符串
	 * @return SHA-256后的字节数组的hex后字符串
	 */
	public static String sha256(String text) {
		return Hex.encode(sha256(StringUtil.toBytes(text)));
	}

	/**
	 * 返回字符串的SHA-256(信息-摘要算法)码
	 * @param b 要SHA-256的字节数组
	 * @return SHA-256后的字节数组
	 */
	public static byte[] sha256(byte[] b) {
		return getMessageDigest(b, EncryptConstants.ALGO_SHA_256);
	}

	/**
	 * 返回字符串的SHA-384(信息-摘要算法)码
	 * @param text 要SHA-384的字符串
	 * @return SHA-384后的字节数组的hex后字符串
	 */
	public static String sha384(String text) {
		return Hex.encode(sha384(StringUtil.toBytes(text)));
	}

	/**
	 * 返回字符串的SHA-384(信息-摘要算法)码
	 * @param b 要SHA-384的字节数组
	 * @return SHA-384后的字节数组
	 */
	public static byte[] sha384(byte[] b) {
		return getMessageDigest(b, EncryptConstants.ALGO_SHA_384);
	}

	/**
	 * 返回字符串的SHA-512(信息-摘要算法)码
	 * @param text 要SHA-512的字符串
	 * @return SHA-512后的字节数组的hex后字符串
	 */
	public static String sha512(String text) {
		return Hex.encode(sha512(StringUtil.toBytes(text)));
	}

	/**
	 * 返回字符串的SHA-512(信息-摘要算法)码
	 * @param b 要SHA-512的字节数组
	 * @return SHA-512后的字节数组
	 */
	public static byte[] sha512(byte[] b) {
		return getMessageDigest(b, EncryptConstants.ALGO_SHA_512);
	}

	/**
	 * 返回字符串的SHA-1(信息-摘要算法)码
	 * @param text 要SHA-1的字符串
	 * @return SHA-1后的字节数组的hex后字符串
	 */
	public static String sha1(String text) {
		return Hex.encode(sha1(StringUtil.toBytes(text)));
	}

	/**
	 * 返回字符串的SHA-1(信息-摘要算法)码
	 * @param text 要SHA-1的字符串
	 * @return SHA-1后的字节数组
	 */
	public static byte[] sha1(byte[] b) {
		return getMessageDigest(b, EncryptConstants.ALGO_SHA_1);
	}

	/**
	 * 获得信息摘要
	 * @param b 要加密的字节数组
	 * @param algorithm 摘要算法
	 * @return 加密后的字节数组
	 */
	public static byte[] getMessageDigest(byte[] b, String algorithm) {
		try {
			// 根据算法获得摘要
			MessageDigest digest = DIGEST.get(algorithm);
			// 摘要为空
			if (digest == null) {
				// 声明新摘要
				digest = MessageDigest.getInstance(algorithm);
				// 设置到静态列表中
				DIGEST.put(algorithm, digest);
			}
			// 摘要算法
			return digest.digest(b);
		} catch (Exception e) {
			return b;
		}
	}

	/**
	 * 私有构造
	 */
	private Digest() {}
}