package com.weicoder.common.util;

import java.io.InputStream;
import java.net.URL;

import com.weicoder.common.constants.StringConstants;

/**
 * 资源工具累类
 * @author WD
 * @since JDK7
 * @version 1.0 2012-04-6
 */
public final class ResourceUtil {
	/**
	 * 尝试加载资源
	 * @param resourceName 资源文件名
	 * @return
	 */
	public static URL getResource(String resourceName) {
		// 获得资源URL 使用当前线程
		URL url = Thread.currentThread().getContextClassLoader().getResource(resourceName);
		// 如果获得的资源为null
		if (EmptyUtil.isEmpty(url)) {
			// 使用本类加载
			url = ClassUtil.class.getClassLoader().getResource(resourceName);
		}
		// 如果url还为空 做资源的名的判断重新调用方法
		if (EmptyUtil.isEmpty(url) && !EmptyUtil.isEmpty(resourceName) && (!resourceName.startsWith(StringConstants.BACKSLASH))) {
			return getResource(StringConstants.BACKSLASH + resourceName);
		}
		// 返回资源
		return url;
	}

	/**
	 * 加载资源
	 * @param name 资源名
	 * @return 输入流
	 */
	public static InputStream loadResource(String name) {
		// 声明流
		InputStream in = ClassUtil.class.getResourceAsStream(name);
		// 判断流为空
		if (EmptyUtil.isEmpty(in)) {
			// 使用当前线程来加载资源
			in = Thread.currentThread().getContextClassLoader().getResourceAsStream(name);
			// 判断流为空
			if (EmptyUtil.isEmpty(in)) {
				// 使用当前类来加载资源
				in = ClassUtil.class.getClassLoader().getResourceAsStream(name);
			}
		}
		// 返回流
		return in;
	}

	/**
	 * 私有构造
	 */
	private ResourceUtil() {}
}
