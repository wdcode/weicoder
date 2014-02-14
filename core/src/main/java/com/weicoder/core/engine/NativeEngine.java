package com.weicoder.core.engine;

import com.sun.jna.Library;
import com.sun.jna.Native;

/**
 * 本地方法调用
 * @author WD
 * @since JDK7
 * @version 1.0 2012-08-10
 */
public final class NativeEngine {
	/**
	 * 加载本地方法dll/so
	 * @param name 本地函数文件名
	 * @param interfaceClass 接口类
	 * @return 接口实习对象
	 */
	public static <E extends Library> E loadLibrary(String name, Class<E> interfaceClass) {
		return (E) Native.loadLibrary(name, interfaceClass);
	}

	private NativeEngine() {}
}
