package com.elead.upcard.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.elead.activity.BaseActivity;
import com.elead.application.MyApplication;
import com.elead.eplatform.R;
import com.elead.upcard.adapter.UpCardAdapter;
import com.elead.upcard.modle.DutyInfo;
import com.elead.upcard.modle.RecordsValue;
import com.elead.upcard.modle.SignInfo;
import com.elead.upcard.utils.ConnectDao;
import com.elead.upcard.utils.Utils;
import com.elead.utils.Util;
import com.elead.views.CustomListView;
import com.gly.calendar.view.CustomDate;
import com.gly.calendar.view.DateUtil;
import com.gly.calendar.view.SignCalendar;
import com.gly.calendar.view.SignCalendarCard;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class AttendanceActivity extends BaseActivity {
    private static final int CNANGE_DATE = 456465;
    private static final int CHANGE_LIST_DATE = 71664;
    @BindView(R.id.atten_tv_normal_count)
    TextView atten_tv_normal_count;
    @BindView(R.id.atten_tv_error_count)
    TextView atten_tv_error_count;
    @BindView(R.id.atten_tv_late_count)
    TextView atten_tv_late_count;
    @BindView(R.id.atten_tv_earlyout_count)
    TextView atten_tv_earlyout_count;
    @BindView(R.id.top_text_layout)
    LinearLayout top_text_layout;
    @BindView(R.id.lv_attendance)
    CustomListView lv_attendance;
    @BindView(R.id.progress_bar)
    ProgressBar progress_bar;
    @BindView(R.id.tv_no_data)
    TextView tv_no_data;
    @BindView(R.id.ll_loading)
    LinearLayout ll_loading;
    @BindView(R.id.continor)
    LinearLayout continor;
    public SignCalendar signcalendar;
    public List<RecordsValue> records;
    public UpCardAdapter adapter;
    private List<DutyInfo> datas = new ArrayList<>();
    private int oldDate;
    private SignInfo signInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attendance_fragment);
        ButterKnife.bind(this);
        setTitle(getResources().getString(R.string.attendance_statistical), TitleType.NORMAL);

        signcalendar = new SignCalendar(mContext);
        signcalendar.setListener(new SignCalendarCard.OnCellClickListener() {
            @Override
            public void clickDate(final CustomDate date) {
                Message message = handler.obtainMessage();
                message.what = CHANGE_LIST_DATE;
                message.obj = date;
                handler.removeMessages(CHANGE_LIST_DATE);
                handler.sendMessageDelayed(message, 100);
            }

            @Override
            public void changeDate(CustomDate date) {
                Log.d("changeDate: ", "changeDate");
                if (0 != oldDate && oldDate != date.month) {
                    Utils.IS_REQUEST_DATA = true;
                }
                oldDate = date.month;
                if (Utils.IS_REQUEST_DATA) {
                    Utils.IS_REQUEST_DATA = false;
                    Message msg = handler.obtainMessage();
                    msg.obj = date.toString();
                    msg.what = CNANGE_DATE;
                    handler.removeMessages(CNANGE_DATE);
                    handler.sendMessageDelayed(msg, 500);
                }
            }
        });
        setTv_no_dataHeight();
        continor.addView(signcalendar, 1);
    }


    private void setTv_no_dataHeight() {
        ll_loading.getLayoutParams().height = (int) (MyApplication.size[0] * 0.3f);
    }


    private void changeListDate(final CustomDate date) {
        if (Util.isNotEmpty(records)) {
            RecordsValue recordsValue = null;
            for (RecordsValue value : records) {
                if (value.date.equals(date.toString())) {
                    recordsValue = value;
                    break;
                }
            }
            final RecordsValue finalRecordsValue = recordsValue;
            if (null != finalRecordsValue) {
                datas.clear();
                DutyInfo on_work = finalRecordsValue.on_duty;
                if (null != on_work) {
//                            on_work = new DutyInfo();
                    datas.add(on_work);
                }
                List<DutyInfo> others = finalRecordsValue.others;
                if (Util.isNotEmpty(others)) {
                    datas.addAll(others);
                }
                DutyInfo off_work = finalRecordsValue.off_duty;
                if (null != off_work) {
//                            off_work = new DutyInfo();
                    datas.add(off_work);
                }
                if (datas.size() > 0) {
                    isShowNoData(false);
                    initAdapter();
                } else {
                    clearData();
                }
            } else {
                clearData();
            }

        } else {
            clearData();
        }
    }


    private void clearData() {
        datas.clear();
        initAdapter();
        isShowNoData(true);
    }

    private void isShowNoData(boolean isShow) {
        if (isShow) {
            lv_attendance.setVisibility(View.GONE);
            ll_loading.setVisibility(View.VISIBLE);
            progress_bar.setVisibility(View.GONE);
            tv_no_data.setVisibility(View.VISIBLE);
        } else {
            progress_bar.setVisibility(View.GONE);
            tv_no_data.setVisibility(View.GONE);
            ll_loading.setVisibility(View.GONE);
            lv_attendance.setVisibility(View.VISIBLE);
        }
    }

    private void initAdapter() {
        if (null == adapter) {
            adapter = new UpCardAdapter(datas, mContext);
            lv_attendance.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case ConnectDao.ATTENDANCE_SUCCESS:
                    signInfo = (SignInfo) msg.obj;
                    initTopTextData(signInfo);

                    if (TextUtils.isEmpty(SignCalendarCard.checkedDay)) {
                        SignCalendarCard.checkedDay = new CustomDate().toString();
                    }
                    updateAbnormals();

                    break;
                case ConnectDao.SIGN_FAIL:
                    break;
                case CNANGE_DATE:
                    try {
                        String date = (String) msg.obj;
                        Date parse = DateUtil.sf.parse(date.toString());
                        String start = DateUtil.sf.format(DateUtil.getFirstDayOfMonth(parse));
                        Date end = null;
                        Date monthEndDate = DateUtil.getLastDayOfMonth(parse);
                        Date today = new Date();
                        if (monthEndDate.getTime() > today.getTime()) {
                            end = today;
                        } else {
                            end = monthEndDate;
                        }
                        String endstr = DateUtil.sf.format(end);
                        ConnectDao.getAttendanceDate(MyApplication.user.work_no, start
                                , endstr, handler);

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    break;
                case CHANGE_LIST_DATE:
                    CustomDate date = (CustomDate) msg.obj;
                    changeListDate(date);
                    break;
            }
        }
    };


    private void initTopTextData(SignInfo signInfo) {
        List<RecordsValue> records = signInfo.records;
        int normal = 0;
        int error = 0;
        int late = 0;
        int earlyout = 0;
        SimpleDateFormat sf1 = new SimpleDateFormat("hh:mm");
        SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        for (RecordsValue value : records) {
            if (value.has_abnormal) {
                if (null != value) {
                    if (null != value.on_duty || null != value.off_duty) {
                        error++;
                    }
                    if (null != value.on_duty) {
                        try {
                            String time = value.on_duty.time.substring(value.on_duty.time.length() - 8, value.on_duty.time.length() - 3);
                            String[] a = time.split(":");
                            double dtime = Integer.parseInt(a[0]) + Integer.parseInt(a[1]) / 60d;
                            if (dtime > 8.5d) {
                                late++;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                    if (null != value.off_duty) {
                        try {
                            String time = value.off_duty.time.substring(value.off_duty.time.length() - 8, value.off_duty.time.length() - 3);
                            String[] a = time.split(":");
                            double dtime = Integer.parseInt(a[0]);
                            if (dtime < 18) {
                                earlyout++;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            } else {
                normal++;
            }
        }
        atten_tv_normal_count.setText("" + normal);
        atten_tv_error_count.setText("" + error);
        atten_tv_late_count.setText("" + late);
        atten_tv_earlyout_count.setText("" + earlyout);
    }

    private void updateAbnormals() {
        signcalendar.update();
        new Thread() {
            @Override
            public void run() {
                if (null == signInfo.records) {
                    return;
                }
                records = signInfo.records;
                List<String> abnormals = new ArrayList<>();
                List<String> normals = new ArrayList<>();
                if (Util.isNotEmpty(records))
                    for (RecordsValue recordsValue : records) {
                        if (null != recordsValue) {
                            boolean has_abnormal = recordsValue.has_abnormal;
                            if (null != recordsValue.off_duty || null != recordsValue.others || null != recordsValue.off_duty) {
                                if (has_abnormal) {
                                    abnormals.add(recordsValue.date);
                                } else {
                                    normals.add(recordsValue.date);
                                }
                            }
                        }
                    }
                SignCalendarCard.abnormal = abnormals;
                SignCalendarCard.normal = normals;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("changeListDate: ", "changeListDate");
                        changeListDate(new CustomDate(SignCalendarCard.checkedDay));
                    }
                });
            }
        }.start();

    }

    @Override
    protected void onResume() {
        super.onResume();
        CustomDate date = new CustomDate();
        Message msg = handler.obtainMessage();
        msg.obj = date.toString();
        msg.what = CNANGE_DATE;
        handler.removeMessages(CNANGE_DATE);
        handler.sendMessageDelayed(msg, 300);
//        if (null != signcalendar) {
//            signcalendar.update();
//        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
