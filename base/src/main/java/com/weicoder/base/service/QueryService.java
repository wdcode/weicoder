package com.weicoder.base.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.weicoder.base.bean.Pagination;
import com.weicoder.base.context.Context;
import com.weicoder.base.entity.Entity;
import com.weicoder.common.lang.Maps;

/**
 * 基于Hibernate的查询器
 * @author WD
 * @since JDK7
 * @version 1.0 2012-06-24
 */
@Service
public final class QueryService {
	// 超级业务接口
	@Resource
	private SuperService	service;
	// context工具类
	@Resource
	private Context			context;

	/**
	 * 根据ID 获得实体
	 * @param entity 要查询的实体
	 * @param pk 主键
	 * @return 实体
	 */
	public Entity get(String entity, Serializable pk) {
		return service.get(context.getClass(entity), pk);
	}

	/**
	 * 获得持久化对象
	 * @param entity 要查询的实体
	 * @param property 属性名
	 * @param value 属性值
	 * @return 要获得的持久化对象，如果不存在返回null
	 */
	public Entity get(String entity, String property, Object value) {
		return service.get(context.getClass(entity), property, value);
	}

	/**
	 * 获得持久化对象
	 * @param entity 要查询的实体
	 * @param data 属性列与值
	 * @return 要获得的持久化对象，如果不存在返回null
	 */
	public Entity get(String entity, Map<String, Object> data) {
		return service.get(context.getClass(entity), data);
	}

	/**
	 * 使用索引查询
	 * @param entity 实体类
	 * @param property 属性名
	 * @param value 属性值
	 * @param firstResult 重第几条开始查询
	 * @param maxResults 一共查回多少条
	 * @return 数据列表
	 */
	public List<? extends Entity> search(String entity, String property, Object value) {
		return search(entity, property, value, -1, -1);
	}

	/**
	 * 使用索引查询
	 * @param entity 实体类
	 * @param property 属性名
	 * @param value 属性值
	 * @param maxResults 一共查回多少条
	 * @return 数据列表
	 */
	public List<? extends Entity> search(String entity, String property, Object value, int maxResults) {
		return search(entity, property, value, -1, maxResults);
	}

	/**
	 * 使用索引查询
	 * @param entity 实体类
	 * @param property 属性名
	 * @param value 属性值
	 * @param firstResult 重第几条开始查询
	 * @param maxResults 一共查回多少条
	 * @return 数据列表
	 */
	public List<? extends Entity> search(String entity, String property, Object value, int firstResult, int maxResults) {
		return service.search(context.getClass(entity), property, value, firstResult, maxResults);
	}

	/**
	 * 查询全部
	 * @param entity 要查询的实体
	 * @return 全部实体
	 */
	public List<? extends Entity> list(String entity) {
		return list(entity, -1);
	}

	/**
	 * 查询指定条数
	 * @param entity 要查询的实体
	 * @param num 要查询的条数
	 * @return 全部实体
	 */
	public List<? extends Entity> list(String entity, int num) {
		return list(entity, -1, num);
	}

	/**
	 * 查询指定条数
	 * @param entity 要查询的实体
	 * @param firstResult 重第几条开始查询
	 * @param maxResults 一共查回多少条
	 * @return 全部实体
	 */
	public List<? extends Entity> list(String entity, int firstResult, int maxResults) {
		return service.list(context.getClass(entity), firstResult, maxResults);
	}

	/**
	 * 查询属性名含有列表的实体列表
	 * @param entity 要查询的实体
	 * @param property 属性名
	 * @param values 属性值
	 * @return 数据列表
	 */
	public List<? extends Entity> list(String entity, String property, List<Object> values) {
		return list(entity, property, values, -1);
	}

	/**
	 * 查询属性名含有列表的实体列表
	 * @param entity 要查询的实体
	 * @param property 属性名
	 * @param values 属性值
	 * @param maxResults 一共查回多少条
	 * @return 数据列表
	 */
	public List<? extends Entity> list(String entity, String property, List<Object> values, int maxResults) {
		return list(entity, property, values, -1, maxResults);
	}

	/**
	 * 查询属性名含有列表的实体列表
	 * @param entity 要查询的实体
	 * @param property 属性名
	 * @param values 属性值
	 * @param firstResult 重第几条开始查询
	 * @param maxResults 一共查回多少条
	 * @return 数据列表
	 */
	public List<? extends Entity> list(String entity, String property, List<Object> values, int firstResult, int maxResults) {
		return service.in(context.getClass(entity), property, values, -1, maxResults);
	}

	/**
	 * 查询属性名含有列表的实体列表
	 * @param entity 要查询的实体
	 * @param op 操作符号
	 * @param map 对应的属性和值
	 * @return 数据列表
	 */
	public List<? extends Entity> list(String entity, List<String> keys, List<Object> values) {
		return list(entity, keys, values, -1, -1);
	}

	/**
	 * 查询属性名含有列表的实体列表
	 * @param entity 要查询的实体
	 * @param op 操作符号
	 * @param map 对应的属性和值
	 * @param maxResults 一共查回多少条
	 * @return 数据列表
	 */
	public List<? extends Entity> list(String entity, List<String> keys, List<Object> values, int maxResults) {
		return list(entity, keys, values, -1, maxResults);
	}

