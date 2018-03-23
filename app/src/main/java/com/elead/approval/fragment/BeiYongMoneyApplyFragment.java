package com.elead.approval.fragment;

import android.content.Context;
import android.view.View;

import com.elead.approval.widget.ChooseView;
import com.elead.approval.widget.EdiTextItem;

import java.util.List;

/**
 * 备用金申请 Fragment
 * <p>
 * Created by Administrator on 2016/10/13 0013.
 */

public class BeiYongMoneyApplyFragment extends ApprovalBaseFragment {



    private EdiTextItem applyPerson;
    private EdiTextItem department;
    private EdiTextItem reason;
    private EdiTextItem applyMoney;

    private ChooseView returnTime;



    private ChooseView useTime;


    private EdiTextItem cashier;
    private EdiTextItem beiZhu;


    @Override
    public void initView(Context context) {
        getActivity().setTitle("我的备用金申请");
        applyPerson = new EdiTextItem(mContext);
        applyPerson.init(EdiTextItem.LineType.signLine, "申请人", "请输入(必填)");
        addView(applyPerson,10);

        department = new EdiTextItem(mContext);
        department.init(EdiTextItem.LineType.signLine, "部门", "请输入(必填)");
        addView(department,10);

        reason = new EdiTextItem(mContext);
        reason.init(EdiTextItem.LineType.moreLine, "事由", "请输入");
        addView(reason,10);

        applyMoney = new EdiTextItem(mContext);
        applyMoney.init(EdiTextItem.LineType.signLine, "申请金额(元 )", "请输入");
        addView(applyMoney,10);

        useTime = new ChooseView(mContext);

        useTime.inIt(ChooseView.Type.time, "使用日期", 10, 0);
        addView(useTime,10);

        returnTime = new ChooseView(mContext);
        returnTime.inIt(ChooseView.Type.time, "归还日期", 0, 0);
        addView(returnTime,0);





        cashier = new EdiTextItem(mContext);
        cashier.init(EdiTextItem.LineType.signLine, "出纳", "请输入");
        addView(cashier,10);

        beiZhu = new EdiTextItem(mContext);
        beiZhu.init(EdiTextItem.LineType.moreLine, "备注", "请输入");
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
