package com.elead.approval.fragment;

import android.content.Context;
import android.view.View;

import com.elead.approval.widget.AddOrDeleteView;
import com.elead.approval.widget.ChooseView;
import com.elead.approval.widget.EdiTextItem;
import com.elead.eplatform.R;

import java.util.List;

/**
 * 采购
 */

public class ApprovalShoping extends ApprovalBaseFragment {
    private EdiTextItem toApplyThing, note;
    private ChooseView shopType, hopeSubDate, payWay;
    private AddOrDeleteView shopDetails;

    @Override
    public void initView(Context context) {
        getActivity().setTitle(getResources().getString(R.string.approval_my_shop));
        toApplyThing = new EdiTextItem(mContext);
        toApplyThing.init(EdiTextItem.LineType.signLine, getResources().getString(R.string.approval_apply_reason), getResources().getString(R.string.approval_pleast_write_apply_reason));
        addView(toApplyThing,10);

        shopType = new ChooseView(mContext);
        shopType.inIt(ChooseView.Type.other, getResources().getString(R.string.approval_shop_type), getResources().getString(R.string.approval_choose), 10, 0, getResources().getStringArray(R.array.approval_shop_types));
        addView(shopType,10);

        hopeSubDate = new ChooseView(mContext);
        hopeSubDate.inIt(ChooseView.Type.time, getResources().getString(R.string.approval_hope_delivery_date), 10, 0);
        addView(hopeSubDate,10);

        shopDetails = new AddOrDeleteView(mContext, getResources().getString(R.string.approval_shop_detils)) {
            @Override
            public View[] initItems() {
                EdiTextItem name = new EdiTextItem(mContext);
                name.init(EdiTextItem.LineType.signLine, getResources().getString(R.string.approval_name));
                EdiTextItem specifications = new EdiTextItem(mContext);
                specifications.init(EdiTextItem.LineType.signLine, getResources().getString(R.string.approval_specifications));
                EdiTextItem num = new EdiTextItem(mContext);
                num.init(EdiTextItem.LineType.signLine, getResources().getString(R.string.approval_count));
                EdiTextItem unit = new EdiTextItem(mContext);
                unit.init(EdiTextItem.LineType.signLine, getResources().getString(R.string.approval_company));
                EdiTextItem price = new EdiTextItem(mContext);
                price.init(EdiTextItem.LineType.signLine, getResources().getString(R.string.approval_price));
                return new View[]{name, specifications, num, unit, price};
            }
        };
        addView(shopDetails,0);


        payWay = new ChooseView(mContext);
        payWay.inIt(ChooseView.Type.other, getResources().getString(R.string.approval_pay_way ), getResources().getString(R.string.approval_choose), 10, 0, getResources().getStringArray(R.array.approval_pay_ways2));
        addView(payWay,10);
        note = new EdiTextItem(mContext);
        note.init(EdiTextItem.LineType.moreLine, getResources().getString(R.string.approval_note ), getResources().getString(R.string.please_write));
        addView(note,10);
    }

    @Override
    public boolean onSubmitClick(View v) {
        return false;
    }

    @Override
    public void onPhotoUploadSuccess(List<String> appvalUsers, List<String> imgUrls) {

    }
}
