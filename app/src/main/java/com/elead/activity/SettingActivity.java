package com.elead.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.elead.eplatform.R;
import com.elead.utils.AppManager;
import com.elead.utils.Util;
import com.elead.views.ActionSheetDialog;

/**
 * Created by Administrator on 2016/12/7 0007.
 */

public class SettingActivity extends BaseActivity implements View.OnClickListener {
    private RelativeLayout accout_safe_rel;
    private RelativeLayout news_notify_rel;
    private RelativeLayout person_private_rel;
    private RelativeLayout genenal_rel;
    private RelativeLayout language_setting_rel;
    private RelativeLayout aboat_E_rel;
    private RelativeLayout logout_rel;
    private TextView language_tv;
    private String language;
    private RelativeLayout chang_passWork_rel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Util.getLanguage(this);
        setContentView(R.layout.activity_setting_layout);
        setTitle(getResources().getString(R.string.set_activity_set), TitleType.NORMAL);
        initViews();
    }

    private void initViews() {
        accout_safe_rel = (RelativeLayout) findViewById(R.id.accout_safe_rel);
        news_notify_rel = (RelativeLayout) findViewById(R.id.news_notify_rel);
        person_private_rel = (RelativeLayout) findViewById(R.id.person_private_rel);
        genenal_rel = (RelativeLayout) findViewById(R.id.genenal_rel);
        language_setting_rel = (RelativeLayout) findViewById(R.id.language_setting_rel);
        aboat_E_rel = (RelativeLayout) findViewById(R.id.aboat_E_rel);
        logout_rel = (RelativeLayout) findViewById(R.id.logout_rel);
        chang_passWork_rel = (RelativeLayout) findViewById(R.id.chang_passWork_rel);
        language_tv = (TextView) findViewById(R.id.language_tv);

        accout_safe_rel.setOnClickListener(this);
        news_notify_rel.setOnClickListener(this);
        person_private_rel.setOnClickListener(this);
        genenal_rel.setOnClickListener(this);
        language_setting_rel.setOnClickListener(this);
        accout_safe_rel.setOnClickListener(this);
        logout_rel.setOnClickListener(this);
        aboat_E_rel.setOnClickListener(this);
        chang_passWork_rel.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.accout_safe_rel:

                break;

            case R.id.news_notify_rel:

                break;

            case R.id.person_private_rel:

                break;

            case R.id.genenal_rel:

                break;

            case R.id.language_setting_rel:
                ShowDailog();
                break;

            case R.id.logout_rel:

                logout();
                break;


            case R.id.aboat_E_rel:


                break;

            case R.id.chang_passWork_rel:
                Intent i = new Intent(mContext, ModifyPasswordActivity.class);
                startActivity(i);
                break;
        }
    }


    private void logout() {
        /*final ProgressDialog pd = new ProgressDialog(this);
        String st = getResources().getString(R.string.Are_logged_out);
        pd.setMessage(st);
        pd.setCanceledOnTouchOutside(false);
        pd.show();
        DemoHelper.getInstance().logout(true, new EMCallBack() {

            @Override
            public void onSuccess() {
                SettingActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        pd.dismiss();
                        // 重新退到登录页面
                        Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        DemoHelper sdkHelper = DemoHelper.getInstance();
                        sdkHelper.finishAllActivity();
                    }
                });
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {
                SettingActivity.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        pd.dismiss();
                        Toast.makeText(SettingActivity.this, "unbind devicetokens failed", Toast.LENGTH_SHORT).show();


                    }
                });
            }
        });*/
        // 重新退到登录页面
        Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("logout", true);
        startActivity(intent);
        AppManager.getAppManager().finishAllActivity();
    }


    private void ShowDailog() {
        new ActionSheetDialog(SettingActivity.this).builder()
                .setCancelable(false)
                .setCanceledOnTouchOutside(true)
                .addSheetItem("ENGLISH", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {

                                Util.switchLanguage(SettingActivity.this, "en");
                                setAllRecreate();
                                // SettingActivity.this.recreate();
                                //language_tv.setText("ENGLISH");
                            }

                        }).addSheetItem("简体中文", ActionSheetDialog.SheetItemColor.Blue,
                new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {

                        Util.switchLanguage(SettingActivity.this, "zh");
                        setAllRecreate();
                        //   SettingActivity.this.recreate();
                        //language_tv.setText("简体中文");
                    }
                }).show();


    }


    private void setAllRecreate() {
        for (int i = 0; i < AppManager.getAppManager().activityStack.size(); i++) {
            if (null != AppManager.getAppManager().activityStack.get(i)) {
                Log.i("TAGll", "apppp.mar==" + AppManager.getAppManager().activityStack.get(i));
                AppManager.getAppManager().activityStack.get(i).recreate();
            }
        }

    }

}
