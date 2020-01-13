package com.weicoder.test;
 
 

public class UnsafeTest {
	private static final sun.misc.Unsafe UNSAFE = getUnsafe();

	public static void main(String[] args) throws NoSuchFieldException, SecurityException {
		System.out.println(UNSAFE);
		System.out.println(UNSAFE.objectFieldOffset(SearchBean.class.getDeclaredField("uid")));
//		System.out.println(UNSAFE.getInt(0xc0000005));
	}

	private static sun.misc.Unsafe getUnsafe() {
		try {
			return sun.misc.Unsafe.getUnsafe();
		} catch (SecurityException tryReflectionInstead) {
		}
		try {
			return java.security.AccessController.doPrivileged(new java.security.PrivilegedExceptionAction<sun.misc.Unsafe>() {
				@Override
				public sun.misc.Unsafe run() throws Exception {
					Class<sun.misc.Unsafe> k = sun.misc.Unsafe.class;
					for (java.lang.reflect.Field f : k.getDeclaredFields()) {
						f.setAccessible(true);
						Object x = f.get(null);
						if (k.isInstance(x))
							return k.cast(x);
					}
					throw new NoSuchFieldError("the Unsafe");
				}
			});
		} catch (java.security.PrivilegedActionException e) {
			throw new RuntimeException("Could not initialize intrinsics", e.getCause());
		}
	}
}
