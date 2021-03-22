package com.example.fisev2concierge.helperClasses;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.speech.SpeechRecognizer;

import androidx.core.app.NotificationCompat;

public class AlertReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationHelper notificationHelper = new NotificationHelper(context);
        NotificationCompat.Builder builder = notificationHelper.getChannel1Notification("Concierge Alarm", intent.getStringExtra("message"));
        notificationHelper.getManager().notify(1, builder.build());
    }
}
