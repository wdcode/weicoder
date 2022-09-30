package com.weicoder.dao.context;

import java.util.Map;

import com.weicoder.common.lang.W;
import com.weicoder.common.util.U;
import com.weicoder.dao.params.DaoParams;

/**
 * Dao 全局控制器
 * 
 * @author WD
 */
public final class DaoContext {
	// 保存po数据库实体
	private final static Map<String, Class<?>> ENTITYS = W.M.map();

	/**
	 * 初始化实体保存类
	 */
	public static void init() {
		// 获得所有实体并保存
		U.C.getPackageClasses(DaoParams.PACKAGES).forEach(c -> ENTITYS.put(U.S.convert(c.getSimpleName()), c));
	}

	/**
	 * 根据实体名称获得类
	 * 
	 * @param entity 实体名称
	 * @return 类
	 */
	public static Class<?> getClass(String entity) {
		return ENTITYS.get(entity);
	}

	private DaoContext() {
	}
}
