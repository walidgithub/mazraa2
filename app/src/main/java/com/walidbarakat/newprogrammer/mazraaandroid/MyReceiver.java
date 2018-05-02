package com.walidbarakat.newprogrammer.mazraaandroid;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by PC on 01/04/2018.
 */

public class MyReceiver extends BroadcastReceiver {
    public MyReceiver() {
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        //NotificationManager mm=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        //mm.cancel(intent.getExtras().getInt("btn_id"));
        Intent intent1 = new Intent(context, MyNewIntentService.class);
        context.startService(intent1);
    }
}
