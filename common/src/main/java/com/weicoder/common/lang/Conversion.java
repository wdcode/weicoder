package com.weicoder.common.lang;

import java.math.BigDecimal;
import java.util.Arrays;

import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.util.MathUtil;
import com.weicoder.common.util.U;

/**
 * 数据类型转换,对null和异常进行处理
 * 
 * @author WD
 */
public class Conversion {
	/**
	 * 根据传入的值判断返回
	 * 
	 * @param <E> 泛型
	 * @param val 值
	 * @param def 默认
	 * @return val为空时返回默认def
	 */
	public static <E> E value(E val, E def) {
		return U.E.isEmpty(val) ? def : val;
	}

	/**
	 * 转换dest到src同类型
	 * 
	 * @param obj 要转换的对象
	 * @param c   要转换的类型
	 * @return 转换后的对象
	 */
	public static Object to(Object obj, Class<?> c) {
		// 判断类型
		if (c == null)
			return obj;
		if (String.class == c)
			return toString(obj);
		if (Integer.class == c || int.class == c)
			return toInt(obj);
		if (Long.class == c || long.class == c)
			return toLong(obj);
		if (Float.class == c || float.class == c)
			return toFloat(obj);
		if (Double.class == c || double.class == c)
			return toDouble(obj);
		if (Short.class == c || short.class == c)
			return toShort(obj);
		if (Byte.class == c || byte.class == c)
			return toByte(obj);
		if (BigDecimal.class == c)
			return toBigDecimal(obj);
		if (Boolean.class == c || boolean.class == c)
			return toBoolean(obj);
		// 返回原类型
		return obj;
	}

	/**
	 * 转换Object到字符串,如果参数为null返回"",否则返回obj.toString()
	 * 
	 * @param obj 要转换为字符串的对象
	 * @return 转换后的字符串
	 */
	public static String toString(Object obj) {
		return toString(obj, StringConstants.EMPTY);
	}

	/**
	 * 转换Object到字符串,如果参数为null返回str,否则返回obj.toString()
	 * 
	 * @param obj          要转换为字符串的对象
	 * @param defaultValue 默认值
	 * @return 转换后的字符串
	 */
	public static String toString(Object obj, String defaultValue) {
		return U.E.isEmpty(obj) ? defaultValue : obj instanceof Object[] ? Arrays.toString(((Object[]) obj)) : obj.toString();
	}

	/**
	 * 转换Object为int,obj必须为能转换成int的对象,如果转换失败将返回0
	 * 
	 * @param obj 要转换的对象
	 * @return 转换后的数字
	 */
	public static int toInt(Object obj) {
		return toInt(obj, 0);
	}

	/**
	 * 转换Object为int,obj必须为能转换成int的对象,如果转换失败将返回i
	 * 
	 * @param obj          要转换的对象
	 * @param defaultValue 默认值
	 * @return 转换后的数字
	 */
	public static int toInt(Object obj, int defaultValue) {
		try {
			// 判断对象类型
			if (U.E.isEmpty(obj))
				return defaultValue;
			if (obj.getClass().isArray())
				return toInt(((Object[]) obj)[0], defaultValue);
			if (obj instanceof Number)
				return ((Number) obj).intValue();
			if (obj instanceof Boolean)
				return ((Boolean) obj).booleanValue() ? 1 : 0;
			if (obj instanceof Character)
				return ((Character) obj).charValue();
			return MathUtil.add(obj).intValue();
		} catch (RuntimeException e) {
			return defaultValue;
		}
	}

	/**
	 * 转换Object为long,obj必须为能转换成long的对象,如果转换失败将返回0
	 * 
	 * @param obj 要转换的对象
	 * @return 转换后的数字
	 */
	public static long toLong(Object obj) {
		return toLong(obj, 0);
	}

	/**
	 * 转换Object为long,obj必须为能转换成long的对象,如果转换失败将返回num
	 * 
	 * @param obj          要转换的对象
	 * @param defaultValue 默认值
	 * @return 转换后的数字
	 */
	public static long toLong(Object obj, long defaultValue) {
		try {
			// 判断对象类型
			if (U.E.isEmpty(obj))
				return defaultValue;
			if (obj instanceof Number)
				// Number
				return ((Number) obj).longValue();
			if (obj instanceof Boolean)
				// Boolean
				return ((Boolean) obj).booleanValue() ? 1 : 0;
			if (obj instanceof Character)
				return ((Character) obj).charValue();
			return MathUtil.add(obj).longValue();
		} catch (RuntimeException e) {
			return defaultValue;
		}
	}

