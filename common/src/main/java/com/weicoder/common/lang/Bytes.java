package com.weicoder.common.lang;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.util.Arrays;
import java.util.List;

import com.weicoder.common.U;
import com.weicoder.common.W;
import com.weicoder.common.binary.Binary;
import com.weicoder.common.binary.Buffer;
import com.weicoder.common.binary.ByteArray;
import com.weicoder.common.constants.ArrayConstants;
import com.weicoder.common.io.ChannelUtil;
import com.weicoder.common.io.FileUtil;
import com.weicoder.common.io.IOUtil;
import com.weicoder.common.params.CommonParams;
import com.weicoder.common.util.BeanUtil;
import com.weicoder.common.util.ClassUtil;
import com.weicoder.common.util.StringUtil;

/**
 * 字节数组操作
 * 
 * @author WD
 */
public class Bytes {
	// 使用高地位算法
	private final static boolean IS_HIGH = "high".equals(CommonParams.BYTES);

	/**
	 * 是否支持序列化类型
	 * 
	 * @param c 类
	 * @return 是否支持序列化
	 */
	public static boolean isType(Class<?> c) {
		// 判断类型
		if (c == null)
			return false;
		// 支持类型
		if (byte[].class == c || Byte[].class == c || String.class == c || Integer.class == c || int.class == c || Long.class == c || long.class == c
				|| Float.class == c || float.class == c || Double.class == c || double.class == c || Short.class == c || short.class == c || Byte.class == c
				|| byte.class == c || Boolean.class == c || boolean.class == c || c == Buffer.class || c.isAssignableFrom(ByteArray.class))
			return true;
		// 不支持
		return false;
	}

	/**
	 * 根据c类型反序列化
	 * 
	 * @param b 要转换的对象
	 * @param o 偏移量 数组0就可以 定义成数组是为了引用传递
	 * @param c 要转换的类型
	 * @return 转换后的对象
	 */
	public static Object to(byte[] b, Class<?> c) {
		// 判断类型
		if (c == null || U.E.isEmpty(b))
			return b;
		if (byte[].class == c || Byte[].class == c)
			return b;
		if (String.class == c)
			return toString(b);
		if (Integer.class == c || int.class == c)
			return toInt(b);
		if (Long.class == c || long.class == c)
			return toLong(b);
		if (Float.class == c || float.class == c)
			return toFloat(b);
		if (Double.class == c || double.class == c)
			return toDouble(b);
		if (Short.class == c || short.class == c)
			return toShort(b);
		if (Byte.class == c || byte.class == c)
			return toByte(b);
		if (Boolean.class == c || boolean.class == c)
			return toBoolean(b);
		if (c == Buffer.class)
			return new Buffer(b);
		if (c.isAssignableFrom(ByteArray.class))
			return toBean((ByteArray) ClassUtil.newInstance(c), b);
		return toBinary(b, c);
	}

	/**
	 * 转换Object变成字节数组
	 * 
	 * @param objs 对象
	 * @return 字节数组
	 */
	public static byte[] toBytes(Object... objs) {
		return toBytes(Boolean.FALSE, objs);
	}

	/**
	 * 转换Object变成字节数组
	 * 
	 * @param objs 对象
	 * @return 字节数组
	 */
	public static byte[] toBytes(Boolean is, Object... objs) {
		// 获得数据长度
		int len = objs.length;
		// 声明字节数组
		byte[][] bs = new byte[len][];
		// 循环数组
		for (int i = 0; i < len; i++)
			bs[i] = toBytes(is, objs[i]);
		// 返回字节数组
		return add(bs);
	}

	/**
	 * 转换Collection变成字节数组
	 * 
	 * @param b 布尔
	 * @return 字节数组
	 */
	public static byte[] toBytes(boolean b) {
		return new byte[] { (byte) (b ? 1 : 0) };
	}

//	/**
//	 * 转换Collection变成字节数组
//	 * 
//	 * @param  c 集合
//	 * @return   字节数组
//	 */
//	public static byte[] toBytes(Collection<?> c) {
//		// 获得列表长度
//		short size = W.C.toShort(U.E.isEmpty(c) ? 0 : c.size());
//		// 判断如果列表为0只返回长度
//		return size == 0 ? toBytes(size) : toBytes(size, c.toArray());
//	}

