package com.elead.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.elead.entity.GroupEntry;
import com.elead.eplatform.R;
import com.elead.views.CircularImageView;

import java.util.List;

/**
 * Created by Administrator on 2016/12/26 0026.
 */

public class GroudExpandListAdapter extends BaseExpandableListAdapter {


    private List<GroupEntry> groupArray;
    private List<List<String>> childArray;
    private Context mContext;
    private Context context;
    private LayoutInflater inflater;

    public GroudExpandListAdapter(Context mContext, List<GroupEntry> groupArray, List<List<String>> childArray) {
        this.mContext = mContext;
        this.groupArray = groupArray;
        this.childArray = childArray;
        this.context = context;
        inflater = LayoutInflater.from(mContext);

        Log.i("TAG", "getChildView======2" + childArray);
    }


    // 返回父列表个数
    @Override
    public int getGroupCount() {
        return groupArray.size();
    }

    // 返回子列表个数
    @Override
    public int getChildrenCount(int groupPosition) {
        return childArray.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {

        return groupArray.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childArray.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {

        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        GroupHolder groupHolder = null;
        if (convertView == null) {
            groupHolder = new GroupHolder();
            convertView = inflater.inflate(R.layout.contact_contact_item, null);
            groupHolder.textView = (TextView) convertView
                    .findViewById(R.id.name);
            groupHolder.imageView = (ImageView) convertView
                    .findViewById(R.id.right_id);
            groupHolder.avater = (CircularImageView) convertView
                    .findViewById(R.id.avatar);
            groupHolder.textView.setTextSize(15);
            convertView.setTag(groupHolder);
        } else {
            groupHolder = (GroupHolder) convertView.getTag();
        }

        groupHolder.textView.setText(((GroupEntry) getGroup(groupPosition)).getGroudTitle().toString());
        groupHolder.avater.setImageDrawable(((GroupEntry) getGroup(groupPosition)).getDrawable());

        if (isExpanded)// ture is Expanded or false is not isExpanded
            groupHolder.imageView.setImageResource(R.drawable.right_arrow_down);
        else
            groupHolder.imageView.setImageResource(R.drawable.right_arrow);
//        ContactItemView contactItemView = new ContactItemView(mContext);
//        contactItemView.setTitle(groupArray.get(groupPosition));
//        if (isExpanded) {
//            contactItemView.setArrow(mContext.getResources().getDrawable(R.mipmap.right_arrow_down));
//        } else {
//            contactItemView.setArrow(mContext.getResources().getDrawable(R.mipmap.right_arrow));
//        }


        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
//        if (convertView == null) {
//            convertView = inflater.inflate(R.layout.item, null);
//        }
//        TextView textView = (TextView) convertView.findViewById(R.id.item);
//        textView.setTextSize(13);
//        textView.setText(childArray.get(groupPosition).get(childPosition).toString());
        //View view = convertView;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.expanlist_groud_item, null);
        }
        TextView tv = (TextView) convertView.findViewById(R.id.groud_title_iv_item);
        tv.setText(childArray.get(groupPosition).get(childPosition).toString());

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


    class GroupHolder {
        TextView textView;
        ImageView imageView;
        CircularImageView avater;
    }

}
