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

public class LoginActivity extends Activity implements OnClickListener {

	private TextView forget;
	private Button login;
	private TextView register;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		forget = (TextView) findViewById(R.id.forget);
		login = (Button) findViewById(R.id.bt_login);
		register = (TextView) findViewById(R.id.register);
		
		forget.setOnClickListener(this);
		login.setOnClickListener(this);
		register.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_login://µã»÷µÇÂ¼
			startActivity(new Intent(this,MainActivity.class));
			break;
		case R.id.forget://Íü¼ÇÃÜÂë
			Toast.makeText(this, "´ý¡£¡£", 0).show();
			break;
		case R.id.register://×¢²á
			startActivity(new Intent(this,RegisterActivity.class));
			break;

		default:
			break;
		}
	}

	
}
