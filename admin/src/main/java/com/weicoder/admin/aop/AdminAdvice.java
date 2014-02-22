package com.weicoder.admin.aop;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.Action;
import com.weicoder.admin.action.AdminAction;
import com.weicoder.admin.exception.AdminException;
import com.weicoder.admin.params.AdminParams;
import com.weicoder.admin.po.Logs;
import com.weicoder.admin.po.LogsLogin;
import com.weicoder.admin.po.Operate;
import com.weicoder.admin.po.Role;
import com.weicoder.base.entity.Entity;
import com.weicoder.base.service.SuperService;
import com.weicoder.common.lang.Lists;
import com.weicoder.common.util.DateUtil;
import com.weicoder.common.util.EmptyUtil;
import com.weicoder.site.action.SiteAction;

/**
 * 权限拦截器
 * @author WD
 * @since JDK7
 * @version 1.0 2011-01-25
 */
@Component
@Aspect
public final class AdminAdvice {
	@Resource
	private SuperService	service;

	/**
	 * 验证权限方法
	 * @param point
	 */
	@Before("execution(* com.weicoder.base.action.SuperAction.add(..)) or execution(* com.weicoder.base.action.SuperAction.adds(..)) or execution(* com.weicoder.base.action.SuperAction.edit(..)) or execution(* com.weicoder.base.action.SuperAction.dels(..)) or execution(* com.weicoder.base.action.SuperAction.del(..)) or execution(* com.weicoder.base.action.SuperAction.trun(..))")
	public void security(JoinPoint point) {
		// 判断是否开启权限验证
		if (AdminParams.SECURITY_POWER) {
			// 获得后台Action
			SiteAction<?> action = (SiteAction<?>) point.getTarget();
			// 是否IP验证
			if (AdminParams.SECURITY_IP) {
				// 获得IP
				String ip = action.getIp();
				// 判断是否在IP列表中
				if (!AdminParams.SECURITY_IPS.contains(ip)) {
					throw new AdminException("not,ip," + ip);
				}
			}
			// link
			String link = action.getLink();
			// 获得操作
			Operate operate = service.get(Operate.class, link);
			// 如果操作为空 直接异常
			if (operate == null) {
				throw new AdminException("not,operate," + link);
			}
			// 判断是否类型验证
			// if (AdminParams.SECURITY_TYPE > 0) {
			// if (AdminParams.SECURITY_TYPE != Conversion.toInt(operate.getType())) {
			// throw new AdminException("not,type=" + operate.getType());
			// }
			// }
			// 判断是否开启角色权限验证
			if (AdminParams.SECURITY_ROLE && action.getToken().isLogin()) {
				// 获得角色
				Role role = service.get(Role.class, action.getToken().getId());
				// 如果角色为空
				if (role == null) {
					throw new AdminException("not,role");
				}
				// 获得自己的权限列表
				// List<Operate> lsOperate = role.getOperates();
				List<Operate> lsOperate = Lists.getList();
				// 不是所有权限 继续判断
				if (EmptyUtil.isEmpty(lsOperate) || !lsOperate.contains(operate)) {
					throw new AdminException("role,operate");
				}
			}
		}
	}

	/**
	 * 后置通知方法 记录登录日志日志
	 * @param point aop切点信息
	 * @param retVal 返回值
	 */
	@AfterReturning(pointcut = "execution(* com.weicoder.site.action.SiteAction.login())", returning = "retVal")
	public void login(JoinPoint point, Object retVal) {
		// 获得登录Login
		SiteAction<?> login = (SiteAction<?>) point.getTarget();
		// 获得用户ID
		int uid = login.getToken().getId();
		// 获得登录key
		String key = login.getLoginKey();
		// 获得IP
		String ip = login.getIp();
		// 判断是否开启登录日志记录
		if (AdminParams.LOGS_LOGIN) {
			// 获得登录状态
			int state = uid > 0 ? 1 : 0;
			// 声明一个新的登录日志实体
			LogsLogin logs = new LogsLogin();
			// 设置属性
			logs.setUserId(uid);
			logs.setName(key);
			logs.setTime(DateUtil.getTime());
			logs.setIp(ip);
			logs.setState(state);
			// 添加到数据库
			service.insert(logs);
		}
	}

	/**
	 * 后置通知方法 记录日志
	 * @param point aop切点信息
	 * @param retVal 返回值
	 */
	@AfterReturning(pointcut = "execution(* com.weicoder.base.action.SuperAction.add(..)) or execution(* com.weicoder.base.action.SuperAction.adds(..)) or execution(* com.weicoder.base.action.SuperAction.edit(..)) or execution(* com.weicoder.base.action.SuperAction.dels(..)) or execution(* com.weicoder.base.action.SuperAction.del(..)) or execution(* com.weicoder.base.action.SuperAction.trun(..))", returning = "retVal")
	public void logs(JoinPoint point, Object retVal) {
		// 判断是否开启操作日志记录
		if (AdminParams.LOGS) {
			// 获得后台Action
			AdminAction action = (AdminAction) point.getTarget();
			// 获得登录状态
			int state = Action.ERROR.equals(retVal) ? 0 : 1;
			// 获得提交的连接
			String link = action.getLink();
			// 获得操作实体
			Entity entity = action.getEntity();
			// 获得删除的IDS
			Serializable[] keys = action.getKeys();
			// 添加日志
			Logs logs = new Logs();
			// 设置用户ID
			logs.setUserId(action.getToken().getId());
			logs.setTime(DateUtil.getTime());
			logs.setState(state);
			logs.setName(link);
			logs.setIp(action.getIp());
			// 判断操作
			if (EmptyUtil.isEmpty(keys)) {
				// 判断实体不为空
				if (!EmptyUtil.isEmpty(entity)) {
					logs.setContent(entity.toString());
				}
			} else {
				// 删除多个数据
				logs.setContent(Arrays.toString((keys)));
			}
			// 记录日志
			service.insert(logs);
		}
	}
}