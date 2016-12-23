package com.jingcheng.dininghall.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.jingcheng.jingchengdininghall.R;

public class AddSubMenuActivity extends BaseActivity implements OnClickListener {
	private TextView add_submenu_ok, add_submenu_cancel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_submenu);
		setFinishOnTouchOutside(false);//  …Ë÷√¥•≈ˆdialogÕ‚ «∑Òfinish
		add_submenu_ok = (TextView) findViewById(R.id.add_submenu_ok);
		add_submenu_cancel = (TextView) findViewById(R.id.add_submenu_cancel);
		add_submenu_ok.setOnClickListener(this);
		add_submenu_cancel.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.add_submenu_ok:
			setResult(9999);
			finish();
			break;
		case R.id.add_submenu_cancel:
			finish();
			break;

		default:
			break;
		}
	}
}
