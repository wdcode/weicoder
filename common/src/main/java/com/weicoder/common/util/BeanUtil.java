package com.weicoder.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.weicoder.common.constants.StringConstants;

import com.weicoder.common.lang.Conversion;
import com.weicoder.common.lang.Lists;
import com.weicoder.common.log.Logs;

/**
 * Bean工具类
 * @author WD
 */
public final class BeanUtil {
	/**
	 * 拷贝属性
	 * @param source 原对象
	 * @param entity 目标类
	 * @param <T> 泛型
	 * @return 目标对象
	 */
	public static <T> T copy(Object source, Class<T> entity) {
		return copy(source, newInstance(entity));
	}

	/**
	 * 拷贝属性
	 * @param source 原对象
	 * @param target 目标对象
	 * @param <T> 泛型
	 * @return 目标对象
	 */
	@SuppressWarnings("unchecked")
	public static <T> T copy(Object source, T target) {
		// 判读对象为空
		if (source == null || target == null)
			return target;
		// 如果源为Map
		if (source instanceof Map<?, ?>)
			return copy((Map<?, ?>) source, target);
		// 如果目标为Map
		Map<String, Object> map = null;
		if (target instanceof Map<?, ?>)
			map = (Map<String, Object>) target;
		// 循环字段
		for (Field field : getFields(source.getClass()))
			try {
				// 不是符合字段
				if (!field.isSynthetic())
					// 设置字段值
					if (map == null)
						setFieldValue(target, getField(target, field.getName()), getFieldValue(source, field));
					else
						map.put(field.getName(), getFieldValue(source, field));
			} catch (Exception e) {
				Logs.error(e);
			}
		// 返回对象
		return target;
	}

	/**
	 * 把Map的Key与Object属性相同的字段赋值 就是把Map对应的值赋给Object
	 * @param map 源对象
	 * @param dest 目标对象
	 * @param <T> 泛型
	 * @return dest 目标对象
	 */
	public static <T> T copy(Map<?, ?> map, T dest) {
		// 循环字段
		getFields(dest.getClass()).forEach(field -> {
			try {
				// 不是复合字段
				if (!field.isSynthetic()) {
					// 设置字段值
					String name = field.getName();
					setFieldValue(dest, getField(dest, name), map.get(name));
				}
			} catch (Exception e) {}
		});
		// 返回对象
		return dest;
	}

	/**
	 * 把Map的Key与Class的实例属性相同的字段赋值 就是把Map对应的值赋给Object
	 * <h2>注: 此方法回返回Class的一个新实例对象</h2>
	 * @param map 源对象
	 * @param dest 目标对象的Class用dest.newInstance()生成一个新的实例 *
	 * @param <T> 泛型
	 * @return dest 目标对象的新实例
	 */
	public static <T> T copy(Map<?, ?> map, Class<T> dest) {
		return copy(map, newInstance(dest));
	}

	/**
	 * 把Map的Key与Class的实例属性相同的字段赋值 就是把Map对应的值赋给Object
	 * @param dest 目标对象的E用dest.getClass().newInstance()生成一个新的实例
	 * @param list map对象列表
	 * @param <T> 泛型
	 * @return 转换后的对象
	 */
	public static <T> List<T> copy(List<Map<String, Object>> list, Class<T> dest) {
		// 获得列表大小
		int size = list.size();
		// 获得列表
		List<T> ls = Lists.newList(size);
		// 是 ArrayList
		for (int i = 0; i < size; i++)
			ls.add(copy(list.get(i), dest));
		// 返回列表
		return ls;
	}

	/**
	 * 使用Class的newInstance()方法实例一个对象 封装异常为运行时异常
	 * @param entity 对象的类
	 * @param parameterTypes 参数类型
	 * @param <T> 泛型
	 * @return 实例的对象
	 */
	public static <T> T newInstance(Class<T> entity,Class<?>... parameterTypes) {
		try {
			return entity == null ? null : entity.getDeclaredConstructor(parameterTypes).newInstance();
		} catch (Exception e) {
			Logs.error(e);
			return null;
		}
	}

	/**
	 * 使用Class的newInstance()方法实例一个对象 封装异常为运行时异常
	 * @param className 对象的类
	 * @return 实例的对象
	 */
	public static Object newInstance(String className) {
		return newInstance(ClassUtil.forName(className));
	}

