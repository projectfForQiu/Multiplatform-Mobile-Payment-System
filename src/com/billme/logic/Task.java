package com.billme.logic;

import java.util.Map;

public class Task {
	private int taskId; //任务编号
	private Map taskParam; //任务参数
	//now there are total 36 tasks;
	public static final int TASK_USER_LOGIN = 1000;
	public static final int TASK_USER_LOGOUT = 1001;
	public static final int TASK_GET_USER_INFO = 1002;
	public static final int TASK_USER_REGIST = 1034;
	public static final int TASK_CHECK_NAME_AVAILABLE = 1035;
	
	public static final int TASK_SINGLE_USER_PAY = 1003;
	public static final int TASK_MULTI_USER_PAY = 1021;
	
	public static final int TASK_GET_TRADING_REACOR = 1004;
	public static final int TASK_GET_BALANCE = 1005;
	
	public static final int TASK_GET_BANK_CARD = 1036;
	public static final int TASK_ADD_BANK_CARD = 1017;
	public static final int TASK_DEL_BANK_CARD = 1022;
	
	public static final int TASK_GET_COUPON = 1006;
	public static final int TASK_PRESENT_COUPON = 1010;
	public static final int TASK_GET_COUPON_INFO = 1008;
	public static final int TASK_DEL_COUPON = 1012;
	public static final int TASK_EXCHANGE_COUPON = 1014;
	public static final int TASK_USE_COUPON = 1025;
	
	public static final int TASK_GET_VIP_CARDS = 1007;
	public static final int TASK_GET_VIP_CARD_INFO = 1009;
	public static final int TASK_APPLY_VIP_CARD = 1023;
	public static final int TASK_LOAN_VIP_CARD = 1011;
	public static final int TASK_DEL_VIP_CARD = 1013;
	public static final int TASK_USE_VIP_CARD = 1024;
	
	public static final int TASK_EXCHANGE_CREDIT = 1015;

	public static final int TASK_MODIFY_PASSWORD = 1018;
	//security tasks have not been difine yet
	public static final int TASK_SHARE_MOMENT = 1019;
	public static final int TASK_GET_MOMENTS = 1020;
	public static final int TASK_GET_FRIENDS = 1025;
	public static final int TASK_ADD_FRIEND = 1026;
	public static final int TASK_DEL_FRIEND = 1027;
	public static final int TASK_FOLLOW_ENTERPRISE = 1028;
	public static final int TASK_UNFOLLOW_ENTERPRISE = 1029;
	
	public static final int TASK_GET_PIC = 1030;
	public static final int TASK_GET_ADVERTISING = 1031;
	public static final int TASK_GET_ENTERPRISE_INFO = 1032;
	public static final int TASK_GET_AROUND_ENTERPRISE_INFO = 1033;
	

	public Task(int id, Map param){
		this.taskId = id;
		this.taskParam = param;
	}
	public Task(int id){
		this.taskId = id;
		this.taskParam = null;
	}
	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public Map getTaskParam() {
		return taskParam;
	}

	public void setTaskParam(Map taskParam) {
		this.taskParam = taskParam;
	}
	
}
