package com.weicoder.common.crypto;

import java.security.Key;

import javax.crypto.Cipher;

import com.weicoder.common.codec.Hex;
import com.weicoder.common.constants.EncryptConstants;
import com.weicoder.common.crypto.base.BaseCrypt;
import com.weicoder.common.lang.Bytes;
import com.weicoder.common.lang.Conversion;
import com.weicoder.common.params.CommonParams;
import com.weicoder.common.util.KeyUtil;
import com.weicoder.common.util.StringUtil;

/**
 * 加密类
 * @author WD
 * @since JDK7
 * @version 1.0 2010-06-22
 */
public final class Encrypts extends BaseCrypt {
	/**
	 * 加密
	 * @param obj 要加密的对象
	 * @return 加密后的字节数组
	 */
	public static String encrypt(Object obj) {
		return obj instanceof String ? encrypt(Conversion.toString(obj)) : Hex.encode(encrypt(Bytes.toBytes(obj)));
	}

	/**
	 * 加密字符串 Hex编码
	 * @param text 要加密的字符串
	 * @return 加密后的字节数组
	 */
	public static String encrypt(String text) {
		return Hex.encode(encrypt(StringUtil.toBytes(text)));
	}

	/**
	 * 加密字符串
	 * @param b 要加密的字节数组
	 * @param key 加密key
	 * @return 加密后的字节数组
	 */
	public static byte[] encrypt(byte[] b) {
		return encrypt(b, CommonParams.ENCRYPT_KEY);
	}

	/**
	 * 加密字符串
	 * @param b 要加密的字节数组
	 * @param key 加密key
	 * @return 加密后的字节数组
	 */
	public static byte[] encrypt(byte[] b, String key) {
		// 判断加密方式
		switch (CommonParams.ENCRYPT_ALGO) {
			case EncryptConstants.ALGO_AES:
				// AES加密
				return aes(b, key);
			case EncryptConstants.ALGO_DES:
				// DES加密
				return des(b, key);
			case EncryptConstants.ALGO_RC2:
				// RC2加密
				return rc2(b, key);
			case EncryptConstants.ALGO_RC4:
				// RC4加密
				return rc4(b, key);
			default:
				// 默认返回AES
				return aes(b, key);
		}
	}

	/**
	 * 可逆的加密算法 DES算法
	 * @param b 需要加密的字节数组
	 * @return 返回加密后的字节数组
	 */
	public static byte[] des(byte[] b) {
		return des(b, CommonParams.ENCRYPT_KEY);
	}

	/**
	 * 可逆的加密算法 DES算法
	 * @param b 需要加密的字节数组
	 * @param key 加密key
	 * @return 返回加密后的字节数组
	 */
	public static byte[] des(byte[] b, String key) {
		return encrypt(b, key, CommonParams.ENCRYPT_KEY_LENGTH_DES, EncryptConstants.ALGO_DES);
	}

	/**
	 * 可逆的加密算法 AES算法
	 * @param b 需要加密的字节数组
	 * @return 返回加密后的字节数组
	 */
	public static byte[] aes(byte[] b) {
		return aes(b, CommonParams.ENCRYPT_KEY);
	}

	/**
	 * 可逆的加密算法 AES算法
	 * @param b 需要加密的字节数组
	 * @param key 加密key
	 * @return 返回加密后的字节数组
	 */
	public static byte[] aes(byte[] b, String key) {
		return encrypt(b, key, CommonParams.ENCRYPT_KEY_LENGTH_AES, EncryptConstants.ALGO_AES);
	}

	/**
	 * 可逆的加密算法 RC2算法
	 * @param b 需要加密的字节数组
	 * @return 返回加密后的字节数组
	 */
	public static byte[] rc2(byte[] b) {
		return rc2(b, CommonParams.ENCRYPT_KEY);
	}

	/**
	 * 可逆的加密算法 RC2算法
	 * @param b 需要加密的字节数组
	 * @param key 加密key
	 * @return 返回加密后的字节数组
	 */
	public static byte[] rc2(byte[] b, String key) {
		return encrypt(b, key, CommonParams.ENCRYPT_KEY_LENGTH_RC2, EncryptConstants.ALGO_RC2);
	}

	/**
	 * 可逆的加密算法 RC4算法
	 * @param b 需要加密的字节数组
	 * @return 返回加密后的字节数组
	 */
	public static byte[] rc4(byte[] b) {
		return rc4(b, CommonParams.ENCRYPT_KEY);
	}

	/**
	 * 可逆的加密算法 RC4算法
	 * @param b 需要加密的字节数组
	 * @param key 加密key
	 * @return 返回加密后的字节数组
	 */
	public static byte[] rc4(byte[] b, String key) {
		return encrypt(b, key, CommonParams.ENCRYPT_KEY_LENGTH_RC4, EncryptConstants.ALGO_RC4);
	}

	/**
	 * 可逆的非对称加密算法 RSA算法
	 * @param b 需要加密的字节数组
	 * @return 返回加密后的字节数组
	 */
	public static byte[] rsa(byte[] b) {
		return rsa(b, KeyUtil.getPublicKey(EncryptConstants.ALGO_RSA));
	}

	/**
	 * 可逆的非对称加密算法 RSA算法
	 * @param b 需要加密的字节数组
	 * @param key 加密密钥
	 * @return 返回加密后的字节数组
	 */
	public static byte[] rsa(byte[] b, Key key) {
		return doFinal(b, key, EncryptConstants.ALGO_RSA, Cipher.ENCRYPT_MODE);
	}

	/**
	 * 加密字符串
	 * @param text 要加密的字符串
	 * @param key 加密密钥Key 长度有限制 DSE 为8位 ASE 为16位
	 * @param keys 键
	 * @param algorithm 算法
	 * @return 加密后的字节数组
	 */
	private static byte[] encrypt(byte[] b, String keys, int len, String algorithm) {
		return doFinal(b, keys, len, algorithm, Cipher.ENCRYPT_MODE);
	}

	/**
	 * 私有构造
	 */
	private Encrypts() {}
}
