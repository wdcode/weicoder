package com.weicoder.test;
 
import com.weicoder.http5.HttpClient;

public class Test {

	public static void main(String[] args) throws Exception {
		String url = "https://www.baidu.com/sugrec";
		System.out.println(HttpClient.get(url));
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
