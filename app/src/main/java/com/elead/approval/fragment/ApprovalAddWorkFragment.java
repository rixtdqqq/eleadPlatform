package com.elead.approval.fragment;

import android.content.Context;
import android.view.View;

import com.elead.approval.widget.ChooseView;
import com.elead.approval.widget.EdiTextItem;
import com.elead.eplatform.R;

import java.util.List;

/**
 * 加班
 * Created by Administrator on 2016/10/12 0012.
 */

public class ApprovalAddWorkFragment extends ApprovalBaseFragment {
    private ChooseView startTime, endTime, isFadingHoliday, accountingWay;
    private EdiTextItem workTime, whyaddwork;

    @Override
    public void initView(Context context) {
        getActivity().setTitle(getResources().getString(R.string.approval_workover));
        startTime = new ChooseView(mContext);
        startTime.inIt(ChooseView.Type.time,getResources().getString(R.string.approval_starttime) , 10, 0);
        addView(startTime,10);
        endTime = new ChooseView(mContext);
        endTime.inIt(ChooseView.Type.time, getResources().getString(R.string.approval_endtime), 10, 0);
        addView(endTime,0);

        workTime = new EdiTextItem(mContext);
        workTime.init(EdiTextItem.LineType.signLine, getResources().getString(R.string.approval_workover_time),getResources().getString(R.string.approval_write_workover_time));
        addView(workTime,0);


        isFadingHoliday = new ChooseView(mContext);
        isFadingHoliday.inIt(ChooseView.Type.other, getResources().getString(R.string.approval_isfadingjiari), getResources().getString(R.string.approval_choose), 10, 0, new String[]{getResources().getString(R.string.approval_choose),getResources().getString(R.string.approval_yes) ,getResources().getString(R.string.approval_no) });
        addView(isFadingHoliday,10);

        accountingWay = new ChooseView(mContext);
        accountingWay.inIt(ChooseView.Type.other,  getResources().getString(R.string.approval_he_suan_fang_shi),getResources().getString(R.string.approval_choose), 10, 0, new String[]{getResources().getString(R.string.approval_choose), getResources().getString(R.string.approval_sheqing_tiaoxiu),getResources().getString(R.string.approval_shenqing_jiaban_fei)});
        addView(accountingWay,10);
        whyaddwork = new EdiTextItem(mContext);
        whyaddwork.init(EdiTextItem.LineType.signLine, getResources().getString(R.string.approval_workover_reasonwhy),getResources().getString(R.string.approval_white_reasonwhy));
        addView(whyaddwork,10);
    }

    @Override
    public boolean onSubmitClick(View v) {
        return false;
    }

    @Override
    public void onPhotoUploadSuccess(List<String> appvalUsers, List<String> imgUrls) {

    }
}
