package com.gly.calendar.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.gly.calendar.R;

public class SimpleChooseLin extends LinearLayout {

	private RadioGroupEx mradio;
	private onChooseLinstener checkLinstener;
	private Button btn_cust_scope;
	private ImageButton btn_close;

	public SimpleChooseLin(Context context, onChooseLinstener checkLinstener) {
		super(context);
		this.checkLinstener = checkLinstener;
		init();
	}

	private void init() {
		LayoutInflater.from(getContext()).inflate(
				R.layout.layout_simple_date_choose, this);
		mradio = (RadioGroupEx) findViewById(R.id.mradio);
		btn_cust_scope = (Button) findViewById(R.id.btn_cust_scope);
		btn_close = (ImageButton) findViewById(R.id.btn_close);
		btn_close.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				checkLinstener.onCloseClick();
			}
		});
		btn_cust_scope.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				checkLinstener.onCusScopeClick();
			}
		});
		mradio.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				if (arg1 == R.id.rad_1) {
					checkLinstener.onCheckChange(0);
				} else if (arg1 == R.id.rad_2) {
					checkLinstener.onCheckChange(1);
				} else if (arg1 == R.id.rad_3) {
					checkLinstener.onCheckChange(2);
				} else if (arg1 == R.id.rad_4) {
					checkLinstener.onCheckChange(3);
				} else if (arg1 == R.id.rad_5) {
					checkLinstener.onCheckChange(4);
				} else if (arg1 == R.id.rad_6) {
					checkLinstener.onCheckChange(5);
				}
			}
		});
	}

	public interface onChooseLinstener {
		void onCheckChange(int position);
		void onCloseClick();
		void onCusScopeClick();
	}

}
