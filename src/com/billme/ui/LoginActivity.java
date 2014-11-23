package com.billme.ui;

import java.util.HashMap;

import com.billme.logic.BillMeActivity;
import com.billme.logic.MainService;
import com.billme.logic.Task;
import com.billme.ui.LoginActivity;
import com.billme.ui.MainActivity;
import com.billme.util.NetUtil;
import com.futurePayment.constant.ResultCode;
import com.futurePayment.model.PaymentException;

import android.os.Bundle;
import android.app.ProgressDialog;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends BaseActivity implements BillMeActivity {
	private Button loginButton = null;
	private Button registButton = null;
	private EditText loginName = null;
	private EditText loginPassword = null;
	private ProgressDialog pd = null;
	public static final int LOGIN_SECCUSS = 1;
	public static final int LOGIN_FAILURE = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		loginButton = (Button) findViewById(R.id.loginButton);
		registButton = (Button) findViewById(R.id.registButton);
		loginName = (EditText) findViewById(R.id.loginName);
		loginPassword = (EditText) findViewById(R.id.loginPassword);

		loginButton.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (LoginActivity.this.pd == null) {
					LoginActivity.this.pd = new ProgressDialog(
							LoginActivity.this);
				}
				pd.setMessage("Logining..");
				pd.show();
				HashMap<String, String> param = new HashMap<String, String>();
				param.put("userName", loginName.getText().toString());
				param.put("userPassword", loginPassword.getText().toString());
				Task task = new Task(Task.TASK_USER_LOGIN, param);
				MainService.newTask(task);
			}

		});

		registButton.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(LoginActivity.this, RegistActivity.class);
				startActivity(intent);
			}

		});
		MainService.allActivities.add(this);
	}

	
	@Override
	protected void onResume() {
		super.onResume();
		this.init();
	}


	@Override
	public void init() {
		if (NetUtil.checkNet(LoginActivity.this)) {
			Intent it = new Intent(LoginActivity.this, MainService.class);
			this.startService(it);
		} else {
			MainService.AlertNetError(LoginActivity.this);
		}
	}

	@Override
	public void refresh(Object... param) {
		// TODO Auto-generated method stub
		switch (((Integer) param[0]).intValue()) {
		case LOGIN_FAILURE:
			int state = ((PaymentException) param[1]).getResultCode();
			String hint = null;
			switch (state) {
			case ResultCode.WRONG_PASSWORD:
				hint = "Password Wrong";
				break;
			case ResultCode.REGISTER_NOT_EXISTS:
				hint = "User Not Exits";
				break;
			}
			pd.cancel();
			Toast.makeText(LoginActivity.this, hint, Toast.LENGTH_SHORT).show();
			break;
		case LOGIN_SECCUSS:
			pd.cancel();
			Intent it = new Intent(this, MainActivity.class);
			this.startActivity(it);
			MainService.allActivities.remove(this);
			this.finish();
			break;
		}
	}
}
