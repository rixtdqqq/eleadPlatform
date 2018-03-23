package com.elead.approval.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.elead.approval.Interfaces.ITextItem;
import com.elead.eplatform.R;
import com.elead.utils.ToastUtil;

import java.util.LinkedHashMap;
import java.util.Map;

import static com.elead.eplatform.R.id.tv_xingcheng_tip;

/**
 * Created by Administrator on 2016/9/24 0024.
 */

public class AddOrDeleteBaseItem extends LinearLayout implements ITextItem {
    private RelativeLayout Mingxing_item_rel;
    private TextView tvxingchengtip;
    private Button btn_delete;
    private LinearLayout itemContiner;

    public AddOrDeleteBaseItem(Context context) {
        this(context, null);
    }

    public AddOrDeleteBaseItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AddOrDeleteBaseItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.approval_add_remove_base_item, this);
        tvxingchengtip = (TextView) findViewById(tv_xingcheng_tip);
        btn_delete = (Button) findViewById(R.id.btn_delete);
        itemContiner = (LinearLayout) findViewById(R.id.item_continer);
        Mingxing_item_rel = (RelativeLayout) findViewById(R.id.Mingxing_item_rel);
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

    public void init(View... views) {
        for (View v : views)
            if (v instanceof ITextItem) {
                itemContiner.addView(v);
            } else {
                ToastUtil.showToast("The View must extends ITextItem!");
            }
    }

    @Override
    public String getContent() {
        return null;
    }

    @Override
    public boolean isEmpty() {
        int childCount = itemContiner.getChildCount();
        boolean isEmpty = false;
        for (int i = 0; i < childCount; i++) {
            boolean empty = ((ITextItem) itemContiner.getChildAt(i)).isEmpty();
            if (empty) {
                isEmpty = true;
                break;
            }
        }
        return isEmpty;
    }

    public Map<Integer, String> getItemInfo() {
        Map<Integer, String> contents = new LinkedHashMap<>();
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            if (getChildAt(i) instanceof ITextItem) {
                ITextItem childAt = (ITextItem) getChildAt(i);
                contents.put(i, childAt.getContent());
            }
        }

        return contents;
    }

    public void setTipText(String text) {
        tvxingchengtip.setText(text);
    }

    public void setViewGone() {
        Mingxing_item_rel.setVisibility(View.GONE);
    }
}
