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
import android.database.DataSetObserver;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.elead.activity.BaseActivity;
import com.elead.application.MyApplication;
import com.elead.eplatform.R;
import com.elead.im.adapter.SearchUserAdapter;
import com.elead.im.db.DemoHelper;
import com.elead.im.entry.EaseUser;
import com.elead.im.entry.GroupPickUser;
import com.elead.im.util.EaseUserUtils;
import com.elead.im.widget.EaseConstant;
import com.elead.utils.DensityUtil;
import com.elead.utils.StatusBarUtils;
import com.elead.views.CircularImageView;
import com.elead.views.MyScrollView;
import com.elead.views.baseadapter.CommonAdapter;
import com.elead.views.baseadapter.ViewHolder;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.finalteam.mygallery.widget.HorizontalListView;

import static com.elead.eplatform.R.id.pick_lin_my_group;

public class GroupPickContactsActivity extends BaseActivity implements View.OnClickListener {
    private static final int SEARCH = 10005;
    @BindView(R.id.horizontallistview)
    HorizontalListView horizontallistview;
    @BindView(pick_lin_my_group)
    LinearLayout pickLinMyGroup;
    @BindView(R.id.pick_lin_my_conpany)
    LinearLayout pickLinMyConpany;
    @BindView(R.id.list)
    ListView listView;
    @BindView(R.id.pick_lin_my_friend)
    LinearLayout pickLinMyFriend;
    @BindView(R.id.pick_lin_common_used)
    LinearLayout pickLinCommonUsed;
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.sure)
    TextView sure;
    @BindView(R.id.ed_search)
    EditText edSearch;
    @BindView(R.id.pick_lin_main)
    LinearLayout pickLinMain;
    @BindView(R.id.scrollview)
    MyScrollView scrollview;
    @BindView(R.id.lv_searchResult)
    ListView lvSearchResult;
    private CommonAdapter<GroupPickUser> contactAdapter;
    private List<GroupPickUser> alluserList = new ArrayList<>();
    /**
     * members already in the group
     */
    private List<GroupPickUser> existMembers = new ArrayList<>();
    private CommonAdapter<GroupPickUser> horizontallistviewAdapter;
    private int itemWidth;
    private SearchUserAdapter easeContactAdapter;
    private String addUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        addUser = getIntent().getStringExtra(EaseUser.class.getSimpleName());
        StatusBarUtils.setWindowStatusBarColor(this, getResources().getColor(R.color.qianlv));
        setContentView(R.layout.em_activity_group_pick_contacts);
        ButterKnife.bind(this);
        String groupId = getIntent().getStringExtra("groupId");
        if (groupId == null) {
            if (!TextUtils.isEmpty(addUser)) {
                GroupPickUser groupPickUser = new GroupPickUser(EaseUserUtils.getUserInfo(addUser));
                groupPickUser.isChecked = false;
                existMembers.add(groupPickUser);
            }
        } else {
            EMGroup group = EMClient.getInstance().groupManager().getGroup(groupId);

            for (String s : group.getMembers()) {
                GroupPickUser groupPickUser = new GroupPickUser(s);
                groupPickUser.isChecked = false;
                existMembers.add(groupPickUser);
            }
        }

        itemWidth = (int) (MyApplication.size[0] / 8.33f);
        horizontallistviewAdapter = new CommonAdapter<GroupPickUser>(mContext, existMembers, R.layout.exist_item) {
            @Override
            public void convert(ViewHolder helper, GroupPickUser item) {
                CircularImageView circleView = helper.getView(R.id.circleView);
                RelativeLayout relat = helper.getView(R.id.relat);
                relat.getLayoutParams().width = itemWidth;
                circleView.getLayoutParams().height = (int) (MyApplication.size[0] / 11.35f);
                circleView.getLayoutParams().width = (int) (MyApplication.size[0] / 11.35f);
                EaseUserUtils.setUserAvatar(mContext, item.getUsername(), circleView);
            }
        };
        horizontallistview.setAdapter(horizontallistviewAdapter);
        horizontallistview.setTranslationY(0f);
        horizontallistviewAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                if (horizontallistviewAdapter.getCount() >= 6) {
                    edSearch.getLayoutParams().width = (int) (MyApplication.size[0] / 4f);
                    horizontallistview.scrollTo(itemWidth * horizontallistviewAdapter.getCount());
                } else {
                    edSearch.getLayoutParams().width = MyApplication.size[0] - itemWidth * horizontallistviewAdapter.getCount() - DensityUtil.dip2px(mContext, 10);
                }
            }
        });
        if (horizontallistviewAdapter.getCount() >= 6) {
            edSearch.getLayoutParams().width = (int) (MyApplication.size[0] / 4f);
        } else {
            edSearch.getLayoutParams().width = MyApplication.size[0] - itemWidth * horizontallistviewAdapter.getCount() - DensityUtil.dip2px(mContext, 10);
        }
        edSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                handler.removeMessages(SEARCH);
                Message message = handler.obtainMessage();
                message.obj = s;
                message.what = SEARCH;
                handler.sendMessageDelayed(message, 200);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initSearchResultList(CharSequence s) {
        if (null == easeContactAdapter) {
            List<GroupPickUser> users = new ArrayList<>();
            users.addAll(getUsers());
            easeContactAdapter = new SearchUserAdapter(mContext, 0, users, horizontallistviewAdapter, existMembers, sure);
            lvSearchResult.setAdapter(easeContactAdapter);
        }
        easeContactAdapter.getFilter().filter(s);
    }


    /**
     * save selected members
     */
    public void save(View v) {
        setResult(RESULT_OK, new Intent().putExtra("newmembers", getToBeAddMembers().toArray(new String[0])));
        finish();
    }

    /**
     * get selected members
     *
     * @return
     */
    private List<String> getToBeAddMembers() {
        List<String> members = new ArrayList<>();
        if (null != contactAdapter) {
            int length = contactAdapter.getCount();
            for (int i = 0; i < length; i++) {
                GroupPickUser item = contactAdapter.getItem(i);
                if (item.isChecked && item.isEnable) {
                    members.add(item.getUsername());
                }
            }
        }
        return members;
    }

    @OnClick({R.id.back, R.id.sure, pick_lin_my_group, R.id.pick_lin_my_conpany, R.id.pick_lin_my_friend, R.id.pick_lin_common_used})
    public void onClick(View view) {
        pickLinMain.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(edSearch.getWindowToken(), 0);
        switch (view.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.sure:
                save(view);
                break;
            case pick_lin_my_group:
                break;
            case R.id.pick_lin_my_conpany:
                break;
            case R.id.pick_lin_my_friend:
                Log.d("onClick", "onClick");
                if (listView.getVisibility() == View.GONE) {
                    if (alluserList.size() == 0) {
                        alluserList.addAll(getUsers());
                        contactAdapter = new CommonAdapter<GroupPickUser>(mContext, alluserList, R.layout.em_row_contact_with_checkbox) {
                            @Override
                            public void convert(ViewHolder helper, final GroupPickUser item) {

                                final String username = item.getUsername();

                                final CheckBox checkBox = helper.getView(R.id.checkbox);
                                CircularImageView avatarView = helper.getView(R.id.avatar);
                                TextView nameView = helper.getView(R.id.name);
                                EaseUserUtils.setUserAvatar(mContext, username, avatarView);
                                EaseUserUtils.setUserNick(username, nameView);


                                if (checkBox != null) {
                                    checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
                                        @Override
                                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                            if (!item.isEnable) return;
                                            if (existMembers.contains(item)) {
                                                item.isChecked = false;
                                                checkBox.setChecked(false);
                                                existMembers.remove(item);
                                            } else {
                                                item.isChecked = true;
                                                existMembers.add(item);
                                            }
                                            horizontallistviewAdapter.notifyDataSetChanged();
                                            int count = 0;
                                            for (GroupPickUser b : existMembers) {
                                                if (b.isChecked && b.isEnable) {
                                                    count++;
                                                }
                                            }
                                            if (count > 0) {
                                                sure.setText("确定(" + count + ")");
                                            } else
                                                sure.setText("确定");

                                        }
                                    });
                                    if (!item.isEnable) {
                                        checkBox.setEnabled(false);
                                        item.isChecked = true;
                                    } else {
                                        checkBox.setChecked(item.isChecked);
                                        checkBox.setEnabled(true);
                                    }
                                }

                            }
                        };
                        listView.setAdapter(contactAdapter);

                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkbox);
                                checkBox.toggle();
                            }
                        });
                    }
                    listView.setVisibility(View.VISIBLE);
                } else {
                    listView.setVisibility(View.GONE);
                }
                break;
            case R.id.pick_lin_common_used:
                break;
            case R.id.pick_lin_main:
                break;
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SEARCH:
                    CharSequence s = (CharSequence) msg.obj;
                    if (TextUtils.isEmpty(s)) {
                        scrollview.setVisibility(View.VISIBLE);
                        lvSearchResult.setVisibility(View.GONE);
                    } else {
                        scrollview.setVisibility(View.GONE);
                        lvSearchResult.setVisibility(View.VISIBLE);
                        initSearchResultList(s);
                    }
                    break;
            }
        }
    };

    @Override
    public void onBackPressed() {
        if (edSearch.isFocused()) {
            edSearch.clearFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(edSearch.getWindowToken(), 0);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public void setOnClick() {
        super.setOnClick();
        setResult(RESULT_OK, new Intent().putExtra("newmembers", getToBeAddMembers().toArray(new String[0])));
        finish();
    }

    public List<GroupPickUser> getUsers() {
        List<GroupPickUser> lists = new ArrayList<>();
        for (EaseUser user : DemoHelper.getInstance().getContactMap().values()) {
            if (!user.getUsername().equals(EaseConstant.NEW_FRIENDS_USERNAME) & !user.getUsername().equals(EaseConstant.GROUP_USERNAME) & !user.getUsername().equals(EaseConstant.CHAT_ROOM) & !user.getUsername().equals(EaseConstant.CHAT_ROBOT)) {
                GroupPickUser groupPickUser = new GroupPickUser(user);
                if (existMembers.contains(groupPickUser))
                    groupPickUser.isEnable = false;
                lists.add(groupPickUser);
            }
        }
        // sort the list
        Collections.sort(alluserList, new Comparator<GroupPickUser>() {

            @Override
            public int compare(GroupPickUser lhs, GroupPickUser rhs) {
                if (lhs.getInitialLetter().equals(rhs.getInitialLetter())) {
                    return lhs.getNick().compareTo(rhs.getNick());
                } else {
                    if ("#".equals(lhs.getInitialLetter())) {
                        return 1;
                    } else if ("#".equals(rhs.getInitialLetter())) {
                        return -1;
                    }
                    return lhs.getInitialLetter().compareTo(rhs.getInitialLetter());
                }
            }
        });
        return lists;
    }
}
