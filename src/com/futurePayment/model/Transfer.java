package com.futurePayment.model;

import java.util.Date;

/**
 * 
 * @author luo
 *
 */
public class Transfer {
	private String sender;
	private String receiver;
	private double amount;
	private String bank;
	private String cardNumber;
	private String method;
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getBank()
	{
		return bank;
	}
	public void setBank(String bank)
	{
		this.bank = bank;
	}
	public String getCardNumber()
	{
		return cardNumber;
	}
	public void setCardNumber(String cardNumber)
	{
		this.cardNumber = cardNumber;
	}
	public String getMethod()
	{
		return method;
	}
	public void setMethod(String method)
	{
		this.method = method;
	}
}
