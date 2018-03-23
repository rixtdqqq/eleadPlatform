package com.elead.utils;

/**
 * Created by lm806 on 2016/12/15.
 */

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.elead.eplatform.R;


@SuppressLint("Recycle")
public class AnimFragmentUtil {
    private FragmentManager fragmentManager;
    private int layoutId;
    private String currfragment = "";

    public enum AnimTYPE {
        left, top, right, bottom, noanim;
    }

    public AnimFragmentUtil(FragmentManager fragmentManager, int layoutId) {
        super();
        this.fragmentManager = fragmentManager;
        this.layoutId = layoutId;
    }

    public void selectFragment(Fragment fragment, AnimTYPE inAnimType,
                               AnimTYPE outAnimType) {
        // 开启一个Fragment事务
        // hideFragments();
        String fraName = fragment.getClass().getSimpleName();
        // if (!currfragment.equals(fraName)) {
        fragmentManager.popBackStackImmediate();
        addFragment(fragment, inAnimType, outAnimType);
        currfragment = fraName;
        // }
    }

    /**
     * 将所有的Fragment都置为隐藏状态。
     * <p>
     * 用于对Fragment执行操作的事务
     */
    // private void hideFragments() {
    // FragmentTransaction transaction = fragmentManager.beginTransaction();
    // for (Fragment fragment : fragments.values()) {
    // transaction.hide(fragment);
    // System.out.println(fragment.getClass().getSimpleName());
    // }
    // transaction.commitAllowingStateLoss();
    // }
    public Fragment addFragment(Fragment fragment, AnimTYPE inAnimType,
                                AnimTYPE outAnimType) {
        int inAnimId = android.R.anim.fade_in;
        int outAnimId = android.R.anim.fade_out;

        switch (inAnimType) {
            case left:
                inAnimId = R.anim.fragment_slide_in_from_left;
                break;
            case top:
                inAnimId = R.anim.fragment_slide_in_from_top;
                break;
            case right:
                inAnimId = R.anim.fragment_slide_in_from_right;
                break;
            case bottom:
                inAnimId = R.anim.fragment_slide_in_from_bottom;
                break;
        }

        switch (outAnimType) {
            case left:
                outAnimId = R.anim.fragment_slide_out_to_left;
                break;
            case top:
                outAnimId = R.anim.fragment_slide_out_to_top;
                break;
            case right:
                outAnimId = R.anim.fragment_slide_out_to_right;
                break;
            case bottom:
                outAnimId = R.anim.fragment_slide_out_to_bottom;
                break;
        }

        return addFragment(fragment, inAnimId, outAnimId, inAnimId, outAnimId);
    }

    /**
     * add the fragment
     *
     * @param arg0
     * @param arg1
     * @param arg2
     * @param arg3
     */

    private Fragment addFragment(Fragment fragment, int arg0, int arg1,
                                 int arg2, int arg3) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.setCustomAnimations(arg0, arg1, arg2, arg3);
        // Bundle bundle = new Bundle();
        // bundle.putSerializable("message", message);
        // fragment.setArguments(bundle);
        ft.add(layoutId, fragment);
        ft.addToBackStack(null);
        ft.commitAllowingStateLoss();
        return fragment;
    }

    @SuppressWarnings("unchecked")
    private <T> T getInstance(Class<T> theClass) {
        T o = null;
        try {
            o = (T) Class.forName(theClass.getName()).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return o;
    }

}
