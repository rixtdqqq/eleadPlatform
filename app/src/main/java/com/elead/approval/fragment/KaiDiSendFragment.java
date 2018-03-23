package com.elead.approval.fragment;

import android.content.Context;
import android.view.View;

import com.elead.approval.widget.ChooseView;
import com.elead.approval.widget.EdiTextItem;
import com.elead.eplatform.R;

import java.util.List;

/**
 * Created by Administrator on 2016/10/13 0013.
 */

public class KaiDiSendFragment extends ApprovalBaseFragment {





    private EdiTextItem youJiContent;
    private EdiTextItem kaidiName;
    private ChooseView youJiDate;
    private EdiTextItem kaiDiNumber;
    private EdiTextItem kaiDiDealWithRequire;
    private ChooseView qianShouRequire;
    private EdiTextItem beiZhu;



    @Override
    public void initView(Context context) {
        getActivity().setTitle(mContext.getResources().getString(R.string.approval_my_express_send));
        youJiContent = new EdiTextItem(mContext);
        youJiContent.init(EdiTextItem.LineType.signLine,mContext.getResources().getString(R.string.approval_send_content),mContext.getResources().getString(R.string.approval_please_input) );
        addView(youJiContent,10);


        kaidiName = new EdiTextItem(mContext);
        kaidiName.init(EdiTextItem.LineType.signLine, mContext.getResources().getString(R.string.approval_express_company_name),mContext.getResources().getString(R.string.approval_please_input) );
        addView(kaidiName,10);


        youJiDate = new ChooseView(mContext);
        youJiDate.inIt(ChooseView.Type.time,mContext.getResources().getString(R.string.approval_send_date) , 10, 0);
        addView(youJiDate,10);





        kaiDiNumber = new EdiTextItem(mContext);
        kaiDiNumber.init(EdiTextItem.LineType.signLine,mContext.getResources().getString(R.string.approval_express_number) ,mContext.getResources().getString(R.string.approval_please_input) );
        addView(kaiDiNumber,10);

        kaiDiDealWithRequire = new EdiTextItem(mContext);
        kaiDiDealWithRequire.init(EdiTextItem.LineType.moreLine,mContext.getResources().getString(R.string.approval_express_deal_requier), mContext.getResources().getString(R.string.please_write));
        addView(kaiDiDealWithRequire,10);

        qianShouRequire= new ChooseView(mContext);

        qianShouRequire.inIt(ChooseView.Type.other, mContext.getResources().getString(R.string.approval_sign_requrie),mContext.getResources().getString(R.string.approval_please_sign_up), 10, 0,new String[]{mContext.getResources().getString(R.string.approval_choose),mContext.getResources().getString(R.string.approval_single_sign_up),mContext.getResources().getString(R.string.approval_require_more_people_sign_up)});


        beiZhu= new EdiTextItem(mContext);
        beiZhu.init(EdiTextItem.LineType.moreLine,mContext.getResources().getString(R.string.approval_note) ,mContext.getResources().getString(R.string.please_write) );
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
