package com.weicoder.redis;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.weicoder.common.interfaces.Calls;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.resps.Tuple;

/**
 * Redis 操作接口
 * 
 * @author WD
 */
public interface Redis {
	/**
	 * 返回redis名称
	 * 
	 * @return redis名
	 */
	String name();

	/**
	 * 集群模式会跟据key返回Jedis连接 pool模式直接返回Jedis
	 * 
	 * @return Jedis
	 */
	Jedis getResource(String key);

	/**
	 * 执行Redis 回调使用 内部处理jedis的关闭问题带事务功能
	 * 
	 * @param callback
	 */
	void multi(Calls.EoV<Transaction> callback);

	/**
	 * 执行Redis 回调使用 内部处理jedis的关闭问题
	 * 
	 * @param callback
	 */
	void exec(Calls.EoV<Jedis> callback);

	/**
	 * 从大到小获取有序集合里的数据
	 * 
	 * @param key    剑
	 * @param max    最大值
	 * @param min    最小值
	 * @param offset 获取开始
	 * @param count  获取数量
	 * @return Set<Tuple>
	 */
	List<Tuple> zrevrangeByScoreWithScores(String key, double max, double min, int offset, int count);

	/**
	 * 从队列右侧开始取数据
	 * 
	 * @param key
	 * @return
	 */
	String rpop(String key);

	/**
	 * 分布式锁锁定1秒 无超时时间 会一直等待
	 * 
	 * @param key 加锁key
	 * @return 是否成功
	 */
	void lock(String key);

	/**
	 * 分布式锁锁定1秒 有超时时间
	 * 
	 * @param key 加锁key
	 * @param ms  超时时间 毫秒
	 * @return 是否加锁成功 如果超时会返回false失败
	 */
	boolean lock(String key, long ms);

	/**
	 * 分布式锁锁定s秒 无超时
	 * 
	 * @param key 加锁key
	 * @param s   锁时间 秒
	 * @return 是否加锁成功 如果超时会返回false失败
	 */
	boolean lock(String key, int s);

	/**
	 * 分布式锁
	 * 
	 * @param key     加锁key
	 * @param seconds 加锁超时时间 最小1秒 -1为无超时时间 注意不要死锁
	 * @param timeout 超时时间 等待超时时间 如果有锁并等待时间已过返回false
	 * @return 是否加锁成功 如果超时会返回false失败
	 */
	boolean lock(String key, int seconds, long timeout);

	/**
	 * 分布式解锁
	 * 
	 * @param key 解锁key
	 * @return 是否成功
	 */
	void unlock(String key);

	/**
	 * 压缩值 当值能压缩时才压缩
	 * 
	 * @param key   键
	 * @param value 值
	 * @return 是否成功
	 */
	String compress(String key, Object value);

	/**
	 * 根据键获得压缩值 如果是压缩的返回解压缩的byte[] 否是返回Object
	 * 
	 * @param key 键
	 * @return 值
	 */
	byte[] extract(String key);

	/**
	 * 获得多个键的数组
	 * 
	 * @param keys 键
	 * @return 值
	 */
	List<byte[]> extract(String... keys);

	/**
	 * 追加键值
	 * 
	 * @param key   键
	 * @param value 值
	 * @return 是否成功
	 */
	long append(String key, Object value);

	/**
	 * 设置键值 无论存储空间是否存在相同键，都保存
	 * 
	 * @param key   键
	 * @param value 值
	 * @return 状态码
	 */
	String set(String key, String value);

	/**
	 * 设置键值 无论存储空间是否存在相同键，都保存
	 * 
	 * @param key   键
	 * @param value 值
	 * @return 状态码
	 */
	String set(String key, Object value);

	/**
	 * 根据哈希健 保存字段值
	 * 
	 * @param key   键
	 * @param field 字段
	 * @param value 值
	 * @return 状态码 0 更新 1 新增 -1 错误
	 */
	long hset(String key, String field, String value);

	/**
	 * 根据哈希健 保存字段值
	 * 
	 * @param key   键
	 * @param field 字段
	 * @param value 值
	 * @return 状态码 0 更新 1 新增 -1 错误
	 */
	long hset(String key, Object field, Object value);

	/**
	 * 设置键值 无论存储空间是否存在相同键，都保存
	 * 
	 * @param key   键
	 * @param value 值
	 * @return 状态码
	 */
	String set(byte[] key, byte[] value);

	/**
	 * 设置值 带有效期
	 * 
	 * @param key     健
	 * @param seconds 有效期秒
	 * @param value   值
	 * @return 状态码
	 */
	String setex(String key, long seconds, String value);

	/**
	 * 根据键获得值
	 * 
	 * @param key 键
	 * @return 值
	 */
	String get(String key);

	/**
	 * 根据哈希键字段获得值
	 * 
	 * @param key   键
	 * @param field 值
	 * @return 值
	 */
	String hget(String key, String field);

	/**
	 * 根据哈希键获得数量
	 * 
	 * @param key 键
	 * @return 值
	 */
	long hlen(String key);

	/**
	 * 根据哈希主键获得所有列表数据 性能超差慎用慎用
	 * 
	 * @param key 哈希主键
	 * @return Map
	 */
	Map<String, String> hgetAll(String key);

	/**
	 * 根据键获得值
	 * 
	 * @param key 键
	 * @return 值
	 */
	byte[] get(byte[] key);

	/**
	 * 根据键获得值
	 * 
	 * @param key 键
	 * @return 值
	 */
	List<byte[]> mget(byte[][] key);

	/**
	 * 获得多个键的数组
	 * 
	 * @param keys 键
	 * @return 值
	 */
	Object[] get(String... keys);

