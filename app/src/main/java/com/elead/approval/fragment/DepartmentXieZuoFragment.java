package com.elead.approval.fragment;

import android.content.Context;
import android.view.View;

import com.elead.approval.widget.AddOrDeleteView;
import com.elead.approval.widget.ChooseView;
import com.elead.approval.widget.EdiTextItem;
import com.elead.eplatform.R;

import java.util.List;

/**
 * 我的协作 Fragment
 * Created by Administrator on 2016/10/12 0012.
 */

public class DepartmentXieZuoFragment extends ApprovalBaseFragment {


    private EdiTextItem yuQiTarget,xieZuoReason,faQiPerson,beiZhu;
    private AddOrDeleteView addOrDeleteView;



    @Override
    public void initView(Context context) {
        getActivity().setTitle(mContext.getResources().getString(R.string.approval_my_cooperation));

        xieZuoReason = new EdiTextItem(mContext);
        xieZuoReason.init(EdiTextItem.LineType.signLine, mContext.getResources().getString(R.string.approval_cooperation_reason),mContext.getResources().getString(R.string.approval_please_write_must));
        addView(xieZuoReason,10);

        yuQiTarget = new EdiTextItem(mContext);
        yuQiTarget.init(EdiTextItem.LineType.signLine, mContext.getResources().getString(R.string.approval_expect_target),mContext.getResources().getString(R.string.approval_please_write_must));
        addView(yuQiTarget,10);

       faQiPerson = new EdiTextItem(mContext);
       faQiPerson.init(EdiTextItem.LineType.signLine, mContext.getResources().getString(R.string.approval_sponsor),mContext.getResources().getString(R.string.approval_please_write_must));
        addView(faQiPerson,10);

        addOrDeleteView= new AddOrDeleteView(mContext,mContext.getResources().getString(R.string.approval_cooperation)){
            @Override
            public View[] initItems() {

                EdiTextItem xieZuoPerson = new EdiTextItem(mContext);
                xieZuoPerson.init(EdiTextItem.LineType.signLine,mContext.getResources().getString(R.string.approval_cooperation_other),mContext.getResources().getString(R.string.approval_please_write_must));
                EdiTextItem xieZuoPersonItem = new EdiTextItem(mContext);
                xieZuoPersonItem.init(EdiTextItem.LineType.moreLine,mContext.getResources().getString(R.string.approval_cooperation_item),mContext.getResources().getString(R.string.approval_please_write_must));
                ChooseView starTime = new ChooseView(mContext);
                starTime.inIt(ChooseView.Type.time,mContext.getResources().getString(R.string.approval_complete_date),0,0);

                return new View[]{xieZuoPerson, xieZuoPersonItem, starTime};


            }
        };

        addView(addOrDeleteView,0);


       beiZhu = new EdiTextItem(mContext);
       beiZhu.init(EdiTextItem.LineType.moreLine,mContext.getResources().getString(R.string.approval_note),mContext.getResources().getString(R.string.please_write));
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
