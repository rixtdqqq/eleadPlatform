package com.elead.adapter;

import android.content.Context;

import com.elead.card.cardviews.BarChartCard;
import com.elead.eplatform.R;
import com.elead.qs.model.QsBarChartModel;
import com.elead.views.baseadapter.CommonAdapter;
import com.elead.views.baseadapter.ViewHolder;

import java.util.List;

/**
 * Created by jiangdikai on 2017/2/10.
 */

public class QsDetailAdapter extends CommonAdapter<QsBarChartModel> {

    public QsDetailAdapter(Context context, List mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder helper, QsBarChartModel item) {
        BarChartCard barChartCard = helper.getView(R.id.barchart);
        barChartCard.setTitle(item.title);
        barChartCard.setData(item.yVals1);

    }
}
