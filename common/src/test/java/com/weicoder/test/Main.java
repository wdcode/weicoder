package com.weicoder.test;
 

public class Main {
	public static void main(String[] args) {
		int m = 5299;
		double b = 0.0016974;
		double n = 0;
		for(int i=0;i<24;i++) {
			n+=m*b;
			m-=220.8;
		}
		System.out.println("m="+m+";n="+n);
		
		  m = 6999; 
		  n = 0;
		for(int i=0;i<6;i++) {
			n+=m*b;
			m-=1166.5;
		}
		System.out.println("m="+m+";n="+n);
		
//		byte[] b = "lkajejkgt32089".getBytes();
//		int n = 1000000;
//		long curr = System.currentTimeMillis();
//		for (int i = 0; i < n; i++) {
//			Encrypts.aes(b);
//		}
//		System.out.println(System.currentTimeMillis() - curr);
//		curr = System.currentTimeMillis();
//		for (int i = 0; i < n; i++) {
//			Encrypts.rc4(b);
//		}
//		System.out.println(System.currentTimeMillis() - curr);
//		curr = System.currentTimeMillis();
//		for (int i = 0; i < n; i++) {
//			Encrypts.des(b);
//		}
//		System.out.println(System.currentTimeMillis() - curr);
//		curr = System.currentTimeMillis();
//		for (int i = 0; i < n; i++) {
//			Base64.encode(b);
//		}
//		System.out.println(System.currentTimeMillis() - curr);
//		curr = System.currentTimeMillis();
//		for (int i = 0; i < n; i++) {
//			Hex.encode(b);
//		}
//		System.out.println(System.currentTimeMillis() - curr);
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
