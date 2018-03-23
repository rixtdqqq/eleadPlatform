package com.elead.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.alibaba.fastjson.JSON;
import com.elead.activity.MainActivity;
import com.elead.application.MyApplication;
import com.elead.approval.entity.ApprovalGridEntity;
import com.elead.card.CardListAdapter;
import com.elead.card.mode.BaseCardInfo;
import com.elead.eplatform.R;
import com.elead.utils.IoUtil;
import com.elead.views.BaseCommonAdapter;
import com.elead.views.CustomGridView;
import com.elead.views.pulltorefresh.PullToRefreshListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/8/18 0018.
 */
public class WorkFragment extends BaseCommonFragment {

    private View view;
    private MyApplication application;
    private MainActivity context;
    private List<Map<String, Object>> recyclerGridItems;
    ScrollView slv;

    ImageView img_punch_the_clock;

    private CustomGridView common_grid;
    private BaseCommonAdapter commonGridAdapter;
    private List<ApprovalGridEntity> commonGridEntityList = new ArrayList<ApprovalGridEntity>();

    /**
     * 请求更新显示的View
     */
    protected static final int UPDATE_IMAGE = 1;

    private static final int DOWNLOAD_FAIL_BANNAER = 2;

    private static final int REQUESTCODE_PICK = 1;
    private static final int REQUESTCODE_CUTTING = 2;
    private static final int SELECT_PIC_KITKAT = 3;
    private static final int SELECT_PIC = 4;
    protected static final String REQUEST_GET_DATA_CODE = "GET_DATA_CODE";
    protected static final String REQUEST_GET_MORE_DATA_CODE = "GET_MORE_DATA_CODE";
    protected String REQUEST_MARK_READ_CODE = "MARK_READ_CODE";
    private PullToRefreshListView lvMsgNotificationsEntryList;
    protected boolean isRefreshing;
    protected int currentPage;
    protected boolean isHasMore;
    private CardListAdapter adapter;
    private List<BaseCardInfo> cardList = new ArrayList<>();

    private UIHandler mHandler;

    private class UIHandler extends Handler {

        private WeakReference<WorkFragment> self;

        public UIHandler(WorkFragment self) {
            this.self = new WeakReference<WorkFragment>(self);
        }

        @Override
        public void handleMessage(Message msg) {
            WorkFragment workFragment = self.get();
            if (workFragment == null) {
                return;
            }
            switch (msg.what) {
                case DOWNLOAD_FAIL_BANNAER:
                    break;
                case UPDATE_IMAGE:
                    break;
                case 100:
                    lvMsgNotificationsEntryList.onRefreshComplete();
                    if (null == adapter) {
                        adapter = new CardListAdapter(context, cardList);
                        lvMsgNotificationsEntryList.setAdapter(adapter);
                    } else {
                        adapter.notifyDataSetChanged();
                    }
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (null == context)
            context = (MainActivity) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (null == view) {
            view = inflater.inflate(R.layout.fragment_work, container, false);
        }
        mHandler = new UIHandler(this);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //        inItView();
        initList();
        initDate();
    }

    private void initDate() {
        try {
            String fromAssets = IoUtil.getFromAssets(context, "EPWork");
            JSONObject jsonObject = new JSONObject(fromAssets);
            String object = jsonObject.getString("cards");
            cardList = JSON.parseArray(object,
                    BaseCardInfo.class);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        mHandler.sendEmptyMessageDelayed(100, 1);
    }

    private void initList() {

        lvMsgNotificationsEntryList = (PullToRefreshListView) view.findViewById(R.id.pulltorefreshlistview);
        lvMsgNotificationsEntryList.getListview().setDividerHeight(0);
        lvMsgNotificationsEntryList.getListview().setDivider(getResources().getDrawable(R.color.transport));
        // 刚开始没数据
    }

}
