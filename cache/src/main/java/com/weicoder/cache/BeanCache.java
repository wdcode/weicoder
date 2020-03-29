package com.weicoder.cache;

import java.lang.reflect.Field;
import java.util.List;

import com.weicoder.cache.annotation.Key;
import com.weicoder.common.U.B;
import com.weicoder.common.W.C;
import com.weicoder.common.interfaces.Callback;

/**
 * 实体类的缓存 需要标记一个@see {@link Key} 用户缓存key
 * 
 * @author     wudi
 * @param  <K> 键
 * @param  <V> 值
 */
@SuppressWarnings("unchecked")
public class BeanCache<K, V> extends LoadCache<K, V> {
//	// 获得所有Cache Bean
//	protected final static Map<String, Class<?>> CACHES = M.newMap();
//	// 获得所有Cache Bean
//	private final static Map<String, Field> FIELDS = M.newMap();
//	static {
//		// 获得所有Cache缓存实体
//		U.C.from(Cache.class).forEach(c -> {
//			// 获得缓存名称
//			String name = S.convert(c.getSimpleName(), "Cache");
//			// 添加到缓存类列表
//			CACHES.put(name, c);
//			// 获得所有字段
//			for (Field f : c.getFields())
//				// 判断是否主键
//				if (f.isAnnotationPresent(Key.class)) {
//					// 是主键添加并跳出
//					FIELDS.put(name, f);
//					break;
//				}
//		});
//	}

	// 缓存实体名
	protected String name;
	// 缓存键值的Class
	protected Class<K> key;
	// 缓存值的Class
	protected Class<V> val;
	// 主键的字段
	protected Field field;

	/**
	 * 构造
	 * 
	 * @param name 缓存实体名称
	 * @param val  缓存值的Class
	 * @param load 缓存加载回调
	 */
	protected BeanCache(String name, Class<V> val, Callback<K, V> load) {
		super(load);
		this.name = name;
		this.val = val;
//		this.field = FIELDS.get(name);
		// 获得所有字段
		for (Field f : val.getFields())
			// 判断是否主键
			if (f.isAnnotationPresent(Key.class)) {
				this.field = f;
				break;
			}
		this.key = (Class<K>) this.field.getClass();
	}

	/**
	 * 添加缓存
	 * 
	 * @param val
	 */
	public void put(V val) {
		put(key(val), val);
	}

	/**
	 * 加载所以缓存
	 */
	public void fill(List<V> vals) {
		vals.forEach(v -> put(v));
	}

	/**
	 * 根据val获得key值
	 * 
	 * @param  val 缓存值实体
	 * @return     缓存key
	 */
	protected K key(V val) {
		return (K) C.to(B.getFieldValue(val, field), key);
	}
}
