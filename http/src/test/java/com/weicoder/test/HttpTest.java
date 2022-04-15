package com.weicoder.test;
  
import org.junit.Test;

import com.weicoder.common.bean.Result; 
import com.weicoder.http.HttpManager; 

public class HttpTest {
	@Test
	public void main() {   
		UserHttp user = HttpManager.create(UserHttp.class);
		Result<User> u = user.info(10001004);
		System.out.println(u);
		System.out.println(u.getContent());
		System.out.println(u.getContent().getUid());
		u = user.info(111123414);
		System.out.println(u);
		
//		System.out.println(HttpFactory.getHttp());
//		System.out.println(HttpFactory.getHttp("jdk11"));
//		System.out.println(HttpFactory.getHttp("jdk8"));
//		String url = "https://www.baidu.com/sugrec";
//		String r1 = com.weicoder.http4.HttpClient.get(url);
//		String r2 = com.weicoder.http.HttpClient.get(url);
//		System.out.println("http=" + r1);
//		System.out.println("http2=" + r2);
//		System.out.println(r1.equals(r2));
//		System.out.println(r1.length() == r2.length());
//		int n = 100;
//		long curr = System.currentTimeMillis();
//		for (int i = 0; i < n; i++)
//			r1 = HttpEngine.get(url);
//		System.out.println((System.currentTimeMillis() - curr) + "h" + r1);
//		curr = System.currentTimeMillis();
//		for (int i = 0; i < n; i++)
//			HttpEngine.get(url);
//		System.out.println((System.currentTimeMillis() - curr) + "h2" + r1);
//		curr = System.currentTimeMillis();
//		for (int i = 0; i < n; i++)
//			com.weicoder.http.HttpClient.get(url);
//		System.out.println((System.currentTimeMillis() - curr) + "a" + r1);
//		HttpClient http = HttpClient.newBuilder().version(Version.HTTP_1_1).build();
//		curr = System.currentTimeMillis();
//		for (int i = 0; i < n; i++)
//			get(url, http);
//		System.out.println((System.currentTimeMillis() - curr) + "h1" + r1);
//		http = HttpClient.newBuilder().version(Version.HTTP_2).build();
//		curr = System.currentTimeMillis();
//		for (int i = 0; i < n; i++)
//			get(url, http);
//		System.out.println((System.currentTimeMillis() - curr) + "h2" + r1);
	}
//
//	public static byte[] get(String url, HttpClient client) {
//		try {
//			// 获得HttpRequest构建器
//			HttpRequest.Builder builder = HttpRequest.newBuilder(new URI(url));
//			// HttpRequest
//			HttpRequest request = builder.GET().build();
//			// 请求
//			HttpResponse<byte[]> response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());
//			// 返回结果
//			return response.body();
//		} catch (Exception e) {
//			Logs.error(e, "Http2Engine get url={}", url);
//		}
//		return ArrayConstants.BYTES_EMPTY;
//	}
}
