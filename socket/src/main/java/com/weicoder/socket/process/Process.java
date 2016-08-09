package com.weicoder.socket.process;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Map;

import com.weicoder.common.binary.Binary;
import com.weicoder.common.binary.Buffer;
import com.weicoder.common.binary.ByteArray;
import com.weicoder.common.concurrent.ScheduledUtile;
import com.weicoder.common.lang.Bytes;
import com.weicoder.common.lang.Conversion;
import com.weicoder.common.lang.Maps;
import com.weicoder.common.util.BeanUtil;
import com.weicoder.common.util.ClassUtil;
import com.weicoder.common.util.CloseUtil;
import com.weicoder.common.util.DateUtil;
import com.weicoder.common.util.EmptyUtil;
import com.weicoder.common.util.StringUtil;
import com.weicoder.common.zip.ZipEngine;
import com.weicoder.common.log.Logs;
import com.weicoder.socket.params.SocketParams;
import com.weicoder.socket.Closed;
import com.weicoder.socket.Connected;
import com.weicoder.socket.Session;
import com.weicoder.socket.Sockets;
import com.weicoder.socket.annotation.Handler;
import com.weicoder.socket.annotation.Head;
import com.weicoder.socket.heart.Heart;
import com.weicoder.socket.manager.Manager;
import com.weicoder.socket.message.Null;

/**
 * Socket 数据处理器实现
 * @author WD
 */
public final class Process {
	// Handler列表
	private Map<Short, Handler>		handlers	= Maps.getMap();
	// head 对应方法
	private Map<Short, Method>		methods		= Maps.getMap();
	// 保存Session
	private Map<Long, Session>		sessions	= Maps.getConcurrentMap();
	// 保存Session
	private Map<Long, Integer>		times		= Maps.getConcurrentMap();
	// 保存全局IoBuffer
	private Map<Long, Buffer>		buffers		= Maps.getConcurrentMap();
	// 限制IP连接
	private Map<String, Boolean>	limits		= Maps.getConcurrentMap();
	// 连接超时错误
	private Map<String, Integer>	overs		= Maps.getConcurrentMap();
	// 管理器
	private Manager					manager;
	// 心跳处理
	private Heart					heart;
	// 连接处理
	private Connected				connected;
	// 关闭处理
	private Closed					closed;
	// 心跳ID
	private short					heartId;
	// 处理器名字
	private String					name;
	// 是否使用压缩
	private boolean					zip;

	/**
	 * 构造
	 * @param name 名称
	 */
	public Process(String name) {
		// 设置属性
		this.name = name;
		// 获得是否压缩
		this.zip = SocketParams.isZip(name);
		// 获得管理器
		this.manager = Sockets.manager();
		// 获得心跳时间
		int htime = SocketParams.getHeartTime(name);
		// 设置连接处理器
		this.connected = (Connected) BeanUtil.newInstance(SocketParams.getConnected(name));
		// 设置关闭处理器
		this.closed = (Closed) BeanUtil.newInstance(SocketParams.getClosed(name));

		// 设置handler
		for (Class<Handler> c : ClassUtil.getAnnotationClass(Handler.class)) {
			// 是本类使用
			Handler h = BeanUtil.newInstance(c);
			if (name.equals(h.value())) {
				// 所有方法
				for (Method m : c.getDeclaredMethods()) {
					// 判断是公有方法 并且是head 头的
					if (Modifier.isPublic(m.getModifiers()) && m.isAnnotationPresent(Head.class)) {
						// 添加到map中
						short id = m.getAnnotation(Head.class).id();
						methods.put(id, m);
						handlers.put(id, h);
					}
				}
			}
		}

		// 配置了心跳
		if (htime > 0) {
			// 设置心跳
			heart = new Heart(heartId = SocketParams.getHeartId(name), htime, SocketParams.isHeartPack(name));
			// addHandler(heart);
		}
		// 检测时间
		final int time = SocketParams.getTime(name);
		// 超时次数
		final int over = SocketParams.getOver(name);
		if (time > 0) {
			// 定时检测
			ScheduledUtile.rate(() -> {
				// 检测连接超时
				try {
					// 获得当前时间
					int curr = DateUtil.getTime();
					for (Map.Entry<Long, Integer> e : times.entrySet()) {
						// 超时
						if (curr - e.getValue() >= time) {
							// 获得Session
							Session session = sessions.get(e.getKey());
							// IP
							String ip = session.ip();
							// 获得本IP次数
							int num = Conversion.toInt(overs.get(ip));
							// 判断超时超过一定次数
							if (num > over) {
								// 添加到拒绝列表
								limits.put(ip, true);
							}
							// 添加次数
							overs.put(ip, ++num);
							// 关闭
							session.close();
							Logs.info(StringUtil.add("name=", name, ";overtime close id=", e.getKey()));
						}
					}
				} catch (Exception e) {}
			}, 1);
		}
	}

