package com.elead.approval.fragment;

import android.content.Context;
import android.text.InputType;
import android.view.View;

import com.elead.approval.widget.ChooseView;
import com.elead.approval.widget.EdiTextItem;

import java.util.List;

/**
 * 招聘
 */

public class ApprovalRecruitment extends ApprovalBaseFragment {
    private EdiTextItem needJobs, needNumber, havePeoCount, jobRequire;
    private ChooseView requireComeDate;

    @Override
    public void initView(Context context) {
        getActivity().setTitle("我的招聘");
        needJobs = new EdiTextItem(mContext);
        needJobs.init(EdiTextItem.LineType.signLine, "需求岗位", "请填写需求岗位");
        addView(needJobs,10);

        needNumber = new EdiTextItem(mContext);
        needNumber.init(EdiTextItem.LineType.signLine, InputType.TYPE_NUMBER_VARIATION_NORMAL, "需求人数", "请填写需求人数(必填)");
        addView(needNumber,10);

        havePeoCount = new EdiTextItem(mContext);
        havePeoCount.init(EdiTextItem.LineType.signLine, "岗位现有人数", "请填写岗位现有人数");
        addView(havePeoCount,10);

        jobRequire = new EdiTextItem(mContext);
        jobRequire.init(EdiTextItem.LineType.signLine, "岗位职责要求", "请填写岗位职责要求(必填)");
        addView(jobRequire,10);

        requireComeDate = new ChooseView(mContext);
        requireComeDate.inIt(ChooseView.Type.time, "期望到岗日期", 10, 0);
        addView(requireComeDate,10);


    }

    @Override
    public boolean onSubmitClick(View v) {
        return false;
    }

    @Override
    public void onPhotoUploadSuccess(List<String> appvalUsers, List<String> imgUrls) {

    }
}
