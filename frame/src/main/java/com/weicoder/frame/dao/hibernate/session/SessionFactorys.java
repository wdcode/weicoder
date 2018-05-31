package com.weicoder.frame.dao.hibernate.session;

import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.sql.DataSource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyLegacyHbmImpl;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Component;
import com.weicoder.frame.context.Context;
import com.weicoder.frame.dao.hibernate.naming.ImprovedNamingStrategy;
import com.weicoder.frame.dao.hibernate.search.HibernateSearch;
import com.weicoder.frame.entity.Entity;
import com.weicoder.frame.params.DaoParams;
import com.alibaba.druid.pool.DruidDataSource;
import com.weicoder.common.lang.Maps;
import com.weicoder.common.util.EmptyUtil;

/**
 * SessionFactory包装类
 * @author WD
 * 
 * @version 1.0 2013-11-19
 */
@Component
public final class SessionFactorys {
	// Context
	@Resource
	private Context							context;
	// 类对应SessionFactory
	private Map<Class<?>, SessionFactory>	factorys;
	// 保存单session工厂 只有一个SessionFactory工厂时使用
	private SessionFactory					factory;

	/**
	 * 初始化
	 */
	@PostConstruct
	protected void init() {
		// 实例化表列表
		factorys = Maps.newConcurrentMap();
		// 初始化SessionFactory
		initSessionFactory();
		// 获得所有SessionFactory
		Map<String, SessionFactory> map = context.getBeans(SessionFactory.class);
		// 如果只有一个SessionFactory
		if (map.size() == 1) {
			factory = map.values().toArray(new SessionFactory[1])[0];
		}
		// 循环获得表名
		for (Class<? extends Entity> c : context.getEntitys()) {
			// 循环获得SessionFactory
			for (SessionFactory sessionFactory : map.values()) {
				try {
					if (((SessionFactoryImplementor) sessionFactory).getMetamodel()
							.entity(c) != null) {
						factorys.put(c, sessionFactory);
					}
				} catch (Exception e) {}
			}
		}
	}

	/**
	 * 根据实体类获得SessionFactory
	 * @param entity 实体类
	 * @return SessionFactory
	 */
	public SessionFactory getSessionFactory(Class<?> entity) {
		return factory == null ? factorys.get(entity) : factory;
	}

	/**
	 * 获得当前Session
	 * @param entity 实体类
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

	/**
	 * 关闭资源
	 */
	public void close() {
		if (factory != null) {
			factory.close();
		}
		for (SessionFactory factory : factorys.values()) {
			factory.close();
		}
	}

	/**
	 * 初始化SessionFactory
	 */
	private void initSessionFactory() {
		// 循环生成
		for (String name : DaoParams.NAMES) {
			// 根据类获得BeanDefinitionBuilder
			BeanDefinitionBuilder builder = BeanDefinitionBuilder
					.genericBeanDefinition(LocalSessionFactoryBean.class);
			// 获得数据源
			DataSource ds = getDataSource(name);
			// 设置数据源
			builder.addPropertyValue("dataSource", ds);
			// 设置namingStrategy
			builder.addPropertyValue("implicitNamingStrategy",
					ImplicitNamingStrategyLegacyHbmImpl.INSTANCE);
			// builder.addPropertyValue("physicalNamingStrategy",
			// PhysicalNamingStrategyStandardImpl.INSTANCE);
			builder.addPropertyValue("physicalNamingStrategy", ImprovedNamingStrategy.INSTANCE);
			// 设置扫描包
			builder.addPropertyValue("packagesToScan", DaoParams.getPackages(name));
			// 设置Hibernate属性
			Properties hp = new Properties();
			// 方言
			hp.put("hibernate.dialect", DaoParams.getDialect(name));
			hp.put("hibernate.show_sql", DaoParams.getSql(name));
			hp.put("hibernate.format_sql", DaoParams.getSql(name));
			hp.put("hibernate.release_mode", "auto");
			// 数据库参数
			hp.put("hibernate.jdbc.batch_size", DaoParams.getBatch(name));
			hp.put("hibernate.jdbc.fetch_size", DaoParams.getFetch(name));
			// search
			if (!EmptyUtil.isEmpty(context.getBeans(HibernateSearch.class))) {
				hp.put("hibernate.search.default.directory_provider",
						DaoParams.getSearchDirectory(name));
				hp.put("hibernate.search.default.indexBase", DaoParams.getSearchBase(name));
				hp.put("hibernate.search.lucene_version", DaoParams.getSearchVersion(name));
				hp.put("hibernate.ejb.event.post-insert",
						"org.hibernate.search.event.FullTextIndexEventListener");
				hp.put("hibernate.ejb.event.post-update",
						"org.hibernate.search.event.FullTextIndexEventListener");
				hp.put("hibernate.ejb.event.post-delete",
						"org.hibernate.search.event.FullTextIndexEventListener");
				hp.put("hibernate.search.autoregister_listeners", true);

			}
			builder.addPropertyValue("hibernateProperties", hp);
			// 注册
			context.registerBeanDefinition(name + "SessionFactory", builder.getRawBeanDefinition());
		}
	}

	/**
	 * 获得数据源
	 * @param name 名称
	 * @return 数据源
	 */
	private DataSource getDataSource(String name) {
		// 声明数据源
		DruidDataSource ds = new DruidDataSource();
		System.setProperty("druid.logType", "log4j2");
		ds.setDriverClassName((DaoParams.getDriver(name)));
		ds.setUrl(DaoParams.getUrl(name));
		ds.setUsername(DaoParams.getUser(name));
		ds.setPassword(DaoParams.getPassword(name));
		ds.setMaxActive(DaoParams.getMaxPoolSize(name));
		ds.setMinIdle(DaoParams.getMinPoolSize(name));
		ds.setValidationQueryTimeout(300000);
		ds.setTimeBetweenEvictionRunsMillis(60000);
		ds.setInitialSize(DaoParams.getInitialPoolSize(name));
		ds.setMaxWait(DaoParams.getMaxIdleTime(name));
		ds.setValidationQuery("SELECT 1");
		// 返回数据源
		return ds;
	}
}
