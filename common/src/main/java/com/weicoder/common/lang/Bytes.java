package com.weicoder.common.lang;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.weicoder.common.binary.Binary;
import com.weicoder.common.binary.ByteArray;
import com.weicoder.common.binary.BytesBean;
import com.weicoder.common.constants.ArrayConstants;
import com.weicoder.common.io.ChannelUtil;
import com.weicoder.common.io.FileUtil;
import com.weicoder.common.io.IOUtil;
import com.weicoder.common.params.CommonParams;
import com.weicoder.common.util.BeanUtil;
import com.weicoder.common.util.CloseUtil;
import com.weicoder.common.util.EmptyUtil;
import com.weicoder.common.util.StringUtil;

/**
 * 字节数组操作
 * @author WD
 * @since JDK7
 * @version 1.0 2010-12-17
 */
public final class Bytes {
	// 使用使用Java算法
	private final static boolean	IS_HIGH;
	/** 零长度 */
	public final static byte[]		ZERO_SIZE	= new byte[] { 0, 0 };

	/**
	 * 静态初始化
	 */
	static {
		IS_HIGH = "high".equals(CommonParams.BYTES);
	}

	/**
	 * 转换Object变成字节数组
	 * @param obj d
	 * @return 字节数组
	 */
	public static byte[] toBytes(Object... objs) {
		// 获得数据长度
		int len = objs.length;
		// 声明字节数组
		byte[][] bs = new byte[len][];
		// 循环数组
		for (int i = 0; i < len; i++) {
			bs[i] = toBytes(objs[i]);
		}
		// 返回字节数组
		return add(bs);
	}

	/**
	 * 转换Collection变成字节数组
	 * @param obj d
	 * @return 字节数组
	 */
	public static byte[] toBytes(boolean b) {
		return new byte[] { (byte) (b ? 1 : 0) };
	}

	/**
	 * 转换Collection变成字节数组
	 * @param obj d
	 * @return 字节数组
	 */
	public static byte[] toBytes(Collection<?> c) {
		// 获得列表长度
		short size = Conversion.toShort(EmptyUtil.isEmpty(c) ? 0 : c.size());
		// 判断如果列表为0只返回长度
		return size == 0 ? toBytes(size) : toBytes(size, c.toArray());
	}

	/**
	 * 转换Object变成字节数组
	 * @param obj 对象
	 * @return 字节数组
	 */
	public static byte[] toBytes(Object obj) {
		// 声明字节数组
		byte[] b = ArrayConstants.BYTES_EMPTY;
		// 判断类型
		if (obj == null) {
			return ZERO_SIZE;
		} else if (obj instanceof byte[]) {
			// byte[]
			b = (byte[]) obj;
		} else if (obj instanceof Object[]) {
			// Byte
			b = toBytes((Object[]) obj);
		} else if (obj instanceof Collection<?>) {
			// Byte
			b = toBytes((Collection<?>) obj);
		} else if (obj instanceof Byte) {
			// Byte
			b = new byte[] { (Byte) obj };
		} else if (obj instanceof Integer) {
			// int
			b = toBytes(Conversion.toInt(obj));
		} else if (obj instanceof Long) {
			// Long
			b = toBytes(Conversion.toLong(obj));
		} else if (obj instanceof Float) {
			// float
			b = toBytes(Conversion.toFloat(obj));
		} else if (obj instanceof Double) {
			// Double
			b = toBytes(Conversion.toDouble(obj));
		} else if (obj instanceof Short) {
			// Short
			b = toBytes(Conversion.toShort(obj));
		} else if (obj instanceof Byte) {
			// Short
			b = new byte[] { (byte) (obj) };
		} else if (obj instanceof Boolean) {
			// Short
			b = toBytes(Conversion.toBoolean(obj));
		} else if (obj instanceof String) {
			// String
			b = toBytes(Conversion.toString(obj));
		} else if (obj instanceof ByteBuffer) {
			// String
			b = toBytes((ByteBuffer) obj);
		} else if (obj instanceof Binary) {
			// File
			b = toBytes((Binary) obj);
		} else if (obj instanceof BytesBean) {
			// File
			b = toBytes((BytesBean) obj);
		} else if (obj instanceof ByteArray) {
			// File
			b = toBytes((ByteArray) obj);
		} else if (obj instanceof File) {
			// File
			b = FileUtil.read((File) obj);
		} else if (obj instanceof InputStream) {
			// File
			b = IOUtil.read((InputStream) obj, false);
		} else if (obj instanceof ReadableByteChannel) {
			// File
			b = ChannelUtil.read((ReadableByteChannel) obj, false);
		} else if (obj instanceof Serializable) {
			// Serializable
			b = toBytes((Serializable) obj);
		} else {
			// Object调用toString()然后转换成byte[]
			b = StringUtil.toBytes(obj.toString());
		}
		// 返回字节数组
		return b;
	}

