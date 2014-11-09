package com.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.domain.RegisterBean;
import com.helper.RegisterHelper;
/**
 * 处理登录信息
 * @author Lee
 *
 */
public class LoginCtForWeb extends HttpServlet {

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		doPost(request,response);
		out.close();
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Enumeration<String> headers=request.getHeaderNames();
		while(headers.hasMoreElements()){
			String headername=headers.nextElement();
			System.out.println(headername+"= "+request.getHeader(headername));
		}


		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		String checkCode=request.getParameter("checkCode");//获取验证码
		System.out.println(checkCode);
		HttpSession session=request.getSession();
		String validateCode=(String) session.getAttribute("validateCode");
		System.out.println(validateCode);
		//检测验证码
		if(checkCode.equals(validateCode)){
			/**
			 * @author Lee
			 * 获取从Login.jsp传来的账号ID与密码，放在RegisterBean对象里
			 */
			
			
			RegisterBean register=new RegisterBean();
			register.setuserId(request.getParameter("userId"));
			register.setLoginPassword(request.getParameter("loginPassword"));
			
			/**
			 * @author Lee
			 * 校验密码
			 */
			RegisterHelper regHelper=new RegisterHelper();
			if(regHelper.checkAccount(register)){
				out.println("验证成功");
			}
		}else{
			System.out.println("验证码错误！");
		}
	}

}
