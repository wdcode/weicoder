package com.weicoder.test;

import com.weicer.rpc.annotation.RpcBean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wudi
 */
@RpcBean(name = "rpct", method = "user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RpcB {
	private long uid;
	private String name;
}
