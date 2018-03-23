package com.elead.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.elead.eplatform.R;
import com.elead.im.util.EaseUserUtils;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class CircularImageView extends ImageView {
    private static final int LONG_CLICK_HAND = 1000;
    // Border & Selector configuration variables
    private boolean isSelected;
    private int borderWidth;
    private int canvasSize;
    private Bitmap image;
    private Paint selectPaint;
    private Paint paintBorder;
    private Paint circlePaint;
    private String text = "";
    private float textHeight;
    private Paint textPaint;
    private float mHeight;
    private float mWidth;
    private float bitmapWidth;
    private float btnScale;
    private Matrix imgMatrix;
    private RectF btnRect;
    private Path btnPath;

    private int borderColor;
    private Paint linePaint;
    private float density;
    private Paint arcPaint;
    private String[] arcColors = new String[]{"#629ab8", "#a48fd9", "#f6b563", "#16c195", "#a48fd9", "#629ab8", "#f6b563", "#16c195"};
    private RectF rectArc;
    private float textCenterDistance;
    private String unReadMessageCount;
    private Paint messageCountPaint;
    private float messageCountH;
    private List<String> userNames;

    public CircularImageView(Context context) {
        this(context, null);
    }

    public CircularImageView(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.circularImageViewStyle);
    }

    public CircularImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    public void setBackgroundColor(String username) {
        circlePaint.setColor(userName2Color(username));
        invalidate();
    }

    public static int userName2Color(String userName) {
        if (TextUtils.isEmpty(userName)) {
            return Color.parseColor("#f6b563");
        }
        int color = 0;
        try {
            int s = Integer.parseInt(userName.substring(1));
            int a = s % 4;
            switch (a) {
                case 0:
                    color = Color.parseColor("#f6b563");
                    break;
                case 1:
                    color = Color.parseColor("#a48fd9");
                    break;
                case 2:
                    color = Color.parseColor("#16c195");
                    break;
                case 3:
                    color = Color.parseColor("#629ab8");
                    break;
            }
        } catch (Exception e) {
            color = hanzi2Color(userName);
        }
        return color;
    }


    public static int hanzi2Color(String str) {
        String s = hanzi216(str);
        String color = "";
        if (!TextUtils.isEmpty(s) && s.length() < 6) {
            String b = "";
            for (int i = 0; i < 6 - s.length(); i++) {
                b += "0";
            }
            color = b + s;
        } else {
            color = s.substring(s.length() - 6, s.length());
        }
        return Color.parseColor("#" + color);
    }

    public static String hanzi216(String str) {
        String result = "";
        if (TextUtils.isEmpty(str)) {
            return result;
        }
        String hexString = "0123456789ABCDEF";
        byte[] bytes;
        try {
            bytes = str.getBytes("GBK");// 如果此处不加编码转化，得到的结果就不是理想的结果，中文转码
            StringBuilder sb = new StringBuilder(bytes.length * 2);

            for (int i = 0; i < bytes.length; i++) {
                sb.append(hexString.charAt((bytes[i] & 0xf0) >> 4));
                sb.append(hexString.charAt((bytes[i] & 0x0f) >> 0));
                // sb.append("");
            }

            result = sb.toString();
            System.out.println(result);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    public void setText(String text) {
        this.image = null;
        if (!TextUtils.isEmpty(text) && text.length() > 2) {
            this.text = text.substring(text.length() - 2, text.length());
        } else if (!TextUtils.isEmpty(text)) {
            this.text = text;
        }
        invalidate();
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        this.image = bm;
        this.text = "";
        invalidate();
    }

    @Override
    public void setImageResource(int resId) {
        this.image = BitmapFactory.decodeResource(getResources(), resId);
        this.text = "";
        invalidate();
    }

    @Override
    public void setBackgroundResource(int resid) {
        setImageResource(resid);
    }

    private void initBitMap() {
        // TODO Auto-generated method stub
        btnScale = bitmapWidth / image.getWidth();
        imgMatrix.reset();
        imgMatrix.setScale(btnScale, btnScale);
        imgMatrix.postTranslate((mWidth - image.getWidth() * btnScale) / 2f,
                (mHeight - image.getHeight() * btnScale) / 2f);
        btnRect = new RectF(0, 0, image.getWidth(), image.getHeight());
        imgMatrix.mapRect(btnRect);
        btnPath = new Path();
        btnPath.arcTo(btnRect, 0, 359);
        btnPath.close();
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
//        setVisibility(GONE);
        // Initialize paint objects
        density = getResources().getDisplayMetrics().density;
        imgMatrix = new Matrix();
        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint.setColor(Color.TRANSPARENT);
        circlePaint.setStyle(Paint.Style.FILL);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextAlign(Paint.Align.CENTER);

        selectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        selectPaint.setColor(Color.parseColor("#20000000"));

        paintBorder = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintBorder.setStyle(Paint.Style.STROKE);

        arcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setColor(Color.WHITE);

        messageCountPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        messageCountPaint.setTypeface(Typeface.DEFAULT_BOLD);
        messageCountPaint.setTextAlign(Paint.Align.CENTER);

        TypedArray attributes = context.obtainStyledAttributes(attrs,
                R.styleable.CircularImageView, defStyle, 0);
        borderWidth = attributes.getDimensionPixelOffset(
                R.styleable.CircularImageView_border_width, 0);
        borderColor = attributes.getColor(
                R.styleable.CircularImageView_border_color, 0);
        paintBorder.setColor(borderColor);
        paintBorder.setStrokeWidth(borderWidth);
        setClickable(false);
    }

    public void addShadow() {
        setLayerType(LAYER_TYPE_SOFTWARE, paintBorder);
        paintBorder.setShadowLayer(4.0f, 0.0f, 2.0f,
                Color.parseColor("#FFFFFF"));
    }


    boolean isLongClick = false;


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isClickable()) {
            return false;
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isSelected = true;
                isLongClick = false;
                handler.sendEmptyMessageDelayed(LONG_CLICK_HAND, 500);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                if (!isLongClick) {
                    performClick();
                }
            case MotionEvent.ACTION_CANCEL:
                isSelected = false;
                handler.removeMessages(LONG_CLICK_HAND);
                invalidate();
                break;
        }
        return true;
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case LONG_CLICK_HAND:
                    isLongClick = true;
                    performLongClick();
                    break;
            }
        }
    };

    @Override
    public void invalidate() {
        setVisibility(VISIBLE);
        super.invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        float center = mWidth / 2f;
        if (null != image) {
            initBitMap();
            canvas.save();
            canvas.clipPath(btnPath);
            canvas.drawBitmap(image, imgMatrix, null);
            canvas.restore();
        } else if (!TextUtils.isEmpty(text) || (null != userNames && userNames.size() == 1)) {
            textPaint.setTextSize(textHeight);
            canvas.drawCircle(center, mHeight / 2, center, circlePaint);
            canvas.drawText(text, center, (mHeight + textHeight) / 2f - mHeight
                    / 20f, textPaint);
        } else if (null != userNames && userNames.size() > 1) {
            float startAngle = -270;
            float angle = 360 / userNames.size();
            float textSize = mWidth / 0.897f / (userNames.size() > 4 ? userNames.size() : 4);
            textPaint.setTextSize(textSize);
            for (int i = 0; i < userNames.size(); i++) {

                if (i > 3) break;
                String s = EaseUserUtils.getNick(userNames.get(i));
                if (!TextUtils.isEmpty(s) && s.length() > 1)
                    s = s.substring(s.length() - 1, s.length());
                int color = userName2Color(userNames.get(i));
                arcPaint.setColor(color);
                startAngle += angle;
                float sweepAngle = angle;

                canvas.drawArc(rectArc, startAngle, sweepAngle, true, arcPaint);

                double v = Math.toRadians((startAngle * 2 + sweepAngle) / 2f);
                float textX = (float) (Math.cos(v) *
                        textCenterDistance) + mWidth / 2;
                float textY = (float) (Math.sin(v) *
                        textCenterDistance) + mHeight / 2 + textSize / 3;
                canvas.drawText(s, textX, textY, textPaint);


                double linev = Math.toRadians(startAngle);
                float lineX = (float) (Math.cos(linev) *
                        mWidth / 2) + mWidth / 2;
                float lineY = (float) (Math.sin(linev) *
                        mWidth / 2) + mHeight / 2;

                canvas.drawLine(mWidth / 2, mHeight / 2, lineX, lineY, linePaint);
            }
            double linev = Math.toRadians(startAngle + angle);
            float lineX = (float) (Math.cos(linev) *
                    mWidth / 2) + mWidth / 2;
            float lineY = (float) (Math.sin(linev) *
                    mWidth / 2) + mHeight / 2;
            canvas.drawLine(mWidth / 2, mHeight / 2, lineX, lineY, linePaint);
        }
        if (0 != borderWidth) {
            canvas.drawCircle(center, center, center - borderWidth / 2, paintBorder);
        }

        if (!TextUtils.isEmpty(unReadMessageCount)) {
            messageCountPaint.setColor(Color.parseColor("#ff0000"));
            float r = messageCountH / 2;
            float messageY = r;
            float messageX = mWidth - r * 1.1f;
            canvas.drawCircle(messageX, messageY, messageY, messageCountPaint);
            messageCountPaint.setColor(Color.WHITE);
            canvas.drawText(unReadMessageCount + "", messageX,
                    messageY + messageCountPaint.getTextSize() / 3f, messageCountPaint);
        }

        if (isSelected) {
            canvas.drawCircle(center, mHeight / 2, center, selectPaint);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        // TODO Auto-generated method stub
        super.onSizeChanged(w, h, oldw, oldh);
        mHeight = h;
        mWidth = w;
        bitmapWidth = mWidth;
        textHeight = mHeight / 3f;
        textPaint.setTextSize(textHeight);

        linePaint.setStrokeWidth(mWidth / 36f);
        rectArc = new RectF(0, 0, mWidth, mHeight);
        textCenterDistance = mWidth / 4f;

        messageCountH = mHeight / 3;
        messageCountPaint.setTextSize(messageCountH / 2f);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getDefaultSize(0, widthMeasureSpec),
                getDefaultSize(widthMeasureSpec, 0));
        int childHeightSize = getMeasuredHeight();
        int childWidthSize = childHeightSize;
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidthSize,
                MeasureSpec.EXACTLY);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(childHeightSize,
                MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public String getText() {
        return text;
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        BitmapDrawable bd = (BitmapDrawable) drawable;
        setImageBitmap(bd.getBitmap());
    }
//
//    public void setText(String[] texts) {
//        this.text = "";
//        this.image = null;
//        this.texts = texts;
//        for (int i = 0; i < texts.length; i++) {
//            String s = texts[i];
//            if (s.length() > 1)
//                texts[i] = s.substring(0, 1);
//        }
//        invalidate();
//    }


    public void setText(List<String> userNames) {
        this.text = "";
        this.image = null;
        this.userNames = userNames;
        if (userNames.size() != 1) {
            for (int i = userNames.size() - 1; i >= 0; i--) {
                if (userNames.size() <= 4) break;
                userNames.remove(i);
            }
        } else {
            setText(EaseUserUtils.getNick(userNames.get(0)));
            setBackgroundColor(userNames.get(0));
        }
        invalidate();
    }

    public void setUnreadMsgCount(int unReadMessageCount) {
        if (unReadMessageCount >= 99) {
            this.unReadMessageCount = 99 + "+";
        } else if (0 == unReadMessageCount) {
            this.unReadMessageCount = "";
        } else
            this.unReadMessageCount = unReadMessageCount + "";

        invalidate();
    }
}