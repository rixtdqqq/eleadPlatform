package cn.finalteam.mygallery.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.RadioButton;

import cn.finalteam.mygallery.R;

public class CusTextCheckBox extends RadioButton {

    private Context context;
    private int bitmap;

    private String text = "选择";
    private Drawable drfaultresource;
    private Drawable checkresource;

    public CusTextCheckBox(Context context) {
        super(context);
        inIt(context);
    }

    public CusTextCheckBox(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        inIt(context);
    }

    public CusTextCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        inIt(context);
    }


    private void inIt(Context context) {
        this.context = context;
        float scale = context.getResources().getDisplayMetrics().density;
        drfaultresource = context.getResources().getDrawable(R.drawable.check_normal);
        drfaultresource.setBounds(0, 0, drfaultresource.getMinimumWidth(),
                drfaultresource.getMinimumHeight());

        checkresource = context.getResources().getDrawable(R.drawable.check_pressed);
        checkresource.setBounds(0, 0, checkresource.getMinimumWidth(),
                checkresource.getMinimumHeight());
        setCompoundDrawables(drfaultresource, null, null, null);
        setCompoundDrawablePadding((int) (5 * scale + 0.5f));
        setText(text);
    }

    @Override
    public void setChecked(boolean checked) {
        super.setChecked(checked);
        if (checked) {
            setCompoundDrawables(drfaultresource, null, null, null);
        } else {
            setCompoundDrawables(checkresource, null, null, null);
        }
    }

    public void init(String text, int defaultBitmap, int checkedBitmap) {

        this.drfaultresource = resource2Drawable(defaultBitmap);
        this.checkresource = resource2Drawable(checkedBitmap);
        this.text = text;
    }

    private Drawable resource2Drawable(int res) {
        Drawable drawable = context.getResources().getDrawable(res);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                drawable.getMinimumHeight());
        return drawable;
    }

}
