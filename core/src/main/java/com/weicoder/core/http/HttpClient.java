package com.weicoder.core.http;

import java.io.InputStream; 
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost; 
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;

import com.weicoder.common.constants.ArrayConstants;
import com.weicoder.common.constants.HttpConstants;
import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.constants.SystemConstants;
import com.weicoder.common.io.IOUtil;
import com.weicoder.common.lang.Conversion;
import com.weicoder.common.lang.Lists;
import com.weicoder.common.log.Logs;
import com.weicoder.common.params.CommonParams;
import com.weicoder.common.util.EmptyUtil;
import com.weicoder.common.util.StringUtil;

/**
 * HTTP客户端工具类
 * @author WD
 */
public final class HttpClient {
	// Http客户端
	final static CloseableHttpClient CLIENT;

	static {
		// Http连接池
		PoolingHttpClientConnectionManager pool = new PoolingHttpClientConnectionManager();
		pool.setDefaultMaxPerRoute(SystemConstants.CPU_NUM);
		pool.setMaxTotal(SystemConstants.CPU_NUM * 10);
		// 设置请求参数
		RequestConfig.Builder config = RequestConfig.custom();
		config.setSocketTimeout(5000);
		config.setConnectTimeout(5000);
		config.setCircularRedirectsAllowed(false);
		// HttpClientBuilder
		HttpClientBuilder builder = HttpClientBuilder.create();
		builder.setDefaultRequestConfig(config.build());
		builder.setConnectionManager(pool);
		builder.setMaxConnPerRoute(SystemConstants.CPU_NUM);
		// 设置 头
		List<BasicHeader> headers = Lists.newList();
		headers.add(new BasicHeader(HttpConstants.USER_AGENT_KEY, HttpConstants.USER_AGENT_VAL));
		headers.add(new BasicHeader(HttpConstants.ACCEPT_KEY, HttpConstants.ACCEPT_VAL));
		// headers.add(new BasicHeader(HttpConstants.ACCEPT_LANGUAGE_KEY,
		// HttpConstants.ACCEPT_LANGUAGE_VAL));
		// headers.add(new BasicHeader(HttpConstants.ACCEPT_CHARSET_KEY,
		// HttpConstants.ACCEPT_CHARSET_VAL));
		builder.setDefaultHeaders(headers);
//		// 设置连接配置
//		builder.setDefaultConnectionConfig(
//				ConnectionConfig.custom().setCharset(Charset.forName(CommonParams.ENCODING)).build());
		// 实例化客户端
		CLIENT = builder.build();
	}

	/**
	 * 模拟get提交
	 * @param url get提交地址
	 * @return 返回结果
	 */
	public static String get(String url) {
		return get(url, CommonParams.ENCODING);
	}

	/**
	 * 模拟get提交
	 * @param url get提交地址
	 * @param charset 编码
	 * @return 返回结果
	 */
	public static String get(String url, String charset) {
		return StringUtil.toString(download(url), charset);
	}

	/**
	 * 下载文件
	 * @param url get提交地址
	 * @return 返回流
	 */
	public static byte[] download(String url) {
		// 声明HttpGet对象
		HttpGet get = null;
		try {
			// 获得HttpGet对象
			get = new HttpGet(url);
			// 获得HttpResponse
			HttpResponse response = CLIENT.execute(get);
			// 返回字节流
			try (InputStream in = response.getEntity().getContent()) {
				return IOUtil.read(in);
			} catch (Exception e) {
				Logs.error(e);
			}
		} catch (Exception e) {
			Logs.error(e);
		} finally {
			// 销毁get
			if (get != null) {
				get.abort();
			}
		}
		return ArrayConstants.BYTES_EMPTY;
	}

	/**
	 * 模拟post提交
	 * @param url post提交地址
	 * @param data 提交参数
	 * @return 提交结果
	 */
	public static String post(String url, Map<String, Object> data) {
		return post(url, data, CommonParams.ENCODING);
	}

	/**
	 * 模拟post提交
	 * @param url post提交地址
	 * @param data 提交参数
	 * @param charset 编码
	 * @return 提交结果
	 */
	public static String post(String url, Map<String, Object> data, String charset) {
		// 声明HttpPost
		HttpPost post = null;
		try {
			// 获得HttpPost
			post = new HttpPost(url);
			// 如果参数列表为空 data为空map
			if (!EmptyUtil.isEmpty(data)) {
				// 声明参数列表
				List<NameValuePair> list = Lists.newList(data.size());
				// 设置参数
				for (Map.Entry<String, Object> entry : data.entrySet()) {
					// 添加参数
					list.add(new BasicNameValuePair(entry.getKey(), Conversion.toString(entry.getValue())));
				}
				// 设置参数与 编码格式
				post.setEntity(new UrlEncodedFormEntity(list, charset));
			}
			// 获得HttpResponse参数
			HttpResponse response = CLIENT.execute(post);
			// 返回结果
			try (InputStream in = response.getEntity().getContent()) {
				return IOUtil.readString(in);
			} catch (Exception e) {
				Logs.error(e);
			}
		} catch (Exception e) {
			Logs.error(e);
		} finally {
			// 销毁post
			if (post != null) {
				post.abort();
			}
		}
		return StringConstants.EMPTY;
	}

	private HttpClient() {}
}
