package com.weicoder.test;

import com.weicoder.common.http.HttpEngine;
import com.weicoder.common.lang.Maps; 

public class Test {

	public static void main(String[] args) {
		String url = "http://m.i4322.com/user/get/info?uid=10000002";
		System.out.println(HttpEngine.get(url));
		url = "http://m.i4322.com/user/get/info";
		System.out.println(HttpEngine.post(url, Maps.newMap("uid", "10000002")));
//		System.out.println(DateUtil.toString(1578883508));
//		System.out.println(Instant.now().toEpochMilli());
//		System.out.println(System.currentTimeMillis());
//		int n = 10000000;
//		long curr = System.currentTimeMillis();
//		for (int i = 0; i < n; i++)
//			System.currentTimeMillis();
//		System.out.println(System.currentTimeMillis() - curr);
//		curr = System.currentTimeMillis();
//		for (int i = 0; i < n; i++)
//			Instant.now().toEpochMilli();
//		System.out.println(System.currentTimeMillis() - curr);
//		String name = "admin";
//		String token = AdminToken.encrypt(name, IpUtil.SERVER_IP);
//		System.out.println(token);
//		System.out.println(AdminToken.decrypt(token));
//		System.out.println(Admin.class.getCanonicalName());
//		System.out.println(Admin.class.getSimpleName());
//		System.out.println(StringUtil.convert(Admin.class.getSimpleName()));
//		Logs.debug(token);
//		System.out.println(KafkaParams.getGroup(""));
//		String s = "1%s2";
//		System.out.println(Bytes.toBytes(s).length);
//		System.out.println(String.format(s, "-"));
//		System.out.println(Short.MAX_VALUE);
//		String url = "http://m.i4322.com/user/login/login";
//		Map<String, Object> data = Maps.newMap();
//		data.put("type", "mobile");
//		data.put("mobile", "13261115158");
//		data.put("password", "123456");
//
//		System.out.println(HttpEngine.post(url, data));
//		StateCode code = HttpClient.post(url, new Param("mobile", "13261115158", "123456"));
//		System.out.println(code);
//		System.out.println(code.getContent());
//		Map<String, Object> d = Maps.newMap();
//		d.put("uid", 10000002L);
//		d.put("tid", 10000001L);
//		d.put("dd", "哈哈哈");
//		System.out.println(JsonEngine.toJson(d));
//		System.out.println(HttpClient.post("http://127.0.0.1:8080/logic/follow/isFollow",d));
	}
}
