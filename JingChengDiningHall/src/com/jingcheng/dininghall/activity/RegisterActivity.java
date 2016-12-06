package com.jingcheng.dininghall.activity;

import com.jingcheng.jingchengdininghall.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends Activity implements OnClickListener {
	private Button bt_back;
	private Button next;
	private TextView tv_back;
	private Button getVerify;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		bt_back = (Button) findViewById(R.id.bt_back);
		next = (Button) findViewById(R.id.bt_next);
		tv_back = (TextView) findViewById(R.id.tv_back);
		getVerify = (Button) findViewById(R.id.bt_getVerify);
		
		getVerify.setOnClickListener(this);
		bt_back.setOnClickListener(this);
		next.setOnClickListener(this);
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
		case R.id.bt_getVerify:
			Toast.makeText(this, "´ý¡£¡£", 0).show();
			break;
		case R.id.bt_next:
			startActivity(new Intent(RegisterActivity.this,RegisterSucceedActivity.class));
			finish();
			break;

		default:
			break;
		}
	}
}
