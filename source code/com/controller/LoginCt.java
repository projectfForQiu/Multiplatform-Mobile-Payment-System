package com.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.domain.RegisterBean;
import com.domain.LoginMessageBean;
import com.helper.RegisterHelper;
import com.helper.LoginMessageHelper;
import com.util.JSONReader;

public class LoginCt extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request,response);

	}


	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("@json:test;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		//接受数据
		String data=request.getParameter("data");
		
		/**
		 * 处理传过来的JSON
		 */
		JSONObject jsonobj=JSONObject.fromObject(data);
	    RegisterBean register=new RegisterBean();
		register.setuserId(jsonobj.getString("userId"));
		register.setLoginPassword(jsonobj.getString("loinPassword"));
		
		
		//验证账号
		RegisterHelper helper=new RegisterHelper();
		//要返回的验证消息
		String CheckCodeMessage=null;
		int status=helper.checkAccount(register);
		if(status==1){
			CheckCodeMessage="The password is correct.";
		}
		if(status==0){
			CheckCodeMessage="The password is wrong.";
		}
		if(status==-1){
			CheckCodeMessage="The user is not exist.";
		}
		
		/**
		 * 向loginMessage表中加入信息
		 */
		String DBMessage=null;
		if(status==1){
			LoginMessageBean logMessage=new LoginMessageBean();
			logMessage.setUserId(jsonobj.getString("userId"));
			logMessage.setLoginAddress(request.getRemoteHost());//获取登录地址
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
			logMessage.setLoginTime(df.format(new Date()));
			LoginMessageHelper lMHelper=new LoginMessageHelper();//使用loginMessage帮助类
			/**
			 * 根据status判断数据是否插入loginMessage表
			 */
			status=lMHelper.setLoginMessage(logMessage);
			if(status==1){
				DBMessage="We have write the loginMessage into the database.";
			}
			if(status==0){
				DBMessage="Error.";
			}
		}
		
		/**
		 * 将处理的数据打包成JSONObject，传回客户端
		 */
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("CheckCodeMessage", CheckCodeMessage);
		jsonObject.put("DBMessage", DBMessage);
		 
		out.print(jsonObject);
		
		
		
	}

}