	/**
	 * 转换Binary序列化
	 * @param bean
	 * @return 字节数组
	 */
	public static byte[] toBytes(Binary binary) {
		// 对象为空
		if (EmptyUtil.isEmpty(binary)) {
			return ArrayConstants.BYTES_EMPTY;
		}
		// 字段值
		List<Object> values = Lists.getList();
		// 获得字段赋值
		for (Field field : BeanUtil.getFields(binary.getClass())) {
			if (!field.isSynthetic()) {
				values.add(BeanUtil.getFieldValue(binary, field));
			}
		}
		// 返回字节数组
		return Bytes.toBytes(values.toArray());
	}

	/**
	 * 转换ByteArray变成字节数组
	 * @param bean BytesBean类型
	 * @return 字节数组
	 */
	public static byte[] toBytes(ByteArray array) {
		return EmptyUtil.isEmpty(array) ? ArrayConstants.BYTES_EMPTY : array.array();
	}

	/**
	 * 转换BytesBean变成字节数组
	 * @param bean BytesBean类型
	 * @return 字节数组
	 */
	public static byte[] toBytes(BytesBean bean) {
		// 转换成字节数组
		byte[] b = toBytes((ByteArray) bean);
		// 加上长度返回
		return EmptyUtil.isEmpty(b) ? b : toBytes(bean.getClass().getName(), b.length, b);
	}

	/**
	 * 转换ByteBuffer变成字节数组
	 * @param buff ByteBuffer类型
	 * @return 字节数组
	 */
	public static byte[] toBytes(ByteBuffer buff) {
		// 如果为null
		if (buff == null) {
			return ArrayConstants.BYTES_EMPTY;
		}
		// 如果可以直接访问数组
		if (buff.hasArray()) {
			return buff.array();
		}
		// 如果读取长度大于0
		int len = buff.limit();
		if (len > 0) {
			// 读取全部字节返回
			byte[] dst = new byte[len];
			buff.get(dst);
			return dst;
		}
		// 返回空字节
		return ArrayConstants.BYTES_EMPTY;
	}

	/**
	 * 转换int变成字节数组
	 * @param i int类型
	 * @return 字节数组
	 */
	public static byte[] toBytes(int i) {
		// 声明字节数组
		byte[] b = new byte[4];
		// 使用什么算法
		if (IS_HIGH) {
			// 高位在前
			b[3] = (byte) (i & 0xFF);
			b[2] = (byte) ((i >> 8) & 0xFF);
			b[1] = (byte) ((i >> 16) & 0xFF);
			b[0] = (byte) ((i >> 24) & 0xFF);
		} else {
			// 低位在前
			b[0] = (byte) (i & 0xFF);
			b[1] = (byte) ((i >> 8) & 0xFF);
			b[2] = (byte) ((i >> 16) & 0xFF);
			b[3] = (byte) ((i >> 24) & 0xFF);
		}// 返回字节数组
		return b;
	}

	/**
	 * 把字节数组转换成int
	 * @param b 字节数组
	 * @return int
	 */
	public static int toInt(byte[] b) {
		return toInt(b, 0);
	}

	/**
	 * 把字节数组转换成int
	 * @param b 字节数组
	 * @return int
	 */
	public static byte toByte(byte[] b) {
		return toByte(b, 0);
	}

	/**
	 * 把字节数组转换成int
	 * @param b 字节数组
	 * @return int
	 */
	public static byte toByte(byte[] b, int offset) {
		return copy(b, offset, offset + 1)[0];
	}

