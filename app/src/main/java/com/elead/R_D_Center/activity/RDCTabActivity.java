package com.elead.R_D_Center.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.elead.activity.BaseActivity;
import com.elead.card.CardListAdapter;
import com.elead.card.cardviews.AdvertCard;
import com.elead.card.mode.BaseCardInfo;
import com.elead.card.mode.NavInfo;
import com.elead.eplatform.R;
import com.elead.module.TbSelecterInfo;
import com.elead.utils.IoUtil;
import com.elead.views.pulltorefresh.PullToRefreshListView;
import com.elead.views.TbSelector;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.elead.activity.BaseActivity.TitleType.RDCENTER;

/**
 * Created by gly on 2016/10/11 0011.
 */

public class RDCTabActivity extends BaseActivity {
    @BindView(R.id.report_main_tb)
    TbSelector reportMainTb;
    private static final int REQUESTCODE_PICK = 1;
    private static final int REQUESTCODE_CUTTING = 2;
    private static final int SELECT_PIC_KITKAT = 3;
    private static final int SELECT_PIC = 4;
    protected static final String REQUEST_GET_DATA_CODE = "GET_DATA_CODE";
    protected static final String REQUEST_GET_MORE_DATA_CODE = "GET_MORE_DATA_CODE";
    protected String REQUEST_MARK_READ_CODE = "MARK_READ_CODE";
    @BindView(R.id.pulltorefreshlistview)
    PullToRefreshListView pulltorefreshlistview;
    private CardListAdapter adapter;
    private List<NavInfo> tabList = new ArrayList<>();
    private List<BaseCardInfo> cardList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.list_tab_layout);
        ButterKnife.bind(this);


        initDate(IoUtil.getFromAssets(this, "EPPhoneDevelop"));
        initList();
        initTbSelecter();
    }

    private void initDate(String result) {
        try {
            JSONObject jsonObject = new JSONObject(result);
            tabList = JSON.parseArray(jsonObject.getJSONArray("data").toString(), NavInfo.class);
            cardList.addAll(tabList.get(0).getCards());
            setTitle(tabList.get(0).getNavTitle(), RDCENTER);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        handler.sendEmptyMessageDelayed(100, 1000);
    }

    @Override
    public void onResume() {
        super.onResume();
        AdvertCard.isFragmentVisible = true;
    }

    @Override
    public void onPause() {
        super.onPause();
        AdvertCard.isFragmentVisible = false;
    }

    private void initTbSelecter() {
        List<TbSelecterInfo> tbSelecterInfo = new ArrayList<>();
        int defColor = getResources().getColor(R.color.fontcolors4);
        int pressColor = getResources().getColor(R.color.qianlv);
        String title;
        for (int i = 0; i < tabList.size(); i++) {
            title = tabList.get(i).getTabTitle();
            switch (i) {
                case 0:
                    tbSelecterInfo.add(new TbSelecterInfo(title, defColor, pressColor, R.drawable.need, R.drawable.need_press));
                    break;
                case 1:
                    tbSelecterInfo.add(new TbSelecterInfo(title, defColor, pressColor, R.drawable.quality, R.drawable.quality_press));
                    break;
                case 2:

                    tbSelecterInfo.add(new TbSelecterInfo(title, defColor, pressColor, R.drawable.control, R.drawable.control_press));
                    break;
                case 3:
                    tbSelecterInfo.add(new TbSelecterInfo(title, defColor, pressColor, R.drawable.charts, R.drawable.charts_pressed));
                    break;
                case 4:
                    tbSelecterInfo.add(new TbSelecterInfo(title, defColor, pressColor, R.drawable.rdc_qa_img, R.drawable.rdc_qa_img_pressed));
                    break;
            }
        }


        reportMainTb.inItTabS(tbSelecterInfo);
        reportMainTb.setOnTbSelectListener(new TbSelector.onTbSelectListener() {
            @Override
            public void onSelectChange(int position) {
                Log.d("aaaaa", position + "");

                if (position == 0 || position == 3) {
                    AdvertCard.isFragmentVisible = true;
                } else {
                    AdvertCard.isFragmentVisible = false;
                }
                setTitle(tabList.get(position).getNavTitle(), TitleType.RDCENTER);
                cardList.clear();
                cardList.addAll(tabList.get(position).getCards());

                adapter.notifyDataSetChanged();
            }
        });

        reportMainTb.setCurrItem(0);
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 100:
                    adapter.notifyDataSetChanged();
                    pulltorefreshlistview.onRefreshComplete();
                    break;

                default:
                    break;
            }
        }

        ;
    };


    private void initList() {
        adapter = new CardListAdapter(mContext, cardList);
        pulltorefreshlistview.setAdapter(adapter);
        pulltorefreshlistview.setBackgroundColor(getResources().getColor(R.color.rdc_base_bg_color));
    }
}
