package com.elead.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.elead.activity.RssDetailActivity;
import com.elead.entity.ItemEntity;
import com.elead.eplatform.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.Collections;
import java.util.List;

/**
 * Created by xieshibin on 2016/12/9.
 */

public class MyListAdapter extends BaseAdapter {

    List<ItemEntity> items;
    private LayoutInflater mInflater;
    private Context context;

    public MyListAdapter(Context context, List<ItemEntity> items) {
        this.items = items;
        this.context = context;
        mInflater = LayoutInflater.from(context);

        Collections.shuffle(items);

    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.rss_list_item, null);
            holder.title_tv = (TextView) convertView.findViewById(R.id.title_tv);
            holder.time_author_tv = (TextView) convertView.findViewById(R.id.time_author_tv);
            holder.imgeUrl = (ImageView) convertView.findViewById(R.id.info_iv);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.title_tv.setText(items.get(i).title);
        holder.time_author_tv.setText(items.get(i).time + "   " + items.get(i).author);
        if (TextUtils.isEmpty(items.get(i).imageLink)) {
            holder.imgeUrl.setVisibility(View.GONE);
        } else {
            holder.imgeUrl.setVisibility(View.VISIBLE);
            String imageUrl = items.get(i).imageLink;

            DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisc(true)
                    .showStubImage(R.drawable.default_image).showImageForEmptyUri(R.drawable.default_image)
                    .showImageOnFail(R.drawable.default_image).imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                    .bitmapConfig(Bitmap.Config.RGB_565).build();

            ImageLoader.getInstance().displayImage(imageUrl,holder.imgeUrl,options);

        }

        final String url = items.get(i).link;
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, RssDetailActivity.class);
                i.putExtra("urlDetail", url);
                context.startActivity(i);
            }
        });

        return convertView;
    }

    public class ViewHolder {
        TextView title_tv;
        TextView time_author_tv;
        ImageView imgeUrl;
    }


}
