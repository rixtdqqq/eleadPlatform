package com.elead.approval.fragment;

import android.content.Context;
import android.view.View;

import com.elead.approval.widget.ChooseView;
import com.elead.approval.widget.EdiTextItem;
import com.elead.eplatform.R;

import java.util.List;

/**
 * 转正
 */

public class ApprovalPositive extends ApprovalBaseFragment {
    private ChooseView ruzhiDate;
    private EdiTextItem gangwei, gangweilijie, gongzuozongjie, yijianjianyi;

    @Override
    public void initView(Context context) {
        getActivity().setTitle(getResources().getString(R.string.approval_my_positive));
        ruzhiDate = new ChooseView(mContext);
        ruzhiDate.inIt(ChooseView.Type.time, getResources().getString(R.string.approval_loginin_date), 10, 0);
        addView(ruzhiDate,10);

        gangwei = new EdiTextItem(mContext);
        gangwei.init(EdiTextItem.LineType.moreLine, getResources().getString(R.string.approval_tryuse_job), getResources().getString(R.string.approval_pleast_write_tryuse_job));
        addView(gangwei,10);

        gangweilijie = new EdiTextItem(mContext);
        gangweilijie.init(EdiTextItem.LineType.moreLine, getResources().getString(R.string.approval_job_understand) ,getResources().getString(R.string.approval_pleast_whrite_job_understand));
        addView(gangweilijie,10);

        gongzuozongjie = new EdiTextItem(mContext);
        gongzuozongjie.init(EdiTextItem.LineType.moreLine,getResources().getString(R.string.approval_conclusion ), getResources().getString(R.string.approval_please_write_conclusion ));
        addView(gongzuozongjie,10);

        yijianjianyi = new EdiTextItem(mContext);
        yijianjianyi.init(EdiTextItem.LineType.moreLine, getResources().getString(R.string.approval_advice_to_company),  getResources().getString(R.string.approval_pleast_write_advice_to_company));
        addView(yijianjianyi,10);

    }

    @Override
    public boolean onSubmitClick(View v) {
        return false;
    }

    @Override
    public void onPhotoUploadSuccess(List<String> appvalUsers, List<String> imgUrls) {

    }
}
