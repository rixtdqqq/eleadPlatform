package com.elead.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.elead.eplatform.R;
import com.elead.utils.ImageLoader;

import java.util.List;

/**
 * Created by Administrator on 2016/8/30 0030.
 */
public class SelectPhotoAdapter extends BaseAdapter {
    final int VIEW_TYPE = 2;
    final int TYPE_1 = 0;
    final int TYPE_2 = 1;

    private final List<String> imgsList;
    private Context mcontext;
    private String path;

    public SelectPhotoAdapter(Context context, List<String> imgsList, String path) {
        this.mcontext = context;
        this.imgsList = imgsList;
        this.path = path;
    }

    @Override
    public int getCount() {
        return imgsList.size() + 1;
    }

    @Override
    public Object getItem(int position) {
        return imgsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_1;
        } else {
            return TYPE_2;
        }
    }


    @Override
    public int getViewTypeCount() {

        return VIEW_TYPE;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolderT viewHolderT = null;
        ViewHolder viewHolder = null;
        int type = getItemViewType(position);

        if (convertView == null) {
            switch (type) {
                case TYPE_1:
                    convertView = LayoutInflater.from(mcontext).inflate(R.layout.grid_take_photo, null);
                    viewHolderT = new ViewHolderT();
                    viewHolderT.take_photo = (ImageView) convertView.findViewById(R.id.take_photo_id);
                    viewHolderT.take_photo_liner = (LinearLayout) convertView.findViewById(R.id.liner_take_photo_id);

                    convertView.setTag(viewHolderT);
                    break;

                case TYPE_2:
                    convertView = LayoutInflater.from(mcontext).inflate(R.layout.grid_show_photo, null);
                    viewHolder = new ViewHolder();
                    viewHolder.show_photo = (ImageView) convertView.findViewById(R.id.show_photo_id);
                    convertView.setTag(viewHolder);
                    break;

            }

        } else {
            switch (type) {
                case TYPE_1:
                    viewHolderT = (ViewHolderT) convertView.getTag();
                    break;

                case TYPE_2:
                    viewHolder = (ViewHolder) convertView.getTag();
                    break;
            }
        }

        switch (type) {
            case TYPE_1:
                viewHolderT.take_photo_liner.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                      if(onPhotoSelectedListener!=null){
                          onPhotoSelectedListener.takePhoto();
                      }
                    }
                });
                break;

            case TYPE_2:
                viewHolder.show_photo.setBackgroundResource(R.drawable.pictures_no);
                ImageLoader.getInstance(3, ImageLoader.Type.LIFO).loadImage(path + "/" + imgsList.get(position - 1), viewHolder.show_photo);

                viewHolder.show_photo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    String imgName=imgsList.get(position-1);
                      if(onPhotoSelectedListener!=null){
                          onPhotoSelectedListener.photoClick(imgName);
                      }

                    }
                });


                break;
        }
        return convertView;
    }


    class ViewHolderT {
        ImageView take_photo;
        LinearLayout take_photo_liner;
    }

    class ViewHolder {
        ImageView show_photo;
    }

    public OnPhotoSelectedListener onPhotoSelectedListener;

    public void setOnPhotoSelectedListener(OnPhotoSelectedListener onPhotoSelectedListener){
        this.onPhotoSelectedListener=onPhotoSelectedListener;
    }
    public  interface OnPhotoSelectedListener{
        public void photoClick(String ImgName);
        public void takePhoto();
    }




}
