package com.elead.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.elead.eplatform.R;
import com.elead.im.db.DemoHelper;
import com.elead.im.entry.EaseUser;
import com.elead.im.util.EaseUserUtils;
import com.elead.views.CircularImageView;
import com.hyphenate.EMValueCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMContact;
import com.hyphenate.chat.EMGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2017/1/9 0009.
 */

public class CommonLinkAdapter extends ArrayAdapter<EMContact> {

    private LayoutInflater inflater;
    private List<EMContact> conversationList;
    private List<EMContact> copyConversationList;
    private ConversationFilter conversationFilter;
    private boolean notiyfyByFilter;

    public CommonLinkAdapter(Context context, int textViewResourceId, List<EMContact> objects) {
        super(context, textViewResourceId, objects);
        this.conversationList = objects;
        copyConversationList = new ArrayList<EMContact>();
        copyConversationList.addAll(objects);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.ease_row_contact, parent, false);
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        if (holder == null) {
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.name);
//            holder.message = (TextView) convertView.findViewById(R.id.message);
//            holder.time = (TextView) convertView.findViewById(R.id.time);
            holder.avatar = (CircularImageView) convertView.findViewById(R.id.avatar);
//            holder.msgState = convertView.findViewById(R.id.msg_state);
//            holder.list_item_layout = (RelativeLayout) convertView.findViewById(R.id.list_item_layout);
            convertView.setTag(holder);
        }


        // 获取与此用户/群组的会话
        EMContact conversation = getItem(position);
        // 获取用户username或者群组groupid
       // String username = conversation.getUserName();
        String username = conversation.getUsername();
        List<EMGroup> groups = EMClient.getInstance().groupManager().getAllGroups();
        EMContact contact = null;
        boolean isGroup = false;
        for (EMGroup group : groups) {
            if (!group.getGroupId().equals(username)) {
                isGroup = true;
                //contact = group;
                break;
            }
        }
        if (isGroup) {
            if (username != null) {
                if (username.equals(EMClient.getInstance().getCurrentUser())) {
                   // holder.name.setText(EMClient.getInstance().getCurrentUser());
                    EaseUserUtils.setUserNick(username, holder.name);
                    EaseUserUtils.setUserAvatar(this.getContext(), username, holder.avatar);
                } else {
                   // holder.name.setText(username);
                    EaseUserUtils.setUserNick(username,holder.name);
                    EaseUserUtils.setUserAvatar(this.getContext(), username, holder.avatar);
                    asyncFetchUserInfo(username,holder.name, holder.avatar);
                }
            }
            //holder.name.setText(username);
            Log.i("TAG","holder.name==="+username);
        }



