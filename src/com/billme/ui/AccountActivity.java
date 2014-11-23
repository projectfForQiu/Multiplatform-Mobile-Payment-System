package com.billme.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.QuickContactBadge;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.billme.logic.BillMeActivity;
import com.billme.widget.RoundAdapte;
import com.billme.widget.RoundListViewAdapter;

public class AccountActivity extends BaseActivity implements BillMeActivity {

	private QuickContactBadge userIcon;
	private TextView infoUserName;
	private TextView infoUserBalance;
	private ListView accountAdmins;
	private RoundAdapte adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_account);
		userIcon = (QuickContactBadge) this.findViewById(R.id.qcb_userIcon);
		infoUserName = (TextView) this
				.findViewById(R.id.tv_imformation_userName);
		infoUserBalance = (TextView) this
				.findViewById(R.id.tv_imformation_acountBalance);
		accountAdmins = (ListView) this.findViewById(R.id.lv_account_admin);

		String[] adapterData = new String[] { "Preferential Cards",
				"VIP Cards", "Bills", "Bank Cards", "Setting" };
		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
		for (String str : adapterData) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(RoundListViewAdapter.ROUND_TEXT_1, str);
			listMap.add(map);
		}

		List<String> listHeader = new ArrayList<String>();
		for (int i = 0; i < 30; i++) {
			listHeader.add("第" + i + "组");
		}
		adapter = new RoundAdapte(listMap, this, listHeader);
		accountAdmins.setAdapter(adapter);
		accountAdmins.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				System.out.println(position + "++++");
			}
		});
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public void refresh(Object... param) {
		// TODO Auto-generated method stub

	}

}
/*
 * public class AccountActivity extends Activity {
 * 
 * private ListView listView; private List<Map<String, Object>> mData;
 * 
 * @Override protected void onCreate(Bundle savedInstanceState) {
 * super.onCreate(savedInstanceState);
 * setContentView(R.layout.activity_account); listView = (ListView)
 * findViewById(R.id.listview); SimpleAdapter simpleAdapter = new
 * SimpleAdapter(this, getData(), R.layout.list_item, new String[] {
 * "appnametext", "sizetext", "amounttext" }, new int[] { R.id.appnamentext,
 * R.id.sizetext, R.id.amounttext }); listView.setAdapter(simpleAdapter); mData
 * = getData(); MyAdapter adapter = new MyAdapter(this);
 * listView.setAdapter(adapter); }
 * 
 * public List<Map<String, Object>> getData() { List<Map<String, Object>> list =
 * new ArrayList<Map<String, Object>>(); Map<String, Object> map = new
 * HashMap<String, Object>();
 * 
 * map.put("appnametext", "微信"); map.put("sizetext", "15.2");
 * map.put("amounttext", "1234323"); map.put("appimg", R.drawable.skype);
 * list.add(map);
 * 
 * map = new HashMap<String, Object>(); map.put("appnametext", "手机QQ");
 * map.put("sizetext", "8.5"); map.put("amounttext", "122073323");
 * map.put("appimg", R.drawable.icq); list.add(map);
 * 
 * map = new HashMap<String, Object>(); map.put("appnametext", "手机QQ空间");
 * map.put("sizetext", "6.3"); map.put("amounttext", "122393");
 * map.put("appimg", R.drawable.facebook); list.add(map);
 * 
 * map = new HashMap<String, Object>(); map.put("appnametext", "微博");
 * map.put("sizetext", "7.7"); map.put("amounttext", "1278323");
 * map.put("appimg", R.drawable.twitter); list.add(map);
 * 
 * map = new HashMap<String, Object>(); map.put("appnametext", "陌陌");
 * map.put("sizetext", "6.9"); map.put("amounttext", "1279073");
 * map.put("appimg", R.drawable.wordpress); list.add(map);
 * 
 * map = new HashMap<String, Object>(); map.put("appnametext", "飞信");
 * map.put("sizetext", "6.9"); map.put("amounttext", "1279073");
 * map.put("appimg", R.drawable.rss); list.add(map); return list; }
 * 
 * public final class ViewHolder { public ImageView appimg; public TextView
 * appnametext; public TextView sizetext; public TextView amounttext; public
 * Button dowmbutton; }
 * 
 * public class MyAdapter extends BaseAdapter {
 * 
 * private LayoutInflater mInflater;
 * 
 * public MyAdapter(Context context) { this.mInflater =
 * LayoutInflater.from(context); }
 * 
 * @Override public int getCount() { return mData.size(); }
 * 
 * @Override public Object getItem(int arg0) { return null; }
 * 
 * @Override public long getItemId(int arg0) { return 0; }
 * 
 * @Override public View getView(int arg0, View arg1, ViewGroup arg2) {
 * ViewHolder holder = null; if (arg1 == null) {
 * 
 * holder = new ViewHolder();
 * 
 * arg1 = mInflater.inflate(R.layout.list_item, null); holder.appimg =
 * (ImageView) arg1.findViewById(R.id.appimg); holder.appnametext = (TextView)
 * arg1 .findViewById(R.id.appnamentext); holder.sizetext = (TextView)
 * arg1.findViewById(R.id.sizetext); holder.amounttext = (TextView) arg1
 * .findViewById(R.id.amounttext); holder.dowmbutton = (Button)
 * arg1.findViewById(R.id.dowmbutton);
 * 
 * arg1.setTag(holder); } else { holder = (ViewHolder) arg1.getTag(); }
 * 
 * holder.appimg.setBackgroundResource((Integer) mData.get(arg0).get(
 * "appimg")); holder.appnametext.setText((String) mData.get(arg0).get(
 * "appnametext")); holder.sizetext.setText((String)
 * mData.get(arg0).get("sizetext")); holder.amounttext.setText((String)
 * mData.get(arg0) .get("amounttext"));
 * 
 * return arg1; }
 * 
 * } }
 */

