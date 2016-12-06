package com.jingcheng.dininghall.adapter;


import java.util.ArrayList;

import com.jingcheng.dininghall.activity.MainActivity;
import com.jingcheng.dininghall.bean.Type;
import com.jingcheng.jingchengdininghall.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DishMenuAdapter extends BaseAdapter {
	private ViewHolder holder;
	private Context context;
	private ArrayList<Type> list;
	
	
	public DishMenuAdapter(MainActivity context,ArrayList<Type> list) {
		super();
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		return (list == null)?0:list.size();
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
			convertView = inflater.inflate(R.layout.list_type_item, null);
			holder = new ViewHolder();
			holder.iv_type  = (ImageView) convertView.findViewById(R.id.iv_type);
			holder.tv_type = (TextView) convertView.findViewById(R.id.tv_type);
			holder.menu1 = (RelativeLayout) convertView.findViewById(R.id.menu1);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		Type type = list.get(position);
		holder.iv_type.setImageResource(type.getResId());
		holder.tv_type.setText(type.getSubMenuName());
//		holder.menu1.setTag(position);
//		holder.menu1.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				int position = (int) v.getTag();
//			}
//		});
		return convertView;
	}
	
	class ViewHolder{
		public ImageView iv_type;
		public TextView tv_type;
		public RelativeLayout menu1;
	}
}
