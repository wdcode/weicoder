package com.weicoder.core.json.impl;

import java.util.List;

import net.minidev.json.JSONValue;

import com.weicoder.common.util.BeanUtil;
import com.weicoder.core.json.Json;

/**
 * json-smart包实现
 * @author WD 
 * @version 1.0 
 */
public final class JsonSmart implements Json {
	@Override
	public String toJson(Object obj) {
		return JSONValue.toJSONString(obj);
	}

	@Override
	public <E> E toBean(String json, Class<E> clazz) {
		return (E) BeanUtil.copy(JSONValue.parse(json), BeanUtil.newInstance(clazz));
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E> List<E> toList(String json, Class<E> clazz) {
		return (List<E>) JSONValue.parse(json);
	}
}
