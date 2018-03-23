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

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.elead.activity.BaseActivity;
import com.elead.eplatform.R;
import com.elead.im.adapter.EaseContactAdapter;
import com.elead.im.db.DemoHelper;
import com.elead.im.entry.EaseUser;
import com.elead.im.widget.EaseConstant;
import com.elead.views.Sidebar;
import com.elead.views.pulltorefresh.PullToRefreshListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@SuppressLint("Registered")
public class PickContactNoCheckboxActivity extends EaseBaseActivity {

    protected EaseContactAdapter contactAdapter;
    private List<EaseUser> contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getResources().getString(R.string.select_contacts), BaseActivity.TitleType.NORMAL);
        setContentView(R.layout.em_activity_pick_contact_no_checkbox);
        PullToRefreshListView listView = (PullToRefreshListView) findViewById(R.id.list);
        Sidebar sidebar = (Sidebar) findViewById(R.id.sidebar);
        sidebar.setListView(listView.getListview());
        contactList = new ArrayList<>();
        // get contactlist
        getContactList();
        // set adapter
        contactAdapter = new EaseContactAdapter(this, R.layout.ease_row_contact, contactList);
        listView.setAdapter(contactAdapter);
        listView.getListview().setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onListItemClick(position);
            }
        });

    }

    protected void onListItemClick(int position) {
        setResult(RESULT_OK, new Intent().putExtra("username", contactAdapter.getItem(position)
                .getUsername()));
        finish();
    }

    public void back(View view) {
        finish();
    }

    private void getContactList() {
        contactList.clear();
        Map<String, EaseUser> users = DemoHelper.getInstance().getContactList();
        for (Map.Entry<String, EaseUser> entry : users.entrySet()) {
            if (!entry.getKey().equals(EaseConstant.NEW_FRIENDS_USERNAME) && !entry.getKey().equals(EaseConstant.GROUP_USERNAME) && !entry.getKey().equals(EaseConstant.CHAT_ROOM) && !entry.getKey().equals(EaseConstant.CHAT_ROBOT))
                contactList.add(entry.getValue());
        }
        // sort
        Collections.sort(contactList, new Comparator<EaseUser>() {

            @Override
            public int compare(EaseUser lhs, EaseUser rhs) {
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
    }

}
