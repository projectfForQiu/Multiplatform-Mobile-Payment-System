package com.helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import com.domain.RegisterBean;
import com.domain.UserTableBean;
import com.util.SqlHelper;
/**
 * 登录与注册的操作类
 * @author Lee
 *
 */
public class RegisterHelper {

	Connection ct=null;
	PreparedStatement ps=null;
	static ResultSet rs=null;
	
	
	/**
	 * 校验账号函数
	 * @param register
	 * @return
	 */
	public int checkAccount(RegisterBean register){
		int flag=-1;//验证消息
		try {
			String sql="select* from register where userId=?";
			String parameters[]={register.getuserId()};
			System.out.println(parameters[0]);
			rs=SqlHelper.executeQuery(sql, parameters);
			if(rs.next()){
				if(rs.getString(4).equals(register.getLoginPassword())){
					flag=1;
					return flag;
				}else{
					flag=0;
					return flag;
				}
			}else{
				return flag;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return flag;
		}
	}

	
	/**
	 * 校验用户账号
	 * @param 账号
	 * @return 0代表number已存在，1代表number不存在
	 */
	public static int checkUserId(String userId){
		int flag=0;
		try {
			String sql="select* from register where userId=?";
			String []parameters={userId};
			rs=SqlHelper.executeQuery(sql, parameters);
			if(rs.next()){
				flag=1;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
		
	}

//	/**
//	 * 用邮箱注册
//	 * 将信息插入register表
//	 * @param jsonobj
//	 * @return 0代表插入失败，1代表插入成功
//	 */
//	public static int registerByEmail(JSONObject jsonobj){
//		//取出账号
//		String userId=jsonobj.getString("userId");
//		//放入register表中
//		RegisterBean register=new RegisterBean();
//		register.setuserId(userId);
//		//取出昵称
//		String name=jsonobj.getString("name");
//		register.setName(name);
//		//取出注册类型
//		String Rgtype=jsonobj.getString("Rgtype");
//		register.setRgtype(Rgtype);
//		//取出登录密码
//		String loginPassword=jsonobj.getString("loginPassword");
//		register.setLoginPassword(loginPassword);
//		//将register插入表中
//		int flag=registerInsert(register);
//		return flag;
//		
//	}
	
	/**
	 * 将信息插入register表
	 * @param register
	 * @return 0代表插入失败，1代表插入成功
	 */
	public static int registerInsert(RegisterBean register){
		
		String sql="insert into register values(?,?,?,?,?)";
		String []parameters={register.getuserId(),register.getName(),register.getRgtype(),register.getLoginPassword(),"NULL"};
		int flag=SqlHelper.executeUpdate(sql, parameters);
		return flag;
		
	}
	
	
}