//        if (conversation.getUnreadMsgCount() > 0) {
//            // 显示与此用户的消息未读数
//            holder.unreadLabel.setText(String.valueOf(conversation.getUnreadMsgCount()));
//            holder.unreadLabel.setVisibility(View.VISIBLE);
//        } else {
//            holder.unreadLabel.setVisibility(View.INVISIBLE);
//        }
//
////        if (conversation.getMsgCount() != 0) {
//            // 把最后一条消息的内容作为item的message内容
//            EMMessage lastMessage = conversation.getLastMessage();
//            holder.message.setText(SmileUtils.getSmiledText(getContext(), getMessageDigest(lastMessage, (this.getContext()))),
//                    TextView.BufferType.SPANNABLE);
//
//            holder.time.setText(DateUtils.getTimestampString(new Date(lastMessage.getMsgTime())));
//            if (lastMessage.direct == EMMessage.Direct.SEND && lastMessage.status == EMMessage.Status.FAIL) {
//                holder.msgState.setVisibility(View.VISIBLE);
//            } else {
//                holder.msgState.setVisibility(View.GONE);
//            }
//        }

        return convertView;
    }

   /* *//**
     * 根据消息内容和消息类型获取消息内容提示
     *
     * @param
     * @return
     *//*
    private String getMessageDigest(EMMessage message, Context context) {
        String digest = "";
        switch (message.getType()) {
            case LOCATION: // 位置消息
                if (message.direct == EMMessage.Direct.RECEIVE) {
                    // 从sdk中提到了ui中，使用更简单不犯错的获取string的方法
                    // digest = EasyUtils.getAppResourceString(context,
                    // "location_recv");
                    digest = getStrng(context, R.string.location_recv);
                    digest = String.format(digest, message.getFrom());
                    return digest;
                } else {
                    // digest = EasyUtils.getAppResourceString(context,
                    // "location_prefix");
                    digest = getStrng(context, R.string.location_prefix);
                }
                break;
            case IMAGE: // 图片消息
                ImageMessageBody imageBody = (ImageMessageBody) message.getBody();
                digest = getStrng(context, R.string.picture) + imageBody.getFileName();
                break;
            case VOICE:// 语音消息
                digest = getStrng(context, R.string.voice);
                break;
            case VIDEO: // 视频消息
                digest = getStrng(context, R.string.video);
                break;
            case TXT: // 文本消息
                if(!message.getBooleanAttribute(Constant.MESSAGE_ATTR_IS_VOICE_CALL,false)){
                    TextMessageBody txtBody = (TextMessageBody) message.getBody();
                    digest = txtBody.getMessage();
                }else{
                    TextMessageBody txtBody = (TextMessageBody) message.getBody();
                    digest = getStrng(context, R.string.voice_call) + txtBody.getMessage();
                }
                break;
            case FILE: // 普通文件消息
                digest = getStrng(context, R.string.file);
                break;
            default:
                System.err.println("error, unknow type");
                return "";
        }

        return digest;
    }
*/
    private static class ViewHolder {
        /** 和谁的聊天记录 */
        TextView name;
        /** 消息未读数 */
        TextView unreadLabel;
        /** 最后一条消息的内容 */
        TextView message;
        /** 最后一条消息的时间 */
        TextView time;
        /** 用户头像 */
        CircularImageView avatar;
        /** 最后一条消息的发送状态 */
        View msgState;
        /** 整个list中每一行总布局 */
        RelativeLayout list_item_layout;

    }

    String getStrng(Context context, int resId) {
        return context.getResources().getString(resId);
    }



    @Override
    public Filter getFilter() {
        if (conversationFilter == null) {
            conversationFilter = new ConversationFilter(conversationList);
        }
        return conversationFilter;
    }

    private class ConversationFilter extends Filter {
        List<EMContact> mOriginalValues = null;

        public ConversationFilter(List<EMContact> mList) {
            mOriginalValues = mList;
        }

        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            FilterResults results = new FilterResults();

            if (mOriginalValues == null) {
                mOriginalValues = new ArrayList<EMContact>();
            }
            if (prefix == null || prefix.length() == 0) {
                results.values = copyConversationList;
                results.count = copyConversationList.size();
            } else {
                String prefixString = prefix.toString();
                final int count = mOriginalValues.size();
                final ArrayList<EMContact> newValues = new ArrayList<EMContact>();

                for (int i = 0; i < count; i++) {
                    final EMContact value = mOriginalValues.get(i);
                    String username = value.getUsername();

                    EMGroup group = EMClient.getInstance().groupManager().getGroup(username);
                    if(group != null){
                        username = group.getGroupName();
                    }

                    // First match against the whole ,non-splitted value
                    if (username.startsWith(prefixString)) {
                        newValues.add(value);
                    } else{
                        final String[] words = username.split(" ");
                        final int wordCount = words.length;

                        // Start at index 0, in case valueText starts with space(s)
                        for (int k = 0; k < wordCount; k++) {
                            if (words[k].startsWith(prefixString)) {
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
            conversationList.addAll((List<EMContact>) results.values);
            if (results.count > 0) {
                notiyfyByFilter = true;
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }

        }

    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        if(!notiyfyByFilter){
            copyConversationList.clear();
            copyConversationList.addAll(conversationList);
            notiyfyByFilter = false;
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
                    //textView.setText(user.getNickname());
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
