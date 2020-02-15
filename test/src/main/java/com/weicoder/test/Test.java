package com.weicoder.test;
 
import java.util.List;

import com.weicoder.common.lang.Lists;

import lombok.NonNull;

public class Test {

	public static void main(String[] args) {
		List<Integer> l = Lists.newList();
		l.add(1);
		test(l);
		test(null);
		l.add(2);
		l.add(3);
		test(l);
	}	
	
	
	public static void test(@NonNull List<?> l) {
		System.out.println(l.size());
	}
}
