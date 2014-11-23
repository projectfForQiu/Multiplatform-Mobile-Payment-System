package com.billme.ui;

import java.util.HashMap;
import java.util.LinkedList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import com.billme.logic.BillMeActivity;
import com.billme.logic.MainService;
import com.zxing.activity.CaptureActivity;


public class PaymentActivity extends BaseActivity implements BillMeActivity {

	public static int PAY_SUCCESS = 200;
	public static int PAY_FAiLURE = 201;
	private Button btnPayByQRCode;
	private Button btnPayByNFC;
	private Button btnClearCart;
	private Button btnPayCart;
	private LinkedList<HashMap<String, String>> myList = new LinkedList<HashMap<String, String>>();
	private ListView lvItemRecords;
	private BaseAdapter myItemRecords;
	private AlertDialog dialog = null;
	private TextView tvAmount = null;;
	private String Receiver = null;
	private double Amount = 0;
	private ArrayAdapter<String> adapter;
	private String choice = "";
	private String []tempBankCard;
	//private LinkedList<BankCard> bankCard;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_payment);

//		bankCard = MainService.getUser().getBankCards();
//		tempBankCard = new String[bankCard.size()];
//		for (int i = 0; i < bankCard.size(); ++i) {
//			tempBankCard[i] = bankCard.get(i).getBankName()+" "+bankCard.get(i).getCardNumber();
//		}
		
		
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, tempBankCard);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		tvAmount = (TextView) this.findViewById(R.id.tv_amount);

		btnPayByQRCode = (Button) this.findViewById(R.id.btn_pay_by_QRcode);
		btnPayByQRCode.setOnClickListener(new QRCodeOnClickListener());

		btnPayByNFC = (Button) this.findViewById(R.id.btn_pay_by_NFC);
		btnPayByNFC.setOnClickListener(new NFCOnClickListener());

		lvItemRecords = (ListView) this.findViewById(R.id.lv_item_records);
		// for (int i = 0; i < 1; i++) {
		// HashMap<String, String> map = new HashMap<String, String>();
		// map.put("itemName", "a commodity");
		// map.put("itemPrice", "1100");
		// myList.add(map);
		// }

//		myItemRecords = new SimpleAdapter(this, myList,
//				R.layout.trading_record_list_item, new String[] { "itemName",
//						"itemPrice", "del" }, new int[] { R.id.tv_item_name,
//						R.id.tv_item_price, R.id.del });
//		// myItemRecords = new MyAdapter(this, myList);
//		lvItemRecords.setAdapter(myItemRecords);
//
//		btnClearCart = (Button) this.findViewById(R.id.btn_clear_cart);
//		btnClearCart.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				myList.clear();
//				myItemRecords.notifyDataSetChanged();
//				tvAmount.setText("0.0");
//				Amount = 0;
//			}
//		});
//
//		btnPayCart = (Button) this.findViewById(R.id.btn_pay_cart);
//		btnPayCart.setOnClickListener(new payCartOnClickListener());

		MainService.allActivities.add(this);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			Bundle bundle = data.getExtras();
			String scanResult = bundle.getString("result");
			
			String[] tempString = scanResult.split(",");
			for(int i =0;i<tempString.length;++i){
				Log.i("error", tempString[i]);
			}
			
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("itemName", tempString[0]);
			map.put("itemPrice", tempString[1]);
			myList.add(map);
			myItemRecords.notifyDataSetChanged();

			Receiver = tempString[2];
			Amount += Double.valueOf(tempString[1]);
			tvAmount.setText(String.valueOf(Amount));
			myItemRecords.notifyDataSetChanged();
		}
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public void refresh(Object... param) {
		if (((Integer) param[0]).intValue() == PAY_SUCCESS) {
			Toast.makeText(this, "Pay Bill successfully", Toast.LENGTH_SHORT)
					.show();
		} else {
			Toast.makeText(this, "Pay Bill failurily", Toast.LENGTH_SHORT)
					.show();
		}

	}

	class QRCodeOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			Intent openCameraIntent = new Intent(PaymentActivity.this,
					CaptureActivity.class);
			startActivityForResult(openCameraIntent, 0);
		}

	}

	class NFCOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("itemName", "testCommodity");
			map.put("itemPrice", "10");
			myList.add(map);
			myItemRecords.notifyDataSetChanged();

			Receiver = "201302";
			Amount += Double.valueOf("10");
			tvAmount.setText(String.valueOf(Amount));
		}

	}

	class payCartOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			if (dialog == null) {
				LayoutInflater layoutInflater = LayoutInflater
						.from(PaymentActivity.this);
				View tempView = layoutInflater.inflate(
						R.layout.confirm_payment, null);
				dialog = new AlertDialog.Builder(PaymentActivity.this)
						.setTitle("Confirm Payment")
						.setView(tempView)
						.setPositiveButton("Pay!", new PayOnClickListener())
						.setNegativeButton("Cancel",
								new cancleOnClickListener()).show();
				Spinner spBankCards = (Spinner) ((Dialog) dialog)
						.findViewById(R.id.sp_bankCard);
				spBankCards.setAdapter(adapter);
				spBankCards.setOnItemSelectedListener(new SpinnerSelectedListener());
				spBankCards.setVisibility(View.VISIBLE);
//				choice = bankCard.get(0).getCardNumber();
				Log.i("error", choice);
			} else {
				dialog.show();
			}
		}

	}

	class PayOnClickListener implements DialogInterface.OnClickListener {

		@Override
		public void onClick(DialogInterface dialog, int which) {

		}

	}

	class cancleOnClickListener implements DialogInterface.OnClickListener {

		@Override
		public void onClick(DialogInterface dialog, int which) {
			((Dialog) dialog).hide();
			EditText etPayPassword = (EditText) ((Dialog) dialog)
					.findViewById(R.id.et_paymentPassword);
			etPayPassword.setText("");
		}

	}
	
	class SpinnerSelectedListener implements OnItemSelectedListener {

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
//			choice = bankCard.get(arg2).getCardNumber();
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
		}
	}
}
