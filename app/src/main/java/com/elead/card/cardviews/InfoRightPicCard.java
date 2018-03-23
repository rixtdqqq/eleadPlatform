
package com.elead.card.cardviews;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.elead.card.interfaces.ICard;
import com.elead.card.mode.BaseCardInfo;
import com.elead.eplatform.R;

/**
 * Created by Administrator on 2016/11/10 0010.
 */

public class InfoRightPicCard extends BaseCard implements ICard {

    private TextView timeIv;
    private TextView sourceIv;
    private TextView praiseIv;
    private TextView title;
    private ImageView uc_img;
    private LinearLayout ll;
    private TextView zhui_zhong_tv;

    public InfoRightPicCard(Context context) {
        super(context);
    }

    public InfoRightPicCard(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public InfoRightPicCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public int initLayout() {
        return R.layout.card_uc_item;


    }

    @Override
    public void initView() {
        title = getView(R.id.title_iv);
        timeIv = getView(R.id.time_id);
        sourceIv = getView(R.id.adress_id);
        praiseIv = getView(R.id.praise_id);
        uc_img = getView(R.id.uc_res_im);
        ll = getView(R.id.uc_ll_id);
        zhui_zhong_tv=getView(R.id.zhui_zong_id);
    }

    @Override
    public void setDate(BaseCardInfo baseCardInfo) {
        switch (baseCardInfo.cardDetail.cardIndex) {
            case 6:
                title.setText("AA用车:智能短租租车平台");
                praiseIv.setText(0 + "");
                 uc_img.setImageResource(R.drawable.rightpicimage5_3x);
                break;

            case 1:
                 title.setText("一台主机同时控制多个显示器同步或不同画面设置技巧");
                 praiseIv.setText(23+"");
                 uc_img.setImageResource(R.drawable.rightpicimage1);
                sourceIv.setText("拉手网");
                break;
            case 2:
                title.setText("全球首条超级高铁或将落户迪拜:预计2021年投入运营");
                praiseIv.setText(153+"");
                uc_img.setImageResource(R.drawable.rightiicimage2_3x);
                zhui_zhong_tv.setVisibility(View.VISIBLE);
                sourceIv.setText("慕课网");
                break;
            case 3:
                title.setText("以后不用换手机了,它自己能做到自愈修复了");
                praiseIv.setText(1230 + "");
                uc_img.setImageResource(R.drawable.rightpicimage3_3x);
                sourceIv.setText("凤凰网");
                break;
            case 5:
                title.setText("39+13+2杀神拼吐血 硬拼49分钟!它被活活耗死");
                praiseIv.setText(36+"");
                uc_img.setImageResource(R.drawable.rightpicimage4_3x);
                sourceIv.setText("安卓巴士");
                zhui_zhong_tv.setVisibility(View.VISIBLE);
                Drawable d = getResources().getDrawable(R.drawable.teamlabel_3x);
                d.setBounds(0,0,d.getMinimumWidth(),d.getMinimumHeight());
                zhui_zhong_tv.setCompoundDrawables(d,null,null,null);
                zhui_zhong_tv.setTextColor(Color.parseColor("#12bdff"));
                break;

        }




    }
}
