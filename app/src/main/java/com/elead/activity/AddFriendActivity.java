package com.elead.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.elead.entity.SearchFriendEntity;
import com.elead.eplatform.R;
import com.elead.im.db.DemoHelper;
import com.elead.service.EPlamformServices;
import com.elead.utils.Constants;
import com.elead.utils.EloadingDialog;
import com.elead.utils.HttpUrlConnectionUtil;
import com.elead.utils.NetWorkUtils;
import com.elead.utils.PermissionUtil;
import com.elead.utils.SearchUtils;
import com.elead.utils.ToastUtil;
import com.elead.utils.Util;
import com.elead.utils.volley.VHttpUrlConnectionUtil;
import com.elead.views.AlertDialog;
import com.elead.views.CircularImageView;
import com.elead.views.QrCodeDialog;
import com.elead.views.baseadapter.CommonAdapter;
import com.elead.views.baseadapter.ViewHolder;
import com.hyphenate.chat.EMClient;
import com.xys.libzxing.zxing.activity.CaptureActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.elead.utils.StatusBarUtils.setWindowStatusBarColor;

public class AddFriendActivity extends BaseActivity implements View.OnClickListener, TextWatcher, AdapterView.OnItemClickListener {


    private AlertDialog builder;
    private static final int SEARCHFRIEND = 120;
    private SearchUtils searchutils;
    private View searchView;
    private EditText ediSearch;
    private View alphaView;
    private PopupWindow popUWindow;
    private LinearLayout add_friend_all_rel;
    private TextView tvCancle, my_qrcode_tv;
    private ImageButton back_t;
    private TextView title_middle;
    private RelativeLayout toolbar_rel;
    private ListView listView;
    public SearchFriendEntity entry;
    private List<SearchFriendEntity> searchList = new ArrayList<>();
    private CommonAdapter adapter;
    private TextView user_no_exit;
    private ProgressDialog progressDialog;
    private ImageView cear_delete;

    TextView search_friend_tv;

