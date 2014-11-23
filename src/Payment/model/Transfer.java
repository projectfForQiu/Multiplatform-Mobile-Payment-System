package futurePayment.model;

import java.util.Date;

/**
 * 
 * @author luo
 *
 */
public class Transfer {
	private String sender;
	private String receiver;
	private String amount;
	private Date tradeDate = new Date(System.currentTimeMillis());
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
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public Date getTradeDate(){
		return tradeDate;
	}
}
