package com.weicoder.http;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.nio.reactor.IOReactorException;

import com.weicoder.common.constants.HttpConstants;
import com.weicoder.common.constants.SystemConstants; 
import com.weicoder.common.interfaces.CallbackVoid;
import com.weicoder.common.io.IOUtil;
import com.weicoder.common.lang.C;
import com.weicoder.common.lang.Lists;
import com.weicoder.common.log.Log;
import com.weicoder.common.log.LogFactory; 
import com.weicoder.common.params.CommonParams;
import com.weicoder.common.util.CloseUtil;
import com.weicoder.common.util.EmptyUtil;
import com.weicoder.common.util.StringUtil;

/**
 * HTTP异步客户端
 * 
 * @author WD
 */
public final class HttpAsyncClient {
	// 日志
	private final static Log LOG = LogFactory.getLog(HttpAsyncClient.class);
	// Http客户端
	final static CloseableHttpAsyncClient CLIENT;

	static {
		// Http连接池
		PoolingNHttpClientConnectionManager pool = null;
		try {
			pool = new PoolingNHttpClientConnectionManager(new DefaultConnectingIOReactor());
		} catch (IOReactorException e) {
			LOG.error(e);
		}
		pool.setDefaultMaxPerRoute(SystemConstants.CPU_NUM * 10);
		pool.setMaxTotal(SystemConstants.CPU_NUM * 10);
		// 设置请求参数
		RequestConfig.Builder config = RequestConfig.custom();
		config.setSocketTimeout(2000);
		config.setConnectTimeout(2000);
		config.setCircularRedirectsAllowed(false);
		// HttpClientBuilder
		HttpAsyncClientBuilder builder = HttpAsyncClientBuilder.create();
		builder.setDefaultRequestConfig(config.build());
		builder.setConnectionManager(pool);
		builder.setMaxConnPerRoute(SystemConstants.CPU_NUM * 10);
		// 设置 头
		List<BasicHeader> headers = Lists.newList();
		headers.add(new BasicHeader(HttpConstants.USER_AGENT_KEY, HttpConstants.USER_AGENT_VAL));
		headers.add(new BasicHeader(HttpConstants.ACCEPT_KEY, HttpConstants.ACCEPT_VAL));
		headers.add(new BasicHeader(HttpConstants.ACCEPT_LANGUAGE_KEY, HttpConstants.ACCEPT_LANGUAGE_VAL));
		headers.add(new BasicHeader(HttpConstants.ACCEPT_CHARSET_KEY, HttpConstants.ACCEPT_CHARSET_VAL));
		builder.setDefaultHeaders(headers);
		// 设置连接配置
		builder.setDefaultConnectionConfig(ConnectionConfig.custom().setCharset(Charset.forName(CommonParams.ENCODING)).build());
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
	public static void get(String url, CallbackVoid<String> callback) {
		get(url, callback, CommonParams.ENCODING);
	}

	/**
	 * 模拟get提交
	 * 
	 * @param url      get提交地址
	 * @param callback 回调结果
	 * @param charset  编码
	 */
	public static void get(String url, CallbackVoid<String> callback, String charset) {
		download(url, (byte[] result) -> {
			if (callback != null) {
				callback.callback(StringUtil.toString(result, charset));
			} 
		});
	}

	/**
	 * 下载文件
	 * 
	 * @param url      get提交地址
	 * @param callback 回调结果
	 */
	public static void download(String url, final CallbackVoid<byte[]> callback) {
		// 声明HttpGet对象
		HttpGet get = null;
		try {
			// 获得HttpGet对象
			get = new HttpGet(url);
			get.addHeader(new BasicHeader(HttpConstants.CONTENT_TYPE_KEY, HttpConstants.CONTENT_TYPE_VAL));
			// 执行get
			CLIENT.execute(get, new FutureCallback<HttpResponse>() {
				@Override
				public void failed(Exception ex) {
					LOG.error(ex);
				}

				@Override
				public void completed(HttpResponse result) {
					if (callback != null) {
						try (InputStream in = result.getEntity().getContent()) {
							callback.callback(IOUtil.read(in));
						} catch (Exception e) {
							LOG.error(e);
						}
					}
				}

				@Override
				public void cancelled() {
				}
			});
		} catch (Exception e) {
			LOG.error(e);
		} finally {
//			// 销毁get
//			if (get != null) {
//				get.abort();
//			}
		}
	}

	/**
	 * 模拟post提交
	 * 
	 * @param url      post提交地址
	 * @param data     提交参数
	 * @param callback 回调结果
	 */
	public static void post(String url, Map<String, Object> data, CallbackVoid<String> callback) {
		post(url, data, callback, CommonParams.ENCODING);
	}

	/**
	 * 模拟post提交
	 * 
	 * @param url      post提交地址
	 * @param data     提交参数
	 * @param callback 回调结果
	 * @param charset  编码
	 */
	public static void post(String url, Map<String, Object> data, CallbackVoid<String> callback, String charset) {
		// 声明HttpPost
		HttpPost post = null;
		try {
			// 获得HttpPost
			post = new HttpPost(url);
			post.addHeader(new BasicHeader(HttpConstants.CONTENT_TYPE_KEY, HttpConstants.CONTENT_TYPE_VAL));
			// 如果参数列表为空 data为空map
			if (EmptyUtil.isNotEmpty(data)) {
				// 声明参数列表
				List<NameValuePair> list = Lists.newList(data.size());
				// 设置参数
				data.forEach((k, v) -> list.add(new BasicNameValuePair(k,C.toString(v))));
				// 设置参数与 编码格式
				post.setEntity(new UrlEncodedFormEntity(list, charset));
			}
			// 执行POST
			CLIENT.execute(post, new FutureCallback<HttpResponse>() {
				@Override
				public void failed(Exception ex) {
					LOG.error(ex);
				}

				@Override
				public void completed(HttpResponse result) {
					if (callback != null) {
						try (InputStream in = result.getEntity().getContent()) {
							callback.callback(IOUtil.readString(in));
						} catch (Exception e) {
							LOG.error(e);
						}
					}
				}

				@Override
				public void cancelled() {
				}
			});
		} catch (Exception e) {
			LOG.error(e);
		} finally {
//			// 销毁post
//			if (post != null) {
//				post.abort();
//			}
		}
	}
	
	public static void close() {
		CloseUtil.close(CLIENT);
	}

	private HttpAsyncClient() {
	}
}
