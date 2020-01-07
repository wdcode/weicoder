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
import com.weicoder.common.lang.Conversion;
import com.weicoder.common.lang.Maps;
import com.weicoder.common.log.Logs;
import com.weicoder.common.util.EmptyUtil;
import com.weicoder.common.util.StringUtil;

/**
 * http客户端通讯
 * 
 * @author WD
 */
public final class HttpEngine {
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
			HttpRequest.Builder builder = HttpRequest.newBuilder(new URI(url));
			// 头不为空，添加头
			if (EmptyUtil.isNotEmpty(header))
				for (Map.Entry<String, Object> h : header.entrySet())
					builder.setHeader(h.getKey(), Conversion.toString(h.getValue()));
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

	/**
	 * 使用post提交url
	 * 
	 * @param  url  网址
	 * @param  data 参数
	 * @return      返回的结果
	 */
	public static String post(String url, Map<String, Object> data) {
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
	public static String post(String url, Map<String, Object> data, Map<String, Object> header) {
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
	public static String post(HttpClient client, String url, Map<String, Object> data, Map<String, Object> header) {
		try {
			// 获得HttpRequest构建器
			HttpRequest.Builder builder = HttpRequest.newBuilder(new URI(url));
			// 头不为空，添加头
			if (EmptyUtil.isNotEmpty(header))
				for (Map.Entry<String, Object> h : header.entrySet())
					builder.setHeader(h.getKey(), Conversion.toString(h.getValue()));

			// 请求body
			String body = null;
			// 判断有参数提交
			if (EmptyUtil.isNotEmpty(data)) {
				// 声明字符串缓存
				StringBuilder sb = new StringBuilder();
				// 循环参数
				data.entrySet().forEach(e -> {
					// 添加条件与分隔符
					sb.append(e.getKey()).append("=").append(e.getValue()).append("&");
				});
				body = sb.substring(0, sb.length() - 1);

			}
			// HttpRequest
			HttpRequest request = builder.POST(BodyPublishers.ofString(body)).build();
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

	private HttpEngine() {
	}
}
