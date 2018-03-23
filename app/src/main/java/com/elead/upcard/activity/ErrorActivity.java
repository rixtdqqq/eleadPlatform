package com.elead.upcard.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.elead.activity.BaseActivity;
import com.elead.application.MyApplication;
import com.elead.eplatform.R;
import com.elead.service.EPlamformServices;
import com.elead.upcard.entity.UpCardErrorEntity;
import com.elead.upcard.view.SignErrorView;
import com.elead.upcard.view.SignTbSelector;
import com.elead.utils.EloadingDialog;
import com.elead.utils.HttpUrlConnectionUtil;
import com.elead.utils.ToastUtil;
import com.elead.utils.volley.VHttpUrlConnectionUtil;
import com.elead.viewpagerutils.VViewPagerAdapter;
import com.elead.views.JazzyViewPager;
import com.elead.views.pulltorefresh.PullToRefreshListView;
import com.elead.views.pulltorefresh.PullToRefreshView;
import com.elead.views.baseadapter.CommonAdapter;
import com.elead.views.baseadapter.ViewHolder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/12/28 0028.
 */

public class ErrorActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    @BindView(R.id.tb_selector)
    SignTbSelector tbSelector;
    @BindView(R.id.viewpager)
    JazzyViewPager viewpager;
    private VViewPagerAdapter adapter;
    private List<View> views;
    private PullToRefreshListView leftList, rightList;
    private CommonAdapter<UpCardErrorEntity> errorUnhandledEntityAdapter;
    private CommonAdapter<UpCardErrorEntity> errorHandledEntityAdapter;
    private List<UpCardErrorEntity> errorUnhandledEntityList = new ArrayList<>();
    private List<UpCardErrorEntity> errorHandledEntityList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error);
        setTitle(getResources().getString(R.string.error), BaseActivity.TitleType.NORMAL);
        ButterKnife.bind(this);
        initView();
        requestErrorData(REQUEST_CODE_UNHANDLED);
    }

    private static final int REQUEST_CODE_UNHANDLED = 0;//未处理异常
    private static final int REQUEST_CODE_HANDLED = 1;//已处理异常

    /**
     * create by mujun.xu at 2017/1/10
     *
     * @param code 0 is unhandled, 1 is handled
     */
    private void requestErrorData(final int code) {
        EloadingDialog.ShowDialog(this);
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("work_no", MyApplication.user.work_no);
        String url = EPlamformServices.get_error_punch_the_card_unhandled_service;
        if (code == REQUEST_CODE_HANDLED) {
            url = EPlamformServices.get_error_punch_the_card_handled_service;
        }
        VHttpUrlConnectionUtil.postRequest(url, parameters,
                new HttpUrlConnectionUtil.onConnectionFinishLinstener() {

                    @Override
                    public void onSuccess(String url, String result) {
                        EloadingDialog.cancle();
                        int ret_code = 0;
                        String ret_message;
                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            if (jsonObject.has("ret_code")) {
                                ret_code = jsonObject.getInt("ret_code");
                            }
                            if (jsonObject.has("ret_message")) {
                                ret_message = jsonObject.getString("ret_message");
                            }
                            if (ret_code == 1) {
                                if (code == REQUEST_CODE_UNHANDLED) { //未处理异常
                                    List<UpCardErrorEntity> list = JSON.parseArray(jsonObject.getJSONArray("datas").toString(), UpCardErrorEntity.class);
                                    if (list.size() > 0) {
                                        errorUnhandledEntityList.clear();
                                        errorUnhandledEntityList.addAll(list);
                                    }
                                    errorUnhandledEntityAdapter.notifyDataSetChanged();
                                    leftList.onRefreshComplete();
                                    Log.d("error0size", errorUnhandledEntityList.size() + "");
                                } else if (code == REQUEST_CODE_HANDLED) { //已处理异常
                                    List<UpCardErrorEntity> list = JSON.parseArray(jsonObject.getJSONArray("datas").toString(), UpCardErrorEntity.class);
                                    if (list.size() > 0) {
                                        errorHandledEntityList.clear();
                                        errorHandledEntityList.addAll(list);
                                    }
                                    errorHandledEntityAdapter.notifyDataSetChanged();
                                    rightList.onRefreshComplete();
                                    Log.d("error1size", errorHandledEntityList.size() + "");
                                }
                            } else if (ret_code == 0) {
                                //暂无数据
                                if (code == REQUEST_CODE_UNHANDLED) {
                                    leftList.onRefreshComplete();
                                    leftList.addEmptyView();
                                } else if (code == REQUEST_CODE_HANDLED) {
                                    rightList.onRefreshComplete();
                                    rightList.addEmptyView();
                                }


                            } else {
                                //暂无数据
                                if (code == REQUEST_CODE_UNHANDLED) {
                                    leftList.onRefreshComplete();
                                    leftList.addEmptyView();
                                } else if (code == REQUEST_CODE_HANDLED) {
                                    rightList.onRefreshComplete();
                                    rightList.addEmptyView();
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            if (code == REQUEST_CODE_UNHANDLED) {
                                leftList.onRefreshComplete();
                                leftList.addErrorView();
                            } else if (code == REQUEST_CODE_HANDLED) {
                                rightList.onRefreshComplete();
                                rightList.addErrorView();
                            }
                        }
                    }

                    @Override
                    public void onFail(String url) {
                        //提示失败
                        ToastUtil.showToast(ErrorActivity.this, getResources().getString(R.string.request_fail), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private View visibView;
    private SignErrorView lastErrView;
    private UpCardErrorEntity lastInfo;

    private void inItJazzView() {
        views = new ArrayList<>();
        leftList = (PullToRefreshListView) LayoutInflater.from(this).inflate(R.layout.listview, null);
        errorUnhandledEntityAdapter = new CommonAdapter<UpCardErrorEntity>(this, errorUnhandledEntityList, R.layout.err_list_item) {
            @Override
            public void convert(ViewHolder helper, final UpCardErrorEntity item) {
                final SignErrorView view = helper.getView(R.id.sign_err_view);
                final LinearLayout rdg_err_select = helper.getView(R.id.rdg_err_select);
                if (item.isSelect) {
                    rdg_err_select.setVisibility(View.VISIBLE);

                } else {
                    rdg_err_select.setVisibility(View.GONE);
                }

                View.OnClickListener onclickLinstener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (v.getId()) {
                            case R.id.rad_buqian:
                                Intent RetroactiveIntent = new Intent(ErrorActivity.this, RetroactiveCardActivity.class);
                                startActivity(RetroactiveIntent);
                                // Toast.makeText(ErrorActivity.this,"vvvvvv1",Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.rad_qingjia:
                                Intent LeaveIntent = new Intent(ErrorActivity.this, LeaveActivity.class);
                                startActivity(LeaveIntent);
                                // Toast.makeText(ErrorActivity.this,"vvvvvv2",Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.rad_waichu:
                                Intent GoOutBusinessIntent = new Intent(ErrorActivity.this, GoOutBusinessActivity.class);
                                startActivity(GoOutBusinessIntent);
                                // Toast.makeText(ErrorActivity.this,"vvvvvv3",Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                };
                helper.getView(R.id.rad_buqian).setOnClickListener(onclickLinstener);
                helper.getView(R.id.rad_qingjia).setOnClickListener(onclickLinstener);
                helper.getView(R.id.rad_waichu).setOnClickListener(onclickLinstener);
                view.setUpCardErrorEntity(item);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (view == lastErrView) return;
                        item.isSelect = true;
                        view.setUpCardErrorEntity(item);
                        if (null != lastErrView && null != lastInfo) {
                            lastInfo.isSelect = false;
                            lastErrView.setUpCardErrorEntity(lastInfo);
                        }
                        lastErrView = view;
                        lastInfo = item;

                        if (null != visibView) {
                            visibView.setVisibility(View.GONE);
                        }
                        rdg_err_select.setVisibility(View.VISIBLE);
                        visibView = rdg_err_select;
                        leftList.requestLayout();
                    }
                });
            }
        };
        leftList.setAdapter(errorUnhandledEntityAdapter);
        leftList.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestErrorData(0);
            }
        });
        leftList.setSpkey(getClass().getSimpleName() + "leftList");


        rightList = (PullToRefreshListView) LayoutInflater.from(this).inflate(R.layout.listview, null);
        errorHandledEntityAdapter = new CommonAdapter<UpCardErrorEntity>(this, errorHandledEntityList, R.layout.err_list_item) {
            @Override
            public void convert(ViewHolder helper, UpCardErrorEntity item) {
                final SignErrorView view = helper.getView(R.id.sign_err_view);
                item.isSelect = true;
                view.setUpCardErrorEntity(item);
            }
        };
        rightList.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestErrorData(1);
            }
        });
        rightList.setSpkey(getClass().getSimpleName() + "rightList");
        rightList.setAdapter(errorHandledEntityAdapter);


        views.add(leftList);
        views.add(rightList);


//        viewpager.setEnabled(false);
        viewpager.setTransitionEffect(JazzyViewPager.TransitionEffect.CubeOut);
        adapter = new VViewPagerAdapter(views);
        viewpager.setAdapter(adapter);
        viewpager.setOffscreenPageLimit(0);
        viewpager.addOnPageChangeListener(this);
        viewpager.setPageMargin(0);
        viewpager.setCurrentItem(0, false);
    }


    private void initView() {
        inItJazzView();
        tbSelector.setDate("未处理", "已处理");
        tbSelector.setOnTbSelectLinstener(new SignTbSelector.onTbSelectLinstener() {
            @Override
            public void onSelector(int position) {
                viewpager.setCurrentItem(position, true);
            }
        });
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        tbSelector.setOffest(position, positionOffset);
    }

    @Override
    public void onPageSelected(int position) {
        tbSelector.setCurrItem(position);
        requestErrorData(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

}
