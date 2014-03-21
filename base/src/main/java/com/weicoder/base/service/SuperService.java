package com.weicoder.base.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.weicoder.base.cache.Cache;
import com.weicoder.base.cache.impl.CacheEmpty;
import com.weicoder.base.context.Context;
import com.weicoder.base.dao.Dao;
import com.weicoder.base.entity.Entity;
import com.weicoder.base.bean.Pagination;
import com.weicoder.common.lang.Conversion;
import com.weicoder.common.lang.Lists;
import com.weicoder.common.lang.Maps;
import com.weicoder.common.util.BeanUtil;
import com.weicoder.common.util.EmptyUtil;

/**
 * 超级通用业务hibernate实现
 * @author WD
 * @since JDK7
 * @version 1.0 2012-07-02
 */
@Service
public class SuperService {
	// Context
	@Resource
	private Context															context;
	// Hibernate Dao
	@Resource
	private Dao																dao;
	// 缓存
	private ConcurrentMap<Class<? extends Entity>, Cache<? extends Entity>>	caches;
	// 缓存加载
	private ConcurrentMap<Class<? extends Entity>, Boolean>					loads;
	// 空缓存
	private Cache<? extends Entity>											empty;

	/**
	 * 初始化
	 */
	@PostConstruct
	protected void init() {
		// 获得所有带缓存实体
		Map<String, Object> map = context.getBeansWithAnnotation(com.weicoder.base.annotation.Cache.class);
		// 实例化缓存
		caches = Maps.getConcurrentMap();
		// 实例化缓存加载
		loads = Maps.getConcurrentMap();
		// 实例化空缓存
		empty = new CacheEmpty();
		// 循环赋值
		for (Map.Entry<String, Object> e : map.entrySet()) {
			// 声明Class
			Class<? extends Entity> c = (Class<? extends Entity>) e.getValue().getClass();
			// 声明cached
			Cache<? extends Entity> cache = context.getCache();
			cache.setClass(c);
			// 设置有缓存的实体Map
			caches.put(c, cache);
			loads.put(c, false);
		}
	}

	/**
	 * 添加
	 * @param entity 实体
	 * @return ID
	 */
	public <E extends Entity> E insert(E entity) {
		return getCache(entity).set(dao.insert(entity));
	}

	/**
	 * 添加
	 * @param entitys 实体
	 * @return ID
	 */
	public <E extends Entity> List<E> insert(List<E> entitys) {
		return getCache(entitys).set(dao.insert(entitys));
	}

	/**
	 * 更新
	 * @param entity 实体
	 * @return 是否成功
	 */
	public <E extends Entity> E update(E entity) {
		return getCache(entity).set(dao.update(entity));
	}

	/**
	 * 更新
	 * @param entitys 实体
	 * @return 是否成功
	 */
	public <E extends Entity> List<E> update(List<E> entitys) {
		return getCache(entitys).set(dao.update(entitys));
	}

	/**
	 * 添加或更新
	 * @param entitys 实体
	 * @return 影响行数
	 */
	public <E extends Entity> E insertOrUpdate(E entity) {
		return getCache(entity).set(dao.insertOrUpdate(entity));
	}

	/**
	 * 添加或更新
	 * @param entitys 实体
	 * @return 影响行数
	 */
	public <E extends Entity> List<E> insertOrUpdate(List<E> entitys) {
		return getCache(entitys).set(dao.insertOrUpdate(entitys));
	}

	/**
	 * 删除
	 * @param list 实体列表
	 * @return 是否成功
	 */
	public <E extends Entity> List<E> delete(E entity) {
		// 查询出符合删除实体列表
		List<E> list = list(entity, -1, -1);
		// 删除列表为空
		if (EmptyUtil.isEmpty(list)) {
			return Lists.emptyList();
		}
		// 删除
		list = dao.delete(list);
		// 返回结果
		return isCache(entity.getClass()) ? getCache(entity).remove(list) : list;
	}

