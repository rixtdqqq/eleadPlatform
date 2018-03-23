package com.elead.im.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.TextView;

import com.elead.eplatform.R;
import com.elead.im.entry.GroupPickUser;
import com.elead.im.util.EaseUserUtils;
import com.elead.utils.Pinyin4jUtil;
import com.elead.views.CircularImageView;

import java.util.ArrayList;
import java.util.List;

import static com.elead.application.MyApplication.mContext;

/**
 * Created by Administrator on 2017/1/22 0022.
 */

public class SearchUserAdapter extends BaseAdapter {
    private final TextView sure;
    private List<GroupPickUser> users;
    private LayoutInflater inflater;
    private List<GroupPickUser> mFilteredArrayList = new ArrayList<>();

    private NameFilter mNameFilter;
    private BaseAdapter horizontallistviewAdapter;
    private List<GroupPickUser> existMembers;


    public SearchUserAdapter(Activity mContext, int i, List<GroupPickUser>
            users, BaseAdapter horizontallistviewAdapter, List<GroupPickUser> existMembers, TextView sure) {
        this.inflater = LayoutInflater.from(mContext);
        this.users = users;
        this.horizontallistviewAdapter = horizontallistviewAdapter;
        this.existMembers = existMembers;
        this.sure = sure;

    }

    private class ViewHolder {
        TextView name;
        CircularImageView avatar;
        public CheckBox checkbox;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.em_row_contact_with_checkbox, parent, false);
            viewHolder.avatar = (CircularImageView) convertView.findViewById(R.id.avatar);
            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
            viewHolder.checkbox = (CheckBox) convertView.findViewById(R.id.checkbox);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final GroupPickUser item = mFilteredArrayList.get(position);

        final String username = item.getUsername();

        EaseUserUtils.setUserAvatar(mContext, username, viewHolder.avatar);
        EaseUserUtils.setUserNick(username, viewHolder.name);

        viewHolder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!item.isEnable) return;
                if (existMembers.contains(item)) {
                    item.isChecked = false;
                    viewHolder.checkbox.setChecked(false);
                    existMembers.remove(item);
                } else {
                    item.isChecked = true;
                    existMembers.add(item);
                }
                horizontallistviewAdapter.notifyDataSetChanged();
                int count = 0;
                for (GroupPickUser b : existMembers) {
                    if (b.isChecked && b.isEnable) {
                        count++;
                    }
                }
                if (count > 0) {
                    sure.setText("确定(" + count + ")");
                } else
                    sure.setText("确定");

            }
        });
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.checkbox.performClick();
            }
        });
        if (!item.isEnable) {
            viewHolder.checkbox.setEnabled(false);
            item.isChecked = true;
        } else {
            viewHolder.checkbox.setChecked(item.isChecked);
            viewHolder.checkbox.setEnabled(true);
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
    public GroupPickUser getItem(int position) {
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
            List<GroupPickUser> searchUsers = new ArrayList<>();
            for (GroupPickUser user : users) {
                String nick = user.getNick();
                if (Pinyin4jUtil.getPinYin(nick).contains(charSequence) || nick.contains(charSequence) || Pinyin4jUtil.getPinYinHeadChar(nick).contains(charSequence)) {
                    searchUsers.add(user);
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = searchUsers;
            return filterResults;
        }

        //筛选结果
        @Override
        protected void publishResults(CharSequence arg0, FilterResults results) {
            List<GroupPickUser> grouppickuser = (List<GroupPickUser>) results.values;
            if (grouppickuser.size() > 0) {
                mFilteredArrayList.clear();
                mFilteredArrayList.addAll(grouppickuser);
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }

        }
    }


}
