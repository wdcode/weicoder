package com.weicoder.common.token;

import java.util.Set;

import com.weicoder.common.binary.ByteArray;
import com.weicoder.common.crypto.Decrypts;
import com.weicoder.common.crypto.Encrypts;
import com.weicoder.common.lang.Bytes;
import com.weicoder.common.params.CommonParams;
import com.weicoder.common.util.DateUtil;
import com.weicoder.common.U;
import com.weicoder.common.util.IpUtil;

/**
 * 登录信息封装
 * 
 * @author WD
 */
public final class TokenBean implements ByteArray {
	// 用户ID
	private long id;
	// 过期时间 固定时间戳秒
	private int time;
	// 登录IP
	private String ip;
	// 服务器IP
	private String server;
	// 是否被禁止
	private boolean ban;
	// 空位
	private byte b;
	// 标志
	private short sign;
	// 是否有效
	private boolean valid;
	// 保存token
	private String token;

	TokenBean() {
	}

	/**
	 * 构造方法
	 * 
	 * @param token token串
	 */
	TokenBean(String token) {
		this.token = token;
		array(Decrypts.token(token));
	}

	/**
	 * 构造方法
	 * 
	 * @param id   登录用户ID
	 * @param ip   登录IP
	 * @param time 有效时间 当前时间戳加上time 单位秒
	 */
	TokenBean(long id, String ip, int time, boolean ban, byte b) {
		this.id = id;
		this.time = DateUtil.getTime() + time;
		this.ip = ip;
		this.server = IpUtil.SERVER_IP;
		this.ban = ban;
		this.b = b;
		this.sign = CommonParams.TOKEN_SIGN;
		this.token = Encrypts.token(array());
		this.valid = true;
	}

	public String getDate() {
		return DateUtil.toString(time);
	}

	/**
	 * 是否登录 不验证是IP和登录时间
	 * 
	 * @return true 登录 false 未登录
	 */
	public boolean isLogin() {
		return id != 0 && !isExpire() && isValid() && isSign() && !isBan();
	}

	/**
	 * 判断Token标示是否正确
	 * 
	 * @return 是否正确
	 */
	public boolean isSign() {
		return this.sign == CommonParams.TOKEN_SIGN;// isSign(CommonParams.TOKEN_SIGN);
	}

	/**
	 * 是否被禁用 true 禁用 false 正常
	 * 
	 * @return
	 */
	public boolean isBan() {
		return ban;
	}

	/**
	 * 验证是否服务器发放token
	 * 
	 * @return 是否存在
	 */
	public boolean isServer() {
		return CommonParams.TOKEN_SERVERS.contains(server); // isServer(CommonParams.TOKEN_SERVERS);
	}

	/**
	 * 验证是否服务器发放token
	 * 
	 * @param  servers 服务器列表
	 * @return         是否存在
	 */
	public boolean isServer(Set<String> servers) {
		return servers.contains(server);
	}

//	/**
//	 * 判断Token标示是否正确
//	 * @param sign 验证标签
//	 * @return 是否正确
//	 */
//	public boolean isSign(int sign) {
//		return this.sign == sign;
//	}

	/**
	 * 判断Token是否无效
	 * 
	 * @return true 有效 false 无效
	 */
	public boolean isValid() {
		return valid;
	}

	/**
	 * 是否有效期
	 * 
	 * @return true 到期 false 有效
	 */
	public boolean isExpire() {
		return time - DateUtil.getTime() < 0;
	}

	/**
	 * 获得用户ID
	 * 
	 * @return 用户ID
	 */
	public long getId() {
		return id;
	}

	/**
	 * 获得过期时间
	 * 
	 * @return 过期时间
	 */
	public int getTime() {
		return time;
	}

	/**
	 * 获得登录IP
	 * 
	 * @return 登录IP
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * 获得Token加密串
	 * 
	 * @return 登录时间
	 */
	public String getToken() {
		return token;
	}

	@Override
	public String toString() {
		return token;
	}

	// @Override
	// public String toString() {
	// return "TokenBean [id=" + id + ", time=" + time + ", ip=" + ip + ", server=" + server + ", sign=" + sign + ", valid=" + valid + ", token=" + token + "]";
	// }

	@Override
	public byte[] array() {
		return Bytes.toBytes(id, time, IpUtil.encode(ip), IpUtil.encode(server), ban, b, sign);
	}

	@Override
	public TokenBean array(byte[] b) {
		// 判断字节数组不为空
		if (U.E.isNotEmpty(b)) {
			this.id = Bytes.toLong(b);
			this.time = Bytes.toInt(b, 8);
			this.ip = IpUtil.decode(Bytes.toInt(b, 12));
			this.server = IpUtil.decode(Bytes.toInt(b, 16));
			this.ban = Bytes.toBoolean(b, 20);
			this.b = Bytes.toByte(b, 21);
			this.sign = Bytes.toShort(b, 22);
			this.valid = id != 0;
		}
		// 返回自身
		return this;
	}
}