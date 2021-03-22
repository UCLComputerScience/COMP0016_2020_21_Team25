package com.example.fisev2concierge.functionalityClasses;

import android.Manifest;

import com.example.fisev2concierge.UI.HistoryActivity;
import com.example.fisev2concierge.UI.MainActivity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowToast;

import static org.junit.Assert.assertEquals;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
public class SmsFunctionalityTest {

    @Test
    public void requestPermissionTest(){
        HistoryActivity activity = Robolectric.buildActivity(HistoryActivity.class).get();
        SmsFunctionality smsFunctionality = new SmsFunctionality(activity, activity);
        ShadowActivity shadowActivity = shadowOf(activity);
        smsFunctionality.sendSMS("012345678910", "test");
        assertEquals("android.permission.SEND_SMS", shadowActivity.getLastRequestedPermission().requestedPermissions[0]);
    }

    @Test
    public void sendSMSTest() {
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        SmsFunctionality smsFunctionality = new SmsFunctionality(activity, activity);
        ShadowActivity shadowApplication = shadowOf(activity);
        shadowApplication.grantPermissions(Manifest.permission.SEND_SMS);
        smsFunctionality.sendSMS("012345678910", "test");
        assertEquals("Text sent successfully", ShadowToast.getTextOfLatestToast());
    }

    @Test
    public void sendSMSErrorTest() {
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        SmsFunctionality smsFunctionality = new SmsFunctionality(activity, activity);
        ShadowActivity shadowApplication = shadowOf(activity);
        shadowApplication.grantPermissions(Manifest.permission.SEND_SMS);
        smsFunctionality.sendSMS(null, null);
        assertEquals("Error sending text", ShadowToast.getTextOfLatestToast());
    }
}