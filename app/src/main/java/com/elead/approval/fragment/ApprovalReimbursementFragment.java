package com.elead.approval.fragment;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.elead.approval.entity.ApprovalReimbursementItemEntity;
import com.elead.approval.entity.ApprovalReimbusementEntity;
import com.elead.approval.widget.ReimbursementLayout;
import com.elead.eplatform.R;
import com.elead.utils.Util;

import java.util.ArrayList;
import java.util.List;



/**
 * @报销
 * @auth Created by Administrator on 2016/9/23 0023.
 */
public class ApprovalReimbursementFragment extends  ApprovalBaseFragment implements View.OnClickListener, ReimbursementLayout.RemoveCallBack {

    private View mView;
    private Activity mContext;
    private LinearLayout mLlAddMoney;
    private TextView mTvTotal;
    private ReimbursementLayout reimbursementLayout;
    private LinearLayout ll_reimbursement;
    private List<ApprovalReimbursementItemEntity> reimbursementItemEntities = new ArrayList<ApprovalReimbursementItemEntity>();
    private int lableCount = 1;
    private ApprovalReimbusementEntity mApprovalReimbusementEntity;
    private String moneyTotal;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = (Activity) context;
    }

    @Override
    public void initView(Context context) {
        mContext.setTitle(mContext.getResources().getStringArray(R.array.approval)[1]);
        mView = LinearLayout.inflate(context,R.layout.approval_f_reimbursement, null);
        mLlAddMoney = (LinearLayout) mView.findViewById(R.id.ll_add);
        mTvTotal = (TextView) mView.findViewById(R.id.tv_total);
        ll_reimbursement = (LinearLayout) mView.findViewById(R.id.ll_reimbursement);
        reimbursementLayout = (ReimbursementLayout) mView.findViewById(R.id.ll_reimbursement_item);
        lableCount = 1;
        reimbursementLayout.setLable(lableCount+"");
        reimbursementLayout.isShowDeleted(false);
        mLlAddMoney.setOnClickListener(this);
        addView(mView,0);
    }

    private void setLableCount(){
        int count = ll_reimbursement.getChildCount();
        lableCount = count;
        if (count > 0) {
            for (int i = 0; i < count; i ++) {
                ReimbursementLayout r = (ReimbursementLayout)ll_reimbursement.getChildAt(i);
                r.setLable((i+1)+"");
            }
        }
    }

    private boolean isSubmit(){
        int count = ll_reimbursement.getChildCount();
        if (count > 0) {
            for (int i = 0; i < count; i ++) {
                ReimbursementLayout r = (ReimbursementLayout)ll_reimbursement.getChildAt(i);
                if (TextUtils.isEmpty(r.getMoney()) || TextUtils.isEmpty(r.getType())) {
                    return false;
                }
            }
        }
        return true;
    }

    private void setTotalDetails(){
        int count = ll_reimbursement.getChildCount();
        int total = 0;
        if (count > 0) {
            if (count == 1) {
                ApprovalReimbursementItemEntity entity = new ApprovalReimbursementItemEntity();
                ReimbursementLayout r = (ReimbursementLayout)ll_reimbursement.getChildAt(0);
                moneyTotal = r.getMoney();
                entity.setType(r.getType());
                entity.setMoney(moneyTotal);
                entity.setMoneyDetail(r.getMoneyDetails());
                reimbursementItemEntities.add(entity);
            } else {
                for (int i = 0; i < count; i ++) {
                    ApprovalReimbursementItemEntity entity = new ApprovalReimbursementItemEntity();
                    ReimbursementLayout r = (ReimbursementLayout)ll_reimbursement.getChildAt(i);
                    String money = r.getMoney();
                    total += Util.parseInt(money, 0);
                    entity.setType(r.getType());
                    entity.setMoney(money);
                    entity.setMoneyDetail(r.getMoneyDetails());
                    reimbursementItemEntities.add(entity);
                }
                moneyTotal = total + "";
            }
            mApprovalReimbusementEntity.setReimbursementList(reimbursementItemEntities);
            mApprovalReimbusementEntity.setMoneyTotal(moneyTotal);
        }
    }

    @Override
    public boolean onSubmitClick(View v) {
      /*  if (!isSubmit()) {
            ToastUtil.showToast(mContext,mContext.getResources().getString(R.string.approval_is_not_null), 0).show();
            return false;
        }*/
        mApprovalReimbusementEntity = new ApprovalReimbusementEntity();
        setTotalDetails();
        return true;
    }

    @Override
    public void onPhotoUploadSuccess(List<String> appvalUsers, List<String> imgUrls) {
       /* mApprovalReimbusementEntity.setApprovalUsers(appvalUsers);
        mApprovalReimbusementEntity.setImageNames(imgUrls);
        mApprovalReimbusementEntity.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                finishSubmit(mApprovalReimbusementEntity);
            }
        });*/
    }

    @Override
    public void onClick(View v) {
        int id  = v.getId();
        switch (id) {
            case R.id.ll_add:
                lableCount += 1;
                ReimbursementLayout reimbursementLayout = new ReimbursementLayout(mContext);
                reimbursementLayout.setLable(lableCount+"");
                reimbursementLayout.setRemoveCallBack(this);
                ll_reimbursement.addView(reimbursementLayout);
                break;
            default:
                break;
        }
    }

    @Override
    public void removeCallBack(View view) {
        ll_reimbursement.removeView(view);
        setLableCount();
    }
}
