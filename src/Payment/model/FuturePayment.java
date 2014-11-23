package futurePayment.model;

import java.util.LinkedList;
import org.json.JSONObject;


/**
 * 
 * @author luo
 *
 */
public class FuturePayment extends FuturePaymentSurport{ 
	private static String baseURL = "http://localhost:8080/futurePayment"; //主页url
	
	/**
	 * 
	 * @param userId 用户名
	 * @param password 登陆密码
	 * 构造函数
	 */
	public FuturePayment(String userId, String password) {
		super(baseURL,userId, password);
	}
	public FuturePayment(){
		super(baseURL,null,null);
	}
	
	/**
	 *用户登陆
	 */
	public User loginUser() throws PaymentException {
		User user = null;
		//login successfully
		
		if(http.beginSession()){
			user = new User(getUserInfo());
		}
		return user;
	}
	
	public void logoutUser(){
		super.logoutUser();
	}
	
	/**
	 * get all bankcards binded
	 */
	public LinkedList<BankCard> getAllBankCards(){	
		return super.getAllBankCards();
	}
	
	/**
	 * 
	 * @return JSONObject
	 * get the infomation of the user
	 */
	public JSONObject getUserInfo(){
		return super.getUserInfo();
	}
	/**
	 * @param transfer transfer to be handled.
	 * @param password the payment password of the user
	 */
	public boolean pay(Transfer transfer,String password) throws PaymentException {
		return super.pay(transfer, password);
	}
	
	public boolean get(Transfer transfer) throws PaymentException{
		return super.get(transfer);
	}
	public LinkedList<TradeRecord> getTradeRecords(int page,int perpage) {
		return super.getTradeRecords(page, perpage);
	}
}
