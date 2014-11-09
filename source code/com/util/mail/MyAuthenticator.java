package com.util.mail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class MyAuthenticator extends Authenticator{

	private String userName=null;
	private String password=null;
	
	public MyAuthenticator(String username, String password){
		this.userName=username;
		this.password=password;
	}
	protected PasswordAuthentication getPasswordAuthentication(){
		return new PasswordAuthentication(userName, password); 
	}
	
	
	/*
	 * 测试代码
	 */
	public static void main(String []args){
		//这个类主要是设置邮件
		MailSenderInfo mailInfo=new MailSenderInfo();
		mailInfo.setMailServerHost("smtp.qq.com");
		mailInfo.setMailServerPort("465");
		mailInfo.setValidate(true);
		mailInfo.setUserName("452721007@qq.com");
		mailInfo.setPassword("lgb13539783913");
		mailInfo.setFromAddress("452721007@qq.com");
		mailInfo.setToAddress("452721007@qq.com");
		mailInfo.setSubject("设置邮箱标题 如http://www.guihua.org 中国桂花网");  
		mailInfo.setContent("设置邮箱内容 如http://110.64.89.205:8080/BigProject/Login.jsp 是中国最大桂花网站=="); 
		 //这个类主要来发送邮件 
		SimpleMailSender sms = new SimpleMailSender();
		sms.sendTextMail(mailInfo);//发送文体格式
		//sms.sendHtmlMail(mailInfo);//发送html格式
		
		
	}
	
}
