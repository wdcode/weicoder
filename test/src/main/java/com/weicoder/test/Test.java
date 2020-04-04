package com.weicoder.test;
  
public class Test {

	public static void main(String[] args) throws Exception {
		Class<RpcS> r = RpcS.class;
		System.out.println(r.getNestHost().getName());
		System.out.println(r.getCanonicalName());
	}
}
