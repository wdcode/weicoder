package com.weicoder.ssh.token;

import com.weicoder.ssh.entity.EntityUser;
import com.weicoder.ssh.params.SiteParams; 
import com.weicoder.common.util.DateUtil;
import com.weicoder.json.JsonEngine;

/**
 * 登录信息封装
 * 
 * @author WD
 * @version 1.0
 */
public class LoginToken {
	// 用户ID
	protected long		id;
	// 登录时间
	protected int		time;
	// 登录IP
	protected String	loginIp;

	public LoginToken() {
	}

	/**
	 * 构造方法
	 * 
	 * @param id       登录用户ID
	 * @param name     用户名
	 * @param loginIp  登录IP
	 * @param serverIp 服务器IP
	 */
	public LoginToken(EntityUser login, String loginIp, String serverIp) {
		this(login.getId(), loginIp);
	}

	/**
	 * 构造方法
	 * 
	 * @param id       登录用户ID
	 * @param name     用户名
	 * @param loginIp  登录IP
	 * @param serverIp 服务器IP
	 */
	public LoginToken(long id, String loginIp) {
		this.id = id;
		this.time = DateUtil.getTime();
		this.loginIp = loginIp;
	}
 
	public boolean isLogin() {
		return id != 0 && (SiteParams.LOGIN_MAX_AGE > 0 ? DateUtil.getTime() - time < SiteParams.LOGIN_MAX_AGE : time > 0);
	}
 
	public boolean isUser() {
		return id > 0;
	}
 
	public boolean isGuest() {
		return id < 0;
	}
 
	public long getId() {
		return id;
	}
 
	public int getTime() {
		return time;
	}
 
	public String getLoginIp() {
		return loginIp;
	}

	@Override
	public String toString() {
		return JsonEngine.toJson(this);
	}
}