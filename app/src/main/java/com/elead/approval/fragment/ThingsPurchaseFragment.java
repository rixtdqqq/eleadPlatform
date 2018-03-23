package com.elead.approval.fragment;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.elead.approval.widget.AddOrDeleteView;
import com.elead.approval.widget.EdiTextItem;
import com.elead.eplatform.R;

import java.util.List;

/**
 * 物品申购 Fragment
 *
 * Created by Administrator on 2016/10/13 0013.
 */

public class ThingsPurchaseFragment extends ApprovalBaseFragment {


    private AddOrDeleteView addOrDeleteView;


    @Override
    public void initView(Context context) {
    getActivity().setTitle(mContext.getResources().getString(R.string.approval_my_things_purchase));


        addOrDeleteView = new AddOrDeleteView(mContext,mContext.getResources().getString(R.string.approval_detail) ) {
            @Override
            public View[] initItems() {
                EdiTextItem thingsName = new EdiTextItem(mContext);
                thingsName.init(EdiTextItem.LineType.signLine, mContext.getResources().getString(R.string.approval_things_name), mContext.getResources().getString(R.string.approval_please_input));

                EdiTextItem specifications = new EdiTextItem(mContext);
                specifications.init(EdiTextItem.LineType.signLine, mContext.getResources().getString(R.string.approval_specifications_type),mContext.getResources().getString(R.string.approval_please_input_specifications_type));

                EdiTextItem money = new EdiTextItem(mContext);
                money.init(EdiTextItem.LineType.signLine,mContext.getResources().getString(R.string.approval_single_money), mContext.getResources().getString(R.string.approval_please_input));

                EdiTextItem Number = new EdiTextItem(mContext);
                Number.init(EdiTextItem.LineType.signLine,mContext.getResources().getString(R.string.approval_carnum), mContext.getResources().getString(R.string.approval_please_input));

                EdiTextItem allMoney = new EdiTextItem(mContext);
                allMoney.init(EdiTextItem.LineType.signLine, mContext.getResources().getString(R.string.approval_things_all_money), mContext.getResources().getString(R.string.approval_please_input));

                EdiTextItem usePath = new EdiTextItem(mContext);
                usePath .init(EdiTextItem.LineType.signLine, mContext.getResources().getString(R.string.approval_usefully), mContext.getResources().getString(R.string.approval_please_input_usefully_pass));


                return new View[]{thingsName,specifications, money,Number,allMoney,usePath };
            }
        };
        addView(addOrDeleteView,10);


        TextView v = new TextView(mContext);
        v.setHint(mContext.getResources().getString(R.string.approval_all_all_money));
        v.setGravity(Gravity.CENTER_VERTICAL);
        addView(v,0);

    }

    @Override
    public boolean onSubmitClick(View v) {
        return false;
    }

    @Override
    public void onPhotoUploadSuccess(List<String> appvalUsers, List<String> imgUrls) {

    }
}
