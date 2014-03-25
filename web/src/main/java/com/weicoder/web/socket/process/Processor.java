package com.weicoder.web.socket.process;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.weicoder.common.binary.Buffer;
import com.weicoder.common.lang.Bytes;
import com.weicoder.common.lang.Maps;
import com.weicoder.common.util.ClassUtil;
import com.weicoder.common.util.DateUtil;
import com.weicoder.common.util.StringUtil;
import com.weicoder.core.log.Logs;
import com.weicoder.web.socket.Closed;
import com.weicoder.web.socket.Handler;
import com.weicoder.web.socket.Session;
import com.weicoder.web.socket.heart.Heart;
import com.weicoder.web.socket.manager.Manager;
import com.weicoder.web.socket.message.Message;
import com.weicoder.web.socket.message.Null;

/**
 * Socket 数据处理器实现
 * @author WD
 * @since JDK7
 * @version 1.0 2013-12-22
 */
public final class Processor implements Process {
	// 线程池
	private ExecutorService				ES			= Executors.newCachedThreadPool();
	// Handler列表
	private Map<Short, Handler<Object>>	handlers	= Maps.getMap();
	// 保存Session
	private Map<Integer, Session>		sessions	= Maps.getConcurrentMap();
	// 保存全局IoBuffer
	private Map<Integer, Buffer>		buffers		= Maps.getConcurrentMap();
	// SessionManager
	private Manager						manager;
	// 心跳处理
	private Heart						heart;
	// 关闭处理
	private Closed						closed;

	/**
	 * Session管理
	 * @param manager Session管理
	 * @param heart 心跳协议
	 */
	public Processor(Manager manager) {
		this.manager = manager;
	}

	@Override
	public void setHeart(Heart heart) {
		this.heart = heart;
	}

	@Override
	public void setClosed(Closed closed) {
		this.closed = closed;
	}

	@Override
	public void addHandler(Handler<?> handler) {
		handlers.put(handler.getId(), (Handler<Object>) handler);
	}

	@Override
	public void connected(Session session) {
		sessions.put(session.getId(), session);
		buffers.put(session.getId(), new Buffer());
		// 如果心跳处理不为空
		if (heart != null) {
			heart.add(session);
		}
		Logs.info("socket conn=" + session.getId());
	}

	@Override
	public void closed(Session session) {
		// 关闭处理器
		if (closed != null) {
			closed.closed(session);
		}
		// 删除session
		sessions.remove(session.getId());
		// 删除缓存
		buffers.remove(session.getId());
		// 如果心跳处理不为空
		if (heart != null) {
			heart.remove(session);
		}
		// 删除Session管理中的注册Session
		manager.remove(session);
		Logs.info("socket close=" + session.getId());
	}

	@Override
	public Session getSession(int id) {
		return sessions.get(id);
	}

	@Override
	public Map<Integer, Session> getSessions() {
		return sessions;
	}

	@Override
	public void process(final Session session, final byte[] message) {
		Logs.debug("socket=" + session.getId() + ";receive=" + session.getId() + ";len=" + message.length);
		// 获得全局buffer
		Buffer buff = buffers.get(session.getId());
		// 添加新消息到全局缓存中
		buff.write(message);
		// 反转缓存区
		// buff.flip();
		// 循环读取数据
		while (true) {
			// 剩余字节长度不足，等待下次信息
			if (buff.remaining() < 4) {
				// 压缩并跳出循环
				buff.compact();
				break;
			}
			// 获得信息长度
			// int length = Integer.reverseBytes(buff.getInt());
			int length = buff.readInt();
			// 无长度 发送消息不符合 关掉连接
			if (length == 0) {
				session.close();
				break;
			}
			// 剩余字节长度不足，等待下次信息
			if (buff.remaining() < length) {
				// 重置缓存
				buff.rewind();
				// 压缩并跳出循环
				buff.compact();
				break;
			} else {
				// 读取指令id
				// int id = Integer.reverseBytes(buff.getInt());
				final short id = buff.readShort();
				// 获得相应的
				final Handler<Object> handler = handlers.get(id);
				Logs.info("socket=" + session.getId() + ";receive len=" + length + ";id=" + id + ";handler=" + handler + ";time=" + DateUtil.getTheDate());
				// 消息长度
				final int len = length - 2;
				// 读取指定长度的字节数
				final byte[] data = new byte[len];
				// 读取指定长度字节数组
				if (len > 0) {
					buff.read(data);
				}
				// 如果处理器为空
				if (handler == null) {
					// 抛弃这次消息
					Logs.warn("socket=" + session.getId() + ";handler message discard id=" + id + ";message len=" + len);
					return;
				}
				// 线程执行
				ES.execute(new Runnable() {
					@Override
					public void run() {
						try {
							// 当前时间
							long curr = System.currentTimeMillis();
							// 如果消息长度为0
							if (len == 0) {
								handler.handler(session, null, manager);
								Logs.info("socket=" + session.getId() + ";handler message is null end time=" + (System.currentTimeMillis() - curr));
							} else {
								// 获得处理器消息类
								Class<?> type = ClassUtil.getGenericClass(handler.getClass());
								// 消息实体
								Object mess = null;
								// 判断消息实体类型
								if (type.equals(String.class)) {
									// 字符串
									mess = StringUtil.toString(data);
								} else if (type.equals(Null.class)) {
									// 字节流
									mess = Null.NULL;
								} else if (type.equals(Buffer.class)) {
									// 字节流
									mess = new Buffer(data);
								} else if (type.equals(int.class) || type.equals(Integer.class)) {
									// 整型
									mess = Bytes.toInt(data);
								} else if (type.equals(long.class) || type.equals(Long.class)) {
									// 长整型
									mess = Bytes.toLong(data);
								} else if (type.equals(boolean.class) || type.equals(Boolean.class)) {
									// 布尔
									mess = Bytes.toLong(data);
								} else if (type.equals(float.class) || type.equals(Float.class)) {
									// float型
									mess = Bytes.toFloat(data);
								} else if (type.equals(double.class) || type.equals(Double.class)) {
									// Double型
									mess = Bytes.toDouble(data);
								} else if (type.equals(byte[].class)) {
									// 字节流
									mess = data;
								} else {
									// 默认使用消息体
									mess = ((Message) ClassUtil.newInstance(type)).array(data);
								}
								Logs.info("socket=" + session.getId() + ";handler message=" + mess + ";time=" + (System.currentTimeMillis() - curr));
								curr = System.currentTimeMillis();
								// 回调处理器
								handler.handler(session, mess, manager);
								Logs.info("socket=" + session.getId() + ";handler end time=" + (System.currentTimeMillis() - curr));
							}
						} catch (Exception e) {
							Logs.error(e);
						}
					}
				});
				// 如果缓存区为空
				if (buff.remaining() == 0) {
					// 清除并跳出
					buff.clear();
					break;
				} else {
					// 压缩
					buff.compact();
					// 反转缓存区
					// buff.flip();
				}
			}
		}
	}
}
