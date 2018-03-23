package com.elead.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.elead.card.views.BaseLayoutView;
import com.elead.eplatform.R;
import com.elead.utils.AnimUtils;

/**
 * Created by Administrator on 2017/1/17 0017.
 */

public class SelectRotateRightLayout extends BaseLayoutView {
    private CircularImageView cmg_left;
    private TextView tv_text;
    private ImageView img_right;
    private OnClickListener onClickListener;

    public SelectRotateRightLayout(Context context) {
        this(context, null);
    }

    public SelectRotateRightLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SelectRotateRightLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = mContext.obtainStyledAttributes(attrs, R.styleable.SelectRotateRightLayout);
        Drawable image = ta.getDrawable(R.styleable.SelectRotateRightLayout_leftImage);
        String text = ta.getString(R.styleable.SelectRotateRightLayout_centerText);
        ta.recycle();


        cmg_left = getView(R.id.cmg_left);
        tv_text = getView(R.id.tv_text);
        img_right = getView(R.id.img_right);
        if (null != image)
            cmg_left.setImageDrawable(image);
        if (null != text)
            tv_text.setText(text);
        getView(R.id.gen_linear).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onClickListener && !AnimChangeLayout.isAnim) {
                    onClickListener.onClick(SelectRotateRightLayout.this);
                    AnimUtils.rotateAnim(img_right, 90);
                }

            }
        });
    }


    @Override
    public int initLayout() {
        return R.layout.select_layout;
    }

    @Override
    public void initView() {

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getDefaultSize(0, widthMeasureSpec),
                getDefaultSize(0, heightMeasureSpec));
        int childWidthSize = getMeasuredWidth();
        int childHeightSize = (int) (childWidthSize / 6.54f);
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidthSize,
                MeasureSpec.EXACTLY);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(childHeightSize,
                MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public CircularImageView getCmg_left() {
        return cmg_left;
    }


    public void setTv_text(String text) {
        tv_text.setText(text);
    }

}
