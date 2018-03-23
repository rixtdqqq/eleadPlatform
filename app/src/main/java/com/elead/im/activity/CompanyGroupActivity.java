package com.elead.im.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.elead.activity.BaseActivity;
import com.elead.eplatform.R;
import com.elead.module.EpUser;
import com.elead.module.MGroup;
import com.elead.views.baseadapter.CommonAdapter;
import com.elead.views.baseadapter.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/11/30 0030.
 */

public class CompanyGroupActivity extends BaseActivity {
    @BindView(R.id.lv_search)
    ListView lvSearch;
    private EditText edText;
    private MGroup mGroup;
    private List<EpUser> conformUsers = new ArrayList<>();
    private CommonAdapter<EpUser> commonAdapter;
    private InputMethodManager imm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String mGroupStr = getIntent().getStringExtra(MGroup.class.getSimpleName());
        Log.d("mGroupStr", mGroupStr);
        mGroup = JSON.parseObject(mGroupStr, MGroup.class);
        if (null == mGroup)
            return;
        setTitle(mGroup.getGroupName(), TitleType.NORMAL);
        setContentView(R.layout.activity_company_group);
        ButterKnife.bind(this);
        conformUsers.addAll(mGroup.getEpUsers());
        commonAdapter = new CommonAdapter<EpUser>(this, conformUsers, R.layout.list_item_simple_text) {
            @Override
            public void convert(ViewHolder helper, EpUser item) {
                helper.setText(R.id.tv_content, item.getRole_name());
            }
        };
        lvSearch.setAdapter(commonAdapter);

        edText = (EditText) getTitleView().getCustomView().findViewById(R.id.action_ed_search);
        edText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (null != mGroup && !"".equals(s.toString().trim())) {
                    conformUsers.clear();
                    mGroup.search(s.toString(), conformUsers);
                    commonAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fram:
                edText.setVisibility(View.VISIBLE);
                edText.setFocusable(true);
                edText.requestFocus();
                imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                break;
        }
    }


    @Override
    public void onBackPressed() {
        if (edText.getVisibility() == View.VISIBLE) {
            edText.setVisibility(View.GONE);
            imm.hideSoftInputFromWindow(edText.getWindowToken(), 0);
        } else {
            super.onBackPressed();
        }
    }
}
