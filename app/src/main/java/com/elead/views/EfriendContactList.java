package com.elead.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.elead.im.adapter.EfriendAdapter;
import com.elead.eplatform.R;
import com.elead.module.EpUser;
import com.elead.views.pulltorefresh.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

public class EfriendContactList extends RelativeLayout {
    protected static final String TAG = EfriendContactList.class.getSimpleName();

    protected Context context;
    protected PullToRefreshListView listView;
    protected EfriendAdapter adapter;
    protected List<EpUser> contactList;
    protected Sidebar sidebar;

    protected int primaryColor;
    protected int primarySize;
    protected boolean showSiderBar;
    protected Drawable initialLetterBg;

    static final int MSG_UPDATE_LIST = 0;

    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_UPDATE_LIST:
                    if (adapter != null) {
                        adapter.clear();
                        adapter.addAll(new ArrayList<EpUser>(contactList));
                        adapter.notifyDataSetChanged();
                    }
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };

    protected int initialLetterColor;


    public EfriendContactList(Context context) {
        super(context);
        init(context, null);
    }

    public EfriendContactList(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public EfriendContactList(Context context, AttributeSet attrs, int defStyle) {
        this(context, attrs);
    }


    private void init(Context context, AttributeSet attrs) {
        this.context = context;
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.EaseContactList);
        primaryColor = ta.getColor(R.styleable.EaseContactList_ctsListPrimaryTextColor, 0);
        primarySize = ta.getDimensionPixelSize(R.styleable.EaseContactList_ctsListPrimaryTextSize, 0);
        showSiderBar = ta.getBoolean(R.styleable.EaseContactList_ctsListShowSiderBar, true);
        initialLetterBg = ta.getDrawable(R.styleable.EaseContactList_ctsListInitialLetterBg);
        initialLetterColor = ta.getColor(R.styleable.EaseContactList_ctsListInitialLetterColor, 0);
        ta.recycle();


        LayoutInflater.from(context).inflate(R.layout.phone_widget_contact_list, this);
        listView = (PullToRefreshListView) findViewById(R.id.list);
        sidebar = (Sidebar) findViewById(R.id.sidebar);
        if (!showSiderBar)
            sidebar.setVisibility(View.GONE);
    }

    /*
     * init mView
     */
    public void init(List<EpUser> contactList) {
        this.contactList = contactList;
        adapter = new EfriendAdapter(context, 0, new ArrayList<>(contactList));
        adapter.setPrimaryColor(primaryColor).setPrimarySize(primarySize).setInitialLetterBg(initialLetterBg)
                .setInitialLetterColor(initialLetterColor);
        listView.setAdapter(adapter);
        if (showSiderBar) {
//            sidebar.setListView(listView.getListview());
//            sidebar.init(contactList);
        }
    }


    public void refresh() {
        Message msg = handler.obtainMessage(MSG_UPDATE_LIST);
        handler.sendMessage(msg);
    }

    public void filter(CharSequence str) {
        adapter.getFilter().filter(str);
    }

    public PullToRefreshListView getListView() {
        return listView;
    }

    public void setShowSiderBar(boolean showSiderBar) {
        if (showSiderBar) {
            sidebar.setVisibility(View.VISIBLE);
        } else {
            sidebar.setVisibility(View.GONE);
        }
    }

    public EfriendAdapter getAdapter() {
        return adapter;
    }
}
