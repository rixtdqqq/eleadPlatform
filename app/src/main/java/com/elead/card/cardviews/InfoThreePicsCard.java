package com.elead.card.cardviews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.elead.card.mode.BaseCardInfo;
import com.elead.eplatform.R;

/**
 * Created by Administrator on 2016/11/10 0010.
 */

public class InfoThreePicsCard extends BaseCard {

    private TextView title;
    private TextView timeIv;
    private TextView sourceIv;
    private TextView praiseIv;
    private ImageView uc_img_one;
    private ImageView uc_img_two;
    private ImageView uc_img_three;
    private TextView zhui_zhong_tv;


    public InfoThreePicsCard(Context context) {
        super(context);
    }

    public InfoThreePicsCard(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public InfoThreePicsCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public int initLayout() {
        return R.layout.card_uc_three_item;
    }

    @Override
    public void initView() {
        title = getView(R.id.title_iv);
        timeIv = getView(R.id.time_id);
        sourceIv =getView(R.id.adress_id);
        praiseIv = getView(R.id.praise_id);
        uc_img_one = getView(R.id.uc_res_one_im);
        uc_img_two = getView(R.id.uc_res_two_im);
        uc_img_three = getView(R.id.uc_res_three_im);
            zhui_zhong_tv=getView(R.id.zhui_zong_id);
    }

    @Override
    public void setDate(BaseCardInfo baseCardInfo) {
        switch (baseCardInfo.cardDetail.cardIndex) {
            case 1:
                uc_img_one.setImageResource(R.drawable.phone0_3x);
                uc_img_two.setImageResource(R.drawable.phone1_3x);
                uc_img_three.setImageResource(R.drawable.phone2_3x);
            break;

            case 2 :
                uc_img_one.setImageResource(R.drawable.rightiicimage2_3x);
                uc_img_two.setImageResource(R.drawable.rightpicimage4_3x);
                uc_img_three.setImageResource(R.drawable.rightpicimage1);
                zhui_zhong_tv.setVisibility(View.VISIBLE);

            break;

            case 3:
                uc_img_one.setImageResource(R.drawable.rightpicimage1);
                uc_img_two.setImageResource(R.drawable.rightpicimage4_3x);
                uc_img_three.setImageResource(R.drawable.rightpicimage5_3x);
            break;

            case 4:
//                uc_img_one.setImageResource(R.drawable.phone0_3x);
//                uc_img_two.setImageResource(R.drawable.phone1_3x);
//                uc_img_three.setImageResource(R.drawable.phone2_3x);
                break;
        }
    }
}
