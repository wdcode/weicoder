package com.weicoder.hibernate.shards;

/**
 * 实体类切片
 * @author WD
 */
public interface Shards {
	/**
	 * 分表切表后缀 比如log - log + shard
	 * @return 表后缀
	 */
	String shard();
}
