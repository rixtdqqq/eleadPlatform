package com.elead.im.activity;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.elead.activity.BaseActivity;
import com.elead.application.MyApplication;
import com.elead.eplatform.R;
import com.elead.im.db.InviteMessgeDao;
import com.elead.im.entry.InviteMessage;
import com.elead.im.util.EaseUserUtils;
import com.elead.views.AlertDialog;
import com.elead.views.CircularImageView;
import com.elead.views.baseadapter.CommonAdapter;
import com.elead.views.baseadapter.ViewHolder;
import com.hyphenate.chat.EMClient;

import java.util.List;

/**
 * @desc 邀请界面
 * @auth Created by mujun.xu on 2016/12/8 0008.
 */

public class InvitationFriendActivity extends BaseActivity {

    private String TAG = "InvitationFriendActivity";
    private ListView new_fried_list;
    private InviteMessgeDao messgeDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ease_invitation_friend);
        initView();
        loadData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setTitle(getResources().getString(R.string.ease_invite), TitleType.NORMAL);
    }

    private void initView() {
        new_fried_list = (ListView) findViewById(R.id.new_fried_list);
    }

    private void loadData() {
        messgeDao = new InviteMessgeDao(mContext);

        final List<InviteMessage> msgs = messgeDao.getMessagesList();
        messgeDao.saveUnreadMessageCount(0);
        new_fried_list.setAdapter(new CommonAdapter<InviteMessage>(MyApplication.mContext, msgs, R.layout.em_row_invite_msg) {
            @Override
            public void convert(ViewHolder helper, final InviteMessage msg) {
                helper.getConvertView().setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        new AlertDialog(InvitationFriendActivity.this).builder().setTitle("提示").setMsg("确定删除?").setPositiveButton(getResources().getString(R.string.sure_of), getResources().getColor(R.color.fontcolors8), new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                messgeDao.deleteMessage(msg.getFrom());
                                msgs.remove(msg);
                                notifyDataSetChanged();
                            }
                        }).setNegativeButton(getResources().getString(R.string.cancel),null).show();
                        return true;
                    }
                });
                String str1 = mContext.getResources().getString(R.string.Has_agreed_to_your_friend_request);
                String str2 = mContext.getResources().getString(R.string.agree);

                String str3 = mContext.getResources().getString(R.string.Request_to_add_you_as_a_friend);
                String str4 = mContext.getResources().getString(R.string.Apply_to_the_group_of);
                String str5 = mContext.getResources().getString(R.string.Has_agreed_to);
                String str6 = mContext.getResources().getString(R.string.Has_refused_to);

                String str7 = mContext.getResources().getString(R.string.refuse);
                String str8 = mContext.getResources().getString(R.string.invite_join_group);
                String str9 = mContext.getResources().getString(R.string.accept_join_group);
                String str10 = mContext.getResources().getString(R.string.refuse_join_group);

                CircularImageView avator = helper.getView(R.id.avatar);
                TextView reason = helper.getView(R.id.message);
                TextView name = helper.getView(R.id.name);
                final Button agree = helper.getView(R.id.agree);
                final Button status = helper.getView(R.id.user_state);

                if (msg != null) {
                    agree.setVisibility(View.GONE);
                    reason.setText(msg.getReason());
                    name.setText(msg.getGroupName());
                    final String userName = TextUtils.isEmpty(msg.getGroupInviter()) ? msg.getFrom() :
                            msg.getGroupInviter();
                    if (!TextUtils.isEmpty(userName)) {
                        EaseUserUtils.setUserAvatar(mContext, userName, avator);
                        avator.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(mContext, UserInfoDetailActivity.class).putExtra(UserInfoDetailActivity.TAG, userName));
                            }
                        });
                    }
                    // time.setText(DateUtils.getTimestampString(new
                    // Date(msg.getTime())));
                    if (msg.getStatus() == InviteMessage.InviteMesageStatus.BEAGREED) {
                        String userName1 = msg.getFrom();
                        status.setVisibility(View.GONE);
                        name.setVisibility(View.GONE);
                        agree.setVisibility(View.GONE);
                        EaseUserUtils.setUserAvatar(mContext, userName1, avator);
                        reason.setText(EaseUserUtils.getNick(userName1) + str1);
                    } else if (msg.getStatus() == InviteMessage.InviteMesageStatus.BEINVITEED || msg.getStatus() == InviteMessage.InviteMesageStatus.BEAPPLYED ||
                            msg.getStatus() == InviteMessage.InviteMesageStatus.GROUPINVITATION) {
                        agree.setVisibility(View.VISIBLE);
                        agree.setEnabled(true);
                        agree.setText(str2);
                        name.setText(EaseUserUtils.getNick(userName));
                        status.setVisibility(View.VISIBLE);
                        status.setEnabled(true);
                        status.setText(str7);
                        if (msg.getStatus() == InviteMessage.InviteMesageStatus.BEINVITEED) {
                            if (msg.getReason() == null) {
                                // use default text
                                reason.setText(str3);
                            }
                        } else if (msg.getStatus() == InviteMessage.InviteMesageStatus.BEAPPLYED) { //application to join group
                            if (TextUtils.isEmpty(msg.getReason())) {
                                reason.setText(str4 + msg.getGroupName());
                            }
                        } else if (msg.getStatus() == InviteMessage.InviteMesageStatus.GROUPINVITATION) {
                            if (TextUtils.isEmpty(msg.getReason())) {
                                reason.setText(str8 + msg.getGroupName());
                            }
                        }

                        // set click listener
                        agree.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // accept invitation
                                acceptInvitation(agree, status, msg);
                            }
                        });
                        status.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // decline invitation
                                refuseInvitation(agree, status, msg);
                            }
                        });
                    } else if (msg.getStatus() == InviteMessage.InviteMesageStatus.AGREED) {
                        status.setText(str5);
                        status.setEnabled(false);
                    } else if (msg.getStatus() == InviteMessage.InviteMesageStatus.REFUSED) {
                        status.setText(str6);
                        status.setEnabled(false);
                    } else if (msg.getStatus() == InviteMessage.InviteMesageStatus.GROUPINVITATION_ACCEPTED) {
                        String nick = EaseUserUtils.getNick(msg.getGroupInviter());
                        String str = nick + str9 + msg.getGroupName();
                        status.setVisibility(View.GONE);
                        reason.setVisibility(View.VISIBLE);
                        name.setVisibility(View.GONE);
                        reason.setText(str);
                    } else if (msg.getStatus() == InviteMessage.InviteMesageStatus.GROUPINVITATION_DECLINED) {
                        String str = EaseUserUtils.getNick(msg.getGroupInviter()) + str10 + msg.getGroupName();
                        status.setText(str);
                        status.setEnabled(false);
                    }
                }
            }
        });
    }

    private void acceptInvitation(final Button buttonAgree, final Button buttonRefuse, final InviteMessage msg) {
        final ProgressDialog pd = new ProgressDialog(mContext);
        String str1 = mContext.getResources().getString(R.string.Are_agree_with);
        final String str2 = mContext.getResources().getString(R.string.Has_agreed_to);
        final String str3 = mContext.getResources().getString(R.string.Agree_with_failure);
        pd.setMessage(str1);
        pd.setCanceledOnTouchOutside(false);
        pd.show();

        new Thread(new Runnable() {
            public void run() {
                // call api
                try {
                    if (msg.getStatus() == InviteMessage.InviteMesageStatus.BEINVITEED) {//accept be friends
                        EMClient.getInstance().contactManager().acceptInvitation(msg.getFrom());
                    } else if (msg.getStatus() == InviteMessage.InviteMesageStatus.BEAPPLYED) { //accept application to join group
                        EMClient.getInstance().groupManager().acceptApplication(msg.getFrom(), msg.getGroupId());
                    } else if (msg.getStatus() == InviteMessage.InviteMesageStatus.GROUPINVITATION) {
                        EMClient.getInstance().groupManager().acceptInvitation(msg.getGroupId(), msg.getGroupInviter());
                    }
                    msg.setStatus(InviteMessage.InviteMesageStatus.AGREED);
                    // update database
                    ContentValues values = new ContentValues();
                    values.put(InviteMessgeDao.COLUMN_NAME_STATUS, msg.getStatus().ordinal());
                    messgeDao.updateMessage(msg.getId(), values);
                    mContext.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            pd.dismiss();
                            buttonAgree.setText(str2);
                            buttonAgree.setBackgroundDrawable(null);
                            buttonAgree.setEnabled(false);

                            buttonRefuse.setVisibility(View.GONE);
                        }
                    });
                } catch (final Exception e) {
                    mContext.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            pd.dismiss();
                            Toast.makeText(mContext, str3 + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });

                }
            }
        }).start();
    }

    private void refuseInvitation(final Button buttonAgree, final Button buttonRefuse, final InviteMessage msg) {
        final ProgressDialog pd = new ProgressDialog(mContext);
        String str1 = mContext.getResources().getString(R.string.Are_refuse_with);
        final String str2 = mContext.getResources().getString(R.string.Has_refused_to);
        final String str3 = mContext.getResources().getString(R.string.Refuse_with_failure);
        pd.setMessage(str1);
        pd.setCanceledOnTouchOutside(false);
        pd.show();

        new Thread(new Runnable() {
            public void run() {
                // call api
                try {
                    if (msg.getStatus() == InviteMessage.InviteMesageStatus.BEINVITEED) {//decline the invitation
                        EMClient.getInstance().contactManager().declineInvitation(msg.getFrom());
                    } else if (msg.getStatus() == InviteMessage.InviteMesageStatus.BEAPPLYED) { //decline application to join group
                        EMClient.getInstance().groupManager().declineApplication(msg.getFrom(), msg.getGroupId(), "");
                    } else if (msg.getStatus() == InviteMessage.InviteMesageStatus.GROUPINVITATION) {
                        EMClient.getInstance().groupManager().declineInvitation(msg.getGroupId(), msg.getGroupInviter(), "");
                    }
                    msg.setStatus(InviteMessage.InviteMesageStatus.REFUSED);
                    // update database
                    ContentValues values = new ContentValues();
                    values.put(InviteMessgeDao.COLUMN_NAME_STATUS, msg.getStatus().ordinal());
                    messgeDao.updateMessage(msg.getId(), values);
                    mContext.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            pd.dismiss();
                            buttonRefuse.setText(str2);
                            buttonRefuse.setBackgroundDrawable(null);
                            buttonRefuse.setEnabled(false);

                            buttonAgree.setVisibility(View.GONE);
                        }
                    });
                } catch (final Exception e) {
                    mContext.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            pd.dismiss();
                            Toast.makeText(mContext, str3 + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        }).start();
    }
}
