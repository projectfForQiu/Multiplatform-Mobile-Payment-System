package com.futurePayment.http;

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

import com.futurePayment.constant.ResultCode;
import com.futurePayment.constant.ServiceType;
import com.futurePayment.constant.Uris;
import com.futurePayment.model.PaymentException;

import android.util.Log;
/**
 * 
 * @author luo
 *
 */
public class MyHttpClient{
	private static int OK = 200;// OK: Success!
	private HttpClient http = new DefaultHttpClient();
	private String name;
	public MyHttpClient(String name){
		this.name = name;
	}
	/**
	 * 
	 * @param url target url
	 * @param json  json of request 
	 * @return   response 
	 * ∑¢ÀÕ post «Î«Û
	 */
	public MyResponse post(int serviceType,JSONObject json){
		MyResponse response = null;
		HttpPost post = new HttpPost(Uris.BASIC_URL);
		try {
			JSONObject set = new JSONObject();
			set.put("serviceType", serviceType);		
			if(name != null)
			{
				set.put("name", name);
			}
			if(json != null)
			{
				set.put("data", json);
			}
			List<NameValuePair> params = new LinkedList<NameValuePair>();
			//String requestJson = AES.encrypt(json.toString());
			params.add(new BasicNameValuePair("json",set.toString()));
			post.setEntity(new UrlEncodedFormEntity(params,"utf-8"));
			Log.i("test", "request:" + params.toString());		
			HttpResponse res = http.execute(post);
			int statusCode = res.getStatusLine().getStatusCode();
			Log.i("test", "statusCode:" + statusCode);	
			if(statusCode == OK){
				response = new MyResponse(EntityUtils.toString(res.getEntity(),"utf-8")); 	
			}
			Log.i("test", "response:" + response.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return response;
	}
	

}
