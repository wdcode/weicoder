package com.weicoder.memcache.map;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.weicoder.common.constants.C;
import com.weicoder.common.lang.W; 
import com.weicoder.common.util.U;
import com.weicoder.memcache.Memcache; 

/**
 * Memcached实现Map
 * @author WD 
 * @version 1.0  
 */
public final class MemcacheMap<K, V> implements Map<K, V> {
	// Memcached前缀名
	private String		name;
	// memcached客户端
	private Memcache	memcache;

	/**
	 * 构造
	 * @param key
	 * @param memcache
	 */
	public MemcacheMap(String name, Memcache memcache) {
		this.name = name;
		this.memcache = memcache;
	}

	@Override
	public int size() {
		return W.C.toInt(memcache.get(getKeySize()));
	}

	@Override
	public boolean isEmpty() {
		return !memcache.exists(getKey());
	}

	@Override
	public boolean containsKey(Object key) {
		return memcache.exists(getKey(key));
	}

	@Override
	public boolean containsValue(Object value) {
		return values().contains(value);
	}

	@SuppressWarnings("unchecked")
	@Override
	public V get(Object key) {
		return (V) memcache.get(getKey(key));
	}

	@Override
	public V put(K key, V value) {
		V v = get(key);
		// 获得键
		String k = getKey(key);
		// 判断键是否存在 不存在更新键 并且添加到memcached成功
		if (!memcache.exists(k) && memcache.set(k, value)) {
			// 获得保存键值
			k = getKey();
			// 追加到memcace
			if (!memcache.append(k, C.S.COMMA + key)) {
				// 没追加到 第一个直接set
				memcache.set(k, W.C.toString(key));
			}
			// 加数量
			memcache.set(getKeySize(), size() + 1);
		}
		// 返回实体
		return v;
	}

	@Override
	public V remove(Object key) {
		// 获得实体
		V e = get(key);
		// 删除键
		memcache.remove(getKey((Serializable) key));
		// 减数量
		memcache.set(getKeySize(), size() - 1);
		// 减key
		List<String> keys = W.L.list(getKeys());
		// 删除key
		keys.remove(key);
		// 重新写入key
		memcache.set(getKey(), W.L.toString(keys));
		// 返回实体
		return e;
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		for (Map.Entry<? extends K, ? extends V> e : m.entrySet()) {
			put(e.getKey(), e.getValue());
		}
	}

	@Override
	public void clear() {
		// 循环删除key
		for (String key : getKeys()) {
			memcache.remove(key);
		}
		// 删除数量
		memcache.remove(getKeySize());
		// 删除key集合
		memcache.remove(getKey());
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<K> keySet() {
		return (Set<K>) W.S.set(getKeys());
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<V> values() {
		return (Collection<V>) W.L.list(memcache.get(getKeys()));
	}

	@Override
	public Set<java.util.Map.Entry<K, V>> entrySet() {
		return map().entrySet();
	}

	/**
	 * 转成HashMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<K, V> map() {
		String[] keys = getKeys();
		Object[] values = memcache.get(getKey(keys));
		Map<K, V> map = W.M.map();
		for (int i = 0; i < keys.length; i++) {
			if (values[i] != null) {
				map.put((K) keys[i], (V) values[i]);
			}
		}
		return map;
	}

	@Override
	public String toString() {
		return map().toString();
	}

	/**
	 * 获得memcached保存主键key
	 * @return memcached使用键
	 */
	private String getKey() {
		return getKey("key");
	}

	/**
	 * 获得memcached保存笨类型的数量
	 * @return memcached使用键
	 */
	private String getKeySize() {
		return getKey("size");
	}

	/**
	 * 根据单个key获得memcached中保存的键
	 * @param key 原实体键
	 * @return memcached使用键
	 */
	private String getKey(Object key) {
		return W.C.toString(key).startsWith(name) ? W.C.toString(key) : name + C.S.UNDERLINE + key;
	}

	/**
	 * 根据单个key获得memcached中保存的键
	 * @param key 原实体键
	 * @return memcached使用键
	 */
	private String[] getKey(String... key) {
		String[] keys = new String[key.length];
		for (int i = 0; i < key.length; i++) {
			keys[i] = getKey(key[i]);
		}
		return keys;
	}

	/**
	 * 获得本类型全部Keys
	 * @return keys
	 */
	private String[] getKeys() {
		return U.S.split(W.C.toString(memcache.get(getKey())), C.S.COMMA);
	}
}
