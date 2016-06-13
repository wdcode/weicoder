package com.weicoder.core.http;

import java.io.File;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;

import com.weicoder.common.constants.ArrayConstants;
import com.weicoder.common.constants.EncodingConstants;
import com.weicoder.common.constants.HttpConstants;
import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.io.IOUtil;
import com.weicoder.common.lang.Conversion;
import com.weicoder.common.lang.Lists;
import com.weicoder.common.log.Logs;
import com.weicoder.common.util.EmptyUtil;
import com.weicoder.core.params.HttpParams;

/**
 * HTTP客户端工具类
 * @author WD
 */
public final class HttpClient {
	// Http客户端
	private CloseableHttpClient client;

	/**
	 * 构造方法
	 * @param encoding 编码
	 */
	private HttpClient() {
		// Http连接池
		PoolingHttpClientConnectionManager pool = new PoolingHttpClientConnectionManager();
		pool.setDefaultMaxPerRoute(HttpParams.POOL);
		pool.setMaxTotal(HttpParams.POOL);
		// 设置请求参数
		RequestConfig.Builder config = RequestConfig.custom();
		config.setSocketTimeout(HttpParams.TIMEOUT);
		config.setConnectTimeout(HttpParams.TIMEOUT);
		config.setCircularRedirectsAllowed(false);
		config.setCookieSpec(CookieSpecs.DEFAULT);
		// HttpClientBuilder
		HttpClientBuilder builder = HttpClientBuilder.create();
		builder.setDefaultRequestConfig(config.build());
		builder.setConnectionManager(pool);
		//设置 头
		List<BasicHeader> headers = Lists.getList();
		headers.add(new BasicHeader(HttpConstants.USER_AGENT_KEY, HttpConstants.USER_AGENT_VAL));
		headers.add(new BasicHeader(HttpConstants.ACCEPT_KEY, HttpConstants.ACCEPT_VAL));
		headers.add(new BasicHeader(HttpConstants.ACCEPT_LANGUAGE_KEY, HttpConstants.ACCEPT_LANGUAGE_VAL));
		headers.add(new BasicHeader(HttpConstants.ACCEPT_CHARSET_KEY, HttpConstants.ACCEPT_CHARSET_VAL));
		headers.add(new BasicHeader(HttpConstants.CONTENT_TYPE_KEY, HttpConstants.CONTENT_TYPE_VAL));
		builder.setDefaultHeaders(headers);
		//设置连接配置
		builder.setDefaultConnectionConfig(ConnectionConfig.custom().setCharset(Charset.forName(EncodingConstants.UTF_8)).build());
		// 实例化客户端
		client = builder.build();
	}

	/**
	 * 模拟get提交
	 * @param url get提交地址
	 * @return InputStream 提交后的流
	 */
	public byte[] download(String url) {
		// 声明HttpGet对象
		HttpGet get = null;
		try {
			// 获得HttpGet对象
			get = new HttpGet(url);
			// 获得HttpResponse
			HttpResponse response = client.execute(get);
			//返回字节流
			return IOUtil.read(response.getEntity().getContent());
		} catch (Exception e) {
			Logs.error(e);
			return ArrayConstants.BYTES_EMPTY;
		} finally {
			// 销毁get
			if (get != null) {
				get.abort();
			}
		}
	}

	/**
	 * 上传文件
	 * @param url post提交地址
	 * @param files 上传文件
	 */
	public String uploads(String url, File... files) {
		// 如果文件为空
		if (EmptyUtil.isEmpty(files)) {
			// 返回空
			return StringConstants.EMPTY;
		}
		// 声明HttpPost
		HttpPost post = null;
		try {
			// 获得HttpPost
			post = new HttpPost(url);
			// 多提交实体构造器
			MultipartEntityBuilder builder = MultipartEntityBuilder.create();
			// 设置浏览器上传
			builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
			// 添加上传文件
			for (File file : files) {
				builder.addPart(file.getName(), new FileBody(file));
			}
			// 设置提交文件参数
			post.setEntity(builder.build());
			// 获得HttpResponse参数
			HttpResponse response = client.execute(post);
			//返回结果
			return IOUtil.readString(response.getEntity().getContent());
		} catch (Exception e) {
			Logs.error(e);
			return StringConstants.EMPTY;
		} finally {
			// 销毁post
			if (post != null) {
				post.abort();
			}
		}
	}

	/**
	 * 上传文件
	 * @param url post提交地址
	 * @param file 上传文件
	 */
	public String upload(String url, File file) {
		// 如果文件为空
		if (file == null) {
			// 返回空
			return StringConstants.EMPTY;
		}
		// 声明HttpPost
		HttpPost post = null;
		try {
			// 获得HttpPost
			post = new HttpPost(url);
			// 多提交实体构造器
			MultipartEntityBuilder builder = MultipartEntityBuilder.create();
			// 设置浏览器上传
			builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
			// 添加上传文件
			builder.addPart(file.getName(), new FileBody(file));
			// 设置提交文件参数
			post.setEntity(builder.build());
			// 获得HttpResponse参数
			HttpResponse response = client.execute(post);
			//返回结果
			return IOUtil.readString(response.getEntity().getContent());
		} catch (Exception e) {
			Logs.error(e);
			return e.getMessage();
		} finally {
			// 销毁post
			if (post != null) {
				post.abort();
			}
		}
	}

	/**
	 * 模拟post提交
	 * @param url post提交地址
	 * @param data 提交参数
	 * @param encoding 提交参数的编码格式
	 * @param referer referer地址
	 * @return InputStream 提交后的流
	 */
	public String post(String url, Map<String, Object> data, String encoding, String referer) {
		// 声明HttpPost
		HttpPost post = null;
		try {
			// 获得HttpPost
			post = new HttpPost(url);
			// 如果参数列表为空 data为空map
			if (!EmptyUtil.isEmpty(data)) {
				// 声明参数列表
				List<NameValuePair> list = Lists.getList(data.size());
				// 设置参数
				for (Map.Entry<String, Object> entry : data.entrySet()) {
					// 添加参数
					list.add(new BasicNameValuePair(entry.getKey(), Conversion.toString(entry.getValue())));
				}
				// 设置参数与 编码格式
				post.setEntity(new UrlEncodedFormEntity(list, encoding));
			}
			// 获得HttpResponse参数
			HttpResponse response = client.execute(post);
			//返回结果
			return IOUtil.readString(response.getEntity().getContent(), encoding);
		} catch (Exception e) {
			Logs.error(e);
			return e.getMessage();
		} finally {
			// 销毁post
			if (post != null) {
				post.abort();
			}
		}
	}
}
