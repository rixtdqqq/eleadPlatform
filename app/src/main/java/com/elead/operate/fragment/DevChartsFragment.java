package com.elead.operate.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.elead.card.CardListAdapter;
import com.elead.card.mode.BaseCardInfo;
import com.elead.eplatform.R;
import com.elead.popuJarLib.popuJar.PopuItem;
import com.elead.popuJarLib.popuJar.PopuJar;
import com.elead.utils.DensityUtil;
import com.elead.views.BaseFragment;
import com.elead.views.pulltorefresh.PullToRefreshListView;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/11 0011.
 */

public class DevChartsFragment extends BaseFragment {
    TextView developCenterTv;
    PullToRefreshListView lvMsgNotificationsEntryList;
    private View view;
    private static final int REQUESTCODE_PICK = 1;
    private static final int REQUESTCODE_CUTTING = 2;
    private static final int SELECT_PIC_KITKAT = 3;
    private static final int SELECT_PIC = 4;
    protected static final String REQUEST_GET_DATA_CODE = "GET_DATA_CODE";
    protected static final String REQUEST_GET_MORE_DATA_CODE = "GET_MORE_DATA_CODE";
    protected String REQUEST_MARK_READ_CODE = "MARK_READ_CODE";
    protected boolean isRefreshing;
    protected int currentPage;
    protected boolean isHasMore;
    private CardListAdapter adapter;
    private List<BaseCardInfo> list = new ArrayList<BaseCardInfo>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (null == view) {
            view = inflater.inflate(R.layout.dev_fragment_chart, container, false);
        }
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (null == lvMsgNotificationsEntryList) {
            initListView();
            initCenterTv();
            initBackImg();
        }
        getMarketIngDate();
    }

    private void initBackImg() {
        mContext.findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.onBackPressed();
            }
        });
    }

    private void initCenterTv() {
        developCenterTv = (TextView) mContext.findViewById(R.id.title);
        developCenterTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PopuJar popujar = new PopuJar(mContext);
                int textColor = Color.WHITE;
                int textSize = DensityUtil.px2dip(mContext, mContext.getResources().getDimensionPixelSize(R.dimen.title_textsize));
                popujar.addPopuItem(new PopuItem(0, "Marketing", textColor, textSize));
                popujar.addPopuItem(new PopuItem(1, "Reporter", textColor, textSize));
                popujar.setBackground(R.drawable.popview_bg);
                popujar.setOnPopuItemClickListener(new PopuJar.OnPopuItemClickListener() {
                    @Override
                    public void onItemClick(PopuJar source, int pos, int actionId) {
                        developCenterTv.setText(popujar.getPopuItem(pos).getTitle());
                        switch (pos) {
                            case 0:
                                getMarketIngDate();
                                break;
                            case 1:
                                getReporterDate();
                                break;
                        }

                    }
                });
                popujar.show(v);
            }
        });
    }

    private void getMarketIngDate() {
        AbstractList<BaseCardInfo> list = new ArrayList<BaseCardInfo>();
        list.add(new BaseCardInfo("list_item_project"));
        list.add(new BaseCardInfo("list_item_distribution"));
        list.add(new BaseCardInfo("list_item_multiplecirclecard"));
        list.add(new BaseCardInfo("list_item_hotsale"));
        list.add(new BaseCardInfo("list_item_moreappcard"));
        initDate(list);
    }

    private void getReporterDate() {
        AbstractList<BaseCardInfo> list = new ArrayList<BaseCardInfo>();

        list.add(new BaseCardInfo("list_item_contain_dital"));
        list.add(new BaseCardInfo("reporter_v_line_chart"));
        list.add(new BaseCardInfo("reporter_v_annular_chart"));
        list.add(new BaseCardInfo("card_progress_item"));

        initDate(list);
    }


    public void initDate(List<BaseCardInfo> baseCardInfos) {
        if (baseCardInfos.size() > 0) {
            list.clear();
            list.addAll(baseCardInfos);
            if (null == adapter) {
                adapter = new CardListAdapter(mContext, list);
                lvMsgNotificationsEntryList.setAdapter(adapter);
            } else {
                adapter.notifyDataSetChanged();
            }
        }

    }

    public PullToRefreshListView getListView() {
        return lvMsgNotificationsEntryList;
    }

    private void initListView() {
        lvMsgNotificationsEntryList = (PullToRefreshListView) mContext.findViewById(R.id.pulltorefreshlistview);
        // 刚开始没数据
    }


}
