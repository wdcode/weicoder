package com.weicoder.socket.message;

import com.weicoder.common.constants.ArrayConstants;
import com.weicoder.common.lang.Bytes;
import com.weicoder.common.lang.W;
import com.weicoder.common.util.StringUtil;
import com.weicoder.protobuf.Protobuf;
import com.weicoder.protobuf.ProtobufEngine;

/**
 * 信息序列化处理类
 * 
 * @author wudi
 */
public final class Messages { 
	/**
	 * 包装数据
	 * 
	 * @param  id      指令
	 * @param  message 消息
	 * @return         字节数组
	 */
	public static byte[] pack(short id, Object message) {
		// 声明字节数组
		byte[] data = toBytes(message);
		// 返回数据
		return Bytes.toBytes(W.C.toShort(data.length + 2), id, data);
	}

	/**
	 * 包装数据
	 * 
	 * @param  message 消息
	 * @return         字节数组
	 */
	public static byte[] pack(Object message) {
		// 声明字节数组
		byte[] data = toBytes(message);
		// 返回数据
		return Bytes.toBytes(W.C.toShort(data.length), data);
	}

	/**
	 * 转换message为字节数组
	 * 
	 * @param  message 消息
	 * @return         字节数组
	 */
	private static byte[] toBytes(Object message) {
		// 判断类型
		if (message == null)
			// 空
			return ArrayConstants.BYTES_EMPTY;
		else if (message instanceof String)
			// 字符串
			return StringUtil.toBytes(message);
		else if (message.getClass().isAnnotationPresent(Protobuf.class))
			// 字符串
			return ProtobufEngine.toBytes(message);
		else
			// 不知道的类型 以字节数组发送
			return Bytes.toBytes(message);
	}

	private Messages() {
	}
}
