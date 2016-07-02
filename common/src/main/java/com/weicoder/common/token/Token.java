package com.weicoder.common.token;

import com.weicoder.common.binary.ByteArray;
import com.weicoder.common.lang.Bytes;
import com.weicoder.common.params.CommonParams;
import com.weicoder.common.util.DateUtil;
import com.weicoder.common.util.EmptyUtil;
import com.weicoder.common.util.IpUtil;
import com.weicoder.common.util.StringUtil;

/**
 * 登录信息封装
 * @author WD
 */
public class Token implements ByteArray {
	// 用户ID
	protected long		id;
	// 登录时间
	protected int		time;
	// 登录IP
	protected String	ip;

	public Token() {}

	/**
	 * 构造方法
	 * @param id 登录用户ID
	 * @param ip 登录IP
	 * @param serverIp 服务器IP
	 */
	public Token(long id, String ip) {
		this.id = id;
		this.time = DateUtil.getTime();
		this.ip = ip;
	}

	/**
	 * 是否登录 不验证是IP和登录时间
	 * @return true 登录 false 未登录
	 */
	public boolean isLogin() {
		return id != 0 && (CommonParams.LOGIN_MAX_AGE > 0 ? DateUtil.getTime() - time < CommonParams.LOGIN_MAX_AGE : time > 0);
	}

	/**
	 * 获得用户ID
	 * @return 用户ID
	 */
	public long getId() {
		return id;
	}

	/**
	 * 获得登录时间
	 * @return 登录时间
	 */
	public int getTime() {
		return time;
	}

	/**
	 * 获得登录IP
	 * @return IP
	 */
	public String getIp() {
		return ip;
	}

	@Override
	public String toString() {
		return StringUtil.add("id=", id, ";time=", time, ";ip=", ip);
	}

	@Override
	public byte[] array() {
		return Bytes.toBytes(id, time, IpUtil.encode(ip));
	}

	@Override
	public Token array(byte[] b) {
		// 判断字节数组不为空
		if (!EmptyUtil.isEmpty(b)) {
			this.id = Bytes.toLong(b);
			this.time = Bytes.toInt(b, 8);
			this.ip = IpUtil.decode(Bytes.toInt(b, 12));
		}
		// 返回自身
		return this;
	}
}