	/**
	 * 转换Object变成字节数组
	 * 
	 * @param obj 对象
	 * @return 字节数组
	 */
	public static byte[] toBytes(Object obj) {
		return toBytes(false, obj);
	}

	/**
	 * 转换Object变成字节数组
	 * 
	 * @param obj 对象
	 * @return 字节数组
	 */
	public static byte[] toBytes(boolean is, Object obj) {
		// 判断类型
		if (obj == null)
			return ArrayConstants.BYTES_EMPTY;
		if (obj instanceof byte[])
			// byte[]
			return (byte[]) obj;
		if (obj instanceof Object[])
			// Byte
			return toBytes((Object[]) obj);
//		if (obj instanceof Collection<?>)
//			// Byte
//			return toBytes((Collection<?>) obj);
		if (obj instanceof Byte)
			// Byte
			return new byte[] { (Byte) obj };
		if (obj instanceof Integer)
			// int
			return toBytes(W.C.toInt(obj));
		if (obj instanceof Long)
			// Long
			return toBytes(W.C.toLong(obj));
		if (obj instanceof Float)
			// float
			return toBytes(W.C.toFloat(obj));
		if (obj instanceof Double)
			// Double
			return toBytes(W.C.toDouble(obj));
		if (obj instanceof Short)
			// Short
			return toBytes(W.C.toShort(obj));
		if (obj instanceof Byte)
			// Short
			return new byte[] { (byte) (obj) };
		if (obj instanceof Boolean)
			// Short
			return toBytes(W.C.toBoolean(obj));
		if (obj instanceof String)
			// String
			return toBytes(W.C.toString(obj), is);
		if (obj instanceof ByteBuffer)
			// String
			return toBytes((ByteBuffer) obj);
		if (obj instanceof ByteArray)
			// File
			return toBytes((ByteArray) obj);
		if (obj instanceof Binary || obj.getClass().isAssignableFrom(Binary.class))
			// File
			return toBytes((Binary) obj);
		if (obj instanceof File)
			// File
			return FileUtil.read((File) obj);
		if (obj instanceof InputStream)
			// File
			return IOUtil.read((InputStream) obj, false);
		if (obj instanceof ReadableByteChannel)
			// File
			return ChannelUtil.read((ReadableByteChannel) obj, false);
		// 直接按binary接口处理
		return binary(obj);
	}

	/**
	 * 转换Binary序列化
	 * 
	 * @param binary Binary接口
	 * @return 字节数组
	 */
	public static byte[] toBytes(Binary binary) {
		return binary(binary);
	}

	/**
	 * 转换Binary序列化
	 * 
	 * @param binary Binary接口
	 * @return 字节数组
	 */
	public static byte[] binary(Object binary) {
		// 对象为空
		if (U.E.isEmpty(binary))
			return ArrayConstants.BYTES_EMPTY;
		// 字段值
		List<Object> values = Lists.newList();
		// 获得字段赋值
		for (Field field : BeanUtil.getFields(binary.getClass()))
			if (!field.isSynthetic())
				values.add(BeanUtil.getFieldValue(binary, field));
		// 返回字节数组
		return Bytes.toBytes(true, values.toArray());
	}

	/**
	 * 转换ByteArray变成字节数组
	 * 
	 * @param array ByteArray类型
	 * @return 字节数组
	 */
	public static byte[] toBytes(ByteArray array) {
		return U.E.isEmpty(array) ? ArrayConstants.BYTES_EMPTY : array.array();
	}

