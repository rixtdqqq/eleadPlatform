package com.elead.views;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.elead.application.MyApplication;
import com.elead.eplatform.R;
import com.elead.im.util.EaseUserUtils;
import com.elead.module.EpUser;
import com.hyphenate.chat.EMClient;

/**
 * Created by Administrator on 2016/12/6 0006.
 */

public class TitleView extends RelativeLayout {
    private Context mContext;
    private TextView title_decrie_mid;
    private CircularImageView my_photo;
    private TextView tv_use_name;
    private LinearLayout news_ll_id;
    private ImageView add_more_iv;
    private ImageView qc_code_iv;
    private LinearLayout work_ll;
    private ImageView add_more_iv_iv;
    private ImageView search_iv;
    private LinearLayout link_ll;
    private ImageView telphonte_iv;
    private ImageView link_iv;
    private OnClickListenerM onClickListenerM;


    public TitleView(Context context) {
        this(context, null);
    }

    public TitleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    private void initView() {
        LayoutInflater.from(mContext).inflate(R.layout.titleview_item_bar, this, true);
        title_decrie_mid = (TextView) findViewById(R.id.title_text_mid);
        my_photo = (CircularImageView) findViewById(R.id.my_photo_img);
        tv_use_name = (TextView) findViewById(R.id.use_name_id);

        news_ll_id = (LinearLayout) findViewById(R.id.news_ll_id);
        add_more_iv = (ImageView) findViewById(R.id.add_more_id);
        qc_code_iv = (ImageView) findViewById(R.id.qc_code_id);

        work_ll = (LinearLayout) findViewById(R.id.work_ll);
        add_more_iv_iv = (ImageView) findViewById(R.id.add_more_id_id);
        search_iv = (ImageView) findViewById(R.id.search_id);

        link_ll = (LinearLayout) findViewById(R.id.link_ll);
        telphonte_iv = (ImageView) findViewById(R.id.telphone_id);
        link_iv = (ImageView) findViewById(R.id.link_id);


        qc_code_iv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onClickListenerM) {
                    onClickListenerM.onQcCodeClick(qc_code_iv);
                }
            }
        });

        add_more_iv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onClickListenerM) {
                    onClickListenerM.onAddClick(add_more_iv);
                }
            }
        });


        add_more_iv_iv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onClickListenerM) {
                    onClickListenerM.onAddClick(add_more_iv_iv);
                }
            }
        });


        search_iv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onClickListenerM) {
                    onClickListenerM.onSearchClick(search_iv);
                }
            }
        });


        telphonte_iv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onClickListenerM) {
                    onClickListenerM.onTelPhoneClick(telphonte_iv);
                }
            }
        });


        link_iv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onClickListenerM) {
                    onClickListenerM.onAddlinkClick(link_iv);
                }
            }
        });


    }

    private int mPosition = -1;
    //传入的滚动的位置改变不同标题
    public void setSlectTitle(int position) {
        mPosition = position;
        if (position == 0) {
            title_decrie_mid.setVisibility(View.VISIBLE);
            my_photo.setVisibility(View.GONE);
            tv_use_name.setVisibility(View.GONE);
            news_ll_id.setVisibility(View.GONE);
            work_ll.setVisibility(View.GONE);
            link_ll.setVisibility(View.GONE);
            title_decrie_mid.setText(getResources().getString(R.string.home_page));

        }

        if (position == 1) {
            title_decrie_mid.setVisibility(View.VISIBLE);
            my_photo.setVisibility(View.GONE);
            tv_use_name.setVisibility(View.GONE);
            work_ll.setVisibility(View.GONE);
            link_ll.setVisibility(View.GONE);
            news_ll_id.setVisibility(View.VISIBLE);
            title_decrie_mid.setText(getResources().getString(R.string.message_page));

        }

        if (position == 2) {
            title_decrie_mid.setVisibility(View.VISIBLE);
            tv_use_name.setVisibility(View.VISIBLE);
            link_ll.setVisibility(View.GONE);
            news_ll_id.setVisibility(View.GONE);
            work_ll.setVisibility(View.VISIBLE);
            title_decrie_mid.setVisibility(View.GONE);
            if (!TextUtils.isEmpty(MyApplication.user.work_no)){
                my_photo.setBackgroundColor(MyApplication.user.work_no);
                my_photo.setText(MyApplication.user.name);
                my_photo.setVisibility(View.VISIBLE);
            }

        }

        if (position == 3) {
            my_photo.setVisibility(View.GONE);
            tv_use_name.setVisibility(View.GONE);
            link_ll.setVisibility(View.VISIBLE);
            news_ll_id.setVisibility(View.GONE);
            work_ll.setVisibility(View.GONE);
            title_decrie_mid.setVisibility(View.VISIBLE);
            title_decrie_mid.setText(getResources().getString(R.string.contact_page));

        }

        if (position == 4) {
            my_photo.setVisibility(View.GONE);
            tv_use_name.setVisibility(View.GONE);
            link_ll.setVisibility(View.GONE);
            news_ll_id.setVisibility(View.GONE);
            work_ll.setVisibility(View.GONE);
            title_decrie_mid.setVisibility(View.VISIBLE);
            title_decrie_mid.setText(getResources().getString(R.string.me_page));

        }


    }

    //各个点击的事件
    public void setOnClickListenerM(TitleView.OnClickListenerM onClickListenerM) {
        this.onClickListenerM = onClickListenerM;
    }

    public interface OnClickListenerM {
        //在消息页面点击创建澡堂模式的ImageView回调接口
        void onQcCodeClick(View view);

        //在工作页面点击铃铛ImageView查看消息页面回调接口
        void onTelPhoneClick(View view);

        //点击搜索ImageView时回调接口；
        void onSearchClick(View view);

        //在联系人页面点击添加好友ImageView回调接口
        void onAddlinkClick(View view);

        //点击加号回调接口
        void onAddClick(View view);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getDefaultSize(0, widthMeasureSpec),
                getDefaultSize(0, heightMeasureSpec));
        int childWidthSize = getMeasuredWidth();
        int childHeightSize = (int) (childWidthSize / 4.08f);
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidthSize,
                MeasureSpec.EXACTLY);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(childHeightSize,
                MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void updateHeadPhoto(EpUser epUser) {
        setSlectTitle(mPosition);
        if (!TextUtils.isEmpty(epUser.name)) {
          //  my_photo.setText(epUser.name);
            tv_use_name.setText(epUser.name);

        }
    }

}
