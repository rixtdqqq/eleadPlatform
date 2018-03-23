package com.elead.report.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.elead.activity.BaseFragmentActivity;
import com.elead.eplatform.R;
import com.elead.module.TbSelecterInfo;
import com.elead.report.fragment.ReportMainFragment;
import com.elead.report.fragment.ReportMineFragment;
import com.elead.report.fragment.ReportZhiBiaoFragment;
import com.elead.views.TbSelector;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gly on 2016/10/11 0011.
 */

public class ReportMainActivity extends BaseFragmentActivity {
    @BindView(R.id.report_main_tb)
    TbSelector reportMainTb;
    private TbSelector report_main_tb;
    private Fragment reportMainF, zhibiaoF, mineF;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setTitle("智能报表");
        setContentView(R.layout.report_activity_main);
        ButterKnife.bind(this);
        reportMainF = new ReportMainFragment();
        zhibiaoF = new ReportZhiBiaoFragment();
        mineF = new ReportMineFragment();
        replaceFragment(reportMainF);
        initTbSelecter();
    }


    private void initTbSelecter() {
        List<TbSelecterInfo> tbSelecterInfo = new ArrayList<>();
        int defColor = getResources().getColor(R.color.fontcolors4);
        int pressColor = getResources().getColor(R.color.qianlv);
        tbSelecterInfo.add(new TbSelecterInfo("首页", defColor, pressColor, R.drawable.home_bottom_tab_icon_mine_normal, R.drawable.home_bottom_tab_icon_mine_highlight));
        tbSelecterInfo.add(new TbSelecterInfo("指标", defColor, pressColor, R.drawable.home_bottom_tab_icon_work_normal, R.drawable.home_bottom_tab_icon_work_highlight));
        tbSelecterInfo.add(new TbSelecterInfo("我", defColor, pressColor, R.drawable.home_bottom_tab_icon_contact_normal, R.drawable.home_bottom_tab_icon_contact_highlight));
        reportMainTb.inItTabS(tbSelecterInfo);
        reportMainTb.setOnTbSelectListener(new TbSelector.onTbSelectListener() {
            @Override
            public void onSelectChange(int position) {
                switch (position) {
                    case 0:
                        setTitle("智能报表");
                        replaceFragment(reportMainF);
                        break;
                    case 1:
                        setTitle("全部指标");
                        replaceFragment(zhibiaoF);
                        break;
                    case 2:
                        setTitle("我");
                        replaceFragment(mineF);
                        break;
                }
            }
        });
    }

    public void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.report_container, fragment, fragment.getClass().getSimpleName()).commit();
    }
}
