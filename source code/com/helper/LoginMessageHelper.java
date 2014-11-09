package com.helper;

import com.domain.LoginMessageBean;
import com.util.SqlHelper;
/**
 * 
 * @author Lee
 * 将登录信息插入数据库
 */
public class LoginMessageHelper {

	public int setLoginMessage(LoginMessageBean logMessage){
		
		
		String sql="insert into loginMessage values(?,?,?)";
		String []parameters={logMessage.getUserId(),logMessage.getLoginAddress(),logMessage.getLoginTime()};
		int status=SqlHelper.executeUpdate(sql, parameters);
		return status;
		
		
	}
}
