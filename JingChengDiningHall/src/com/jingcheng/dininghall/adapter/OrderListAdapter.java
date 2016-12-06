package com.jingcheng.dininghall.adapter;

import java.util.ArrayList;

import com.jingcheng.dininghall.bean.OrderInfo;
import com.jingcheng.jingchengdininghall.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class OrderListAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<OrderInfo> list;
	private ViewHolder holder;
	
	
	
	public OrderListAdapter(Context context, ArrayList<OrderInfo> list) {
		super();
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		return 15;
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
			convertView = inflater.inflate(R.layout.list_orderinfo_item, null);
			holder = new ViewHolder();
//			holder.name = (CustomFontEditText) convertView.findViewById(R.id.name);
//			holder.count = (CustomFontEditText) convertView.findViewById(R.id.count);
//			holder.price = (CustomFontEditText) convertView.findViewById(R.id.price);
//			holder.cb = (CheckBox) convertView.findViewById(R.id.cb);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		
//		OrderInfo info = list.get(position);
//		holder.name.setText(position+1+"."+info.getName());
//		holder.count.setText(String.valueOf(info.getCount()));
//		holder.price.setText(String.valueOf(info.getPrice()));
//		if(info.getPush()){
//			holder.cb.setChecked(true);
//		}else{
//			holder.cb.setChecked(false);
//		}
		return convertView;
	}
	
	
	public class ViewHolder{
		private TextView name;
		private TextView count;
		private TextView price;
		private CheckBox cb;
	}
}
