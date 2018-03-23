package com.elead.operate.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.elead.eplatform.R;

import java.util.LinkedHashMap;
import java.util.Map;

public class Detail extends LinearLayout{
	
private Map<String,String>map=new LinkedHashMap<String,String>();
	
	private View view;
	private TextView left;
	private TextView right;
	
	public Detail(Context context) {
		
		this(context,null);
		
	}
	
	public Detail(Context context, AttributeSet attrs) {
		this(context, attrs,0);
		
	}
	
	
	
	@SuppressLint("NewApi")
	public Detail(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		//setDatas();
		initViews(context);
	}

	
	public void setDatas(String name,String content) {
		
		left.setText(name);
		right.setText(content);
	}
	private void initViews(Context context) {
		LayoutInflater.from(context).inflate(R.layout.detail_main_item,this);

		left=(TextView)findViewById(R.id.detail_title_iv);
		right=(TextView)findViewById(R.id.detail_content_iv);
	}
	
	

}
