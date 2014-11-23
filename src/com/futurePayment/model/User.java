package com.futurePayment.model;

import java.util.ArrayList;
import java.sql.Date;
import java.util.LinkedList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;



import android.util.Log;

/**
 * 
 * @author luo
 *
 */
public class User {
	private String name;
	private String realName;
	private String sex;
	private String birthday;
	private String phone;
	private String email;
	private int grade;
	private double balance;
	private ArrayList<VipCard> vipCardList = new ArrayList<VipCard>();
	private ArrayList<Coupon> couponList = new ArrayList<Coupon>();
	private LinkedList<String> friendList = new LinkedList<String>();
	private LinkedList<String> concernList = new LinkedList<String>();
	private ArrayList<BankCard> bankCardList = new ArrayList<BankCard>();
	private LinkedList<TradeRecord> tradeRecordList = new LinkedList<TradeRecord>();
	
	public User()
	{
	}
    public User(String name){
		this.name = name;
	}
    
	public User(JSONObject userInfo){
		try {
			setName(userInfo.getString("name"));
			setRealName(userInfo.getString("realName"));
			setSex(userInfo.getString("sex"));
			setBirthday(userInfo.getString("birthday"));
			setPhone(userInfo.getString("phone"));
			setEmail(userInfo.getString("email"));
			setGrade(userInfo.getInt("grade"));
			
			Log.i("error", userInfo.toString());
			JSONArray vipCards = userInfo.getJSONArray("vipCardList");
			for(int i = 0; i < vipCards.length(); i++){
				JSONObject ob = vipCards.getJSONObject(i);
				VipCard card = new VipCard();
				card.setEnterpriseName(ob.getString("enterpriseName"));
				card.setGrade(ob.getInt("grade"));
				vipCardList.add(card);
			}
			JSONArray coupons = userInfo.getJSONArray("couponList");
			for(int i = 0; i < coupons.length(); i++){
				JSONObject ob = coupons.getJSONObject(i);
				Coupon coupon = new Coupon();
				coupon.setEnterpriseName(ob.getString("enterpriseName"));
				coupon.setEndTime(ob.getString("endTime"));
				coupon.setLeast(ob.getDouble("least"));
				coupon.setStartTime(ob.getString("startTime"));
				coupon.setValue(ob.getDouble("value"));
				coupon.setAmount(ob.getInt("amount"));
				couponList.add(coupon);
			}
			JSONArray friends = userInfo.getJSONArray("friendList");
			for(int i = 0; i < friends.length(); i++){
				JSONObject ob = friends.getJSONObject(i);
				String friend = ob.getString("friendName");
				friendList.add(friend);
			}
			JSONArray concerns = userInfo.getJSONArray("concernList");
			for(int i = 0; i < concerns.length(); i++){
				JSONObject ob = concerns.getJSONObject(i);
				String enterprise = ob.getString("enterpriseName");
				concernList.add(enterprise);
			}
			JSONArray bankCards = userInfo.getJSONArray("bankCardList");
			for(int i = 0; i < bankCards.length(); i++){
				JSONObject ob = bankCards.getJSONObject(i);
				BankCard card = new BankCard();
				card.setCardNumber(ob.getString("cardNumber"));
				card.setBankName(ob.getString("bank"));
				bankCardList.add(card);
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			Log.i("error", e.toString());
		}
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}



	public String getRealName()
	{
		return realName;
	}

	public void setRealName(String realName)
	{
		this.realName = realName;
	}

	public String getSex()
	{
		return sex;
	}

	public void setSex(String sex)
	{
		this.sex = sex;
	}


	public String getPhone()
	{
		return phone;
	}

	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}


	public ArrayList<VipCard> getVipCardList()
	{
		return vipCardList;
	}
	public void setVipCardList(ArrayList<VipCard> vipCardList)
	{
		this.vipCardList = vipCardList;
	}
	public ArrayList<Coupon> getCouponList()
	{
		return couponList;
	}
	public void setCouponList(ArrayList<Coupon> couponList)
	{
		this.couponList = couponList;
	}
	public LinkedList<String> getFriendList()
	{
		return friendList;
	}
	public void setFriendList(LinkedList<String> friendList)
	{
		this.friendList = friendList;
	}
	public LinkedList<String> getConcernList()
	{
		return concernList;
	}
	public void setConcernList(LinkedList<String> concernList)
	{
		this.concernList = concernList;
	}
	public ArrayList<BankCard> getBankCardList()
	{
		return bankCardList;
	}
	public void setBankCardList(ArrayList<BankCard> bankCardList)
	{
		this.bankCardList = bankCardList;
	}

	public int getGrade()
	{
		return grade;
	}
	public void setGrade(int grade)
	{
		this.grade = grade;
	}

	public String getBirthday()
	{
		return birthday;
	}
	public void setBirthday(String birthday)
	{
		this.birthday = birthday;
	}
	public LinkedList<TradeRecord> getTradeRecordList()
	{
		return tradeRecordList;
	}
	public void setTradeRecordList(LinkedList<TradeRecord> tradeRecordList)
	{
		this.tradeRecordList = tradeRecordList;
	}
	public double getBalance()
	{
		return balance;
	}
	public void setBalance(double balance)
	{
		this.balance = balance;
	}
	
}
