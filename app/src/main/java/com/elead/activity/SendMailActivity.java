package com.elead.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.elead.eplatform.R;
import com.elead.service.EPlamformServices;
import com.elead.utils.AppManager;
import com.elead.utils.EloadingDialog;
import com.elead.utils.HttpUrlConnectionUtil;
import com.elead.utils.NetWorkUtils;
import com.elead.utils.ToastUtil;
import com.elead.utils.volley.VHttpUrlConnectionUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by xieshibin on 2016/12/13.
 */

public class SendMailActivity extends Activity implements View.OnClickListener{

    ImageView iv_back;
    ImageView del_iv;
    Button btn_send;
    EditText edit_mail, edit_mail_1;
    String mail_account = "";
    TextView title_tv;
    int userType;
    String english = "abcdefghijklmnopqrstuvwxyz";

    private String mail_address;
    //后台返回是否是新用户的标志
    public int isNew = 1;
    public int isOld = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.forget_psw_layout);
        AppManager.getAppManager().addActivity(this);

        userType = getIntent().getIntExtra("type",0);

        initView();
        setListener();
    }

    private void initView(){
        mail_address = this.getResources().getString(R.string.e_lead_mail_address);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        del_iv = (ImageView) findViewById(R.id.del_iv);
        btn_send = (Button) findViewById(R.id.btn_send);
        edit_mail = (EditText) findViewById(R.id.edit_mail);
        edit_mail.setTextColor(getResources().getColor(R.color.transport));

        edit_mail_1 = (EditText) findViewById(R.id.edit_mail_1);
        title_tv = (TextView) findViewById(R.id.title_tv);
        if(userType == 1){
            title_tv.setText(getResources().getString(R.string.register));
        }else{
            title_tv.setText(getResources().getString(R.string.get_back_password));
        }

        iv_back.setOnClickListener(this);
        del_iv.setOnClickListener(this);
        btn_send.setOnClickListener(this);
    }

    /**
     * 设置监听
     */
    private void setListener() {

        // if user changed, clear the passwordEd
        edit_mail.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                OnStrChange(s, count);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }


    /**
     *编辑框内容改变
     * @param s
     * @param count
     */
    private void OnStrChange(CharSequence s, int count) {

        isShowMailAddress();
        if (s.length() > 0) {
            isShowClear(del_iv, true);
        } else {
            if (TextUtils.isEmpty(s)) {
                isShowClear(del_iv, false);
            } else {
                isShowClear(del_iv, true);
            }
        }
    }

    /**
     * 是否显示清除控件
     *
     * @param view
     * @param isShow
     */
    private void isShowClear(View view, boolean isShow) {
        if (isShow) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.INVISIBLE);
        }
    }



    /**
     * 是否包含英文字母
     */
    private void isShowMailAddress() {
        String str = edit_mail.getText().toString();
        if(str.length()>0){
            if(english.contains(str.substring(0,1))){
                edit_mail_1.setText(str+mail_address);
            }else{
                edit_mail_1.setText(str);
            }
        }else{
            edit_mail_1.setText(str);
        }

    }


    @Override
    public void onClick(View v) {

        if(R.id.iv_back == v.getId() ){
            finish();
        }else if(R.id.del_iv == v.getId()){
            edit_mail.setText("");
            edit_mail_1.setText("");
        }else{
            //调接口
            mail_account = edit_mail_1.getText().toString();
            isNewMail(mail_account);
        }

    }

    /**
     * 调接口--发送邮箱
     * @param account
     */
    private void isNewMail(String account) {

        if(!NetWorkUtils.isNetworkConnected(this)){
            ToastUtil.showToast(this,getResources().getString(R.string.network_not_connected),
                    Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(account)){
            ToastUtil.showToast(this,"邮箱不能为空！",Toast.LENGTH_LONG).show();
            return;
        }
        EloadingDialog.ShowDialog(SendMailActivity.this);
        HashMap<String, String> parameters = new HashMap<String, String>();
        parameters.put("email", account);
        String url = EPlamformServices.is_new_user_service;
        VHttpUrlConnectionUtil.postRequest(url, parameters,
                new HttpUrlConnectionUtil.onConnectionFinishLinstener() {
                    @Override
                    public void onSuccess(String url, String result) {
                        EloadingDialog.cancle();
                        JSONObject dataJson = null;
                        int ret_code = 0;
                        String ret_message="";
                        try {
                            dataJson = new JSONObject(result);
                            ret_code = dataJson.getInt("ret_code");
                            if(dataJson.has("ret_message")){
                                ret_message = dataJson.getString("ret_message");
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        //后台返回是否是新用户的标志
                        if(ret_code == userType){
                            sendMailAccount(mail_account);
                        }else if(ret_code == isNew){//忘记页面输入的新用户
                            ToastUtil.showToast(SendMailActivity.this,"该邮箱未注册！",Toast.LENGTH_LONG).show();
                        }else if(ret_code == isOld){//新页面输入的老用户
                            ToastUtil.showToast(SendMailActivity.this,"该邮箱已注册！",Toast.LENGTH_LONG).show();
                        }else{
                            ToastUtil.showToast(SendMailActivity.this,ret_message ,Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onFail(String result) {
                        EloadingDialog.cancle();
                        ToastUtil.showToast(SendMailActivity.this, getResources().getString(R.string.request_fail), Toast.LENGTH_LONG).show();
                    }
                });

    }

    /**
     * 调接口--发送邮箱
     * @param account
     */
    private void sendMailAccount(String account) {
        if(!NetWorkUtils.isNetworkConnected(this)){
            ToastUtil.showToast(this,getResources().getString(R.string.network_not_connected),
                    Toast.LENGTH_LONG).show();
            return;
        }
        HashMap<String, String> parameters = new HashMap<String, String>();
        parameters.put("email", account);
        String url = EPlamformServices.get_verification_code_service;
        VHttpUrlConnectionUtil.postRequest(url, parameters,
                new HttpUrlConnectionUtil.onConnectionFinishLinstener() {
                    @Override
                    public void onSuccess(String url, String result) {
                        EloadingDialog.cancle();
                        JSONObject dataJson = null;
                        int ret_code = 0;
                        String ret_message="";
                        try {
                            dataJson = new JSONObject(result);
                            ret_code = dataJson.getInt("ret_code");
                            if(dataJson.has("ret_message")){
                                ret_message = dataJson.getString("ret_message");
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if(ret_code>0){
                            Intent i = new Intent(SendMailActivity.this,SendPswActivity.class);
                            i.putExtra("email",mail_account);
                            startActivity(i);
                            ToastUtil.showToast(SendMailActivity.this,ret_message,Toast.LENGTH_LONG).show();
                            finish();

                        }else{
                            ToastUtil.showToast(SendMailActivity.this,ret_message,Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onFail(String result) {
                        EloadingDialog.cancle();
                        ToastUtil.showToast(SendMailActivity.this, getResources().getString(R.string.request_fail), Toast.LENGTH_LONG).show();
                    }
                });

    }

}
