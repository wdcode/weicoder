package com.weicoder.nosql.kafka;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import com.weicoder.common.lang.Maps;
import com.weicoder.common.log.Logs;
import com.weicoder.common.params.CommonParams;
import com.weicoder.common.util.BeanUtil;
import com.weicoder.common.util.ClassUtil;
import com.weicoder.common.util.EmptyUtil;

/**
 * kafka生成器
 * @author WD
 */
public final class Kafkas {
	// 保存Topic对应对象
	private final static Map<String, Object>	CONSUMERS	= Maps.newMap();
	// 保存Topic对应方法
	private final static Map<String, Method>	METHODS		= Maps.newMap();

	/**
	 * 初始化
	 */
	public static void init() {
		try {
			List<Class<Consumer>> consumers = ClassUtil.getAnnotationClass(CommonParams.getPackages("kafka"),
					Consumer.class);
			if (!EmptyUtil.isEmpty(consumers)) {
				// 循环处理kafka类
				for (Class<Consumer> c : consumers) {
					// 执行对象
					Object obj = BeanUtil.newInstance(c);
					// 处理所有方法
					for (Method m : c.getMethods()) {
						// 方法有执行时间注解
						Topic t = m.getAnnotation(Topic.class);
						if (t != null) {
							METHODS.put(t.value(), m);
							CONSUMERS.put(t.value(), obj);
							Logs.info("add kafka Consumer={} topic={}", c.getSimpleName(), t.value());
						}
					}
				}
			}

		} catch (Exception e) {
			Logs.error(e);
		}
	}

	private Kafkas() {}
}
