package com.weicoder.hibernate;
 
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria; 
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions; 

import com.weicoder.hibernate.base.BaseHibernateDao; 
import com.weicoder.common.U;
import com.weicoder.common.W; 

/**
 * Hibernate接口
 * 
 * @author WD
 */
public final class HibernateDao extends BaseHibernateDao { 
	@Override
	public <E> E get(Class<E> entity, String property, Object value) {
		return getCriteria(entity, DetachedCriteria.forClass(entity).add(Restrictions.eq(property, value)));
	}

	@Override
	public <E> E get(Class<E> entity, Map<String, Object> map) {
		return getCriteria(entity, DetachedCriteria.forClass(entity).add(Restrictions.allEq(map)));
	}

	@Override
	@SuppressWarnings("unchecked")
	public <E> List<E> list(final E entity, final int firstResult, final int maxResults) {
		return execute(entity.getClass(), session -> {
			// 获得Criteria
			Criteria criteria = DetachedCriteria.forClass(entity.getClass()).getExecutableCriteria(session);
			// 添加实体参数
			criteria.add(Example.create(entity));
			// 开始结果大于等于0
			if (firstResult >= 0)
				criteria.setFirstResult(firstResult);
			// 最大结果大于零
			if (maxResults > 0)
				criteria.setMaxResults(maxResults);
			// 返回查询结果
			return criteria.list();
		});
	}

	@Override
	public <E> List<E> list(Class<E> entityClass, int firstResult, int maxResults) {
		return queryCriteria(entityClass, DetachedCriteria.forClass(entityClass), firstResult, maxResults);
	}

	@Override
	public <E> List<E> eq(Class<E> entityClass, String property, Object value, int firstResult, int maxResults) {
		return queryCriteria(entityClass, DetachedCriteria.forClass(entityClass).add(Restrictions.eq(property, value)), firstResult, maxResults);
	}

	@Override
	public <E> List<E> gt(Class<E> entityClass, String property, Object value, int firstResult, int maxResults) {
		return queryCriteria(entityClass, DetachedCriteria.forClass(entityClass).add(Restrictions.gt(property, value)), firstResult, maxResults);
	}

	@Override
	public <E> List<E> ge(Class<E> entityClass, String property, Object value, int firstResult, int maxResults) {
		return queryCriteria(entityClass, DetachedCriteria.forClass(entityClass).add(Restrictions.ge(property, value)), firstResult, maxResults);
	}

	@Override
	public <E> List<E> lt(Class<E> entityClass, String property, Object value, int firstResult, int maxResults) {
		return queryCriteria(entityClass, DetachedCriteria.forClass(entityClass).add(Restrictions.lt(property, value)), firstResult, maxResults);
	}

	@Override
	public <E> List<E> le(Class<E> entityClass, String property, Object value, int firstResult, int maxResults) {
		return queryCriteria(entityClass, DetachedCriteria.forClass(entityClass).add(Restrictions.le(property, value)), firstResult, maxResults);
	}

	@Override
	public <E> List<E> eq(Class<E> entityClass, Map<String, Object> map, int firstResult, int maxResults) {
		return queryCriteria(entityClass, DetachedCriteria.forClass(entityClass).add(Restrictions.allEq(map)), firstResult, maxResults);
	}

	@Override
	public <E> List<E> like(Class<E> entityClass, String property, Object value, int firstResult, int maxResults) {
		return queryCriteria(entityClass, DetachedCriteria.forClass(entityClass).add(Restrictions.like(property, value)), firstResult, maxResults);
	}

	@Override
	public <E> List<E> order(E entity, Map<String, Object> orders, int firstResult, int maxResults) {
		return queryCriteria(entity.getClass(), getOrder(entity.getClass(), orders).add(Example.create(entity)), firstResult, maxResults);
	}

	@Override
	public <E> List<E> order(Class<E> entityClass, Map<String, Object> orders, int firstResult, int maxResults) {
		return queryCriteria(entityClass, getOrder(entityClass, orders), firstResult, maxResults);
	}

	@Override
	public <E> List<E> in(Class<E> entityClass, String property, List<Object> values, int firstResult, int maxResults) {
		return queryCriteria(entityClass, DetachedCriteria.forClass(entityClass).add(Restrictions.in(property, values)), firstResult, maxResults);
	}

	@Override
	public <E> List<E> in(Class<E> entityClass, String property, List<Object> values, Map<String, Object> orders, int firstResult, int maxResults) {
		return queryCriteria(entityClass, getOrder(entityClass, orders).add(Restrictions.in(property, values)), firstResult, maxResults);
	}

	@Override
	public <E> List<E> in(Class<E> entityClass, Map<String, List<Object>> parames, int firstResult, int maxResults) {
		// 获得Conjunction AND 条件
		Conjunction conj = Restrictions.conjunction();
		// 循环赋值in
		parames.forEach((k, v) -> conj.add(Restrictions.in(k, v)));
		// 查询结果
		return queryCriteria(entityClass, DetachedCriteria.forClass(entityClass).add(conj), firstResult, maxResults);
	}

	@Override
	public <E> List<E> between(E entity, String property, Object lo, Object hi, int firstResult, int maxResults) {
		return queryCriteria(entity.getClass(), getBetween(entity, property, lo, hi), firstResult, maxResults);
	}

	@Override
	public <E> List<E> between(Class<E> entity, String property, Object lo, Object hi, int firstResult, int maxResults) {
		return queryCriteria(entity, getBetween(entity, property, lo, hi), firstResult, maxResults);
	}
 
