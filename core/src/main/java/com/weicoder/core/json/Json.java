package com.weicoder.core.json;

import java.util.List;

/**
 * Json格式读写器 接口
 * @author WD
 * @since JDK7
 * @version 1.0 2012-09-15
 */
public interface Json {
	/**
	 * 把一个对象转换成JSON
	 * @param obj 要转换的对象
	 * @return 转换后的字符串
	 */
	String toJson(Object obj);

	/**
	 * 转换JSON根据传入的Class反射生成回实体Bean
	 * @param json JSON字符串
	 * @param clazz 要转换对象的class
	 * @return 对象
	 */
	<E> E toBean(String json, Class<E> clazz);

	/**
	 * 把json转换成List
	 * @param json JSON字符串
	 * @return List
	 */
	<E> List<E> toList(String json, Class<E> clazz);
}
