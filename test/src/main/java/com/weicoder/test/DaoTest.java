package com.weicoder.test;

import com.weicoder.dao.service.SuperService;
import com.weicoder.po.user.User;

public class DaoTest {

	public static void main(String[] args) {
//		SuperService.DAO.insert(new User().setUid(1001L));
		 System.out.println(SuperService.all(User.class));
	} 
}
