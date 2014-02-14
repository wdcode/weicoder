package com.weicoder.core.nosql.memcache;

import java.util.List;
import java.util.Map;

import com.weicoder.common.interfaces.Clear;
import com.weicoder.common.interfaces.Close;
import com.weicoder.core.nosql.NoSQL;

/**
 * MemCached的客户端调用接口
 * @author WD
 * @since JDK7
 * @version 1.0 2010-08-29
 */
public interface Memcache extends NoSQL, Close, Clear {

	/**
	 * 追加键值
	 * @param key 键
	 * @param value 值
	 */
	boolean append(String key, Object value);

	/**
	 * 获得多个键的Map
	 * @param keys 键
	 * @return 值
	 */
	Map<String, Object> getMap(String... keys);

	/**
	 * 获得多个键的数组
	 * @param keys 键
	 * @return 值
	 */
	Object[] get(String... keys);

	/**
	 * 获得多个键的数组
	 * @param keys 键
	 * @return 值
	 */
	List<byte[]> extract(String... keys);
}