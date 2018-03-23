package com.elead.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.elead.application.MyApplication;
import com.elead.entity.DeviceItemEntity;
import com.elead.eplatform.R;
import com.elead.im.widget.EaseConstant;
import com.elead.module.EpUser;
import com.elead.service.EPlamformServices;
import com.elead.utils.Constants;
import com.elead.utils.CryptionUtil;
import com.elead.utils.DeviceManageUtil;
import com.elead.utils.EloadingDialog;
import com.elead.utils.EncryptUtil;
import com.elead.utils.HttpUrlConnectionUtil;
import com.elead.utils.SharedPreferencesUtil;
import com.elead.utils.StatusBarUtils;
import com.elead.utils.ToastUtil;
import com.elead.utils.Util;
import com.elead.utils.volley.VHttpUrlConnectionUtil;
import com.elead.views.AlertDialog;
import com.elead.views.BindDialog;
import com.elead.views.InputMethodRelativeLayout;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends BaseActivity implements InputMethodRelativeLayout.OnSizeChangedListenner {
    private static final String TAG = "LoginActivity";
    public static final int REQUEST_CODE_SETNICK = 1;
    private final int REQUEST_READ_PHONE_STATE = 2;
    public static final String english = "abcdefghijklmnopqrstuvwxyz";
    @BindView(R.id.iv_username)
    ImageView ivUsername;
    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.iv_password)
    ImageView ivPassword;
    @BindView(R.id.password)
    EditText password;
    //    @BindView(R.id.tv_mail_address)
