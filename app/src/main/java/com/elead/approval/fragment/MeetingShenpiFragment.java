package com.elead.approval.fragment;

import android.content.Context;
import android.view.View;

import com.elead.approval.widget.AddOrDeleteView;
import com.elead.approval.widget.ChooseView;
import com.elead.approval.widget.EdiTextItem;
import com.elead.eplatform.R;

import java.util.List;

/**
 * 会议审批 Fragment
 * Created by Administrator on 2016/10/13 0013.
 */

public class MeetingShenpiFragment extends ApprovalBaseFragment {


    private EdiTextItem meetingName;
    private ChooseView stratTime;
    private ChooseView endTime;
    private EdiTextItem meetingPlace;



    private ChooseView StratTime;
    private ChooseView EndTime;


    private EdiTextItem hoster;
    private AddOrDeleteView addOrDeleteView;



    @Override
    public void initView(Context context) {
        getActivity().setTitle(mContext.getResources().getString(R.string.approval_my_meeting_approval));

        meetingName = new EdiTextItem(mContext);
        meetingName.init(EdiTextItem.LineType.signLine,mContext.getResources().getString(R.string.approval_meeting_name), mContext.getResources().getString(R.string.please_write));
        addView(meetingName,10);


        stratTime = new ChooseView(mContext);
        stratTime.inIt(ChooseView.Type.time,mContext.getResources().getString(R.string.approval_starttime) , 10, 0);
        addView(stratTime,10);

        endTime = new ChooseView(mContext);
        endTime.inIt(ChooseView.Type.time,mContext.getResources().getString(R.string.approval_endtime) , 0, 0);
        addView(endTime,0);



        meetingPlace = new EdiTextItem(mContext);
        meetingPlace.init(EdiTextItem.LineType.signLine, mContext.getResources().getString(R.string.approval_meeting_place), mContext.getResources().getString(R.string.approval_please_input));
        addView(meetingPlace,10);

        hoster = new EdiTextItem(mContext);
        hoster.init(EdiTextItem.LineType.signLine, mContext.getResources().getString(R.string.approval_hoster), mContext.getResources().getString(R.string.please_write));
        addView(hoster,10);


        addOrDeleteView = new AddOrDeleteView(mContext,mContext.getResources().getString(R.string.approval_meeting_trip)) {
            @Override
            public View[] initItems() {
                EdiTextItem themeL = new EdiTextItem(mContext);
                themeL.init(EdiTextItem.LineType.signLine, mContext.getResources().getString(R.string.approval_meeting_theme), mContext.getResources().getString(R.string.please_write));

                EdiTextItem speaker = new EdiTextItem(mContext);
                speaker.init(EdiTextItem.LineType.signLine, mContext.getResources().getString(R.string.approval_spokesman), mContext.getResources().getString(R.string.please_write));


                return new View[]{themeL,speaker};
            }
        };
        addView(addOrDeleteView,0);


    }

    @Override
    public boolean onSubmitClick(View v) {
        return false;
    }

    @Override
    public void onPhotoUploadSuccess(List<String> appvalUsers, List<String> imgUrls) {

    }
}
