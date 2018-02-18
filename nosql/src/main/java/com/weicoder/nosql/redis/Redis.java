package com.weicoder.nosql.redis;

import java.util.List;

/**
 * Redis 操作接口
 * @author WD
 */
public interface Redis {
	/**
	 * 压缩值 当值能压缩时才压缩
	 * @param key 键
	 * @param value 值
	 * @return 是否成功
	 */
	String compress(String key, Object value);

	/**
	 * 根据键获得压缩值 如果是压缩的返回解压缩的byte[] 否是返回Object
	 * @param key 键
	 * @return 值
	 */
	byte[] extract(String key);

	/**
	 * 获得多个键的数组
	 * @param keys 键
	 * @return 值
	 */
	List<byte[]> extract(String... keys);

	/**
	 * 追加键值
	 * @param key 键
	 * @param value 值
	 * @return 是否成功
	 */
	boolean append(String key, Object value);

	/**
	 * 设置键值 无论存储空间是否存在相同键，都保存
	 * @param key 键
	 * @param value 值
	 * @return 状态码
	 */
	String set(String key, String value);

	/**
	 * 根据哈希健 保存字段值
	 * @param key 键
	 * @param field 字段
	 * @param value 值
	 * @return 状态码 0 更新 1 新增 -1 错误
	 */
	long hset(String key, String field, String value);

	/**
	 * 设置键值 无论存储空间是否存在相同键，都保存
	 * @param key 键
	 * @param value 值
	 * @return 状态码
	 */
	String set(byte[] key, byte[] value);

	/**
	 * 设置值 带有效期
	 * @param key 健
	 * @param seconds 有效期秒
	 * @param value 值
	 * @return 状态码
	 */
	String setex(String key, int seconds, String value);

	/**
	 * 根据键获得值
	 * @param key 键
	 * @return 值
	 */
	String get(String key);

	/**
	 * 根据哈希键字段获得值
	 * @param key 键
	 * @param field 值
	 * @return 值
	 */
	String hget(String key, String field);

	/**
	 * 根据键获得值
	 * @param key 键
	 * @return 值
	 */
	byte[] get(byte[] key);

	/**
	 * 获得多个键的数组
	 * @param keys 键
	 * @return 值
	 */
	Object[] get(String... keys);

	/**
	 * 删除键值
	 * @param key 键
	 * @return 成功数量
	 */
	long del(String... key);

	/**
	 * 验证键是否存在
	 * @param key 键
	 * @return true 存在 false 不存在
	 */
	boolean exists(String key);

	/**
	 * 根据哈希指定字段 验证是否存在
	 * @param key 键
	 * @param field 字段
	 * @return true 存在 false 不存在
	 */
	boolean hexists(String key, String field);

	/**
	 * 返回剩余的时间以秒为单位
	 * @param key 键
	 * @return 返回整数 剩余的时间以秒为单位 -1 版本不支持 -2 不存在
	 */
	long ttl(String key);
}