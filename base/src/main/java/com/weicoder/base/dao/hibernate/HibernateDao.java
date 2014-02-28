package com.weicoder.base.dao.hibernate;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.weicoder.base.context.Context;
import com.weicoder.base.dao.Dao;
import com.weicoder.base.dao.hibernate.search.HibernateSearch;
import com.weicoder.base.dao.hibernate.session.SessionFactorys;
import com.weicoder.common.lang.Conversion;
import com.weicoder.common.lang.Lists;
import com.weicoder.common.util.EmptyUtil;

/**
 * Hibernate接口
 * @author WD
 * @since JDK7
 * @version 1.0 2012-03-05
 */
@Repository
public final class HibernateDao implements Dao {
	// Context
	@Resource
	private Context			context;
	// Session工厂
	@Resource
	private SessionFactorys	factorys;
	// HibernateSearch
	@Autowired(required = false)
	private HibernateSearch	search;

	/**
	 * 使用索引查询
	 * @param entityClass 实体类
	 * @param property 属性名
	 * @param value 属性值
	 * @param firstResult 重第几条开始查询
	 * @param maxResults 一共查回多少条
	 * @return 数据列表
	 */
	public <E> List<E> search(final Class<E> entityClass, final String property, final Object value, final int firstResult, final int maxResults) {
		return execute(entityClass, new Callback<List<E>>() {
			public List<E> callback(Session session) {
				return search.search(session, entityClass, property, value, firstResult, maxResults);
			}
		});
	}

	/**
	 * 使用索引查询
	 * @param entity 实体
	 * @param firstResult 重第几条开始查询
	 * @param maxResults 一共查回多少条
	 * @return 数据列表
	 */
	public <E> List<E> search(final E entity, final int firstResult, final int maxResults) {
		return execute(entity.getClass(), new Callback<List<E>>() {
			public List<E> callback(Session session) {
				return search.search(session, entity, firstResult, maxResults);
			}
		});
	}

	/**
	 * 持久化对象，添加操作，此方法会向对应的数据库表中插入一条数据
	 * @param entity 对象实体
	 * @return 返回插入数据的唯一标识(主键) 出现异常返回0
	 */
	public <E> E insert(final E entity) {
		return insert(Lists.getList(entity)).get(0);
	}

	/**
	 * 持久化对象，添加操作，此方法会向对应的数据库表中插入一条数据
	 * @param entitys 对象实体
	 * @return 返回插入数据的唯一标识(主键) 出现异常返回0
	 */
	public <E> List<E> insert(final List<E> entitys) {
		return execute(entitys.get(0).getClass(), new Callback<List<E>>() {
			public List<E> callback(Session session) {
				// 循环添加
				for (E e : entitys) {
					session.save(e);
				}
				// 返回实体
				return entitys;
			}
		});
	}

	/**
	 * 持久化数据，锁表 更新表中一行数据
	 * @param entity 对象实体
	 * @return 是否成功
	 */
	public <E> E update(final E entity) {
		return update(Lists.getList(entity)).get(0);
	}

	/**
	 * 持久化数据，锁表 更新表中一行数据
	 * @param entity 对象实体
	 * @param lockMode 锁表的模式 具体参看 LockMode
	 * @return 是否成功
	 * @see org.hibernate.LockMode
	 */
	public <E> List<E> update(final List<E> entitys) {
		return execute(entitys.get(0).getClass(), new Callback<List<E>>() {
			public List<E> callback(Session session) {
				// 循环更新
				for (E e : entitys) {
					session.update(e);
				}
				// 返回实体
				return entitys;
			}
		});
	}

	/**
	 * 批量持久化对象 保存或更新，如果存在就更新，不存在就插入
	 * @param entity 需要持久化的对象
	 * @return 列表对象
	 */
	public <E> E insertOrUpdate(E entity) {
		return insertOrUpdate(Lists.getList(entity)).get(0);
	}

	/**
	 * 批量持久化对象 保存或更新，如果存在就更新，不存在就插入
	 * @param entitys 需要持久化的对象
	 * @return 列表对象
	 */
	public <E> List<E> insertOrUpdate(final List<E> entitys) {
		return execute(entitys.get(0).getClass(), new Callback<List<E>>() {
			public List<E> callback(Session session) {
				// 循环更新
				for (E e : entitys) {
					session.saveOrUpdate(e);
				}
				// 返回实体
				return entitys;
			}
		});
	}

