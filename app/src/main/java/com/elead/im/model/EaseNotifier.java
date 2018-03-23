/************************************************************
 * * Hyphenate CONFIDENTIAL
 * __________________
 * Copyright (C) 2016 Hyphenate Inc. All rights reserved.
 * <p>
 * NOTICE: All information contained herein is, and remains
 * the property of Hyphenate Inc.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Hyphenate Inc.
 */
package com.elead.im.model;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;

import com.elead.eplatform.R;
import com.elead.im.controller.EaseUI;
import com.elead.im.receiver.NotificationClickReceiver;
import com.elead.im.util.EaseCommonUtils;
import com.elead.im.util.EaseSmileUtils;
import com.elead.im.widget.EaseConstant;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.exceptions.HyphenateException;
import com.hyphenate.util.EMLog;
import com.hyphenate.util.EasyUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * new message notifier class
 * <p>
 * this class is subject to be inherited and implement the relative APIs
 */
public class EaseNotifier {
    private final static String TAG = "notify";
    Ringtone ringtone = null;

    protected final static String[] msg_eng = {"sent a message", "sent a picture", "sent a voice",
            "sent location message", "sent a video", "sent a file", "%1 contacts sent %2 messages"
    };
    protected final static String[] msg_ch = {"发来一条消息", "发来一张图片", "发来一段语音", "发来位置信息", "发来一个视频", "发来一个文件",
            "%1个联系人发来%2条消息"
    };

    protected static int notifyID = 0525; // start notification id
    protected static int foregroundNotifyID = 0555;

    protected NotificationManager notificationManager = null;

    protected HashSet<String> fromUsers = new HashSet<String>();
    protected int notificationNum = 0;

    protected Context appContext;
    protected String packageName;
    protected String[] msgs;
    protected long lastNotifiyTime;
    protected AudioManager audioManager;
    protected Vibrator vibrator;
    protected EaseNotificationInfoProvider notificationInfoProvider;

    public EaseNotifier() {
    }

    /**
     * this function can be override
     *
     * @param context
     * @return
     */
    public EaseNotifier init(Context context) {
        appContext = context;
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        packageName = appContext.getApplicationInfo().packageName;
        if (Locale.getDefault().getLanguage().equals("zh")) {
            msgs = msg_ch;
        } else {
            msgs = msg_eng;
        }

        audioManager = (AudioManager) appContext.getSystemService(Context.AUDIO_SERVICE);
        vibrator = (Vibrator) appContext.getSystemService(Context.VIBRATOR_SERVICE);

        return this;
    }

    /**
     * this function can be override
     */
    public void reset() {
        resetNotificationCount();
        cancelNotificaton();
    }

    void resetNotificationCount() {
        notificationNum = 0;
        fromUsers.clear();
    }

    void cancelNotificaton() {
        if (notificationManager != null)
            notificationManager.cancel(notifyID);
    }

    /**
     * handle the new message
     * this function can be override
     *
     * @param message
     */
    public synchronized void onNewMsg(EMMessage message) {
        if (EMClient.getInstance().chatManager().isSilentMessage(message)) {
            return;
        }
        EaseUI.EaseSettingsProvider settingsProvider = EaseUI.getInstance().getSettingsProvider();
        if (!settingsProvider.isMsgNotifyAllowed(message)) {
            return;
        }

        // check if app running background
        if (!EasyUtils.isAppRunningForeground(appContext)) {
            EMLog.d(TAG, "app is running in backgroud");
            sendNotification(message, false);
        } else {
            sendNotification(message, true);

        }

        vibrateAndPlayTone(message);
    }

    public synchronized void onNewMesg(List<EMMessage> messages) {
        if (EMClient.getInstance().chatManager().isSilentMessage(messages.get(messages.size() - 1))) {
            return;
        }
        EaseUI.EaseSettingsProvider settingsProvider = EaseUI.getInstance().getSettingsProvider();
        if (!settingsProvider.isMsgNotifyAllowed(null)) {
            return;
        }
        // check if app running background
        if (!EasyUtils.isAppRunningForeground(appContext)) {
            EMLog.d(TAG, "app is running in backgroud");
            sendNotification(messages, false);
        } else {
            sendNotification(messages, true);
        }
        vibrateAndPlayTone(messages.get(messages.size() - 1));
    }

    /**
     * send it to notification bar
     * This can be override by subclass to provide customer implementation
     *
     * @param messages
     * @param isForeground
     */
    protected void sendNotification(List<EMMessage> messages, boolean isForeground) {
        for (EMMessage message : messages) {
            if (!isForeground) {
                notificationNum++;
                fromUsers.add(message.getFrom());
            }
        }
        sendNotification(messages.get(messages.size() - 1), isForeground, false);
    }

    protected void sendNotification(EMMessage message, boolean isForeground) {
        sendNotification(message, isForeground, true);
    }


    /**
     * send it to notification bar
     * This can be override by subclass to provide customer implementation
     *
     * @param message
     */
    public static Map<String, Integer> msgCount = new HashMap<>();

