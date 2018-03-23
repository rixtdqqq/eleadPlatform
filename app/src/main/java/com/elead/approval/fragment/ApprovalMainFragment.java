package com.elead.approval.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.alibaba.fastjson.JSON;
import com.elead.activity.BaseActivity;
import com.elead.activity.BaseFragmentActivity;
import com.elead.application.MyApplication;
import com.elead.approval.activity.ApWaitingForMyApprovalActivity;
import com.elead.approval.activity.ApprovalCommonActivity;
import com.elead.approval.entity.ApprovalClassicModelEntity;
import com.elead.approval.entity.ApprovalListEntity;
import com.elead.eplatform.R;
import com.elead.upcard.fragment.UpCardLeaveFragment;
import com.elead.utils.SharedPreferencesUtil;
import com.elead.utils.Util;
import com.elead.views.BaseCommonAdapter;
import com.elead.views.BaseFragment;
import com.elead.views.CustomGridView;
import com.elead.views.ViewHolder;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * @desc 审批首页
 * @auth Created by mujun.xu on 2016/9/14 0014.
 */
public class ApprovalMainFragment extends BaseFragment implements /*TopBarView.OnClickListener, */View.OnClickListener {

    private View mView;

    //    private TopBarView titleView;
    private CustomGridView gridView;
    private String[] strArray;
    private BaseCommonAdapter adapter;
    private LinearLayout mLlWaitingForMyApproval;
    private LinearLayout mLllIStarted;
    private LinearLayout mLlCcMy;
    private ScrollView sv_main;
    private RelativeLayout rl_loading;
    private final int QUERY_SUCCESS = 0;
    private final int QUERY_FAILD = 1;
    private List<ApprovalClassicModelEntity> classicModelEntityList = new ArrayList<ApprovalClassicModelEntity>();
    private ApprovalListEntity mApprovalListDataEntity;
    private String mData;

    private UIHandler mHandler;


    class UIHandler extends Handler {

        private WeakReference<ApprovalMainFragment> self;

        public UIHandler(ApprovalMainFragment self) {
            this.self = new WeakReference<ApprovalMainFragment>(self);
        }

