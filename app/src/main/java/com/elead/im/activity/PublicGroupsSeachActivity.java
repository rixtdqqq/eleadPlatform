package com.elead.im.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.elead.activity.BaseActivity;
import com.elead.eplatform.R;
import com.elead.utils.ToastUtil;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.exceptions.HyphenateException;

public class PublicGroupsSeachActivity extends BaseActivity {
    private RelativeLayout containerLayout;
    private EditText idET;
    private TextView nameText;
    public static EMGroup searchedGroup;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.em_activity_public_groups_search);
        setTitle(getResources().getString(R.string.search_public_group_chat), TitleType.NORMAL);
        containerLayout = (RelativeLayout) findViewById(R.id.rl_searched_group);
        idET = (EditText) findViewById(R.id.et_search_id);
        nameText = (TextView) findViewById(R.id.name);

        searchedGroup = null;
    }

    public void searchGroup(View v) {
        if (TextUtils.isEmpty(idET.getText())) {
            return;
        }

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage(getResources().getString(R.string.searching));
        pd.setCancelable(false);
        pd.show();

        new Thread(new Runnable() {

            public void run() {
                try {
                    searchedGroup = EMClient.getInstance().groupManager().getGroupFromServer(idET.getText().toString());
                    runOnUiThread(new Runnable() {
                        public void run() {
                            pd.dismiss();
                            containerLayout.setVisibility(View.VISIBLE);
                            nameText.setText(searchedGroup.getGroupName());
                        }
                    });

                } catch (final HyphenateException e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        public void run() {
                            pd.dismiss();
                            searchedGroup = null;
                            containerLayout.setVisibility(View.GONE);
                            if (e.getErrorCode() == EMError.GROUP_INVALID_ID) {
                                ToastUtil.showToast(getApplicationContext(), getResources().getString(R.string.group_not_existed), 0).show();
                            } else {
                                ToastUtil.showToast(getApplicationContext(), getResources().getString(R.string.group_search_failed) + " : " + getString(R.string.connect_failuer_toast), 0).show();
                            }
                        }
                    });
                }
            }
        }).start();
    }

    @Override
    public void setOnClick() {
        super.setOnClick();
        if (TextUtils.isEmpty(idET.getText())) {
            return;
        }

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage(getResources().getString(R.string.searching));
        pd.setCancelable(false);
        pd.show();

        new Thread(new Runnable() {

            public void run() {
                try {
                    searchedGroup = EMClient.getInstance().groupManager().getGroupFromServer(idET.getText().toString());
                    runOnUiThread(new Runnable() {
                        public void run() {
                            pd.dismiss();
                            containerLayout.setVisibility(View.VISIBLE);
                            nameText.setText(searchedGroup.getGroupName());
                        }
                    });

                } catch (final HyphenateException e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        public void run() {
                            pd.dismiss();
                            searchedGroup = null;
                            containerLayout.setVisibility(View.GONE);
                            if (e.getErrorCode() == EMError.GROUP_INVALID_ID) {
                                ToastUtil.showToast(getApplicationContext(), getResources().getString(R.string.group_not_existed), 0).show();
                            } else {
                                ToastUtil.showToast(getApplicationContext(), getResources().getString(R.string.group_search_failed) + " : " + getString(R.string.connect_failuer_toast), 0).show();
                            }
                        }
                    });
                }
            }
        }).start();
    }
}
