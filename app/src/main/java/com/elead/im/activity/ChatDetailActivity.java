package com.elead.im.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;

import com.elead.activity.BaseActivity;
import com.elead.eplatform.R;
import com.elead.im.entry.EaseUser;
import com.elead.im.util.EaseUserUtils;
import com.elead.im.widget.EaseAlertDialog;
import com.elead.im.widget.EaseConstant;
import com.elead.im.widget.EaseSwitchButton;
import com.elead.utils.ToastUtil;
import com.elead.views.CircularImageView;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.chat.EMGroupManager;
import com.hyphenate.exceptions.HyphenateException;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by gly on 2017/1/20 0020.
 */

public class ChatDetailActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.chat_detiail_user)
    CircularImageView chatDetiailUser;
    @BindView(R.id.chat_detiail_add)
    CircularImageView chatDetiailAdd;
    @BindView(R.id.top_chat_switch_btn)
    EaseSwitchButton topChatSwitchBtn;
    @BindView(R.id.top_chat_rel)
    RelativeLayout topChatRel;
    @BindView(R.id.switch_btn)
    EaseSwitchButton switchBtn;
    @BindView(R.id.message_disturb_rel)
    RelativeLayout messageDisturbRel;
    @BindView(R.id.chat_piture_rel)
    RelativeLayout chatPitureRel;
    @BindView(R.id.chat_file_rel)
    RelativeLayout chatFileRel;
    @BindView(R.id.clear_all_history)
    RelativeLayout clearAllHistory;
    private String userName;
    private ProgressDialog progressDialog;
    private String groupName;
    private List<EMGroup> allGroups;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_detiail);
        setTitle(getResources().getString(R.string.chat_details), TitleType.NORMAL);
        ButterKnife.bind(this);
        userName = getIntent().getExtras().getString(EaseUser.class.getSimpleName());
        if (!TextUtils.isEmpty(userName))
            EaseUserUtils.setUserAvatar(this, userName, chatDetiailUser);

        allGroups = EMClient.getInstance().groupManager().getAllGroups();
    }

    private int groupNum = 1;

    private String getGroupName() {
        String name = "群组" + groupNum;
        int count = 0;
        for (EMGroup emgroup : allGroups) {
            if (emgroup.getGroupName().equals(name)) {
                count++;
            }
        }

        if (count != 0) {
            groupNum++;
            return getGroupName();
        } else {
            return name;
        }
    }

    @OnClick({R.id.chat_detiail_user, R.id.chat_detiail_add, R.id.top_chat_rel, R.id.message_disturb_rel, R.id.chat_file_rel, R.id.clear_all_history, R.id.chat_piture_rel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.chat_detiail_user:
                startActivity(new Intent(this, UserInfoDetailActivity.class).putExtra(UserInfoDetailActivity.TAG, userName));
                break;
            case R.id.chat_detiail_add:
////                Intent intent = new Intent(this, GroupPickContactsActivity.class);
//                intent.putExtra(EaseUser.class.getSimpleName(), userName);
//                startActivity(intent);
                groupName = getGroupName();
                startActivityForResult(new Intent(this, GroupPickContactsActivity.class).putExtra(EaseUser.class.getSimpleName(), userName), 0);
                break;
            case R.id.top_chat_rel:
                break;
            case R.id.message_disturb_rel:
                break;
            case R.id.chat_piture_rel:
                break;
            case R.id.chat_file_rel:
                break;
            case R.id.clear_all_history:
                emptyHistory();
                break;
        }
    }


    /**
     * clear the conversation history
     */
    protected void emptyHistory() {
        String msg = getResources().getString(R.string.Whether_to_empty_all_chats);
        new EaseAlertDialog(mContext, null, msg, null, new EaseAlertDialog.AlertDialogUser() {

            @Override
            public void onResult(boolean confirmed, Bundle bundle) {
                if (confirmed) {
                    EMClient.getInstance().chatManager().deleteConversation(userName, true);
                }
            }
        }, true).show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String st1 = getResources().getString(R.string.Is_to_create_a_group_chat);
        final String st2 = getResources().getString(R.string.Failed_to_create_groups);
        if (resultCode == RESULT_OK) {
            //new group
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage(st1);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    String[] members = data.getStringArrayExtra("newmembers");
                    if (!TextUtils.isEmpty(userName)) {
                        members = new String[members.length + 1];
                        members[members.length - 1] = userName;
                    }
                    try {
                        EMGroupManager.EMGroupOptions option = new EMGroupManager.EMGroupOptions();
                        option.maxUsers = 200;
                        String reason = mContext.getString(R.string.invite_join_group);
                        reason = EMClient.getInstance().getCurrentUser() + reason + groupName;

                        option.style = EMGroupManager.EMGroupStyle.EMGroupStylePrivateMemberCanInvite;
                        final EMGroup group = EMClient.getInstance().groupManager().createGroup(groupName, "", members, reason, option);
                        runOnUiThread(new Runnable() {
                            public void run() {
                                progressDialog.dismiss();
                                setResult(RESULT_OK);
                                Intent intent = new Intent(mContext, ChatActivity.class);
                                intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_GROUP);
                                intent.putExtra(EaseConstant.EXTRA_USER_ID, group.getGroupId());
                                startActivityForResult(intent, 0);
                                finish();
                            }
                        });
                    } catch (final HyphenateException e) {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                progressDialog.dismiss();
                                ToastUtil.showToast(mContext, st2 + e.getLocalizedMessage(), 0).show();
                            }
                        });
                    }

                }
            }).start();
        }
    }

}
