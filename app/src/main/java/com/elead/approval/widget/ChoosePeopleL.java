package com.elead.approval.widget;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.elead.approval.fragment.ChooseApprovalPeopleFragment;
import com.elead.eplatform.R;
import com.elead.module.EpUser;
import com.elead.utils.DensityUtil;
import com.elead.utils.ToastUtil;
import com.elead.views.CircularImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public class ChoosePeopleL extends LinearLayout {
    private View inflate;
    private LinearLayout lin_approval_add_people_continer;
    private List<EpUser> addApprovalUsers = new ArrayList<>();
    private TextView personName;

    public ChoosePeopleL(Context context) {
        this(context, null);
    }

    public ChoosePeopleL(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChoosePeopleL(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate = LayoutInflater.from(getContext()).inflate(R.layout.approval_choose_pelple_l, this);
        lin_approval_add_people_continer = (LinearLayout) inflate.findViewById(R.id.lin_approval_add_people_continer);
        personName = (TextView) inflate.findViewById(R.id.personName_iv);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                final FragmentActivity context = (FragmentActivity) getContext();
                ChooseApprovalPeopleFragment chooseApprovalPeopleFragment = new ChooseApprovalPeopleFragment();
                chooseApprovalPeopleFragment.setOnPeopleChooseLinstener(new ChooseApprovalPeopleFragment.OnPeopleChooseLinstener() {
                    @Override
                    public void onPeopleChoose(final EpUser user) {
                        if (!addApprovalUsers.contains(user)) {

                            addApprovalUsers.add(user);
                            final CircularImageView b = new CircularImageView(context);
                            final View line = new View(context);
                            line.setLayoutParams(new LayoutParams(DensityUtil.dip2px(context, 20), 2));
                            line.setBackgroundColor(Color.BLACK);
                            b.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    addApprovalUsers.remove(user);
                                    lin_approval_add_people_continer.removeView(b);
                                    lin_approval_add_people_continer.removeView(line);
                                }
                            });
                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
                            int margin = DensityUtil.dip2px(context, 15);
                            layoutParams.setMargins(0, 0, 0, 0);
                            b.setLayoutParams(layoutParams);
                            b.setText(user.getRole_name());
                            b.setBackgroundColor(user.getUsername());
                            int index = lin_approval_add_people_continer.getChildCount() - 1;
                            lin_approval_add_people_continer.addView(b, index);
                            lin_approval_add_people_continer.addView(line, index + 1);
                            lin_approval_add_people_continer.requestLayout();
                        } else {
                            ToastUtil.showToast(context, "不能重复添加", 2000);
                        }
                    }
                });
                context.getSupportFragmentManager().beginTransaction().replace(R.id.content, chooseApprovalPeopleFragment, "chooseApprovalPeopleFragment").addToBackStack("chooseApprovalPeopleFragment").commit();
            }
        });
    }

    public List<EpUser> getAddApprovalMyUsers() {
        return addApprovalUsers;
    }

    public List<String> getAddApprovalUsers() {
        List<String> users = new ArrayList<>();
        for (EpUser user : addApprovalUsers) {
            users.add(user.getUsername());
        }
        return users;
    }

    public void setPersonName(String name) {
        personName.setText(name);
    }
}
