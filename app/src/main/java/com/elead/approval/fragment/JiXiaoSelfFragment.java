package com.elead.approval.fragment;

import android.content.Context;
import android.view.View;

import com.elead.approval.widget.EdiTextItem;
import com.elead.eplatform.R;

import java.util.List;

/**
 * 绩效自评 Fragment
 * Created by Administrator on 2016/10/13 0013.
 */

public class JiXiaoSelfFragment extends ApprovalBaseFragment {


    private EdiTextItem lastMonthWorkTask;
    private EdiTextItem shiJiCompleteTask;
    private EdiTextItem taskCompleteRate;
    private EdiTextItem lastMonthWorkSelf;
    private EdiTextItem thisMonthWorkTask;
    private EdiTextItem thisMonthWorkPlan;



    @Override
    public void initView(Context context) {
       getActivity().setTitle(mContext.getResources().getString(R.string.approval_Self_evaluation));

        lastMonthWorkTask = new EdiTextItem(mContext);
        lastMonthWorkTask.init(EdiTextItem.LineType.moreLine, mContext.getResources().getString(R.string.approval_last_month_task, mContext.getResources().getString(R.string.approval_please_input)));
        addView(lastMonthWorkTask,10);

        shiJiCompleteTask = new EdiTextItem(mContext);
        shiJiCompleteTask.init(EdiTextItem.LineType.moreLine, mContext.getResources().getString(R.string.approval_actually_complete_task, mContext.getResources().getString(R.string.approval_please_input)));
        addView(shiJiCompleteTask,10);


        taskCompleteRate = new EdiTextItem(mContext);
        taskCompleteRate.init(EdiTextItem.LineType.signLine, mContext.getResources().getString(R.string.approval_task_complete_rate, mContext.getResources().getString(R.string.approval_digital_complete_rate)));
        addView(taskCompleteRate,10);

        lastMonthWorkSelf = new EdiTextItem(mContext);
        lastMonthWorkSelf.init(EdiTextItem.LineType.moreLine, mContext.getResources().getString(R.string.approval_lastmonth_self,mContext.getResources().getString(R.string.approval_please_input )));
        addView(lastMonthWorkSelf,10);

        thisMonthWorkTask = new EdiTextItem(mContext);
        thisMonthWorkTask.init(EdiTextItem.LineType.moreLine, mContext.getResources().getString(R.string.approval_this_moth_task,mContext.getResources().getString(R.string.approval_please_input )));
        addView(thisMonthWorkTask,10);

        thisMonthWorkPlan = new EdiTextItem(mContext);
        thisMonthWorkPlan.init(EdiTextItem.LineType.moreLine,mContext.getResources().getString(R.string.approval_this_month_plan ,mContext.getResources().getString(R.string.approval_please_input)));
        addView(thisMonthWorkPlan,10);
    }

    @Override
    public boolean onSubmitClick(View v) {
        return false;
    }

    @Override
    public void onPhotoUploadSuccess(List<String> appvalUsers, List<String> imgUrls) {

    }
}
