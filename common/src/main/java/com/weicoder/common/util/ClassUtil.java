package com.weicoder.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.weicoder.common.U.B;
import com.weicoder.common.U.C;
import com.weicoder.common.U.S;
import com.weicoder.common.W.L;
import com.weicoder.common.W.M;
import com.weicoder.common.U;
import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.lang.Lists;
import com.weicoder.common.log.Logs;
import com.weicoder.common.params.CommonParams;

/**
 * 关于Class的一些操作
 * 
 * @author WD
 */
@SuppressWarnings("unchecked")
public class ClassUtil {
	// 包名下的class
	private final static Map<String, List<Class<?>>> PASSAGES = M.newMap();
	// 对应class名称的Bean
	private final static Map<String, Class<?>>                BEANS       = M.newMap();
	private final static Map<Class<?>, Map<String, Class<?>>> CLASS_BEANS = M.newMap();
	// ioc使用
	private final static Map<Class<?>, Object> IOC_BEANS = M.newMap();
	// 保存指定报名下所有class
	private final static Map<Class<?>, List<Class<?>>> CLASSES = init();

	/**
	 * 对传入的类进行实例化并进行类型注入 非基础类型
	 * 
	 * @param c 要注入的类
	 */
	public static <E> E ioc(Class<E> c) {
		return ioc(newInstance(c));
	}

	/**
	 * 对传入的对象进行类型注入 非基础类型
	 * 
	 * @param o 要注入的对象
	 */
	public static <E> E ioc(E o) {
		// 对象不为空
		if (o != null) {
			// 获得对象内的字段
			B.getFields(o.getClass()).forEach(f -> {
				// 不是基础类型才注入
				Class<?> type = f.getType();
				if (!isBaseType(type) && f.isAnnotationPresent(Resource.class)) {
					// 声明类对象
					Object val = null;
					if (IOC_BEANS.containsKey(type))
						// 已经存在IOC列表中 直接获取使用
						val = IOC_BEANS.get(type);
					else
					// 不在列表 声明新对象放入列表
					if (type.isInterface() || type.isAnnotation())
						IOC_BEANS.put(type, val = ioc(from(type)));
					else
						IOC_BEANS.put(type, val = ioc(type));
					// 注入到字段
					B.setFieldValue(o, f, val);
				}
			});
		}
		// 返回对象
		return o;
	}

	/**
	 * 获得包名下指定接口的实现类
	 * 
	 * @param  <E>
	 * @param  name 包名
	 * @param  c    超类类型
	 * @return      实现类列表
	 */
	public static <E> List<Class<E>> pack(String name, Class<E> cls) {
		// 声明类别
		List<Class<E>> list = L.newList();
		// 接口
		PASSAGES.get(name).forEach(c -> {
			if (c.isAssignableFrom(cls) || c.isAnnotationPresent((Class<? extends Annotation>) cls))
				list.add((Class<E>) c);
		});
		// 返回列表
		return list;
	}

	/**
	 * 根据类名称或则指定类
	 * 
	 * @param  c    指定类下的
	 * @param  name 类名称
	 * @return
	 */
	public static <E> Class<E> bean(Class<E> c, String name) {
		return (Class<E>) CLASS_BEANS.get(c).get(name);
	}

	/**
	 * 根据类名称或则指定类
	 * 
	 * @param  name 类名称
	 * @return
	 */
	public static <E> Class<E> bean(String name) {
		return (Class<E>) BEANS.get(name);
	}

	/**
	 * 获取指定接口下的所有实现类
	 * 
	 * @param  c
	 * @return
	 */
	public static <E> List<Class<E>> list(Class<E> c) {
		return L.newList(CLASSES.get(c)).stream().map(o -> (Class<E>) o).collect(Collectors.toList());
	}

	/**
	 * 获取指定接口下的最后一个实现类
	 * 
	 * @param  c 要指定接口或注解的类
	 * @return
	 */ 
	public static <E> Class<E> from(Class<E> c) {
		return from(c, CLASSES.get(c).size() - 1);
	}

