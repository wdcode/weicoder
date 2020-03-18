package com.weicoder.json;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.weicoder.common.constants.ArrayConstants;
import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.lang.Lists;
import com.weicoder.common.lang.Maps;
import com.weicoder.common.util.BeanUtil;
import com.weicoder.common.U;
import com.weicoder.common.W.L;

/**
 * JSON处理引擎
 * 
 * @author WD
 */
public final class JsonEngine {
	/**
	 * 是否是json串
	 * 
	 * @param  json json串
	 * @return      true false
	 */
	public static boolean isJson(String json) {
		// 字符串为空
		if (U.E.isEmpty(json))
			return false;
		// 是数组格式
		if (isObject(json))
			return true;
		// 是数组格式
		if (isArray(json))
			return true;
		// 返回false
		return false;
	}

	/**
	 * 是否是json普通对象格式
	 * 
	 * @param  json json串
	 * @return      true false
	 */
	public static boolean isObject(String json) {
		// 字符串为空
		if (U.E.isEmpty(json))
			return false;
		// {开头 }结尾
		if (json.startsWith("{") && json.endsWith("}"))
			return true;
		// 空json
		if (json.equals("{}"))
			return true;
		// 返回false
		return false;
	}

	/**
	 * 是否是json数组格式
	 * 
	 * @param  json json串
	 * @return      true false
	 */
	public static boolean isArray(String json) {
		// 字符串为空
		if (U.E.isEmpty(json))
			return false;
		// [开头 ]结尾
		if (json.startsWith("[{") && json.endsWith("}]"))
			return true;
		// 空json
		if (json.equals("[]"))
			return true;
		// 返回false
		return false;
	}

	/**
	 * 把一个对象转换成JSON
	 * 
	 * @param  obj 要转换的对象
	 * @return     转换后的字符串
	 */
	public static String toJson(Object obj) {
		return obj == null ? StringConstants.EMPTY : JSON.toJSONString(obj);
	}

	/**
	 * 把列表实体数据转换成列表json数据
	 * 
	 * @param  list 列表
	 * @return      转换后的数据
	 */
	public static List<String> toJsonByList(List<?> list) {
		return L.newList(toJsonByArray(list));
	}

	/**
	 * 把列表实体数据转换成数组json数据
	 * 
	 * @param  list 列表
	 * @return      转换后的数组数据
	 */
	public static String[] toJsonByArray(List<?> list) {
		if (U.E.isEmpty(list))
			return ArrayConstants.STRING_EMPTY;
		// 生成字节数组
		String[] array = new String[list.size()];
		for (int i = 0; i < list.size(); i++)
			array[i] = toJson(list.get(0));
		// 返回数组
		return array;
	}

	/**
	 * 把json变成 JSONArray
	 * 
	 * @param  json json字符串
	 * @return      JSONArray
	 */
	public static JSONArray toJSONArray(String json) {
		return JSON.parseArray(json);
	}

	/**
	 * 把json变成 JSONObject
	 * 
	 * @param  json json字符串
	 * @return      JSONObject
	 */
	public static JSONObject toJSONObject(String json) {
		return JSON.parseObject(json);
	}

	/**
	 * 转换JSON根据传入的Class反射生成回实体Bean
	 * 
	 * @param  json  JSON字符串
	 * @param  clazz 要转换对象的class
	 * @param  <E>   泛型
	 * @return       对象
	 */
	public static <E> E toBean(String json, Class<E> clazz) {
		return U.E.isEmpty(json) ? null : JSON.parseObject(json, clazz);
	}

	/**
	 * 把json转换成List
	 * 
	 * @param  json  JSON字符串
	 * @param  clazz 类
	 * @param  <E>   泛型
	 * @return       List
	 */
	public static <E> List<E> toList(String json, Class<E> clazz) {
		return U.E.isEmpty(json) ? Lists.emptyList() : JSON.parseArray(json, clazz);
	}

	/**
	 * 把json转换成Map
	 * 
	 * @param  json JSON字符串
	 * @return      Map
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> toMap(String json) {
		return toBean(json, Map.class);
	}

	/**
	 * 把json转换成Map
	 * 
	 * @param  json  JSON字符串
	 * @param  value Map值类
	 * @param  <E>   泛型
	 * @return       Map
	 */
	@SuppressWarnings("unchecked")
	public static <E> Map<String, E> toMap(String json, Class<E> value) {
		// 获得Map
		Map<String, Object> map = toBean(json, Map.class);
		// 如果map为空
		if (U.E.isEmpty(map))
			return Maps.newMap();
		// 声明返回map
		Map<String, E> data = Maps.newMap(map.size());
		// 循环生成类
		map.forEach((k, v) -> {
			// 声明值
			E val = null;
			// 是一个类型
			if (v.equals(value))
				val = (E) v;
			else
				val = BeanUtil.copy(v, value);
			// 添加到列表
			data.put(k, val);
		});
		// 返回数据
		return data;
	}

	/**
	 * 把json转换成List
	 * 
	 * @param  json JSON字符串
	 * @return      List
	 */
	public static List<Object> toList(String json) {
		return toList(json, Object.class);
	}

	private JsonEngine() {
	}
}
