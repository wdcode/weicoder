package com.weicoder.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 通用Dao接口
 * 
 * @author WD
 */
public interface Dao {
	/**
	 * 持久化对象，添加操作
	 * 
	 * @param  entity 对象实体
	 * @param  <E>    泛型
	 * @return        返回插入实体
	 */
	<E> E insert(E entity);

	/**
	 * 持久化对象，添加操作
	 * 
	 * @param  entitys 对象实体
	 * @param  <E>     泛型
	 * @return         返回插入实体
	 */
	<E> List<E> insert(List<E> entitys);

	/**
	 * 持久化对象，添加操作
	 * 
	 * @param  entitys 对象实体
	 * @param  <E>     泛型
	 * @param  step    分步数量入库
	 * @return         返回插入实体
	 */
	<E> List<E> insert(List<E> entitys, int step);

	/**
	 * 一个事务下插入多个对象
	 * 
	 * @param  entitys 对象列表
	 * @return         是否成功
	 */
	boolean inserts(Object... entitys);

	/**
	 * 一个事务下更新多个对象
	 * 
	 * @param  entitys 对象列表
	 * @return         是否成功
	 */
	boolean updates(Object... entitys);

	/**
	 * 持久化数据，锁表 更新表中一行数据
	 * 
	 * @param  entity 对象实体
	 * @param  <E>    泛型
	 * @return        是否成功
	 */
	<E> E update(E entity);

	/**
	 * 持久化数据，锁表 更新表中一行数据
	 * 
	 * @param  entitys 对象实体
	 * @param  <E>     泛型
	 * @param  step    分步数量入库
	 * @return         是否成功
	 */
	<E> List<E> update(List<E> entitys, int step);

	/**
	 * 持久化数据，锁表 更新表中一行数据
	 * 
	 * @param  entitys 对象实体
	 * @param  <E>     泛型
	 * @return         是否成功
	 */
	<E> List<E> update(List<E> entitys);

	/**
	 * 批量持久化对象 保存或更新，如果存在就更新，不存在就插入
	 * 
	 * @param  entity 需要持久化的对象
	 * @param  <E>    泛型
	 * @return        列表对象
	 */
	<E> E insertOrUpdate(E entity);

	/**
	 * 批量持久化对象 保存或更新，如果存在就更新，不存在就插入
	 * 
	 * @param  entitys 需要持久化的对象
	 * @param  <E>     泛型
	 * @return         列表对象
	 */
	<E> List<E> insertOrUpdate(List<E> entitys);

	/**
	 * 批量持久化对象 保存或更新，如果存在就更新，不存在就插入
	 * 
	 * @param  entitys 需要持久化的对象
	 * @param  <E>     泛型
	 * @param  step    分步数量入库
	 * @return         列表对象
	 */
	<E> List<E> insertOrUpdate(List<E> entitys, int step);

	/**
	 * 一个事务批量持久化对象 保存或更新，如果存在就更新，不存在就插入
	 * 
	 * @param  entity 需要持久化的对象
	 * @return        是否成功
	 */
	boolean insertOrUpdates(Object... entity);

	/**
	 * 持久化数据，删除表中多行数据
	 * 
	 * @param  entity 需要持久话对象的集合
	 * @param  <E>    泛型
	 * @return        是否成功
	 */
	<E> E delete(E entity);

	/**
	 * 持久化数据，删除表中多行数据
	 * 
	 * @param  entitys 需要持久话对象的集合
	 * @param  <E>     泛型
	 * @return         列表
	 */
	<E> List<E> delete(List<E> entitys);

	/**
	 * 一个事务中删除多个数据
	 * 
	 * @param  entitys 需要持久话对象的集合
	 * @return         是否成功
	 */
	boolean deletes(Object... entitys);

	/**
	 * 获得持久化对象
	 * 
	 * @param  entityClass 实体类
	 * @param  pk          持久化对象的唯一标识(主键)
	 * @param  <E>         泛型
	 * @return             要获得的持久化对象，异常返回null
	 */
	<E> E get(Class<E> entityClass, Serializable pk);

	/**
	 * 获得持久化对象
	 * 
	 * @param  entityClass 实体类
	 * @param  pks         持久化对象的唯一标识(主键)
	 * @param  <E>         泛型
	 * @return             要获得的持久化对象，异常返回null
	 */
	<E> List<E> gets(Class<E> entityClass, Serializable... pks);

	/**
	 * 获得持久化对象 如果没有查询到对象 返回null
	 * 
	 * @param  entity 对象实体
	 * @param  <E>    泛型
	 * @return        持久化对象
	 */
	<E> E get(E entity);

	/**
	 * 获得持久化对象
	 * 
	 * @param  entityClass 实体类
	 * @param  property    属性名
	 * @param  value       属性值
	 * @param  <E>         泛型
	 * @return             要获得的持久化对象，如果不存在返回null
	 */
	<E> E get(Class<E> entityClass, String property, Object value);

