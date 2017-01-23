package com.jingcheng.dininghall.activity;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;
import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.Animator.AnimatorListener;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.jingcheng.dininghall.adapter.SubMenuManagerAdapter;
import com.jingcheng.dininghall.adapter.SubMenuManagerAdapter.OnButtonClickListener;
import com.jingcheng.dininghall.adapter.SubMenuManagerAdapter.ViewHolder;
import com.jingcheng.dininghall.bean.DishInfo;
import com.jingcheng.dininghall.bean.Type;
import com.jingcheng.dininghall.utils.BlobHelp;
import com.jingcheng.dininghall.utils.ImageFactory;
import com.jingcheng.dininghall.utils.RequestManager;
import com.jingcheng.dininghall.utils.RequestManager.ReqCallBack;
import com.jingcheng.jingchengdininghall.R;

public class SubMenuManager extends BaseActivity implements OnClickListener {
	private Button sub_bcak;
	private TextView sub_setting;
	private GridView sub_grid;
	private ArrayList<Type> menuList = new ArrayList<Type>();
	private SubMenuManagerAdapter sma;
	private View remove_view = null;
	private Dialog dialog;
	private ImageView img;
	private File file_icon;
	private File file_icon_down;
	private String subMenuName;
	private ImageFactory imgf = null;
	
	
	private String MenuName = null;
	private String PictureUrl = null;
	private String PictureUrlSelected = null;
	private Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 8://�ϴ�icon�ɹ�
				if(PictureUrl == null && PictureUrlSelected == null){
					PictureUrl = msg.getData().getString("URI");//ͼƬURL
					if(imgf == null){
						imgf = new ImageFactory();
					}
					try {
						imgf.ratioAndGenThumb(file_icon_down.getPath(), file_icon_down.getPath(),  84f, 84f,false);
						new BlobHelp(file_icon_down, subMenuName, handler, MenuName).execute();
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else if(PictureUrl != null && PictureUrlSelected == null){
					PictureUrlSelected = msg.getData().getString("URI");//ͼƬURL
					Toast.makeText(SubMenuManager.this, "icon�ϴ��ɹ�", 0).show();
					//��MenuName������PictureUrl������PictureUrlSelected��
					Type type = new Type(PictureUrl,PictureUrlSelected,MenuName);
					Gson gson = new Gson();
					String rqt = gson.toJson(type);//api/restaurant/{id}/menu 
					String subMenuUrl = RequestManager.JINGCHENG+"api/restaurant/63/menu";
					RequestManager.getInstance(SubMenuManager.this).requestAsyn(subMenuUrl,
							1, postMenuReqCallBack, rqt);
				}
				break;
				//	new BlobHelp(file_icon_down, subMenuName, handler).execute();
			default:
				break;
			}
		};
	};
	
	/**
	 * 
	 * ��������ȡ�����б�Ļص�
	 */
	private ReqCallBack postMenuReqCallBack = new ReqCallBack() {
		
		@Override
		public void onReqSuccess(String result) {
			file_icon = null;
			file_icon_down = null;
			dialog.dismiss();
			Toast.makeText(SubMenuManager.this, "������ӳɹ�", 0).show();
			getSubMenuData();
		}
		
		@Override
		public void onReqFailed(String errorMsg) {
			file_icon = null;
			file_icon_down = null;
			dialog.dismiss();
			Toast.makeText(SubMenuManager.this, "���ʧ��", 0).show();
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_submenu_manager);
		setFinishOnTouchOutside(false);//  ���ô���dialog���Ƿ�finish
		sub_bcak = (Button) findViewById(R.id.sub_bcak);
		sub_setting = (TextView) findViewById(R.id.sub_setting);
		sub_grid = (GridView) findViewById(R.id.sub_grid);
		img = (ImageView) findViewById(R.id.iviv);
//		// ����ͼƬ
//		Glide.with(this).load(R.drawable.loading).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(img);
		sub_bcak.setOnClickListener(this);
		sub_setting.setOnClickListener(this);
		getSubMenuData();
	}

	private void getSubMenuData() {
		loadShow();
		String json = "get����";
		//      api/restaurant/{id}/menu 
		String subMenuUrl = RequestManager.JINGCHENG+"api/restaurant/63/menu";
		RequestManager.getInstance(this).requestAsyn(subMenuUrl,
				0, subMenuReqCallBack, json);
	}

	private ReqCallBack subMenuReqCallBack = new ReqCallBack() {
		
		@Override
		public void onReqSuccess(String result) {
			JSONArray jsonArray;
			try {
				jsonArray = new JSONArray(result);
				menuData(jsonArray);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		@Override
		public void onReqFailed(String errorMsg) {
			if(Integer.valueOf(errorMsg) == 404){
				Toast.makeText(SubMenuManager.this, "����Ϊ��", 0).show();
				loadDisMiss();
				menuList.clear();
				initAdapter();
			}
		}
	};
	
	
	
	/**
	 * �������˵������ȡ�ɹ���Է��ص�JSON���н���
	 * @param jsonArray ���������ص�����
	 */
	protected void menuData(JSONArray jsonArray) {
		menuList.clear();
		for (int i = 0; i < jsonArray.length(); i++) {
			try {
				JSONObject	object = jsonArray.getJSONObject(i);
				Type type = new Type(object.getInt("MenuId"),
						object.getString("PictureUrl"), 
						object.getString("PictureUrlSelected"), 
						object.getString("MenuName"));
				menuList.add(type);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		initAdapter();
		loadDisMiss();
	}
	
	private void initAdapter() {
		if(sma == null){
			sma = new SubMenuManagerAdapter(menuList,this,new OnButtonClickListener() {
				
				private View view;

				@Override
				public void onButtonClick(View view) {
					switch (view.getId()) {
					case R.id.submenu_manager_show://����
						ViewHolder holder = (ViewHolder) view.getTag();
						Intent intent = new Intent(SubMenuManager.this,AddSubMenuActivity.class);
//						Bundle bundle = new Bundle();
//						bundle.putSerializable("data", holder.type);
						intent.putExtra("data", holder.type);
						startActivityForResult(intent,0);
						break;
					case R.id.submenu_manager_add://���
						startActivityForResult(new Intent(SubMenuManager.this,AddSubMenuActivity.class),0);
						break;
					case R.id.submenu_manager_delete://ɾ��
//						ViewHolder holder1 = (ViewHolder) view.getTag();
//						menuList.remove(holder1.position);
//						sma.notifyDataSetChanged();
						remove_view = view;
						dialog();
						break;

					default:
						break;
					}
				}
			});
		}
		sub_grid.setAdapter(sma);
	}

	/**
	 * �������Զ��嵯��
	 */
	private void dialog(){
		//�����ļ�ת��Ϊview����
        LayoutInflater inflaterDl = LayoutInflater.from(SubMenuManager.this);
        RelativeLayout layout = (RelativeLayout)inflaterDl.inflate(R.layout.layout_dialog, null );
       
        //�Ի���
        final Dialog dialog = new AlertDialog.Builder(SubMenuManager.this).create();
        dialog.show();
        dialog.getWindow().setContentView(layout);
       
       
        //ȡ����ť
        Button btnCancel = (Button) layout.findViewById(R.id.dialog_cancel);
        btnCancel.setOnClickListener(new OnClickListener() {
         
          @Override
          public void onClick(View v) {
        	 dialog.dismiss();
             Toast.makeText(SubMenuManager.this, "ȡ��", Toast.LENGTH_SHORT).show();
             remove_view = null;
          }
        });
       
       
        //ȷ����ť
        Button btnOK = (Button) layout.findViewById(R.id.dialog_exit);
        btnOK.setOnClickListener(new OnClickListener() {
         
          @Override
          public void onClick(View v) {
        	  ViewHolder holder1 = (ViewHolder) remove_view.getTag();
				menuList.remove(holder1.position);
				sma.notifyDataSetChanged();
        	  dialog.dismiss();
             Toast.makeText(SubMenuManager.this, "��ɾ��", Toast.LENGTH_SHORT).show();           
             remove_view = null;
          }
        });
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.sub_bcak://���ذ�ť
			finish();
			break;
		case R.id.sub_setting://��ɰ�ť
			finish();
			break;

		default:
			break;
		}
	}
	
	/**
	 * չʾ���ض���
	 */
	private void loadShow(){
		img.setVisibility(View.VISIBLE);
		sub_grid.setVisibility(View.GONE);
		Glide.with(this).load(R.drawable.loading).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(img);
		ObjectAnimator anim = ObjectAnimator.ofFloat(img, "alpha", 0f, 1f);  
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
	 * ���ؼ��ض���
	 */
	private void loadDisMiss(){
//		Glide.with(this).load(R.drawable.loading).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(menu_iv);
		ObjectAnimator anim = ObjectAnimator.ofFloat(img, "alpha", 1f, 0f);  
		anim.setDuration(400);
		anim.start();
		
		img.setVisibility(View.GONE);
		sub_grid.setVisibility(View.VISIBLE);
		ObjectAnimator anim1 = ObjectAnimator.ofFloat(sub_grid, "alpha", 0f, 1f);  
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
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if(resultCode == 9999){
			subMenuName = data.getStringExtra("subMenuName");
			MenuName = subMenuName;
			try {
				file_icon = new File(new URI(data.getStringExtra("icon")));
				file_icon_down = new File(new URI(data.getStringExtra("icon_down")));
				uploadLoading();
				if(imgf == null){
					imgf = new ImageFactory();
				}
				try {
					imgf.ratioAndGenThumb(file_icon.getPath(), file_icon.getPath(),  84f, 84f,false);
					new BlobHelp(file_icon, subMenuName, handler, MenuName).execute();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	
	/**
	 * �������Զ����ϴ�load
	 */
	private void uploadLoading(){
		//�����ļ�ת��Ϊview����
        LayoutInflater inflaterDl = LayoutInflater.from(SubMenuManager.this);
        RelativeLayout layout = (RelativeLayout)inflaterDl.inflate(R.layout.upload_dialog, null );
       if(dialog == null){
    	   dialog = new AlertDialog.Builder(SubMenuManager.this).create();
    	   dialog.setCancelable(false);
       }
        dialog.show();
        dialog.getWindow().setContentView(layout);
       
	}
}
