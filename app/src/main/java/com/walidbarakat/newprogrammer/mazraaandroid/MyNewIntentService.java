package com.walidbarakat.newprogrammer.mazraaandroid;

import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.NotificationCompat;

/**
 * Created by PC on 07/04/2018.
 */

public class MyNewIntentService extends IntentService {
    private static final int NOTIFICATION_ID = DataEntry.NotificationId;

    public MyNewIntentService() {
        super("MyNewIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle("الفطام");
        builder.setContentText("يوجد مواليد يجب فطامها اليوم");
        builder.setSmallIcon(R.drawable.wean);
        builder.setDefaults(NotificationCompat.DEFAULT_SOUND);
        Intent notifyIntent = new Intent(this, EndBaby.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, NOTIFICATION_ID, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        //to be able to launch your activity from the notification
        builder.setContentIntent(pendingIntent);
        Notification notificationCompat = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            notificationCompat = builder.build();
        }
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(NOTIFICATION_ID, notificationCompat);
    }
}