	@Override
	public int count(final Class<?> entityClass, final String property, final Object value) {
		return execute(entityClass, session -> {
			// 创建查询条件
			Criteria criteria = DetachedCriteria.forClass(entityClass).getExecutableCriteria(session);
			// 设置参数
			if (U.E.isNotEmpty(property) && U.E.isNotEmpty(value))
				criteria.add(Restrictions.eq(property, value));
			// 设置获得总行数
			criteria.setProjection(Projections.rowCount());
			// 返回结果
			return W.C.toInt(criteria.uniqueResult());
		});
	}

	@Override
	public int count(final Class<?> entityClass, final Map<String, Object> map) {
		return execute(entityClass, session -> {
			// 创建查询条件
			Criteria criteria = DetachedCriteria.forClass(entityClass).getExecutableCriteria(session);
			// 判断属性名不为空
			if (U.E.isNotEmpty(map))
				criteria.add(Restrictions.allEq(map));
			// 设置获得总行数
			criteria.setProjection(Projections.rowCount());
			// 返回结果
			return W.C.toInt(criteria.uniqueResult());
		});
	}

	@Override
	public int count(final Object entity) {
		return execute(entity.getClass(), session -> {
			// 创建查询条件
			Criteria criteria = DetachedCriteria.forClass(entity.getClass()).getExecutableCriteria(session);
			// 添加实体对象
			criteria.add(Example.create(entity));
			// 设置获得总行数
			criteria.setProjection(Projections.rowCount());
			// 返回结果
			return W.C.toInt(criteria.uniqueResult());
		});
	}

	@Override
	public int count(Object entity, String property, Object lo, Object hi) {
		return count(entity.getClass(), getBetween(entity, property, lo, hi));
	}

	

	/**
	 * 根据DetachedCriteria 查询条件 查询一个结果
	 * 
	 * @param  entityClass 类
	 * @param  criteria    查询条件
	 * @param  <E>         泛型
	 * @return             返回结果列表
	 */
	private <E> E getCriteria(Class<?> entityClass, final DetachedCriteria criteria) {
		// 获得结果
		List<E> list = queryCriteria(entityClass, criteria, 0, 1);
		// 返回结果
		return U.E.isEmpty(list) ? null : list.get(0);
	}

	/**
	 * 根据DetachedCriteria 查询条件 查询 支持分页
	 * 
	 * @param  entityClass 实体类
	 * @param  criteria    查询条件
	 * @param  firstResult 开始查询的条数
	 * @param  maxResults  最多查询多少条
	 * @param  <E>         泛型
	 * @return             返回结果列表
	 */
	@SuppressWarnings("unchecked")
	private <E> List<E> queryCriteria(Class<?> entityClass, final DetachedCriteria criteria, final int firstResult, final int maxResults) {
		return execute(entityClass, session -> {
			// 获得Criteria
			Criteria executableCriteria = criteria.getExecutableCriteria(session);
			// 判断开始结果
			if (firstResult >= 0)
				executableCriteria.setFirstResult(firstResult);
			// 判断最大结果
			if (maxResults > 0)
				executableCriteria.setMaxResults(maxResults);
			// 返回查询结果
			return executableCriteria.list();
		});
	}

	/**
	 * 根据DetachedCriteria 查询条件 查询总行数
	 * 
	 * @param  entityClass 实体类
	 * @param  criteria    查询条件
	 * @return             返回结果列表 异常返回0
	 */
	private int count(Class<?> entityClass, final DetachedCriteria criteria) {
		return execute(entityClass, session -> W.C.toInt(criteria.getExecutableCriteria(session).setProjection(Projections.rowCount()).uniqueResult()));
	}

	/**
	 * queryByBetween使用 返回DetachedCriteria
	 * 
	 * @param  entity   查询实体
	 * @param  property 字段名
	 * @param  lo       开始条件
	 * @param  hi       结束条件
	 * @return          DetachedCriteria
	 */
	private DetachedCriteria getBetween(Object entity, String property, Object lo, Object hi) {
		// 获得criteria
		DetachedCriteria criteria = DetachedCriteria.forClass(entity.getClass());
		// 添加实体条件
		criteria.add(Example.create(entity));
		// 添加条件
		criteria.add(Restrictions.between(property, lo, hi));
		// 返回DetachedCriteria
		return criteria;
	}

	/**
	 * queryByBetween使用 返回DetachedCriteria
	 * 
	 * @param  entity   查询实体
	 * @param  property 字段名
	 * @param  lo       开始条件
	 * @param  hi       结束条件
	 * @param  <E>      泛型
	 * @return          DetachedCriteria
	 */
	private <E> DetachedCriteria getBetween(Class<E> entity, String property, Object lo, Object hi) {
		// 获得criteria
		DetachedCriteria criteria = DetachedCriteria.forClass(entity);
		// 添加条件
		criteria.add(Restrictions.between(property, lo, hi));
		// 返回DetachedCriteria
		return criteria;
	}

	/**
	 * 获得排序DetachedCriteria
	 * 
	 * @param  entityClass 实体类
	 * @param  orders      排序参数
	 * @return             DetachedCriteria
	 */
	private DetachedCriteria getOrder(Class<?> entityClass, Map<String, Object> orders) {
		// 获得DetachedCriteria
		DetachedCriteria criteria = DetachedCriteria.forClass(entityClass);
		// 循环排序
		orders.forEach((k, v) -> criteria.addOrder(W.C.toBoolean(v) ? Order.asc(k) : Order.desc(k)));
		// 返回DetachedCriteria
		return criteria;
	} 
}
