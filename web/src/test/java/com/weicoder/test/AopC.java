package com.weicoder.test;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.weicoder.web.aop.AopAll;

public class AopC implements AopAll {

	@Override
	public void before(Object action, Object[] params, HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub

	}

	@Override
	public void after(Object action, Object[] params, Object result, HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub

	}

}
