package com.elead.approval.fragment;

import android.content.Context;
import android.view.View;

import com.elead.approval.widget.ChooseView;
import com.elead.approval.widget.EdiTextItem;
import com.elead.eplatform.R;

import java.util.List;

/**
 * 资质使用 Fragment
 * Created by Administrator on 2016/10/13 0013.
 */

public class ZizhiUseFragment extends ApprovalBaseFragment {


    private EdiTextItem ziZhiKind;
    private EdiTextItem operator;
    private EdiTextItem operatorDepartment;
    private ChooseView ifOutTake;
    private ChooseView jieYongTitme;
    private ChooseView returnTitme;


    private EdiTextItem useReason;
    private EdiTextItem beizhu;


    @Override
    public void initView(Context context) {
        getActivity().setTitle(mContext.getResources().getString(R.string.approval_my_qualifications_use));
        ziZhiKind = new EdiTextItem(mContext);
        ziZhiKind.init(EdiTextItem.LineType.signLine, mContext.getResources().getString(R.string.approval_qulifications_type),mContext.getResources().getString(R.string.approval_qulifications_tpye_example));
        addView(ziZhiKind,10);

        operator = new EdiTextItem(mContext);
        operator.init(EdiTextItem.LineType.signLine,mContext.getResources().getString(R.string.approval_agent), mContext.getResources().getString(R.string.please_write));
        addView(operator,10);

        operatorDepartment = new EdiTextItem(mContext);
        operatorDepartment.init(EdiTextItem.LineType.signLine, mContext.getResources().getString(R.string.approval_agent_department),mContext.getResources().getString(R.string.please_write));
        addView(operatorDepartment,10);


        ifOutTake = new ChooseView(mContext);
        ifOutTake.inIt(ChooseView.Type.other, mContext.getResources().getString(R.string.approval_whether_to_go), mContext.getResources().getString(R.string.approval_choose), 10, 0, new String[]{mContext.getResources().getString(R.string.approval_choose),mContext.getResources().getString(R.string.approval_need_to_go),mContext.getResources().getString(R.string.approval_no_need_to_go)});
        addView(ifOutTake,10);

        jieYongTitme = new ChooseView(mContext);
        jieYongTitme.inIt(ChooseView.Type.time,mContext.getResources().getString(R.string.approval_borrowing_time) , 10, 0);
        addView(jieYongTitme,10);


        returnTitme = new ChooseView(mContext);
        returnTitme.inIt(ChooseView.Type.time, mContext.getResources().getString(R.string.approval_return_time), 0, 0);
        addView(returnTitme,10);




        useReason = new EdiTextItem(mContext);
        useReason.init(EdiTextItem.LineType.signLine, mContext.getResources().getString(R.string.approval_usefully_reason),mContext.getResources().getString(R.string.approval_please_input) );
        addView(useReason,10);

        beizhu = new EdiTextItem(mContext);
        beizhu.init(EdiTextItem.LineType.signLine,mContext.getResources().getString(R.string.approval_note) , mContext.getResources().getString(R.string.please_write));
        addView(beizhu,10);
    }

    @Override
    public boolean onSubmitClick(View v) {
        return false;
    }

    @Override
    public void onPhotoUploadSuccess(List<String> appvalUsers, List<String> imgUrls) {

    }
}
