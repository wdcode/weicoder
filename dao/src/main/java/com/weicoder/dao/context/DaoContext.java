package com.weicoder.dao.context;

import java.util.Map;

import javax.persistence.Entity;

import com.weicoder.common.lang.Maps;
import com.weicoder.common.util.ClassUtil;
import com.weicoder.common.util.StringUtil;
import com.weicoder.dao.params.DaoParams;

/**
 * Dao 全局控制器
 * @author WD
 */
public final class DaoContext {
	// 保存po数据库实体
	private final static Map<String, Class<Entity>> ENTITYS = Maps.newMap();

	/**
	 * 初始化实体保存类
	 */
	public static void init() {
		// 获得所有实体并保存
		for (Class<Entity> c : ClassUtil.getPackageClasses(DaoParams.PACKAGES, Entity.class)) {
			// 保存到列表
			ENTITYS.put(StringUtil.convert(c.getSimpleName()), c);
		}
	}

	/**
	 * 根据实体名称获得类
	 * @param entity 实体名称
	 * @return
	 */
	public static Class<Entity> getClass(String entity) {
		return ENTITYS.get(entity);
	}

	private DaoContext() {}
}
