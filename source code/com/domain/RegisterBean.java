package com.domain;
/**
 * 
 * @author Lee
 * ∂‘”¶Register±Ì
 */
public class RegisterBean {
	private String userId;
	private String rgtype;
	private String loginPassword;
	private String payPassword;
	private String name;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getuserId() {
		return userId;
	}
	public void setuserId(String userId) {
		this.userId = userId;
	}
	public String getRgtype() {
		return rgtype;
	}
	public void setRgtype(String rgtype) {
		this.rgtype = rgtype;
	}
	public String getLoginPassword() {
		return loginPassword;
	}
	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}
	public String getPayPassword() {
		return payPassword;
	}
	public void setPayPassword(String payPassword) {
		this.payPassword = payPassword;
	}
	
}
