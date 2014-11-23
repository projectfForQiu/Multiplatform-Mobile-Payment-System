package futurePayment.model;

import java.util.LinkedList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import futurePayment.http.MyHttpClient;
import futurePayment.http.MyResponse;
import futurePayment.http.ResultCode;

/**
 * 
 * @author luo
 *
 */
public class FuturePaymentSurport {
	protected MyHttpClient http = new MyHttpClient();
	public FuturePaymentSurport(String baseUrl,String userId,String password){
		http.setUserId(userId);
		http.setPassword(password);
	}
	
	public void setUserId(String userId){
		http.setUserId(userId);
	}
	
	public void setPassword(String password){
		http.setPassword(password);
	}
	/**
	 *user login
	 */
	public User loginUser() throws PaymentException {
		User user = null;
		//login successfully
		if(http.beginSession()){
			user = new User(getUserInfo());
		}
		return user;
	}
	
	/**
	 * user logout
	 */
	public void logoutUser(){
		http.endSession();
	}
	public JSONObject getUserInfo() {
		JSONObject info = null;
		JSONObject jobj = new JSONObject();
		try {
			jobj.put("serviceType", ServiceType.GET_USER_INFO);
			MyResponse response = http.post(Uris.GET_USER_INFO, jobj);
			if(response.getResultCode() == ResultCode.SUCCESS){
				info = response.getResultObject("userInfo");
			
			}
				
		} catch (JSONException e) {
			Log.i("error",e.toString());
		}
		return info;
	}
	/**
	 * 
	 * @param transfer 
	 * @param password
	 * @throws PaymentException
	 */
	public boolean pay(Transfer transfer,String password) throws PaymentException {
		JSONObject jobj = new JSONObject();
		try{
			jobj.put("serviceType", ServiceType.TRANSFER_PAY);
			jobj.put("sender", transfer.getSender());
			jobj.put("receiver", transfer.getReceiver());
			jobj.put("amount",transfer.getAmount());
			jobj.put("password", password);
			MyResponse response = http.post(Uris.TRANSFER, jobj);
			if(response.getResultCode() == ResultCode.TRANSFER_SUCCESS)
				return true;
		}catch(Exception e){
			Log.i("error", e.toString());
			throw new PaymentException(ResultCode.OTHER_FAILURE);
		}
		//throw new PaymentException(ResultCode.TRANSFER_FAILURE);
		return false;
	}
	
	public boolean get(Transfer transfer)throws PaymentException{
		JSONObject jobj = new JSONObject();
		try{
			jobj.put("serviceType", ServiceType.TRANSFER_GET);
			jobj.put("sender", transfer.getSender());
			jobj.put("receiver", transfer.getReceiver());
			jobj.put("amount",transfer.getAmount());
			MyResponse response = http.post(Uris.TRANSFER, jobj);
			if(response.getResultCode() == ResultCode.SUCCESS)
				return true;
		}catch(Exception e){
			e.printStackTrace();
			throw new PaymentException(ResultCode.OTHER_FAILURE);
		}
		throw new PaymentException();
	}
	
	/**
	 * @param userId 
	 * @param cardNumber 
	 * @param bank        
	 * @param password    the password of the card
	 * @throws PaymentException 
	 */
	public boolean bindCard(String cardNumber,String bank,String password) throws PaymentException{
		boolean result = false;
		JSONObject jobj = new JSONObject();
		try {
			jobj.put("serviceType",ServiceType.BINDCARD);
			jobj.put("cardNumber",cardNumber);
			jobj.put("bank", bank);
			jobj.put("password", password);
			MyResponse response = http.post(Uris.ACCOUNT_SERVICE, jobj);
			if(response.getResultCode() == ResultCode.BINDCARD_FAILURE)
				throw new PaymentException(ResultCode.BINDCARD_FAILURE);
			result = true;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new PaymentException(ResultCode.OTHER_FAILURE);
		}
		return result;
	}
	
	/**
	 * 
	 * @return 
	 */
	public LinkedList<BankCard> getAllBankCards(){
		LinkedList<BankCard> bankCards = new LinkedList<BankCard>();
		JSONObject jobj = new JSONObject();
		try {
			jobj.put("serviceType", ServiceType.GET_ALL_BANKCARDS);
			MyResponse response = http.post(Uris.ACCOUNT_SERVICE, jobj);
			int resultCode = response.getResultCode();
			if (resultCode != ResultCode.NOCARDS) {
				JSONArray cards = response.getResultArray("bankCards");
				for(int i = 0; i < cards.length(); i++){
					JSONObject card = cards.getJSONObject(i);
					BankCard bankCard = new BankCard();
					bankCard.setCardNumber(card.getString("cardNumber"));
					bankCard.setBankName(card.getString("bankName"));
					bankCards.add(bankCard);
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bankCards;
	}
	
	/**
	 * @param page pagenumber
	 * @param perpage  number of records per page
	 * @return list of records
	 * 分页获取交易记录
	 */
	public LinkedList<TradeRecord> getTradeRecords(int page,int perpage){
		LinkedList<TradeRecord> records = null;
		JSONObject jobj = new JSONObject();
		try {
			jobj.put("serviceType", ServiceType.GET_TRADERECORD);
			jobj.put("page", page);
			jobj.put("perpage", perpage);
			MyResponse response = http.post(Uris.GET_USER_INFO, jobj);
			int resultCode = response.getResultCode();
			if(resultCode != ResultCode.NO_TRADE_RECORD){
				records = new LinkedList<TradeRecord>();
				JSONArray array = response.getResultArray("tradeRecords");
				for(int i = 0 ; i < array.length(); i++){
					JSONObject ob = array.getJSONObject(i);
					TradeRecord record = new TradeRecord();
					record.setSender(ob.getString("sender"));
					record.setReceiver(ob.getString("receiver"));
					record.setAmount(ob.getDouble("amount"));
					record.setDate(ob.getString("date"));
					records.add(record);
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return records;
	}
	
	public String lookUpUserNameByCardNumber(String cardNumber){
		JSONObject jobj = new JSONObject();
		try {
			jobj.put("serviceType", ServiceType.LOOK_UP_NAME_BY_CARDNUMBER);
			MyResponse response = http.post(Uris.ACCOUNT_SERVICE, jobj);
			if(response.getResultCode() == ResultCode.SUCCESS){
				return response.getResponseBody().getString("userName");
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
