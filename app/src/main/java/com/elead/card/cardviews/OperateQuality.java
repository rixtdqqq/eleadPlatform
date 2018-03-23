package com.elead.card.cardviews;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.elead.eplatform.R;
import com.elead.operate.entity.OperateDemandEntity;
import com.elead.operate.entity.OperateQualityEntity;
import com.elead.operate.widget.OperateDemandItem;
import com.elead.operate.widget.OperateQualityItem;

import java.util.ArrayList;
import java.util.List;

/**
 * @desc 运营总监质量View
 * @auth Created by Administrator on 2016/11/3 0003.
 */

public class OperateQuality extends LinearLayout {

    private Context mContext;
    private View mView;
    private TextView tv_1, tv_2, tv_3,tv_4, tv_5, tv_6;
    private LinearLayout ll_quality;
    private LinearLayout ll_more;
    private List<OperateQualityEntity> operateQualityEntityList = new ArrayList<>();
    private List<OperateQualityEntity> mores = new ArrayList<>();
    private Resources resources;

    public LinearLayout getLl_quality() {
        return ll_quality;
    }

    public LinearLayout getLl_more() {
        return ll_more;
    }

    public OperateQuality(Context context) {
        this(context,null);
    }

    public OperateQuality(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OperateQuality(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        resources = mContext.getResources();
        mView = LayoutInflater.from(context).inflate(R.layout.operate_quality_view, this);
        initView();
        for (int i = 0; i < 2; i++){
            OperateQualityEntity entity = new OperateQualityEntity();
            OperateQualityItem operateQualityItem = new OperateQualityItem(mContext);
            if (i == 0) {
                entity.setName("UAT");
                entity.setPrompt("1");
                entity.setCommonly("5");
                entity.setSerious("0");
                entity.setDeadly("0");
                entity.setDefect_rate("2.7%");
                operateQualityItem.getTv_6().setTextColor(Color.WHITE);
                operateQualityItem.getTv_6().setBackgroundResource(R.drawable.operate_demand_text_bg3);
            } else if (i == 1) {
                entity.setName("SIT");
                entity.setPrompt("2");
                entity.setCommonly("6");
                entity.setSerious("2");
                entity.setDeadly("0");
                entity.setDefect_rate("5%");
                operateQualityItem.getTv_4().setTextColor(Color.WHITE);
                operateQualityItem.getTv_4().setBackgroundResource(R.drawable.operate_demand_text_bg1);
                operateQualityItem.getTv_6().setTextColor(Color.WHITE);
                operateQualityItem.getTv_6().setBackgroundResource(R.drawable.operate_quality_text_bg1);
            }
            operateQualityItem.getTv_1().setText(entity.getName());
            operateQualityItem.getTv_2().setText(entity.getPrompt());
            operateQualityItem.getTv_3().setText(entity.getCommonly());
            operateQualityItem.getTv_4().setText(entity.getSerious());
            operateQualityItem.getTv_5().setText(entity.getDeadly());
            operateQualityItem.getTv_6().setText(entity.getDefect_rate());
            ll_quality.addView(operateQualityItem);
            operateQualityEntityList.add(entity);
            mores.add(entity);
        }
    }

    private void initView(){
        tv_1 = (TextView) mView.findViewById(R.id.tv_1);
        tv_2 = (TextView) mView.findViewById(R.id.tv_2);
        tv_3 = (TextView) mView.findViewById(R.id.tv_3);
        tv_4 = (TextView) mView.findViewById(R.id.tv_4);
        tv_5 = (TextView) mView.findViewById(R.id.tv_5);
        tv_6 = (TextView) mView.findViewById(R.id.tv_6);
        tv_1.setText(resources.getString(R.string.operate_quality_name));
        tv_2.setText(resources.getString(R.string.operate_quality_prompt));
        tv_3.setText(resources.getString(R.string.operate_quality_commonly));
        tv_4.setText(resources.getString(R.string.operate_quality_serious));
        tv_5.setText(resources.getString(R.string.operate_quality_deadly));
        tv_6.setText(resources.getString(R.string.operate_quality_defect_rate));
        ll_quality = (LinearLayout) mView.findViewById(R.id.ll_quality);
        ll_more = (LinearLayout) mView.findViewById(R.id.ll_more);

        ll_more.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                initData(mores);
            }
        });
    }

    public void initData(List<OperateQualityEntity> lists){
        operateQualityEntityList.addAll(lists);
        addMore();
    }

    private void addMore(){
        for (int i = 0, size = mores.size(); i < size; i++){
            OperateQualityEntity entity = mores.get(i);
            OperateQualityItem operateQualityItem = new OperateQualityItem(mContext);
            if (i == 0) {
                entity.setName("UAT");
                entity.setPrompt("1");
                entity.setCommonly("5");
                entity.setSerious("0");
                entity.setDeadly("0");
                entity.setDefect_rate("2.7%");
                operateQualityItem.getTv_6().setTextColor(Color.WHITE);
                operateQualityItem.getTv_6().setBackgroundResource(R.drawable.operate_demand_text_bg3);
            } else if (i == 1) {
                entity.setName("SIT");
                entity.setPrompt("2");
                entity.setCommonly("6");
                entity.setSerious("2");
                entity.setDeadly("0");
                entity.setDefect_rate("5%");
                operateQualityItem.getTv_4().setTextColor(Color.WHITE);
                operateQualityItem.getTv_4().setBackgroundResource(R.drawable.operate_demand_text_bg1);
                operateQualityItem.getTv_6().setTextColor(Color.WHITE);
                operateQualityItem.getTv_6().setBackgroundResource(R.drawable.operate_quality_text_bg1);
            }
            operateQualityItem.getTv_1().setText(entity.getName());
            operateQualityItem.getTv_2().setText(entity.getPrompt());
            operateQualityItem.getTv_3().setText(entity.getCommonly());
            operateQualityItem.getTv_4().setText(entity.getSerious());
            operateQualityItem.getTv_5().setText(entity.getDeadly());
            operateQualityItem.getTv_6().setText(entity.getDefect_rate());
            ll_quality.addView(operateQualityItem);
        }
    }

}
