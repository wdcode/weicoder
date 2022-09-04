package com.weicoder.common.crypto;

import javax.crypto.Cipher;

import com.weicoder.common.codec.Hex;
import com.weicoder.common.constants.EncryptConstants;
import com.weicoder.common.crypto.base.BaseCrypt;
import com.weicoder.common.lang.Bytes;
import com.weicoder.common.lang.W;
import com.weicoder.common.params.CommonParams;
import com.weicoder.common.util.StringUtil;

/**
 * 对称加密类
 * 
 * @author WD
 */
public class Encrypts extends BaseCrypt {
	/**
	 * 加密token方法
	 * 
	 * @param array token转换的字符串
	 * @return 加密后的字符串
	 */
	public static String token(byte[] array) {
		// 加密登录凭证字符串
		String info = Hex.encode(Encrypts.rc4(array, CommonParams.TOKEN_KEY));
		// 返回加密字符串
		return StringUtil.combine(Digest.absolute(info, CommonParams.TOKEN_LENGHT), info).toUpperCase();
	}

	/**
	 * 加密
	 * 
	 * @param obj 要加密的对象
	 * @return 加密后的字节数组
	 */
	public static String encrypt(Object obj) {
		return obj instanceof String ? encrypt(W.C.toString(obj)) : Hex.encode(encrypt(Bytes.toBytes(obj)));
	}

	/**
	 * 加密字符串 Hex编码
	 * 
	 * @param text 要加密的字符串
	 * @return 加密后的字节数组
	 */
	public static String encrypt(String text) {
		return Hex.encode(encrypt(StringUtil.toBytes(text)));
	}

	/**
	 * 加密字符串
	 * 
	 * @param b 要加密的字节数组
	 * @return 加密后的字节数组
	 */
	public static byte[] encrypt(byte[] b) {
		return encrypt(b, CommonParams.ENCRYPT_KEY);
	}

	/**
	 * 加密字符串
	 * 
	 * @param b   要加密的字节数组
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
	 * 
	 * @param b 需要加密的字节数组
	 * @return 返回加密后的字节数组
	 */
	public static byte[] des(byte[] b) {
		return des(b, CommonParams.ENCRYPT_KEY);
	}

	/**
	 * 可逆的加密算法 DES算法
	 * 
	 * @param b   需要加密的字节数组
	 * @param key 加密key
	 * @return 返回加密后的字节数组
	 */
	public static byte[] des(byte[] b, String key) {
		return encrypt(b, key, EncryptConstants.LENGTH_DES, EncryptConstants.ALGO_DES);
	}

	/**
	 * 可逆的加密算法 AES算法
	 * 
	 * @param b 需要加密的字节数组
	 * @return 返回加密后的字节数组
	 */
	public static byte[] aes(byte[] b) {
		return aes(b, CommonParams.ENCRYPT_KEY);
	}

	/**
	 * 可逆的加密算法 AES算法
	 * 
	 * @param b   需要加密的字节数组
	 * @param key 加密key
	 * @return 返回加密后的字节数组
	 */
	public static byte[] aes(byte[] b, String key) {
		return encrypt(b, key, EncryptConstants.LENGTH_AES, EncryptConstants.ALGO_AES);
	}

	/**
	 * 可逆的加密算法 RC4算法
	 * 
	 * @param b 需要加密的字节数组
	 * @return 返回加密后的字节数组
	 */
	public static byte[] rc4(byte[] b) {
		return rc4(b, CommonParams.ENCRYPT_KEY);
	}

	/**
	 * 可逆的加密算法 RC4算法
	 * 
	 * @param b   需要加密的字节数组
	 * @param key 加密key
	 * @return 返回加密后的字节数组
	 */
	public static byte[] rc4(byte[] b, String key) {
		return encrypt(b, key, EncryptConstants.LENGTH_RC4, EncryptConstants.ALGO_RC4);
	}

	/**
	 * 加密字符串
	 * 
	 * @param text      要加密的字符串
	 * @param key       加密密钥Key 长度有限制 DSE 为8位 ASE 为16位
	 * @param keys      键
	 * @param algorithm 算法
	 * @return 加密后的字节数组
	 */
	private static byte[] encrypt(byte[] b, String keys, int len, String algorithm) {
		return doFinal(b, keys, len, algorithm, Cipher.ENCRYPT_MODE);
	}
}
