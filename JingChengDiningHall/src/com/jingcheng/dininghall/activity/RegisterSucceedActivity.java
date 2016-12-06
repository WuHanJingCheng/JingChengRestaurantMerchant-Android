package com.jingcheng.dininghall.activity;

import com.jingcheng.jingchengdininghall.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class RegisterSucceedActivity extends BaseActivity implements OnClickListener {
	private Button bt_back;
	private Button succeed;
	private TextView tv_back;
	private Button getVerify;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_succeed);
		
		bt_back = (Button) findViewById(R.id.bt_back);
		succeed = (Button) findViewById(R.id.bt_succeed);
		tv_back = (TextView) findViewById(R.id.tv_back);
		
		
		bt_back.setOnClickListener(this);
		tv_back.setOnClickListener(this);
		succeed.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_back:
			finish();
			break;
		case R.id.tv_back:
			finish();
			break;
		case R.id.bt_succeed:
			startActivity(new Intent(this,MainActivity.class));
			finish();
			break;

		default:
			break;
		}
	}
}
