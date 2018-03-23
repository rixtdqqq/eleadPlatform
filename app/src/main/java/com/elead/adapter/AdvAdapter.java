package com.elead.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.android.volley.toolbox.Volley;
import com.elead.entity.BannerEntity;
import com.elead.utils.BitmapCache;
import com.elead.utils.CacheTools;
import com.elead.utils.HRAppAutoFitScreen;
import com.elead.utils.Util;
import com.elead.views.FixedSpeedScrollerViewPager;

import java.util.ArrayList;
import java.util.List;

import static com.elead.utils.Util.setViewPagerSpeed;

/*import com.nostra13.universalimageloader.core.DisplayImageOptions;
 import com.nostra13.universalimageloader.core.ImageLoader;
 import com.nostra13.universalimageloader.core.assist.FailReason;
 import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;*/

/**
 * bannerAdapter
 */
public class AdvAdapter extends PagerAdapter {
    private List<View> views;
    private ViewGroup.LayoutParams params;
    private Context mContext;
    List<BannerEntity> datas;
    private RequestQueue mQueue;
    private int listSize = 0;
    /**
     * scroller类，用于设置ViewPager滚动速度
     */
    FixedSpeedScrollerViewPager scroller;

    public AdvAdapter(ViewPager pager, Context context, List<BannerEntity> data) {
        datas = data;
        listSize = data.size();
        scroller = new FixedSpeedScrollerViewPager(context);
        mQueue = Volley.newRequestQueue(context);
        mImageLoader = new ImageLoader(mQueue, new BitmapCache(mContext));

        /**
         * 设置ViewPager滑动速度
         */
        setViewPagerSpeed(pager, scroller);
        params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        views = new ArrayList<View>();
        this.mContext = context;
        for (int i = 0, j = data.size(); i < j; i++) {
            bindImageViewData(data.get(i), context, i);
        }
    }

    /**
     * 设置bannaer图片集合
     */
    public void setBannerList() {

    }

    /**
     * 设置viewpager滑动速度
     *
     * @param mDuration
     */
    public void setScrollerSpeed(int mDuration) {
        scroller.setmDuration(mDuration);
    }

    ImageView bg;
    private ImageLoader mImageLoader;

    public ImageView getFirstBg() {
        return bg;
    }

    /**
     * 绑定下载图片数据
     *
     * @param bannerEntity banner实体类
     */
    public void bindImageViewData(final BannerEntity bannerEntity,
                                  final Context context, final int pos) {
        String imageUrl = bannerEntity.getImage();
        boolean loadCacheSuccess = false;
        RelativeLayout layout = new RelativeLayout(context);
        RelativeLayout.LayoutParams parms = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        layout.setLayoutParams(parms);
        ImageView imageView = new ImageView(context);

        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        if (null != params)
            imageView.setLayoutParams(params);
        layout.addView(imageView, params);
        String descrp = Util.getCurrentModuleLanguage() ? bannerEntity
                .getDesrpcn() : bannerEntity.getDesrpen();
        if (!TextUtils.isEmpty(descrp)
                && !TextUtils.isEmpty(bannerEntity.getFontcolor())) {
            TextView mTextView = new TextView(context);
            RelativeLayout.LayoutParams parmsmTextView = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            mTextView.setText(descrp);
            if (!TextUtils.isEmpty(bannerEntity.getFontcolor())) {
                try {
                    mTextView.setTextColor(Color.parseColor(bannerEntity
                            .getFontcolor()));
                } catch (Exception e) {
                    mTextView.setTextColor(Color.parseColor("#666666"));
                }
            } else {
                mTextView.setTextColor(Color.parseColor("#666666"));
            }
            parmsmTextView.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM,
                    RelativeLayout.TRUE);
            parmsmTextView.addRule(RelativeLayout.CENTER_HORIZONTAL,
                    RelativeLayout.TRUE);
            parmsmTextView.bottomMargin = HRAppAutoFitScreen.autofitY(50);
            layout.addView(mTextView, parmsmTextView);
        }
        loadCacheSuccess = CacheTools.isFileExist(imageUrl, mContext);

