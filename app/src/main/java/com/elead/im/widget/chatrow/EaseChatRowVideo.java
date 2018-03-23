package com.elead.im.widget.chatrow;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.elead.eplatform.R;
import com.elead.im.activity.EaseShowVideoActivity;
import com.elead.im.adapter.EaseMessageAdapter;
import com.elead.im.model.EaseImageCache;
import com.elead.im.util.EaseCommonUtils;
import com.elead.im.util.EaseUserUtils;
import com.elead.im.widget.EaseChatMessageList;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMFileMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMMessage.ChatType;
import com.hyphenate.chat.EMVideoMessageBody;
import com.hyphenate.util.DateUtils;
import com.hyphenate.util.EMLog;
import com.hyphenate.util.ImageUtils;
import com.hyphenate.util.TextFormater;

import java.io.File;
import java.util.Date;

public class EaseChatRowVideo extends EaseChatRowFile {

    private ImageView imageView;
    private TextView sizeView;
    private TextView timeLengthView;

    public EaseChatRowVideo(Context context, EMMessage message, int position, BaseAdapter adapter) {
        super(context, message, position, adapter);
    }

    @Override
    protected void onInflateView() {
        inflater.inflate(message.direct() == EMMessage.Direct.RECEIVE ?
                R.layout.ease_row_received_video : R.layout.ease_row_sent_video, this);
    }

    @Override
    protected void onFindViewById() {
        imageView = ((ImageView) findViewById(R.id.chatting_content_iv));
        sizeView = (TextView) findViewById(R.id.chatting_size_iv);
        timeLengthView = (TextView) findViewById(R.id.chatting_length_iv);
        ImageView playView = (ImageView) findViewById(R.id.chatting_status_btn);
        percentageView = (TextView) findViewById(R.id.percentage);
    }

    @Override
    protected void onSetUpView() {
        EMVideoMessageBody videoBody = (EMVideoMessageBody) message.getBody();
        String localThumb = videoBody.getLocalThumb();

        if (localThumb != null) {

            showVideoThumbView(localThumb, imageView, videoBody.getThumbnailUrl(), message);
        }
        if (videoBody.getDuration() > 0) {
            String time = DateUtils.toTime(videoBody.getDuration());
            timeLengthView.setText(time);
        }

        if (message.direct() == EMMessage.Direct.RECEIVE) {
            if (videoBody.getVideoFileLength() > 0) {
                String size = TextFormater.getDataSize(videoBody.getVideoFileLength());
                sizeView.setText(size);
            }
        } else {
            if (videoBody.getLocalUrl() != null && new File(videoBody.getLocalUrl()).exists()) {
                String size = TextFormater.getDataSize(new File(videoBody.getLocalUrl()).length());
                sizeView.setText(size);
            }
        }

        EMLog.d(TAG, "video thumbnailStatus:" + videoBody.thumbnailDownloadStatus());
        if (message.direct() == EMMessage.Direct.RECEIVE) {
            if (videoBody.thumbnailDownloadStatus() == EMFileMessageBody.EMDownloadStatus.DOWNLOADING ||
                    videoBody.thumbnailDownloadStatus() == EMFileMessageBody.EMDownloadStatus.PENDING) {
                imageView.setImageResource(R.drawable.ic_launcher);
                setMessageReceiveCallback();
            } else {
                // System.err.println("!!!! not back receive, show image directly");
                imageView.setImageResource(R.drawable.ic_launcher);
                if (localThumb != null) {
                    showVideoThumbView(localThumb, imageView, videoBody.getThumbnailUrl(), message);
                }

            }

            return;
        }
        //handle sending message
        handleSendMessage();
    }

