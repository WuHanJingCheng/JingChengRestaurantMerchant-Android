package com.jingcheng.dininghall.activity;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.jingcheng.dininghall.adapter.SubMenuManagerAdapter.ViewHolder;
import com.jingcheng.dininghall.bean.DishInfo;
import com.jingcheng.dininghall.bean.TableInfo;
import com.jingcheng.dininghall.bean.Type;
import com.jingcheng.dininghall.fragment.MenuFragment;
import com.jingcheng.dininghall.fragment.OrderFragment;
import com.jingcheng.dininghall.utils.BlobHelp;
import com.jingcheng.dininghall.utils.RequestManager;
import com.jingcheng.dininghall.utils.RequestManager.ReqCallBack;
import com.jingcheng.jingchengdininghall.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class MainActivity extends BaseActivity implements OnClickListener {
	public static final String storageConnectionString =
	        "DefaultEndpointsProtocol=https;"
	        + "AccountName=jingchengrestaurant;"
	        + "AccountKey=vgPX6U7Z3+r4X3CRqoX/bfwxaqAZwDccu1EzLKyAHfvtheHwypGy9o7ulhCLE/9+CNJqTPq8r4uGQ29/TWnN/w==";
	private File file;
	
	
	private int ALL = 1;//全部
	private int OCCUPIED = 2;//使用中
	private int AVAILABLE = 3;//空闲
	private int HISTORY= 4;//历史订单
	
	private ListView submenu_dish_list;
	/**
	 * 描述：分类列表数据
	 */
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
	public String menuManage_select_name;//菜单管理中当前选中的submenu名称
	public int orderManege_select = ALL;
	private SharedPreferences sp;
//	
	private List<TableInfo> mTableInfo = new ArrayList<TableInfo>();
	private List<TableInfo> table1 = new ArrayList<TableInfo>();
	private List<TableInfo> table2 = new ArrayList<TableInfo>();
	private List<TableInfo> table3 = new ArrayList<TableInfo>();
	private LinearLayout type_add;
	private DishMenuAdapter dishMenuAdapter;
	private ImageView menu_iv;
	private final int MENU_OK = 0;//获取菜单分类成功
	private final int MENU_ERROR = 1;//获取菜单分类失败
	private final int SUBMENU_OK = 2;//获取子菜单成功
	private final int SUBMENU_ERROR = 3;//获取子菜单失败z
	private final int UPLOAD_OK = 8;//上传图片成功
	private final int UPLOAD_ERROR = 7;//上传图片失败
	private Intent resultData = null;
	private boolean isFrist = true;
	
	private Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MENU_OK://获取菜单分类成功
				//分类列表添加适配器
				dishMenuAdapter = new DishMenuAdapter();
				submenu_dish_list.setAdapter(dishMenuAdapter);
				getSubMenu();
//				loadShow();
				break;
			case MENU_ERROR://获取菜单分类失败
				replceMenu(0);
				Toast.makeText(MainActivity.this, "菜单获取失败，请检查网络", 0).show();
				loadDisMiss();
				break;
			case SUBMENU_OK://获取子菜单成功
				
				replceMenu(0);
				loadDisMiss();
				break;
			case SUBMENU_ERROR://获取子菜单失败
				replceMenu(0);
				Toast.makeText(MainActivity.this, "获取失败，请检查网络", 0).show();
				loadDisMiss();
				break;
			case UPLOAD_OK://上传图片成功
				String uri = msg.getData().getString("URI");//图片URL
				
				String dishName = resultData.getStringExtra("dishName");//获取菜名
				String dishPrice = resultData.getStringExtra("dishPrice");//获取菜价格
//					String json = "get";//          api/menu/264/dish
//					String subMenuUrl = RequestManager.JINGCHENG+"api/menu/"+menuManage_select+"/dish";
//					RequestManager.getInstance(this).requestAsyn(subMenuUrl,
//							0, subMenuReqCallBack, json);
				DishInfo dish = new DishInfo(dishName, dishPrice, uri);
				Gson gson = new Gson();
				String rqt = gson.toJson(dish);
				String subMenuUrl = RequestManager.JINGCHENG+"api/menu/"+menuManage_select+"/dish";
				RequestManager.getInstance(MainActivity.this).requestAsyn(subMenuUrl,
						1, postDishReqCallBack, rqt);
				
			break;
			case UPLOAD_ERROR://上传图片失败
				dialog.dismiss();
				Toast.makeText(MainActivity.this, "上传图片失败", 0).show();
				break;
			}
		}
	};
	
	
	
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
		menu_iv = (ImageView) findViewById(R.id.menu_iv);
		menu_loading = (RelativeLayout) findViewById(R.id.menu_loading);
		fragment = (FrameLayout) findViewById(R.id.fragment);
		
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
		
		options = new DisplayImageOptions.Builder()
        .showStubImage(R.drawable.img_2)  //设置加载时的图片
        .showImageOnFail(R.drawable.img_7) //设置加载失败的图片
        .cacheInMemory()//设置加载的图片缓存在内存中
        .cacheOnDisc()//设置加载的图片缓存在SD卡