	/**
	 * 删除
	 * @param list 实体列表
	 * @return 是否成功
	 */
	public <E extends Entity> List<E> delete(List<E> entitys) {
		// 删除
		List<E> list = dao.delete(entitys);
		// 返回结果
		return isCache(entitys.get(0).getClass()) ? getCache(entitys).remove(list) : list;
	}

	/**
	 * 根据ID数组删除 完全删除
	 * @param entity 要查询的实体
	 * @param pk 主键数组
	 * @return 是否成功
	 */
	public <E extends Entity> List<E> delete(Class<E> entity, Serializable... pks) {
		return delete(gets(entity, pks));
	}

	/**
	 * 根据ID 获得实体
	 * @param entityClass 要查询的实体
	 * @param pk 主键
	 * @return 实体
	 */
	public <E extends Entity> E get(Class<E> entityClass, Serializable pk) {
		pk = key(pk);
		// 获得缓存
		Cache<E> cache = getCache(entityClass);
		// 转换主键类型
		pk = Conversion.toInt(pk) > 0 ? Conversion.toInt(pk) : Conversion.toString(pk);
		// 返回查询结果
		return cache.isValid() ? cache.get(pk) : dao.get(entityClass, pk);
	}

	/**
	 * 根据ID 获得实体
	 * @param entityClass 要查询的实体
	 * @param pk 主键
	 * @return 实体
	 */
	public <E extends Entity> List<E> gets(Class<E> entityClass, Serializable... pks) {
		// 如果主键为空
		if (EmptyUtil.isEmpty(pks)) {
			return Lists.emptyList();
		}
		// 转换主键
		pks = keys(pks);
		// 获得缓存
		Cache<E> cache = getCache(entityClass);
		// 缓存存在
		if (cache.isValid()) {
			// 声明列表
			List<E> list = Lists.getList();
			// 循环赋值
			for (Serializable pk : pks) {
				list.add(cache.get(Conversion.toInt(pk) > 0 ? Conversion.toInt(pk) : Conversion.toString(pk)));
			}
			// 返回列表
			return list;
		} else {
			// 无缓存 返回查询结果
			return dao.gets(entityClass, pks);
		}
	}

	/**
	 * 根据传入的条件，返回唯一的实体 如果有多个返回第一个实体
	 * @param entity 实体,
	 * @return 实体
	 */
	public <E extends Entity> E get(E entity) {
		return dao.get(entity);
	}

	/**
	 * 获得持久化对象
	 * @param entityClass 要查询的实体
	 * @param property 属性名
	 * @param value 属性值
	 * @return 要获得的持久化对象，如果不存在返回null
	 */
	public <E extends Entity> E get(Class<E> entityClass, String property, Object value) {
		return dao.get(entityClass, property, value);
	}

	/**
	 * 获得持久化对象
	 * @param entityClass 实体类
	 * @param map 属性键值
	 * @return 要获得的持久化对象，如果不存在返回null
	 */
	public <E extends Entity> E get(Class<E> entityClass, Map<String, Object> map) {
		return dao.get(entityClass, map);
	}

	/**
	 * 查询全部数据
	 * @param entityClass 要查询的实体
	 * @return 全部实体
	 */
	public <E extends Entity> List<E> all(Class<E> entityClass) {
		return list(entityClass, -1, -1);
	}

	/**
	 * 查询指定条数
	 * @param entityClass 要查询的实体
	 * @param firstResult 开始查询的条数
	 * @param maxResults 最多查询多少条
	 * @return 全部实体
	 */
	public <E extends Entity> List<E> list(Class<E> entityClass, int firstResult, int maxResults) {
		// 获得缓存
		Cache<E> cache = getCache(entityClass);
		// 判断有缓存
		if (cache.isValid()) {
			// 返回新列表
			return Lists.subList(cache.list(), firstResult, firstResult + maxResults);
		} else {
			// 查询数据库
			return toString(dao.list(entityClass, firstResult, maxResults));
		}
	}

