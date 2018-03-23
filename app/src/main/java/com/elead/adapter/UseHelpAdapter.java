package com.elead.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.elead.eplatform.R;

import java.util.List;

/**
 * Created by Administrator on 2016/10/11 0011.
 */

public class UseHelpAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> list;

    public UseHelpAdapter(Context context, List<String> list) {
        this.mContext = context;
        this.list = list;
    }

    public void setDatasList(List<String> listMore) {
        this.list = listMore;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
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
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = View.inflate(mContext, R.layout.use_help_item, null);
        TextView help_text = (TextView) view.findViewById(R.id.help_text_id);
        help_text.setText(list.get(position));

        return view;
    }


}
