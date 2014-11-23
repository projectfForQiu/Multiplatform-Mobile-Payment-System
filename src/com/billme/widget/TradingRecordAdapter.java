package com.billme.widget;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.billme.ui.R;
import com.billme.widget.XListView.PinnedHeaderAdapter;

public class TradingRecordAdapter extends BaseExpandableListAdapter implements
		PinnedHeaderAdapter, OnScrollListener{

	private ArrayList<String[]> group;
	private ArrayList<ArrayList<String[]>> item;
	// private Context context;
	private LayoutInflater inflater;

	public TradingRecordAdapter(Context context, ArrayList<String[]> group,
			ArrayList<ArrayList<String[]>> item) {
		// this.context = context;
		inflater = LayoutInflater.from(context);
		this.group = group;
		this.item = item;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return item.get(groupPosition).get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return groupPosition * 10000 + childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		View view = convertView;
		if (view == null) {
			view = inflater.inflate(R.layout.trading_record_list_item, null);

		}
		TextView recordName = (TextView) view.findViewById(R.id.tv_record_name);
		TextView tradeDate = (TextView) view.findViewById(R.id.tv_trading_date);
		TextView tradePrice = (TextView) view
				.findViewById(R.id.tv_trading_price);
		TextView tradeState = (TextView) view
				.findViewById(R.id.tv_trading_state);
		String[] tempItem = item.get(groupPosition).get(childPosition);
		recordName.setText(tempItem[0]);
		tradeDate.setText(tempItem[1]);
		tradePrice.setText(tempItem[2]);
		tradeState.setText(tempItem[3]);
		recordName.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.i("error", "touch me again");
				
			}
		});
		tradeDate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.i("error", "touch me again");
				
			}
		});
		return view;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return item.get(groupPosition).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return group.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return group.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		View view = convertView;
		if (view == null) {
			view = inflater.inflate(R.layout.trading_record_group_item, null);
		}
		String[] tempItem = group.get(groupPosition);
		((TextView) view.findViewById(R.id.tv_mounth_label))
				.setText(tempItem[0]);
		((TextView) view.findViewById(R.id.tv_income_amount_label))
				.setText(tempItem[1]);
		((TextView) view.findViewById(R.id.tv_outcome_amount_label))
				.setText(tempItem[2]);

		return view;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	@Override
	public int getPinnedHeaderState(long position, long nextPosition) {
		int type = ExpandableListView.getPackedPositionType(position);
		int nextType = ExpandableListView.getPackedPositionType(nextPosition);
		if (type == ExpandableListView.PACKED_POSITION_TYPE_NULL) {
			return PINNED_HEADER_GONE;
		}

		if (type == ExpandableListView.PACKED_POSITION_TYPE_GROUP
				&& nextType == type) {
			return PINNED_HEADER_GONE;
		}
		if (type == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
			int cpos = ExpandableListView.getPackedPositionChild(position);
			int gpos = ExpandableListView.getPackedPositionGroup(position);
			if ((cpos + 1) == item.get(gpos).size()) {
				return PINNED_HEADER_PUSHED_UP;
			}
		}
		return PINNED_HEADER_VISIBLE;
	}

	@Override
	public void configurePinnedHeader(View header, long position, int alpha) {
		int gpos = ExpandableListView.getPackedPositionGroup(position);
		String[] str = group.get(gpos);
		((TextView) header.findViewById(R.id.tv_mounth_label)).setText(str[0]);
		((TextView) header.findViewById(R.id.tv_income_amount_label))
				.setText(str[1]);
		((TextView) header.findViewById(R.id.tv_outcome_amount_label))
				.setText(str[2]);
	}


	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		if (view instanceof XListView) {
			((XListView) view).configureHeaderView(firstVisibleItem);
		}

	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub

	}

}
