package com.weicoder.test;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient.Version;

import com.weicoder.common.constants.ArrayConstants;
import com.weicoder.common.http.HttpEngine; 
import com.weicoder.common.log.Logs;

public class HttpTest {

	public static void main(String[] args) {
		String url = "https://www.baidu.com/sugrec";
		String r1 = HttpEngine.get(url);
		String r2 = HttpEngine.get(url);
		System.out.println("http=" + r1);
		System.out.println("http2=" + r2);
		System.out.println(r1.equals(r2));
		System.out.println(r1.length() == r2.length());
		int n = 100;
		long curr = System.currentTimeMillis();
		for (int i = 0; i < n; i++)
			r1 = HttpEngine.get(url);
		System.out.println((System.currentTimeMillis() - curr) + "h" + r1);
		curr = System.currentTimeMillis();
		for (int i = 0; i < n; i++)
			HttpEngine.get(url);
		System.out.println((System.currentTimeMillis() - curr) + "h2" + r1);
		curr = System.currentTimeMillis();
		for (int i = 0; i < n; i++)
			com.weicoder.http.HttpClient.get(url);
		System.out.println((System.currentTimeMillis() - curr) + "a" + r1);
		HttpClient http = HttpClient.newBuilder().version(Version.HTTP_1_1).build();
		curr = System.currentTimeMillis();
		for (int i = 0; i < n; i++)
			get(url, http);
		System.out.println((System.currentTimeMillis() - curr) + "h1" + r1);
		http = HttpClient.newBuilder().version(Version.HTTP_2).build();
		curr = System.currentTimeMillis();
		for (int i = 0; i < n; i++)
			get(url, http);
		System.out.println((System.currentTimeMillis() - curr) + "h2" + r1);
	}

	public static byte[] get(String url, HttpClient client) {
		try {
			// 获得HttpRequest构建器
			HttpRequest.Builder builder = HttpRequest.newBuilder(new URI(url));
			// HttpRequest
			HttpRequest request = builder.GET().build();
			// 请求
			HttpResponse<byte[]> response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());
			// 返回结果
			return response.body();
		} catch (Exception e) {
			Logs.error(e, "Http2Engine get url={}", url);
		}
		return ArrayConstants.BYTES_EMPTY;
	}
}
