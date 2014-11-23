package com.billme.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;

import com.billme.logic.BillMeActivity;

public class LogoActivity extends BaseActivity implements BillMeActivity {

	private ImageView iv;
	AlphaAnimation aa;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_logo);
		iv = (ImageView) this.findViewById(R.id.iv_logo_bg);
		aa = new AlphaAnimation(0.1f, 1.0f);
		aa.setDuration(1000);
	}

	@Override
	protected void onResume() {
		super.onResume();
		this.init();
		iv.startAnimation(aa);
		aa.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				
				Intent intent = new Intent(LogoActivity.this,
						LoginActivity.class);
				startActivity(intent);
				LogoActivity.this.finish();
			}
		});
		// new CountDownTimer(1000, 1000) {
		//
		// @Override
		// public void onTick(long millisUntilFinished) {
		// // TODO Auto-generated method stub
		// }
		//
		// @Override
		// public void onFinish() {
		// Intent intent = new Intent(LogoActivity.this,
		// LoginActivity.class);
		// startActivity(intent);
		// LogoActivity.this.finish();
		//
		// }
		// }.start();
	}

	@Override
	public void init() {


	}

	@Override
	public void refresh(Object... param) {
		// TODO Auto-generated method stub

	}

}