	/**
	 * Session连接时
	 * @param session Session
	 */
	public void connected(Session session) {
		// 是否拒绝连接
		if (Conversion.toBoolean(limits.get(session.ip()))) {
			CloseUtil.close(session);
			Logs.info(StringUtil.add("name=", name, ";limits ip=", session.ip(), " close id=", session.id()));
			return;
		}
		// 是否连接
		boolean is = true;
		// 如果连接处理器不为空
		if (connected != null) {
			is = connected.connected(session);
		}
		// 允许连接
		if (is) {
			sessions.put(session.id(), session);
			buffers.put(session.id(), new Buffer());
			times.put(session.id(), DateUtil.getTime());
			// 管理器注册Session
			if (manager != null) {
				manager.register(name, session);
			}
			// 如果心跳处理不为空
			if (heart != null) {
				heart.add(session);
			}
		} else {
			CloseUtil.close(session);
			Logs.info(StringUtil.add("name=", name, ";connected - close id=", session.id()));
		}
		// 日志
		Logs.info(StringUtil.add("name=", name, ";socket conn=", session.id(), ";ip=", session.ip(), ";is=", is));
	}

	/**
	 * Session关闭时
	 * @param session Session
	 */
	public void closed(Session session) {
		try {
			// 关闭处理器
			if (closed != null) {
				closed.closed(session);
			}
			// 删除管理器注册Session
			if (manager != null) {
				manager.remove(name, session.id());
			}
		} catch (Exception e) {
			Logs.error(e);
		}
		// 删除session
		sessions.remove(session.id());
		// 删除缓存
		buffers.remove(session.id());
		// 如果心跳处理不为空
		if (heart != null) {
			heart.remove(session);
		}
		Logs.info(StringUtil.add("name=", name, ";socket close=", session.id(), ";ip=", session.ip()));
	}

	/**
	 * 根据ID获得session
	 * @param id SessionId
	 * @return Session
	 */
	public Session session(int id) {
		return sessions.get(id);
	}

