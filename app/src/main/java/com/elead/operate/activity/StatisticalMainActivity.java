package com.elead.operate.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.elead.activity.BaseActivity;
import com.elead.card.mode.JerryChartInfo;
import com.elead.card.views.JerryChartView_statistical;
import com.elead.develop.entity.PoPreViewEnty;
import com.elead.develop.widget.PoOperationList;
import com.elead.eplatform.R;
import com.elead.views.CustomListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/11/3 0003.
 */

public class StatisticalMainActivity extends BaseActivity {

    @BindView(R.id.jerrychartview_statistical)
    JerryChartView_statistical jerrychartview;
    private PoOperationList load_more_list;
    private String Toptitle[] = {"iCaptain for mobile in the HR", "云朵云_MTL颗粒2016产品版本V02nn", "华为人才社区IHUAWEI二期需求V02BBB", "iCaptain for mobile in the HR", "云朵云_MTL颗粒2016产品版本V02nn", "华为人才社区IHUAWEI二期需求V02BBB", "云朵云_MTL颗粒2016产品版本V02nn"};
    private String unit[] = {"移动业务单元", "移动业务单元", "移动业务单元", "移动业务单元", "移动业务单元", "移动业务单元", "综合业务单元"};
    private String progress[] = {"待验收审核", "待验收审核", "代付款", "待验收审核", "待验收审核", "代付款", "代付款"};
    private int img[] = {R.drawable.project_preview_1, R.drawable.project_preview_2, R.drawable.project_preview_3, R.drawable.project_preview_1, R.drawable.project_preview_2, R.drawable.project_preview_1, R.drawable.project_preview_3};
    private boolean state[] = {true, false, true, true, true, true, true};
    private List<PoPreViewEnty> listAll = new ArrayList<>();
    private PoPreViewEnty popreviewentry;
    private LinearLayout po_ll;
    private CustomListView listiv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistical_activity_main);
        ButterKnife.bind(this);
        setTitle("运营统计", TitleType.STATISTICAL);
        setDatas();
        initView();
    }

    private void setDatas() {

        for (int i = 0; i < Toptitle.length; i++) {
            popreviewentry = new PoPreViewEnty();
            popreviewentry.setImgSrc(img[i]);
            popreviewentry.setTopTitle(Toptitle[i]);
            popreviewentry.setBottomTime(progress[i]);
            popreviewentry.setUnit(unit[i]);
            popreviewentry.setState(state[i]);
            listAll.add(popreviewentry);
        }


    }

    private void initView() {
        List<JerryChartInfo> datas = new ArrayList<JerryChartInfo>();
        datas.add(new JerryChartInfo(Color.parseColor("#b39ddb"), (float) (Math
                .random() * 3755f) + 200, Color.RED, false, "Visitors"));
        datas.add(new JerryChartInfo(Color.parseColor("#48d5cd"), (float) (Math
                .random() * 9012f) + 200, Color.RED, false, "Registered"));
        datas.add(new JerryChartInfo(Color.parseColor("#fff100"), (float) (Math
                .random() * 1600f) + 200, Color.RED, true, "Bounce"));
        jerrychartview.setData(datas, JerryChartView_statistical.ChartStyle.ANNULAR);

        load_more_list = (PoOperationList) findViewById(R.id.load_more_list_iv);
        load_more_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(StatisticalMainActivity.this, OperatTabActivity.class);
                startActivity(intent);
            }
        });


    }



}
