package com.jingcheng.dininghall.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jingcheng.dininghall.activity.MainActivity;
import com.jingcheng.dininghall.adapter.HistoryGridAdapter;
import com.jingcheng.dininghall.adapter.OrderGridAdapter;
import com.jingcheng.dininghall.adapter.OrderListAdapter;
import com.jingcheng.dininghall.bean.DishInfo;
import com.jingcheng.dininghall.bean.OrderHistoryInfo;
import com.jingcheng.dininghall.bean.OrderInfo;
import com.jingcheng.dininghall.bean.TableInfo;
import com.jingcheng.dininghall.fragment.MenuFragment.MenuGridAdapter;
import com.jingcheng.jingchengdininghall.R;

public class OrderFragment extends BaseFragment{
	
	private int ALL = 1;//全部
	private int OCCUPIED = 2;//使用中
	private int AVAILABLE = 3;//空闲
	private int HISTORY= 4;//历史订单
	private Context context;
	private List<OrderInfo> order_list = null;
	private List<TableInfo> table_list = null;
	private List<OrderHistoryInfo> history_list;
	private OrderListAdapter order_adapter;
	private OrderGridAdapter grid_adapter;
	private HistoryGridAdapter history_adapter;
	private ListView lv;
	private GridView grid_table;
	private RelativeLayout comm;
	private LinearLayout order_add_ll;
	private RelativeLayout orderinfo_layout_out;//停用布局
	private RelativeLayout orderinfo_layout;//未停用布局
	private TextView out_text;
	private Button out_bt;
	private ImageView out_iv_info;
	private TextView out_tv_info;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.fragment_order, container,false);
		
		context = getActivity();
		
		lv = (ListView) root.findViewById(R.id.order_lv);
		order_add_ll = (LinearLayout) root.findViewById(R.id.order_add_ll);
		grid_table = (GridView) root.findViewById(R.id.grid_table);
		comm = (RelativeLayout) root.findViewById(R.id.comm);
		orderinfo_layout_out = (RelativeLayout) root.findViewById(R.id.orderinfo_layout_out);
		orderinfo_layout = (RelativeLayout) root.findViewById(R.id.orderinfo_layout);
		out_text = (TextView) root.findViewById(R.id.out_text);
		out_bt = (Button) root.findViewById(R.id.out_bt);
		out_iv_info = (ImageView) root.findViewById(R.id.out_iv_info);
		out_tv_info = (TextView) root.findViewById(R.id.out_tv_info);
		
		init();
		return root;
	}

	private void init() {
		//设置添加桌子点击监听
		order_add_ll.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(context, "添加桌子", 0).show();
			}
		});
		
		//初始化历史订单
		history_list = new ArrayList<OrderHistoryInfo>();
		history_adapter = new HistoryGridAdapter(context, history_list);
		
		//初始化订单详情
		order_list = new ArrayList<OrderInfo>();
		
		//从MainActivity获取table信息
		table_list = ((MainActivity)getActivity()).getmTableInfo();
		
		//初始化grid_table 默认为餐桌列表
		grid_adapter = new OrderGridAdapter(context, table_list, this);
		grid_table.setSelector(new ColorDrawable(Color.TRANSPARENT));
		grid_table.setAdapter(grid_adapter);
	}
	
	/**
	 * 
	 * 描述：更新餐桌管理页面 <br>
	 * 此方法用于MainActivity中点击相应的餐桌状态时更新
	 * @param table_list MainActivity中调用此方法时需传入table_list数据
	 */
	
	public void updataTableUI(List<TableInfo> table_list,int i){
		if(i == ALL){
			comm.setVisibility(View.VISIBLE);
		}else{
			comm.setVisibility(View.GONE);
		}
		this.table_list = table_list;
		if(grid_adapter == null){
			grid_adapter = new OrderGridAdapter(context, table_list, this);
		}else{
			grid_adapter.list = table_list;
		}
		grid_adapter.notifyDataSetChanged();
		grid_table.setNumColumns(4);
		grid_table.setVerticalSpacing(100);
		grid_table.setHorizontalSpacing(100);
		grid_table.setAdapter(grid_adapter);
	}
	
	/**
	 * 描述：切换历史订单信息
	 */
	public void updataHistoryUI(){
		comm.setVisibility(View.GONE);
		grid_table.setNumColumns(3);
		grid_table.setVerticalSpacing(40);
		grid_table.setHorizontalSpacing(48);
		grid_table.setAdapter(history_adapter);
		updataOrderInfo(4);
	}

	
	/**
	 * 描述：更新订单详情（右侧）数据
	 */
	public void updataOrderInfo(int tag){
		
		if(tag == 2 || tag == 3){// 2表示使用中 ; 3表示停用
			orderinfo_layout_out.setVisibility(View.GONE);
			orderinfo_layout.setVisibility(View.VISIBLE);
			order_list.clear();
			for (int i = 0; i < 15; i++) {
				Boolean push = false;
				if(i == 3 || i == 8 || i == 9 || i == 10){
					push = true;
				}
				OrderInfo info = new OrderInfo("黑椒牛排", 98.00f, 1, push);
				order_list.add(info);
			}
			order_list = new ArrayList<OrderInfo>();
			order_adapter = new OrderListAdapter(context, order_list);
			lv.setAdapter(order_adapter);
		}else if(tag == 0){//停用状态
			orderinfo_layout_out.setVisibility(View.VISIBLE);
			orderinfo_layout.setVisibility(View.GONE);
			out_text.setText("启用此桌");
			out_bt.setBackgroundResource(R.drawable.out_bt_close);
			out_iv_info.setImageResource(R.drawable.out_iv_info);
			out_tv_info.setText("此桌已停用！");
		}else if(tag == 1){//空闲状态
			orderinfo_layout_out.setVisibility(View.VISIBLE);
			orderinfo_layout.setVisibility(View.GONE);
			out_text.setText("停用此桌");
			out_bt.setBackgroundResource(R.drawable.out_bt_open);
			out_iv_info.setImageResource(R.drawable.out_iv_info_down);
			out_tv_info.setText("此桌还未开台");
		}else if(tag == 4){//历史订单信息
			orderinfo_layout_out.setVisibility(View.GONE);
			orderinfo_layout.setVisibility(View.VISIBLE);
			order_list.clear();
			for (int i = 0; i < 15; i++) {
				Boolean push = false;
				if(i == 3 || i == 8 || i == 9 || i == 10){
					push = true;
				}
				OrderInfo info = new OrderInfo("黑椒牛排", 98.00f, 1, push);
				order_list.add(info);
			}
			order_list = new ArrayList<OrderInfo>();
			order_adapter = new OrderListAdapter(context, order_list);
			lv.setAdapter(order_adapter);
		}
	}
	
	/**
	 * 描述：设置餐桌Grid中默认选择的item
	 */
	public void setTableSelect(int i){
		grid_adapter.select = i;
	}
	
	/**
	 * 描述：设置历史订单中默认选择的item
	 */
	public void setHistorySelect(int i){
		history_adapter.select = i;
	}
}
