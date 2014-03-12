package com.weicoder.site.engine;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.weicoder.base.entity.EntityUser;
import com.weicoder.base.token.AuthToken;
import com.weicoder.base.token.TokenEngine;
import com.weicoder.common.lang.Conversion;
import com.weicoder.common.util.EmptyUtil;
import com.weicoder.site.params.SiteParams;
import com.weicoder.site.token.LoginToken;
import com.weicoder.web.util.AttributeUtil;
import com.weicoder.web.util.IpUtil;

/**
 * 登录信息Bean
 * @author WD
 * @since JDK7
 * @version 1.0 2012-07-20
 */
public final class LoginEngine {
	// 空登录信息
	private final static LoginToken	EMPTY		= new LoginToken();
	// 登录信息标识
	private final static String		INFO		= "_info";
	// 游客IP
	private static int				GUEST_ID	= SiteParams.LOGIN_GUEST_ID;

	/**
	 * 是否登录
	 * @param request HttpServletRequest
	 * @param key 登录标识
	 * @return true 登录 false 未登录
	 */
	public static boolean isLogin(HttpServletRequest request, String key) {
		return getLoginBean(request, key).isLogin();
	}

	/**
	 * 添加登录信息
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @param login 登录实体
	 * @param maxAge 保存时间
	 */
	public static LoginToken addLogin(HttpServletRequest request, HttpServletResponse response, EntityUser login, int maxAge) {
		// 获得登录token实体
		LoginToken token = new LoginToken(login, IpUtil.getIp(request), IpUtil.getIp());
		// 返回token
		return setToken(request, response, login.getClass().getSimpleName(), token, maxAge);
	}

	/**
	 * 设置登录凭证
	 * @param request
	 * @param response
	 * @param key
	 * @param token
	 * @param maxAge
	 * @return
	 */
	public static LoginToken setToken(HttpServletRequest request, HttpServletResponse response, String key, LoginToken token, int maxAge) {
		// 保存登录信息
		AttributeUtil.set(request, response, key + INFO, encrypt(token), maxAge);
		// 返回token
		return token;
	}

	/**
	 * 获得用户信息
	 * @param request HttpServletRequest
	 * @param key 登录标识
	 * @return 用户信息
	 */
	public static LoginToken getLoginBean(HttpServletRequest request, String key) {
		// 读取用户信息
		String info = Conversion.toString(AttributeUtil.get(request, key + INFO));
		// 如果用户信息为空
		if (EmptyUtil.isEmpty(info)) {
			return EMPTY;
		} else {
			return decrypt(info);
		}
	}

	/**
	 * 移除登录信息
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @param key 登录标识
	 */
	public static void removeLogin(HttpServletRequest request, HttpServletResponse response, String key) {
		// 写入用户信息
		AttributeUtil.remove(request, response, key + INFO);
		// 销毁用户session
		// SessionUtil.close(request.getSession());
	}

	/**
	 * 加密信息
	 * @param token 登录凭证
	 * @return 加密信息
	 */
	public static String encrypt(AuthToken token) {
		return TokenEngine.encrypt(token);
	}

	/**
	 * 验证登录凭证
	 * @return 登录实体
	 */
	public static LoginToken decrypt(String info) {
		return TokenEngine.decrypt(info, new LoginToken());
	}

	/**
	 * 获得一样空登录信息
	 * @return
	 */
	public static LoginToken guest(HttpServletRequest request, HttpServletResponse response, String key) {
		// 如果游客ID已经分配到最大值 把游客ID重置
		if (GUEST_ID == Integer.MIN_VALUE) {
			GUEST_ID = 0;
		}
		// 获得游客凭证
		LoginToken token = new LoginToken(GUEST_ID--, IpUtil.getIp(request), IpUtil.getIp());
		// 设置游客凭证
		AttributeUtil.set(request, response, key + INFO, encrypt(token), -1);
		// 返回游客凭证
		return token;
	}

	/**
	 * 获得一样空登录信息
	 * @return
	 */
	public static LoginToken empty() {
		return EMPTY;
	}

	/**
	 * 私有构造
	 */
	private LoginEngine() {}
}