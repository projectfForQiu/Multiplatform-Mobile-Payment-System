package com.billme.ui;

import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;

public class BaseActivity extends Activity{

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		return super.onOptionsItemSelected(item);
	}

	public void pushMessage(){
		
	}
}
