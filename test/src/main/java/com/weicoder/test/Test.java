package com.weicoder.test;
  
import java.io.Serializable;

public class Test {

	public static void main(String[] args) throws Exception { 
		Serializable s = "546e3";
		System.out.println(s.toString());
		Serializable e = "1002456";
		System.out.println(e.toString());
		Serializable i = 100248;
		System.out.println(i.toString());
		Serializable l = 2002456L;
		System.out.println(l);
//		String url = "https://www.baidu.com/sugrec";
//		Logs.info(HttpClient.get(url));
//		Logs.warn("123321");
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
