package com.elead.im.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.elead.activity.BaseActivity;
import com.elead.application.MyApplication;
import com.elead.eplatform.R;
import com.elead.im.db.DemoHelper;
import com.elead.im.util.EaseNname2EpUser;
import com.elead.im.util.EaseUserUtils;
import com.elead.im.widget.EaseAlertDialog;
import com.elead.im.widget.EaseConstant;
import com.elead.service.EPlamformServices;
import com.elead.utils.EloadingDialog;
import com.elead.utils.HttpUrlConnectionUtil;
import com.elead.utils.NetWorkUtils;
import com.elead.utils.ToastUtil;
import com.elead.utils.volley.VHttpUrlConnectionUtil;
import com.elead.views.CircularImageView;
import com.elead.views.QrCodeDialog;
import com.hyphenate.chat.EMClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by xieshibin 2017/01/11
 */

public class UserInfoDetailActivity extends BaseActivity implements View.OnClickListener {

    public static String TAG = "UserInfoDetailActivity";
    //工号
    String work_no = "";
    //环信号
    String hx_no = "";
    RelativeLayout qr_code_layout;
    TextView use_name_tv, department_tv, number_tv, mail_tv,telephone_tv;
    TextView send_msg_tv, add_contact_tv;
    CircularImageView user_iv;
    TextView username_tv;
    private ProgressDialog progressDialog;
    //用户名和部门
    String user_name, dept_name,phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_info_detail);
        setTitle(getResources().getString(R.string.info_detail), TitleType.NORMAL, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        }, R.drawable.more_pic);

        hx_no = getIntent().getStringExtra(TAG);
        work_no = EaseNname2EpUser.EaseNname2EpUser(hx_no);
        initView();
        dealViewStatus();
        getUserInfo();

    }


    /**
     * 处理view的状态
     */
    private void dealViewStatus() {
        if (work_no.equalsIgnoreCase(MyApplication.user.work_no)) {
            //如果是自己
            send_msg_tv.setVisibility(View.GONE);
            add_contact_tv.setVisibility(View.GONE);
        } else {
            //非好友
            send_msg_tv.setVisibility(View.VISIBLE);
            add_contact_tv.setVisibility(View.VISIBLE);
            if (DemoHelper.getInstance().getContactList().containsKey(hx_no)) {
                //如果是好友
                send_msg_tv.setVisibility(View.VISIBLE);
                add_contact_tv.setVisibility(View.GONE);
            }

        }

    }

    /**
     * 初始化view
     */
    private void initView() {
        qr_code_layout = (RelativeLayout) findViewById(R.id.qr_code_layout);
        //用户姓名、部门、工号、邮箱
        use_name_tv = (TextView) findViewById(R.id.use_name_tv);
        department_tv = (TextView) findViewById(R.id.department_tv);
        number_tv = (TextView) findViewById(R.id.number_tv);
        mail_tv = (TextView) findViewById(R.id.mail_tv);
        telephone_tv = (TextView) findViewById(R.id.telephone_tv);
        //发信息、添加联系人
        send_msg_tv = (TextView) findViewById(R.id.send_msg_tv);
        add_contact_tv = (TextView) findViewById(R.id.add_contact_tv);
        username_tv = (TextView) findViewById(R.id.username_tv);

        user_iv = (CircularImageView) findViewById(R.id.user_iv);
        qr_code_layout.setOnClickListener(this);
        send_msg_tv.setOnClickListener(this);
        add_contact_tv.setOnClickListener(this);
        telephone_tv.setOnClickListener(this);

    }

    /**
     * add contact
     * copy from  AddContactActivity.java
     */
    public void addContact() {

        if (EMClient.getInstance().getCurrentUser().equals(hx_no)) {
            new EaseAlertDialog(this, R.string.not_add_myself).show();
            return;
        }

        if (DemoHelper.getInstance().getContactList().containsKey(hx_no)) {
            //let the user know the contact already in your contact list
            if (EMClient.getInstance().contactManager().getBlackListUsernames().contains(hx_no)) {
                new EaseAlertDialog(this, R.string.user_already_in_contactlist).show();
                return;
            }
            new EaseAlertDialog(this, R.string.This_user_is_already_your_friend).show();
            return;
        }

        progressDialog = new ProgressDialog(this);
        String stri = getResources().getString(R.string.Is_sending_a_request);
        progressDialog.setMessage(stri);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        new Thread(new Runnable() {
            public void run() {

                try {
                    //demo use a hardcode reason here, you need let user to input if you like
                    String s = getResources().getString(R.string.Add_a_friend);
                    EMClient.getInstance().contactManager().addContact(hx_no, s);
                    runOnUiThread(new Runnable() {
                        public void run() {
                            progressDialog.dismiss();
                            String s1 = getResources().getString(R.string.send_successful);
                            Toast.makeText(getApplicationContext(), s1, Toast.LENGTH_LONG).show();
                        }
                    });
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
    public void onClick(View v) {
         if (v == qr_code_layout) {//二维码
             QrCodeDialog dialog = new QrCodeDialog(UserInfoDetailActivity.this,hx_no,user_name);
             dialog.show();
        } else if (v == send_msg_tv) {//发信息
            startActivity(new Intent(mContext, ChatActivity.class).putExtra(EaseConstant.EXTRA_USER_ID, hx_no));
        } else if (v == add_contact_tv) {//添加联系人
            addContact();
        }else if (v == telephone_tv) {//电话号码
             if (TextUtils.isEmpty(phone)||work_no.equalsIgnoreCase(MyApplication.user.work_no)) {
                 //如果是自己或者为空
                 return;
             }else{
                 Intent intent = new Intent();
                 intent.setAction(Intent.ACTION_CALL);
                 //url:统一资源定位符
                 intent.setData(Uri.parse("tel:" + phone));
                 //开启系统拨号器
                 startActivity(intent);
             }
         }
    }

    /**
     * 获取用户信息
     */
    private void getUserInfo() {

        if (!NetWorkUtils.isNetworkConnected(this)) {
            ToastUtil.showToast(this, getResources().getString(R.string.network_not_connected),
                    Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(work_no)) {
            ToastUtil.showToast(this, "工号为空!", Toast.LENGTH_LONG).show();
            return;
        }
        EloadingDialog.ShowDialog(this);
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("work_no", work_no);
        String url = EPlamformServices.get_user_info;
        VHttpUrlConnectionUtil.postRequest(url, parameters,
                new HttpUrlConnectionUtil.onConnectionFinishLinstener() {
                    @Override
                    public void onSuccess(String url, String result) {
                        JSONObject dataJson = null;
                        int ret_code = 0;
                        String ret_message = "";
                        EloadingDialog.cancle();
                        try {
                            dataJson = new JSONObject(result);
                            ret_code = dataJson.getInt("ret_code");
                            if (dataJson.has("ret_message")) {
                                ret_message = dataJson.getString("ret_message");
                            }

                            if (ret_code > 0) {
                                String content = dataJson.getString("ep_user_info");
                                JSONObject contentObject = new JSONObject(content);
                                String work_no = contentObject.getString("work_no");
                                String email = contentObject.getString("email");
                                user_name = contentObject.getString("name");
                                dept_name = contentObject.getString("dept_name");
                                phone = contentObject.getString("mobile_phone");

                                telephone_tv.setText(phone);
                                use_name_tv.setText(user_name);
                                username_tv.setText(user_name);
                                department_tv.setText(dept_name);
                                number_tv.setText(work_no);
                                mail_tv.setText(email);
                                user_iv.setVisibility(View.VISIBLE);
                                EaseUserUtils.setUserNick(hx_no, user_iv);
                            } else {
                                ToastUtil.showToast(UserInfoDetailActivity.this, ret_message, Toast.LENGTH_LONG).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFail(String result) {
                        EloadingDialog.cancle();
                        ToastUtil.showToast(UserInfoDetailActivity.this, getResources().getString(R.string.request_fail), Toast.LENGTH_LONG).show();
                    }
                });

    }


}
