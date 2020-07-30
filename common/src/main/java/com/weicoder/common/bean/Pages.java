package com.weicoder.common.bean;

import java.util.List;

import com.weicoder.common.params.CommonParams;

import lombok.Data;

/**
 * 分页信息保存的实体Bean
 * 
 * @author WD
 */
@Data
public final class Pages {
	// 总数量
	private int total;
	// 当前页
	private int page;
	// 每页显示数量
	private int size = CommonParams.PAGE_SIZE;

	/**
	 * 返回包括本身的分页结果
	 * 
	 * @param list
	 * @return
	 */
	public PageResult result(List<?> list) {
		return new PageResult(list, this);
	}

	/**
	 * 获得总页数
	 * 
	 * @return 总页数
	 */
	public int getTotalPage() {
		return total < size ? 0 : total % size == 0 ? total / size : total / size + 1;
	}

	/**
	 * 获得开始页码
	 * 
	 * @return 开始页码
	 */
	public int getStartPage() {
		return page - 5 > 0 ? page - 5 : 0;
	}

	/**
	 * 获得结束页码
	 * 
	 * @return 结束页码
	 */
	public int getEndPage() {
		// 开始页
		int current = getPage();
		// 总页数
		int total = getTotalPage();
		// 返回结束页
		return (current == 1 || current < 6) ? (total > 10 ? 10 : total) : (current + 5 <= total ? current + 5 : total);
	}

	/**
	 * 获得最大结果数
	 * 
	 * @return 最大结果数
	 */
	public int getEnd() {
		return (getPage() + 1) * getSize();
	}

	/**
	 * 获得从第N条开始
	 * 
	 * @return 从第N条开始返回结果
	 */
	public int getStart() {
		return getPage() * getSize();
	}
}
