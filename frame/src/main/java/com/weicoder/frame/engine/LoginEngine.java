package com.weicoder.frame.engine;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.weicoder.frame.entity.EntityUser;
import com.weicoder.frame.params.SiteParams;
import com.weicoder.web.util.AttributeUtil;
import com.weicoder.web.util.RequestUtil;
import com.weicoder.common.lang.W;
import com.weicoder.common.token.TokenBean;
import com.weicoder.common.token.TokenEngine;
import com.weicoder.common.util.U;

/**
 * 登录信息Bean
 * @author WD
 * 
 * @version 1.0 2012-07-20
 */
public final class LoginEngine {
	// 登录信息标识
	private final static String	INFO		= "_info";
	// 游客IP
	private static int			GUEST_ID	= SiteParams.LOGIN_GUEST_ID;

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
	 * @return TokenBean
	 */
	public static TokenBean addLogin(HttpServletRequest request, HttpServletResponse response, EntityUser login,
			int maxAge) {
		return setToken(request, response, login.getClass().getSimpleName(),
				getLogin(login.getId(), RequestUtil.getIp(request)), maxAge);
	}

	/**
	 * 活动登录信息
	 * @param id 用户ID
	 * @param ip 用户IP
	 * @return 获得登录状态
	 */
	public static TokenBean getLogin(int id, String ip) {
		return TokenEngine.newToken(id, ip, SiteParams.LOGIN_MAX_AGE);
	}

	/**
	 * 设置登录凭证
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @param key 登录标识
	 * @param token 登录实体
	 * @param maxAge 保存时间
	 * @return TokenBean
	 */
	public static TokenBean setToken(HttpServletRequest request, HttpServletResponse response, String key,
			TokenBean token, int maxAge) {
		// 保存登录信息
		AttributeUtil.set(request, response, key + INFO, token.getToken(), maxAge);
		// 返回token
		return token;
	}

	/**
	 * 获得用户信息
	 * @param request HttpServletRequest
	 * @param key 登录标识
	 * @return 用户信息
	 */
	public static TokenBean getLoginBean(HttpServletRequest request, String key) {
		// 读取用户信息
		String info = W.C.toString(AttributeUtil.get(request, key + INFO));
		// 如果用户信息为空
		if (U.E.isEmpty(info)) {
			return empty();
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
	 * @param id 用户ID
	 * @param ip 用户IP
	 * @return 加密信息
	 */
	public static String encrypt(int id, String ip) {
		return getLogin(id, ip).getToken();
	}

	/**
	 * 验证登录凭证
	 * @param info 实体信息
	 * @return 登录实体
	 */
	public static TokenBean decrypt(String info) {
		return TokenEngine.decrypt(info);
	}

	/**
	 * 获得一样空登录信息
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @param key Key
	 * @return TokenBean
	 */
	public static TokenBean guest(HttpServletRequest request, HttpServletResponse response, String key) {
		// 如果游客ID已经分配到最大值 把游客ID重置
		if (GUEST_ID == Integer.MIN_VALUE) {
			GUEST_ID = 0;
		}
		// 获得游客凭证
		TokenBean token = TokenEngine.newToken(GUEST_ID--, RequestUtil.getIp(request), SiteParams.LOGIN_MAX_AGE);
		// 设置游客凭证
		AttributeUtil.set(request, response, key + INFO, token.getToken(), -1);
		// 返回游客凭证
		return token;
	}

	/**
	 * 获得一样空登录信息
	 * @param ip 客户端ip
	 * @return TokenBean
	 */
	public static TokenBean guest(String ip) {
		// 如果游客ID已经分配到最大值 把游客ID重置
		if (GUEST_ID == Integer.MIN_VALUE) {
			GUEST_ID = 0;
		}
		// 返回游客凭证
		return TokenEngine.newToken(GUEST_ID--, ip, SiteParams.LOGIN_MAX_AGE);
	}

	/**
	 * 获得一样空登录信息
	 * @return TokenBean
	 */
	public static TokenBean empty() {
		return TokenEngine.EMPTY;
	}

	/**
	 * 私有构造
	 */
	private LoginEngine() {}
}