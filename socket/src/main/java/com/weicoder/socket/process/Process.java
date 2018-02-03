package com.weicoder.socket.process;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Map;

import com.weicoder.common.binary.Binary;
import com.weicoder.common.binary.Buffer;
import com.weicoder.common.binary.ByteArray;
import com.weicoder.common.lang.Bytes;
import com.weicoder.common.lang.Maps;
import com.weicoder.common.util.BeanUtil;
import com.weicoder.common.util.ClassUtil;
import com.weicoder.common.util.CloseUtil;
import com.weicoder.common.util.DateUtil;
import com.weicoder.common.util.EmptyUtil;
import com.weicoder.common.util.StringUtil;
import com.weicoder.common.zip.ZipEngine;
import com.weicoder.core.protobuf.Protobuf;
import com.weicoder.core.protobuf.ProtobufEngine;
import com.weicoder.common.log.Logs;
import com.weicoder.common.params.CommonParams;
import com.weicoder.socket.params.SocketParams;
import com.weicoder.socket.Session;
import com.weicoder.socket.Sockets;
import com.weicoder.socket.annotation.Closed;
import com.weicoder.socket.annotation.Connected;
import com.weicoder.socket.annotation.Handler;
import com.weicoder.socket.annotation.Head;
import com.weicoder.socket.manager.Manager;

/**
 * Socket 数据处理器实现
 * @author WD
 */
public final class Process {
	// Handler列表
	private Map<Short, Object>	handlers;
	// head 对应方法
	private Map<Short, Method>	methods;
	// 关闭处理器
	private Map<Object, Method>	closeds;
	// 连接处理器
	private Map<Object, Method>	connected;
	// 管理器
	private Manager				manager;
	// 处理器名字
	private String				name;
	// 是否使用压缩
	private boolean				zip;

	/**
	 * 构造
	 * @param name 名称
	 */
	public Process(String name) {
		// 设置属性
		handlers = Maps.newMap();
		methods = Maps.newMap();
		closeds = Maps.newMap();
		this.name = name;
		// 获得是否压缩
		this.zip = SocketParams.isZip(name);
		// 获得管理器
		this.manager = Sockets.manager();

		// 设置handler closed
		for (Class<?> c : ClassUtil.getAnnotationClass(CommonParams.getPackages("socket"),
				Handler.class)) {
			// 是本类使用
			Object h = BeanUtil.newInstance(c);
			if (name.equals(h.getClass().getAnnotation(Handler.class).value())) {
				// 所有方法
				for (Method m : c.getDeclaredMethods()) {
					// 判断是公有方法
					if (Modifier.isPublic(m.getModifiers())) {
						// 是head 头的
						if (m.isAnnotationPresent(Head.class)) {
							// 添加到map中
							short id = m.getAnnotation(Head.class).id();
							methods.put(id, m);
							handlers.put(id, h);
						} else if (m.isAnnotationPresent(Closed.class)) {
							// Closed 头
							closeds.put(h, m);
						} else if (m.isAnnotationPresent(Connected.class)) {
							// Closed 头
							connected.put(h, m);
						}
					}
				}
			}
		}
	}

	/**
	 * Session连接时
	 * @param session Session
	 */
	public void connected(Session session) {
		// 管理器注册Session
		manager.register(session);
		// 如果连接处理器不为空
		for (Map.Entry<Object, Method> e : connected.entrySet()) {
			// 获得关闭方法
			Method m = e.getValue();
			if (m.getParameterCount() == 1) {
				BeanUtil.invoke(e.getKey(), m, session);
			} else {
				BeanUtil.invoke(e.getKey(), m);
			}
		}
		// 日志
		Logs.info("name={};socket conn={};ip={};", name, session.getId(), session.getIp());
	}

	/**
	 * Session关闭时
	 * @param session Session
	 */
	public void closed(Session session) {
		// 关闭处理器
		for (Map.Entry<Object, Method> e : closeds.entrySet()) {
			// 获得关闭方法
			Method m = e.getValue();
			if (m.getParameterCount() == 1) {
				BeanUtil.invoke(e.getKey(), m, session);
			} else {
				BeanUtil.invoke(e.getKey(), m);
			}
		}
		// 删除管理器注册Session
		manager.remove(session.getId());
		// 删除缓存
		Logs.info("name={};socket close={};ip={}", name, session.getId(), session.getIp());
	}

