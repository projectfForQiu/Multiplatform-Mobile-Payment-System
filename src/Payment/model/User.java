package futurePayment.model;

import java.util.LinkedList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

/**
 * 
 * @author luo
 *
 */
public class User {
	private FuturePayment payment;
	private String userId;
	private String name;
	private LinkedList<TradeRecord> tradeRecords = new LinkedList<TradeRecord>();
	private LinkedList<BankCard>bankCards = new LinkedList<BankCard>();
	
    User(String userId){
		this.userId = userId;
	}
    
	User(JSONObject userInfo){
		try {
			setUserId(userInfo.getString("userId"));
			setName(userInfo.getString("name"));
			
			Log.i("error", userInfo.toString());
			JSONArray cards = userInfo.getJSONArray("bankCards");
			for(int i = 0; i < cards.length(); i++){
				JSONObject ob = cards.getJSONObject(i);
				BankCard card = new BankCard();
				card.setCardNumber(ob.getString("cardNumber"));
				card.setBankName(ob.getString("bank"));
				bankCards.add(card);
			}
			
			JSONArray records = userInfo.getJSONArray("tradeRecords");
			for(int i = 0; i < records.length(); i++){
				JSONObject ob = records.getJSONObject(i);
				TradeRecord record = new TradeRecord();
				record.setSender(ob.getString("sender"));
				record.setReceiver(ob.getString("receiver"));
				record.setAmount(ob.getDouble("amount"));
				record.setDate(ob.getString("date"));
				tradeRecords.add(record);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			Log.i("error", e.toString());
		}
	}
	public FuturePayment getPayment() {
		return payment;
	}
	public void setPayment(FuturePayment payment) {
		this.payment = payment;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public LinkedList<BankCard> getBankCards() {
		return bankCards;
	}

	public void setBankCards(LinkedList<BankCard> bankCards) {
		this.bankCards = bankCards;
	}
	public LinkedList<TradeRecord> getTradeRecords() {
		return tradeRecords;
	}
	public void setTradeRecords(LinkedList<TradeRecord> tradeRecords) {
		this.tradeRecords = tradeRecords;
	}
	
}
