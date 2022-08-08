package com.weicoder.json.fastjson;

import java.util.List;

import com.weicoder.json.Json;

import com.alibaba.fastjson2.JSON;

/**
 * fastjson的JSON实现
 * 
 * @author WD
 * @version 1.0
 */
public final class FastJson2 implements Json {
	@Override
	public String toJson(Object obj) {
		return JSON.toJSONString(obj);
	}

	@Override
	public <E> E toBean(String json, Class<E> clazz) {
		return JSON.parseObject(json, clazz);
	}

	@Override
	public String getJson(String json, String key) {
		return JSON.parseObject(json).getString(key);
	}

	@Override
	public <E> List<E> toList(String json, Class<E> clazz) {
		return JSON.parseArray(json, clazz);
	}
}
