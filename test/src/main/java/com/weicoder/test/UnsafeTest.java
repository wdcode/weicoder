package com.weicoder.test;

import com.weicoder.common.lang.Unsafes;

public class UnsafeTest {
	private static final sun.misc.Unsafe UNSAFE = Unsafes.getUnsafe();

	public static void main(String[] args) throws NoSuchFieldException, SecurityException {
		System.out.println(UNSAFE);
		System.out.println(UNSAFE.objectFieldOffset(SearchBean.class.getDeclaredField("uid")));
//		System.out.println(UNSAFE.getInt(0xc0000005));
	}
}
