package com.elead.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.elead.activity.ModifyPasswordActivity;
import com.elead.activity.MyInformatActivity;
import com.elead.activity.SettingActivity;
import com.elead.application.MyApplication;
import com.elead.eplatform.R;
import com.elead.im.activity.InvitationFriendActivity;
import com.elead.utils.EloadingDialog;
import com.elead.views.BaseFragment;
import com.elead.views.CircularImageView;
import com.elead.views.QrCodeDialog;

/**
 * Created by Administrator on 2016/8/18 0018.
 */
public class UserFragment extends BaseFragment implements View.OnClickListener {

    private CircularImageView my_photo;//我的头像
    private TextView useName;//二维码用户名
    private RelativeLayout rel_phone;//商务电话
    private RelativeLayout rel_collect;//收藏
    private RelativeLayout rel_invite;//邀请
    private RelativeLayout rel_help;//新手帮助
    private RelativeLayout rel_set;//设置
    private RelativeLayout rel_modify_pwd;//修改密码
    private final static int MY_PHOTO_RESULT_OK = 0x100;
    private RelativeLayout all_photo_username_rel;
    private TextView userPhone;
    private ImageView qr_code;
    private RelativeLayout rel_about_id;
    public String nickname;
    public String avatar;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return getView(inflater, R.layout.my_setfragment_activity, container);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        inItView();
    }


    private void inItView() {
        my_photo = (CircularImageView) mView.findViewById(R.id.my_photo_id);

        useName = (TextView) mView.findViewById(R.id.use_name_id);
        userPhone = (TextView) mView.findViewById(R.id.user_phone_id);
        rel_phone = (RelativeLayout) mView.findViewById(R.id.rel_phone_id);
        rel_collect = (RelativeLayout) mView.findViewById(R.id.rel_collect_id);
        rel_invite = (RelativeLayout) mView.findViewById(R.id.rel_invite_id);
        rel_help = (RelativeLayout) mView.findViewById(R.id.rel_help_id);
        rel_set = (RelativeLayout) mView.findViewById(R.id.rel_set_id);
        rel_modify_pwd = (RelativeLayout) mView.findViewById(R.id.rel_modify_pwd);
        all_photo_username_rel = (RelativeLayout) mView.findViewById(R.id.all_photo_username_rel);
        qr_code = (ImageView) mView.findViewById(R.id.qr_code_imge_id);
        rel_about_id = (RelativeLayout) mView.findViewById(R.id.rel_about_id);

        my_photo.setOnClickListener(this);
        rel_help.setOnClickListener(this);
        rel_phone.setOnClickListener(this);
        rel_collect.setOnClickListener(this);
        rel_invite.setOnClickListener(this);
        rel_help.setOnClickListener(this);
        rel_set.setOnClickListener(this);
        rel_modify_pwd.setOnClickListener(this);
        useName.setOnClickListener(this);
        all_photo_username_rel.setOnClickListener(this);
        qr_code.setOnClickListener(this);
        rel_about_id.setOnClickListener(this);

        useName.setText(MyApplication.user.name);
        userPhone.setText(MyApplication.user.dept_name);

    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            initHeadView();
        }
    }

    public void initHeadView() {
        if (null != my_photo && null != useName && userPhone != null
                && TextUtils.isEmpty(my_photo.getText())) {
            my_photo.setBackgroundColor(MyApplication.user.work_no);
            my_photo.setText(MyApplication.user.name);
            useName.setText(MyApplication.user.name);
            userPhone.setText(MyApplication.user.dept_name);
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            //商务电话
            case R.id.rel_phone_id:
             /*  Intent B =new Intent(activity,BusinessTripActivity.class);
                startActivity(B);*/
//                Intent intents = new Intent(getActivity(),BindListActivity.class);
//                startActivity(intents);
                EloadingDialog.showDailog();
                break;
            //收藏
            case R.id.rel_collect_id:
//                Intent m = new Intent(activity, OtherPeopleActivity.class);
//                startActivity(m);
                EloadingDialog.showDailog();
                break;
            //邀请
            case R.id.rel_invite_id:
//                EloadingDialog.showDailog();
                Intent invitationIntent = new Intent(mContext, InvitationFriendActivity.class);
                startActivity(invitationIntent);
                break;
            //新手帮助
            case R.id.rel_help_id:
                EloadingDialog.showDailog();
                break;
            //点击设置
            case R.id.rel_set_id:
                Intent setintent = new Intent(mContext, SettingActivity.class);
                startActivity(setintent);
                break;

            case R.id.qr_code_imge_id:
//                Intent qrIntent = new Intent(mContext, QrCodeActivity.class);
//                startActivity(qrIntent);
                QrCodeDialog dialog = new QrCodeDialog(getActivity(),"","");
                dialog.show();
                break;
            case R.id.rel_about_id:
                EloadingDialog.showDailog();
                break;
            //点击头像
            case R.id.all_photo_username_rel:
                Intent intent = new Intent(mContext, MyInformatActivity.class);
                startActivityForResult(intent, MY_PHOTO_RESULT_OK);
                break;
            case R.id.rel_modify_pwd:
                Intent i = new Intent(mContext, ModifyPasswordActivity.class);
                startActivity(i);
                break;

        }
    }

}
