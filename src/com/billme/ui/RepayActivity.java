package com.billme.ui;


import com.billme.logic.BillMeActivity;
import com.billme.logic.MainService;
import com.google.zxing.WriterException;
import com.zxing.encoding.EncodingHandler;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

public class RepayActivity extends BaseActivity implements BillMeActivity {

	private Spinner payeeAcount;

	private ArrayAdapter<String> adapter;
	private EditText money;
	private Button generateQRCodeButton;
	private Button cancelButton;
	private ImageView qrImgImageView;
	private String choice;
//	private String[] tempBankCard = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_repayment);

//		LinkedList<BankCard> bankCard = MainService.getUser().getBankCards();
//		tempBankCard = new String[bankCard.size()];
//		for (int i = 0; i < bankCard.size(); ++i) {
//			tempBankCard[i] = bankCard.get(i).getBankName()+" "+bankCard.get(i).getCardNumber();
//			if(i == 0){
//				choice = bankCard.get(i).getCardNumber();
//			}
//		}
		payeeAcount = (Spinner) this.findViewById(R.id.et_payee_acount);
//		adapter = new ArrayAdapter<String>(this,
//				android.R.layout.simple_spinner_item, tempBankCard);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		payeeAcount.setAdapter(adapter);
		payeeAcount.setOnItemSelectedListener(new SpinnerSelectedListener());
		payeeAcount.setVisibility(View.VISIBLE);
		
		money = (EditText) this.findViewById(R.id.et_money);
		qrImgImageView = (ImageView) this.findViewById(R.id.iv_qr_image);

		generateQRCodeButton = (Button) this.findViewById(R.id.btn_generate);
		generateQRCodeButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					String contentString = "giveme,"
							+ money.getText().toString() + ","
							+ choice;
					Bitmap qrCodeBitmap = EncodingHandler.createQRCode(
							contentString, 350);
					qrImgImageView.setImageBitmap(qrCodeBitmap);

				} catch (WriterException e) {
					e.printStackTrace();
				}
			}
		});

		cancelButton = (Button) this.findViewById(R.id.btn_cancel);
		cancelButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent myIntent = new Intent(RepayActivity.this,
						MainActivity.class);
				RepayActivity.this.startActivity(myIntent);
			}
		});

		MainService.allActivities.add(this);
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public void refresh(Object... param) {
		// TODO Auto-generated method stub

	}

	class SpinnerSelectedListener implements OnItemSelectedListener {

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
//			choice = tempBankCard[arg2];
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
		}
	}
}
