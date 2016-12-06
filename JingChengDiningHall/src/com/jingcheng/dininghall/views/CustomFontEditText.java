//package com.jingcheng.dininghall.views;
//
//import android.content.Context;
//import android.content.res.AssetManager;
//import android.graphics.Typeface;
//import android.util.AttributeSet;
//import android.widget.EditText;
//
//public class CustomFontEditText extends EditText {
//
//	public CustomFontEditText(Context context, AttributeSet attrs,
//			int defStyleAttr, int defStyleRes) {
//		super(context, attrs, defStyleAttr, defStyleRes);
//		init(context);
//	}
//
//	public CustomFontEditText(Context context, AttributeSet attrs,
//			int defStyleAttr) {
//		super(context, attrs, defStyleAttr);
//		init(context);
//	}
//
//	public CustomFontEditText(Context context, AttributeSet attrs) {
//		super(context, attrs);
//		init(context);
//	}
//
//	public CustomFontEditText(Context context) {
//		super(context);
//		init(context);
//	}
//
//	private void init(Context context){
//		AssetManager assetManager = context.getAssets();
//		Typeface font = Typeface.createFromAsset(assetManager, "fonts/PingFang.ttf");
//		setTypeface(font);
//	}
//}
