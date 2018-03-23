package com.elead.card.cardviews;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.elead.R_D_Center.activity.RDCMainActivity;
import com.elead.adapter.MoreAppCardAdapter;
import com.elead.approval.activity.ApprovalMainActivity;
import com.elead.card.entity.MoreEntry;
import com.elead.card.interfaces.ICard;
import com.elead.card.mode.BaseCardInfo;
import com.elead.eplatform.R;
import com.elead.md.activity.MDMainActivity;
import com.elead.operate.activity.StatisticalMainActivity;
import com.elead.qs.QSMainActivity;
import com.elead.report.activity.ReportMainActivity;
import com.elead.utils.EloadingDialog;
import com.elead.utils.ToastUtil;
import com.elead.utils.Util;
import com.elead.views.CustomGridView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016/10/28 0028.
 */

public class SubmenuListCard extends LinearLayout implements ICard {
    private final Context mContext;
    private CustomGridView gridView;
    private int ImgRrc[] = {R.drawable.approval, R.drawable.report, R.drawable.test_005, R.drawable.card_mark, R.drawable.card_isales, R.drawable.card_manufacture, R.drawable.card_ipurchase};
    private String name[] = {getResources().getString(R.string.approved), getResources().getString(R.string.intelligence_reports), getResources().getString(R.string.mobile_development), getResources().getString(R.string.terminal_development), getResources().getString(R.string.quality_summary)};
    private List<MoreEntry> listData = new ArrayList<>();
    private MoreEntry moreEntry;
    private MoreAppCardAdapter adapter;
    private View view;

    public SubmenuListCard(Context context) {
        this(context, null);
    }

    public SubmenuListCard(Context context, AttributeSet attrs) {
        this(context, null, 0);
    }

    public SubmenuListCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        setDatas();
        initViews();
        inListener();
    }


    private void setDatas() {
        for (int i = 0; i < name.length; i++) {
            moreEntry = new MoreEntry();
            moreEntry.setImgRrc(ImgRrc[i]);
            moreEntry.setName(name[i]);
            listData.add(moreEntry);
        }
    }

    private void initViews() {
        view = LayoutInflater.from(mContext).inflate(R.layout.card_more_app_list, this);
        gridView = (CustomGridView) view.findViewById(R.id.gridListIv);

        adapter = new MoreAppCardAdapter(mContext, listData);
        gridView.setAdapter(adapter);
    }

    private void inListener() {


        adapter.setClickMoreAppListener(new MoreAppCardAdapter.OnClickMoreAppListener() {

            @Override
            public void clickMoreApp(int position) {
                if (Util.isExitWhiteList()) {
                    if (TextUtils.equals(getResources().getString(R.string.approved), listData.get(position).getName())) {
                        //showDailog();
                        Intent intent = new Intent(mContext, ApprovalMainActivity.class);
                        mContext.startActivity(intent);
                    } else if (TextUtils.equals(getResources().getString(R.string.intelligence_reports), listData.get(position).getName())) {
                        Intent intent = new Intent(mContext, ReportMainActivity.class);
                        mContext.startActivity(intent);
                    } else if (TextUtils.equals(getResources().getString(R.string.mobile_development), listData.get(position).getName())) {
//                        Intent intent = new Intent(mContext, StatisticalMainActivity.class);
                        Intent intent = new Intent(mContext, MDMainActivity.class);
                        mContext.startActivity(intent);
                    } else if (TextUtils.equals(getResources().getString(R.string.terminal_development), listData.get(position).getName())) {
                        Intent intent = new Intent(mContext, RDCMainActivity.class);
                        mContext.startActivity(intent);
                    } else if (TextUtils.equals(getResources().getString(R.string.quality_summary), listData.get(position).getName())) {
                        Intent intent = new Intent(mContext, QSMainActivity.class);
                        mContext.startActivity(intent);
                    } else {
                        ToastUtil.showToast(mContext, "玩命开发中...", 0).show();
                    }
                } else {
                    EloadingDialog.showDailog();
                }

            }

            @Override
            public void clickMorePuls() {
                //ToastUtil.showToast(mContext, "more...玩命开发中...", 0).show();
                EloadingDialog.showDailog();
            }

        });


    }

    @Override
    public void setDate(BaseCardInfo baseCardInfo) {

    }
}
