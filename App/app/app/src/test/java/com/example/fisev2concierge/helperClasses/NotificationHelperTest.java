package com.example.fisev2concierge.helperClasses;

import com.example.fisev2concierge.views.MainActivity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(RobolectricTestRunner.class)
public class NotificationHelperTest {

    @Test
    public void notificationHelperCreateChannelsTest() {
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        NotificationHelper notificationHelper = new NotificationHelper(activity);
        notificationHelper.createChannels();
    }

    @Test
    public void notificationHelperGetManagerTest() {
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        NotificationHelper notificationHelper = new NotificationHelper(activity);
        assertNotNull(notificationHelper.getManager());
    }

    @Test
    public void notificationHelperGetChannel1NotificationTest() {
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        NotificationHelper notificationHelper = new NotificationHelper(activity);
        assertNotNull(notificationHelper.getChannel1Notification("channel 1", "notification 1"));
    }

}