	/**
	 * 把字节数组转换成int
	 * @param b 字节数组
	 * @return int
	 */
	public static boolean toBoolean(byte[] b) {
		return toBoolean(b, 0);
	}

	/**
	 * 把字节数组转换成int
	 * @param b 字节数组
	 * @return int
	 */
	public static boolean toBoolean(byte[] b, int offset) {
		return copy(b, offset, offset + 1)[0] == 1;
	}

	/**
	 * 把字节数组转换成int
	 * @param b 字节数组
	 * @param offset 偏移
	 * @return int
	 */
	public static int toInt(byte[] b, int offset) {
		// 声明int
		int i = 0;
		// 使用什么算法
		if (IS_HIGH) {
			// 高位在前
			i = b[offset + 0] & 0xFF;
			i = (i << 8) | (b[offset + 1] & 0xFF);
			i = (i << 8) | (b[offset + 2] & 0xFF);
			i = (i << 8) | (b[offset + 3] & 0xFF);
		} else {
			// 低位在前
			i = b[offset + 3] & 0xFF;
			i = (i << 8) | (b[offset + 2] & 0xFF);
			i = (i << 8) | (b[offset + 1] & 0xFF);
			i = (i << 8) | (b[offset + 0] & 0xFF);
		}
		// 返回整数
		return i;
	}

	/**
	 * 转换short变成字节数组
	 * @param s short类型
	 * @return 字节数组
	 */
	public static byte[] toBytes(short s) {
		// 声明数组
		byte[] b = new byte[2];
		// 使用什么算法
		if (IS_HIGH) {
			// 高位在前
			b[1] = (byte) (s & 0xFF);
			b[0] = (byte) ((s >> 8) & 0xFF);
		} else {
			// 低位在前
			b[0] = (byte) (s & 0xFF);
			b[1] = (byte) ((s >> 8) & 0xFF);
		}
		// 返回字节数组
		return b;
	}

	/**
	 * 把字节数组转换成short
	 * @param b 字节数组
	 * @return short
	 */
	public static short toShort(byte[] b) {
		return toShort(b, 0);
	}

	/**
	 * 把字节数组转换成short
	 * @param b 字节数组
	 * @param offset 偏移
	 * @return short
	 */
	public static short toShort(byte[] b, int offset) {
		// 声明返回值
		short s = 0;
		// 使用什么算法
		if (IS_HIGH) {
			// 高位在前
			s = (short) (b[offset + 0] & 0xFF);
			s = (short) ((s << 8) | (b[offset + 1] & 0xFF));
		} else {
			// 低位在前
			s = (short) (b[offset + 1] & 0xFF);
			s = (short) ((s << 8) | (b[offset + 0] & 0xFF));
		}
		// 返回整数
		return s;
	}

	/**
	 * 转换char变成字节数组
	 * @param c char类型
	 * @return 字节数组
	 */
	public static byte[] toBytes(char c) {
		return toBytes((short) c);
	}

	/**
	 * 把字节数组转换成char
	 * @param b 字节数组
	 * @return char
	 */
	public static char toChar(byte[] b) {
		return toChar(b, 0);
	}

	/**
	 * 把字节数组转换成char
	 * @param b 字节数组
	 * @param offset 偏移
	 * @return char
	 */
	public static char toChar(byte[] b, int offset) {
		return (char) toShort(b, offset);
	}

	/**
	 * 转换float变成字节数组
	 * @param f float类型
	 * @return 字节数组
	 */
	public static byte[] toBytes(float f) {
		return toBytes(Float.floatToIntBits(f));
	}

	/**
	 * 把字节数组转换成float
	 * @param b 字节数组
	 * @return float
	 */
	public static float toFloat(byte[] b) {
		return toFloat(b, 0);
	}

	/**
	 * 把字节数组转换成float
	 * @param b 字节数组
	 * @param offset 偏移
	 * @return float
	 */
	public static float toFloat(byte[] b, int offset) {
		return Float.intBitsToFloat(toInt(b, offset));
	}

	/**
	 * 转换double变成字节数组
	 * @param d double类型
	 * @return 字节数组
	 */
	public static byte[] toBytes(double d) {
		return toBytes(Double.doubleToLongBits(d));
	}

