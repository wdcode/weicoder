package com.weicoder.redis.cache;

import com.weicoder.common.constants.C.S;
import com.weicoder.common.lang.W.D;
import com.weicoder.common.util.U;
import com.weicoder.common.util.U.B;
import com.weicoder.dao.service.SuperService;
import com.weicoder.json.JsonEngine;
import com.weicoder.redis.Redis;
import com.weicoder.redis.params.RedisParams;

/**
 * redis缓存数据库持久化
 * 
 * @author wudi
 */
public final class RedisCacheDao {
	/**
	 * 添加到持久化列表
	 * 
	 * @param cache 要持久化的缓存
	 * @param po    持久化对应的类 要持久化数据要与缓存数据一致
	 */
	public static <K, V> void add(RedisCache<K, V> cache, Class<?> po) {
		add(cache, po, RedisParams.DELAY, RedisParams.SETP);
	}

	/**
	 * 添加到持久化列表
	 * 
	 * @param cache 要持久化的缓存
	 * @param po    持久化对应的类 要持久化数据要与缓存数据一致
	 * @param delay 定时几秒执行
	 * @param setp  执行步长 如果为0 使用SuperService.adds的队列执行
	 */
	public static <K, V> void add(RedisCache<K, V> cache, Class<?> po, int delay, boolean setp) {
		// 定时任务
		U.SES.delay(() -> {
			// 保存MD5的KEY
			String name = cache.name() + S.UNDERLINE + po.getSimpleName() + "_MD5";
			Redis redis = cache.redis();
			// 在更新队列里获取对象
			String k = redis.rpop(cache.push);
			V v = cache.get(k);
			// 计算md5 比对更新缓存是否需要持久化
			String md5 = D.md5(JsonEngine.toJson(v));
			// 如果MD5值不同 添加到列表
			if (!md5.equals(redis.hget(name, k)))
				// 是否分步执行
				if (setp) {
					// 添加到队列里执行
					SuperService.add(B.copy(v, po));
					// 持久化正确 添加MD5
					redis.hset(name, k, md5);
				} else if (SuperService.DAO.insertOrUpdate(B.copy(v, po)) != null)
					// 持久化正确 添加MD5
					redis.hset(name, k, md5);
				else
					// 执行失败 放回队列
					redis.lpush(cache.push, k);
		}, delay);
	}

	private RedisCacheDao() {
	}
}
