/*
 * Copyright (C) 2014 pengjianbo(pengjianbosoft@gmail.com), Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.elead.approval.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.elead.eplatform.R;
import com.elead.utils.BitmapUtil;
import com.elead.utils.DensityUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.finalteam.mygallery.model.PhotoInfo;
import cn.finalteam.mygallery.toolsfinal.DeviceUtils;

/**
 * Desction:
 * Author:pengjianbo
 * Date:15/12/1 下午8:42
 */
public class ChoosePhotoListAdapter extends BaseAdapter {
    private final TextView pitureIv;
    private List<PhotoInfo> mList;
    private LayoutInflater mInflater;
    private int mScreenWidth;
    private Activity activity;
    private Map<String, Bitmap> bitmaps = new HashMap();

    public ChoosePhotoListAdapter(Activity activity, List<PhotoInfo> list,TextView pitureIv) {
        this.mList = list;
        this.activity = activity;
        this.mInflater = LayoutInflater.from(activity);
        this.mScreenWidth = DeviceUtils.getScreenPix(activity).widthPixels;
        this.pitureIv=pitureIv;
        Log.i("TAG,,","yyyy=="+mList.size()+"mm1=="+pitureIv);
    }

    @Override
    public int getCount() {
        return mList.size();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnFail(R.drawable.photo_default_bg)
                .showImageForEmptyUri(R.drawable.photo_default_bg)
                .showImageOnLoading(R.drawable.photo_default_bg).build();
        FrameLayout relativeLayout = (FrameLayout) mInflater.inflate(R.layout.approval_choose_photo_list_item, null);
        ImageView ivPhoto = (ImageView) relativeLayout.findViewById(R.id.shoose_img);
        ImageButton delete = (ImageButton) relativeLayout.findViewById(R.id.delete_img);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mList.remove(position);
                pitureIv.setText(activity.getResources().getString(R.string.photo)+"("+mList.size()+")");
                //Log.i("TAG,,","yyyy=="+mList.size()+"mm=="+pitureIv);
                notifyDataSetChanged();
            }
        });
        setHeight(relativeLayout);
        PhotoInfo photoInfo = mList.get(position);
        String photoPath = photoInfo.getPhotoPath();
        if (!bitmaps.containsKey(photoPath)) {
            Bitmap imageThumbnail = BitmapUtil.createImageThumbnail(photoPath);
            bitmaps.put(photoPath, imageThumbnail);
        }

        ivPhoto.setImageBitmap(bitmaps.get(photoPath));
//        ImageLoader.getInstance().displayImage("file:/" + photoPath, ivPhoto, options);
        return relativeLayout;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    private void setHeight(final View convertView) {
        int height = mScreenWidth / 5 - DensityUtil.dip2px(activity,10);
        convertView.setLayoutParams(new FrameLayout.LayoutParams(height, height));
    }
}
