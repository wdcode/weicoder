package com.weicoder.common.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 返回结果
 * 
 * @author wdcode
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Result<T> {
	// 状态码
	protected int code;
	// 内容
	protected T content;
	// 状态码对应信息
	protected String message;
}