	/**
	 * 直接读取对象属性值, 无视private/protected修饰符, 不经过getter函数.
	 * @param list 列表
	 * @param fieldName 属性名
	 * @return 属性值
	 */
	public static List<Object> getFieldValues(Collection<?> list, String fieldName) {
		// 对象为空
		if (EmptyUtil.isEmpty(list))
			return Lists.emptyList();
		// 声明返回列表
		List<Object> ls = Lists.newList(list.size());
		// 循环添加
		list.forEach(e -> {
			// 获得值
			Object val = getFieldValue(e, fieldName);
			// 判断值是否为集合
			if (val instanceof Collection<?>)
				ls.addAll((Collection<?>) val);
			else
				ls.add(val);
		});
		// 返回列表
		return ls;
	}

	/**
	 * 获得本类下所有字段值
	 * @param obj 对象
	 * @return 列表
	 */
	public static List<Object> getFieldValues(Object obj) {
		// 获得所有字段
		List<Field> fields = getFields(obj.getClass());
		// 声明值列表
		List<Object> values = Lists.newList(fields.size());
		// 循环赋值
		fields.forEach(field -> values.add(getFieldValue(obj, field.getName())));
		// 返回值列表
		return values;
	}

	/**
	 * 直接读取对象属性值, 无视private/protected修饰符, 不经过getter函数.
	 * @param object 调用的对象
	 * @param fieldName 属性名
	 * @return 属性值
	 */
	public static Object getFieldValue(Object object, String fieldName) {
		// 如果有复杂字段
		if (fieldName.indexOf(StringConstants.POINT) > -1)
			return getFieldValue(getFieldValue(object, StringUtil.subStringEnd(fieldName, StringConstants.POINT)), StringUtil.subString(fieldName, StringConstants.POINT));
		// 获得字段
		Field field = getField(object, fieldName);
		// 判断字段为空 返回null
		if (EmptyUtil.isEmpty(field))
			return null;
		try {
			// 获得字段值
			return makeAccessible(field).get(object);
		} catch (IllegalAccessException e) {
			Logs.error(e);
			return null;
		}
	}

	/**
	 * 直接读取对象属性值, 无视private/protected修饰符, 不经过getter函数.
	 * @param object 调用的对象
	 * @param field 字段
	 * @return 属性值
	 */
	public static Object getFieldValue(Object object, Field field) {
		// 判断字段为空 返回null
		if (object == null || field == null)
			return null;
		try {
			// 获得字段值
			return makeAccessible(field).get(object);
		} catch (IllegalAccessException e) {
			Logs.error(e);
			return null;
		}
	}

	/**
	 * 直接设置对象属性值, 无视private/protected修饰符, 不经过setter函数
	 * @param object 对象
	 * @param fieldName 字段名
	 * @param value 值
	 */
	public static void setFieldValue(Object object, String fieldName, Object value) {
		setFieldValue(object, getField(object, fieldName), value);
	}

	/**
	 * 直接设置对象属性值, 无视private/protected修饰符, 不经过setter函数.
	 * @param object 对象
	 * @param field 字段
	 * @param value 值
	 */
	public static void setFieldValue(Object object, Field field, Object value) {
		// 判断字段为空 返回
		if (object == null || field == null || value == null)
			return;
		// 设置字段值
		try {
			makeAccessible(field).set(object, Conversion.to(value, field.getType()));
		} catch (Exception e) {}
	}

	/**
	 * 直接调用对象方法
	 * @param obj 调用的对象
	 * @param method 方法
	 * @param args 参数
	 * @return 方法返回值
	 */
	public static Object invoke(Object obj, Method method, Object... args) {
		try {
			return makeAccessible(method).invoke(obj, EmptyUtil.isEmpty(args) ? null : args);
		} catch (Exception e) {
			Logs.error(e, "invoke method={} args={} params={}", method.getName(), Arrays.toString(args), Arrays.toString(method.getParameters()));
			return null;
		}
	}

	/**
	 * 直接调用对象方法
	 * @param object 调用的对象
	 * @param name 方法名
	 * @return 方法返回值
	 */
	public static Object invoke(Object object, String name) {
		return invoke(object, name, null, null);
	}

