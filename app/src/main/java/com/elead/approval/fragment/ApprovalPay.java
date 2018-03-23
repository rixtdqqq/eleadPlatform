package com.elead.approval.fragment;

import android.content.Context;
import android.text.InputType;
import android.view.View;

import com.elead.approval.widget.ChooseView;
import com.elead.approval.widget.EdiTextItem;
import com.elead.eplatform.R;

import java.util.List;

/**
 * 我的付款
 */

public class ApprovalPay extends ApprovalBaseFragment {
    private EdiTextItem shiyou, total, object, kaihuhang, yinhangzhanghu;
    private ChooseView way, date;

    @Override
    public void initView(Context context) {
        getActivity().setTitle(getResources().getString(R.string.approval_my_pay));
        shiyou = new EdiTextItem(mContext);
        shiyou.init(EdiTextItem.LineType.moreLine,
                getResources().getString(R.string.approval_pay_reason),
                getResources().getString(R.string.approval_please_write_pay_reason));
        addView(shiyou,10);

        total = new EdiTextItem(mContext);
        total.init(EdiTextItem.LineType.signLine, InputType.TYPE_NUMBER_VARIATION_NORMAL, getResources().getString(R.string.approval_pay_total), getResources().getString(R.string.approval_pleast_write_pay_total));
        addView(total,10);


        way = new ChooseView(mContext);
        way.inIt(ChooseView.Type.other, getResources().getString(R.string.approval_pay_way), getResources().getString(R.string.approval_choose_must), 10, 0, getResources().getStringArray(R.array.approval_pay_ways));
        addView(way,10);

        date = new ChooseView(mContext);
        date.inIt(ChooseView.Type.time, getResources().getString(R.string.approval_pay_date), 10, 0);
        addView(date,10);


        object = new EdiTextItem(mContext);
        object.init(EdiTextItem.LineType.signLine, getResources().getString(R.string.approval_pay_object), getResources().getString(R.string.approval_pleast_write_pay_object));
        addView(object,10);

        kaihuhang = new EdiTextItem(mContext);
        kaihuhang.init(EdiTextItem.LineType.signLine, getResources().getString(R.string.approval_open_bank), getResources().getString(R.string.approval_pleast_write_open_bank));
        addView(kaihuhang,10);

        yinhangzhanghu = new EdiTextItem(mContext);
        yinhangzhanghu.init(EdiTextItem.LineType.signLine, InputType.TYPE_NUMBER_VARIATION_NORMAL, getResources().getString(R.string.approval_bank_user), getResources().getString(R.string.approval_pleast_write_bank_user));
        addView(yinhangzhanghu,10);

    }

    @Override
    public boolean onSubmitClick(View v) {
        return false;
    }

    @Override
    public void onPhotoUploadSuccess(List<String> appvalUsers, List<String> imgUrls) {

    }
}
