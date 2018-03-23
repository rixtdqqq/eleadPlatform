package com.elead.develop.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.elead.develop.adapter.MyAdapter;
import com.elead.develop.entity.PoPreViewEnty;
import com.elead.eplatform.R;
import com.elead.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016/11/2 0002.
 */

public class PoThreePreView extends LinearLayout implements View.OnClickListener, AbsListView.OnScrollListener {

    private LinearLayout ll;
    private Context mContext;
    private int img[] = {R.drawable.card_listv_1, R.drawable.card_list_2, R.drawable.card_list_3, R.drawable.card_listv_1, R.drawable.card_list_2, R.drawable.card_list_3, R.drawable.card_list_2};
    private String Toptitle[] = {"iCaptain for mobile in the HR", "云朵云_MTL颗粒2016产品版本V02nn", "华为人才社区IHUAWEI二期需求V02BBB", "iCaptain for mobile in the HR", "云朵云_MTL颗粒2016产品版本V02nn", "华为人才社区IHUAWEI二期需求V02BBB", "云朵云_MTL颗粒2016产品版本V02nn"};
    private String time[] = {"2016/06/15 09:04:03", "2016/06/15 09:04:03", "2016/06/15 09:04:03", "2016/06/15 09:04:03", "2016/06/15 09:04:03", "2016/06/15 09:04:03", "2016/06/15 09:04:03"};
    private String unit[] = {"移动业务单元", "移动业务单元", "移动业务单元", "移动业务单元", "移动业务单元", "移动业务单元", "综合业务单元"};
    private boolean state[] = {true, false, true, true, true, true, true};

    private List<PoPreViewEnty> listAll = new ArrayList<>();
    private List<PoPreViewEnty> list = new ArrayList<>();
    private PoPreViewEnty popreviewentry;
    private ListView listiv;
    private View btMoreCheck;
    private int VIEW_COUNT = 3;
    private int index = 0;
    public MyAdapter adapter;
    private Handler handler = new Handler();
    private int lastVisibleIndex;
    private int totalItemCounts;
    private ProgressBar progreebar;
    private TextView load_more_text;
    private TextView load_more_iv;
    private AdapterView.OnItemClickListener onItemClickListener;
    private int type;
    private int topColor;
    private int bottomColor;
    private int lineColor;
    private int drawableSrc;
    //private List<PoPreViewEnty> listAll;

    public PoThreePreView(Context context) {
        this(context, null);
    }

    public PoThreePreView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
//
//    public PoThreePreView(Context context) {
//        super(context);
//
//    }
//
//    public PoThreePreView(Context context, AttributeSet attrs) {
//        super(context, attrs);
//
//    }