/*
 * @SuppressLint("NewApi") public class AccountActivity extends Activity{
 * 
 * @Override protected void onCreate(Bundle arg0) { // TODO Auto-generated
 * method stub super.onCreate(arg0); setContentView(R.layout.learn_fragment);
 * 
 * FragmentManager fragmentManager = getFragmentManager(); FragmentTransaction
 * fragmentTransaction = fragmentManager.beginTransaction();
 * 
 * ExampleFragment fragment = new ExampleFragment();
 * fragmentTransaction.add(R.id.linear,fragment); fragmentTransaction.commit();
 * }
 * 
 * }
 */

/*
 * public class AccountActivity extends BaseActivity implements BillMeActivity {
 * 
 * final int SUM = 3; TextView[] mTVs; ImageView[] mBGs; int mPreClickID = 0;
 * int mCurClickID = 0;
 * 
 * @Override protected void onCreate(Bundle savedInstanceState) { // TODO
 * Auto-generated method stub super.onCreate(savedInstanceState);
 * setContentView(R.layout.activity_account); initView(); }
 * 
 * public void initView() { mTVs = new TextView[SUM]; mTVs[0] = (TextView)
 * this.findViewById(R.id.tv_amount); mTVs[1] = (TextView)
 * this.findViewById(R.id.tv_amount_tag); mTVs[2] = (TextView)
 * this.findViewById(R.id.tv_current_page); mBGs = new ImageView[SUM]; mBGs[0] =
 * (ImageView) this.findViewById(R.id.iv_qr_image); mBGs[1] = (ImageView)
 * this.findViewById(R.id.iv_qr_image); mBGs[2] = (ImageView)
 * this.findViewById(R.id.iv_qr_image); for (int i = 0; i < SUM; ++i) {
 * mTVs[i].setOnClickListener(clickListener); } mTVs[0].setEnabled(false);
 * mPreClickID = 0; }
 * 
 * private void updataCurView(int curClickID) { if (0 <= curClickID && SUM >
 * curClickID) { mTVs[mPreClickID].setEnabled(true);
 * mTVs[curClickID].setEnabled(false);
 * mBGs[mPreClickID].setVisibility(View.INVISIBLE);
 * mBGs[curClickID].setVisibility(View.VISIBLE); mPreClickID = curClickID; } }
 * 
 * private void startSlip(View v) { Animation a = new TranslateAnimation(0.0f,
 * v.getLeft() - mTVs[mPreClickID].getLeft(), 0.0f, 0.0f); a.setDuration(400);
 * a.setFillAfter(false); a.setFillBefore(false); for (int i = 0; i < SUM; i++)
 * { if (mTVs[i] == v) { mCurClickID = i; break; } } a.setAnimationListener(new
 * AnimationListener() {
 * 
 * @Override public void onAnimationStart(Animation animation) { // TODO
 * Auto-generated method stub
 * 
 * }
 * 
 * @Override public void onAnimationRepeat(Animation animation) { // TODO
 * Auto-generated method stub
 * 
 * }
 * 
 * @Override public void onAnimationEnd(Animation animation) { // TODO
 * Auto-generated method stub
 * 
 * } }); mBGs[mPreClickID].startAnimation(a);
 * 
 * }
 * 
 * private OnClickListener clickListener = new OnClickListener() {
 * 
 * @Override public void onClick(View v) { startSlip(v);
 * 
 * } };
 * 
 * @Override protected void onResume() { // TODO Auto-generated method stub
 * super.onResume(); }
 * 
 * @Override public void init() { // TODO Auto-generated method stub
 * 
 * }
 * 
 * @Override public void refresh(Object... param) { // TODO Auto-generated
 * method stub
 * 
 * }
 * 
 * }
 */