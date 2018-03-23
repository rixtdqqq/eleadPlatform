package com.elead.report.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.elead.eplatform.R;
import com.elead.views.BaseFragment;
import com.elead.views.CircularImageView;

/**
 * Created by Administrator on 2016/10/11 0011.
 */

public class ReportMainFragment extends BaseFragment {
    private View view;
    private LinearLayout fragment_main_container;

    private View attendanceView;
    private CircularImageView circleView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (null == view) {
            view = inflater.inflate(R.layout.report_fragment_main, container, false);
        }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (null == fragment_main_container) {
            fragment_main_container = (LinearLayout) view.findViewById(R.id.fragment_main_container);
            attendanceView = LayoutInflater.from(mContext).inflate(R.layout.report_m_attendance, null);
            fragment_main_container.addView(attendanceView);
            View view = LayoutInflater.from(mContext).inflate(R.layout.report_entity_approval, null);
            fragment_main_container.addView(view);
            intView();
        }
    }

    private void intView(){
        circleView = (CircularImageView) attendanceView.findViewById(R.id.circleView);
    }
}
