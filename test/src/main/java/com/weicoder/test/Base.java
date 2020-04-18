package com.weicoder.test;

public class Base {
	public void get() {
		System.out.println("base get ...");
	}
	
	public void put() {
		System.out.println("base put ...");
		get();
	}
}
