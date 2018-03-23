package com.elead.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;

import com.elead.adapter.UseHelpAdapter;
import com.elead.approval.widget.SpringListView;
import com.elead.eplatform.R;
import com.elead.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/11 0011.
 */

public class UseHelpActivity extends BaseActivity {

    private String[] help_text = new String[]{"智能报表是什么?", "如何接入数据?", "如何通过日志数据生成报表?"
            , "如何通过审批数据生成报表?", "报表有哪些类型?", "如何切换日期查看数据?", "数据报表权限范围是什么?",
            "智能报表PC端后台如何进入?"
    };

    private List<String> list = new ArrayList<>();
    private RelativeLayout load_more_rel;
    private SpringListView listView;
    private UseHelpAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.use_help_activity);
        setupViews();
        setTitle("使用帮助");
        setData();
        setListener();

    }

    private void setListener() {

        load_more_rel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.add("上传的excel有什么要求?");
                load_more_rel.setVisibility(View.GONE);
                adapter.setDatasList(list);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ToastUtil.showToast(UseHelpActivity.this, "暂时还没开放此功能,请耐心等待！", 0).show();

            }
        });


    }

    private void setData() {

        for (int i = 0; i < help_text.length; i++) {
            list.add(help_text[i]);
        }
    }

    private void setupViews() {

        listView = (SpringListView) findViewById(R.id.SpringListView_id);
        View view = LayoutInflater.from(this).inflate(R.layout.load_more_item, null);
        load_more_rel = (RelativeLayout) view.findViewById(R.id.load_more_rel);

        listView.addFooterView(view);

        adapter = new UseHelpAdapter(this, list);
        listView.setAdapter(adapter);
    }

}
