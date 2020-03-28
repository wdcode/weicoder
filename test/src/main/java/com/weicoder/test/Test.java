package com.weicoder.test;
 
import com.weicoder.common.U.B;
import com.weicoder.common.U.C;

public class Test {

	public static void main(String[] args) throws Exception {
		C.getPublicMethod(RpcS.class).forEach(m -> {
			System.out.println(m.getName() + "=" + m.getReturnType() + "=" + (void.class.equals(m.getReturnType())));
		});
		System.out.println(Void.class);
		System.out.println(B.invoke(new RpcS(), "t"));
	}
}