	/**
	 * 获得持久化对象
	 * 
	 * @param  entity 实体类
	 * @param  map    属性键值
	 * @param  <E>    泛型
	 * @return        要获得的持久化对象，如果不存在返回null
	 */
	<E> E get(Class<E> entity, Map<String, Object> map);

	/**
	 * 获得查询的对象实体列表
	 * 
	 * @param  entity      需要获得的对象，会查询出实体中封装的相等的条件
	 * @param  firstResult 重第几条开始查询
	 * @param  maxResults  一共查回多少条
	 * @param  <E>         泛型
	 * @return             数据列表 异常返回 W.L.empty()
	 */

	<E> List<E> list(E entity, int firstResult, int maxResults);

	/**
	 * 查询所有数据
	 * 
	 * @param  entityClass 实体类
	 * @param  firstResult 开始查询的条数
	 * @param  maxResults  最多查询多少条
	 * @param  <E>         泛型
	 * @return             返回结果列表
	 */
	<E> List<E> list(Class<E> entityClass, int firstResult, int maxResults);

	/**
	 * 查询属性名等值的实体列表
	 * 
	 * @param  entityClass 实体类
	 * @param  property    属性名
	 * @param  value       属性值
	 * @param  firstResult 重第几条开始查询
	 * @param  maxResults  一共查回多少条
	 * @param  <E>         泛型
	 * @return             数据列表
	 */
	<E> List<E> like(Class<E> entityClass, String property, Object value, int firstResult, int maxResults);

	/**
	 * 查询属性名等值的实体列表
	 * 
	 * @param  entityClass 实体类
	 * @param  property    属性名
	 * @param  value       属性值
	 * @param  firstResult 重第几条开始查询
	 * @param  maxResults  一共查回多少条
	 * @param  <E>         泛型
	 * @return             数据列表
	 */
	<E> List<E> eq(Class<E> entityClass, String property, Object value, int firstResult, int maxResults);

	/**
	 * 查询属性名大于的实体列表
	 * 
	 * @param  entityClass 实体类
	 * @param  property    属性名
	 * @param  value       属性值
	 * @param  firstResult 重第几条开始查询
	 * @param  maxResults  一共查回多少条
	 * @param  <E>         泛型
	 * @return             数据列表
	 */
	<E> List<E> gt(Class<E> entityClass, String property, Object value, int firstResult, int maxResults);

	/**
	 * 查询属性名大于等于的实体列表
	 * 
	 * @param  entityClass 实体类
	 * @param  property    属性名
	 * @param  value       属性值
	 * @param  firstResult 重第几条开始查询
	 * @param  maxResults  一共查回多少条
	 * @param  <E>         泛型
	 * @return             数据列表
	 */
	<E> List<E> ge(Class<E> entityClass, String property, Object value, int firstResult, int maxResults);

	/**
	 * 查询属性名小于的实体列表
	 * 
	 * @param  entityClass 实体类
	 * @param  property    属性名
	 * @param  value       属性值
	 * @param  firstResult 重第几条开始查询
	 * @param  maxResults  一共查回多少条
	 * @param  <E>         泛型
	 * @return             数据列表
	 */
	<E> List<E> lt(Class<E> entityClass, String property, Object value, int firstResult, int maxResults);

	/**
	 * 查询属性名小于等于的实体列表
	 * 
	 * @param  entityClass 实体类
	 * @param  property    属性名
	 * @param  value       属性值
	 * @param  firstResult 重第几条开始查询
	 * @param  maxResults  一共查回多少条
	 * @param  <E>         泛型
	 * @return             数据列表
	 */
	<E> List<E> le(Class<E> entityClass, String property, Object value, int firstResult, int maxResults);

	/**
	 * 查询属性名等值的实体列表
	 * 
	 * @param  entityClass 实体类
	 * @param  map         属性
	 * @param  firstResult 重第几条开始查询
	 * @param  maxResults  一共查回多少条
	 * @param  <E>         泛型
	 * @return             数据列表
	 */
	<E> List<E> eq(Class<E> entityClass, Map<String, Object> map, int firstResult, int maxResults);

	/**
	 * 查询属性名含有列表的实体列表
	 * 
	 * @param  entityClass 实体类
	 * @param  property    属性名
	 * @param  values      属性值
	 * @param  firstResult 重第几条开始查询
	 * @param  maxResults  一共查回多少条
	 * @param  <E>         泛型
	 * @return             数据列表
	 */
	<E> List<E> in(Class<E> entityClass, String property, List<Object> values, int firstResult, int maxResults);

	/**
	 * 查询属性名含有列表的实体列表
	 * 
	 * @param  entityClass 实体类
	 * @param  property    属性名
	 * @param  values      属性值
	 * @param  orders      排序
	 * @param  firstResult 重第几条开始查询
	 * @param  maxResults  一共查回多少条
	 * @param  <E>         泛型
	 * @return             数据列表
	 */
	<E> List<E> in(Class<E> entityClass, String property, List<Object> values, Map<String, Object> orders, int firstResult, int maxResults);

