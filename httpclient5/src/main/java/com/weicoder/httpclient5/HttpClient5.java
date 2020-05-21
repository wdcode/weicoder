package com.weicoder.httpclient5;

import java.util.List;
import java.util.Map;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.message.BasicHeader;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.apache.hc.core5.util.Timeout;

import com.weicoder.common.C;
import com.weicoder.common.U;
import com.weicoder.common.W;  
import com.weicoder.common.http.base.BaseHttp;
import com.weicoder.common.io.IOUtil;
import com.weicoder.common.lang.Lists;
import com.weicoder.common.log.Logs;
import com.weicoder.httpclient5.params.HttpParams;

/**
 * HttpClient5的实现
 * 
 * @author wdcode
 *
 */
public class HttpClient5 extends BaseHttp {
	// Http客户端
	final static CloseableHttpClient CLIENT = init();

	@Override
	public byte[] download(String url, Map<String, Object> header) {
		// 声明HttpGet对象
		HttpGet get = null;
		try {
			Logs.debug("HttpClient5 get url={}", url);
			// 获得HttpGet对象
			get = new HttpGet(url);
			// 获得HttpResponse 返回字节流
			return IOUtil.read(CLIENT.execute(get).getEntity().getContent());
		} catch (Exception e) {
			Logs.error(e);
		} finally {
			// 销毁get
			if (get != null) {
				get.abort();
			}
		}
		return C.A.BYTES_EMPTY;
	}

	@Override
	public String post(String url, Map<String, Object> data, Map<String, Object> header) {
		try {
			// 获得HttpPost
			HttpPost post = new HttpPost(url);
			// 如果参数列表为空 data为空map
			if (U.E.isNotEmpty(data)) {
				// 声明参数列表
				List<NameValuePair> list = Lists.newList(data.size());
				// 设置参数
				data.forEach((k, v) -> list.add(new BasicNameValuePair(k, W.C.toString(v))));
				// 设置参数与 编码格式
				post.setEntity(new UrlEncodedFormEntity(list));
			}
			// 添加http头
			if (U.E.isNotEmpty(header))
				header.forEach((k, v) -> post.addHeader(k, v));
			Logs.debug("HttpClient post url={} data={} header={}", url, data, header);
			// 返回结果
			return U.I.readString(CLIENT.execute(post).getEntity().getContent());
		} catch (Exception e) {
			Logs.error(e);
		}
		return C.S.EMPTY;
	}

	/**
	 * 初始化httpclient
	 * 
	 * @return CloseableHttpClient
	 */
	private static CloseableHttpClient init() {
		// Http连接池
		PoolingHttpClientConnectionManager pool = new PoolingHttpClientConnectionManager();
		pool.setDefaultMaxPerRoute(C.O.CPU_NUM);
		pool.setMaxTotal(HttpParams.HTTP_MAX);
		// 设置请求参数
		RequestConfig.Builder config = RequestConfig.custom();
		config.setConnectionRequestTimeout(Timeout.ofSeconds(W.C.toLong(HttpParams.HTTP_TIMEOUT)));
		config.setConnectTimeout(Timeout.ofSeconds(W.C.toLong(HttpParams.HTTP_TIMEOUT)));
		config.setCircularRedirectsAllowed(false);
		// HttpClientBuilder
		HttpClientBuilder builder = HttpClientBuilder.create();
		builder.setDefaultRequestConfig(config.build());
		builder.setConnectionManager(pool);
//		builder.setMaxConnPerRoute(SystemConstants.CPU_NUM);
		// 设置 头
		List<BasicHeader> headers = Lists.newList();
		headers.add(new BasicHeader(C.H.USER_AGENT_KEY, C.H.USER_AGENT_VAL));
		headers.add(new BasicHeader(C.H.ACCEPT_KEY, C.H.ACCEPT_VAL));
		builder.setDefaultHeaders(headers);
		// 实例化客户端
		return builder.build();
	}
}
