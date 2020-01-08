package com.weicoder.admin.listener;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.weicoder.admin.po.Admin;
import com.weicoder.common.lang.Conversion;
import com.weicoder.common.log.Logs;
import com.weicoder.common.util.EmptyUtil;
import com.weicoder.common.util.ResourceUtil;
import com.weicoder.common.util.StringUtil;
import com.weicoder.dao.service.SuperService;

/**
 * 后台初始化监听器
 * 
 * @author  WD 
 */
@WebListener
public class AdminInitListener implements ServletContextListener {
	/**
	 * 初始化资源
	 */
	public void contextInitialized(ServletContextEvent event) {
		// 如果没有管理员 初始化
		if (SuperService.DAO.count(Admin.class) == 0) {
			// 读取初始化文件
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(ResourceUtil.loadResource("init.sql")))) {
				reader.lines().forEach(sql -> {
					if (EmptyUtil.isNotEmpty(Conversion.toString(sql)) && !StringUtil.startsWith(sql, "#")) {
						SuperService.DAO.execute(Admin.class, sql);
					}
				});
			} catch (Exception e) {
				Logs.error(e);
			}
			Logs.info("init admin.....");
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}
}