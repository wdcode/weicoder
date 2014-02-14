package com.weicoder.core.json.impl;

import java.util.List;

import net.minidev.json.JSONValue;

import com.weicoder.common.util.BeanUtil;
import com.weicoder.core.json.Json;

/**
 * json-smart包实现
 * @author WD
 * @since JDK6
 * @version 1.0 2013-03-11
 */
public final class JsonSmart implements Json {
	@Override
	public String toJson(Object obj) {
		return JSONValue.toJSONString(obj);
	}

	@Override
	public <E> E toBean(String json, Class<E> clazz) {
		return (E) BeanUtil.copyProperties(JSONValue.parse(json), BeanUtil.newInstance(clazz));
	}

	@Override
	public <E> List<E> toList(String json, Class<E> clazz) {
		return (List<E>) JSONValue.parse(json);
	}
}
