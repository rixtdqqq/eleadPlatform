package com.elead.approval.fragment;

import android.content.Context;
import android.text.InputType;
import android.view.View;

import com.elead.approval.widget.ChooseView;
import com.elead.approval.widget.EdiTextItem;
import com.elead.eplatform.R;

import java.util.List;

/**
 * 外出
 * Created by Administrator on 2016/9/23 0023.
 */

public class GoOutFragment extends ApprovalBaseFragment {

    private EdiTextItem waichuTime, waichuShiyou;
    private ChooseView startTime, endTime;

    @Override
    public void initView(Context context) {
        getActivity().setTitle(getResources().getString(R.string.approval_my_go_out));
        startTime = new ChooseView(context);
        startTime.inIt(ChooseView.Type.time, getResources().getString(R.string.approval_starttime), 10, 0);
        addView(startTime,10);

        endTime = new ChooseView(context);
        endTime.inIt(ChooseView.Type.time, getResources().getString(R.string.approval_endtime), 10, 0);
        addView(endTime,0);

        waichuTime = new EdiTextItem(context);
        waichuTime.init(EdiTextItem.LineType.signLine, InputType.TYPE_CLASS_NUMBER, getResources().getString(R.string.approval_go_out_time), getResources().getString(R.string.approval_go_out_time_hint));
        addView(waichuTime,10);

        waichuShiyou = new EdiTextItem(context);
        waichuShiyou.init(EdiTextItem.LineType.moreLine, getResources().getString(R.string.approval_go_out_reason), getResources().getString(R.string.approval_go_out_reason_hint));
        addView(waichuShiyou,10);

    }

    @Override
    public boolean onSubmitClick(View v) {
        if (waichuTime.isEmpty() || waichuShiyou.isEmpty()) {
            return false;
        } else {

            return true;
        }
    }

    @Override
    public void onPhotoUploadSuccess(List<String> appvalUsers, List<String> imgUrls) {

    }
}
