package com.futurePayment.model;

import java.sql.Date;


public class Message {
	private String content;
	private Date date;
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	

	public String toString(){
		String s = "{content:" + content + ",date:" + date + "}";
		return s;
	}
	public Date getDate()
	{
		return date;
	}
	public void setDate(Date date)
	{
		this.date = date;
	}
}
