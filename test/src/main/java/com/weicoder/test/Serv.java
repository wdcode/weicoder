package com.weicoder.test;

import com.weicoder.common.annotation.Service;
import com.weicoder.dao.Dao;

@Service
public class Serv {
	private Dao dao;

	public Dao dao() {
		return dao;
	}
}
