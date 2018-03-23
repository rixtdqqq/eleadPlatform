package com.elead.card.cardviews;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.elead.card.mode.BaseCardInfo;
import com.elead.eplatform.R;
import com.elead.upcard.activity.SignMainActivity;
import com.elead.upcard.utils.ConnectDao;
import com.elead.upcard.utils.LocationUtil;
import com.elead.upcard.utils.SignDialog;
import com.elead.upcard.view.RipAnimView;
import com.elead.utils.NetWorkUtils;
import com.elead.utils.ToastUtil;

import static com.elead.upcard.utils.ConnectDao.SIGN_IN_SUCCESS;

/**
 * @desc 打卡
 * @auth Created by Administrator on 2016/10/31 0031.
 */

public class PunchTheClock extends BaseCard implements View.OnClickListener {

    private LinearLayout ll_un_punch_the_clock;
    private RipAnimView sign_view;

    public PunchTheClock(Context context) {
        this(context, null);
    }

    public PunchTheClock(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PunchTheClock(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public int initLayout() {
        return R.layout.punch_the_clock;
    }

    @Override
    public void initView() {
        LocationUtil.inIt(mContext);
        ll_un_punch_the_clock = getView(R.id.ll_un_punch_the_clock);
        sign_view = getView(R.id.img_punch_the_clock);
        ll_un_punch_the_clock.setOnClickListener(this);
        sign_view.setOnSignClickLinstener(new RipAnimView.OnSignClickLinstener() {
            @Override
            public void onClick() {
                if (NetWorkUtils.isNetworkConnected(mContext)) {
                    ConnectDao.onSign(sign_view,handler);
                } else {
                    ToastUtil.showToast(mContext,mContext.getResources().getString(R.string.elead_connect_failed),1).show();
                }
            }
        });

//        layout = (RippleLayout) mView.findViewById(R.id
//                .ripple_layout);
//        if (!layout.isRippleAnimationRunning()) {
//            layout.startRippleAnimation();
//        }
    }

    @Override
    public void setDate(BaseCardInfo baseCardInfo) {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.ll_un_punch_the_clock:
                //非打卡
                mContext.startActivity(new Intent(mContext, SignMainActivity.class));
                break;
            default:
                break;
        }
    }

    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case SIGN_IN_SUCCESS:
                    SignDialog.ShowSuccessDialog(sign_view, msg);
                    break;
                case ConnectDao.SIGN_FAIL:
                    SignDialog.ShowFailDialog(sign_view,msg);
                    break;
            }
        }
    };
}
