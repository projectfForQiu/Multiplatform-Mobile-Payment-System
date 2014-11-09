package com.util;

import net.sf.json.*;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;

public class JSONReader {
	
    private  JSONObject jsonObj;
    private  JSONArray jsonArray;
    
    public JSONReader(String jsonStr)
    {
    	/*
    	 * JSONArray和JSONObject是有区别的，我看网上很多示例都搞成一样的
    	 * 如果下面都用jsonStr做参数，会抛出异常：
    	 * 严重: Servlet.service() for servlet jsp threw exception
         * net.sf.json.JSONException: A JSONArray text must start with '[' 
         * at character 1 of {'username':'we','password':'we','major':'we','gender':'male'}
	     * at net.sf.json.util.JSONTokener.syntaxError(JSONTokener.java:512)
	     * at net.sf.json.JSONArray._fromJSONTokener(JSONArray.java:903)
	     * at net.sf.json.JSONArray._fromString(JSONArray.java:983)
	     * at net.sf.json.JSONArray.fromObject(JSONArray.java:141)
	     * at net.sf.json.JSONArray.fromObject(JSONArray.java:120)
	     * at JSON.JSONReader.<init>(JSONReader.java:18)
    	 */
    	String jsonArr="["+jsonStr+"]";
    	jsonObj=JSONObject.fromObject(jsonStr);
    	jsonArray=JSONArray.fromObject(jsonArr);
    }
    /**
     * 把JSONObject对象转成java Map
     * @return
     */
    public  Map getMap4JSON()
    {
    	Map map=new HashMap();
    	Iterator keys=jsonObj.keys();
    	String key;
    	Object value;
    	while(keys.hasNext())
    	{
    		key=(String)keys.next();
    		value=jsonObj.get(key);
    		map.put(key, value);
    	}
    	return map;
    }
    
    /**
     * 转成数组
     * @return Object[]
     */
    public Object[] getArray4JSON()
    {
    	return jsonArray.toArray();
    }
    
    /**
     * 从中得到String[],int[]以及其他，类似于下面的过程
     * @return String[]
     */
    public String[] getStringArray4JSON()
    {
    	String str[]=new String[jsonArray.size()];
    	for(int i=0;i<str.length;i++)
    	{
    		str[i]=jsonArray.getString(i);
    	}
    	return str;
    }
    
    /**
     * 得到Java 对象
     * @param beanClass
     * @return
     */
    public Object getObject4JSON(Class beanClass)
    {
    	/**
    	 * 不知道为什么，在调试的时候出现过找不到这个方法的异常
    	 * 后来换成json-lib-2.1.jar又换回来就好了，很奇怪
    	 * 也许是其他错误造成Editor不正确报错吧
    	 * 
    	 */
    	Object obj=JSONObject.toBean(jsonObj, beanClass);
    	return obj;
    }
    
    /**
     * 得到java 对象列表
     * @param beanClass
     * @return
     */
    public List getList4JSON(Class beanClass)
    {
    	List list=new ArrayList();
    	Object obj;
    	for(int i=0;i<jsonArray.size();i++)
    	{
    		obj=JSONObject.toBean(jsonObj, beanClass);
    		list.add(obj);
    	}
    	return list;
    }
}

