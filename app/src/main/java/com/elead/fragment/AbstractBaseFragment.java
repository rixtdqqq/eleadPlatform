package com.elead.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import java.lang.reflect.Field;

public abstract class AbstractBaseFragment extends Fragment {
    public Activity activity;

//	@Override
//	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		super.onActivityCreated(savedInstanceState);
//		this.activity = getActivity();
//		// getActivity().getWindow().setBackgroundDrawable(new
//		// ColorDrawable(Color.WHITE));
//
//	}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.activity = getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        try {
            Field childFragmentManager = Fragment.class
                    .getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);

        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Fragment当前状态是否可见
     */
    protected boolean isVisible;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (getUserVisibleHint()) {
            isVisible = true;
            handler.sendEmptyMessage(100);
        } else {
            isVisible = false;
            handler.sendEmptyMessage(200);

        }
    }

    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 100:
                    onVisible();
                    break;
                case 200:
                    onInvisible();
                    break;

                default:
                    break;
            }
        }

        ;
    };

    /**
     * 可见
     */
    protected void onVisible() {
        lazyLoad();
    }

    /**
     * 不可见
     */
    protected void onInvisible() {

    }

    /**
     * 延迟加载 子类必须重写此方法
     */
    protected abstract void lazyLoad();

    public interface BackHandledInterface {
        public abstract void setSelectedFragment(
                BaseBackPressFragment selectedFragment);
    }
}
