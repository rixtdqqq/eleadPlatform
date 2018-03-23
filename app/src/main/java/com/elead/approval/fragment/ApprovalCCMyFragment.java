package com.elead.approval.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.elead.activity.BaseActivity;
import com.elead.approval.activity.ApprovalCommonActivity;
import com.elead.approval.entity.ApprovalWaitEntity;
import com.elead.eplatform.R;
import com.elead.utils.Util;
import com.elead.views.BaseCommonAdapter;
import com.elead.views.BaseFragment;
import com.elead.views.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * @desc 抄送我的
 * @auth Created by mujun.xu on 2016/9/14 0014.
 */
public class ApprovalCCMyFragment extends BaseFragment/* implements TopBarView.OnClickListener*/ {

    private View mView;
    private ApprovalCommonActivity mContext;
    private List<ApprovalWaitEntity> entityList = new ArrayList<ApprovalWaitEntity>();
    private BaseCommonAdapter adapter;
    private ListView mListView;
    private LinearLayout mLlEmpty;
//    private TopBarView titleView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (null == mView) {
            mView = inflater.inflate(R.layout.approval_f_cc_my, container, false);
            setUpView();
        }
        return mView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = (ApprovalCommonActivity) context;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addItem();
        if (Util.isNotEmpty(entityList)) {
            initAdapter();
        } else {
            mListView.setVisibility(View.GONE);
            mLlEmpty.setVisibility(View.VISIBLE);
        }
    }

    protected void setUpView() {
        mContext.setTitle(getResources().getString(R.string.cc_my), BaseActivity.TitleType.NORMAL);
        mListView = (ListView) mView.findViewById(R.id.lv_cc_my);
        mLlEmpty = (LinearLayout) mView.findViewById(R.id.ll_empty);
        /*titleView = (TopBarView) mView.findViewById(R.id.cc_my_titleView);
        titleView.dismissSeacher();
        titleView.dismissSetting();
        titleView.dismissRightView();
        titleView.toggleCenterView(getResources().getString(R.string.cc_my));
        titleView.toggleLeftView(R.drawable.title_back);
        titleView.setClickListener(this);*/
    }

    private void addItem() {
        for (int i = 0; i < 7; i++) {
            ApprovalWaitEntity entity = new ApprovalWaitEntity();
            entity.setType("事假");
            entity.setName("林一");
            entity.setDate("2016/0" + (i + 1) + "/0" + i);
            entityList.add(entity);
        }
    }

    private void initAdapter() {
        if (null == adapter) {

            adapter = new BaseCommonAdapter<ApprovalWaitEntity>(mContext, entityList, R.layout.approval_i_waiting_for_my_approval) {

                @Override
                public void convert(ViewHolder holder, ApprovalWaitEntity approvalWaitEntity, int position) {
                    holder.setText(R.id.tv_topValue, approvalWaitEntity.getType());
                    holder.setText(R.id.tv_bottomValue, approvalWaitEntity.getName());
                    holder.setText(R.id.tv_date, approvalWaitEntity.getDate());
                }
            };
            mListView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

   /* @Override
    public void onRightBtnClick(View v) {

    }

    @Override
    public void onLeftBtnClick(View v) {
        mContext.finish();
    }*/
}
