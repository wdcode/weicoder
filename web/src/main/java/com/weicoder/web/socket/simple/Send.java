package com.weicoder.web.socket.simple;

import java.lang.reflect.Field;
import java.util.List;

import com.weicoder.common.interfaces.ByteArray;
import com.weicoder.common.lang.Bytes;
import com.weicoder.common.lang.Lists;
import com.weicoder.common.util.BeanUtil;
import com.weicoder.core.json.JsonEngine;

/**
 * 单发送消息
 * @author WD
 * @since JDK7
 * @version 1.0 2014-3-12
 */
public abstract class Send implements ByteArray {
	@Override
	public byte[] array() {
		// 字段值
		List<Object> values = Lists.getList();
		// 获得字段赋值
		for (Field field : BeanUtil.getFields(this.getClass())) {
			if (!field.isSynthetic()) {
				values.add(BeanUtil.getFieldValue(this, field));
			}
		}
		// 返回字节数组
		return Bytes.toBytes(values.toArray());
	}

	@Override
	public String toString() {
		return JsonEngine.toJson(this);
	}
}
