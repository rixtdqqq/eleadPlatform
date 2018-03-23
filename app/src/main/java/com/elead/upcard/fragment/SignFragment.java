/*
package com.elead.upcard.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.elead.application.MyApplication;
import com.elead.eplatform.R;
import com.elead.upcard.activity.SignMainActivity;
import com.elead.upcard.modle.DutyInfo;
import com.elead.upcard.modle.RecordsValue;
import com.elead.upcard.modle.SignInfo;
import com.elead.upcard.utils.ConnectDao;
import com.elead.upcard.utils.SignDialog;
import com.elead.upcard.utils.Utils;
import com.elead.upcard.view.RipAnimView;
import com.elead.upcard.view.UpCardItem;
import com.elead.upcard.view.UpCardItemView;
import com.elead.upcard.view.UpCardLodingView;
import com.elead.utils.Util;
import com.gly.calendar.view.DateUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class SignFragment extends BaseAnimFragment {

    private static final String TAG = SignFragment.class.getSimpleName();
    private RipAnimView btn_sign;
    public List<SignInfo> attendances = new ArrayList<>();
    private LinearLayout ll_up_card_main;
    private TextView btn_calendar;
    private UpCardItem upCardItem;
    private UpCardLodingView upCardLodingView;
    private SignMainActivity signMainActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        signMainActivity = (SignMainActivity) context;
    }

    private boolean isFirst = true;
    private boolean isFailed = false;

    @Override
    protected void lazyLoad() {
        if (isFirst || isFailed) {
            isFirst = false;
            isFailed = false;
            getTodayAttendance();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return getFragmentView(inflater, container, R.layout.sign_fragment);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        ll_up_card_main = (LinearLayout) myView.findViewById(R.id.ll_up_card_main);
        btn_calendar = (TextView) myView.findViewById(R.id.btn_calendar);
        btn_calendar.setText(new SimpleDateFormat("yyyy/MM/dd").format(new Date()));

        upCardLodingView = new UpCardLodingView(mContext);
        ll_up_card_main.addView(upCardLodingView, 2);
        btn_sign = (RipAnimView) myView.findViewById(R.id.sign_view);
        btn_sign.setOnSignClickLinstener(new RipAnimView.OnSignClickLinstener() {
            @Override
            public void onClick() {
                ConnectDao.onSign(btn_sign, handler);
            }
        });
    }

    private void getTodayAttendance() {
        String todayDate = DateUtil.sf.format(new Date());
        ConnectDao.getAttendanceDate(MyApplication.user.work_no,
                todayDate, todayDate, handler);
    }

    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case ConnectDao.SIGN_IN_SUCCESS:
                    Log.d("SIGN_IN_SUCCESS:", "SIGN_IN_SUCCESS");
                    getTodayAttendance();
                    SignDialog.ShowSuccessDialog(msg);
                    break;
                case ConnectDao.ATTENDANCE_SUCCESS:

                    signMainActivity.signInfo = (SignInfo) msg.obj;
                    Utils.IS_REQUEST_DATA = true;
                    if (null != signMainActivity.signInfo && Util.isNotEmpty(signMainActivity.signInfo.records)) {
                        RecordsValue recordsValue = signMainActivity.signInfo.records.get(0);
                        if (null != recordsValue) {
                            DutyInfo on_work = recordsValue.on_duty;
                            if (null != on_work) {
                                if (null == upCardItem) {
                                    upCardLodingView = null;
                                    ll_up_card_main.removeViewAt(2);
                                    upCardItem = new UpCardItem(mContext);
                                    ll_up_card_main.addView(upCardItem, 2);
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

                    break;
                case ConnectDao.SIGN_FAIL:
                    SignDialog.ShowFailDialog(msg);
                    break;
                case ConnectDao.ATT_FAIL:
                    showEmpty();
                    isFailed = true;
                    break;
            }
        }

    };

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
        } else {

        }
        upCardItemView.setUpCardTime(Utils.formatTime(dutyInfo.time));
        upCardItemView.setUpCardLocation(location);
        Utils.setStatus(upCardItemView.getTv_up_card_status(), dutyInfo.is_normal, type);
    }

    @Override
    public void onResume() {
        super.onResume();
        btn_sign.startAnimation();
    }

    @Override
    public void onPause() {
        super.onPause();
        btn_sign.stopAnimation();
    }

}
*/
