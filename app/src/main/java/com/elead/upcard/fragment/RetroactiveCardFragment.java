package com.elead.upcard.fragment;

import android.content.Context;
import android.view.View;

import com.elead.approval.fragment.ApprovalBaseFragment;
import com.elead.approval.widget.ChooseView;
import com.elead.approval.widget.EdiTextItem;
import com.elead.eplatform.R;

import java.util.List;

/**
 * 补签卡
 * Created by Administrator on 2016/12/29 0029.
 */

public class RetroactiveCardFragment extends ApprovalBaseFragment {


    private ChooseView startTime;
    private ChooseView endTime;
    private EdiTextItem retroactiveReson;
    private ChooseView total;

    @Override
    public void initView(Context context) {
        startTime = new ChooseView(context);
        startTime.setRightText(true);
        startTime.inIt(ChooseView.Type.No_arrow, getResources().getString(R.string.approval_starttime), 10, 0);
       // startTime.setRightText("2016年11月23日 08:30");
        addView(startTime, 10);

        endTime = new ChooseView(context);
        endTime.setRightText(true);
        endTime.inIt(ChooseView.Type.No_arrow, getResources().getString(R.string.approval_endtime), 10, 0);
        //endTime.setRightText("2016年11月23日 18:00");
        addView(endTime, 0);

        total= new ChooseView(context);
        total.setRightText(false);
        total.inIt(ChooseView.Type.No_arrow, getResources().getString(R.string.total_accout), 10, 0);

        addView(total,10);

        retroactiveReson = new EdiTextItem(context);
        retroactiveReson.init(EdiTextItem.LineType.moreLine, getResources().getString(R.string.retroactive_reason), getResources().getString(R.string.mandatory_input));
        addView(retroactiveReson,10);

       // choosePhotoL.setVisibility(View.GONE);
        flags=false;

    }

    @Override
    public boolean onSubmitClick(View v) {
        return false;
    }

    @Override
    public void onPhotoUploadSuccess(List<String> appvalUsers, List<String> imgUrls) {

    }
}
