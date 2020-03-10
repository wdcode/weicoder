package com.weicoder.admin.token;

import java.util.Arrays;

import com.weicoder.common.lang.Bytes; 
import com.weicoder.common.W;
import com.weicoder.common.token.TokenEngine;

/**
 * 管理员Token相关
 * 
 * @author wudi
 */
public final class AdminToken {
	/**
	 * 加密信息
	 * 
	 * @param  name 管理员用户名
	 * @param  ip   用户IP
	 * @return      加密token字符串
	 */
	public static String encrypt(String name, String ip) {
		return TokenEngine.encrypt(Bytes.toLong(Arrays.copyOf(Bytes.toBytes(name), 8)), ip);
	}

	/**
	 * 验证登录凭证
	 * 
	 * @param  info 登陆信息
	 * @return      管理员名
	 */
	public static String decrypt(String info) {
		return W.C.toString(Bytes.toString(Bytes.toBytes(TokenEngine.decrypt(info).getId())));
	}

	private AdminToken() {
	}
}