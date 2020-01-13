package com.weicoder.test;

import com.weicoder.redis.builder.JedisBuilder;

import redis.clients.jedis.Jedis;

public class RedisTest {

	public static void main(String[] args) {
		String name = "user";
		String key = "test";
//		JedisCluster r = JedisBuilder.buildCluster(name); //
		Jedis r = JedisBuilder.buildPool(name).getResource();// 3303-3170-3171
//		RedisCommands<String, String> r = LettuceBuilder.buildPool(name).connect().sync();//4026-4081-3830
//		RedissonClient r = RedissonBuilder.newBuilder(name);//4454-3732-4052
//		RedisAsyncCommands<String, String> r = LettuceBuilder.buildPool(name).connect().async();
		int n = 1000;
		r.set(key, key);
		System.out.println(r.get(key));
		long curr = System.currentTimeMillis();
		for (int i = 0; i < n; i++)
			r.set(key + i, key + i);
		System.out.println("redis set is time=" + (System.currentTimeMillis() - curr));
		curr = System.currentTimeMillis();
		for (int i = 0; i < n; i++)
			r.get(key + i);
		System.out.println("redis get is time=" + (System.currentTimeMillis() - curr));
		curr = System.currentTimeMillis();
		for (int i = 0; i < n; i++)
			r.del(key + i);
		System.out.println("redis del is time=" + (System.currentTimeMillis() - curr));

	}

}