	/**
	 * 把字节数组转换成double
	 * @param b 字节数组
	 * @return double
	 */
	public static double toDouble(byte[] b) {
		return toDouble(b, 0);
	}

	/**
	 * 把字节数组转换成double
	 * @param b 字节数组
	 * @param offset 偏移
	 * @return double
	 */
	public static double toDouble(byte[] b, int offset) {
		return Double.longBitsToDouble(toLong(b, offset));
	}

	/**
	 * 转换long变成字节数组
	 * @param l long类型
	 * @return 字节数组
	 */
	public static byte[] toBytes(long l) {
		// 声明返回字节数组
		byte[] b = new byte[8];
		// 使用什么算法
		if (IS_HIGH) {
			// Java自带算法
			b[7] = (byte) (l & 0xFF);
			b[6] = (byte) ((l >> 8) & 0xFF);
			b[5] = (byte) ((l >> 16) & 0xFF);
			b[4] = (byte) ((l >> 24) & 0xFF);
			b[3] = (byte) ((l >> 32) & 0xFF);
			b[2] = (byte) ((l >> 40) & 0xFF);
			b[1] = (byte) ((l >> 48) & 0xFF);
			b[0] = (byte) ((l >> 56) & 0xFF);
		} else {
			// 低位在前
			b[0] = (byte) (l & 0xFF);
			b[1] = (byte) ((l >> 8) & 0xFF);
			b[2] = (byte) ((l >> 16) & 0xFF);
			b[3] = (byte) ((l >> 24) & 0xFF);
			b[4] = (byte) ((l >> 32) & 0xFF);
			b[5] = (byte) ((l >> 40) & 0xFF);
			b[6] = (byte) ((l >> 48) & 0xFF);
			b[7] = (byte) ((l >> 56) & 0xFF);
		}
		// 返回字节数组
		return b;
	}

	/**
	 * 把字节数组转换成long
	 * @param b 字节数组
	 * @return long
	 */
	public static long toLong(byte[] b) {
		return toLong(b, 0);
	}

	/**
	 * 把字节数组转换成long
	 * @param b 字节数组
	 * @param offset 偏移
	 * @return long
	 */
	public static long toLong(byte[] b, int offset) {
		// 返回整数
		long l = 0;
		// 使用什么算法
		if (IS_HIGH) {
			// 高位在前
			l = b[offset + 0] & 0xFF;
			l = (l << 8) | (b[offset + 1] & 0xFF);
			l = (l << 8) | (b[offset + 2] & 0xFF);
			l = (l << 8) | (b[offset + 3] & 0xFF);
			l = (l << 8) | (b[offset + 4] & 0xFF);
			l = (l << 8) | (b[offset + 5] & 0xFF);
			l = (l << 8) | (b[offset + 6] & 0xFF);
			l = (l << 8) | (b[offset + 7] & 0xFF);
		} else {
			// 低位在前
			l = b[offset + 7] & 0xFF;
			l = (l << 8) | (b[offset + 6] & 0xFF);
			l = (l << 8) | (b[offset + 5] & 0xFF);
			l = (l << 8) | (b[offset + 4] & 0xFF);
			l = (l << 8) | (b[offset + 3] & 0xFF);
			l = (l << 8) | (b[offset + 2] & 0xFF);
			l = (l << 8) | (b[offset + 1] & 0xFF);
			l = (l << 8) | (b[offset + 0] & 0xFF);
		}
		// 返回整数
		return l;
	}

	/**
	 * 转换String变成字节数组
	 * @param s String类型
	 * @return 字节数组
	 */
	public static byte[] toBytes(String s) {
		// 转换为字节数组
		byte[] b = StringUtil.toBytes(s);
		// 获得长度
		short size = Conversion.toShort(b.length);
		// 如果长度为0 只返回长度
		return size == 0 ? toBytes(size) : toBytes(size, b);
	}

	/**
	 * 把字节数组转换成long
	 * @param b 字节数组
	 * @param offset 偏移
	 * @return long
	 */
	public static String toString(byte[] b) {
		return toString(b, 0);
	}

