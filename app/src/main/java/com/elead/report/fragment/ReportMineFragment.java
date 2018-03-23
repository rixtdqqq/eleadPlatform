package com.elead.report.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.elead.activity.UseHelpActivity;
import com.elead.application.MyApplication;
import com.elead.eplatform.R;
import com.elead.views.BaseFragment;
import com.elead.views.CircularImageView;

/**
 * Created by Administrator on 2016/10/11 0011.
 */

public class ReportMineFragment extends BaseFragment implements View.OnClickListener {
    private View view;
    private LinearLayout fragment_mine_container;
    private CircularImageView my_photo;
    private TextView username;
    private RelativeLayout use_help_rel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (null == view) {
            view = inflater.inflate(R.layout.report_fragment_mine, container, false);
        }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (null == fragment_mine_container) {
            fragment_mine_container = (LinearLayout) view.findViewById(R.id.fragment_mine_container);
//        View mView = LayoutInflater.from(mContext).inflate(R.layout.report_entity_approval, null);
            my_photo = (CircularImageView) view.findViewById(R.id.my_photo_id);
            username = (TextView) view.findViewById(R.id.username_id);
            use_help_rel = (RelativeLayout) view.findViewById(R.id.use_help_rel);

            //String name = EMClient.getInstance().getCurrentUser();
            //EaseUser user = EaseUI.getInstance().getUserProfileProvider().getUser(name);

//            EaseUserUtils.setUserAvatar(getActivity(), name, my_photo);
//            EaseUserUtils.setUserNick(name, username);
            //()(getActivity().getApplication())
            if(MyApplication.user.name!=null){
                my_photo.setText(MyApplication.user.name);
                username.setText(MyApplication.user.name);
            }

            use_help_rel.setOnClickListener(this);
        }


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.use_help_rel:
                Intent helpIntent = new Intent(getActivity(), UseHelpActivity.class);
                startActivity(helpIntent);
                break;
        }
    }
}
