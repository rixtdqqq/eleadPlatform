/**
 * Copyright (C) 2016 Hyphenate Inc. All rights reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.elead.im.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.elead.activity.BaseActivity;
import com.elead.im.adapter.SearchGroupAdapter;
import com.elead.eplatform.R;
import com.elead.im.adapter.GroupAdapter;
import com.elead.im.widget.EaseConstant;
import com.elead.utils.SearchUtils;
import com.elead.utils.ToastUtil;
import com.elead.views.baseadapter.CommonAdapter;
import com.elead.views.pulltorefresh.PullToRefreshListView;
import com.elead.views.pulltorefresh.PullToRefreshView;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.exceptions.HyphenateException;

import java.util.ArrayList;
import java.util.List;

import static com.elead.utils.StatusBarUtils.setWindowStatusBarColor;


public class GroupsActivity extends BaseActivity implements View.OnClickListener, TextWatcher, OnItemClickListener {
    public static final String TAG = "GroupsActivity";
    private static final int SEARCH = 124;
    private PullToRefreshListView groupListView;
    private List<EMGroup> grouplist = new ArrayList<>();
    private InputMethodManager inputMethodManager;
    private ImageButton back_bt;
    private ImageView liao_tian;
    private ImageView search_iv;

    private View searchView;
    private View alphaView;
    private TextView tvCancle;
    private ImageButton back_t;
    private ListView listView;
    private TextView user_no_exit;
    private ImageView cear_delete;
    private SearchUtils searchutils;
    private EditText ediSearch;
    private LinearLayout add_friend_all_rel;
    private RelativeLayout toolbar_rel;
    private SearchGroupAdapter searchGroupadapter;
    private PopupWindow popupWidow;

    private CommonAdapter<EMGroup> groupAdapter;

    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            groupListView.onRefreshComplete();
            switch (msg.what) {
                case 0:
                    refresh();
                    break;
                case 1:
                    ToastUtil.showToast(GroupsActivity.this, R.string.Failed_to_get_group_chat_information, 1).show();
                    break;
                case SEARCH:
                    CharSequence s = (CharSequence) msg.obj;
                    if (TextUtils.isEmpty(s)) {
                        alphaView.setEnabled(true);
                        alphaView.setBackgroundColor(Color.parseColor("#80000000"));
                        //searchList.clear();
                        listView.setVisibility(View.GONE);
                        user_no_exit.setVisibility(View.GONE);
                        cear_delete.setVisibility(View.GONE);
                    } else {
                        cear_delete.setVisibility(View.VISIBLE);
                        setAdapter(s);
                    }
                    break;
            }
        }

        ;
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.em_fragment_groups);
        setWindowStatusBarColor(this, Color.parseColor("#2ec7c9"));
        InitSearchViews();
        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        back_bt = (ImageButton) findViewById(R.id.back_bt_id);
        liao_tian = (ImageView) findViewById(R.id.liao_tian_iv);
        search_iv = (ImageView) findViewById(R.id.search_id);
        toolbar_rel = (RelativeLayout) findViewById(R.id.toolbar_rel);
        add_friend_all_rel = (LinearLayout) findViewById(R.id.add_friend_all_rel);


        back_bt.setOnClickListener(this);
        liao_tian.setOnClickListener(this);
        search_iv.setOnClickListener(this);
    }


    @Override
    public void onResume() {
        super.onResume();
        initList();

    }

    public void initList() {
        if (null == groupAdapter) {
            grouplist.addAll(EMClient.getInstance().groupManager().getAllGroups());
            groupListView = (PullToRefreshListView) findViewById(R.id.list);
            groupListView.setSpkey(GroupsActivity.class.getSimpleName());
            groupAdapter = new GroupAdapter(mContext, grouplist, R.layout.em_row_group);

            groupListView.setAdapter(groupAdapter);

            groupListView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    new Thread() {
                        @Override
                        public void run() {
                            try {
                                EMClient.getInstance().groupManager().getJoinedGroupsFromServer();
                                handler.sendEmptyMessage(0);
                            } catch (HyphenateException e) {
                                e.printStackTrace();
                                handler.sendEmptyMessage(1);
                            }
                        }
                    }.start();
                }
            });

            groupListView.getListview().setOnItemClickListener(new OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*if (position == 1) {
                    // create a new group
                    startActivityForResult(new Intent(GroupsActivity.this, NewGroupActivity.class), 0);
                } else if (position == 1) {
                    // join a public group
                    startActivityForResult(new Intent(GroupsActivity.this, PublicGroupsActivity.class), 0);
                } else {*/
                    // enter group chat
                    Intent intent = new Intent(GroupsActivity.this, ChatActivity.class);
                    intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_GROUP);
                    intent.putExtra(EaseConstant.EXTRA_USER_ID, groupAdapter.getItem(position).getGroupId());
                    startActivityForResult(intent, 0);
                    //}
                }

            });
            groupListView.setOnTouchListener(new OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
                        if (getCurrentFocus() != null)
                            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                    return false;
                }
            });
        } else {
            refresh();
        }
    }

    private void refresh() {
        grouplist.clear();
        grouplist.addAll(EMClient.getInstance().groupManager().getAllGroups());
        groupAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_bt_id:
                finish();
                break;

            case R.id.liao_tian_iv:
                Intent intent = new Intent(this, NewGroupActivity.class);
                startActivity(intent);
                break;

            case R.id.search_id:
                //  EloadingDialog.howDailg();
                searchutils = new SearchUtils(GroupsActivity.this, toolbar_rel, add_friend_all_rel, searchView);
                popupWidow = searchutils.getPopupWindow();
                searchutils.setEditText(ediSearch);
                searchutils.showSearchBar();
                break;

            case R.id.tvCanale:
                searchutils.dismissPopupWindow();
                ediSearch.setText("");
                //imm.hideSoftInputFromWindow(ediSearch.getWindowToken(),0);
                break;

            case R.id.popup_window_v_alpha:
                searchutils.dismissPopupWindow();
                ediSearch.setText("");
                //imm.hideSoftInputFromWindow(ediSearch.getWindowToken(),0);
                break;
            case R.id.cear_delete_iv:
                ediSearch.setText("");
                break;
        }
    }

    /**
     * 搜索群组
     */
    private void InitSearchViews() {
        searchView = LayoutInflater.from(this).inflate(R.layout.item_popup_search, null);
        ediSearch = (EditText) searchView.findViewById(R.id.edi_search);
        alphaView = (View) searchView.findViewById(R.id.popup_window_v_alpha);
        tvCancle = (TextView) searchView.findViewById(R.id.tvCanale);
        listView = (ListView) searchView.findViewById(R.id.search_listiv);
        user_no_exit = (TextView) searchView.findViewById(R.id.user_no_exit_iv);
        cear_delete = (ImageView) searchView.findViewById(R.id.cear_delete_iv);
        if (user_no_exit != null) {
            user_no_exit.setText("群组不存在");
        }
        if (ediSearch != null) {
            ediSearch.setHint("输入群名称");
        }
        // ediSearch.setHint(strHint);
        ediSearch.setFocusable(true);
        tvCancle.setOnClickListener(this);
        alphaView.setOnClickListener(this);
        ediSearch.addTextChangedListener(this);
        cear_delete.setOnClickListener(this);
        listView.setOnItemClickListener(this);
    }


    /**
     * 以下都是搜索
     */
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        handler.removeMessages(SEARCH);
        Message message = handler.obtainMessage();
        message.obj = s;
        message.what = SEARCH;
        handler.sendMessageDelayed(message, 300);


    }

    @Override
    public void afterTextChanged(Editable s) {
//        if (s.length() > 0) {
//            if (searchGroupadapter == null) {
//                listView.setVisibility(View.VISIBLE);
//                alphaView.setEnabled(false);
//                alphaView.setBackgroundColor(Color.parseColor("#F5F5F5"));
//                searchGroupadapter = new SearchGroupAdapter(this, 1, grouplist);
//                listView.setAdapter(searchGroupadapter);
//            } else {
//                searchGroupadapter.notifyDataSetChanged();
//            }
//        }
    }

    private void setAdapter(CharSequence s) {
        listView.setVisibility(View.VISIBLE);
        alphaView.setEnabled(false);
        alphaView.setBackgroundColor(Color.parseColor("#F5F5F5"));
        if (null == searchGroupadapter) {
            searchGroupadapter = new SearchGroupAdapter(this, grouplist);
            searchGroupadapter.getFilter().filter(s);
            listView.setAdapter(searchGroupadapter);
        } else {
            searchGroupadapter.getFilter().filter(s);
            searchGroupadapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(GroupsActivity.this, ChatActivity.class);
        intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_GROUP);
        intent.putExtra(EaseConstant.EXTRA_USER_ID, searchGroupadapter.getItem(position).getGroupId());
        startActivityForResult(intent, 0);
        // searchutils.dismissPopupWindow();
        // popupWidow.dismiss();
    }
}
