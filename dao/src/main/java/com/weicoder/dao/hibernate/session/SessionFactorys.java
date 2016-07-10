package com.weicoder.dao.hibernate.session;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl;
import org.hibernate.cfg.Configuration;
import org.hibernate.engine.spi.SessionFactoryImplementor;

import com.weicoder.dao.hibernate.interceptor.EntityInterceptor;
import com.weicoder.dao.hibernate.naming.ImprovedNamingStrategy;
import com.weicoder.dao.params.FrameParams;
import com.weicoder.common.lang.Lists;
import com.weicoder.common.lang.Maps;
import com.weicoder.common.util.ClassUtil;
import com.weicoder.common.util.ResourceUtil;

/**
 * SessionFactory包装类
 * @author WD
 */
public final class SessionFactorys {
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
		for (Class<?> e : ClassUtil.getAnnotationClass(Entity.class)) {
			// 循环获得SessionFactory
			for (SessionFactory sessionFactory : factorys) {
				try {
					if (((SessionFactoryImplementor) sessionFactory).getMetamodel().entity(e) != null) {
						entity_factorys.put(e, sessionFactory);
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
		return factory == null ? entity_factorys.get(entity) : factory;
	}

	/**
	 * 获得当前Session
	 * @param entity 类
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
	 * 初始化SessionFactory
	 */
	private void initSessionFactory() {
		// 获得数据库配置文件
		File file = ResourceUtil.getFile(FrameParams.DB_CONFIG);
		// 不为空
		if (file != null) {
			// 循环生成
			for (String name : file.list()) {
				// 实例化hibernate配置类
				Configuration config = new Configuration().configure(FrameParams.DB_CONFIG + name);
				// 设置namingStrategy
				config.setImplicitNamingStrategy(ImplicitNamingStrategyJpaCompliantImpl.INSTANCE);
				config.setPhysicalNamingStrategy(ImprovedNamingStrategy.INSTANCE);
				// 设置分表过滤器
				config.setInterceptor(EntityInterceptor.INSTANCE);
				// 注册
				factorys.add(config.buildSessionFactory());
			}
		}
	}
}
