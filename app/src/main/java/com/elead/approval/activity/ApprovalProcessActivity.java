package com.elead.approval.activity;


import android.os.Bundle;

import com.elead.activity.BaseActivity;
import com.elead.approval.adapter.ApprovalAdapter;
import com.elead.approval.entity.ApprovalEntry;
import com.elead.eplatform.R;
import com.elead.views.CircularImageView;
import com.elead.views.CustomListView;

import java.util.ArrayList;
import java.util.List;

public class ApprovalProcessActivity extends BaseActivity {

    private String name[] = {"邹宇", "赌神", "张珊珊"};
    private String status[] = {"已同意", "审批中", "null"};
    private ApprovalEntry entry;
    private List<ApprovalEntry> list = new ArrayList<>();
    private CustomListView listView;
    private ApprovalAdapter adapter;
    private CircularImageView my_photo;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approval_process);
        setTitle("审批详情", BaseActivity.TitleType.NORMAL);
        initDatas();
        initViews();
    }

    private void initDatas() {
        for (int i = 0; i < name.length; i++) {
            entry = new ApprovalEntry();
            entry.setName(name[i]);
            entry.setApprovalStatus(status[i]);
            list.add(entry);
        }
    }

    private void initViews() {
        listView = (CustomListView) findViewById(R.id.listIv);
        my_photo = (CircularImageView) findViewById(R.id.my_photo_iv);
        adapter = new ApprovalAdapter(this, list);
        listView.setAdapter(adapter);
        my_photo.setBackgroundColor("安洋");
        my_photo.setText("安洋");
    }


}
