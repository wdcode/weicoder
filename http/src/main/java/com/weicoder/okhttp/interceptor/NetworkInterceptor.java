package com.weicoder.okhttp.interceptor;

import java.io.IOException;

import com.weicoder.common.util.U.C;
import com.weicoder.okhttp.Headers;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * okhttp过滤器 为了给调用方添加Header
 * 
 * @author wdcode
 *
 */
public class NetworkInterceptor implements Interceptor {

	@Override
	public Response intercept(Chain chain) throws IOException {
		// 获得Request
		Request.Builder request = chain.request().newBuilder();
		// 添加Header 并处理成新Header
		C.list(Headers.class).forEach(c -> C.ioc(c).headers().forEach(request::addHeader));
		// 执行并返回Response
		return chain.proceed(request.build());
	}
}
