package com.weicoder.dao.hibernate.type;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import javax.persistence.metamodel.Attribute;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;

import com.weicoder.common.json.JsonEngine;
import com.weicoder.common.util.BeanUtil;
import com.weicoder.common.util.ClassUtil;
import com.weicoder.common.util.EmptyUtil;
import com.weicoder.common.util.StringUtil;

/**
 * Json保存数据类型
 * @author WD
 */
public final class JsonType implements UserType, Serializable {
	// 序列化ID
	private static final long	serialVersionUID	= 3125729339438469811L;
	// 返回的Class
	private Class<?>			returnedClass;

	@Override
	public int[] sqlTypes() {
		return new int[] { Types.VARCHAR };
	}

	@Override
	public Class<?> returnedClass() {
		return returnedClass;
	}

	@Override
	public boolean equals(Object o, Object o1) throws HibernateException {
		if (o == o1) {
			return true;
		}
		if (o == null || o == null) {
			return false;
		}
		return o.equals(o1);
	}

	@Override
	public int hashCode(Object o) throws HibernateException {
		return o.hashCode();
	}

	@Override
	public Object deepCopy(Object o) throws HibernateException {
		return o;
	}

	@Override
	public boolean isMutable() {
		return true;
	}

	@Override
	public Serializable disassemble(Object value) throws HibernateException {
		return (Serializable) value;
	}

	@Override
	public Object assemble(Serializable cached, Object owner) throws HibernateException {
		return cached;
	}

	@Override
	public Object replace(Object original, Object target, Object owner) throws HibernateException {
		return original;
	}

	@Override
	public Object nullSafeGet(ResultSet rs, String[] names, SharedSessionContractImplementor session, Object owner) throws HibernateException, SQLException {
		String json = rs.getString(names[0]);
		// 判断json不为空
		if (EmptyUtil.isEmpty(json)) {
			return null;
		} else {
			// 声明字段名
			Field field = null;
			for (Attribute<?, ?> a : session.getFactory().getMetamodel().entity(owner.getClass()).getDeclaredAttributes()) {
				if (names[0].indexOf(StringUtil.subString(a.getName(), 0, 6)) > -1) {
					field = BeanUtil.getField(owner, a.getName());
					break;
				}
			}
			// 获得字段class
			returnedClass = field.getType();
			// 返回对象
			return returnedClass.equals(List.class) ? JsonEngine.toList(json, ClassUtil.getGenericClass(field.getGenericType(), 0)) : JsonEngine.toBean(json, returnedClass);
		}
	}

	@Override
	public void nullSafeSet(PreparedStatement st, Object value, int index, SharedSessionContractImplementor session) throws HibernateException, SQLException {
		if (value == null) {
			st.setNull(index, Types.VARCHAR);
		} else {
			st.setString(index, JsonEngine.toJson(value));
		}
	}
}
