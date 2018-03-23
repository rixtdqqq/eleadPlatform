package com.elead.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseIntArray;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageButton;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.elead.eplatform.R;
import com.elead.im.util.RegistUsersMessageSpUtil;
import com.elead.module.EpUser;
import com.elead.views.CircularImageView;
import com.hyphenate.util.EMLog;

import java.util.ArrayList;
import java.util.List;

public class PhoneContactAdapter extends ArrayAdapter<EpUser> implements SectionIndexer {
    private static final String TAG = "ContactAdapter";
    List<String> list;
    List<EpUser> userList;
    List<EpUser> copyUserList;
    private LayoutInflater layoutInflater;
    private SparseIntArray positionOfSection;
    private SparseIntArray sectionOfPosition;
    private int res;
    private MyFilter myFilter;
    private boolean notiyfyByFilter;
    private onPhoneClickLinstener onPhoneClickLinstener;
    private Context mContext;

    public PhoneContactAdapter(Context context, int resource, List<EpUser> objects) {
        super(context, resource, objects);
        this.res = resource;
        this.userList = objects;
        mContext = context;
        copyUserList = new ArrayList<>();
        copyUserList.addAll(objects);
        layoutInflater = LayoutInflater.from(context);
    }

    private static class ViewHolder {
        CircularImageView avatar;
        TextView nameView;
        TextView headerView;
        TextView phone;
        ImageButton phone_row_add_imb;
    }

    public static final int STATE_DEFAULT = 0;
    public static final int STATE_IS_REG = 1;
    public static final int STATE_NO_REG = 2;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            if (res == 0)
                convertView = layoutInflater.inflate(R.layout.item_phonecontact, null);
            else
                convertView = layoutInflater.inflate(res, null);
            holder.avatar = (CircularImageView) convertView.findViewById(R.id.avatar);
            holder.nameView = (TextView) convertView.findViewById(R.id.name);
            holder.phone = (TextView) convertView.findViewById(R.id.phone);
            holder.headerView = (TextView) convertView.findViewById(R.id.header);
            holder.phone_row_add_imb = (ImageButton) convertView.findViewById(R.id.phone_row_add_imb);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final EpUser user = getItem(position);
        if (user == null)
            Log.d("ContactAdapter", position + "");
        String username = user.getRole_name();
        String phone = user.getMobile_phone();
        String header = user.getInitialLetter();

        if (position == 0 || header != null && !header.equals(getItem(position - 1).getInitialLetter())) {
            if (TextUtils.isEmpty(header)) {
                holder.headerView.setVisibility(View.GONE);
            } else {
                holder.headerView.setVisibility(View.VISIBLE);
                holder.headerView.setText(header);
            }
        } else {
            holder.headerView.setVisibility(View.GONE);
        }

        holder.nameView.setText(username);
        holder.phone.setText(phone);
        holder.avatar.setBackgroundColor(username);
        holder.avatar.setText(username);

        if (null != onPhoneClickLinstener) {
            holder.phone_row_add_imb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onPhoneClickLinstener.onAddClick(user);
                }
            });
        }
        if (phone.equals("13528810091")) {
            Log.d("tag", "" + RegistUsersMessageSpUtil.getInstance(mContext).isAddFriend(phone));

        }
        holder.phone_row_add_imb.setVisibility(View.GONE);
        if (user.londingState != STATE_NO_REG && (user.londingState == STATE_IS_REG
                || RegistUsersMessageSpUtil.getInstance(mContext).isRegist(phone))
                && !RegistUsersMessageSpUtil.getInstance(mContext).isAddFriend(phone)
                ) {
            holder.phone_row_add_imb.setVisibility(View.VISIBLE);
            user.londingState = STATE_IS_REG;
        } else {
            user.londingState = STATE_NO_REG;
        }

