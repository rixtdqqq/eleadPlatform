//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.elead.im.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.elead.eplatform.R;
import com.elead.im.db.DemoHelper;
import com.elead.im.entry.EaseUser;
import com.elead.im.util.EaseUserUtils;
import com.elead.utils.Pinyin4jUtil;
import com.elead.views.CircularImageView;
import com.hyphenate.EMValueCallBack;
import com.hyphenate.chat.EMClient;

import java.util.ArrayList;
import java.util.List;

public class EaseContactAdapter extends ArrayAdapter<EaseUser> implements SectionIndexer {
    private static final String TAG = "ContactAdapter";
    List<String> list;
    List<EaseUser> userList;
    List<EaseUser> copyUserList;
    private List<EaseUser> mFilteredArrayList = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private SparseIntArray positionOfSection;
    private SparseIntArray sectionOfPosition;
    private int res;
    private MyFilter myFilter;
    private boolean notiyfyByFilter;
    protected int primaryColor;
    protected int primarySize;
    protected Drawable initialLetterBg;
    protected int initialLetterColor;

    public EaseContactAdapter(Context context, int resource, List<EaseUser> objects) {
        super(context, resource, objects);
        this.res = resource;
        this.userList = objects;
        this.copyUserList = new ArrayList();
        this.copyUserList.addAll(objects);
        this.layoutInflater = LayoutInflater.from(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            if (this.res == 0) {
                convertView = this.layoutInflater.inflate(R.layout.ease_row_contact, parent, false);
            } else {
                convertView = this.layoutInflater.inflate(this.res, null);
            }

            holder.avatar = (CircularImageView) convertView.findViewById(R.id.avatar);
            holder.nameView = (TextView) convertView.findViewById(R.id.name);
            holder.headerView = (TextView) convertView.findViewById(R.id.header);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        EaseUser user = this.getItem(position);
        if (user == null) {
            Log.d("ContactAdapter", position + "");
        }

        String username = user.getUsername();
        String header = user.getInitialLetter();
        if (position != 0 && (header == null || header.equals(this.getItem(position - 1).getInitialLetter()))) {
            holder.headerView.setVisibility(View.GONE);
        } else if (TextUtils.isEmpty(header)) {
            holder.headerView.setVisibility(View.GONE);
        } else {
            holder.headerView.setVisibility(View.VISIBLE);
            holder.headerView.setText(header);
        }


        if (username != null) {
            if (username.equals(EMClient.getInstance().getCurrentUser())) {
                holder.nameView.setText(EMClient.getInstance().getCurrentUser());
                EaseUserUtils.setUserNick(username, holder.nameView);
                EaseUserUtils.setUserAvatar(this.getContext(), username, holder.avatar);
            } else {
                holder.nameView.setText(username);
                EaseUserUtils.setUserNick(username, holder.nameView);
                EaseUserUtils.setUserAvatar(this.getContext(), username, holder.avatar);
                asyncFetchUserInfo(username, holder.nameView, holder.avatar);
            }
        }
        if (this.primaryColor != 0) {
            holder.nameView.setTextColor(this.primaryColor);
        }

        if (this.primarySize != 0) {
            holder.nameView.setTextSize(0, (float) this.primarySize);
        }

        if (this.initialLetterBg != null) {
            holder.headerView.setBackgroundDrawable(this.initialLetterBg);
        }

        if (this.initialLetterColor != 0) {
            holder.headerView.setTextColor(this.initialLetterColor);
        }

        return convertView;
    }

    public EaseUser getItem(int position) {
        return (EaseUser) super.getItem(position);
    }

    public int getCount() {
        return super.getCount();
    }

    public int getPositionForSection(int section) {
        return this.positionOfSection.get(section);
    }

    public int getSectionForPosition(int position) {
        return this.sectionOfPosition.get(position);
    }

    public Object[] getSections() {
        this.positionOfSection = new SparseIntArray();
        this.sectionOfPosition = new SparseIntArray();
        int count = this.getCount();
        this.list = new ArrayList();
        this.list.add(this.getContext().getString(R.string.search_header));
        this.positionOfSection.put(0, 0);
        this.sectionOfPosition.put(0, 0);

        for (int i = 1; i < count; ++i) {
            String letter = this.getItem(i).getInitialLetter();
            int section = this.list.size() - 1;
            if (this.list.get(section) != null && !((String) this.list.get(section)).equals(letter)) {
                this.list.add(letter);
                ++section;
                this.positionOfSection.put(section, i);
            }

            this.sectionOfPosition.put(i, section);
        }

        return this.list.toArray(new String[this.list.size()]);
    }

    public Filter getFilter() {
        if (this.myFilter == null) {
            this.myFilter = new MyFilter(this.userList);
        }

        return this.myFilter;
    }

    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        if (!this.notiyfyByFilter) {
            this.copyUserList.clear();
            this.copyUserList.addAll(this.userList);
        }

    }

