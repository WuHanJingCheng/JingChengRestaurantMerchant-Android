package com.jingcheng.dininghall.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.jingcheng.jingchengdininghall.R;

public class UserInfoActivity extends BaseActivity implements OnClickListener {
	private ImageView finish;
	private TextView sign_out;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_userinfo);
		finish = (ImageView) findViewById(R.id.userinfo_finish);
		sign_out = (TextView) findViewById(R.id.userinfo_sign_out);
		finish.setOnClickListener(this);
		sign_out.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.userinfo_finish:
			finish();
			break;
		case R.id.userinfo_sign_out:
			setResult(10000);
            finish();
			break;

		default:
			break;
		}
	}
}
