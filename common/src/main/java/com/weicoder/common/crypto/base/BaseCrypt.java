package com.weicoder.common.crypto.base;

import java.security.Key;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import com.weicoder.common.constants.ArrayConstants;
import com.weicoder.common.lang.Maps;
import com.weicoder.common.util.StringUtil;

/**
 * 加密解密基础类 内部使用
 * @author WD
 * @since JDK7
 * @version 1.0 2010-06-23
 */
public abstract class BaseCrypt {
	// 加密算法
	private final static Map<String, Key>	KEYS	= Maps.getConcurrentMap();

	/**
	 * 计算密文
	 * @param b 要计算的字节数组
	 * @param key 计算密钥Key 长度有限制 DSE 为8位 ASE 为16位
	 * @param offset 偏移从第几位开始
	 * @param len 长度一共几位
	 * @param algorithm 算法
	 * @param mode 计算模式 加密和解密
	 * @return 字节数组
	 */
	protected final static byte[] doFinal(byte[] b, String keys, int len, String algorithm, int mode) {
		return doFinal(b, getKey(algorithm, keys, len), algorithm, mode);
	}

	/**
	 * 计算密文
	 * @param b 要计算的字节数组
	 * @param key 计算密钥Key 长度有限制 DSE 为8位 ASE 为16位
	 * @param offset 偏移从第几位开始
	 * @param len 长度一共几位
	 * @param algorithm 算法
	 * @param mode 计算模式 加密和解密
	 * @return 字节数组
	 */
	protected final static byte[] doFinal(byte[] b, Key key, String algorithm, int mode) {
		try {
			// 算法操作
			Cipher cipher = Cipher.getInstance(algorithm);
			// 初始化
			cipher.init(mode, key);
			// 返回计算结果
			return cipher.doFinal(b);
		} catch (Exception e) {
			// 返回空字节数组
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
		// 获得Key
		Key key = KEYS.get(algorithm + keys);
		// 如果key为空
		if (key == null) {
			// 把键转换程字节数组
			byte[] b = StringUtil.toBytes(StringUtil.resolve(keys, len));
			// 添加到列表中
			KEYS.put(algorithm + keys, key = new SecretKeySpec(b, 0, len, algorithm));
		}
		// 返回key
		return key;
	}
}
