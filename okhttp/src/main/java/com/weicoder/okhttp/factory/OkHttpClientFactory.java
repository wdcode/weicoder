package com.weicoder.okhttp.factory;

import java.util.concurrent.TimeUnit;

import com.weicoder.common.factory.Factory;
import com.weicoder.common.http.params.HttpParams;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;

/**
 * OkHttp工厂
 * 
 * @author wdcode
 *
 */
public class OkHttpClientFactory extends Factory<OkHttpClient> {
	private static final OkHttpClientFactory FACTORY = new OkHttpClientFactory();

	/**
	 * 获得OkHttp
	 * 
	 * @return OkHttp
	 */
	public static OkHttpClient getOkHttp() {
		return FACTORY.getInstance();
	}

	@Override
	public OkHttpClient newInstance() {
		return new OkHttpClient.Builder().connectTimeout(HttpParams.HTTP_TIMEOUT, TimeUnit.SECONDS)
				.readTimeout(HttpParams.HTTP_TIMEOUT, TimeUnit.SECONDS)
				.writeTimeout(HttpParams.HTTP_TIMEOUT, TimeUnit.SECONDS)
				.connectionPool(new ConnectionPool(HttpParams.HTTP_MAX, HttpParams.HTTP_TIMEOUT, TimeUnit.SECONDS))
//				.cache(new Cache(new File(O.BASE_DIR, "OkHttpCache"), CommonParams.IO_BUFFERSIZE))
				.build();
		// .addNetworkInterceptor(new NetworkInterceptor())
	}

	private OkHttpClientFactory() {
	}
}
