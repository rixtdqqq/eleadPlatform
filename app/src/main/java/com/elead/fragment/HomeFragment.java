package com.elead.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.elead.card.CardListAdapter;
import com.elead.card.cardviews.AdvertCard;
import com.elead.card.mode.BaseCardInfo;
import com.elead.eplatform.R;
import com.elead.utils.IoUtil;
import com.elead.views.BaseFragment;
import com.elead.views.pulltorefresh.PullToRefreshListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


/**
 * Created by Administrator on 2016/8/18 0018.
 */
public class HomeFragment extends BaseFragment {
    protected static final String REQUEST_GET_DATA_CODE = "GET_DATA_CODE";
    protected static final String REQUEST_GET_MORE_DATA_CODE = "GET_MORE_DATA_CODE";
    protected String REQUEST_MARK_READ_CODE = "MARK_READ_CODE";
    PullToRefreshListView pulltorefreshlistview;
    private CardListAdapter adapter;
    private List<BaseCardInfo> cardList;


    public HomeFragment() {
    }

    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 100:
                    if (null == adapter) {
                        adapter = new CardListAdapter(mContext, cardList);
                        pulltorefreshlistview.setAdapter(adapter);
                    } else {
                        adapter.notifyDataSetChanged();
                    }
                    pulltorefreshlistview.onRefreshComplete();


                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return getView(inflater, R.layout.fragment_home, container);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initList();
        initDate();
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

    private void initDate() {
        try {
            String fromAssets = IoUtil.getFromAssets(mContext, "EPHome");
            JSONObject jsonObject = new JSONObject(fromAssets);
            String object = jsonObject.getString("cards");
            Log.d("aaa", object);
            cardList = JSON.parseArray(object,
                    BaseCardInfo.class);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        mHandler.sendEmptyMessageDelayed(100, 1);
    }


    private void initList() {

        pulltorefreshlistview = (PullToRefreshListView) mView.findViewById(R.id.pulltorefreshlistview);
    }

    private void doRequest(String requestGetDataCode, String string) {
        if (requestGetDataCode.equals(REQUEST_GET_DATA_CODE)) {

        } else if (requestGetDataCode.equals(REQUEST_GET_MORE_DATA_CODE)) {

        } else if (requestGetDataCode.equals(REQUEST_MARK_READ_CODE)) {

        }
        initDate();

        // TODO Auto-generated method stub

    }

}
