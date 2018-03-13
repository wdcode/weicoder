package com.weicoder.nosql.redis;

import java.util.List;
import java.util.Map;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

/**
 * Redis 操作接口
 * @author WD
 */
public interface Redis {
	/**
	 * 获取资源Jedis
	 * @return Jedis
	 */
	Jedis getResource();

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
	long append(String key, Object value);

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
	 * 根据哈希主键获得所有列表数据
	 * @param key 哈希主键
	 * @return Map
	 */
	Map<String, String> hgetAll(String key);

	/**
	 * 根据键获得值
	 * @param key 键
	 * @return 值
	 */
	byte[] get(byte[] key);

	/**
	 * 根据键获得值
	 * @param key 键
	 * @return 值
	 */
	List<byte[]> mget(byte[][] key);

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
	 * 删除键值
	 * @param key 键
	 * @param field 要删除的字段
	 * @return 成功数量
	 */
	long hdel(String key, String... field);

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
	 * 如果字段不存在，则将指定的散列字段设置为指定的值
	 * @param key 健
	 * @param field 字段
	 * @param value 值
	 * @return 如果字段已存在返回，则返回0，否则如果创建新字段，则为1
	 */
	long hsetnx(String key, String field, String value);

	/**
	 * 返回剩余的时间以秒为单位
	 * @param key 键
	 * @return 返回整数 剩余的时间以秒为单位 -1 版本不支持 -2 不存在
	 */
	long ttl(String key);

	/**
	 * 订阅消息
	 * @param jedisPubSub 订阅类
	 * @param channels 通道
	 */
	void subscribe(JedisPubSub jedisPubSub, String... channels);

	/**
	 * 从右侧入队列
	 * @param key 健
	 * @param strings 入队数据
	 * @return 返回数量
	 */
	long rpush(String key, String... strings);

	/**
	 * 从左侧读取数据
	 * @param key 健
	 * @return 读出的元素
	 */
	String lpop(String key);

	/**
	 * 读取队列数量
	 * @param key 健
	 * @return 数量
	 */
	long llen(String key);
}