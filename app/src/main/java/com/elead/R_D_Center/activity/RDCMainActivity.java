package com.elead.R_D_Center.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.elead.activity.BaseActivity;
import com.elead.card.mode.JerryChartInfo;
import com.elead.card.views.JerryChartView_statistical;
import com.elead.develop.entity.PoPreViewEnty;
import com.elead.develop.widget.PoThreePreView;
import com.elead.eplatform.R;
import com.elead.views.CustomListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/11/3 0003.
 */

public class RDCMainActivity extends BaseActivity {

    @BindView(R.id.jerrychartview)
    JerryChartView_statistical jerrychartview;
    @BindView(R.id.title_1)
    TextView depart_text;
    @BindView(R.id.title_2)
    TextView projPro_text;
    @BindView(R.id.title_3)
    TextView sample_text;
    @BindView(R.id.title_yuanf_list_id)
    TextView projpreviewtitle_text;
    @BindView(R.id.two_arrow_rel)
    RelativeLayout two_arrow_rel;

   // private PoThreePreView load_more_list;
    private CustomListView listiv;
    private PoThreePreView load_more_list;
    private String Toptitle[] = {"ES3 373 seris", "GS9 series", "NX7 Note series", "ES3 373 seris", "GS9 series", "NX7 Note series", "ES3 373 seris"};
   // private String unit[] = {"移动业务单元", "移动业务单元", "移动业务单元", "移动业务单元", "移动业务单元", "移动业务单元", "综合业务单元"};
    private String progress[] = {"模型评审", "用户调研", "设计建模", "模型评审", "用户调研", "设计建模", "模型评审"};
    private int img[] = {R.drawable.project_preview_1, R.drawable.project_preview_2, R.drawable.project_preview_3, R.drawable.project_preview_1, R.drawable.project_preview_2, R.drawable.project_preview_1, R.drawable.project_preview_3};
    private boolean state[] = {true,true, false, true, true, true, true};
    private String time[] = {"2016/06/15 09:04:03", "2016/06/15 09:04:03", "2016/06/15 09:04:03", "2016/06/15 09:04:03", "2016/06/15 09:04:03", "2016/06/15 09:04:03", "2016/06/15 09:04:03"};
    private PoPreViewEnty popreviewentry;
    private List<PoPreViewEnty> listAll = new ArrayList<>();
    private    boolean flags=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.r_d_c_activity_main);
        ButterKnife.bind(this);
        setDatas();
        initView();
    }

    private void setDatas() {

        for (int i = 0; i < Toptitle.length; i++) {
            popreviewentry = new PoPreViewEnty();
            popreviewentry.setImgSrc(img[i]);
            popreviewentry.setTopTitle(Toptitle[i]);
            popreviewentry.setBottomTime(progress[i]);
            popreviewentry.setUnit(time[i]);
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
        //   stMainCaItem.setTitle("");


        load_more_list = (PoThreePreView) findViewById(R.id.load_more_list_iv);
        load_more_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(RDCMainActivity.this, RDCTabActivity.class);
                startActivity(intent);
            }
        });
       depart_text.setTextColor(getResources().getColor(R.color.fontcolors2));
       projPro_text.setTextColor(getResources().getColor(R.color.fontcolors2));
       sample_text.setTextColor(getResources().getColor(R.color.fontcolors2));
       projpreviewtitle_text.setTextColor(getResources().getColor(R.color.fontcolors2));


       two_arrow_rel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             if (flags==true){
                 Drawable d = getResources().getDrawable(R.drawable.top_arrow);
                 d.setBounds(0,0,d.getMinimumWidth(),d.getMinimumHeight());
                 projPro_text.setCompoundDrawables(null,null,d,null);
                 flags=false;
             }else{
                 Drawable d = getResources().getDrawable(R.drawable.bottom_arrow);
                 d.setBounds(0,0,d.getMinimumWidth(),d.getMinimumHeight());
                 projPro_text.setCompoundDrawables(null,null,d,null);
                 flags=true;
             }
            }
        });

    }  
        
        
       


    public void onClick(View v) {
        if (v.getId() == R.id.back)
            onBackPressed();
    }
}
