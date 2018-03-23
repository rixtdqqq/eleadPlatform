package com.elead.approval.fragment;

import android.content.Context;
import android.view.View;

import com.elead.approval.widget.AddOrDeleteView;
import com.elead.approval.widget.EdiTextItem;
import com.elead.eplatform.R;

import java.util.List;

/**
 * 合同条款
 */

public class ApprovalContractFragment extends ApprovalBaseFragment {
    private EdiTextItem cntractId, ourName, ourPeoName, otherName, otherPeoName;
    private AddOrDeleteView contractContents;

    @Override
    public void initView(Context context) {
        getActivity().setTitle(getResources().getString(R.string.approval_my_contract));
        cntractId = new EdiTextItem(mContext);
        cntractId.init(EdiTextItem.LineType.signLine, getResources().getString(R.string.approval_contract_code), getResources().getString(R.string.approval_please_write_must));
        addView(cntractId,10);

        ourName = new EdiTextItem(mContext);
        ourName.init(EdiTextItem.LineType.signLine,getResources().getString(R.string.approval_our_name), getResources().getString(R.string.approval_please_write_must));
        addView(ourName,10);
        ourPeoName = new EdiTextItem(mContext);
        ourPeoName.init(EdiTextItem.LineType.signLine, getResources().getString(R.string.approval_our_head), getResources().getString(R.string.please_write));
        addView(ourPeoName,10);
        otherName = new EdiTextItem(mContext);
        otherName.init(EdiTextItem.LineType.signLine,getResources().getString(R.string.approval_there_entrty_name),  getResources().getString(R.string.approval_please_write_must));
        addView(otherName,10);
        otherPeoName = new EdiTextItem(mContext);
        otherPeoName.init(EdiTextItem.LineType.signLine, getResources().getString(R.string.approval_there_head_name), getResources().getString(R.string.please_write));
        addView(otherPeoName,10);

        contractContents = new AddOrDeleteView(context,getResources().getString(R.string.approval_contract_terms)) {
            @Override
            public View[] initItems() {
                EdiTextItem content = new EdiTextItem(mContext);
                content.init(EdiTextItem.LineType.signLine, getResources().getString(R.string.approval_content),  getResources().getString(R.string.approval_please_write_must));
                return new View[]{content};
            }
        };
        addView(contractContents,0);
    }

    @Override
    public boolean onSubmitClick(View v) {
        return false;
    }

    @Override
    public void onPhotoUploadSuccess(List<String> appvalUsers, List<String> imgUrls) {

    }
}
