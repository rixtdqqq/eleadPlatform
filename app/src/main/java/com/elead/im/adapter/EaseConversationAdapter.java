package com.elead.im.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;
import android.widget.TextView.BufferType;

import com.elead.eplatform.R;
import com.elead.im.activity.GroupDetailsActivity;
import com.elead.im.activity.UserInfoDetailActivity;
import com.elead.im.entry.EaseUser;
import com.elead.im.model.EaseAtMessageHelper;
import com.elead.im.util.EaseCommonUtils;
import com.elead.im.util.EaseSmileUtils;
import com.elead.im.util.EaseUserUtils;
import com.elead.im.view.EaseConversationList.EaseConversationListHelper;
import com.elead.im.widget.EaseConstant;
import com.elead.views.CircularImageView;
import com.hyphenate.chat.EMChatRoom;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMConversation.EMConversationType;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.util.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.elead.application.MyApplication.showingActivity;

/**
 * conversation list adapter
 */
public class EaseConversationAdapter extends ArrayAdapter<EMConversation> {
    private static final String TAG = "ChatAllHistoryAdapter";
    private List<EMConversation> conversationList;
    private List<EMConversation> copyConversationList;
    private ConversationFilter conversationFilter;
    private boolean notiyfyByFilter;

    protected int primaryColor;
    protected int secondaryColor;
    protected int timeColor;
    protected int primarySize;
    protected int secondarySize;
    protected float timeSize;

    public EaseConversationAdapter(Context context, int resource,
                                   List<EMConversation> objects) {
        super(context, resource, objects);
        conversationList = objects;
        copyConversationList = new ArrayList<EMConversation>();
        copyConversationList.addAll(objects);
    }

    @Override
    public int getCount() {
        return conversationList.size();
    }

