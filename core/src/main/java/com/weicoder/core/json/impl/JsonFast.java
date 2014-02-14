package com.weicoder.core.json.impl;

import java.util.List;

import com.weicoder.core.json.Json;

import com.alibaba.fastjson.JSON;

/**
 * fastjson的JSON实现
 * @author WD
 * @since JDK7
 * @version 1.0 2012-09-15
 */
public final class JsonFast implements Json {
	@Override
	public String toJson(Object obj) {
		return JSON.toJSONString(obj);
	}

	@Override
	public <E> E toBean(String json, Class<E> clazz) {
		return JSON.parseObject(json, clazz);
	}

	@Override
	public <E> List<E> toList(String json, Class<E> clazz) {
		return JSON.parseArray(json, clazz);
	}
}
