package com.weicoder.frame.servlet;

import javax.servlet.annotation.WebServlet;

import com.weicoder.web.servlet.BasicServlet;

/**
 * WEB框架基础Servlet拦截
 * @author WD
 */
@WebServlet({ "/*" })
public class FrameServlet extends BasicServlet {
	private static final long serialVersionUID = 3127452546968363359L;
}