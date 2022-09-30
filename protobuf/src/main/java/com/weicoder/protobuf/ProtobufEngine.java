package com.weicoder.protobuf;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
 
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.WireFormat;
import com.weicoder.common.constants.C;
import com.weicoder.common.lang.W; 
import com.weicoder.common.log.Logs;
import com.weicoder.common.util.U; 

/**
 * Google Protobuf 处理器 使用的Protobuf 3
 * 
 * @author WD
 */
public final class ProtobufEngine {
	/**
	 * 使用Protobuf序列化对象
	 * 
	 * @param  obj 实体对象
	 * @return     字节数组
	 */
	public static byte[] toBytes(Object obj) {
		try {
			// 获得对象类
			Class<?> c = obj.getClass();
			// 获得所有类
			List<Field> fields = U.B.getFields(c);
			// 声明返回序列化结果 获得序列化字节大小
			byte[] result = new byte[getSerializedSize(obj, c, fields)];
			// 生成编码流Protobuf内部提供
			CodedOutputStream output = CodedOutputStream.newInstance(result);
			// 把对象属性序列化写入字节数组
			writeTo(output, obj, fields);
			// 校验结果
			output.checkNoSpaceLeft();
			// 返回结果
			return result;
		} catch (Exception e) {
			// 错误返回空字节数组
			Logs.error(e);
			return C.A.BYTES_EMPTY;
		}
	}

	/**
	 * 反序列化获得对象
	 * 
	 * @param  b   字节数组
	 * @param  c   反序列化的对象
	 * @param  <E> 范型
	 * @return     获得的对象
	 */
	public static <E> E toBean(byte[] b, Class<E> c) {
		// 实例化对象
		E bean = U.C.newInstance(c);
		try {
			// 声明编码输入流 用于读取字段数据
			CodedInputStream input = CodedInputStream.newInstance(b);
			// 获得所有字段
			List<Field> fields = U.B.getFields(c);
			// 读取标签
			int tag = input.readTag();
			// 根据标签获得第几个属性
			int num = WireFormat.getTagFieldNumber(tag);
			// 循环所有属性 字段标示从1开始 所以i从1 开始
			for (int i = 1; i <= fields.size(); i++) {
				// 读取标签为0时跳出循环
				if (tag == 0)
					break;
				// 如果字段位置大于当前，跳过本次循环
				if (num > i)
					continue;
				// 获得本字段
				Field field = fields.get(i - 1);
				// 获得字段类型
				Class<?> type = field.getType();
				// 判断字段类型 根据字段类型赋值
				if (type.equals(String.class))
					// 字符串
					U.B.setFieldValue(bean, field, input.readStringRequireUtf8());
				else if (type.equals(byte.class) || type.equals(Byte.class))
					// 字节类型
					U.B.setFieldValue(bean, field, input.readBytes().byteAt(0));
				else if (type.equals(short.class) || type.equals(Short.class))
					// 字节类型
					U.B.setFieldValue(bean, field, W.B.toShort(input.readBytes().toByteArray()));
				else if (type.equals(int.class) || type.equals(Integer.class))
					// 整型
					U.B.setFieldValue(bean, field, input.readInt32());
				else if (type.equals(long.class) || type.equals(Long.class))
					// 长整型
					U.B.setFieldValue(bean, field, input.readInt64());
				else if (type.equals(boolean.class) || type.equals(Boolean.class))
					// 布尔
					U.B.setFieldValue(bean, field, input.readBool());
				else if (type.equals(float.class) || type.equals(Float.class))
					// float型
					U.B.setFieldValue(bean, field, input.readFloat());
				else if (type.equals(double.class) || type.equals(Double.class))
					// Double型
					U.B.setFieldValue(bean, field, input.readDouble());
//				else if (type.equals(ByteString.class))
//					// 字节字符串
//					U.B.setFieldValue(bean, field, input.readBytes());
				else if (type.equals(byte[].class))
					// 字节流
					U.B.setFieldValue(bean, field, input.readByteArray());
				// 重新读取标签和字段位置
				tag = input.readTag();
				num = WireFormat.getTagFieldNumber(tag);
			}
			// 校验标签是否为0
			input.checkLastTagWas(0);
		} catch (Exception e) {
			Logs.error(e);
		}
		// 返回结果
		return bean;
	}

