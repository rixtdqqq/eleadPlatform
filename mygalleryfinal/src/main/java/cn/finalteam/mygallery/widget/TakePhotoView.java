package cn.finalteam.mygallery.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageButton;

import cn.finalteam.mygallery.R;

/**
 * Desction:为了兼容fresco框架而自定义的ImageView
 * Author:pengjianbo
 * Date:2015/12/24 0024 20:14
 */
public class TakePhotoView extends ImageButton {


    private Paint paint;
    private Paint textpaint;
    private String text = "";
    private Bitmap defaultBitmap;
    private Bitmap checkedBitmap;
    private Matrix matrix;
    private float bitmapHeight;
    private int defaultTextColor;
    private int checkedTextColor;
    private float vertSpace;

    public TakePhotoView(Context context) {
        this(context, null);
    }

    public TakePhotoView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        inIt(context);
    }

    public TakePhotoView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    private int textHeight;
    private int width;
    private int height;

    private void inIt(Context context) {
        setImageResource(R.drawable.takephoto_selector);
//        paint = new Paint();
//        textpaint = new Paint();
//        textpaint.setColor(Color.WHITE);
////        float scale = context.getResources().getDisplayMetrics().density;
////        textpaint.setTextSize(10 * scale + 0.5f);
//        textpaint.setTextAlign(Paint.Align.CENTER);
//        textpaint.setFakeBoldText(true);
//        textpaint.setTypeface(Typeface.DEFAULT_BOLD);
//
//
//        paint.setAntiAlias(true);
//        matrix = new Matrix();
//        matrix.mapRect(new RectF(0, 0, width, bitmapHeight));
//
//        defaultBitmap = BitmapFactory.decodeResource(getResources(),
//                R.drawable.btn_takephoto_pressed);
//
//
//        checkedBitmap = BitmapFactory.decodeResource(getResources(),
//                R.drawable.btn_takephoto_normal);
//        text = "拍摄照片";
//        defaultTextColor = Color.TRANSPARENT;
//        checkedTextColor = Color.parseColor("#30000000");
    }
//
//    @Override
//    protected void onDraw(Canvas canvas) {
//        // TODO Auto-generated method stub
//        super.onDraw(canvas);
//        if (isPressed()) {
//            canvas.drawBitmap(checkedBitmap, matrix, null);
//            paint.setColor(checkedTextColor);
//        } else {
//            canvas.drawBitmap(defaultBitmap, matrix, null);
//            paint.setColor(defaultTextColor);
//        }
//        if (!"".equals(text)) {
//            canvas.drawText(text, width / 2, height - vertSpace, textpaint);
//        }
//        canvas.drawRect(0, 0, width, height, paint);
//
//        // if (mt >= getWidth()) {
//        // int index = (int) (getWidth() / mt * text.length());
//        // paint.setTextAlign(Align.LEFT);
//        // StringBuilder builder = new StringBuilder();
//        // builder.append(text.substring(0, index - 1));
//        // builder.append("..");
//        // canvas.drawText(builder.toString(), 0,
//        // height - textHeight / 2, paint);
//        // } else {
//        // paint.setTextAlign(Align.CENTER);
//        // canvas.drawText(text, getWidth() / 2,
//        // (getHeight() + textHeight) / 2, paint);
//        // }
//    }
//
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                setPressed(true);
//                invalidate();
//                break;
//            case MotionEvent.ACTION_UP:
//            case MotionEvent.ACTION_CANCEL:
//                setPressed(false);
//                invalidate();
//                break;
//        }
//        return false;
//    }
//
//    @Override
//    protected void onLayout(boolean changed, int left, int top, int right,
//                            int bottom) {
//        matrix.reset();
//        width = getWidth();
//        height = getHeight();
//
//        if (null == text || !"".equals(text)) {
//            textHeight = height / 13;
//            vertSpace = height / 10;
//            textpaint.setTextSize(textHeight);
//            bitmapHeight = (height - textHeight) / 10 * 8;
//        } else {
//            bitmapHeight = height;
//        }
//        float s = defaultBitmap.getWidth() < defaultBitmap.getHeight() ? bitmapHeight
//                / defaultBitmap.getWidth()
//                : bitmapHeight / defaultBitmap.getHeight();
//        if (null == defaultBitmap || null == checkedBitmap) {
//            return;
//        }
//        matrix.postScale(s, s);
//        matrix.postTranslate((width - defaultBitmap.getWidth() * s) / 2, height
//                - defaultBitmap.getHeight() * s - textHeight - vertSpace);
//        // TODO Auto-generated method stub
//        super.onLayout(changed, left, top, right, bottom);
//    }

}
