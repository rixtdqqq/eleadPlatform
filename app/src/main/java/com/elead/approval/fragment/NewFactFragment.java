package com.elead.approval.fragment;

import android.content.Context;
import android.view.View;

import com.elead.approval.widget.ChooseView;
import com.elead.approval.widget.EdiTextItem;
import com.elead.eplatform.R;

import java.util.List;

/**
 * 新闻爆料 Fragment
 * Created by Administrator on 2016/10/13 0013.
 */

public class NewFactFragment extends ApprovalBaseFragment{






    private ChooseView newStyle;
    private EdiTextItem newsResource;
    private ChooseView jingjiDegree;
    private EdiTextItem describe;



    @Override
    public void initView(Context context) {

        getActivity().setTitle(mContext.getResources().getString(R.string.approval_my_news_fact));
        newStyle=new ChooseView(mContext);
        newStyle.inIt(ChooseView.Type.other,mContext.getResources().getString(R.string.approval_news_type),mContext.getResources().getString(R.string.approval_choose),10,0,new String[]{mContext.getResources().getString(R.string.approval_choose),mContext.getResources().getString(R.string.approval_emergent_events),mContext.getResources().getString(R.string.approval_be_heroic),mContext.getResources().getString(R.string.approval_continuous_reporting)});
        addView(newStyle,10);




        newsResource = new EdiTextItem(mContext);
        newsResource.init(EdiTextItem.LineType.moreLine,mContext.getResources().getString(R.string.approval_news_source) ,mContext.getResources().getString(R.string.approval_please_input) );
        addView( newsResource,10);

        jingjiDegree=new ChooseView(mContext);

        jingjiDegree.inIt(ChooseView.Type.other,mContext.getResources().getString(R.string.approval_urgency_level),mContext.getResources().getString(R.string.approval_choose),10,0,new String[]{mContext.getResources().getString(R.string.approval_choose),mContext.getResources().getString(R.string.approval_urgenc),mContext.getResources().getString(R.string.approval_ordinary)});



        addView(jingjiDegree,10);


        describe = new EdiTextItem(mContext);
        describe.init(EdiTextItem.LineType.moreLine,mContext.getResources().getString(R.string.approval_event_description), mContext.getResources().getString(R.string.approval_please_input));
        addView( describe,10);


    }

    @Override
    public boolean onSubmitClick(View v) {
        return false;
    }

    @Override
    public void onPhotoUploadSuccess(List<String> appvalUsers, List<String> imgUrls) {

    }
}
