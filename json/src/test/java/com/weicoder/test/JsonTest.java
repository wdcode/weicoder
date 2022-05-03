package com.weicoder.test;

import org.junit.jupiter.api.Test;

import com.weicoder.json.J;
  
public class JsonTest { 
	@Test
	public void main() {
		Users user = new Users(3L, true, Byte.MAX_VALUE, Short.MAX_VALUE, 1, 2, 4, "5", new byte[]{0});
		String json = J.toJson(user);
		System.out.println(json);
		Users u = J.toBean(json, Users.class);
		System.out.println(u);

		SearchBean bean = new SearchBean(1215000L, 1000000L, "admin");
		String s = J.toJson(bean);
		System.out.println(s);
		SearchBean sb = J.toBean(s, SearchBean.class);
		System.out.println(sb);
	} 
}
