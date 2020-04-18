package com.weicoder.redis.cache; 

import com.weicoder.common.C.S;
import com.weicoder.common.U;
import com.weicoder.common.U.B; 
import com.weicoder.common.W.C;
import com.weicoder.common.W.D; 
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
	public static void add(RedisCache<?, ?> cache, Class<?> po) {
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
	public static void add(RedisCache<?, ?> cache, Class<?> po, int delay, int setp) {
		// 定时任务
		U.SES.delay(() -> {
			// 保存MD5的KEY
			String name = cache.name() + S.UNDERLINE + po.getSimpleName() + "_MD5";
			Redis redis = cache.redis();
			// 获得所有经验并且计算是否需要持久化
			cache.map().forEach((k, v) -> {
				// 计算md5 比对更新缓存是否需要持久化
				String md5 = D.md5(JsonEngine.toJson(v));
				String uid = C.toString(k);
				// 如果MD5值不同 添加到列表
				if (!md5.equals(redis.hget(name, uid))) {
					// 持久化
					if (setp == 0)
						SuperService.add(B.copy(v, po));
					else
						SuperService.DAO.insertOrUpdate(B.copy(v, po));
					// 添加MD5
					redis.hset(name, uid, md5);
				}
			});
		}, delay);
	}

	private RedisCacheDao() {
	}
}
