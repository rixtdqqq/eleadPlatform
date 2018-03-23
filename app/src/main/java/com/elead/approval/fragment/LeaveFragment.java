package com.elead.approval.fragment;

import android.content.Context;
import android.text.InputType;
import android.util.Log;
import android.view.View;

import com.elead.approval.entity.LeaveApprovalInfo;
import com.elead.approval.widget.ChooseView;
import com.elead.approval.widget.EdiTextItem;
import com.elead.eplatform.R;
import com.elead.utils.ToastUtil;

import java.util.List;


/**请假
 * Created by Administrator on 2016/9/14 0014.
 */
public class LeaveFragment extends ApprovalBaseFragment {


    private static final String TAG = LeaveFragment.class.getSimpleName();

    private ChooseView qingjialei, kaishishijian, jieshushijian;
    private LeaveApprovalInfo leaveApproval;
    private EdiTextItem qingjiaDays, qingjiaShiyou;

    @Override
    public void initView(Context context) {
//        LeaveView leaveView = new LeaveView(context);
        Log.d("aaa", "initView");
        getActivity().setTitle(getResources().getString(R.string.approval_leave));

        //getActivity().setTitle(getResources().getString(R.string.approval_leave), BaseActivity.TitleType.NORMAL);
        qingjialei = new ChooseView(context);
        qingjialei.inIt(ChooseView.Type.leave, getResources().getString(R.string.approval_leave_type),10, 0);
        addView(qingjialei,10);

        kaishishijian = new ChooseView(context);
        kaishishijian.inIt(ChooseView.Type.time, getResources().getString(R.string.approval_starttime), 10, 0);
        addView(kaishishijian,10);

        jieshushijian = new ChooseView(context);
        jieshushijian.inIt(ChooseView.Type.time,getResources().getString(R.string.approval_endtime) , 0, 0);
        addView(jieshushijian,0);

        qingjiaDays = new EdiTextItem(context);
        qingjiaDays.init(EdiTextItem.LineType.signLine, InputType.TYPE_CLASS_NUMBER, getResources().getString(R.string.approval_leave_days), getResources().getString(R.string.approval_leave_days_hint));
        addView(qingjiaDays,10);

        qingjiaShiyou = new EdiTextItem(context);
        qingjiaShiyou.init(EdiTextItem.LineType.moreLine,getResources().getString(R.string.approval_leave_reason) , getResources().getString(R.string.approval_leave_reason_hint));
        addView(qingjiaShiyou,10);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("aaa", "onResume");
    }

    @Override
    public boolean onSubmitClick(View v) {
        if (qingjialei.isEmpty() || kaishishijian.isEmpty() || jieshushijian.isEmpty()) {
            ToastUtil.showToast(mContext, getResources().getString(R.string.approval_must_write_not_null), 2000);
            return false;
        }
        leaveApproval = new LeaveApprovalInfo();
        leaveApproval.setLeaveType(qingjialei.getContent());
        leaveApproval.setStartTime(kaishishijian.getContent());
        leaveApproval.setEndTime(jieshushijian.getContent());

        leaveApproval.setLeaveDay(qingjiaDays.getContent());
        leaveApproval.setApprovalDsicribe(qingjiaShiyou.getContent());
        leaveApproval.setApprovalUsers(choosePeopleL.getAddApprovalUsers());
        return true;
    }

    @Override
    public void onPhotoUploadSuccess(List<String> appvalUsers, List<String> imgUrls) {
       /* leaveApproval.setApprovalUsers(appvalUsers);
        leaveApproval.setImageNames(imgUrls);
        leaveApproval.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                finishSubmit(leaveApproval);
            }
        });*/
    }

}
