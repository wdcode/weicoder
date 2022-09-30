package com.weicoder.common.crypto;

import javax.crypto.Cipher;

import com.weicoder.common.codec.Hex;
import com.weicoder.common.constants.C; 
import com.weicoder.common.crypto.base.BaseCrypt;
import com.weicoder.common.params.P;
import com.weicoder.common.util.U; 

/**
 * 对称解密类
 * 
 * @author WD
 */
public class Decrypts extends BaseCrypt {
	/**
	 * 解密Token 使用
	 * 
	 * @param  info token信息串
	 * @return      字节数组
	 */
	public static byte[] token(String info) {
		// 验证去掉"""
		String token = U.S.replace(U.S.trim(info), "\"", C.S.EMPTY);
		// 判断验证串是否符合标准
		if (U.E.isNotEmpty(token) && token.length() > P.C.TOKEN_LENGHT) {
			// 变为小写
			String t = token.toLowerCase();
			// 拆分字符串
			String[] temp = U.S.separate(t, t.length() / P.C.TOKEN_LENGHT);
			if (U.E.isNotEmpty(temp) && temp.length == 2) {
				// 验证串
				String ver = temp[0];
				// 信息串
				String user = temp[1];
				// 判断校验串是否合法
				if (ver.equals(Digest.absolute(user, P.C.TOKEN_LENGHT)))
					return Decrypts.rc4(Hex.decode(user));
			}
		}
		return C.A.BYTES_EMPTY;
	}

	/**
	 * 解密字符串
	 * 
	 * @param  text 要解密的字符串
	 * @return      解密后的字符串
	 */
	public static String decryptString(String text) {
		return U.S.toString(decrypt(text));
	}

	/**
	 * 解密字符串
	 * 
	 * @param  text 要解密的字符串
	 * @return      解密后的字符串
	 */
	public static byte[] decrypt(String text) {
		return decrypt(Hex.decode(text));
	}

	/**
	 * 解密字符串
	 * 
	 * @param  b 要解密的字节数组
	 * @return   解密后的字节数组
	 */
	public static byte[] decrypt(byte[] b) {
		return decrypt(b, P.C.ENCRYPT_KEY);
	}

	/**
	 * 解密字符串
	 * 
	 * @param  b    要解密的字节数组
	 * @param  keys 键
	 * @return      解密后的字节数组
	 */
	public static byte[] decrypt(byte[] b, String keys) {
		// 判断解密方式
		switch (P.C.ENCRYPT_ALGO) {
			case C.E.ALGO_AES:
				// AES解密
				return aes(b, keys);
			case C.E.ALGO_DES:
				// DES解密
				return des(b, keys);
			case C.E.ALGO_RC4:
				// RC4解密
				return rc4(b, keys);
			default:
				// 默认返回AES
				return aes(b, keys);
		}
	}

	/**
	 * 针对encode方法的解密 DES算法
	 * 
	 * @param  b 需要解密的字节数组
	 * @return   返回解密后的字符串
	 */
	public static byte[] des(byte[] b) {
		return des(b, P.C.ENCRYPT_KEY);
	}

	/**
	 * 针对encode方法的解密 DES算法
	 * 
	 * @param  b    需要解密的字节数组
	 * @param  keys 键
	 * @return      返回解密后的字符串
	 */
	public static byte[] des(byte[] b, String keys) {
		return decrypt(b, keys, C.E.LENGTH_DES, C.E.ALGO_DES);
	}

	/**
	 * 针对encrypt方法的解密 AES算法
	 * 
	 * @param  b 需要解密的字节数组
	 * @return   返回解密后的字符串 text为空或发生异常返回原串
	 */
	public static byte[] aes(byte[] b) {
		return aes(b, P.C.ENCRYPT_KEY);
	}

	/**
	 * 针对encrypt方法的解密 AES算法
	 * 
	 * @param  b    需要解密的字节数组
	 * @param  keys 键
	 * @return      返回解密后的字符串 text为空或发生异常返回原串
	 */
	public static byte[] aes(byte[] b, String keys) {
		return decrypt(b, keys, C.E.LENGTH_AES, C.E.ALGO_AES);
	}

	/**
	 * 针对encrypt方法的解密 RC4算法
	 * 
	 * @param  b 需要解密的字节数组
	 * @return   返回解密后的字符串 text为空或发生异常返回原串
	 */
	public static byte[] rc4(byte[] b) {
		return rc4(b, P.C.ENCRYPT_KEY);
	}

	/**
	 * 针对encrypt方法的解密 RC4算法
	 * 
	 * @param  b    需要解密的字节数组
	 * @param  keys 键
	 * @return      返回解密后的字符串 text为空或发生异常返回原串
	 */
	public static byte[] rc4(byte[] b, String keys) {
		return decrypt(b, keys, C.E.LENGTH_RC4, C.E.ALGO_RC4);
	}

	/**
	 * 解密字符串
	 * 
	 * @param  text      要解密的字符串
	 * @param  key       解密密钥Key 长度有限制 DSE 为8位 ASE 为16位
	 * @param  keys      键
	 * @param  algorithm 算法
	 * @return           解密后的字符串
	 */
	private static byte[] decrypt(byte[] b, String keys, int len, String algorithm) {
		return doFinal(b, keys, len, algorithm, Cipher.DECRYPT_MODE);
	}
}
