package cn.finalteam.mygallery.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import cn.finalteam.mygallery.R;

public class CusCheckBox extends CheckBox implements CompoundButton.OnCheckedChangeListener {

    private Paint paint;
    private Context context;
    private Bitmap defaultBitmap;
    private Bitmap checkedBitmap;
    private Matrix matrix;
    private float bitmapHeight;

    public CusCheckBox(Context context) {
        this(context, null);
    }

    public CusCheckBox(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CusCheckBox(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        inIt(context);
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CusCheckBox);
            int defDrawable = a.getResourceId(R.styleable.CusCheckBox_def_drawable, R.drawable.check_normal);
            int cheDrawable = a.getResourceId(R.styleable.CusCheckBox_check_drawable, R.drawable.check_pressed);
            init(defDrawable, cheDrawable);
        }
    }

    private int width;
    private int height;


    private void inIt(Context context) {
        this.context = context;
        paint = new Paint();
        matrix = new Matrix();
        matrix.mapRect(new RectF(0, 0, width, bitmapHeight));
        setClickable(true);
        setBackgroundColor(Color.TRANSPARENT);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        if (isChecked()) {
            canvas.drawBitmap(checkedBitmap, matrix, null);
        } else {
            canvas.drawBitmap(defaultBitmap, matrix, null);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right,
                            int bottom) {
        System.out.println("onLayout");
        width = getWidth();
        height = getHeight();
        bitmapHeight = height / 10 * 5;
        matrix.reset();
        if (null == defaultBitmap) {
            return;
        }
        float s = defaultBitmap.getWidth() > defaultBitmap.getHeight() ? bitmapHeight
                / defaultBitmap.getWidth() : bitmapHeight / defaultBitmap.getHeight();
        matrix.postScale(s, s);
        matrix.postTranslate((width - defaultBitmap.getWidth() * s) / 2, (height - defaultBitmap.getHeight() * s) / 2);
        // TODO Auto-generated method stub
        super.onLayout(changed, left, top, right, bottom);
    }

    public void init(int defaultBitmap, int checkedBitmap) {
        this.defaultBitmap = BitmapFactory.decodeResource(getResources(), defaultBitmap);
        this.checkedBitmap = BitmapFactory.decodeResource(getResources(), checkedBitmap);
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }

    /**
     * Called when the checked state of a compound button has changed.
     *
     * @param buttonView The compound button view whose state has changed.
     * @param isChecked  The new checked state of buttonView.
     */
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        invalidate();
    }

    @Override
    public void setChecked(boolean checked) {
        super.setChecked(checked);
        invalidate();
    }
}
