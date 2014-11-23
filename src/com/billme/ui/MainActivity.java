package com.billme.ui;

import java.util.HashMap;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.QuickContactBadge;
import android.widget.TextView;
import android.widget.Toast;

import com.billme.logic.BillMeActivity;
import com.billme.logic.MainService;
import com.billme.logic.Task;

public class MainActivity extends BaseActivity implements BillMeActivity{
	private QuickContactBadge userIcon;
	private TextView infoUserName;
	private TextView infoUserBalance;
	private Button pay;
	private Button repay;
	public static final int REFRESH_USERINFO = 1024;
	public static final int REFRESH_PAYMENT = 1025;
	public static final int REFRESH_REPAYMENT = 1026;
	
	@Override 
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_main);
		
		userIcon = (QuickContactBadge)this.findViewById(R.id.qcb_userIcon);
		userIcon.setOnClickListener(new userInfoOnClickListner());
		
		infoUserName = (TextView)this.findViewById(R.id.tv_imformation_userName);
		//infoUserName.setText(MainService.getUser().getName());
		infoUserName.setText("testUserName");
		infoUserName.setOnClickListener(new userInfoOnClickListner());
		
		infoUserBalance = (TextView)this.findViewById(R.id.tv_imformation_acountBalance);
		//infoUserBalance.setText(MainService.getUser().getUserId());
		infoUserBalance.setText("testUserBalance");
		infoUserBalance.setOnClickListener(new userInfoOnClickListner());
		
		pay = (Button)this.findViewById(R.id.btn_payment);
		pay.setOnClickListener(new payOnClickListner());
		
		repay = (Button)this.findViewById(R.id.btn_repayment);
		repay.setOnClickListener(new repayOnclickListner());
		
		MainService.allActivities.add(this);
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void refresh(Object... param) {
		// TODO Auto-generated method stub
		switch (((Integer) param[0]).intValue()) {
		case -100:{
			Toast.makeText(MainActivity.this, "Something failed",
					Toast.LENGTH_SHORT).show();
			break;
		}
		case REFRESH_USERINFO:{
			Intent it = new Intent(this, UserImformationActivity.class);
			
			this.startActivity(it);
			break;
		}
		case REFRESH_PAYMENT:{
			
			break;
		}
		case REFRESH_REPAYMENT:{
			Intent it = new Intent(this, RepayActivity.class);
			this.startActivity(it);
			break;
		}
		default:
			break;
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Intent it = new Intent(MainActivity.this,MainService.class);
		stopService(it);
	}
	
	



	private class userInfoOnClickListner implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent it = new Intent(MainActivity.this, UserImformationActivity.class);
			MainActivity.this.startActivity(it);
		}
		
	}
	private class payOnClickListner implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
//			HashMap<String, String> param = new HashMap<String, String>();
//			Task task = new Task(Task.TASK_USER_PAYMENT, param);
//			MainService.newTask(task);
			Intent it = new Intent(MainActivity.this, PaymentActivity.class);
			MainActivity.this.startActivity(it);
		}
	}
	private class repayOnclickListner implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			//HashMap<String, String> param = new HashMap<String, String>();
			//Task task = new Task(Task.TASK_USER_REPAYMENT, param);
			//MainService.newTask(task);
			Intent it = new Intent(MainActivity.this,AccountActivity.class);
			MainActivity.this.startActivity(it);
		}
		
	}
}
