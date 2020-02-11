package com.weicoder.admin.action;

import static com.weicoder.dao.service.SuperService.DAO;

import java.io.Serializable;
import java.util.Map;

import com.weicoder.common.bean.Pages;
import com.weicoder.common.util.EmptyUtil;
import com.weicoder.dao.service.SuperService;
import com.weicoder.web.annotation.Action;
import com.weicoder.web.annotation.State;
import com.weicoder.web.validator.annotation.Token;

/**
 * 超级通用Action
 * 
 * @author WD
 */
@Action
@State
@Token
public class EntityAction {
	/**
	 * 添加
	 * 
	 * @param  entity 实体
	 * @return
	 */
	public Object add(String entity, Map<String, String> ps) {
		return SuperService.entity(entity, ps, e -> DAO.insert(e));
	}

	/**
	 * 添加
	 * 
	 * @param  entity 实体
	 * @return
	 */
	public Object updata(String entity, Map<String, String> ps) {
		return SuperService.entity(entity, filter(ps), e -> DAO.update(e));
	}

	/**
	 * 删除
	 * 
	 * @return
	 */
	public Object delete(String entity, Map<String, String> ps) {
		return SuperService.entity(entity, filter(ps), e -> DAO.delete(e));
	}

	/**
	 * 查询所有
	 * 
	 * @return
	 */
	public Object all(String entity) {
		return SuperService.entity(entity, c -> SuperService.all(c));
	}

	/**
	 * 分页排序查询
	 * 
	 * @return
	 */
	public Object order(String entity, Map<String, Object> orders, Pages page, Map<String, String> ps) {
		return SuperService.entity(entity, filter(ps), e -> SuperService.order(e, orders, page));
	}

	/**
	 * 分页查询
	 * 
	 * @return
	 */
	public Object date(String entity, String begin, String end, Pages page, Map<String, String> ps) {
		return SuperService.entity(entity, filter(ps), e -> SuperService.date(e, begin, end, page));
	}

	/**
	 * 实体条件查询出所有
	 * 
	 * @return
	 */
	public Object get(String entity, Serializable key) {
		return SuperService.entity(entity, c -> DAO.get(c, key));
	}

	/**
	 * 实体条件查询出所有
	 * 
	 * @return
	 */
	public Object gets(String entity, Serializable... pks) {
		return SuperService.entity(entity, c -> DAO.gets(c, pks));
	}

	/**
	 * 实体条件查询出所有
	 * 
	 * @return
	 */
	public Object entity(String entity, Map<String, String> ps) {
		return SuperService.entity(entity, filter(ps), e -> DAO.get(e));
	}

	/**
	 * 跳转到列表页
	 * 
	 * @return
	 */
	public Object list(String entity, String begin, String end, Pages page, Map<String, String> ps) {
		return SuperService.entity(entity, filter(ps), e -> {
			// 如果日期不为空 按日期查询
			if (EmptyUtil.isNotEmptys(begin, end))
				return SuperService.date(e, begin, end, page);
			else
				return SuperService.list(e, page);
		});
	}

	/**
	 * 获得数量
	 * 
	 * @return
	 */
	public Object count(String entity, Map<String, String> ps) {
		return SuperService.entity(entity, filter(ps), e -> DAO.count(e));
	}

	/**
	 * 过滤参数
	 * 
	 * @param  ps
	 * @return
	 */
	private Map<String, String> filter(Map<String, String> ps) {
		ps.remove("ip");
		ps.remove("time");
		return ps;
	}
}