    @Override
    protected void onBubbleClick() {
        EMVideoMessageBody videoBody = (EMVideoMessageBody) message.getBody();
        EMLog.d(TAG, "video view is on click");
        Intent intent = new Intent(context, EaseShowVideoActivity.class);
        intent.putExtra("localpath", videoBody.getLocalUrl());
        intent.putExtra("secret", videoBody.getSecret());
        intent.putExtra("remotepath", videoBody.getRemoteUrl());
        if (message != null && message.direct() == EMMessage.Direct.RECEIVE && !message.isAcked()
                && message.getChatType() == ChatType.Chat) {
            try {
                EMClient.getInstance().chatManager().ackMessageRead(message.getFrom(), message.getMsgId());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        activity.startActivity(intent);
    }

    /**
     * show video thumbnails
     *
     * @param localThumb   local path for thumbnail
     * @param iv
     * @param thumbnailUrl Url on server for thumbnails
     * @param message
     */
    private void showVideoThumbView(final String localThumb, final ImageView iv, String thumbnailUrl, final EMMessage message) {
        // first check if the thumbnail image already loaded into cache
        Bitmap bitmap = EaseImageCache.getInstance().get(localThumb);
        if (bitmap != null) {
            // thumbnail image is already loaded, reuse the drawable
            iv.setImageBitmap(bitmap);

        } else {
            new AsyncTask<Void, Void, Bitmap>() {

                @Override
                protected Bitmap doInBackground(Void... params) {
                    if (new File(localThumb).exists()) {
                        return ImageUtils.decodeScaleImage(localThumb, 160, 160);
                    } else {
                        return null;
                    }
                }

                @Override
                protected void onPostExecute(Bitmap result) {
                    super.onPostExecute(result);
                    if (result != null) {
                        EaseImageCache.getInstance().put(localThumb, result);
                        iv.setImageBitmap(result);

                    } else {
                        if (message.status() == EMMessage.Status.FAIL) {
                            if (EaseCommonUtils.isNetWorkConnected(activity)) {
                                EMClient.getInstance().chatManager().downloadThumbnail(message);
                            }
                        }

                    }
                }
            }.execute();
        }

    }

    public abstract static class EaseChatRow extends LinearLayout {
        protected static final String TAG = EaseChatRow.class.getSimpleName();

        protected LayoutInflater inflater;
        protected Context context;
        protected BaseAdapter adapter;
        protected EMMessage message;
        protected int position;

        protected TextView timeStampView;
        protected ImageView userAvatarView;
        protected View bubbleLayout;
        protected TextView usernickView;

        protected TextView percentageView;
        protected ProgressBar progressBar;
        protected ImageView statusView;
        protected Activity activity;

        protected TextView ackedView;
        protected TextView deliveredView;

        protected EMCallBack messageSendCallback;
        protected EMCallBack messageReceiveCallback;

        protected EaseChatMessageList.MessageListItemClickListener itemClickListener;

        public EaseChatRow(Context context, EMMessage message, int position, BaseAdapter adapter) {
            super(context);
            this.context = context;
            this.activity = (Activity) context;
            this.message = message;
            this.position = position;
            this.adapter = adapter;
            inflater = LayoutInflater.from(context);

            initView();
        }

        private void initView() {
            onInflateView();
            timeStampView = (TextView) findViewById(R.id.timestamp);
            userAvatarView = (ImageView) findViewById(R.id.iv_userhead);
            bubbleLayout = findViewById(R.id.bubble);
            usernickView = (TextView) findViewById(R.id.tv_userid);

            progressBar = (ProgressBar) findViewById(R.id.progress_bar);
            statusView = (ImageView) findViewById(R.id.msg_status);
            ackedView = (TextView) findViewById(R.id.tv_ack);
            deliveredView = (TextView) findViewById(R.id.tv_delivered);

            onFindViewById();
        }

        /**
         * set property according message and postion
         *
         * @param message
         * @param position
         */
        public void setUpView(EMMessage message, int position,
                              EaseChatMessageList.MessageListItemClickListener itemClickListener) {
            this.message = message;
            this.position = position;
            this.itemClickListener = itemClickListener;

            setUpBaseView();
            onSetUpView();
            setClickListener();
        }

        private void setUpBaseView() {
            // set nickname, avatar and background of bubble
            TextView timestamp = (TextView) findViewById(R.id.timestamp);
            if (timestamp != null) {
                if (position == 0) {
                    timestamp.setText(DateUtils.getTimestampString(new Date(message.getMsgTime())));
                    timestamp.setVisibility(View.VISIBLE);
                } else {
                    // show time stamp if interval with last message is > 30 seconds
                    EMMessage prevMessage = (EMMessage) adapter.getItem(position - 1);
                    if (prevMessage != null && DateUtils.isCloseEnough(message.getMsgTime(), prevMessage.getMsgTime())) {
                        timestamp.setVisibility(View.GONE);
                    } else {
                        timestamp.setText(DateUtils.getTimestampString(new Date(message.getMsgTime())));
                        timestamp.setVisibility(View.VISIBLE);
                    }
                }
            }
            //set nickname and avatar
            if (message.direct() == EMMessage.Direct.SEND) {
                EaseUserUtils.setUserAvatar(context, EMClient.getInstance().getCurrentUser(), userAvatarView);
            } else {
                EaseUserUtils.setUserAvatar(context, message.getFrom(), userAvatarView);
                EaseUserUtils.setUserNick(message.getFrom(), usernickView);
            }

            if (deliveredView != null) {
                if (message.isDelivered()) {
                    deliveredView.setVisibility(View.VISIBLE);
                } else {
                    deliveredView.setVisibility(View.INVISIBLE);
                }
            }

            if (ackedView != null) {
                if (message.isAcked()) {
                    if (deliveredView != null) {
                        deliveredView.setVisibility(View.INVISIBLE);
                    }
                    ackedView.setVisibility(View.VISIBLE);
                } else {
                    ackedView.setVisibility(View.INVISIBLE);
                }
            }


            if (adapter instanceof EaseMessageAdapter) {
                if (((EaseMessageAdapter) adapter).isShowAvatar())
                    userAvatarView.setVisibility(View.VISIBLE);
                else
                    userAvatarView.setVisibility(View.GONE);
                if (usernickView != null) {
                    if (((EaseMessageAdapter) adapter).isShowUserNick())
                        usernickView.setVisibility(View.VISIBLE);
                    else
                        usernickView.setVisibility(View.GONE);
                }
                if (message.direct() == EMMessage.Direct.SEND) {
                    if (((EaseMessageAdapter) adapter).getMyBubbleBg() != null) {
                        bubbleLayout.setBackgroundDrawable(((EaseMessageAdapter) adapter).getMyBubbleBg());
                    }
                } else if (message.direct() == EMMessage.Direct.RECEIVE) {
                    if (((EaseMessageAdapter) adapter).getOtherBuddleBg() != null) {
                        bubbleLayout.setBackgroundDrawable(((EaseMessageAdapter) adapter).getOtherBuddleBg());
                    }
                }
            }
        }

        /**
         * set callback for sending message
         */
        protected void setMessageSendCallback() {
            if (messageSendCallback == null) {
                messageSendCallback = new EMCallBack() {

                    @Override
                    public void onSuccess() {
                        updateView();
                    }

                    @Override
                    public void onProgress(final int progress, String status) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (percentageView != null)
                                    percentageView.setText(progress + "%");

                            }
                        });
                    }

                    @Override
                    public void onError(int code, String error) {
                        updateView();
                    }
                };
            }
            message.setMessageStatusCallback(messageSendCallback);
        }

        /**
         * set callback for receiving message
         */
        protected void setMessageReceiveCallback() {
            if (messageReceiveCallback == null) {
                messageReceiveCallback = new EMCallBack() {

                    @Override
                    public void onSuccess() {
                        updateView();
                    }

                    @Override
                    public void onProgress(final int progress, String status) {
                        activity.runOnUiThread(new Runnable() {
                            public void run() {
                                if (percentageView != null) {
                                    percentageView.setText(progress + "%");
                                }
                            }
                        });
                    }

                    @Override
                    public void onError(int code, String error) {
                        updateView();
                    }
                };
            }
            message.setMessageStatusCallback(messageReceiveCallback);
        }


        private void setClickListener() {
            if (bubbleLayout != null) {
                bubbleLayout.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        if (itemClickListener != null) {
                            if (!itemClickListener.onBubbleClick(message)) {
                                // if listener return false, we call default handling
                                onBubbleClick();
                            }
                        }
                    }
                });

                bubbleLayout.setOnLongClickListener(new OnLongClickListener() {

                    @Override
                    public boolean onLongClick(View v) {
                        if (itemClickListener != null) {
                            itemClickListener.onBubbleLongClick(message);
                        }
                        return true;
                    }
                });
            }

            if (statusView != null) {
                statusView.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        if (itemClickListener != null) {
                            itemClickListener.onResendClick(message);
                        }
                    }
                });
            }

            if (userAvatarView != null) {
                userAvatarView.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        if (itemClickListener != null) {
                            if (message.direct() == EMMessage.Direct.SEND) {
                                itemClickListener.onUserAvatarClick(EMClient.getInstance().getCurrentUser());
                            } else {
                                itemClickListener.onUserAvatarClick(message.getFrom());
                            }
                        }
                    }
                });
                userAvatarView.setOnLongClickListener(new OnLongClickListener() {

                    @Override
                    public boolean onLongClick(View v) {
                        if (itemClickListener != null) {
                            if (message.direct() == EMMessage.Direct.SEND) {
                                itemClickListener.onUserAvatarLongClick(EMClient.getInstance().getCurrentUser());
                            } else {
                                itemClickListener.onUserAvatarLongClick(message.getFrom());
                            }
                            return true;
                        }
                        return false;
                    }
                });
            }
        }


        protected void updateView() {
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    if (message.status() == EMMessage.Status.FAIL) {

                        if (message.getError() == EMError.MESSAGE_INCLUDE_ILLEGAL_CONTENT) {
                            Toast.makeText(activity, activity.getString(R.string.send_fail) + activity.getString(R.string.error_send_invalid_content), Toast.LENGTH_SHORT).show();
                        } else if (message.getError() == EMError.GROUP_NOT_JOINED) {
                            Toast.makeText(activity, activity.getString(R.string.send_fail) + activity.getString(R.string.error_send_not_in_the_group), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(activity, activity.getString(R.string.send_fail) + activity.getString(R.string.connect_failuer_toast), Toast.LENGTH_SHORT).show();
                        }
                    }

                    onUpdateView();
                }
            });

        }

        protected abstract void onInflateView();

        /**
         * find view by id
         */
        protected abstract void onFindViewById();

        /**
         * refresh list view when message status change
         */
        protected abstract void onUpdateView();

        /**
         * setup view
         */
        protected abstract void onSetUpView();

        /**
         * on bubble clicked
         */
        protected abstract void onBubbleClick();

    }
}
