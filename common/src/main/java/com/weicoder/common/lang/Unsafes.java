package com.weicoder.common.lang;

import com.weicoder.common.log.Logs;

import sun.misc.Unsafe;

/**
 * 获取Unsafe类
 * 
 * @author wudi
 */
public final class Unsafes {
	// 声明Unsafe
	private static final Unsafe UNSAFE;

	static {
		UNSAFE = init();
	}

	/**
	 * 获取Unsafe
	 * 
	 * @return Unsafe
	 */
	public static Unsafe getUnsafe() {
		return UNSAFE;
	}

	private static Unsafe init() {
		try {
			return Unsafe.getUnsafe();
		} catch (SecurityException tryReflectionInstead) {
		}
		try {
			return java.security.AccessController.doPrivileged(new java.security.PrivilegedExceptionAction<Unsafe>() {
				@Override
				public Unsafe run() throws Exception {
					Class<Unsafe> k = Unsafe.class;
					for (java.lang.reflect.Field f : k.getDeclaredFields()) {
						f.setAccessible(true);
						Object x = f.get(null);
						if (k.isInstance(x))
							return k.cast(x);
					}
					return null;
				}
			});
		} catch (java.security.PrivilegedActionException e) {
			Logs.error(e);
		}
		return null;
	}

	private Unsafes() {
	}
}
