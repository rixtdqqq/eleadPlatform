package com.elead.im.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.elead.activity.BaseActivity;
import com.elead.eplatform.R;
import com.elead.im.db.DemoHelper;
import com.elead.im.entry.EaseUser;
import com.elead.utils.ToastUtil;
import com.elead.views.CircularImageView;
import com.elead.views.pulltorefresh.PullToRefreshListView;
import com.hyphenate.EMValueCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import java.util.Collections;
import java.util.List;

public class GroupBlacklistActivity extends BaseActivity {
    private PullToRefreshListView listView;
    private BlacklistAdapter adapter;
    private String groupId;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.em_activity_group_blacklist);
        setTitle(getResources().getString(R.string.blacklist), TitleType.NORMAL);
        listView = (PullToRefreshListView) findViewById(R.id.list);

        groupId = getIntent().getStringExtra("groupId");
        // register context menu
        registerForContextMenu(listView.getListview());
        final String st1 = getResources().getString(R.string.get_failed_please_check);
        new Thread(new Runnable() {

            public void run() {
                try {
                    List<String> blockedList = EMClient.getInstance().groupManager().getBlockedUsers(groupId);
                    if (blockedList != null) {
                        Collections.sort(blockedList);
                        adapter = new BlacklistAdapter(GroupBlacklistActivity.this, 1, blockedList);
                        runOnUiThread(new Runnable() {
                            public void run() {
                                listView.setAdapter(adapter);
                            }
                        });
                    }
                } catch (HyphenateException e) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            ToastUtil.showToast(getApplicationContext(), st1, 0).show();
                        }
                    });
                }
            }
        }).start();

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.em_remove_from_blacklist, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.remove) {
            final String tobeRemoveUser = adapter.getItem(((AdapterContextMenuInfo) item.getMenuInfo()).position);
            // move out of blacklist
            removeOutBlacklist(tobeRemoveUser);
            return true;
        }
        return super.onContextItemSelected(item);
    }

    /**
     * move out of blacklist
     *
     * @param tobeRemoveUser
     */
    void removeOutBlacklist(final String tobeRemoveUser) {
        final String st2 = getResources().getString(R.string.Removed_from_the_failure);
        try {
            EMClient.getInstance().groupManager().unblockUser(groupId, tobeRemoveUser);
            adapter.remove(tobeRemoveUser);
        } catch (HyphenateException e) {
            e.printStackTrace();
            runOnUiThread(new Runnable() {
                public void run() {
                    ToastUtil.showToast(getApplicationContext(), st2, 0).show();
                }
            });
        }
    }

    /**
     * adapter
     */
    private class BlacklistAdapter extends ArrayAdapter<String> {

        public BlacklistAdapter(Context context, int textViewResourceId, List<String> objects) {
            super(context, textViewResourceId, objects);
        }

        class ViewHolder {
            TextView name;
            CircularImageView avatar;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = View.inflate(getContext(), R.layout.ease_row_contact, null);
                holder.name = (TextView) convertView.findViewById(R.id.name);
                holder.avatar = (CircularImageView) convertView.findViewById(R.id.avatar);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.name.setText("佚名");
            holder.avatar.setText("佚名");
            holder.avatar.setBackgroundColor("佚名");

            DemoHelper.getInstance().getUserProfileManager().asyncGetUserInfo(getItem(position), new EMValueCallBack<EaseUser>() {
                @Override
                public void onSuccess(final EaseUser easeUser) {
                    mContext.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            holder.name.setText(easeUser.getNickname());
                            holder.avatar.setText(easeUser.getNickname());
                            holder.avatar.setBackgroundColor(easeUser.getUsername());
                        }
                    });
                }

                @Override
                public void onError(int i, String s) {

                }
            });

            return convertView;
        }

    }

}
