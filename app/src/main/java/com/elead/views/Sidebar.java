/**
 * Copyright (C) 2016 Hyphenate Inc. All rights reserved.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.elead.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.elead.eplatform.R;
import com.elead.utils.DensityUtil;

import java.util.ArrayList;
import java.util.List;


public class Sidebar extends View {
    private static final String TOP_STR = "↑";
    private Paint paint;
    private TextView header;
    private float height, textSize;
    private ListView mListView;
    private Context context;

    private SectionIndexer sectionIndexter = null;

    public void setListView(ListView listView) {
        mListView = listView;
    }


    public Sidebar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    List<String> sections = new ArrayList<>();
    private final int TEXT_SIZE = 10;


    public void init(List<String> letters) {
        if (letters.size() > 0) {
            sections.add(TOP_STR);
            for (int i = 0; i < letters.size(); i++) {
                String letter = letters.get(i);
                if (!sections.contains(letter)) {
                    sections.add(letter);
                }
            }
            textSize = DensityUtil.sp2px(context, TEXT_SIZE);
            double height = textSize * 1.5d * sections.size() + textSize;
            getLayoutParams().height = (int) height;
            paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setColor(Color.parseColor("#8C8C8C"));
            paint.setTextAlign(Align.CENTER);
            paint.setTextSize(DensityUtil.sp2px(context, TEXT_SIZE));
        }
    }
//    public void init(List<EaseUser> contactList) {
//        if (contactList.size() > 0) {
//            sections.add(TOP_STR);
//            for (int i = 0; i < contactList.size(); i++) {
//                String letter = contactList.get(i).getInitialLetter();
//                if (!sections.contains(letter)) {
//                    sections.add(letter);
//                }
//            }
//            textSize = DensityUtil.sp2px(context, TEXT_SIZE);
//            double height = textSize * 1.5d * sections.size() + textSize;
//            getLayoutParams().height = (int) height;
//            paint = new Paint(Paint.ANTI_ALIAS_FLAG);
//            paint.setColor(Color.parseColor("#8C8C8C"));
//            paint.setTextAlign(Align.CENTER);
//            paint.setTextSize(DensityUtil.sp2px(context, TEXT_SIZE));
//        }
//    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float center = getWidth() / 2;
        if (null != sections && sections.size() > 0) {
            height = textSize * 1.5f;
//            height = sections.size() <= 0 ? 0 : getHeight() * 0.98f / sections.size();
            for (int i = sections.size() - 1; i > -1; i--) {
                String s = sections.get(i);
                if (!TextUtils.isEmpty(s))
                    canvas.drawText(s, center, height * (i + 1), paint);
            }
        }
    }

    private int sectionForPoint(float y) {
        int index = (int) (y / height);
        if (index < 0) {
            index = 0;
        }
        if (index > sections.size() - 1) {
            index = sections.size() - 1;
        }
        return index;
    }

    private void setHeaderTextAndscroll(MotionEvent event) {
        if (mListView == null) {
            //check the mListView to avoid NPE. but the mListView shouldn't be null
            //need to check the call stack later
            return;
        }
        String headerString = sections.get(sectionForPoint(event.getY()));
        header.setText(headerString);
        ListAdapter adapter = mListView.getAdapter();
        if (sectionIndexter == null) {
            if (adapter instanceof HeaderViewListAdapter) {
                sectionIndexter = (SectionIndexer) ((HeaderViewListAdapter) adapter).getWrappedAdapter();
            } else if (adapter instanceof SectionIndexer) {
                sectionIndexter = (SectionIndexer) adapter;
            } else {
                throw new RuntimeException("listview sets adpater does not implement SectionIndexer interfaces");
            }
        }
        String[] adapterSections = (String[]) sectionIndexter.getSections();
        try {
            for (int i = adapterSections.length - 1; i > -1; i--) {
                if (adapterSections[i].equals(headerString)) {
                    mListView.setSelection(sectionIndexter.getPositionForSection(i));
                    break;
                } else if (headerString.equals(TOP_STR)) {
                    mListView.setSelection(0);
                }
            }
        } catch (Exception e) {
            Log.e("setHeaderTextAndscroll", e.getMessage());
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("event", event.getAction() + "");
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                handler.removeMessages(MotionEvent.ACTION_UP);
                if (header == null) {
                    header = (TextView) ((View) getParent()).findViewById(R.id.floating_header);
                }
                setHeaderTextAndscroll(event);
                header.setVisibility(View.VISIBLE);
                paint.setColor(Color.parseColor("#FFFFFF"));
                setBackgroundResource(R.drawable.sidebar_background_pressed);
                break;

            case MotionEvent.ACTION_MOVE:
                handler.removeMessages(MotionEvent.ACTION_UP);
                setHeaderTextAndscroll(event);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                setBackgroundColor(Color.TRANSPARENT);
                header.setVisibility(View.INVISIBLE);
                paint.setColor(Color.parseColor("#8C8C8C"));
                break;
        }
        return true;
    }


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MotionEvent.ACTION_UP:
                    header.setVisibility(View.INVISIBLE);
                    paint.setColor(Color.parseColor("#8C8C8C"));
                    setBackgroundColor(Color.TRANSPARENT);
                    break;

                default:
                    break;
            }
        }
    };

}
