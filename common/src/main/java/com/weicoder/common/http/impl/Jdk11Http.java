package com.weicoder.common.http.impl;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest.BodyPublishers;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import com.weicoder.common.constants.C;
import com.weicoder.common.http.Http;
import com.weicoder.common.lang.W;
import com.weicoder.common.log.L;
import com.weicoder.common.log.Logs;
import com.weicoder.common.util.U;
import com.weicoder.common.util.U.S;

/**
 * http jdk11的实现
 * 
 * @author wudi
 */
public class Jdk11Http implements Http {
	// 全局HttpClient
	private HttpClient client;// = HttpClient.newBuilder().version(Version.HTTP_2).build();

	public Jdk11Http() {
		try {// TLSv1.3 DTLSv1.2
			SSLContext ssl = SSLContext.getInstance("TLSv1.3");
			ssl.init(null, new TrustManager[] { new X509TrustManager() {
				@Override
				public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				}

				@Override
				public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				}

				@Override
				public X509Certificate[] getAcceptedIssuers() {
					return new X509Certificate[0];
				}
			} }, new SecureRandom());
			client = HttpClient.newBuilder().sslContext(ssl).version(Version.HTTP_2).build();
//			client = HttpClient.newBuilder().sslContext(SSLContext.getDefault()).version(Version.HTTP_2).build();
		} catch (Exception e) {
			L.info(e);
			client = HttpClient.newBuilder().version(Version.HTTP_2).build();
		}
	}

	@Override
	public String post(String url, Map<String, Object> data, Map<String, Object> header) {
		try {
			// 请求body
			String body = S.add("?", S.toParameters(data));
//				// 判断有参数提交
//				if (U.E.isNotEmpty(data)) {
//					// 声明字符串缓存
//					StringBuilder sb = new StringBuilder("?");
//					// 循环参数
//					data.entrySet().forEach(e -> sb.append(e.getKey()).append("=").append(e.getValue()).append("&"));
//					body = sb.substring(0, sb.length() - 1);
//				}

			// 获得HttpRequest构建器
			HttpRequest.Builder builder = HttpRequest.newBuilder(URI.create(url + body));
			// 头不为空，添加头
			if (U.E.isNotEmpty(header))
				header.forEach((k, v) -> builder.setHeader(k, W.C.toString(v)));
			// HttpRequest
			HttpRequest request = builder.POST(BodyPublishers.noBody()).build();
			// 请求
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
			// 使用GZIP一般服务器支持解压获得的流 然后转成字符串 一般为UTF-8
//			String res = response.body();
//			Logs.debug("Jdk11Http post url={} data={} header={} res={}", url, data, header, res);
			return response.body();
		} catch (Exception e) {
			Logs.error(e, "Jdk11Http post url={} data={} header={}", url, data, header);
		}
		return C.S.EMPTY;
	}

	@Override
	public byte[] download(String url, Map<String, Object> header) {
		try {
			// 获得HttpRequest构建器
			HttpRequest.Builder builder = HttpRequest.newBuilder(URI.create(url));
			// 头不为空，添加头
			if (U.E.isNotEmpty(header))
				for (Map.Entry<String, Object> h : header.entrySet())
					builder.setHeader(h.getKey(), W.C.toString(h.getValue()));
			// HttpRequest
			HttpRequest request = builder.GET().build();
			// 请求
			HttpResponse<byte[]> response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());
			// 返回结果
			return response.body();
		} catch (Exception e) {
			Logs.error(e, "Jdk11Http download url={}", url);
		}finally {
			client.close();
		}
		return C.A.BYTES_EMPTY;
//		return response(url, header, HttpResponse.BodyHandlers.ofByteArray());
	}

//	@Override
//	public String get(String url, Map<String, Object> header) {
//		try {
//			// 获得HttpRequest构建器
//			HttpRequest.Builder builder = HttpRequest.newBuilder(URI.create(url));
//			// 头不为空，添加头
//			if (U.E.isNotEmpty(header))
//				for (Map.Entry<String, Object> h : header.entrySet())
//					builder.setHeader(h.getKey(), W.C.toString(h.getValue()));
//			// HttpRequest
//			HttpRequest request = builder.GET().build();
//			// 请求
//			HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
//			// 返回结果
//			return response.body();
//		} catch (Exception e) {
//			Logs.error(e, "Jdk11Http get url={}", url);
//		}
	//// return response(url, header,
	/// HttpResponse.BodyHandlers.ofString());// C.S.EMPTY;
//	}

//	private <T> T response(String url, Map<String, Object> header, BodyHandler<T> body) {
//		try {
//			// 获得HttpRequest构建器
//			HttpRequest.Builder builder = HttpRequest.newBuilder(URI.create(url));
//			// 头不为空，添加头
//			if (U.E.isNotEmpty(header))
//				for (Map.Entry<String, Object> h : header.entrySet())
//					builder.setHeader(h.getKey(), W.C.toString(h.getValue()));
//			// HttpRequest
//			HttpRequest request = builder.GET().build();
//			// 请求
//			HttpResponse<T> response = CLIENT.send(request, body);
//			// 返回结果
//			return response.body();
//		} catch (Exception e) {
//			Logs.error(e, "Jdk*Http get url={}", url);
//			return null;
//		}
//	}
}
