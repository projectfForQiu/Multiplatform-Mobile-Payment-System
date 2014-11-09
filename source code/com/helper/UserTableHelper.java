package com.helper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.domain.UserTableBean;
import com.util.SqlHelper;

public class UserTableHelper {
	Connection ct=null;
	PreparedStatement ps=null;
	static ResultSet rs=null;

	/**
	 * 将信息插入userTable表
	 * @param usertable
	 * @return 0代表插入失败，1代表插入成功
	 */
	public static int userTableInsert(UserTableBean usertable){
		int flag=0;
		String sql="insert into userTable values(?,?,?,?)";
		String []parameters={usertable.getNumber(),usertable.getType(),usertable.getUserId(),usertable.getState()};
		flag=SqlHelper.executeUpdate(sql, parameters);
		return flag;
		
	}

	/**
	 * 校验邮箱或手机号码
	 * @author Lee
	 * @param number
	 * @return 0代表number已存在，1代表number不存在
	 */
	public static int checkNumber(String number){
		int flag=0;
		try {
			String sql="select* from userTable where number=?";
			String []parameters={number};
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

	/**
	 * 根据email到数据库userTable表中取出userId
	 * @param email
	 * @return String
	 */
	public static String getUserIdByEmail(String email){
		String userId=null;
		try {
			String sql="select email from UserTable where number=?";
			String []parameters={email};
			rs=SqlHelper.executeQuery(sql, parameters);
			if(rs.next()){
				userId=rs.getString(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userId;
		
	}

	/**
	 * 根据email设置已激活
	 * @param email
	 */
	public static void setStateValidate(String email){
		String sql="update userTable set state='Active' where email=?";
		String []parameters={email};
		SqlHelper.executeUpdate(sql, parameters);
		
	}
}
