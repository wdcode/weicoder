package com.weicoder.test;

import com.weicoder.common.util.ThreadUtil;
import com.weicoder.cache.LoadCache;
import com.weicoder.cache.CacheBuilder;

public class CacheTest {

	public static void main(String[] args) {
		String key = "1";
		LoadCache<String, String> cache = CacheBuilder.build(uid -> getRedis(uid));
		cache.put(key, "2");
		System.out.println(cache.get(key));
		ThreadUtil.sleep(1);
		System.out.println(cache.get(key));
	}

	/**
	 * 获得用户缓存
	 * 
	 * @param  uid 用户id
	 * @return     用户缓存
	 */
	private static String getRedis(String uid) {
		return "3";
	}
}