	/**
	 * 获得查询的对象实体列表 分页功能
	 * @param entityClass 要查询的实体
	 * @param page 分页Bean
	 * @return 返回这个对象的列表
	 */
	public <E extends Entity> List<E> list(Class<E> entityClass, Pagination page) {
		// 获得数据列表
		List<E> list = list(entityClass, getFirstResult(page), getMaxResults(page));
		// 判断列表
		if (EmptyUtil.isEmpty(list)) {
			// 为空 设置总数为 0
			page.setTotalSize(0);
		} else {
			// 不为空 查询出总数
			page.setTotalSize(count(entityClass));
		}
		// 返回列表
		return list;
	}

	/**
	 * 根据实体查询
	 * @param entity 要查询的实体
	 * @param entity 实体
	 * @param firstResult 开始查询的条数
	 * @param maxResults 最多查询多少条
	 * @return 列表
	 */
	public <E extends Entity> List<E> list(E entity, int firstResult, int maxResults) {
		return dao.list(entity, firstResult, maxResults);
	}

	/**
	 * 获得查询的对象实体列表 分页功能
	 * @param entity 需要获得的对象，会查询出实体中封装的相等的条件
	 * @param page 分页Bean
	 * @return 返回这个对象的列表
	 */
	public <E extends Entity> List<E> list(E entity, Pagination page) {
		// 获得数据列表
		List<E> list = list(entity, getFirstResult(page), getMaxResults(page));
		// 判断列表
		if (EmptyUtil.isEmpty(list)) {
			// 为空 设置总数为 0
			page.setTotalSize(0);
		} else {
			// 不为空 查询出总数
			page.setTotalSize(count(entity));
		}
		// 返回列表
		return list;
	}

	/**
	 * 查询属性名等值的实体列表
	 * @param entityClass 实体类
	 * @param property 属性名
	 * @param value 属性值
	 * @param firstResult 重第几条开始查询
	 * @param maxResults 一共查回多少条
	 * @return 数据列表
	 */
	public <E extends Entity> List<E> like(Class<E> entityClass, String property, Object value, int firstResult, int maxResults) {
		return dao.like(entityClass, property, value, firstResult, maxResults);
	}

	/**
	 * 查询属性名含有列表的实体列表
	 * @param entityClass 要查询的实体
	 * @param map 键值表
	 * @param firstResult 重第几条开始查询
	 * @param maxResults 一共查回多少条
	 * @return 数据列表
	 */
	public <E extends Entity> List<E> eq(Class<E> entityClass, Map<String, Object> map, int firstResult, int maxResults) {
		return dao.eq(entityClass, map, firstResult, maxResults);
	}

	/**
	 * 查询属性名等值的实体列表
	 * @param entityClass 要查询的实体
	 * @param property 属性名
	 * @param value 属性值
	 * @param firstResult 重第几条开始查询
	 * @param maxResults 一共查回多少条
	 * @return 数据列表
	 */
	public <E extends Entity> List<E> eq(Class<E> entityClass, String property, Object value, int firstResult, int maxResults) {
		return dao.eq(entityClass, property, Conversion.to(value, BeanUtil.getField(entityClass, property).getType()), firstResult, maxResults);
	}

	/**
	 * 查询属性名等值的实体列表
	 * @param entityClass 要查询的实体
	 * @param property 属性名
	 * @param values 属性值
	 * @param pager 分页Bean
	 * @return 数据列表
	 */
	public <E extends Entity> List<E> in(Class<E> entityClass, String property, List<Object> values, Pagination pager) {
		// 获得数据列表
		List<E> list = in(entityClass, property, values, getFirstResult(pager), getMaxResults(pager));
		// 判断列表
		if (EmptyUtil.isEmpty(list)) {
			// 为空 设置总数为 0
			pager.setTotalSize(0);
		} else {
			// 不为空 查询出总数
			pager.setTotalSize(count(entityClass, property, values));
		}
		// 返回列表
		return list;
	}

