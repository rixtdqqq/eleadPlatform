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

package cn.finalteam.mygallery.adapter;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageButton;

import java.util.List;

import cn.finalteam.mygallery.GalleryFinal;
import cn.finalteam.mygallery.R;
import cn.finalteam.mygallery.model.PhotoInfo;
import cn.finalteam.mygallery.widget.GFImageView;
import cn.finalteam.mygallery.widget.TakePhotoView;

/**
 * Desction:
 * Author:pengjianbo
 * Date:15/10/10 下午4:59
 */
public class PhotoListAdapter extends ViewHolderAdapter<PhotoListAdapter.PhotoViewHolder, PhotoInfo> {

    private onChangeLinstener onChangeLinstener;
    private int mScreenWidth;
    private int mRowWidth;

    private Activity mActivity;

    public PhotoListAdapter(Activity activity, List<PhotoInfo> list, int screenWidth, onChangeLinstener onChangeLinstener) {
        super(activity, list);
        this.mScreenWidth = screenWidth;
        this.mRowWidth = mScreenWidth / 3;
        this.mActivity = activity;
        this.onChangeLinstener = onChangeLinstener;
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? 0 : 1;
    }

    @Override
    public int getCount() {
        return getDatas().size()+ 1;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        View view = inflate(R.layout.my_gf_adapter_photo_list_item, parent);
        View camara = new TakePhotoView(getContext());
        setHeight(view);
        setHeight(camara);
        switch (getItemViewType(position)) {
            case 0:
                return new PhotoViewHolder(camara);
            case 1:
                return new PhotoViewHolder(view);
            default:
                return null;
        }
    }


    @Override
    public void onBindViewHolder(final PhotoViewHolder holder, final int position) {
        switch (getItemViewType(position)) {
            case 0:
                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onChangeLinstener.onTakePhoto();
                    }
                });
                break;
            case 1:
                final int realPosition = position - 1;
                final PhotoInfo photoInfo = getDatas().get(realPosition);
                Log.d("photoInfo: ", photoInfo.toString());
                holder.mIvBtn.setVisibility(View.VISIBLE);
                holder.mIvThumb.setSelectorEnable(true);
                String path = "";
                if (photoInfo != null) {
                    path = photoInfo.getPhotoPath();
                }
                if (TextUtils.isEmpty(path)) return;

                Drawable defaultDrawable = mActivity.getResources().getDrawable(R.drawable.photo_default_bg);
                GalleryFinal.getCoreConfig().getImageLoader().displayImage(mActivity, path, holder.mIvThumb, defaultDrawable, mRowWidth, mRowWidth);
                holder.mView.setAnimation(null);

                holder.mIvBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean b = onChangeLinstener.onCheck(realPosition);
                        if (b) {
                            photoInfo.setCheck(photoInfo.isCheck());
                            holder.mIvThumb.setChecked(photoInfo.isCheck());
                            if (photoInfo.isCheck()) {
                                holder.mIvBtn.setImageResource(R.drawable.check_pressed);
                            } else {
                                holder.mIvBtn.setImageResource(R.drawable.check_normal);
                            }
                        } else {
                            holder.mIvBtn.setImageResource(R.drawable.check_normal);
                        }
                    }
                });
//        if (GalleryFinal.getCoreConfig().getAnimation() > 0) {
//            holder.mView.setAnimation(AnimationUtils.loadAnimation(mActivity, GalleryFinal.getCoreConfig().getAnimation()));
//        }
                if (photoInfo.isCheck()) {
                    holder.mIvBtn.setImageResource(R.drawable.check_pressed);
                    holder.mIvThumb.setChecked(true);
                } else {
                    holder.mIvBtn.setImageResource(R.drawable.check_normal);
                    holder.mIvThumb.setChecked(false);
                }
                break;
        }


    }


    private void setHeight(final View convertView) {
        int height = mScreenWidth / 3 - 8;
        convertView.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height));
    }

    public static class PhotoViewHolder extends ViewHolderAdapter.ViewHolder {

        public GFImageView mIvThumb;
        public ImageButton mIvBtn;
        public View mView;

        public PhotoViewHolder(View view) {
            super(view);
            mView = view;
            mIvThumb = (GFImageView) view.findViewById(R.id.iv_thumb);
            mIvBtn = (ImageButton) view.findViewById(R.id.img_btn);
        }
    }


    public interface onChangeLinstener {
        boolean onCheck(int position);

        void onTakePhoto();
    }
}
