package com.jingcheng.dininghall.adapter;

import java.io.ObjectInputStream.GetField;
import java.util.ArrayList;
import java.util.List;

import com.jingcheng.dininghall.bean.DishInfo;
import com.jingcheng.dininghall.bean.TableInfo;
import com.jingcheng.dininghall.fragment.OrderFragment;
import com.jingcheng.jingchengdininghall.R;
import com.jingcheng.jingchengdininghall.R.id;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class OrderGridAdapter extends BaseAdapter {
	private Context context;
	public List<TableInfo> list = new ArrayList<TableInfo>();
	private ViewHolder holder;
	public int select = 0;
	private OrderFragment fragment;
	
	public OrderGridAdapter(Context context, List<TableInfo> table_list,OrderFragment fragment) {
		super();
		this.context = context;
		this.list = table_list;
		this.fragment = fragment;
	}

	@Override
	public int getCount() {
		return list.size();
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
			holder.tableId = (TextView) convertView.findViewById(R.id.grid_tableId);
			holder.table_bg = (ImageView) convertView.findViewById(R.id.grid_table_bg);
			holder.grid_table_item = (RelativeLayout) convertView.findViewById(R.id.grid_table_item);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		TableInfo info = list.get(position);
		holder.tableId.setText(String.valueOf(info.getTableId())+"号桌");
		int tag = info.getTableTag();
		if(tag == 0){//停用
			if(position == select){
				holder.table_bg.setImageResource(R.drawable.table_shutdown_down);
				fragment.updataOrderInfo(0);//更新右侧餐桌数据
			}else{
				holder.table_bg.setImageResource(R.drawable.table_shutdown);
			}
		}else if(tag == 1){//空闲
			if(position == select){
				holder.table_bg.setImageResource(R.drawable.table_available_dow);
				fragment.updataOrderInfo(1);//更新右侧餐桌数据
			}else{
				holder.table_bg.setImageResource(R.drawable.table_availabl);
			}
		}else if(tag == 2){//使用
			if(position == select){
				holder.table_bg.setImageResource(R.drawable.table_occupied_down);
				fragment.updataOrderInfo(2);//更新右侧餐桌数据
			}else{
				holder.table_bg.setImageResource(R.drawable.table_occupied);
			}
		}else{
			Toast.makeText(context, "Tag参数错误", 0).show();
		}
		
		holder.grid_table_item.setTag(position);
		holder.grid_table_item.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				select = (int) view.getTag();
				notifyDataSetChanged();
			}
		});
		return convertView;
	}
	
	public class ViewHolder{
		private TextView tableId;
		private ImageView table_bg;
		private RelativeLayout grid_table_item;
	}

}
