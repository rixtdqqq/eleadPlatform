package com.elead.im.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.elead.eplatform.R;
import com.elead.im.entry.EMConversion;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by linmin on 2016/12/27.
 */

public class MessageListAdapter extends BaseAdapter {

    private List<EMConversion> datas = new ArrayList<EMConversion>();
    private LayoutInflater inflater;

    public MessageListAdapter(Context context, List<EMConversion> data){
        this.datas = data;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return datas.size();
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHodler holder = null;
        if(convertView == null){
            holder = new ViewHodler();
            convertView = inflater.inflate(R.layout.im_mainchat_item, null);
            holder.msgTitle = (TextView) convertView.findViewById(R.id.im_msg_title);
            holder.msgContent = (TextView) convertView.findViewById(R.id.im_msg_content);
            holder.msgTime = (TextView) convertView.findViewById(R.id.im_msg_time);
            convertView.setTag(holder);
        }
        holder = (ViewHodler) convertView.getTag();
        Log.i("ChatActivity", "数据：" + datas.get(0).toString());
        if(!TextUtils.isEmpty(datas.get(position).getMessageTxt()))
        {//标题
            holder.msgTitle.setText(datas.get(position).getMessageTxt());
        }
        if(!TextUtils.isEmpty(datas.get(position).getMessageContent()))
        {//内容
            holder.msgContent.setText(datas.get(position).getMessageContent());
        }
        if(!TextUtils.isEmpty(datas.get(position).getMessagetime()))
        {//时间
            holder.msgTime.setText(datas.get(position).getMessagetime());
        }

        return convertView;
    }


    class ViewHodler{

        private TextView msgTitle;
        private TextView msgContent;
        private TextView msgTime;

    }
}
