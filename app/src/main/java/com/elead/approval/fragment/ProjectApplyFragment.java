package com.elead.approval.fragment;

import android.content.Context;
import android.view.View;

import com.elead.approval.widget.AddOrDeleteView;
import com.elead.approval.widget.ChooseView;
import com.elead.approval.widget.EdiTextItem;
import com.elead.eplatform.R;

import java.util.List;

/**
 * 立项申请 Fragment
 * Created by Administrator on 2016/10/12 0012.
 */

public class ProjectApplyFragment extends ApprovalBaseFragment {


    private EdiTextItem projectName, projectDesCri, expectTarget;
    private ChooseView expectStartTime;
    private EdiTextItem personRequire;
    private EdiTextItem moneyRequire;

    private AddOrDeleteView addOrDeleteView;
    private EdiTextItem planProgress;
    private EdiTextItem beiZhu;


    @Override
    public void initView(Context context) {
        getActivity().setTitle(mContext.getResources().getString(R.string.approval_my_project_approval));

        projectName = new EdiTextItem(mContext);
        projectName.init(EdiTextItem.LineType.signLine, mContext.getResources().getString(R.string.approval_project_name), mContext.getResources().getString(R.string.approval_please_input));
        addView(projectName,10);

        projectDesCri = new EdiTextItem(mContext);
        projectDesCri.init(EdiTextItem.LineType.moreLine,mContext.getResources().getString(R.string.approval_project_summary) ,mContext.getResources().getString(R.string.approval_please_input) );
        addView(projectDesCri,10);

        expectTarget = new EdiTextItem(mContext);
        expectTarget.init(EdiTextItem.LineType.signLine, mContext.getResources().getString(R.string.approval_expect_target), mContext.getResources().getString(R.string.approval_please_input));
        addView(expectTarget,10);


        expectStartTime = new ChooseView(mContext);
        expectStartTime.inIt(ChooseView.Type.time,mContext.getResources().getString(R.string.approval_expect_startTime), 10, 0);
        addView(expectStartTime,10);


        personRequire = new EdiTextItem(mContext);
        personRequire.init(EdiTextItem.LineType.signLine, mContext.getResources().getString(R.string.approval_people_require), mContext.getResources().getString(R.string.approval_please_input));
        addView(personRequire,10);


        moneyRequire = new EdiTextItem(mContext);
        moneyRequire.init(EdiTextItem.LineType.signLine, mContext.getResources().getString(R.string.approval_money_require),mContext.getResources().getString(R.string.approval_please_input));
        addView(moneyRequire,10);

        addOrDeleteView = new AddOrDeleteView(mContext,mContext.getResources().getString(R.string.approval_budget) ) {
            @Override
            public View[] initItems() {
                EdiTextItem expectContent = new EdiTextItem(mContext);
                expectContent.init(EdiTextItem.LineType.signLine, mContext.getResources().getString(R.string.approval_butget_content), mContext.getResources().getString(R.string.approval_please_input));
                EdiTextItem expectMoney = new EdiTextItem(mContext);
                expectMoney.init(EdiTextItem.LineType.signLine, mContext.getResources().getString(R.string.approval_budget_money),mContext.getResources().getString(R.string.approval_please_input) );


                return new View[]{expectContent, expectMoney};
            }
        };


        addView(addOrDeleteView,0);

        planProgress = new EdiTextItem(mContext);
        planProgress.init(EdiTextItem.LineType.moreLine, mContext.getResources().getString(R.string.approval_plan_progress), mContext.getResources().getString(R.string.approval_please_input));
        addView(planProgress,10);


        beiZhu = new EdiTextItem(mContext);
        beiZhu.init(EdiTextItem.LineType.moreLine, mContext.getResources().getString(R.string.approval_note), mContext.getResources().getString(R.string.please_write));
        addView(beiZhu,10);
    }

    @Override
    public boolean onSubmitClick(View v) {
        return false;
    }

    @Override
    public void onPhotoUploadSuccess(List<String> appvalUsers, List<String> imgUrls) {

    }
}
