package com.weicoder.test;

import com.weicoder.rpc.annotation.RpcServer;
import com.weicoder.common.log.Logs;

@RpcServer
public final class RpcS implements Irpc {

	@Override
	public String test(int i) {
		StringBuilder sb = new StringBuilder("rpc test res=");
		for (int n = 0; n < 100000; n++)
			sb.append(n);
		Logs.info("test={}", sb.length());
		return sb.toString();
	}

	@Override
	public Users get(long i) {
		return new Users(i, true, Byte.MAX_VALUE, Short.MAX_VALUE, 1, 1F, 1D, null, null);
	}

	@Override
	public Users user(RpcB b) {
		return new Users(b.getUid(), true, Byte.MIN_VALUE, Short.MIN_VALUE, 2, 2F, 2D, b.getName(), null);
	}
	
	public void t() {
		throw new RuntimeException("tet");
	}
}
