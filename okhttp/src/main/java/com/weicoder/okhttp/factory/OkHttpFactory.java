package com.weicoder.okhttp.factory;

import com.weicoder.common.factory.Factory;
import com.weicoder.okhttp.OkHttp;

/**
 * OkHttp工厂
 * 
 * @author wdcode
 *
 */
public class OkHttpFactory extends Factory<OkHttp> {
	private static final OkHttpFactory FACTORY = new OkHttpFactory();

	/**
	 * 获得OkHttp
	 * 
	 * @return OkHttp
	 */
	public OkHttp getOkHttp() {
		return FACTORY.getInstance();
	}

	@Override
	public OkHttp newInstance() {
		return new OkHttp(OkHttpClientFactory.getOkHttp());
	}

	private OkHttpFactory() {
	}
}
