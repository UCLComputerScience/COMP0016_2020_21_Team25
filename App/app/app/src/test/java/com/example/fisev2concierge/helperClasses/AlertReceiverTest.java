package com.example.fisev2concierge.helperClasses;

import android.content.Intent;

import com.example.fisev2concierge.UI.MainActivity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertEquals;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
public class AlertReceiverTest {
    //Adding tests for buttons on MainActivity

    @Test
    public void makingCall() {
//        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        NotificationHelper notificationHelper = new NotificationHelper(activity);
        AlertReceiver alertReceiver = new AlertReceiver();
        Intent intent = new Intent();
        alertReceiver.onReceive(activity, intent);
        assertEquals(notificationHelper.getManager().getActiveNotifications().length, 1);
    }

}