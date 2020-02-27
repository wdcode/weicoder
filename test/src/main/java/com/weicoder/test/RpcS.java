package com.weicoder.test;
 
 
public class RpcS implements Irpc {

	@Override
	public String test(int i) {
		return "rpc test res=" + i;
	}

	@Override
	public Users get(long i) {
		return new Users(true, Byte.MAX_VALUE, Short.MAX_VALUE, 1, 1F, i, 1D, null, null);
	}

	@Override
	public Users user(RpcB b) {
		return new Users(true, Byte.MIN_VALUE, Short.MIN_VALUE, 2, 2F, b.getUid(), 2D, b.getName(), null);
	}
}
