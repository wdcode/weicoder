package com.weicoder.dao.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.weicoder.dao.bean.Pagination;
import com.weicoder.common.lang.Conversion;
import com.weicoder.common.lang.Lists;
import com.weicoder.common.util.BeanUtil;
import com.weicoder.common.util.EmptyUtil;
import com.weicoder.dao.Dao;
import com.weicoder.dao.hibernate.HibernateDao;

/**
 * 超级通用业务hibernate实现
 * @author WD
 */
public final class SuperService {
	/** 本类的单例对象 */
	public final static SuperService	SERVICE	= new SuperService();
	// hibernate dao
	private Dao							dao		= new HibernateDao();

	private SuperService() {}

	/**
	 * 添加
	 * @param entity 实体
	 * @return ID
	 */
	public <E> E insert(E entity) {
		return dao.insert(entity);
	}

	/**
	 * 添加
	 * @param entitys 实体
	 * @return ID
	 */
	public <E> List<E> insert(List<E> entitys) {
		return dao.insert(entitys);
	}

	/**
	 * 更新
	 * @param entity 实体
	 * @return 是否成功
	 */
	public <E> E update(E entity) {
		return dao.update(entity);
	}

	/**
	 * 更新
	 * @param entitys 实体
	 * @return 是否成功
	 */
	public <E> List<E> update(List<E> entitys) {
		return dao.update(entitys);
	}

	/**
	 * 添加或更新
	 * @param entitys 实体
	 * @return 影响行数
	 */
	public <E> E insertOrUpdate(E entity) {
		return dao.insertOrUpdate(entity);
	}

	/**
	 * 添加或更新
	 * @param entitys 实体
	 * @return 影响行数
	 */
	public <E> List<E> insertOrUpdate(List<E> entitys) {
		return dao.insertOrUpdate(entitys);
	}

	/**
	 * 删除
	 * @param list 实体列表
	 * @return 是否成功
	 */
	public <E> List<E> delete(E entity) {
		// 查询出符合删除实体列表
		List<E> list = list(entity, -1, -1);
		// 删除列表为空
		if (EmptyUtil.isEmpty(list)) {
			return Lists.emptyList();
		}
		// 删除
		return dao.delete(list);
	}

	/**
	 * 删除
	 * @param list 实体列表
	 * @return 是否成功
	 */
	public <E> List<E> delete(List<E> entitys) {
		return dao.delete(entitys);
	}

	/**
	 * 根据ID数组删除 完全删除
	 * @param entity 要查询的实体
	 * @param pk 主键数组
	 * @return 是否成功
	 */
	public <E> List<E> delete(Class<E> entity, Serializable... pks) {
		return delete(gets(entity, pks));
	}

	/**
	 * 根据ID 获得实体
	 * @param entityClass 要查询的实体
	 * @param pk 主键
	 * @return 实体
	 */
	public <E> E get(Class<E> entityClass, Serializable pk) {
		return dao.get(entityClass, pk);
	}

	/**
	 * 根据ID 获得实体
	 * @param entityClass 要查询的实体
	 * @param pk 主键
	 * @return 实体
	 */
	public <E> List<E> gets(Class<E> entityClass, Serializable... pks) {
		return dao.gets(entityClass, pks);
	}

	/**
	 * 根据传入的条件，返回唯一的实体 如果有多个返回第一个实体
	 * @param entity 实体,
	 * @return 实体
	 */
	public <E> E get(E entity) {
		return dao.get(entity);
	}

	/**
	 * 获得持久化对象
	 * @param entityClass 要查询的实体
	 * @param property 属性名
	 * @param value 属性值
	 * @return 要获得的持久化对象，如果不存在返回null
	 */
	public <E> E get(Class<E> entityClass, String property, Object value) {
		return dao.get(entityClass, property, value);
	}

	/**
	 * 获得持久化对象
	 * @param entityClass 实体类
	 * @param map 属性键值
	 * @return 要获得的持久化对象，如果不存在返回null
	 */
	public <E> E get(Class<E> entityClass, Map<String, Object> map) {
		return dao.get(entityClass, map);
	}

	/**
	 * 查询全部数据
	 * @param entityClass 要查询的实体
	 * @return 全部实体
	 */
	public <E> List<E> all(Class<E> entityClass) {
		return list(entityClass, -1, -1);
	}

