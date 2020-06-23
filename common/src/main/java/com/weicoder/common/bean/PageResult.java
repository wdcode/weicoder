package com.weicoder.common.bean;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 保存分页查询返回结果
 * 
 * @author wudi
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public final class PageResult {
	// 数据列表
	private List<?> list;
	// 分页页码
	private Pages pager;
}
