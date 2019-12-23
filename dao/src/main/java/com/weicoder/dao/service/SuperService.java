package com.weicoder.dao.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.weicoder.dao.factory.DaoFactory;
import com.weicoder.common.bean.Pages;
import com.weicoder.common.lang.Lists;
import com.weicoder.common.util.BeanUtil;
import com.weicoder.common.util.DateUtil;
import com.weicoder.common.util.EmptyUtil;
import com.weicoder.dao.Dao;
import com.weicoder.dao.bean.PageResult;

/**
 * 超级通用业务hibernate实现
 * 
 * @author WD
 */
public final class SuperService {
	/** Dao 接口 */
	public final static Dao DAO = DaoFactory.FACTORY.getInstance();

	/**
	 * 添加要更新的数据到队列 队列按定时执行insertOrUpdate 不要使用此方法保存重要数据
	 * 
	 * @param  obj 数据库对象
	 * @return     是否添加成功
	 */
	public static boolean add(Object obj) {
		return obj == null ? false : QueueFactory.get(obj.getClass()).add(obj);
	}

	/**
	 * 添加要更新的数据到队列 队列按定时执行insertOrUpdate 不要使用此方法保存重要数据
	 * 
	 * @param  objs 对象列表
	 * @return      是否添加成功
	 */
	public static boolean adds(List<Object> objs) {
		return EmptyUtil.isEmpty(objs) ? false : QueueFactory.get(objs.get(0).getClass()).addAll(objs);
	}

	/**
	 * 删除
	 * 
	 * @param  entity 实体
	 * @param  <E>    泛型
	 * @return        是否成功
	 */
	public static <E> List<E> delete(E entity) {
		// 查询出符合删除实体列表
		List<E> list = DAO.list(entity, -1, -1);
		// 删除列表为空
		if (EmptyUtil.isEmpty(list))
			return Lists.emptyList();
		// 删除
		return DAO.delete(list);
	}

	/**
	 * 根据ID数组删除 完全删除
	 * 
	 * @param  entity 要查询的实体
	 * @param  pks    主键数组
	 * @param  <E>    泛型
	 * @return        是否成功
	 */
	public static <E> List<E> delete(Class<E> entity, Serializable... pks) {
		return DAO.delete(DAO.gets(entity, pks));
	}

	/**
	 * 查询全部数据
	 * 
	 * @param  entityClass 要查询的实体
	 * @param  <E>         泛型
	 * @return             全部实体
	 */
	public static <E> List<E> all(Class<E> entityClass) {
		return DAO.list(entityClass, -1, -1);
	}

	/**
	 * 获得查询的对象实体列表 分页功能
	 * 
	 * @param  entityClass 要查询的实体
	 * @param  page        分页Bean
	 * @param  <E>         泛型
	 * @return             返回这个对象的列表
	 */
	public static <E> PageResult list(Class<E> entityClass, Pages page) {
		// 获得数据列表
		List<E> list = DAO.list(entityClass, getFirstResult(page), getMaxResults(page));
		// 判断列表
		if (EmptyUtil.isEmpty(list))
			// 为空 设置总数为 0
			page.setTotal(0);
		else
			// 不为空 查询出总数
			page.setTotal(DAO.count(entityClass));
		// 返回列表
		return new PageResult(list, page);
	}

	/**
	 * 根据时间段查询的对象实体列表 分页功能
	 * 
	 * @param  <E>
	 * @param  entity 查询对象
	 * @param  begin  开始时间
	 * @param  end    结束时间
	 * @param  page   页面
	 * @return
	 */
	public static <E> List<E> date(E entity, String begin, String end, Pages page) {
		return time(entity, DateUtil.getTime(begin), DateUtil.getTime(end), page);
	}

	/**
	 * 根据时间段查询的对象实体列表 分页功能
	 * 
	 * @param  <E>
	 * @param  entity 查询对象
	 * @param  begin  开始时间
	 * @param  end    结束时间
	 * @param  page   页面
	 * @return
	 */
	public static <E> List<E> time(E entity, int begin, int end, Pages page) {
		// 获得数据列表
		List<E> list = DAO.between(entity, "time", begin, end, getFirstResult(page), getMaxResults(page));
//		// 判断列表
//		if (EmptyUtil.isEmpty(list))
//			// 为空 设置总数为 0
//			page.setTotal(0);
//		else
//			// 不为空 查询出总数
//			page.setTotal(DAO.count(entity.getClass()));
		// 返回列表
		return list;
	}

