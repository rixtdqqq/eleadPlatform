package com.elead.im.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.elead.eplatform.R;
import com.elead.im.activity.GroupDetailsActivity;
import com.elead.im.widget.EaseConstant;
import com.elead.utils.Pinyin4jUtil;
import com.elead.views.CircularImageView;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.exceptions.HyphenateException;

import java.util.ArrayList;
import java.util.List;

import static com.elead.application.MyApplication.showingActivity;
import static com.elead.eplatform.R.id.avatar;

/**
 * Created by Administrator on 2017/1/22 0022.
 */

public class SearchGroupAdapter extends BaseAdapter {
    private List<EMGroup> groups;
    private LayoutInflater inflater;
    private List<EMGroup> mFilteredArrayList = new ArrayList<>();
    private NameFilter mNameFilter;

    public SearchGroupAdapter(Context context, List<EMGroup> groups) {
        this.inflater = LayoutInflater.from(context);
        this.groups = groups;
    }

    private class ViewHolder {
        TextView name;
        TextView number_count_group_iv;
        CircularImageView avatar;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.em_row_group, parent, false);
            viewHolder.avatar = (CircularImageView) convertView.findViewById(avatar);
            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
            viewHolder.number_count_group_iv = (TextView) convertView.findViewById(R.id.number_count_group_iv);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final EMGroup emGroup = mFilteredArrayList.get(position);

        viewHolder.name.setVisibility(View.VISIBLE);
        viewHolder.name.setText(emGroup.getGroupName());
        viewHolder.avatar.setText(emGroup.getMembers());
        viewHolder.avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showingActivity.startActivity(new Intent(showingActivity, GroupDetailsActivity.class).putExtra(EaseConstant.GROUP_ID, emGroup.getGroupId()));
            }
        });

        viewHolder.number_count_group_iv.setText(emGroup.getAffiliationsCount() + "人");

        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 121312:
                        String s = (String) msg.obj;
                        viewHolder.number_count_group_iv.setText(s);
                        break;
                }
            }
        };
        synchronized (emGroup.getGroupName()) {
            new Thread(new Runnable() {
                public void run() {
                    try {
                        EMGroup groupFromServer = EMClient.getInstance().groupManager().getGroupFromServer(emGroup.getGroupId());
                        Message message = handler.obtainMessage();
                        message.obj = groupFromServer.getAffiliationsCount() + "人";
                        message.what = 121312;
                        handler.sendMessage(message);

                    } catch (HyphenateException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }


        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public Filter getFilter() {
        if (mNameFilter == null) {
            mNameFilter = new NameFilter();
        }
        return mNameFilter;
    }

    @Override
    public int getCount() {
        if (mFilteredArrayList == null) {
            return 0;
        } else {
            return (mFilteredArrayList.size());
        }

    }

    @Override
    public EMGroup getItem(int position) {
        if (mFilteredArrayList == null) {
            return null;
        } else {
            return mFilteredArrayList.get(position);
        }
    }

    //过滤数据
    class NameFilter extends Filter {
        //执行筛选
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<EMGroup> emGroups = new ArrayList<>();
            for (EMGroup emGroup : groups) {
                String groupName = emGroup.getGroupName();
                if (Pinyin4jUtil.getPinYin(groupName).contains(charSequence) || groupName.contains(charSequence) || Pinyin4jUtil.getPinYinHeadChar(groupName).contains(charSequence)) {
                    emGroups.add(emGroup);
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = emGroups;
            return filterResults;
        }

        //筛选结果
        @Override
        protected void publishResults(CharSequence arg0, FilterResults results) {
            List<EMGroup> grouppickuser = (List<EMGroup>) results.values;
            mFilteredArrayList.clear();
            if (grouppickuser.size() > 0) {
                mFilteredArrayList.addAll(grouppickuser);
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }


        }
    }


}
