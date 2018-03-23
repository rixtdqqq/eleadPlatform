package com.elead.im.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.elead.eplatform.R;
import com.elead.im.activity.GroupDetailsActivity;
import com.elead.im.widget.EaseConstant;
import com.elead.views.CircularImageView;
import com.elead.views.baseadapter.CommonAdapter;
import com.elead.views.baseadapter.ViewHolder;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.exceptions.HyphenateException;

import java.util.List;

import static com.elead.application.MyApplication.showingActivity;

/**
 * Created by Administrator on 2017/1/23 0023.
 */

public class GroupAdapter extends CommonAdapter<EMGroup> {

    public GroupAdapter(Context context, List<EMGroup> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder helper, final EMGroup emGroup) {
        CircularImageView avatar = helper.getView(R.id.avatar);
        avatar.setText(emGroup.getMembers());
        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showingActivity.startActivity(new Intent(showingActivity, GroupDetailsActivity.class).putExtra(EaseConstant.GROUP_ID, emGroup.getGroupId()));
            }
        });
        helper.getView(R.id.number_count_group_iv).setVisibility(View.VISIBLE);
        helper.setText(R.id.name, emGroup.getGroupName());
        final TextView textview = helper.getView(R.id.number_count_group_iv);
        textview.setText(emGroup.getAffiliationsCount() + "人");
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                textview.setText((String) msg.obj);
            }
        };
        synchronized (emGroup.getGroupName()) {
            new Thread(new Runnable() {
                public void run() {
                    try {
                        EMGroup groupFromServer = EMClient.getInstance().groupManager().getGroupFromServer(emGroup.getGroupId());
                        Message message = handler.obtainMessage();
                        message.obj = groupFromServer.getAffiliationsCount() + "人";
                        handler.sendMessage(message);

                    } catch (HyphenateException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}
