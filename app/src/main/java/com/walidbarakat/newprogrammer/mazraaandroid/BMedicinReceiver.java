package com.walidbarakat.newprogrammer.mazraaandroid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by PC on 13/04/2018.
 */

public class BMedicinReceiver extends BroadcastReceiver {
    public BMedicinReceiver() {
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        //NotificationManager mm=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        //mm.cancel(intent.getExtras().getInt("btn_id"));
        Intent intent1 = new Intent(context, BMedicinService.class);
        context.startService(intent1);
    }
}
