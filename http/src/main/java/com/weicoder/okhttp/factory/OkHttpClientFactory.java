package com.weicoder.okhttp.factory;

import java.util.concurrent.TimeUnit;

import com.weicoder.common.factory.Factory;
import com.weicoder.common.params.P;
import com.weicoder.okhttp.interceptor.NetworkInterceptor;

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
		return new OkHttpClient.Builder().connectTimeout(P.H.TIMEOUT, TimeUnit.SECONDS).readTimeout(P.H.TIMEOUT, TimeUnit.SECONDS)
				.writeTimeout(P.H.TIMEOUT, TimeUnit.SECONDS)
				.connectionPool(new ConnectionPool(P.H.MAX, P.H.TIMEOUT, TimeUnit.SECONDS))
				.addNetworkInterceptor(new NetworkInterceptor())
//				.cache(new Cache(new File(O.BASE_DIR, "OkHttpCache"), P.C.IO_BUFFERSIZE))
				.build();
		//
	}

	private OkHttpClientFactory() {
	}
}
