package com.weicoder.elasticsearch.annotation;

/**
 * es index
 * 
 * @author wudi
 */
public @interface Index {
	/**
	 * 分片数量
	 * 
	 * @return 分片数量
	 */
	int shards() default 1;

	/**
	 * 副本数量
	 * 
	 * @return 副本数量
	 */
	int replica() default 0;

	/**
	 * es的主键ID
	 * 
	 * @return id
	 */
	String id() default "id";

	/**
	 * 索引名称
	 * 
	 * @return 索引名称
	 */
	String name();
}
