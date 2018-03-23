package com.elead.approval.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.elead.application.MyApplication;
import com.elead.approval.activity.ApprovalManagerActivity;
import com.elead.approval.entity.ApprovalClassicModelEntity;
import com.elead.approval.entity.ApprovalListEntity;
import com.elead.eplatform.R;
import com.elead.utils.Util;
import com.elead.views.BaseCommonAdapter;
import com.elead.views.BaseFragment;
import com.elead.views.ViewHolder;

import java.util.ArrayList;
import java.util.List;


/**
 * @desc 经典模板
 * @auth Created by mujun.xu on 2016/9/14 0014.
 */
public class ApprovalCustomFragment extends BaseFragment {

    private View mView;
    private ListView mListView;
    private TextView tv_custom_no_data;
    private ApprovalManagerActivity mActivity;
    private BaseCommonAdapter adapter;
    private List<ApprovalClassicModelEntity> approvalClassicModelEntityList = new ArrayList<ApprovalClassicModelEntity>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (null == mView) {
            mView = inflater.inflate(R.layout.approval_f_custom, container, false);
            setUpView();
            initData();
        }
        return mView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (ApprovalManagerActivity) context;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    protected void setUpView() {
        mListView = (ListView) mView.findViewById(R.id.lv_custom);
        tv_custom_no_data = (TextView) mView.findViewById(R.id.tv_custom_no_data);
    }

    public void initData(){
        if (Util.isNotEmpty(Util.CLASSIC_MODEL_LIST)) {
            tv_custom_no_data.setVisibility(View.GONE);
            mListView.setVisibility(View.VISIBLE);
            approvalClassicModelEntityList.clear();
            approvalClassicModelEntityList.addAll(Util.CLASSIC_MODEL_LIST);
            initAdapter();
        } else {
            mListView.setVisibility(View.GONE);
            tv_custom_no_data.setVisibility(View.VISIBLE);
        }
    }

    public void initAdapter(){
        if (null == adapter) {

            adapter = new BaseCommonAdapter<ApprovalClassicModelEntity>(mActivity, approvalClassicModelEntityList, R.layout.approval_i_classic_model) {

                @Override
                public void convert(ViewHolder holder, ApprovalClassicModelEntity approvalClassicModelEntity, final int position) {
                    String name = approvalClassicModelEntity.getName();
                    holder.setText(R.id.tv_name, name);
                    holder.setText(R.id.tv_description, approvalClassicModelEntity.getDescription());
                    holder.setImageResource(R.id.img_left, Util.APPROVAL_IMAGES.get(name));
                    final TextView tv_add = holder.getView(R.id.tv_add);
                    tv_add.setText(mContext.getResources().getString(R.string.approval_deleted));
                    TextView tv_add_not_click = holder.getView(R.id.tv_add_not_click);
                    tv_add_not_click.setText(mContext.getResources().getString(R.string.approval_deleted));
                    if (approvalClassicModelEntity.isAdd()) {
                        tv_add_not_click.setVisibility(View.GONE);
                        tv_add.setVisibility(View.VISIBLE);
                    } else {
                        tv_add.setVisibility(View.GONE);
                        tv_add_not_click.setVisibility(View.VISIBLE);
                    }
                    tv_add.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ApprovalListEntity approvalListDataEntity = new ApprovalListEntity();
                            approvalClassicModelEntityList.get(position).setAdd(false);
                            initAdapter();
                            if (Util.isNotEmpty(Util.CLASSIC_MODEL_LIST)) {
                                Util.CLASSIC_MODEL_LIST.remove(approvalClassicModelEntityList.get(position));
                                approvalListDataEntity.setClassicModelList(Util.CLASSIC_MODEL_LIST);
                                approvalListDataEntity.setUserName(MyApplication.user.code);
//                                approvalListDataEntity.update(Util.APPROVAL_LIST_OBJECT_ID, new UpdateListener() {
//                                    @Override
//                                    public void done(BmobException e) {
//                                        if (e == null) {
//                                            approvalClassicModelEntityList.remove(position);
//                                            initAdapter();
//                                            if (!Util.isNotEmpty(Util.CLASSIC_MODEL_LIST)) {
//                                                mListView.setVisibility(View.GONE);
//                                                tv_custom_no_data.setVisibility(View.VISIBLE);
//                                            }
//                                            Util.IS_REFRESH_APPROVAL = true;
//                                            mActivity.getApprovalClassicModelFragment().resetData();
//                                            mActivity.getApprovalClassicModelFragment().initAdapter();
//                                        } else {
//                                            approvalClassicModelEntityList.get(position).setAdd(true);
//                                            initAdapter();
//                                            Util.CLASSIC_MODEL_LIST.clear();
//                                            Util.CLASSIC_MODEL_LIST.addAll(approvalClassicModelEntityList);
//                                            ToastUtil.showToast(mContext,mContext.getResources().getString(R.string.approval_deleted_failed), 0).show();
//                                        }
//                                    }
//                                });
                            }
                        }
                    });
                }
            };
            mListView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    public static ApprovalCustomFragment newInstance()
    {
        ApprovalCustomFragment fragment = new ApprovalCustomFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }
    public RecycleView setRecyView() {
        recycleview = new RecycleView() {

            @Override
            public void recycleThisView() {
                //回收
            }
        };
        return recycleview;
    }

    private RecycleView recycleview;

    public interface RecycleView {
        void recycleThisView();
    }
}
