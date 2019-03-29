package ru.wdsoft.utils;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;

import ru.wdsoft.R;

/**
 * Created by slaventii@mail.ru on 13.02.2019.
 */
public class NotifyUtils {

    /**
     * НОТИФИКАТОРЫ
     */

    public static void notifyUser(Context ctx, String message, int notifyId){

        notifyUser(ctx, message, notifyId, null);

    }

    public static void notifyUser(Context ctx, String message, int notifyId,
                                  Intent notificationIntent) {

        NotificationManager nManager = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(ctx);
        mBuilder.setSmallIcon(R.drawable.ic_notification);
        mBuilder.setColor(Color.RED);

        mBuilder.setLargeIcon(BitmapFactory.decodeResource(ctx.getResources(),
                R.mipmap.ic_launcher));
        mBuilder.setContentTitle(ctx.getString(R.string.app_name));
        mBuilder.setStyle(new NotificationCompat.BigTextStyle().bigText(message));
        mBuilder.setContentText(message);
        //mBuilder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        mBuilder.setAutoCancel(true);
        nManager.notify(notifyId, mBuilder.build());

        if (notificationIntent != null){

            PendingIntent pendingIntent = PendingIntent.getActivity(ctx, 1, notificationIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            mBuilder.setContentIntent(pendingIntent);

            nManager.notify(notifyId, mBuilder.build());
        }
    }

    public static void removeAllNotify(Context ctx, int id){
//        ShortcutBadger.removeCount(ctx);
        NotificationManager nManager = (
                NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);

        nManager.cancel(id);
    }

}
