package com.elead.views;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.elead.eplatform.R;
import com.elead.module.TbSelecterInfo;
import com.elead.utils.ScreenUtil;

import java.util.ArrayList;
import java.util.List;


public class TbSelector extends HorizontalScrollView {

    private Context mContext;
    private onTbSelectListener titleSelectListener;
    private LinearLayout myRadioGroup;
    private int _id = 1000;
    private LinearLayout titleLayout;
    private boolean isCallBack;

    public TbSelector(Context context) {
        this(context, null);
    }

    public TbSelector(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TbSelector(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inIt(context);
        titleList.add(new TbSelecterInfo("Home", Color.BLACK, Color.BLACK, R.drawable.home, R.drawable.home_pressed));
    }


    private void inIt(Context context) {
        this.mContext = context;
    }

    private List<TbSelecterInfo> titleList = new ArrayList<TbSelecterInfo>();

    public void inItTabS(List<TbSelecterInfo> titleList) {
        this.titleList.clear();
        this.titleList.addAll(titleList);
        initGroup();
    }


    private void initGroup() {
        LinearLayout.LayoutParams textparams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        LinearLayout.LayoutParams imgParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        imgParams.weight = 2;
        textparams.weight = 1;
        titleLayout = new LinearLayout(mContext);
        titleLayout.setLayoutParams(imgParams);
        titleLayout.setOrientation(LinearLayout.VERTICAL);
        titleLayout.setGravity(Gravity.CENTER_VERTICAL);
        myRadioGroup = new LinearLayout(mContext);
        myRadioGroup.setLayoutParams(imgParams);

        // mButton = new Button(mContext);
        // mButton.setClickable(false);
        // mButton.setBackgroundResource(R.drawable.idea2_list_bg);

        // titleLayout.addContentView(mButton);

        myRadioGroup.setOrientation(LinearLayout.HORIZONTAL);
        titleLayout.addView(myRadioGroup);
        addView(titleLayout);
        int len = titleList.size();
        for (int i = 0; i < len; i++) {
            final CusRadioButton radio = new CusRadioButton(mContext);
            TbSelecterInfo info = titleList.get(i);
            radio.inItDrawables(info);
            // radio.setButtonDrawable(android.R.color.transparent);
            LinearLayout.LayoutParams l = new LinearLayout.LayoutParams(
                    ScreenUtil.getScreenDisplay((Activity) getContext())[0]
                            / titleList.size(), LayoutParams.MATCH_PARENT,
                    Gravity.CENTER);
            l.weight = 1;
            radio.setLayoutParams(l);
            radio.setPadding(20, 15, 20, 15);
            radio.setId(i);
            radio.setTag(radio);
            radio.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int i = 0; i < myRadioGroup.getChildCount(); i++) {
                        CusRadioButton cusRadioButton = (CusRadioButton) myRadioGroup.getChildAt(i);
                        cusRadioButton.setChecked(false);
                    }
                    radio.setUnReadMessageCount(0);
                    radio.setChecked(true);
                    titleSelectListener.onSelectChange(radio.getId());
                }
            });
            if (i == 0) {
                radio.setChecked(true);
                // mButton.setLayoutParams(new LinearLayout.LayoutParams(
                // LayoutParams.MATCH_PARENT,
                // LayoutParams.MATCH_PARENT));
            }
            myRadioGroup.addView(radio);
        }
//        myRadioGroup
//                .setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//
//                    @Override
//                    public void onCheckedChanged(RadioGroup group, int checkedId) {
//                        // Map<String, Object> map = (Map<String, Object>)
//                        // group.getChildAt(checkedId).getTag();
//                        int radioButtonId = group.getCheckedRadioButtonId();
//                        // 根据ID获取RadioButton的实例
//                        CusRadioButton rb = (CusRadioButton) findViewById(radioButtonId);
//                        AnimationSet animationSet = new AnimationSet(true);
//                        TranslateAnimation translateAnimation;
//                        translateAnimation = new TranslateAnimation(
//                                mCurrentCheckedRadioLeft, rb.getLeft(), 0f, 0f);
//                        animationSet.addAnimation(translateAnimation);
//                        animationSet.setFillBefore(true);
//                        animationSet.setFillAfter(true);
//                        animationSet.setDuration(300);
//
//                        // mButton.startAnimation(animationSet);//
//                        // 开始上面蓝色横条图片的动画切换
//                        if (null != titleSelectListener) {
//                            titleSelectListener.onSelectChange(radioButtonId - _id);
//                        }
//                        mCurrentCheckedRadioLeft = rb.getLeft();// 更新当前蓝色横条距离左边的距离
//                        smoothScrollTo((int) mCurrentCheckedRadioLeft - 100, 0);
//
//                        // mButton.setLayoutParams(new
//                        // LinearLayout.LayoutParams(
//                        // LayoutParams.MATCH_PARENT,
//                        // LayoutParams.MATCH_PARENT));
//
//                    }
//                });

    }

    public void setIsCallBack(boolean isCallBack) {
        this.isCallBack = isCallBack;
    }

    public void setCurrItem(int currItem) {
        for (int i = 0; i < myRadioGroup.getChildCount(); i++) {
            CusRadioButton cusRadioButton = (CusRadioButton) myRadioGroup.getChildAt(i);
            cusRadioButton.setChecked(false);
        }
        CusRadioButton childAt = (CusRadioButton) myRadioGroup.getChildAt(currItem);
        childAt.setChecked(true);
    }

    public void setMessageTip(int index, int count) {
        CusRadioButton childAt = (CusRadioButton) myRadioGroup.getChildAt(index);
        childAt.setUnReadMessageCount(count);
    }

    public int getCurrItem() {
        int index = -1;
        for (int i = 0; i < myRadioGroup.getChildCount(); i++) {
            CusRadioButton cusRadioButton = (CusRadioButton) myRadioGroup.getChildAt(i);
            if (cusRadioButton.isChecked()) {
                index = i;
            }
        }
        return index;
    }

    public interface onTbSelectListener {
        void onSelectChange(int position);
    }

    public onTbSelectListener getTitleSelectListener() {
        return titleSelectListener;
    }

    public void setOnTbSelectListener(onTbSelectListener titleSelectListener) {
        this.titleSelectListener = titleSelectListener;
    }

    public LinearLayout getMyRadioGroup() {
        return myRadioGroup;
    }

    public void setMyRadioGroup(RadioGroup myRadioGroup) {
        this.myRadioGroup = myRadioGroup;
    }

    public RadioButton getChildAtPosition(int i) {
        return (RadioButton) myRadioGroup.getChildAt(i);
    }

//    public void setCurrItem(int position) {
//        int len = myRadioGroup.getChildCount();
//        for (int i = 0; i < len; i++) {
//            CusRadioButton childAt = (CusRadioButton) myRadioGroup.getChildAt(i);
//            if (childAt.isChecked()) {
//                childAt.setChecked(false);
//                break;
//            }
//        }
//        CusRadioButton csRadioButton = (CusRadioButton) myRadioGroup.getChildAt(position);
//        csRadioButton.setAlpha(0.1f);
//        csRadioButton.animate().alpha(1f).setDuration(300).start();
//        csRadioButton.setChecked(true);
//    }

}
