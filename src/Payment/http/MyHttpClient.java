package futurePayment.http;

import java.util.*;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import futurePayment.model.PaymentException;
import futurePayment.model.ServiceType;
import futurePayment.model.Uris;
/**
 * 
 * @author luo
 *
 */
public class MyHttpClient{
	private static int OK = 200;// OK: Success!
	private HttpClient http = new DefaultHttpClient();
	private String userId;
	private String password;
	public MyHttpClient(){
	}
	
	public void setUserId(String userId){
		this.userId = userId;
	}
	
	public String getUserId(){
		return userId;
	}
	/**
	 * 
	 * @param password   login password 
	 */
	public void setPassword(String password){
		this.password = password;
	}
	/**
	 * 
	 * @param url target url
	 * @param json  json of request 
	 * @return   response 
	 * ∑¢ÀÕ post «Î«Û
	 */
	public MyResponse post(String uri,JSONObject json){
		MyResponse response = null;
		HttpPost post = new HttpPost(Uris.BASIC_URL + uri);
		try {
			if(userId != null)
				json.put("userId", userId);
			List<NameValuePair> params = new LinkedList<NameValuePair>();
			//String requestJson = AES.encrypt(json.toString());
			params.add(new BasicNameValuePair("json",json.toString()));
			post.setEntity(new UrlEncodedFormEntity(params,"utf-8"));
			HttpResponse res = http.execute(post);
			int statusCode = res.getStatusLine().getStatusCode();
			Log.i("error", statusCode+" ");
			if(statusCode == OK){
				response = new MyResponse(EntityUtils.toString(res.getEntity(),"utf-8")); 
			}
		} catch (Exception e) {
			Log.i("error", e.toString());
		}
		
		return response;
	}
	
	/**
	 * 
	 * @return 
	 * @throws PaymentException
	 * try to begin session with the server
	 */
	public boolean beginSession() throws PaymentException{
		boolean result = false;
		try {
			JSONObject jobj = new JSONObject();
			jobj.put("userId", userId);
			jobj.put("password", password);
			MyResponse response = post(Uris.LOGIN, jobj);
			if(response.getResultCode() == ResultCode.LOGIN_SUCCESS){
				result = true;
			}
			else throw new PaymentException(response.getResultCode());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * end session with the server
	 */
	public void endSession(){
		JSONObject jobj = new JSONObject();
		try {
			jobj.put("serviceType", ServiceType.ENDSESSION);
			post(Uris.LOGIN, jobj);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
