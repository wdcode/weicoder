package com.weicoder.hibernate.session;

import java.io.File;
import java.util.List;
import java.util.Map;

import jakarta.persistence.Entity;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl;
import org.hibernate.cfg.Configuration;

import com.weicoder.hibernate.interceptor.EntityInterceptor;
import com.weicoder.hibernate.naming.ImprovedNamingStrategy;
import com.weicoder.common.lang.Lists;
import com.weicoder.common.lang.Maps;
import com.weicoder.common.lang.W;
import com.weicoder.common.log.Logs;
import com.weicoder.common.util.ResourceUtil;
import com.weicoder.common.util.StringUtil;
import com.weicoder.common.util.U.C;

/**
 * SessionFactory包装类
 * 
 * @author WD
 */
public final class SessionFactorys {
	// 所有SessionFactory
	private List<SessionFactory> factorys;
	// 类对应SessionFactory
	private Map<Class<?>, SessionFactory> entity_factorys;
	// 保存单session工厂 只有一个SessionFactory工厂时使用
	private SessionFactory factory;
	// 保存实体对象类
	private Map<String, Class<?>> entitys;

	/**
	 * 初始化
	 */
	public SessionFactorys() {
		// 实例化表列表
		entity_factorys = Maps.newMap();
		factorys = Lists.newList();
		entitys = Maps.newMap();
		// 初始化SessionFactory
		initSessionFactory();
		// 如果只有一个SessionFactory
		if (factorys.size() == 1)
			factory = factorys.get(0);
	}

	/**
	 * 根据实体类获得SessionFactory
	 * 
	 * @param  entity 实体类
	 * @return        SessionFactory
	 */
	public SessionFactory getSessionFactory(Class<?> entity) {
		return factory == null ? entity_factorys.get(entity) : factory;
	}

	/**
	 * 根据类名找到对应的实体类
	 * 
	 * @param  name
	 * @return
	 */
	public Class<?> entity(String name) {
		return entitys.get(name);
	}

	/**
	 * 获得当前Session
	 * 
	 * @param  entity 类
	 * @return        Session
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
//		// 优先加载测试文件夹
		String path = "db/";
//		// 获得数据库配置文件
		File file = ResourceUtil.newFile(path);
		Logs.debug("hibernate initSessionFactory config={}", file);

		// 不为空
		if (file != null) {
			// 循环生成
			for (String name : file.list()) {
				// 实例化hibernate配置类
				Configuration config = new Configuration().configure(ResourceUtil.getResource("hibernate.xml"));
				try {
					config.getProperties().load(ResourceUtil.loadResource(path + name));
				} catch (Exception e) {
					Logs.error(e);
				}
				config.setProperty("hibernate.hikari.jdbcUrl",
						String.format(W.C.toString(config.getProperty("hibernate.hikari.jdbcUrl")),
								W.C.toString(config.getProperty("url"))));
				String url = "jdbc:mysql://%s?useLocalSessionState=true&haracterEncoding=utf8&useSSL=true&verifyServerCertificate=false&serverTimezone=UTC";
				config.setProperty("url", String.format(url, W.C.toString(config.getProperty("url"))));
				config.setProperty("hibernate.hikari.username", W.C.toString(config.getProperty("username")));
				config.setProperty("hibernate.hikari.password", W.C.toString(config.getProperty("password")));
//				config.getEntityTuplizerFactory().registerDefaultTuplizerClass(EntityMode.POJO, ModPojoEntityTuplizer.class);
//				config.getEntityTuplizerFactory().registerDefaultTuplizerClass(entityMode, tuplizerClass);
//				// 声明实体列表
//				List<Class<Entity>> list = C.getAnnotationClass(config.getProperty("package"), Entity.class);
				List<Class<Entity>> list = C.pack(config.getProperty("package"), Entity.class);
				// 声明实体列表
//				List<Class<com.weicoder.hibernate.entity.Entity>> list = ClassUtil.getAssignedClass(config.getProperty("package"), com.weicoder.hibernate.entity.Entity.class);
				// 根据包名获取对象实体
				list.forEach(e -> config.addAnnotatedClass(e));
				Logs.info("load hibernate name={}", name);
				// 设置namingStrategy
				config.setImplicitNamingStrategy(ImplicitNamingStrategyJpaCompliantImpl.INSTANCE);
				config.setPhysicalNamingStrategy(ImprovedNamingStrategy.INSTANCE);
				// 设置分表过滤器
				config.setInterceptor(EntityInterceptor.INSTANCE);
				// 注册 并添加实体对应工厂
				SessionFactory sf = config.buildSessionFactory();
				factorys.add(sf);
				list.forEach(e -> {
					entity_factorys.put(e, sf);
					entitys.put(StringUtil.convert(e.getSimpleName()), e);
				});
			}
		}
	}
}