	/**
	 * 持久化数据，删除表中多行数据
	 * @param entity 需要持久话对象的集合
	 * @return 是否成功
	 */
	public <E> E delete(E entity) {
		return delete(Lists.getList(entity)).get(0);
	}

	/**
	 * 持久化数据，删除表中多行数据
	 * @param entitys 需要持久话对象的集合
	 * @return 是否成功
	 */
	public <E> List<E> delete(final List<E> entitys) {
		return execute(entitys.get(0).getClass(), new Callback<List<E>>() {
			public List<E> callback(Session session) {
				// 循环更新
				for (E e : entitys) {
					session.delete(e);
				}
				// 返回实体
				return entitys;
			}
		});
	}

	/**
	 * 获得持久化对象
	 * @param entityClass 实体类
	 * @param id 持久化对象的唯一标识(主键)
	 * @param lockMode 锁表的模式 具体参看 org.hibernate.LockMode
	 * @return 要获得的持久化对象，异常返回null
	 */
	public <E> E get(final Class<E> entityClass, final Serializable pk) {
		// 验证pk是否为空
		if (EmptyUtil.isEmpty(pk)) {
			return null;
		}
		// 查找对象
		return execute(entityClass, new Callback<E>() {
			public E callback(Session session) {
				return (E) session.get(entityClass, pk);
			}
		});
	}

	@Override
	public <E> List<E> gets(final Class<E> entityClass, final Serializable... pks) {
		// 验证pk是否为空
		if (EmptyUtil.isEmpty(pks)) {
			return Lists.emptyList();
		}
		// 查找对象
		return execute(entityClass, new Callback<List<E>>() {
			public List<E> callback(Session session) {
				// 声明返回对象
				List<E> list = Lists.getList(pks.length);
				// 循环获得实体列表
				for (Serializable pk : pks) {
					list.add((E) session.get(entityClass, pk));
				}
				// 返回对象列表
				return list;
			}
		});
	}

	/**
	 * 获得持久化对象 如果没有查询到对象 返回null
	 * @param entity 对象实体
	 * @return 持久化对象
	 */
	public <E> E get(final E entity) {
		// 获得结果
		List<E> list = list(entity, 0, 1);
		// 返回结果
		return EmptyUtil.isEmpty(list) ? null : list.get(0);
	}

	/**
	 * 获得持久化对象
	 * @param entity 实体类
	 * @param property 属性名
	 * @param value 属性值
	 * @return 要获得的持久化对象，如果不存在返回null
	 */
	public <E> E get(Class<E> entity, String property, Object value) {
		return getCriteria(entity, DetachedCriteria.forClass(entity).add(Restrictions.eq(property, value)));
	}

	/**
	 * 获得持久化对象
	 * @param entity 实体类
	 * @param map 属性键值
	 * @return 要获得的持久化对象，如果不存在返回null
	 */
	public <E> E get(Class<E> entity, Map<String, Object> map) {
		return getCriteria(entity, DetachedCriteria.forClass(entity).add(Restrictions.allEq(map)));
	}

	/**
	 * 获得查询的对象实体列表
	 * @param entity 需要获得的对象，会查询出实体中封装的相等的条件
	 * @param firstResult 重第几条开始查询
	 * @param maxResults 一共查回多少条
	 * @return 数据列表 异常返回 Collections.emptyList()
	 */

	public <E> List<E> list(final E entity, final int firstResult, final int maxResults) {
		return execute(entity.getClass(), new Callback<List<E>>() {
			public List<E> callback(Session session) {
				// 获得Criteria
				Criteria criteria = session.createCriteria(entity.getClass());
				// 添加实体参数
				criteria.add(Example.create(entity));
				// 开始结果大于等于0
				if (firstResult >= 0) {
					criteria.setFirstResult(firstResult);
				}
				// 最大结果大于零
				if (maxResults > 0) {
					criteria.setMaxResults(maxResults);
				}
				// 返回查询结果
				return criteria.list();
			}
		});
	}

