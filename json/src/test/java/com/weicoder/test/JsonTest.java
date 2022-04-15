package com.weicoder.test;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
//import com.google.gson.Gson; 

public class JsonTest {
//	private final static Gson GSON = new GsonBuilder().create();
	@Test
	public void main() {
		Users user = new Users(3L, true, Byte.MAX_VALUE, Short.MAX_VALUE, 1, 2, 4, "5", new byte[]{0});
		String json = toJson(user);
		System.out.println(json);
		Users u = toBean(json, Users.class);
		System.out.println(u);

		SearchBean bean = new SearchBean(1215000L, 1000000L, "admin");
		String s = toJson(bean);
		System.out.println(s);
		SearchBean sb = toBean(s, SearchBean.class);
		System.out.println(sb);
	}

	public String toJson(Object obj) {
		return JSON.toJSONString(obj);
//		return GSON.toJson(obj);
	}

	public <E> E toBean(String json, Class<E> clazz) {
		return JSON.parseObject(json, clazz);
//		return GSON.fromJson(json, clazz);
	}
}
