package com.elead.views;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elead.application.MyApplication;
import com.elead.utils.ToastUtil;

/**
 * 提供Fragment最基础的扩展
 * onAttach -> onCreate -> onCreateView ->onViewCreated -> onActivityCreated -> onViewStateRestored -> onStart -> onResume
 */
public abstract class BaseFragment extends Fragment {
    protected Activity mContext;
    public View mView;
    /**
     * 标题
     */
    private String myTitle;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
    }

    public View getView(LayoutInflater inflater, int layoutId, ViewGroup container) {
        if (null == mView) {
            mView = inflater.inflate(layoutId, container, false);
        }
        return mView;
    }

    protected void showToast(String string) {
        ToastUtil.showToast(mContext, string, 0).show();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    protected static BaseFragment getInstance(Class<? extends BaseFragment> clz) {
        try {
            return (BaseFragment) clz.newInstance();
        } catch (java.lang.InstantiationException e) {
            Log.e("BaseFragment error", e.toString());
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            Log.e("BaseFragment error", e.toString());
            e.printStackTrace();
        }
        return null;
    }

    public String getMyTitle() {
        return myTitle;
    }

    public void setMyTitle(String myTitle) {
        this.myTitle = myTitle;
    }

}