//    TextView tv_mail_address;
    @BindView(R.id.username1)
    EditText username1;
    @BindView(R.id.line_username)
    View line_username;
    @BindView(R.id.line_pwd)
    View line_pwd;

    TextView tv_register, tv_get_back_pwd;

    private String currentUsername;
    private String currentPassword;
    private String oldUsername;
    private String oldPassword;

    private boolean isShowPassword = false;
    private SharedPreferences loginUsers = null;
    private AlertDialog builder;
    private boolean isShowMailAddress;
    private String mail_address;
    private Resources resources;
    private boolean isLogout;
    private Button btn_login_btn;
    private InputMethodRelativeLayout viewById;

    private boolean isClearPwd = false;


    public class DeviceHandler extends android.os.Handler {
        private WeakReference<Context> reference;

        public DeviceHandler(Context context) {
            reference = new WeakReference<>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            EloadingDialog.cancle();
            LoginActivity activity = (LoginActivity) reference.get();
            if (null == activity) {
                return;
            }
            if (msg.what == DeviceManageUtil.MutiDevice) {

                String msgStr = (String) msg.obj;
                ToastUtil.showToast(msgStr).show();
                if (msg.arg2 < 1) {//没有绑定次数了
//                    exitToLogin();
                } else if (msg.arg1 > 1) {//多设备
                    BindDialog dialog = new BindDialog(LoginActivity.this);
                    dialog.show();
                }

            } else if (msg.what == DeviceManageUtil.BindStatus) {
                //后台返回  0 表示未绑定   1表示自己账户绑定 2 表示其他账户绑定
                if (msg.arg1 == 0 || msg.arg1 == 3) {
                    showBindDailog();
                } else if (msg.arg1 == 2) {//退出
//                    exitToLogin();
                    ToastUtil.showToast(getResources().getString(R.string.device_binded_other)).show();
                } else if (msg.arg1 == 1) {
                    MyApplication.isBind = true;
                    jumpToMainActivity();
                }
            } else if (msg.what == DeviceManageUtil.BindDevice) {
                if (msg.arg1 == -5) {
                    deviceManageUtil.isMutiDeviceUser(mHandler);
                } else if (msg.arg1 < 0) {
                    //绑定失败是否要退出登录？暂时不做处理。
//                    exitToLogin();
                } else if (msg.arg1 > 0) {
                    jumpToMainActivity();
                }
            }
        }
    }

    public DeviceHandler mHandler = new DeviceHandler(this);
    DeviceManageUtil deviceManageUtil = new DeviceManageUtil(LoginActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        StatusBarUtils.setWindowStatusBarColor(this, getResources().getColor(R.color.qianlv));
        setContentView(R.layout.elead_activity_login);
        isLogout = getIntent().getBooleanExtra("logout", false);
        loginUsers = getSharedPreferences("LoginUsers", Context.MODE_PRIVATE);
        ButterKnife.bind(this);
        initView();
        setUserInfo();

    }

    private void setUserInfo() {
        oldUsername = SharedPreferencesUtil.getString(this, Constants.USERNAME);
        if (!TextUtils.isEmpty(oldUsername)) {
            oldPassword = SharedPreferencesUtil.getString(this, Constants.PASSWORD);
            String oldName = formatUsername(oldUsername);
            username.setText(oldName);
            if (!isLogout) {
                password.setText(oldPassword);
            } else {
                SharedPreferencesUtil.putString(LoginActivity.this, Constants.PASSWORD, "");
            }
            username.setSelection(username.getText().toString().length());
        }
    }

    /**
     * 弹出绑定对话框
     */
    private void showBindDailog() {

        new com.elead.views.AlertDialog(mContext)
                .builder()
                .setMsg(getResources().getString(R.string.bind_device))
                .setCancelable(false)
                .setPositiveButton(getResources().getString(R.string.sure_of),0, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DeviceItemEntity entity = new DeviceItemEntity();
                        entity.udid = MyApplication.serial_device;
                        entity.name = "" + android.os.Build.MODEL;//手机型号
                        deviceManageUtil.bindDevice(entity, mHandler);
                    }
                }).setNegativeButton(getResources().getString(R.string.cancle), new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        }).show();
    }


    /**
     * 保持用户信息到本地
     */
    private void saveUserInfoToLocal() {
        SharedPreferencesUtil.putString(LoginActivity.this, Constants.USERNAME, currentUsername);
        SharedPreferencesUtil.putString(LoginActivity.this, Constants.PASSWORD, currentPassword);
    }

    /**
     * 初始化view
     */
    private void initView() {
        resources = getResources();
        mail_address = this.getResources().getString(R.string.e_lead_mail_address);
        ivPassword.setVisibility(View.INVISIBLE);

        btn_login_btn = (Button) findViewById(R.id.btn_login_btn);
        tv_register = (TextView) findViewById(R.id.tv_register);
        tv_get_back_pwd = (TextView) findViewById(R.id.tv_get_back_pwd);
        viewById = (InputMethodRelativeLayout) findViewById(R.id.getnlay);
        setListener();
    }

    /**
     * 设置监听
     */
    private void setListener() {

        viewById.setOnSizeChangedListenner(this);
        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, SendMailActivity.class);
                i.putExtra("type", 1);
                startActivity(i);
            }
        });

        tv_get_back_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, SendMailActivity.class);
                i.putExtra("type", 2);
                startActivity(i);
            }
        });

        // if user changed, clear the passwordEd
        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                OnUsernameTextChange(s, count);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        username.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // 此处为得到焦点时的处理内容
                    line_username.setBackgroundColor(resources.getColor(R.color.develop_tb_line));
                    if (!TextUtils.isEmpty(username.getText().toString())) {
                        isShowClearUsernameOrPwd(ivUsername, true);
                    } else {
                        isShowClearUsernameOrPwd(ivUsername, false);
                    }
                    isShowPassword(false);
                } else {
                    // 此处为失去焦点时的处理内容
                    line_username.setBackgroundColor(resources.getColor(R.color.small_line_bg));
                }
            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s) && s.length() > 0) {
                    btn_login_btn.setEnabled(true);
                    if (TextUtils.equals(isShowMailAddress ? username.getText().toString() + mail_address : username.getText().toString(), oldUsername)
                            && !isClearPwd) {
                        isShowPassword(false);
                    } else {
                        isShowPassword(true);
                    }
                } else {
                    isClearPwd = true;
                    btn_login_btn.setEnabled(false);
                    if (TextUtils.isEmpty(s)) {
                        isShowPassword(false);
                    } else {
                        isShowPassword(true);
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // 此处为得到焦点时的处理内容
                    line_pwd.setBackgroundColor(resources.getColor(R.color.develop_tb_line));
                    isShowClearUsernameOrPwd(ivUsername, false);
                    if (TextUtils.equals(isShowMailAddress
                                    ? username.getText().toString() + mail_address
                                    : username.getText().toString(),
                            oldUsername)
                            && !TextUtils.isEmpty(password.getText().toString())
                            && !isClearPwd) {
                        isShowPassword(false);
                    } else {
                        if (TextUtils.isEmpty(password.getText().toString())) {
                            isShowPassword(false);
                        } else {
                            isShowPassword(true);
                        }
                    }
                } else {
                    // 此处为失去焦点时的处理内容
                    line_pwd.setBackgroundColor(resources.getColor(R.color.small_line_bg));
                }
            }
        });

    }

    /**
     * 处理username字符改变事件
     *
     * @param s
     * @param count
     */
    private void OnUsernameTextChange(CharSequence s, int count) {
        isShowMailAddress(s.toString());
        if (TextUtils.equals(isShowMailAddress ? s + mail_address : s, oldUsername)
                && !isClearPwd) {
            isShowPassword = false;
        } else {
            isShowPassword = true;
        }
        password.setText(null);
        if (!TextUtils.isEmpty(s) && s.length() > 0) {
            password.setEnabled(true);
            isShowClearUsernameOrPwd(ivUsername, true);
        } else {
            if (TextUtils.isEmpty(s)) {
                password.setEnabled(false);
                isShowClearUsernameOrPwd(ivUsername, false);
            } else {
                password.setEnabled(true);
                isShowClearUsernameOrPwd(ivUsername, true);
            }
        }
    }

    /**
     * 是否显示密码选择框
     *
     * @param isShow
     */
    private void isShowPassword(boolean isShow) {
        if (isShow && isShowPassword) {
            ivPassword.setVisibility(View.VISIBLE);
        } else {
            ivPassword.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 是否显示清除控件
     *
     * @param view
     * @param isShow
     */
    private void isShowClearUsernameOrPwd(View view, boolean isShow) {
        if (isShow) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 是否包含英文字母
     *
     * @param s
     */
    private void isShowMailAddress(String s) {
        String start;
        if (!TextUtils.isEmpty(s)) {
            if (s.length() > 0) {
                start = s.substring(0, 1);
                if (english.contains(start)) {
                    isShowMailAddress = true;
                    username1.setVisibility(View.VISIBLE);
                    setTextColor(s + mail_address);
                } else {
                    isShowMailAddress = false;
                    username1.setVisibility(View.GONE);
                }
            } else {
                isShowMailAddress = false;
                username1.setVisibility(View.GONE);
            }
        } else {
            isShowMailAddress = false;
            username1.setVisibility(View.GONE);
        }
    }

    private void setTextColor(String text) {
        if (!TextUtils.isEmpty(text)) {
            username1.setText(text);
            SpannableStringBuilder builder = new SpannableStringBuilder(username1.getText().toString());
            int count = mail_address.length();
            //ForegroundColorSpan 为文字前景色，BackgroundColorSpan为文字背景色
            ForegroundColorSpan whiteSpan = new ForegroundColorSpan(Color.WHITE);
            ForegroundColorSpan blackSpan = new ForegroundColorSpan(Color.BLACK);
            builder.setSpan(whiteSpan, 0, text.length() - count, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            builder.setSpan(blackSpan, text.length() - count - 1, text.length() - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            username1.setText(builder);
        }
    }

    private String formatUsername(String text) {
        if (!TextUtils.isEmpty(text) && text.contains("@")) {
            return text.substring(0, text.indexOf("@"));
        }
        return text;
    }

    /**
     * login
     *
     * @param view
     */
    public void login(View view) {
        currentUsername = username.getText().toString().trim();
        if (isShowMailAddress) {
            currentUsername = currentUsername + mail_address;
        }
        currentPassword = password.getText().toString().trim();
        if (TextUtils.isEmpty(currentUsername)) {
            ToastUtil.showToast(this, R.string.User_name_cannot_be_empty, 0).show();
            return;
        }
        if (TextUtils.isEmpty(currentPassword)) {
            ToastUtil.showToast(this, R.string.Password_cannot_be_empty, 0).show();
            return;
        }
        saveUserInfoToLocal();
        EloadingDialog.ShowDialog(mContext);
        eleadLogin(currentUsername, currentPassword);
    }

    /**
     * 绑定设备逻辑
     */
    public void bindDeviceLogic() {

        deviceManageUtil.isDeviceBindedStatus(mHandler);
    }

    /**
     * 登录成功跳转到主页面
     */
    public void jumpToMainActivity() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        LoginActivity.this.finish();
    }

    private void eleadLogin(final String uName, final String pwd) {

        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("user_code", uName);
//        parameters.put("plain_pwd", pwd);
        parameters.put("user_pwd", EncryptUtil.encryptPsw(pwd));
        parameters.put("platform", "android");
        //http://192.168.1.225:8080/eplat-uat/
        String url = EPlamformServices.login_service;
        VHttpUrlConnectionUtil.postRequest(url, parameters,
                new HttpUrlConnectionUtil.onConnectionFinishLinstener() {

                    @Override
                    public void onSuccess(String url, String result) {
                        JSONObject dataJson = null;
                        int ret_code = 0;
                        String userName = "";
                        try {
                            dataJson = new JSONObject(result);
                            ret_code = dataJson.getInt("ret_code");
                            userName = dataJson.getString("im_user_name");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        EloadingDialog.cancle();
                        if (ret_code > 0) {//登陆成功
                            MyApplication.user.code = uName;
                            MyApplication.user.pwd = pwd;
                            try {
                                JSONObject jsonObject = new JSONObject(result);
                                JSONObject ep_user_info = jsonObject.getJSONObject("ep_user_info");
                                MyApplication.user.id = ep_user_info.getString("userid").toString();
                                MyApplication.user.work_no = ep_user_info.getString("work_no").toString();
                                loginHuanxin(userName);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            SharedPreferencesUtil.putString(mContext, EpUser.class.getSimpleName(), JSON.toJSONString(MyApplication.user));
                            if (Util.isExitWhiteList()) {//白名单的用户不做绑定设备检测
                                jumpToMainActivity();
                            } else {
                                //绑定设备逻辑处理
                                bindDeviceLogic();
                            }

                        } else if (ret_code == 0) {
                            ToastUtil.showToast(mContext, R.string.user_password_isnull, 0).show();
                        } else if (ret_code == -1) {//该用户不存在！
                            ToastUtil.showToast(mContext, R.string.user_not_exist, 0).show();
                        } else if (ret_code == -2) {//用户名或密码错误
                            ToastUtil.showToast(mContext, R.string.error_user_password, 0).show();
                        } else if (ret_code == -7) {//原始密码，强制修改
                            if (null == builder) {
                                builder = new AlertDialog(LoginActivity.this).builder();
                            }
                            builder.setMsg("你的密码是原始密码，请修改后使用!");
                            builder.setTitle("提示");

                            builder.setPositiveButton("确认",0, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(LoginActivity.this, ModifyPasswordActivity.class);
                                    intent.putExtra("user_code", uName);
                                    intent.putExtra("plain_pwd", pwd);
                                    startActivity(intent);
                                }
                            });

                            builder.setNegativeButton("取消", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                }
                            });
                            builder.setCancelable(false);
                            builder.show();
                        } else {
                            ToastUtil.showToast(mContext, R.string.e_lead_login_failed, 0).show();
                        }
                    }

                    @Override
                    public void onFail(String result) {
//                        Log.d("eleadLogin", result);
                        EloadingDialog.cancle();
                        int requestCount = SharedPreferencesUtil.getInt(mContext, Util.REQUEST_COUNT);
                        SharedPreferencesUtil.putInt(mContext, Util.REQUEST_COUNT, requestCount+1);
                        if (requestCount < 1) {
                            eleadLogin(uName, pwd);
                        } else {
                            SharedPreferencesUtil.putInt(mContext, Util.REQUEST_COUNT, -1);
                            ToastUtil.showToast(mContext, mContext.getResources().getString(R.string.e_lead_login_failed), 1).show();
                        }
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.LOGIN_RESULT_CODE && null != data) {
            String userName = data.getStringExtra(Constants.USERNAME);
            String pwd = data.getStringExtra(Constants.PASSWORD);
            if (!TextUtils.isEmpty(userName)) {
                username.setText(userName);
                username.setSelection(username.getText().length());
            }
            if (!TextUtils.isEmpty(pwd)) {
                password.setText(pwd);
                btn_login_btn.setEnabled(true);
            }
        }
    }

    /**
     * 登录环信
     */
    private void loginHuanxin(final String username) {
        Log.d("eleadLogin", "loginHuanxin");

        EMClient.getInstance().login(username, CryptionUtil.md5Hex(currentPassword).toLowerCase(), new EMCallBack() {

            @Override
            public void onSuccess() {
                Log.d(TAG, "login: onSuccess");
            }

            @Override
            public void onProgress(int progress, String status) {
                Log.d(TAG, "login: onProgress");
            }

            @Override
            public void onError(final int code, final String message) {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        ToastUtil.showToast(mContext, "登陆失败", 2000).show();
                    }
                });
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (MyApplication.isSetPsw) {
            MyApplication.isSetPsw = false;
            password.setText("");
        }

    }

    /**
     * clearUsername
     *
     * @param view
     */
    public void clearUsername(View view) {
        username.setText(null);
    }

    private boolean isChecked = false;

    /**
     * clearPassword
     *
     * @param view
     */
    public void showPassword(View view) {
        if (isChecked) {
            password.setTransformationMethod(PasswordTransformationMethod.getInstance());
        } else {
            password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }
        isChecked = !isChecked;
        password.postInvalidate();
        password.setSelection(password.getText().length());
    }

    @Override
    public void onSizeChange(boolean paramBoolean, int w, int h) {
        if (paramBoolean) {//软键盘弹出
            findViewById(R.id.login_bottom_tv).setVisibility(View.GONE);
        } else {//软键盘收回
            findViewById(R.id.login_bottom_tv).setVisibility(View.VISIBLE);
        }

    }
}
