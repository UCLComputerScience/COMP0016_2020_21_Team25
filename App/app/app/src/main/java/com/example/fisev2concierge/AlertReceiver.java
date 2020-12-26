package com.example.fisev2concierge;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

//import com.example.fisev2concierge.model.Notification;

public class AlertReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationHelper notificationHelper = new NotificationHelper(context);
        NotificationCompat.Builder builder = notificationHelper.getChannel1Notification("Test", "Test");
        notificationHelper.getManager().notify(1, builder.build());
    }
}
