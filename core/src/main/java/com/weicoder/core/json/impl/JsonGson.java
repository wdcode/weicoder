package com.weicoder.core.json.impl;

import java.util.List;

import com.weicoder.common.lang.Lists;
import com.weicoder.common.log.Logs;
import com.weicoder.common.util.BeanUtil;
import com.weicoder.common.util.EmptyUtil;
import com.weicoder.core.json.Json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 * Json格式读写器 gson 包实现
 * @author WD 
 * @version 1.0 
 */
public final class JsonGson implements Json {
	// Gson
	private final static Gson GSON = new GsonBuilder().create();

	@Override
	public String toJson(Object obj) {
		return GSON.toJson(obj);
	}

	@Override
	public <E> E toBean(String json, Class<E> clazz) {
		try {
			return GSON.fromJson(json, clazz);
		} catch (Exception e) {
			Logs.info(e);
			return null;
		}
	}

	@Override
	public <E> List<E> toList(String json, Class<E> clazz) {
		try {
			// 转换成list
			List<E> list = GSON.fromJson(json, new TypeToken<E>() {}.getType());
			// 如果返回列表类型与传入类型不同
			if (!EmptyUtil.isEmpty(list) && !list.get(0).getClass().equals(clazz)) {
				// 声明列表
				List<E> ls = Lists.getList(list.size());
				// 转换列表
				for (Object o : list) {
					ls.add(BeanUtil.copy(o, clazz));
				}
				// 返回列表
				return ls;
			}
			// 返回列表
			return list;
		} catch (Exception e) {
			return Lists.getList();
		}
	}
}