	/**
	 * 把字节数组转换成字符串
	 * @param b 字节数组
	 * @param offset 偏移
	 * @return 字符串
	 */
	public static String toString(byte[] b, int offset) {
		return StringUtil.toString(copy(b, offset + 2, offset + 2 + toShort(b, offset)));
	}

	/**
	 * 拷贝字节数组
	 * @param b 字节数组
	 * @param offset 偏移
	 * @param len 长度
	 * @return 字节数组
	 */
	public static byte[] copy(byte[] b, int offset, int len) {
		return EmptyUtil.isEmpty(b) || (offset == 0 && b.length == len) ? b : Arrays.copyOfRange(b, offset, len);
	}

	/**
	 * 读取序列化后字节数组
	 * @param s 序列化对象
	 * @return 字节数组
	 */
	public static byte[] toBytes(Serializable s) {
		// 声明512字节的数组对象流
		ByteArrayOutputStream baos = new ByteArrayOutputStream(512);
		// 声明对象流
		ObjectOutputStream out = null;
		try {
			// 实例化一个对象流并写入对象
			out = new ObjectOutputStream(baos);
			out.writeObject(s);
			// 返回对象的序列化字节数组
			return baos.toByteArray();
		} catch (IOException ex) {} finally {
			CloseUtil.close(baos, out);
		}
		// 返回空字节数组
		return ArrayConstants.BYTES_EMPTY;
	}

	/**
	 * 把字节数组转换为BytesBean
	 * @param obj BytesBean对象
	 * @param b 字节数组
	 * @return 转换后的对象
	 */
	public static BytesBean toBean(byte[] b) {
		return toBean(b, 0, b.length);
	}

	/**
	 * 把字节数组转换为BytesBean
	 * @param obj BytesBean对象
	 * @param b 字节数组
	 * @return 转换后的对象
	 */
	public static BytesBean toBean(byte[] b, int offset) {
		return toBean(b, offset, b.length);
	}

	/**
	 * 把字节数组转换为BytesBean
	 * @param obj BytesBean对象
	 * @param b 字节数组
	 * @param offset 偏移
	 * @param len 长度
	 * @return 转换后的对象
	 */
	public static BytesBean toBean(byte[] b, int offset, int len) {
		// 如果字节流为空
		if (EmptyUtil.isEmpty(b)) {
			return null;
		}
		// 获得字节数组
		byte[] data = copy(b, offset, len);
		// 获得Bean的Class
		String name = toString(data);
		// Class名为空
		if (EmptyUtil.isEmpty(name)) {
			return null;
		}
		// 转行为BytesBean
		BytesBean bean = (BytesBean) BeanUtil.newInstance(name);
		// Bean为空
		if (bean == null) {
			return null;
		}
		// 设置偏移 6=4(字节数组)+2(字符串)
		int pos = 6 + name.length();
		// 返回Bean
		return (BytesBean) toBean(bean, data, pos);
	}

	/**
	 * 把字节数组转换为ByteArray
	 * @param array ByteArray 对象
	 * @param b 字节数组
	 * @return ByteArray对象
	 */
	public static ByteArray toBean(ByteArray array, byte[] b) {
		return toBean(array, b, 0, b.length);
	}

	/**
	 * 把字节数组转换为ByteArray
	 * @param array ByteArray 对象
	 * @param b 字节数组
	 * @param offset 偏移
	 * @return ByteArray对象
	 */
	public static ByteArray toBean(ByteArray array, byte[] b, int offset) {
		return toBean(array, b, offset, b.length);
	}

	/**
	 * 把字节数组转换为ByteArray
	 * @param array ByteArray 对象
	 * @param b 字节数组
	 * @param offset 偏移
	 * @param len 长度
	 * @return ByteArray对象
	 */
	public static ByteArray toBean(ByteArray array, byte[] b, int offset, int len) {
		return array.array(copy(b, offset, len));
	}

