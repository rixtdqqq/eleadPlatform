package com.elead.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.elead.eplatform.R;
import com.elead.utils.StatusBarUtils;
import com.elead.views.ImageTextView;

import static com.elead.eplatform.R.id.back_bt_id;

/**
 * Created by Administrator on 2017/1/11 0011.
 */

public class AlertGroupNameActivity extends BaseActivity implements TextWatcher, View.OnClickListener {

    private ImageButton back_bt;
    private ImageTextView right_tv;
    private ImageView delete_bt;
    private EditText edit_iv;
    private String data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.alert_group_name);
        StatusBarUtils.setWindowStatusBarColor(this, getResources().getColor(R.color.qianlv));
        data = getIntent().getStringExtra("data");
        InitViews();


    }

    private void InitViews() {
        back_bt = (ImageButton) findViewById(back_bt_id);
        right_tv = (ImageTextView) findViewById(R.id.im_img_right_id);
        delete_bt = (ImageView) findViewById(R.id.delete_bt_id);
        edit_iv = (EditText) findViewById(R.id.edit_validation_id);
        right_tv.setText(getResources().getString(R.string.save));
        right_tv.setTextColor(Color.parseColor("#ace4e5"));
        right_tv.setTextSize((int)getResources().getDimension(R.dimen.px48));

        if (data != null)
            edit_iv.setText(data);
        edit_iv.setSelection(edit_iv.length());

        back_bt.setOnClickListener(this);
        delete_bt.setOnClickListener(this);
        edit_iv.addTextChangedListener(this);

        if (!TextUtils.isEmpty(edit_iv.getText().toString()) && !edit_iv.getText().toString().equals("")) {
            right_tv.setOnClickListener(this);
            delete_bt.setVisibility(View.VISIBLE);
            right_tv.setTextColor(getResources().getColor(R.color.white));
        }else {
            delete_bt.setVisibility(View.GONE);
            right_tv.setTextColor(Color.parseColor("#ace4e5"));
        }
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (!TextUtils.isEmpty(edit_iv.getText().toString()) && !edit_iv.getText().toString().equals("")) {
            delete_bt.setVisibility(View.VISIBLE);
            right_tv.setTextColor(getResources().getColor(R.color.white));
            right_tv.setEnabled(true);
        } else {
            delete_bt.setVisibility(View.INVISIBLE);
            right_tv.setTextColor(Color.parseColor("#ace4e5"));
            right_tv.setEnabled(false);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_bt_id:
                finish();
                break;

            case R.id.im_img_right_id:
                setResult(RESULT_OK, new Intent().putExtra("data", edit_iv.getText().toString()));
                finish();
                break;

            case R.id.delete_bt_id:
                edit_iv.setText("");
                break;


        }
    }
}
