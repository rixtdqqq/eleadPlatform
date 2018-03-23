package com.elead.approval.fragment;

import android.content.Context;
import android.view.View;

import com.elead.approval.widget.ChooseView;
import com.elead.approval.widget.EdiTextItem;
import com.elead.eplatform.R;

import java.util.List;

/**
 * Created by Administrator on 2016/10/12 0012.
 */

public class ApprovalGoAway extends ApprovalBaseFragment {
    private ChooseView ruzhiDate, lizhiDate, startTime, endTime;
    private EdiTextItem suoshuDanwei, jiaojiePeo, lizhiReason, jiaojieshixiang;

    @Override
    public void initView(Context context) {
        getActivity().setTitle(getResources().getString(R.string.approval_my_goaway));
        ruzhiDate = new ChooseView(mContext);
        ruzhiDate.inIt(ChooseView.Type.time, getResources().getString(R.string.approval_induction_date), 10, 0);
        addView(ruzhiDate,10);

        lizhiDate = new ChooseView(mContext);
        lizhiDate.inIt(ChooseView.Type.time,getResources().getString(R.string.approval_my_goaway_date), 0, 10);
        addView(lizhiDate,0);

        suoshuDanwei = new EdiTextItem(mContext);
        suoshuDanwei.init(EdiTextItem.LineType.signLine, getResources().getString(R.string.approval_job), getResources().getString(R.string.approval_please_write_job_must));
        addView(suoshuDanwei,10);

        jiaojiePeo = new EdiTextItem(mContext);
        jiaojiePeo.init(EdiTextItem.LineType.signLine,  getResources().getString(R.string.approval_tranfser_peo),  getResources().getString(R.string.approval_please_write_transfer_must));
        addView(jiaojiePeo,10);

        startTime = new ChooseView(mContext);
        startTime.inIt(ChooseView.Type.time, getResources().getString(R.string.approval_contact_start_time), 10, 0);
        addView(startTime,10);

        endTime = new ChooseView(mContext);
        endTime.inIt(ChooseView.Type.time, getResources().getString(R.string.approval_contact_end_time), 0, 10);
        addView(endTime,10);

        lizhiReason = new EdiTextItem(mContext);
        lizhiReason.init(EdiTextItem.LineType.moreLine, getResources().getString(R.string.approval_goaway_reason), getResources().getString(R.string.approval_write_goaway_time));
        addView(lizhiReason,10);

        jiaojieshixiang = new EdiTextItem(mContext);
        jiaojieshixiang.init(EdiTextItem.LineType.moreLine, getResources().getString(R.string.approval_transfer_thing),  getResources().getString(R.string.approval_please_write_transfer_thing));
        addView(jiaojieshixiang,10);

    }

    @Override
    public boolean onSubmitClick(View v) {
        return false;
    }

    @Override
    public void onPhotoUploadSuccess(List<String> appvalUsers, List<String> imgUrls) {

    }
}
