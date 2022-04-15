package com.weicoder.test;
//
//import java.lang.reflect.ParameterizedType;
//import java.lang.reflect.Type;
//
//import com.weicoder.cache.BeanCache;
//import com.weicoder.cache.CacheBuilder;
//import com.weicoder.common.U;
//import com.weicoder.common.C.O;
//import com.weicoder.common.U.B;
//import com.weicoder.common.U.C;
//import com.weicoder.common.annotation.Ioc;
//import com.weicoder.common.annotation.Service;
//import com.weicoder.common.crypto.Encrypts;
//import com.weicoder.common.http.factory.HttpFactory;
//import com.weicoder.common.log.Logs;

public class Test {

	public static void main(String[] args) throws Exception {
		// 指数位数
		int two = 2, three = 3;
		int dig = 128;
		for (int i = 1; i <= dig; i++)
			System.out.println("位数=" + i + ";二进制=" + Math.pow(two, i)+ ";三进制=" + Math.pow(three, i));
//		System.out.println(Encrypts.rc4("twete".getBytes(), ""));
//		Logs.info("123321");
//		System.out.println(O.BASE_DIR);
////		Serv s = new Serv();
////		System.out.println(s.dao());
////		C.ioc(s);
////		System.out.println(User.class.getField("uid"));
//		System.out.println(Serv.class.isAnnotationPresent(Service.class));
//		System.out.println(Serv.class.isAnnotationPresent(Ioc.class));
//		System.out.println(C.getGenericClass(HttpFactory.class, 0));
//		System.out.println(User.class.getDeclaredField("uid"));
//		BeanCache<Long, Users> c = CacheBuilder.build("users", k -> {
//			return new Users();
//		});
//		for(Type t : ((ParameterizedType) c.getClass().getGenericSuperclass()).getActualTypeArguments()) {
//			System.out.println(t.getClass());
//			U.C.getPublicMethod(t.getClass()).forEach(m->System.out.println(B.invoke(t, m)));
//		} 
//		System.out.println(C.getGenericClass(c.getClass()));
//		System.out.println(C.getGenericClass(c.getClass().getTypeParameters().getClass()));
//		System.out.println(C.list(Json.class));
//		new Base().put();
//		new Ma().put();
//		String[] s = new String[1];
//		long[] l = new long[1];
//		int[] i = new int[1];
//		Class<?> cs = s.getClass();
//		Class<?> ls = l.getClass();
//		Class<?> is = i.getClass(); 
//		System.out.println(cs);
//		System.out.println(cs.getNestHost());
//		System.out.println(cs.getComponentType());
//		System.out.println(cs.isArray());
//		System.out.println(ls);
//		System.out.println(ls.getNestHost());
//		System.out.println(ls.getComponentType());
//		System.out.println(ls.isArray());
//		System.out.println(is);
//		System.out.println(is.getNestHost());
//		System.out.println(is.getComponentType());
//		System.out.println(is.isArray());
//		System.out.println("------------"); 
//		System.out.println(int.class);
//		System.out.println(U.C.forName("java.lang.Integer"));
//		System.out.println(U.C.forName("java.lang.Long"));
//		System.out.println(U.C.forName("int"));
//		System.out.println(U.C.forName("long"));
	}
}
