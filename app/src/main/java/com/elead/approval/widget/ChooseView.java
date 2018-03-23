package com.elead.approval.widget;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.elead.approval.Interfaces.ITextItem;
import com.elead.eplatform.R;
import com.elead.pickerview.TimePickerView;
import com.elead.upcard.utils.Utils;
import com.elead.utils.DensityUtil;
import com.elead.views.ActionSheetDialog;

import java.util.Calendar;

/**
 * Created by Administrator on 2016/9/14 0014.
 */
public class ChooseView extends LinearLayout implements ITextItem {

    private TextView tvRight_no_arrow;
    TextView tvLeft;
    TextView tvRight;
    private Context context;
    private View inflate;
    private String[] itemStrs;
    private String rightText = null;
    private String rightTextNoArrow;
    private TimePickerView.Type typeTime;
    private String format = "";
    private boolean isClick;
    private String StartTimetext, EndTimetext;
    public String date;
    public String rightLt;


    public ChooseView(Context context) {
        this(context, null);
    }

    public ChooseView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChooseView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        inflate = LayoutInflater.from(context).inflate(R.layout.view_chooselin, this);
        tvLeft = (TextView) inflate.findViewById(R.id.tv_left);
        tvRight = (TextView) inflate.findViewById(R.id.tv_right);
        tvRight_no_arrow = (TextView) inflate.findViewById(R.id.tv_right_no_arrow);
    }

    @Override
    public String getContent() {
        return tvRight.getText().toString();
    }

    @Override
    public boolean isEmpty() {
        return tvRight.getText().toString().contains("必填") ? true : false;
    }

    public static enum Type {
        time, leave, other, No_arrow
    }

    private Type type;

    public void inIt(final Type type, String leftText, String rightHint, int marginTop, int marginBottom, String[] itemStrs) {
        inIt(type, leftText, marginTop, marginBottom);
        this.itemStrs = itemStrs;
        this.rightText = rightHint;
    }

    ;

    public void inIt(final Type type, String leftText, int marginTop, int marginBottom) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
        layoutParams.bottomMargin = DensityUtil.dip2px(context, marginBottom);
        layoutParams.topMargin = DensityUtil.dip2px(context, marginTop);
        setLayoutParams(layoutParams);

        tvLeft.setText(leftText);

        if (type == Type.No_arrow) {
            tvRight.setVisibility(View.GONE);
            tvRight_no_arrow.setVisibility(View.VISIBLE);

        } else if (type == Type.leave || type == Type.leave || type == Type.time) {
            tvRight.setVisibility(View.VISIBLE);
            tvRight_no_arrow.setVisibility(View.GONE);
        }

        if (isClick) {
            inflate.setEnabled(true);
        } else {
            inflate.setEnabled(false);

        }
        inflate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type == Type.time) {
                    showChooseTimeDialog();
                } else if (type == Type.leave) {
//                    final String[] setItems = {"请选择", "事假", "病假", "年假", "调休", "婚假", "产假", "陪产假", "路途假", "其他"};
//                    showChooseLeaveDialog(setItems);
                    showChooseBottomLeaveTyte();
                } else if (type == Type.other) {
                    showChooseLeaveDialog(itemStrs);
                } else if (type == Type.No_arrow) {
                    showTimeCalendarDialog();

                }
            }
        });

    }

    private void showChooseLeaveDialog(final String[] items) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);

        dialog.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String setItem = items[which];
                if ("请选择".equals(setItem)) {
                    tvRight.setText(null == rightText ? "请选择(必填)" : rightText);
                } else {
                    tvRight.setText(setItem);
                }
            }
        }).show();
    }

    private void showChooseTimeDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                tvRight.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
            }
        }, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.show();

    }

    public void setRightText(boolean isClick) {
        this.rightTextNoArrow = rightText;
        this.isClick = isClick;
        if (isClick == false) {
//            String StartTimetextL = StartTimetext.substring(0, 4) + "-" + StartTimetext.substring(5, 7) + "-" +StartTimetext.substring(8, 10) + "-" + " " +StartTimetext.substring(11, 13) + ":" +StartTimetext.substring(14, 16);
//            String EndTimetextL = EndTimetext.substring(0, 4) + "-" + EndTimetext.substring(5, 7) + "-" +EndTimetext.substring(8, 10) + "-" + " " +EndTimetext.substring(11, 13) + ":" + EndTimetext.substring(14, 16);

            //df = new SimpleDateFormat("yyyy-MM-dd HH:mm");


            tvRight_no_arrow.setText("0 天");
        }
    }


    private void showChooseBottomLeaveTyte() {
        new ActionSheetDialog(context).builder()
                .setCanceledOnTouchOutside(true)
                .setCancelable(true)
                .addSheetItem(getResources().getString(R.string.paid_leave), ActionSheetDialog.SheetItemColor.Gray, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        tvRight.setText(getResources().getString(R.string.paid_leave));
                    }
                })
                .addSheetItem(getResources().getString(R.string.paid_leave), ActionSheetDialog.SheetItemColor.Gray, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        tvRight.setText("事假");
                    }
                })
                .addSheetItem(getResources().getString(R.string.paid_sick_leave), ActionSheetDialog.SheetItemColor.Gray, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        tvRight.setText(getResources().getString(R.string.paid_sick_leave));
                    }
                })
                .addSheetItem("非带薪病假", ActionSheetDialog.SheetItemColor.Gray, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        tvRight.setText("非带薪病假");
                    }
                }).show();
    }


    private void showTimeCalendarDialog() {
        typeTime = TimePickerView.Type.DINFINE;
        format = "yyyy-MM-dd HH:mm";

        Utils.alertTimerPicker(context, typeTime, format, new Utils.TimerPickerCallBack() {

            @Override
            public void onTimeSelect(String date) {
                // Log.i("log==","hhhh=="+ date.replace(date.replace(String.valueOf(date.charAt(4)),"年")+"uuu="+date.substring(6,8)+"yyy=="+date.substring(9,10));
                String dateString = date.substring(0, 4) + "年" + date.substring(5, 7) + "月" + date.substring(8, 10) + "日" + " " + date.substring(11, 13) + ":" + date.substring(14, 16);
                tvRight_no_arrow.setText(dateString);
                // Toast.makeText(context,date.substring(0,4)+"年"+date.substring(5,7)+"月"+date.substring(8,10)+"日"+date.substring(11,13)+":"+date.substring(14,16), Toast.LENGTH_SHORT).show();
                //rightLt = tvRight_no_arrow.getText().toString();


            }
        });
    }


}