	/**
	 * 处理数据
	 * @param session Session
	 * @param message 字节流
	 */
	public void process(Session session, byte[] message) {
		// 获得session id
		long sid = session.id();
		Logs.debug(StringUtil.add("name=", name, ";socket=", sid, ";receive=", sid, ";len=", message.length, ";message=", Arrays.toString(message)));
		// 获得全局buffer
		Buffer buff = buffers.get(sid);
		// 添加新消息到全局缓存中
		buff.write(message);
		// 反转缓存区
		// buff.flip();
		// 循环读取数据
		while (true) {
			// 剩余字节长度不足，等待下次信息
			if (buff.remaining() < 4) {
				// 压缩并跳出循环
				// buff.compact();
				break;
			}
			// 是否存在
			if (times.containsKey(sid)) {
				times.remove(sid);
			}
			// 是否存在
			if (limits.containsKey(sid)) {
				limits.remove(sid);
			}
			// 获得信息长度
			short length = buff.readShort();
			// 无长度 发送消息不符合 关掉连接
			if (length < 2 || length > Short.MAX_VALUE) {
				CloseUtil.close(session);
				Logs.info(StringUtil.add("name=", name, ";error len close id=", session.id(), ";len=" + length));
				return;
			}
			// 剩余字节长度不足，等待下次信息
			if (buff.remaining() < length) {
				// 重置缓存
				buff.offset(buff.offset() - 2);
				break;
			}

			// 读取指令id
			// int id = Integer.reverseBytes(buff.getInt());
			short id = buff.readShort();
			// 获得相应的
			// Handler<Object> handler = handlers.get(id);
			Method m = methods.get(id);
			// 日志
			String log = StringUtil.add("name=", name, ";socket=", sid, ";receive len=", length, ";id=", id, ";method=", m, ";time=", DateUtil.getTheDate());
			// 心跳包用debug 其它info
			if (id == heartId) {
				heart.handler(session);
				Logs.debug(log);
				break;
			}
			Logs.info(log);

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
			// 如果处理器为空
			if (m == null) {
				// 抛弃这次消息
				Logs.warn(StringUtil.add("name=", name, ";socket=", sid, ";handler message discard id=", id, ";message len=", len));
				return;
			}
			try {
				// 当前时间
				long curr = System.currentTimeMillis();
				// // 如果消息长度为0
				// if (len == 0) {
				// heart.handler(session, Null.NULL);
				// // handler.handler(session, null);
				// // 日志
				// log = StringUtil.add("name=", name, ";socket=", sid, ";handler message is null end time=", System.currentTimeMillis() - curr);
				// // 心跳包用debug 其它info
				// if (id == heartId) {
				// Logs.debug(log);
				// } else {
				// Logs.info(log);
				// }
				// } else {
				// // 获得处理器消息类
				// Class<?> type = ClassUtil.getGenericClass(handler.getClass());
				// // 消息实体
				// Object mess = null;
				// // 判断消息实体类型
				// if (type.equals(String.class)) {
				// // 字符串
				// mess = StringUtil.toString(data);
				// } else if (Binary.class.isAssignableFrom(type)) {
				// // 字节流
				// mess = Bytes.toBinary((Binary) ClassUtil.newInstance(type), data);
				// } else if (ByteArray.class.isAssignableFrom(type)) {
				// // 字节流
				// mess = ((ByteArray) ClassUtil.newInstance(type)).array(data);
				// } else if (type.equals(Null.class)) {
				// // 字节流
				// mess = Null.NULL;
				// } else if (type.equals(Buffer.class)) {
				// // 字节流
				// mess = new Buffer(data);
				// } else if (type.equals(int.class) || type.equals(Integer.class)) {
				// // 整型
				// mess = Bytes.toInt(data);
				// } else if (type.equals(long.class) || type.equals(Long.class)) {
				// // 长整型
				// mess = Bytes.toLong(data);
				// } else if (type.equals(boolean.class) || type.equals(Boolean.class)) {
				// // 布尔
				// mess = Bytes.toLong(data);
				// } else if (type.equals(float.class) || type.equals(Float.class)) {
				// // float型
				// mess = Bytes.toFloat(data);
				// } else if (type.equals(double.class) || type.equals(Double.class)) {
				// // Double型
				// mess = Bytes.toDouble(data);
				// } else if (type.equals(byte.class) || type.equals(Byte.class)) {
				// // 字节流
				// mess = data[0];
				// } else if (type.equals(byte[].class)) {
				// // 字节流
				// mess = data;
				// } else {
				// // 默认使用空消息体
				// log = StringUtil.add("name=", name, ";socket=", sid, ";handler data not null data.length=", data.length);
				// // 心跳包用debug 其它info
				// if (id == heartId) {
				// Logs.debug(log);
				// } else {
				// Logs.info(log);
				// }
				// mess = Null.NULL;// ((Message) ClassUtil.newInstance(type)).array(data);
				// }
				// log = StringUtil.add("name=", name, ";socket=", sid, ";handler message=", mess, ";time=", System.currentTimeMillis() - curr);
				// 心跳包用debug 其它info
				// if (id == heartId) {
				// Logs.debug(log);
				// } else {
				// Logs.info(log);
				// }
				curr = System.currentTimeMillis();
				// 回调处理器
				BeanUtil.invoke(handlers.get(id), m, getParames(m, data, session));
				log = StringUtil.add("name=", name, ";socket=", sid, ";handler end time=", System.currentTimeMillis() - curr);
				// 心跳包用debug 其它info
				if (id == heartId) {
					Logs.debug(log);
				} else {
					Logs.info(log);
				}
				// }
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
				} else if (type.equals(String.class)) {
					// 字符串
					params[i] = StringUtil.toString(data);
				} else if (Binary.class.isAssignableFrom(type)) {
					// 字节流
					params[i] = Bytes.toBinary((Binary) ClassUtil.newInstance(type), data);
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
				} else {
					params[i] = Null.NULL;
				}
			}
		}
		// 返回参数
		return params;
	}
}
