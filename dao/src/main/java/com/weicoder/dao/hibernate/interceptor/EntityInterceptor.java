package com.weicoder.dao.hibernate.interceptor;

import java.util.Map;

import org.hibernate.EmptyInterceptor;
import org.hibernate.Interceptor;

import com.weicoder.common.lang.Maps;
import com.weicoder.common.util.EmptyUtil;
import com.weicoder.common.util.StringUtil;
import com.weicoder.dao.hibernate.shards.Shards;
import com.weicoder.dao.util.SqlUtil;

/**
 * hibernate 实体拦截器
 * @author WD
 */
public class EntityInterceptor extends EmptyInterceptor {
	public static final Interceptor	INSTANCE			= new EntityInterceptor();
	private static final long		serialVersionUID	= 2314652107885146870L;
	// 原表名
	private Map<String, String>		names				= Maps.newMap();

	private EntityInterceptor() {}

	@Override
	public String onPrepareStatement(String sql) {
		// 分表列表为空
		if (EmptyUtil.isEmpty(names))
			return sql;
		// 查找表名
		String name = SqlUtil.getTable(sql);
		String table = names.get(name);
		if (EmptyUtil.isNotEmptys(name, table) && !name.equals(table))
			sql = StringUtil.replace(sql, name, table);
		return super.onPrepareStatement(sql);
	}

	@Override
	public String getEntityName(Object entity) {
		// 获得表名
		String name = StringUtil.convert(entity.getClass().getSimpleName());
		// 是否切片实体
		if (entity instanceof Shards)
			// 切片
			names.put(name, StringUtil.add(name, ((Shards) entity).shard()));
		// 返回实体名
		return super.getEntityName(entity);
	}
}
