package com.weicoder.hibernate6;
 
import java.util.List;
import java.util.Map;
 
import com.weicoder.hibernate.base.BaseHibernateDao;

/**
 * Hibernate6的DAO实现
 * @author wdcode
 *
 */
public class Hibernate6Dao extends BaseHibernateDao {

	@Override
	public <E> E get(Class<E> entityClass, String property, Object value) {
		
		return null;
	}

	@Override
	public <E> E get(Class<E> entity, Map<String, Object> map) {
		
		return null;
	}

	@Override
	public <E> List<E> list(E entity, int firstResult, int maxResults) {
		
		return null;
	}

	@Override
	public <E> List<E> list(Class<E> entityClass, int firstResult, int maxResults) {
		
		return null;
	}

	@Override
	public <E> List<E> like(Class<E> entityClass, String property, Object value, int firstResult, int maxResults) {
		
		return null;
	}

	@Override
	public <E> List<E> eq(Class<E> entityClass, String property, Object value, int firstResult, int maxResults) {
		
		return null;
	}

	@Override
	public <E> List<E> gt(Class<E> entityClass, String property, Object value, int firstResult, int maxResults) {
		
		return null;
	}

	@Override
	public <E> List<E> ge(Class<E> entityClass, String property, Object value, int firstResult, int maxResults) {
		
		return null;
	}

	@Override
	public <E> List<E> lt(Class<E> entityClass, String property, Object value, int firstResult, int maxResults) {
		
		return null;
	}

	@Override
	public <E> List<E> le(Class<E> entityClass, String property, Object value, int firstResult, int maxResults) {
		
		return null;
	}

	@Override
	public <E> List<E> eq(Class<E> entityClass, Map<String, Object> map, int firstResult, int maxResults) {
		
		return null;
	}

	@Override
	public <E> List<E> in(Class<E> entityClass, String property, List<Object> values, int firstResult, int maxResults) {
		
		return null;
	}

	@Override
	public <E> List<E> in(Class<E> entityClass, String property, List<Object> values, Map<String, Object> orders, int firstResult, int maxResults) {
		
		return null;
	}

	@Override
	public <E> List<E> in(Class<E> entityClass, Map<String, List<Object>> parames, int firstResult, int maxResults) {
		
		return null;
	}

	@Override
	public <E> List<E> between(E entity, String property, Object lo, Object hi, int firstResult, int maxResults) {
		
		return null;
	}

	@Override
	public <E> List<E> between(Class<E> entity, String property, Object lo, Object hi, int firstResult, int maxResults) {
		
		return null;
	}

	@Override
	public <E> List<E> order(E entity, Map<String, Object> orders, int firstResult, int maxResults) {
		
		return null;
	}

	@Override
	public <E> List<E> order(Class<E> entityClass, Map<String, Object> orders, int firstResult, int maxResults) {
		
		return null;
	}

	@Override
	public int count(Object entity, String property, Object lo, Object hi) {
		
		return 0;
	}

	@Override
	public int count(Class<?> entityClass, Map<String, Object> map) {
		
		return 0;
	}

	@Override
	public int count(Object entity) {
		
		return 0;
	}

	@Override
	public int count(Class<?> entityClass, String property, Object value) {
		
		return 0;
	}
 
}
