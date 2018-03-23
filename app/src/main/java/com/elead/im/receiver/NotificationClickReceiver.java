package com.elead.im.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.elead.im.activity.ChatActivity;
import com.elead.im.model.EaseNotifier;
import com.elead.im.widget.EaseConstant;

public class NotificationClickReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //todo 跳转之前要处理的逻辑
        EaseNotifier.msgCount.put(intent.getStringExtra(EaseConstant.EXTRA_USER_ID),0);
        intent.setClass(context, ChatActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}