	/**
	 * 直接调用对象方法
	 * @param object 调用的对象
	 * @param name 方法名
	 * @param parameterTypes 参数类型
	 * @param parameters 参数
	 * @return 方法返回值
	 */
	public static Object invoke(Object object, String name, Class<?>[] parameterTypes, Object[] parameters) {
		// 声明Class
		Class<?> c = null;
		if (object instanceof Class<?>)
			c = (Class<?>) object;
		else
			c = object.getClass();
		// Class不为空
		if (c == null)
			return null;
		else
			try {
				return getMethod(c, name, parameterTypes).invoke(object, parameters);
			} catch (Exception e) {
				Logs.error(e);
				return null;
			}
	}

	/**
	 * 获得对象的字段
	 * @param object 对象
	 * @param name 字段名
	 * @return 字段
	 */
	public static Field getField(Object object, String name) {
		return getField(object.getClass(), name);
	}

	/**
	 * 获得Class的字段
	 * @param clazz Class
	 * @param name 字段名
	 * @return 字段
	 */
	public static Field getField(Class<?> clazz, String name) {
		// 判断对象和字段名是否为空
		if (clazz == null || EmptyUtil.isEmpty(name))
			return null;
		// 如果有复杂字段
		if (name.indexOf(StringConstants.POINT) > -1)
			return getField(getField(clazz, StringUtil.subStringEnd(name, StringConstants.POINT)), StringUtil.subString(name, StringConstants.POINT));
		// 声明字段
		Field f = null;
		// 循环对象类
		for (; clazz != Object.class && f == null; clazz = clazz.getSuperclass())
			try {
				// 获得字段
				f = clazz.getDeclaredField(name);
			} catch (Exception e) {}
		// 返回null
		return f;
	}

	/**
	 * 循环向上转型, 获取对象的DeclaredField. 如向上转型到Object仍无法找到, 返回null.
	 * @param clazz 类
	 * @return 列表
	 */
	public static List<Field> getFields(Class<?> clazz) {
		// 判断对象和字段名是否为空
		if (EmptyUtil.isEmpty(clazz))
			return Lists.emptyList();
		// 声明列表
		List<Field> fields = Lists.newList();
		// 循环对象类
		for (; clazz != Object.class; clazz = clazz.getSuperclass())
			try {
				// 添加字段列表
				fields.addAll(Lists.newList(clazz.getDeclaredFields()));
			} catch (Exception e) {}
		// 没有找到返回null
		return fields;
	}

	/**
	 * 获得对象的方法
	 * @param obj 对象
	 * @param name 方法
	 * @param parameterTypes 参数类型
	 * @return 方法
	 */
	public static Method getMethod(Object obj, String name, Class<?>... parameterTypes) {
		// 判断对象和字段名是否为空
		if (obj == null || EmptyUtil.isEmpty(name))
			return null;
		// 声明Method
		Method method = null;
		// 循环对象类
		for (Class<?> superClass = obj instanceof Class<?> ? (Class<?>) obj : obj.getClass(); superClass != Object.class && method == null; superClass = superClass.getSuperclass())
			try {
				// 返回方法
				method = superClass.getDeclaredMethod(name, parameterTypes);
			} catch (Exception e) {}
		// 返回方法
		return method;
	}

	/**
	 * 强行设置Field可访问.
	 * @param field 字段
	 * @return 字段
	 */
	private static Field makeAccessible(Field field) {
		// 判断字段是否公有
		if (!Modifier.isPublic(field.getModifiers()) || !Modifier.isPublic(field.getDeclaringClass().getModifiers()))
			// 设置可访问
			field.setAccessible(true);
		// 返回字段
		return field;
	}

	/**
	 * 强行设置Field可访问.
	 * @param method 方法
	 * @return 方法
	 */
	private static Method makeAccessible(Method method) {
		// 判断字段是否公有
		if (!Modifier.isPublic(method.getModifiers()) || !Modifier.isPublic(method.getDeclaringClass().getModifiers()))
			// 设置可访问
			method.setAccessible(true);
		// 返回方法
		return method;
	}

	private BeanUtil() {}
}
