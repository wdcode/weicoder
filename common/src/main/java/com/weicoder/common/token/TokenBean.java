package com.weicoder.common.token;

import java.util.Arrays;

import com.weicoder.common.binary.ByteArray;
import com.weicoder.common.crypto.Decrypts;
import com.weicoder.common.crypto.Encrypts;
import com.weicoder.common.lang.Bytes;
import com.weicoder.common.log.Logs;
import com.weicoder.common.util.DateUtil;
import com.weicoder.common.util.EmptyUtil;
import com.weicoder.common.util.IpUtil;

/**
 * 登录信息封装
 * @author WD
 */
public final class TokenBean implements ByteArray {
	// 用户ID
	private long	id;
	// 过期时间 固定时间戳秒
	private int		time;
	// 登录IP
	private String	ip;
	// // 标志
	// private byte sign = CommonParams.TOKEN_SIGN;
	// // 补位
	// private byte sss;
	// 是否有效
	private boolean	valid;
	// 保存token
	private String	token;

	TokenBean() {}

	/**
	 * 构造方法
	 * @param token token串
	 */
	TokenBean(String token) {
		this.token = token;
		array(Decrypts.token(token));
	}

	/**
	 * 构造方法
	 * @param id 登录用户ID
	 * @param ip 登录IP
	 * @param time 有效时间 当前时间戳加上time 单位秒
	 */
	TokenBean(long id, String ip, int time) {
		this.id = id;
		this.time = DateUtil.getTime() + time;
		this.ip = ip;
		this.token = Encrypts.token(array());
		this.valid = true;
	}

	public String getDate() {
		return DateUtil.toString(time);
	}

	/**
	 * 是否登录 不验证是IP和登录时间
	 * @return true 登录 false 未登录
	 */
	public boolean isLogin() {
		return id != 0 && !isExpire() && isValid();// && isSign();
	}

	// /**
	// * 判断Token标示是否正确
	// * @return
	// */
	// public boolean isSign() {
	// return sign == CommonParams.TOKEN_SIGN;
	// }

	/**
	 * 判断Token是否无效
	 * @return true 有效 false 无效
	 */
	public boolean isValid() {
		return valid;
	}

	/**
	 * 是否有效期
	 * @return true 到期 false 有效
	 */
	public boolean isExpire() {
		return time - DateUtil.getTime() < 0;
	}

	/**
	 * 获得用户ID
	 * @return 用户ID
	 */
	public long getId() {
		return id;
	}

	/**
	 * 获得过期时间
	 * @return 过期时间
	 */
	public int getTime() {
		return time;
	}

	/**
	 * 获得登录IP
	 * @return 登录IP
	 */
	public int getIp() {
		return time;
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
		return Bytes.toBytes(id, time, IpUtil.encode(ip));// , sign, sss);
	}

	@Override
	public TokenBean array(byte[] b) {
		// 判断字节数组不为空
		if (EmptyUtil.isNotEmpty(b)) {
			this.id = Bytes.toLong(b);
			this.time = Bytes.toInt(b, 8);
			this.ip = IpUtil.decode(Bytes.toInt(b, 12));
			// this.sign = Bytes.toByte(b, 16);
			// this.sss = Bytes.toByte(b, 17);
			// this.valid = sign == CommonParams.TOKEN_SIGN;
			this.valid = true;
		} else {
			Logs.debug("token decrypt fail data={} token={}", Arrays.toString(b), token);
		}
		// 返回自身
		return this;
	}
}