//        .displayer(new RoundedBitmapDisplayer(20))//设置成圆角图片
        .bitmapConfig(Bitmap.Config.RGB_565)  
        .build();
		getMenu();
		
//		list.add(new Type(R.drawable.type_rou, R.drawable.type_rou_down,"肉类"));
//		list.add(new Type(R.drawable.type_haixian,R.drawable.type_haixian_down, "海鲜"));
//		list.add(new Type(R.drawable.type_shucai,R.drawable.type_shucai_down, "蔬菜"));
//		list.add(new Type(R.drawable.type_zhushi,R.drawable.type_zhushi_down, "主食"));
		
		/**
		 * 获取分类列表
		 */
		
		
		
		
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
	 * 描述：网络请求，获取分类列表
	 */
	private void getMenu(){
		loadShow();
		String json = "get";
		String subMenuUrl = RequestManager.JINGCHENG+"api/restaurant/63/menu";
		RequestManager.getInstance(this).requestAsyn(subMenuUrl,
				0, menuReqCallBack, json);
	}
	/**
	 * 
	 * 描述：获取分类列表的回调
	 */
	private ReqCallBack menuReqCallBack = new ReqCallBack() {
		
		@Override
		public void onReqSuccess(String result) {
			JSONArray jsonArray;
			try {
				jsonArray = new JSONArray(result);
				if(sp == null){//缓存至首选项
					sp = getSharedPreferences("JingCheng",Activity.MODE_PRIVATE);
				}
				sp.edit().putString("menuData", result).commit();
				menuData(jsonArray);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		@Override
		public void onReqFailed(String errorMsg) {
			Message message = new Message();
			message.what = MENU_ERROR;
			handler.sendMessage(message);
		}
	};
	
	/**
	 * 描述：菜单分类获取成功后对返回的JSON进行解析
	 * @param jsonArray 服务器返回的数据
	 */
	protected void menuData(JSONArray jsonArray) {
		list.clear();
		for (int i = 0; i < jsonArray.length(); i++) {
			try {
				JSONObject	object = jsonArray.getJSONObject(i);
				Type type = new Type(object.getInt("MenuId"),
						object.getString("PictureUrl"), 
						object.getString("PictureUrlSelected"), 
						object.getString("MenuName")
						);
				list.add(type);
				if(i == 0){
					int a =  object.getInt("MenuId");
					menuManage_select =a;//设置当前subMenu
					menuManage_select_name = object.getString("MenuName");
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Message message = new Message();
		message.what = MENU_OK;
		handler.sendMessage(message);
	}
	
	/**
	 * 描述：获取该子菜单分类下的菜
	 * @param i 
	 */
	private void getSubMenu(){
		String json = "get";//          api/menu/264/dish
		String subMenuUrl = RequestManager.JINGCHENG+"api/menu/"+menuManage_select+"/dish";
		RequestManager.getInstance(this).requestAsyn(subMenuUrl,
				0, subMenuReqCallBack, json);
	}
	
	/**
	 * 
	 * 描述：获取该分类下所有菜的回调
	 */
	private ReqCallBack subMenuReqCallBack = new ReqCallBack() {
		
		/**
		 * 
		 * 描述：获取该分类下所有菜的回调
		 */
		private ReqCallBack subMenuReqCallBack = new ReqCallBack() {
			
			@Override
			public void onReqSuccess(String result) {
				try {
					JSONArray jsonArray = new JSONArray(result);
//					if(sp == null){//缓存至首选项
//						sp = getSharedPreferences("JingCheng",Activity.MODE_PRIVATE);
//					}
//					//子菜单以url方式存储首选项 
//					sp.edit().putString(String.valueOf(menuManage_select), result).commit();
					subMenuData(jsonArray);
					
				} catch (JSONException e) {
					e.printStackTrace();
					Message message = new Message();
					message.what = SUBMENU_ERROR;
					handler.sendMessage(message);
				}
			}
			
			@Override
			public void onReqFailed(String errorMsg) {
				Message message = new Message();
				message.what = SUBMENU_ERROR;
				handler.sendMessage(message);
			}
		};

		@Override
		public void onReqSuccess(String result) {
			try {
				JSONArray jsonArray = new JSONArray(result);
				if(sp == null){//缓存至首选项
					sp = getSharedPreferences("JingCheng",Activity.MODE_PRIVATE);
				}
				//子菜单以url方式存储首选项 
				sp.edit().putString(String.valueOf(menuManage_select), result).commit();
				subMenuData(jsonArray);
				
			} catch (JSONException e) {
				e.printStackTrace();
				Message message = new Message();
				message.what = SUBMENU_ERROR;
				handler.sendMessage(message);
			}
		}
		
		@Override
		public void onReqFailed(String errorMsg) {
			Message message = new Message();
			message.what = SUBMENU_ERROR;
			handler.sendMessage(message);
		}
	};
	private DisplayImageOptions options;
	private RelativeLayout menu_loading;
	private FrameLayout fragment;
	
	/**
	 * 描述：对返回的该分类下所有菜进行解析
	 * @param jsonArray
	 */
	private void subMenuData(JSONArray jsonArray) {
		mlistInfo.clear();
		for (int i = 0; i < jsonArray.length(); i++) {
			try {
				JSONObject object = jsonArray.getJSONObject(i);
				DishInfo info = new DishInfo(object.getInt("DishId"), 
						object.getString("DishName"), 
						object.getString("Price"), 
						object.getString("PictureUrlLarge"));
				mlistInfo.add(info);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Message message = new Message();
		message.what = SUBMENU_OK;
		handler.sendMessage(message);
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
//			if(!isFrist){
//				menuFragment.updataUI(mlistInfo);]
//						
//						menuFragment.updataUI(mlistInfo);
//						notifyDataSetChanged();
//			}
			replceFragment(menuFragment);
		}else if(i == 1){//订单管 理
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
			getSubMenu();
			replceMenu(0);
			loadShow();
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
//			Toast.makeText(MainActivity.this, "不可用", 0).show();
			startActivityForResult(new Intent(this,SubMenuManager.class),0);
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
//		private ArrayList<Type> list;
		
		public DishMenuAdapter() {
			super();
//			this.list = list;
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
				LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
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
			if(menuManage_select == type.getTypeId()){
//				holder.iv_type.setImageResource(type.getResId_down());
				ImageLoader.getInstance().displayImage(type.getPictureUrlSelected(), holder.iv_type, options); 
				holder.tv_type.setTextColor(Color.parseColor("#DC9B3E"));
//				loadData();
				if(isFrist){
					menuFragment.updataUI(mlistInfo);
					isFrist = false;
				}
				
			}else{
//				holder.iv_type.setImageResource(type.getResId());
				ImageLoader.getInstance().displayImage(type.getPictureUrl(), holder.iv_type, options); 
				holder.tv_type.setTextColor(Color.parseColor("#000000"));
			}
			holder.tv_type.setText(type.getMenuName());
			
			holder.rl_submenu.setTag(type);
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
			loadShow();
			Type type = (Type) arg0.getTag();
			menuManage_select = type.getTypeId();
			menuManage_select_name = type.getMenuName();
			menuFragment.updataUI(mlistInfo);
			notifyDataSetChanged();
			getSubMenu();
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
//	public void loadData(){
//		if(menuManage_select == 0){
//			mlistInfo = menu1;
//		}else if(menuManage_select == 1){
//			mlistInfo = menu2;
//		}else if(menuManage_select == 2){
//			mlistInfo = menu3;
//		}else{
//			mlistInfo = menu4;
//		}
//		menuFragment.updataUI(mlistInfo);
//	}
	
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == 10000){
			finish();
		}else if(resultCode == 9999){
			Toast.makeText(this, "添加分类成功", 0).show();
		}else if(resultCode == 8888){//添加菜品
			try {
				resultData = data;
				file = new File(new URI(data.getStringExtra("dishImg")));
				new BlobHelp(file, menuManage_select_name, handler).execute();
				uploadLoading();
				
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	private ReqCallBack postDishReqCallBack = new ReqCallBack() {
		
		@Override
		public void onReqSuccess(String result) {
			dialog.dismiss();
			loadShow();
			getSubMenu();
			Toast.makeText(MainActivity.this, "上传成功", 0).show();
		}
		
		@Override
		public void onReqFailed(String errorMsg) {
			Toast.makeText(MainActivity.this, "上传失败", 0).show();
		}
	};
	private ImageView upload_iv;
	private Button upload_bt;
	private Dialog dialog;
//	private RelativeLayout layout;
	
	/**
	 * 展示加载动画
	 */
	private void loadShow(){
		menu_loading.setVisibility(View.VISIBLE);
		fragment.setVisibility(View.GONE);
		Glide.with(this).load(R.drawable.loading).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(menu_iv);
		ObjectAnimator anim = ObjectAnimator.ofFloat(menu_iv, "alpha", 0f, 1f);  
		anim.setDuration(600);
		anim.start();
		anim.addListener(new AnimatorListener() {
			
			@Override
			public void onAnimationStart(Animator arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animator arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animator arg0) {
				// TODO Auto-generated method stub
//				menu_loading.setVisibility(View.VISIBLE);
				
			}
			
			@Override
			public void onAnimationCancel(Animator arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	/**
	 * 隐藏加载动画
	 */
	private void loadDisMiss(){
//		Glide.with(this).load(R.drawable.loading).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(menu_iv);
		ObjectAnimator anim = ObjectAnimator.ofFloat(menu_iv, "alpha", 1f, 0f);  
		anim.setDuration(400);
		anim.start();
		
		menu_loading.setVisibility(View.GONE);
		fragment.setVisibility(View.VISIBLE);
		ObjectAnimator anim1 = ObjectAnimator.ofFloat(fragment, "alpha", 0f, 1f);  
		anim1.setDuration(400);
		anim1.start();
		anim.addListener(new AnimatorListener() {
			
			@Override
			public void onAnimationStart(Animator arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animator arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animator arg0) {
//				menu_loading.setVisibility(View.GONE);
//				fragment.setVisibility(View.VISIBLE);
//				ObjectAnimator anim = ObjectAnimator.ofFloat(fragment, "alpha", 0f, 1f);  
//				anim.setDuration(700);
//				anim.start();
			}
			
			@Override
			public void onAnimationCancel(Animator arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	
	/**
	 * 描述：自定义弹窗
	 */
	private void uploadLoading(){
//		//布局文件转换为view对象
//        LayoutInflater inflaterDl = LayoutInflater.from(MainActivity.this);
//        RelativeLayout layout = (RelativeLayout)inflaterDl.inflate(R.layout.upload_dialog, null );
//       
////        upload_iv = (ImageView)findViewById(R.id.upload_iv);
////        upload_bt = (Button) findViewById(R.id.upload_bt);
////        Glide.with(this).load(R.drawable.upload_iv).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(upload_iv);
//        upload_dialog = new AlertDialog.Builder(MainActivity.this).create();
//        upload_dialog.show();
//        upload_dialog.getWindow().setContentView(layout);
//        upload_bt.setOnClickListener(new OnClickListener() {
//        	
//        	@Override
//        	public void onClick(View arg0) {
//        		upload_dialog.dismiss();
//        	}
//        });
		//布局文件转换为view对象
        LayoutInflater inflaterDl = LayoutInflater.from(MainActivity.this);
        RelativeLayout layout = (RelativeLayout)inflaterDl.inflate(R.layout.upload_dialog, null );
       if(dialog == null){
    	   dialog = new AlertDialog.Builder(MainActivity.this).create();
    	   dialog.setCancelable(false);
       }
        dialog.show();
        dialog.getWindow().setContentView(layout);
       
//        ImageView upload_iv = (ImageView)findViewById(R.id.upload_iv);
//        upload_bt = (Button) findViewById(R.id.upload_bt);
//        Glide.with(this).load(R.drawable.upload_iv).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(upload_iv);
//        //取消按钮
//        Button btnCancel = (Button) layout.findViewById(R.id.dialog_cancel);
//        btnCancel.setOnClickListener(new OnClickListener() {
//         
//          @Override
//          public void onClick(View v) {
//        	 dialog.dismiss();
//             Toast.makeText(MainActivity.this, "取消", Toast.LENGTH_SHORT).show();
//          }
//        });
//       
//       
//        //确定按钮
//        Button btnOK = (Button) layout.findViewById(R.id.dialog_exit);
//        btnOK.setOnClickListener(new OnClickListener() {
//         
//          @Override
//          public void onClick(View v) {
//        	  dialog.dismiss();
//             Toast.makeText(MainActivity.this, "已删除", Toast.LENGTH_SHORT).show();           
//          }
//        });
//       
	}

}
