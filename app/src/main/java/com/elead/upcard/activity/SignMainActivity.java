package com.elead.upcard.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.elead.activity.BaseActivity;
import com.elead.application.MyApplication;
import com.elead.approval.activity.ApprovalMainActivity;
import com.elead.eplatform.R;
import com.elead.upcard.modle.DutyInfo;
import com.elead.upcard.modle.RecordsValue;
import com.elead.upcard.modle.SignInfo;
import com.elead.upcard.utils.ConnectDao;
import com.elead.upcard.utils.SignDialog;
import com.elead.upcard.utils.Utils;
import com.elead.upcard.view.RipAnimView2;
import com.elead.upcard.view.UpCardItem;
import com.elead.upcard.view.UpCardItemView;
import com.elead.upcard.view.UpCardLodingView;
import com.elead.utils.NetWorkUtils;
import com.elead.utils.ToastUtil;
import com.elead.utils.Util;
import com.gly.calendar.view.DateUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SignMainActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.sign_view)
    RipAnimView2 sign_view;
    @BindView(R.id.btn_err_upcard)
    Button btn_err_upcard;
    @BindView(R.id.btn_my_apply)
    Button btn_my_apply;
    @BindView(R.id.ll_up_card_main)
    LinearLayout ll_up_card_main;
    @BindView(R.id.btn_calendar)
    TextView btn_calendar;
    @BindView(R.id.btn_peie)
    Button btn_peie;
    private UpCardItem upCardItem;
    private UpCardLodingView upCardLodingView;
    private boolean isFirst = true;
    private boolean isFailed = false;
    private SignInfo signInfo;
    private static final int INSET_INDEX = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_main);
        getMyTitleView();
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        btn_calendar.setText(new SimpleDateFormat("yyyy/MM/dd").format(new Date()));
        upCardLodingView = new UpCardLodingView(mContext);
        ll_up_card_main.addView(upCardLodingView, INSET_INDEX);
        sign_view.setOnSignClickLinstener(new RipAnimView2.OnSignClickLinstener() {
            @Override
            public void onClick() {
                if (NetWorkUtils.isNetworkConnected(mContext)) {
                    ConnectDao.onSign(sign_view,handler);
                } else {
                    ToastUtil.showToast(mContext,mContext.getResources().getString(R.string.elead_connect_failed),1).show();
                }
            }
        });

        btn_my_apply.setOnClickListener(this);
        btn_err_upcard.setOnClickListener(this);
        btn_peie.setOnClickListener(this);
    }

    public void getMyTitleView() {
        setTitleView(R.layout.action_sign_activity);
        View myTitleView = actionBar.getCustomView();


        myTitleView.findViewById(R.id.imb_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        myTitleView.findViewById(R.id.imb_right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Utils.checkWifiAndGpsStatus(mContext);

        if (isFirst || isFailed) {
            isFirst = false;
            isFailed = false;
            getTodayAttendance();
        }
    }

    private void getTodayAttendance() {
        String todayDate = DateUtil.sf.format(new Date());
        ConnectDao.getAttendanceDate(MyApplication.user.work_no,
                todayDate, todayDate, handler);
    }


    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ConnectDao.SIGN_IN_SUCCESS:
                    Log.d("SIGN_IN_SUCCESS:", "SIGN_IN_SUCCESS");
                    getTodayAttendance();
                    SignDialog.ShowSuccessDialog(sign_view, msg);
                    break;
                case ConnectDao.ATTENDANCE_SUCCESS:

                    signInfo = (SignInfo) msg.obj;
                    Utils.IS_REQUEST_DATA = true;
                    if (null != signInfo && Util.isNotEmpty(signInfo.records)) {
                        RecordsValue recordsValue = signInfo.records.get(0);
                        if (null != recordsValue) {
                            DutyInfo on_work = recordsValue.on_duty;
                            if (null != on_work) {
                                if (null == upCardItem) {
                                    upCardLodingView = null;
                                    ll_up_card_main.removeViewAt(INSET_INDEX);
                                    upCardItem = new UpCardItem(mContext);
                                    upCardItem.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent intent = new Intent(mContext, AttendanceActivity.class);
                                            startActivity(intent);
                                        }
                                    });
                                    ll_up_card_main.addView(upCardItem, INSET_INDEX);
                                }
                                setData(upCardItem.getStartWork(), on_work, "上");
                                DutyInfo off_work = recordsValue.off_duty;
                                if (null != off_work) {
                                    setData(upCardItem.getEndWork(), off_work, "下");
                                }
                            } else {
                                showEmpty();
                            }

                        } else {
                            showEmpty();
                        }
                    } else {
                        showEmpty();
                    }

                    updateBottomText();

                    break;
                case ConnectDao.SIGN_FAIL:
                    SignDialog.ShowFailDialog(sign_view, msg);
                    break;
                case ConnectDao.ATT_FAIL:
                    showEmpty();
                    isFailed = true;
                    break;
            }
        }

    };

    private void updateBottomText() {
    }

    private void showEmpty() {
        if (null != upCardLodingView) {
            upCardLodingView.getProgress_bar().setVisibility(View.GONE);
            upCardLodingView.getTv_no_data().setVisibility(View.VISIBLE);
        }
    }

    private void setData(UpCardItemView upCardItemView, DutyInfo dutyInfo, String type) {
        String location = "";
        if (!TextUtils.isEmpty(dutyInfo.wifiname)) {
            location = dutyInfo.wifiname;
            Util.setBackGround(mContext, upCardItemView.getImg_location(), R.drawable.up_card_location_wifi);
        } else if (!TextUtils.isEmpty(dutyInfo.gpsaddr)) {
            location = dutyInfo.gpsaddr;
            Util.setBackGround(mContext, upCardItemView.getImg_location(), R.drawable.up_card_location_gps);
        } else if (!TextUtils.isEmpty(dutyInfo.locationName)){
            location = dutyInfo.locationName;
            if (TextUtils.equals("fingerprint", dutyInfo.type)) {
                Util.setBackGround(mContext, upCardItemView.getImg_location(), R.drawable.up_card_location_fingerprint);
            } else if (TextUtils.equals("IC_Card", dutyInfo.type)) {
                Util.setBackGround(mContext, upCardItemView.getImg_location(), R.drawable.up_card_location_card);
            }
        }
        upCardItemView.setUpCardTime(Utils.formatTime(dutyInfo.time));
        upCardItemView.setUpCardLocation(location);
        Utils.setStatus(upCardItemView.getTv_up_card_status(), dutyInfo.is_normal, type);
    }


//    long updateTime = 0;
//
//    private void initAttendanceDate() {//更新记录 
//        if (null != signInfo && Util.isNotEmpty(signInfo.records) && Util.isNotEmpty(attendanceFragment.records))
//            for (int i = 0; i < signInfo.records.size(); i++) {
//                RecordsValue mainrecordsValue = signInfo.records.get(i);
//                RecordsValue attrecordsValue1 = attendanceFragment.records.get(i);
//                if (mainrecordsValue.date.equals(attrecordsValue1.date)) {
//                    attendanceFragment.records.set(i, mainrecordsValue);
//                }
//            }
//    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_err_upcard:
                Intent intent = new Intent(mContext, ErrorActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_my_apply:
                Intent intent1 = new Intent(mContext, ApprovalMainActivity.class);
                startActivity(intent1);
                break;
        }
    }
}
