package com.weicoder.http.factory;

import com.weicoder.common.factory.FactoryKey;
import com.weicoder.http.retrofit2.CallAdapterFactory;
import com.weicoder.http.retrofit2.ConverterFactory;
import com.weicoder.okhttp.factory.OkHttpClientFactory;

import retrofit2.Retrofit;

/**
 * Retrofit工厂
 * 
 * @author wdcode
 *
 */
public final class RetrofitFactory extends FactoryKey<String, Retrofit> {
	private static final RetrofitFactory FACTORY = new RetrofitFactory();

	/**
	 * 获得Retrofit
	 * 
	 * @param url 基础url
	 * @return Retrofit
	 */
	public static Retrofit get(String url) {
		return FACTORY.getInstance(url);
	}

	@Override
	public Retrofit newInstance(String key) {
		return new Retrofit.Builder().baseUrl(key).client(OkHttpClientFactory.getOkHttp())
				.addConverterFactory(ConverterFactory.create())
				.addCallAdapterFactory(CallAdapterFactory.create())
				.build();
	}

	private RetrofitFactory() {
	}
}