    public PoThreePreView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initAttrs(context, attrs, defStyleAttr);
        setData();
        initViews();
    }

    public void initAttrs(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.LoadMoreItem, defStyleAttr, 0);
        topColor = a.getColor(R.styleable.LoadMoreItem_topColor, 0xff4E5671);
        bottomColor = a.getColor(R.styleable.LoadMoreItem_bottomColor, 0XF758698);
        lineColor = a.getColor(R.styleable.LoadMoreItem_lineColors, 0xffD9D9D9);
        drawableSrc = a.getResourceId(R.styleable.LoadMoreItem_drawSrc, R.drawable.right_arrow);

        a.recycle();
    }

    private void initViews() {
        LayoutInflater.from(mContext).inflate(R.layout.card_po_preview_activity, this, true);
        btMoreCheck = LayoutInflater.from(mContext).inflate(R.layout.card_check_more_po, null);
        listiv = (ListView) findViewById(R.id.listVi);
        // Log.i("TAG","tpee"+type);
        adapter = new MyAdapter(mContext, list);
        listiv.addFooterView(btMoreCheck);
        listiv.setAdapter(adapter);
        adapter.setColor(topColor, bottomColor, lineColor, drawableSrc);

        // Utils.setListViewHeightBasedOnChildren(listiv);

        ll = (LinearLayout) btMoreCheck.findViewById(R.id.check_more_ll);
        load_more_text = (TextView) btMoreCheck.findViewById(R.id.load_more_text_iv);
        progreebar = (ProgressBar) btMoreCheck.findViewById(R.id.progreebar);
        load_more_iv = (TextView) btMoreCheck.findViewById(R.id.loading_more_iv);
        ll.setOnClickListener(PoThreePreView.this);
        // changeButtonStatus();
        listiv.setOnScrollListener(PoThreePreView.this);

    }


    public void setListDatas(List<PoPreViewEnty> listall) {
        this.listAll = listall;
        if(null!=listAll) {
            adapter = new MyAdapter(mContext, list);
            listiv.setAdapter(adapter);
            adapter.setColor(topColor, bottomColor, lineColor, drawableSrc);
            adapter.notifyDataSetChanged();
        }
}



    private void setData() {
        for (int i = 0; i < 3; i++) {
            popreviewentry = new PoPreViewEnty();
            popreviewentry.setImgSrc(img[i]);
            popreviewentry.setTopTitle(Toptitle[i]);
            popreviewentry.setBottomTime(time[i]);
            popreviewentry.setUnit(unit[i]);
            popreviewentry.setState(state[i]);
           // if (null!=listAll) {
                //popreviewentry = listAll.get(i);
                list.add(popreviewentry);
           // }
        }

        for (int i = 0; i < time.length; i++) {
            popreviewentry = new PoPreViewEnty();
            popreviewentry.setImgSrc(img[i]);
            popreviewentry.setTopTitle(Toptitle[i]);
            popreviewentry.setBottomTime(time[i]);
            popreviewentry.setUnit(unit[i]);
            popreviewentry.setState(state[i]);
            listAll.add(popreviewentry);
        }

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.check_more_ll:
                progreebar.setVisibility(View.VISIBLE);
                load_more_iv.setVisibility(View.VISIBLE);
                load_more_text.setVisibility(View.GONE);

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (totalItemCounts == listAll.size() + 1) {
                           listiv.removeFooterView(btMoreCheck);
                            adapter.notifyDataSetChanged();
                            Toast.makeText(mContext, "数据全部加载完成", Toast.LENGTH_SHORT).show();
                        }
                        loadMoreDate();// 加载更多数据

                        load_more_text.setVisibility(View.VISIBLE);
                        progreebar.setVisibility(View.GONE);
                        load_more_iv.setVisibility(View.GONE);
                        adapter.notifyDataSetChanged();
                    }

                }, 2000);

                break;

        }

    }

    private void loadMoreDate() {
        int count = adapter.getCount();
        if (count + 3 < listAll.size()) {
            for (int i = count; i < count + 3; i++) {
              PoPreViewEnty popreviewentry = new PoPreViewEnty();
                popreviewentry.setImgSrc(img[i]);
                popreviewentry.setTopTitle(Toptitle[i]);
                popreviewentry.setBottomTime(time[i]);
                popreviewentry.setUnit(unit[i]);
                popreviewentry.setState(state[i]);
              //  PoPreViewEnty popreviewentry =listAll.get(i);
                list.add(popreviewentry);
            }
        } else {
            // 数据已经不足3条
            for (int i = count; i < listAll.size(); i++) {
                PoPreViewEnty popreviewentry = new PoPreViewEnty();
                popreviewentry.setImgSrc(img[i]);
                popreviewentry.setTopTitle(Toptitle[i]);
                popreviewentry.setBottomTime(time[i]);
                popreviewentry.setUnit(unit[i]);
                popreviewentry.setState(state[i]);
               // PoPreViewEnty popreviewentry =listAll.get(i);
                list.add(popreviewentry);
                progreebar.setVisibility(View.VISIBLE);
                listiv.removeFooterView(btMoreCheck);
                ToastUtil.showToast(mContext, "数据全部加载完成，没有更多数据！", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
        listiv.setOnItemClickListener(onItemClickListener);
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int scrollState) {
        if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE
                && lastVisibleIndex == adapter.getCount()) {
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
// 计算最后可见条目的索引
        lastVisibleIndex = firstVisibleItem + visibleItemCount - 1;

        //  所有的条目已经和最大条数相等，则移除底部的View

        if (totalItemCount == listAll.size() + 1) {
            listiv.removeFooterView(btMoreCheck);
            ToastUtil.showToast(mContext, "数据全部加载完成，没有更多数据！", Toast.LENGTH_LONG).show();
        }

        // totalItemCounts = totalItemCount;

//    }
//
//    lItemCounts = totalItemCount;

    }


    public interface OnClickItem {
        void clickItem();
    }


    public void setType(int type) {
        this.type = type;
    }

}
