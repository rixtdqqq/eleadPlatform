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
package com.elead.im.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.elead.eplatform.R;
import com.elead.im.activity.ChatActivity;
import com.elead.im.db.DemoHelper;
import com.elead.im.db.DemoHelper.DataSyncListener;
import com.elead.im.db.InviteMessgeDao;
import com.elead.im.entry.EaseUser;
import com.elead.im.widget.EaseConstant;
import com.elead.views.ContactItemView;
import com.hyphenate.util.EMLog;

import java.util.Hashtable;
import java.util.Map;

/**
 * contact list
 *
 */
public class ContactListFragment extends EaseContactListFragment {

    private static final String TAG = ContactListFragment.class.getSimpleName();
    private ContactSyncListener contactSyncListener;
    private BlackListSyncListener blackListSyncListener;
    private ContactInfoSyncListener contactInfoSyncListener;
    private View loadingView;
    private ContactItemView applicationItem;
    private InviteMessgeDao inviteMessgeDao;

    @SuppressLint("InflateParams")
    @Override
    protected void initView() {
        super.initView();
//        @SuppressLint("InflateParams") View headerView = LayoutInflater.from(getActivity()).inflate(R.layout.em_contacts_header, null);
//        HeaderItemClickListener clickListener = new HeaderItemClickListener();
//        applicationItem = (ContactItemView) headerView.findViewById(R.id.application_item);
//        applicationItem.setOnClickListener(clickListener);
//        headerView.findViewById(R.id.group_item).setOnClickListener(clickListener);
//        headerView.findViewById(R.id.chat_room_item).setOnClickListener(clickListener);
//        headerView.findViewById(R.id.robot_item).setOnClickListener(clickListener);
//        listView.addHeaderView(headerView);
        //add loading view
        loadingView = LayoutInflater.from(getActivity()).inflate(R.layout.em_layout_loading_data, null);
        contentContainer.addView(loadingView);

        registerForContextMenu(listView);
    }

    @Override
    public void refresh() {
        Map<String, EaseUser> m = DemoHelper.getInstance().getContactList();
        if (m instanceof Hashtable<?, ?>) {
            //noinspection unchecked
            m = (Map<String, EaseUser>) ((Hashtable<String, EaseUser>) m).clone();
        }
        setContactsMap(m);
        super.refresh();
        if (inviteMessgeDao == null) {
            inviteMessgeDao = new InviteMessgeDao(getActivity());
        }
        if (inviteMessgeDao.getUnreadMessagesCount() > 0) {
            applicationItem.showUnreadMsgView();
        } else {
            applicationItem.hideUnreadMsgView();
        }
    }


    @SuppressWarnings("unchecked")
    @Override
    protected void setUpView() {
        //设置联系人数据
        Map<String, EaseUser> m = DemoHelper.getInstance().getContactList();
        if (m instanceof Hashtable<?, ?>) {
            m = (Map<String, EaseUser>) ((Hashtable<String, EaseUser>) m).clone();
        }
        setContactsMap(m);
        super.setUpView();
        listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EaseUser user = (EaseUser) listView.getItemAtPosition(position);
                if (user != null) {
                    String username = user.getUsername();
                    // demo中直接进入聊天页面，实际一般是进入用户详情页
                    startActivity(new Intent(getActivity(), ChatActivity.class).putExtra(EaseConstant.EXTRA_USER_ID, username));
                }
            }
        });


        contactSyncListener = new ContactSyncListener();
        DemoHelper.getInstance().addSyncContactListener(contactSyncListener);

        blackListSyncListener = new BlackListSyncListener();
        DemoHelper.getInstance().addSyncBlackListListener(blackListSyncListener);

        contactInfoSyncListener = new ContactInfoSyncListener();
        DemoHelper.getInstance().getUserProfileManager().addSyncContactInfoListener(contactInfoSyncListener);

        if (DemoHelper.getInstance().isContactsSyncedWithServer()) {
            loadingView.setVisibility(View.GONE);
        } else if (DemoHelper.getInstance().isSyncingContactsWithServer()) {
            loadingView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (contactSyncListener != null) {
            DemoHelper.getInstance().removeSyncContactListener(contactSyncListener);
            contactSyncListener = null;
        }

        if (blackListSyncListener != null) {
            DemoHelper.getInstance().removeSyncBlackListListener(blackListSyncListener);
        }

        if (contactInfoSyncListener != null) {
            DemoHelper.getInstance().getUserProfileManager().removeSyncContactInfoListener(contactInfoSyncListener);
        }
    }

//
//	protected class HeaderItemClickListener implements OnClickListener{
//
//        @Override
//        public void onClick(View v) {
//            switch (v.getId()) {
//            case R.id.application_item:
//                // 进入申请与通知页面
//                break;
//            case R.id.group_item:
//                // 进入群聊列表页面
//                startActivity(new Intent(getActivity(), GroupsActivity.class));
//                break;
//            case R.id.chat_room_item:
//                //进入聊天室列表页面
//                startActivity(new Intent(getActivity(), PublicChatRoomsActivity.class));
//                break;
//            case R.id.robot_item:
//                //进入Robot列表页面
//                startActivity(new Intent(getActivity(), RobotsActivity.class));
//                break;
//
//            default:
//                break;
//            }
//        }
//
//	}
//
//
//	@Override
//	public boolean onContextItemSelected(MenuItem item) {
//		if (item.getItemId() == R.id.delete_contact) {
//			try {
//                // delete contact
//                deleteContact(toBeProcessUser);
//                // remove invitation message
//                InviteMessgeDao dao = new InviteMessgeDao(getActivity());
//                dao.deleteMessage(toBeProcessUser.getUsername());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//			return true;
//		}else if(item.getItemId() == R.id.add_to_blacklist){
//			moveToBlacklist(toBeProcessUsername);
//			return true;
//		}
//		return super.onContextItemSelected(item);
//	}


    class ContactSyncListener implements DataSyncListener {
        @Override
        public void onSyncComplete(final boolean success) {
            EMLog.d(TAG, "on contact list sync success:" + success);
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    getActivity().runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            if (success) {
                                loadingView.setVisibility(View.GONE);
                                refresh();
                            } else {
                                String s1 = getResources().getString(R.string.get_failed_please_check);
                                Toast.makeText(getActivity(), s1, Toast.LENGTH_LONG).show();
                                loadingView.setVisibility(View.GONE);
                            }
                        }

                    });
                }
            });
        }
    }

    class BlackListSyncListener implements DataSyncListener {

        @Override
        public void onSyncComplete(boolean success) {
            getActivity().runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    refresh();
                }
            });
        }

    }

    public class ContactInfoSyncListener implements DataSyncListener {

        @Override
        public void onSyncComplete(final boolean success) {
            EMLog.d(TAG, "on contactinfo list sync success:" + success);
            getActivity().runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    loadingView.setVisibility(View.GONE);
                    if (success) {
                        refresh();
                    }
                }
            });
        }

    }

}
