package com.weicoder.common.crypto;

import javax.crypto.Cipher;

import com.weicoder.common.codec.Hex;
import com.weicoder.common.constants.ArrayConstants;
import com.weicoder.common.constants.EncryptConstants;
import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.crypto.base.BaseCrypt;
import com.weicoder.common.params.CommonParams;
import com.weicoder.common.util.EmptyUtil;
import com.weicoder.common.util.StringUtil;

/**
 * 解密类
 * @author WD
 */
public final class Decrypts extends BaseCrypt {
	/**
	 * 解密Token 使用
	 * @param info token信息串
	 * @return 字节数组
	 */
	public static byte[] token(String info) {
		// 验证去掉"""
		String token = StringUtil.replace(StringUtil.trim(info), "\"", StringConstants.EMPTY);
		// 判断验证串是否符合标准
		if (!EmptyUtil.isEmpty(token) && token.length() > CommonParams.TOKEN_LENGHT) {
			// 变为小写
			String t = token.toLowerCase();
			// 拆分字符串
			String[] temp = StringUtil.separate(t, t.length() / CommonParams.TOKEN_LENGHT);
			if (!EmptyUtil.isEmpty(temp) && temp.length == 2) {
				// 验证串
				String ver = temp[0];
				// 信息串
				String user = temp[1];
				// 判断校验串是否合法
				if (ver.equals(Digest.absolute(user, CommonParams.TOKEN_LENGHT))) {
					return Decrypts.rc4(Hex.decode(user));
				}
			}
		}
		return ArrayConstants.BYTES_EMPTY;
	}

	/**
	 * 解密字符串
	 * @param text 要解密的字符串
	 * @return 解密后的字符串
	 */
	public static String decryptString(String text) {
		return StringUtil.toString(decrypt(text));
	}

	/**
	 * 解密字符串
	 * @param text 要解密的字符串
	 * @return 解密后的字符串
	 */
	public static byte[] decrypt(String text) {
		return decrypt(Hex.decode(text));
	}

	/**
	 * 解密字符串
	 * @param b 要解密的字节数组
	 * @return 解密后的字节数组
	 */
	public static byte[] decrypt(byte[] b) {
		return decrypt(b, CommonParams.ENCRYPT_KEY);
	}

	/**
	 * 解密字符串
	 * @param b 要解密的字节数组
	 * @param keys 键
	 * @return 解密后的字节数组
	 */
	public static byte[] decrypt(byte[] b, String keys) {
		// 判断解密方式
		switch (CommonParams.ENCRYPT_ALGO) {
			case EncryptConstants.ALGO_AES:
				// AES解密
				return aes(b, keys);
			case EncryptConstants.ALGO_DES:
				// DES解密
				return des(b, keys);
			case EncryptConstants.ALGO_RC4:
				// RC4解密
				return rc4(b, keys);
			default:
				// 默认返回AES
				return aes(b, keys);
		}
	}

	/**
	 * 针对encode方法的解密 DES算法
	 * @param b 需要解密的字节数组
	 * @return 返回解密后的字符串
	 */
	public static byte[] des(byte[] b) {
		return des(b, CommonParams.ENCRYPT_KEY);
	}

	/**
	 * 针对encode方法的解密 DES算法
	 * @param b 需要解密的字节数组
	 * @param keys 键
	 * @return 返回解密后的字符串
	 */
	public static byte[] des(byte[] b, String keys) {
		return decrypt(b, keys, EncryptConstants.LENGTH_DES, EncryptConstants.ALGO_DES);
	}

	/**
	 * 针对encrypt方法的解密 AES算法
	 * @param b 需要解密的字节数组
	 * @return 返回解密后的字符串 text为空或发生异常返回原串
	 */
	public static byte[] aes(byte[] b) {
		return aes(b, CommonParams.ENCRYPT_KEY);
	}

	/**
	 * 针对encrypt方法的解密 AES算法
	 * @param b 需要解密的字节数组
	 * @param keys 键
	 * @return 返回解密后的字符串 text为空或发生异常返回原串
	 */
	public static byte[] aes(byte[] b, String keys) {
		return decrypt(b, keys, EncryptConstants.LENGTH_AES, EncryptConstants.ALGO_AES);
	}

	/**
	 * 针对encrypt方法的解密 RC4算法
	 * @param b 需要解密的字节数组
	 * @return 返回解密后的字符串 text为空或发生异常返回原串
	 */
	public static byte[] rc4(byte[] b) {
		return rc4(b, CommonParams.ENCRYPT_KEY);
	}

	/**
	 * 针对encrypt方法的解密 RC4算法
	 * @param b 需要解密的字节数组
	 * @param keys 键
	 * @return 返回解密后的字符串 text为空或发生异常返回原串
	 */
	public static byte[] rc4(byte[] b, String keys) {
		return decrypt(b, keys, EncryptConstants.LENGTH_RC4, EncryptConstants.ALGO_RC4);
	}

	/**
	 * 解密字符串
	 * @param text 要解密的字符串
	 * @param key 解密密钥Key 长度有限制 DSE 为8位 ASE 为16位
	 * @param keys 键
	 * @param algorithm 算法
	 * @return 解密后的字符串
	 */
	private static byte[] decrypt(byte[] b, String keys, int len, String algorithm) {
		return doFinal(b, keys, len, algorithm, Cipher.DECRYPT_MODE);
	}

	private Decrypts() {}
}
