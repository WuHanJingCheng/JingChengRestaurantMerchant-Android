package com.jingcheng.dininghall.adapter;

import java.util.ArrayList;
import java.util.List;

import com.jingcheng.dininghall.bean.DishInfo;
import com.jingcheng.dininghall.bean.OrderHistoryInfo;
import com.jingcheng.jingchengdininghall.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


public class HistoryGridAdapter extends BaseAdapter {
	private Context context;
	private List<OrderHistoryInfo> list;
	private ViewHolder holder;
	public int select = 0;
	
	
	
	public HistoryGridAdapter(Context context, List<OrderHistoryInfo> list) {
		super();
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		return 29;
	}

	@Override
	public Object getItem(int arg0) {
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null){
			LayoutInflater inflater = LayoutInflater.from(context);
			convertView = inflater.inflate(R.layout.grid_order_history_item, null);
			holder = new ViewHolder();
			holder.grid_order_item = (LinearLayout) convertView.findViewById(R.id.grid_order_item);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		if(position == select){
			holder.grid_order_item.setBackgroundResource(R.drawable.history_order_bg_down);
		}else{
			holder.grid_order_item.setBackgroundResource(R.drawable.history_order_bg);
		}
		holder.grid_order_item.setTag(position);
		holder.grid_order_item.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				select = (int) v.getTag();
				notifyDataSetChanged();
			}
		});
		return convertView;
	}
	
	public class ViewHolder{
		private LinearLayout grid_order_item;
	}
	
}
