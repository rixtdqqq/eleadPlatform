package com.elead.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.RelativeLayout;

import com.elead.application.MyApplication;
import com.elead.eplatform.R;


/**
 * @author gly
 * @Description:自定义对话框
 */
public class EloadingDialog extends Dialog {
    public static EloadingDialog eloadingDialog;

    public static void ShowDialog(Context mContext) {
        if (null != eloadingDialog) {
            if (null != eloadingDialog.getWindow() && eloadingDialog.isShowing()) {
                eloadingDialog.cancel();
                eloadingDialog = null;
            }
        }
        eloadingDialog = new EloadingDialog(mContext);
        eloadingDialog.setCancelable(true);
        eloadingDialog.show();

    }

    public static void cancle() {
        if (null != eloadingDialog && eloadingDialog.isShowing()) {
            eloadingDialog.dismiss();
            eloadingDialog = null;
        }
    }

    public EloadingDialog(Context mContext) {
        super(mContext, R.style.sign_dialog);
        float density = mContext.getResources().getDisplayMetrics().density;
        int size = (int) (density * 90);


        Window dialogWindow = getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        p.height = (int) (MyApplication.size[0] * 0.2); // 高度设置为屏幕的0.6
        p.width = (int) (MyApplication.size[0] * 0.25); // 宽度设置为屏幕的0.65
        dialogWindow.setAttributes(p);

        View inflate = LayoutInflater.from(mContext).inflate(R.layout.e_loading_dialog, null);
        setContentView(inflate, new RelativeLayout.LayoutParams(size, size));
        inflate.setBackgroundResource(R.drawable.bantou_bg);
        RotateAnimation animation = new RotateAnimation(0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

        animation.setInterpolator(new LinearInterpolator());
        animation.setDuration(900);
        animation.setRepeatMode(Animation.RESTART);
        animation.setRepeatCount(Animation.INFINITE);
        findViewById(R.id.imv_loading).startAnimation(animation);

    }

    protected EloadingDialog(Context context, boolean cancelable,
                             OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        // TODO Auto-generated constructor stub
    }


    public static void showDailog() {
        ToastUtil.showToast(MyApplication.showingActivity, MyApplication.showingActivity.
                getResources().getString(R.string.construction_function), 2000).show();
    }

}
