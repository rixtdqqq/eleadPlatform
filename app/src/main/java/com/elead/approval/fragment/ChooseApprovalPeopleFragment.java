package com.elead.approval.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.elead.eplatform.R;
import com.elead.im.view.EaseContactList;
import com.elead.module.EpUser;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public class ChooseApprovalPeopleFragment extends Fragment {
    @BindView(R.id.list_contact_friend)
    EaseContactList listContactFriend;
    private View view;
    private OnPeopleChooseLinstener onPeopleChooseLinstener;


    /**
     * 所有继承BackHandledFragment的子类都将在这个方法中实现物理Back键按下后的逻辑
     * FragmentActivity捕捉到物理返回键点击事件后会首先询问Fragment是否消费该事件
     * 如果没有Fragment消息时FragmentActivity自己才会消费该事件
     */
//    @Override
//    public boolean onBackPressed() {
//        onBackPressed();
//        return true;
//    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_approval_choose_prople, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listContactFriend.getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            }
        });
    }

    public interface OnPeopleChooseLinstener {
        void onPeopleChoose(EpUser user);
    }

    public void setOnPeopleChooseLinstener(OnPeopleChooseLinstener onPeopleChooseLinstener) {
        this.onPeopleChooseLinstener = onPeopleChooseLinstener;
    }
}
