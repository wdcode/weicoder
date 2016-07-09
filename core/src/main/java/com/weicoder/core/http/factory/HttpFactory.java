package com.weicoder.core.http.factory;

import com.weicoder.common.params.CommonParams;
import com.weicoder.core.factory.Factory;
import com.weicoder.core.http.Http;
import com.weicoder.core.http.impl.HttpApache3;
import com.weicoder.core.http.impl.HttpApache4;
import com.weicoder.core.http.impl.HttpApache4Async;
import com.weicoder.core.http.impl.HttpJava;
import com.weicoder.core.params.HttpParams;

/**
 * 获得HttpClient实例工厂 
 * @author WD 
 * @version 1.0 
 */
public final class HttpFactory extends Factory<Http> {
	// 工厂
	private final static HttpFactory FACTORY = new HttpFactory();

	/**
	 * 返回工厂
	 * @return 工厂
	 */
	public static Http getHttp() {
		return FACTORY.getInstance();
	}

	/**
	 * 实例化一个新的HttpClient实例
	 * @return HttpClient
	 */
	public Http newInstance() {
		return newInstance(CommonParams.ENCODING);
	}

	/**
	 * 实例化一个新的HttpClient实例
	 * @param encoding 请求头的编码
	 * @return HttpClient
	 */
	public Http newInstance(String encoding) {
		switch (HttpParams.PARSE) {
			case "async":
				return new HttpApache4Async(encoding);
			case "apache3":
				return new HttpApache3(encoding);
			case "java":
				return new HttpJava(encoding);
			default:
				return new HttpApache4(encoding);
		}
	}

	private HttpFactory() {}
}
