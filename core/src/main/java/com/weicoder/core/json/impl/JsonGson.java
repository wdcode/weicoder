package com.weicoder.core.json.impl;

import java.util.List;
import java.util.Map;

import com.weicoder.common.lang.Lists;
import com.weicoder.common.util.BeanUtil;
import com.weicoder.core.json.Json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 * Json格式读写器 gson 包实现
 * @author WD
 * @since JDK7
 * @version 1.0 2012-09-15
 */
public final class JsonGson implements Json {
	// Gson
	private final static Gson	GSON	= new GsonBuilder().create();

	@Override
	public String toJson(Object obj) {
		return GSON.toJson(obj);
	}

	@Override
	public <E> E toBean(String json, Class<E> clazz) {
		try {
			return GSON.fromJson(json, clazz);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public <E> List<E> toList(String json, Class<E> clazz) {
		try {
			// 转换成list
			List<Map<String, Object>> list = GSON.fromJson(json, new TypeToken<List<Map<String, Object>>>() {}.getType());
			// 返回列表
			return BeanUtil.copyProperties(clazz, list);
		} catch (Exception e) {
			return Lists.getList();
		}
	}
}
