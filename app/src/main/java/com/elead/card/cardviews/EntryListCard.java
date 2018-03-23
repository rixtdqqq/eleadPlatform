package com.elead.card.cardviews;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.elead.develop.adapter.MyAdapter;
import com.elead.develop.entity.PoPreViewEnty;
import com.elead.eplatform.R;
import com.elead.operate.activity.DetaiCardActivity;
import com.elead.views.CustomListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/4 0004.
 */

public class EntryListCard extends LinearLayout{


    private CustomListView listiv;
    private PoPreViewEnty PopreViewentry;
   private List<PoPreViewEnty>list=new ArrayList<>();
    private MyAdapter adapter;

    public EntryListCard(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public EntryListCard(Context context) {
        this(context,null);
    }

    public EntryListCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setDatas();
     initViews(context);
    }

    private void setDatas() {
        PopreViewentry=new PoPreViewEnty();
        PopreViewentry.setImgSrc(R.drawable.card_listv_1);
        PopreViewentry.setTopTitle("iCaptain for mobile in the HR");
        PopreViewentry.setBottomTime("2016/06/15 09:04:03");
        PopreViewentry.setUnit("综合业务单元");
        list.add(PopreViewentry);
    }

    private void initViews(final Context context) {
        LayoutInflater.from(context).inflate(R.layout.card_listview_information_po,this,true);
        listiv = (CustomListView) findViewById(R.id.informatlistIv);
        adapter=new MyAdapter(context,list);
        adapter.setColor(Color.parseColor("#000000"),Color.parseColor("#cccccc"),Color.parseColor("#cccccc"),R.drawable.right_arrow);
        listiv.setAdapter(adapter);

        listiv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              Intent intent= new Intent(context, DetaiCardActivity.class);
                context.startActivity(intent);
            }
        });

    }





}