	/**
	 * 查询属性名等值的实体列表
	 * @param entityClass 要查询的实体
	 * @param property 属性名
	 * @param value 属性值
	 * @param pager 分页Bean
	 * @return 数据列表
	 */
	public <E extends Entity> List<E> eq(Class<E> entityClass, String property, Object value, Pagination pager) {
		// 获得数据列表
		List<E> list = eq(entityClass, property, value, getFirstResult(pager), getMaxResults(pager));
		// 判断列表
		if (EmptyUtil.isEmpty(list)) {
			// 为空 设置总数为 0
			pager.setTotalSize(0);
		} else {
			// 不为空 查询出总数
			pager.setTotalSize(count(entityClass, property, value));
		}
		// 返回列表
		return list;
	}

	/**
	 * 查询属性名含有列表的实体列表
	 * @param entityClass 要查询的实体
	 * @param property 属性名
	 * @param values 属性值
	 * @param firstResult 重第几条开始查询
	 * @param maxResults 一共查回多少条
	 * @return 数据列表
	 */
	public <E extends Entity> List<E> in(Class<E> entityClass, String property, List<?> values, int firstResult, int maxResults) {
		return (List<E>) (EmptyUtil.isEmpty(values) ? Lists.getList() : dao.in(entityClass, property, values, firstResult, maxResults));
	}

	/**
	 * 查询属性名含有列表的实体列表
	 * @param entityClass 要查询的实体
	 * @param property 属性名
	 * @param values 属性值
	 * @param firstResult 重第几条开始查询
	 * @param maxResults 一共查回多少条
	 * @return 数据列表
	 */
	public <E extends Entity> List<E> in(Class<E> entityClass, String property, List<Object> values, Map<String, Object> orders, int firstResult, int maxResults) {
		return dao.in(entityClass, property, values, orders, firstResult, maxResults);
	}

	/**
	 * 查询属性名等值的实体列表
	 * @param entityClass 要查询的实体
	 * @param property 属性名
	 * @param values 属性值
	 * @param pager 分页Bean
	 * @return 数据列表
	 */
	public <E extends Entity> List<E> in(Class<E> entityClass, String property, List<Object> values, Map<String, Object> orders, Pagination pager) {
		// 获得数据列表
		List<E> list = in(entityClass, property, values, orders, getFirstResult(pager), getMaxResults(pager));
		// 判断列表
		if (EmptyUtil.isEmpty(list)) {
			// 为空 设置总数为 0
			pager.setTotalSize(0);
		} else {
			// 不为空 查询出总数
			pager.setTotalSize(count(entityClass, property, values));
		}
		// 返回列表
		return list;
	}

	/**
	 * 查询属性名含有列表的实体列表
	 * @param entityClass 要查询的实体
	 * @param parames 参数map
	 * @param firstResult 重第几条开始查询
	 * @param maxResults 一共查回多少条
	 * @return 数据列表
	 */
	public <E extends Entity> List<E> in(Class<E> entityClass, Map<String, List<Object>> parames, int firstResult, int maxResults) {
		return dao.in(entityClass, parames, firstResult, maxResults);
	}

	/**
	 * 查询字段在lo到hi之间的实体
	 * @param entity 查询实体
	 * @param property 字段名
	 * @param lo 开始条件
	 * @param hi 结束条件
	 * @param firstResult 重第几条开始查询
	 * @param maxResults 一共查回多少条
	 * @return 返回结果列表
	 */
	public <E extends Entity> List<E> between(E entity, String property, Object lo, Object hi, int firstResult, int maxResults) {
		return dao.between(entity, property, lo, hi, firstResult, maxResults);
	}

