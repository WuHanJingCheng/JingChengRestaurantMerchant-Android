package com.jingcheng.dininghall.adapter;

import java.util.ArrayList;

import com.jingcheng.dininghall.bean.DishInfo;
import com.jingcheng.dininghall.bean.TableInfo;
import com.jingcheng.jingchengdininghall.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class OrderGridAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<TableInfo> list;
	private ViewHolder holder;
	
	
	public OrderGridAdapter(Context context, ArrayList<TableInfo> list) {
		super();
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		return 26;
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
			convertView = inflater.inflate(R.layout.grid_order_item, null);
			holder = new ViewHolder();
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		return convertView;
	}
	
	public class ViewHolder{
		
	}

}
