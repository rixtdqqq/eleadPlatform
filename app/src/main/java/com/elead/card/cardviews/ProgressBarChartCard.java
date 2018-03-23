package com.elead.card.cardviews;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.elead.card.entity.ChartEntry;
import com.elead.card.utils.HotUtil;
import com.elead.card.views.RoundCornerProgressBar;
import com.elead.eplatform.R;
import com.elead.views.baseadapter.CommonAdapter;
import com.elead.views.baseadapter.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/19 0019.
 */

public class ProgressBarChartCard extends LinearLayout {

    private String text[] = {"the product is Awesome", "Yeah I'm Satisfied with it", "Average product,not bad", "No usefully at all it Sucks"};
    private int progress[] = {70, 40, 20, 30};
    private int imge[] = {R.drawable.card_simle_1, R.drawable.card_simle_2, R.drawable.card_simle_3, R.drawable.card_simle_4};
    public List<ChartEntry> list = new ArrayList<ChartEntry>();
    private ChartEntry Entry;

    private String tv;
    private String tv1;
    private CommonAdapter<ChartEntry> commonAdapter;

    public ProgressBarChartCard(Context context) {
        this(context,null);
        //   setData();

    }

    public ProgressBarChartCard(Context context,AttributeSet attrs) {
        this(context,attrs,0);

    }


    public ProgressBarChartCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setData();
        init(context);
    }

    private void setData() {
        for (int i = 0; i < text.length; i++) {
            Entry = new ChartEntry();
            Entry.setName(text[i]);
            Entry.setProgress(progress[i]);
            Entry.setImage(imge[i]);
            list.add(Entry);

        }
    }


    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.card_progress_list_activity, this,true);
        View viewfresh = LayoutInflater.from(context).inflate(R.layout.card_item_tittle_chart,null);

        ListView lv = (ListView) findViewById(R.id.listIv);
        lv.addHeaderView(viewfresh);
//        ChartAdater adapter=new ChartAdater(list,context);
//        lv.setAdapter(adapter);
        if (commonAdapter == null) {
            commonAdapter = new CommonAdapter<ChartEntry>(context, list, R.layout.card_listview_chart_progress_item) {
                @Override
                public void convert(ViewHolder helper, ChartEntry item) {
                    RoundCornerProgressBar p = (RoundCornerProgressBar) helper.getView(R.id.progressbarid).findViewById(R.id.progressbarid);
                    p.setProgressBackgroundColor(Color.parseColor("#DAE4E8"));
                    p.setProgress(item.getProgress());
                    helper.setImageResource(R.id.simle_img, item.getImage());
                    helper.setText(R.id.text_id, item.getName());

                    if (TextUtils.equals(item.getName(), "the product is Awesome")) {
                        p.setProgressColor(Color.parseColor("#5EC7BD"));
                    } else if (TextUtils.equals(item.getName(), "Yeah I'm Satisfied with it")) {
                        p.setProgressColor(Color.parseColor("#38A4DD"));
                    } else if (TextUtils.equals(item.getName(), "Average product,not bad")) {
                        p.setProgressColor(Color.parseColor("#FFC400"));
                    } else {
                        p.setProgressColor(Color.parseColor("#FF7043"));
                    }
                }
            };
            lv.setAdapter(commonAdapter);
            HotUtil.setListViewHeightBasedOnChildren(lv);
        } else {
            commonAdapter.notifyDataSetChanged();
        }
    }


}
