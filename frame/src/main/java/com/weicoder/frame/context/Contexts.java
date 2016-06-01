package com.weicoder.frame.context;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

import com.weicoder.frame.entity.Entity;
import com.weicoder.frame.service.SuperService;
import com.weicoder.common.lang.Maps;
import com.weicoder.common.util.ClassUtil;

/**
 * 全局Context控制
 * @author WD
 * 
 */
public final class Contexts {
	// SuperService
	private static SuperService										service;
	// 短类名对应的类对象Map
	private static ConcurrentMap<String, Class<? extends Entity>>	entitys;

	static {
		service = new SuperService();
		// 获得所有实体
		List<Class<Entity>> lists = ClassUtil.getAssignedClass(Entity.class);
		// 实例化短类名对应的类对象Map
		entitys = Maps.getConcurrentMap();
		// 循环赋值
		for (Class<Entity> c : lists) {
			// 设置实体名对应类
			entitys.put(c.getSimpleName(), c);
		}
	}

	/**
	 * 获得SuperService
	 * @return SuperService
	 */
	public static SuperService getService() {
		return service;
	}

	/**
	 * 根据实体名对应的类对象
	 * @param entity 实体名
	 * @return 类对象
	 */
	@SuppressWarnings("unchecked")
	public static <E extends Entity> Class<E> getClass(String entity) {
		return (Class<E>) entitys.get(entity);
	}

	private Contexts() {
	}
}