	/**
	 * 查询字段在lo到hi之间的实体
	 * @param entity 查询实体
	 * @param property 字段名
	 * @param lo 开始条件
	 * @param hi 结束条件
	 * @param page 分页实体
	 * @return 返回结果列表
	 */
	public <E extends Entity> List<E> between(E entity, String property, Object lo, Object hi, Pagination page) {
		// 获得数据列表
		List<E> list = between(entity, property, lo, hi, getFirstResult(page), getMaxResults(page));
		// 判断列表
		if (EmptyUtil.isEmpty(list)) {
			// 为空 设置总数为 0
			page.setTotalSize(0);
		} else {
			// 不为空 查询出总数
			page.setTotalSize(count(entity, property, lo, hi));
		}
		// 返回列表
		return list;
	}

	/**
	 * 查询字段在lo到hi之间的实体
	 * @param entity 查询实体
	 * @param orders 排序参数
	 * @param page 分页实体
	 * @return 返回结果列表
	 */
	public <E extends Entity> List<E> order(E entity, Map<String, Object> orders, Pagination page) {
		// 获得数据列表
		List<E> list = order(entity, orders, getFirstResult(page), getMaxResults(page));
		// 判断列表
		if (EmptyUtil.isEmpty(list)) {
			// 为空 设置总数为 0
			page.setTotalSize(0);
		} else {
			// 不为空 查询出总数
			page.setTotalSize(count(entity));
		}
		// 返回列表
		return list;
	}

	/**
	 * 查询字段在lo到hi之间的实体
	 * @param entity 查询实体
	 * @param orders 排序参数
	 * @param page 分页实体
	 * @return 返回结果列表
	 */
	public <E extends Entity> List<E> order(Class<E> entity, Map<String, Object> orders, Pagination page) {
		// 获得数据列表
		List<E> list = order(entity, orders, getFirstResult(page), getMaxResults(page));
		// 判断列表
		if (EmptyUtil.isEmpty(list)) {
			// 为空 设置总数为 0
			page.setTotalSize(0);
		} else {
			// 不为空 查询出总数
			page.setTotalSize(count(entity));
		}
		// 返回列表
		return list;
	}

	/**
	 * 查询属性名等值的实体列表
	 * @param entity 实体类
	 * @param orders 排序参数
	 * @param firstResult 重第几条开始查询
	 * @param maxResults 一共查回多少条
	 * @return 数据列表
	 */
	public <E extends Entity> List<E> order(E entity, Map<String, Object> orders, int firstResult, int maxResults) {
		return dao.order(entity, orders, firstResult, maxResults);
	}

	/**
	 * 查询属性名等值的实体列表
	 * @param entityClass 实体类
	 * @param orders 排序参数
	 * @param firstResult 重第几条开始查询
	 * @param maxResults 一共查回多少条
	 * @return 数据列表
	 */
	public <E extends Entity> List<E> order(Class<E> entityClass, Map<String, Object> orders, int firstResult, int maxResults) {
		return dao.order(entityClass, orders, firstResult, maxResults);
	}

	/**
	 * 使用索引查询
	 * @param entityClass 实体类
	 * @param property 属性名
	 * @param value 属性值
	 * @param pager 分页Bean
	 * @return 数据列表
	 */
	public <E extends Entity> List<E> search(Class<E> entityClass, String property, Object value, Pagination pager) {
		// 获得数据列表
		List<E> list = search(entityClass, property, value, getFirstResult(pager), getMaxResults(pager));
		// 判断列表
		if (EmptyUtil.isEmpty(list)) {
			// 为空 设置总数为 0
			pager.setTotalSize(0);
		} else {
			// 不为空 查询出总数
			pager.setTotalSize(count(entityClass, property, value));
		}
		// 返回列表
		return list;
	}

	/**
	 * 使用索引查询
	 * @param entityClass 实体类
	 * @param property 属性名
	 * @param value 属性值
	 * @param firstResult 重第几条开始查询
	 * @param maxResults 一共查回多少条
	 * @return 数据列表
	 */
	public <E extends Entity> List<E> search(Class<E> entityClass, String property, Object value, int firstResult, int maxResults) {
		return dao.search(entityClass, property, value, firstResult, maxResults);
	}

