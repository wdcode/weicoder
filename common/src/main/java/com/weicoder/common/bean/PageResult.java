package com.weicoder.common.bean;

import java.util.List;
import java.util.Objects;

/**
 * 保存分页查询返回结果
 * 
 * @author wudi
 */
public final class PageResult {
	// 数据列表
	private List<?>	list;
	// 分页页码
	private Pages	pager;

	public PageResult() {
	}

	public PageResult(List<?> list, Pages pager) {
		super();
		this.list = list;
		this.pager = pager;
	}

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

	@Override
	public int hashCode() {
		return Objects.hash(list, pager);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PageResult other = (PageResult) obj;
		return Objects.equals(list, other.list) && Objects.equals(pager, other.pager);
	}

	@Override
	public String toString() {
		return "PageResult [list=" + list + ", pager=" + pager + "]";
	}
}
