package com.weicoder.common.token;

import com.weicoder.common.crypto.Decrypts;
import com.weicoder.common.crypto.Encrypts;

/**
 * Token令牌处理器
 * @author WD
 */
public final class TokenEngine {
	/**
	 * 加密信息
	 * @param id 用户ID
	 * @param ip 用户IP
	 * @param time 有效时间 当前时间戳加上time 单位秒
	 * @return 加密token字符串
	 */
	public static String encrypt(int id, String ip, int time) {
		return encrypt(new Token(id, ip, time));
	}

	/**
	 * 加密信息
	 * @param token 登录凭证
	 * @return 加密信息
	 */
	public static String encrypt(Token token) {
		return Encrypts.token(token.array());
	}

	/**
	 * 验证登录凭证
	 * @param info 登陆信息
	 * @return 登录实体
	 */
	public static Token decrypt(String info) {
		return new Token(Decrypts.token(info));
	}

	private TokenEngine() {}
}