    public EaseContactAdapter setPrimaryColor(int primaryColor) {
        this.primaryColor = primaryColor;
        return this;
    }

    public EaseContactAdapter setPrimarySize(int primarySize) {
        this.primarySize = primarySize;
        return this;
    }

    public EaseContactAdapter setInitialLetterBg(Drawable initialLetterBg) {
        this.initialLetterBg = initialLetterBg;
        return this;
    }

    public EaseContactAdapter setInitialLetterColor(int initialLetterColor) {
        this.initialLetterColor = initialLetterColor;
        return this;
    }

    protected class MyFilter extends Filter {
        List<EaseUser> mOriginalList = null;

        public MyFilter(List<EaseUser> myList) {
            this.mOriginalList = myList;
        }

        protected synchronized FilterResults performFiltering(CharSequence prefix) {
            FilterResults results = new FilterResults();
            if (this.mOriginalList == null) {
                this.mOriginalList = new ArrayList();
            }

            if (prefix != null && prefix.length() != 0) {
                String prefixString = prefix.toString();
                int count = this.mOriginalList.size();
                ArrayList newValues = new ArrayList();

                for (int i = 0; i < count; ++i) {
                    EaseUser user = (EaseUser) this.mOriginalList.get(i);
                    String username = user.getUsername();
                    if (username.startsWith(prefixString)
                            || Pinyin4jUtil.getPinYin(username).contains(prefixString)
                            || username.contains(prefixString)
                            || Pinyin4jUtil.getPinYinHeadChar(username).equals(prefixString)) {
                        newValues.add(user);
                    } else {
                        String[] words = username.split(" ");
                        int wordCount = words.length;
                        String[] var11 = words;
                        int var12 = words.length;

                        for (int var13 = 0; var13 < var12; ++var13) {
                            String word = var11[var13];
                            if (word.startsWith(prefixString)) {
                                newValues.add(user);
                                break;
                            }
                        }
                    }
                }

                results.values = newValues;
                results.count = newValues.size();
            } else {
                results.values = EaseContactAdapter.this.copyUserList;
                results.count = EaseContactAdapter.this.copyUserList.size();
            }

            return results;
        }

        protected synchronized void publishResults(CharSequence constraint, FilterResults results) {
            EaseContactAdapter.this.userList.clear();
            EaseContactAdapter.this.userList.addAll((List) results.values);
            if (results.count > 0) {
                EaseContactAdapter.this.notiyfyByFilter = true;
                EaseContactAdapter.this.notifyDataSetChanged();
                EaseContactAdapter.this.notiyfyByFilter = false;
            } else {
                EaseContactAdapter.this.notifyDataSetInvalidated();
            }

        }
    }

    private static class ViewHolder {
        CircularImageView avatar;
        TextView nameView;
        TextView headerView;

        private ViewHolder() {
        }
    }


    public void asyncFetchUserInfo(String username, final TextView textView, final ImageView avatar) {

        DemoHelper.getInstance().getUserProfileManager().asyncGetUserInfo(username, new EMValueCallBack<EaseUser>() {

            @Override
            public void onSuccess(EaseUser user) {
                if (!(getContext() instanceof Activity)) return;
                Activity activity = (Activity) getContext();
                if (activity.isFinishing()) return;

                if (user != null) {
                    DemoHelper.getInstance().saveContact(user);
                    if (null == textView) {
                        return;
                    }
                    textView.setText(user.getNickname());
                    if (!TextUtils.isEmpty(user.getAvatar())) {
                        Glide.with(getContext()).load(user.getAvatar()).into(avatar);
                    }
                }
            }

            @Override
            public void onError(int error, String errorMsg) {

            }
        });
    }
}