	/**
	 * 使用索引查询
	 * @param entity 实体
	 * @param firstResult 重第几条开始查询
	 * @param maxResults 一共查回多少条
	 * @return 数据列表
	 */
	public <E> List<E> search(E entity, int firstResult, int maxResults) {
		return dao.search(entity, firstResult, maxResults);
	}

	/**
	 * 使用索引查询
	 * @param entityClass 实体类
	 * @param property 属性名
	 * @param value 属性值
	 * @param pager 分页Bean
	 * @return 数据列表
	 */
	public <E extends Entity> List<E> search(E entity, Pagination pager) {
		// 获得数据列表
		List<E> list = search(entity, getFirstResult(pager), getMaxResults(pager));
		// 判断列表
		if (EmptyUtil.isEmpty(list)) {
			// 为空 设置总数为 0
			pager.setTotalSize(0);
		} else {
			// 不为空 查询出总数
			pager.setTotalSize(count(entity));
		}
		// 返回列表
		return list;
	}

	/**
	 * 根据实体条件查询数量
	 * @param entity 实体
	 * @return 数量
	 */
	public <E extends Entity> int count(E entity) {
		return getCache(entity).isEmpty() ? list(entity, -1, -1).size() : dao.count(entity);
	}

	/**
	 * 根据实体条件查询数量
	 * @param entityClass 实体
	 * @return 数量
	 */
	public <E extends Entity> int count(Class<E> entityClass) {
		return isCache(entityClass) ? list(entityClass, -1, -1).size() : dao.count(entityClass);
	}

	/**
	 * 根据实体条件查询数量
	 * @param entityClass 实体类
	 * @param property 属性名
	 * @param value 属性值
	 * @return 数量
	 */
	public <E extends Entity> int count(Class<E> entityClass, String property, Object value) {
		// return isCache(entityClass) ? eq(entityClass, property, value, -1, -1).size() :
		// dao.count(entityClass, property, value);
		return dao.count(entityClass, property, value);
	}

	/**
	 * 根据实体条件查询数量
	 * @param entityClass 实体类
	 * @param property 属性名
	 * @param values 属性值
	 * @return 数量
	 */
	public <E extends Entity> int count(Class<E> entityClass, String property, List<Object> values) {
		// return isCache(entityClass) ? in(entityClass, property, values, -1, -1).size() :
		// dao.count(entityClass, property, values);
		return dao.count(entityClass, property, values);
	}

	/**
	 * 查询字段在lo到hi之间的实体总数
	 * @param entity 查询实体
	 * @param property 字段名
	 * @param lo 开始条件
	 * @param hi 结束条件
	 * @return 返回结果列表
	 */
	public <E extends Entity> int count(E entity, String property, Object lo, Object hi) {
		return dao.count(entity, property, lo, hi);
	}

	/**
	 * 获得指定属性下的所有实体 包含指定属性
	 * @param entity 类名称
	 * @param property 属性名
	 * @param values 属性值
	 * @return 下级所有分类列表
	 */
	public <E extends Entity> List<E> next(Class<E> entity, String property, Object value) {
		// 声明列表
		List<E> list = eq(entity, property, value, -1, -1);
		// 声明返回列表
		List<E> ls = Lists.getList(list.size());
		// 添加指定实体
		ls.add(get(entity, (Serializable) value));
		// 循环添加
		for (E obj : Lists.getList(list)) {
			ls.addAll(next(entity, property, obj.getKey()));
		}
		// 返回列表
		return ls;
	}

	/**
	 * 获得指定属性上的所有实体 包含指定属性
	 * @param entity 类名称
	 * @param property 属性名
	 * @param pk 主键
	 * @return 上级所有分类列表
	 */
	public <E extends Entity> List<E> prev(Class<E> entity, String property, Serializable pk) {
		// 声明列表
		List<E> list = Lists.getList();
		// 获得相当对象
		E obj = get(entity, pk);
		// 对象不为空
		if (obj != null) {
			list.addAll(prev(entity, property, (Serializable) BeanUtil.getFieldValue(obj, property)));
			// 添加对象
			list.add(obj);
		}
		// 返回列表
		return list;
	}

