package com.elead.card.cardviews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.elead.card.entity.HotEntry;
import com.elead.card.utils.HotUtil;
import com.elead.eplatform.R;
import com.elead.views.baseadapter.CommonAdapter;
import com.elead.views.baseadapter.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/28 0028.
 */

public class HotSaleCard extends LinearLayout {

    private int imgRourse[] = {R.drawable.card_listv_1, R.drawable.card_list_2, R.drawable.card_list_3};
    private String titleText[] = {"Merchandise 01", "Merchandise 02", "Merchandise 02"};
    private String contentText[] = {"Readable content of a page", "Various versions evolved over", "Letraset sheets containing"};
    private int Number[] = {1232, 980, 1009};
    private String time[] = {"Last Sale Today", "Last Sale Today", "Last Sale Today"};
    private String amonut[] = {"15 Sale", "11 Sale", "16 Sale"};
    private List<HotEntry> list = new ArrayList<>();

    private ListView listV;
    private HotEntry entry;
    private CommonAdapter<HotEntry> commoAdapter;

    public HotSaleCard(Context context) {
        this(context,null);
    }

    public HotSaleCard(Context context, AttributeSet attrs) {
        this(context, null, 0);
    }

    public HotSaleCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData();
        initViews(context);
    }

    private void initData() {
        for (int i = 0; i < imgRourse.length; i++) {
            entry = new HotEntry();
            entry.setImgRourse(imgRourse[i]);
            entry.setTitle(titleText[i]);
            entry.setContent(contentText[i]);
            entry.setNumber(Number[i]);
            entry.setTime(time[i]);
            entry.setAmonut(amonut[i]);
            list.add(entry);
        }
    }


    private void initViews(Context context) {
        LayoutInflater.from(context).inflate(R.layout.card_hot_list_activity, this, true);
        listV = (ListView) findViewById(R.id.hostlistiv);


        if (commoAdapter == null) {
            commoAdapter = new CommonAdapter<HotEntry>(context, list, R.layout.card_hot_list_item) {
                @Override

                public void convert(ViewHolder helper, HotEntry item) {

                    helper.setImageResource(R.id.img_id, item.getImgRourse());
                    helper.setText(R.id.content_one_text_id, item.getContent());
                    helper.setText(R.id.title_one_text_id, item.getTitle());
                    helper.setText(R.id.number_id, item.getNumber() + "");
                    helper.setText(R.id.time_id, item.getTime());
                    helper.setText(R.id.amount_id, item.getAmonut());
                }
            };

            listV.setAdapter(commoAdapter);
            HotUtil.setListViewHeightBasedOnChildren(listV);
        } else {
            commoAdapter.notifyDataSetChanged();
        }

    }


}
