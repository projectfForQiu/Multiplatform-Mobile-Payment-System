package com.futurePayment.model;

public class PaymentException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3635525910787368676L;
	
	private int resultCode;
	
	public PaymentException(){
		super();
	}
	public PaymentException(int resultCode){
		super();
		this.resultCode = resultCode;
	}
	public PaymentException(String message){
		super(message);
	}
	public PaymentException(String message,Throwable cause){
		super(message,cause);
	}
	
	public int getResultCode(){
		return resultCode;
	}
}
