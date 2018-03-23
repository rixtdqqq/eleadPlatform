package com.elead.activity;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.elead.eplatform.R;
import com.elead.im.db.DemoHelper;
import com.elead.utils.Constants;
import com.elead.utils.ToastUtil;
import com.elead.views.AlertDialog;
import com.elead.views.ImageTextView;
import com.hyphenate.chat.EMClient;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.elead.utils.StatusBarUtils.setWindowStatusBarColor;

/**
 * Created by Administrator on 2017/1/6 0006.
 */

public class ValidationFriendActivity extends BaseActivity implements TextWatcher, View.OnClickListener {

    @BindView(R.id.edit_validation_id)
    EditText edit_iv;
    private ProgressDialog progressDialog;
    private String scanResult;
    private AlertDialog builder;
    private ImageButton back_bt;
    private TextView title_iv;
    private ImageTextView right_tv;
    private ImageView delete_bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.validation_identity_identify_qrcode);
        ButterKnife.bind(this);
        setWindowStatusBarColor(this, Color.parseColor("#2ec7c9"));
        scanResult = getIntent().getStringExtra(Constants.VALIDATION_FRIEND_KEY);
//        setTitle(getResources().getString(R.string.good_friend_validation), TitleType.NORMAL, new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(edit_iv.getText().toString()!=null) {
//                    send();
//                }else{
//                    ToastUtil.showToast(ValidationFriendActivity.this,"数据不能输入空", 2000).show();
//                }
//            }
//
//
//        }, getResources().getString(R.string.button_send));

        InitViews();


        edit_iv.addTextChangedListener(this);

    }

    private void InitViews() {
        back_bt = (ImageButton) findViewById(R.id.back_bt_id);
        // title_iv=(TextView)findViewById(R.id.title);
        right_tv = (ImageTextView) findViewById(R.id.im_img_right_id);
        delete_bt = (ImageView) findViewById(R.id.delete_bt_id);
        right_tv.setText(getResources().getString(R.string.button_send));

        back_bt.setOnClickListener(this);
        right_tv.setOnClickListener(this);
        delete_bt.setOnClickListener(this);
    }


    private void send() {
        //不能添加自己
//        if (EMClient.getInstance().getCurrentUser().equals(scanResult)) {
//            new EaseAlertDialog(ValidationFriendActivity.this, R.string.not_add_myself).show();
//            return;
//        }
        //判断是是好友了

        if (DemoHelper.getInstance().getContactList().containsKey(scanResult)) {
            //let the user know the contact already in your contact list
            if (EMClient.getInstance().contactManager().getBlackListUsernames().contains(scanResult)) {//黑名单
                //new EaseAlertDialog(ValidationFriendActivity.this, R.string.user_already_in_contactlist).show();
                showDailog(getResources().getString(R.string.user_already_in_contactlist));
                return;
            }
            // new EaseAlertDialog(ValidationFriendActivity.this, R.string.This_user_is_already_your_friend).show();
            showDailog(getResources().getString(R.string.This_user_is_already_your_friend));
            return;
        }

        progressDialog = new ProgressDialog(ValidationFriendActivity.this);
        String stri = getResources().getString(R.string.Is_sending_a_request);
        progressDialog.setMessage(stri);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        new Thread(new Runnable() {
            public void run() {

                try {
                    //demo use a hardcode reason here, you need let user to input if you like
                    // String s = getResources().getString(R.string.Add_a_friend);
                    if (edit_iv.getText().toString() != null) {
                        EMClient.getInstance().contactManager().addContact(scanResult, edit_iv.getText().toString());
                        runOnUiThread(new Runnable() {
                            public void run() {
                                progressDialog.dismiss();
                                String s1 = getResources().getString(R.string.send_successful);
                                Toast.makeText(getApplicationContext(), s1, Toast.LENGTH_LONG).show();
                                ValidationFriendActivity.this.finish();
                            }
                        });
                    } else {
                        ToastUtil.showToast(ValidationFriendActivity.this, "数据不能输入空", 2000).show();
                    }
                } catch (final Exception e) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            progressDialog.dismiss();
                            String s2 = getResources().getString(R.string.Request_add_buddy_failure);
                            Toast.makeText(getApplicationContext(), s2 + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        }).start();
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        Log.i("TAG", "===" + edit_iv.getText().toString());
        if (!TextUtils.isEmpty(edit_iv.getText().toString()) && !edit_iv.getText().toString().equals("")) {
            delete_bt.setVisibility(View.VISIBLE);
        } else {
            delete_bt.setVisibility(View.INVISIBLE);
        }
    }


    private void showDailog(String msg) {
        if (builder == null) {
            builder = new AlertDialog(ValidationFriendActivity.this).builder();
        }
        builder.setTitle(getResources().getString(R.string.warm_reminder));
        builder.setMsg(msg);
        builder.setPositiveButton(getResources().getString(R.string.ok), 0, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        builder.setCancelable(false);
        builder.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_bt_id:
                finish();
                break;

            case R.id.im_img_right_id:
                Log.i("ii", "yyy==" + edit_iv.getText().toString());
                if (!TextUtils.isEmpty(edit_iv.getText().toString()) || !edit_iv.getText().toString().equals("")) {
                    send();
                } else {
                    ToastUtil.showToast(ValidationFriendActivity.this, getResources().getString(R.string.must_input_yanzhen), 5000).show();
                }
                break;

            case R.id.delete_bt_id:
                edit_iv.setText("");
                break;
        }
    }
}
