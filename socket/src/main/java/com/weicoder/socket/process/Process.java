package com.weicoder.socket.process;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Map;

import com.weicoder.common.binary.Buffer;
import com.weicoder.common.concurrent.ExecutorUtil;
import com.weicoder.common.lang.Bytes;
import com.weicoder.common.lang.Maps;
import com.weicoder.common.util.BeanUtil;
import com.weicoder.common.util.ClassUtil;
import com.weicoder.common.util.DateUtil;
import com.weicoder.common.util.U;
import com.weicoder.common.util.U.C;
import com.weicoder.protobuf.Protobuf;
import com.weicoder.protobuf.ProtobufEngine;
import com.weicoder.common.log.Log;
import com.weicoder.common.log.LogFactory;
import com.weicoder.common.statics.Closes;
import com.weicoder.socket.Event;
import com.weicoder.socket.Session;
import com.weicoder.socket.annotation.AllHead; 
import com.weicoder.socket.annotation.Handler;
import com.weicoder.socket.annotation.Head; 
import com.weicoder.socket.manager.Manager;
import com.weicoder.socket.params.SocketParams;

/**
 * Socket 数据处理器实现
 * 
 * @author WD
 */
public final class Process {
	// 日志
	private final static Log LOG = LogFactory.getLog(Process.class);
	// Handler列表
	private Map<Short, Object> handlers = Maps.newMap();
	// head 对应方法
	private Map<Short, Method> methods = Maps.newMap();
	// 所有Handler列表
	private Map<Object, Method> all = Maps.newMap();
	// 事件处理器
	private Event event;
	// 处理器名字
	private String name;

	/**
	 * 构造
	 * 
	 * @param name 名称
	 */
	public Process(String name) {
		// 设置属性
		this.name = name;
		// 设置handler closed
		C.list(Handler.class).forEach(c -> {
			// 是本类使用
			Object h = ClassUtil.newInstance(c);
			if (name.equals(h.getClass().getAnnotation(Handler.class).value())) {
				// 所有方法
				ClassUtil.getPublicMethod(c).forEach(m -> {
					// 是head 头的
					if (m.isAnnotationPresent(Head.class)) {
						// 添加到map中
						short id = m.getAnnotation(Head.class).id();
						methods.put(id, m);
						handlers.put(id, h);
					} else if (m.isAnnotationPresent(AllHead.class))
						all.put(h, m);
				});
			}
		});
		// 获取事件处理器
		event = ClassUtil.newInstance(C.from(Event.class, 0));
		if (event == null)
			event = new EmptyEvent();
	}

	/**
	 * Session连接时
	 * 
	 * @param session Session
	 */
	public void connected(Session session) {
		// 管理器注册Session
		Manager.register(session);
		// 如果连接处理器不为空
		event.connected(session);
		// 日志
		LOG.info("name={};socket conn={};ip={};", name, session.getId(), session.getIp());
	}

	/**
	 * Session关闭时
	 * 
	 * @param session Session
	 */
	public void closed(Session session) {
		// 关闭处理器
		event.closed(session);
		// 删除管理器注册Session
		Manager.remove(session.getId());
		// 删除缓存
		LOG.info("name={};socket close={};ip={}", name, session.getId(), session.getIp());
	}

