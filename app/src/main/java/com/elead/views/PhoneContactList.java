package com.elead.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.elead.adapter.PhoneContactAdapter;
import com.elead.eplatform.R;
import com.elead.module.EpUser;
import com.elead.utils.GetPhoneContactsAsyncTask;

import java.util.ArrayList;
import java.util.List;

public class PhoneContactList extends RelativeLayout {
    protected static final String TAG = PhoneContactList.class.getSimpleName();

    protected Context context;
    protected ListView listView;
    protected PhoneContactAdapter adapter;
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


    public PhoneContactList(Context context) {
        this(context, null);
    }

    public PhoneContactList(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PhoneContactList(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
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


        LayoutInflater.from(context).inflate(R.layout.mobile_address_book_contact_list, this);
        listView = (ListView) findViewById(R.id.list);
        sidebar = (Sidebar) findViewById(R.id.sidebar);
        if (!showSiderBar)
            sidebar.setVisibility(View.GONE);


    }

    /*
     * init mView
     */
    public void init() {
        this.contactList = new ArrayList<>();
        adapter = new PhoneContactAdapter(context, 0, contactList);
        adapter.setPrimaryColor(primaryColor).setPrimarySize(primarySize).setInitialLetterBg(initialLetterBg)
                .setInitialLetterColor(initialLetterColor);
        listView.setAdapter(adapter);
        if (showSiderBar) {
            sidebar.setListView(listView);

            adapter.registerDataSetObserver(new DataSetObserver() {
                @Override
                public void onChanged() {
                    List<String> strings = new ArrayList<>();
                    for (EpUser epUser : contactList) {
                        strings.add(epUser.getInitialLetter());
                    }
                    sidebar.init(strings);
                }
            });

        }

        new GetPhoneContactsAsyncTask(getContext(), adapter, contactList).execute("");
    }


    public void refresh() {
        Message msg = handler.obtainMessage(MSG_UPDATE_LIST);
        handler.sendMessage(msg);
    }

    public void filter(CharSequence str) {
        adapter.getFilter().filter(str);
    }

    public ListView getListView() {
        return listView;
    }

    public void setShowSiderBar(boolean showSiderBar) {
        if (showSiderBar) {
            sidebar.setVisibility(View.VISIBLE);
        } else {
            sidebar.setVisibility(View.GONE);
        }
    }

    public PhoneContactAdapter getAdapter() {
        return adapter;
    }
}