	/**
	 * 读取字节数组变成对象
	 * @param b 字节数组
	 * @return 对象
	 */
	public static <B extends Binary> B toBinary(B binary, byte[] b) {
		// 获得全部字段
		List<Field> fields = BeanUtil.getFields(binary.getClass());
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
					BeanUtil.setFieldValue(binary, field, Bytes.toInt(b, offset));
					offset += 4;
				} else if (type.equals(Long.class) || type.equals(long.class)) {
					BeanUtil.setFieldValue(binary, field, Bytes.toLong(b, offset));
					offset += 8;
				} else if (type.equals(Double.class) || type.equals(double.class)) {
					BeanUtil.setFieldValue(binary, field, Bytes.toDouble(b, offset));
					offset += 8;
				} else if (type.equals(Float.class) || type.equals(float.class)) {
					BeanUtil.setFieldValue(binary, field, Bytes.toFloat(b, offset));
					offset += 4;
				} else if (type.equals(Short.class) || type.equals(short.class)) {
					BeanUtil.setFieldValue(binary, field, Bytes.toShort(b, offset));
					offset += 2;
				} else if (type.equals(Byte.class) || type.equals(byte.class)) {
					BeanUtil.setFieldValue(binary, field, Bytes.toByte(b, offset));
					offset += 1;
				} else if (type.equals(Boolean.class) || type.equals(boolean.class)) {
					BeanUtil.setFieldValue(binary, field, Bytes.toBoolean(b, offset));
					offset += 1;
				} else if (type.equals(String.class)) {
					String s = Bytes.toString(b, offset);
					BeanUtil.setFieldValue(binary, field, s);
					offset += Bytes.toShort(b, offset) + 2;
				} else if (type.isAssignableFrom(BytesBean.class)) {
					// 转换为BytesBean
					BytesBean bean = Bytes.toBean(b, offset);
					BeanUtil.setFieldValue(binary, field, bean);
					// 类的字符串长度
					offset += 2 + Bytes.toShort(b, offset);
					// 字节数组长度
					offset += 4 + Bytes.toInt(b, offset);
				} else if (type.isAssignableFrom(ByteArray.class)) {
					// 转换为BytesBean
					ByteArray bean = Bytes.toBean((ByteArray) BeanUtil.newInstance(type), b, offset);
					BeanUtil.setFieldValue(binary, field, bean);
					// 字节数组长度
					offset += bean.array().length;
				} else if (type.equals(byte[].class)) {
					// 字节数组会获得后面全部的 所以一般这个类型也是本类的最后一个字段
					byte[] t = Bytes.copy(b, offset, b.length);
					BeanUtil.setFieldValue(binary, field, t);
					offset += t.length;
				}
			}
		}
		// 返回对象
		return binary;
	}

	/**
	 * 读取字节数组变成对象
	 * @param b 字节数组
	 * @return 对象
	 */
	public static Object toObject(byte[] b) {
		// 如果字节数组为空 返回null
		if (EmptyUtil.isEmpty(b)) {
			return null;
		}
		// 声明字节数组输入流
		ByteArrayInputStream bais = new ByteArrayInputStream(b);
		// 声明对象输入流
		ObjectInputStream in = null;
		try {
			// 声明对象输入流
			in = new ObjectInputStream(bais);
			// 返回对象
			return in.readObject();
		} catch (Exception ex) {
			// 返回null
			return null;
		} finally {
			CloseUtil.close(bais, in);
		}
	}

	/**
	 * 字节数组相连
	 * @param bs 字节数组
	 * @return 相连后的数组
	 */
	public static byte[] add(byte[]... bs) {
		// 判断字节数组是否为空
		if (!EmptyUtil.isEmpty(bs)) {
			// 获得所有字节数组长度
			int len = 0;
			for (int i = 0; i < bs.length; i++) {
				len += bs[i].length;
			}
			// 声明需要的字节数组
			byte[] b = new byte[len];
			// 声明字节数组用于循环
			byte[] d = null;
			// 声明偏移
			int pos = 0;
			// 循环拷贝数组
			for (int i = 0; i < bs.length; i++) {
				// 获得字节数组
				d = bs[i];
				// 拷贝数组
				System.arraycopy(d, 0, b, pos, d.length);
				// 加偏移量
				pos += d.length;
			}
			// 返回相连的数组
			return b;
		}
		return ArrayConstants.BYTES_EMPTY;
	}

	/**
	 * 私有构造
	 */
	private Bytes() {}
}