package cn.finalteam.mygallery.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import cn.finalteam.mygallery.GalleryFinal;
import cn.finalteam.mygallery.R;
import cn.finalteam.mygallery.toolsfinal.DeviceUtils;

/**
 * Desction:
 * Author:pengjianbo
 * Date:2015/12/29 0029 15:53
 */
public class PhotoPreviewAdapter extends ViewHolderRecyclingPagerAdapter<PhotoPreviewAdapter.PreviewViewHolder, cn.finalteam.mygallery.model.PhotoInfo> {

    private Activity mActivity;
    private DisplayMetrics mDisplayMetrics;

    public PhotoPreviewAdapter(Activity activity, List<cn.finalteam.mygallery.model.PhotoInfo> list) {
        super(activity, list);
        this.mActivity = activity;
        this.mDisplayMetrics = DeviceUtils.getScreenPix(mActivity);
    }

    @Override
    public PreviewViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        View view = getLayoutInflater().inflate(R.layout.my_gf_adapter_preview_viewpgaer_item, null);
        return new PreviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PreviewViewHolder holder, int position) {
        cn.finalteam.mygallery.model.PhotoInfo photoInfo = getDatas().get(position);
        String path = "";
        if (photoInfo != null) {
            path = photoInfo.getPhotoPath();
        }
        if (TextUtils.isEmpty(path)) return;
        holder.mImageView.setImageResource(R.drawable.photo_default_bg);
//        Drawable defaultDrawable = mActivity.getResources().getDrawable(R.drawable.photo_default_bg);
        GalleryFinal.getCoreConfig().getImageLoader().displayImage(mActivity, path, holder.mImageView, new ColorDrawable(Color.TRANSPARENT), mDisplayMetrics.widthPixels / 2, mDisplayMetrics.heightPixels / 2);
    }

    static class PreviewViewHolder extends ViewHolderRecyclingPagerAdapter.ViewHolder {
        cn.finalteam.mygallery.widget.zoonview.PhotoView mImageView;

        public PreviewViewHolder(View view) {
            super(view);
            mImageView = (cn.finalteam.mygallery.widget.zoonview.PhotoView) view;
        }
    }
}
