package com.elead.views;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.elead.application.MyApplication;
import com.elead.eplatform.R;
import com.elead.im.util.EaseUserUtils;
import com.hyphenate.chat.EMClient;
import com.xys.libzxing.zxing.encoding.ColorQRCodeWriter;


/**
 * created by xieshibin 2017/1/13
 */
public class QrCodeDialog extends Dialog {

	Activity context;
	CircularImageView photo_qrcode;
	ImageView qr_code_imge_id;
	TextView use_name_iv;

	private int width, height;
	private Bitmap bitmap;
	private String userName;
	String hx_no;//环信号

	public QrCodeDialog(Context mContext,String hx_no,String userName) {
		super(mContext);
		context = (Activity) mContext;
		this.hx_no = hx_no;
		this.userName = userName;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setCancelable(true);
		setContentView(R.layout.qrcode_layout);

//		resetSize();
		initView();
		initDatas();

	}

	private void initDatas() {

		if(!TextUtils.isEmpty(hx_no)){
			use_name_iv.setText(userName);
		}else{
			hx_no = EMClient.getInstance().getCurrentUser();
			use_name_iv.setText(MyApplication.user.name);
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

		final ViewTreeObserver vto = qr_code_imge_id.getViewTreeObserver();
		vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
			public boolean onPreDraw() {
				if (vto.isAlive()) {
					vto.removeOnPreDrawListener(this);
				}
				height = qr_code_imge_id.getMeasuredHeight();
				width = qr_code_imge_id.getMeasuredWidth();
				try {
					//获得二维码图片
//					bitmap = BitmapUtil.makeQRImage(hx_no,
//							width, height);
					bitmap = ColorQRCodeWriter.encodeBitmap(hx_no, width, height, Color.parseColor("#000000"),
							Color.parseColor("#000000"), Color.parseColor("#000000"), false);
					//设置二维码图片
					qr_code_imge_id.setImageBitmap(bitmap);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return true;
			}
		});

	}

	public void initView(){
		photo_qrcode = (CircularImageView)findViewById(R.id.photo_qrcode);
		qr_code_imge_id = (ImageView)findViewById(R.id.qr_code_imge_id);
		use_name_iv = (TextView) findViewById(R.id.use_name_iv);
	}

	/**
	 * 重新设定布局宽高
	 */
	private  void  resetSize(){
		LinearLayout main_layout = (LinearLayout)findViewById(R.id.main_layout);

		WindowManager wm = (WindowManager) getContext()
				.getSystemService(Context.WINDOW_SERVICE);
		int width = wm.getDefaultDisplay().getWidth();
		int height = wm.getDefaultDisplay().getHeight();
		FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams)main_layout.getLayoutParams();
		layoutParams.width = (int) ((int)width*0.8);
		layoutParams.height = (int) ((int)height*0.7);
		main_layout.setLayoutParams(layoutParams);

	}

}