//                final BmobQuery<EpUser> bmobQuery = new BmobQuery<EpUser>();
//                bmobQuery.addWhereEqualTo("mobile_phone", user.getMobile_phone());
//                bmobQuery.findObjects(mContext, new FindListener<EpUser>() {
//
//                    @Override
//                    public void onSuccess(List<EpUser> object) {
//                        if (object.size() == 1) {
//                            EpUser user = object.get(0);
//                            RegistUsersMessageSpUtil.getInstance(mContext).saveInstallationId(user.getMobile_phone(), user.getInstallationId());
//                             user.londingState = STATE_IS_REG;
//                               notifyDataSetChanged();
//                        } else {
//                            user.londingState = STATE_NO_REG;
//                        }
//                    }
//
//                    @Override
//                    public void onError(int code, String msg) {
//                        user.londingState = STATE_NO_REG;
//                    }
//                });

        if (primaryColor != 0)
            holder.nameView.setTextColor(primaryColor);
        if (primarySize != 0)
            holder.nameView.setTextSize(TypedValue.COMPLEX_UNIT_PX, primarySize);
        if (initialLetterBg != null)
            holder.headerView.setBackgroundDrawable(initialLetterBg);
        if (initialLetterColor != 0)
            holder.headerView.setTextColor(initialLetterColor);

        return convertView;
    }

    @Override
    public EpUser getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public int getPositionForSection(int section) {
        return positionOfSection.get(section);
    }

    @Override
    public int getSectionForPosition(int position) {
        return sectionOfPosition.get(position);
    }

    @Override
    public Object[] getSections() {
        positionOfSection = new SparseIntArray();
        sectionOfPosition = new SparseIntArray();
        int count = getCount();
        list = new ArrayList<String>();
        list.add(getContext().getString(R.string.search_header));
        positionOfSection.put(0, 0);
        sectionOfPosition.put(0, 0);
        for (int i = 1; i < count; i++) {

            String letter = getItem(i).getInitialLetter();
            int section = list.size() - 1;
            if (list.get(section) != null && !list.get(section).equals(letter)) {
                list.add(letter);
                section++;
                positionOfSection.put(section, i);
            }
            sectionOfPosition.put(i, section);
        }
        return list.toArray(new String[list.size()]);
    }

    @Override
    public Filter getFilter() {
        if (myFilter == null) {
            myFilter = new MyFilter(userList);
        }
        return myFilter;
    }

    protected class MyFilter extends Filter {
        List<EpUser> mOriginalList = null;

        public MyFilter(List<EpUser> myList) {
            this.mOriginalList = myList;
        }

        @Override
        protected synchronized FilterResults performFiltering(CharSequence prefix) {
            FilterResults results = new FilterResults();
            if (mOriginalList == null) {
                mOriginalList = new ArrayList<EpUser>();
            }
            EMLog.d(TAG, "contacts original size: " + mOriginalList.size());
            EMLog.d(TAG, "contacts copy size: " + copyUserList.size());

            if (prefix == null || prefix.length() == 0) {
                results.values = copyUserList;
                results.count = copyUserList.size();
            } else {
                String prefixString = prefix.toString();
                final int count = mOriginalList.size();
                final ArrayList<EpUser> newValues = new ArrayList<EpUser>();
                for (int i = 0; i < count; i++) {
                    final EpUser user = mOriginalList.get(i);
                    String username = user.getMobile_phone();

                    if (username.startsWith(prefixString)) {
                        newValues.add(user);
                    } else {
                        final String[] words = username.split(" ");
                        final int wordCount = words.length;

                        // Start at INDEX 0, in case valueText starts with space(s)
                        for (int k = 0; k < wordCount; k++) {
                            if (words[k].startsWith(prefixString)) {
                                newValues.add(user);
                                break;
                            }
                        }
                    }
                }
                results.values = newValues;
                results.count = newValues.size();
            }
            EMLog.d(TAG, "contacts filter results size: " + results.count);
            return results;
        }

        @Override
        protected synchronized void publishResults(CharSequence constraint,
                                                   FilterResults results) {
            userList.clear();
            userList.addAll((List<EpUser>) results.values);
            EMLog.d(TAG, "publish contacts filter results size: " + results.count);
            if (results.count > 0) {
                notiyfyByFilter = true;
                notifyDataSetChanged();
                notiyfyByFilter = false;
            } else {
                notifyDataSetInvalidated();
            }
        }
    }


    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        if (!notiyfyByFilter) {
            copyUserList.clear();
            copyUserList.addAll(userList);
        }
    }

    protected int primaryColor;
    protected int primarySize;
    protected Drawable initialLetterBg;
    protected int initialLetterColor;

    public PhoneContactAdapter setPrimaryColor(int primaryColor) {
        this.primaryColor = primaryColor;
        return this;
    }


    public PhoneContactAdapter setPrimarySize(int primarySize) {
        this.primarySize = primarySize;
        return this;
    }

    public PhoneContactAdapter setInitialLetterBg(Drawable initialLetterBg) {
        this.initialLetterBg = initialLetterBg;
        return this;
    }

    public PhoneContactAdapter setInitialLetterColor(int initialLetterColor) {
        this.initialLetterColor = initialLetterColor;
        return this;
    }

    public interface onPhoneClickLinstener {
        void onAddClick(EpUser user);
    }

    public void setOnClickLinstener(PhoneContactAdapter.onPhoneClickLinstener onPhoneClickLinstener) {
        this.onPhoneClickLinstener = onPhoneClickLinstener;
    }
}
