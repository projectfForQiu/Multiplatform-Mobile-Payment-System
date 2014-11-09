package com.util.mail;

public class MailHelper {

	public static void main(String[]args){
		//MailHelper.sendMail("123@qq.com","123");
	}
	public static void sendMail(String data,String ToAddress){
		//这个类主要是设置邮件
		MailSenderInfo mailInfo=new MailSenderInfo();
		mailInfo.setMailServerHost("smtp.qq.com");
		mailInfo.setMailServerPort("465");
		mailInfo.setValidate(true);
		mailInfo.setUserName("452721007@qq.com");
		mailInfo.setPassword("lgb13539783913");
		mailInfo.setFromAddress("452721007@qq.com");
		mailInfo.setToAddress(ToAddress);
		mailInfo.setSubject("注册ByEmail");  
		mailInfo.setContent("请点击以下地址确认验证信息 http://110.64.89.205:8080/BigProject/servlet/ReceiveVarificationByEmailCt?"+data); 
		 //这个类主要来发送邮件 
		SimpleMailSender sms = new SimpleMailSender();
		sms.sendTextMail(mailInfo);//发送文体格式
		//sms.sendHtmlMail(mailInfo);//发送html格式
		
		
	}
}
