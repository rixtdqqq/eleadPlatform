/**
 * Copyright (C) 2016 Hyphenate Inc. All rights reserved.
 * <p/>
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
package com.elead.fragment;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;

import com.elead.activity.AddressActivity;
import com.elead.adapter.GroudExpandListAdapter;
import com.elead.entity.GroupEntry;
import com.elead.eplatform.R;
import com.elead.im.activity.GroupsActivity;
import com.elead.im.activity.MyFriendActivity;
import com.elead.im.db.DemoHelper;
import com.elead.im.entry.EaseUser;
import com.elead.utils.EloadingDialog;
import com.elead.utils.PermissionUtil;
import com.elead.views.BaseFragment;
import com.elead.views.ContactItemView;
import com.elead.views.CustomExpandableListView;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMContact;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * contact list
 */
public class ContactFragment extends BaseFragment implements ExpandableListView.OnChildClickListener {

    protected LinearLayout btn_phone_people, btn_efriend, btn_mygroup;
    private boolean flags = false;
    private List<GroupEntry> groupArray;
    private List<List<String>> childArray;
    private List<String> tempArray01;
    private List<String> tempArray02;
    private List<String> tempArray03;
    private GroupEntry groupEntry;
    private GroudExpandListAdapter adapter;
    private CustomExpandableListView mExpandableListView;
    private ContactItemView frequent_contacts_rel;

    private Map<String, EaseUser> contactList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return getView(inflater, R.layout.contace_fragment, container);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setDatas();
        initView();
    }


    public void initView() {

        btn_phone_people = (LinearLayout) mView.findViewById(R.id.btn_phone_people);
        btn_efriend = (LinearLayout) mView.findViewById(R.id.btn_efriend);
        btn_mygroup = (LinearLayout) mView.findViewById(R.id.btn_mygroup);
        frequent_contacts_rel = (ContactItemView) mView.findViewById(R.id.frequent_contacts_rel);

        mExpandableListView = (CustomExpandableListView) mView.findViewById(R.id.expandableListView);
        adapter = new GroudExpandListAdapter(mContext, groupArray, childArray);
        mExpandableListView.setAdapter(adapter);
        btn_phone_people.setOnClickListener(myOnClickListener);
        btn_efriend.setOnClickListener(myOnClickListener);
        btn_mygroup.setOnClickListener(myOnClickListener);
        mExpandableListView.setOnChildClickListener(this);
        frequent_contacts_rel.setOnClickListener(myOnClickListener);

    }

    OnClickListener myOnClickListener = new OnClickListener() {


        @Override
        public void onClick(View v) {
            Intent intent = null;
            switch (v.getId()) {
                case R.id.btn_phone_people:
                    //EloadingDialog.showDailog();
                    if( PermissionUtil.requestReadConstantPermissions(mContext, 101)){
                        intent = new Intent(mContext, AddressActivity.class);
                    }
                    break;
                case R.id.btn_efriend:
                    intent = new Intent(mContext, MyFriendActivity.class);
                    break;
                case R.id.btn_mygroup:
//                    EloadingDialog.showDailog();
                    startActivity(new Intent(mContext, GroupsActivity.class));
                    break;

                //点击常用联系人
                case R.id.frequent_contacts_rel:
                    if (flags == true) {
                        frequent_contacts_rel.setArrow(mContext.getResources().getDrawable(R.drawable.right_arrow_down));

                        flags = false;
                    } else {

                        frequent_contacts_rel.setArrow(mContext.getResources().getDrawable(R.drawable.right_arrow));


                        flags = true;
                    }
                    break;


                default:
                    break;
            }
            if (null != intent) {
                startActivity(intent);
            }
        }
    };


    @Override
    public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
//        Toast.makeText(MainActivity.this,
//                childList.get(groupPosition).get(childPosition), 1).show();
        EloadingDialog.showDailog();
        return false;
    }


    private void setDatas() {
        Drawable d[] = {getResources().getDrawable(R.drawable.default_org_icon), getResources().getDrawable(R.drawable.ihuawei), getResources().getDrawable(R.drawable.default_org_icon)};
        String title[] = {"上海易立德企业管理有限公司", "Ihuawei开发团队", "向左走团队"};
        groupArray = new ArrayList<>();
        //子标题
        childArray = new ArrayList<List<String>>();
        for (int i = 0; i < d.length; i++) {
            groupEntry = new GroupEntry();
            groupEntry.setDrawable(d[i]);
            groupEntry.setGroudTitle(title[i]);
            groupArray.add(groupEntry);
        }

        tempArray01 = new ArrayList<String>();
        tempArray01.add("组织架构");
        tempArray01.add("综合事业部");
        tempArray01.add("外部联系人");

        tempArray02 = new ArrayList<String>();
        tempArray02.add("组织架构");
        tempArray02.add("移动事业部");
        tempArray02.add("外部联系人");

        tempArray03 = new ArrayList<String>();

        tempArray03.add("组织架构");
        tempArray03.add("移动事业部");
        tempArray03.add("外部联系人");


        childArray.add(tempArray01);
        childArray.add(tempArray02);
        childArray.add(tempArray03);
    }


    /**
     * 获取所有会话
     *
     * @param
     * @return +
     */
    private List<EMContact> loadConversationsWithRecentChat() {
        // 获取所有会话，包括陌生人
        contactList = DemoHelper.getInstance().getModel().getContactList();
        List<EMContact> list = new ArrayList<EMContact>();
        // List<Pair<Long, EMConversation>> sortList = new ArrayList<Pair<Long, EMConversation>>();
        //获取有聊天记录的users，不包括陌生人

        for (EaseUser user : contactList.values()) {
            EMConversation conversation = EMClient.getInstance().chatManager().getConversation(user.getUsername());
            if (conversation != null && conversation.getAllMsgCount() > 0) {
                list.add(user);
            }
        }

// 排序
        sortUserByLastChatTime(list);
        return list;
    }

    /**
     * 根据最后一条消息的时间排序
     *
     * @param
     */
    private void sortUserByLastChatTime(List<EMContact> contactList) {
        Collections.sort(contactList, new Comparator<EMContact>() {
            @Override
            public int compare(final EMContact user1, final EMContact user2) {
                EMConversation conversation1 = EMClient.getInstance().chatManager().getConversation(user1.getUsername());
                EMConversation conversation2 = EMClient.getInstance().chatManager().getConversation(user2.getUsername());

                EMMessage user2LastMessage = conversation2.getLastMessage();
                EMMessage user1LastMessage = conversation1.getLastMessage();
                if (user2LastMessage.getMsgTime() == user1LastMessage.getMsgTime()) {
                    return 0;
                } else if (user2LastMessage.getMsgTime() > user1LastMessage.getMsgTime()) {
                    return 1;
                } else {
                    return -1;
                }
            }

        });
    }
}
