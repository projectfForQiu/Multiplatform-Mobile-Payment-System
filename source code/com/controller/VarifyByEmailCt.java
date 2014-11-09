package com.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.domain.RegisterBean;
import com.domain.UserTableBean;
import com.helper.RegisterHelper;
import com.helper.UserTableHelper;
import com.util.md5;
import com.util.mail.MailHelper;

public class VarifyByEmailCt extends HttpServlet {

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
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the GET method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
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

		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		//获取数据
		String dataByEmail=request.getParameter("dataByEmail");
		JSONObject jsonobj=JSONObject.fromObject(dataByEmail);
		/*
		 * 将数据userId,rgtype,loginPassword转成一个register表，再插入到register表
		 */
		RegisterBean register=new RegisterBean();
		register.setuserId(jsonobj.getString("userId"));
		register.setRgtype("person");
		register.setLoginPassword(jsonobj.getString("loginPassword"));
		int flag1=RegisterHelper.registerInsert(register);
		
		/*
		 * 将数据转成一个usertable表，再插入到usertable表
		 */
		UserTableBean usertable=new UserTableBean();
		usertable.setNumber(jsonobj.getString("email"));
		usertable.setState("Unactive");
		usertable.setType("Email");
		usertable.setUserId(jsonobj.getString("userId"));
		int flag2=UserTableHelper.userTableInsert(usertable);
		
		
		
		/*
		 * 若都插入上述表，则加密email,userId到一字符串
		 * 并发出向该email发出邮件。
		 */
		if(flag1==1&&flag2==1){
			md5 md=new md5();
			//将userId加密,email直接发送
			String codeStr=md.calcMD5(usertable.getUserId());
			String data="email="+usertable.getNumber()+"&userId="+codeStr;
			MailHelper.sendMail(data,usertable.getNumber());
			String Varification=(String) request.getAttribute("Varification");
			if(Varification.equals("true")){
				out.println("The Varification is done!");
			}
		}
		
		
	}

}
