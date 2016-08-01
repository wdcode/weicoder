package com.weicoder.core.http;

/**
 * HTTP 异步回调
 * @author WD
 */
public interface Callback<T> {
	/**
	 * http请求成功后返回
	 * @param result 返回结果
	 */
	void callback(T result);
}
