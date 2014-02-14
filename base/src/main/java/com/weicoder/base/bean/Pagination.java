package com.weicoder.base.bean;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import com.weicoder.base.params.BaseParams;
import com.weicoder.core.json.JsonEngine;

/**
 * 分页信息保存的实体Bean 在分页Dao和Tag之间传递值用
 * @author WD
 * @since JDK7
 * @version 1.0 2009-04-21
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public final class Pagination {
	// 总数量
	private int	totalSize;
	// 当前页
	private int	currentPage	= 1;
	// 每页显示数量
	private int	pageSize	= BaseParams.PAGE_SIZE;

	/**
	 * 获得总页数
	 * @return 总页数
	 */
	public int getTotalPage() {
		return totalSize == 0 ? 1 : totalSize % pageSize == 0 ? totalSize / pageSize : totalSize / pageSize + 1;
	}

	/**
	 * 获得每页显示数量
	 * @return 每页显示数量
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 设置每页显示数量
	 * @param pageSize 每页显示数量
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * 获得总数量
	 * @return 总数量
	 */
	public int getTotalSize() {
		return totalSize;
	}

	/**
	 * 设置总数量
	 * @param totalSize 总数量
	 */
	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}

	/**
	 * 获得当前显示页
	 * @return 当前显示页
	 */
	public int getCurrentPage() {
		return currentPage;
	}

	/**
	 * 设置当前显示页
	 * @param currentPage 当前显示页
	 */
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	/**
	 * 获得开始页码
	 * @return 开始页码
	 */
	public int getStartPage() {
		return currentPage - 5 > 0 ? currentPage - 5 : 1;
	}

	/**
	 * 获得结束页码
	 * @return 结束页码
	 */
	public int getEndPage() {
		// 开始页
		int current = getCurrentPage();
		// 总页数
		int total = getTotalPage();
		// 返回结束页
		return (current == 1 || current < 6) ? (total > 10 ? 10 : total) : (current + 5 <= total ? current + 5 : total);
	}

	@Override
	public String toString() {
		return JsonEngine.toJson(this);
	}
}
