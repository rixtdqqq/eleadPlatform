package com.elead.im.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.elead.activity.BaseActivity;
import com.elead.eplatform.R;
import com.elead.im.db.DemoHelper;
import com.elead.im.db.InviteMessgeDao;
import com.elead.im.db.UserDao;
import com.elead.im.entry.EaseUser;
import com.elead.im.util.EaseCommonUtils;
import com.elead.im.view.EaseContactList;
import com.elead.views.AlertDialog;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/1/3 0003.
 */

public class MyFriendActivity extends BaseActivity {

    protected List<EaseUser> easeUsers;
    protected ListView listView;
    @BindView(R.id.contact_list)
    EaseContactList contactListLayout;

    private Map<String, EaseUser> contactsMap;
    private ContactSyncListener contactSyncListener;
    private BlackListSyncListener blackListSyncListener;
    private InviteMessgeDao inviteMessgeDao;
    private InputMethodManager inputMethodManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myfriend);
        ButterKnife.bind(this);
        setTitle(getResources().getString(R.string.my_friends), TitleType.NORMAL);
        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        listView = contactListLayout.getListView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Map<String, EaseUser> m = DemoHelper.getInstance().getContactList();
        if (m instanceof Hashtable<?, ?>) {
            //noinspection unchecked
            m = (Map<String, EaseUser>) ((Hashtable<String, EaseUser>) m).clone();
        }
        setContactsMap(m);
        initDate();
        setUpView();
    }

    protected void initDate() {
        EMClient.getInstance().addConnectionListener(connectionListener);

        easeUsers = new ArrayList<>();
        getContactList();
        //init list
        contactListLayout.init(easeUsers);

        if (listItemClickListener != null) {
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    EaseUser user = (EaseUser) listView.getItemAtPosition(position);
                    listItemClickListener.onListItemClicked(user);
                }
            });
        }
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final EaseUser easeUser = easeUsers.get(position);
                new AlertDialog(mContext).builder().setTitle("删除联系人")
                        .setMsg(String.format(getString(R.string.sure_delete_friend), easeUser.getNick())).
                        setPositiveButton(getResources().getString(R.string.delete),
                                mContext.getResources().getColor(R.color.elead_f25e5e) ,new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                deleteContact(easeUser);
                            }
                        }).setNegativeButton(getResources().getString(R.string.cancel), null).show();
                return true;
            }
        });

    }

    protected void hideSoftKeyboard() {
        if (mContext.getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (mContext.getCurrentFocus() != null)
                inputMethodManager.hideSoftInputFromWindow(mContext.getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * get contact list and sort, will filter out users in blacklist
     */
    protected void getContactList() {
        easeUsers.clear();
        if (contactsMap == null) {
            return;
        }
        synchronized (this.contactsMap) {
            Iterator<Map.Entry<String, EaseUser>> iterator = contactsMap.entrySet().iterator();
            List<String> blackList = EMClient.getInstance().contactManager().getBlackListUsernames();
            while (iterator.hasNext()) {
                Map.Entry<String, EaseUser> entry = iterator.next();
                // to make it compatible with data in previous version, you can remove this check if this is new app
                if (!entry.getKey().equals("item_new_friends")
                        && !entry.getKey().equals("item_groups")
                        && !entry.getKey().equals("item_chatroom")
                        && !entry.getKey().equals("item_robots")) {
                    if (!blackList.contains(entry.getKey())) {
                        //filter out users in blacklist
                        EaseUser user = entry.getValue();
                        EaseCommonUtils.setUserInitialLetter(user);
                        easeUsers.add(user);
                    }
                }
            }
        }

        // sorting
        Collections.sort(easeUsers, new Comparator<EaseUser>() {

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


    public void refresh() {
        Map<String, EaseUser> m = DemoHelper.getInstance().getContactList();
        if (m instanceof Hashtable<?, ?>) {
            //noinspection unchecked
            m = (Map<String, EaseUser>) ((Hashtable<String, EaseUser>) m).clone();
        }
        setContactsMap(m);
        if (inviteMessgeDao == null) {
            inviteMessgeDao = new InviteMessgeDao(this);
        }
    }


    protected void setUpView() {
        //设置联系人数据
        Map<String, EaseUser> m = DemoHelper.getInstance().getContactList();
        if (m instanceof Hashtable<?, ?>) {
            m = (Map<String, EaseUser>) ((Hashtable<String, EaseUser>) m).clone();
        }
        setContactsMap(m);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EaseUser user = (EaseUser) listView.getItemAtPosition(position);
                if (user != null) {
                    // demo中直接进入聊天页面，实际一般是进入用户详情页
                    startActivity(new Intent(mContext, UserInfoDetailActivity.class).putExtra(UserInfoDetailActivity.TAG, user.getUsername()));
                }
            }
        });

        contactSyncListener = new ContactSyncListener();
        DemoHelper.getInstance().addSyncContactListener(contactSyncListener);

        blackListSyncListener = new BlackListSyncListener();
        DemoHelper.getInstance().addSyncBlackListListener(blackListSyncListener);
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
//                startActivity(new Intent(mContext, GroupsActivity.class));
//                break;
//            case R.id.chat_room_item:
//                //进入聊天室列表页面
//                startActivity(new Intent(mContext, PublicChatRoomsActivity.class));
//                break;
//            case R.id.robot_item:
//                //进入Robot列表页面
//                startActivity(new Intent(mContext, RobotsActivity.class));
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
//                InviteMessgeDao dao = new InviteMessgeDao(mContext);
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


    /**
     * delete contact
     */
    public void deleteContact(final EaseUser tobeDeleteUser) {
        String st1 = getResources().getString(R.string.deleting);
        final String st2 = getResources().getString(R.string.Delete_failed);
        final ProgressDialog pd = new ProgressDialog(mContext);
        pd.setMessage(st1);
        pd.setCanceledOnTouchOutside(false);
        pd.show();
        new Thread(new Runnable() {
            public void run() {
                try {
                    EMClient.getInstance().contactManager().deleteContact(tobeDeleteUser.getUsername());
                    // remove user from memory and database
                    UserDao dao = new UserDao(mContext);
                    dao.deleteContact(tobeDeleteUser.getUsername());
                    DemoHelper.getInstance().getContactList().remove(tobeDeleteUser.getUsername());
                    mContext.runOnUiThread(new Runnable() {
                        public void run() {
                            pd.dismiss();
                            easeUsers.remove(tobeDeleteUser);
                            contactListLayout.refresh();

                        }
                    });
                } catch (final Exception e) {
                    mContext.runOnUiThread(new Runnable() {
                        public void run() {
                            pd.dismiss();
                            Toast.makeText(mContext, st2 + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });

                }

            }
        }).start();

    }

    class ContactSyncListener implements DemoHelper.DataSyncListener {
        @Override
        public void onSyncComplete(final boolean success) {
            mContext.runOnUiThread(new Runnable() {
                public void run() {
                    mContext.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            if (success) {
                                refresh();
                            } else {
                                String s1 = getResources().getString(R.string.get_failed_please_check);
                                Toast.makeText(mContext, s1, Toast.LENGTH_LONG).show();
                            }
                        }

                    });
                }
            });
        }
    }

    class BlackListSyncListener implements DemoHelper.DataSyncListener {

        @Override
        public void onSyncComplete(boolean success) {
            mContext.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    refresh();
                }
            });
        }

    }

    class ContactInfoSyncListener implements DemoHelper.DataSyncListener {

        @Override
        public void onSyncComplete(final boolean success) {
            mContext.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    if (success) {
                        refresh();
                    }
                }
            });
        }

    }

    private boolean isConflict;
    protected EMConnectionListener connectionListener = new EMConnectionListener() {

        @Override
        public void onDisconnected(int error) {
            if (error == EMError.USER_REMOVED || error == EMError.USER_LOGIN_ANOTHER_DEVICE || error == EMError.SERVER_SERVICE_RESTRICTED) {
                isConflict = true;
            } else {
                mContext.runOnUiThread(new Runnable() {
                    public void run() {
                        onConnectionDisconnected();
                    }

                });
            }
        }

        @Override
        public void onConnected() {
            mContext.runOnUiThread(new Runnable() {
                public void run() {
                    onConnectionConnected();
                }

            });
        }
    };
    private EaseContactListItemClickListener listItemClickListener;


    protected void onConnectionDisconnected() {

    }

    protected void onConnectionConnected() {

    }

    /**
     * set contacts map, key is the hyphenate id
     *
     * @param contactsMap
     */
    public void setContactsMap(Map<String, EaseUser> contactsMap) {
        this.contactsMap = contactsMap;
    }

    public interface EaseContactListItemClickListener {
        /**
         * on click event for item in contact list
         *
         * @param user --the user of item
         */
        void onListItemClicked(EaseUser user);
    }

    /**
     * set contact list item click listener
     *
     * @param listItemClickListener
     */
    public void setContactListItemClickListener(EaseContactListItemClickListener listItemClickListener) {
        this.listItemClickListener = listItemClickListener;
    }
}