    @Override
    public EMConversation getItem(int arg0) {
        if (arg0 >= 0 && arg0 < conversationList.size()) {
            return conversationList.get(arg0);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.ease_row_chat_history, parent, false);
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.message = (TextView) convertView.findViewById(R.id.message);
            holder.time = (TextView) convertView.findViewById(R.id.time);
            holder.avatar = (CircularImageView) convertView.findViewById(R.id.messagelist_avatar);
            holder.msgState = convertView.findViewById(R.id.msg_state);
            holder.motioned = (TextView) convertView.findViewById(R.id.mentioned);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // get conversation
        EMConversation conversation = getItem(position);
        // get username or group id
        final String username = conversation.getUserName();

        if (conversation.getType() == EMConversationType.GroupChat) {
            String groupId = conversation.getUserName();
            if (EaseAtMessageHelper.get().hasAtMeMsg(groupId)) {
                holder.motioned.setVisibility(View.VISIBLE);
            } else {
                holder.motioned.setVisibility(View.GONE);
            }

            Log.i("Adapter", username + "");
            // group message, show group avatar
            final EMGroup group = EMClient.getInstance().groupManager().getGroup(username);
            String groupName = group != null ? group.getGroupName() : username;

            holder.avatar.setText(group.getMembers());
            holder.name.setText(groupName);
            holder.avatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showingActivity.startActivity(new Intent(showingActivity, GroupDetailsActivity.class).putExtra(EaseConstant.GROUP_ID, group.getGroupId()));
                }
            });
        } else if (conversation.getType() == EMConversationType.ChatRoom) {
            holder.avatar.setImageResource(R.drawable.em_group_icon);
            EMChatRoom room = EMClient.getInstance().chatroomManager().getChatRoom(username);
            holder.name.setText(room != null && !TextUtils.isEmpty(room.getName()) ? room.getName() : username);
            holder.motioned.setVisibility(View.GONE);

        } else {
            EaseUserUtils.setUserAvatar(getContext(), username, holder.avatar);
            EaseUserUtils.setUserNick(username, holder.name);
            holder.motioned.setVisibility(View.GONE);
            holder.avatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showingActivity.startActivity(new Intent(showingActivity, UserInfoDetailActivity.class).putExtra(UserInfoDetailActivity.TAG, username));
                }
            });
        }

        holder.avatar.setUnreadMsgCount(conversation.getUnreadMsgCount());

        if (conversation.getAllMsgCount() != 0) {
            // show the content of latest message
            EMMessage lastMessage = conversation.getLastMessage();
            String content = null;
            if (cvsListHelper != null) {
                content = cvsListHelper.onSetItemSecondaryText(lastMessage);
            }
            String first = "";
            if (conversation.getType() == EMConversationType.GroupChat) {
                first = EaseUserUtils.getNick(lastMessage.getFrom()) + ":";
            }
            holder.message.setText(EaseSmileUtils.getSmiledText(getContext(), first + EaseCommonUtils.getMessageDigest(lastMessage, (this.getContext()))),
                    BufferType.SPANNABLE);
            if (content != null) {
                holder.message.setText(content);
            }
            holder.time.setText(DateUtils.getTimestampString(new Date(lastMessage.getMsgTime())));
            if (lastMessage.direct() == EMMessage.Direct.SEND && lastMessage.status() == EMMessage.Status.FAIL) {
                holder.msgState.setVisibility(View.VISIBLE);
            } else {
                holder.msgState.setVisibility(View.GONE);
            }
        }

        //set property
        //holder.name.setTextColor(primaryColor);
        //holder.message.setTextColor(secondaryColor);
        //holder.time.setTextColor(timeColor);
        if (primarySize != 0)
            holder.name.setTextSize(TypedValue.COMPLEX_UNIT_PX, primarySize);
        if (secondarySize != 0)
            holder.message.setTextSize(TypedValue.COMPLEX_UNIT_PX, secondarySize);
        if (timeSize != 0)
            holder.time.setTextSize(TypedValue.COMPLEX_UNIT_PX, timeSize);

        return convertView;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        if (!notiyfyByFilter) {
            copyConversationList.clear();
            copyConversationList.addAll(conversationList);
            notiyfyByFilter = false;
        }
    }

    @Override
    public Filter getFilter() {
        if (conversationFilter == null) {
            conversationFilter = new ConversationFilter(conversationList);
        }
        return conversationFilter;
    }


    public void setPrimaryColor(int primaryColor) {
        this.primaryColor = primaryColor;
    }

    public void setSecondaryColor(int secondaryColor) {
        this.secondaryColor = secondaryColor;
    }

    public void setTimeColor(int timeColor) {
        this.timeColor = timeColor;
    }

    public void setPrimarySize(int primarySize) {
        this.primarySize = primarySize;
    }

    public void setSecondarySize(int secondarySize) {
        this.secondarySize = secondarySize;
    }

    public void setTimeSize(float timeSize) {
        this.timeSize = timeSize;
    }


    private class ConversationFilter extends Filter {
        List<EMConversation> mOriginalValues = null;

        public ConversationFilter(List<EMConversation> mList) {
            mOriginalValues = mList;
        }

        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            FilterResults results = new FilterResults();

            if (mOriginalValues == null) {
                mOriginalValues = new ArrayList<EMConversation>();
            }
            if (prefix == null || prefix.length() == 0) {
                results.values = copyConversationList;
                results.count = copyConversationList.size();
            } else {
                String prefixString = prefix.toString();
                final int count = mOriginalValues.size();
                final ArrayList<EMConversation> newValues = new ArrayList<EMConversation>();

                for (int i = 0; i < count; i++) {
                    final EMConversation value = mOriginalValues.get(i);
                    String username = value.getUserName();

                    EMGroup group = EMClient.getInstance().groupManager().getGroup(username);
                    if (group != null) {
                        username = group.getGroupName();
                    } else {
                        EaseUser user = EaseUserUtils.getUserInfo(username);
                        // TODO: not support Nick anymore
//                        if(user != null && user.getNick() != null)
//                            username = user.getNick();
                    }

                    // First match against the whole ,non-splitted value
                    if (username.startsWith(prefixString)) {
                        newValues.add(value);
                    } else {
                        final String[] words = username.split(" ");
                        final int wordCount = words.length;

                        // Start at index 0, in case valueText starts with space(s)
                        for (String word : words) {
                            if (word.startsWith(prefixString)) {
                                newValues.add(value);
                                break;
                            }
                        }
                    }
                }

                results.values = newValues;
                results.count = newValues.size();
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            conversationList.clear();
            if (results.values != null) {
                conversationList.addAll((List<EMConversation>) results.values);
            }
            if (results.count > 0) {
                notiyfyByFilter = true;
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }
    }

    private EaseConversationListHelper cvsListHelper;

    public void setCvsListHelper(EaseConversationListHelper cvsListHelper) {
        this.cvsListHelper = cvsListHelper;
    }

    private static class ViewHolder {
        /**
         * who you chat with
         */
        TextView name;
        /**
         * content of last message
         */
        TextView message;
        /**
         * time of last message
         */
        TextView time;
        /**
         * avatar
         */
        CircularImageView avatar;
        /**
         * status of last message
         */
        View msgState;
        /**
         * layout
         */
        TextView motioned;
    }
}

