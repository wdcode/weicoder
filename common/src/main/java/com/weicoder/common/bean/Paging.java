package com.weicoder.common.bean;

import com.weicoder.common.params.CommonParams;

/**
 * 分页信息保存的实体Bean
 * 
 * @author WD
 */
public final class Paging {
	// 总数量
	private int total;
	// 当前页
	private int page;
	// 每页显示数量
	private int size = CommonParams.PAGE_SIZE;

//	/**
//	 * 获得总页数
//	 * 
//	 * @return 总页数
//	 */
//	public int getTotalPage() {
//		return total < size ? 0 : total % size == 0 ? total / size : total / size + 1;
//	}

	/**
	 * 获得每页显示数量
	 * 
	 * @return 每页显示数量
	 */
	public int getSize() {
		return size;
	}

	/**
	 * 设置每页显示数量
	 * 
	 * @param size 每页显示数量
	 */
	public void setSize(int size) {
		this.size = size;
	}

	/**
	 * 获得总数量
	 * 
	 * @return 总数量
	 */
	public int getTotal() {
		return total;
	}

	/**
	 * 设置总数量
	 * 
	 * @param total 总数量
	 */
	public void setTotal(int total) {
		this.total = total;
	}

	/**
	 * 获得当前显示页
	 * 
	 * @return 当前显示页
	 */
	public int getPage() {
		return page;
	}

	/**
	 * 设置当前显示页
	 * 
	 * @param page 当前显示页
	 */
	public void setPage(int page) {
		this.page = page;
	}

//	/**
//	 * 获得开始页码
//	 * 
//	 * @return 开始页码
//	 */
//	public int getStartPage() {
//		return page - 5 > 0 ? page - 5 : 0;
//	}

//	/**
//	 * 获得结束页码
//	 * 
//	 * @return 结束页码
//	 */
//	public int getEndPage() {
//		// 开始页
//		int current = getPage();
//		// 总页数
//		int total = getTotalPage();
//		// 返回结束页
//		return (current == 1 || current < 6) ? (total > 10 ? 10 : total) : (current + 5 <= total ? current + 5 : total);
//	}

	/**
	 * 获得最大结果数
	 * 
	 * @return 最大结果数
	 */
	public int getMaxResults() {
		return (getPage() + 1) * getSize();
	}

	/**
	 * 获得从第N条开始返回结果
	 * 
	 * @return 从第N条开始返回结果
	 */
	public int getFirstResult() {
		return getPage() * getSize();
	}

	@Override
	public String toString() {
		return "Pages [page=" + page + ", size=" + size + "]";
	}
}