	/**
	 * 获得查询的对象实体列表 分页功能
	 * 
	 * @param  entity 需要获得的对象，会查询出实体中封装的相等的条件
	 * @param  page   分页Bean
	 * @return        返回这个对象的列表
	 */
	public static PageResult list(Object entity, Pages page) {
		// 获得数据列表
		List<Object> list = DAO.list(entity, getFirstResult(page), getMaxResults(page));
		// 判断列表
		if (EmptyUtil.isEmpty(list))
			// 为空 设置总数为 0
			page.setTotal(0);
		else
			// 不为空 查询出总数
			page.setTotal(DAO.count(entity));
		// 返回列表
		return new PageResult(list, page);
	}

	/**
	 * 查询属性名等值的实体列表
	 * 
	 * @param  entityClass 要查询的实体
	 * @param  property    属性名
	 * @param  values      属性值
	 * @param  pager       分页Bean
	 * @param  <E>         泛型
	 * @return             数据列表
	 */
	public static <E> List<E> in(Class<E> entityClass, String property, List<Object> values, Pages pager) {
		// 获得数据列表
		List<E> list = DAO.in(entityClass, property, values, getFirstResult(pager), getMaxResults(pager));
		// 判断列表
		if (EmptyUtil.isEmpty(list))
			// 为空 设置总数为 0
			pager.setTotal(0);
		else
			// 不为空 查询出总数
			pager.setTotal(DAO.count(entityClass, property, values));
		// 返回列表
		return list;
	}

	/**
	 * 查询属性名等值的实体列表
	 * 
	 * @param  entityClass 要查询的实体
	 * @param  property    属性名
	 * @param  value       属性值
	 * @param  <E>         泛型
	 * @return             数据列表
	 */
	public static <E> List<E> eq(Class<E> entityClass, String property, Object value) {
		return DAO.eq(entityClass, property, value, -1, -1);
	}

	/**
	 * 查询属性名大于的实体列表
	 * 
	 * @param  entityClass 要查询的实体
	 * @param  property    属性名
	 * @param  value       属性值
	 * @param  <E>         泛型
	 * @return             数据列表
	 */
	public static <E> List<E> gt(Class<E> entityClass, String property, Object value) {
		return DAO.gt(entityClass, property, value, -1, -1);
	}

	/**
	 * 查询属性名小于的实体列表
	 * 
	 * @param  entityClass 要查询的实体
	 * @param  property    属性名
	 * @param  value       属性值
	 * @param  <E>         泛型
	 * @return             数据列表
	 */
	public static <E> List<E> lt(Class<E> entityClass, String property, Object value) {
		return DAO.lt(entityClass, property, value, -1, -1);
	}

	/**
	 * 查询属性名等值的实体列表
	 * 
	 * @param  entityClass 要查询的实体
	 * @param  property    属性名
	 * @param  value       属性值
	 * @param  pager       分页Bean
	 * @param  <E>         泛型
	 * @return             数据列表
	 */
	public static <E> List<E> eq(Class<E> entityClass, String property, Object value, Pages pager) {
		// 获得数据列表
		List<E> list = DAO.eq(entityClass, property, value, getFirstResult(pager), getMaxResults(pager));
		// 判断列表
		if (EmptyUtil.isEmpty(list))
			// 为空 设置总数为 0
			pager.setTotal(0);
		else
			// 不为空 查询出总数
			pager.setTotal(DAO.count(entityClass, property, value));
		// 返回列表
		return list;
	}

	/**
	 * 查询属性名等值的实体列表
	 * 
	 * @param  entityClass 要查询的实体
	 * @param  property    属性名
	 * @param  values      属性值
	 * @param  orders      排序
	 * @param  pager       分页Bean
	 * @param  <E>         泛型
	 * @return             数据列表
	 */
	public static <E> List<E> in(Class<E> entityClass, String property, List<Object> values, Map<String, Object> orders, Pages pager) {
		// 获得数据列表
		List<E> list = DAO.in(entityClass, property, values, orders, getFirstResult(pager), getMaxResults(pager));
		// 判断列表
		if (EmptyUtil.isEmpty(list))
			// 为空 设置总数为 0
			pager.setTotal(0);
		else
			// 不为空 查询出总数
			pager.setTotal(DAO.count(entityClass, property, values));
		// 返回列表
		return list;
	}

