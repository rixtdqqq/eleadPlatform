package com.elead.develop.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.elead.develop.entity.PoPreViewEnty;
import com.elead.develop.widget.PoItemPreView;

import java.util.List;

/**
 * Created by Administrator on 2016/11/3 0003.
 */

public class MyAdapter extends BaseAdapter {

    private int type;
    private int index;
    private int VIEW_COUNT;
    private List<PoPreViewEnty> list;
    private Context mContext;
    public PoItemPreView poItemPreView;
    private int topColor;
    private int bottomColor;
    private int lineColor;
    private int drawSrc;

    public MyAdapter(Context context, List<PoPreViewEnty> list) {
        this.mContext = context;
        this.list = list;

    }


    public void setColor(int topColor, int bottomColor, int lineColor, int drawSrc) {
        this.topColor = topColor;
        this.bottomColor = bottomColor;
        this.lineColor = lineColor;
        this.drawSrc = drawSrc;
    }

    @Override
    public int getCount() {
//        int ori = VIEW_COUNT * INDEX;
//        if (list.size() - ori < VIEW_COUNT) {
//            return list.size()-ori;
//        } else {
//            return VIEW_COUNT;
//        }

        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        poItemPreView = new PoItemPreView(mContext);
        PoPreViewEnty entry = list.get(position);
        poItemPreView.setColorFont(topColor, bottomColor,lineColor,drawSrc);
        poItemPreView.InitData(entry.getImgSrc(), entry.getTopTitle(), entry.getBottomTime(), entry.getUnit(), entry.isState());
        return poItemPreView;
    }


}