	/**
	 * 转换Object为float,obj必须为能转换成float的对象,如果转换失败将返回0
	 * 
	 * @param obj 要转换的对象
	 * @return 转换后的数字
	 */
	public static float toFloat(Object obj) {
		return toFloat(obj, 0);
	}

	/**
	 * 转换Object为float,obj必须为能转换成float的对象,如果转换失败将返回num
	 * 
	 * @param obj          要转换的对象
	 * @param defaultValue 默认值
	 * @return 转换后的数字
	 */
	public static float toFloat(Object obj, float defaultValue) {
		try {
			// 判断对象类型
			if (U.E.isEmpty(obj))
				return defaultValue;
			if (obj instanceof Number)
				// Number
				return ((Number) obj).floatValue();
			if (obj instanceof String)
				// String
				return Float.parseFloat(obj.toString());
			if (obj instanceof Boolean)
				// Boolean
				return ((Boolean) obj).booleanValue() ? 1 : 0;
			if (obj instanceof Character)
				return ((Character) obj).charValue();
			// 普通对象 转换成String 在返回
			return Float.parseFloat(toString(obj));
		} catch (RuntimeException e) {
			return defaultValue;
		}
	}

	/**
	 * 转换Object为double,obj必须为能转换成double的对象,如果转换失败将返回0
	 * 
	 * @param obj 要转换的对象
	 * @return 转换后的数字
	 */
	public static double toDouble(Object obj) {
		return toDouble(obj, 0);
	}

	/**
	 * 转换Object为double,obj必须为能转换成double的对象,如果转换失败将返回num
	 * 
	 * @param obj          要转换的对象
	 * @param defaultValue 默认值
	 * @return 转换后的数字
	 */
	public static double toDouble(Object obj, double defaultValue) {
		try {
			// 判断对象类型
			if (U.E.isEmpty(obj))
				return defaultValue;
			if (obj instanceof Number)
				// Number
				return ((Number) obj).doubleValue();
			if (obj instanceof String)
				// String
				return Double.parseDouble(obj.toString());
			if (obj instanceof Boolean)
				// Boolean
				return ((Boolean) obj).booleanValue() ? 1 : 0;
			if (obj instanceof Character)
				return ((Character) obj).charValue();
			// 普通对象 转换成String 在返回
			return Double.parseDouble(toString(obj));
		} catch (RuntimeException e) {
			return defaultValue;
		}
	}

	/**
	 * 转换Object为short,obj必须为能转换成short的对象,如果转换失败将返回0
	 * 
	 * @param obj 要转换的对象
	 * @return 转换后的数字
	 */
	public static short toShort(Object obj) {
		return toShort(obj, (short) 0);
	}

	/**
	 * 转换Object为short,obj必须为能转换成short的对象,如果转换失败将返回num
	 * 
	 * @param obj          要转换的对象
	 * @param defaultValue 默认值
	 * @return 转换后的数字
	 */
	public static short toShort(Object obj, short defaultValue) {
		try {
			// 判断对象类型
			if (U.E.isEmpty(obj))
				return defaultValue;
			if (obj instanceof Number)
				// Number
				return ((Number) obj).shortValue();
			if (obj instanceof String)
				// String
				return Short.parseShort(obj.toString());
			if (obj instanceof Boolean)
				// Boolean
				return (short) (((Boolean) obj).booleanValue() ? 1 : 0);
			if (obj instanceof Character)
				return (short) (((Character) obj).charValue());
			// 普通对象 转换成String 在返回
			return Short.parseShort(toString(obj));
		} catch (RuntimeException e) {
			return defaultValue;
		}
	}

	/**
	 * 转换Object为byte,obj必须为能转换成byte的对象,如果转换失败将返回0
	 * 
	 * @param obj 要转换的对象
	 * @return 转换后的数字
	 */
	public static byte toByte(Object obj) {
		return toByte(obj, (byte) 0);
	}

