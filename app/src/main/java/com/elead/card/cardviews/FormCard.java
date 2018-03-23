package com.elead.card.cardviews;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.elead.card.interfaces.ICard;
import com.elead.card.mode.BaseCardInfo;
import com.elead.eplatform.R;
import com.elead.operate.entity.OperateDemandEntity;
import com.elead.operate.widget.OperateDemandItem;

import java.util.ArrayList;
import java.util.List;

/**
 * @desc 运营总监需求View
 * @auth Created by Administrator on 2016/11/3 0003.
 */

public class FormCard extends LinearLayout implements ICard{

    private Context mContext;
    private View mView;
    private TextView tv_left, tv_middle, tv_right;
    private LinearLayout ll_demand;
    private LinearLayout ll_more;
    private List<OperateDemandEntity> operateDemandEntityList = new ArrayList<>();
    private List<OperateDemandEntity> mores = new ArrayList<>();
    private Resources resources;

    public LinearLayout getLl_demand() {
        return ll_demand;
    }

    public LinearLayout getLl_more() {
        return ll_more;
    }

    public FormCard(Context context) {
        this(context,null);
    }

    public FormCard(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FormCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        resources = mContext.getResources();
        mView = LayoutInflater.from(context).inflate(R.layout.operate_demand_view, this);
        initView();
        for (int i = 0; i < 3; i++){
            OperateDemandEntity entity = new OperateDemandEntity();
            OperateDemandItem operateDemandItem = new OperateDemandItem(mContext);
            operateDemandItem.getTv_right().setTextColor(Color.WHITE);
            if (i == 0) {
                entity.setCheckItem("第一季度表格文件...");
                entity.setProblemNumber("1");
                entity.setCompliance("86%");
                operateDemandItem.getTv_right().setBackgroundResource(R.drawable.operate_demand_text_bg3);
            } else if (i == 1) {
                entity.setCheckItem("第二季度表格文件...");
                entity.setProblemNumber("0");
                entity.setCompliance("95%");
                operateDemandItem.getTv_right().setBackgroundResource(R.drawable.operate_demand_text_bg2);
            } else if (i == 2) {
                entity.setCheckItem("第三季度表格文件...");
                entity.setProblemNumber("12");
                entity.setCompliance("60%");
                operateDemandItem.getTv_middle().setTextColor(Color.WHITE);
                operateDemandItem.getTv_middle().setBackgroundResource(R.drawable.operate_demand_text_bg1);
                operateDemandItem.getTv_right().setBackgroundResource(R.drawable.operate_demand_text_bg2);
            }
            operateDemandItem.getTv_left().setText(entity.getCheckItem());
            operateDemandItem.getTv_middle().setText(entity.getProblemNumber());
            operateDemandItem.getTv_right().setText(entity.getCompliance());
            ll_demand.addView(operateDemandItem);
            operateDemandEntityList.add(entity);
            mores.add(entity);
        }
    }

    private void initView(){
        tv_left = (TextView) mView.findViewById(R.id.tv_left);
        tv_middle = (TextView) mView.findViewById(R.id.tv_middle);
        tv_right = (TextView) mView.findViewById(R.id.tv_right);
        tv_left.setText(resources.getString(R.string.operate_check_item));
        tv_middle.setText(resources.getString(R.string.operate_problem_number));
        tv_right.setText(resources.getString(R.string.operate_compliance));
        ll_demand = (LinearLayout) mView.findViewById(R.id.ll_demand);
        ll_more = (LinearLayout) mView.findViewById(R.id.ll_more);

        ll_more.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                initData(mores);
            }
        });
    }

    public void initData(List<OperateDemandEntity> lists){
        operateDemandEntityList.addAll(lists);
        addMore();
    }

    private void addMore(){
        for (int i = 0, size = mores.size(); i < size; i++){
            OperateDemandEntity entity = mores.get(i);
            OperateDemandItem operateDemandItem = new OperateDemandItem(mContext);
            operateDemandItem.getTv_right().setTextColor(Color.WHITE);
            if (i == 0) {
                entity.setCheckItem("第一季度表格文件...");
                entity.setProblemNumber("1");
                entity.setCompliance("86%");
                operateDemandItem.getTv_right().setBackgroundResource(R.drawable.operate_demand_text_bg3);
            } else if (i == 1) {
                entity.setCheckItem("第二季度表格文件...");
                entity.setProblemNumber("0");
                entity.setCompliance("95%");
                operateDemandItem.getTv_right().setBackgroundResource(R.drawable.operate_demand_text_bg2);
            } else if (i == 2) {
                entity.setCheckItem("第三季度表格文件...");
                entity.setProblemNumber("12");
                entity.setCompliance("60%");
                operateDemandItem.getTv_middle().setTextColor(Color.WHITE);
                operateDemandItem.getTv_middle().setBackgroundResource(R.drawable.operate_demand_text_bg1);
                operateDemandItem.getTv_right().setBackgroundResource(R.drawable.operate_demand_text_bg2);
            }
            operateDemandItem.getTv_left().setText(entity.getCheckItem());
            operateDemandItem.getTv_middle().setText(entity.getProblemNumber());
            operateDemandItem.getTv_right().setText(entity.getCompliance());
            ll_demand.addView(operateDemandItem);
        }
    }

    @Override
    public void setDate(BaseCardInfo baseCardInfo) {

    }
}
