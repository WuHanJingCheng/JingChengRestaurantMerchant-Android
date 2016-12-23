package com.jingcheng.dininghall.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jingcheng.dininghall.bean.DishInfo;
import com.jingcheng.dininghall.bean.TableInfo;
import com.jingcheng.dininghall.bean.Type;
import com.jingcheng.dininghall.fragment.MenuFragment;
import com.jingcheng.dininghall.fragment.OrderFragment;
import com.jingcheng.jingchengdininghall.R;

public class MainActivity extends BaseActivity implements OnClickListener {
	
	private int ALL = 1;//全部
	private int OCCUPIED = 2;//使用中
	private int AVAILABLE = 3;//空闲
	private int HISTORY= 4;//历史订单
	
	private ListView submenu_dish_list;
	private ArrayList<Type> list;
	/*
	 * 一级菜单的第一选项（菜单管理）、第二选项（订单管理）
	 */
	private ImageView menu_caidan_select, menu_dingdan_select;
	private MenuFragment menuFragment;
	private OrderFragment orderFragment;
	private RelativeLayout submenu_dish;
	private Fragment mContent = null;
	private LinearLayout submenu_order, menu_dingdan, menu_caidan, menu_personal, menu, submenu_table_all, 
	submenu_table_occupied, submenu_table_available, submenu_order_history;
	private ImageView history_iv, all_iv, occupied_iv, available_iv;
	private TextView history_tv, all_tv, occupied_tv, available_tv;
	private List<DishInfo> mlistInfo = new ArrayList<DishInfo>();//subMenu菜品集合
	
	public int menuManage_select = 0;//菜单管理中当前选中的submenu
	public int orderManege_select = ALL;
	
	private List<DishInfo> menu1 = new ArrayList<DishInfo>();//用作模拟数据 接口接通后删除
	private List<DishInfo> menu2 = new ArrayList<DishInfo>();//用作模拟数据 接口接通后删除
	private List<DishInfo> menu3 = new ArrayList<DishInfo>();//用作模拟数据 接口接通后删除
	private List<DishInfo> menu4 = new ArrayList<DishInfo>();//用作模拟数据 接口接通后删除
	
	private List<TableInfo> mTableInfo = new ArrayList<TableInfo>();
	private List<TableInfo> table1 = new ArrayList<TableInfo>();
	private List<TableInfo> table2 = new ArrayList<TableInfo>();
	private List<TableInfo> table3 = new ArrayList<TableInfo>();
	private LinearLayout type_add;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		menu_caidan_select = (ImageView) findViewById(R.id.menu_caidan_select);
		menu_dingdan_select = (ImageView) findViewById(R.id.menu_dingdan_select);
		menu_caidan = (LinearLayout) findViewById(R.id.menu_caidan);
		menu_dingdan = (LinearLayout) findViewById(R.id.menu_dingdan);
		submenu_dish = (RelativeLayout) findViewById(R.id.submenu_dish);
		submenu_order = (LinearLayout) findViewById(R.id.submenu_order);
		submenu_dish_list = (ListView) findViewById(R.id.submenu_dish_list);
		submenu_order_history = (LinearLayout) findViewById(R.id.submenu_order_history);
		history_iv = (ImageView) findViewById(R.id.history_iv);
		history_tv = (TextView) findViewById(R.id.history_tv);
		submenu_table_all = (LinearLayout) findViewById(R.id.submenu_table_all);
		all_iv = (ImageView) findViewById(R.id.all_iv);
		all_tv = (TextView) findViewById(R.id.all_tv);
		submenu_table_occupied = (LinearLayout) findViewById(R.id.submenu_table_occupied);
		occupied_iv = (ImageView) findViewById(R.id.occupied_iv);
		occupied_tv = (TextView) findViewById(R.id.occupied_tv);
		submenu_table_available = (LinearLayout) findViewById(R.id.submenu_table__available);
		available_iv = (ImageView) findViewById(R.id.available_iv);
		available_tv = (TextView) findViewById(R.id.available_tv);
		menu = (LinearLayout) findViewById(R.id.menu);
		menu_personal = (LinearLayout) findViewById(R.id.menu_personal);
		type_add = (LinearLayout) findViewById(R.id.type_add);
		
