package com.weicoder.dao.bean;

import java.util.List;

import com.weicoder.common.bean.Pages;

/**
 * 保存分页查询返回结果
 * 
 * @author wudi
 */
public final class PageResult {
	// 数据列表
	private List<?> list;
	// 分页页码
	private Pages pager;

	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}

	public Pages getPager() {
		return pager;
	}

	public void setPager(Pages pager) {
		this.pager = pager;
	}

	public PageResult(List<?> list, Pages pager) {
		this.list = list;
		this.pager = pager;
	}

	public PageResult() {
	}
}
