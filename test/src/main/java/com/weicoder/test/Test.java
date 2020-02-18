package com.weicoder.test;
 
import java.util.List;
 
import com.weicoder.common.log.Logs;

import lombok.NonNull;

public class Test {

	public static void main(String[] args) {
		Logs.info("123");
//		List<Integer> l = Lists.newList();
//		l.add(1);
//		test(l);
//		test(null);
//		l.add(2);
//		l.add(3);
//		test(l);
	}	
	
	
	public static void test(@NonNull List<?> l) {
		System.out.println(l.size());
	}
}
