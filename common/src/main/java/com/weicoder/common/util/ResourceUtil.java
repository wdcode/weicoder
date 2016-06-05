package com.weicoder.common.util;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.log.Logs;

/**
 * 资源工具累类
 * @author WD 
 *   
 */
public final class ResourceUtil {
	/**
	 * 尝试加载工程类下的资源文件
	 * @param name 文件名 相对路径
	 * @return 文件 如果不存在返回 null
	 */
	public static File getFile(String name) {
		try {
			return new File(getResource(name).toURI());
		} catch (Exception e) {
			Logs.debug(e);
			return null;
		}
	}

	/**
	 * 尝试加载资源
	 * @param resourceName 资源文件名
	 * @return
	 */
	public static URL getResource(String resourceName) {
		// 获得资源URL 使用当前线程
		URL url = Thread.currentThread().getContextClassLoader().getResource(resourceName);
		// 如果获得的资源为null
		if (url == null) {
			// 使用本类加载
			url = ClassLoader.getSystemClassLoader().getResource(resourceName);
			//如果为空
			if (url == null) {
				url = ClassLoader.getSystemResource(resourceName);
			}
		}
		// 如果url还为空 做资源的名的判断重新调用方法
		if (url == null && !EmptyUtil.isEmpty(resourceName) && (!resourceName.startsWith(StringConstants.BACKSLASH))) { return getResource(StringConstants.BACKSLASH + resourceName); }
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
		InputStream in = ClassLoader.getSystemResourceAsStream(name);
		// 判断流为空
		if (in == null) {
			// 使用当前线程来加载资源
			in = Thread.currentThread().getContextClassLoader().getResourceAsStream(name);
			// 判断流为空
			if (in == null) {
				// 使用当前类来加载资源
				in = ClassLoader.getSystemClassLoader().getResourceAsStream(name);
			}
		}
		// 返回流
		return in;
	}

	private ResourceUtil() {}
}
