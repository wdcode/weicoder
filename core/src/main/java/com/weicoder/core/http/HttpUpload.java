package com.weicoder.core.http;

import java.io.File;
import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.message.BasicHeader;

import com.weicoder.common.constants.HttpConstants;
import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.io.IOUtil;
import com.weicoder.common.log.Logs;
import com.weicoder.common.util.EmptyUtil;
import com.weicoder.core.http.HttpAsyncClient.Callback;

/**
 * http 上传文件
 * @author WD
 */
public class HttpUpload {
	/**
	 * 上传文件
	 * @param url post提交地址
	 * @param files 上传文件
	 * @return 返回结果
	 */
	public static String upload(String url, File... files) {
		// 如果文件为空
		if (EmptyUtil.isEmpty(url) || EmptyUtil.isEmpty(files)) {
			return StringConstants.EMPTY;
		}
		// 声明HttpPost
		HttpPost post = null;
		try {
			// 获得HttpPost
			post = new HttpPost(url);
			post.addHeader(new BasicHeader(HttpConstants.CONTENT_TYPE_KEY,
					HttpConstants.CONTENT_TYPE_VAL));
			// post.addHeader(new BasicHeader(HttpConstants.CONTENT_TYPE_KEY, HttpConstants.CONTENT_TYPE_FILE + "; boundary=---123"));
			// 多提交实体构造器
			MultipartEntityBuilder builder = MultipartEntityBuilder.create();
			// 设置浏览器上传
			builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
			// 添加上传文件
			for (File file : files) {
				builder.addBinaryBody(file.getName(), file);
				// builder.addPart(file.getName(), new FileBody(file));
				// builder.addBinaryBody("file", file, ContentType.MULTIPART_FORM_DATA,file.getName());
			}
			// 设置提交文件参数
			post.setEntity(builder.build());
			// 获得HttpResponse参数
			HttpResponse response = HttpClient.CLIENT.execute(post);
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

	/**
	 * 上传文件
	 * @param url post提交地址
	 * @param callback 回调结果
	 * @param files 上传文件
	 */
	public static void upload(String url, Callback<String> callback, File... files) {
		// 声明HttpPost
		HttpPost post = null;
		try {
			// 获得HttpPost
			post = new HttpPost(url);
			// post.addHeader(new BasicHeader(HttpConstants.CONTENT_TYPE_KEY, HttpConstants.CONTENT_TYPE_FILE + "; boundary=---123"));
			// 多提交实体构造器
			MultipartEntityBuilder builder = MultipartEntityBuilder.create();
			// 设置浏览器上传
			builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
			// 添加上传文件
			for (File file : files) {
				builder.addBinaryBody(file.getName(), file);
				// builder.addPart(file.getName(), new FileBody(file));
				// builder.addBinaryBody("file", file, ContentType.MULTIPART_FORM_DATA,file.getName());
			}
			// 设置提交文件参数
			post.setEntity(builder.build());
			// 执行post
			HttpAsyncClient.CLIENT.execute(post, new FutureCallback<HttpResponse>() {
				@Override
				public void failed(Exception ex) {
					Logs.error(ex);
				}

				@Override
				public void completed(HttpResponse result) {
					if (callback != null) {
						try (InputStream in = result.getEntity().getContent()) {
							callback.callback(IOUtil.readString(in));
						} catch (Exception e) {
							Logs.error(e);
						}
					}
				}

				@Override
				public void cancelled() {}
			});
		} catch (Exception e) {
			Logs.error(e);
		} finally {
			// 销毁post
			if (post != null) {
				post.abort();
			}
		}
	}
}
