package com.elead.qs;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.elead.activity.BaseActivity;
import com.elead.application.MyApplication;
import com.elead.eplatform.R;
import com.elead.operate.activity.OperatTabActivity;
import com.elead.qs.views.ProjectGeneralList;
import com.elead.utils.DensityUtil;
import com.elead.views.baseadapter.CommonAdapter;
import com.elead.views.baseadapter.ViewHolder;
import com.gly.calendar.view.CalendarCard;
import com.gly.calendar.view.PopCalendar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.finalteam.mygallery.widget.HorizontalListView;

import static com.gly.calendar.CalendarMainView.getTopText;

/**
 * Created by Administrator on 2017/2/9 0009.
 */
public class QSMainActivity extends BaseActivity {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.imb_right)
    ImageView imbRight;
    @BindView(R.id.imb_predaybtn)
    Button imbPredaybtn;
    @BindView(R.id.top_centor_tv)
    TextView topCentorTv;
    @BindView(R.id.imb_nextdaybtn)
    Button imbNextdaybtn;
    @BindView(R.id.horizontallistview)
    HorizontalListView horizontallistview;
    @BindView(R.id.load_more_list_iv)
    ProjectGeneralList loadMoreListIv;
    @BindView(R.id.layout_asdsad)
    RelativeLayout layoutAsdsad;
    @BindView(R.id.qs_main_layout)
    LinearLayout qsMainLayout;
    private PopCalendar instance;
    private List<QsProject> list;
    private CommonAdapter<QsProject> commonAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_qs_main);
        ButterKnife.bind(this);
        list = new ArrayList<>();
        list.add(new QsProject("86.7", "质量评分", "6", 0));
        list.add(new QsProject("80", "SAI值", "9", 0));
        list.add(new QsProject("20%", "事件量", "5", 0));
        list.add(new QsProject("24", "缺陷个数", "3", 0));
        initView();
    }

    private void initView() {
        topCentorTv.setText("今日(" + CalendarCard.checkedStartDay + ")");
        commonAdapter = new CommonAdapter<QsProject>(this, list, R.layout.qsproject_list_item) {

            @Override
            public void convert(ViewHolder helper, QsProject item) {
                helper.setText(R.id.tv_bottom, item.getText());
                String num = item.getNum();
                if (num.contains("%")) {
                    Spannable spannable = new SpannableString(num);
                    spannable.setSpan(new AbsoluteSizeSpan(DensityUtil.dip2px(mContext, 14)), num.length() - 1, num.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    TextView view = helper.getView(R.id.tv_content);
                    view.setText(spannable);
                } else
                    helper.setText(R.id.tv_content, num);
                SanjiaoTextView sanjiaotextview = helper.getView(R.id.sanjiao_tip);
                sanjiaotextview.setText(item.getTip());
                LinearLayout view = helper.getView(R.id.sadad);
                view.getLayoutParams().width = (int) (MyApplication.size[0] / 1080f * 267f);
                int i = (int) (MyApplication.size[0] / 1080f * 22);
                view.setPadding(i, 0, i, 0);
            }
        };
        horizontallistview.getLayoutParams().height = (int) (MyApplication.size[0] / 1080f * 320f);
        horizontallistview.setAdapter(commonAdapter);
        int v = (int) (MyApplication.size[0] / 1080f * 5f);
        horizontallistview.setPadding(v, 0, v, 0);


        loadMoreListIv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mContext, OperatTabActivity.class);
                startActivity(intent);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_centor_tv:
                if (instance == null) {
                    instance = new PopCalendar(mContext, v,
                            new PopCalendar.onPopCancleLinstener() {

                                @Override
                                public void onCalendarCheck(
                                        String currShooseTime) {
                                    if (null == currShooseTime) {
                                        topCentorTv.setText(getTopText());
                                    } else {
                                        topCentorTv.setText(currShooseTime);
                                    }
                                }

                                @Override
                                public void onPopuCancle() {
                                    instance.dismiss();
                                    instance = null;
                                }
                            });
                    instance.show();
                } else {
                    instance.dismiss();
                    instance = null;
                }
                break;
            case R.id.imb_predaybtn:
                if (null != instance && null != instance.linCalendar
                        && null != instance.linCalendar.mCalendarCard) {
                    instance.linCalendar.mCalendarCard.preXXXAndUpdate();
                } else {
                    CalendarCard.preXXX();
                }
                topCentorTv.setText(getTopText());
                break;
            case R.id.imb_nextdaybtn:
                if (null != instance && null != instance.linCalendar
                        && null != instance.linCalendar.mCalendarCard) {
                    instance.linCalendar.mCalendarCard.nextXXXAndUpdate();
                } else {
                    CalendarCard.nextXXX();
                }
                topCentorTv.setText(getTopText());
                break;
            case R.id.qs_back:
                onBackPressed();
                break;
            case R.id.imb_delete:
                AlphaAnimation animation = new AlphaAnimation(1f, 0f);
                animation.setFillAfter(true);
                animation.setDuration(500);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        qsMainLayout.removeView(layoutAsdsad);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                layoutAsdsad.startAnimation(animation);
                break;
        }

    }
}