    protected void sendNotification(EMMessage message, boolean isForeground, boolean numIncrease) {
        String username = message.getFrom();
        if (null == msgCount.get(username) || 0 == msgCount.get(username)) {
            msgCount.put(username, 1);
        } else {
            msgCount.put(username, msgCount.get(username) + 1);
        }

        int notifyID = 0;
        try {
            notifyID = Integer.parseInt(username.substring(1));
        } catch (Exception e) {

        }
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(appContext)
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true);

        Intent msgIntent = new Intent(appContext, NotificationClickReceiver.class);
        msgIntent.putExtra(EaseConstant.EXTRA_USER_ID, username);

        PendingIntent pendingIntent = PendingIntent.getActivity(appContext, notifyID, msgIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        if (numIncrease) {
            // prepare latest event info section
            if (!isForeground) {
                notificationNum++;
                fromUsers.add(message.getFrom());
            }
        }


        String nickName = null;
        try {
            nickName = message.getStringAttribute("nickName");
        } catch (HyphenateException e) {
            e.printStackTrace();
        }

        mBuilder.setContentTitle(nickName);
        mBuilder.setTicker(nickName);
        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setSmallIcon(R.drawable.ic_launcher);
        mBuilder.setColor(Color.WHITE);
        mBuilder.setLargeIcon(BitmapFactory.decodeResource(appContext.getResources(), R.drawable.ic_launcher));
        int count = msgCount.get(username);
        String aa = count > 1 ? "[" + msgCount.get(username) + "条]" : "";
        mBuilder.setContentText(aa + nickName + ":" + EaseSmileUtils.getSmiledText(appContext, EaseCommonUtils.getMessageDigest(message, appContext)));
        mBuilder.setNumber(notificationNum);
        Notification notification = mBuilder.build();

        notificationManager.notify(notifyID, notification);


    }

    /**
     * vibrate and  play tone
     */
    public void vibrateAndPlayTone(EMMessage message) {
        if (message != null) {
            if (EMClient.getInstance().chatManager().isSilentMessage(message)) {
                return;
            }
        }

        if (System.currentTimeMillis() - lastNotifiyTime < 1000) {
            // received new messages within 2 seconds, skip play ringtone
            return;
        }

        try {
            lastNotifiyTime = System.currentTimeMillis();

            // check if in silent mode
            if (audioManager.getRingerMode() == AudioManager.RINGER_MODE_SILENT) {
                EMLog.e(TAG, "in slient mode now");
                return;
            }
            EaseUI.EaseSettingsProvider settingsProvider = EaseUI.getInstance().getSettingsProvider();
            if (settingsProvider.isMsgVibrateAllowed(message)) {
                long[] pattern = new long[]{0, 180, 80, 120};
                vibrator.vibrate(pattern, -1);
            }

            if (settingsProvider.isMsgSoundAllowed(message)) {
                if (ringtone == null) {
                    Uri notificationUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

                    ringtone = RingtoneManager.getRingtone(appContext, notificationUri);
                    if (ringtone == null) {
                        EMLog.d(TAG, "cant find ringtone at:" + notificationUri.getPath());
                        return;
                    }
                }

                if (!ringtone.isPlaying()) {
                    String vendor = Build.MANUFACTURER;

                    ringtone.play();
                    // for samsung S3, we meet a bug that the phone will
                    // continue ringtone without stop
                    // so add below special handler to stop it after 3s if
                    // needed
                    if (vendor != null && vendor.toLowerCase().contains("samsung")) {
                        Thread ctlThread = new Thread() {
                            public void run() {
                                try {
                                    Thread.sleep(3000);
                                    if (ringtone.isPlaying()) {
                                        ringtone.stop();
                                    }
                                } catch (Exception e) {
                                }
                            }
                        };
                        ctlThread.run();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * set notification info Provider
     *
     * @param provider
     */
    public void setNotificationInfoProvider(EaseNotificationInfoProvider provider) {
        notificationInfoProvider = provider;
    }

    public interface EaseNotificationInfoProvider {
        /**
         * set the notification content, such as "you received a new image from xxx"
         *
         * @param message
         * @return null-will use the default text
         */
        String getDisplayedText(EMMessage message);

        /**
         * set the notification content: such as "you received 5 message from 2 contacts"
         *
         * @param message
         * @param fromUsersNum- number of message sender
         * @param messageNum    -number of messages
         * @return null-will use the default text
         */
        String getLatestText(EMMessage message, int fromUsersNum, int messageNum);

        /**
         * 设置notification标题
         *
         * @param message
         * @return null- will use the default text
         */
        String getTitle(EMMessage message);

        /**
         * set the small icon
         *
         * @param message
         * @return 0- will use the default icon
         */
        int getSmallIcon(EMMessage message);

        /**
         * set the intent when notification is pressed
         *
         * @param message
         * @return null- will use the default icon
         */
        Intent getLaunchIntent(EMMessage message);
    }
}
