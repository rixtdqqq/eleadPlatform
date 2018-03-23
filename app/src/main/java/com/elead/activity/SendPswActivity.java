package com.elead.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.elead.application.MyApplication;
import com.elead.eplatform.R;
import com.elead.service.EPlamformServices;
import com.elead.utils.AppManager;
import com.elead.utils.EloadingDialog;
import com.elead.utils.EncryptUtil;
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

public class SendPswActivity extends Activity implements View.OnClickListener {

    ImageView iv_back;
    ImageView del_1, del_2, del_3;
    EditText ed_identify, ed_psw, ed_sure_psw;
    Button btn_finish;
    String str_identify, str_psw, str_sure_psw;
    String mail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.set_psw_layout);
        AppManager.getAppManager().addActivity(this);

        mail = getIntent().getStringExtra("email");
        initView();
    }

    /**
     * 初始化
     */
    private void initView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        del_1 = (ImageView) findViewById(R.id.del_1);
        del_2 = (ImageView) findViewById(R.id.del_2);
        del_3 = (ImageView) findViewById(R.id.del_3);
        ed_identify = (EditText) findViewById(R.id.ed_identify);
        ed_psw = (EditText) findViewById(R.id.ed_psw);
        ed_sure_psw = (EditText) findViewById(R.id.ed_sure_psw);
        btn_finish = (Button) findViewById(R.id.btn_finish);

        iv_back.setOnClickListener(this);
        del_1.setOnClickListener(this);
        del_2.setOnClickListener(this);
        del_3.setOnClickListener(this);
        btn_finish.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_back) {
            finish();
        } else if (v.getId() == R.id.del_1) {
            ed_identify.setText("");
        } else if (v.getId() == R.id.del_2) {
            ed_psw.setText("");
        } else if (v.getId() == R.id.del_3) {
            ed_sure_psw.setText("");
        } else if (v.getId() == R.id.btn_finish) {
            //调接口
            str_identify = ed_identify.getText().toString();
            str_psw = ed_psw.getText().toString();
            str_sure_psw = ed_sure_psw.getText().toString();

            sendMailAccount(mail, str_identify, str_psw, str_sure_psw);

        }
    }

    /**
     * @param email     邮箱
     * @param ver_code  验证码
     * @param plain_pwd 密码
     * @param pwd       确认密码
     */
    private void sendMailAccount(String email, String ver_code, String plain_pwd, String pwd) {

        if(!NetWorkUtils.isNetworkConnected(this)){
            ToastUtil.showToast(this,getResources().getString(R.string.network_not_connected),
                    Toast.LENGTH_LONG).show();
            return;
        }

        if (ver_code == null  || ver_code.length()<1) {
            ToastUtil.showToast(SendPswActivity.this, "验证码不能为空！", Toast.LENGTH_LONG).show();
            return;
        }
        if(plain_pwd.length()<6){
            ToastUtil.showToast(this, "密码不能少于6位!", Toast.LENGTH_LONG).show();
            return;
        }
        if(plain_pwd.length()>16){
            ToastUtil.showToast(this, "密码不能多于16位!",  Toast.LENGTH_LONG).show();
            return;
        }
        if (!plain_pwd.equals(pwd)) {
            ToastUtil.showToast(SendPswActivity.this, "密码和确认密码不一致!", Toast.LENGTH_LONG).show();
            return;
        }
        EloadingDialog.ShowDialog(this);

        HashMap<String, String> parameters = new HashMap<String, String>();
        parameters.put("email", email);
        parameters.put("ver_code", ver_code);
//        parameters.put("plain_pwd", plain_pwd);
        parameters.put("pwd", EncryptUtil.encryptPsw(plain_pwd));
        parameters.put("platform", "android");

        String url = EPlamformServices.reset_password_service;
        VHttpUrlConnectionUtil.postRequest(url, parameters,
                new HttpUrlConnectionUtil.onConnectionFinishLinstener() {
                    @Override
                    public void onSuccess(String url, String result) {

                        EloadingDialog.cancle();
                        JSONObject dataJson = null;
                        int ret_code = 0;
                        String ret_message = "";
                        try {
                            dataJson = new JSONObject(result);
                            ret_code = dataJson.getInt("ret_code");
                            if (dataJson.has("ret_message")) {
                                ret_message = dataJson.getString("ret_message");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (ret_code > 0) {
                            ToastUtil.showToast(SendPswActivity.this, ret_message, Toast.LENGTH_LONG).show();
                            MyApplication.isSetPsw = true;
                            finish();
                        } else {
                            ToastUtil.showToast(SendPswActivity.this, ret_message, Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFail(String result) {
                        EloadingDialog.cancle();
                        ToastUtil.showToast(SendPswActivity.this, getResources().getString(R.string.request_fail), Toast.LENGTH_LONG).show();
                    }
                });

    }

}