	/**
	 * 删除键值
	 * 
	 * @param key 键
	 * @return 成功数量
	 */
	long del(String... key);

	/**
	 * 删除键值
	 * 
	 * @param key   键
	 * @param field 要删除的字段
	 * @return 成功数量
	 */
	long hdel(String key, String... field);

	/**
	 * 验证键是否存在
	 * 
	 * @param key 键
	 * @return true 存在 false 不存在
	 */
	boolean exists(String key);

	/**
	 * 验证集合是否存在值
	 * 
	 * @param key    键
	 * @param member 值
	 * @return true 存在 false 不存在
	 */
	boolean sexists(String key, String member);

	/**
	 * 验证集合是否存在值
	 * 
	 * @param key    键
	 * @param member 值
	 * @return true 存在 false 不存在
	 */
	boolean sexists(String key, Object member);

	/**
	 * 根据哈希指定字段 验证是否存在
	 * 
	 * @param key   键
	 * @param field 字段
	 * @return true 存在 false 不存在
	 */
	boolean hexists(String key, String field);

	/**
	 * 根据哈希指定字段 验证是否存在
	 * 
	 * @param key   键
	 * @param field 字段
	 * @return true 存在 false 不存在
	 */
	boolean hexists(String key, Object field);

	/**
	 * 如果字段不存在，则将指定的散列字段设置为指定的值
	 * 
	 * @param key   健
	 * @param field 字段
	 * @param value 值
	 * @return 如果字段已存在返回，则返回0，否则如果创建新字段，则为1
	 */
	long hsetnx(String key, String field, String value);

	/**
	 * 返回剩余的时间以秒为单位
	 * 
	 * @param key 键
	 * @return 返回整数 剩余的时间以秒为单位 -1 版本不支持 -2 不存在
	 */
	long ttl(String key);

	/**
	 * 订阅消息
	 * 
	 * @param sub      订阅类
	 * @param channels 通道
	 */
	void subscribe(Subscribe sub, String... channels);

	/**
	 * 发布消息
	 * 
	 * @param channel 消息通道
	 * @param message 消息内容
	 * @return 返回结果
	 */
	long publish(String channel, String message);

	/**
	 * 发布消息
	 * 
	 * @param channel 消息通道
	 * @param message 消息内容
	 * @return 返回结果
	 */
	long publish(byte[] channel, byte[] message);

	/**
	 * 从右侧入队列
	 * 
	 * @param key     健
	 * @param strings 入队数据
	 * @return 返回数量
	 */
	Long rpush(String key, String... strings);

	/**
	 * 从左侧读取数据
	 * 
	 * @param key 健
	 * @return 读出的元素
	 */
	String lpop(String key);

	/**
	 * 从左侧入队列
	 * 
	 * @param key     健
	 * @param strings 入队数据
	 * @return 返回数量
	 */
	Long lpush(String key, String... strings);

	/**
	 * 获得列表
	 * 
	 * @param key   键
	 * @param start 开始 位置
	 * @param stop  结束位置
	 * @return 列表
	 */
	List<String> lrange(final String key, final long start, final long stop);

	/**
	 * 读取队列数量
	 * 
	 * @param key 健
	 * @return 数量
	 */
	long llen(String key);

	/**
	 * 返回集合数量
	 * 
	 * @param key 键
	 * @return 集合数量
	 */
	long zcard(String key);

	/**
	 * 返回有序集 key 中，成员 member 的 score 值 如果 member 元素不是有序集 key 的成员，或 key 不存在，返回 null
	 * 
	 * @param key    键
	 * @param member 成员
	 * @return Double
	 */
	Double zscore(String key, String member);

	/**
	 * 获得redis list 数据
	 * 
	 * @param key   健
	 * @param start 开始数
	 * @param end   结束数
	 * @return 值列表
	 */
	List<String> zrevrange(String key, long start, long end);

	/**
	 * 获得redis list 数据
	 * 
	 * @param key   健
	 * @param start 开始数
	 * @param end   结束数
	 * @return 值列表
	 */
	List<String> zrange(String key, long start, long end);

	/**
	 * 获得redis list 数据
	 * 
	 * @param key 健
	 * @param min 最小
	 * @param max 最大
	 * @return 结果
	 */
	List<String> zrangeByScore(String key, String min, String max);

	/**
	 * 添加列表数据
	 * 
	 * @param key    健
	 * @param score  分值
	 * @param member 成员
	 * @return 数量
	 */
	Long zadd(String key, double score, String member);

	/**
	 * 增量添加到列表数据 如果存在成员加法 不存在添加
	 * 
	 * @param key       键
	 * @param increment 分数
	 * @param member    成员
	 * @return
	 */
	Double zincrby(String key, double increment, String member);

	/**
	 * 添加列表数据
	 * 
	 * @param key     健
	 * @param members 成员
	 * @return 数量
	 */
	Long sadd(String key, String... members);

	/**
	 * 获取sadd添加列表
	 * 
	 * @param key 键
	 * @return 列表
	 */
	Set<String> smembers(String key);

	/**
	 * 获得列表成员数
	 * 
	 * @param key 健
	 * @return 数量
	 */
	long scard(String key);

	/**
	 * 删除列表数据
	 * 
	 * @param key     健
	 * @param members 成员
	 * @return 数量
	 */
	Long zrem(String key, String... members);

	/**
	 * 删除列表数据
	 * 
	 * @param key     健
	 * @param members 成员
	 * @return 数量
	 */
	Long srem(String key, String... members);

	/**
	 * redis订阅
	 * 
	 * @author wudi
	 */
	interface Subscribe {
		/**
		 * 回调
		 * 
		 * @param channel
		 * @param message
		 */
		void onMessage(String channel, String message);
	}
}