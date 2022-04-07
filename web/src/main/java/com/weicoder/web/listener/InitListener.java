package com.weicoder.web.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import com.weicoder.common.init.Inits; 

/**
 * 初始化监听器
 * 
 * @author WD
 */
@WebListener
public class InitListener implements ServletContextListener {
	/**
	 * 初始化资源
	 */
	public void contextInitialized(ServletContextEvent event) {
		Inits.init();
	}
}
