package com.weicoder.common.crypto.base;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import com.weicoder.common.constants.ArrayConstants;
import com.weicoder.common.util.EmptyUtil;
import com.weicoder.common.util.StringUtil;

/**
 * 加密解密基础类 内部使用
 * @author WD
 */
public abstract class BaseCrypt {
	/**
	 * 计算密文
	 * @param b 要计算的字节数组
	 * @param keys 计算密钥Key 长度有限制 DSE 为8位 ASE 为16位
	 * @param len 长度一共几位
	 * @param algorithm 算法
	 * @param mode 计算模式 加密和解密
	 * @return 字节数组
	 */
	protected final static byte[] doFinal(byte[] b, String keys, int len, String algorithm,
			int mode) {
		return doFinal(b, getKey(algorithm, keys, len), algorithm, mode);
	}

	/**
	 * 计算密文
	 * @param b 要计算的字节数组
	 * @param key 计算密钥Key 长度有限制 DSE 为8位 ASE 为16位
	 * @param algorithm 算法
	 * @param mode 计算模式 加密和解密
	 * @return 字节数组
	 */
	protected final static byte[] doFinal(byte[] b, Key key, String algorithm, int mode) {
		try {
			// 字节为空自动返回
			if (EmptyUtil.isEmpty(b))
				return b;
			// 算法操作
			Cipher cipher = Cipher.getInstance(algorithm);
			// 初始化
			cipher.init(mode, key);
			// 返回计算结果
			return cipher.doFinal(b);
		} catch (Exception e) {
			return ArrayConstants.BYTES_EMPTY;
		}
	}

	/**
	 * 根据算法和key字符串获得Key对象
	 * @param algorithm 算法
	 * @param keys 键
	 * @param len 键长度
	 * @return Key
	 */
	private static Key getKey(String algorithm, String keys, int len) {
		return new SecretKeySpec(StringUtil.toBytes(StringUtil.resolve(keys, len)), 0, len,
				algorithm);
	}
}
