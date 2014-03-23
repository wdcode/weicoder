package com.weicoder.core.nosql.memcache;

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
	 * 获得多个键的Map
	 * @param keys 键
	 * @return 值
	 */
	Map<String, Object> getMap(String... keys);
}