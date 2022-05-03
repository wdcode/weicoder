package com.weicoder.json.fastjson2;

import java.util.List;

import com.alibaba.fastjson2.JSON;
import com.weicoder.json.Json;

/**
 * fastjson2的JSON实现
 * @author wdcode
 *
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
	public <E> List<E> toList(String json, Class<E> clazz) {
		return JSON.parseArray(json, clazz);
	}

}
