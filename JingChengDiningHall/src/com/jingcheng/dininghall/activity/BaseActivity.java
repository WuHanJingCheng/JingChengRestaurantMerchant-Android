package com.jingcheng.dininghall.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class BaseActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		//ȥ��title  
//		requestWindowFeature(Window.FEATURE_NO_TITLE);  
		//ȥ��Activity�����״̬��  
//		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);  
//		//ȥ�����ⰴ��ȫ����ʾ  
//		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION); 
		
//		this.getWindow().getDecorView().setSystemUiVisibility(  
//				View.SYSTEM_UI_FLAG_LAYOUT_STABLE  
//				| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION  
//				| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN  
//				| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav  
//				// bar  
//				| View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar  
//				| View.SYSTEM_UI_FLAG_IMMERSIVE);  
//		
	
/*
 * WindowManager wm = (WindowManager)this.getSystemService(Context.WINDOW_SERVICE);

		 int width = wm.getDefaultDisplay().getWidth();
		 int height = wm.getDefaultDisplay().getHeight();
		 
		//��Ļ  
	        DisplayMetrics dm = new DisplayMetrics();  
	        getWindowManager().getDefaultDisplay().getMetrics(dm);  
	        Log.e("WangJ", "��Ļ��:" + dm.heightPixels);  
	  
	        //Ӧ������  
	        Rect outRect1 = new Rect();  
	        getWindow().getDecorView().getWindowVisibleDisplayFrame(outRect1);  
	        Log.e("WangJ", "Ӧ��������" + outRect1.top);  
	        Log.e("WangJ", "Ӧ������" + outRect1.height());  
	          
	        //View��������  
	        Rect outRect2 = new Rect();  
	        getWindow().findViewById(Window.ID_ANDROID_CONTENT).getDrawingRect(outRect2);   
	        Log.e("WangJ", "View�������򶥲�-���󷽷���" + outRect2.top);   //�������ϱ�һ����outRect2.top��ȡ�����ַ�ʽ��õ�top��0��������bug��  
	        int viewTop = getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();   //Ҫ�����ַ���  
	        Log.e("WangJ", "View�������򶥲�-��ȷ������" + viewTop);  
	        Log.e("WangJ", "View��������߶ȣ�" + outRect2.height());  	
 */
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
	}
	
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
	}
}
