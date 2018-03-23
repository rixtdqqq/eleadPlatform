package com.elead.operate.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.alibaba.fastjson.JSON;
import com.elead.activity.BaseActivity;
import com.elead.card.CardEnums;
import com.elead.card.CardListAdapter;
import com.elead.card.cardviews.TbSelectCard;
import com.elead.card.mode.BaseCardInfo;
import com.elead.card.mode.NavInfo;
import com.elead.card.views.BaseWaveView;
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


/**
 * Created by gly on 2016/10/11 0011.
 */

public class OperatTabActivity extends BaseActivity {
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
    private BaseWaveView tu;
    private MyBrocastReciver receiver;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.list_tab_layout);
        ButterKnife.bind(this);
//        getNeedCards();
        setTitle("需求", TitleType.STATISTICAL);
        initDate(IoUtil.getFromAssets(this, "EPAPPDevelop"));

        initList();
        initTbSelecter();
    }

    private void initDate(String result) {
        try {
            JSONObject jsonObject = new JSONObject(result);
            tabList = JSON.parseArray(jsonObject.getJSONArray("data").toString(), NavInfo.class);
            cardList.addAll(tabList.get(0).getCards());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        handler.sendEmptyMessageDelayed(100, 1000);
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
            }
        }


        reportMainTb.inItTabS(tbSelecterInfo);
        reportMainTb.setOnTbSelectListener(new TbSelector.onTbSelectListener() {
            @Override
            public void onSelectChange(int position) {
                setTitle(tabList.get(position).getNavTitle(), TitleType.STATISTICAL);
                cardList.clear();
                List<BaseCardInfo> cards = tabList.get(position).getCards();
                if (cards.contains(new BaseCardInfo(CardEnums.TabItemCard.toString()))) {
                    for (BaseCardInfo cardInfo : cards) {
                        if (cardInfo.tabType != 1) {
                            cardList.add(cardInfo);
                        }
                    }
                } else {
                    cardList.addAll(cards);
                }

                adapter.notifyDataSetChanged();
            }
        });

        reportMainTb.setCurrItem(0);
    }

    private void registReciver() {
        receiver = new MyBrocastReciver();
        IntentFilter filter = new IntentFilter(TbSelectCard.action);
        mContext.registerReceiver(receiver, filter);
    }

    @Override
    public void onResume() {
        super.onResume();
        registReciver();
    }

    @Override
    public void onPause() {
        super.onPause();
        mContext.unregisterReceiver(receiver);
    }

    class MyBrocastReciver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().contains(TbSelectCard.action)) {
                int index = (int) intent.getExtras().get(TbSelectCard.INDEX);
                for (int i = 0; i < cardList.size(); i++) {
                    if (!cardList.get(i).equals(CardEnums.TabItemCard.toString())) {
                        cardList.remove(i);
                        i--;
                    }
                }
                NavInfo navInfo = tabList.get(reportMainTb.getCurrItem());
                for (BaseCardInfo cardInfo : navInfo.getCards()) {
                    if (cardInfo.tabType == index && !cardInfo.cardType.equals(CardEnums.TabItemCard.toString())) {
                        cardList.add(cardInfo);
                    }
                }
                adapter.notifyDataSetChanged();
            }
        }
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
    }

    private void doRequest(String requestGetDataCode, String string) {
        if (requestGetDataCode.equals(REQUEST_GET_DATA_CODE)) {

        } else if (requestGetDataCode.equals(REQUEST_GET_MORE_DATA_CODE)) {

        } else if (requestGetDataCode.equals(REQUEST_MARK_READ_CODE)) {

        }
    }
}
