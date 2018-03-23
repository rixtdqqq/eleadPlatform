package com.elead.approval.fragment;

import android.content.Context;
import android.text.InputType;
import android.view.View;

import com.elead.approval.widget.AddOrDeleteView;
import com.elead.approval.widget.ChooseView;
import com.elead.approval.widget.EdiTextItem;
import com.elead.eplatform.R;

import java.util.List;

/**
 * Created by Administrator on 2016/10/12 0012.
 */

public class ApprovalUserCarApply extends ApprovalBaseFragment {
    private EdiTextItem department, why, startPlace, backPlace;
    private ChooseView useDate, backDate;
    private AddOrDeleteView carDetail;

    @Override
    public void initView(Context context) {
        getActivity().setTitle(getResources().getString(R.string.approval_my_use_car));
        department = new EdiTextItem(mContext);
        department.init(EdiTextItem.LineType.signLine, getResources().getString(R.string.approval_apply_department), getResources().getString(R.string.approval_please_write_department));
        addView(department,10);

        why = new EdiTextItem(mContext);
        why.init(EdiTextItem.LineType.moreLine, getResources().getString(R.string.approval_usecar_reson), getResources().getString(R.string.approval_pleast_write_user_reason));
        addView(why,10);

        startPlace = new EdiTextItem(mContext);
        startPlace.init(EdiTextItem.LineType.signLine, getResources().getString(R.string.approval_start_place), getResources().getString(R.string.approval_pleast_write_start_place));
        addView(startPlace,10);

        backPlace = new EdiTextItem(mContext);
        backPlace.init(EdiTextItem.LineType.signLine, getResources().getString(R.string.approval_back_place), getResources().getString(R.string.approval_pleast_write_back_place));
        addView(backPlace,10);

        useDate = new ChooseView(mContext);
        useDate.inIt(ChooseView.Type.time, getResources().getString(R.string.approval_use_car_date), 10, 0);
        addView(useDate,10);

        backDate = new ChooseView(mContext);
        backDate.inIt(ChooseView.Type.time, getResources().getString(R.string.approval_back_date), 0, 0);
        addView(backDate,0);

        carDetail = new AddOrDeleteView(mContext, getResources().getString(R.string.approval_car_details)) {
            @Override
            public View[] initItems() {
                EdiTextItem carType = new EdiTextItem(mContext);
                carType.init(EdiTextItem.LineType.signLine, getResources().getString(R.string.approval_car_type), getResources().getString(R.string.approval_please_write_must));

                EdiTextItem num = new EdiTextItem(mContext);
                num.init(EdiTextItem.LineType.signLine, InputType.TYPE_NUMBER_VARIATION_NORMAL, getResources().getString(R.string.approval_carnum), getResources().getString(R.string.approval_please_write_must));

                EdiTextItem otherOder = new EdiTextItem(mContext);
                otherOder.init(EdiTextItem.LineType.signLine, getResources().getString(R.string.approval_other_order), getResources().getString(R.string.please_write));

                return new View[]{carType, num, otherOder};

            }
        };
        addView(carDetail,0);

    }

    @Override
    public boolean onSubmitClick(View v) {
        return false;
    }

    @Override
    public void onPhotoUploadSuccess(List<String> appvalUsers, List<String> imgUrls) {

    }
}
