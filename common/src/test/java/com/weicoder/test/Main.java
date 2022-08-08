package com.weicoder.test;
 
import com.weicoder.common.http.Http;
import com.weicoder.common.http.impl.Jdk11Http;

public class Main {
	public static void main(String[] args) {
		System.out.println(Http.class.isAnnotation());
		System.out.println(Http.class.isAnonymousClass());		
		System.out.println(Http.class.isHidden());
		System.out.println(Http.class.isInterface());
		System.out.println(Http.class.isLocalClass());
		System.out.println(Http.class.isMemberClass());
		System.out.println(Http.class.isPrimitive());
		System.out.println(Http.class.isRecord());
		System.out.println(Http.class.isSealed());
		System.out.println(Http.class.isSynthetic());
		System.out.println("-----------------------");
		System.out.println(Jdk11Http.class.isAnnotation());
		System.out.println(Jdk11Http.class.isAnonymousClass());		
		System.out.println(Jdk11Http.class.isHidden());
		System.out.println(Jdk11Http.class.isInterface());
		System.out.println(Jdk11Http.class.isLocalClass());
		System.out.println(Jdk11Http.class.isMemberClass());
		System.out.println(Jdk11Http.class.isPrimitive());
		System.out.println(Jdk11Http.class.isRecord());
		System.out.println(Jdk11Http.class.isSealed());
		System.out.println(Jdk11Http.class.isSynthetic());
//		int m = 5299;
//		double b = 0.0016974;
//		double n = 0;
//		for(int i=0;i<24;i++) {
//			n+=m*b;
//			m-=220.8;
//		}
//		System.out.println("m="+m+";n="+n);
//		
//		  m = 6999; 
//		  n = 0;
//		for(int i=0;i<6;i++) {
//			n+=m*b;
//			m-=1166.5;
//		}
//		System.out.println("m="+m+";n="+n);
		
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
