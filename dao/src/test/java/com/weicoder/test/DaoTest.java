package com.weicoder.test;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedType;

import org.junit.jupiter.api.Test;
 
import com.weicoder.common.util.U.C;
import com.weicoder.dao.service.SuperService;

public class DaoTest {
	@Test
	public void main() {
		C.getInterfaces(User.class).forEach(System.out::println);
		for (Annotation a : User.class.getAnnotations())
			System.out.println(a);
		for (AnnotatedType a : User.class.getAnnotatedInterfaces())
			System.out.println(a);

		SuperService.DAO.insert(new User().setUid(1001L));
		System.out.println(SuperService.all(User.class));
	}
}
