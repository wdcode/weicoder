package com.weicoder.common.util;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.Map;

import com.weicoder.common.lang.Maps;
import com.weicoder.common.params.CommonParams;

/**
 * 生成密钥对用
 * @author WD
 * @since JDK7
 * @version 1.0 2011-06-18
 */
public final class KeyUtil {
	// 密钥对
	private final static Map<String, KeyPair>	KEYPAIRS;

	/**
	 * 静态初始化
	 */
	static {
		KEYPAIRS = Maps.getConcurrentMap();
	}

	/**
	 * 获得密钥对 里面包含 公钥 密钥
	 * @param algorithm 加密算法
	 * @return 密钥对
	 */
	public static KeyPair getKeyPair(String algorithm) {
		// 获得KeyPair
		KeyPair pair = KEYPAIRS.get(algorithm);
		// 如果键为空
		if (pair == null) {
			KEYPAIRS.put(algorithm, pair = getKeyPair(algorithm, CommonParams.ENCRYPT_KEY_LENGTH, CommonParams.ENCRYPT_KEY));
		}
		// 返回
		return pair;
	}

	/**
	 * 获得默认公钥
	 * @param algorithm 加密算法
	 * @return 公钥
	 */
	public static PublicKey getPublicKey(String algorithm) {
		return getKeyPair(algorithm).getPublic();
	}

	/**
	 * 获得默认私钥
	 * @param algorithm 加密算法
	 * @return 私钥
	 */
	public static PrivateKey getPrivateKey(String algorithm) {
		return getKeyPair(algorithm).getPrivate();
	}

	/**
	 * 获得密钥对 里面包含 公钥 密钥
	 * @param algorithm 加密算法
	 * @param size 密钥大小
	 * @param key 生成密钥的Key
	 * @return 密钥对
	 */
	private static KeyPair getKeyPair(String algorithm, int keysize, String key) {
		try {
			// 生成KeyPaire
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(algorithm);
			// 初始化大小
			keyPairGenerator.initialize(keysize, new SecureRandom(StringUtil.toBytes(key)));
			// 返回密钥对
			return keyPairGenerator.genKeyPair();
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 私有构造
	 */
	private KeyUtil() {}
}
