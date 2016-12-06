package com.jingcheng.dininghall.fragment;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;

import com.jingcheng.dininghall.adapter.HistoryGridAdapter;
import com.jingcheng.dininghall.adapter.OrderGridAdapter;
import com.jingcheng.dininghall.adapter.OrderListAdapter;
import com.jingcheng.dininghall.bean.OrderHistoryInfo;
import com.jingcheng.dininghall.bean.OrderInfo;
import com.jingcheng.dininghall.bean.TableInfo;
import com.jingcheng.jingchengdininghall.R;

public class OrderFragment extends BaseFragment{
	private Context context;
	private ArrayList<OrderInfo> order_list;
	private ArrayList<TableInfo> table_list;
	private ArrayList<OrderHistoryInfo> history_list;
	private OrderListAdapter order_adapter;
	private OrderGridAdapter grid_adapter;
	private HistoryGridAdapter history_adapter;
	private ListView lv;
	private GridView grid_order;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.fragment_order, container,false);
		
		context = getActivity();
		
		lv = (ListView) root.findViewById(R.id.order_lv);
		grid_order = (GridView) root.findViewById(R.id.grid_order);
		
		init();
		return root;
	}

	private void init() {
		order_list = new ArrayList<OrderInfo>();
		for (int i = 0; i < 15; i++) {
			Boolean push = false;
			if(i == 3 || i == 8 || i == 9 || i == 10){
				push = true;
			}
			OrderInfo info = new OrderInfo("ºÚ½·Å£ÅÅ", 98.00f, 1, push);
			order_list.add(info);
		}
		order_adapter = new OrderListAdapter(context, order_list);
		lv.setAdapter(order_adapter);
		
		
		table_list = new ArrayList<TableInfo>();
		grid_adapter = new OrderGridAdapter(context, table_list);
		grid_order.setAdapter(grid_adapter);
	}
	
	public void history(){
		for (int i = 0; i < 15; i++) {
			Boolean push = false;
			if(i == 3 || i == 8 || i == 9 || i == 10){
				push = true;
			}
			OrderInfo info = new OrderInfo("ºÚ½·Å£ÅÅ", 98.00f, 1, push);
			order_list.add(info);
		}
		order_list = new ArrayList<OrderInfo>();
		order_adapter = new OrderListAdapter(context, order_list);
		lv.setAdapter(order_adapter);
		
		
		history_list = new ArrayList<OrderHistoryInfo>();
		history_adapter = new HistoryGridAdapter(context, history_list);
		grid_order.setNumColumns(3);
		grid_order.setVerticalSpacing(40);
		grid_order.setHorizontalSpacing(48);
		grid_order.setAdapter(history_adapter);
		
	}
	
	
	public void table(){
		for (int i = 0; i < 15; i++) {
			Boolean push = false;
			if(i == 3 || i == 8 || i == 9 || i == 10){
				push = true;
			}
			OrderInfo info = new OrderInfo("ºÚ½·Å£ÅÅ", 98.00f, 1, push);
			order_list.add(info);
		}
		order_list = new ArrayList<OrderInfo>();
		order_adapter = new OrderListAdapter(context, order_list);
		lv.setAdapter(order_adapter);
		

		table_list = new ArrayList<TableInfo>();
		grid_adapter = new OrderGridAdapter(context, table_list);
		grid_order.setNumColumns(4);
		grid_order.setVerticalSpacing(100);
		grid_order.setHorizontalSpacing(100);
		grid_order.setAdapter(grid_adapter);
	}
}
