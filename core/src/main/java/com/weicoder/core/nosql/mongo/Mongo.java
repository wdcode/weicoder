package com.weicoder.core.nosql.mongo;

import java.util.List;
import java.util.Map;

import com.weicoder.core.nosql.NoSQL;

/**
 * MongoDB Dao接口
 * @author WD
 * @since JDK7
 * @version 1.0 2010-11-21
 */
public interface Mongo extends NoSQL {
	/**
	 * 插入数据
	 * @param name 数据集合
	 * @param data 数据对象
	 */
	void insert(String name, Map<String, Object> data);

	/**
	 * 插入数据
	 * @param name 数据集合
	 * @param data 数据对象
	 */
	void insert(String name, Map<String, Object>... data);

	/**
	 * 获得数据总数量
	 * @param name 数据集合
	 * @return 数量
	 */
	long count(String name);

	/**
	 * 根据查询条件获得数量
	 * @param name 数据集合
	 * @param query 查询条件
	 * @return 数量
	 */
	long count(String name, Map<String, Object> query);

	/**
	 * 根据查询条件获得数量
	 * @param name 数据集合
	 * @param key 查询主键
	 * @return 数量
	 */
	long count(String name, Object key);

	/**
	 * 删除数据
	 * @param name 数据集合
	 * @param data 数据
	 */
	void delete(String name, Map<String, Object> data);

	/**
	 * 删除数据
	 * @param name 数据集合
	 * @param data 数据
	 */
	void delete(String name, Map<String, Object>... data);

	/**
	 * 根据query参数,更新obj值
	 * @param name 数据集合
	 * @param query 条件值
	 * @param obj 要更新的值
	 */
	void update(String name, Map<String, Object> query, Map<String, Object> obj);

	/**
	 * 根据query参数,更新obj值
	 * @param name 数据集合
	 * @param query 条件值
	 * @param obj 要更新的值
	 * @param upsert 没有查询到直接添加新文档
	 * @param multi 是否多列更新
	 */
	void update(String name, Map<String, Object> query, Map<String, Object> obj, boolean upsert, boolean multi);

	/**
	 * 获得所有数据
	 * @param name 数据集合
	 * @return 数据列表
	 */
	List<Map<String, Object>> query(String name);

	/**
	 * 根据条件获得数据
	 * @param name 数据集合
	 * @param query 查询条件
	 * @return 数据列表
	 */
	List<Map<String, Object>> query(String name, Map<String, Object> query);

	/**
	 * 根据条件获得 start到end的数据
	 * @param name 数据集合
	 * @param query 查询条件
	 * @param start 开始条数
	 * @param end 结束条数
	 * @return 数据列表
	 */
	List<Map<String, Object>> query(String name, Map<String, Object> query, int start, int end);

	/**
	 * 根据键获得值
	 * @param name 集合名
	 * @param key 键
	 * @return 值
	 */
	Map<String, Object> get(String name, Object key);

	/**
	 * 根据键获得值
	 * @param name 集合名
	 * @param key 键
	 * @return 值
	 */
	Map<String, Object> get(String name, Map<String, Object> query);

	/**
	 * 创建索引
	 * @param name 数据集合
	 * @param keys 索引键
	 */
	void createIndex(String name, Map<String, Object> keys);

	/**
	 * 删除索引
	 * @param name 数据集合
	 * @param name 索引名
	 */
	void dropIndex(String name, String index);

	/**
	 * 删除索引
	 * @param name 数据集合
	 * @param keys 索引键
	 */
	void dropIndex(String name, Map<String, Object> keys);

	/**
	 * 删除所有索引
	 * @param name 数据集合
	 */
	void dropIndexes(String name);
}
