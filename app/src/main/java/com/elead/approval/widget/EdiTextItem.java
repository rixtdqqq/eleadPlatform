package com.elead.approval.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.elead.approval.Interfaces.ITextItem;
import com.elead.eplatform.R;
import com.elead.utils.DensityUtil;

/**
 * Created by Administrator on 2016/9/23 0023.
 */

public class EdiTextItem extends LinearLayout implements ITextItem {
    private EditText ed_right;
    private TextView tv_left;

    @Override
    public String getContent() {
        return ed_right.getText().toString();
    }

    @Override
    public boolean isEmpty() {
        return ed_right.getText().toString().trim().equals("") ? true : false;
    }

    public enum LineType {
        signLine, moreLine
    }

    public LineType type;

    public EdiTextItem(Context context) {
        this(context, null);
    }

    public EdiTextItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EdiTextItem(final Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(getContext()).inflate(R.layout.approval_ed_item, this);
        ed_right = (EditText) findViewById(R.id.ed_right);
        tv_left = (TextView) findViewById(R.id.tv_left);
    }

    public void init(LineType type, String leftText) {
        init(type, leftText, "请输入(必填)");
    }

    public void init(LineType type, String leftText, String rightHint) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
        layoutParams.bottomMargin = DensityUtil.dip2px(getContext(), 0);
        layoutParams.topMargin = DensityUtil.dip2px(getContext(),0);
        setLayoutParams(layoutParams);

        this.type = type;
        if (type == LineType.signLine) {
            ed_right.setGravity(Gravity.CENTER_VERTICAL);
            ed_right.setSingleLine(true);
        } else if (type == LineType.moreLine) {
            tv_left.setGravity(Gravity.TOP);
            ed_right.setGravity(Gravity.TOP);
            ed_right.setMinLines(4);
            ed_right.setHorizontallyScrolling(false);
            ed_right.setSingleLine(false);
        }
        tv_left.setText(leftText);
        ed_right.setHint(rightHint);
    }

    public void init(LineType type, int inPutType, String leftText, String rightHint) {
        init(type, leftText, rightHint);
        ed_right.setInputType(inPutType);
    }

}
