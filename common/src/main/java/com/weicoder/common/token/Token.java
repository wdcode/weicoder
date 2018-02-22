package com.weicoder.common.token;

import com.weicoder.common.binary.ByteArray;
import com.weicoder.common.lang.Bytes;
import com.weicoder.common.util.DateUtil;
import com.weicoder.common.util.EmptyUtil;
import com.weicoder.common.util.IpUtil;

/**
 * 登录信息封装
 * @author WD
 */
public final class Token implements ByteArray {
	// 用户ID
	protected long		id;
	// 过期时间 固定时间戳秒
	protected int		time;
	// 登录IP
	protected String	ip;
	// 保存token
	private String		token;

	Token() {}

	/**
	 * 构造方法
	 * @param token token串
	 */
	Token(String token) {
		this(TokenEngine.decrypt(token).array());
	}

	/**
	 * 构造方法
	 * @param array 字节数组
	 */
	Token(byte[] array) {
		array(array);
	}

	/**
	 * 构造方法
	 * @param id 登录用户ID
	 * @param ip 登录IP
	 * @param time 有效时间 当前时间戳加上time 单位秒
	 */
	Token(long id, String ip, int time) {
		this.id = id;
		this.time = DateUtil.getTime() + time;
		this.ip = ip;
		this.token = TokenEngine.encrypt(this);
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
	public long getId() {
		return id;
	}

	/**
	 * 获得Token加密串
	 * @return 登录时间
	 */
	public String getToken() {
		return token;
	}

	@Override
	public String toString() {
		return token;
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