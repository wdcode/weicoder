package com.weicoder.common.token;

/**
 * Token令牌处理器
 * @author WD
 */
public final class TokenEngine {
	/** 空登录信息 */
	public final static TokenBean EMPTY = new TokenBean();

	/**
	 * 加密信息
	 * @param id 用户ID
	 * @param ip 用户IP
	 * @param time 有效时间 当前时间戳加上time 单位秒
	 * @return Token
	 */
	public static TokenBean newToken(long id, String ip, int time) {
		return new TokenBean(id, ip, time);
	}

	/**
	 * 加密信息
	 * @param id 用户ID
	 * @param ip 用户IP
	 * @param time 有效时间 当前时间戳加上time 单位秒
	 * @return 加密token字符串
	 */
	public static String encrypt(long id, String ip, int time) {
		return newToken(id, ip, time).getToken();
	}

	/**
	 * 验证登录凭证
	 * @param info 登陆信息
	 * @return 登录实体
	 */
	public static TokenBean decrypt(String info) {
		return new TokenBean(info);
	}

	private TokenEngine() {}
}