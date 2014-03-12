package com.weicoder.site.token;

import com.weicoder.base.entity.EntityUser;
import com.weicoder.base.token.AuthToken;
import com.weicoder.common.lang.Bytes;
import com.weicoder.common.util.DateUtil;
import com.weicoder.common.util.EmptyUtil;
import com.weicoder.core.json.JsonEngine;
import com.weicoder.site.params.SiteParams;
import com.weicoder.web.util.IpUtil;

/**
 * 登录信息封装
 * @author WD
 * @since JDK7
 * @version 1.0 2010-11-29
 */
public class LoginToken implements AuthToken {
	// 用户ID
	protected int		id;
	// 登录时间
	protected int		time;
	// 登录IP
	protected String	loginIp;
	// 服务器IP
	protected String	serverIp;

	/**
	 * 构造方法
	 */
	public LoginToken() {}

	/**
	 * 构造方法
	 * @param id 登录用户ID
	 * @param name 用户名
	 * @param loginIp 登录IP
	 * @param serverIp 服务器IP
	 */
	public LoginToken(EntityUser login, String loginIp, String serverIp) {
		this(login.getId(), loginIp, serverIp);
	}

	/**
	 * 构造方法
	 * @param id 登录用户ID
	 * @param name 用户名
	 * @param loginIp 登录IP
	 * @param serverIp 服务器IP
	 */
	public LoginToken(int id, String loginIp, String serverIp) {
		this.id = id;
		this.time = DateUtil.getTime();
		this.loginIp = loginIp;
		this.serverIp = serverIp;
	}

	@Override
	public boolean isLogin() {
		return id != 0 && (SiteParams.LOGIN_MAX_AGE > 0 ? DateUtil.getTime() - time < SiteParams.LOGIN_MAX_AGE : time > 0);
	}

	@Override
	public boolean isUser() {
		return id > 0;
	}

	@Override
	public boolean isGuest() {
		return id < 0;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public int getTime() {
		return time;
	}

	@Override
	public String getLoginIp() {
		return loginIp;
	}

	@Override
	public String getServerIp() {
		return serverIp;
	}

	@Override
	public String toString() {
		return JsonEngine.toJson(this);
	}

	@Override
	public byte[] array() {
		return Bytes.toBytes(id, time, IpUtil.encode(loginIp), IpUtil.encode(serverIp));
	}

	@Override
	public LoginToken array(byte[] b) {
		// 判断字节数组不为空
		if (!EmptyUtil.isEmpty(b)) {
			this.id = Bytes.toInt(b);
			this.time = Bytes.toInt(b, 4);
			this.loginIp = IpUtil.decode(Bytes.toInt(b, 8));
			this.serverIp = IpUtil.decode(Bytes.toInt(b, 12));
		}
		// 返回自身
		return this;
	}
}