        @Override
        public void handleMessage(Message msg) {
            ApprovalMainFragment approvalMainFragment = self.get();
            if (approvalMainFragment == null) {
                return;
            }
            switch (msg.what) {
                case QUERY_SUCCESS:
                    saveDataToLocal();
                    initAdapter();
                    break;
                case QUERY_FAILD:
                    Util.CLASSIC_MODEL_LIST.clear();
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 发送Handler消息
     *
     * @param what
     */
    private void sendMessage(int what) {
        if (null != mHandler) {
            Message msg = mHandler.obtainMessage(what);
            mHandler.removeMessages(what);
            mHandler.sendMessage(msg);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (null == mView) {
            mView = inflater.inflate(R.layout.approval_f_main, container, false);
           // Util.getLanguage(mContext);
            setUpView();
            if (Util.IS_FIRST_LOADING) {
                Util.IS_FIRST_LOADING = false;
                doRequestData();
            } else {
                initListData();
            }
        }
        return mView;
    }

    private void initListData() {
        mData = SharedPreferencesUtil.getString(mContext, MyApplication.user.code + "data");
        if (!TextUtils.isEmpty(mData)) {
            classicModelEntityList.clear();
            classicModelEntityList = JSON.parseArray(mData, ApprovalClassicModelEntity.class);
            if (Util.isNotEmpty(classicModelEntityList)) {
                initAdapter();
            } else {
                doRequestData();
            }
        } else {
            doRequestData();
        }
    }

    private void doRequestData() {
        addCacheData();
        initAdapter();
//        queryData();
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        //Util.getLanguage(mContext);
        //mContext.recreate();
        if (mContext.isDestroyed()) {

            initListData();
        }
        ((BaseFragmentActivity)mContext).setTitle(getResources().getString(R.string.approved), BaseActivity.TitleType.NORMAL);
        if (Util.IS_REFRESH_APPROVAL) {
            Util.IS_REFRESH_APPROVAL = false;
            addCacheData();
            sendMessage(QUERY_SUCCESS);
        }
    }

    private void addCacheData() {
        addItem();
        if (Util.isNotEmpty(Util.CLASSIC_MODEL_LIST)) {
            for (int i = 0, size = Util.CLASSIC_MODEL_LIST.size(); i < size; i++) {
                classicModelEntityList.add(classicModelEntityList.size() - 1, Util.CLASSIC_MODEL_LIST.get(i));
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
       // saveDataToLocal();
    }

    private void saveDataToLocal() {
        String data = JSON.toJSONString(classicModelEntityList);
        SharedPreferencesUtil.putString(mContext, MyApplication.user.code + "data", data);
    }

    private void queryData() {
       /* BmobQuery<ApprovalListEntity> query = new BmobQuery<ApprovalListEntity>();
        query.addWhereEqualTo("userName", MyApplication.user.code);
        query.findObjects(new FindListener<ApprovalListEntity>() {
            @Override
            public void done(List<ApprovalListEntity> list, BmobException e) {
                if (null == e) {
                    if (Util.isNotEmpty(list)) {
                        mApprovalListDataEntity = list.get(0);
                        Util.APPROVAL_LIST_OBJECT_ID = mApprovalListDataEntity.getObjectId();
                        addItem();
                        formatData(mApprovalListDataEntity.getClassicModelList());
                    } else {
                        sendMessage(QUERY_FAILD);
                    }
                } else {
                    sendMessage(QUERY_FAILD);
                }
            }
        });*/
    }

    private void formatData(List<ApprovalClassicModelEntity> list) {
        if (Util.isNotEmpty(list)) {
            Util.CLASSIC_MODEL_LIST.clear();
            Util.CLASSIC_MODEL_LIST.addAll(list);
            for (int i = 0, size = list.size(); i < size; i++) {
                classicModelEntityList.add(classicModelEntityList.size() - 1, list.get(i));
            }
            sendMessage(QUERY_SUCCESS);
        } else {
            sendMessage(QUERY_FAILD);
        }
    }

    protected void setUpView() {
        mHandler = new UIHandler(this);
        strArray =mContext.getResources().getStringArray(R.array.approval);
        sv_main = (ScrollView) mView.findViewById(R.id.sv_main);
        rl_loading = (RelativeLayout) mView.findViewById(R.id.rl_loading);
        rl_loading.setVisibility(View.GONE);
        sv_main.setVisibility(View.VISIBLE);
        mLlWaitingForMyApproval = (LinearLayout) mView.findViewById(R.id.ll_waiting_for_my_approval);
        mLllIStarted = (LinearLayout) mView.findViewById(R.id.ll_i_started);
        mLlCcMy = (LinearLayout) mView.findViewById(R.id.ll_cc_my);
        gridView = (CustomGridView) mView.findViewById(R.id.approval_grid);
        mLlWaitingForMyApproval.setOnClickListener(this);
        mLllIStarted.setOnClickListener(this);
        mLlCcMy.setOnClickListener(this);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = classicModelEntityList.get(position).getName();
                switchFragment(name);
            }
        });
    }

    private void switchFragment(String name) {
        if (TextUtils.equals(strArray[0], name)) {
            openFragment(new UpCardLeaveFragment());
        } else if (TextUtils.equals(strArray[1], name)) {
            openFragment(new ApprovalReimbursementFragment());
        } else if (TextUtils.equals(strArray[2], name)) {
            openFragment(new BusinessRripFragment());
        } else if (TextUtils.equals(strArray[3], name)) {
            openFragment(new GoOutFragment());
        } else if (TextUtils.equals(strArray[4], name)) {
            openFragment(new ApprovalResFragment());
        } else if (TextUtils.equals(strArray[5], name)) {
            openFragment(new ApprovalCommonFragment());
        } else if (TextUtils.equals(mContext.getString(R.string.add_approved), name)) {
          //  Intent intent = new Intent(mContext, ApprovalManagerActivity.class);
           // startActivity(intent);
        } else if (TextUtils.equals("部门协作", name)) {
            openFragment(new DepartmentXieZuoFragment());
        } else if (TextUtils.equals("立项申请", name)) {
            openFragment(new ProjectApplyFragment());
        } else if (TextUtils.equals("备用金申请", name)) {
            openFragment(new BeiYongMoneyApplyFragment());
        } else if (TextUtils.equals("订货审批", name)) {
            openFragment(new OrderApplyFragment());
        } else if (TextUtils.equals("物品申购", name)) {
            openFragment(new ThingsPurchaseFragment());
        } else if (TextUtils.equals("收款", name)) {
            openFragment(new CollectionMoneyFragment());
        } else if (TextUtils.equals("采购", name)) {
            openFragment(new ApprovalShoping());
        } else if (TextUtils.equals("新闻爆料", name)) {
            openFragment(new NewFactFragment());
        } else if (TextUtils.equals("会议审批", name)) {
            openFragment(new MeetingShenpiFragment());
        } else if (TextUtils.equals("资质使用", name)) {
            openFragment(new ZizhiUseFragment());
        } else if (TextUtils.equals("合同审批", name)) {
            openFragment(new ApprovalContractFragment());
        } else if (TextUtils.equals("工作请示", name)) {
            openFragment(new WrokAskFragment());
        } else if (TextUtils.equals("调课报备", name)) {
            openFragment(new ClassBaoBeiFragment());
        } else if (TextUtils.equals("快递寄送", name)) {
            openFragment(new KaiDiSendFragment());
        } else if (TextUtils.equals("绩效自评", name)) {
            openFragment(new JiXiaoSelfFragment());
        } else if (TextUtils.equals("用车申请", name)) {
            openFragment(new ApprovalUserCarApply());
        } else if (TextUtils.equals("用印申请", name)) {
            openFragment(new ApprovalYongYinApply());
        } else if (TextUtils.equals("离职", name)) {
            openFragment(new ApprovalGoAway());
        } else if (TextUtils.equals("转正", name)) {
            openFragment(new ApprovalPositive());
        } else if (TextUtils.equals("加班", name)) {
            openFragment(new ApprovalAddWorkFragment());
        } else if (TextUtils.equals("付款", name)) {
            openFragment(new ApprovalPay());
        } else if (TextUtils.equals("招聘", name)) {
            openFragment(new ApprovalRecruitment());
        }
    }

    private int[] imgs = new int[]{R.drawable.leave, R.drawable.reimbursement, R.drawable.a_business_travel,
            R.drawable.go_out, R.drawable.res_receive, R.drawable.common_apprival, R.drawable.add_icon};

    private void addItem() {
        classicModelEntityList.clear();
        for (int i = 0; i < 7; i++) {
            ApprovalClassicModelEntity entity = new ApprovalClassicModelEntity();
            if (i == 6) {
                entity.setName(mContext.getResources().getString(R.string.add_approved));
            } else {
                entity.setName(strArray[i]);
            }
            entity.setImgId(imgs[i]);
            classicModelEntityList.add(entity);
        }
    }

    private void openFragment(Fragment fragment) {
        if (null != fragment) {
            String simpleName = fragment.getClass().getSimpleName();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content, fragment,
                    simpleName).addToBackStack(simpleName).commit();
        }
    }

    private void initAdapter() {
        if (null == adapter) {
            adapter = new BaseCommonAdapter<ApprovalClassicModelEntity>(mContext, classicModelEntityList, R.layout.approval_grid_item) {

                @Override
                public void convert(ViewHolder holder, ApprovalClassicModelEntity classicModelEntity, int position) {
                    String name = classicModelEntity.getName();
                    holder.setText(R.id.tv_approval, name);
                 // holder.setImageResource(R.id.img_approval, Util.APPROVAL_IMAGES.get(name));
                    holder.setImageResource(R.id.img_approval,classicModelEntity.getImgId());
                }
            };
            gridView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.ll_waiting_for_my_approval:
                jumpingToWaitingApproval();
                break;
            case R.id.ll_i_started:
                jumpingToIStartedOrCCMy("ApprovalIStartedFragment");
                break;
            case R.id.ll_cc_my:
                jumpingToIStartedOrCCMy("ApprovalCCMyFragment");
                break;
            default:
                break;
        }
    }

    /**
     * 跳转到待我审批界面
     */
    private void jumpingToWaitingApproval() {
        Intent intent = new Intent(mContext, ApWaitingForMyApprovalActivity.class);
        startActivity(intent);
    }

    /**
     * 跳转到我发起的或抄送我的界面
     */
    private void jumpingToIStartedOrCCMy(String frgName) {
        Intent intent = new Intent(mContext, ApprovalCommonActivity.class);
        intent.putExtra("fragment", frgName);
        startActivity(intent);
    }
}
