package com.elead.approval.fragment;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.elead.approval.widget.AddOrDeleteView;
import com.elead.approval.widget.ChooseView;
import com.elead.approval.widget.EdiTextItem;
import com.elead.eplatform.R;

import java.util.List;

/**
 * 订货审批 Fragment
 * Created by Administrator on 2016/10/13 0013.
 */

public class OrderApplyFragment extends ApprovalBaseFragment {


    private EdiTextItem orderUnit;
    private ChooseView orderDate;



    private AddOrDeleteView addOrDeleteView;
    private EdiTextItem orderInstructions;
    private EdiTextItem duiFangFuzheRen;


    @Override
    public void initView(Context context) {
        getActivity().setTitle(mContext.getResources().getString(R.string.approval_my_order_approval));
        orderUnit = new EdiTextItem(mContext);
        orderUnit.init(EdiTextItem.LineType.signLine, mContext.getResources().getString(R.string.approval_order_unit),mContext.getResources().getString(R.string.please_write) );
        addView(orderUnit,10);

        duiFangFuzheRen = new EdiTextItem(mContext);
        duiFangFuzheRen.init(EdiTextItem.LineType.signLine,mContext.getResources().getString(R.string.approval_other_charger),mContext.getResources().getString(R.string.please_write));
        addView( duiFangFuzheRen,10);

        orderDate = new ChooseView(mContext);
        orderDate.inIt(ChooseView.Type.time, mContext.getResources().getString(R.string.approval_order_date), 10, 0);
        addView(orderDate,0);





        addOrDeleteView = new AddOrDeleteView(mContext, mContext.getResources().getString(R.string.approval_order)) {
            @Override
            public View[] initItems() {
                EdiTextItem productName = new EdiTextItem(mContext);
                productName.init(EdiTextItem.LineType.signLine,mContext.getResources().getString(R.string.approval_product_name) , mContext.getResources().getString(R.string.approval_please_input));
                EdiTextItem ordeNumber = new EdiTextItem(mContext);
                ordeNumber.init(EdiTextItem.LineType.signLine,mContext.getResources().getString(R.string.approval_order_amout) , mContext.getResources().getString(R.string.please_write));

                EdiTextItem Money = new EdiTextItem(mContext);
                Money.init(EdiTextItem.LineType.signLine, mContext.getResources().getString(R.string.approval_order_money), mContext.getResources().getString(R.string.please_write));


                return new View[]{productName, ordeNumber, Money};
            }
        };
        addView(addOrDeleteView,0);

        TextView v = new TextView(mContext);
        v.setHint(mContext.getResources().getString(R.string.approval_all_order_money));
        v.setGravity(Gravity.CENTER_VERTICAL);
        addView(v,0);

        orderInstructions = new EdiTextItem(mContext);
        orderInstructions.init(EdiTextItem.LineType.moreLine,mContext.getResources().getString(R.string.approval_order_explain),mContext.getResources().getString(R.string.please_write));
        addView(orderInstructions,0);


    }

    @Override
    public boolean onSubmitClick(View v) {
        return false;
    }

    @Override
    public void onPhotoUploadSuccess(List<String> appvalUsers, List<String> imgUrls) {

    }
}
