package com.elead.approval.fragment;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.elead.approval.entity.ApprovalCommonEntity;
import com.elead.approval.widget.EdiTextItem;
import com.elead.eplatform.R;
import com.elead.utils.ToastUtil;

import java.util.List;

/**
 * @通用审批
 * @auth Created by Administrator on 2016/9/23 0023.
 */
public class ApprovalCommonFragment extends  ApprovalBaseFragment {

    private View mView;
    private Activity mContext;
    EdiTextItem ed_common_content;
    EdiTextItem ed_common_detail;
    ApprovalCommonEntity mApprovalCommonEntity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = (Activity) context;
    }

    @Override
    public void initView(Context context) {
        mContext.setTitle(mContext.getResources().getStringArray(R.array.approval)[5]);
        mView = LinearLayout.inflate(context,R.layout.approval_f_common, null);
        ed_common_content = (EdiTextItem) mView.findViewById(R.id.ed_common_content);
        ed_common_content.init(EdiTextItem.LineType.signLine,
                mContext.getResources().getString(R.string.approval_application_content),
                mContext.getResources().getString(R.string.approval_application_content_hint));
        ed_common_detail = (EdiTextItem) mView.findViewById(R.id.ed_common_detail);
        ed_common_detail.init(EdiTextItem.LineType.moreLine,
                mContext.getResources().getString(R.string.approval_details),
                mContext.getResources().getString(R.string.approval_details_hint));
        addView(mView,10);
    }

    private boolean isSubmit(){
        if (TextUtils.isEmpty(ed_common_content.getContent())
                || TextUtils.isEmpty(ed_common_detail.getContent())) {
            return false;
        }
        return true;
    }

    @Override
    public boolean onSubmitClick(View v) {
        if (!isSubmit()) {
            ToastUtil.showToast(mContext,mContext.getResources().getString(R.string.approval_is_not_null), 0).show();
            return false;
        }
        mApprovalCommonEntity = new ApprovalCommonEntity();
        mApprovalCommonEntity.setApprovalContent(ed_common_content.getContent());
        mApprovalCommonEntity.setApprovalDsicribe(ed_common_detail.getContent());
        return true;
    }

    @Override
    public void onPhotoUploadSuccess(List<String> appvalUsers, List<String> imgUrls) {
        /*mApprovalCommonEntity.setApprovalUsers(appvalUsers);
        mApprovalCommonEntity.setImageNames(imgUrls);
        mApprovalCommonEntity.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                finishSubmit(mApprovalCommonEntity);
            }
        });*/
    }
}
