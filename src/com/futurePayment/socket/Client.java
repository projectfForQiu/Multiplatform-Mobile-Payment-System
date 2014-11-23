package com.futurePayment.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.sql.Date;
import java.util.LinkedList;

import org.json.JSONObject;
import org.json.JSONTokener;

import com.futurePayment.model.Message;


import android.util.Log;

 

public class Client implements Runnable {
	private String clientId;           //the id of the client
	private String serverUrl = "";     // the url of the server
	private int serverPost;            //the post of the server
	private final int INTERVAL = 1000 * 30;       // interval is set to 30 seconds 
	private LinkedList<Message> unReadMessage = new LinkedList<Message>();
	
	public Client(){
		
	}
	
	public Client(String clientId,int serverPost){
		this.clientId = clientId;
		this.serverPost = serverPost;
	}
	
	public boolean hasUnReadMessage(){
		return unReadMessage.size() > 0;
	}
	
	public LinkedList<Message> getUnReadMessage(){
		return unReadMessage;
	}
	
	/**
	 * this method defines the job of the client thread.
	 * the client connects to the server to see if there is
	 * message for it in every other interval.
	 */
	@Override
	public void run() {
		while(true){
			Socket socket;
			try {
				Thread.sleep(INTERVAL);
			    socket = new Socket(serverUrl,serverPost);
				DataOutputStream out = new DataOutputStream(socket.getOutputStream());
				out.writeUTF(clientId);
				DataInputStream in = new DataInputStream(socket.getInputStream());
				
				String hasMessage = in.readUTF();
				//check to see if there is unread message
				if(hasMessage != null && hasMessage.equals("yes")){
					boolean done = false;
					while(!done){
						String messageString = in.readUTF();
						if(messageString != null){
							if (messageString.equals("done!"))
								break;
							else{
								Message message = getMessage(messageString);
								unReadMessage.add(message);
							}
						}
					}
				}
				socket.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	
	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getServerUrl() {
		return serverUrl;
	}

	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}

	public int getServerPost() {
		return serverPost;
	}

	public void setServerPost(int serverPost) {
		this.serverPost = serverPost;
	}

	public int getINTERVAL() {
		return INTERVAL;
	}
	public Message getMessage(String messageString){
		JSONTokener parser = new JSONTokener(messageString);
		Message message= new Message();
		try{
			JSONObject json = (JSONObject)parser.nextValue();
			
			message.setContent(json.getString("content"));
			message.setDate((Date)json.get("date"));
		}catch(Exception e){
			Log.i("error", e.toString());
		}
		return message;
	}
}
