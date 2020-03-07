package com.weicoder.test;
 
import com.weicoder.common.log.Logs;
import com.weicoder.http.HttpClient;

public class Test {

	public static void main(String[] args) throws Exception {
		String url = "https://www.baidu.com/sugrec";
		Logs.info(HttpClient.get(url));
		Logs.warn("123321");
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
