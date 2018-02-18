package com.weicoder.common.token;

import com.weicoder.common.binary.ByteArray;
import com.weicoder.common.lang.Bytes;
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
	protected int		id;
	// 过期时间 固定时间戳秒
	protected int		time;
	// 登录IP
	protected String	ip;

	public Token() {}

	public Token(byte[] array) {
		array(array);
	}

	/**
	 * 构造方法
	 * @param id 登录用户ID
	 * @param ip 登录IP
	 * @param time 有效时间 当前时间戳加上time 单位秒
	 */
	public Token(int id, String ip, int time) {
		this.id = id;
		this.time = DateUtil.getTime() + time;
		this.ip = ip;
	}

	/**
	 * 是否登录 不验证是IP和登录时间
	 * @return true 登录 false 未登录
	 */
	public boolean isLogin() {
		return id != 0 && (time - DateUtil.getTime() > 0);
	}

	/**
	 * 获得用户ID
	 * @return 用户ID
	 */
	public int getId() {
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
			this.id = Bytes.toInt(b);
			this.time = Bytes.toInt(b, 4);
			this.ip = IpUtil.decode(Bytes.toInt(b, 8));
		}
		// 返回自身
		return this;
	}
}