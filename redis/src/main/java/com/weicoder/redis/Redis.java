package com.weicoder.redis;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;
import java.util.Map;

import com.weicoder.common.concurrent.ExecutorUtil;
import com.weicoder.common.lang.Lists;
import com.weicoder.common.lang.Maps;
import com.weicoder.common.log.Log;
import com.weicoder.common.log.LogFactory;
import com.weicoder.common.params.CommonParams;
import com.weicoder.common.util.BeanUtil;
import com.weicoder.common.util.ClassUtil;
import com.weicoder.common.util.EmptyUtil;
import com.weicoder.core.json.JsonEngine;
import com.weicoder.redis.params.RedisParams;
import com.weicoder.redis.annotation.Channel;
import com.weicoder.redis.annotation.Subscribes;
import com.weicoder.redis.factory.RedisFactory;

/**
 * redis订阅功能
 * 
 * @author WD
 */
public final class Redis {
	// 日志
	private final static Log LOG = LogFactory.getLog(Redis.class);
	// 保存Channel对应对象
	private final static Map<String, Object> SUBSCRIBES = Maps.newMap();
	// 保存Channel对应方法
	private final static Map<String, Method> METHODS = Maps.newMap();
	// 保存Redis消费
	private final static Map<String, RedisPool> REDIS = Maps.newMap();
	// 保存Redis对应消费的Channel
	private final static Map<String, List<String>> CHANNELS = Maps.newMap();

	/**
	 * 初始化redis订阅
	 */
	public static void subscribes() {
		// 获得所有redis订阅者
		List<Class<Subscribes>> subscribes = ClassUtil.getAnnotationClass(CommonParams.getPackages("redis"),
				Subscribes.class);
		if (EmptyUtil.isNotEmpty(subscribes)) {
			// 循环处理所有redis订阅类
			int n = 0;
			for (Class<Subscribes> c : subscribes) {
				// 执行对象
				Object subscribe = BeanUtil.newInstance(c);
				Subscribes a = subscribe.getClass().getAnnotation(Subscribes.class);
				String name = a.value();
				if (!REDIS.containsKey(name))
					REDIS.put(name, RedisFactory.getRedis(name));
				// 获得channels列表
				List<String> channels = Maps.getList(CHANNELS, name, String.class);
				// 处理所有方法
				ClassUtil.getPublicMethod(c).forEach(m -> {
					// 方法有执行时间注解
					Channel channel = m.getAnnotation(Channel.class);
					if (channel != null) {
						String val = channel.value();
						METHODS.put(val, m);
						channels.add(val);
						SUBSCRIBES.put(val, subscribe);
						LOG.debug("add redis name={} subscribe={} channel={}", name, c.getSimpleName(), val);
					}
				});
				n += channels.size();
			}
			LOG.info("add redis subscribe={} channels={}", subscribes.size(), n);
			// 订阅相关消费数据
			CHANNELS.forEach((key, val) -> {
				// 定时观察订阅信息
				ExecutorUtil.pool(RedisParams.PREFIX).execute(() -> {
					// ScheduledUtil.delay(RedisParams.PREFIX, () -> {
					REDIS.get(key).subscribe((channel, message) -> {
						// 线程池id
						long tid = Thread.currentThread().getId();
						// 获得订阅通道的对象和方法
						long time = System.currentTimeMillis();
						Object s = SUBSCRIBES.get(channel);
						Method m = METHODS.get(channel);
						if (EmptyUtil.isNotEmptys(s, m)) {
							// 获得所有参数
							Parameter[] params = m.getParameters();
							Object[] objs = null;
							if (EmptyUtil.isEmpty(params))
								// 参数为空直接执行方法
								BeanUtil.invoke(s, m);
							else {
								objs = new Object[params.length];
								// 有参数 现在只支持 1位的参数，1个参数表示message
								Class<?> type = params[0].getType();
								if (params.length == 1)
									if (String.class.equals(type))
										objs[0] = message;
									else
										objs[0] = JsonEngine.toBean(message, type);
								// 执行方法
								BeanUtil.invoke(s, m, objs);
							}
						}
						LOG.debug("redis subscribe={} method={} channel={} message={} time={}  thread={}",
								s.getClass().getSimpleName(), m.getName(), channel, message,
								System.currentTimeMillis() - time, tid);
					}, Lists.toArray(val));
				});
			});
		}
	}

	private Redis() {
	}
}
