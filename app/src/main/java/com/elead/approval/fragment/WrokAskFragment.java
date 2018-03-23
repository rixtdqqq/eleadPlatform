package com.elead.approval.fragment;

import android.content.Context;
import android.view.View;

import com.elead.approval.widget.ChooseView;
import com.elead.approval.widget.EdiTextItem;
import com.elead.eplatform.R;

import java.util.List;

/**
 * 工作请示 Fragment
 * Created by Administrator on 2016/10/13 0013.
 */

public class WrokAskFragment extends ApprovalBaseFragment {



    private ChooseView JingjiDr;
    private ChooseView Date;


    private EdiTextItem reason;
    private ChooseView jingJiDr;
    private ChooseView date;
    private EdiTextItem content;



    @Override
    public void initView(Context context) {
       getActivity().setTitle(mContext.getResources().getString(R.string.approval_my_wrok_ask));

        reason = new EdiTextItem(mContext);
        reason.init(EdiTextItem.LineType.signLine,mContext.getResources().getString(R.string.approval_reason) ,mContext.getResources().getString(R.string.approval_please_input_reason));
        addView(reason,10);

        jingJiDr=new ChooseView(mContext);
        jingJiDr.inIt(ChooseView.Type.other,mContext.getResources().getString(R.string.approval_urgency_level),mContext.getResources().getString(R.string.approval_choose),10,0,new String[]{mContext.getResources().getString(R.string.approval_choose),mContext.getResources().getString(R.string.approval_urgenc),mContext.getResources().getString(R.string.approval_ordinary),mContext.getResources().getString(R.string.approval_suspension)});
        addView(jingJiDr,10);

        date=new ChooseView(mContext);
        date.inIt(ChooseView.Type.time,mContext.getResources().getString(R.string.approval_date),10,0);
        addView(date,10);


        content = new EdiTextItem(mContext);
        content.init(EdiTextItem.LineType.moreLine,mContext.getResources().getString(R.string.approval_specific_content) , mContext.getResources().getString(R.string.approval_please_input_specific_content));
        addView(content,10);


    }

    @Override
    public boolean onSubmitClick(View v) {
        return false;
    }

    @Override
    public void onPhotoUploadSuccess(List<String> appvalUsers, List<String> imgUrls) {

    }
}