	/**
	 * 获取指定接口下的一个实现类 当实现大于2个不使用默认
	 * 
	 * @param  c   要指定接口或注解的类
	 * @param  def 默认实现
	 * @return
	 */
	public static <E> Class<E> from(Class<E> c, Class<? extends E> def) {
		// 获得接口类列表
		List<Class<?>> list = CLASSES.get(c);
		// 实现超过2个 并且默认不为空
		if (list.size() > 1 && def != null)
			// 获取实现 并且不是默认实现返回
			for (Class<?> cs : list)
				if (!cs.equals(def))
					return (Class<E>) cs;
		// 返回默认实现
		return def == null ? (Class<E>) list.get(0) : (Class<E>) def;
	}

	/**
	 * 获取指定接口下的指定索引的实现类
	 * 
	 * @param  c 要指定接口或注解的类
	 * @param  i 索引第几个
	 * @return
	 */
	public static <E> Class<E> from(Class<E> c, int i) {
		return (Class<E>) L.get(CLASSES.get(c), i);
	}

	/**
	 * 根据给入的Class返回对应的空对象
	 * 
	 * @param  cls 声明的对象
	 * @return     空对象
	 */
	public static Object empty(Class<?> cls) {
		if (Number.class.isAssignableFrom(cls))
			return 0;
		else
			return newInstance(cls);
	}

	/**
	 * 判断是否是基础类型
	 * 
	 * @param  clazz 要检查的类
	 * @return       是否基础类型
	 */
	public static boolean isBaseType(Class<?> clazz) {
		if (clazz == null)
			return false;
		if (clazz.equals(Integer.class) || clazz.equals(int.class))
			return true;
		if (clazz.equals(Long.class) || clazz.equals(long.class))
			return true;
		if (clazz.equals(Double.class) || clazz.equals(double.class))
			return true;
		if (clazz.equals(Float.class) || clazz.equals(float.class))
			return true;
		if (clazz.equals(Short.class) || clazz.equals(short.class))
			return true;
		if (clazz.equals(Byte.class) || clazz.equals(byte.class))
			return true;
		if (clazz.equals(Boolean.class) || clazz.equals(boolean.class))
			return true;
		if (clazz.equals(Character.class) || clazz.equals(char.class))
			return true;
		if (clazz.equals(String.class) || clazz.equals(BigDecimal.class))
			return true;
		return false;

	}

