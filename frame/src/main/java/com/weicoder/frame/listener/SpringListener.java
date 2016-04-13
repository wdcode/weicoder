package com.weicoder.frame.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;

import org.springframework.web.context.ContextLoaderListener;

import com.weicoder.frame.context.Contexts;

/**
 * Spring监听器
 * @author WD
 * @version 1.0
 */
@WebListener
public class SpringListener extends ContextLoaderListener {

	@Override
	public void contextInitialized(ServletContextEvent event) {
		event.getServletContext().setInitParameter("contextConfigLocation", "classpath:config/spring.xml");
		super.contextInitialized(event);
		Contexts.init(ContextLoaderListener.getCurrentWebApplicationContext());
	}
}
