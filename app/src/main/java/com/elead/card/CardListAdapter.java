package com.elead.card;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.elead.card.interfaces.ICard;
import com.elead.card.mode.BaseCardInfo;

import java.util.ArrayList;
import java.util.List;

public class CardListAdapter extends BaseAdapter {
    private Context mContext;
    List<BaseCardInfo> itemTypes = null;
    private List<BaseCardInfo> list;

    public CardListAdapter(Context mContext,
                           List<BaseCardInfo> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public int getItemViewType(int position) {
        if (null != itemTypes) {
            return itemTypes.indexOf(list.get(position));
        }
        return -1;
    }

    @Override
    public int getViewTypeCount() {
        if (itemTypes == null) {
            itemTypes = new ArrayList<BaseCardInfo>();
            for (int i = 0; i < list.size(); i++) {
                BaseCardInfo card = list.get(i);
                if (!itemTypes.contains(card)) {
                    itemTypes.add(card);

                }

            }
//            System.out.println("itemTypes: " + itemTypes);
        }
        return itemTypes.size();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseCardInfo cardInfo = list.get(position);
        String cardType = cardInfo.cardType;
        String cardRes = ResConstants.cardType2Res(cardType);
        ViewHolder holder = null;
        if (!TextUtils.isEmpty(cardRes)) {
            if (null == convertView
                    || (null != convertView && !convertView.getClass()
                    .getSimpleName().equals(cardInfo.cardType))) {
                holder = new ViewHolder();

                int identifier = mContext.getResources().getIdentifier(cardRes,
                        "layout", mContext.getPackageName());
                if (0 != identifier) {
                    holder.cardView = convertView = LayoutInflater.from(
                            mContext).inflate(identifier, null);
                }
                if (null != convertView) {
                    convertView.setTag(holder);
                }
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            if (holder.cardView instanceof ICard) {
                ((ICard) holder.cardView).setDate(cardInfo);
            }
        } else {
            holder = null;
            convertView = null;
        }
        return convertView;
    }

    class ViewHolder {
        View cardView;
    }
}
