package com.weicoder.core.json;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.weicoder.common.json.Json;

/**
 * fastjson的JSON实现
 * @author WD
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
