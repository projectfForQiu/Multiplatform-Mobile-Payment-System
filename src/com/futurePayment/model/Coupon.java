package com.futurePayment.model;

import java.sql.Date;



public class Coupon
{
	private String enterpriseName;
	private String startTime;
	private String endTime;
	private double value;
	private double least;
	private int amount;
	public String getEnterpriseName()
	{
		return enterpriseName;
	}
	public void setEnterpriseName(String enterpriseName)
	{
		this.enterpriseName = enterpriseName;
	}

	public double getValue()
	{
		return value;
	}
	public void setValue(double value)
	{
		this.value = value;
	}
	public double getLeast()
	{
		return least;
	}
	public void setLeast(double least)
	{
		this.least = least;
	}
	public int getAmount()
	{
		return amount;
	}
	public void setAmount(int amount)
	{
		this.amount = amount;
	}
	public String getStartTime()
	{
		return startTime;
	}
	public void setStartTime(String startTime)
	{
		this.startTime = startTime;
	}
	public String getEndTime()
	{
		return endTime;
	}
	public void setEndTime(String endTime)
	{
		this.endTime = endTime;
	}
}
