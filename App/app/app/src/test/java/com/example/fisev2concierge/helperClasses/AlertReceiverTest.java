package com.example.fisev2concierge.helperClasses;

import android.content.Intent;

import com.example.fisev2concierge.UI.MainActivity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
public class AlertReceiverTest {

    @Test
    public void alertReceiverOnReceieveTest() {
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        NotificationHelper notificationHelper = new NotificationHelper(activity);
        AlertReceiver alertReceiver = new AlertReceiver();
        Intent intent = new Intent();
        alertReceiver.onReceive(activity, intent);
        assertEquals(1, notificationHelper.getManager().getActiveNotifications().length);
    }

}