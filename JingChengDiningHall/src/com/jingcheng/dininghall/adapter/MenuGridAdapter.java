package com.jingcheng.dininghall.adapter;

import java.util.ArrayList;

import com.jingcheng.dininghall.bean.DishInfo;
import com.jingcheng.jingchengdininghall.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MenuGridAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<DishInfo> list;
	private ViewHolder holder;
	
	public MenuGridAdapter(Context context, ArrayList<DishInfo> list) {
		super();
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
//		return (list == null)?0:list.size();
		return 20;
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
			convertView = inflater.inflate(R.layout.grid_menu_item,null);
			holder = new ViewHolder();
			holder.iv_dish = (ImageView) convertView.findViewById(R.id.iv_dish);
			holder.tv_dish_name = (TextView) convertView.findViewById(R.id.tv_dish_name);
			holder.tv_dish_price = (TextView) convertView.findViewById(R.id.tv_dish_price);
			holder.dish_info = (RelativeLayout) convertView.findViewById(R.id.dish_info);
			holder.dish_add = (ImageView) convertView.findViewById(R.id.dish_add);
			convertView.setTag(holder);  
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		if(position == 0){
			holder.dish_add.setVisibility(View.VISIBLE);
			holder.dish_info.setVisibility(View.GONE);
		}else{
			holder.dish_add.setVisibility(View.GONE);
			holder.dish_info.setVisibility(View.VISIBLE);
//			
//			DishInfo dishInfo = list.get(position);
//			holder.iv_dish.setImageResource(dishInfo.getImage());
//			holder.tv_dish_name.setText(dishInfo.getDishName());
//			holder.tv_dish_price.setText(String.valueOf(dishInfo.getPrice()));
		}
		
		return convertView;
	}
	public class ViewHolder{
		private ImageView iv_dish;
		private TextView tv_dish_name;
		private TextView tv_dish_price;
		private RelativeLayout dish_info;
		private ImageView dish_add;
	}
}
