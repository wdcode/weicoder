package com.weicoder.cache;

import java.lang.reflect.Field;
import java.util.List;

import com.weicoder.cache.annotation.Cache;
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
		if (val.isAnnotationPresent(Cache.class))
			this.field = B.getField(val, val.getAnnotation(Cache.class).key());
		else
			this.field = B.getField(val, 0);
		this.key = (Class<K>) this.field.getClass();
	}

	/**
	 * 获得缓存实体名
	 * 
	 * @return 缓存实体名
	 */
	public String name() {
		return name;
	}

	/**
	 * 设置对象到缓存值 只用Bean拷贝 暂时没做基础类型处理 调用可以异常
	 * 
	 * @param  key 键
	 * @param  obj 赋值给缓存
	 * @return
	 */
	public V of(K key, Object obj) {
		// 获取值
		V v = get(key);
		// 值为空 声明新的 不为空 拷贝属性
		if (v == null)
			v = B.copy(obj, val);
		else
			v = B.copy(obj, v);
		// 返回值
		return put(key, v);
	}

	/**
	 * 添加缓存
	 * 
	 * @param val
	 */
	public V put(V val) {
		return put(key(val), val);
	}

	/**
	 * 加载所以缓存
	 */
	public void fill(List<V> vals) {
		vals.forEach(v -> put(v));
	}

	/**
	 * 传入任意类型的key 内部转换成K获取V
	 */
	public V get(Object k) {
		return super.get((K) C.to(k, key));
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
