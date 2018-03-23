package com.elead.activity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.elead.application.MyApplication;
import com.elead.eplatform.R;
import com.elead.im.util.EaseUserUtils;
import com.elead.views.CircularImageView;
import com.hyphenate.chat.EMClient;
import com.xys.libzxing.zxing.encoding.ColorQRCodeWriter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/9/6 0006.
 */
public class QrCodeActivity extends BaseActivity {

    @BindView(R.id.unit_iv)
    TextView unit_tv;
    @BindView(R.id.photo_qrcode)
    CircularImageView photo_qrcode;
    @BindView(R.id.use_name_iv)
    TextView name_qrCode_username;
    @BindView(R.id.qr_code_imge_id)
    ImageView qrCode_imge;

    private int width, height;
    private Bitmap bitmap;
    private String userName;
    String hx_no, department;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generate_qrcode_activity);
        ButterKnife.bind(this);

        initDatas();
    }

    private void initDatas() {

        setTitle("二维码", TitleType.NORMAL);

        hx_no = getIntent().getStringExtra("hx_no");
        department = getIntent().getStringExtra("department");
        userName = getIntent().getStringExtra("user_name");
        if (!TextUtils.isEmpty(hx_no)) {
            name_qrCode_username.setText(userName);
            unit_tv.setText(department);
        } else {
            hx_no = EMClient.getInstance().getCurrentUser();
            name_qrCode_username.setText(MyApplication.user.name);
            unit_tv.setText(MyApplication.user.dept_name);
        }
        EaseUserUtils.setUserNick(hx_no, photo_qrcode);
        photo_qrcode.setVisibility(View.VISIBLE);
        //获得控件的宽高
        calculateView();

    }

    /**
     * 计算控件的宽高
     */
    private void calculateView() {

        final ViewTreeObserver vto = qrCode_imge.getViewTreeObserver();
        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            public boolean onPreDraw() {
                if (vto.isAlive()) {
                    vto.removeOnPreDrawListener(this);
                }
                height = qrCode_imge.getMeasuredHeight();
                width = qrCode_imge.getMeasuredWidth();
                DisplayMetrics metric = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(metric);
                int widthS = metric.widthPixels;     // 屏幕宽度（像素）
                int heightS = metric.heightPixels;   // 屏幕高度（像素）
                try {
//                    //获得二维码图片
//                    bitmap = BitmapUtil.makeQRImage(hx_no,
//                            WIDTH, HEIGHT);
//                    //设置二维码图片
//                    qrCode_imge.setImageBitmap(bitmap);
                    bitmap = ColorQRCodeWriter.encodeBitmap(hx_no, width, height, Color.parseColor("#ca0300"), Color.parseColor("#ca0300"), Color.parseColor("#0090e2"), false);
                    qrCode_imge.setImageBitmap( bitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            }
        });

    }

}
