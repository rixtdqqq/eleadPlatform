package com.elead.md.activity;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.elead.activity.BaseActivity;
import com.elead.activity.BaseFragmentActivity;
import com.elead.eplatform.R;
import com.elead.md.fragment.MDDynamicFragment;
import com.elead.md.fragment.MDProjectFragment;
import com.elead.md.fragment.MDResourcesFragment;
import com.elead.md.utils.MDUtils;
import com.elead.module.TbSelecterInfo;
import com.elead.utils.StatusBarUtils;
import com.elead.utils.ToastUtil;
import com.elead.viewpagerutils.FViewPagerAdapter;
import com.elead.views.BaseCommonAdapter;
import com.elead.views.JazzyViewPager;
import com.elead.views.TbSelector;
import com.elead.views.ViewHolder;

import java.util.ArrayList;
import java.util.List;


/**
 * @desc 研发移动化主页
 * Created by mujun.xu at 2017/2/13
 */
public class MDMainActivity extends BaseFragmentActivity implements ViewPager.OnPageChangeListener {

    private Resources resources;
    private JazzyViewPager md_vp_main;
    private TbSelector md_ntb_main;

    private FViewPagerAdapter adapter;
    public String[] footArray;
    private List<Fragment> views;
    private MDProjectFragment projectFragment;
    private MDResourcesFragment resourcesFragment;
    private MDDynamicFragment dynamicFragment;
    private LinearLayout ll_center_title;
    private ImageView img_arrow_down_title;
    private ImageButton img_msg;
    private PopupWindow mTypePopupWindow;
    private List<String> mTypeList = new ArrayList<>();
    private BaseCommonAdapter mTypePopupWindowAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.md_a_main);
        init();
    }

    /**
     * 初始化
     * Created by mujun.xu at 2017/2/13
     */
    private void init(){
        StatusBarUtils.setWindowStatusBarColor(this, getResources().getColor(R.color.qianlv));
        resources = getResources();
        footArray = resources.getStringArray(R.array.md_main_foot);
        setTitle(footArray[0], BaseActivity.TitleType.MD);
        md_vp_main = (JazzyViewPager) findViewById(R.id.md_vp_main);
        md_ntb_main = (TbSelector) findViewById(R.id.md_ntb_main);
        initFragment();
        initSelectBar();
        initJazzView();
        initTypePopup();
    }

    /**
     * 初始化fragment
     * Created by mujun.xu at 2017/2/13
     */
    private void initFragment() {
        views = new ArrayList();
        if (null == projectFragment) {
            projectFragment = new MDProjectFragment();
        }
        views.add(projectFragment);
        if (null == resourcesFragment) {
            resourcesFragment = new MDResourcesFragment();
        }
        views.add(resourcesFragment);
        if (null == dynamicFragment) {
            dynamicFragment = new MDDynamicFragment();
        }
        views.add(dynamicFragment);
    }

    /**
     * 初始化SelectBar
     * Created by mujun.xu at 2017/2/13
     */
    private void initSelectBar() {
        ArrayList<TbSelecterInfo> tbSelecterInfos = new ArrayList<>();
        int defColor = getResources().getColor(R.color.fontcolors4);
        int pressColor = getResources().getColor(R.color.develop_tb_line);
        tbSelecterInfos.add(new TbSelecterInfo(footArray[0], defColor, pressColor, R.drawable.md_project_normal, R.drawable.md_project_pressed));
        tbSelecterInfos.add(new TbSelecterInfo(footArray[1], defColor, pressColor, R.drawable.md_resources_normal, R.drawable.md_resources_pressed));
        tbSelecterInfos.add(new TbSelecterInfo(footArray[2], defColor, pressColor, R.drawable.md_dynamic_normal, R.drawable.md_dynamic_pressed));
        md_ntb_main.inItTabS(tbSelecterInfos);
        md_ntb_main.setOnTbSelectListener(new TbSelector.onTbSelectListener() {
            @Override
            public void onSelectChange(int position) {
                md_vp_main.setCurrentItem(position, false);
            }
        });
    }

    /**
     * 初始化JazzView
     * Created by mujun.xu at 2017/2/13
     */
    private void initJazzView() {
        md_vp_main.setTransitionEffect(JazzyViewPager.TransitionEffect.CubeOut);
        adapter = new FViewPagerAdapter(getSupportFragmentManager(), views);
        md_vp_main.setAdapter(adapter);
        md_vp_main.setOffscreenPageLimit(3);
        md_vp_main.addOnPageChangeListener(this);
        md_vp_main.setPageMargin(30);
        md_vp_main.setCurrentItem(0, false);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    /**
     * 选到tab的处理
     * @param position
     */
    @Override
    public void onPageSelected(int position) {
        md_ntb_main.setCurrItem(position);
        if (position == views.size()-1) {
            setTitle(mTypeList.get(MDUtils.POSITION), BaseActivity.TitleType.MD);
            findTitleView();
            isDynamic(true);
        } else {
            setTitle(footArray[position], BaseActivity.TitleType.MD);
            findTitleView();
            dismissPopup(mTypePopupWindow);
            isDynamic(false);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     * 找到TitleView
     */
    private void findTitleView(){
        ll_center_title = (LinearLayout) getTitleView().getCustomView().findViewById(R.id.ll_center);
        img_arrow_down_title = (ImageView) getTitleView().getCustomView().findViewById(R.id.img_arrow_down);
        img_msg = (ImageButton) getTitleView().getCustomView().findViewById(R.id.msg);
    }

    /**
     * 是否是在动态页面
     */
    private void isDynamic(boolean isDynamic){
        if (isDynamic) {
            img_msg.setVisibility(View.VISIBLE);
            img_arrow_down_title.setVisibility(View.VISIBLE);
            ll_center_title.setEnabled(true);
            ll_center_title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showPopup(mTypePopupWindow);
                }
            });
            img_msg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtil.showToast(mContext, "跳转至Msg", 0);
                }
            });
        } else {
            img_msg.setVisibility(View.GONE);
            img_arrow_down_title.setVisibility(View.GONE);
            ll_center_title.setEnabled(false);
        }
    }

    /**
     * 初始化TypePopup
     */
    private void initTypePopup() {
        mTypeList.clear();
        mTypeList.add(resources.getString(R.string.md_weekly));
        mTypeList.add(resources.getString(R.string.md_risk));
        mTypeList.add(resources.getString(R.string.md_problem));

        mTypePopupWindow = new PopupWindow(mContext);
        View typeView = getLayoutInflater().inflate(R.layout.md_v_dynamic_title_popup, null);
        mTypePopupWindow.setContentView(typeView);
        ListView typeListView = (ListView) typeView.findViewById(R.id.lv_type);
        mTypePopupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        mTypePopupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        mTypePopupWindow.setFocusable(true);
        mTypePopupWindow.setTouchable(true);
        mTypePopupWindow.setOutsideTouchable(true);
        mTypePopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mTypePopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
//                dismissPopup(mTypePopupWindow);
            }
        });
        mTypePopupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                delayDismissPopup(mTypePopupWindow);
                return false;
            }
        });
        mTypePopupWindowAdapter = new BaseCommonAdapter<String>(mContext, mTypeList, R.layout.md_i_dynamic_type) {

            @Override
            public void convert(ViewHolder holder, String type, final int position) {
                holder.setText(R.id.tv_type, type);
            }
        };
        typeListView.setAdapter(mTypePopupWindowAdapter);
        typeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MDUtils.POSITION = i;
                //在这里切换:周报、风险、问题...等界面
                ToastUtil.showToast(mContext,"在这里切换:周报、风险、问题...等界面",0);
                setTitle(mTypeList.get(i), BaseActivity.TitleType.MD);
                findTitleView();
                isDynamic(true);
                dismissPopup(mTypePopupWindow);
            }
        });
    }

    /**
     * 显示popupwindow
     *
     * @param popupWindow
     */
    private void showPopup(PopupWindow popupWindow) {
        if (null != popupWindow) {
            if (popupWindow.isShowing()) {
                popupWindow.dismiss();
            } else {
                popupWindow.showAsDropDown(ll_center_title);
                popupWindow.update();
            }
        }
    }

    /**
     * 延迟关闭popup避免冲突
     * @param pp
     */
    private void delayDismissPopup(final PopupWindow pp) {
        if (null != ll_center_title) {
            ll_center_title.postDelayed(new Runnable() {

                @Override
                public void run() {
                    dismissPopup(pp);
                }
            }, 200);
        }
    }

    /**
     * 关闭popupwindow
     *
     * @param p
     */
    private void dismissPopup(PopupWindow p) {
        if (null != p && p.isShowing()) {
            p.dismiss();
        }
    }
}
