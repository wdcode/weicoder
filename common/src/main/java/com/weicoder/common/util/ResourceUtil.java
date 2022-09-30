package com.weicoder.common.util;

import java.io.File;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.List;

import com.weicoder.common.constants.C;
import com.weicoder.common.io.I;
import com.weicoder.common.lang.W;

/**
 * 资源工具类
 * 
 * @author WD
 */
public sealed class ResourceUtil permits U.R {
	/**
	 * 尝试加载工程类下的资源文件
	 * 
	 * @param name 文件名 相对路径
	 * @return 文件 如果不存在返回 null
	 */
	public static File newFile(String name) {
		try {
			return new File(getResource(name).toURI());
		} catch (Exception e) {
			return I.F.newFile(C.O.USER_DIR + C.S.BACKSLASH + name);
		}
	}

	/**
	 * 尝试加载资源
	 * 
	 * @param resourceName 资源文件名
	 * @return URL资源
	 */
	public static URL getResource(String resourceName) {
		// 获得资源URL 使用当前线程
		URL url = Thread.currentThread().getContextClassLoader().getResource(resourceName);
		// 如果获得的资源为null
		if (url == null)
			// 使用本类加载 如果为空
			if ((url = ClassLoader.getSystemClassLoader().getResource(resourceName)) == null)
				url = ClassLoader.getSystemResource(resourceName);
		// 如果url还为空 做资源的名的判断重新调用方法
		if (url == null && U.E.isNotEmpty(resourceName) && (!resourceName.startsWith(C.S.BACKSLASH)))
			return getResource(C.S.BACKSLASH + resourceName);
		// 返回资源
		try {
			return url == null ? new URL(C.H.HTTP) : url;
		} catch (MalformedURLException e) {
			return null;
		}
	}

	/**
	 * 尝试加载资源
	 * 
	 * @param resourceName 资源文件名
	 * @return URL资源
	 */
	public static List<URL> getResources(String resourceName) {
		// 声明列表
		List<URL> urls = W.L.list();
		try {
			// 获得资源URL 使用当前线程
			for (Enumeration<URL> u = Thread.currentThread().getContextClassLoader().getResources(resourceName); u
					.hasMoreElements();)
				urls.add(u.nextElement());
			// 如果获得的资源为null
			if (U.E.isEmpty(urls)) {
				// 使用本类加载
				for (Enumeration<URL> u = ClassLoader.getSystemClassLoader().getResources(resourceName); u.hasMoreElements();)
					urls.add(u.nextElement());
				// 如果为空
				if (U.E.isEmpty(urls))
					for (Enumeration<URL> u = ClassLoader.getSystemResources(resourceName); u.hasMoreElements();)
						urls.add(u.nextElement());
			}
			// 如果url还为空 做资源的名的判断重新调用方法
			if (U.E.isEmpty(urls) && !U.E.isEmpty(resourceName) && (!resourceName.startsWith(C.S.BACKSLASH)))
				return getResources(C.S.BACKSLASH + resourceName);
		} catch (Exception e) {
		}
		// 返回资源
		return urls;
	}

	/**
	 * 加载资源
	 * 
	 * @param name 资源名
	 * @return 输入流
	 */
	public static InputStream loadResource(String name) {
		// 声明流
		InputStream in = ClassLoader.getSystemResourceAsStream(name);
		// 判断流为空
		if (in == null)
			// 使用当前线程来加载资源 判断流为空
			if ((in = Thread.currentThread().getContextClassLoader().getResourceAsStream(name)) == null)
				// 使用当前类来加载资源
				in = ClassLoader.getSystemClassLoader().getResourceAsStream(name);
		// 返回流
		return in;
	}
}
