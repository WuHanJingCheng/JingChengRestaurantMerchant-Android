package com.jingcheng.dininghall.adapter;

import java.util.ArrayList;

import com.jingcheng.dininghall.activity.MainActivity;
import com.jingcheng.dininghall.activity.MainActivity.DishMenuAdapter.ViewHolder;
import com.jingcheng.dininghall.bean.Type;
import com.jingcheng.jingchengdininghall.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SubMenuManagerAdapter extends BaseAdapter implements OnClickListener {
	private ArrayList<Type> menuList = new ArrayList<Type>();
	private Context context;
	private ViewHolder holder;
	private OnButtonClickListener mListener;
	private DisplayImageOptions options  = new DisplayImageOptions.Builder()
//	    .showStubImage(R.drawable.img_2)  //设置加载时的图片
//	    .showImageOnFail(R.drawable.img_7) //设置加载失败的图片
	    .cacheInMemory()//设置加载的图片缓存在内存中
	    .cacheOnDisc()//设置加载的图片缓存在SD卡
	//    .displayer(new RoundedBitmapDisplayer(20))//设置成圆角图片
	    .bitmapConfig(Bitmap.Config.RGB_565)  
	    .build();
	
	public SubMenuManagerAdapter(ArrayList<Type> menuList, Context context, OnButtonClickListener Listener) {
		super();
		this.menuList = menuList;
		this.mListener = Listener;
		this.context = context;
	}

	@Override
	public int getCount() {
		return menuList.size()+1;
	}

	@Override
	public Object getItem(int arg0) {
		return menuList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null){
			LayoutInflater inflater = LayoutInflater.from(context);
			convertView = inflater.inflate(R.layout.submenu_manager_layout, null);
			holder = new ViewHolder();
			holder.delete  = (Button) convertView.findViewById(R.id.submenu_manager_delete);
			holder.tv = (TextView) convertView.findViewById(R.id.submenu_manager_tv);
			holder.img = (ImageView) convertView.findViewById(R.id.submenu_manager_img);
			holder.submenu_manager_show = (RelativeLayout) convertView.findViewById(R.id.submenu_manager_show);
			holder.submenu_manager_add = (ImageView) convertView.findViewById(R.id.submenu_manager_add);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.position = position;
		if(menuList.size()>position){
			Type type = menuList.get(position);
			holder.submenu_manager_show.setVisibility(View.VISIBLE);
			holder.submenu_manager_add.setVisibility(View.GONE);
			holder.tv.setText(type.getMenuName());
			ImageLoader.getInstance().displayImage(type.getPictureUrlSelected(), holder.img, options); 
			
			holder.type = type;
			holder.delete.setTag(holder);
			holder.submenu_manager_show.setTag(holder);
			
			holder.delete.setOnClickListener(this);
			holder.submenu_manager_show.setOnClickListener(this);
		}else{
			holder.submenu_manager_show.setVisibility(View.GONE);
			holder.submenu_manager_add.setVisibility(View.VISIBLE);
			holder.submenu_manager_add.setOnClickListener(this);
		}
		return convertView;
	}
	
	public class ViewHolder{
		public Type type;
		public ImageView img;
		public TextView tv;
		public Button delete;
		public RelativeLayout submenu_manager_show;
		public ImageView submenu_manager_add;
		public int position;
	}

	public interface OnButtonClickListener{
		void onButtonClick(View view);
	}

	@Override
	public void onClick(View view) {
		mListener.onButtonClick(view);
	}
}