	/**
	 * 查询字段在lo到hi之间的实体
	 * 
	 * @param  entity   查询实体
	 * @param  property 字段名
	 * @param  lo       开始条件
	 * @param  hi       结束条件
	 * @param  <E>      泛型
	 * @return          返回结果列表
	 */
	public static <E> List<E> between(Class<E> entity, String property, Object lo, Object hi) {
		return DAO.between(entity, property, lo, hi, -1, -1);
	}

	/**
	 * 查询字段在lo到hi之间的实体
	 * 
	 * @param  entity   查询实体
	 * @param  property 字段名
	 * @param  lo       开始条件
	 * @param  hi       结束条件
	 * @param  page     分页实体
	 * @param  <E>      泛型
	 * @return          返回结果列表
	 */
	public static <E> List<E> between(E entity, String property, Object lo, Object hi, Pages page) {
		// 获得数据列表
		List<E> list = DAO.between(entity, property, lo, hi, getFirstResult(page), getMaxResults(page));
		// 判断列表
		if (EmptyUtil.isEmpty(list))
			// 为空 设置总数为 0
			page.setTotal(0);
		else
			// 不为空 查询出总数
			page.setTotal(DAO.count(entity, property, lo, hi));
		// 返回列表
		return list;
	}

	/**
	 * 查询字段在lo到hi之间的实体
	 * 
	 * @param  entity 查询实体
	 * @param  orders 排序参数
	 * @param  page   分页实体
	 * @param  <E>    泛型
	 * @return        返回结果列表
	 */
	public static <E> List<E> order(E entity, Map<String, Object> orders, Pages page) {
		// 获得数据列表
		List<E> list = DAO.order(entity, orders, getFirstResult(page), getMaxResults(page));
		// 判断列表
		if (EmptyUtil.isEmpty(list))
			// 为空 设置总数为 0
			page.setTotal(0);
		else
			// 不为空 查询出总数
			page.setTotal(DAO.count(entity));
		// 返回列表
		return list;
	}

	/**
	 * 查询字段在lo到hi之间的实体
	 * 
	 * @param  entity 查询实体
	 * @param  orders 排序参数
	 * @param  page   分页实体
	 * @param  <E>    泛型
	 * @return        返回结果列表
	 */
	public static <E> List<E> order(Class<E> entity, Map<String, Object> orders, Pages page) {
		// 获得数据列表
		List<E> list = DAO.order(entity, orders, getFirstResult(page), getMaxResults(page));
		// 判断列表
		if (EmptyUtil.isEmpty(list))
			// 为空 设置总数为 0
			page.setTotal(0);
		else
			// 不为空 查询出总数
			page.setTotal(DAO.count(entity));
		// 返回列表
		return list;
	}

	/**
	 * 获得指定属性下的所有实体 包含指定属性
	 * 
	 * @param  entity   类名称
	 * @param  property 属性名
	 * @param  value    属性值
	 * @param  <E>      泛型
	 * @return          下级所有分类列表
	 */
	public static <E> List<E> next(Class<E> entity, String property, Object value) {
		// 声明列表
		List<E> list = DAO.eq(entity, property, value, -1, -1);
		// 声明返回列表
		List<E> ls = Lists.newList(list.size());
		// 添加指定实体
		ls.add(DAO.get(entity, (Serializable) value));
		// 循环添加
		Lists.newList(list).forEach(obj -> ls.addAll(next(entity, property, BeanUtil.getFieldValue(obj, property))));
		// 返回列表
		return ls;
	}

	/**
	 * 获得指定属性上的所有实体 包含指定属性
	 * 
	 * @param  entity   类名称
	 * @param  property 属性名
	 * @param  pk       主键
	 * @param  <E>      泛型
	 * @return          上级所有分类列表
	 */
	public static <E> List<E> prev(Class<E> entity, String property, Serializable pk) {
		// 声明列表
		List<E> list = Lists.newList();
		// 获得相当对象
		E obj = DAO.get(entity, pk);
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
	 * 
	 * @param  page 分页Bean
	 * @return      最大结果数
	 */
	private static int getMaxResults(Pages page) {
		return EmptyUtil.isEmpty(page) ? -1 : page.getSize();
	}

	/**
	 * 获得从第N条开始返回结果
	 * 
	 * @param  page 分页Bean
	 * @return      从第N条开始返回结果
	 */
	private static int getFirstResult(Pages page) {
		return EmptyUtil.isEmpty(page) ? -1 : (page.getPage() - 1) * page.getSize();
	}
}
