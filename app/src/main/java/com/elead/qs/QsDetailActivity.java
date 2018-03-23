package com.elead.qs;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.elead.activity.BaseActivity;
import com.elead.adapter.QsDetailAdapter;
import com.elead.eplatform.R;
import com.elead.qs.model.QsBarChartModel;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 质量汇总详情页面
 */
public class QsDetailActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.lv_qc_detail)
    ListView lvQcDetail;

    List<QsBarChartModel> list;
    @BindView(R.id.qs_back)
    ImageButton img_qsBack;
    @BindView(R.id.ly_select_one)
    RelativeLayout ly_select_one;
    @BindView(R.id.ly_select_two)
    RelativeLayout ly_select_two;
    @BindView(R.id.ly_select_three)
    RelativeLayout ly_select_three;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qs_detail);
        ButterKnife.bind(this);
        initLocalData();
        initView();
        initListView();
    }

    private void initView() {
        img_qsBack.setOnClickListener(this);
        ly_select_one.setOnClickListener(this);
        ly_select_two.setOnClickListener(this);
        ly_select_three.setOnClickListener(this);
    }

    private void initLocalData() {
        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
        list = new ArrayList<QsBarChartModel>();
        yVals1.add(new BarEntry(0, 10));
        yVals1.add(new BarEntry(1, 20));
        yVals1.add(new BarEntry(2, 30));
        yVals1.add(new BarEntry(3, 40));
        yVals1.add(new BarEntry(4, 50));
        QsBarChartModel model = new QsBarChartModel("Icaptain for mobile in the hr", yVals1);
        QsBarChartModel model1 = new QsBarChartModel("华为人才社区IHUAWEI二期", yVals1);
        QsBarChartModel model2 = new QsBarChartModel("三朵云_MTL颗粒2016产品版本", yVals1);
        list.add(model);
        list.add(model1);
        list.add(model2);


    }

    private void initListView() {
        QsDetailAdapter adapter = new QsDetailAdapter(this, list, R.layout.list_item_qs_detail);
        lvQcDetail.setAdapter(adapter);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.qs_back:
                finish();
                break;
            //综合
            case R.id.ly_select_one:
                break;
            //评分
            case R.id.ly_select_two:
                break;
            //帅选
            case R.id.ly_select_three:
                break;
        }
    }
}
