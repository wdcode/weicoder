package com.weicoder.hibernate.type;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;

import com.weicoder.common.lang.Lists;
import com.weicoder.common.U;
import com.weicoder.json.JsonEngine;

/**
 * Json保存数据类型
 * @author WD
 */
public class JsonType extends BaseType {

	@Override
	public Object nullSafeGet(ResultSet rs, String[] names, SharedSessionContractImplementor session, Object owner) throws HibernateException, SQLException {
		String json = rs.getString(names[0]);
		// 判断json不为空
		if (U.E.isEmpty(json))
			return Lists.newList();
		// 返回对象
		return JsonEngine.toList(json, returnedClass());
	}

	@Override
	public void nullSafeSet(PreparedStatement st, Object value, int index, SharedSessionContractImplementor session) throws HibernateException, SQLException {
		if (value == null)
			st.setNull(index, Types.VARCHAR);
		st.setString(index, JsonEngine.toJson(value)); 
	}
}