	/**
	 * 获得序列化字节数量
	 * 
	 * @param  obj    要序列化的对象
	 * @param  c      序列化对象的类
	 * @param  fields 序列化对象的所有字段
	 * @return        序列化后的字节数量
	 */
	private static int getSerializedSize(Object obj, Class<?> c, List<Field> fields) {
		// 要返回的数量
		int size = 0;
		// 循环所有字段
		for (int i = 0; i < fields.size(); i++) {
			// 获得字段
			Field field = fields.get(i);
			// 获得当前字段的值 如果值为null 跳过循环
			Object val = U.B.getFieldValue(obj, field);
			if (val == null)
				continue;
			// 获得字段类型
			Class<?> type = field.getType();
			// 判断字段类型并累加大小
			if (type.equals(String.class)) {
				// 字符串
				String s = W.C.toString(val);
				if (U.E.isNotEmpty(s))
					size += CodedOutputStream.computeStringSize(i, s);
			} else if (type.equals(int.class) || type.equals(Integer.class)) {
				// 整型
				int n = W.C.toInt(val);
				if (n != 0)
					size += CodedOutputStream.computeInt32Size(i, n);
			} else if (type.equals(long.class) || type.equals(Long.class)) {
				// 长整型
				long n = W.C.toLong(val);
				if (n != 0L)
					size += CodedOutputStream.computeInt64Size(i, n);
			} else if (type.equals(boolean.class) || type.equals(Boolean.class)) {
				// 布尔
				boolean n = W.C.toBoolean(val);
				if (n != false)
					size += CodedOutputStream.computeBoolSize(i, n);
			} else if (type.equals(float.class) || type.equals(Float.class)) {
				// float型
				float n = W.C.toFloat(val);
				if (n != 0F)
					size += CodedOutputStream.computeFloatSize(i, n);
			} else if (type.equals(double.class) || type.equals(Double.class)) {
				// Double型
				double n = W.C.toDouble(val);
				if (n != 0D)
					size += CodedOutputStream.computeDoubleSize(i, n);
			} 
//			else if (type.equals(ByteString.class)) {
//				// 字节字符串
//				ByteString n = (ByteString) val;
//				if (!n.isEmpty())
//					size += CodedOutputStream.computeBytesSize(i, n);
//			} 
			else if (type.equals(byte[].class) || U.C.isBaseType(type)) {
				// 字节流
				byte[] n = W.B.toBytes(val);
				if (U.E.isNotEmpty(n))
					size += CodedOutputStream.computeByteArraySize(i, n);
			}
//			else if (type.equals(byte.class) || type.equals(Byte.class) ||) {
//				// 整型
//				byte[] n = W.B.toBytes(val);
//				if (U.E.isNotEmpty(n))
//					size += CodedOutputStream.computeByteArraySize(i, n);
//			} else if (type.equals(short.class) || type.equals(Integer.class)) {
//				// 整型
//				int n = W.C.toInt(val);
//				if (n != 0)
//					size += CodedOutputStream.computeInt32Size(i, n);
//			} else {
//				// 转成字节流
//				ByteString n = ByteString.copyFrom(W.B.toBytes(val));
//				if (!n.isEmpty())
//					size += CodedOutputStream.computeBytesSize(i, n);
//			}
		}
		// 返回数量
		return size;
	}

	/**
	 * 写入数据
	 * 
	 * @param  output      编码输出流
	 * @param  obj         序列化对象
	 * @param  fields      字段列表
	 * @throws IOException IO异常
	 */
	private static void writeTo(CodedOutputStream output, Object obj, List<Field> fields) throws IOException {
		for (int i = 1; i <= fields.size(); i++) {
			Field field = fields.get(i - 1);
			Object val = U.B.getFieldValue(obj, field);
			if (val == null)
				continue;
			Class<?> type = field.getType();
			if (type.equals(String.class)) {
				// 字符串
				String s = W.C.toString(val);
				if (U.E.isNotEmpty(s))
					output.writeString(i, s);
			} else if (type.equals(int.class) || type.equals(Integer.class)) {
				// 整型
				int n = W.C.toInt(val);
				if (n != 0)
					output.writeInt32(i, n);
			} else if (type.equals(long.class) || type.equals(Long.class)) {
				// 长整型
				long n = W.C.toLong(val);
				if (n != 0L)
					output.writeInt64(i, n);
			} else if (type.equals(boolean.class) || type.equals(Boolean.class)) {
				// 布尔
				boolean n = W.C.toBoolean(val);
				if (n != false)
					output.writeBool(i, n);
			} else if (type.equals(float.class) || type.equals(Float.class)) {
				// float型
				float n = W.C.toFloat(val);
				if (n != 0F)
					output.writeFloat(i, n);
			} else if (type.equals(double.class) || type.equals(Double.class)) {
				// Double型
				double n = W.C.toDouble(val);
				if (n != 0D)
					output.writeDouble(i, n);
			} 
//			else if (type.equals(ByteString.class)) {
//				// 字节字符串
//				ByteString n = (ByteString) val;
//				if (!n.isEmpty())
//					output.writeBytes(i, n);
//			} 
			else if (type.equals(byte[].class) || U.C.isBaseType(type)) {
				// 字节流
				byte[] n = W.B.toBytes(val);
				if (U.E.isNotEmpty(n))
					output.writeByteArray(i, n);
			}
//			else {
//				// 转成字节流
//				ByteString n = ByteString.copyFrom(W.B.toBytes(val));
//				if (!n.isEmpty())
//					output.writeBytes(i, n);
//			}
		}
	}

	private ProtobufEngine() {
	}
}