	/**
	 * 查询指定条数
	 * @param entityClass 要查询的实体
	 * @param firstResult 开始查询的条数
	 * @param maxResults 最多查询多少条
	 * @return 全部实体
	 */
	public <E> List<E> list(Class<E> entityClass, int firstResult, int maxResults) {
		return dao.list(entityClass, firstResult, maxResults);

	}

	/**
	 * 获得查询的对象实体列表 分页功能
	 * @param entityClass 要查询的实体
	 * @param page 分页Bean
	 * @return 返回这个对象的列表
	 */
	public <E> List<E> list(Class<E> entityClass, Pagination page) {
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
	public <E> List<E> list(E entity, int firstResult, int maxResults) {
		return dao.list(entity, firstResult, maxResults);
	}

	/**
	 * 获得查询的对象实体列表 分页功能
	 * @param entity 需要获得的对象，会查询出实体中封装的相等的条件
	 * @param page 分页Bean
	 * @return 返回这个对象的列表
	 */
	public <E> List<E> list(E entity, Pagination page) {
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
	public <E> List<E> like(Class<E> entityClass, String property, Object value, int firstResult, int maxResults) {
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
	public <E> List<E> eq(Class<E> entityClass, Map<String, Object> map, int firstResult, int maxResults) {
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
	public <E> List<E> eq(Class<E> entityClass, String property, Object value, int firstResult, int maxResults) {
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
	public <E> List<E> in(Class<E> entityClass, String property, List<Object> values, Pagination pager) {
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
	public <E> List<E> eq(Class<E> entityClass, String property, Object value, Pagination pager) {
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
	@SuppressWarnings("unchecked")
	public <E> List<E> in(Class<E> entityClass, String property, List<?> values, int firstResult, int maxResults) {
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
	public <E> List<E> in(Class<E> entityClass, String property, List<Object> values, Map<String, Object> orders, int firstResult, int maxResults) {
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
	public <E> List<E> in(Class<E> entityClass, String property, List<Object> values, Map<String, Object> orders, Pagination pager) {
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
	public <E> List<E> in(Class<E> entityClass, Map<String, List<Object>> parames, int firstResult, int maxResults) {
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
	public <E> List<E> between(E entity, String property, Object lo, Object hi, int firstResult, int maxResults) {
		return dao.between(entity, property, lo, hi, firstResult, maxResults);
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
	public <E> List<E> between(Class<E> entity, String property, Object lo, Object hi, int firstResult, int maxResults) {
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
	public <E> List<E> between(E entity, String property, Object lo, Object hi, Pagination page) {
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
	public <E> List<E> order(E entity, Map<String, Object> orders, Pagination page) {
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
	public <E> List<E> order(Class<E> entity, Map<String, Object> orders, Pagination page) {
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
	public <E> List<E> order(E entity, Map<String, Object> orders, int firstResult, int maxResults) {
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
	public <E> List<E> order(Class<E> entityClass, Map<String, Object> orders, int firstResult, int maxResults) {
		return dao.order(entityClass, orders, firstResult, maxResults);
	}

	/**
	 * 根据实体条件查询数量
	 * @param entity 实体
	 * @return 数量
	 */
	public <E> int count(E entity) {
		return dao.count(entity);
	}

	/**
	 * 根据实体条件查询数量
	 * @param entityClass 实体
	 * @return 数量
	 */
	public <E> int count(Class<E> entityClass) {
		return dao.count(entityClass);
	}

	/**
	 * 根据实体条件查询数量
	 * @param entityClass 实体类
	 * @param property 属性名
	 * @param value 属性值
	 * @return 数量
	 */
	public <E> int count(Class<E> entityClass, String property, Object value) {
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
	public <E> int count(Class<E> entityClass, String property, List<Object> values) {
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
	public <E> int count(E entity, String property, Object lo, Object hi) {
		return dao.count(entity, property, lo, hi);
	}

	/**
	 * 获得指定属性下的所有实体 包含指定属性
	 * @param entity 类名称
	 * @param property 属性名
	 * @param values 属性值
	 * @return 下级所有分类列表
	 */
	public <E> List<E> next(Class<E> entity, String property, Object value) {
		// 声明列表
		List<E> list = eq(entity, property, value, -1, -1);
		// 声明返回列表
		List<E> ls = Lists.getList(list.size());
		// 添加指定实体
		ls.add(get(entity, (Serializable) value));
		// 循环添加
		for (E obj : Lists.getList(list)) {
			ls.addAll(next(entity, property, BeanUtil.getFieldValue(obj, property)));
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
	public <E> List<E> prev(Class<E> entity, String property, Serializable pk) {
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
}
