package com.elead.approval.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.elead.application.MyApplication;
import com.elead.approval.activity.ApprovalCommonActivity;
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
public class ApprovalClassicModelFragment extends BaseFragment {

    private View mView;
    private ApprovalManagerActivity mActivity;
    private List<ApprovalClassicModelEntity> approvalClassicModelEntityList = new ArrayList<ApprovalClassicModelEntity>();
    private BaseCommonAdapter adapter;
    private ListView mListView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (null == mView) {
            mView = inflater.inflate(R.layout.approval_f_classic_model, container, false);
            setUpView();
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
        addItem();
        initAdapter();
    }

    protected void setUpView() {
        clissicModelNames = mActivity.getResources().getStringArray(R.array.classic_model_name);
        descriptionArray = mActivity.getResources().getStringArray(R.array.classic_model_description);
        mListView = (ListView) mView.findViewById(R.id.lv_classic_model);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = approvalClassicModelEntityList.get(position).getName();
                if (TextUtils.equals("部门协作",name)) {
                    openFragment(new DepartmentXieZuoFragment());
                } else if (TextUtils.equals("立项申请",name)) {
                    openFragment(new ProjectApplyFragment());
                } else if (TextUtils.equals("备用金申请",name)) {
                    openFragment(new BeiYongMoneyApplyFragment());
                } else if (TextUtils.equals("订货审批",name)) {
                    openFragment(new OrderApplyFragment());
                } else if (TextUtils.equals("物品申购",name)) {
                    openFragment(new ThingsPurchaseFragment());
                } else if (TextUtils.equals("收款",name)) {
                    openFragment(new CollectionMoneyFragment());
                } else if (TextUtils.equals("采购",name)) {
                    openFragment(new ApprovalShoping());
                } else if (TextUtils.equals("新闻爆料",name)) {
                    openFragment(new NewFactFragment());
                } else if (TextUtils.equals("会议审批",name)) {
                    openFragment(new MeetingShenpiFragment());
                } else if (TextUtils.equals("资质使用",name)) {
                    openFragment(new ZizhiUseFragment());
                } else if (TextUtils.equals("合同审批",name)) {
                    openFragment(new ApprovalContractFragment());
                } else if (TextUtils.equals("工作请示",name)) {
                    openFragment(new WrokAskFragment());
                } else if (TextUtils.equals("调课报备",name)) {
                    openFragment(new ClassBaoBeiFragment());
                } else if (TextUtils.equals("快递寄送",name)) {
                    openFragment(new KaiDiSendFragment());
                } else if (TextUtils.equals("绩效自评",name)) {
                    openFragment(new JiXiaoSelfFragment());
                } else if (TextUtils.equals("用车申请",name)) {
                    openFragment(new ApprovalUserCarApply());
                } else if (TextUtils.equals("用印申请",name)) {
                    openFragment(new ApprovalYongYinApply());
                } else if (TextUtils.equals("离职",name)) {
                    openFragment(new ApprovalGoAway());
                } else if (TextUtils.equals("转正",name)) {
                    openFragment(new ApprovalPositive());
                } else if (TextUtils.equals("加班",name)) {
                    openFragment(new ApprovalAddWorkFragment());
                } else if (TextUtils.equals("付款",name)) {
                    openFragment(new ApprovalPay());
                } else if (TextUtils.equals("招聘",name)) {
                    openFragment(new ApprovalRecruitment());
                }
            }
        });
    }

    private void openFragment(Fragment fragment){
        Intent intent = new Intent(mActivity, ApprovalCommonActivity.class);
        intent.putExtra("fragment", fragment.getClass().getSimpleName());
        startActivity(intent);
    }

    private String [] clissicModelNames;
    private int[] imgs = new int[]{R.drawable.department_cooperation,R.drawable.project_apply,R.drawable.payment,
            R.drawable.order_apply,R.drawable.res_apply,R.drawable.payment,R.drawable.become_a_regular_worker,
            R.drawable.news_broke,R.drawable.project_apply,R.drawable.car_apply,
            R.drawable.overtime,R.drawable.leave_office,R.drawable.department_cooperation,
            R.drawable.res_apply,R.drawable.department_cooperation,R.drawable.car_apply,
            R.drawable.seal_apply,R.drawable.leave_office,R.drawable.become_a_regular_worker,
            R.drawable.overtime,R.drawable.payment,R.drawable.recruit};
    private String[] descriptionArray;
    private void addItem(){
        approvalClassicModelEntityList.clear();
        for (int i = 0; i < 22; i ++){
            ApprovalClassicModelEntity entity = new ApprovalClassicModelEntity();
            entity.setName(clissicModelNames[i]);
//            entity.setImgId(imgs[i]);
            entity.setDescription(descriptionArray[i]);
            approvalClassicModelEntityList.add(entity);
        }
        formatData();
    }

    public void formatData(){
        if (Util.isNotEmpty(Util.CLASSIC_MODEL_LIST)) {
            for (int i = 0, sizei = Util.CLASSIC_MODEL_LIST.size(); i < sizei; i ++) {
                String name = Util.CLASSIC_MODEL_LIST.get(i).getName();
                for (int j = 0, sizej = approvalClassicModelEntityList.size(); j < sizej; j ++){
                    ApprovalClassicModelEntity entity = approvalClassicModelEntityList.get(j);
                    if (TextUtils.equals(name, entity.getName())) {
                        entity.setAdd(true);
                        continue;
                    }
                }
            }
        }
    }

    public void resetData(){
        addItem();
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
                    TextView tv_add_not_click = holder.getView(R.id.tv_add_not_click);
                    if (!approvalClassicModelEntity.isAdd()) {
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
                            approvalClassicModelEntityList.get(position).setAdd(true);
                            initAdapter();
                            if (Util.isNotEmpty(Util.CLASSIC_MODEL_LIST)) {
                                Util.CLASSIC_MODEL_LIST.add(approvalClassicModelEntityList.get(position));
                                approvalListDataEntity.setClassicModelList(Util.CLASSIC_MODEL_LIST);
                                approvalListDataEntity.setUserName(MyApplication.user.code);
//                                approvalListDataEntity.update(Util.APPROVAL_LIST_OBJECT_ID, new UpdateListener() {
//                                    @Override
//                                    public void done(BmobException e) {
//                                        if (e == null) {
//                                            initAdapter();
//                                            Util.IS_REFRESH_APPROVAL = true;
//                                            mActivity.getApprovalCustomFragment().initData();
//                                        } else {
//                                            approvalClassicModelEntityList.get(position).setAdd(false);
//                                            initAdapter();
//                                            Util.CLASSIC_MODEL_LIST.remove(approvalClassicModelEntityList.get(position));
//                                            ToastUtil.showToast(mContext,mContext.getResources().getString(R.string.approval_add_failed), 0).show();
//                                        }
//                                    }
//                                });
                            } else {
                                Util.CLASSIC_MODEL_LIST.add(approvalClassicModelEntityList.get(position));
                                approvalListDataEntity.setClassicModelList(Util.CLASSIC_MODEL_LIST);
                                approvalListDataEntity.setUserName(MyApplication.user.code);
//                                approvalListDataEntity.save(new SaveListener<String>() {
//                                    @Override
//                                    public void done(String s, BmobException e) {
//                                        if (null == e) {
//                                            initAdapter();
//                                            Util.IS_REFRESH_APPROVAL = true;
//                                            mActivity.getApprovalCustomFragment().initData();
//                                        } else {
//                                            approvalClassicModelEntityList.get(position).setAdd(false);
//                                            initAdapter();
//                                            Util.CLASSIC_MODEL_LIST.remove(approvalClassicModelEntityList.get(position));
//                                            ToastUtil.showToast(mContext,mContext.getResources().getString(R.string.approval_add_failed), 0).show();
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

    public static ApprovalClassicModelFragment newInstance()
    {
        ApprovalClassicModelFragment fragment = new ApprovalClassicModelFragment();
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
                if (approvalClassicModelEntityList != null) {
                    approvalClassicModelEntityList.clear();
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
