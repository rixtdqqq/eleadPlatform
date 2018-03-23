package com.elead.approval.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.elead.eplatform.R;

/**
 * @desc
 * @auth Created by mujun.xu on 2016/9/23 0023.
 */
public class ReimbursementLayout extends LinearLayout {
    private Context mContext;
    private TextView tv_lable;
    private TextView tv_deleted;
    private EdiTextItem ed_money;
    private EdiTextItem ed_type;
    private EdiTextItem ed_money_detail;

    public ReimbursementLayout(Context context) {
        this(context,null);
    }

    public ReimbursementLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ReimbursementLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initView();
    }

    private void initView(){
        LayoutInflater.from(mContext).inflate(R.layout.approval_i_reimbursement, this);
        tv_lable = (TextView) findViewById(R.id.tv_lable);
        tv_deleted = (TextView) findViewById(R.id.tv_deleted);
        ed_money = (EdiTextItem) findViewById(R.id.ed_money);
        ed_money.init(EdiTextItem.LineType.signLine,
                mContext.getResources().getString(R.string.approval_reimbursement_money),
                mContext.getResources().getString(R.string.approval_reimbursement_money_hint));
        ed_type = (EdiTextItem) findViewById(R.id.ed_type);
        ed_type.init(EdiTextItem.LineType.signLine,
                mContext.getResources().getString(R.string.approval_reimbursement_type),
                mContext.getResources().getString(R.string.approval_reimbursement_type_hint));
        ed_money_detail = (EdiTextItem) findViewById(R.id.ed_money_detail);
        ed_money_detail.init(EdiTextItem.LineType.moreLine,
                mContext.getResources().getString(R.string.approval_money_detail),
                mContext.getResources().getString(R.string.approval_reimbursement_money_detail_hint));
        tv_deleted.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != callBack) {
                    callBack.removeCallBack(ReimbursementLayout.this);
                }
            }
        });
    }

    RemoveCallBack callBack;
    public void setRemoveCallBack(RemoveCallBack callBack){
        this.callBack = callBack;
    }

    public interface RemoveCallBack{
        public void removeCallBack(View view);
    }

    public TextView getDeleted(){
        return tv_deleted;
    }

    public void isShowDeleted(boolean isShow){
        if (isShow) {
            tv_deleted.setVisibility(VISIBLE);
        } else {
            tv_deleted.setVisibility(GONE);
        }
    }

    public void setLable(String number){
        if (TextUtils.isEmpty(number))
            number = "0";
        tv_lable.setText(String.format(mContext.getResources().getString(R.string.approval_reimbursement_detail), number));
    }

    public String getMoney(){
        return ed_money.getContent();
    }

    public String getType(){
        return ed_type.getContent();
    }

    public String getMoneyDetails(){
        return ed_money_detail.getContent();
    }

}
