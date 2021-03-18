package com.example.fisev2concierge.helperClasses;

import android.app.NotificationManager;
import android.content.Context;

import com.example.fisev2concierge.UI.MainActivity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowContextWrapper;
import org.robolectric.shadows.ShadowNotificationManager;

import static org.junit.Assert.assertEquals;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
public class NotificationHelperTest {
    //Adding tests for buttons on MainActivity

    @Test
    public void makingCall() {
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        ShadowActivity shadowActivity = shadowOf(activity);
        NotificationManager notificationManager = (NotificationManager) activity.getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        ShadowNotificationManager shadowNotificationManager = shadowOf(notificationManager);
        NotificationHelper notificationHelper = new NotificationHelper(activity);
        ShadowContextWrapper shadowContextWrapper = shadowOf(notificationHelper);
        notificationHelper.createChannels();
        notificationHelper.getManager();
        notificationHelper.getChannel1Notification("channel 1", "notification 1");
        System.out.println(shadowNotificationManager.getAllNotifications().toArray().length);
    }

}