        // 如果是第一张图片，且文件存在，那么优先读取这一张图片
        if (loadCacheSuccess) {
            imageView.setImageBitmap(CacheTools.getBitmap2(imageUrl, mContext));
        } else {
            ImageListener listener = ImageLoader.getImageListener(imageView,
                    android.R.drawable.ic_menu_rotate, android.R.drawable.ic_delete);
            mImageLoader.get(imageUrl, listener);
        }

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Object tag = v.getTag();
                if (null != tag && tag instanceof BannerEntity) {
                    /*
					 * BannerEntity entity = (BannerEntity) tag;
					 * if(TextUtils.isEmpty(entity.getDetail())){ return; }
					 */
                    // MsgUtils.jumpingToMsgDetailsActivity(context,"","",entity.getDesrpcn(),entity.getDesrpen(),entity.getDetail());
                    // Intent intent = new Intent();
                    // intent.setClassName(mContext,
                    // GlobalJumpSerice.MSG_MSGDETAILSACTIVITY);
                    // intent.putExtra("entryid", entity.getDetail());//detail
                    // intent.putExtra("msgcategory",
                    // "publicnumber");//publicnumber
                    // intent.putExtra("entryname", entity.getDesrpcn());//desrp
                    // intent.putExtra("entrynameen",
                    // entity.getDesrpen());//desrp
                    // intent.putExtra("position","");//""
                    // mContext.startActivity(intent);
                }
            }
        });
        layout.setTag(bannerEntity);
        views.add(layout);
    }

    /**
     * getCount() 方法的返回值：这个值直接关系到ViewPager的“边界”，
     * 因此当我们把它设置为Integer.MAX_VALUE之后，用户基本就看不到这个边界了
     *
     * @return
     */
    @Override
    public int getCount() {
        if (listSize == 1) {
            return 1;
        }
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    /**
     * 由于我们在instantiateItem()方法中已经处理了remove的逻辑，因此这里并不需要处理。
     * 实际上，实验表明这里如果加上了remove的调用，则会出现ViewPager的内容为空的情况
     *
     * @param container
     * @param position
     * @param object
     */
    @Override
    public void destroyItem(View container, int position, Object object) {
    }

    /**
     * instantiateItem() 方法position的处理： 由于我们设置了count为 Integer.MAX_VALUE，
     * 因此这个position的取值范围很大很大，实际要显示的内容只有几项， 所以这里有求模操作。但是，简单的求模会出现问题：
     * 考虑用户向左滑的情形，则position可能会出现负值。 所以需要对负值再处理一次，使其落在正确的区间内。
     *
     * @param container
     * @param position
     * @return
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        // 对ViewPager页号求模取出View列表中要显示的项
        position %= views.size();
        if (position < 0) {
            position = views.size() + position;
        }
        View view = views.get(position);
        // 如果View已经在之前添加到了一个父组件，则必须先remove，否则会抛出IllegalStateException。
        ViewParent vp = view.getParent();
        if (vp != null) {


            ViewGroup parent = (ViewGroup) vp;
            parent.removeView(view);
        }
        container.addView(view);
        // add listeners here if necessary
        return view;
    }

    @Override
    public void restoreState(Parcelable arg0, ClassLoader arg1) {

    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    @Override
    public void startUpdate(View arg0) {

    }

    /**
     * 网络下载图片displayImageOptions设置
     *
     * @param loadCacheSuccess
     *            不需要加载默认图片？
     * @return
     */
    //
    // public DisplayImageOptions setNetWorkOptions(boolean loadCacheSuccess) {
    // DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder();
    // builder.considerExifParams(true);
    // builder.cacheInMemory(true);
    // builder.cacheOnDisk(true);
    // builder.bitmapConfig(Bitmap.Config.RGB_565);
    // if (!loadCacheSuccess) {
    // builder.showImageOnLoading(R.drawable.banner_bg);
    // builder.showImageOnFail(R.drawable.banner_bg);
    // builder.showImageForEmptyUri(R.drawable.banner_bg);
    // }
    // return builder.build();
    // }

}
