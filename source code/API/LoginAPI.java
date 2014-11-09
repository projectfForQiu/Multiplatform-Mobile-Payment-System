package leo.API;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;

import net.sf.json.JSONObject;

/**
 * 登录接口类
 * @author Lee
 *
 */
public class LoginAPI {
/**
 * 调试代码
 * @param userId
 * @param loinPassword
 * @return
 */
	 public static void main(String[] args) { 
	       JSONObject json=setLoginMessage("123","123");
	       System.out.println(json);
	        
	    } 
	
	
	 /**输入用户id与用户密码，并返回一个jsonobject，里面包含验证密码信息，
	  * 是否已经将登录信息写入数据库的信息。
	  * @author Lee
	  * @param userId
	  * @param loinPassword
	  * @return JSONObject 
	  *
	  */
	 
	 public static JSONObject setLoginMessage(String userId,String loinPassword){
		 
		 JSONObject jsonobj=new JSONObject();
		 jsonobj.put("userId", userId);
		 jsonobj.put("loinPassword", loinPassword);
		 
		 //1. 创建 HttpClient 的实例
		 HttpClient client=new HttpClient();
		 
		 //设置url
		 String url="http://110.64.89.205:8080/BigProject/servlet/LoginCt";
		 
		 //2. 创建POST连接方法的实例
		 PostMethod postMethod=new PostMethod(url);
		 
		 //3.往postMethod里添加jsonobj
		 postMethod.addParameter("data",jsonobj.toString());
		 
		 //4.发送请求
		 try {
			client.executeMethod(postMethod);
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 //5.返回请求，获取response
		 String response=null;
		 try {
			response=postMethod.getResponseBodyAsString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 JSONObject jsonBack=JSONObject.fromObject(response);
		return jsonBack;
	 
		 
	 }
	
}
