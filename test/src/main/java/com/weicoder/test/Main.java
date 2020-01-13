package com.weicoder.test;

import com.weicoder.common.codec.Base64;
import com.weicoder.common.codec.Hex;
import com.weicoder.common.crypto.Encrypts; 

public class Main {
	public static void main(String[] args) {
		byte[] b = "lkajejkgt32089".getBytes();
		int n = 1000000;
		long curr = System.currentTimeMillis();
		for (int i = 0; i < n; i++) {
			Encrypts.aes(b);
		}
		System.out.println(System.currentTimeMillis() - curr);
		curr = System.currentTimeMillis();
		for (int i = 0; i < n; i++) {
			Encrypts.rc4(b);
		}
		System.out.println(System.currentTimeMillis() - curr);
		curr = System.currentTimeMillis();
		for (int i = 0; i < n; i++) {
			Encrypts.des(b);
		}
		System.out.println(System.currentTimeMillis() - curr);
		curr = System.currentTimeMillis();
		for (int i = 0; i < n; i++) {
			Base64.encode(b);
		}
		System.out.println(System.currentTimeMillis() - curr);
		curr = System.currentTimeMillis();
		for (int i = 0; i < n; i++) {
			Hex.encode(b);
		}
		System.out.println(System.currentTimeMillis() - curr);
//		System.out.println(Long.MAX_VALUE);
//		System.out.println(Long.MIN_VALUE);
//		System.out.println(Integer.MAX_VALUE);
//		System.out.println(Integer.MIN_VALUE);
//		System.out.println((1 << 31) - 1);
//		System.out.println(1 << 32);
//		System.out.println(1 << 31);
//		System.out.println(1 << 4);
//		System.out.println(32 >> 1);
//		System.out.println(32 >> 2);
//		System.out.println(DateUtil.toString(1575896007));
	}

	public static void test(Object o) {
		System.out.println(1 << 30);
	}
}
