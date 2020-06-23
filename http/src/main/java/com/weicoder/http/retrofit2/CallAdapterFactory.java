package com.weicoder.http.retrofit2;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Retrofit;

/**
 * CallAdapter工厂
 * 
 * @author wdcode
 *
 */
public class CallAdapterFactory extends CallAdapter.Factory {
	/**
	 * 创建CallAdapterFactory
	 * 
	 * @return CallAdapterFactory
	 */
	public static CallAdapterFactory create() {
		return new CallAdapterFactory();
	}

	@Override
	public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
		return new CallAdapter<Object, Object>() {
			@Override
			public Type responseType() {
				return returnType;
			}

			@Override
			public Object adapt(Call<Object> call) {
				try {
					return call.execute().body();
				} catch (IOException e) {
					return null;
				}
			}
		};
	}

	private CallAdapterFactory() {
	}
}
