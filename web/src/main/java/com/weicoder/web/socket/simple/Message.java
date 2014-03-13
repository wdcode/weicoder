package com.weicoder.web.socket.simple;

import java.lang.reflect.Field;
import java.util.List;

import com.weicoder.common.interfaces.BytesBean;
import com.weicoder.common.lang.Bytes;
import com.weicoder.common.util.BeanUtil;

/**
 * Socket 传递消息实体
 * @author WD
 * @since JDK7
 * @version 1.0 2013-12-19
 */
public abstract class Message extends Send implements BytesBean {
	@Override
	public BytesBean array(byte[] b) {
		// 获得全部字段
		List<Field> fields = BeanUtil.getFields(this.getClass());
		// 偏移
		int offset = 0;
		// 循环设置字段值
		for (Field field : fields) {
			// 如果偏移与字节长度相同 没有数据 跳出
			if (b.length <= offset) {
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
					offset += Bytes.toShort(b, offset) + 2;
				} else if (type.isAssignableFrom(BytesBean.class)) {
					// 转换为BytesBean
					BytesBean bean = Bytes.toBean(b, offset);
					BeanUtil.setFieldValue(this, field, bean);
					// 类的字符串长度
					offset += 2 + Bytes.toShort(b, offset);
					// 字节数组长度
					offset += 4 + Bytes.toInt(b, offset);
				} else if (type.equals(byte[].class)) {
					// 字节数组会获得后面全部的 所以一般这个类型也是本类的最后一个字段
					byte[] t = Bytes.copy(b, offset, b.length);
					BeanUtil.setFieldValue(this, field, t);
					offset += t.length;
				}
			}
		}
		// 返回本身
		return this;
	}
}
