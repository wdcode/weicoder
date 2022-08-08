package com.weicoder.okhttp;

import java.io.IOException;
import java.util.Map;

import com.weicoder.common.C;
import com.weicoder.common.W;
import com.weicoder.common.http.Http;
import com.weicoder.common.log.Logs;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * okhttp实现
 * 
 * @author wdcode
 *
 */
public class OkHttp implements Http {
	private OkHttpClient client;

	public OkHttp(OkHttpClient client) {
		super();
		this.client = client;
	}

	@Override
	public byte[] download(String url, Map<String, Object> header) {
		Request.Builder builder = new Request.Builder().url(url);
		header.forEach((k, v) -> builder.addHeader(k, W.C.toString(v)));
		try (Response res = client.newCall(builder.build()).execute()) {
			return res.body().bytes();
		} catch (IOException e) {
			Logs.error(e);
		}
		return C.A.BYTES_EMPTY;
	}

	@Override
	public String post(String url, Map<String, Object> data, Map<String, Object> header) {
		Request.Builder builder = new Request.Builder().url(url);
		FormBody.Builder body = new FormBody.Builder();
		data.forEach((k, v) -> body.add(k, W.C.toString(v)));
		header.forEach((k, v) -> builder.addHeader(k, W.C.toString(v)));
		builder.post(body.build());
		try (Response res = client.newCall(builder.build()).execute()) {
			return res.body().string();
		} catch (IOException e) {
			Logs.error(e);
		}
		return C.S.EMPTY;
	}
}