	/**
	 * 查询属性名含有列表的实体列表
	 * 
	 * @param  entityClass 实体类
	 * @param  parames     参数map
	 * @param  firstResult 重第几条开始查询
	 * @param  maxResults  一共查回多少条
	 * @param  <E>         泛型
	 * @return             数据列表
	 */
	<E> List<E> in(Class<E> entityClass, Map<String, List<Object>> parames, int firstResult, int maxResults);

	/**
	 * 查询字段在lo到hi之间的实体
	 * 
	 * @param  entity      查询实体
	 * @param  property    字段名
	 * @param  lo          开始条件
	 * @param  hi          结束条件
	 * @param  firstResult 重第几条开始查询
	 * @param  maxResults  一共查回多少条
	 * @param  <E>         泛型
	 * @return             返回结果列表
	 */
	<E> List<E> between(E entity, String property, Object lo, Object hi, int firstResult, int maxResults);

	/**
	 * 查询字段在lo到hi之间的实体
	 * 
	 * @param  entity      查询实体
	 * @param  property    字段名
	 * @param  lo          开始条件
	 * @param  hi          结束条件
	 * @param  firstResult 重第几条开始查询
	 * @param  maxResults  一共查回多少条
	 * @param  <E>         泛型
	 * @return             返回结果列表
	 */
	<E> List<E> between(Class<E> entity, String property, Object lo, Object hi, int firstResult, int maxResults);

	/**
	 * 查询属性名等值的实体列表
	 * 
	 * @param  entity      实体类
	 * @param  orders      排序参数
	 * @param  firstResult 重第几条开始查询
	 * @param  maxResults  一共查回多少条
	 * @param  <E>         泛型
	 * @return             数据列表
	 */
	<E> List<E> order(E entity, Map<String, Object> orders, int firstResult, int maxResults);

	/**
	 * 查询属性名等值的实体列表
	 * 
	 * @param  entityClass 实体类
	 * @param  orders      排序参数
	 * @param  firstResult 重第几条开始查询
	 * @param  maxResults  一共查回多少条
	 * @param  <E>         泛型
	 * @return             数据列表
	 */
	<E> List<E> order(Class<E> entityClass, Map<String, Object> orders, int firstResult, int maxResults);

	/**
	 * 获得查询的对象实体总数
	 * 
	 * @param  entityClass 实体类
	 * @return             对象实体总数 异常返回 0
	 */
	int count(Class<?> entityClass);

	/**
	 * 查询字段在lo到hi之间的实体总数
	 * 
	 * @param  entity   查询实体
	 * @param  property 字段名
	 * @param  lo       开始条件
	 * @param  hi       结束条件
	 * @return          返回结果列表
	 */
	int count(Object entity, String property, Object lo, Object hi);

	/**
	 * 获得查询的对象实体总数
	 * 
	 * @param  entityClass 实体类
	 * @param  map         属性键值
	 * @return             对象实体总数 异常返回 0
	 */
	int count(Class<?> entityClass, Map<String, Object> map);

	/**
	 * 获得查询的对象实体总数
	 * 
	 * @param  entity 需要获得的对象，会查询出实体中封装的相等的条件
	 * @return        对象实体总数 异常返回 0
	 */
	int count(Object entity);

	/**
	 * 根据实体条件查询数量
	 * 
	 * @param  entityClass 实体类
	 * @param  property    属性名
	 * @param  value       属性值
	 * @return             数量
	 */
	int count(Class<?> entityClass, String property, Object value);

	/**
	 * 执行非查询的SQL语言 使用 ? 做参数
	 * 
	 * @param  entityClass 实体类
	 * @param  sql         sql语句 不能是查询的
	 * @param  values      参数值数组
	 * @return             返回影响的行数 异常返回-1
	 */
	int execute(Class<?> entityClass, String sql, Object... values);

	/**
	 * 根据SQL查询语句查询
	 * 
	 * @param  entityClass 实体类
	 * @param  sql         SQL查询语句 参数为?的语句
	 * @param  values      参数列表
	 * @param  firstResult 重第几条开始查询
	 * @param  maxResults  一共查回多少条
	 * @param  <E>         泛型
	 * @return             返回结果列表
	 */
	<E> List<E> query(Class<E> entityClass, String sql, List<Object> values, int firstResult, int maxResults);

	/**
	 * 根据SQL查询语句查询
	 * 
	 * @param  entityClass 实体类
	 * @param  sql         SQL查询语句 参数为?的语句
	 * @param  values      参数值数组
	 * @return             返回结果
	 */
	Object query(Class<?> entityClass, String sql, Object... values);

	/**
	 * 根据类名找到对应的实体类
	 * 
	 * @param  name
	 * @return
	 */
	Class<?> entity(String name);

	/**
	 * 获得当前实体类所在事务
	 * 
	 * @param  entityClass 实体类
	 * @return             事务对象
	 */
	Transactional getTransaction(Class<?> entityClass);
}
