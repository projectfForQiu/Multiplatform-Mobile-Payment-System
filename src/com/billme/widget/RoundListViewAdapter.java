package com.billme.widget;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.billme.ui.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.view.ViewPager.LayoutParams;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

@SuppressLint("UseSparseArrays")
public abstract class RoundListViewAdapter extends BaseAdapter {

	public static final String ROUND_TEXT_1 = "text1";
	final Integer TOP_CAP = 1;
	final Integer BOTTOM_CAP = 2;
	final Integer NO_CAP = 3;
	final Integer TOP_AND_BOTTOM = 4;

	List<Map<String, Object>> mData;
	Map<Integer, Integer> itemState;
	List<String> itemHeader;
	Context mContext;

	public RoundListViewAdapter(List<Map<String, Object>> data,
			Context context, List<String> headers) {
		mData = data;
		mContext = context;
		itemState = new HashMap<Integer, Integer>();
		itemHeader = headers;
		setItemState();
	}

	@Override
	public int getCount() {
		return mData == null ? 0 : mData.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		ViewHoder hoder;
		if (convertView == null) {
			hoder = new ViewHoder();
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.round_list_item, null);
			hoder.textView = (TextView) convertView
					.findViewById(R.id.textValue);
			hoder.textHeader = (TextView) convertView
					.findViewById(R.id.sectionHeader);
			convertView.setTag(hoder);
		} else {
			hoder = (ViewHoder) convertView.getTag();
		}
		int state = itemState.get(position);

		Map<String, Object> data = mData.get(position);
		hoder.textView.setText(String.valueOf(data.get(ROUND_TEXT_1)));

		LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		layout.setMargins(0, -2, 0, 0);
		switch (state) {
		case 1:
			hoder.textView.setBackgroundResource(R.drawable.top_round);
			hoder.textHeader.setVisibility(View.VISIBLE);
			layout.setMargins(0, 0, 0, 0);
			hoder.textView.setLayoutParams(layout);
			break;
		case 2:
			hoder.textView.setBackgroundResource(R.drawable.bottom_round);
			hoder.textView.setLayoutParams(layout);
			hoder.textHeader.setVisibility(View.GONE);
			break;
		case 3:
			hoder.textView.setBackgroundResource(R.drawable.no_round);
			hoder.textHeader.setVisibility(View.GONE);
			hoder.textView.setLayoutParams(layout);
			break;
		case 4:
			hoder.textView
					.setBackgroundResource(R.drawable.bottom_and_top_round);
			hoder.textHeader.setVisibility(View.VISIBLE);
			layout.setMargins(0, 0, 0, 0);
			hoder.textView.setLayoutParams(layout);
		default:
			break;
		}

		if (itemHeader != null && (state == 1 || state == 4)) {
			String header = itemHeader.get(getHeaderIndexForPosition(position));
			hoder.textHeader.setText(header);
		} else {
			hoder.textHeader.setText("");
		}
		return convertView;
	}

	/**
	 * 设置分组数
	 * 
	 * @param count
	 */
	public abstract int numberOfRowsInSection();

	/**
	 * 设置每一组显示的个数
	 * 
	 * @param number
	 */
	public abstract int numberOfSectionsInRow(int row);

	static class ViewHoder {
		TextView textView;
		TextView textHeader;
	}

	public void setItemState() {
		int row = numberOfRowsInSection();
		if (row == 0) {
			return;
		}
		int position = 0;
		for (int i = 0; i < row; i++) {

			int rowCount = numberOfSectionsInRow(i);
			for (int j = 0; j < rowCount; j++) {
				if (position == mData.size()) {
					break;
				}
				if (j == 0 && rowCount == 1) {
					itemState.put(position++, TOP_AND_BOTTOM);
					break;
				}
				if (j == 0) {
					itemState.put(position++, TOP_CAP);
				} else if (j == rowCount - 1) {
					itemState.put(position++, BOTTOM_CAP);
				} else {
					itemState.put(position++, NO_CAP);
				}
				System.out.println(position + "_这周");
			}
		}

		int lastState = itemState.get(position - 1);
		boolean isBottom = false;
		if (lastState == TOP_CAP || lastState == NO_CAP) {
			itemState.put(position - 1, TOP_AND_BOTTOM);
			isBottom = false;
		} else {
			isBottom = true;
		}

		int remainder = mData.size() - position;
		if (position < mData.size()) {
			for (int i = 0; i < remainder; i++) {
				// 剩下一个
				if (remainder == 1) {
					itemState.put(position++, TOP_AND_BOTTOM);
					return;
				}
				// 第一个
				if (i == 0 && isBottom) {
					itemState.put(position++, TOP_CAP);
				} else if (i == remainder - 1) {
					itemState.put(position++, BOTTOM_CAP);
				} else {
					itemState.put(position++, NO_CAP);
				}
				System.out.println(position + "_这周");
			}
		}
	}

	private int getHeaderIndexForPosition(int position) {

		int row = numberOfRowsInSection();

		int sumCount = 0;
		for (int i = 0; i < row; i++) {
			int rowCount = numberOfSectionsInRow(i);
			sumCount += rowCount;
			if (sumCount > position) {
				return i;
			}
		}
		return row;
	}

	@Override
	public void notifyDataSetChanged() {
		setItemState();
		super.notifyDataSetChanged();
	}

}
