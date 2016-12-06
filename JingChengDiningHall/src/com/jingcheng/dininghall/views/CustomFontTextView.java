//package com.jingcheng.dininghall.views;
//
//import android.content.Context;
//import android.content.res.AssetManager;
//import android.graphics.Typeface;
//import android.util.AttributeSet;
//import android.widget.TextView;
//
//public class CustomFontTextView extends TextView {
//
//	public CustomFontTextView(Context context) {
//		super(context);
//		init(context);
//	}
//
//	public CustomFontTextView(Context context, AttributeSet attrs,
//			int defStyleAttr, int defStyleRes) {
//		super(context, attrs, defStyleAttr, defStyleRes);
//		init(context);
//	}
//
//	public CustomFontTextView(Context context, AttributeSet attrs,
//			int defStyleAttr) {
//		super(context, attrs, defStyleAttr);
//		init(context);
//	}
//
//	public CustomFontTextView(Context context, AttributeSet attrs) {
//		super(context, attrs);
//		init(context);
//	}
//	private void init(Context context){
//		AssetManager assetManager = context.getAssets();
//		Typeface font = Typeface.createFromAsset(assetManager, "fonts/PingFang.ttf");
//		setTypeface(font);
//	}
//}
