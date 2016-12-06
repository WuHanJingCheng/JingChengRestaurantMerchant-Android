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
		
		
		//去除title  
//		requestWindowFeature(Window.FEATURE_NO_TITLE);  
		//去掉Activity上面的状态栏  
//		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);  
//		//去掉虚拟按键全屏显示  
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
		 
		//屏幕  
	        DisplayMetrics dm = new DisplayMetrics();  
	        getWindowManager().getDefaultDisplay().getMetrics(dm);  
	        Log.e("WangJ", "屏幕高:" + dm.heightPixels);  
	  
	        //应用区域  
	        Rect outRect1 = new Rect();  
	        getWindow().getDecorView().getWindowVisibleDisplayFrame(outRect1);  
	        Log.e("WangJ", "应用区顶部" + outRect1.top);  
	        Log.e("WangJ", "应用区高" + outRect1.height());  
	          
	        //View绘制区域  
	        Rect outRect2 = new Rect();  
	        getWindow().findViewById(Window.ID_ANDROID_CONTENT).getDrawingRect(outRect2);   
	        Log.e("WangJ", "View绘制区域顶部-错误方法：" + outRect2.top);   //不能像上边一样由outRect2.top获取，这种方式获得的top是0，可能是bug吧  
	        int viewTop = getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();   //要用这种方法  
	        Log.e("WangJ", "View绘制区域顶部-正确方法：" + viewTop);  
	        Log.e("WangJ", "View绘制区域高度：" + outRect2.height());  	
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
