package com.billme.util;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.billme.ui.R;

public class MyAdapter extends BaseAdapter{
	
	private List<HashMap<String, String>> arrays = null;
	private Context mContext;
	private Button curDel_btn;
	private float x,ux;
	
	public MyAdapter(Context mContext, List<HashMap<String, String>> arrays){
		this.mContext = mContext;
		this.arrays = arrays;
	}
	
	@Override
	public int getCount() {
		return this.arrays.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View view, ViewGroup arg2) {
		ViewHolder viewHolder = null;
		if(view == null){
			viewHolder = new ViewHolder();
			view = LayoutInflater.from(mContext).inflate(R.layout.listview_itemrecords, null);
			viewHolder.tvTitle = (TextView)view.findViewById(R.id.tv_item_name);
			viewHolder.tvPrice = (TextView)view.findViewById(R.id.tv_item_price);
			viewHolder.btnDel = (Button) view.findViewById(R.id.del);
			view.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) view.getTag();
		}
		
		//为每个view项设置触控监听
		view.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				final ViewHolder holder = (ViewHolder)v.getTag();
				//当按下时处理
				if(event.getAction() == MotionEvent.ACTION_DOWN){
					v.setBackgroundResource(R.drawable.bg_yellow);
					//获取按下时的x轴坐标
					x = event.getX();
					//判断之前是否出现了删除按钮如果存在就隐藏
					if(curDel_btn != null){
						curDel_btn.setVisibility(View.GONE);
					}
				}else if(event.getAction() == MotionEvent.ACTION_UP){
					v.setBackgroundResource(0);
					//获取松开时的x坐标
					ux = event.getX();
					//判断当前项中按钮控件不为空时
					if(holder.btnDel != null){
						//按下和松开绝对值差当大于20时显示删除按钮，否则不显示
						if(Math.abs(x-ux)>20){
							holder.btnDel.setVisibility(View.VISIBLE);
							curDel_btn = holder.btnDel;
						}
					}
				}else if(event.getAction() == MotionEvent.ACTION_MOVE){
					v.setBackgroundResource(R.drawable.bg_yellow);
				}else{
					v.setBackgroundResource(0);
				}
				return true;
			}
		});
		
		viewHolder.tvTitle.setText(this.arrays.get(position).get("itemName"));
		viewHolder.tvPrice.setText(this.arrays.get(position).get("itemPrice"));
		//为删除按钮添加监听事件
		viewHolder.btnDel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(curDel_btn != null){
					curDel_btn.setVisibility(View.GONE);
					arrays.remove(position);
					notifyDataSetChanged();
				}
				
			}
		});
		return view;
	}

	final static class ViewHolder{
		TextView tvTitle;
		TextView tvPrice;
		Button btnDel;
	}
}
