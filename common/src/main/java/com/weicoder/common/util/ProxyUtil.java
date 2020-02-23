package com.weicoder.common.util;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * JDK代理使用
 * 
 * @author wudi
 */
public final class ProxyUtil {
	/**
	 * 使用JDK代理生成代理类
	 * 
	 * @param  <E>
	 * @param  cls     要生成代理的类接口
	 * @param  handler 代理方法处理器
	 * @return         代理对象
	 */
	@SuppressWarnings("unchecked")
	public static <E> E newProxyInstance(Class<E> cls, InvocationHandler handler) {
		return (E) Proxy.newProxyInstance(ClassUtil.getClassLoader(), new Class[]{cls}, handler);
	}

	private ProxyUtil() {
	}
}
