package com.elead.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elead.activity.MainActivity;
import com.elead.adapter.MyListAdapter;
import com.elead.entity.ItemEntity;
import com.elead.eplatform.R;
import com.elead.utils.DateUtils;
import com.elead.views.BaseFragment;
import com.elead.views.pulltorefresh.PullToRefreshListView;
import com.elead.views.pulltorefresh.PullToRefreshView;
import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndContent;
import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndEntry;
import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndFeed;
import com.google.code.rome.android.repackaged.com.sun.syndication.io.SyndFeedInput;
import com.google.code.rome.android.repackaged.com.sun.syndication.io.XmlReader;

import java.lang.ref.WeakReference;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;


/**
 * Created by xieshibin on 2016/12/9 .
 */
public class HomeRssFragment extends BaseFragment {
    //    https://www.huxiu.com/rss/0.xml
    String[] rss = {"http://www.tmtpost.com/rss.xml", "https://www.huxiu.com/rss/0.xml"};
    public ArrayList<ItemEntity> items = new ArrayList<ItemEntity>();
    public ArrayList<ItemEntity> tem_items = new ArrayList<ItemEntity>();
    public PullToRefreshListView pullToRefreshListView;
    public MyListAdapter adapter;

    public class MyHandler extends android.os.Handler {
        private WeakReference<Context> reference;

        public MyHandler(Context context) {
            reference = new WeakReference<>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            MainActivity activity = (MainActivity) reference.get();
            if (null != activity) {
                items.clear();
                items.addAll(tem_items);
                Collections.shuffle(items);
                if (null == adapter) {
                    adapter = new MyListAdapter(activity, items);
                    pullToRefreshListView.setAdapter(adapter);
                } else {
                    adapter.notifyDataSetChanged();
                }
                pullToRefreshListView.onRefreshComplete();
            }

        }
    }

    public MyHandler mHandler;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return getView(inflater, R.layout.rss_list_layout, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mHandler = new MyHandler(this.getActivity());
        pullToRefreshListView = (PullToRefreshListView) view.findViewById(R.id.pulltorefreshlistview);
        pullToRefreshListView.setSpkey(getClass().getSimpleName());
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        tem_items.clear();
                        for (int i = 0; i < rss.length; i++) {
                            parseRss(rss[i]);
                        }
                        mHandler.sendEmptyMessage(10);
                    }
                }).start();
            }
        });
        pullToRefreshListView.setRefreshingInternal();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < rss.length; i++) {
                    parseRss(rss[i]);
                }
                mHandler.sendEmptyMessage(0);
            }
        }).start();

    }

    /**
     * 从dscription中截取图片的路径
     */
    private String filterImage(String strContent) {
        if (!TextUtils.isEmpty(strContent)) {
            int start = strContent.indexOf("src=") + 4;
            int end = strContent.indexOf("jpg") + 3;
            int end_2 = strContent.indexOf("png") + 3;
            if (end_2 < end) {
                end = end_2;
            }
            if (end <= start) {
                return "";
            }
            strContent = strContent.substring(start, end);
            if (strContent.startsWith("\"\"")) {
                strContent = strContent.substring(2);
            }
            if (strContent.startsWith("\"")) {
                strContent = strContent.substring(1);
            }

            return strContent;
        }
        return "";
    }

    /**
     * 格式化时间
     */
    private String formatDate(String strDate) {

//        Log.i("time", strDate);
        if (DateUtils.IsToday(strDate)) {
            return DateUtils.getTimeDifference(strDate);
        } else if (DateUtils.IsYesterday(strDate)) {
            return "1天前";
        } else {
            Date date = new Date(strDate);
            String pat1 = "yyyy-MM-dd HH:mm";
            SimpleDateFormat sdf1 = new SimpleDateFormat(pat1); // 实例化模板对象
            return sdf1.format(date);
        }

    }

    /**
     * 装填数据
     */
    private void getItems(List entries) {
        // 循环得到每个子项信息
        for (int i = 0; i < entries.size(); i++) {
            SyndEntry entry = (SyndEntry) entries.get(i);
            ItemEntity itemEntity = new ItemEntity();
            itemEntity.author = entry.getAuthor().trim();
            SyndContent content = entry.getDescription();
            String contentValue = content.getValue();
            contentValue = filterImage(contentValue);
//            Log.i("imageUrl", "" + contentValue);
            itemEntity.imageLink = contentValue;

            itemEntity.link = entry.getLink();
            itemEntity.time = formatDate(entry.getPublishedDate().toString()).trim();
            itemEntity.title = entry.getTitle().trim();
            tem_items.add(itemEntity);
        }
    }

    /**
     * @param rssUrl
     */
    public void parseRss(String rssUrl) {

        try {
//            XmlReader reader = new XmlReader( getResources().getAssets().open("rss_001.xml"));
            URL url = new URL(rssUrl);
            XmlReader reader = new XmlReader(url);
            SyndFeedInput input = new SyndFeedInput();
            // 得到SyndFeed对象，即得到Rss源里的所有信息
            SyndFeed feed = input.build(reader);
            List entries = feed.getEntries();
            getItems(entries);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
