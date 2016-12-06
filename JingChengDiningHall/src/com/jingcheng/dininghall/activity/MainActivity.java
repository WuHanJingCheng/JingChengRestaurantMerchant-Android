package com.jingcheng.dininghall.activity;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.util.ArrayList;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.jingcheng.dininghall.adapter.DishMenuAdapter;
import com.jingcheng.dininghall.bean.Type;
import com.jingcheng.dininghall.fragment.MenuFragment;
import com.jingcheng.dininghall.fragment.OrderFragment;
import com.jingcheng.jingchengdininghall.R;

public class MainActivity extends BaseActivity implements OnClickListener {
	private ListView menu2_list;
	private ArrayList<Type> list;
	/*
	 * 一级菜单的第一选项（菜单管理）、第二选项（订单管理）
	 */
	private ImageView menu1_select1, menu1_select2;
	private MenuFragment menuFragment;
	private LinearLayout menu1_caidan, menu1_dingdan;
	private OrderFragment orderFragment;
	private RelativeLayout menu2_dish;
	private LinearLayout menu2_order;
	private Fragment mContent = null;
	private LinearLayout menu1;
	private LinearLayout all, jiucan, kongxian, jilu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
		
		menu1_select1 = (ImageView) findViewById(R.id.menu1_select1);
		menu1_select2 = (ImageView) findViewById(R.id.menu1_select2);
		menu1_caidan = (LinearLayout) findViewById(R.id.menu1_caidan);
		menu1_dingdan = (LinearLayout) findViewById(R.id.menu1_dingdan);
		menu2_dish = (RelativeLayout) findViewById(R.id.menu2_dish);
		menu2_order = (LinearLayout) findViewById(R.id.menu2_order);
		menu2_list = (ListView) findViewById(R.id.dishMenu_list);
		jilu = (LinearLayout) findViewById(R.id.jilu);
		all = (LinearLayout) findViewById(R.id.all);
		jiucan = (LinearLayout) findViewById(R.id.jiucan);
		kongxian = (LinearLayout) findViewById(R.id.kongxian);
		menu1 = (LinearLayout) findViewById(R.id.menu1);
		
		menu1_caidan.setOnClickListener(this);
		menu1_dingdan.setOnClickListener(this);
		jilu.setOnClickListener(this);
		all.setOnClickListener(this);
		jiucan.setOnClickListener(this);
		kongxian.setOnClickListener(this);
		
		menuFragment = new MenuFragment();
		orderFragment = new OrderFragment();
		
		init();
//		aaa();
		
	}

	private void init() {
		list = new ArrayList<Type>();
		list.add(new Type(R.drawable.type_rou, "肉类"));
		list.add(new Type(R.drawable.type_haixian, "海鲜"));
		list.add(new Type(R.drawable.type_shucai, "蔬菜"));
		list.add(new Type(R.drawable.type_zhushi, "主食"));
		menu2_list.setAdapter(new DishMenuAdapter(this,list));
		replceMenu(0);
	}
	
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
//		FragmentTransaction ft = getFragmentManager().beginTransaction();
//		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//		ft.replace(R.id.fragment, fragment).commit();
	}
	
	public void replceMenu(int i){
		if(i == 0){//菜单管理
			menu1_select1.setVisibility(View.VISIBLE);
			menu1_select2.setVisibility(View.GONE);
			
			menu2_dish.setVisibility(View.VISIBLE);
			menu2_order.setVisibility(View.GONE);
			
			replceFragment(menuFragment);
		}else if(i == 1){//订单管理
			menu1_select1.setVisibility(View.GONE);
			menu1_select2.setVisibility(View.VISIBLE);
			
			menu2_dish.setVisibility(View.GONE);
			menu2_order.setVisibility(View.VISIBLE);
			
			replceFragment(orderFragment);
		}else{
			Toast.makeText(this, "参数错误！", 0).show();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.menu1_caidan:
			replceMenu(0);
			break;
		case R.id.menu1_dingdan:
			replceMenu(1);
			break;
		case R.id.jilu:
			orderFragment.history();
			break;
		case R.id.all:
			orderFragment.table();
			break;
		case R.id.kongxian:
			orderFragment.table();
			break;
		case R.id.jiucan:
			orderFragment.table();
			break;

		default:
			break;
		}
	}
	
	
//	public void aaa(){
//		String s = "输出";
//		float m = 2.03f;
//		for (int i = 0; i <= 2048; i++) {
//			float count = i/m;
//			System.out.println("<dimen name=\"x" + i + "\">" + count + "dp</dimen>/n");
//			s += "<dimen name=\"x" + i + "\">" + count + "dp</dimen>/n";
//			
//		}  
//		String ss = s + "over";
//		String sss = ss;
		
		
//		
//		BufferedWriter writer ;
//		try {
//			String path = getFilesDir().getAbsolutePath() ;
//			File file = new File(path + "/etc") ;
//			if(!file.exists()){
//				file.mkdirs() ;
//			}
//			File file2 = new File(file.getAbsoluteFile() + "/name.txt") ;
//			FileOutputStream out = new FileOutputStream(file2);
//			writer = new BufferedWriter(new OutputStreamWriter(out)) ;
//			try {
//				
//				float m = 1.36f;
//				
//				for (int i = 1; i <= 2048; i++) {
//					float count = i/m;
//					float cc = new BigDecimal(count).setScale(2, 4).floatValue();   
////					System.out.println("<dimen name=\"x" + i + "\">" + count + "dp</dimen>/n");
//					writer.write("<dimen name=\"x" + i + "\">" + cc + "dp</dimen>") ;
//					writer.write("\r\n");
//					
//				}  
//				
////				writer.write("123123") ;
//				writer.close() ;
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
}
