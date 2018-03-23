package com.elead.upcard.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.location.LocationManager;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.elead.eplatform.R;
import com.elead.pickerview.TimePickerView;
import com.elead.pickerview.adapter.ArrayWheelAdapter;
import com.elead.pickerview.lib.WheelView;
import com.elead.utils.ToastUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * @desc 工具类
 * @auth Created by Administrator on 2016/12/8 0008.
 */

public class Utils {

    public static boolean IS_REQUEST_DATA = true;

    public static void setStatus(TextView tv, boolean isNormal, String type) {
        if (!TextUtils.isEmpty(type)) {
            if (isNormal) {
                tv.setText("正常");
                tv.setBackgroundResource(R.drawable.up_card_normal_bg);
            } else {
                if (TextUtils.equals("上", type)) {
                    tv.setText("迟到");
                } else if (TextUtils.equals("下", type)) {
                    tv.setText("早退");
                }
                tv.setBackgroundResource(R.drawable.up_card_abnormal_bg);
            }
        }

    }


    public static boolean checkWifiAndGpsStatus(final Activity context) {
        /* 取得WifiManager与LocationManager */
        WifiManager wManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        LocationManager lManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        /* 确认WiFi服务是关闭且不在开启运行中 */
        boolean wifiEnabled = !wManager.isWifiEnabled();
        boolean providerEnabled = !lManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean bWifiState = wManager.getWifiState() != WifiManager.WIFI_STATE_ENABLING;
        boolean nonthing = wifiEnabled && providerEnabled && bWifiState;
        if (nonthing) {
            ToastUtil.showToast(context, "WiFi和GPS未启用!", 1).show();
            /*if (!wManager.isWifiEnabled()
                    && wManager.getWifiState() != WifiManager.WIFI_STATE_ENABLING) {
                new android.app.AlertDialog.Builder(context).setTitle("检测到WiFi尚未开启,是否前往开启?").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS);
                        context.startActivityForResult(intent, 0);
                    }
                }).show();
                return false;
            } else if (!lManager.isProviderEnabled(LocationManager.GPS_PROVIDER)*//* 确认GPS是否开启 *//*) {
                new android.app.AlertDialog.Builder(context).setTitle("检测到GPS尚未开启,是否前往开启?").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                context.startActivityForResult(intent, 0);
                            }
                        }
                ).show();
                return false;
            }*/

        }
        return nonthing;
    }

    public static String formatTime(String time) {
        if (null == time)
            return "";
        try {
            SimpleDateFormat sf = new SimpleDateFormat("HH:mm");
            SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date onWorkre_time = sf2.parse(time);
            time = sf.format(onWorkre_time);
            return time;
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static void isShowLine(View v, boolean isShow) {
        if (isShow) {
            v.setVisibility(View.VISIBLE);
        } else {
            v.setVisibility(View.INVISIBLE);
        }
    }

    public static float formatTime2Integer(String time) {
        if (null == time)
            return 0;
        try {
            SimpleDateFormat sf = new SimpleDateFormat("HH:mm");
            SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date onWorkre_time = sf2.parse(time);
            time = sf.format(onWorkre_time);
            String a[] = time.split(":");
            return Float.parseFloat(a[0]) + Float.parseFloat(a[1]) / 60f;
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }


    /**
     * 弹出底部滚轮选择
     *
     * @param context
     * @param list
     * @param click
     */
    public static void alertBottomWheelOption(Context context, ArrayList<?> list, final OnWheelViewClick click) {

        final PopupWindow popupWindow = new PopupWindow();

        View view = LayoutInflater.from(context).inflate(R.layout.layout_bottom_wheel_option, null);
        TextView tv_confirm = (TextView) view.findViewById(R.id.btnSubmit);
        final WheelView wv_option = (WheelView) view.findViewById(R.id.wv_option);
        final WheelView wv_option1 = (WheelView) view.findViewById(R.id.wv_option1);
        final WheelView wv_option2 = (WheelView) view.findViewById(R.id.wv_option2);
        wv_option.setAdapter(new ArrayWheelAdapter(list));
        wv_option1.setAdapter(new ArrayWheelAdapter(list));
        wv_option2.setAdapter(new ArrayWheelAdapter(list));
        wv_option.setCyclic(false);
        wv_option1.setCyclic(false);
        wv_option2.setCyclic(false);
        wv_option.setLabel("天");
        wv_option1.setLabel("天");
        wv_option2.setLabel("天");
        wv_option.setTextSize(16);
        wv_option1.setTextSize(16);
        wv_option2.setTextSize(16);
//        float centerX=wv_option.getWidth()/2.0f;
//        float centerY=wv_option.getHeight()/2.0f;
//        AnimationUtil my3dAnimation = new AnimationUtil(ROTATE_X, 0, 180, centerX,
//                centerY, 310f);
//        wv_option.startAnimation(my3dAnimation);
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click.onClickL(view, wv_option.getCurrentItem());
                //click.onClick(view, wv_option1.getCurrentItem());
                //click.onClick(view, wv_option2.getCurrentItem());
                click.onClickMiddle(view, wv_option1.getCurrentItem());
                click.onClickR(view,wv_option2.getCurrentItem());
                popupWindow.dismiss();
            }
        });

        view.findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 2016/8/11 0011 取消
                popupWindow.dismiss();
            }
        });
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int top = view.findViewById(R.id.ll_container).getTop();
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    int y = (int) motionEvent.getY();
                    if (y < top) {
                        popupWindow.dismiss();
                    }
                }
                return true;
            }
        });
        popupWindow.setContentView(view);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
//        popupWindow.setAnimationStyle(R.style.ActionSheetDialogAnimation);
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.showAtLocation(((ViewGroup) ((Activity) context).findViewById(android.R.id.content)).getChildAt(0), Gravity.CENTER, 0, 0);
    }


    /**
     * 底部滚轮点击事件回调
     */
    public interface OnWheelViewClick {
        void onClickL(View view, int postion);
        void onClickMiddle(View view,int positonM);
        void onClickR(View view,int positionR);
    }


    /**
     * 弹出时间选择
     *
     * @param context
     * @param type     TimerPickerView 中定义的 选择时间类型
     * @param format   时间格式化
     * @param callBack 时间选择回调
     */
    public static void alertTimerPicker(Context context, TimePickerView.Type type, final String format, final TimerPickerCallBack callBack) {
        TimePickerView pvTime = new TimePickerView(context, type);
        //控制时间范围
        //        Calendar calendar = Calendar.getInstance();
        //        pvTime.setRange(calendar.get(Calendar.YEAR) - 20, calendar.get(Calendar.YEAR));
        pvTime.setTime(new Date());
        pvTime.setCyclic(false);
        pvTime.setCancelable(true);
        //时间选择后回调
        pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {

            @Override
            public void onTimeSelect(Date date) {
//                        tvTime.setText(getTime(date));
                SimpleDateFormat sdf = new SimpleDateFormat(format);
                callBack.onTimeSelect(sdf.format(date));
            }
        });
        pvTime.setTextSize(16);
        //弹出时间选择器
        pvTime.show();
    }


    /**
     * 时间选择回调
     */
    public interface TimerPickerCallBack {
        void onTimeSelect(String date);
    }




}
