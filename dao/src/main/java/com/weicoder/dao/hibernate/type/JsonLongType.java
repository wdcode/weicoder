package com.weicoder.dao.hibernate.type;

/**
 * Json保存数据类型 返回Long类型
 * @author WD
 */
public class JsonLongType extends JsonType {
	@Override
	public Class<?> returnedClass() {
		return Long.class;
	}
}
