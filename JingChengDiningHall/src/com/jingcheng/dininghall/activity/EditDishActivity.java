package com.jingcheng.dininghall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.jingcheng.jingchengdininghall.R;

public class EditDishActivity extends BaseActivity implements OnClickListener {
	private TextView editdish_ok;
	private TextView editdish_cancel;
	private String title;
	private TextView editdish_title;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editdish);
		setFinishOnTouchOutside(false);//  设置触碰dialog外是否finish
		editdish_ok = (TextView) findViewById(R.id.editdish_ok);
		editdish_cancel = (TextView) findViewById(R.id.editdish_cancel);
		editdish_title = (TextView) findViewById(R.id.editdish_title);
		
		editdish_ok.setOnClickListener(this);
		editdish_cancel.setOnClickListener(this);
		
		init();
		
	}
	/**
	 * 
	 * 描述：初始化标题
	 */
	private void init() {
		Intent intent = getIntent();
		title = intent.getExtras().getString("title");
		editdish_title.setText(title);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.editdish_ok:
			finish();
			break;
		case R.id.editdish_cancel:
			finish();
			break;

		default:
			break;
		}
	}
}
