package com.elead.approval.fragment;

import android.content.Context;
import android.text.InputType;
import android.util.Log;
import android.view.View;

import com.elead.approval.entity.BusinessApprovalInfo;
import com.elead.approval.widget.AddOrDeleteView;
import com.elead.approval.widget.ChooseView;
import com.elead.approval.widget.EdiTextItem;
import com.elead.eplatform.R;

import java.util.List;



/**
 * 出差
 * Created by Administrator on 2016/9/23 0023.
 */

public class BusinessRripFragment extends ApprovalBaseFragment {

    private AddOrDeleteView addOrDeleteView;
    private BusinessApprovalInfo businessApprovalInfo;
    private EdiTextItem chuchaiDays, chuchaiShiYou;

    @Override
    public void initView(Context context) {
        getActivity().setTitle(getResources().getString(R.string.approval_my_business_trip));
        addOrDeleteView = new AddOrDeleteView(context, getResources().getString(R.string.approval_trip_details)) {
            @Override
            public View[] initItems() {
                EdiTextItem ediTextItem = new EdiTextItem(mContext);
                ediTextItem.init(EdiTextItem.LineType.signLine, getResources().getString(R.string.approval_trip_place), getResources().getString(R.string.approval_trip_place_hint));
                ChooseView starTime = new ChooseView(mContext);
                ChooseView endTime = new ChooseView(mContext);
                return new View[]{ediTextItem, starTime, endTime};
            }
        };
        addView(addOrDeleteView,10);

        chuchaiDays = new EdiTextItem(context);
        chuchaiDays.init(EdiTextItem.LineType.signLine, InputType.TYPE_CLASS_NUMBER, getResources().getString(R.string.approval_trip_days), getResources().getString(R.string.approval_trip_days_hint));
        addView(chuchaiDays,10);

        chuchaiShiYou = new EdiTextItem(context);
        chuchaiShiYou.init(EdiTextItem.LineType.moreLine, getResources().getString(R.string.approval_trip_reason), getResources().getString(R.string.approval_trip_reason_hint));
        addView(chuchaiShiYou,10);


    }

    @Override
    public boolean onSubmitClick(View v) {
        boolean allEmpty = addOrDeleteView.isAllEmpty();
        Log.d(Tag, " " + allEmpty);
        if (chuchaiDays.isEmpty() || chuchaiShiYou.isEmpty() || allEmpty) {
            return false;
        } else {
            return true;
        }
    }

    ;

    @Override
    public void onPhotoUploadSuccess(List<String> appvalUsers, List<String> imgUrls) {
        /*businessApprovalInfo = new BusinessApprovalInfo();
        businessApprovalInfo.setBusInessMingXIInfos(addOrDeleteView.getMingXIInfos());
        businessApprovalInfo.setImageNames(imgUrls);
        businessApprovalInfo.setApprovalUsers(appvalUsers);
        businessApprovalInfo.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                finishSubmit(businessApprovalInfo);
            }
        });*/
    }
}
