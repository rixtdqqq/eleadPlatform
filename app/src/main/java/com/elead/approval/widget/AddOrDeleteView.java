package com.elead.approval.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.elead.approval.Interfaces.ITextItem;
import com.elead.eplatform.R;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/23 0023.
 */

public abstract class AddOrDeleteView extends LinearLayout {
    public Context mContext;
    private String tipName;
    private TextView button;

    private AddOrDeleteView(Context context) {
        super(context);
    }

    public AddOrDeleteView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AddOrDeleteView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public AddOrDeleteView(Context context, String tipName) {
        this(context);
        setLayoutParams(new LayoutParams(-1, -1));
        mContext = context;
        this.tipName = tipName;
        addMingXi();
        init(context);
    }

    private void init(Context context) {
        setGravity(Gravity.CENTER_HORIZONTAL);
        setOrientation(LinearLayout.VERTICAL);
        LinearLayout lin = (LinearLayout)LinearLayout.inflate(context, R.layout.approval_common_add_layout, null);
        button = (TextView)lin.findViewById(R.id.tv_content);
        addView(lin);
        lin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                addMingXi();
            }
        });
    }

    public abstract View[] initItems();

    public void addMingXi() {
        final AddOrDeleteBaseItem item = new AddOrDeleteBaseItem(getContext());
        item.init(initItems());
        int count = getChildCount();
        if (tipName=="") {
            item.setViewGone();
        } else {
            item.setTipText(tipName + "(" + (count == 0 ? 1 : count) + ")");
        }
        item.setOnDelClickLinstener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                removeView(item);
                int childCount = getChildCount();
                for (int i = 0; i < childCount; i++) {
                    if (getChildAt(i) instanceof AddOrDeleteBaseItem) {
                        AddOrDeleteBaseItem childAt = (AddOrDeleteBaseItem) getChildAt(i);
                        if (tipName=="") {
                            item.setViewGone();
                        } else {
                            childAt.setTipText(tipName + "(" + (i + 1) + ")");
                        }
                    }
                }
            }
        });
        if (getChildCount() == 0) {
            item.dismissDelete();
        }
        addView(item, getChildCount() - 1, new LayoutParams(-1, -1));
        requestLayout();
    }


    public boolean isAllEmpty() {
        boolean isEmpty = false;
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            if (getChildAt(i) instanceof ITextItem) {
                ITextItem childAt = (ITextItem) getChildAt(i);
                if (childAt.isEmpty()) {
                    isEmpty = true;
                }
            }
        }
        return isEmpty;
    }


    public List<Map<Integer, String>> getMingXIInfos() {
        List<Map<Integer, String>> contents = new LinkedList<>();
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            if (getChildAt(i) instanceof AddOrDeleteBaseItem) {
                AddOrDeleteBaseItem childAt = (AddOrDeleteBaseItem) getChildAt(i);
                contents.add(childAt.getItemInfo());
            }
        }
        return contents;
    }
}