	/**
	 * 转换Object为byte,obj必须为能转换成byte的对象,如果转换失败将返回num
	 * 
	 * @param obj          要转换的对象
	 * @param defaultValue 默认值
	 * @return 转换后的数字
	 */
	public static byte toByte(Object obj, byte defaultValue) {
		try {
			// 判断对象类型
			if (U.E.isEmpty(obj))
				return defaultValue;
			if (obj instanceof Number)
				// Number
				return ((Number) obj).byteValue();
			if (obj instanceof String)
				// String
				return Byte.parseByte(obj.toString());
			if (obj instanceof Boolean)
				// Boolean
				return (byte) (((Boolean) obj).booleanValue() ? 1 : 0);
			if (obj instanceof Character)
				return (byte) (((Character) obj).charValue());
			// 普通对象 转换成String 在返回
			return Byte.parseByte(toString(obj));
		} catch (RuntimeException e) {
			return defaultValue;
		}
	}

	/**
	 * 转换Object为byte,obj必须为能转换成byte的对象,如果转换失败将返回0
	 * 
	 * @param obj 要转换的对象
	 * @return 转换后的数字
	 */
	public static BigDecimal toBigDecimal(Object obj) {
		return toBigDecimal(obj, BigDecimal.ZERO);
	}

	/**
	 * 转换Object为byte,obj必须为能转换成byte的对象,如果转换失败将返回num
	 * 
	 * @param obj          要转换的对象
	 * @param defaultValue 默认值
	 * @return 转换后的数字
	 */
	public static BigDecimal toBigDecimal(Object obj, BigDecimal defaultValue) {
		try {
			// 判断对象类型
			if (U.E.isEmpty(obj))
				return defaultValue;
			if (obj instanceof BigDecimal)
				// String
				return (BigDecimal) obj;
			if (obj instanceof Long || obj instanceof Integer)
				// Long Integer
				return BigDecimal.valueOf(toLong(obj));
			if (obj instanceof Double || obj instanceof Float)
				// Double Float
				return BigDecimal.valueOf(toDouble(obj));
			if (obj instanceof String)
				// String
				return new BigDecimal(obj.toString());
			if (obj instanceof Boolean)
				// Boolean
				return ((Boolean) obj).booleanValue() ? BigDecimal.ONE : BigDecimal.ZERO;
			if (obj instanceof Character)
				return new BigDecimal(((Character) obj).charValue());
			// 普通对象 转换成String 在返回
			return new BigDecimal(toString(obj));
		} catch (RuntimeException e) {
			return defaultValue;
		}
	}

	/**
	 * 转换Object为boolean 转换失败或不能转换返回 false
	 * 
	 * @param obj 转换为boolean的对象
	 * @return true false
	 */
	public static boolean toBoolean(Object obj) {
		return toBoolean(obj, false);
	}

	/**
	 * 转换Object为boolean 转换失败或不能转换返回 defaultValue
	 * 
	 * @param obj          转换为boolean的对象
	 * @param defaultValue 默认值
	 * @return true false
	 */
	public static boolean toBoolean(Object obj, boolean defaultValue) {
		try {
			// 判断类型
			if (U.E.isEmpty(obj))
				return defaultValue;
			if (obj instanceof Boolean)
				// Boolean
				return ((Boolean) obj).booleanValue();
			if (obj instanceof String) {
				// String
				String is = obj.toString();
				return "true".equalsIgnoreCase(is) || "yes".equalsIgnoreCase(is) || "ok".equalsIgnoreCase(is);
			}
			if (obj instanceof Number)
				// Number
				return ((Number) obj).intValue() > 0 ? true : false;
			// 其它类型先转换成String 在转成Boolean
			return Boolean.parseBoolean(toString(obj));
		} catch (RuntimeException e) {
			return defaultValue;
		}
	}

	/**
	 * 如果str等于""返回null,否则返回它本身
	 * 
	 * @param str 要对比的字符串
	 * @return 比较后的字符串
	 */
	public static String stringToNull(String str) {
		return stringToNull(str, StringConstants.EMPTY);
	}

	/**
	 * 如果str等于eq返回null,否则返回它本身
	 * 
	 * @param str 要对比的字符串
	 * @param eq  对比字符串
	 * @return 比较后的字符串
	 */
	public static String stringToNull(String str, String eq) {
		return toString(eq).equals(str) ? null : str;
	}
}
