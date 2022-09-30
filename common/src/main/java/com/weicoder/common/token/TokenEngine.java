package com.weicoder.common.token;

import com.weicoder.common.params.P;

/**
 * Token令牌处理器
 * 
 * @author WD
 */
public class TokenEngine {
	/** 空登录信息 */
	public final static TokenBean EMPTY = new TokenBean();

	/**
	 * 加密信息
	 * 
	 * @param  id   用户ID
	 * @param  ip   用户IP
	 * @param  time 有效时间 当前时间戳加上time 单位秒
	 * @return      Token
	 */
	public static TokenBean newToken(long id, String ip) {
		return newToken(id, ip, P.C.TOKEN_EXPIRE);
	}

	/**
	 * 加密信息
	 * 
	 * @param  id   用户ID
	 * @param  ip   用户IP
	 * @param  time 有效时间 当前时间戳加上time 单位秒
	 * @return      Token
	 */
	public static TokenBean newToken(long id, String ip, int time) {
		return newToken(id, ip, time, false);
	}

	/**
	 * 加密信息
	 * 
	 * @param  id   用户ID
	 * @param  ip   用户IP
	 * @param  time 有效时间 当前时间戳加上time 单位秒
	 * @param  ban  是否被禁用
	 * @return      Token
	 */
	public static TokenBean newToken(long id, String ip, int time, boolean ban) {
		return new TokenBean(id, ip, time, ban, Byte.MAX_VALUE);
	}

	/**
	 * 加密信息
	 * 
	 * @param  id 用户ID
	 * @param  ip 用户IP
	 * @return    加密token字符串
	 */
	public static String encrypt(long id, String ip) {
		return newToken(id, ip).getToken();
	}

	/**
	 * 加密信息
	 * 
	 * @param  id   用户ID
	 * @param  ip   用户IP
	 * @param  time 有效时间 当前时间戳加上time 单位秒
	 * @return      加密token字符串
	 */
	public static String encrypt(long id, String ip, int time) {
		return newToken(id, ip, time).getToken();
	}

	/**
	 * 加密信息
	 * 
	 * @param  id   用户ID
	 * @param  ip   用户IP
	 * @param  time 有效时间 当前时间戳加上time 单位秒
	 * @return      加密token字符串
	 */
	public static String encrypt(long id, String ip, int time, boolean ban) {
		return newToken(id, ip, time, ban).getToken();
	}

	/**
	 * 验证登录凭证
	 * 
	 * @param  info 登陆信息
	 * @return      登录实体
	 */
	public static TokenBean decrypt(String info) {
		return new TokenBean(info);
	}
}