package com.weicoder.frame.context;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;

import com.weicoder.frame.cache.Cache;
import com.weicoder.frame.cache.impl.CacheMap;
import com.weicoder.frame.cache.impl.CacheNoSQL;
import com.weicoder.frame.entity.Entity;
import com.weicoder.frame.params.FrameParams;
import com.weicoder.frame.service.SuperService;
import com.weicoder.common.lang.Lists;
import com.weicoder.common.lang.Maps;

/**
 * 全局Context控制
 * @author WD 
 * @version 1.0 
 */
public final class Contexts {
	//WebApplicationContext
	private static ApplicationContext								context;
	//SuperService
	private static SuperService										service;
	// DefaultListableBeanFactory 
	private static DefaultListableBeanFactory						factory;
	// 短类名对应的类对象Map
	private static ConcurrentMap<String, Class<? extends Entity>>	entitys;

	/**
	 * 初始化
	 * @param context
	 */
	public static void init(ApplicationContext context) {
		Contexts.context = context;
		service = getBean(SuperService.class);
		// 获得所有实体
		Map<String, Entity> map = getBeans(Entity.class);
		// 实例化短类名对应的类对象Map
		entitys = Maps.getConcurrentMap();
		// 循环赋值
		for (Map.Entry<String, ? extends Entity> e : map.entrySet()) {
			// 设置实体名对应类
			entitys.put(e.getKey(), e.getValue().getClass());
		}
	}

	/**
	 * 获得ApplicationContext
	 * @return ApplicationContext
	 */
	public static ApplicationContext getContext() {
		return context;
	}

	/**
	 * 获得SuperService
	 * @return SuperService
	 */
	public static SuperService getService() {
		return service;
	}

	/**
	 * 获得所有实体类列表
	 * @return 类列表
	 */
	public static List<Class<? extends Entity>> getEntitys() {
		return Lists.getList(entitys.values());
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

	/**
	 * 根据名称和类获得实体
	 * @param name 名称
	 * @param requiredType 类型
	 * @return 实体
	 */
	public static <E> E getBean(String name, Class<E> requiredType) {
		return context.getBean(name, requiredType);
	}

	/**
	 * 根据类获得实体
	 * @param requiredType 类型
	 * @return 实体
	 */
	public static <E> E getBean(Class<E> requiredType) {
		return context.getBean(requiredType);
	}

	/**
	 * 根据类获得实体
	 * @param requiredType 类型
	 * @param 参数
	 * @return 实体
	 */
	@SuppressWarnings("unchecked")
	public static <E> E getBean(Class<E> requiredType, Object... args) {
		return (E) context.getBean(requiredType.getName(), args);
	}

	/**
	 * 根据类获得缓存
	 * @param type 实体类型
	 * @return 缓存
	 */
	@SuppressWarnings("unchecked")
	public static <E extends Entity> Cache<E> getCache() {
		return "map".equals(FrameParams.CACHE_TYPE) ? getBean(CacheMap.class) : getBean(CacheNoSQL.class);
	}

	/**
	 * 根据传入的注解类获得 名-对象 Map列表
	 * @param annotationType 注解类
	 * @return Map列表
	 */
	public static Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> annotationType) {
		return context.getBeansWithAnnotation(annotationType);
	}

	/**
	 * 根据传入的注解类获得 名-对象 Map列表
	 * @param annotationType 注解类
	 * @return Map列表
	 */
	public static <E> Map<String, E> getBeans(Class<E> type) {
		return context.getBeansOfType(type);
	}

	/**
	 * 注册一个新Bean
	 * @param beanName Bean名称
	 * @param beanDefinition Bean
	 */
	public static void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
		factory.registerBeanDefinition(beanName, beanDefinition);
	}

	private Contexts() {}
}