		type_add.setOnClickListener(this);
		menu_caidan.setOnClickListener(this);
		menu_dingdan.setOnClickListener(this);
		submenu_order_history.setOnClickListener(this);
		submenu_table_all.setOnClickListener(this);
		submenu_table_occupied.setOnClickListener(this);
		submenu_table_available.setOnClickListener(this);
		menu_personal.setOnClickListener(this);
		
		menuFragment = new MenuFragment();
		orderFragment = new OrderFragment();
		
		init();
//		aaa();
		
	}
	/**
	 * 描述： 初始化  submenu导航栏数据  模拟menu数据
	 * 
	 */
	private void init() {
		list = new ArrayList<Type>();
		list.add(new Type(R.drawable.type_rou, R.drawable.type_rou_down,"肉类"));
		list.add(new Type(R.drawable.type_haixian,R.drawable.type_haixian_down, "海鲜"));
		list.add(new Type(R.drawable.type_shucai,R.drawable.type_shucai_down, "蔬菜"));
		list.add(new Type(R.drawable.type_zhushi,R.drawable.type_zhushi_down, "主食"));
		submenu_dish_list.setAdapter(new DishMenuAdapter(this, list));
		replceMenu(0);
		
		menu1.add(new DishInfo(01, "dishName_01", 1.11f, R.drawable.img_1));
		menu1.add(new DishInfo(02, "dishName_02", 2.22f, R.drawable.img_2));
		menu1.add(new DishInfo(03, "dishName_03", 3.33f, R.drawable.img_3));
		menu1.add(new DishInfo(04, "dishName_04", 4.44f, R.drawable.img_4));
		menu1.add(new DishInfo(05, "dishName_05", 5.55f, R.drawable.img_5));
		menu1.add(new DishInfo(06, "dishName_06", 6.66f, R.drawable.img_6));
		menu1.add(new DishInfo(07, "dishName_07", 7.77f, R.drawable.img_7));
		
		menu2.add(new DishInfo(001, "dishName_001", 1.11f, R.drawable.img_7));
		menu2.add(new DishInfo(002, "dishName_002", 2.22f, R.drawable.img_5));
		menu2.add(new DishInfo(003, "dishName_003", 3.33f, R.drawable.img_4));
		menu2.add(new DishInfo(004, "dishName_004", 4.44f, R.drawable.img_2));
		menu2.add(new DishInfo(005, "dishName_005", 5.55f, R.drawable.img_1));
		menu2.add(new DishInfo(006, "dishName_006", 6.66f, R.drawable.img_3));
		menu2.add(new DishInfo(007, "dishName_007", 7.77f, R.drawable.img_6));
		
		menu3.add(new DishInfo(0001, "dishName_0001", 1.11f, R.drawable.img_2));
		menu3.add(new DishInfo(0002, "dishName_0002", 2.22f, R.drawable.img_4));
		menu3.add(new DishInfo(0003, "dishName_0003", 3.33f, R.drawable.img_3));
		menu3.add(new DishInfo(0004, "dishName_0004", 4.44f, R.drawable.img_7));
		menu3.add(new DishInfo(0005, "dishName_0005", 5.55f, R.drawable.img_5));
		menu3.add(new DishInfo(0006, "dishName_0006", 6.66f, R.drawable.img_1));
		menu3.add(new DishInfo(0007, "dishName_0007", 7.77f, R.drawable.img_6));
		
		menu4.add(new DishInfo(00001, "dishName_00001", 1.11f, R.drawable.img_1));
		menu4.add(new DishInfo(00002, "dishName_00002", 2.22f, R.drawable.img_5));
		menu4.add(new DishInfo(00003, "dishName_00003", 3.33f, R.drawable.img_2));
		menu4.add(new DishInfo(00004, "dishName_00004", 4.44f, R.drawable.img_3));
		menu4.add(new DishInfo(00005, "dishName_00005", 5.55f, R.drawable.img_6));
		menu4.add(new DishInfo(00006, "dishName_00006", 6.66f, R.drawable.img_4));
		menu4.add(new DishInfo(00007, "dishName_00007", 7.77f, R.drawable.img_7));
		mlistInfo = menu1;
		
		//模拟【全部】数据 
		int j = 1;
		for (int i = 0; i < 20; i++) {
			if(i == 5||i == 6||i == 7||i == 15|| i == 18){
				j = 0;
			}else if(i == 2||i == 10||i == 11||i == 20){
				j = 2;
			}
			TableInfo tableInfo = new TableInfo(i, j);
			table1.add(tableInfo);
		}
		
		//模拟【就餐中】数据
		for (int i = 0; i < 25; i++) {
			TableInfo tableInfo = new TableInfo(i, 2);
			table2.add(tableInfo);
		}
		
		//模拟【空闲】数据
		for (int i = 0; i < 28; i++) {
			TableInfo tableInfo = new TableInfo(i, 1);
			table3.add(tableInfo);
		}
		mTableInfo = table1;
	}
	
	/**
	 * 
	 * 描述： 替换fragment方法 ,此方法仅在replceMenu()中被调用
	 */
	private void replceFragment(Fragment fragment){
		if(mContent != fragment){
			FragmentTransaction ft = getFragmentManager().beginTransaction();
			if(!fragment.isAdded()){
				if(mContent != null){
					ft.hide(mContent).add(R.id.fragment, fragment).commit();
				}else{
					ft.add(R.id.fragment, fragment).commit();
				}
			}else{
				ft.hide(mContent).show(fragment).commit();
			}
			mContent = fragment;
		}
	}
	
	/**
	 * 描述：替换菜单
	 * @param i   0表示切换菜单管理 ; 1表示切换订单管理
	 */
	public void replceMenu(int i){
		if(i == 0){//菜单管理
			menu_caidan_select.setVisibility(View.VISIBLE);
			menu_dingdan_select.setVisibility(View.GONE);
			
			submenu_dish.setVisibility(View.VISIBLE);
			submenu_order.setVisibility(View.GONE);
			
			replceFragment(menuFragment);
		}else if(i == 1){//订单管理
			menu_caidan_select.setVisibility(View.GONE);
			menu_dingdan_select.setVisibility(View.VISIBLE);
			
			submenu_dish.setVisibility(View.GONE);
			submenu_order.setVisibility(View.VISIBLE);
			
			replceFragment(orderFragment);
		}else{
			Toast.makeText(this, "参数错误！", 0).show();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.menu_caidan://一级菜单---菜单管理
			replceMenu(0);
			break;
		case R.id.menu_dingdan://一级菜单---订单管理
			if(orderManege_select == ALL){
				subMenuOrderSelect(all_iv);
			}else if(orderManege_select == AVAILABLE){
				subMenuOrderSelect(available_iv);
			}else if(orderManege_select == OCCUPIED){
				subMenuOrderSelect(occupied_iv);
			}else if(orderManege_select == HISTORY){
				subMenuOrderSelect(history_iv);
			}
			replceMenu(1);
			break;
		case R.id.submenu_order_history://二级菜单---历史查看
			subMenuOrderSelect(history_iv);
			orderFragment.setHistorySelect(0);
			orderFragment.updataHistoryUI();
			orderManege_select = HISTORY;
			break;
		case R.id.submenu_table_all://二级菜单---全部
			subMenuOrderSelect(all_iv);
			mTableInfo = table1;
			orderFragment.setTableSelect(0);
			orderFragment.updataTableUI(mTableInfo,ALL);
			orderManege_select = ALL;
			break;
		case R.id.submenu_table__available://二级菜单---空闲
			subMenuOrderSelect(available_iv);
			mTableInfo = table3;
			orderFragment.setTableSelect(0);
			orderFragment.updataTableUI(mTableInfo,AVAILABLE);
			orderManege_select = AVAILABLE;
			break;
		case R.id.submenu_table_occupied://二级菜单---使用中
			subMenuOrderSelect(occupied_iv);
			mTableInfo = table2;
			orderFragment.setTableSelect(0);
			orderFragment.updataTableUI(mTableInfo,OCCUPIED);
			orderManege_select = OCCUPIED;
			break;
		case R.id.menu_personal://查看我的信息
			Intent intent = new Intent(this,UserInfoActivity.class);
			startActivityForResult(intent,0);
			break;
		case R.id.type_add://添加菜品分类
			startActivityForResult(new Intent(this,AddSubMenuActivity.class),0);
			break;

		default:
			break;
		}
	}
	
	
	/**
	 * 描述： 对订单管理中的二级菜单的item进行切换状态显示 （全部、就餐中、空闲、记录） 
	 * @param iv  传入点击item的ImageView
	 */
	public void subMenuOrderSelect(ImageView iv){
		all_iv.setImageResource(R.drawable.all);
		occupied_iv.setImageResource(R.drawable.occupied);
		available_iv.setImageResource(R.drawable.available);
		history_iv.setImageResource(R.drawable.history);
		
		all_tv.setTextColor(Color.parseColor("#000000"));
		occupied_tv.setTextColor(Color.parseColor("#000000"));
		available_tv.setTextColor(Color.parseColor("#000000"));
		history_tv.setTextColor(Color.parseColor("#000000"));
		
		if(iv == all_iv){
			all_iv.setImageResource(R.drawable.all_down);
			all_tv.setTextColor(Color.parseColor("#DC9B3E"));
		}else if(iv == occupied_iv){
			occupied_iv.setImageResource(R.drawable.occupied_down);
			occupied_tv.setTextColor(Color.parseColor("#DC9B3E"));
		}else if(iv == available_iv){
			available_iv.setImageResource(R.drawable.available_down);
			available_tv.setTextColor(Color.parseColor("#DC9B3E"));
		}else if(iv == history_iv){
			history_iv.setImageResource(R.drawable.history_down);
			history_tv.setTextColor(Color.parseColor("#DC9B3E"));
	
		}
	}

	
	public class DishMenuAdapter extends BaseAdapter implements OnClickListener {
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
				holder.rl_submenu = (RelativeLayout) convertView.findViewById(R.id.rl_submenu);
				convertView.setTag(holder);
			}else{
				holder = (ViewHolder) convertView.getTag();
			}
			Type type = list.get(position);
			if(menuManage_select == position){
				holder.iv_type.setImageResource(type.getResId_down());
				holder.tv_type.setTextColor(Color.parseColor("#DC9B3E"));
				loadData();
				
			}else{
				holder.iv_type.setImageResource(type.getResId());
				holder.tv_type.setTextColor(Color.parseColor("#000000"));
			}
			holder.tv_type.setText(type.getSubMenuName());
			
			holder.rl_submenu.setTag(position);
			holder.rl_submenu.setOnClickListener(this);
			return convertView;
		}
		
		public class ViewHolder{
			public ImageView iv_type;
			public TextView tv_type;
			public RelativeLayout rl_submenu;
		}

		@Override
		public void onClick(View arg0) {
			menuManage_select = (int) arg0.getTag();
			notifyDataSetChanged();
		}
	}
	
	public List<DishInfo> getMlistInfo() {
		return mlistInfo;
	}
	public void setMlistInfo(List<DishInfo> mlistInfo) {
		this.mlistInfo = mlistInfo;
	}
	
	
	
	public List<TableInfo> getmTableInfo() {
		return mTableInfo;
	}
	public void setmTableInfo(List<TableInfo> mTableInfo) {
		this.mTableInfo = mTableInfo;
	}
	/**
	 * 描述： 加载submenu下的dish  更新数据  <br>
	 * 此方法在切换菜单管理时调用一次  <br>
	 * 在DishMenuAdapter的getView()中每次更新时加载<br>
	 */
	public void loadData(){
		if(menuManage_select == 0){
			mlistInfo = menu1;
		}else if(menuManage_select == 1){
			mlistInfo = menu2;
		}else if(menuManage_select == 2){
			mlistInfo = menu3;
		}else{
			mlistInfo = menu4;
		}
		menuFragment.updataUI(mlistInfo);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == 10000){
			finish();
		}else if(resultCode == 9999){
			Toast.makeText(this, "添加分类成功", 0).show();
		}
	}

}
