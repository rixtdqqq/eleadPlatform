package com.elead.approval.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.elead.approval.Interfaces.ITextItem;
import com.elead.eplatform.R;

import static com.elead.eplatform.R.id.tv_xingcheng_tip;

/**
 * Created by Administrator on 2016/9/24 0024.
 */

public class XingChengItem extends LinearLayout implements ITextItem  {
    private TextView tvxingchengtip;
    private Button btn_delete;
    private EdiTextItem ed_chuchaididian;
    private ChooseView ct_start;
    private ChooseView ct_end;

    public XingChengItem(Context context) {
        this(context, null);
    }

    public XingChengItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XingChengItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(getContext()).inflate(R.layout.approval_xingcheng_item, this);
        tvxingchengtip = (TextView) findViewById(tv_xingcheng_tip);
        btn_delete = (Button) findViewById(R.id.btn_delete);
        ed_chuchaididian = (EdiTextItem) findViewById(R.id.ed_chuchaididian);
        ct_start = (ChooseView) findViewById(R.id.ct_start);
        ct_end = (ChooseView) findViewById(R.id.ct_end);
        init();
    }

    public void showDelete() {
        btn_delete.setVisibility(View.VISIBLE);
    }

    public void dismissDelete() {
        btn_delete.setVisibility(View.GONE);
    }

    public void setOnDelClickLinstener(OnClickListener onDelClickLinstener) {
        btn_delete.setOnClickListener(onDelClickLinstener);
    }

    public void init() {
        ed_chuchaididian.init(EdiTextItem.LineType.signLine, "出差地点", "如:北京,上海,杭州(必填)");
        ct_start.inIt(ChooseView.Type.time, "开始时间", 10, 0);
        ct_end.inIt(ChooseView.Type.time, "结束时间", 0, 0);

    }

    @Override
    public String getContent() {
        return null;
    }
    @Override
    public boolean isEmpty() {
        return ed_chuchaididian.isEmpty() || ct_start.isEmpty() || ct_end.isEmpty() ? false : true;
    }

    public String getStartTime() {
        return ct_end.getContent();
    }


    public String getEndTime() {
        return ct_start.getContent();
    }

    public String getChuchaiDidian() {
        return ed_chuchaididian.getContent();
    }

    public void setTipText(String text) {
        tvxingchengtip.setText("行程明细(" + text + ")");
    }

}
