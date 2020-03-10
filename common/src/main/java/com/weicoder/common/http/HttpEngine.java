package com.weicoder.common.http;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.util.Map;

import com.weicoder.common.constants.ArrayConstants;
import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.U;
import com.weicoder.common.W;
import com.weicoder.common.lang.Maps;
import com.weicoder.common.log.Logs;
import com.weicoder.common.util.BeanUtil;
import com.weicoder.common.util.StringUtil;

/**
 * http客户端通讯
 * 
 * @author WD
 */
public class HttpEngine {
	// 全局HttpClient
	private final static HttpClient CLIENT = createClient();

	/**
	 * 使用get提交url
	 * 
	 * @param  url 网址
	 * @return     返回的结果
	 */
	public static byte[] download(String url) {
		return download(url, Maps.emptyMap());
	}

	/**
	 * 使用get提交url
	 * 
	 * @param  url 网址
	 * @return     返回的结果
	 */
	public static String get(String url) {
		// 使用GZIP一般服务器支持解压获得的流 然后转成字符串 一般为UTF-8
		String res = StringUtil.toString(download(url));
		Logs.debug("HttpEngine get url={} res={}", url, res);
		// 返回对象
		return res;
	}

	/**
	 * 使用get提交url
	 * 
	 * @param  url    网址
	 * @param  header http头列表
	 * @return        返回的结果
	 */
	public static String get(String url, Map<String, Object> header) {
		// 使用GZIP一般服务器支持解压获得的流 然后转成字符串 一般为UTF-8
		String res = StringUtil.toString(download(url, header));
		Logs.debug("HttpEngine get url={} header={} res={}", url, header, res);
		// 返回对象
		return res;
	}

	/**
	 * 使用get提交url
	 * 
	 * @param  url    网址
	 * @param  header http头列表
	 * @return        返回的结果
	 */
	public static byte[] download(String url, Map<String, Object> header) {
		return download(CLIENT, url, header);
	}

	/**
	 * 使用get提交url
	 * 
	 * @param  client HttpClinet
	 * @param  url    网址
	 * @param  header http头列表
	 * @return        返回的结果
	 */
	public static byte[] download(HttpClient client, String url, Map<String, Object> header) {
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
			Logs.error(e, "Http2Engine download url={}", url);
		}
		return ArrayConstants.BYTES_EMPTY;
	}

	/**
	 * 模拟post提交 定制提交 参数对象与提交参数相同 返回结果为json对象
	 * 
	 * @param  url  post提交地址
	 * @param  data 提交参数
	 * @param  c    返回类类型
	 * @return      提交结果
	 */
	public static String post(String url, Object data) {
		return post(url, BeanUtil.copy(data, Maps.newMap()));
	}

	/**
	 * 使用post提交url
	 * 
	 * @param  url  网址
	 * @param  data 参数
	 * @return      返回的结果
	 */
	public static String post(String url, Map<String, String> data) {
		return post(url, data, Maps.emptyMap());
	}

	/**
	 * 使用post提交url
	 * 
	 * @param  url    网址
	 * @param  data   参数
	 * @param  header http头列表
	 * @return        返回的结果
	 */
	public static String post(String url, Map<String, String> data, Map<String, String> header) {
		return post(CLIENT, url, data, header);
	}

	/**
	 * 使用post提交url
	 * 
	 * @param  client HttpClinet
	 * @param  url    网址
	 * @param  data   参数
	 * @param  header http头列表
	 * @return        返回的结果
	 */
	public static String post(HttpClient client, String url, Map<String, String> data, Map<String, String> header) {
		try {
			// 请求body
			String body = StringUtil.add("?", StringUtil.toParameters(data));
//			// 判断有参数提交
//			if (U.E.isNotEmpty(data)) {
//				// 声明字符串缓存
//				StringBuilder sb = new StringBuilder("?");
//				// 循环参数
//				data.entrySet().forEach(e -> sb.append(e.getKey()).append("=").append(e.getValue()).append("&"));
//				body = sb.substring(0, sb.length() - 1);
//			}

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
			String res = response.body();
			Logs.debug("HttpEngine post url={} data={} header={} res={}", url, data, header, res);
			return res;
		} catch (Exception e) {
			Logs.error(e, "HttpEngine post url={} data={} header={}", url, data, header);
		}
		return StringConstants.EMPTY;
	}

	/**
	 * 创建http2 HttpClient
	 * 
	 * @return HttpClient
	 */
	private static HttpClient createClient() {
		return HttpClient.newBuilder().version(Version.HTTP_2).build();
	}
}