	/**
	 * 查询所有数据
	 * @param entityClass 实体类
	 * @param firstResult 开始查询的条数
	 * @param maxResults 最多查询多少条
	 * @return 返回结果列表
	 */
	public <E> List<E> list(Class<E> entityClass, int firstResult, int maxResults) {
		return queryCriteria(entityClass, DetachedCriteria.forClass(entityClass), firstResult, maxResults);
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
	public <E> List<E> eq(Class<E> entityClass, String property, Object value, int firstResult, int maxResults) {
		return queryCriteria(entityClass, DetachedCriteria.forClass(entityClass).add(Restrictions.eq(property, value)), firstResult, maxResults);
	}

	/**
	 * 查询属性名等值的实体列表
	 * @param entityClass 实体类
	 * @param map 属性
	 * @param firstResult 重第几条开始查询
	 * @param maxResults 一共查回多少条
	 * @return 数据列表
	 */
	public <E> List<E> eq(Class<E> entityClass, Map<String, Object> map, int firstResult, int maxResults) {
		return queryCriteria(entityClass, DetachedCriteria.forClass(entityClass).add(Restrictions.allEq(map)), firstResult, maxResults);
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
		return queryCriteria(entityClass, DetachedCriteria.forClass(entityClass).add(Restrictions.like(property, value)), firstResult, maxResults);
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
		return queryCriteria(entity.getClass(), getOrder(entity.getClass(), orders).add(Example.create(entity)), firstResult, maxResults);
	}

	/**
	 * 查询属性名等值的实体列表
	 * @param entity 实体类
	 * @param orders 排序参数
	 * @param firstResult 重第几条开始查询
	 * @param maxResults 一共查回多少条
	 * @return 数据列表
	 */
	public <E> List<E> order(Class<E> entityClass, Map<String, Object> orders, int firstResult, int maxResults) {
		return queryCriteria(entityClass, getOrder(entityClass, orders), firstResult, maxResults);
	}

	/**
	 * 查询属性名含有列表的实体列表
	 * @param entityClass 实体类
	 * @param property 属性名
	 * @param values 属性值
	 * @param firstResult 重第几条开始查询
	 * @param maxResults 一共查回多少条
	 * @return 数据列表
	 */
	public <E> List<E> in(Class<E> entityClass, String property, List<?> values, int firstResult, int maxResults) {
		return queryCriteria(entityClass, DetachedCriteria.forClass(entityClass).add(Restrictions.in(property, values)), firstResult, maxResults);
	}

	/**
	 * 查询属性名含有列表的实体列表
	 * @param entityClass 实体类
	 * @param property 属性名
	 * @param values 属性值
	 * @param orders 排序
	 * @param firstResult 重第几条开始查询
	 * @param maxResults 一共查回多少条
	 * @return 数据列表
	 */
	public <E> List<E> in(Class<E> entityClass, String property, List<Object> values, Map<String, Object> orders, int firstResult, int maxResults) {
		return queryCriteria(entityClass, getOrder(entityClass, orders).add(Restrictions.in(property, values)), firstResult, maxResults);
	}

	/**
	 * 查询属性名含有列表的实体列表
	 * @param entityClass 实体类
	 * @param parames 参数map
	 * @param firstResult 重第几条开始查询
	 * @param maxResults 一共查回多少条
	 * @return 数据列表
	 */
	public <E> List<E> in(Class<E> entityClass, Map<String, List<Object>> parames, int firstResult, int maxResults) {
		// 获得Conjunction AND 条件
		Conjunction conj = Restrictions.conjunction();
		// 循环赋值in
		for (Map.Entry<String, List<Object>> e : parames.entrySet()) {
			conj.add(Restrictions.in(e.getKey(), e.getValue()));
		}
		// 查询结果
		return queryCriteria(entityClass, DetachedCriteria.forClass(entityClass).add(conj), firstResult, maxResults);
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
		return queryCriteria(entity.getClass(), getBetween(entity, property, lo, hi), firstResult, maxResults);
	}

	/**
	 * 获得查询的对象实体总数
	 * @param entityClass 实体类
	 * @return 对象实体总数 异常返回 0
	 */
	public int count(Class<?> entityClass) {
		return count(entityClass, null, null);
	}

	/**
	 * 根据实体条件查询数量
	 * @param entityClass 实体类
	 * @param property 属性名
	 * @param value 属性值
	 * @return 数量
	 */
	public int count(final Class<?> entityClass, final String property, final Object value) {
		return execute(entityClass, new Callback<Integer>() {
			public Integer callback(Session session) {
				// 创建查询条件
				Criteria criteria = session.createCriteria(entityClass);
				// 设置参数
				if (!EmptyUtil.isEmpty(property) && !EmptyUtil.isEmpty(value)) {
					criteria.add(Restrictions.eq(property, value));
				}
				// 设置获得总行数
				criteria.setProjection(Projections.rowCount());
				// 返回结果
				return Conversion.toInt(criteria.uniqueResult());
			}
		});
	}

	/**
	 * 获得查询的对象实体总数
	 * @param entityClass 实体类
	 * @param op 操作符
	 * @param map 属性键值
	 * @return 对象实体总数 异常返回 0
	 */
	public int count(final Class<?> entityClass, final Map<String, Object> map) {
		return execute(entityClass, new Callback<Integer>() {
			public Integer callback(Session session) {
				// 创建查询条件
				Criteria criteria = session.createCriteria(entityClass);
				// 判断属性名不为空
				if (!EmptyUtil.isEmpty(map)) {
					criteria.add(Restrictions.allEq(map));
				}
				// 设置获得总行数
				criteria.setProjection(Projections.rowCount());
				// 返回结果
				return Conversion.toInt(criteria.uniqueResult());
			}
		});
	}

	/**
	 * 获得查询的对象实体总数
	 * @param entity 需要获得的对象，会查询出实体中封装的相等的条件
	 * @return 对象实体总数 异常返回 0
	 */
	public int count(final Object entity) {
		return execute(entity.getClass(), new Callback<Integer>() {
			public Integer callback(Session session) {
				// 创建查询条件
				Criteria criteria = session.createCriteria(entity.getClass());
				// 添加实体对象
				criteria.add(Example.create(entity));
				// 设置获得总行数
				criteria.setProjection(Projections.rowCount());
				// 返回结果
				return Conversion.toInt(criteria.uniqueResult());
			}
		});
	}

	/**
	 * 查询字段在lo到hi之间的实体总数
	 * @param entity 查询实体
	 * @param property 字段名
	 * @param lo 开始条件
	 * @param hi 结束条件
	 * @return 返回结果列表
	 */
	public int count(Object entity, String property, Object lo, Object hi) {
		return count(entity.getClass(), getBetween(entity, property, lo, hi));
	}

	/**
	 * 执行非查询的SQL语言 使用 ? 做参数
	 * @param sql sql语句 不能是查询的
	 * @param values 参数值数组
	 * @return 返回影响的行数 异常返回-1
	 */
	public int execute(Class<?> entityClass, final String sql, final Object... values) {
		return execute(entityClass, new Callback<Integer>() {
			public Integer callback(Session session) {
				return setParameter(session.createSQLQuery(sql), Lists.getList(values), -1, -1).executeUpdate();
			}
		});
	}

	/**
	 * 根据SQL查询语句查询
	 * @param sql SQL查询语句 参数为?的语句
	 * @param values 参数列表
	 * @param firstResult 重第几条开始查询
	 * @param maxResults 一共查回多少条
	 * @return 返回结果列表
	 */
	public <E> List<E> query(Class<?> entityClass, final String sql, final List<Object> values, final int firstResult, final int maxResults) {
		return execute(entityClass, new Callback<List<E>>() {
			public List<E> callback(Session session) {
				return setParameter(session.createSQLQuery(sql), values, firstResult, maxResults).list();
			}
		});
	}

	/**
	 * 关闭Session
	 * @param session
	 */
	public void close() {
		factorys.close();
	}

	/**
	 * 设置Query 参数与结果集数量
	 * @param query Query查询器
	 * @param values 参数列表
	 * @param firstResult 第一条结果
	 * @param maxResults 最大结果
	 * @return Query
	 */
	private Query setParameter(Query query, List<Object> values, int firstResult, int maxResults) {
		// 是否有参数
		if (!EmptyUtil.isEmpty(values)) {
			// 循环参数
			for (int i = 0; i < values.size(); i++) {
				// 设置参数
				query.setParameter(i, values.get(i));
			}
		}
		// 开始结果大于等于0
		if (firstResult >= 0) {
			query.setFirstResult(firstResult);
		}
		// 最大结果大于零
		if (maxResults > 0) {
			query.setMaxResults(maxResults);
		}
		// 返回Query
		return query;
	}

	/**
	 * 根据DetachedCriteria 查询条件 查询一个结果
	 * @param criteria 查询条件
	 * @return 返回结果列表
	 */
	private <E> E getCriteria(Class<?> entityClass, final DetachedCriteria criteria) {
		// 获得结果
		List<E> list = queryCriteria(entityClass, criteria, 0, 1);
		// 返回结果
		return EmptyUtil.isEmpty(list) ? null : list.get(0);
	}

	/**
	 * 根据DetachedCriteria 查询条件 查询 支持分页
	 * @param criteria 查询条件
	 * @param firstResult 开始查询的条数
	 * @param maxResults 最多查询多少条
	 * @return 返回结果列表
	 */
	private <E> List<E> queryCriteria(Class<?> entityClass, final DetachedCriteria criteria, final int firstResult, final int maxResults) {
		return execute(entityClass, new Callback<List<E>>() {
			public List<E> callback(Session session) {
				// 获得Criteria
				Criteria executableCriteria = criteria.getExecutableCriteria(session);
				// 判断开始结果
				if (firstResult >= 0) {
					executableCriteria.setFirstResult(firstResult);
				}
				// 判断最大结果
				if (maxResults > 0) {
					executableCriteria.setMaxResults(maxResults);
				}
				// 返回查询结果
				return executableCriteria.list();
			}
		});
	}

	/**
	 * 根据DetachedCriteria 查询条件 查询总行数
	 * @param criteria 查询条件
	 * @return 返回结果列表 异常返回0
	 */
	private int count(Class<?> entityClass, final DetachedCriteria criteria) {
		return execute(entityClass, new Callback<Integer>() {
			public Integer callback(Session session) {
				return Conversion.toInt(criteria.getExecutableCriteria(session).setProjection(Projections.rowCount()).uniqueResult());
			}
		});
	}

	/**
	 * queryByBetween使用 返回DetachedCriteria
	 * @param entity 查询实体
	 * @param property 字段名
	 * @param lo 开始条件
	 * @param hi 结束条件
	 * @return DetachedCriteria
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
	 * 获得排序DetachedCriteria
	 * @param entity 实体类
	 * @param orders 排序参数
	 * @return DetachedCriteria
	 */
	private DetachedCriteria getOrder(Class<?> entityClass, Map<String, Object> orders) {
		// 获得DetachedCriteria
		DetachedCriteria criteria = DetachedCriteria.forClass(entityClass);
		// 循环排序
		for (Map.Entry<String, Object> e : orders.entrySet()) {
			criteria.addOrder(Conversion.toBoolean(e.getValue()) ? Order.asc(e.getKey()) : Order.desc(e.getKey()));
		}
		// 返回DetachedCriteria
		return criteria;
	}

	/**
	 * 获得当前Session
	 * @return Session
	 */
	private Session getSession(Class<?> entity) {
		return factorys.getSession(entity);
	}

	/**
	 * 执行Hibernate操作
	 * @param callback 回调方法
	 * @param lockMode 锁模式
	 * @return 泛型对象
	 */
	private <T> T execute(Class<?> entity, Callback<T> callback) {
		// 获得Session
		Session session = getSession(entity);
		// 声明事务
		Transaction tx = null;
		// 是否自己控制事务
		boolean isSession = !session.getTransaction().isActive();
		// 返回结果
		T t = null;
		try {
			// 是否自己控制事务
			if (isSession) {
				// 开始事务
				tx = session.beginTransaction();
			}
			// 执行
			t = callback.callback(session);
			// 是否自己控制事务
			if (!EmptyUtil.isEmpty(tx)) {
				// 提交事务
				tx.commit();
			}
			// toString() 为了使关联生效
			if (!EmptyUtil.isEmpty(t)) {
				t.toString();
			}
		} catch (Exception e) {
			// 回滚事务
			if (!EmptyUtil.isEmpty(tx)) {
				tx.rollback();
			}
			throw e;
		} finally {
			// 自己关闭session
			if (isSession && session.isOpen() && session.isConnected()) {
				session.close();
			}
		}
		// 返回对象
		return t;
	}

	/**
	 * Hibernate回调方法
	 * @author WD
	 * @since JDK7
	 * @version 1.0 2012-03-05
	 */
	interface Callback<T> {
		/**
		 * 调用Hibernate执行操作
		 * @param session hibernate Session
		 * @return 指定的泛型
		 */
		T callback(Session session);
	}
}
