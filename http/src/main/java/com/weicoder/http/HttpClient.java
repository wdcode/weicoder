package com.weicoder.http;

import java.util.List;
import java.util.Map;

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

import com.weicoder.common.bean.StateCode;
import com.weicoder.common.constants.ArrayConstants;
import com.weicoder.common.constants.HttpConstants;
import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.constants.SystemConstants;
import com.weicoder.common.io.IOUtil;
import com.weicoder.common.lang.Conversion;
import com.weicoder.common.lang.Lists;
import com.weicoder.common.lang.Maps;
import com.weicoder.common.log.Log;
import com.weicoder.common.log.LogFactory;
import com.weicoder.common.params.CommonParams;
import com.weicoder.common.util.BeanUtil;
import com.weicoder.common.util.EmptyUtil;
import com.weicoder.common.util.StringUtil;
import com.weicoder.core.json.JsonEngine;
import com.weicoder.http.params.HttpParams;

/**
 * HTTP客户端工具类
 * 
 * @author WD
 */
public final class HttpClient {
	// 日志
	private final static Log LOG = LogFactory.getLog(HttpClient.class);
	// Http客户端
	final static CloseableHttpClient CLIENT = init();

	/**
	 * 获得HttpClient
	 * 
	 * @return HttpClient
	 */
	public static CloseableHttpClient getClient() {
		return CLIENT;
	}

	/**
	 * 模拟get提交
	 * 
	 * @param  url get提交地址
	 * @return     返回结果
	 */
	public static String get(String url) {
		return get(url, CommonParams.ENCODING);
	}

	/**
	 * 模拟get提交 定制提交 参数对象与提交参数相同 返回结果为json对象
	 * 
	 * @param  url  get提交地址
	 * @param  data 提交参数
	 * @return      提交结果
	 */
	public static StateCode getToState(String url) {
		// 返回json转换成对象
		return JsonEngine.toBean(get(url, CommonParams.ENCODING), StateCode.class);
	}

	/**
	 * 模拟get提交 定制提交 参数对象与提交参数相同 返回结果为json对象
	 * 
	 * @param  url  get提交地址
	 * @param  data 提交参数
	 * @return      提交结果
	 */
	public static <E> E get(String url, Class<E> c) {
		// 返回json转换成对象
		return JsonEngine.toBean(get(url, CommonParams.ENCODING), c);
	}

	/**
	 * 模拟get提交
	 * 
	 * @param  url     get提交地址
	 * @param  charset 编码
	 * @return         返回结果
	 */
	public static String get(String url, String charset) {
		return StringUtil.toString(download(url), charset);
	}

	/**
	 * 下载文件
	 * 
	 * @param  url get提交地址
	 * @return     返回流
	 */
	public static byte[] download(String url) {
		// 声明HttpGet对象
		HttpGet get = null;
		try {
			LOG.debug("HttpClient get url={}", url);
			// 获得HttpGet对象
			get = new HttpGet(url);
			// 获得HttpResponse 返回字节流
			return IOUtil.read(CLIENT.execute(get).getEntity().getContent());
		} catch (Exception e) {
			LOG.error(e);
		} finally {
			// 销毁get
			if (get != null) {
				get.abort();
			}
		}
		return ArrayConstants.BYTES_EMPTY;
	}

	/**
	 * 模拟post提交 定制提交 参数对象与提交参数相同 返回结果为json对象
	 * 
	 * @param  url  post提交地址
	 * @param  data 提交参数
	 * @return      提交结果
	 */
	public static StateCode post(String url, Object data) {
		return post(url, data, StateCode.class);
	}

	/**
	 * 模拟post提交 定制提交 参数对象与提交参数相同 返回结果为json对象
	 * 
	 * @param  url  post提交地址
	 * @param  data 提交参数
	 * @return      提交结果
	 */
	public static <E> E post(String url, Object data, Class<E> c) {
		// 设置参数
		Map<String, Object> params = Maps.newMap();
		BeanUtil.copy(data, params);
		// 返回json转换成对象
		return JsonEngine.toBean(post(url, params, CommonParams.ENCODING), c);
	}

	/**
	 * 模拟post提交
	 * 
	 * @param  url  post提交地址
	 * @param  data 提交参数
	 * @return      提交结果
	 */
	public static String post(String url, Map<String, Object> data) {
		return post(url, data, CommonParams.ENCODING);
	}

	/**
	 * 模拟post提交
	 * 
	 * @param  url    post提交地址
	 * @param  data   提交参数
	 * @param  header http头
	 * @return        提交结果
	 */
	public static String post(String url, Map<String, Object> data, Map<String, Object> header) {
		return post(url, data, header, CommonParams.ENCODING);
	}

	/**
	 * 模拟post提交
	 * 
	 * @param  url     post提交地址
	 * @param  data    提交参数
	 * @param  charset 编码
	 * @return         提交结果
	 */
	public static String post(String url, Map<String, Object> data, String charset) {
		return post(url, data, Maps.emptyMap(), charset);
	}

	/**
	 * 模拟post提交
	 * 
	 * @param  url     post提交地址
	 * @param  data    提交参数
	 * @param  header  http头
	 * @param  charset 编码
	 * @return         提交结果
	 */
	public static String post(String url, Map<String, Object> data, Map<String, Object> header, String charset) {
		// 声明HttpPost
		HttpPost post = null;
		try {
			// 获得HttpPost
			post = new HttpPost(url);
			// 如果参数列表为空 data为空map
			if (EmptyUtil.isNotEmpty(data)) {
				// 声明参数列表
				List<NameValuePair> list = Lists.newList(data.size());
				// 设置参数
				data.forEach((k, v) -> list.add(new BasicNameValuePair(k, Conversion.toString(v))));
				// 设置参数与 编码格式
				post.setEntity(new UrlEncodedFormEntity(list, charset));
			}
			// 添加http头
			for (Map.Entry<String, Object> h : header.entrySet()) {
				post.addHeader(h.getKey(), Conversion.toString(h.getValue()));
			}
			LOG.debug("HttpClient post url={} data={} header={} charset={}", url, data, header, charset);
			// 返回结果
			return IOUtil.readString(CLIENT.execute(post).getEntity().getContent());
		} catch (Exception e) {
			LOG.error(e);
		} finally {
			// 销毁post
			if (post != null) {
				post.abort();
			}
		}
		return StringConstants.EMPTY;
	}

	/**
	 * 初始化httpclient
	 * 
	 * @return CloseableHttpClient
	 */
	private static CloseableHttpClient init() {
		// Http连接池
		PoolingHttpClientConnectionManager pool = new PoolingHttpClientConnectionManager();
		pool.setDefaultMaxPerRoute(SystemConstants.CPU_NUM);
		pool.setMaxTotal(HttpParams.HTTP_MAX);
		// 设置请求参数
		RequestConfig.Builder config = RequestConfig.custom();
		config.setSocketTimeout(HttpParams.HTTP_TIMEOUT);
		config.setConnectTimeout(HttpParams.HTTP_TIMEOUT);
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
		builder.setDefaultHeaders(headers);
		// // 设置连接配置
		// 实例化客户端
		return builder.build();
	}

	private HttpClient() {
	}
}