	/**
	 * 处理数据 消息处理 short(消息长度不算本身2字节) short(ID) byte[]
	 * @param session Session
	 * @param message 字节流
	 */
	public void process(Session session, byte[] message) {
		// 获得session id
		long sid = session.getId();
		Logs.debug("name={};socket={};len={};message={}", name, sid, message.length,
				Arrays.toString(message));
		// 获得全局buffer
		Buffer buff = session.buffer();
		// 添加新消息到全局缓存中
		buff.write(message);
		// 循环读取数据
		while (true) {
			// 剩余字节长度不足，等待下次信息
			if (buff.remaining() < 4) {
				// 压缩并跳出循环
				break;
			}
			// 获得信息长度
			short length = buff.readShort();
			// 无长度 发送消息不符合 关掉连接
			if (length < 2 || length > Short.MAX_VALUE) {
				CloseUtil.close(session);
				Logs.info("name={};error len close id={};len={}", name, session.getId(), length);
				return;
			}
			// 剩余字节长度不足，等待下次信息
			if (buff.remaining() < length) {
				// 重置缓存
				buff.offset(buff.offset() - 2);
				break;
			}
			// 读取指令id
			short id = buff.readShort();
			// 消息长度
			int len = length - 2;
			// 读取指定长度的字节数
			byte[] data = new byte[len];
			// 读取指定长度字节数组
			if (len > 0) {
				// 读取字节数组
				buff.read(data);
				// 启用压缩
				if (zip) {
					// 解压缩
					data = ZipEngine.extract(data);
				}
			}
			// 获得相应的方法
			Method m = methods.get(id);
			// 如果处理器为空
			if (m == null) {
				// 抛弃这次消息
				Logs.warn("name={};socket={};handler message discard id={};message len={}", name,
						sid, id, len);
				return;
			}
			Logs.info("name={};socket={};receive len={};id={};method={};time={}", name, sid, length,
					id, m, DateUtil.getTheDate());
			try {
				// 当前时间
				long curr = System.currentTimeMillis();
				// 回调处理器
				m.invoke(handlers.get(id), getParames(m, data, session));
				// 设置心跳时间
				session.setHeart(DateUtil.getTime());
				Logs.info("name={};socket={};handler end time={}", name, sid,
						System.currentTimeMillis() - curr);
			} catch (Exception e) {
				Logs.error(e);
			}
			// 如果缓存区为空
			if (buff.remaining() == 0) {
				// 清除并跳出
				buff.clear();
				break;
			}
		}
	}

	private Object[] getParames(Method m, byte[] data, Session session) {
		// 如果数据为空
		if (EmptyUtil.isEmpty(data)) {
			return null;
		}
		// 设置参数
		Parameter[] pars = m.getParameters();
		Object[] params = null;
		if (!EmptyUtil.isEmpty(pars)) {
			// 参数不为空 设置参数
			params = new Object[pars.length];
			// action全部参数下标
			int i = 0;
			for (; i < pars.length; i++) {
				// 判断类型并设置
				Parameter p = pars[i];
				// 参数的类型
				Class<?> type = p.getType();
				if (Session.class.isAssignableFrom(type)) {
					// Session
					params[i] = session;
				} else if (Manager.class.equals(type)) {
					// Manager
					params[i] = Sockets.manager();
				}  else if (type.isAnnotationPresent(Protobuf.class)) {
					// 字节流
					params[i] = ProtobufEngine.toBean(data, type);
				} else if (type.equals(String.class)) {
					// 字符串
					params[i] = StringUtil.toString(data);
				} else if (Binary.class.isAssignableFrom(type)) {
					// 字节流
					params[i] = Bytes.toBinary(type, data);
				} else if (ByteArray.class.isAssignableFrom(type)) {
					// 字节流
					params[i] = ((ByteArray) ClassUtil.newInstance(type)).array(data);
				} else if (type.equals(Buffer.class)) {
					// 字节流
					params[i] = new Buffer(data);
				} else if (type.equals(int.class) || type.equals(Integer.class)) {
					// 整型
					params[i] = Bytes.toInt(data);
				} else if (type.equals(long.class) || type.equals(Long.class)) {
					// 长整型
					params[i] = Bytes.toLong(data);
				} else if (type.equals(boolean.class) || type.equals(Boolean.class)) {
					// 布尔
					params[i] = Bytes.toBoolean(data);
				} else if (type.equals(float.class) || type.equals(Float.class)) {
					// float型
					params[i] = Bytes.toFloat(data);
				} else if (type.equals(double.class) || type.equals(Double.class)) {
					// Double型
					params[i] = Bytes.toDouble(data);
				} else if (type.equals(byte.class) || type.equals(Byte.class)) {
					// 字节流
					params[i] = data[0];
				} else if (type.equals(byte[].class)) {
					// 字节流
					params[i] = data;
				}else {
					params[i] = null;
				}
			}
		}
		// 返回参数
		return params;
	}
}
