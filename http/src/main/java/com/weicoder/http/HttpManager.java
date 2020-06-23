package com.weicoder.http;

import com.weicoder.common.factory.FactoryKey;
import com.weicoder.common.http.params.HttpParams;
import com.weicoder.http.factory.RetrofitFactory;

import retrofit2.Retrofit;

/**
 * http接口代理管理类
 * 
 * @author wdcode
 *
 */
public final class HttpManager extends FactoryKey<Class<?>, Object> {
	private static final HttpManager MANAGER = new HttpManager();
	private Retrofit retrofit;

	/**
	 * 创建http接口代理类
	 * 
	 * @param cls
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <E> E create(Class<E> cls) {
		return (E) MANAGER.getInstance(cls);
	}

	@Override
	public Object newInstance(Class<?> key) {
		return retrofit.create(key);
	}

	private HttpManager() {
		retrofit = RetrofitFactory.get(HttpParams.BASE_URL);
	}
}
