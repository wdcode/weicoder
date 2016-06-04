package com.weicoder.frame.dao.hibernate.session;

import java.util.List;
import java.util.Map;
//import java.util.Properties;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl;
import org.hibernate.cfg.Configuration;

import com.weicoder.frame.dao.hibernate.naming.ImprovedNamingStrategy;
import com.weicoder.frame.entity.Entity;
//import com.weicoder.frame.params.DaoParams;
import com.weicoder.common.interfaces.Close;
import com.weicoder.common.lang.Lists;
import com.weicoder.common.lang.Maps;
import com.weicoder.common.util.ClassUtil;
//import com.weicoder.core.dao.datasource.BasicDataSource;
//import com.weicoder.core.dao.datasource.DataSource;

/**
 * SessionFactory包装类
 * @author WD
 */
public final class SessionFactorys implements Close {
	// 所有SessionFactory
	private List<SessionFactory>			factorys;
	// 类对应SessionFactory
	private Map<Class<?>, SessionFactory>	entity_factorys;
	// 保存单session工厂 只有一个SessionFactory工厂时使用
	private SessionFactory					factory;

	/**
	 * 初始化
	 */
	public SessionFactorys() {
		// 实例化表列表
		entity_factorys = Maps.getConcurrentMap();
		factorys = Lists.getList();
		// 初始化SessionFactory
		initSessionFactory();
		// 如果只有一个SessionFactory
		if (factorys.size() == 1) {
			factory = factorys.get(0);
		}
		// 循环获得表名
		for (Class<Entity> e : ClassUtil.getAssignedClass(Entity.class)) {
			// 循环获得SessionFactory
			for (SessionFactory sessionFactory : factorys) {
				try {
					if (sessionFactory.getClassMetadata(e) != null) {
						entity_factorys.put(e, sessionFactory);
					}
				} catch (Exception ex) {
				}
			}
		}
	}

	/**
	 * 根据实体类获得SessionFactory
	 * @param entity 实体类
	 * @return SessionFactory
	 */
	public SessionFactory getSessionFactory(Class<?> entity) {
		return factory == null ? entity_factorys.get(entity) : factory;
	}

	/**
	 * 获得当前Session
	 * @return Session
	 */
	public Session getSession(Class<?> entity) {
		// 获得sessionFactory
		SessionFactory sessionFactory = getSessionFactory(entity);
		try {
			return sessionFactory.getCurrentSession();
		} catch (Exception e) {
			return sessionFactory.openSession();
		}
	}

	@Override
	public void close() {
		if (factory != null) {
			factory.close();
		}
		for (SessionFactory factory : entity_factorys.values()) {
			factory.close();
		}
	}

	/**
	 * 初始化SessionFactory
	 */
	private void initSessionFactory() {
		// 循环生成
//		for (String name : DaoParams.NAMES) {
			// 实例化hibernate配置类
			Configuration config = new Configuration().configure("db/hibernate.cfg.xml");
			// 获得数据源
//			DataSource ds = getDataSource(name);
			// 设置namingStrategy
			config.setImplicitNamingStrategy(ImplicitNamingStrategyJpaCompliantImpl.INSTANCE);
			config.setPhysicalNamingStrategy(ImprovedNamingStrategy.INSTANCE);
			// 设置Hibernate属性
//			Properties hp = new Properties();
			// 设置数据源
//			hp.put("connection.datasource", ds);
			// 设置扫描包
//			hp.put("packagesToScan", DaoParams.getPackages(name));
			// 方言
//			hp.put("hibernate.dialect", DaoParams.getDialect(name));
//			hp.put("hibernate.show_sql", DaoParams.getSql(name));
//			hp.put("hibernate.format_sql", DaoParams.getSql(name));
//			hp.put("hibernate.release_mode", "auto");
			// 数据库参数
//			hp.put("hibernate.jdbc.batch_size", DaoParams.getBatch(name));
//			hp.put("hibernate.jdbc.fetch_size", DaoParams.getFetch(name));
			// 添加参数
//			config.addProperties(hp);
			// 注册
			factorys.add(config.buildSessionFactory());
//		}
	}

//	/**
//	 * 获得数据源
//	 * @param name 名称
//	 * @return 数据源
//	 */
//	private DataSource getDataSource(String name) {
//		// 声明数据源
//		BasicDataSource ds = new BasicDataSource();
//		// 设置属性
//		ds.setParse(DaoParams.getParse(name));
//		ds.setDriver(DaoParams.getDriver(name));
//		ds.setUrl(DaoParams.getUrl(name));
//		ds.setUser(DaoParams.getUser(name));
//		ds.setPassword(DaoParams.getPassword(name));
//		ds.setMaxPoolSize(DaoParams.getMaxPoolSize(name));
//		ds.setMinPoolSize(DaoParams.getMinPoolSize(name));
//		ds.setMaxSize(DaoParams.getMaxSize(name));
//		ds.setTimeout(DaoParams.getTimeout(name));
//		ds.setIdleTimeout(DaoParams.getIdleTime(name));
//		ds.setInitialPoolSize(DaoParams.getInitialPoolSize(name));
//		ds.setMaxIdleTime(DaoParams.getMaxIdleTime(name));
//		// 返回数据源
//		return ds;
//	}
}
