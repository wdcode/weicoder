package com.weicoder.httpclient;

import java.util.List;
import java.util.Map;

import org.apache.hc.client5.http.async.methods.SimpleHttpResponse;
import org.apache.hc.client5.http.async.methods.SimpleRequestBuilder;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.async.CloseableHttpAsyncClient;
import org.apache.hc.client5.http.impl.async.HttpAsyncClientBuilder;
import org.apache.hc.client5.http.impl.nio.PoolingAsyncClientConnectionManager;
import org.apache.hc.core5.concurrent.FutureCallback;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.message.BasicHeader;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.apache.hc.core5.util.Timeout;

import com.weicoder.common.constants.C; 
import com.weicoder.common.interfaces.Calls;
import com.weicoder.common.lang.W;
import com.weicoder.common.log.Log;
import com.weicoder.common.log.LogFactory;
import com.weicoder.common.params.P;
import com.weicoder.common.statics.S;
import com.weicoder.common.util.U;

/**
 * HTTP异步客户端
 * 
 * @author WD
 */
public final class HttpAsyncClient {
	// 日志
	private final static Log				LOG	= LogFactory.getLog(HttpAsyncClient.class);
	// Http客户端
	final static CloseableHttpAsyncClient	CLIENT;

	static {
		// Http连接池
		PoolingAsyncClientConnectionManager pool = new PoolingAsyncClientConnectionManager();
		pool.setDefaultMaxPerRoute(C.O.CPU_NUM);
		pool.setMaxTotal(P.H.MAX);
		// 设置请求参数
		RequestConfig.Builder config = RequestConfig.custom();
		config.setConnectionRequestTimeout(Timeout.ofSeconds(W.C.toLong(P.H.TIMEOUT))); 
//		config.setConnectTimeout(Timeout.ofSeconds(W.C.toLong(P.H.TIMEOUT)));
		config.setResponseTimeout(Timeout.ofSeconds(W.C.toLong(P.H.TIMEOUT)));		
		config.setCircularRedirectsAllowed(false);
		// HttpClientBuilder
		HttpAsyncClientBuilder builder = HttpAsyncClientBuilder.create();
		builder.setDefaultRequestConfig(config.build());
		builder.setConnectionManager(pool);
//		builder.setMaxConnPerRoute(C.O.CPU_NUM * 10);
		// 设置 头
		List<BasicHeader> headers = W.L.list();
		headers.add(new BasicHeader(C.H.USER_AGENT_KEY, C.H.USER_AGENT_VAL));
		headers.add(new BasicHeader(C.H.ACCEPT_KEY, C.H.ACCEPT_VAL));
		headers.add(new BasicHeader(C.H.ACCEPT_LANGUAGE_KEY, C.H.ACCEPT_LANGUAGE_VAL));
		headers.add(new BasicHeader(C.H.ACCEPT_CHARSET_KEY, C.H.ACCEPT_CHARSET_VAL));
		builder.setDefaultHeaders(headers);
		// 实例化客户端
		CLIENT = builder.build();
		// 启动
		CLIENT.start();
	}

	/**
	 * 模拟get提交
	 * 
	 * @param url      get提交地址
	 * @param callback 回调结果
	 */
	public static void get(String url, Calls.EoV<String> callback) {
		get(url, callback, P.C.ENCODING);
	}

	/**
	 * 模拟get提交
	 * 
	 * @param url      get提交地址
	 * @param callback 回调结果
	 * @param charset  编码
	 */
	public static void get(String url, Calls.EoV<String> callback, String charset) {
		download(url, (byte[] result) -> {
			if (callback != null) {
				callback.call(U.S.toString(result, charset));
			}
		});
	}

	/**
	 * 下载文件
	 * 
	 * @param url      get提交地址
	 * @param callback 回调结果
	 */
	public static void download(String url, final Calls.EoV<byte[]> callback) {
		// 声明HttpGet对象
		HttpGet get = null;
		try {
			// 获得HttpGet对象
			get = new HttpGet(url);
			get.addHeader(new BasicHeader(C.H.CONTENT_TYPE_KEY, C.H.CONTENT_TYPE_VAL));
			// 执行get
			CLIENT.execute(SimpleRequestBuilder.copy(get).build(), new FutureCallback<SimpleHttpResponse>() {
				@Override
				public void failed(Exception ex) {
					LOG.error(ex);
				}

				@Override
				public void completed(SimpleHttpResponse result) {
					if (callback != null)
						callback.call(result.getBodyBytes());
				}

				@Override
				public void cancelled() {
				}
			});
		} catch (Exception e) {
			LOG.error(e);
		}
	}

	/**
	 * 模拟post提交
	 * 
	 * @param url      post提交地址
	 * @param data     提交参数
	 * @param callback 回调结果
	 */
	public static void post(String url, Map<String, Object> data, Calls.EoV<String> callback) {
		post(url, data, callback, P.C.ENCODING);
	}

	/**
	 * 模拟post提交
	 * 
	 * @param url      post提交地址
	 * @param data     提交参数
	 * @param callback 回调结果
	 * @param charset  编码
	 */
	public static void post(String url, Map<String, Object> data, Calls.EoV<String> callback, String charset) {
		// 声明HttpPost
		HttpPost post = null;
		try {
			// 获得HttpPost
			post = new HttpPost(url);
			post.addHeader(new BasicHeader(C.H.CONTENT_TYPE_KEY, C.H.CONTENT_TYPE_VAL));
			// 如果参数列表为空 data为空map
			if (U.E.isNotEmpty(data)) {
				// 声明参数列表
				List<NameValuePair> list = W.L.list(data.size());
				// 设置参数
				data.forEach((k, v) -> list.add(new BasicNameValuePair(k, W.C.toString(v))));
				// 设置参数与 编码格式
				post.setEntity(new UrlEncodedFormEntity(list));
			}
			// 执行POST
			CLIENT.execute(SimpleRequestBuilder.copy(post).build(), new FutureCallback<SimpleHttpResponse>() {
				@Override
				public void failed(Exception ex) {
					LOG.error(ex);
				}

				@Override
				public void completed(SimpleHttpResponse result) {
					if (callback != null)
						callback.call(result.getBodyText());
				}

				@Override
				public void cancelled() {
				}
			});
		} catch (Exception e) {
			LOG.error(e);
		}
	}

	public static void close() {
		S.C.close(CLIENT);
	}

	private HttpAsyncClient() {
	}
}
