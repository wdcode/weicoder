package com.weicoder.base.dao.hibernate.search;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.hibernate.search.FullTextQuery;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.query.dsl.QueryBuilder;
import com.weicoder.base.context.Context;
import com.weicoder.base.dao.hibernate.session.SessionFactorys;
import com.weicoder.common.lang.Lists;
import com.weicoder.common.util.EmptyUtil;

/**
 * Hibernate 使用lucene搜索数据
 * @author WD
 * @since JDK7
 * @version 1.0 2013-12-31
 */
public final class HibernateSearch {
	// Context
	@Resource
	private Context			context;
	// Session工厂
	@Resource
	private SessionFactorys	factorys;

	/**
	 * 初始化
	 */
	@PostConstruct
	protected void init() {
		// 创建索引
		createIndex();
	}

	/**
	 * 使用索引查询
	 * @param session Hibernate Session
	 * @param entityClass 实体类
	 * @param property 属性名
	 * @param value 属性值
	 * @param firstResult 重第几条开始查询
	 * @param maxResults 一共查回多少条
	 * @return 数据列表
	 */
	public <E> List<E> search(Session session, Class<E> entityClass, final String property, final Object value, final int firstResult, final int maxResults) {
		// 通过Hibernate的Session获取FullTextSession对象
		FullTextSession fullTextSession = Search.getFullTextSession(session);
		// 获取特定类的特定QueryBuilder对象
		QueryBuilder queryBuilder = fullTextSession.getSearchFactory().buildQueryBuilder().forEntity(entityClass).get();
		// 得到search query
		org.apache.lucene.search.Query query = queryBuilder.keyword().onField(property).matching(value).createQuery();
		// 这里使用FullTextQuery和org.hibernate.Query来分装org.apache.lucene.search.Query都是可以的，
		// 但FullTextQuery的功能比org.hibernate.Query的功能要强大一点，究竟什么时候要用org.hibernate.Query而不能用
		// org.apache.lucene.search.Query这个我暂时还没有发现！
		FullTextQuery fullTextQuery = fullTextSession.createFullTextQuery(query, entityClass);
		// 开始结果大于等于0
		if (firstResult >= 0) {
			fullTextQuery.setFirstResult(firstResult);
		}
		// 最大结果大于零
		if (maxResults > 0) {
			fullTextQuery.setMaxResults(maxResults);
		}
		// 返回结果
		return fullTextQuery.list();
	}

	/**
	 * 使用索引查询
	 * @param session Hibernate Session
	 * @param entity 实体
	 * @param firstResult 重第几条开始查询
	 * @param maxResults 一共查回多少条
	 * @return 数据列表
	 */
	public <E> List<E> search(Session session, E entity, int firstResult, int maxResults) {
		// 通过Hibernate的Session获取FullTextSession对象
		FullTextSession fullTextSession = Search.getFullTextSession(session);
		// 获取特定类的特定QueryBuilder对象
		QueryBuilder queryBuilder = fullTextSession.getSearchFactory().buildQueryBuilder().forEntity(entity.getClass()).get();
		// 得到search query
		org.apache.lucene.search.Query query = queryBuilder.all().createQuery();
		// 这里使用FullTextQuery和org.hibernate.Query来分装org.apache.lucene.search.Query都是可以的，
		// 但FullTextQuery的功能比org.hibernate.Query的功能要强大一点，究竟什么时候要用org.hibernate.Query而不能用
		// org.apache.lucene.search.Query这个我暂时还没有发现！
		FullTextQuery fullTextQuery = fullTextSession.createFullTextQuery(query, entity.getClass()).setCriteriaQuery(fullTextSession.createCriteria(entity.getClass()).add(Example.create(entity)));
		// 开始结果大于等于0
		if (firstResult >= 0) {
			fullTextQuery.setFirstResult(firstResult);
		}
		// 最大结果大于零
		if (maxResults > 0) {
			fullTextQuery.setMaxResults(maxResults);
		}
		// 返回结果
		return fullTextQuery.list();
	}

	/**
	 * 创建索引
	 */
	public void createIndex() {
		// 获得带索引的实体
		List<Object> list = Lists.getList(context.getBeansWithAnnotation(Indexed.class).values());
		// 如果有索引列表
		if (!EmptyUtil.isEmpty(list)) {
			// 获得列表长度
			int size = list.size();
			// 声明实体列表
			Class<?>[] entityClass = new Class<?>[size];
			// 声明实体列表
			SessionFactory[] sessionFactory = new SessionFactory[size];
			// 循环获得索引实体类型
			for (int i = 0; i < size; i++) {
				entityClass[i] = list.get(i).getClass();
				sessionFactory[i] = factorys.getSessionFactory(entityClass[i]);
			}
			// 创建索引
			for (SessionFactory factory : sessionFactory) {
				// 声明Session
				Session session = null;
				try {
					// 获得Session
					session = factory.openSession();
					Search.getFullTextSession(session).createIndexer(entityClass).startAndWait();
				} catch (Exception e) {} finally {
					if (session != null) {
						session.close();
					}
				}
			}
		}
	}
}
