package com.elead.approval.fragment;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.elead.approval.entity.ApprovalResEntity;
import com.elead.approval.entity.ApprovalResItemEntity;
import com.elead.approval.widget.EdiTextItem;
import com.elead.approval.widget.ResLayout;
import com.elead.eplatform.R;
import com.elead.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * 物品领用
 * @auth Created by Administrator on 2016/9/23 0023.
 */
public class ApprovalResFragment extends  ApprovalBaseFragment implements View.OnClickListener, ResLayout.RemoveCallBack {

    private View mView;
    private Activity mContext;
    private LinearLayout mLlAddMoney;
    private TextView mTvTotal;
    private ResLayout resLayout;
    private LinearLayout ll_res;
    private ApprovalResEntity mApprovalResEntity;
    private List<ApprovalResItemEntity> resItemEntities = new ArrayList<ApprovalResItemEntity>();
    private int lableCount = 1;
    EdiTextItem ed_purpose;
    EdiTextItem ed_detail;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = (Activity) context;
    }

    @Override
    public void initView(Context context) {
        mContext.setTitle(mContext.getResources().getStringArray(R.array.approval)[4]);
        mView = LinearLayout.inflate(context,R.layout.approval_f_res, null);
        mLlAddMoney = (LinearLayout) mView.findViewById(R.id.ll_add);
        mTvTotal = (TextView) mView.findViewById(R.id.tv_total);
        ll_res = (LinearLayout) mView.findViewById(R.id.ll_res);
        resLayout = (ResLayout) mView.findViewById(R.id.ll_res_item);
        ed_purpose = (EdiTextItem) mView.findViewById(R.id.ed_res_purpose);
        ed_purpose.init(EdiTextItem.LineType.signLine,
                mContext.getResources().getString(R.string.approval_res_purpose),
                mContext.getResources().getString(R.string.approval_res_purpose_hint));
        ed_detail = (EdiTextItem) mView.findViewById(R.id.ed_res_detail);
        ed_detail.init(EdiTextItem.LineType.moreLine,
                mContext.getResources().getString(R.string.approval_res_receive_detail),
                mContext.getResources().getString(R.string.approval_res_receive_detail_hint));
        lableCount = 1;
        resLayout.setLable(lableCount+"");
        resLayout.isShowDeleted(false);
        mLlAddMoney.setOnClickListener(this);
        addView(mView,0);
    }

    private void setLableCount(){
        int count = ll_res.getChildCount();
        lableCount = count;
        if (count > 0) {
            for (int i = 0; i < count; i ++) {
                ResLayout r = (ResLayout) ll_res.getChildAt(i);
                r.setLable((i+1)+"");
            }
        }
    }

    private boolean isSubmit(){
        int count = ll_res.getChildCount();
        if (count > 0) {
            for (int i = 0; i < count; i ++) {
                ResLayout r = (ResLayout) ll_res.getChildAt(i);
                if (TextUtils.isEmpty(r.getName()) || TextUtils.isEmpty(r.getCount())
                        || TextUtils.isEmpty(ed_purpose.getContent())
                        || TextUtils.isEmpty(ed_detail.getContent())) {
                    return false;
                }
            }
        }
        return true;
    }

    private void setResDetails() {
        int count = ll_res.getChildCount();
        int total = 0;
        if (count > 0) {
            if (count == 1) {
                ApprovalResItemEntity entity = new ApprovalResItemEntity();
                ResLayout r = (ResLayout)ll_res.getChildAt(0);
                entity.setName(r.getName());
                entity.setCount(r.getCount());
                resItemEntities.add(entity);
            } else {
                for (int i = 0; i < count; i ++) {
                    ApprovalResItemEntity entity = new ApprovalResItemEntity();
                    ResLayout r = (ResLayout)ll_res.getChildAt(i);
                    entity.setName(r.getName());
                    entity.setCount(r.getCount());
                    resItemEntities.add(entity);
                }
            }
            mApprovalResEntity.setResList(resItemEntities);
        }
        mApprovalResEntity.setResPurpose(ed_purpose.getContent());
        mApprovalResEntity.setResReceiveDetail(ed_detail.getContent());
    }

    @Override
    public boolean onSubmitClick(View v) {
        if (!isSubmit()) {
            ToastUtil.showToast(mContext,mContext.getResources().getString(R.string.approval_is_not_null), 0).show();
            return false;
        }
        mApprovalResEntity = new ApprovalResEntity();
        setResDetails();
        return true;
    }

    @Override
    public void onPhotoUploadSuccess(List<String> appvalUsers, List<String> imgUrls) {
       /* mApprovalResEntity.setApprovalUsers(appvalUsers);
        mApprovalResEntity.setImageNames(imgUrls);
        mApprovalResEntity.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                finishSubmit(mApprovalResEntity);
            }
        });*/
    }

    @Override
    public void onClick(View v) {
        int id  = v.getId();
        switch (id) {
            case R.id.ll_add:
                lableCount += 1;
                ResLayout resLayout = new ResLayout(mContext);
                resLayout.setLable(lableCount+"");
                resLayout.setRemoveCallBack(this);
                ll_res.addView(resLayout);
                break;
            default:
                break;
        }
    }

    @Override
    public void removeCallBack(View view) {
        ll_res.removeView(view);
        setLableCount();
    }
}
