package com.elead.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.elead.application.MyApplication;
import com.elead.eplatform.R;
import com.elead.utils.AppManager;
import com.elead.utils.ScreenUtil;
import com.elead.utils.StatusBarUtils;
import com.elead.views.ImageTextView;

public class BaseActivity extends Activity {
    public Activity mContext;
    public ActionBar actionBar;

    public static enum TitleType {
        APPROVAL, RDCENTER, COMPANY_GROUP, STATISTICAL, NORMAL, MD
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        mContext = this;
        AppManager.getAppManager().addActivity(this);

        if (MyApplication.size == null) {
            MyApplication.size = ScreenUtil.getScreenVisiable(this);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        MyApplication.showingActivity = this;
        overridePendingTransition(android.R.anim.fade_in,
                android.R.anim.fade_out);
        super.onResume();
    }


    public void setTitleView(View v) {
        actionBar = getActionBar();
        if (null != actionBar) {
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            actionBar.setBackgroundDrawable(null);
            actionBar.removeAllTabs();//
            actionBar.setCustomView(v);
            if (Build.VERSION.SDK_INT >= 21) {
                actionBar.setElevation(0);
            }
        }
    }

    public void setTitleView(int layourid) {
        actionBar = getActionBar();
        if (null == actionBar) {
            return;
        }
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setBackgroundDrawable(null);
        actionBar.removeAllTabs();//
        actionBar.setCustomView(layourid);//自定义ActionBar布局
        if (Build.VERSION.SDK_INT >= 21) {
            actionBar.setElevation(0);
        }
        StatusBarUtils.setWindowStatusBarColor(this, getResources().getColor(R.color.qianlv));
    }

    public ActionBar getTitleView() {
        return actionBar;
    }

    @Override
    public void setTitle(CharSequence title) {
        setTitle(title, R.layout.actionbar);
    }

    public void setTitle(int bgColor, int btnResource, String title, int titleColor) {
        actionBar = getActionBar();
        if (null == actionBar) {
            return;
        }

        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setBackgroundDrawable(null);
        actionBar.removeAllTabs();//
        actionBar.setCustomView(R.layout.actionbar);//自定义ActionBar布局
        TextView viewById = (TextView) actionBar.getCustomView().findViewById(R.id.title);
        View action_base = (View) actionBar.getCustomView().findViewById(R.id.action_base);
        ImageView back = (ImageView) actionBar.getCustomView().findViewById(R.id.img_back);
        back.setImageResource(btnResource);
        action_base.setBackgroundColor(bgColor);
        viewById.setTextColor(titleColor);
        viewById.setText(title);
        actionBar.getCustomView().findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {//监听事件
            @Override
            public void onClick(View arg0) {
                onBackPressed();

            }
        });
        if (Build.VERSION.SDK_INT >= 21) {
            actionBar.setElevation(0);
        }
    }

    public void setTitle(CharSequence title, TitleType titleType, View.OnClickListener onClickListener, Object o) {
        setTitle(title, titleType);
        if (null == actionBar) return;
        View titleView = actionBar.getCustomView();
        ImageTextView viewById = (ImageTextView) titleView.findViewById(R.id.im_img_right);
        if (null == viewById) return;
        if (o instanceof Integer) {
            viewById.setImageResource((Integer) o);
        } else if (o instanceof String) {
            viewById.setText((String) o);
//            viewById.setTranslationX(-DensityUtil.dip2px(mContext,10));
        } else {
            return;
        }
        viewById.setVisibility(View.VISIBLE);
        viewById.setOnClickListener(onClickListener);
    }

    public void setTitle(CharSequence title, TitleType titleType) {
        switch (titleType) {
            case STATISTICAL:
                setTitle(title, R.layout.actionbar_statistical);
                break;
            case RDCENTER:
                setTitle(title, R.layout.actionbar_explore);
                break;
            case NORMAL:
                setTitle(title, R.layout.actionbar_normal);
                StatusBarUtils.setWindowStatusBarColor(this, getResources().getColor(R.color.qianlv));
                break;
            default:
                setTitle(title, R.layout.actionbar);
                break;
        }
    }

    private void setTitle(CharSequence title, int layourid) {
        actionBar = getActionBar();
        if (null == actionBar) {
            return;
        }

        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setBackgroundDrawable(null);
        actionBar.removeAllTabs();//
        actionBar.setCustomView(layourid);//自定义ActionBar布局
        TextView viewById = (TextView) actionBar.getCustomView().findViewById(R.id.title);
        viewById.setText(title);
        actionBar.getCustomView().findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {//监听事件
            @Override
            public void onClick(View arg0) {
                onBackPressed();
            }
        });
        if (Build.VERSION.SDK_INT >= 21) {
            actionBar.setElevation(0);
        }
    }

    public void setTitle(CharSequence title, int layourid, int imgRes) {
        this.setTitle(title, layourid);
        ImageView img_right = (ImageView) actionBar.getCustomView().findViewById(R.id.im_img_right);
        img_right.setVisibility(View.VISIBLE);
        img_right.setImageResource(imgRes);
    }


    public void setOnClick() {
        Log.i("TAGhhh", "setOnClick: ");
    }


}
