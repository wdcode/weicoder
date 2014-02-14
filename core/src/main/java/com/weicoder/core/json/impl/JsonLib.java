package com.weicoder.core.json.impl;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import com.weicoder.common.lang.Lists;
import com.weicoder.core.json.Json;

/**
 * Json格式读写器 json-lib 包实现
 * @author WD
 * @since JDK7
 * @version 1.0 2012-09-15
 */
public final class JsonLib implements Json {
	@Override
	public String toJson(Object obj) {
		return JSONUtils.isArray(obj) ? JSONArray.fromObject(obj).toString() : JSONObject.fromObject(obj).toString();
	}

	@Override
	public <E> E toBean(String json, Class<E> clazz) {
		return (E) JSONObject.toBean(JSONObject.fromObject(json), clazz);
	}

	@Override
	public <E> List<E> toList(String json, Class<E> clazz) {
		return Lists.getList(JSONArray.toCollection(JSONArray.fromObject(json), clazz));
	}
}