	/**
	 * 转换ByteBuffer变成字节数组
	 * 
	 * @param buff ByteBuffer类型
	 * @return 字节数组
	 */
	public static byte[] toBytes(ByteBuffer buff) {
		// 如果为null
		if (buff == null)
			return ArrayConstants.BYTES_EMPTY;
		// 如果可以直接访问数组
		if (buff.hasArray())
			return buff.array();
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
	 * 
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
		} // 返回字节数组
		return b;
	}

	/**
	 * 把字节数组转换成int
	 * 
	 * @param b 字节数组
	 * @return int
	 */
	public static int toInt(byte[] b) {
		return toInt(b, 0);
	}

	/**
	 * 把字节数组转换成int
	 * 
	 * @param b 字节数组
	 * @return int
	 */
	public static byte toByte(byte[] b) {
		return toByte(b, 0);
	}

	/**
	 * 把字节数组转换成int
	 * 
	 * @param b      字节数组
	 * @param offset 偏移
	 * @return int
	 */
	public static byte toByte(byte[] b, int offset) {
		return copy(b, offset, offset + 1)[0];
	}

	/**
	 * 把字节数组转换成int
	 * 
	 * @param b 字节数组
	 * @return int
	 */
	public static boolean toBoolean(byte[] b) {
		return toBoolean(b, 0);
	}

	/**
	 * 把字节数组转换成int
	 * 
	 * @param b      字节数组
	 * @param offset 偏移
	 * @return int
	 */
	public static boolean toBoolean(byte[] b, int offset) {
		return b[offset] == 1;
	}

	/**
	 * 把字节数组转换成int
	 * 
	 * @param b      字节数组
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
	 * 
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
	 * 
	 * @param b 字节数组
	 * @return short
	 */
	public static short toShort(byte[] b) {
		return toShort(b, 0);
	}

	/**
	 * 把字节数组转换成short
	 * 
	 * @param b      字节数组
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
	 * 
	 * @param c char类型
	 * @return 字节数组
	 */
	public static byte[] toBytes(char c) {
		return toBytes((short) c);
	}

	/**
	 * 把字节数组转换成char
	 * 
	 * @param b 字节数组
	 * @return char
	 */
	public static char toChar(byte[] b) {
		return toChar(b, 0);
	}

	/**
	 * 把字节数组转换成char
	 * 
	 * @param b      字节数组
	 * @param offset 偏移
	 * @return char
	 */
	public static char toChar(byte[] b, int offset) {
		return (char) toShort(b, offset);
	}

	/**
	 * 转换float变成字节数组
	 * 
	 * @param f float类型
	 * @return 字节数组
	 */
	public static byte[] toBytes(float f) {
		return toBytes(Float.floatToIntBits(f));
	}

	/**
	 * 把字节数组转换成float
	 * 
	 * @param b 字节数组
	 * @return float
	 */
	public static float toFloat(byte[] b) {
		return toFloat(b, 0);
	}

	/**
	 * 把字节数组转换成float
	 * 
	 * @param b      字节数组
	 * @param offset 偏移
	 * @return float
	 */
	public static float toFloat(byte[] b, int offset) {
		return Float.intBitsToFloat(toInt(b, offset));
	}

	/**
	 * 转换double变成字节数组
	 * 
	 * @param d double类型
	 * @return 字节数组
	 */
	public static byte[] toBytes(double d) {
		return toBytes(Double.doubleToLongBits(d));
	}

	/**
	 * 把字节数组转换成double
	 * 
	 * @param b 字节数组
	 * @return double
	 */
	public static double toDouble(byte[] b) {
		return toDouble(b, 0);
	}

	/**
	 * 把字节数组转换成double
	 * 
	 * @param b      字节数组
	 * @param offset 偏移
	 * @return double
	 */
	public static double toDouble(byte[] b, int offset) {
		return Double.longBitsToDouble(toLong(b, offset));
	}

	/**
	 * 转换long变成字节数组
	 * 
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
	 * 
	 * @param b 字节数组
	 * @return long
	 */
	public static long toLong(byte[] b) {
		return toLong(b, 0);
	}

	/**
	 * 把字节数组转换成long
	 * 
	 * @param b    字节数组
	 * @param high true 高位在前 false 低位在前
	 * @return long
	 */
	public static long toLong(byte[] b, boolean high) {
		return toLong(b, 0, high);
	}

	/**
	 * 把字节数组转换成long
	 * 
	 * @param b      字节数组
	 * @param offset 偏移
	 * @return long
	 */
	public static long toLong(byte[] b, int offset) {
		return toLong(b, offset, IS_HIGH);
	}

	/**
	 * 把字节数组转换成long
	 * 
	 * @param b      字节数组
	 * @param offset 偏移
	 * @param high   true 高位在前 false 低位在前
	 * @return long
	 */
	public static long toLong(byte[] b, int offset, boolean high) {
		// 返回整数
		long l = 0;
		// 使用什么算法
		if (high) {
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
	 * 
	 * @param s String类型
	 * @return 字节数组
	 */
	public static byte[] toBytes(String s) {
		return toBytes(s, false);
	}

	/**
	 * 转换String变成字节数组
	 * 
	 * @param s  String类型
	 * @param is 是否加上长度
	 * @return 字节数组
	 */
	public static byte[] toBytes(String s, boolean is) {
		// 转换为字节数组
		byte[] b = StringUtil.toBytes(s);
		if (is) {
			// 获得长度
			short size = W.C.toShort(b.length);
			// 如果长度为0 只返回长度
			return size == 0 ? toBytes(size) : toBytes(size, b);
		}
		// 返回字节数组
		return b;
	}

	/**
	 * 把字节数组转换成long
	 * 
	 * @param b 字节数组
	 * @return long
	 */
	public static String toString(byte[] b) {
		return toString(b, 0);
	}

	/**
	 * 把字节数组转换成字符串
	 * 
	 * @param b      字节数组
	 * @param offset 偏移
	 * @return 字符串
	 */
	public static String toString(byte[] b, int offset) {
		return toString(b, offset, false);
	}

	/**
	 * 把字节数组转换成字符串
	 * 
	 * @param b      字节数组
	 * @param offset 偏移
	 * @param is     是否读取长度
	 * @return 字符串
	 */
	public static String toString(byte[] b, int offset, boolean is) {
		return StringUtil.toString(is ? copy(b, offset + 2, offset + 2 + toShort(b, offset)) : copy(b, offset, b.length));
	}

	/**
	 * 拷贝字节数组
	 * 
	 * @param b 字节数组
	 * @return 字节数组
	 */
	public static byte[] copy(byte[] b) {
		return copy(b, b.length);
	}

	/**
	 * 拷贝字节数组
	 * 
	 * @param b   字节数组
	 * @param len 长度
	 * @return 字节数组
	 */
	public static byte[] copy(byte[] b, int len) {
		return Arrays.copyOf(b, len);
	}

	/**
	 * 拷贝字节数组
	 * 
	 * @param b      字节数组
	 * @param offset 偏移
	 * @param len    长度
	 * @return 字节数组
	 */
	public static byte[] copy(byte[] b, int offset, int len) {
		return U.E.isEmpty(b) || (offset == 0 && b.length == len) ? b : Arrays.copyOfRange(b, offset, len);
	}

	/**
	 * 把字节数组转换为ByteArray
	 * 
	 * @param array ByteArray 对象
	 * @param b     字节数组
	 * @return ByteArray对象
	 */
	public static ByteArray toBean(ByteArray array, byte[] b) {
		return toBean(array, b, 0, b.length);
	}

	/**
	 * 把字节数组转换为ByteArray
	 * 
	 * @param array  ByteArray 对象
	 * @param b      字节数组
	 * @param offset 偏移
	 * @return ByteArray对象
	 */
	public static ByteArray toBean(ByteArray array, byte[] b, int offset) {
		return toBean(array, b, offset, b.length);
	}

	/**
	 * 把字节数组转换为ByteArray
	 * 
	 * @param array  ByteArray 对象
	 * @param b      字节数组
	 * @param offset 偏移
	 * @param len    长度
	 * @return ByteArray对象
	 */
	public static ByteArray toBean(ByteArray array, byte[] b, int offset, int len) {
		return array.array(copy(b, offset, len));
	}

	/**
	 * 读取字节数组变成对象
	 * 
	 * @param c   序列化类
	 * @param b   字节数组
	 * @param <B> 泛型
	 * @return 对象
	 */
	public static <B> B toBinary(byte[] b, Class<B> c) {
		// 实例化
		B binary = ClassUtil.newInstance(c);
		// 获得全部字段
		List<Field> fields = BeanUtil.getFields(c);
		// 偏移
		int offset = 0;
		// 循环设置字段值
		for (Field field : fields) {
			// 如果偏移与字节长度相同 没有数据 跳出
			if (b.length <= offset)
				break;
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
				} else if (type.isAssignableFrom(ByteArray.class)) {
					// 转换为BytesBean
					ByteArray bean = Bytes.toBean((ByteArray) ClassUtil.newInstance(type), b, offset);
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
	 * 字节数组相连
	 * 
	 * @param bs 字节数组
	 * @return 相连后的数组
	 */
	public static byte[] add(byte[]... bs) {
		// 判断字节数组是否为空
		if (U.E.isNotEmpty(bs)) {
			// 获得所有字节数组长度
			int len = 0;
			for (int i = 0; i < bs.length; i++)
				len += bs[i].length;
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
}