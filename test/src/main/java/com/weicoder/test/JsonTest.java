package com.weicoder.test;

import com.weicoder.json.JsonEngine;

public class JsonTest {

	public static void main(String[] args) {
		Users user = new Users(true, Byte.MAX_VALUE, Short.MAX_VALUE, 1, 2, 3L, 4, "5", new byte[]{0});
		String json = JsonEngine.toJson(user);
		System.out.println(json);
		Users u = JsonEngine.toBean(json, Users.class);
		System.out.println(u);
		
		SearchBean bean = new SearchBean(1215000L, 1000000L, "admin");
		String s = JsonEngine.toJson(bean);
		System.out.println(s);
		SearchBean sb = JsonEngine.toBean(s, SearchBean.class);
		System.out.println(sb);
	} 
}
