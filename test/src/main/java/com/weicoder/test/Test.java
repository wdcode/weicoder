package com.weicoder.test;

import com.weicoder.extend.Logs;

public class Test {

	public static void main(String[] args) throws Exception {
		Logs.info("sss");
		Logs.error("sss");
// 		int[] i = new int[]{0};
//		to(i);
//		System.out.println(i[0]);
//		to(i);
//		System.out.println(i[0]);
	}

	public static void to(int[] i) {
		System.out.println(i[0]);
		System.out.println(i[0]++);
		System.out.println(i[0] += 1); 
	}
}
