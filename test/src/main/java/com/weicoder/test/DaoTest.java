package com.weicoder.test;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedType;

import com.weicoder.common.U.C; 

public class DaoTest {

	public static void main(String[] args) {
		C.getInterfaces(User.class).forEach(System.out::println);
		for(Annotation a : User.class.getAnnotations())
			System.out.println(a);
		for(AnnotatedType a : User.class.getAnnotatedInterfaces())
			System.out.println(a);
			
//		SuperService.DAO.insert(new User().setUid(1001L));
//		 System.out.println(SuperService.all(User.class));
	} 
}
