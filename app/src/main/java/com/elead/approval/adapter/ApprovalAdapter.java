package com.elead.approval.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.elead.approval.entity.ApprovalEntry;
import com.elead.eplatform.R;
import com.elead.views.CircularImageView;

import java.util.List;

/**
 * Created by Administrator on 2017/2/9 0009.
 */

public class ApprovalAdapter extends BaseAdapter {


    private List<ApprovalEntry> list;
    private Context mContext;

    public ApprovalAdapter(Context context, List<ApprovalEntry> list) {
        this.mContext = context;
        this.list = list;
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
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder vh;
        if (convertView == null) {
            vh = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.approval_process_item, null);
            vh.vh_tv_name = (TextView) convertView.findViewById(R.id.use_name_id);
            vh.vh_tv_status = (TextView) convertView.findViewById(R.id.status_id);
            vh.vh_tv_time = (TextView) convertView.findViewById(R.id.time_id);
            vh.my_pohto = (CircularImageView) convertView.findViewById(R.id.my_photo_iv);
            vh.view_line = convertView.findViewById(R.id.lineid);
            vh.last_line = convertView.findViewById(R.id.last_line_id);
            vh.statusImge = (ImageView) convertView.findViewById(R.id.left_imageview);
            convertView.setTag(vh);

        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        vh.vh_tv_name.setText(list.get(position).getName());
        vh.vh_tv_status.setText(list.get(position).getApprovalStatus());
        vh.my_pohto.setBackgroundColor(list.get(position).getName());
        vh.my_pohto.setText(list.get(position).getName());

        if (list.get(position).getApprovalStatus().equals("已同意")) {
            vh.vh_tv_status.setTextColor(Color.parseColor("#2ec7c9"));
            vh.statusImge.setImageResource(R.drawable.agree_p);
        } else if (list.get(position).getApprovalStatus().equals("审批中")) {
            vh.vh_tv_status.setTextColor(Color.parseColor("#feb689"));
            vh.statusImge.setImageResource(R.drawable.shenpi_p);
        }
//        if (position == 0) {
//            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(Util.dpToPx(mContext.getResources(),1),Util.dpToPx(mContext.getResources(),23));
//            vh.last_line.setLayoutParams(params);
//        }
        if (position == list.size() - 2) {
            vh.view_line.setBackgroundColor(Color.parseColor("#DEDEDE"));
        } else {
            vh.view_line.setBackgroundColor(Color.parseColor("#4BCACB"));
        }

        if (position == list.size() - 1) {
            vh.last_line.setBackgroundColor(Color.parseColor("#DEDEDE"));
            vh.view_line.setVisibility(View.GONE);
            vh.statusImge.setImageResource(R.drawable.wangcheng);
        } else {
            vh.last_line.setBackgroundColor(Color.parseColor("#4BCACB"));
            vh.view_line.setVisibility(View.VISIBLE);
        }
        return convertView;
    }

    class ViewHolder {
        TextView vh_tv_name;
        View last_line;
        TextView vh_tv_time;
        TextView vh_tv_status;
        CircularImageView my_pohto;
        View view_line;
        ImageView statusImge;
    }


}