	/**
	 * 获得指定类型的泛型
	 * 
	 * @param  clazz 指定的类型
	 * @param  index 索引
	 * @return       这个类的泛型
	 */
	public static Class<?> getGenericClass(Class<?> clazz, int index) {
		try {
			return getGenericClass(clazz)[index];
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 获得指定类型的泛型
	 * 
	 * @param  clazz 指定的类型
	 * @return       这个类的泛型
	 */
	public static Class<?>[] getGenericClass(Class<?> clazz) {
		// 查询父类是否有泛型
		Class<?>[] gc = getGenericClass(clazz.getGenericSuperclass());
		// 如果没找到
		if (gc == null) {
			// 获得所有接口
			Type[] type = clazz.getGenericInterfaces();
			// 接口不为空
			if (U.E.isNotEmpty(type)) {
				// 循环接口
				for (Type t : type) {
					// 获得泛型
					gc = getGenericClass(t);
					// 泛型不为空 跳出循环
					if (gc != null)
						break;
				}
			}
		}
		// 返回类
		return gc;
	}

	/**
	 * 获得指定类型的泛型
	 * 
	 * @param  type 指定的类型
	 * @return      这个类的泛型
	 */
	public static Class<?>[] getGenericClass(Type type) {
		// 类型不对
		if (type instanceof ParameterizedType) {
			// 获得类型类型数组
			Type[] types = ((ParameterizedType) type).getActualTypeArguments();
			// 声明Class数组
			Class<?>[] clazzs = new Class<?>[types.length];
			// 循环
			for (int i = 0; i < types.length; i++)
				// 强制转换
				clazzs[i] = (Class<?>) types[i];
			// 返回数组
			return clazzs;
		} else {
			return null;
		}
	}

	/**
	 * 获得指定类型的泛型
	 * 
	 * @param  type  指定的类型
	 * @param  index 索引
	 * @return       这个类型的泛型
	 */
	public static Class<?> getGenericClass(Type type, int index) {
		try {
			return getGenericClass(type)[index];
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 加载类
	 * 
	 * @param  className 类名
	 * @return           获得的类
	 */
	public static Class<?> loadClass(String className) {
		// 声明类
		Class<?> theClass = null;
		try {
			// 获得类
			theClass = Class.forName(className);
		} catch (ClassNotFoundException e1) {
			try {
				// 当前线程获得类
				theClass = Thread.currentThread().getContextClassLoader().loadClass(className);
			} catch (ClassNotFoundException e2) {
				try {
					// 使用当前类获得类
					theClass = ClassUtil.class.getClassLoader().loadClass(className);
				} catch (ClassNotFoundException e3) {
					return null;
				}
			}
		}
		// 返回类
		return theClass;
	}

	/**
	 * 使用JDK代理生成代理类
	 * 
	 * @param  <E>
	 * @param  cls     要生成代理的类接口
	 * @param  handler 代理方法处理器
	 * @return         代理对象
	 */

	public static <E> E newProxyInstance(Class<E> cls, InvocationHandler handler) {
		return (E) Proxy.newProxyInstance(ClassUtil.getClassLoader(), new Class[]{cls}, handler);
	}

	/**
	 * 获得Class 会处理基础类型
	 * 
	 * @param  className Class名称
	 * @return           Class
	 */
	public static Class<?> forName(String className) {
		try {
			// 判断为空和基础类型
			if (U.E.isEmpty(className))
				return null;
			if ("byte".equals(className))
				return byte.class;
			if ("short".equals(className))
				return short.class;
			if ("int".equals(className))
				return int.class;
			if ("long".equals(className))
				return long.class;
			if ("float".equals(className))
				return float.class;
			if ("double".equals(className))
				return double.class;
			if ("boolean".equals(className))
				return boolean.class;
			if ("char".equals(className))
				return char.class;
			// 不是基础类型生成
			return Class.forName(className);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 实例化对象
	 * 
	 * @param  className      类名
	 * @param  parameterTypes 参数类型
	 * @return                实例化对象
	 */
	public static Object newInstance(String className, Class<?>... parameterTypes) {
		try {
			if (U.E.isEmpty(className))
				return null;
			Class<?> c = forName(className);
			return c == null || c.isInterface() ? null : c.getDeclaredConstructor(parameterTypes).newInstance();
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 实例化对象
	 * 
	 * @param  className 类名
	 * @param  obj       默认值
	 * @param  <E>       泛型
	 * @return           实例化对象
	 */

	public static <E> E newInstance(String className, E obj) {
		// 实例化对象
		E o = (E) newInstance(className);
		// 对象为null 返回默认
		return o == null ? obj : o;
	}

	/**
	 * 实例化对象
	 * 
	 * @param  clazz          类
	 * @param  <T>            泛型
	 * @param  parameterTypes 参数类型
	 * @return                实例化对象
	 */
	public static <T> T newInstance(Class<T> clazz, Class<?>... parameterTypes) {
		try {
			return clazz == null || clazz.isInterface() ? null : clazz.getConstructor(parameterTypes).newInstance();
		} catch (Exception e) {
			Logs.error(e);
			return null;
		}
	}

	/**
	 * 使用Class的newInstance()方法实例一个对象 封装异常为运行时异常
	 * 
	 * @param  className 对象的类
	 * @return           实例的对象
	 */
	public static Object newInstance(String className) {
		return newInstance(forName(className));
	}

//	/**
//	 * 指定包下 指定类的实现
//	 * 
//	 * @param  cls 指定类
//	 * @param  i   指定索引
//	 * @param  <E> 泛型
//	 * @return     类列表
//	 */
//	public static <E> Class<E> getAssignedClass(Class<E> cls, int i) {
//		return Lists.get(getAssignedClass(CommonParams.PACKAGES, cls), i);
//	}
//
//	/**
//	 * 指定包下 指定类的实现
//	 * 
//	 * @param  cls 指定类
//	 * @param  i   指定索引
//	 * @param  <E> 泛型
//	 * @return     类列表
//	 */
//	public static <E> List<Class<E>> getAssignedClass(Class<E> cls) {
//		return getAssignedClass(CommonParams.PACKAGES, cls);
//	}
//
//	/**
//	 * 指定包下 指定类的实现
//	 * 
//	 * @param  packageName 包名
//	 * @param  cls         指定类
//	 * @param  <E>         泛型
//	 * @return             类列表
//	 */
//
//	public static <E> List<Class<E>> getAssignedClass(String packageName, Class<E> cls) {
//		// 声明类列表
//		List<Class<E>> classes = Lists.newList();
//		// 循环包下所有类
//		for (Class<?> c : getPackageClasses(packageName))
//			// 是本类实现 并且不是本类
//			if (cls.isAssignableFrom(c) && !cls.equals(c))
//				classes.add((Class<E>) c);
//		// 返回列表
//		return classes;
//	}
//
//	/**
//	 * 指定包下 指定类的实现
//	 * 
//	 * @param  packageName 包名
//	 * @param  cls         指定类
//	 * @param  i           指定索引
//	 * @param  <E>         泛型
//	 * @return             类列表
//	 */
//	public static <E extends Annotation> Class<E> getAnnotationClass(String packageName, Class<E> cls, int i) {
//		return Lists.get(getAnnotationClass(packageName, cls), i);
//	}
//
//	/**
//	 * 指定包下 指定类的实现
//	 * 
//	 * @param  cls 指定类
//	 * @param  <E> 泛型
//	 * @return     类列表
//	 */
//	public static <E extends Annotation> List<Class<E>> getAnnotationClass(Class<E> cls) {
//		return getAnnotationClass(CommonParams.PACKAGES, cls);
//	}
//
//	/**
//	 * 指定包下 指定类的实现
//	 * 
//	 * @param  packageName 包名
//	 * @param  cls         指定类
//	 * @param  <E>         泛型
//	 * @return             类列表
//	 */
//
//	public static <E extends Annotation> List<Class<E>> getAnnotationClass(String packageName, Class<E> cls) {
//		// 声明类列表
//		List<Class<E>> classes = Lists.newList();
//		// 循环包下所有类
//		for (Class<?> c : getPackageClasses(packageName))
//			// 是本类实现 并且不是本类
//			if (c.isAnnotationPresent(cls) && !cls.equals(c))
//				classes.add((Class<E>) c);
//		// 返回列表
//		return classes;
//	}

	/**
	 * 获取本类下所有公用方法 不读取父类
	 * 
	 * @param  c 类
	 * @return   list
	 */
	public static List<Method> getPublicMethod(Class<?> c) {
		// 返回的方法列表
		List<Method> methods = Lists.newList();
		// 处理所有方法
		for (Method m : c.getDeclaredMethods())
			// 判断是公有方法
			if (Modifier.isPublic(m.getModifiers()))
				methods.add(m);
		// 返回
		return methods;
	}

	/**
	 * 获得指定包下的所有Class
	 * 
	 * @return 类列表
	 */
	public static List<Class<?>> getPackageClasses() {
		return getPackageClasses(CommonParams.PACKAGES);
	}

	/**
	 * 获得指定包下的所有Class
	 * 
	 * @param  packageName 报名
	 * @return             类列表
	 */
	public static List<Class<?>> getPackageClasses(String packageName) {
		// 声明返回类列表
		List<Class<?>> classes = Lists.newList();
		// 转换报名为路径格式
		for (String path : StringUtil.split(packageName, StringConstants.COMMA)) {
			String p = StringUtil.replace(path, StringConstants.POINT, StringConstants.BACKSLASH);
			// 获得目录资源
			ResourceUtil.getResources(p).forEach(url -> {
				// 循环目录下的所有文件与目录
				for (String name : getClasses(url.getPath(), p)) {
					// 如果是class文件
					if (name.endsWith(".class")) {
						try {
							// 反射出类对象 并添加到列表中
							name = p + StringConstants.POINT + StringUtil.subString(name, 0, name.length() - 6);
							name = StringUtil.replace(name, StringConstants.BACKSLASH, StringConstants.POINT);
							// 如果开始是.去掉
							if (name.startsWith(StringConstants.POINT))
								name = StringUtil.subString(name, 1);
							classes.add(Class.forName(name, false, getClassLoader()));
//							classes.add(forName(name));
						} catch (ClassNotFoundException e) {
							Logs.error(e);
						}
					} else
						// 迭代调用本方法 获得类列表
						classes.addAll(
								getPackageClasses(U.E.isEmpty(p) ? name : path + StringConstants.BACKSLASH + name));
				}
			});
		}
		// 返回类列表
		return classes;
	}

	/**
	 * 获得当前ClassLoader
	 * 
	 * @return ClassLoader
	 */
	public static ClassLoader getClassLoader() {
		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		return cl == null ? ClassLoader.getSystemClassLoader() : cl;
	}

	private static List<String> getClasses(String name, String packageName) {
		// 获得文件名
		File path = new File(name);
		// 判断是否目录
		if (path.isDirectory())
			// 如果是目录
			return Lists.newList(path.list());
		if (name.indexOf(".jar!") > -1)
			// 是否jar文件内
			return getClassesFromJARFile(StringUtil.subString(name, "file:/", "!"),
					packageName + StringConstants.BACKSLASH);
		// 返回空列表
		return Lists.emptyList();
	}

	private static List<String> getClassesFromJARFile(String jar, String name) {
		// 判断jar第二位是否: 不为:默认linux前面加上/
		if (jar.indexOf(StringConstants.COLON) == -1)
			jar = StringConstants.BACKSLASH + jar;
		// 声明返回列表
		List<String> list = Lists.newList();
		// 获得jar流
		try (JarInputStream jarFile = new JarInputStream(new FileInputStream(jar))) {
			// 循环获得JarEntry
			JarEntry jarEntry = null;
			while ((jarEntry = jarFile.getNextJarEntry()) != null) {
				// 判断是否包内class
				String className = jarEntry.getName();
				if (className.indexOf(name) > -1 && !className.equals(name))
					list.add(StringUtil.subString(className, name));
			}
		} catch (IOException e) {
			Logs.error(e);
		}
		// 返回列表
		return list;
	}

	/**
	 * 获得本类下的所有实现接口
	 * 
	 * @param  c 要获取的类
	 * @return   本类实现的所有接口
	 */
	public static List<Class<?>> getInterfaces(Class<?> c) {
		// 声明列表
		List<Class<?>> list = L.newList();
		if (c == null)
			return list;
		// 如果超类不为Object 获取超类迭代
		if (!Object.class.equals(c) && !Object.class.equals(c.getSuperclass()))
			list.addAll(getInterfaces(c.getSuperclass()));
		// 获得本类的接口
		for (Class<?> i : c.getInterfaces())
			list.add(i);
		// 返回列表
		return list;
	}

	/**
	 * 获得本类实现的注解
	 * 
	 * @param  c 要获取的类
	 * @return   本类实现的所有注解
	 */
	public static List<Annotation> getAnnotations(Class<?> c) {
		// 声明列表
		List<Annotation> list = L.newList();
		// 获得本类的所有注解
		for (Annotation a : c.getAnnotations())
			list.add(a);
		// 返回列表
		return list;
	}

	/**
	 * 初始化
	 * 
	 * @return
	 */
	private static Map<Class<?>, List<Class<?>>> init() {
		// 声明class列表
		Map<Class<?>, List<Class<?>>> map = M.newMap();
		// 扫描指定包下的类
		C.getPackageClasses().forEach(c -> {
			// 处理接口类型
			getInterfaces(c).forEach(i -> {
				M.getList(map, i).add(c);
				M.getMap(CLASS_BEANS, i).put(S.convert(c.getSimpleName(), i.getSimpleName(), "Impl"), c);
			});
			// 处理注解类型
			for (Annotation a : c.getAnnotations())
				M.getList(map, a.annotationType()).add(c);
			// 处理包名
//			M.getList(PASSAGES, c.getPackageName()).add(c);
			M.getList(PASSAGES, c.getPackage().getName()).add(c);
			// 处理类名Bean
//			String name = c.getSimpleName();
//			for (String n : CommonParams.CLASS_NAMES)
//				name = S.convert(name, n);
			BEANS.put(S.convert(c.getSimpleName(), CommonParams.CLASS_NAMES), c);
			// 对ioc注解的类进行注入
		});
		// 返回列表
		return map;
	}
}
