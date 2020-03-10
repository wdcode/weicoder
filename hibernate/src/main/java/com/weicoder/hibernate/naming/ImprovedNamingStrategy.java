package com.weicoder.hibernate.naming;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

import com.weicoder.common.U;

/**
 * 驼峰命名法
 * @author WD
 */
public class ImprovedNamingStrategy implements PhysicalNamingStrategy {
	public static final ImprovedNamingStrategy	INSTANCE	= new ImprovedNamingStrategy();
	private String								regex		= "([a-z])([A-Z])";
	private String								replacement	= "$1_$2";

	@Override
	public Identifier toPhysicalCatalogName(Identifier name, JdbcEnvironment jdbcEnvironment) {
		return convert(name);
	}

	@Override
	public Identifier toPhysicalSchemaName(Identifier name, JdbcEnvironment jdbcEnvironment) {
		return convert(name);
	}

	@Override
	public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment jdbcEnvironment) {
		return convert(name);
	}

	@Override
	public Identifier toPhysicalSequenceName(Identifier name, JdbcEnvironment jdbcEnvironment) {
		return convert(name);
	}

	@Override
	public Identifier toPhysicalColumnName(Identifier name, JdbcEnvironment jdbcEnvironment) {
		return convert(name);
	}

	private Identifier convert(Identifier name) {
		if (name == null || U.E.isEmpty(name.getText()))
			return name;
		return Identifier.toIdentifier(name.getText().replaceAll(regex, replacement).toLowerCase());
	}
}
