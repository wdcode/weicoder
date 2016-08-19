package com.weicoder.dao.jdbc;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import com.weicoder.common.lang.Maps;
import com.weicoder.common.log.Logs;
import com.weicoder.common.util.BeanUtil;
import com.weicoder.common.util.ClassUtil;
import com.weicoder.common.util.StringUtil;
import com.weicoder.dao.Dao;
import com.weicoder.dao.annotation.Table;
import com.weicoder.dao.db.DataBase;
import com.weicoder.dao.db.factory.DataBaseFactory;

/**
 * Dao JDBC实现
 * @author WD
 */
public final class JdbcDao implements Dao {
	// DataBase 对象
	private DataBase							db;
	// 保存INSERT INTN TO 语句
	private Map<Class<? extends Table>, String>	insert;

	public JdbcDao() {
		db = DataBaseFactory.getDataBase();
		insert = Maps.getMap();
		// 加载所有类对应的SQL语句
		for (Class<Table> c : ClassUtil.getAnnotationClass(Table.class)) {
			// 声明SQL INSERT INTO t(f1,f2,...) VALUES(?,?)
			StringBuilder sql = new StringBuilder("insert into ");
			// 反射类名为表名
			sql.append(StringUtil.toDbName(c.getSimpleName())).append("(");
			// 值
			StringBuilder value = new StringBuilder(" VALUES(");
			// 获得所有字段 拼装SQL
			for (Field f : c.getDeclaredFields()) {
				sql.append(StringUtil.toDbName(f.getName())).append(",");
				value.append("?,");
			}
			// 去掉最后的
			sql.deleteCharAt(sql.length() - 1);
			value.deleteCharAt(value.length() - 1);
			// 添加值
			insert.put(c, sql.append(")").append(value).append(")").toString());
			Logs.debug("sql={}", sql);
		}
		Logs.info("insert={}", insert);
	}

	@Override
	public <E> E insert(E entity) {
		db.execute(insert.get(entity.getClass()), getParame(entity));
		return entity;
	}

	@Override
	public <E> List<E> insert(List<E> entitys) {
		db.execute(insert.get(entitys.get(0).getClass()), getParames(entitys));
		return entitys;
	}

	@Override
	public <E> E update(E entity) {

		return null;
	}

	@Override
	public <E> List<E> update(List<E> entitys) {

		return null;
	}

	@Override
	public <E> E insertOrUpdate(E entity) {

		return null;
	}

	@Override
	public <E> List<E> insertOrUpdate(List<E> entitys) {

		return null;
	}

	@Override
	public <E> E delete(E entity) {

		return null;
	}

	@Override
	public <E> List<E> delete(List<E> entitys) {

		return null;
	}

	@Override
	public <E> E get(Class<E> entityClass, Serializable pk) {

		return null;
	}

	@Override
	public <E> List<E> gets(Class<E> entityClass, Serializable... pks) {

		return null;
	}

	@Override
	public <E> E get(E entity) {

		return null;
	}

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
	public <E> List<E> eq(Class<E> entityClass, Map<String, Object> map, int firstResult, int maxResults) {

		return null;
	}

	@Override
	public <E> List<E> in(Class<E> entityClass, String property, List<?> values, int firstResult, int maxResults) {

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
	public int count(Class<?> entityClass) {

		return 0;
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

	@Override
	public int execute(Class<?> entityClass, String sql, Object... values) {

		return 0;
	}

	@Override
	public <E> List<E> query(Class<?> entityClass, String sql, List<Object> values, int firstResult, int maxResults) {

		return null;
	}

	/**
	 * 根据实体类获得参数
	 * @param e 实体
	 * @return 参数
	 */
	private <E> Object[] getParame(E e) {
		Field[] f = e.getClass().getDeclaredFields();
		Object[] o = new Object[f.length];
		for (int i = 0; i < f.length; i++) {
			o[i] = BeanUtil.getFieldValue(e, f[i]);
		}
		return o;
	}

	/**
	 * 根据实体类获得参数
	 * @param e 实体
	 * @return 参数
	 */
	private <E> Object[][] getParames(List<E> e) {
		Object[][] o = new Object[e.size()][];
		for (int i = 0; i < e.size(); i++) {
			o[i] = getParame(e.get(i));
		}
		return o;
	}
}
