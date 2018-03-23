package com.elead.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.elead.application.MyApplication;
import com.elead.eplatform.R;
import com.elead.service.EPlamformServices;
import com.elead.utils.EncryptUtil;
import com.elead.utils.HttpUrlConnectionUtil;
import com.elead.utils.ToastUtil;
import com.elead.utils.volley.VHttpUrlConnectionUtil;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * @desc 修改密码界面
 * @auth Created by mujun.xu on 2016/12/8 0008.
 */

public class ModifyPasswordActivity extends BaseActivity {

    private String TAG = "ModifyPasswordActivity";
    private EditText old_password;
    private EditText new_password;
    private EditText confirm_password;
    private String userName;
    private String passWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modify_password_activity);
        userName = getIntent().getStringExtra("user_code");
        passWord = getIntent().getStringExtra("plain_pwd");
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setTitle(getResources().getString(R.string.modify_password), TitleType.NORMAL);
    }

    private void initView(){
        old_password = (EditText) findViewById(R.id.old_password);
        new_password = (EditText) findViewById(R.id.new_password);
        confirm_password = (EditText) findViewById(R.id.confirm_password);
    }

    private boolean isSubmit(String newPassword, String confirmPassword){
        if (!TextUtils.isEmpty(newPassword)
                && !TextUtils.isEmpty(confirmPassword)
                && TextUtils.equals(newPassword, confirmPassword)) {
            return true;
        } else {
            ToastUtil.showToast(mContext, "密码不一致!", 0).show();
            return false;
        }
    }

    /**
     * confirm
     *
     * @param view
     */
    public void confirm(View view) {
        String pwd = confirm_password.getText().toString();
        if (isSubmit(new_password.getText().toString(), pwd)){
            if (pwd.length() < 6) {
                ToastUtil.showToast(this, "密码不能少于6位!", 1).show();
                return;
            } else if (pwd.length() > 16) {
                ToastUtil.showToast(this, "密码不能大于16位!", 1).show();
                return;
            }
            //user_code=0993&plain_flag=on&plain_old_pwd=88888&plain_new_pwd=0z4021
            HashMap<String, String> parameters = new HashMap<String, String>();
            parameters.put("user_code", MyApplication.user.work_no);
            parameters.put("old_pwd", EncryptUtil.encryptPsw(old_password.getText().toString()));
            parameters.put("new_pwd", EncryptUtil.encryptPsw(new_password.getText().toString()));
            parameters.put("platform","android");
            //http://192.168.1.225:8080/eplat-uat/
            String url = EPlamformServices.change_password_service;
            VHttpUrlConnectionUtil.postRequest(url, parameters,
                    new HttpUrlConnectionUtil.onConnectionFinishLinstener() {

                        @Override
                        public void onSuccess(String url, String result) {
                            if (!TextUtils.isEmpty(result)) {
                                Log.d(TAG, result);
                                int ret_code = 0;
                                try {
                                    JSONObject dataJson = new JSONObject(result);
                                    ret_code = dataJson.getInt("ret_code");
                                    String ret_message = dataJson.getString("ret_message");
                                    Log.d(TAG, "ret_code: " + ret_code + "ret_message:"+ret_message);
                                    ToastUtil.showToast(mContext, ret_message, 0).show();
                                    //1修改成功,0参数不能为空,-1新旧密码相同,-2解密出现异常,-3用户代码错误,-4当前用户无对应环信用户
                                    //-5旧密码错误,-6修改用户密码异常
                                    if (ret_code > 0) {//修改成功
                                        ModifyPasswordActivity.this.finish();
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void onFail(String result) {
                            Log.d(TAG, result);
                            ToastUtil.showToast(mContext, "修改失败!", 0).show();
                        }
                    });
        }
    }
}
