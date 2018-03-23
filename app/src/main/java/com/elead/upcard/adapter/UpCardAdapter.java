package com.elead.upcard.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.elead.eplatform.R;
import com.elead.upcard.modle.DutyInfo;
import com.elead.upcard.utils.Utils;
import com.elead.utils.Util;

import java.util.List;

/**
 * @desc
 * @auth Created by Administrator on 2016/12/7 0007.
 */

public class UpCardAdapter extends BaseAdapter {
    private Context mContext;
    private List<DutyInfo> datas;

    public UpCardAdapter(List<DutyInfo> datas, Context context) {
        this.datas = datas;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return Util.isNotEmpty(datas) ? datas.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        // TODO Auto-generated method stub
        // 目前共2类
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 || position == datas.size()-1) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        try {
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                if (getItemViewType(position) == 0) {// 第一类item
                    convertView = LayoutInflater.from(mContext).inflate(
                            R.layout.up_card_i_details,
                            null);
                    holder.line_top = (View) convertView.findViewById(R.id.line_top);
                    holder.line_bottom = (View) convertView.findViewById(R.id.line_bottom);
                    holder.tv_up_card_type = (TextView) convertView.findViewById(R.id.tv_up_card_type);
                    holder.tv_up_card_label = (TextView) convertView.findViewById(R.id.tv_up_card_label);
                    holder.tv_up_card_time = (TextView) convertView.findViewById(R.id.tv_up_card_time);
                    holder.tv_up_card_status = (TextView) convertView.findViewById(R.id.tv_up_card_status);
                    holder.tv_up_card_location = (TextView) convertView.findViewById(R.id.tv_up_card_location);
                    holder.img_location = (ImageView) convertView.findViewById(R.id.img_location);
                } else if (getItemViewType(position) == 1) {//第二类item
                    convertView = LayoutInflater.from(mContext).inflate(
                            R.layout.up_card_i_details_child,
                            null);
                    holder.tv_up_card_time = (TextView) convertView.findViewById(R.id.tv_up_card_time);
                    holder.tv_up_card_location = (TextView) convertView.findViewById(R.id.tv_up_card_location);
                    holder.img_location = (ImageView) convertView.findViewById(R.id.img_location);
                }
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            String time = Utils.formatTime(datas.get(position).time);
            String location = datas.get(position).wifiname;
            if (!TextUtils.isEmpty(location)) {
                Util.setBackGround(mContext, holder.img_location, R.drawable.up_card_location_wifi);
            } else {
                location = datas.get(position).gpsaddr;
                if (!TextUtils.isEmpty(location)) {
                    Util.setBackGround(mContext, holder.img_location, R.drawable.up_card_location_gps);
                } else {
                    location = datas.get(position).locationName;
                    if (TextUtils.equals("fingerprint", datas.get(position).type)) {
                        Util.setBackGround(mContext, holder.img_location, R.drawable.up_card_location_fingerprint);
                    } else if (TextUtils.equals("IC_Card", datas.get(position).type)) {
                        Util.setBackGround(mContext, holder.img_location, R.drawable.up_card_location_card);
                    }
                }
            }
            if (getItemViewType(position) == 0) {//上下班
                boolean isNormal = datas.get(position).is_normal;
                String type = "";
                String label = "";
                boolean isShowTop = false;
                boolean isShowBottom = false;
                if (position == 0) {
                    type = "上";
                    label = "上班打卡";
                    isShowTop = false;
                    isShowBottom = true;
                } else if (position == datas.size()-1) {
                    type = "下";
                    label = "下班打卡";
                    isShowTop = true;
                    isShowBottom = false;
                }
                setData(holder, isShowTop, isShowBottom, type, label, time, location, isNormal);
                if (datas.size() == 1) {
                    Utils.isShowLine(holder.line_bottom, false);
                }
            } else if (getItemViewType(position) == 1) {//普通打卡
                setText(holder.tv_up_card_time, time);
                setText(holder.tv_up_card_location, location);
            }

            return convertView;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void setData(ViewHolder holder, boolean isShowLineTop, boolean isShowLineBottom,
                         String type, String label, String time, String location, boolean isNormal) {
        Utils.isShowLine(holder.line_top, isShowLineTop);
        Utils.isShowLine(holder.line_bottom, isShowLineBottom);
        setText(holder.tv_up_card_type, type);
        setText(holder.tv_up_card_label, label);
        setText(holder.tv_up_card_time, time);
        setText(holder.tv_up_card_location, location);
        holder.tv_up_card_label.setTextColor(mContext.getResources().getColor(R.color.up_card_212121));
        holder.tv_up_card_time.setTextColor(mContext.getResources().getColor(R.color.up_card_212121));
        Utils.setStatus(holder.tv_up_card_status, isNormal, type);
    }

    private void setText(TextView tv, String value){
        if (TextUtils.isEmpty(value)) {
            value = "";
        }
        tv.setText(value);
    }

    static class ViewHolder{
        View line_top;
        View line_bottom;
        TextView tv_up_card_type;
        TextView tv_up_card_label;
        TextView tv_up_card_time;
        TextView tv_up_card_status;
        TextView tv_up_card_location;
        ImageView img_location;
    }
}
