package com.elead.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.elead.card.entity.MoreEntry;
import com.elead.eplatform.R;

import java.util.List;

/**
 * Created by Administrator on 2016/10/28 0028.
 */

public class MoreAppCardAdapter extends BaseAdapter {

    private List<MoreEntry> list;
    private Context mContext;
    final int VIEW_TYPE = 2;
    final int TYPE_1 = 0;
    final int TYPE_2 = 1;
    private OnClickMoreAppListener onclickMoreAppListener;

    public MoreAppCardAdapter(Context context, List<MoreEntry> list) {
        this.mContext = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size() + 1;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public int getItemViewType(int position) {
        if (position == list.size()) {
            return TYPE_2;
        } else {
            return TYPE_1;
        }

    }

    @Override
    public int getViewTypeCount() {
        return VIEW_TYPE;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        ViewHolderM viewHolderM = null;
        int type = getItemViewType(position);
        if (convertView == null) {
            switch (type) {
                case TYPE_1:
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.card_grid_view_item, null);
                    viewHolder = new ViewHolder();
                    viewHolder.imgsrc = (ImageView) convertView.findViewById(R.id.imgsrc_id);
                    viewHolder.nameiv = (TextView) convertView.findViewById(R.id.name_text);
                    viewHolder.grid_more_app_ll = (LinearLayout) convertView.findViewById(R.id.grid_more_app_ll);
                    convertView.setTag(viewHolder);
                    break;
                case TYPE_2:
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.card_more_item_item, null);
                    viewHolderM = new ViewHolderM();
                    viewHolderM.more_ll = (LinearLayout) convertView.findViewById(R.id.more_ll_id);
                    convertView.setTag(viewHolderM);
                    break;
            }


        } else {
            switch (type) {

                case TYPE_1:
                    viewHolder = (ViewHolder) convertView.getTag();
                    break;

                case TYPE_2:
                    viewHolderM = (ViewHolderM) convertView.getTag();
                    break;
            }
        }

        switch (type) {
            case TYPE_1:
                viewHolder.imgsrc.setImageResource(list.get(position).getImgRrc());
                viewHolder.nameiv.setText(list.get(position).getName());
                viewHolder.grid_more_app_ll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (null!= onclickMoreAppListener) {
                            onclickMoreAppListener.clickMoreApp(position);
                        }
                    }
                });

                break;

            case TYPE_2:
                viewHolderM.more_ll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (null!= onclickMoreAppListener) {
                            onclickMoreAppListener.clickMorePuls();
                        }
                    }
                });
                break;
        }

        return convertView;
    }

    class ViewHolder {
        TextView nameiv;
        ImageView imgsrc;
        LinearLayout grid_more_app_ll;
    }

    class ViewHolderM {
        LinearLayout more_ll;
    }

    public void setClickMoreAppListener(OnClickMoreAppListener onclickMoreAppListener) {
        this.onclickMoreAppListener = onclickMoreAppListener;
    }

    public interface OnClickMoreAppListener {
        void clickMoreApp(int position);

        void clickMorePuls();

    }


}
