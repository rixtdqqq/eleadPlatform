package com.elead.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.elead.eplatform.R;
import com.elead.im.activity.VideoCallActivity;
import com.elead.im.activity.VoiceCallActivity;
import com.elead.im.db.DemoHelper;
import com.elead.utils.StatusBarUtils;
import com.elead.views.pulltorefresh.EmptyView;
import com.hyphenate.chat.EMClient;
import com.hyphenate.util.EasyUtils;

/**
 * 开屏页
 */
public class SplashActivity extends BaseActivity {
    private static final int sleepTime = 2000;
    private final int REQUEST_READ_PHONE_STATE = 1;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(new EmptyView(this));
        StatusBarUtils.setWindowStatusBarColor(this, R.color.white);
//        application.size = ScreenUtil.getScreenVisiable(this);
//         customProgressDialog = new CustomProgressDialog(this,"加載中",R.drawable.loading_anim);
//        customProgressDialog.show();
        View v = getWindow().getDecorView();
        v.setSystemUiVisibility(View.INVISIBLE);
        isLogged();

    }


    private void isLogged() {
        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();

        new Thread(new Runnable() {
            public void run() {
                if (DemoHelper.getInstance().isLoggedIn()) {
                    // auto login mode, make sure all group and conversation is loaed before enter the main screen
                    long start = System.currentTimeMillis();
                    EMClient.getInstance().chatManager().loadAllConversations();
                    EMClient.getInstance().groupManager().loadAllGroups();
                    long costTime = System.currentTimeMillis() - start;
                    //wait
                    if (sleepTime - costTime > 0) {
                        try {
                            Thread.sleep(sleepTime - costTime);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    String topActivityName = EasyUtils.getTopActivityName(EMClient.getInstance().getContext());
                    if (topActivityName != null && (topActivityName.equals(VideoCallActivity.class.getName()) || topActivityName.equals(VoiceCallActivity.class.getName()))) {
                        // nop
                        // avoid main screen overlap Calling Activity
                    } else {
                        //enter main screen
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    }
                    finish();
                } else {
                    try {
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException e) {
                    }
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                }
            }
        }).start();

    }


}
