package com.elead.approval.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.elead.approval.activity.ApWaitingForMyApprovalActivity;
import com.elead.approval.entity.ApprovalWaitEntity;
import com.elead.eplatform.R;
import com.elead.utils.Util;
import com.elead.views.BaseCommonAdapter;
import com.elead.views.BaseFragment;
import com.elead.views.CircularImageView;
import com.elead.views.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * @desc 已审批
 * @auth Created by mujun.xu on 2016/9/14 0014.
 */
public class ApprovalAlreadyApproveFragment extends BaseFragment {

    private View mView;
    private ApWaitingForMyApprovalActivity mContext;
    private List<ApprovalWaitEntity> approvalWaitEntityList = new ArrayList<ApprovalWaitEntity>();
    private BaseCommonAdapter adapter;
    private ListView mListView;
    private LinearLayout mLlEmpty;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (null == mView) {
            mView = inflater.inflate(R.layout.approval_f_waiting_for_my_approval, container, false);
            setUpView();
        }
        return mView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = (ApWaitingForMyApprovalActivity) context;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addItem();
        if (Util.isNotEmpty(approvalWaitEntityList)) {
            initAdapter();
        } else {
            mListView.setVisibility(View.GONE);
            mLlEmpty.setVisibility(View.VISIBLE);
        }
    }

    protected void setUpView() {
        mListView = (ListView) mView.findViewById(R.id.lv_waiting_approval);
        mLlEmpty = (LinearLayout) mView.findViewById(R.id.ll_empty);
    }

    private void addItem() {
        for (int i = 0; i < 7; i++) {
            ApprovalWaitEntity entity = new ApprovalWaitEntity();
            entity.setType("审批完成");
            entity.setName("李四的外出公干");
            entity.setDate("昨天");
            approvalWaitEntityList.add(entity);
        }
    }

    private void initAdapter() {
        if (null == adapter) {

            adapter = new BaseCommonAdapter<ApprovalWaitEntity>(mContext, approvalWaitEntityList, R.layout.approval_i_waiting_for_my_approval) {

                @Override
                public void convert(ViewHolder holder, ApprovalWaitEntity approvalWaitEntity, int position) {
                    ((TextView) holder.getView(R.id.tv_topValue)).setTextColor(getResources().getColor(R.color.fontcolors4));
                    holder.setText(R.id.tv_topValue, approvalWaitEntity.getType());
                    holder.setText(R.id.tv_bottomValue, approvalWaitEntity.getName());
                    holder.setText(R.id.tv_date, approvalWaitEntity.getDate());
                    CircularImageView photo = holder.getView(R.id.my_photo_id);
                    photo.setBackgroundColor("李四");
                    photo.setText("李四");
                }
            };
            mListView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    public static ApprovalAlreadyApproveFragment newInstance() {
        ApprovalAlreadyApproveFragment fragment = new ApprovalAlreadyApproveFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    public RecycleView setRecyView() {
        recycleview = new RecycleView() {

            @Override
            public void recycleThisView() {
                if (mListView != null) {
                    mListView.removeAllViewsInLayout();
                    mListView = null;
                }
                if (approvalWaitEntityList != null) {
                    approvalWaitEntityList.clear();
                }
            }
        };
        return recycleview;
    }

    private RecycleView recycleview;

    public interface RecycleView {
        void recycleThisView();
    }
}
