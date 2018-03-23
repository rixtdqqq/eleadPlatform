package com.elead.card.cardviews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.elead.card.entity.IndustrialDesignEntry;
import com.elead.card.interfaces.ICard;
import com.elead.card.mode.BaseCardInfo;
import com.elead.eplatform.R;
import com.elead.views.CustomGridView;
import com.elead.views.baseadapter.CommonAdapter;
import com.elead.views.baseadapter.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import static com.elead.application.MyApplication.mContext;

/**
 * Created by Administrator on 2016/11/16 0016.
 */

public class IndustrialDesignCard extends LinearLayout implements ICard {


    private CustomGridView gridView;
    private CommonAdapter commoAdapter;
    public int img[] = {R.drawable.xe_xiang_tou, R.drawable.guan_xue_fangk, R.drawable.close_loop, R.drawable.pdaf, R.drawable.ruan_yin_hdr, R.drawable.xe_xiang_tou};
    public String title[] = {"21M摄像头", "光学防抖", "Close Loop", "PDAF", "软件双HDR", "F2.0大光圈"};
    public List<IndustrialDesignEntry> list = new ArrayList<>();

    String descriable="-全金屬一體化機身,金雕習作,內外皆美;/n-創新全旋浮設計 ，无边框包裹的创新设计;/n-一次柯成的机身线条.金属一体式后盖与玻璃的绝妙搭配;/n-1.6mm的极宰显示边框，呈现大不一样;/n-创新的扬声器控体设计，能自动过滤破音，保证外放悦耳；/n-简约银和典雅金，色彩搭配尽显独特魅力。";
    public IndustrialDesignEntry entry;

    public IndustrialDesignCard(Context context) {
        this(context, null);
    }

    public IndustrialDesignCard(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IndustrialDesignCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        setDatas();
        initView();
    }


    //@Override
    public void initView() {
        LayoutInflater.from(mContext).inflate(R.layout.card_industrial_design_fragment, this, true);
        gridView = (CustomGridView) findViewById(R.id.gridView_id);



        if (commoAdapter == null) {
            commoAdapter = new CommonAdapter<IndustrialDesignEntry>(mContext, list, R.layout.industrial_design_item) {
                @Override

                public void convert(ViewHolder helper, IndustrialDesignEntry item) {

                    helper.setImageResource( R.id.industrial_design_img,item.getImg());
                    helper.setText(R.id.industrial_design_title, item.getImgName());
                }
            };

            gridView.setAdapter(commoAdapter);
//            HotUtil.setListViewHeightBasedOnChildren(listV);
        } else {
            commoAdapter.notifyDataSetChanged();
        }


    }

    public void setDatas() {
        for (int i = 0; i < img.length; i++) {
            entry = new IndustrialDesignEntry();
            entry.setImg(img[i]);
            entry.setImgName(title[i]);
            list.add(entry);
        }
    }


    @Override
    public void setDate(BaseCardInfo baseCardInfo) {

    }
}
