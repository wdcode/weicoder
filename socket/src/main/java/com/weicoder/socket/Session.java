package com.weicoder.socket;

import com.weicoder.common.binary.Buffer;
import com.weicoder.common.token.TokenBean;

/**
 * Socket Session
 * 
 * @author WD
 */
public interface Session extends AutoCloseable {
	/**
	 * 获得SessionId
	 * 
	 * @return SessionId
	 */
	long getId();

	/**
	 * 获得连接IP
	 * 
	 * @return IP
	 */
	String getIp();

	/**
	 * 获得连接端口
	 * 
	 * @return port
	 */
	int getPort();

	/**
	 * 获得心跳存活时间
	 * 
	 * @return 上次存活时间 秒
	 */
	int getHeart();

	/**
	 * 获得Buffer 一般为读取数据缓存
	 * 
	 * @return 字节数组
	 */
	Buffer buffer();

	/**
	 * 设置心跳存活时间
	 * 
	 * @param heart 存活时间 秒
	 */
	void setHeart(int heart);

	/**
	 * 写入数据
	 * 
	 * @param id      指令
	 * @param message 消息
	 */
	void send(short id, Object message);

	/**
	 * 写入数据
	 * 
	 * @param message 消息
	 */
	void send(Object message);

	/**
	 * 写入数据
	 * 
	 * @param data 原始数据
	 */
	void send(byte[] data);

	/**
	 * 写入缓存 必须调用flush才能确保数据写入
	 * 
	 * @param id      指令
	 * @param message 消息
	 */
	void write(short id, Object message);

	/**
	 * 写入缓存 必须调用flush才能确保数据写入
	 * 
	 * @param message 消息
	 */
	void write(Object message);

	/**
	 * 写入缓存 必须调用flush才能确保数据写入
	 * 
	 * @param data 原始数据
	 */
	void write(byte[] data);

	/**
	 * 把缓存区的数据一次性写入
	 */
	void flush();

	/**
	 * 设置绑定的对象 一般为用户
	 * 
	 * @param <E> 泛型
	 * @param e   绑定对象一般为用户
	 */
	/**
	 * 设置用户登录token
	 * 
	 * @param token 用户登录token
	 */
	void setToken(TokenBean token);

	/**
	 * 获得用户Token
	 * 
	 * @return 用户Token
	 */
	TokenBean getToken();

	/**
	 * 设置绑定的对象 一般为用户
	 * 
	 * @param <E> 泛型
	 * @param e   绑定对象一般为用户
	 */
	<E> void set(E e);

	/**
	 * 获得绑定的对象 一般为用户
	 * 
	 * @param  <E> 泛型
	 * @return     E一般为用户
	 */
	<E> E get();
}