    RelativeLayout scan_layout_re, contact_layout_re;

    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {

                case SEARCHFRIEND:
                    CharSequence s = (CharSequence) msg.obj;
                    if (TextUtils.isEmpty(s)) {
                        alphaView.setEnabled(true);
                        alphaView.setBackgroundColor(Color.parseColor("#80000000"));
                        searchList.clear();
                        listView.setVisibility(View.GONE);
                        user_no_exit.setVisibility(View.GONE);
                        cear_delete.setVisibility(View.GONE);
                        // listView.setVisibility(View.VISIBLE);

                    } else {
                        cear_delete.setVisibility(View.VISIBLE);
                        getUserInfo();
                    }
                    break;
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_add_friend);
        setWindowStatusBarColor(this, Color.parseColor("#2ec7c9"));
        //setTitle(getResources().getString(R.string.add_friends), TitleType.NORMAL);
        setupViews();
    }

    private void setupViews() {
        InitSearchViews();
        search_friend_tv = (TextView) findViewById(R.id.search_friend_tv);
        my_qrcode_tv = (TextView) findViewById(R.id.my_qrcode_layout);
        scan_layout_re = (RelativeLayout) findViewById(R.id.scan_layout_re);
        contact_layout_re = (RelativeLayout) findViewById(R.id.contact_layout_re);
        add_friend_all_rel = (LinearLayout) findViewById(R.id.add_friend_all_rel);
        back_t = (ImageButton) findViewById(R.id.back);
        title_middle = (TextView) findViewById(R.id.title);
        toolbar_rel = (RelativeLayout) findViewById(R.id.toolbar_rel);
        search_friend_tv.setOnClickListener(this);
        my_qrcode_tv.setOnClickListener(this);
        scan_layout_re.setOnClickListener(this);
        contact_layout_re.setOnClickListener(this);
        back_t.setOnClickListener(this);
        title_middle.setText(getResources().getString(R.string.add_friends));
        listView.setOnItemClickListener(this);
    }


  /*  @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (popUWindow != null) {
            if (!popUWindow.isShowing()) {
                searchutils.resetUI();
                ediSearch.setText("");
            }
        }
    }*/

    private void InitSearchViews() {
        searchView = LayoutInflater.from(this).inflate(R.layout.item_popup_search, null);
        ediSearch = (EditText) searchView.findViewById(R.id.edi_search);
        alphaView = (View) searchView.findViewById(R.id.popup_window_v_alpha);
        tvCancle = (TextView) searchView.findViewById(R.id.tvCanale);
        listView = (ListView) searchView.findViewById(R.id.search_listiv);
        user_no_exit = (TextView) searchView.findViewById(R.id.user_no_exit_iv);
        cear_delete = (ImageView) searchView.findViewById(R.id.cear_delete_iv);
        // ediSearch.setHint(strHint);
        ediSearch.setFocusable(true);
        tvCancle.setOnClickListener(this);
        alphaView.setOnClickListener(this);
        ediSearch.addTextChangedListener(this);
        cear_delete.setOnClickListener(this);
    }


    private void showToast() {
        ToastUtil.showToast(this, "正在玩命开发中......", 0).show();
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        Intent i = null;
        switch (id) {

            case R.id.search_friend_tv:
//                i = new Intent(AddFriendActivity.this, AddContactActivity.class);
//                startActivity(i);
                // Log.i("TAG", "toolab=" + toolbar_rel);
                searchutils = new SearchUtils(AddFriendActivity.this, toolbar_rel, add_friend_all_rel, searchView);
                popUWindow = searchutils.getPopupWindow();
                searchutils.setEditText(ediSearch);
                searchutils.showSearchBar();

                break;
            case R.id.scan_layout_re:
                //打开扫描界面扫描条形码或二维码
                if (PermissionUtil.requestCameraPermissions(AddFriendActivity.this, 100)) {
                    i = new Intent(AddFriendActivity.this, CaptureActivity.class);
                    startActivityForResult(i, 0);
                }
                break;
            case R.id.contact_layout_re:
                EloadingDialog.showDailog();
                break;

            case R.id.my_qrcode_layout:
                QrCodeDialog dialog = new QrCodeDialog(AddFriendActivity.this, "", "");

                dialog.show();
                break;

            case R.id.tvCanale:
                searchutils.dismissPopupWindow();
                ediSearch.setText("");
                break;

            case R.id.popup_window_v_alpha:
                searchutils.dismissPopupWindow();
                ediSearch.setText("");
                break;

            case R.id.back:
                AddFriendActivity.this.finish();
                break;

            case R.id.cear_delete_iv:
                ediSearch.setText("");
                break;
            default:
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 0) {
            if (null != data) {
                Bundle bundle = data.getExtras();
                final String scanResult = bundle.getString("result");
                ToastUtil.showToast(mContext, scanResult, 0).show();

                //只能由数字和字母组成匹配
                Pattern p = Pattern.compile("^[A-Za-z0-9]+$");
                //邮箱匹配
                Pattern email = Pattern.compile("^[a-zA-Z][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$");
                Matcher phone_p = p.matcher(scanResult);
                Matcher email_p = email.matcher(scanResult);

                //网址匹配
                Pattern pm = Util.setMatcher();
                Matcher Web_p = pm.matcher(scanResult);

                if (phone_p.find() || email_p.find()) {

                    final String userName = EMClient.getInstance().getCurrentUser();
                    if (!userName.equals(scanResult)) {
                        Intent intentValidation = new Intent(this, ValidationFriendActivity.class);
                        intentValidation.putExtra(Constants.VALIDATION_FRIEND_KEY, scanResult);
                        startActivity(intentValidation);
                    } else {
                        showDailog(getResources().getString(R.string.not_add_myself), getResources().getString(R.string.warm_reminder));
                    }

                } else if (Web_p.find()) {

                    Intent intent = new Intent(Intent.ACTION_VIEW, (Uri.parse(scanResult))).
                            addCategory(Intent.CATEGORY_BROWSABLE).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    //扫描不到的
                } else {
                    showDailog(scanResult, getResources().getString(R.string.not_identify_qrcode));
                }
            }
        }


    }

    public void showDailog(String msg, String title) {
        if (builder == null) {
            builder = new com.elead.views.AlertDialog(AddFriendActivity.this).builder();
        }
        builder.setTitle(title);
        builder.setMsg(msg);
        builder.setPositiveButton(getResources().getString(R.string.ok), 0, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        builder.setCancelable(false);
        builder.show();
    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
//            searchutils.dismissPopupWindow();
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }

    /**
     * 搜索
     */
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

        handler.removeMessages(SEARCHFRIEND);
        Message message = handler.obtainMessage();
        message.obj = s;
        message.what = SEARCHFRIEND;
        handler.sendMessageDelayed(message, 300);
    }

    @Override
    public void afterTextChanged(Editable s) {
//        if (s.length() > 0) {
//            getUserInfo();
//        }
    }


    private void getUserInfo() {
        if (!NetWorkUtils.isNetworkConnected(AddFriendActivity.this)) {
            ToastUtil.showToast(this, getResources().getString(R.string.network_not_connected),
                    Toast.LENGTH_LONG).show();
            return;
        }

//        if (TextUtils.isEmpty(ediSearch.getText().toString())) {
//            return;
//        }
        EloadingDialog.ShowDialog(this);
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("name_key", ediSearch.getText().toString());
        String url = EPlamformServices.search_user;
        VHttpUrlConnectionUtil.postRequest(url, parameters, new HttpUrlConnectionUtil.onConnectionFinishLinstener() {


            @Override
            public void onSuccess(String url, String result) {
                JSONObject dataJson = null;

                int ret_code = 0;
                EloadingDialog.cancle();
                try {
                    dataJson = new JSONObject(result);
                    ret_code = dataJson.getInt("ret_code");
                    JSONArray array = null;
                    if (ret_code == 1) {
                        array = dataJson.getJSONArray("match_users");
                        Log.i("TAG", "array==" + array);
                        searchList.clear();
                        for (int i = 0; i < array.length(); i++) {
                            entry = new SearchFriendEntity();
                            JSONObject myObect = (JSONObject) array.get(i);
                            entry.setWorkNumber(myObect.getString("work_no"));
                            entry.setName(myObect.getString("name"));
                            entry.setHxUserName(myObect.getString("em_username"));
                            searchList.add(entry);
                        }

                        if (searchList != null && searchList.size() > 0) {
                            listView.setVisibility(View.VISIBLE);
                            user_no_exit.setVisibility(View.GONE);
                            alphaView.setEnabled(false);
                            alphaView.setBackgroundColor(Color.parseColor("#F5F5F5"));
                            if (null == adapter) {
                                adapter = new CommonAdapter<SearchFriendEntity>(AddFriendActivity.this, searchList, R.layout.search_add_friends_item) {
                                    @Override
                                    public void convert(ViewHolder helper, SearchFriendEntity item) {
                                        helper.setText(R.id.username_iv, item.getName());
                                        helper.setText(R.id.wrok_no_iv, item.getWorkNumber());
                                        CircularImageView photo = helper.getView(R.id.photo_id);
                                        photo.setBackgroundColor(item.getName());
                                        photo.setText(item.getName());
                                        photo.setVisibility(View.VISIBLE);
                                    }
                                };
                                listView.setAdapter(adapter);
                            } else {
                                adapter.notifyDataSetChanged();
                            }
                        } else {
                            listView.setVisibility(View.GONE);
                        }


                    } else if (ret_code == 0) {
                        listView.setVisibility(View.GONE);
                        user_no_exit.setVisibility(View.VISIBLE);
                        alphaView.setBackgroundColor(Color.parseColor("#F5F5F5"));
                        alphaView.setEnabled(false);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFail(String url) {
                EloadingDialog.cancle();
                ToastUtil.showToast(AddFriendActivity.this, getResources().getString(R.string.request_fail), Toast.LENGTH_LONG).show();
            }
        });

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //Log.i("TAG", "hx==" + searchList.get(position).getHxUserName());
        if (searchList.get(position).getHxUserName().equals("null")) {
            ToastUtil.showToast(this, getResources().getString(R.string.no_registered),
                    Toast.LENGTH_LONG).show();
        } else {
//            ToastUtil.showToast(this, "注册了",Toast.LENGTH_LONG).show();
            showDailogAddFriend(position);
        }
    }

    private void showDailogAddFriend(final int position) {
        AlertDialog builderAdd = null;
        if (builderAdd == null) {
            builderAdd = new com.elead.views.AlertDialog(AddFriendActivity.this).builder();
        }
        //builder.setTitle(title);
        builderAdd.setMsg(getResources().getString(R.string.if_add_friend));
        builderAdd.setPositiveButton(getResources().getString(R.string.ok), 0, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addContactFriend(position);
            }


        });

        builderAdd.setNegativeButton(getResources().getString(R.string.cancle), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        builderAdd.setCancelable(false);
        builderAdd.show();
    }


    private void addContactFriend(final int position) {
        if (EMClient.getInstance().getCurrentUser().equals(searchList.get(position).getHxUserName())) {
            // new EaseAlertDialog(this, R.string.not_add_myself).show();
            showDailog(getResources().getString(R.string.not_add_myself), getResources().getString(R.string.warm_reminder));
            return;
        }

        if (DemoHelper.getInstance().getContactList().containsKey(searchList.get(position).getHxUserName())) {
            //let the user know the contact already in your contact list
            if (EMClient.getInstance().contactManager().getBlackListUsernames().contains(searchList.get(position).getHxUserName())) {
                // new EaseAlertDialog(this, R.string.user_already_in_contactlist).show();
                showDailog(getResources().getString(R.string.user_already_in_contactlist), getResources().getString(R.string.warm_reminder));
                return;
            }
            //new EaseAlertDialog(this, R.string.This_user_is_already_your_friend).show();
            showDailog(getResources().getString(R.string.This_user_is_already_your_friend), getResources().getString(R.string.warm_reminder));
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
                    EMClient.getInstance().contactManager().addContact(searchList.get(position).getHxUserName(), s);
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

}
