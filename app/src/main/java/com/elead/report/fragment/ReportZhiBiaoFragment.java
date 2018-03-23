package com.elead.report.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.elead.eplatform.R;
import com.elead.views.BaseFragment;

import static com.elead.eplatform.R.id.fragment_mine_container;

/**
 * Created by Administrator on 2016/10/11 0011.
 */

public class ReportZhiBiaoFragment extends BaseFragment {
    private View view;
    private LinearLayout fragment_zhibiao_container;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (null == view) {
            view = inflater.inflate(R.layout.report_fragment_zhibiao, container, false);
        }
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (null == fragment_zhibiao_container) {
            fragment_zhibiao_container = (LinearLayout) view.findViewById(R.id.fragment_zhibiao_container);
//        View mView = LayoutInflater.from(mContext).inflate(R.layout.report_entity_approval, null);
//        fragment_zhibiao_container.initItems(mView);

        }
    }
}
