package com.weicoder.hibernate.interceptor;

import java.util.Map;

import org.hibernate.EmptyInterceptor;
import org.hibernate.Interceptor;

import com.weicoder.common.lang.W;
import com.weicoder.common.util.U; 
import com.weicoder.hibernate.shards.Shards;
import com.weicoder.dao.util.SqlUtil;

/**
 * hibernate 实体拦截器
 * @author WD
 */
public class EntityInterceptor extends EmptyInterceptor {
	public static final Interceptor	INSTANCE			= new EntityInterceptor();
	private static final long		serialVersionUID	= 2314652107885146870L;
	// 原表名
	private Map<String, String>		names				= W.M.map();

	private EntityInterceptor() {}

	@Override
	public String onPrepareStatement(String sql) {
		// 分表列表为空
		if (U.E.isEmpty(names))
			return sql;
		// 查找表名
		String name = SqlUtil.getTable(sql);
		String table = names.get(name);
		if (U.E.isNotEmptys(name, table) && !name.equals(table))
			sql = U.S.replace(sql, name, table);
		return super.onPrepareStatement(sql);
	}

	@Override
	public String getEntityName(Object entity) {
		// 获得表名
		String name = U.S.convert(entity.getClass().getSimpleName());
		// 是否切片实体
		if (entity instanceof Shards)
			// 切片
			names.put(name, U.S.add(name, ((Shards) entity).shard()));
		// 返回实体名
		return super.getEntityName(entity);
	}
}
