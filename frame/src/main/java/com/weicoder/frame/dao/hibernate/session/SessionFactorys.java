package com.weicoder.frame.dao.hibernate.session;

import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Component;

import com.weicoder.frame.dao.hibernate.naming.ImprovedNamingStrategy;
import com.weicoder.frame.entity.Entity;
import com.weicoder.frame.params.DaoParams;
import com.weicoder.common.interfaces.Close;
import com.weicoder.common.lang.Maps; 
import com.weicoder.core.dao.datasource.BasicDataSource;
import com.weicoder.core.dao.datasource.DataSource;

/**
 * SessionFactory包装类
 * @author WD 
 * @version 1.0 
 */
@Component
public final class SessionFactorys implements Close {
	//ApplicationContext
	@Resource
	private ApplicationContext				context;
	@Resource
	private DefaultListableBeanFactory		beanFactory;
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
		factorys = Maps.getConcurrentMap();
		// 初始化SessionFactory
		initSessionFactory();
		// 获得所有SessionFactory
		Map<String, SessionFactory> map = context.getBeansOfType(SessionFactory.class);
		// 如果只有一个SessionFactory
		if (map.size() == 1) {
			factory = map.values().toArray(new SessionFactory[1])[0];
		}
		// 循环获得表名
		for (Entity e : context.getBeansOfType(Entity.class).values()) {
			// 循环获得SessionFactory
			for (SessionFactory sessionFactory : map.values()) {
				try {
					if (sessionFactory.getClassMetadata(e.getClass()) != null) {
						factorys.put(e.getClass(), sessionFactory);
					}
				} catch (Exception ex) {}
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
			BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(LocalSessionFactoryBean.class);
			// 获得数据源
			DataSource ds = getDataSource(name);
			// 设置数据源
			builder.addPropertyValue("dataSource", ds);
			// 设置namingStrategy
			//			builder.addPropertyValue("implicitNamingStrategy", ImplicitNamingStrategyJpaCompliantImpl.INSTANCE);
			//			builder.addPropertyValue("physicalNamingStrategy", PhysicalNamingStrategyStandardImpl.INSTANCE);
			builder.addPropertyValue("physicalNamingStrategy", new ImprovedNamingStrategy());
			//			builder.addPropertyValue("namingStrategy", ImprovedNamingStrategy.INSTANCE);			
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
	 
			builder.addPropertyValue("hibernateProperties", hp);
			// 注册
			beanFactory.registerBeanDefinition(name + "SessionFactory", builder.getRawBeanDefinition());
		}
	}

	/**
	 * 获得数据源
	 * @param name 名称
	 * @return 数据源
	 */
	private DataSource getDataSource(String name) {
		// 声明数据源
		BasicDataSource ds = new BasicDataSource();
		// 设置属性
		ds.setParse(DaoParams.getParse(name));
		ds.setDriver(DaoParams.getDriver(name));
		ds.setUrl(DaoParams.getUrl(name));
		ds.setUser(DaoParams.getUser(name));
		ds.setPassword(DaoParams.getPassword(name));
		ds.setMaxPoolSize(DaoParams.getMaxPoolSize(name));
		ds.setMinPoolSize(DaoParams.getMinPoolSize(name));
		ds.setMaxSize(DaoParams.getMaxSize(name));
		ds.setTimeout(DaoParams.getTimeout(name));
		ds.setIdleTimeout(DaoParams.getIdleTime(name));
		ds.setInitialPoolSize(DaoParams.getInitialPoolSize(name));
		ds.setMaxIdleTime(DaoParams.getMaxIdleTime(name));
		// 返回数据源
		return ds;
	}
}
