package com.weicoder.common.http.base;

import java.util.Map;

import com.weicoder.common.U.B;
import com.weicoder.common.U.S;
import com.weicoder.common.http.Http;
import com.weicoder.common.lang.Maps;

/**
 * 基础实现http类
 * 
 * @author wudi
 */
public abstract class BaseHttp implements Http {
	@Override
	public byte[] download(String url) {
		return download(url, Maps.emptyMap());
	}

	@Override
	public String get(String url) {
		return S.toString(download(url));
	}

	@Override
	public String get(String url, Map<String, Object> header) {
		return S.toString(download(url, header));
	}

	@Override
	public String post(String url, Object data) {
		return post(url, B.copy(data, Maps.newMap()));
	}

	@Override
	public String post(String url, Map<String, Object> data) {
		return post(url, data, Maps.emptyMap());
	}
}