	/**
	 * 查询属性名含有列表的实体列表
	 * @param entity 要查询的实体
	 * @param parames 参数map
	 * @return 数据列表
	 */
	public List<? extends Entity> list(String entity, Map<String, List<Object>> parames) {
		return list(entity, parames, -1);
	}

	/**
	 * 查询属性名含有列表的实体列表
	 * @param entity 要查询的实体
	 * @param parames 参数map
	 * @param maxResults 一共查回多少条
	 * @return 数据列表
	 */
	public List<? extends Entity> list(String entity, Map<String, List<Object>> parames, int maxResults) {
		return list(entity, parames, -1, maxResults);
	}

	/**
	 * 查询属性名含有列表的实体列表
	 * @param entity 要查询的实体
	 * @param parames 参数map
	 * @param firstResult 重第几条开始查询
	 * @param maxResults 一共查回多少条
	 * @return 数据列表
	 */
	public List<? extends Entity> list(String entity, Map<String, List<Object>> parames, int firstResult, int maxResults) {
		return service.in(context.getClass(entity), parames, firstResult, maxResults);
	}

	/**
	 * 查询属性名含有列表的实体列表
	 * @param entity 要查询的实体
	 * @param keys 键列表
	 * @param values 值列表
	 * @param firstResult 重第几条开始查询
	 * @param maxResults 一共查回多少条
	 * @return 数据列表
	 */
	public List<? extends Entity> list(String entity, List<String> keys, List<Object> values, int firstResult, int maxResults) {
		return service.eq(context.getClass(entity), Maps.getMap(keys, values), firstResult, maxResults);
	}

	/**
	 * 查询属性名等值的实体列表
	 * @param entity 要查询的实体
	 * @param property 属性名
	 * @param value 属性值
	 * @return 数据列表
	 */
	public List<? extends Entity> list(String entity, String property, Object value) {
		return list(entity, property, value, -1);
	}

	/**
	 * 查询属性名等值的实体列表
	 * @param entity 要查询的实体
	 * @param property 属性名
	 * @param value 属性值
	 * @param maxResults 一共查回多少条
	 * @return 数据列表
	 */
	public List<? extends Entity> list(String entity, String property, Object value, int maxResults) {
		return list(entity, property, value, -1, maxResults);
	}

	/**
	 * 查询属性名等值的实体列表
	 * @param entity 要查询的实体
	 * @param property 属性名
	 * @param value 属性值
	 * @param firstResult 重第几条开始查询
	 * @param maxResults 一共查回多少条
	 * @return 数据列表
	 */
	public List<? extends Entity> list(String entity, String property, Object value, int firstResult, int maxResults) {
		return service.eq(context.getClass(entity), property, value, firstResult, maxResults);
	}

	/**
	 * 查询属性名等值的实体列表
	 * @param entity 要查询的实体
	 * @param property 属性名
	 * @param values 属性值
	 * @param pager 分页Bean
	 * @return 数据列表
	 */
	public List<? extends Entity> list(String entity, String property, List<Object> values, Pagination pager) {
		return service.in(context.getClass(entity), property, values, pager);
	}

	/**
	 * 查询属性名等值的实体列表
	 * @param entity 要查询的实体
	 * @param property 属性名
	 * @param values 属性值
	 * @param pager 分页Bean
	 * @return 数据列表
	 */
	public List<? extends Entity> list(String entity, String property, List<Object> values, Map<String, Object> orders, Pagination pager) {
		return service.in(context.getClass(entity), property, values, orders, pager);
	}

	/**
	 * 查询属性名等值的实体列表
	 * @param entity 要查询的实体
	 * @param property 属性名
	 * @param value 属性值
	 * @param pager 分页Bean
	 * @return 数据列表
	 */
	public List<? extends Entity> list(String entity, String property, Object value, Pagination pager) {
		return service.eq(context.getClass(entity), property, value, pager);
	}

	/**
	 * 根据实体条件查询数量
	 * @param entity 实体
	 * @return 数量
	 */
	public int count(String entity) {
		return service.count(context.getClass(entity));
	}

	/**
	 * 根据实体条件查询数量
	 * @param entity 实体
	 * @param property 属性名
	 * @param value 属性值
	 * @return 数量
	 */
	public int count(String entity, String property, Object value) {
		return service.count(context.getClass(entity), property, value);
	}

	/**
	 * 获得指定属性下的所有实体 包含指定属性
	 * @param entity 类名称
	 * @param property 属性名
	 * @param values 属性值
	 * @return 下级所有分类列表
	 */
	public List<? extends Entity> next(String entity, String property, Object value) {
		return service.next(context.getClass(entity), property, value);
	}

	/**
	 * 获得指定属性上的所有实体 包含指定属性
	 * @param entity 类名称
	 * @param property 属性名
	 * @param pk 主键
	 * @return 上级所有分类列表
	 */
	public List<? extends Entity> prev(String entity, String property, Serializable pk) {
		return service.prev(context.getClass(entity), property, pk);
	}

	/**
	 * 查询属性名等值的实体列表
	 * @param entityClass 实体类
	 * @param orders 排序参数
	 * @param maxResults 一共查回多少条
	 * @return 数据列表
	 */
	public List<Entity> order(String entity, Map<String, Object> orders, int firstResult, int maxResults) {
		return service.order(context.getClass(entity), orders, firstResult, maxResults);
	}
}
