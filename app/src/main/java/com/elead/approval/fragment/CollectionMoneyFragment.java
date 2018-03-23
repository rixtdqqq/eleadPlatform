package com.elead.approval.fragment;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.elead.approval.widget.ChooseView;
import com.elead.approval.widget.EdiTextItem;
import com.elead.eplatform.R;

import java.util.List;

/**
 * 收款 Fragment
 * Created by Administrator on 2016/10/13 0013.
 */

public class CollectionMoneyFragment extends ApprovalBaseFragment {


    private ChooseView CollectStyle;


    private EdiTextItem customerName;
    private EdiTextItem customerCompany;
    private EdiTextItem collectReason;
    private ChooseView  collectStyle;
    private EdiTextItem collectMoney;
    private EdiTextItem linksPhone;


    @Override
    public void initView(Context context) {
        getActivity().setTitle(mContext.getResources().getString(R.string.approval_my_collect_money));

        customerName = new EdiTextItem(mContext);
        customerName.init(EdiTextItem.LineType.signLine, mContext.getResources().getString(R.string.approval_customer_name),mContext.getResources().getString(R.string.approval_please_write_must) );
        addView(customerName,10);

        customerCompany = new EdiTextItem(mContext);
        customerCompany.init(EdiTextItem.LineType.signLine,mContext.getResources().getString(R.string.approval_customer_company) ,mContext.getResources().getString(R.string.approval_please_write_must) );
        addView(customerCompany,10);

        collectReason = new EdiTextItem(mContext);
        collectReason.init(EdiTextItem.LineType.signLine,mContext.getResources().getString(R.string.approval_collect_money_reason) , mContext.getResources().getString(R.string.approval_please_write_must));
        addView(collectReason,10);


        collectStyle=new ChooseView(mContext);
        collectStyle.inIt(ChooseView.Type.other,mContext.getResources().getString(R.string.approval_collect_money_way),mContext.getResources().getString(R.string.approval_please_write_must),10,0,new String[]{mContext.getResources().getString(R.string.approval_choose),mContext.getResources().getString(R.string.approval_money),mContext.getResources().getString(R.string.approval_transfer),mContext.getResources().getString(R.string.approval_pay_treasure)});
        addView(collectStyle,10);




        collectMoney= new EdiTextItem(mContext);
        collectMoney.init(EdiTextItem.LineType.signLine, mContext.getResources().getString(R.string.approval_collect_money), mContext.getResources().getString(R.string.please_write));
        addView(collectMoney,10);

        TextView v = new TextView(mContext);
        v.setHint("大写 :");
        //v.setGravity(Gravity.CENTER_VERTICAL);
        v.setPadding(0,5,0,0);
        addView(v,0);

        linksPhone= new EdiTextItem(mContext);
        linksPhone.init(EdiTextItem.LineType.signLine, mContext.getResources().getString(R.string.approval_link_phone), mContext.getResources().getString(R.string.please_write));
        addView(linksPhone,0);
    }

    @Override
    public boolean onSubmitClick(View v) {
        return false;
    }

    @Override
    public void onPhotoUploadSuccess(List<String> appvalUsers, List<String> imgUrls) {

    }
}
