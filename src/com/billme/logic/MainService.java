package com.billme.logic;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import com.billme.ui.LoginActivity;
import com.billme.ui.R;
import com.billme.ui.RegistActivity;
import com.futurePayment.model.FuturePayment;
import com.futurePayment.model.PaymentException;
import com.futurePayment.model.RegistInformation;
import com.futurePayment.model.User;

@SuppressLint({ "UseValueOf", "HandlerLeak" })
public class MainService extends Service implements Runnable {

	/**
	 * @param args
	 */
	public static ArrayList<Activity> allActivities = new ArrayList<Activity>();
	public static int lastActivityId;
	public static ArrayList<Task> allTask = new ArrayList<Task>();
	public boolean isRun = true;
	private static FuturePayment futurePayment = FuturePayment.getInstance();
	private Handler handler = new Handler() {
		// 回调函数
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case Task.TASK_USER_LOGIN: {
				// 登陆
				Log.i("error", "登陆回调中");
				BillMeActivity ba = (BillMeActivity) MainService
						.getActivityByName("LoginActivity");
//				if (msg.obj instanceof PaymentException) {
//					PaymentException e = (PaymentException) msg.obj;
//					// TODO 返回失败信息
//					ba.refresh(new Integer(LoginActivity.LOGIN_FAILURE), e);
//				} else if (msg.obj instanceof BasicInformation) {
//					BasicInformation bi = (BasicInformation) msg.obj;
//					futurePayment.getUser().setName(bi.getName());
//					futurePayment.getUser().setBalance(bi.getBalance());
//					futurePayment.getUser().setGrade(bi.getGrade());
//					c = new Client(futurePayment.getUser().getName(), 8000);
//					Thread t = new Thread(c);
//					t.start();
					ba.refresh(new Integer(LoginActivity.LOGIN_SECCUSS));
//				}
				break;
			}
			case Task.TASK_CHECK_NAME_AVAILABLE:
			{
				Log.i("error","测试用户名是否有效回调中");
				BillMeActivity ba = (BillMeActivity) MainService
						.getActivityByName("RegistActivity");
				if (msg.obj instanceof PaymentException)
				{
					PaymentException e = (PaymentException) msg.obj;
					// TODO 返回失败信息
					ba.refresh(new Integer(RegistActivity.NAME_NOT_AVAILABLE), e);
				}
				else 
				{
					ba.refresh(new Integer(RegistActivity.NAME_AVAILABLE), null);
				}
				break;
			}
			case Task.TASK_USER_REGIST:
			{
				Log.i("error","用户注册回调中");
				BillMeActivity ba = (BillMeActivity) MainService
						.getActivityByName("RegistActivity");
				if (msg.obj instanceof PaymentException)
				{
					PaymentException e = (PaymentException) msg.obj;
					// TODO 返回失败信息
					ba.refresh(new Integer(RegistActivity.REGIST_FAILURE), e);
				}
				else 
				{
					ba.refresh(new Integer(RegistActivity.REGIST_SUCCESS), null);
				}
				break;
			}
			default:
				break;
			}
		}
	};

	public static Activity getActivityByName(String name) {
		for (Activity ac : allActivities) {
			if (ac.getClass().getName().indexOf(name) >= 0) {
				return ac;
			}
		}
		return null;
	}

	public static void newTask(Task task) {
		Log.i("error", "add task");
		allTask.add(task);
	}

	public static void AlertNetError(final Context con) {
		AlertDialog.Builder ab = new AlertDialog.Builder(con);
		ab.setTitle(R.string.Error);
		ab.setMessage(R.string.NoSignalException);
		ab.setNegativeButton(R.string.btn_exit, new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
				MainService.exitApp(con);
			}
		});

		ab.setPositiveButton(R.string.btn_setnet, new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
				con.startActivity(new Intent(
						android.provider.Settings.ACTION_WIRELESS_SETTINGS));
			}
		});
		ab.create().show();
	}

	@Override
	public void run() {
		while (isRun) {
			Task lastTask = null;
			synchronized (allTask) {
				if (allTask.size() > 0) {
					lastTask = allTask.get(0); // 取任务
					doTask(lastTask); // 执行任务
				}
			}
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void doTask(Task task) {
		Message msg = handler.obtainMessage();
		msg.what = task.getTaskId();
		try {
			switch (task.getTaskId()) {
			case Task.TASK_USER_LOGIN: {
				// TODO
				Log.i("error", "登陆中");
//				String userName = (String) task.getTaskParam().get("userName");
//				String userPassword = (String) task.getTaskParam().get(
//						"userPassword");
				break;
			}
			case Task.TASK_CHECK_NAME_AVAILABLE:
			{
				Log.i("error", "测试用户名是否有效");
				try
				{
					futurePayment.checkUserExistence((String)task.getTaskParam().get("name"));
				}
				catch (PaymentException e)
				{
					msg.obj = e;
				}
				break;
			}
			case Task.TASK_USER_REGIST:
			{
				Log.i("error", "用户注册");
				RegistInformation ri = new RegistInformation();
				try
				{
					ri.setName((String)task.getTaskParam().get("name"));
					ri.setLoginPassword((String)task.getTaskParam().get("loginPassword"));
					ri.setPayPassword((String)task.getTaskParam().get("payPassword"));
					ri.setRealName((String)task.getTaskParam().get("realName"));
					ri.setPhone((String)task.getTaskParam().get("phone"));
					ri.setEmail((String)task.getTaskParam().get("email"));
					ri.setBirthday((String)task.getTaskParam().get("birthday"));
					ri.setSex((String)task.getTaskParam().get("sex"));
					futurePayment.regist(ri);
				}
				catch (PaymentException e)
				{
					msg.obj = e;
				}
				break;
			}

			default:
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		handler.sendMessage(msg);
		allTask.remove(task);
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		Log.i("error", " 服务初始化中");
		super.onCreate();
		this.isRun = true;
		Thread t = new Thread(this);
		t.start();

	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		Log.i("error", "服务启动中");
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		this.isRun = false;
	}

	public static void exitApp(Context context) {
		for (Activity ac : allActivities) {
			ac.finish();
		}
		Intent it = new Intent("com.billme.logic.MainService");
		context.stopService(it);
	}

	public static FuturePayment getFuturePayment() {
		return futurePayment;
	}

	public static User getUser() {
		return futurePayment.getUser();
	}
}
