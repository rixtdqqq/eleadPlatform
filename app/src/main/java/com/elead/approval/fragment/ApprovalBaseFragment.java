package com.elead.approval.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;

import com.elead.approval.widget.ApprovalTitle;
import com.elead.approval.widget.ChoosePeopleL;
import com.elead.approval.widget.ChoosePhotoL;
import com.elead.eplatform.R;
import com.elead.utils.DensityUtil;

import java.util.List;


/**
 * Created by Administrator on 2016/9/14 0014.
 */
public abstract class ApprovalBaseFragment extends Fragment {
    public static String Tag;
    private View inflate;
    private ApprovalTitle approvalTitle;
    public LinearLayout container;
    private View approval_submit_btn;
    public Activity mContext;
    public ProgressDialog progressDialog;
    public ChoosePhotoL choosePhotoL;
    public ChoosePeopleL choosePeopleL;
    public boolean flags=true;
    private ChoosePeopleL Ccpeople;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Tag = getClass().getSimpleName();
        if (null == container) {
            progressDialog = new ProgressDialog(mContext);
            container = (LinearLayout) inflate.findViewById(R.id.lin_container);
//            initActionBar();
            initView(mContext);
            approval_submit_btn = LayoutInflater.from(mContext).inflate(R.layout.approval_submit_btn, null);
            approval_submit_btn.findViewById(R.id.btn_sumit).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  /*  if (choosePeopleL.getAddApprovalMyUsers().size() == 0) {
                        ToastUtil.showToast(getResources().getString(R.string.approval_prople_not_null));
                        return;
                    }*/
                   /* if (onSubmitClick(v)) {
                        List<PhotoInfo> photoList = choosePhotoL.getPhotoList();
                        progressDialog.setMax(photoList.size());
                        progressDialog.setProgress(0);
                        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//                    progressDialog.setCanceledOnTouchOutside(false);
//                    progressDialog.setProgressNumberFormat("%1d /%2d ");
                        progressDialog.setTitle(getResources().getString(R.string.approval_iscommit_waite));
                        progressDialog.show();
                        UploadPhotoUtil.getInstance(photoList, mContext, onUploadLinstener).upLoad();
                    } else {
                        ToastUtil.showToast(mContext, getResources().getString(R.string.approval_must_write_must_not_null), 2000);
                    }*/


                }
            });
            if(flags==true) {
                choosePhotoL = new ChoosePhotoL(mContext);
                addView(choosePhotoL, 10);
            }
            choosePeopleL = new ChoosePeopleL(mContext);
            Ccpeople=new ChoosePeopleL(mContext);
            Ccpeople.setPersonName(getResources().getString(R.string.cc_people));
            addView(choosePeopleL,10);
            addView(Ccpeople,10);
            addView(approval_submit_btn,10);
        }

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
    }

    @Override
    public void onPause() {
        super.onPause();
        InputMethodManager imm = (InputMethodManager)
                mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getActivity().findViewById(R.id.content).getWindowToken(), 0);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (null == inflate) {
            inflate = inflater.inflate(R.layout.approval_fragment_base, container, false);
        }
        return inflate;
    }

    /*UploadPhotoUtil.OnUploadLinstener onUploadLinstener = new UploadPhotoUtil.OnUploadLinstener() {
        @Override
        public void onProgress(int currIndex, int totalCount) {
            progressDialog.setProgress(currIndex);
        }

        @Override
        public void onSuccess(List<String> imgUrls) {
            onPhotoUploadSuccess(choosePeopleL.getAddApprovalUsers(), imgUrls);
        }

        @Override
        public void onError(List<String> imgUrls, String errormsg) {
            progressDialog.cancel();
            ToastUtil.showToast(mContext, errormsg, 2000);
        }
    };*/
  /*  Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 100:
                    progressDialog.cancel();
                    break;
            }
        }


    };*/
/*

    public void finishSubmit(ApprovalCommonInfo leaveApproval) {
        progressDialog.setTitle(getResources().getString(R.string.approval_commit_success));
        progressDialog.setProgress(progressDialog.getMax());
        Message message = handler.obtainMessage();
        message.what = 100;
        message.obj = JSON.toJSONString(leaveApproval);
        handler.sendMessageDelayed(message, 1000);
    }
*/

    public void addView(View v,int marginTop) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-1, -2);
        params.setMargins(0, DensityUtil.dip2px(mContext,marginTop), 0, 0);
        v.setLayoutParams(params);
//        int padding = DensityUtil.dip2px(mContext, 10);
//        v.setPadding(0, padding, 0, 0);
        container.addView(v);
    }

    public abstract void initView(Context context);

    public abstract boolean onSubmitClick(View v);

    public abstract void onPhotoUploadSuccess(List<String> appvalUsers, List<String> imgUrls);
}
