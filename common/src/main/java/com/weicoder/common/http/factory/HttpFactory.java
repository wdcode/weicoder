package com.weicoder.common.http.factory;

import com.weicoder.common.factory.FactoryInterface;
import com.weicoder.common.http.Http;
import com.weicoder.common.http.impl.Jdk8Http;

/**
 * http工厂
 * 
 * @author wudi
 */
public final class HttpFactory extends FactoryInterface<Http> {
	// 工厂
	private final static HttpFactory FACTORY = new HttpFactory();

	/**
	 * 获得http
	 * 
	 * @return
	 */
	public static Http getHttp() {
		return FACTORY.getInstance();
	}

	/**
	 * 获得http
	 * 
	 * @param  name
	 * @return
	 */
	public static Http getHttp(String name) {
		return FACTORY.getInstance(name);
	}

	private HttpFactory() {
	}

	@Override
	protected Class<? extends Http> def() { 
		return Jdk8Http.class;
	}
}
