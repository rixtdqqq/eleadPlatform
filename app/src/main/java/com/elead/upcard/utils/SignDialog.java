package com.elead.upcard.utils;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.elead.application.MyApplication;
import com.elead.eplatform.R;
import com.elead.upcard.view.ISignBase;

import java.util.ArrayList;
import java.util.List;

import static com.elead.application.MyApplication.showingActivity;

/**
 * @author gly
 * @Description:自定义对话框
 */
public class SignDialog extends Dialog {
    private static final long RE_SHOW_DALEY = 500;//重复显示dialog间隔
    private static List<SignDialog> DIALOG_TASK = new ArrayList<>();//dialog队列
    private float pcHeight, pcWidth;
    private int width, height;

    private TextView sign_dialog_tv_distribe;
    private TextView sign_dialog_tv_more_words;
    private TextView sign_dialog_tv_time;

    private View sign_big_kuang, btn_zhezhao, imb_sign_dialog_close,
            top_rel, contentView, sign_dialog_img_top;


    @SuppressLint("NewApi")
    public SignDialog(String distribe,
                      String time, String more_words, boolean isSuccess) {
        super(showingActivity, R.style.sign_dialog);
        DIALOG_TASK.add(this);
        width = MyApplication.size[0];
        height = MyApplication.size[1];
        pcHeight = height / 75f;
        pcWidth = width / 43.82f;
        if (isSuccess) {
            contentView = LinearLayout.inflate(showingActivity, R.layout.sign_success_progress_dialog, null);
            initSuccessView(distribe,
                    time, more_words);
        } else {
            contentView = LinearLayout.inflate(showingActivity, R.layout.sign_fail_progress_dialog, null);
            initFailView(more_words);
        }

        setContentView(contentView);


     /*   setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                DIALOG_TASK.remove(SignDialog.this);
                if (DIALOG_TASK.size() > 0) {
                    handler.sendEmptyMessageDelayed(101, RE_SHOW_DALEY);
                }
            }
        });*/


        Window window = getWindow();
//        window.setWindowAnimations(R.style.myDialogAnim);
        window.setLayout(width, height);

    }


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 101:
                    DIALOG_TASK.get(DIALOG_TASK.size() - 1).show();
                    DIALOG_TASK.remove(DIALOG_TASK.size() - 1);
                    break;
            }
        }
    };

    @Override
    public void show() {
        boolean isAnyShow = false;
        for (Dialog dialog : DIALOG_TASK) {
            if (dialog.isShowing()) {
                isAnyShow = true;
                break;
            }
        }
        if (!isAnyShow && null != this && null != showingActivity) {
            try {
                super.show();
            } catch (Exception e) {
            }

        }
    }

    private void initView() {
        top_rel = contentView.findViewById(R.id.top_rel);
        btn_zhezhao = contentView.findViewById(R.id.btn_zhezhao);
        top_rel.setOnClickListener(clickListener);
        imb_sign_dialog_close = contentView.findViewById(R.id.imb_sign_dialog_close);
        imb_sign_dialog_close.setOnClickListener(clickListener);

        sign_dialog_img_top = contentView.findViewById(R.id.sign_dialog_img_top);
        sign_big_kuang = contentView.findViewById(R.id.sign_big_kuang);
        sign_dialog_tv_distribe = (TextView) contentView
                .findViewById(R.id.sign_dialog_tv_distribe);
        sign_dialog_tv_time = (TextView) contentView
                .findViewById(R.id.sign_dialog_tv_time);
        sign_dialog_tv_more_words = (TextView) contentView
                .findViewById(R.id.sign_dialog_tv_more_words);
    }

    private void initSuccessView(String distribe, String time, String more_words) {
        initView();
        chontralSuccessPosition();
        sign_dialog_tv_distribe.setText(distribe);
        sign_dialog_tv_time.setText(time);
        sign_dialog_tv_more_words.setText(more_words);

    }

    private void initFailView(String more_words) {
        initView();
        chontralFailPosition();
//        sign_dialog_tv_more_words.setText(more_words);
    }


    private void chontralSuccessPosition() {
        sign_dialog_img_top.setLayoutParams(new RelativeLayout.LayoutParams(
                (int) (pcWidth * 14.8f), (int) (pcHeight * 3.81f)));
        sign_dialog_img_top.setTranslationY(pcHeight * 19.55f);
        sign_dialog_img_top.setTranslationX((width - pcWidth * 14.8f) / 2);

        btn_zhezhao.setTranslationY(pcHeight * 17.74f);
        btn_zhezhao.setLayoutParams(new RelativeLayout.LayoutParams(
                (int) (pcWidth * 28f), (int) (pcHeight * 34.43f)));
        btn_zhezhao.setTranslationX((width - pcWidth * 28f) / 2);


        sign_big_kuang.setLayoutParams(new RelativeLayout.LayoutParams(
                (int) (pcWidth * 37.57f), (int) (pcHeight * 40.32f)));
        sign_big_kuang.setTranslationY(pcHeight * 11.89f);
        sign_big_kuang.setTranslationX((width - pcWidth * 37.57f) / 2);


        sign_dialog_tv_distribe.setTranslationY(pcHeight * 32f);
        sign_dialog_tv_time.setTranslationY(pcHeight * 34.20f);


        imb_sign_dialog_close.setTranslationY(pcHeight * 55f);


        sign_dialog_tv_more_words
                .setLayoutParams(new RelativeLayout.LayoutParams(
                        (int) (pcWidth * 18f), LayoutParams.WRAP_CONTENT));
        sign_dialog_tv_more_words.setTranslationY(pcHeight * 43f);
        sign_dialog_tv_more_words
                .setTranslationX((width - pcWidth * 18f) / 2);
    }

    private void chontralFailPosition() {
        sign_dialog_img_top.setLayoutParams(new RelativeLayout.LayoutParams(
                (int) (pcWidth * 14.8f), (int) (pcHeight * 3.81f)));
        sign_dialog_img_top.setTranslationY(pcHeight * 15.38f);
        sign_dialog_img_top.setTranslationX((width - pcWidth * 14.8f) / 2);

        btn_zhezhao.setTranslationY(pcHeight * 17.74f);
        btn_zhezhao.setLayoutParams(new RelativeLayout.LayoutParams(
                (int) (pcWidth * 28f), (int) (pcHeight * 34.43f)));
        btn_zhezhao.setTranslationX((width - pcWidth * 28f) / 2);


        sign_big_kuang.setLayoutParams(new RelativeLayout.LayoutParams(
                (int) (pcWidth * 30f), (int) (pcHeight * 24.62f)));
        sign_big_kuang.setTranslationY(pcHeight * 21.59f);
        sign_big_kuang.setTranslationX((width - pcWidth * 30f) / 2);

        imb_sign_dialog_close.setTranslationY(pcHeight * 56f);

        sign_dialog_tv_more_words
                .setLayoutParams(new RelativeLayout.LayoutParams(
                        (int) (pcWidth * 24f), LayoutParams.WRAP_CONTENT));
        sign_dialog_tv_more_words.setTranslationY(pcHeight * 48.2f);
        sign_dialog_tv_more_words
                .setTranslationX((width - pcWidth * 24f) / 2);
    }

    View.OnClickListener clickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.top_rel:
                case R.id.imb_sign_dialog_close:
                    dismiss();
                    break;
                default:
                    break;
            }

        }
    };


    private static String dialogStr = "";

    public static void ShowSuccessDialog(ISignBase iSignBase, Message msg) {
        iSignBase.setConnectFinish(true);
        String str = msg.getData().getString("time");
        dialogStr = TextUtils.isEmpty(str) ? dialogStr : str;
        new SignDialog("打卡时间", dialogStr, "坚持努力,\n收获最优秀的自己", true).show();
    }


    public static void ShowFailDialog(ISignBase iSignBase, Message msg) {
        iSignBase.setConnectFinish(true);
        new SignDialog("", "", "", false).show();
    }
}
