package com.weicoder.nosql.redis;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;
import java.util.Map;

import com.weicoder.common.concurrent.ScheduledUtil;
import com.weicoder.common.lang.Lists;
import com.weicoder.common.lang.Maps;
import com.weicoder.common.log.Logs;
import com.weicoder.common.params.CommonParams;
import com.weicoder.common.util.BeanUtil;
import com.weicoder.common.util.ClassUtil;
import com.weicoder.common.util.DateUtil;
import com.weicoder.common.util.EmptyUtil;
import com.weicoder.nosql.redis.annotation.Channel;
import com.weicoder.nosql.redis.annotation.Subscribes;
import com.weicoder.nosql.redis.factory.RedisFactory;

import redis.clients.jedis.JedisPubSub;

/**
 * redis订阅功能
 * @author WD
 */
public final class Redis {
	// 保存Channel对应对象
	private final static Map<String, Object>		SUBSCRIBES	= Maps.newMap();
	// 保存Channel对应方法
	private final static Map<String, Method>		METHODS		= Maps.newMap();
	// 保存Redis消费
	private final static Map<String, Subscribe>		REDIS		= Maps.newMap();
	// 保存Redis对应消费的Channel
	private final static Map<String, List<String>>	CHANNELS	= Maps.newMap();

	/**
	 * 初始化redis订阅
	 */
	public static void subscribes() {
		// 获得所有redis订阅者
		List<Class<Subscribes>> subscribes = ClassUtil.getAnnotationClass(CommonParams.getPackages("redis"),
				Subscribes.class);
		if (EmptyUtil.isNotEmpty(subscribes)) {
			// 循环处理所有redis订阅类
			for (Class<Subscribes> c : subscribes) {
				// 执行对象
				Object subscribe = BeanUtil.newInstance(c);
				Subscribes a = subscribe.getClass().getAnnotation(Subscribes.class);
				String name = a.value();
				if (!REDIS.containsKey(name)) {
					REDIS.put(name, RedisFactory.getSubscribe(name));
				}
				// 获得channels列表
				List<String> channels = Maps.getList(CHANNELS, name, String.class);
				// 处理所有方法
				for (Method m : c.getDeclaredMethods()) {
					// 方法有执行时间注解
					Channel channel = m.getAnnotation(Channel.class);
					if (channel != null) {
						String val = channel.value();
						METHODS.put(val, m);
						channels.add(val);
						SUBSCRIBES.put(val, subscribe);
						Logs.info("add redis subscribe={} channel={}", c.getSimpleName(), val);
					}
				}
			}
			Logs.info("add redis subscribe={} channels={}", subscribes.size());
			// // 订阅相关消费数据
			for (String key : CHANNELS.keySet()) {
				List<String> channels = CHANNELS.get(key);
				// 定时观察订阅信息
				ScheduledUtil.delay(() -> {
					REDIS.get(key).subscribe(new JedisPubSub() {
						@Override
						public void onMessage(String channel, String message) {
							// 获得订阅通道的对象和方法
							int time = DateUtil.getTime();
							Object s = SUBSCRIBES.get(channel);
							Method m = METHODS.get(channel);
							if (EmptyUtil.isNotEmptys(s, m)) {
								// 获得所有参数
								Parameter[] params = m.getParameters();
								Object[] objs = null;
								if (EmptyUtil.isEmpty(params)) {
									// 参数为空直接执行方法
									BeanUtil.invoke(s, m);
								} else {
									objs = new Object[params.length];
									// 有参数 现在只支持 1-2位的参数，1个参数表示message
									if (params.length == 1) {
										objs[0] = message;
									}
									// 执行方法
									BeanUtil.invoke(s, m, objs);
								}
							}
							Logs.debug("redis onMessage subscribe={} method={} time={}", s, m,
									DateUtil.getTime() - time);
						}
					}, Lists.toArray(channels));
				}, 1);
			}

		}
	}

	private Redis() {}
}
