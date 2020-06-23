package com.weicoder.http.retrofit2;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import com.weicoder.common.U; 
import com.weicoder.common.W.C; 
import com.weicoder.common.bean.Result;
import com.weicoder.json.J; 

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * retrofit2 转换工厂
 * 
 * @author wdcode
 *
 */
public class ConverterFactory extends Converter.Factory {
	/**
	 * 创建ResponseConverterFactory
	 * 
	 * @return ResponseConverterFactory
	 */
	public static ConverterFactory create() {
		return new ConverterFactory();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
		return value -> {
			Result<Object> res = J.toBean(value.string(), Result.class);
			return res.setContent(J.toBean(C.toString(res.getContent()), U.C.getGenericClass(type, 0)));
		};
	}

	private ConverterFactory() {
	}
}
