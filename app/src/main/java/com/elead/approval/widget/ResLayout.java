package com.elead.approval.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.elead.eplatform.R;

/**
 * @desc
 * @auth Created by mujun.xu on 2016/9/23 0023.
 */
public class ResLayout extends LinearLayout {
    private Context mContext;
    private TextView tv_lable;
    private TextView tv_deleted;
    private EdiTextItem ed_name;
    private EdiTextItem ed_count;

    public ResLayout(Context context) {
        this(context,null);
    }

    public ResLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ResLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initView();
    }

    private void initView(){
        LayoutInflater.from(mContext).inflate(R.layout.approval_i_res, this);
        tv_lable = (TextView) findViewById(R.id.tv_lable);
        tv_deleted = (TextView) findViewById(R.id.tv_deleted);
        ed_name = (EdiTextItem) findViewById(R.id.ed_name);
        ed_name.init(EdiTextItem.LineType.signLine,
                mContext.getResources().getString(R.string.approval_res_name),
                mContext.getResources().getString(R.string.approval_res_name_hint));
        ed_count = (EdiTextItem) findViewById(R.id.ed_count);
        ed_count.init(EdiTextItem.LineType.signLine,
                mContext.getResources().getString(R.string.approval_res_count),
                mContext.getResources().getString(R.string.approval_res_count_hint));
        tv_deleted.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != callBack) {
                    callBack.removeCallBack(ResLayout.this);
                }
            }
        });
    }

    RemoveCallBack callBack;
    public void setRemoveCallBack(RemoveCallBack callBack){
        this.callBack = callBack;
    }

    public interface RemoveCallBack{
        public void removeCallBack(View view);
}

    public TextView getDeleted(){
        return tv_deleted;
    }

    public void isShowDeleted(boolean isShow){
        if (isShow) {
            tv_deleted.setVisibility(VISIBLE);
        } else {
            tv_deleted.setVisibility(GONE);
        }
    }

    public void setLable(String number){
        if (TextUtils.isEmpty(number))
            number = "0";
        tv_lable.setText(String.format(mContext.getResources().getString(R.string.approval_res_detail), number));
    }

    public String getName(){
        return ed_name.getContent();
    }

    public String getCount(){
        return ed_count.getContent();
    }

}