	/**
	 * 加载所有缓存
	 */
	public void cache() {
		// 循环加载所以缓存
		for (Class<? extends Entity> c : caches.keySet()) {
			// 加载缓存
			load(c);
		}
	}

	/**
	 * 加载所有数据
	 */
	public void load() {
		// 循环加载所以缓存
		for (Class<? extends Entity> c : context.getEntitys()) {
			// 加载缓存
			load(c);
		}
	}

	/**
	 * 加载指定类的所有数据
	 * @param entityClass 实体类
	 */
	public <E extends Entity> void load(Class<E> entityClass) {
		// 获得缓存
		Cache<E> cache = getCache(entityClass);
		// 获得所有数据列表
		List<E> beans = dao.list(entityClass, -1, -1);
		// 判断有缓存
		if (cache.isValid()) {
			cache.clear();
			cache.set(beans);
			loads.put(entityClass, true);
		}
	}

	/**
	 * 根据实体类获得缓存
	 * @param entityClass 实体类
	 * @return 缓存
	 */
	public <E extends Entity> Cache<E> getCache(Class<E> entityClass) {
		// 获得缓存
		Cache<E> cache = (Cache<E>) caches.get(entityClass);
		// 判断缓存为空
		if (cache == null) {
			// 返回空缓存
			return (Cache<E>) empty;
		} else {
			// 如果缓存为空
			if (cache.isValid() && cache.isEmpty() && !loads.get(entityClass)) {
				cache.set(dao.list(entityClass, -1, -1));
				loads.put(entityClass, true);
			}
			// 返回缓存
			return cache;
		}
	}

	/**
	 * 根据实体类获得缓存
	 * @param entity 实体类
	 * @return 缓存
	 */
	private <E extends Entity> Cache<E> getCache(E entity) {
		return getCache((Class<E>) entity.getClass());
	}

	/**
	 * 根据实体类获得缓存
	 * @param entitys 实体类
	 * @return 缓存
	 */
	private <E extends Entity> Cache<E> getCache(List<E> entitys) {
		return getCache(entitys.get(0));
	}

	/**
	 * 是否使用缓存
	 * @param entityClass 实体类
	 * @return 是否使用缓存
	 */
	private <E extends Entity> boolean isCache(Class<E> entityClass) {
		return getCache(entityClass).isValid();
	}

	/**
	 * 获得最大结果数
	 * @param page 分页Bean
	 * @return 最大结果数
	 */
	private int getMaxResults(Pagination page) {
		return EmptyUtil.isEmpty(page) ? -1 : page.getPageSize();
	}

	/**
	 * 获得从第N条开始返回结果
	 * @param page 分页Bean
	 * @return 从第N条开始返回结果
	 */
	private int getFirstResult(Pagination page) {
		return EmptyUtil.isEmpty(page) ? -1 : (page.getCurrentPage() - 1) * page.getPageSize();
	}

	/**
	 * 处理主键
	 * @param keys 主键
	 * @return
	 */
	private Serializable[] keys(Serializable... keys) {
		// 返回的主键key
		Serializable[] pks = new Serializable[keys.length];
		for (int i = 0; i < keys.length; i++) {
			pks[i] = key(keys[i]);
		}
		return pks;
	}

	/**
	 * 处理主键
	 * @param key 主键
	 * @return
	 */
	private Serializable key(Serializable key) {
		return Conversion.toInt(key) > 0 ? Conversion.toInt(key) : Conversion.toString(key);
	}

	/**
	 * 调用每个元素的toString()方法
	 * @param list
	 * @return
	 */
	private static <E> List<E> toString(List<E> list) {
		// 循环调用
		for (E e : list) {
			e.toString();
		}
		// 返回list
		return list;
	}
}
