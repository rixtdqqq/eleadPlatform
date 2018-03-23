package com.elead.card.cardviews;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.elead.adapter.AdvAdapter;
import com.elead.card.interfaces.ICard;
import com.elead.card.mode.BaseCardInfo;
import com.elead.card.views.BaseLayoutView;
import com.elead.entity.BannerEntity;
import com.elead.eplatform.R;
import com.elead.utils.Util;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * 轮播卡
 */
public class AdvertCard extends BaseLayoutView implements ICard {
    public static boolean isFragmentVisible;
    private int currentItem = 0;
    protected static final int UPDATE_IMAGE = 1;

    private static final int DOWNLOAD_FAIL_BANNAER = 2;
    // 轮播间隔时间
    protected static final long DELAY = 5000;
    /**
     * 广告容器
     */
    private ViewPager advPager;

    private List<BannerEntity> bannerEntityList = new ArrayList<BannerEntity>();

    private UIHandler mHandler;
    private LinearLayout group;

    public AdvertCard(Context context) {
        this(context, null);
    }

    public AdvertCard(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AdvertCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public int initLayout() {
        return R.layout.card_advert;
    }

    @Override
    public void initView() {
        group = getView(R.id.viewGroup);
        mHandler = new UIHandler(this);
        Util.createDownLoadDirectory();
        initBannaer();
    }

    @Override
    public void setDate(BaseCardInfo baseCardInfo) {
        Log.d("aaaaaaa", "avdsetDate");
    }

    private class UIHandler extends Handler {

        private WeakReference<AdvertCard> self;

        public UIHandler(AdvertCard self) {
            this.self = new WeakReference<AdvertCard>(self);
        }

        @Override
        public void handleMessage(Message msg) {
            AdvertCard workFragment = self.get();
            if (workFragment == null) {
                return;
            }
            switch (msg.what) {
                case DOWNLOAD_FAIL_BANNAER:
                /*
                 * if(DataHelpService.launcherW3Success){
				 * if(PublicUtil.isNotEmpty
				 * (TransactionBusinessService.bannerDownloadFail)){
				 * for(ImageView view :
				 * TransactionBusinessService.bannerDownloadFail) { Object tag =
				 * view.getTag(); if(null != tag && tag instanceof
				 * BannerEntity){ BannerEntity mBannerEntity = (BannerEntity)
				 * tag; if(null != mBannerEntity)
				 * ImageLoader.getInstance().displayImage
				 * (mBannerEntity.getImage(), view); } } } }
				 */
                    break;
                case UPDATE_IMAGE:
                    currentItem++;
                    advPager.setCurrentItem(currentItem);
                    break;
                default:
                    break;
            }
        }
    }

    // ---------------------------------------------HR OP bannaerbar
    // start-----------------------------
    private void initBannaer() {
        // 广告部分
        bannerEntityList = new ArrayList<BannerEntity>();
        for (int i = 0; i < 4; i++) {
            BannerEntity bannerEntity = new BannerEntity();
            bannerEntity
                    .setImage("http://img.my.csdn.net/uploads/201611/18/1479431763_3621.png");
            bannerEntity.setDesrpcn("dddddd" + i);
            bannerEntityList.add(bannerEntity);
        }
        initBannerViewPager(mContext, bannerEntityList);
    }

    /**
     * set banner 中的小点
     */
    private void setBannerDot(List<BannerEntity> data) {
        if (Util.isNotEmpty(data)) {
            if (data.size() > 1) {

                // 这里存放的是3张广告背景
                int doteHeight = (int) (density * 6);
                LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(
                        doteHeight, doteHeight);
                int pad = (int) (density * 2.5f);
                parms.setMargins(pad, 0, pad, 0);
                // 小图标
                for (int i = 0; i < data.size(); i++) {
                    ImageView imageView = new ImageView(mContext);
                    imageView.setLayoutParams(parms);
                    if (i == 0) {
                        imageView
                                .setBackgroundResource(R.drawable.page_indicator_focused);
                    } else {
                        imageView
                                .setBackgroundResource(R.drawable.page_indicator_default);
                    }
                    group.addView(imageView);
                }
            }
        }

    }

    private final class BannerPageChangeListener implements
            ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {
            switch (arg0) {
                // arg==1 时默认正在滑动
                case ViewPager.SCROLL_STATE_DRAGGING:
                    pauseClyclePlay();
                    // 请求暂停轮播
                    advAdapter.setScrollerSpeed(1000);
                    // mHandler.removeMessages(UPDATE_IMAGE);
                    break;
                // arg0==0 时默认什么都没做
                case ViewPager.SCROLL_STATE_IDLE:
                    // 请求轮播
                    resumeClyclePlay();
                    break;
                default:
                    break;
            }

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        /**
         * 选中的对应的banner 下方显示的对应提示点点的颜色设置。
         */
        @Override
        public void onPageSelected(int position) {
            currentItem = position;
            int count = group.getChildCount();
            int p = position % count;
            for (int i = 0; i < count; i++) {
                group.getChildAt(i).setBackgroundResource(
                        R.drawable.page_indicator_default);
            }
            group.getChildAt(p).setBackgroundResource(
                    R.drawable.page_indicator_focused);

        }
    }

    /**
     * 恢复轮播
     */
    public void resumeClyclePlay() {
        if (null != mHandler && null != advAdapter) {
            // 请求轮播
            mHandler.removeMessages(UPDATE_IMAGE);
            advAdapter.setScrollerSpeed(5 * 1000);
            mHandler.sendEmptyMessageDelayed(UPDATE_IMAGE, DELAY);
        }
    }

    private AdvAdapter advAdapter;

    /**
     * 暂停轮播
     */
    public void pauseClyclePlay() {
        if (null != mHandler && mHandler.hasMessages(UPDATE_IMAGE)) {
            mHandler.removeMessages(UPDATE_IMAGE);
        }
    }

    /**
     * @param ctx
     * @param data 服务器传过来的 BannerEntity集合
     */
    public void initBannerViewPager(Context ctx, List<BannerEntity> data) {
        setBannerDot(data);
        advPager = (ViewPager) mView.findViewById(R.id.adv_pager);
        // TransactionBusinessService.bannerDownloadFail.clear();

        // data.size < 3时，滑动会出现白屏，故：
        List<BannerEntity> dataList = data;
        if (data.size() == 2) {
            dataList.addAll(data);
        }
        // else if(data.size()==1){
        // dataList.addAll(data);
        // dataList.addAll(data);
        // }
        advAdapter = new AdvAdapter(advPager, ctx, dataList);
        advPager.setAdapter(advAdapter);
        if (data.size() > 1) {
            advPager.setOnPageChangeListener(new BannerPageChangeListener());
        }
        advPager.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (mHandler.hasMessages(UPDATE_IMAGE) && isFragmentVisible) {
                            advPager.setCurrentItem(currentItem);
                            mHandler.removeMessages(UPDATE_IMAGE);
                        }
                    default:
                        break;
                }
                return false;
            }
        });
        // if(data.size() >1) {
        // advAdapter.setScrollerSpeed(5 * 100);
        // mHandler.sendEmptyMessageDelayed(UPDATE_IMAGE, 5 * 1000);
        // }
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        if (isFragmentVisible) {
            resumeClyclePlay();
        } else {
            pauseClyclePlay();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getDefaultSize(0, widthMeasureSpec),
                getDefaultSize(0, heightMeasureSpec));
        int childWidthSize = getMeasuredWidth();
        int childHeightSize = (int) (childWidthSize / 38f * 20f);
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidthSize,
                MeasureSpec.EXACTLY);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(childHeightSize,
                MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

}
