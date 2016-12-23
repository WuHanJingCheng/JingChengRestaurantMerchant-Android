package com.jingcheng.dininghall.fragment;

import java.util.ArrayList;
import java.util.List;

import com.jingcheng.dininghall.activity.EditDishActivity;
import com.jingcheng.dininghall.activity.MainActivity;
import com.jingcheng.dininghall.bean.DishInfo;
import com.jingcheng.jingchengdininghall.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class MenuFragment extends BaseFragment {
	private GridView grid_menu;
	private Context context;
//	private ArrayList<DishInfo> list;
	private List<DishInfo> mlistInfo = new ArrayList<DishInfo>();
	private MenuGridAdapter adapter = null;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.fragment_menu, container,false);
		context = getActivity();
		grid_menu = (GridView) root.findViewById(R.id.grid_menu);
		mlistInfo = ((MainActivity)getActivity()).getMlistInfo();
		if(adapter == null){
			adapter = new MenuGridAdapter();
		}
		grid_menu.setSelector(new ColorDrawable(Color.TRANSPARENT));
		grid_menu.setAdapter(adapter);
		return root;
	}
	
	/**
	 * 
	 * ���������²˵�����ҳ�� <br>
	 * �˷�������MainActivity�е����Ӧ��subMenuʱ����dish
	 *
	 * @param listInfo MainActivity�е��ô˷���ʱ�贫��dish����
	 */
	
	public void updataUI(List<DishInfo> listInfo){
		mlistInfo = listInfo;
		if(adapter == null){
			adapter = new MenuGridAdapter();
		}
		adapter.notifyDataSetChanged();
	}

	
	public class MenuGridAdapter extends BaseAdapter implements OnClickListener {
//		private Context context;
//		private ArrayList<DishInfo> list;
		private ViewHolder holder;
		
		public MenuGridAdapter() {
			super();
		}

		@Override
		public int getCount() {
//			return (list == null)?0:list.size();
			return mlistInfo.size();
		}

		@Override
		public Object getItem(int arg0) {
			return mlistInfo.get(arg0);
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
				holder.delete = (Button) convertView.findViewById(R.id.bt_delete);
				holder.edit = (Button) convertView.findViewById(R.id.bt_edit);
				convertView.setTag(holder);  
			}else{
				holder = (ViewHolder) convertView.getTag();
			}
			if(position == 0){
				holder.dish_add.setVisibility(View.VISIBLE);
				holder.dish_info.setVisibility(View.GONE);
			}else{
				DishInfo info = mlistInfo.get(position-1);
				holder.dish_add.setVisibility(View.GONE);
				holder.dish_info.setVisibility(View.VISIBLE);
				holder.tv_dish_name.setText(info.getDishName());
				holder.tv_dish_price.setText(String.valueOf(info.getPrice()));
				holder.iv_dish.setImageResource(info.getImage());
			}
			
			holder.delete.setOnClickListener(this);
			holder.edit.setOnClickListener(this);
			holder.dish_add.setOnClickListener(this);
			
			return convertView;
		}
		public class ViewHolder{
			private ImageView iv_dish;
			private TextView tv_dish_name;
			private TextView tv_dish_price;
			private RelativeLayout dish_info;
			private ImageView dish_add;
			private Button delete;
			private Button edit;
		}
		@Override
		public void onClick(View view) {
			switch (view.getId()) {
			case R.id.bt_delete://ɾ��
				dialog();
				break;
			case R.id.bt_edit://�༭
				Intent intent_edit= new Intent(context,EditDishActivity.class);
				intent_edit.putExtra("title", "�༭��Ʒ");
				context.startActivity(intent_edit);
				break;
			case R.id.dish_add://��Ӳ�Ʒ
				Intent intent_add = new Intent(context,EditDishActivity.class);
				intent_add.putExtra("title", "��Ӳ�Ʒ");
				context.startActivity(intent_add);
				break;

			default:
				break;
			}
		}
		/**
		 * �������Զ��嵯��
		 */
		private void dialog(){
			//�����ļ�ת��Ϊview����
	        LayoutInflater inflaterDl = LayoutInflater.from(context);
	        RelativeLayout layout = (RelativeLayout)inflaterDl.inflate(R.layout.layout_dialog, null );
	       
	        //�Ի���
	        final Dialog dialog = new AlertDialog.Builder(context).create();
	        dialog.show();
	        dialog.getWindow().setContentView(layout);
	       
	       
	        //ȡ����ť
	        Button btnCancel = (Button) layout.findViewById(R.id.dialog_cancel);
	        btnCancel.setOnClickListener(new OnClickListener() {
	         
	          @Override
	          public void onClick(View v) {
	        	 dialog.dismiss();
	             Toast.makeText(context, "ȡ��", Toast.LENGTH_SHORT).show();           
	          }
	        });
	       
	       
	        //ȷ����ť
	        Button btnOK = (Button) layout.findViewById(R.id.dialog_exit);
	        btnOK.setOnClickListener(new OnClickListener() {
	         
	          @Override
	          public void onClick(View v) {
	        	  dialog.dismiss();
	             Toast.makeText(context, "��ɾ��", Toast.LENGTH_SHORT).show();           
	          }
	        });
		}
	}
	
	
}
