package com.elead.activity;

import android.app.ActionBar;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.elead.application.MyApplication;
import com.elead.eplatform.R;
import com.elead.utils.AppManager;
import com.elead.utils.ScreenUtil;
import com.elead.utils.StatusBarUtils;
import com.elead.views.ImageTextView;

public class BaseFragmentActivity extends FragmentActivity {
    public Context mContext;
    public ActionBar actionBar;


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


    public void setTitle(CharSequence title, BaseActivity.TitleType titleType, View.OnClickListener onClickListener, Object o) {
        setTitle(title, titleType);
        View titleView = getTitleView().getCustomView();
        ImageTextView viewById = (ImageTextView) titleView.findViewById(R.id.im_img_right);
        if (null == viewById) return;
        if (o instanceof Integer) {
            viewById.setImageResource((Integer) o);
        } else if (o instanceof String) {
            viewById.setText((String) o);
        } else {
            return;
        }
        viewById.setVisibility(View.VISIBLE);
        viewById.setOnClickListener(onClickListener);
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

    public void setTitle(CharSequence title, BaseActivity.TitleType titleType) {
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
            case MD:
                setTitle(title, R.layout.actionbar_md);
                StatusBarUtils.setWindowStatusBarColor(this, getResources().getColor(R.color.opera_title_bg));
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

    public void setTitleViewBt(String title, String buttonText) {
        actionBar = getActionBar();
        if (null == actionBar) {
            return;
        }

        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setBackgroundDrawable(null);
        actionBar.removeAllTabs();//
        actionBar.setCustomView(R.layout.actionbar_title);//自定义ActionBar布局
        TextView viewById = (TextView) actionBar.getCustomView().findViewById(R.id.title);
        Button viewBtId = (Button) actionBar.getCustomView().findViewById(R.id.bt_action);
        viewById.setText(title);
        viewBtId.setText(buttonText);
        actionBar.getCustomView().setOnClickListener(new View.OnClickListener() {//监听事件
            @Override
            public void onClick(View arg0) {
                switch (arg0.getId()) {
                    case R.id.back:
                        onBackPressed();
                        break;
                    default:
                        break;
                }
            }


        });
        if (Build.VERSION.SDK_INT >= 21) {
            actionBar.setElevation(0);
        }
        viewBtId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOnClick();
            }
        });
    }


    public void setOnClick() {
        Log.i("TAGhhh", "setOnClick: ");
    }

}
