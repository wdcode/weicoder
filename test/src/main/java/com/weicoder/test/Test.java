package com.weicoder.test;
  
public class Test {

	public static void main(String[] args) throws Exception {
		Class<RpcS> r = RpcS.class;
		System.out.println(r.getNestHost().getName());
		System.out.println(r.getCanonicalName());
		System.out.println(r.getName());
		System.out.println(r.getPackageName());
		System.out.println(r.getPackage().getName());
	}
}
