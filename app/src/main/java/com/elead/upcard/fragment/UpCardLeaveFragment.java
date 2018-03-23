package com.elead.upcard.fragment;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import com.elead.approval.fragment.ApprovalBaseFragment;
import com.elead.approval.widget.AddOrDeleteView;
import com.elead.approval.widget.ChooseView;
import com.elead.approval.widget.EdiTextItem;
import com.elead.eplatform.R;
import com.elead.upcard.view.HolidayChooseView;

import java.util.List;

/**
 * Created by Administrator on 2017/1/3 0003.
 */

public class UpCardLeaveFragment extends ApprovalBaseFragment {


    private ChooseView qingjialei;
    private ChooseView startTime;
    private ChooseView endTime;
    private AddOrDeleteView addOrDeleteView;
    private EdiTextItem qingjiaShiyou;
    private ChooseView total;
    private HolidayChooseView holidayLines;
    private HolidayChooseView thisUse;

    @Override
    public void initView(Context context) {
        mContext.setTitle(mContext.getResources().getStringArray(R.array.approval)[0]);
        qingjialei = new ChooseView(context);
        qingjialei.setRightText(true);
        qingjialei.inIt(ChooseView.Type.leave, mContext.getResources().getString(R.string.approval_leave_type), 10, 0);
        addView(qingjialei, 10);

        addOrDeleteView = new AddOrDeleteView(context, "时间") {
            public String startTimeText;

            @Override
            public View[] initItems() {

                startTime = new ChooseView(mContext);
                startTime.setRightText(true);
                startTime.inIt(ChooseView.Type.No_arrow, getResources().getString(R.string.approval_starttime), 0, 0);



                endTime = new ChooseView(mContext);
                endTime.setRightText(true);
                endTime.inIt(ChooseView.Type.No_arrow, getResources().getString(R.string.approval_endtime), 0, 0);



                return new View[]{startTime, endTime};
            }
        };
        addView(addOrDeleteView, 0);



        total = new ChooseView(context);
        total.setRightText(false);
        total.inIt(ChooseView.Type.No_arrow,mContext.getResources().getString(R.string.total_accout), 10, 0);
        addView(total, 10);

        holidayLines = new HolidayChooseView(context);
        holidayLines.init( mContext.getResources().getString(R.string.holiday_line), Color.parseColor("#2ec7c9"), true);
        addView(holidayLines, 10);

        thisUse = new HolidayChooseView(context);
        thisUse.setShowText(0 + "", 0 + "", 2.0 + "");
        thisUse.init( mContext.getResources().getString(R.string.the_use), Color.parseColor("#666666"), false);
        addView(thisUse, 0);


        qingjiaShiyou = new EdiTextItem(context);
        qingjiaShiyou.init(EdiTextItem.LineType.moreLine,mContext.getResources().getString(R.string.approval_leave_reason),mContext.getResources().getString(R.string.approval_leave_reason_hint));
        addView(qingjiaShiyou, 10);

    }

    @Override
    public boolean onSubmitClick(View v) {
        return false;
    }

    @Override
    public void onPhotoUploadSuccess(List<String> appvalUsers, List<String> imgUrls) {

    }
}
