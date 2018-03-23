package com.elead.card.cardviews;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.elead.card.interfaces.ICard;
import com.elead.card.mode.BaseCardInfo;
import com.elead.eplatform.R;

public class InfoDesRightPicCard extends BaseCard implements ICard {
    private TextView tv_title, tv_content;
    private ImageView img_right;

    public InfoDesRightPicCard(Context context) {
        this(context, null);
    }

    public InfoDesRightPicCard(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public InfoDesRightPicCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public int initLayout() {
        // TODO Auto-generated method stub
        return R.layout.card_info_des_right_pic;
    }

    @Override
    public void initView() {
        tv_title = getView(R.id.tv_title);
        tv_content = getView(R.id.tv_content);
        img_right = getView(R.id.img_right);
    }

    @Override
    public void setDate(BaseCardInfo baseCardInfo) {
        switch (baseCardInfo.cardDetail.cardIndex) {
            case 0:
                break;
            case 1:
                tv_title.setText("全金属机身构架");
                tv_content.setText("全金属一体化机身,精雕细琢 内外皆美;\n" +
                        "一气呵成的机身线条、金的绝妙搭配\n" + "1.6mm的极窄显示边框,呈现非凡乐趣"
                );
                img_right.setImageResource(R.drawable.rdc_img4);
                break;
            case 2:
                tv_title.setText("双摄像头");
                tv_content.setText("全金属一体化机身,精雕细琢 内外皆美;\n" +
                        "一气呵成的机身线条、金的绝妙搭配");
                img_right.setImageResource(R.drawable.rdc_img5);
                break;
            case 3:
                tv_title.setText("重新定义互联网");
                tv_content.setText("全金属一体化机身,精雕细琢 内外皆美;\n" +
                        "一气呵成的机身线条、金的绝妙搭配");
                img_right.setImageResource(R.drawable.rdc_img1);
                break;
            case 4:
                tv_title.setText("更好的内容才是更好的设计");
                tv_content.setText("全金属一体化机身,精雕细琢 内外皆美;\n" +
                        "一气呵成的机身线条、金的绝妙搭配");
                img_right.setImageResource(R.drawable.rdc_img2);
                break;
            case 5:
                tv_title.setText("多类桌面");
                tv_content.setText("打破科技,艺术与互联网的边界,生态集成,深度定制.");
                img_right.setImageResource(R.drawable.rdc_img3);
                break;
        }
    }
}
