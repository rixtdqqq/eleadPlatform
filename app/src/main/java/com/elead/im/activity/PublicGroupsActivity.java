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

package com.elead.im.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.elead.activity.BaseActivity;
import com.elead.eplatform.R;
import com.elead.utils.ToastUtil;
import com.elead.views.pulltorefresh.PullToRefreshListView;
import com.elead.views.pulltorefresh.PullToRefreshView;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMCursorResult;
import com.hyphenate.chat.EMGroupInfo;
import com.hyphenate.exceptions.HyphenateException;

import java.util.ArrayList;
import java.util.List;

public class PublicGroupsActivity extends BaseActivity {
    private PullToRefreshListView listView;
    private GroupsAdapter adapter;

    private List<EMGroupInfo> groupsList;
    private boolean isLoading;
    private boolean isFirstLoading = true;
    private boolean hasMoreData = true;
    private String cursor;
    private final int pagesize = 20;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.em_activity_public_groups);
        setTitle(getResources().getString(R.string.Open_group_chat), TitleType.NORMAL, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search();
            }
        }, getResources().getString(R.string.search));
        listView = (PullToRefreshListView) findViewById(R.id.list);
        groupsList = new ArrayList<EMGroupInfo>();
        loadAndShowData();

        listView.getListview().setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(PublicGroupsActivity.this, GroupSimpleDetailActivity.class).
                        putExtra("groupinfo", adapter.getItem(position)));
            }
        });
        listView.setOnLoadMoreListener(new PullToRefreshView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                loadAndShowData();
            }
        });

    }

    public void search() {
        startActivity(new Intent(this, PublicGroupsSeachActivity.class));
    }

    private void loadAndShowData() {
        new Thread(new Runnable() {

            public void run() {
                try {
                    isLoading = true;
                    final EMCursorResult<EMGroupInfo> result = EMClient.getInstance().groupManager().getPublicGroupsFromServer(pagesize, cursor);
                    final List<EMGroupInfo> returnGroups = result.getData();
                    runOnUiThread(new Runnable() {

                        public void run() {
                            groupsList.addAll(returnGroups);
                            if (returnGroups.size() != 0) {
                                cursor = result.getCursor();
                            }
                            if (isFirstLoading) {
                                // pb.setVisibility(View.INVISIBLE);
                                isFirstLoading = false;
                                adapter = new GroupsAdapter(PublicGroupsActivity.this, 1, groupsList);
                                listView.setAdapter(adapter);
                            } else {
                                if (returnGroups.size() < pagesize) {
                                    hasMoreData = false;
                                }
                                adapter.notifyDataSetChanged();
                                listView.onLoadMoreComplete();
                            }
                            isLoading = false;
                        }
                    });
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        public void run() {
                            isLoading = false;
                            ToastUtil.showToast(PublicGroupsActivity.this, "load failed, please check your network or try it later", 0).show();
                        }
                    });
                }
            }
        }).start();
    }

    /**
     * adapter
     */
    private class GroupsAdapter extends ArrayAdapter<EMGroupInfo> {

        private LayoutInflater inflater;

        public GroupsAdapter(Context context, int res, List<EMGroupInfo> groups) {
            super(context, res, groups);
            this.inflater = LayoutInflater.from(context);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.em_row_group, null);
            }

            ((TextView) convertView.findViewById(R.id.name)).setText(getItem(position).getGroupName());

            return convertView;
        }
    }

    @Override
    public void setOnClick() {
        super.setOnClick();
        startActivity(new Intent(this, PublicGroupsSeachActivity.class));
    }
}
