package com.weicoder.redis;

import java.util.List;

import com.weicoder.common.U;
import com.weicoder.common.U.B;
import com.weicoder.common.U.E;
import com.weicoder.common.W.C;
import com.weicoder.common.W.D;
import com.weicoder.common.W.L;
import com.weicoder.dao.service.SuperService;
import com.weicoder.json.JsonEngine;
import com.weicoder.redis.cache.RedisCache;

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
	 * @param delay 定时几秒执行
	 * @param setp  执行步长 如果为0 使用SuperService.adds的队列执行
	 */
	public static void add(RedisCache<?, ?> cache, Class<?> po, int delay, int setp) {
		// 定时任务
		U.SES.delay(() -> {
			// 声明要更新的经验列表
			List<Object> list = L.newList();
			String name = cache.name() + "_MD5";
			RedisPool redis = cache.redis();
			// 获得所有经验并且计算是否需要持久化
			cache.map().forEach((k, v) -> {
				// 计算md5 比对更新缓存是否需要持久化
				String md5 = D.md5(JsonEngine.toJson(v));
				String uid = C.toString(k);
				// 如果MD5值不同 添加到列表
				if (md5.equals(redis.hget(name, uid))) {
					// 添加到列表
					list.add(B.copy(v, po));
					// 添加MD5
					redis.hset(name, uid, md5);
				}
			});
			// 如果持久化列表不为空 持久化
			if (E.isNotEmpty(list))
				if (setp == 0)
					SuperService.adds(list);
				else
					SuperService.DAO.insertOrUpdate(list, setp);
		}, delay);
	}

	private RedisCacheDao() {
	}
}
