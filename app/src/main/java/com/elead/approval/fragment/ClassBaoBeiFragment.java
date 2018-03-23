package com.elead.approval.fragment;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.elead.approval.widget.ChooseView;
import com.elead.approval.widget.EdiTextItem;
import com.elead.eplatform.R;

import java.util.List;

/**
 * 调课报备 Fragment
 * Created by Administrator on 2016/10/13 0013.
 */

public class ClassBaoBeiFragment extends ApprovalBaseFragment {



    private ChooseView TiaoKeDate;


    private ChooseView DuifaDate;
    private ChooseView DuifaJieChi;



    private ChooseView tiaoKeDate;
    private ChooseView jieChi;
    private ChooseView keMu;
    private EdiTextItem duifaName;
    private ChooseView duifaDate;
    private ChooseView duifaJieChi;
    private EdiTextItem beiZhu;


    @Override
    public void initView(Context context) {
       getActivity().setTitle(mContext.getResources().getString(R.string.approval_my_classes));

        TextView v = new TextView(mContext);
        v.setHint(mContext.getResources().getString(R.string.approval_the_classes_please_recommit_approval));
        v.setGravity(Gravity.CENTER_VERTICAL);
        addView(v,10);


        tiaoKeDate=new ChooseView(mContext);
        tiaoKeDate.inIt(ChooseView.Type.time,mContext.getResources().getString(R.string.approval_the_classes_date),10,0);
        addView(tiaoKeDate,10);

        jieChi=new ChooseView(mContext);
        jieChi.inIt(ChooseView.Type.other,mContext.getResources().getString(R.string.approval_section),mContext.getResources().getString(R.string.approval_choose_must),10,0,new String[]{mContext.getResources().getString(R.string.approval_choose),mContext.getResources().getString(R.string.approval_morning_class),mContext.getResources().getString(R.string.approval_one_am),mContext.getResources().getString(R.string.approval_two_am),mContext.getResources().getString(R.string.approval_three_am),mContext.getResources().getString(R.string.approval_four_am),mContext.getResources().getString(R.string.approval_one_pm),mContext.getResources().getString(R.string.approval_two_pm),mContext.getResources().getString(R.string.approval_three_pm),mContext.getResources().getString(R.string.approval_four_pm),mContext.getResources().getString(R.string.approval_night_class)});
        addView(jieChi,10);

        keMu=new ChooseView(mContext);
        keMu.inIt(ChooseView.Type.other,mContext.getResources().getString(R.string.approval_subjects),mContext.getResources().getString(R.string.approval_choose_must),10,0,new String[]{mContext.getResources().getString(R.string.approval_choose),mContext.getResources().getString(R.string.approval_Chinese_language_and_literature),mContext.getResources().getString(R.string.approval_English),mContext.getResources().getString(R.string.approval_mathematics),mContext.getResources().getString(R.string.approval_physical_education),mContext.getResources().getString(R.string.approval_musice),mContext.getResources().getString(R.string.approval_computer),mContext.getResources().getString(R.string.approval_natural_sciences)});



        duifaName = new EdiTextItem(mContext);
        duifaName.init(EdiTextItem.LineType.signLine, mContext.getResources().getString(R.string.approval_other_name), mContext.getResources().getString(R.string.approval_please_write_must));
        addView(duifaName,10);



        duifaDate=new ChooseView(mContext);
        duifaDate.inIt(ChooseView.Type.time,mContext.getResources().getString(R.string.approval_other_date),10,0);
        addView( duifaDate,10);

        duifaJieChi=new ChooseView(mContext);
        duifaJieChi.inIt(ChooseView.Type.other,mContext.getResources().getString(R.string.approval_other_subjects),mContext.getResources().getString(R.string.approval_choose_must),10,0,new String[]{mContext.getResources().getString(R.string.approval_choose),mContext.getResources().getString(R.string.approval_morning_class),mContext.getResources().getString(R.string.approval_one_am),mContext.getResources().getString(R.string.approval_two_am),mContext.getResources().getString(R.string.approval_three_am),mContext.getResources().getString(R.string.approval_four_am),mContext.getResources().getString(R.string.approval_one_pm),mContext.getResources().getString(R.string.approval_two_pm),mContext.getResources().getString(R.string.approval_three_pm),mContext.getResources().getString(R.string.approval_four_pm),mContext.getResources().getString(R.string.approval_night_class)});
        addView( duifaJieChi,10);





        beiZhu = new EdiTextItem(mContext);
        beiZhu.init(EdiTextItem.LineType.moreLine, mContext.getResources().getString(R.string.approval_note), mContext.getResources().getString(R.string.please_write));
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
