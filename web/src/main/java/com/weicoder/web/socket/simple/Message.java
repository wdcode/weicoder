package com.weicoder.web.socket.simple;

import java.lang.reflect.Field;
import java.util.List;

import com.weicoder.common.interfaces.BytesBean;
import com.weicoder.common.lang.Bytes;
import com.weicoder.common.lang.Lists;
import com.weicoder.common.util.BeanUtil;
import com.weicoder.core.json.JsonEngine;

/**
 * Socket 传递消息实体
 * @author WD
 * @since JDK7
 * @version 1.0 2013-12-19
 */
public abstract class Message implements BytesBean {
	@Override
	public byte[] toBytes() {
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
	public BytesBean toBean(byte[] b) {
		// 获得全部字段
		List<Field> fields = BeanUtil.getFields(this.getClass());
		// 偏移
		int offset = 0;
		// 循环设置字段值
		for (Field field : fields) {
			// 如果偏移与字节长度相同 没有数据 跳出
			if (b.length == offset) {
				break;
			}
			if (!field.isSynthetic()) {
				// 获得字段类型
				Class<?> type = field.getType();
				// 转换字节值
				if (type.equals(Integer.class) || type.equals(int.class)) {
					BeanUtil.setFieldValue(this, field, Bytes.toInt(b, offset));
					offset += 4;
				} else if (type.equals(Long.class) || type.equals(long.class)) {
					BeanUtil.setFieldValue(this, field, Bytes.toLong(b, offset));
					offset += 8;
				} else if (type.equals(Double.class) || type.equals(double.class)) {
					BeanUtil.setFieldValue(this, field, Bytes.toDouble(b, offset));
					offset += 8;
				} else if (type.equals(Float.class) || type.equals(float.class)) {
					BeanUtil.setFieldValue(this, field, Bytes.toFloat(b, offset));
					offset += 4;
				} else if (type.equals(Short.class) || type.equals(short.class)) {
					BeanUtil.setFieldValue(this, field, Bytes.toShort(b, offset));
					offset += 2;
				} else if (type.equals(Byte.class) || type.equals(byte.class)) {
					BeanUtil.setFieldValue(this, field, Bytes.toByte(b, offset));
					offset += 1;
				} else if (type.equals(Boolean.class) || type.equals(boolean.class)) {
					BeanUtil.setFieldValue(this, field, Bytes.toBoolean(b, offset));
					offset += 1;
				} else if (type.equals(String.class)) {
					String s = Bytes.toString(b, offset);
					BeanUtil.setFieldValue(this, field, s);
					offset += s.length() + 4;
				} else {
					BytesBean bean = Bytes.toBean(b, offset);
					BeanUtil.setFieldValue(this, field, bean);
					offset += Bytes.toInt(b, offset);
				}
			}
		}
		// 返回本身
		return this;
	}

	@Override
	public String toString() {
		return JsonEngine.toJson(this);
	}
}
