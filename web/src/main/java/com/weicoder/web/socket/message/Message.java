package com.weicoder.web.socket.message;

import com.weicoder.common.binary.Binary;
import com.weicoder.common.binary.ByteArray;
import com.weicoder.common.lang.Bytes;
import com.weicoder.core.json.JsonEngine;

/**
 * Socket 传递消息实体
 * @author WD
 * @since JDK7
 * @version 1.0 2013-12-19
 */
public abstract class Message implements ByteArray, Binary {
	@Override
	public byte[] array() {
		return Bytes.toBytes((Binary) this);
	}

	@Override
	public ByteArray array(byte[] b) {
		return Bytes.toBinary(this, b);
	}

	@Override
	public String toString() {
		return JsonEngine.toJson(this);
	}
}
