package com.weicoder.test;

import com.weicoder.memcache.Memcache;
import com.weicoder.memcache.factory.MemcacheFactory;

public class MemcacheTest {

	public static void main(String[] args) {
		// telnet 172.21.168.230 11211
		Memcache mem = MemcacheFactory.getMemcache();
		long curr = System.currentTimeMillis();
		int n = 100000;
		String key = "test";
		mem.set(key, key);
		System.out.println(mem.get(key));
		for(int i=0;i<n;i++) 
			mem.set(key+i, key+i);
		for(int i=0;i<n;i++) 
			mem.get(key+i);
		System.out.println(mem.get(key+(n-1)));
		System.out.println(System.currentTimeMillis() - curr);
	}

}
