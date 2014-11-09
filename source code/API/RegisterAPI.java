package API;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import com.domain.RegisterBean;

import net.sf.json.JSONObject;
/**
 * 注册接口
 * @author Lee
 *
 */

public class RegisterAPI {
	
	 public static void main(String[] args) {   
		 //传过来的json里应该有email,userId,loginPassword
		 JSONObject jsonobj=new JSONObject();
		 jsonobj.put("userId", "201304");
		 jsonobj.put("email", "452721007@qq.com");
		 jsonobj.put("loginPassword", "12345");
		 String flag=varifyByEmail(jsonobj);
		 System.out.println("flag="+flag);
	       
	    } 
	
	/**
	 * 检查邮箱或手机是否已经注册
	 * @author Lee
	 * @param number输入邮箱或手机
	 * @return 0代表number已存在，1代表number不存在
	 */
	public static int checkNumber(String number){
		//1. 创建 HttpClient 的实例
		 HttpClient client=new HttpClient();
		 
		 //设置url
		 String url="http://110.64.89.205:8080/BigProject/servlet/CheckNumberCt";
		 
		 //2. 创建POST连接方法的实例
		 PostMethod postMethod=new PostMethod(url);
		 
		 //3.往postMethod里添加number
		 postMethod.addParameter("number",number);
		 
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
		 int response=0;
		 try {
			response=Integer.parseInt(postMethod.getResponseBodyAsString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
		    
		
	}
	
	/**
	 * 检查用户账号是否已经注册
	 * @author Lee
	 * @param userId
	 * @return 0代表账号已存在，1代表账号不存在
	 */
	public static int checkUserId(String userId){
		//1. 创建 HttpClient 的实例
		 HttpClient client=new HttpClient();
		 
		 //设置url
		 String url="http://110.64.89.205:8080/BigProject/servlet/CheckUserIdCt";
		 
		 //2. 创建POST连接方法的实例
		 PostMethod postMethod=new PostMethod(url);
		 
		 //3.往postMethod里添加number
		 postMethod.addParameter("userId",userId);
		 
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
		 int response=0;
		 try {
			response=Integer.parseInt(postMethod.getResponseBodyAsString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}
	
	/**
	 * 用邮箱注册
	 * 传过来的json里应该要有userId,email,loginPassword,name(昵称),Rgtype(注册类型：person or company),state("Unactive","Active")
	 * @author Lee
	 * @param jsonobj
	 * @return 0代表注册失败，1代表注册成功
	 */
	public static JSONObject registerByEmail(JSONObject jsonobj){
		
		//1. 创建 HttpClient 的实例
		 HttpClient client=new HttpClient();
		 
		 //设置url
		 String url="http://110.64.89.205:8080/BigProject/servlet/RegisterCt";
		 
		 //2. 创建POST连接方法的实例
		 PostMethod postMethod=new PostMethod(url);

		 //3.往postMethod里添加注册信息
		 postMethod.addParameter("dataByEmail",jsonobj.toString());
		 
		 System.out.println(jsonobj);
		 
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
	
	
	
	
	
	/**
	 * 用邮箱验证注册
	 * 传过来的json里应该有email,userId,loginPassword
	 * @param jsonobj
	 * @return
	 */
	public static String varifyByEmail(JSONObject jsonobj){
	
		//1. 创建 HttpClient 的实例
		 HttpClient client=new HttpClient();
		 
		 //设置url
		 String url="http://110.64.89.205:8080/BigProject/servlet/VarifyByEmailCt";
		 
		 //2. 创建POST连接方法的实例
		 PostMethod postMethod=new PostMethod(url);

		 //3.往postMethod里添加注册信息
		 postMethod.addParameter("dataByEmail",jsonobj.toString());
		 
		 System.out.println(jsonobj);
		 
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
		return response;
		
		
		
	}
	
	
	
	
	
	/**
	 * 用手机号注册
	 */
	public static JSONObject registerByPhone(JSONObject jsonobj){
		//1. 创建 HttpClient 的实例
		 HttpClient client=new HttpClient();
		 
		 //设置url
		 String url="http://110.64.89.205:8080/BigProject/servlet/RegisterCt";
		 
		 //2. 创建POST连接方法的实例
		 PostMethod postMethod=new PostMethod(url);
		 
		 
		 //3.往postMethod里添加注册信息
		 postMethod.addParameter("dataByPhone",jsonobj.toString());
		 
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
