package com.billme.util;

import java.util.HashMap;

public class Item {
	
	public String ID;
	public HashMap<String, Double> tag;
	public double []tagValues;
	public String price;
	public String Productor;
	
	
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public HashMap<String, Double> getTag() {
		return tag;
	}
	public void setTag(HashMap<String, Double> tag) {
		this.tag = tag;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getProductor() {
		return Productor;
	}
	public void setProductor(String productor) {
		Productor = productor;
	}
	
}
