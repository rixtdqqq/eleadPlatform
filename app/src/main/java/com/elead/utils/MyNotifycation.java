package com.elead.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.elead.eplatform.R;

import static android.content.Context.NOTIFICATION_SERVICE;



public class MyNotifycation {

    private static final int NOTIFICATION_ID = 1;
    public static NotificationManager notificationManager;
    private static RemoteViews rv;

    public static void creatNotification(Context context, String title,
                                         String contentTop, String contentBottom, Boolean isRing,
                                         Intent intent, Service service) {
        // mService = service;

        PendingIntent pi = PendingIntent.getActivity(context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        notificationManager = (NotificationManager) context
                .getSystemService(NOTIFICATION_SERVICE);
        rv = new RemoteViews(context.getPackageName(), R.layout.notifycation);
        Notification notification = new Notification();
        notification.icon = R.drawable.logo;
        notification.tickerText = title;
        notification.contentIntent = pi;
        notification.contentView = rv;
        // notification.flags |= Notification.FLAG_ONGOING_EVENT;//点击后不消失
        notification.flags |= Notification.FLAG_AUTO_CANCEL;// 点击后消失
        if (isRing) {
            notification.defaults = Notification.DEFAULT_SOUND;
        }
        // NotifyReciver receiver = new NotifyReciver();
        // IntentFilter filter = new IntentFilter();
        // filter.addAction(ZXING_BROADCAST_NAME);
        // filter.addAction(MSG_SWITCH_BROADCAST_NAME);
        // service.registerReceiver(receiver, filter);

        // Intent zxingIntent = new Intent(context,HomeActivity.class);
        // zxingIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // PendingIntent zxingPendingIntent = PendingIntent.getActivity(context,
        // 0, zxingIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        // rv.setOnClickPendingIntent(R.id.qrcode, zxingPendingIntent);
        //
        // Intent msgSwitchIntent = new Intent(MSG_SWITCH_BROADCAST_NAME);
        // msgSwitchIntent.putExtra("FLAG", MSG_SWITCH_FLAG);
        // PendingIntent switchPendingIntent =
        // PendingIntent.getBroadcast(context,
        // 0, msgSwitchIntent, 0);
        // rv.setOnClickPendingIntent(R.id.qrcode1, switchPendingIntent);

        //

        //
        // if(bitmap != null) {
        // rv.setImageViewBitmap(R.id.image, bitmap);
        // } else {
        // rv.setImageViewResource(R.id.image, R.drawable.img_album_background);
        // }
        rv.setTextViewText(R.id.tv_top, contentTop);
        rv.setTextViewText(R.id.tv_bottom, contentBottom);
        if (null != service) {
            service.startForeground(NOTIFICATION_ID, notification);
        } else {
            notificationManager.notify(NOTIFICATION_ID, notification);
        }

    }


   /* public static void creatNotify(Context context, String title, String content, int smallIcon, int largeIcon, Intent intent, Service service) {
        NotificationManager manager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        //NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
//        Notification notification = builder
//                .setContentTitle(title)
//                .setContentText(content)
//                .setWhen(System.currentTimeMillis())
//                .setSmallIcon(smallIcon)
//                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), largeIcon))
//                .setColor(Color.TRANSPARENT) //  修改小圆圈的颜色
//                .build();
        notification.defaults = Notification.DEFAULT_SOUND;
        PendingIntent pi = PendingIntent.getActivity(context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        if (null != intent)
            notification.contentIntent = pi;
        if (null != service) {
            service.startForeground(NOTIFICATION_ID, notification);
        } else {
            manager.notify(NOTIFICATION_ID, notification);
        }
    }*/

    public static void cancleNotify() {
        if (null != notificationManager) {
            notificationManager.cancel(NOTIFICATION_ID);
        }
    }

}