	/**
	 * 处理数据 消息处理 short(消息长度不算本身2字节) short(ID) byte[]
	 * 
	 * @param session Session
	 * @param message 字节流
	 */
	public void process(Session session, byte[] message) {
		// 获得session id
		long sid = session.getId();
		LOG.debug("name={};socket={};len={};message={}", name, sid, message.length, Arrays.toString(message));
		// 获得全局buffer
		Buffer buff = session.buffer();
		// 添加新消息到全局缓存中
		buff.write(message);
		// 循环读取数据
		while (true) {
			// 剩余字节长度不足，等待下次信息
			if (buff.remain() < 4)
				// 压缩并跳出循环
				break;
			// 获得信息长度
			short length = buff.readShort();
			// 无长度 发送消息不符合 关掉连接
			if (length < 2 || length > Short.MAX_VALUE) {
				Closes.close(session);
				LOG.info("name={};error len close id={};len={}", name, session.getId(), length);
				return;
			}
			// 剩余字节长度不足，等待下次信息
			if (buff.remain() < length) {
				// 重置缓存
				buff.reset(2);
				break;
			}
			// 读取指令id
			short id = buff.readShort();
			// 消息长度
			int len = length - 2;
			// 读取指定长度的字节数
			final byte[] data = new byte[len];
			// 读取指定长度字节数组
			if (len > 0) {
				// 读取字节数组
				buff.read(data);
//				// 启用压缩
//				if (zip)
//					// 解压缩
//					data = ZipEngine.extract(data);
			}
			// 检测是否是心跳检测
			if (id == SocketParams.HEART_ID) {
				// 设置心跳时间
				session.setHeart(DateUtil.getTime());
				// 心跳处理器
				event.heart(session);
				continue;
			}
			// 如果有接受所有头方法 使用异步方式执行
			if (U.E.isNotEmpty(all))
				ExecutorUtil.pool()
						.execute(() -> all.forEach((h, m) -> BeanUtil.invoke(h, m, getParames(m, data, session))));

			// 获得相应的方法
			Method m = methods.get(id);
			// 如果处理器为空
			if (m == null) {
				// 抛弃这次消息
				LOG.warn("name={};socket={};handler message discard id={};message len={}", name, sid, id, len);
				return;
			}
			LOG.info("name={};socket={};receive len={};id={};method={};time={}", name, sid, length, id, m,
					DateUtil.getTheDate());
			try {
				// 当前时间
				long curr = System.currentTimeMillis();
				// 回调处理器
				m.invoke(handlers.get(id), getParames(m, data, session));
				// 设置心跳时间
				session.setHeart(DateUtil.getTime());
				LOG.info("name={};socket={};handler end time={}", name, sid, System.currentTimeMillis() - curr);
			} catch (Exception e) {
				LOG.error(e);
			}
			// 如果缓存区为空
			if (buff.remain() == 0) {
				// 清除并跳出
				buff.clear();
				break;
			}
		}
	}

	private Object[] getParames(Method m, byte[] data, Session session) {
		// 如果数据为空
		if (U.E.isEmpty(data))
			return null;
		// 设置参数
		Parameter[] pars = m.getParameters();
		Object[] params = null;
		if (U.E.isNotEmpty(pars)) {
			// 参数不为空 设置参数
			params = new Object[pars.length];
			// action全部参数下标
			int i = 0;
			for (; i < pars.length; i++) {
				// 判断类型并设置
				Parameter p = pars[i];
				// 参数的类型
				Class<?> type = p.getType();
				if (Session.class.isAssignableFrom(type))
					// Session
					params[i] = session;
				else if (type.isAnnotationPresent(Protobuf.class))
					// 字节流
					params[i] = ProtobufEngine.toBean(data, type);
				else
					params[i] = Bytes.to(data, type);
//				if (type.equals(String.class))
//					// 字符串
//					params[i] = StringUtil.toString(data);
//				else if (Binary.class.isAssignableFrom(type))
//					// 字节流
//					params[i] = Bytes.toBinary(data, type);
//				else if (ByteArray.class.isAssignableFrom(type))
//					// 字节流
//					params[i] = ((ByteArray) ClassUtil.newInstance(type)).array(data);
//				else if (type.equals(Buffer.class))
//					// 字节流
//					params[i] = new Buffer(data);
//				else if (type.equals(int.class) || type.equals(Integer.class))
//					// 整型
//					params[i] = Bytes.toInt(data);
//				else if (type.equals(long.class) || type.equals(Long.class))
//					// 长整型
//					params[i] = Bytes.toLong(data);
//				else if (type.equals(boolean.class) || type.equals(Boolean.class))
//					// 布尔
//					params[i] = Bytes.toBoolean(data);
//				else if (type.equals(float.class) || type.equals(Float.class))
//					// float型
//					params[i] = Bytes.toFloat(data);
//				else if (type.equals(double.class) || type.equals(Double.class))
//					// Double型
//					params[i] = Bytes.toDouble(data);
//				else if (type.equals(byte.class) || type.equals(Byte.class))
//					// 字节流
//					params[i] = data[0];
//				else if (type.equals(byte[].class))
//					// 字节流
//					params[i] = data;
//				else
//					params[i] = null;
			}
		}
		// 返回参数
		return params;
	}

	class EmptyEvent implements Event {
		@Override
		public void connected(Session session) {
		}

		@Override
		public void closed(Session session) {
		}

		@Override
		public void heart(Session session) {
		}
	}
}
