package com.weicoder.http;

import java.io.File;
import java.util.List;
import java.util.Map;
 
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.entity.mime.HttpMultipartMode;
import org.apache.hc.client5.http.entity.mime.MultipartEntityBuilder;  
import org.apache.hc.core5.http.NameValuePair; 
import org.apache.hc.core5.http.message.BasicHeader;
import org.apache.hc.core5.http.message.BasicNameValuePair;

import com.weicoder.common.constants.HttpConstants;
import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.io.IOUtil;
import com.weicoder.common.U;
import com.weicoder.common.W;
import com.weicoder.common.lang.Lists;
import com.weicoder.common.log.Log;
import com.weicoder.common.log.LogFactory;

/**
 * http 上传文件
 * 
 * @author WD
 */
public class HttpUpload {
	// 日志
	private final static Log LOG = LogFactory.getLog(HttpUpload.class);

	/**
	 * 上传文件
	 * 
	 * @param  url   post提交地址
	 * @param  files 上传文件
	 * @return       返回结果
	 */
	public static String upload(String url, File... files) {
		return upload(url, null, files);
	}

	/**
	 * 上传文件
	 * 
	 * @param  url   post提交地址
	 * @param  data  提交参数
	 * @param  files 上传文件
	 * @return       返回结果
	 */
	public static String upload(String url, Map<String, Object> data, File... files) {
		// 如果文件为空
		if (U.E.isEmpty(url) || U.E.isEmpty(files))
			return StringConstants.EMPTY;
		// 声明HttpPost
		HttpPost post = null;
		try {
			// 获得HttpPost
			post = new HttpPost(url);
//			post.addHeader(new BasicHeader(HttpConstants.CONTENT_TYPE_KEY, HttpConstants.CONTENT_TYPE_UPLOAD));
			// 参数
			if (U.E.isNotEmpty(data)) {
				// 声明参数列表
				List<NameValuePair> list = Lists.newList(data.size());
				// 设置参数
				data.forEach((k, v) -> list.add(new BasicNameValuePair(k, W.C.toString(v))));
				// 设置参数与 编码格式
				post.setEntity(new UrlEncodedFormEntity(list));
			}
			// 多提交实体构造器
			MultipartEntityBuilder builder = MultipartEntityBuilder.create();
			// 设置浏览器上传
			builder.setMode(HttpMultipartMode.EXTENDED);
			// 添加上传文件
			for (File file : files)
				builder.addBinaryBody(file.getName(), file);
			// 设置提交文件参数
			post.setEntity(builder.build());
			// 返回结果
			return IOUtil.readString(HttpClient.CLIENT.execute(post).getEntity().getContent());
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
	 * 上传文件
	 * 
	 * @param  url  post提交地址
	 * @param  name 参数名
	 * @param  b    流
	 * @return      返回结果
	 */
	public static String upload(String url, String name, byte[] b) {
		return upload(url, null, name, b);
	}

	/**
	 * 上传文件
	 * 
	 * @param  url  post提交地址
	 * @param  data 提交参数
	 * @param  name 参数名
	 * @param  b    流
	 * @return      返回结果
	 */
	public static String upload(String url, Map<String, Object> data, String name, byte[] b) {
		// 如果文件为空
		if (U.E.isEmpty(url) || U.E.isEmpty(b))
			return StringConstants.EMPTY;
		// 声明HttpPost
		HttpPost post = null;
		try {
			// 获得HttpPost
			post = new HttpPost(url);
			post.addHeader(new BasicHeader(HttpConstants.CONTENT_TYPE_KEY, "multipart/form-data"));
			// 多提交实体构造器
			MultipartEntityBuilder builder = MultipartEntityBuilder.create();
			// 设置浏览器上传
			builder.setMode(HttpMultipartMode.EXTENDED);
			// 添加上传文件
			builder.addBinaryBody(name, b);
			// 参数
			if (U.E.isNotEmpty(data))
				// 设置参数
				data.forEach((k, v) -> builder.addTextBody(k, W.C.toString(v)));
			// 设置提交文件参数
			post.setEntity(builder.build());
			// 返回结果
			return IOUtil.readString(HttpClient.CLIENT.execute(post).getEntity().getContent());
		} catch (Exception e) {
			LOG.error(e);
		} finally {
			// 销毁post
			if (post != null)
				post.abort();
		}
		return StringConstants.EMPTY;
	}
}
